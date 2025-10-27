package org.eclipse.jetty.server.handler;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ContextHandlerCollection extends HandlerCollection {
    private static final Logger LOG = Log.getLogger((Class<?>) ContextHandlerCollection.class);
    private Class<? extends ContextHandler> _contextClass;
    private volatile PathMap _contextMap;

    public ContextHandlerCollection() {
        super(true);
        this._contextClass = ContextHandler.class;
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(StrPool.DOT) ? str.substring(0, str.length() - 1) : str;
    }

    public ContextHandler addContext(String str, String str2) throws IllegalAccessException, InstantiationException {
        try {
            ContextHandler contextHandlerNewInstance = this._contextClass.newInstance();
            contextHandlerNewInstance.setContextPath(str);
            contextHandlerNewInstance.setResourceBase(str2);
            addHandler(contextHandlerNewInstance);
            return contextHandlerNewInstance;
        } catch (Exception e2) {
            LOG.debug(e2);
            throw new Error(e2);
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerCollection, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        mapContexts();
        super.doStart();
    }

    public Class getContextClass() {
        return this._contextClass;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerCollection, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ContextHandler contextHandler;
        Handler[] handlers = getHandlers();
        if (handlers == null || handlers.length == 0) {
            return;
        }
        AsyncContinuation asyncContinuation = request.getAsyncContinuation();
        if (asyncContinuation.isAsync() && (contextHandler = asyncContinuation.getContextHandler()) != null) {
            contextHandler.handle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        PathMap pathMap = this._contextMap;
        if (pathMap == null || str == null || !str.startsWith("/")) {
            for (Handler handler : handlers) {
                handler.handle(str, request, httpServletRequest, httpServletResponse);
                if (request.isHandled()) {
                    return;
                }
            }
            return;
        }
        Object lazyMatches = pathMap.getLazyMatches(str);
        for (int i2 = 0; i2 < LazyList.size(lazyMatches); i2++) {
            Object value = ((Map.Entry) LazyList.get(lazyMatches, i2)).getValue();
            if (value instanceof Map) {
                Map map = (Map) value;
                String strNormalizeHostname = normalizeHostname(httpServletRequest.getServerName());
                Object obj = map.get(strNormalizeHostname);
                for (int i3 = 0; i3 < LazyList.size(obj); i3++) {
                    ((Handler) LazyList.get(obj, i3)).handle(str, request, httpServletRequest, httpServletResponse);
                    if (request.isHandled()) {
                        return;
                    }
                }
                Object obj2 = map.get("*." + strNormalizeHostname.substring(strNormalizeHostname.indexOf(StrPool.DOT) + 1));
                for (int i4 = 0; i4 < LazyList.size(obj2); i4++) {
                    ((Handler) LazyList.get(obj2, i4)).handle(str, request, httpServletRequest, httpServletResponse);
                    if (request.isHandled()) {
                        return;
                    }
                }
                Object obj3 = map.get("*");
                for (int i5 = 0; i5 < LazyList.size(obj3); i5++) {
                    ((Handler) LazyList.get(obj3, i5)).handle(str, request, httpServletRequest, httpServletResponse);
                    if (request.isHandled()) {
                        return;
                    }
                }
            } else {
                for (int i6 = 0; i6 < LazyList.size(value); i6++) {
                    ((Handler) LazyList.get(value, i6)).handle(str, request, httpServletRequest, httpServletResponse);
                    if (request.isHandled()) {
                        return;
                    }
                }
            }
        }
    }

    public void mapContexts() {
        Handler[] childHandlersByClass;
        Map map;
        PathMap pathMap = new PathMap();
        Handler[] handlers = getHandlers();
        for (int i2 = 0; handlers != null && i2 < handlers.length; i2++) {
            Handler handler = handlers[i2];
            if (handler instanceof ContextHandler) {
                childHandlersByClass = new Handler[]{handler};
            } else if (handler instanceof HandlerContainer) {
                childHandlersByClass = ((HandlerContainer) handler).getChildHandlersByClass(ContextHandler.class);
            } else {
                continue;
            }
            for (Handler handler2 : childHandlersByClass) {
                ContextHandler contextHandler = (ContextHandler) handler2;
                String contextPath = contextHandler.getContextPath();
                if (contextPath == null || contextPath.indexOf(44) >= 0 || contextPath.startsWith("*")) {
                    throw new IllegalArgumentException("Illegal context spec:" + contextPath);
                }
                if (!contextPath.startsWith("/")) {
                    contextPath = '/' + contextPath;
                }
                if (contextPath.length() > 1) {
                    if (contextPath.endsWith("/")) {
                        contextPath = contextPath + "*";
                    } else if (!contextPath.endsWith("/*")) {
                        contextPath = contextPath + "/*";
                    }
                }
                Object obj = pathMap.get(contextPath);
                String[] virtualHosts = contextHandler.getVirtualHosts();
                if (virtualHosts != null && virtualHosts.length > 0) {
                    if (obj instanceof Map) {
                        map = (Map) obj;
                    } else {
                        HashMap map2 = new HashMap();
                        map2.put("*", obj);
                        pathMap.put(contextPath, map2);
                        map = map2;
                    }
                    for (String str : virtualHosts) {
                        map.put(str, LazyList.add(map.get(str), handlers[i2]));
                    }
                } else if (obj instanceof Map) {
                    Map map3 = (Map) obj;
                    map3.put("*", LazyList.add(map3.get("*"), handlers[i2]));
                } else {
                    pathMap.put(contextPath, LazyList.add(obj, handlers[i2]));
                }
            }
        }
        this._contextMap = pathMap;
    }

    public void setContextClass(Class cls) {
        if (cls == null || !ContextHandler.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException();
        }
        this._contextClass = cls;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerCollection
    public void setHandlers(Handler[] handlerArr) {
        this._contextMap = null;
        super.setHandlers(handlerArr);
        if (isStarted()) {
            mapContexts();
        }
    }
}
