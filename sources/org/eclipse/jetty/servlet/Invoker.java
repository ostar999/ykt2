package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class Invoker extends HttpServlet {
    private static final Logger LOG = Log.getLogger((Class<?>) Invoker.class);
    private ContextHandler _contextHandler;
    private Map.Entry _invokerEntry;
    private boolean _nonContextServlets;
    private Map _parameters;
    private ServletHandler _servletHandler;
    private boolean _verbose;

    public class InvokedRequest extends HttpServletRequestWrapper {
        boolean _included;
        String _pathInfo;
        String _servletPath;

        public InvokedRequest(HttpServletRequest httpServletRequest, boolean z2, String str, String str2, String str3) {
            super(httpServletRequest);
            this._included = z2;
            this._servletPath = URIUtil.addPaths(str2, str);
            String strSubstring = str3.substring(str.length() + 1);
            this._pathInfo = strSubstring;
            if (strSubstring.length() == 0) {
                this._pathInfo = null;
            }
        }

        public Object getAttribute(String str) {
            if (this._included) {
                if (str.equals("javax.servlet.include.request_uri")) {
                    return URIUtil.addPaths(URIUtil.addPaths(getContextPath(), this._servletPath), this._pathInfo);
                }
                if (str.equals("javax.servlet.include.path_info")) {
                    return this._pathInfo;
                }
                if (str.equals("javax.servlet.include.servlet_path")) {
                    return this._servletPath;
                }
            }
            return super.getAttribute(str);
        }

        public String getPathInfo() {
            return this._included ? super.getPathInfo() : this._pathInfo;
        }

        public String getServletPath() {
            return this._included ? super.getServletPath() : this._servletPath;
        }
    }

    private ServletHolder getHolder(ServletHolder[] servletHolderArr, String str) {
        ServletHolder servletHolder = null;
        if (servletHolderArr == null) {
            return null;
        }
        for (int i2 = 0; servletHolder == null && i2 < servletHolderArr.length; i2++) {
            if (servletHolderArr[i2].getName().equals(str)) {
                servletHolder = servletHolderArr[i2];
            }
        }
        return servletHolder;
    }

    public void init() {
        ContextHandler contextHandler = ((ContextHandler.Context) getServletContext()).getContextHandler();
        this._contextHandler = contextHandler;
        Handler handler = contextHandler.getHandler();
        while (handler != null && !(handler instanceof ServletHandler) && (handler instanceof HandlerWrapper)) {
            handler = ((HandlerWrapper) handler).getHandler();
        }
        this._servletHandler = (ServletHandler) handler;
        Enumeration initParameterNames = getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String str = (String) initParameterNames.nextElement();
            String initParameter = getInitParameter(str);
            String lowerCase = initParameter.toLowerCase(Locale.ENGLISH);
            if ("nonContextServlets".equals(str)) {
                this._nonContextServlets = initParameter.length() > 0 && lowerCase.startsWith("t");
            }
            if ("verbose".equals(str)) {
                this._verbose = initParameter.length() > 0 && lowerCase.startsWith("t");
            } else {
                if (this._parameters == null) {
                    this._parameters = new HashMap();
                }
                this._parameters.put(str, initParameter);
            }
        }
    }

    public void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UnavailableException, ServletException, IOException {
        String servletPath;
        boolean z2;
        ServletHolder servletHolder;
        String str;
        ServletHolder servletHolder2;
        String str2 = (String) httpServletRequest.getAttribute("javax.servlet.include.servlet_path");
        if (str2 == null) {
            servletPath = httpServletRequest.getServletPath();
            z2 = false;
        } else {
            servletPath = str2;
            z2 = true;
        }
        String pathInfo = (String) httpServletRequest.getAttribute("javax.servlet.include.path_info");
        if (pathInfo == null) {
            pathInfo = httpServletRequest.getPathInfo();
        }
        String str3 = pathInfo;
        if (str3 == null || str3.length() <= 1) {
            httpServletResponse.sendError(404);
            return;
        }
        int i2 = str3.charAt(0) != '/' ? 0 : 1;
        int iIndexOf = str3.indexOf(47, i2);
        String strSubstring = iIndexOf < 0 ? str3.substring(i2) : str3.substring(i2, iIndexOf);
        ServletHolder holder = getHolder(this._servletHandler.getServlets(), strSubstring);
        if (holder != null) {
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Adding servlet mapping for named servlet:" + strSubstring + ":" + URIUtil.addPaths(servletPath, strSubstring) + "/*", new Object[0]);
            }
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(strSubstring);
            servletMapping.setPathSpec(URIUtil.addPaths(servletPath, strSubstring) + "/*");
            ServletHandler servletHandler = this._servletHandler;
            servletHandler.setServletMappings((ServletMapping[]) LazyList.addToArray(servletHandler.getServletMappings(), servletMapping, ServletMapping.class));
            str = strSubstring;
            servletHolder2 = holder;
        } else {
            if (strSubstring.endsWith(".class")) {
                strSubstring = strSubstring.substring(0, strSubstring.length() - 6);
            }
            if (strSubstring == null || strSubstring.length() == 0) {
                httpServletResponse.sendError(404);
                return;
            }
            synchronized (this._servletHandler) {
                this._invokerEntry = this._servletHandler.getHolderEntry(servletPath);
                String strAddPaths = URIUtil.addPaths(servletPath, strSubstring);
                PathMap.Entry holderEntry = this._servletHandler.getHolderEntry(strAddPaths);
                if (holderEntry == null || holderEntry.equals(this._invokerEntry)) {
                    Logger logger2 = LOG;
                    if (logger2.isDebugEnabled()) {
                        logger2.debug("Making new servlet=" + strSubstring + " with path=" + strAddPaths + "/*", new Object[0]);
                    }
                    ServletHolder servletHolderAddServletWithMapping = this._servletHandler.addServletWithMapping(strSubstring, strAddPaths + "/*");
                    Map<String, String> map = this._parameters;
                    if (map != null) {
                        servletHolderAddServletWithMapping.setInitParameters(map);
                    }
                    try {
                        servletHolderAddServletWithMapping.start();
                        if (!this._nonContextServlets) {
                            Servlet servlet = servletHolderAddServletWithMapping.getServlet();
                            if (this._contextHandler.getClassLoader() != servlet.getClass().getClassLoader()) {
                                try {
                                    servletHolderAddServletWithMapping.stop();
                                } catch (Exception e2) {
                                    LOG.ignore(e2);
                                }
                                LOG.warn("Dynamic servlet " + servlet + " not loaded from context " + httpServletRequest.getContextPath(), new Object[0]);
                                throw new UnavailableException("Not in context");
                            }
                        }
                        if (this._verbose && logger2.isDebugEnabled()) {
                            logger2.debug("Dynamic load '" + strSubstring + "' at " + strAddPaths, new Object[0]);
                        }
                        servletHolder = servletHolderAddServletWithMapping;
                    } catch (Exception e3) {
                        LOG.debug(e3);
                        throw new UnavailableException(e3.toString());
                    }
                } else {
                    servletHolder = (ServletHolder) holderEntry.getValue();
                }
            }
            str = strSubstring;
            servletHolder2 = servletHolder;
        }
        if (servletHolder2 != null) {
            servletHolder2.handle(httpServletRequest instanceof Request ? (Request) httpServletRequest : AbstractHttpConnection.getCurrentConnection().getRequest(), new InvokedRequest(httpServletRequest, z2, str, servletPath, str3), httpServletResponse);
            return;
        }
        LOG.info("Can't find holder for servlet: " + str, new Object[0]);
        httpServletResponse.sendError(404);
    }
}
