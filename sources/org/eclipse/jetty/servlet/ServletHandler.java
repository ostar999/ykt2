package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.ContinuationThrowable;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.RuntimeIOException;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServletRequestHttpWrapper;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ServletHandler extends ScopedHandler {
    private static final Logger LOG = Log.getLogger((Class<?>) ServletHandler.class);
    public static final String __DEFAULT_SERVLET = "default";
    private ServletContextHandler _contextHandler;
    private FilterMapping[] _filterMappings;
    private MultiMap<String> _filterNameMappings;
    private List<FilterMapping> _filterPathMappings;
    private IdentityService _identityService;
    private ContextHandler.Context _servletContext;
    private ServletMapping[] _servletMappings;
    private PathMap _servletPathMap;
    private FilterHolder[] _filters = new FilterHolder[0];
    private int _matchBeforeIndex = -1;
    private int _matchAfterIndex = -1;
    private boolean _filterChainsCached = true;
    private int _maxFilterChainsCacheSize = 512;
    private boolean _startWithUnavailable = false;
    private ServletHolder[] _servlets = new ServletHolder[0];
    private final Map<String, FilterHolder> _filterNameMap = new HashMap();
    private final Map<String, ServletHolder> _servletNameMap = new HashMap();
    protected final ConcurrentMap<String, FilterChain>[] _chainCache = new ConcurrentMap[31];
    protected final Queue<String>[] _chainLRU = new Queue[31];

    public class CachedChain implements FilterChain {
        FilterHolder _filterHolder;
        CachedChain _next;
        ServletHolder _servletHolder;

        public CachedChain(Object obj, ServletHolder servletHolder) {
            if (LazyList.size(obj) <= 0) {
                this._servletHolder = servletHolder;
            } else {
                this._filterHolder = (FilterHolder) LazyList.get(obj, 0);
                this._next = ServletHandler.this.new CachedChain(LazyList.remove(obj, 0), servletHolder);
            }
        }

        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws UnavailableException, ServletException, IOException {
            Request request = servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
            if (this._filterHolder == null) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder == null) {
                    if (ServletHandler.this.getHandler() == null) {
                        ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                        return;
                    } else {
                        ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), request, httpServletRequest, (HttpServletResponse) servletResponse);
                        return;
                    }
                }
                if (ServletHandler.LOG.isDebugEnabled()) {
                    ServletHandler.LOG.debug("call servlet " + this._servletHolder, new Object[0]);
                }
                this._servletHolder.handle(request, servletRequest, servletResponse);
                return;
            }
            if (ServletHandler.LOG.isDebugEnabled()) {
                ServletHandler.LOG.debug("call filter " + this._filterHolder, new Object[0]);
            }
            Filter filter = this._filterHolder.getFilter();
            if (this._filterHolder.isAsyncSupported()) {
                filter.doFilter(servletRequest, servletResponse, this._next);
                return;
            }
            if (!request.isAsyncSupported()) {
                filter.doFilter(servletRequest, servletResponse, this._next);
                return;
            }
            try {
                request.setAsyncSupported(false);
                filter.doFilter(servletRequest, servletResponse, this._next);
            } finally {
                request.setAsyncSupported(true);
            }
        }

        public String toString() {
            if (this._filterHolder == null) {
                ServletHolder servletHolder = this._servletHolder;
                return servletHolder != null ? servletHolder.toString() : "null";
            }
            return this._filterHolder + "->" + this._next.toString();
        }
    }

    public class Chain implements FilterChain {
        final Request _baseRequest;
        final Object _chain;
        int _filter = 0;
        final ServletHolder _servletHolder;

        public Chain(Request request, Object obj, ServletHolder servletHolder) {
            this._baseRequest = request;
            this._chain = obj;
            this._servletHolder = servletHolder;
        }

        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws UnavailableException, ServletException, IOException {
            if (ServletHandler.LOG.isDebugEnabled()) {
                ServletHandler.LOG.debug("doFilter " + this._filter, new Object[0]);
            }
            if (this._filter >= LazyList.size(this._chain)) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                if (this._servletHolder == null) {
                    if (ServletHandler.this.getHandler() == null) {
                        ServletHandler.this.notFound(httpServletRequest, (HttpServletResponse) servletResponse);
                        return;
                    } else {
                        ServletHandler.this.nextHandle(URIUtil.addPaths(httpServletRequest.getServletPath(), httpServletRequest.getPathInfo()), servletRequest instanceof Request ? (Request) servletRequest : AbstractHttpConnection.getCurrentConnection().getRequest(), httpServletRequest, (HttpServletResponse) servletResponse);
                        return;
                    }
                }
                if (ServletHandler.LOG.isDebugEnabled()) {
                    ServletHandler.LOG.debug("call servlet " + this._servletHolder, new Object[0]);
                }
                this._servletHolder.handle(this._baseRequest, servletRequest, servletResponse);
                return;
            }
            Object obj = this._chain;
            int i2 = this._filter;
            this._filter = i2 + 1;
            FilterHolder filterHolder = (FilterHolder) LazyList.get(obj, i2);
            if (ServletHandler.LOG.isDebugEnabled()) {
                ServletHandler.LOG.debug("call filter " + filterHolder, new Object[0]);
            }
            Filter filter = filterHolder.getFilter();
            if (filterHolder.isAsyncSupported() || !this._baseRequest.isAsyncSupported()) {
                filter.doFilter(servletRequest, servletResponse, this);
                return;
            }
            try {
                this._baseRequest.setAsyncSupported(false);
                filter.doFilter(servletRequest, servletResponse, this);
            } finally {
                this._baseRequest.setAsyncSupported(true);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < LazyList.size(this._chain); i2++) {
                sb.append(LazyList.get(this._chain, i2).toString());
                sb.append("->");
            }
            sb.append(this._servletHolder);
            return sb.toString();
        }
    }

    private FilterChain getFilterChain(Request request, String str, ServletHolder servletHolder) {
        Object objAdd;
        MultiMap<String> multiMap;
        ConcurrentMap<String, FilterChain>[] concurrentMapArr;
        FilterChain filterChain;
        String name = str == null ? servletHolder.getName() : str;
        int iDispatch = FilterMapping.dispatch(request.getDispatcherType());
        if (this._filterChainsCached && (concurrentMapArr = this._chainCache) != null && (filterChain = concurrentMapArr[iDispatch].get(name)) != null) {
            return filterChain;
        }
        if (str == null || this._filterPathMappings == null) {
            objAdd = null;
        } else {
            objAdd = null;
            for (int i2 = 0; i2 < this._filterPathMappings.size(); i2++) {
                FilterMapping filterMapping = this._filterPathMappings.get(i2);
                if (filterMapping.appliesTo(str, iDispatch)) {
                    objAdd = LazyList.add(objAdd, filterMapping.getFilterHolder());
                }
            }
        }
        if (servletHolder != null && (multiMap = this._filterNameMappings) != null && multiMap.size() > 0 && this._filterNameMappings.size() > 0) {
            Object obj = this._filterNameMappings.get(servletHolder.getName());
            for (int i3 = 0; i3 < LazyList.size(obj); i3++) {
                FilterMapping filterMapping2 = (FilterMapping) LazyList.get(obj, i3);
                if (filterMapping2.appliesTo(iDispatch)) {
                    objAdd = LazyList.add(objAdd, filterMapping2.getFilterHolder());
                }
            }
            Object obj2 = this._filterNameMappings.get("*");
            for (int i4 = 0; i4 < LazyList.size(obj2); i4++) {
                FilterMapping filterMapping3 = (FilterMapping) LazyList.get(obj2, i4);
                if (filterMapping3.appliesTo(iDispatch)) {
                    objAdd = LazyList.add(objAdd, filterMapping3.getFilterHolder());
                }
            }
        }
        if (objAdd == null) {
            return null;
        }
        if (!this._filterChainsCached) {
            if (LazyList.size(objAdd) > 0) {
                return new Chain(request, objAdd, servletHolder);
            }
            return null;
        }
        CachedChain cachedChain = LazyList.size(objAdd) > 0 ? new CachedChain(objAdd, servletHolder) : null;
        ConcurrentMap<String, FilterChain> concurrentMap = this._chainCache[iDispatch];
        Queue<String> queue = this._chainLRU[iDispatch];
        while (true) {
            if (this._maxFilterChainsCacheSize <= 0 || concurrentMap.size() < this._maxFilterChainsCacheSize) {
                break;
            }
            String strPoll = queue.poll();
            if (strPoll == null) {
                concurrentMap.clear();
                break;
            }
            concurrentMap.remove(strPoll);
        }
        concurrentMap.put(name, cachedChain);
        queue.add(name);
        return cachedChain;
    }

    private void invalidateChainsCache() {
        Queue<String> queue = this._chainLRU[1];
        if (queue != null) {
            queue.clear();
            this._chainLRU[2].clear();
            this._chainLRU[4].clear();
            this._chainLRU[8].clear();
            this._chainLRU[16].clear();
            this._chainCache[1].clear();
            this._chainCache[2].clear();
            this._chainCache[4].clear();
            this._chainCache[8].clear();
            this._chainCache[16].clear();
        }
    }

    public FilterHolder addFilter(String str, String str2, EnumSet<DispatcherType> enumSet) {
        return addFilterWithMapping(str, str2, enumSet);
    }

    public void addFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            Holder.Source source = filterMapping.getFilterHolder() == null ? null : filterMapping.getFilterHolder().getSource();
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, false));
                if (source == null || source != Holder.Source.JAVAX_API) {
                    return;
                }
                this._matchAfterIndex = 0;
                return;
            }
            if (source != null && Holder.Source.JAVAX_API == source) {
                setFilterMappings(insertFilterMapping(filterMapping, filterMappings.length - 1, false));
                if (this._matchAfterIndex < 0) {
                    this._matchAfterIndex = getFilterMappings().length - 1;
                    return;
                }
                return;
            }
            int i2 = this._matchAfterIndex;
            if (i2 < 0) {
                setFilterMappings(insertFilterMapping(filterMapping, filterMappings.length - 1, false));
                return;
            }
            FilterMapping[] filterMappingArrInsertFilterMapping = insertFilterMapping(filterMapping, i2, true);
            this._matchAfterIndex++;
            setFilterMappings(filterMappingArrInsertFilterMapping);
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder filterHolderNewFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        filterHolderNewFilterHolder.setHeldClass(cls);
        addFilterWithMapping(filterHolderNewFilterHolder, str, enumSet);
        return filterHolderNewFilterHolder;
    }

    public void addServlet(ServletHolder servletHolder) {
        setServlets((ServletHolder[]) LazyList.addToArray(getServlets(), servletHolder, ServletHolder.class));
    }

    public void addServletMapping(ServletMapping servletMapping) {
        setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
    }

    public ServletHolder addServletWithMapping(String str, String str2) {
        ServletHolder servletHolderNewServletHolder = newServletHolder(Holder.Source.EMBEDDED);
        servletHolderNewServletHolder.setName(str + "-" + LazyList.size(this._servlets));
        servletHolderNewServletHolder.setClassName(str);
        addServletWithMapping(servletHolderNewServletHolder, str2);
        return servletHolderNewServletHolder;
    }

    public void destroyFilter(Filter filter) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyFilter(filter);
        }
    }

    public void destroyServlet(Servlet servlet) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        if (servletContextHandler != null) {
            servletContextHandler.destroyServlet(servlet);
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        FilterMapping[] filterMappingArr;
        FilterMapping[] filterMappingArr2;
        DispatcherType dispatcherType = request.getDispatcherType();
        ServletHolder servletHolder = (ServletHolder) request.getUserIdentityScope();
        FilterChain filterChain = null;
        if (str.startsWith("/")) {
            if (servletHolder != null && (filterMappingArr2 = this._filterMappings) != null && filterMappingArr2.length > 0) {
                filterChain = getFilterChain(request, str, servletHolder);
            }
        } else if (servletHolder != null && (filterMappingArr = this._filterMappings) != null && filterMappingArr.length > 0) {
            filterChain = getFilterChain(request, null, servletHolder);
        }
        LOG.debug("chain={}", filterChain);
        try {
            try {
                try {
                    if (servletHolder != null) {
                        HttpServletRequest request2 = httpServletRequest instanceof ServletRequestHttpWrapper ? ((ServletRequestHttpWrapper) httpServletRequest).getRequest() : httpServletRequest;
                        HttpServletResponse response = httpServletResponse instanceof ServletResponseHttpWrapper ? ((ServletResponseHttpWrapper) httpServletResponse).getResponse() : httpServletResponse;
                        if (filterChain != null) {
                            filterChain.doFilter(request2, response);
                        } else {
                            servletHolder.handle(request, request2, response);
                        }
                    } else if (getHandler() == null) {
                        notFound(httpServletRequest, httpServletResponse);
                    } else {
                        nextHandle(str, request, httpServletRequest, httpServletResponse);
                    }
                } catch (Error e2) {
                    if (!DispatcherType.REQUEST.equals(dispatcherType) && !DispatcherType.ASYNC.equals(dispatcherType)) {
                        throw e2;
                    }
                    Logger logger = LOG;
                    logger.warn("Error for " + httpServletRequest.getRequestURI(), e2);
                    if (logger.isDebugEnabled()) {
                        logger.debug(httpServletRequest.toString(), new Object[0]);
                    }
                    if (httpServletResponse.isCommitted()) {
                        logger.debug("Response already committed for handling ", e2);
                    } else {
                        httpServletRequest.setAttribute("javax.servlet.error.exception_type", e2.getClass());
                        httpServletRequest.setAttribute("javax.servlet.error.exception", e2);
                        httpServletResponse.sendError(500);
                    }
                    if (servletHolder == null) {
                    }
                } catch (EofException e3) {
                    throw e3;
                } catch (Exception e4) {
                    e = e4;
                    if (!DispatcherType.REQUEST.equals(dispatcherType) && !DispatcherType.ASYNC.equals(dispatcherType)) {
                        if (e instanceof IOException) {
                            throw ((IOException) e);
                        }
                        if (e instanceof RuntimeException) {
                            throw ((RuntimeException) e);
                        }
                        if (e instanceof ServletException) {
                            throw e;
                        }
                    }
                    if (e instanceof UnavailableException) {
                        LOG.debug(e);
                    } else if (e instanceof ServletException) {
                        LOG.warn(e);
                        ServletException rootCause = e.getRootCause();
                        if (rootCause != null) {
                            e = rootCause;
                        }
                    }
                    if (e instanceof HttpException) {
                        throw ((HttpException) e);
                    }
                    if (e instanceof RuntimeIOException) {
                        throw ((RuntimeIOException) e);
                    }
                    if (e instanceof EofException) {
                        throw ((EofException) e);
                    }
                    Logger logger2 = LOG;
                    if (logger2.isDebugEnabled()) {
                        logger2.warn(httpServletRequest.getRequestURI(), (Throwable) e);
                        logger2.debug(httpServletRequest.toString(), new Object[0]);
                    } else if ((e instanceof IOException) || (e instanceof UnavailableException)) {
                        logger2.debug(httpServletRequest.getRequestURI(), (Throwable) e);
                    } else {
                        logger2.warn(httpServletRequest.getRequestURI(), (Throwable) e);
                    }
                    if (httpServletResponse.isCommitted()) {
                        logger2.debug("Response already committed for handling " + e, new Object[0]);
                    } else {
                        httpServletRequest.setAttribute("javax.servlet.error.exception_type", e.getClass());
                        httpServletRequest.setAttribute("javax.servlet.error.exception", e);
                        if (!(e instanceof UnavailableException)) {
                            httpServletResponse.sendError(500);
                        } else if (((UnavailableException) e).isPermanent()) {
                            httpServletResponse.sendError(404);
                        } else {
                            httpServletResponse.sendError(503);
                        }
                    }
                    if (servletHolder == null) {
                    }
                }
            } catch (ContinuationThrowable e5) {
                throw e5;
            } catch (RuntimeIOException e6) {
                throw e6;
            }
        } finally {
            if (servletHolder != null) {
                request.setHandled(true);
            }
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServletHolder servletHolder;
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        DispatcherType dispatcherType = request.getDispatcherType();
        if (str.startsWith("/")) {
            PathMap.Entry holderEntry = getHolderEntry(str);
            if (holderEntry != null) {
                servletHolder = (ServletHolder) holderEntry.getValue();
                String str2 = (String) holderEntry.getKey();
                String mapped = holderEntry.getMapped() != null ? holderEntry.getMapped() : PathMap.pathMatch(str2, str);
                String strPathInfo = PathMap.pathInfo(str2, str);
                if (DispatcherType.INCLUDE.equals(dispatcherType)) {
                    request.setAttribute("javax.servlet.include.servlet_path", mapped);
                    request.setAttribute("javax.servlet.include.path_info", strPathInfo);
                } else {
                    request.setServletPath(mapped);
                    request.setPathInfo(strPathInfo);
                }
            } else {
                servletHolder = null;
            }
        } else {
            servletHolder = this._servletNameMap.get(str);
        }
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("servlet {}|{}|{} -> {}", request.getContextPath(), request.getServletPath(), request.getPathInfo(), servletHolder);
        }
        try {
            UserIdentity.Scope userIdentityScope = request.getUserIdentityScope();
            request.setUserIdentityScope(servletHolder);
            if (never()) {
                nextScope(str, request, httpServletRequest, httpServletResponse);
            } else {
                ScopedHandler scopedHandler = this._nextScope;
                if (scopedHandler != null) {
                    scopedHandler.doScope(str, request, httpServletRequest, httpServletResponse);
                } else {
                    ScopedHandler scopedHandler2 = this._outerScope;
                    if (scopedHandler2 != null) {
                        scopedHandler2.doHandle(str, request, httpServletRequest, httpServletResponse);
                    } else {
                        doHandle(str, request, httpServletRequest, httpServletResponse);
                    }
                }
            }
            if (userIdentityScope != null) {
                request.setUserIdentityScope(userIdentityScope);
            }
            if (DispatcherType.INCLUDE.equals(dispatcherType)) {
                return;
            }
            request.setServletPath(servletPath);
            request.setPathInfo(pathInfo);
        } catch (Throwable th) {
            if (0 != 0) {
                request.setUserIdentityScope(null);
            }
            if (!DispatcherType.INCLUDE.equals(dispatcherType)) {
                request.setServletPath(servletPath);
                request.setPathInfo(pathInfo);
            }
            throw th;
        }
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public synchronized void doStart() throws Exception {
        SecurityHandler securityHandler;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        this._servletContext = currentContext;
        ServletContextHandler servletContextHandler = (ServletContextHandler) (currentContext == null ? null : currentContext.getContextHandler());
        this._contextHandler = servletContextHandler;
        if (servletContextHandler != null && (securityHandler = (SecurityHandler) servletContextHandler.getChildHandlerByClass(SecurityHandler.class)) != null) {
            this._identityService = securityHandler.getIdentityService();
        }
        updateNameMappings();
        updateMappings();
        if (this._filterChainsCached) {
            this._chainCache[1] = new ConcurrentHashMap();
            this._chainCache[2] = new ConcurrentHashMap();
            this._chainCache[4] = new ConcurrentHashMap();
            this._chainCache[8] = new ConcurrentHashMap();
            this._chainCache[16] = new ConcurrentHashMap();
            this._chainLRU[1] = new ConcurrentLinkedQueue();
            this._chainLRU[2] = new ConcurrentLinkedQueue();
            this._chainLRU[4] = new ConcurrentLinkedQueue();
            this._chainLRU[8] = new ConcurrentLinkedQueue();
            this._chainLRU[16] = new ConcurrentLinkedQueue();
        }
        super.doStart();
        ServletContextHandler servletContextHandler2 = this._contextHandler;
        if (servletContextHandler2 == null || !(servletContextHandler2 instanceof ServletContextHandler)) {
            initialize();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00a3 A[Catch: all -> 0x011d, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0013, B:8:0x0018, B:12:0x0028, B:14:0x0034, B:15:0x0045, B:17:0x004b, B:19:0x0063, B:20:0x0067, B:11:0x0021, B:22:0x0070, B:24:0x0087, B:27:0x008b, B:29:0x0090, B:31:0x00a3, B:34:0x00a8, B:38:0x00b8, B:40:0x00c4, B:41:0x00d5, B:43:0x00db, B:45:0x00f3, B:46:0x00f7, B:37:0x00b1, B:48:0x0100), top: B:58:0x0001, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0100 A[Catch: all -> 0x011d, EDGE_INSN: B:67:0x0100->B:48:0x0100 BREAK  A[LOOP:2: B:32:0x00a4->B:47:0x00fe], TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0013, B:8:0x0018, B:12:0x0028, B:14:0x0034, B:15:0x0045, B:17:0x004b, B:19:0x0063, B:20:0x0067, B:11:0x0021, B:22:0x0070, B:24:0x0087, B:27:0x008b, B:29:0x0090, B:31:0x00a3, B:34:0x00a8, B:38:0x00b8, B:40:0x00c4, B:41:0x00d5, B:43:0x00db, B:45:0x00f3, B:46:0x00f7, B:37:0x00b1, B:48:0x0100), top: B:58:0x0001, inners: #0, #1 }] */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void doStop() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHandler.doStop():void");
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        super.dumpThis(appendable);
        AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(getHandlers()), getBeans(), TypeUtil.asList(getFilterMappings()), TypeUtil.asList(getFilters()), TypeUtil.asList(getServletMappings()), TypeUtil.asList(getServlets()));
    }

    public Object getContextLog() {
        return null;
    }

    public FilterHolder getFilter(String str) {
        return this._filterNameMap.get(str);
    }

    public FilterMapping[] getFilterMappings() {
        return this._filterMappings;
    }

    public FilterHolder[] getFilters() {
        return this._filters;
    }

    public PathMap.Entry getHolderEntry(String str) {
        PathMap pathMap = this._servletPathMap;
        if (pathMap == null) {
            return null;
        }
        return pathMap.getMatch(str);
    }

    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public int getMaxFilterChainsCacheSize() {
        return this._maxFilterChainsCacheSize;
    }

    public ServletHolder getServlet(String str) {
        return this._servletNameMap.get(str);
    }

    public ServletContext getServletContext() {
        return this._servletContext;
    }

    public ServletMapping getServletMapping(String str) {
        ServletMapping[] servletMappingArr = this._servletMappings;
        ServletMapping servletMapping = null;
        if (servletMappingArr != null) {
            for (ServletMapping servletMapping2 : servletMappingArr) {
                String[] pathSpecs = servletMapping2.getPathSpecs();
                if (pathSpecs != null) {
                    for (String str2 : pathSpecs) {
                        if (str.equals(str2)) {
                            servletMapping = servletMapping2;
                        }
                    }
                }
            }
        }
        return servletMapping;
    }

    public ServletMapping[] getServletMappings() {
        return this._servletMappings;
    }

    public ServletHolder[] getServlets() {
        return this._servlets;
    }

    public void initialize() throws Exception {
        MultiException multiException = new MultiException();
        if (this._filters != null) {
            int i2 = 0;
            while (true) {
                FilterHolder[] filterHolderArr = this._filters;
                if (i2 >= filterHolderArr.length) {
                    break;
                }
                filterHolderArr[i2].start();
                i2++;
            }
        }
        ServletHolder[] servletHolderArr = this._servlets;
        if (servletHolderArr != null) {
            ServletHolder[] servletHolderArr2 = (ServletHolder[]) servletHolderArr.clone();
            Arrays.sort(servletHolderArr2);
            for (int i3 = 0; i3 < servletHolderArr2.length; i3++) {
                try {
                    if (servletHolderArr2[i3].getClassName() != null || servletHolderArr2[i3].getForcedPath() == null) {
                        servletHolderArr2[i3].start();
                    } else {
                        ServletHolder servletHolder = (ServletHolder) this._servletPathMap.match(servletHolderArr2[i3].getForcedPath());
                        if (servletHolder != null && servletHolder.getClassName() != null) {
                            servletHolderArr2[i3].setClassName(servletHolder.getClassName());
                            servletHolderArr2[i3].start();
                        }
                        multiException.add(new IllegalStateException("No forced path servlet for " + servletHolderArr2[i3].getForcedPath()));
                    }
                } catch (Throwable th) {
                    LOG.debug(Log.EXCEPTION, th);
                    multiException.add(th);
                }
            }
            multiException.ifExceptionThrow();
        }
    }

    public FilterMapping[] insertFilterMapping(FilterMapping filterMapping, int i2, boolean z2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("FilterMapping insertion pos < 0");
        }
        FilterMapping[] filterMappings = getFilterMappings();
        if (filterMappings == null || filterMappings.length == 0) {
            return new FilterMapping[]{filterMapping};
        }
        FilterMapping[] filterMappingArr = new FilterMapping[filterMappings.length + 1];
        if (z2) {
            System.arraycopy(filterMappings, 0, filterMappingArr, 0, i2);
            filterMappingArr[i2] = filterMapping;
            System.arraycopy(filterMappings, i2, filterMappingArr, i2 + 1, filterMappings.length - i2);
        } else {
            int i3 = i2 + 1;
            System.arraycopy(filterMappings, 0, filterMappingArr, 0, i3);
            filterMappingArr[i3] = filterMapping;
            if (filterMappings.length > i3) {
                System.arraycopy(filterMappings, i3, filterMappingArr, i2 + 2, filterMappings.length - i3);
            }
        }
        return filterMappingArr;
    }

    public boolean isAvailable() {
        if (!isStarted()) {
            return false;
        }
        for (ServletHolder servletHolder : getServlets()) {
            if (servletHolder != null && !servletHolder.isAvailable()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFilterChainsCached() {
        return this._filterChainsCached;
    }

    public boolean isStartWithUnavailable() {
        return this._startWithUnavailable;
    }

    public FilterHolder newFilterHolder(Holder.Source source) {
        return new FilterHolder(source);
    }

    public ServletHolder newServletHolder(Holder.Source source) {
        return new ServletHolder(source);
    }

    public void notFound(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("Not Found " + httpServletRequest.getRequestURI(), new Object[0]);
        }
    }

    public void prependFilterMapping(FilterMapping filterMapping) {
        if (filterMapping != null) {
            Holder.Source source = filterMapping.getFilterHolder().getSource();
            FilterMapping[] filterMappings = getFilterMappings();
            if (filterMappings == null || filterMappings.length == 0) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, false));
                if (source == null || Holder.Source.JAVAX_API != source) {
                    return;
                }
                this._matchBeforeIndex = 0;
                return;
            }
            if (source == null || Holder.Source.JAVAX_API != source) {
                setFilterMappings(insertFilterMapping(filterMapping, 0, true));
            } else {
                int i2 = this._matchBeforeIndex;
                if (i2 < 0) {
                    this._matchBeforeIndex = 0;
                    setFilterMappings(insertFilterMapping(filterMapping, 0, true));
                } else {
                    FilterMapping[] filterMappingArrInsertFilterMapping = insertFilterMapping(filterMapping, i2, false);
                    this._matchBeforeIndex++;
                    setFilterMappings(filterMappingArrInsertFilterMapping);
                }
            }
            int i3 = this._matchAfterIndex;
            if (i3 >= 0) {
                this._matchAfterIndex = i3 + 1;
            }
        }
    }

    public void setFilterChainsCached(boolean z2) {
        this._filterChainsCached = z2;
    }

    public void setFilterMappings(FilterMapping[] filterMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) filterMappingArr, "filterMapping", true);
        }
        this._filterMappings = filterMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public synchronized void setFilters(FilterHolder[] filterHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) filterHolderArr, "filter", true);
        }
        this._filters = filterHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    public void setMaxFilterChainsCacheSize(int i2) {
        this._maxFilterChainsCacheSize = i2;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        Server server2 = getServer();
        if (server2 != null && server2 != server) {
            getServer().getContainer().update((Object) this, (Object[]) this._filters, (Object[]) null, "filter", true);
            getServer().getContainer().update((Object) this, (Object[]) this._filterMappings, (Object[]) null, "filterMapping", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) null, "servlet", true);
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) null, "servletMapping", true);
        }
        super.setServer(server);
        if (server == null || server2 == server) {
            return;
        }
        server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filters, "filter", true);
        server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._filterMappings, "filterMapping", true);
        server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servlets, "servlet", true);
        server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._servletMappings, "servletMapping", true);
    }

    public void setServletMappings(ServletMapping[] servletMappingArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servletMappings, (Object[]) servletMappingArr, "servletMapping", true);
        }
        this._servletMappings = servletMappingArr;
        updateMappings();
        invalidateChainsCache();
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic dynamic, ServletSecurityElement servletSecurityElement) {
        ServletContextHandler servletContextHandler = this._contextHandler;
        return servletContextHandler != null ? servletContextHandler.setServletSecurity(dynamic, servletSecurityElement) : Collections.emptySet();
    }

    public synchronized void setServlets(ServletHolder[] servletHolderArr) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object[]) this._servlets, (Object[]) servletHolderArr, "servlet", true);
        }
        this._servlets = servletHolderArr;
        updateNameMappings();
        invalidateChainsCache();
    }

    public void setStartWithUnavailable(boolean z2) {
        this._startWithUnavailable = z2;
    }

    public synchronized void updateMappings() {
        if (this._filterMappings != null) {
            this._filterPathMappings = new ArrayList();
            this._filterNameMappings = new MultiMap<>();
            int i2 = 0;
            while (true) {
                FilterMapping[] filterMappingArr = this._filterMappings;
                if (i2 >= filterMappingArr.length) {
                    break;
                }
                FilterHolder filterHolder = this._filterNameMap.get(filterMappingArr[i2].getFilterName());
                if (filterHolder == null) {
                    throw new IllegalStateException("No filter named " + this._filterMappings[i2].getFilterName());
                }
                this._filterMappings[i2].setFilterHolder(filterHolder);
                if (this._filterMappings[i2].getPathSpecs() != null) {
                    this._filterPathMappings.add(this._filterMappings[i2]);
                }
                if (this._filterMappings[i2].getServletNames() != null) {
                    for (String str : this._filterMappings[i2].getServletNames()) {
                        if (str != null) {
                            this._filterNameMappings.add(str, this._filterMappings[i2]);
                        }
                    }
                }
                i2++;
            }
        } else {
            this._filterPathMappings = null;
            this._filterNameMappings = null;
        }
        if (this._servletMappings == null || this._servletNameMap == null) {
            this._servletPathMap = null;
        } else {
            PathMap pathMap = new PathMap();
            int i3 = 0;
            while (true) {
                ServletMapping[] servletMappingArr = this._servletMappings;
                if (i3 >= servletMappingArr.length) {
                    this._servletPathMap = pathMap;
                    break;
                }
                ServletHolder servletHolder = this._servletNameMap.get(servletMappingArr[i3].getServletName());
                if (servletHolder == null) {
                    throw new IllegalStateException("No such servlet: " + this._servletMappings[i3].getServletName());
                }
                if (servletHolder.isEnabled() && this._servletMappings[i3].getPathSpecs() != null) {
                    for (String str2 : this._servletMappings[i3].getPathSpecs()) {
                        if (str2 != null) {
                            pathMap.put(str2, servletHolder);
                        }
                    }
                }
                i3++;
            }
        }
        ConcurrentMap<String, FilterChain>[] concurrentMapArr = this._chainCache;
        if (concurrentMapArr != null) {
            int length = concurrentMapArr.length;
            while (true) {
                int i4 = length - 1;
                if (length <= 0) {
                    break;
                }
                ConcurrentMap<String, FilterChain> concurrentMap = this._chainCache[i4];
                if (concurrentMap != null) {
                    concurrentMap.clear();
                }
                length = i4;
            }
        }
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("filterNameMap=" + this._filterNameMap, new Object[0]);
            logger.debug("pathFilters=" + this._filterPathMappings, new Object[0]);
            logger.debug("servletFilterMap=" + this._filterNameMappings, new Object[0]);
            logger.debug("servletPathMap=" + this._servletPathMap, new Object[0]);
            logger.debug("servletNameMap=" + this._servletNameMap, new Object[0]);
        }
        try {
            ServletContextHandler servletContextHandler = this._contextHandler;
            if ((servletContextHandler != null && servletContextHandler.isStarted()) || (this._contextHandler == null && isStarted())) {
                initialize();
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public synchronized void updateNameMappings() {
        this._filterNameMap.clear();
        int i2 = 0;
        if (this._filters != null) {
            int i3 = 0;
            while (true) {
                FilterHolder[] filterHolderArr = this._filters;
                if (i3 >= filterHolderArr.length) {
                    break;
                }
                this._filterNameMap.put(filterHolderArr[i3].getName(), this._filters[i3]);
                this._filters[i3].setServletHandler(this);
                i3++;
            }
        }
        this._servletNameMap.clear();
        if (this._servlets != null) {
            while (true) {
                ServletHolder[] servletHolderArr = this._servlets;
                if (i2 >= servletHolderArr.length) {
                    break;
                }
                this._servletNameMap.put(servletHolderArr[i2].getName(), this._servlets[i2]);
                this._servlets[i2].setServletHandler(this);
                i2++;
            }
        }
    }

    public void addFilter(FilterHolder filterHolder, FilterMapping filterMapping) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
        if (filterMapping != null) {
            addFilterMapping(filterMapping);
        }
    }

    public void addFilter(FilterHolder filterHolder) {
        if (filterHolder != null) {
            setFilters((FilterHolder[]) LazyList.addToArray(getFilters(), filterHolder, FilterHolder.class));
        }
    }

    public FilterHolder addFilterWithMapping(String str, String str2, EnumSet<DispatcherType> enumSet) {
        FilterHolder filterHolderNewFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        filterHolderNewFilterHolder.setName(str + "-" + this._filters.length);
        filterHolderNewFilterHolder.setClassName(str);
        addFilterWithMapping(filterHolderNewFilterHolder, str2, enumSet);
        return filterHolderNewFilterHolder;
    }

    public ServletHolder addServletWithMapping(Class<? extends Servlet> cls, String str) {
        ServletHolder servletHolderNewServletHolder = newServletHolder(Holder.Source.EMBEDDED);
        servletHolderNewServletHolder.setHeldClass(cls);
        addServletWithMapping(servletHolderNewServletHolder, str);
        return servletHolderNewServletHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, EnumSet<DispatcherType> enumSet) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatcherTypes(enumSet);
            addFilterMapping(filterMapping);
        } catch (Error e2) {
            setFilters(filters);
            throw e2;
        } catch (RuntimeException e3) {
            setFilters(filters);
            throw e3;
        }
    }

    public void addServletWithMapping(ServletHolder servletHolder, String str) {
        ServletHolder[] servlets = getServlets();
        if (servlets != null) {
            servlets = (ServletHolder[]) servlets.clone();
        }
        try {
            setServlets((ServletHolder[]) LazyList.addToArray(servlets, servletHolder, ServletHolder.class));
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(servletHolder.getName());
            servletMapping.setPathSpec(str);
            setServletMappings((ServletMapping[]) LazyList.addToArray(getServletMappings(), servletMapping, ServletMapping.class));
        } catch (Exception e2) {
            setServlets(servlets);
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            throw new RuntimeException(e2);
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> cls, String str, int i2) {
        FilterHolder filterHolderNewFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        filterHolderNewFilterHolder.setHeldClass(cls);
        addFilterWithMapping(filterHolderNewFilterHolder, str, i2);
        return filterHolderNewFilterHolder;
    }

    public FilterHolder addFilterWithMapping(String str, String str2, int i2) {
        FilterHolder filterHolderNewFilterHolder = newFilterHolder(Holder.Source.EMBEDDED);
        filterHolderNewFilterHolder.setName(str + "-" + this._filters.length);
        filterHolderNewFilterHolder.setClassName(str);
        addFilterWithMapping(filterHolderNewFilterHolder, str2, i2);
        return filterHolderNewFilterHolder;
    }

    public void addFilterWithMapping(FilterHolder filterHolder, String str, int i2) {
        FilterHolder[] filters = getFilters();
        if (filters != null) {
            filters = (FilterHolder[]) filters.clone();
        }
        try {
            setFilters((FilterHolder[]) LazyList.addToArray(filters, filterHolder, FilterHolder.class));
            FilterMapping filterMapping = new FilterMapping();
            filterMapping.setFilterName(filterHolder.getName());
            filterMapping.setPathSpec(str);
            filterMapping.setDispatches(i2);
            addFilterMapping(filterMapping);
        } catch (Error e2) {
            setFilters(filters);
            throw e2;
        } catch (RuntimeException e3) {
            setFilters(filters);
            throw e3;
        }
    }
}
