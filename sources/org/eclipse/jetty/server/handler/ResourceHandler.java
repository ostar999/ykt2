package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.WriterOutputStream;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes9.dex */
public class ResourceHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger((Class<?>) ResourceHandler.class);
    boolean _aliases;
    Resource _baseResource;
    ByteArrayBuffer _cacheControl;
    ContextHandler _context;
    Resource _defaultStylesheet;
    boolean _directory;
    boolean _etags;
    Resource _stylesheet;
    String[] _welcomeFiles = {"index.html"};
    MimeTypes _mimeTypes = new MimeTypes();

    public void doDirectory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource) throws IOException {
        if (!this._directory) {
            httpServletResponse.sendError(403);
            return;
        }
        String listHTML = resource.getListHTML(httpServletRequest.getRequestURI(), httpServletRequest.getPathInfo().lastIndexOf("/") > 0);
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.getWriter().println(listHTML);
    }

    public void doResponseHeaders(HttpServletResponse httpServletResponse, Resource resource, String str) {
        if (str != null) {
            httpServletResponse.setContentType(str);
        }
        long length = resource.length();
        if (!(httpServletResponse instanceof Response)) {
            if (length > 0) {
                httpServletResponse.setHeader("Content-Length", Long.toString(length));
            }
            ByteArrayBuffer byteArrayBuffer = this._cacheControl;
            if (byteArrayBuffer != null) {
                httpServletResponse.setHeader("Cache-Control", byteArrayBuffer.toString());
                return;
            }
            return;
        }
        HttpFields httpFields = ((Response) httpServletResponse).getHttpFields();
        if (length > 0) {
            httpFields.putLongField(HttpHeaders.CONTENT_LENGTH_BUFFER, length);
        }
        ByteArrayBuffer byteArrayBuffer2 = this._cacheControl;
        if (byteArrayBuffer2 != null) {
            httpFields.put(HttpHeaders.CACHE_CONTROL_BUFFER, byteArrayBuffer2);
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        ContextHandler contextHandler = currentContext == null ? null : currentContext.getContextHandler();
        this._context = contextHandler;
        if (contextHandler != null) {
            this._aliases = contextHandler.isAliases();
        }
        if (!this._aliases && !FileResource.getCheckAliases()) {
            throw new IllegalStateException("Alias checking disabled");
        }
        super.doStart();
    }

    public Resource getBaseResource() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource;
    }

    public String getCacheControl() {
        return this._cacheControl.toString();
    }

    public MimeTypes getMimeTypes() {
        return this._mimeTypes;
    }

    public Resource getResource(String str) throws MalformedURLException {
        ContextHandler contextHandler;
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        }
        Resource baseResource = this._baseResource;
        if (baseResource == null && ((contextHandler = this._context) == null || (baseResource = contextHandler.getBaseResource()) == null)) {
            return null;
        }
        try {
            return baseResource.addPath(URIUtil.canonicalPath(str));
        } catch (Exception e2) {
            LOG.ignore(e2);
            return null;
        }
    }

    public String getResourceBase() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource.toString();
    }

    public Resource getStylesheet() {
        Resource resource = this._stylesheet;
        if (resource != null) {
            return resource;
        }
        if (this._defaultStylesheet == null) {
            try {
                this._defaultStylesheet = Resource.newResource(getClass().getResource("/jetty-dir.css"));
            } catch (IOException e2) {
                Logger logger = LOG;
                logger.warn(e2.toString(), new Object[0]);
                logger.debug(e2);
            }
        }
        return this._defaultStylesheet;
    }

    public Resource getWelcome(Resource resource) throws IOException {
        int i2 = 0;
        while (true) {
            String[] strArr = this._welcomeFiles;
            if (i2 >= strArr.length) {
                return null;
            }
            Resource resourceAddPath = resource.addPath(strArr[i2]);
            if (resourceAddPath.exists() && !resourceAddPath.isDirectory()) {
                return resourceAddPath;
            }
            i2++;
        }
    }

    public String[] getWelcomeFiles() {
        return this._welcomeFiles;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        boolean z2;
        Resource resource;
        String weakETag;
        ServletOutputStream writerOutputStream;
        if (request.isHandled()) {
            return;
        }
        if ("GET".equals(httpServletRequest.getMethod())) {
            z2 = false;
        } else {
            if (!"HEAD".equals(httpServletRequest.getMethod())) {
                super.handle(str, request, httpServletRequest, httpServletResponse);
                return;
            }
            z2 = true;
        }
        Resource resource2 = getResource(httpServletRequest);
        if (resource2 == null || !resource2.exists()) {
            if (!str.endsWith("/jetty-dir.css")) {
                super.handle(str, request, httpServletRequest, httpServletResponse);
                return;
            }
            resource2 = getStylesheet();
            if (resource2 == null) {
                return;
            } else {
                httpServletResponse.setContentType(com.psychiatrygarden.utils.MimeTypes.TEXT_CSS);
            }
        }
        if (!this._aliases && resource2.getAlias() != null) {
            LOG.info(resource2 + " aliased to " + resource2.getAlias(), new Object[0]);
            return;
        }
        request.setHandled(true);
        if (!resource2.isDirectory()) {
            resource = resource2;
        } else {
            if (!httpServletRequest.getPathInfo().endsWith("/")) {
                httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(URIUtil.addPaths(httpServletRequest.getRequestURI(), "/")));
                return;
            }
            Resource welcome = getWelcome(resource2);
            if (welcome == null || !welcome.exists()) {
                doDirectory(httpServletRequest, httpServletResponse, resource2);
                request.setHandled(true);
                return;
            }
            resource = welcome;
        }
        long jLastModified = resource.lastModified();
        if (this._etags) {
            String header = httpServletRequest.getHeader("If-None-Match");
            weakETag = resource.getWeakETag();
            if (header != null && header.equals(weakETag)) {
                httpServletResponse.setStatus(304);
                request.getResponse().getHttpFields().put(HttpHeaders.ETAG_BUFFER, weakETag);
                return;
            }
        } else {
            weakETag = null;
        }
        if (jLastModified > 0) {
            long dateHeader = httpServletRequest.getDateHeader("If-Modified-Since");
            if (dateHeader > 0 && jLastModified / 1000 <= dateHeader / 1000) {
                httpServletResponse.setStatus(304);
                return;
            }
        }
        Buffer mimeByExtension = this._mimeTypes.getMimeByExtension(resource.toString());
        if (mimeByExtension == null) {
            mimeByExtension = this._mimeTypes.getMimeByExtension(httpServletRequest.getPathInfo());
        }
        doResponseHeaders(httpServletResponse, resource, mimeByExtension != null ? mimeByExtension.toString() : null);
        httpServletResponse.setDateHeader("Last-Modified", jLastModified);
        if (this._etags) {
            request.getResponse().getHttpFields().put(HttpHeaders.ETAG_BUFFER, weakETag);
        }
        if (z2) {
            return;
        }
        try {
            writerOutputStream = httpServletResponse.getOutputStream();
        } catch (IllegalStateException unused) {
            writerOutputStream = new WriterOutputStream(httpServletResponse.getWriter());
        }
        ServletOutputStream servletOutputStream = writerOutputStream;
        if (servletOutputStream instanceof AbstractHttpConnection.Output) {
            ((AbstractHttpConnection.Output) servletOutputStream).sendContent(resource.getInputStream());
        } else {
            resource.writeTo(servletOutputStream, 0L, resource.length());
        }
    }

    public boolean isAliases() {
        return this._aliases;
    }

    public boolean isDirectoriesListed() {
        return this._directory;
    }

    public boolean isEtags() {
        return this._etags;
    }

    public void setAliases(boolean z2) {
        this._aliases = z2;
    }

    public void setBaseResource(Resource resource) {
        this._baseResource = resource;
    }

    public void setCacheControl(String str) {
        this._cacheControl = str == null ? null : new ByteArrayBuffer(str);
    }

    public void setDirectoriesListed(boolean z2) {
        this._directory = z2;
    }

    public void setEtags(boolean z2) {
        this._etags = z2;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public void setResourceBase(String str) {
        try {
            setBaseResource(Resource.newResource(str));
        } catch (Exception e2) {
            Logger logger = LOG;
            logger.warn(e2.toString(), new Object[0]);
            logger.debug(e2);
            throw new IllegalArgumentException(str);
        }
    }

    public void setStylesheet(String str) {
        try {
            Resource resourceNewResource = Resource.newResource(str);
            this._stylesheet = resourceNewResource;
            if (resourceNewResource.exists()) {
                return;
            }
            LOG.warn("unable to find custom stylesheet: " + str, new Object[0]);
            this._stylesheet = null;
        } catch (Exception e2) {
            Logger logger = LOG;
            logger.warn(e2.toString(), new Object[0]);
            logger.debug(e2);
            throw new IllegalArgumentException(str.toString());
        }
    }

    public void setWelcomeFiles(String[] strArr) {
        this._welcomeFiles = strArr;
    }

    public Resource getResource(HttpServletRequest httpServletRequest) throws MalformedURLException {
        String servletPath;
        String pathInfo;
        Boolean boolValueOf = Boolean.valueOf(httpServletRequest.getAttribute("javax.servlet.include.request_uri") != null);
        if (boolValueOf != null && boolValueOf.booleanValue()) {
            servletPath = (String) httpServletRequest.getAttribute("javax.servlet.include.servlet_path");
            pathInfo = (String) httpServletRequest.getAttribute("javax.servlet.include.path_info");
            if (servletPath == null && pathInfo == null) {
                servletPath = httpServletRequest.getServletPath();
                pathInfo = httpServletRequest.getPathInfo();
            }
        } else {
            servletPath = httpServletRequest.getServletPath();
            pathInfo = httpServletRequest.getPathInfo();
        }
        return getResource(URIUtil.addPaths(servletPath, pathInfo));
    }
}
