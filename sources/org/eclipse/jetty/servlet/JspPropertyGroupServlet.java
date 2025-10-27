package org.eclipse.jetty.servlet;

import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes9.dex */
public class JspPropertyGroupServlet extends GenericServlet {
    public static final String NAME = "__org.eclipse.jetty.servlet.JspPropertyGroupServlet__";
    private static final long serialVersionUID = 3681783214726776945L;
    private final ContextHandler _contextHandler;
    private ServletHolder _dftServlet;
    private ServletHolder _jspServlet;
    private final ServletHandler _servletHandler;
    private boolean _starJspMapped;

    public JspPropertyGroupServlet(ContextHandler contextHandler, ServletHandler servletHandler) {
        this._contextHandler = contextHandler;
        this._servletHandler = servletHandler;
    }

    public void init() throws ServletException {
        String servletName;
        ServletMapping servletMapping = this._servletHandler.getServletMapping("*.jsp");
        if (servletMapping != null) {
            this._starJspMapped = true;
            for (ServletMapping servletMapping2 : this._servletHandler.getServletMappings()) {
                String[] pathSpecs = servletMapping2.getPathSpecs();
                if (pathSpecs != null) {
                    for (String str : pathSpecs) {
                        if ("*.jsp".equals(str) && !NAME.equals(servletMapping2.getServletName())) {
                            servletMapping = servletMapping2;
                        }
                    }
                }
            }
            servletName = servletMapping.getServletName();
        } else {
            servletName = "jsp";
        }
        this._jspServlet = this._servletHandler.getServlet(servletName);
        ServletMapping servletMapping3 = this._servletHandler.getServletMapping("/");
        this._dftServlet = this._servletHandler.getServlet(servletMapping3 != null ? servletMapping3.getServletName() : ServletHandler.__DEFAULT_SERVLET);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String servletPath;
        String pathInfo;
        if (!(servletRequest instanceof HttpServletRequest)) {
            throw new ServletException("Request not HttpServletRequest");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getAttribute("javax.servlet.include.request_uri") != null) {
            servletPath = (String) httpServletRequest.getAttribute("javax.servlet.include.servlet_path");
            pathInfo = (String) httpServletRequest.getAttribute("javax.servlet.include.path_info");
            if (servletPath == null) {
                servletPath = httpServletRequest.getServletPath();
                pathInfo = httpServletRequest.getPathInfo();
            }
        } else {
            servletPath = httpServletRequest.getServletPath();
            pathInfo = httpServletRequest.getPathInfo();
        }
        String strAddPaths = URIUtil.addPaths(servletPath, pathInfo);
        if (strAddPaths.endsWith("/")) {
            this._dftServlet.getServlet().service(servletRequest, servletResponse);
            return;
        }
        if (this._starJspMapped && strAddPaths.toLowerCase().endsWith(".jsp")) {
            this._jspServlet.getServlet().service(servletRequest, servletResponse);
            return;
        }
        Resource resource = this._contextHandler.getResource(strAddPaths);
        if (resource == null || !resource.isDirectory()) {
            this._jspServlet.getServlet().service(servletRequest, servletResponse);
        } else {
            this._dftServlet.getServlet().service(servletRequest, servletResponse);
        }
    }
}
