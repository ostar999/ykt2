package org.eclipse.jetty.servlet;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.WriterOutputStream;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.InclusiveByteRange;
import org.eclipse.jetty.server.ResourceCache;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.MultiPartOutputStream;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.resource.ResourceFactory;

/* loaded from: classes9.dex */
public class DefaultServlet extends HttpServlet implements ResourceFactory {
    private static final Logger LOG = Log.getLogger((Class<?>) DefaultServlet.class);
    private static final long serialVersionUID = 4930458713846881193L;
    private ResourceCache _cache;
    private ByteArrayBuffer _cacheControl;
    private ContextHandler _contextHandler;
    private ServletHolder _defaultHolder;
    private MimeTypes _mimeTypes;
    private String _relativeResourceBase;
    private Resource _resourceBase;
    private ServletContext _servletContext;
    private ServletHandler _servletHandler;
    private Resource _stylesheet;
    private String[] _welcomes;
    private boolean _acceptRanges = true;
    private boolean _dirAllowed = true;
    private boolean _welcomeServlets = false;
    private boolean _welcomeExactServlets = false;
    private boolean _redirectWelcome = false;
    private boolean _gzip = true;
    private boolean _pathInfoOnly = false;
    private boolean _etags = false;
    private boolean _useFileMappedBuffer = false;

    private boolean getInitBoolean(String str, boolean z2) {
        String initParameter = getInitParameter(str);
        return (initParameter == null || initParameter.length() == 0) ? z2 : initParameter.startsWith("t") || initParameter.startsWith(ExifInterface.GPS_DIRECTION_TRUE) || initParameter.startsWith("y") || initParameter.startsWith("Y") || initParameter.startsWith("1");
    }

    private int getInitInt(String str, int i2) {
        String initParameter = getInitParameter(str);
        if (initParameter == null) {
            initParameter = getInitParameter(str);
        }
        return (initParameter == null || initParameter.length() <= 0) ? i2 : Integer.parseInt(initParameter);
    }

    private String getWelcomeFile(String str) throws IOException {
        PathMap.Entry holderEntry;
        String str2 = null;
        if (this._welcomes == null) {
            return null;
        }
        int i2 = 0;
        while (true) {
            String[] strArr = this._welcomes;
            if (i2 >= strArr.length) {
                return str2;
            }
            String strAddPaths = URIUtil.addPaths(str, strArr[i2]);
            Resource resource = getResource(strAddPaths);
            if (resource != null && resource.exists()) {
                return this._welcomes[i2];
            }
            if ((this._welcomeServlets || this._welcomeExactServlets) && str2 == null && (holderEntry = this._servletHandler.getHolderEntry(strAddPaths)) != null && holderEntry.getValue() != this._defaultHolder && (this._welcomeServlets || (this._welcomeExactServlets && holderEntry.getKey().equals(strAddPaths)))) {
                str2 = strAddPaths;
            }
            i2++;
        }
    }

    private boolean hasDefinedRange(Enumeration<String> enumeration) {
        return enumeration != null && enumeration.hasMoreElements();
    }

    public void destroy() {
        ResourceCache resourceCache = this._cache;
        if (resourceCache != null) {
            resourceCache.flushCache();
        }
        super.destroy();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:0|2|(1:4)(1:5)|6|(17:14|(1:16)(1:17)|18|(0)|21|(1:23)|24|203|25|(1:54)(5:31|(1:33)(3:34|(1:36)(2:201|37)|38)|(3:198|40|(1:49)(2:44|(0)(1:48)))(0)|(1:(1:189))(1:190)|191)|(3:56|(1:58)(2:60|(1:62)(1:63))|59)|69|200|70|(3:72|(1:74)(1:75)|76)|(2:158|(1:160)(2:165|166))(2:81|(1:(4:(2:95|96)(1:97)|98|(2:(2:103|(1:105))|106)|107)(3:88|(1:92)|93))(1:(3:141|28a|154)(2:114|(2:116|(2:118|(1:123)(1:122))(2:124|(2:126|(1:128)(1:129))))(5:130|196|131|(1:135)|136))))|(2:162|204)(1:(2:164|206)(1:205)))(2:10|(1:12))|13|21|(0)|24|203|25|(9:27|54|(0)|69|200|70|(0)|(4:78|80|158|(0)(0))(0)|(0)(0))(0)|(0)(0)|191|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x02f7, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x02f8, code lost:
    
        r10 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x02fa, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02fb, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02c3 A[Catch: all -> 0x02f1, IllegalArgumentException -> 0x02f3, TryCatch #10 {IllegalArgumentException -> 0x02f3, all -> 0x02f1, blocks: (B:70:0x00f7, B:72:0x00ff, B:76:0x011f, B:78:0x012d, B:81:0x0135, B:84:0x013d, B:86:0x0145, B:88:0x014b, B:90:0x015a, B:92:0x0160, B:93:0x0174, B:95:0x0189, B:109:0x01d5, B:111:0x01db, B:114:0x01e5, B:116:0x01eb, B:118:0x01f8, B:120:0x0201, B:122:0x0207, B:123:0x022e, B:124:0x0241, B:126:0x0247, B:128:0x024d, B:129:0x0252, B:130:0x025c, B:141:0x0286, B:142:0x028a, B:157:0x02c2, B:158:0x02c3, B:160:0x02c9, B:165:0x02da, B:166:0x02f0), top: B:200:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02c9 A[Catch: all -> 0x02f1, IllegalArgumentException -> 0x02f3, TRY_LEAVE, TryCatch #10 {IllegalArgumentException -> 0x02f3, all -> 0x02f1, blocks: (B:70:0x00f7, B:72:0x00ff, B:76:0x011f, B:78:0x012d, B:81:0x0135, B:84:0x013d, B:86:0x0145, B:88:0x014b, B:90:0x015a, B:92:0x0160, B:93:0x0174, B:95:0x0189, B:109:0x01d5, B:111:0x01db, B:114:0x01e5, B:116:0x01eb, B:118:0x01f8, B:120:0x0201, B:122:0x0207, B:123:0x022e, B:124:0x0241, B:126:0x0247, B:128:0x024d, B:129:0x0252, B:130:0x025c, B:141:0x0286, B:142:0x028a, B:157:0x02c2, B:158:0x02c3, B:160:0x02c9, B:165:0x02da, B:166:0x02f0), top: B:200:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02da A[Catch: all -> 0x02f1, IllegalArgumentException -> 0x02f3, TRY_ENTER, TryCatch #10 {IllegalArgumentException -> 0x02f3, all -> 0x02f1, blocks: (B:70:0x00f7, B:72:0x00ff, B:76:0x011f, B:78:0x012d, B:81:0x0135, B:84:0x013d, B:86:0x0145, B:88:0x014b, B:90:0x015a, B:92:0x0160, B:93:0x0174, B:95:0x0189, B:109:0x01d5, B:111:0x01db, B:114:0x01e5, B:116:0x01eb, B:118:0x01f8, B:120:0x0201, B:122:0x0207, B:123:0x022e, B:124:0x0241, B:126:0x0247, B:128:0x024d, B:129:0x0252, B:130:0x025c, B:141:0x0286, B:142:0x028a, B:157:0x02c2, B:158:0x02c3, B:160:0x02c9, B:165:0x02da, B:166:0x02f0), top: B:200:0x00f7 }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0309 A[Catch: all -> 0x031e, TRY_LEAVE, TryCatch #10 {all -> 0x031e, blocks: (B:176:0x02fc, B:178:0x0309), top: B:194:0x02fc }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00da A[Catch: all -> 0x00f0, IllegalArgumentException -> 0x00f3, TryCatch #11 {IllegalArgumentException -> 0x00f3, all -> 0x00f0, blocks: (B:40:0x00a4, B:42:0x00aa, B:44:0x00b0, B:46:0x00bf, B:56:0x00da, B:58:0x00de, B:60:0x00e4, B:63:0x00eb, B:98:0x01a1, B:100:0x01a7, B:103:0x01af, B:105:0x01be, B:106:0x01c1), top: B:198:0x00a4 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ff A[Catch: all -> 0x02f1, IllegalArgumentException -> 0x02f3, TryCatch #10 {IllegalArgumentException -> 0x02f3, all -> 0x02f1, blocks: (B:70:0x00f7, B:72:0x00ff, B:76:0x011f, B:78:0x012d, B:81:0x0135, B:84:0x013d, B:86:0x0145, B:88:0x014b, B:90:0x015a, B:92:0x0160, B:93:0x0174, B:95:0x0189, B:109:0x01d5, B:111:0x01db, B:114:0x01e5, B:116:0x01eb, B:118:0x01f8, B:120:0x0201, B:122:0x0207, B:123:0x022e, B:124:0x0241, B:126:0x0247, B:128:0x024d, B:129:0x0252, B:130:0x025c, B:141:0x0286, B:142:0x028a, B:157:0x02c2, B:158:0x02c3, B:160:0x02c9, B:165:0x02da, B:166:0x02f0), top: B:200:0x00f7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doGet(javax.servlet.http.HttpServletRequest r17, javax.servlet.http.HttpServletResponse r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 813
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.DefaultServlet.doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    public void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setHeader("Allow", "GET,HEAD,POST,OPTIONS");
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Throwable {
        doGet(httpServletRequest, httpServletResponse);
    }

    public void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendError(405);
    }

    public String getInitParameter(String str) {
        String initParameter = getServletContext().getInitParameter("org.eclipse.jetty.servlet.Default." + str);
        return initParameter == null ? super.getInitParameter(str) : initParameter;
    }

    @Override // org.eclipse.jetty.util.resource.ResourceFactory
    public Resource getResource(String str) {
        String str2 = this._relativeResourceBase;
        if (str2 != null) {
            str = URIUtil.addPaths(str2, str);
        }
        Resource resourceAddPath = null;
        try {
            Resource resource = this._resourceBase;
            resourceAddPath = resource != null ? resource.addPath(str) : this._contextHandler.newResource(this._servletContext.getResource(str));
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Resource " + str + "=" + resourceAddPath, new Object[0]);
            }
        } catch (IOException e2) {
            LOG.ignore(e2);
        }
        return ((resourceAddPath == null || !resourceAddPath.exists()) && str.endsWith("/jetty-dir.css")) ? this._stylesheet : resourceAddPath;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.UnavailableException */
    public void init() throws UnavailableException {
        ServletContext servletContext = getServletContext();
        this._servletContext = servletContext;
        ContextHandler contextHandlerInitContextHandler = initContextHandler(servletContext);
        this._contextHandler = contextHandlerInitContextHandler;
        this._mimeTypes = contextHandlerInitContextHandler.getMimeTypes();
        String[] welcomeFiles = this._contextHandler.getWelcomeFiles();
        this._welcomes = welcomeFiles;
        if (welcomeFiles == null) {
            this._welcomes = new String[]{"index.html", "index.jsp"};
        }
        this._acceptRanges = getInitBoolean("acceptRanges", this._acceptRanges);
        this._dirAllowed = getInitBoolean("dirAllowed", this._dirAllowed);
        this._redirectWelcome = getInitBoolean("redirectWelcome", this._redirectWelcome);
        this._gzip = getInitBoolean(HttpHeaderValues.GZIP, this._gzip);
        this._pathInfoOnly = getInitBoolean("pathInfoOnly", this._pathInfoOnly);
        if ("exact".equals(getInitParameter("welcomeServlets"))) {
            this._welcomeExactServlets = true;
            this._welcomeServlets = false;
        } else {
            this._welcomeServlets = getInitBoolean("welcomeServlets", this._welcomeServlets);
        }
        if (getInitParameter(Constants.EXTRA_KEY_ALIASES) != null) {
            this._contextHandler.setAliases(getInitBoolean(Constants.EXTRA_KEY_ALIASES, false));
        }
        boolean zIsAliases = this._contextHandler.isAliases();
        if (!zIsAliases && !FileResource.getCheckAliases()) {
            throw new IllegalStateException("Alias checking disabled");
        }
        if (zIsAliases) {
            this._servletContext.log("Aliases are enabled! Security constraints may be bypassed!!!");
        }
        this._useFileMappedBuffer = getInitBoolean("useFileMappedBuffer", this._useFileMappedBuffer);
        this._relativeResourceBase = getInitParameter("relativeResourceBase");
        String initParameter = getInitParameter("resourceBase");
        if (initParameter != null) {
            if (this._relativeResourceBase != null) {
                throw new UnavailableException("resourceBase & relativeResourceBase");
            }
            try {
                this._resourceBase = this._contextHandler.newResource(initParameter);
            } catch (Exception e2) {
                LOG.warn(Log.EXCEPTION, e2);
                throw new UnavailableException(e2.toString());
            }
        }
        String initParameter2 = getInitParameter("stylesheet");
        if (initParameter2 != null) {
            try {
                Resource resourceNewResource = Resource.newResource(initParameter2);
                this._stylesheet = resourceNewResource;
                if (!resourceNewResource.exists()) {
                    LOG.warn("!" + initParameter2, new Object[0]);
                    this._stylesheet = null;
                }
            } catch (Exception e3) {
                Logger logger = LOG;
                logger.warn(e3.toString(), new Object[0]);
                logger.debug(e3);
            }
        }
        if (this._stylesheet == null) {
            this._stylesheet = Resource.newResource(getClass().getResource("/jetty-dir.css"));
        }
        String initParameter3 = getInitParameter("cacheControl");
        if (initParameter3 != null) {
            this._cacheControl = new ByteArrayBuffer(initParameter3);
        }
        String initParameter4 = getInitParameter("resourceCache");
        int initInt = getInitInt("maxCacheSize", -2);
        int initInt2 = getInitInt("maxCachedFileSize", -2);
        int initInt3 = getInitInt("maxCachedFiles", -2);
        if (initParameter4 != null) {
            if (initInt != -1 || initInt2 != -2 || initInt3 != -2) {
                LOG.debug("ignoring resource cache configuration, using resourceCache attribute", new Object[0]);
            }
            if (this._relativeResourceBase != null || this._resourceBase != null) {
                throw new UnavailableException("resourceCache specified with resource bases");
            }
            ResourceCache resourceCache = (ResourceCache) this._servletContext.getAttribute(initParameter4);
            this._cache = resourceCache;
            LOG.debug("Cache {}={}", initParameter4, resourceCache);
        }
        this._etags = getInitBoolean("etags", this._etags);
        try {
            if (this._cache == null && initInt3 > 0) {
                ResourceCache resourceCache2 = new ResourceCache(null, this, this._mimeTypes, this._useFileMappedBuffer, this._etags);
                this._cache = resourceCache2;
                if (initInt > 0) {
                    resourceCache2.setMaxCacheSize(initInt);
                }
                if (initInt2 >= -1) {
                    this._cache.setMaxCachedFileSize(initInt2);
                }
                if (initInt3 >= -1) {
                    this._cache.setMaxCachedFiles(initInt3);
                }
            }
            ServletHandler servletHandler = (ServletHandler) this._contextHandler.getChildHandlerByClass(ServletHandler.class);
            this._servletHandler = servletHandler;
            for (ServletHolder servletHolder : servletHandler.getServlets()) {
                if (servletHolder.getServletInstance() == this) {
                    this._defaultHolder = servletHolder;
                }
            }
            Logger logger2 = LOG;
            if (logger2.isDebugEnabled()) {
                logger2.debug("resource base = " + this._resourceBase, new Object[0]);
            }
        } catch (Exception e4) {
            LOG.warn(Log.EXCEPTION, e4);
            throw new UnavailableException(e4.toString());
        }
    }

    public ContextHandler initContextHandler(ServletContext servletContext) {
        if (ContextHandler.getCurrentContext() != null) {
            return ContextHandler.getCurrentContext().getContextHandler();
        }
        if (servletContext instanceof ContextHandler.Context) {
            return ((ContextHandler.Context) servletContext).getContextHandler();
        }
        throw new IllegalArgumentException("The servletContext " + servletContext + " " + servletContext.getClass().getName() + " is not " + ContextHandler.Context.class.getName());
    }

    public boolean passConditionalHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource, HttpContent httpContent) throws IOException {
        Buffer lastModified;
        boolean z2;
        try {
            if (!httpServletRequest.getMethod().equals("HEAD")) {
                if (this._etags) {
                    String header = httpServletRequest.getHeader("If-Match");
                    if (header != null) {
                        if (httpContent == null || httpContent.getETag() == null) {
                            z2 = false;
                        } else {
                            QuotedStringTokenizer quotedStringTokenizer = new QuotedStringTokenizer(header, ", ", false, true);
                            z2 = false;
                            while (!z2 && quotedStringTokenizer.hasMoreTokens()) {
                                if (httpContent.getETag().toString().equals(quotedStringTokenizer.nextToken())) {
                                    z2 = true;
                                }
                            }
                        }
                        if (!z2) {
                            Response response = Response.getResponse(httpServletResponse);
                            response.reset(true);
                            response.setStatus(412);
                            return false;
                        }
                    }
                    String header2 = httpServletRequest.getHeader("If-None-Match");
                    if (header2 != null && httpContent != null && httpContent.getETag() != null) {
                        if (httpContent.getETag().toString().equals(httpServletRequest.getAttribute("o.e.j.s.GzipFilter.ETag"))) {
                            Response response2 = Response.getResponse(httpServletResponse);
                            response2.reset(true);
                            response2.setStatus(304);
                            response2.getHttpFields().put(HttpHeaders.ETAG_BUFFER, header2);
                            return false;
                        }
                        if (httpContent.getETag().toString().equals(header2)) {
                            Response response3 = Response.getResponse(httpServletResponse);
                            response3.reset(true);
                            response3.setStatus(304);
                            response3.getHttpFields().put(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
                            return false;
                        }
                        QuotedStringTokenizer quotedStringTokenizer2 = new QuotedStringTokenizer(header2, ", ", false, true);
                        while (quotedStringTokenizer2.hasMoreTokens()) {
                            if (httpContent.getETag().toString().equals(quotedStringTokenizer2.nextToken())) {
                                Response response4 = Response.getResponse(httpServletResponse);
                                response4.reset(true);
                                response4.setStatus(304);
                                response4.getHttpFields().put(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
                                return false;
                            }
                        }
                        return true;
                    }
                }
                String header3 = httpServletRequest.getHeader("If-Modified-Since");
                if (header3 != null) {
                    Response response5 = Response.getResponse(httpServletResponse);
                    if (httpContent != null && (lastModified = httpContent.getLastModified()) != null && header3.equals(lastModified.toString())) {
                        response5.reset(true);
                        response5.setStatus(304);
                        if (this._etags) {
                            response5.getHttpFields().add(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
                        }
                        response5.flushBuffer();
                        return false;
                    }
                    long dateHeader = httpServletRequest.getDateHeader("If-Modified-Since");
                    if (dateHeader != -1 && resource.lastModified() / 1000 <= dateHeader / 1000) {
                        response5.reset(true);
                        response5.setStatus(304);
                        if (this._etags) {
                            response5.getHttpFields().add(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
                        }
                        response5.flushBuffer();
                        return false;
                    }
                }
                long dateHeader2 = httpServletRequest.getDateHeader("If-Unmodified-Since");
                if (dateHeader2 != -1 && resource.lastModified() / 1000 > dateHeader2 / 1000) {
                    httpServletResponse.sendError(412);
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e2) {
            if (!httpServletResponse.isCommitted()) {
                httpServletResponse.sendError(400, e2.getMessage());
            }
            throw e2;
        }
    }

    public void sendData(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean z2, Resource resource, HttpContent httpContent, Enumeration enumeration) throws NumberFormatException, IOException {
        boolean z3;
        long contentLength;
        ServletOutputStream writerOutputStream;
        boolean zIsWritten;
        int i2 = 0;
        if (httpContent == null) {
            contentLength = resource.length();
            z3 = false;
        } else {
            Connector connector = AbstractHttpConnection.getCurrentConnection().getConnector();
            z3 = (connector instanceof NIOConnector) && ((NIOConnector) connector).getUseDirectBuffers() && !(connector instanceof SslConnector);
            contentLength = httpContent.getContentLength();
        }
        try {
            writerOutputStream = httpServletResponse.getOutputStream();
            zIsWritten = writerOutputStream instanceof HttpOutput ? ((HttpOutput) writerOutputStream).isWritten() : AbstractHttpConnection.getCurrentConnection().getGenerator().isWritten();
        } catch (IllegalStateException unused) {
            writerOutputStream = new WriterOutputStream(httpServletResponse.getWriter());
            zIsWritten = true;
        }
        if (enumeration == null || !enumeration.hasMoreElements() || contentLength < 0) {
            if (z2) {
                resource.writeTo(writerOutputStream, 0L, contentLength);
                return;
            }
            if (httpContent == null || zIsWritten || !(writerOutputStream instanceof HttpOutput)) {
                writeHeaders(httpServletResponse, httpContent, zIsWritten ? -1L : contentLength);
                Buffer indirectBuffer = httpContent == null ? null : httpContent.getIndirectBuffer();
                if (indirectBuffer != null) {
                    indirectBuffer.writeTo(writerOutputStream);
                    return;
                } else {
                    resource.writeTo(writerOutputStream, 0L, contentLength);
                    return;
                }
            }
            if (httpServletResponse instanceof Response) {
                writeOptionHeaders(((Response) httpServletResponse).getHttpFields());
                ((AbstractHttpConnection.Output) writerOutputStream).sendContent(httpContent);
                return;
            }
            Buffer directBuffer = z3 ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
            if (directBuffer != null) {
                writeHeaders(httpServletResponse, httpContent, contentLength);
                ((AbstractHttpConnection.Output) writerOutputStream).sendContent(directBuffer);
                return;
            } else {
                writeHeaders(httpServletResponse, httpContent, contentLength);
                resource.writeTo(writerOutputStream, 0L, contentLength);
                return;
            }
        }
        List listSatisfiableRanges = InclusiveByteRange.satisfiableRanges(enumeration, contentLength);
        if (listSatisfiableRanges == null || listSatisfiableRanges.size() == 0) {
            writeHeaders(httpServletResponse, httpContent, contentLength);
            httpServletResponse.setStatus(416);
            httpServletResponse.setHeader("Content-Range", InclusiveByteRange.to416HeaderRangeString(contentLength));
            resource.writeTo(writerOutputStream, 0L, contentLength);
            return;
        }
        if (listSatisfiableRanges.size() == 1) {
            InclusiveByteRange inclusiveByteRange = (InclusiveByteRange) listSatisfiableRanges.get(0);
            long size = inclusiveByteRange.getSize(contentLength);
            writeHeaders(httpServletResponse, httpContent, size);
            httpServletResponse.setStatus(206);
            httpServletResponse.setHeader("Content-Range", inclusiveByteRange.toHeaderRangeString(contentLength));
            resource.writeTo(writerOutputStream, inclusiveByteRange.getFirst(contentLength), size);
            return;
        }
        writeHeaders(httpServletResponse, httpContent, -1L);
        String string = httpContent.getContentType() == null ? null : httpContent.getContentType().toString();
        if (string == null) {
            LOG.warn("Unknown mimetype for " + httpServletRequest.getRequestURI(), new Object[0]);
        }
        MultiPartOutputStream multiPartOutputStream = new MultiPartOutputStream(writerOutputStream);
        httpServletResponse.setStatus(206);
        httpServletResponse.setContentType((httpServletRequest.getHeader(HttpHeaders.REQUEST_RANGE) != null ? "multipart/x-byteranges; boundary=" : "multipart/byteranges; boundary=") + multiPartOutputStream.getBoundary());
        InputStream inputStream = resource.getInputStream();
        String[] strArr = new String[listSatisfiableRanges.size()];
        int i3 = 0;
        int length = 0;
        while (i3 < listSatisfiableRanges.size()) {
            InclusiveByteRange inclusiveByteRange2 = (InclusiveByteRange) listSatisfiableRanges.get(i3);
            strArr[i3] = inclusiveByteRange2.toHeaderRangeString(contentLength);
            length = (int) (length + (i3 > 0 ? 2 : i2) + 2 + multiPartOutputStream.getBoundary().length() + 2 + (string == null ? i2 : 14 + string.length()) + 2 + 13 + 2 + strArr[i3].length() + 2 + 2 + (inclusiveByteRange2.getLast(contentLength) - inclusiveByteRange2.getFirst(contentLength)) + 1);
            i3++;
            i2 = 0;
        }
        httpServletResponse.setContentLength(length + multiPartOutputStream.getBoundary().length() + 4 + 2 + 2);
        long j2 = 0;
        for (int i4 = 0; i4 < listSatisfiableRanges.size(); i4++) {
            InclusiveByteRange inclusiveByteRange3 = (InclusiveByteRange) listSatisfiableRanges.get(i4);
            multiPartOutputStream.startPart(string, new String[]{"Content-Range: " + strArr[i4]});
            long first = inclusiveByteRange3.getFirst(contentLength);
            long size2 = inclusiveByteRange3.getSize(contentLength);
            if (inputStream != null) {
                if (first < j2) {
                    inputStream.close();
                    inputStream = resource.getInputStream();
                    j2 = 0;
                }
                if (j2 < first) {
                    inputStream.skip(first - j2);
                } else {
                    first = j2;
                }
                IO.copy(inputStream, multiPartOutputStream, size2);
                j2 = first + size2;
            } else {
                resource.writeTo(multiPartOutputStream, first, size2);
            }
        }
        if (inputStream != null) {
            inputStream.close();
        }
        multiPartOutputStream.close();
    }

    public void sendDirectory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource, String str) throws IOException {
        if (!this._dirAllowed) {
            httpServletResponse.sendError(403);
            return;
        }
        String strAddPaths = URIUtil.addPaths(httpServletRequest.getRequestURI(), "/");
        Resource resource2 = this._resourceBase;
        if (resource2 != null) {
            if (resource2 instanceof ResourceCollection) {
                resource = resource2.addPath(str);
            }
        } else if (this._contextHandler.getBaseResource() instanceof ResourceCollection) {
            resource = this._contextHandler.getBaseResource().addPath(str);
        }
        String listHTML = resource.getListHTML(strAddPaths, str.length() > 1);
        if (listHTML == null) {
            httpServletResponse.sendError(403, "No directory");
            return;
        }
        byte[] bytes = listHTML.getBytes("UTF-8");
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.setContentLength(bytes.length);
        httpServletResponse.getOutputStream().write(bytes);
    }

    public void writeHeaders(HttpServletResponse httpServletResponse, HttpContent httpContent, long j2) throws IOException {
        if (httpContent.getContentType() != null && httpServletResponse.getContentType() == null) {
            httpServletResponse.setContentType(httpContent.getContentType().toString());
        }
        if (!(httpServletResponse instanceof Response)) {
            long jLastModified = httpContent.getResource().lastModified();
            if (jLastModified >= 0) {
                httpServletResponse.setDateHeader("Last-Modified", jLastModified);
            }
            if (j2 != -1) {
                if (j2 < 2147483647L) {
                    httpServletResponse.setContentLength((int) j2);
                } else {
                    httpServletResponse.setHeader("Content-Length", Long.toString(j2));
                }
            }
            writeOptionHeaders(httpServletResponse);
            if (this._etags) {
                httpServletResponse.setHeader("ETag", httpContent.getETag().toString());
                return;
            }
            return;
        }
        Response response = (Response) httpServletResponse;
        HttpFields httpFields = response.getHttpFields();
        if (httpContent.getLastModified() != null) {
            httpFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, httpContent.getLastModified());
        } else if (httpContent.getResource() != null) {
            long jLastModified2 = httpContent.getResource().lastModified();
            if (jLastModified2 != -1) {
                httpFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, jLastModified2);
            }
        }
        if (j2 != -1) {
            response.setLongContentLength(j2);
        }
        writeOptionHeaders(httpFields);
        if (this._etags) {
            httpFields.put(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
        }
    }

    public void writeOptionHeaders(HttpFields httpFields) throws IOException {
        if (this._acceptRanges) {
            httpFields.put(HttpHeaders.ACCEPT_RANGES_BUFFER, HttpHeaderValues.BYTES_BUFFER);
        }
        ByteArrayBuffer byteArrayBuffer = this._cacheControl;
        if (byteArrayBuffer != null) {
            httpFields.put(HttpHeaders.CACHE_CONTROL_BUFFER, byteArrayBuffer);
        }
    }

    public void writeOptionHeaders(HttpServletResponse httpServletResponse) throws IOException {
        if (this._acceptRanges) {
            httpServletResponse.setHeader("Accept-Ranges", HttpHeaderValues.BYTES);
        }
        ByteArrayBuffer byteArrayBuffer = this._cacheControl;
        if (byteArrayBuffer != null) {
            httpServletResponse.setHeader("Cache-Control", byteArrayBuffer.toString());
        }
    }
}
