package org.eclipse.jetty.server;

import cn.hutool.core.text.StrPool;
import com.yikaobang.yixue.R2;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferUtil;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.DirectNIOBuffer;
import org.eclipse.jetty.io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.MultiPartInputStream;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class Request implements HttpServletRequest {
    private static final int _STREAM = 1;
    private static final String __ASYNC_FWD = "org.eclipse.asyncfwd";
    public static final String __MULTIPART_CONFIG_ELEMENT = "org.eclipse.multipartConfig";
    public static final String __MULTIPART_CONTEXT = "org.eclipse.multiPartContext";
    public static final String __MULTIPART_INPUT_STREAM = "org.eclipse.multiPartInputStream";
    private static final int __NONE = 0;
    private static final int __READER = 2;
    private volatile Attributes _attributes;
    private Authentication _authentication;
    private MultiMap<String> _baseParameters;
    private String _characterEncoding;
    protected AbstractHttpConnection _connection;
    private ContextHandler.Context _context;
    private String _contextPath;
    private CookieCutter _cookies;
    private long _dispatchTime;
    private DispatcherType _dispatcherType;
    private EndPoint _endp;
    private String _method;
    private MultiPartInputStream _multiPartInputStream;
    private boolean _newContext;
    private MultiMap<String> _parameters;
    private boolean _paramsExtracted;
    private String _pathInfo;
    private int _port;
    private String _queryEncoding;
    private String _queryString;
    private BufferedReader _reader;
    private String _readerEncoding;
    private String _remoteAddr;
    private String _remoteHost;
    private Object _requestAttributeListeners;
    private String _requestURI;
    private String _requestedSessionId;
    private Map<Object, HttpSession> _savedNewSessions;
    private UserIdentity.Scope _scope;
    private String _serverName;
    private String _servletPath;
    private HttpSession _session;
    private SessionManager _sessionManager;
    private long _timeStamp;
    private Buffer _timeStampBuffer;
    private HttpURI _uri;
    private static final Logger LOG = Log.getLogger((Class<?>) Request.class);
    private static final Collection __defaultLocale = Collections.singleton(Locale.getDefault());
    protected final AsyncContinuation _async = new AsyncContinuation();
    private boolean _asyncSupported = true;
    private boolean _cookiesExtracted = false;
    private boolean _dns = false;
    private boolean _handled = false;
    private int _inputState = 0;
    private String _protocol = HttpVersions.HTTP_1_1;
    private boolean _requestedSessionIdFromCookie = false;
    private String _scheme = "http";

    public static class MultiPartCleanerListener implements ServletRequestListener {
        public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
            MultiPartInputStream multiPartInputStream = (MultiPartInputStream) servletRequestEvent.getServletRequest().getAttribute(Request.__MULTIPART_INPUT_STREAM);
            if (multiPartInputStream == null || ((ContextHandler.Context) servletRequestEvent.getServletRequest().getAttribute(Request.__MULTIPART_CONTEXT)) != servletRequestEvent.getServletContext()) {
                return;
            }
            try {
                multiPartInputStream.deleteParts();
            } catch (MultiException e2) {
                servletRequestEvent.getServletContext().log("Errors deleting multipart tmp files", e2);
            }
        }

        public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        }
    }

    public Request() {
    }

    public static Request getRequest(HttpServletRequest httpServletRequest) {
        return httpServletRequest instanceof Request ? (Request) httpServletRequest : AbstractHttpConnection.getCurrentConnection().getRequest();
    }

    public void addEventListener(EventListener eventListener) {
        if (eventListener instanceof ServletRequestAttributeListener) {
            this._requestAttributeListeners = LazyList.add(this._requestAttributeListeners, eventListener);
        }
        if (eventListener instanceof ContinuationListener) {
            throw new IllegalArgumentException(eventListener.getClass().toString());
        }
        if (eventListener instanceof AsyncListener) {
            throw new IllegalArgumentException(eventListener.getClass().toString());
        }
    }

    public boolean authenticate(HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this, httpServletResponse));
            return !(this._authentication instanceof Authentication.ResponseSent);
        }
        httpServletResponse.sendError(401);
        return false;
    }

    public void extractParameters() {
        int contentLength;
        int iIntValue;
        int iIntValue2;
        MultiMap<String> multiMap;
        if (this._baseParameters == null) {
            this._baseParameters = new MultiMap<>(16);
        }
        if (this._paramsExtracted) {
            if (multiMap == null) {
                return;
            } else {
                return;
            }
        }
        this._paramsExtracted = true;
        try {
            HttpURI httpURI = this._uri;
            if (httpURI != null && httpURI.hasQuery()) {
                String str = this._queryEncoding;
                if (str == null) {
                    this._uri.decodeQueryTo(this._baseParameters);
                } else {
                    try {
                        this._uri.decodeQueryTo(this._baseParameters, str);
                    } catch (UnsupportedEncodingException e2) {
                        Logger logger = LOG;
                        if (logger.isDebugEnabled()) {
                            logger.warn(e2);
                        } else {
                            logger.warn(e2.toString(), new Object[0]);
                        }
                    }
                }
            }
            String characterEncoding = getCharacterEncoding();
            String contentType = getContentType();
            if (contentType != null && contentType.length() > 0) {
                contentType = HttpFields.valueParameters(contentType, null);
                if ("application/x-www-form-urlencoded".equalsIgnoreCase(contentType) && this._inputState == 0 && (("POST".equals(getMethod()) || "PUT".equals(getMethod())) && (contentLength = getContentLength()) != 0)) {
                    try {
                        ContextHandler.Context context = this._context;
                        if (context != null) {
                            iIntValue = context.getContextHandler().getMaxFormContentSize();
                            iIntValue2 = this._context.getContextHandler().getMaxFormKeys();
                        } else {
                            iIntValue = -1;
                            iIntValue2 = -1;
                        }
                        if (iIntValue < 0) {
                            Object attribute = this._connection.getConnector().getServer().getAttribute("org.eclipse.jetty.server.Request.maxFormContentSize");
                            if (attribute == null) {
                                iIntValue = 200000;
                            } else if (attribute instanceof Number) {
                                iIntValue = ((Number) attribute).intValue();
                            } else if (attribute instanceof String) {
                                iIntValue = Integer.valueOf((String) attribute).intValue();
                            }
                        }
                        if (iIntValue2 < 0) {
                            Object attribute2 = this._connection.getConnector().getServer().getAttribute("org.eclipse.jetty.server.Request.maxFormKeys");
                            if (attribute2 == null) {
                                iIntValue2 = 1000;
                            } else if (attribute2 instanceof Number) {
                                iIntValue2 = ((Number) attribute2).intValue();
                            } else if (attribute2 instanceof String) {
                                iIntValue2 = Integer.valueOf((String) attribute2).intValue();
                            }
                        }
                        if (contentLength > iIntValue && iIntValue > 0) {
                            throw new IllegalStateException("Form too large " + contentLength + ">" + iIntValue);
                        }
                        UrlEncoded.decodeTo(getInputStream(), this._baseParameters, characterEncoding, contentLength < 0 ? iIntValue : -1, iIntValue2);
                    } catch (IOException e3) {
                        Logger logger2 = LOG;
                        if (logger2.isDebugEnabled()) {
                            logger2.warn(e3);
                        } else {
                            logger2.warn(e3.toString(), new Object[0]);
                        }
                    }
                }
            }
            MultiMap<String> multiMap2 = this._parameters;
            if (multiMap2 == null) {
                this._parameters = this._baseParameters;
            } else {
                MultiMap<String> multiMap3 = this._baseParameters;
                if (multiMap2 != multiMap3) {
                    for (Map.Entry<String, Object> entry : multiMap3.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        for (int i2 = 0; i2 < LazyList.size(value); i2++) {
                            this._parameters.add(key, LazyList.get(value, i2));
                        }
                    }
                }
            }
            if (contentType != null && contentType.length() > 0 && contentType.startsWith("multipart/form-data") && getAttribute(__MULTIPART_CONFIG_ELEMENT) != null) {
                try {
                    getParts();
                } catch (ServletException e4) {
                    if (LOG.isDebugEnabled()) {
                        LOG.warn(e4);
                    } else {
                        LOG.warn(e4.toString(), new Object[0]);
                    }
                } catch (IOException e5) {
                    if (LOG.isDebugEnabled()) {
                        LOG.warn(e5);
                    } else {
                        LOG.warn(e5.toString(), new Object[0]);
                    }
                }
            }
            if (this._parameters == null) {
                this._parameters = this._baseParameters;
            }
        } finally {
            if (this._parameters == null) {
                this._parameters = this._baseParameters;
            }
        }
    }

    public AsyncContext getAsyncContext() {
        if (!this._async.isInitial() || this._async.isAsyncStarted()) {
            return this._async;
        }
        throw new IllegalStateException(this._async.getStatusString());
    }

    public AsyncContinuation getAsyncContinuation() {
        return this._async;
    }

    public Object getAttribute(String str) {
        if ("org.eclipse.jetty.io.EndPoint.maxIdleTime".equalsIgnoreCase(str)) {
            return new Long(getConnection().getEndPoint().getMaxIdleTime());
        }
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        return (attribute == null && Continuation.ATTRIBUTE.equals(str)) ? this._async : attribute;
    }

    public Enumeration getAttributeNames() {
        return this._attributes == null ? Collections.enumeration(Collections.EMPTY_LIST) : AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        if (this._attributes == null) {
            this._attributes = new AttributesMap();
        }
        return this._attributes;
    }

    public String getAuthType() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getAuthMethod();
        }
        return null;
    }

    public Authentication getAuthentication() {
        return this._authentication;
    }

    public String getCharacterEncoding() {
        return this._characterEncoding;
    }

    public AbstractHttpConnection getConnection() {
        return this._connection;
    }

    public int getContentLength() {
        return (int) this._connection.getRequestFields().getLongField(HttpHeaders.CONTENT_LENGTH_BUFFER);
    }

    public long getContentRead() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        if (abstractHttpConnection == null || abstractHttpConnection.getParser() == null) {
            return -1L;
        }
        return ((HttpParser) this._connection.getParser()).getContentRead();
    }

    public String getContentType() {
        return this._connection.getRequestFields().getStringField(HttpHeaders.CONTENT_TYPE_BUFFER);
    }

    public ContextHandler.Context getContext() {
        return this._context;
    }

    public String getContextPath() {
        return this._contextPath;
    }

    public Cookie[] getCookies() {
        if (this._cookiesExtracted) {
            CookieCutter cookieCutter = this._cookies;
            if (cookieCutter == null) {
                return null;
            }
            return cookieCutter.getCookies();
        }
        this._cookiesExtracted = true;
        Enumeration<String> values = this._connection.getRequestFields().getValues(HttpHeaders.COOKIE_BUFFER);
        if (values != null) {
            if (this._cookies == null) {
                this._cookies = new CookieCutter();
            }
            while (values.hasMoreElements()) {
                this._cookies.addCookieField(values.nextElement());
            }
        }
        CookieCutter cookieCutter2 = this._cookies;
        if (cookieCutter2 == null) {
            return null;
        }
        return cookieCutter2.getCookies();
    }

    public long getDateHeader(String str) {
        return this._connection.getRequestFields().getDateField(str);
    }

    public long getDispatchTime() {
        return this._dispatchTime;
    }

    public DispatcherType getDispatcherType() {
        return this._dispatcherType;
    }

    public String getHeader(String str) {
        return this._connection.getRequestFields().getStringField(str);
    }

    public Enumeration getHeaderNames() {
        return this._connection.getRequestFields().getFieldNames();
    }

    public Enumeration getHeaders(String str) {
        Enumeration<String> values = this._connection.getRequestFields().getValues(str);
        return values == null ? Collections.enumeration(Collections.EMPTY_LIST) : values;
    }

    public int getInputState() {
        return this._inputState;
    }

    public ServletInputStream getInputStream() throws IOException {
        int i2 = this._inputState;
        if (i2 != 0 && i2 != 1) {
            throw new IllegalStateException("READER");
        }
        this._inputState = 1;
        return this._connection.getInputStream();
    }

    public int getIntHeader(String str) {
        return (int) this._connection.getRequestFields().getLongField(str);
    }

    public String getLocalAddr() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getLocalAddr();
    }

    public String getLocalName() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        if (this._dns) {
            return endPoint.getLocalHost();
        }
        String localAddr = endPoint.getLocalAddr();
        if (localAddr == null || localAddr.indexOf(58) < 0) {
            return localAddr;
        }
        return StrPool.BRACKET_START + localAddr + StrPool.BRACKET_END;
    }

    public int getLocalPort() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return 0;
        }
        return endPoint.getLocalPort();
    }

    public Locale getLocale() {
        String strTrim;
        Enumeration<String> values = this._connection.getRequestFields().getValues("Accept-Language", HttpFields.__separators);
        if (values == null || !values.hasMoreElements()) {
            return Locale.getDefault();
        }
        List listQualityList = HttpFields.qualityList(values);
        if (listQualityList.size() == 0) {
            return Locale.getDefault();
        }
        if (listQualityList.size() <= 0) {
            return Locale.getDefault();
        }
        String strValueParameters = HttpFields.valueParameters((String) listQualityList.get(0), null);
        int iIndexOf = strValueParameters.indexOf(45);
        if (iIndexOf > -1) {
            strTrim = strValueParameters.substring(iIndexOf + 1).trim();
            strValueParameters = strValueParameters.substring(0, iIndexOf).trim();
        } else {
            strTrim = "";
        }
        return new Locale(strValueParameters, strTrim);
    }

    public Enumeration getLocales() {
        String strTrim;
        Enumeration<String> values = this._connection.getRequestFields().getValues("Accept-Language", HttpFields.__separators);
        if (values == null || !values.hasMoreElements()) {
            return Collections.enumeration(__defaultLocale);
        }
        List listQualityList = HttpFields.qualityList(values);
        if (listQualityList.size() == 0) {
            return Collections.enumeration(__defaultLocale);
        }
        int size = listQualityList.size();
        Object objAdd = null;
        for (int i2 = 0; i2 < size; i2++) {
            String strValueParameters = HttpFields.valueParameters((String) listQualityList.get(i2), null);
            int iIndexOf = strValueParameters.indexOf(45);
            if (iIndexOf > -1) {
                strTrim = strValueParameters.substring(iIndexOf + 1).trim();
                strValueParameters = strValueParameters.substring(0, iIndexOf).trim();
            } else {
                strTrim = "";
            }
            objAdd = LazyList.add(LazyList.ensureSize(objAdd, size), new Locale(strValueParameters, strTrim));
        }
        return LazyList.size(objAdd) == 0 ? Collections.enumeration(__defaultLocale) : Collections.enumeration(LazyList.getList(objAdd));
    }

    public String getMethod() {
        return this._method;
    }

    public String getParameter(String str) {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return (String) this._parameters.getValue(str, 0);
    }

    public Map getParameterMap() {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return Collections.unmodifiableMap(this._parameters.toStringArrayMap());
    }

    public Enumeration getParameterNames() {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        return Collections.enumeration(this._parameters.keySet());
    }

    public String[] getParameterValues(String str) {
        if (!this._paramsExtracted) {
            extractParameters();
        }
        List values = this._parameters.getValues(str);
        if (values == null) {
            return null;
        }
        return (String[]) values.toArray(new String[values.size()]);
    }

    public MultiMap<String> getParameters() {
        return this._parameters;
    }

    public Part getPart(String str) throws Throwable {
        getParts();
        return this._multiPartInputStream.getPart(str);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public Collection<Part> getParts() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        if (getContentType() == null || !getContentType().startsWith("multipart/form-data")) {
            throw new ServletException("Content-Type != multipart/form-data");
        }
        if (this._multiPartInputStream == null) {
            this._multiPartInputStream = (MultiPartInputStream) getAttribute(__MULTIPART_INPUT_STREAM);
        }
        if (this._multiPartInputStream == null) {
            MultipartConfigElement multipartConfigElement = (MultipartConfigElement) getAttribute(__MULTIPART_CONFIG_ELEMENT);
            if (multipartConfigElement == null) {
                throw new IllegalStateException("No multipart config for servlet");
            }
            ServletInputStream inputStream = getInputStream();
            String contentType = getContentType();
            ContextHandler.Context context = this._context;
            ByteArrayOutputStream byteArrayOutputStream2 = null;
            MultiPartInputStream multiPartInputStream = new MultiPartInputStream(inputStream, contentType, multipartConfigElement, context != null ? (File) context.getAttribute("javax.servlet.context.tempdir") : null);
            this._multiPartInputStream = multiPartInputStream;
            setAttribute(__MULTIPART_INPUT_STREAM, multiPartInputStream);
            setAttribute(__MULTIPART_CONTEXT, this._context);
            Iterator<Part> it = this._multiPartInputStream.getParts().iterator();
            while (it.hasNext()) {
                MultiPartInputStream.MultiPart multiPart = (MultiPartInputStream.MultiPart) it.next();
                if (multiPart.getContentDispositionFilename() == null) {
                    String charsetFromContentType = multiPart.getContentType() != null ? MimeTypes.getCharsetFromContentType(new ByteArrayBuffer(multiPart.getContentType())) : null;
                    InputStream inputStream2 = multiPart.getInputStream();
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        IO.copy(inputStream2, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        if (charsetFromContentType == null) {
                            charsetFromContentType = "UTF-8";
                        }
                        String str = new String(byteArray, charsetFromContentType);
                        getParameter("");
                        getParameters().add(multiPart.getName(), str);
                        IO.close(byteArrayOutputStream);
                        IO.close(inputStream2);
                    } catch (Throwable th2) {
                        th = th2;
                        byteArrayOutputStream2 = byteArrayOutputStream;
                        IO.close(byteArrayOutputStream2);
                        IO.close(inputStream2);
                        throw th;
                    }
                }
            }
        }
        return this._multiPartInputStream.getParts();
    }

    public String getPathInfo() {
        return this._pathInfo;
    }

    public String getPathTranslated() {
        ContextHandler.Context context;
        String str = this._pathInfo;
        if (str == null || (context = this._context) == null) {
            return null;
        }
        return context.getRealPath(str);
    }

    public String getProtocol() {
        return this._protocol;
    }

    public String getQueryEncoding() {
        return this._queryEncoding;
    }

    public String getQueryString() {
        HttpURI httpURI;
        if (this._queryString == null && (httpURI = this._uri) != null) {
            String str = this._queryEncoding;
            if (str == null) {
                this._queryString = httpURI.getQuery();
            } else {
                this._queryString = httpURI.getQuery(str);
            }
        }
        return this._queryString;
    }

    public BufferedReader getReader() throws IOException {
        int i2 = this._inputState;
        if (i2 != 0 && i2 != 2) {
            throw new IllegalStateException("STREAMED");
        }
        if (i2 == 2) {
            return this._reader;
        }
        String characterEncoding = getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = "ISO-8859-1";
        }
        if (this._reader == null || !characterEncoding.equalsIgnoreCase(this._readerEncoding)) {
            final ServletInputStream inputStream = getInputStream();
            this._readerEncoding = characterEncoding;
            this._reader = new BufferedReader(new InputStreamReader((InputStream) inputStream, characterEncoding)) { // from class: org.eclipse.jetty.server.Request.1
                @Override // java.io.BufferedReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    inputStream.close();
                }
            };
        }
        this._inputState = 2;
        return this._reader;
    }

    public String getRealPath(String str) {
        ContextHandler.Context context = this._context;
        if (context == null) {
            return null;
        }
        return context.getRealPath(str);
    }

    public String getRemoteAddr() {
        String str = this._remoteAddr;
        if (str != null) {
            return str;
        }
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getRemoteAddr();
    }

    public String getRemoteHost() {
        if (!this._dns) {
            return getRemoteAddr();
        }
        String str = this._remoteHost;
        if (str != null) {
            return str;
        }
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return null;
        }
        return endPoint.getRemoteHost();
    }

    public int getRemotePort() {
        EndPoint endPoint = this._endp;
        if (endPoint == null) {
            return 0;
        }
        return endPoint.getRemotePort();
    }

    public String getRemoteUser() {
        Principal userPrincipal = getUserPrincipal();
        if (userPrincipal == null) {
            return null;
        }
        return userPrincipal.getName();
    }

    public RequestDispatcher getRequestDispatcher(String str) {
        if (str == null || this._context == null) {
            return null;
        }
        if (!str.startsWith("/")) {
            String strAddPaths = URIUtil.addPaths(this._servletPath, this._pathInfo);
            int iLastIndexOf = strAddPaths.lastIndexOf("/");
            str = URIUtil.addPaths(iLastIndexOf > 1 ? strAddPaths.substring(0, iLastIndexOf + 1) : "/", str);
        }
        return this._context.getRequestDispatcher(str);
    }

    public String getRequestURI() {
        HttpURI httpURI;
        if (this._requestURI == null && (httpURI = this._uri) != null) {
            this._requestURI = httpURI.getPathAndParam();
        }
        return this._requestURI;
    }

    public StringBuffer getRequestURL() {
        StringBuffer stringBuffer = new StringBuffer(48);
        synchronized (stringBuffer) {
            String scheme = getScheme();
            int serverPort = getServerPort();
            stringBuffer.append(scheme);
            stringBuffer.append("://");
            stringBuffer.append(getServerName());
            if (this._port > 0 && ((scheme.equalsIgnoreCase("http") && serverPort != 80) || (scheme.equalsIgnoreCase("https") && serverPort != 443))) {
                stringBuffer.append(':');
                stringBuffer.append(this._port);
            }
            stringBuffer.append(getRequestURI());
        }
        return stringBuffer;
    }

    public String getRequestedSessionId() {
        return this._requestedSessionId;
    }

    public UserIdentity getResolvedUserIdentity() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.User) {
            return ((Authentication.User) authentication).getUserIdentity();
        }
        return null;
    }

    public Response getResponse() {
        return this._connection._response;
    }

    public StringBuilder getRootURL() {
        StringBuilder sb = new StringBuilder(48);
        String scheme = getScheme();
        int serverPort = getServerPort();
        sb.append(scheme);
        sb.append("://");
        sb.append(getServerName());
        if (serverPort > 0 && ((scheme.equalsIgnoreCase("http") && serverPort != 80) || (scheme.equalsIgnoreCase("https") && serverPort != 443))) {
            sb.append(':');
            sb.append(serverPort);
        }
        return sb;
    }

    public String getScheme() {
        return this._scheme;
    }

    public String getServerName() {
        String str = this._serverName;
        if (str != null) {
            return str;
        }
        HttpURI httpURI = this._uri;
        if (httpURI == null) {
            throw new IllegalStateException("No uri");
        }
        this._serverName = httpURI.getHost();
        this._port = this._uri.getPort();
        String str2 = this._serverName;
        if (str2 != null) {
            return str2;
        }
        Buffer buffer = this._connection.getRequestFields().get(HttpHeaders.HOST_BUFFER);
        if (buffer == null) {
            if (this._connection != null) {
                this._serverName = getLocalName();
                this._port = getLocalPort();
                String str3 = this._serverName;
                if (str3 != null && !StringUtil.ALL_INTERFACES.equals(str3)) {
                    return this._serverName;
                }
            }
            try {
                this._serverName = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e2) {
                LOG.ignore(e2);
            }
            return this._serverName;
        }
        int iPutIndex = buffer.putIndex();
        while (true) {
            int i2 = iPutIndex - 1;
            if (iPutIndex <= buffer.getIndex()) {
                break;
            }
            char cPeek = (char) (buffer.peek(i2) & 255);
            if (cPeek == ':') {
                this._serverName = BufferUtil.to8859_1_String(buffer.peek(buffer.getIndex(), i2 - buffer.getIndex()));
                try {
                    try {
                        this._port = BufferUtil.toInt(buffer.peek(i2 + 1, (buffer.putIndex() - i2) - 1));
                    } catch (IOException e3) {
                        throw new RuntimeException(e3);
                    }
                } catch (NumberFormatException unused) {
                    AbstractHttpConnection abstractHttpConnection = this._connection;
                    if (abstractHttpConnection != null) {
                        abstractHttpConnection._generator.sendError(400, "Bad Host header", null, true);
                    }
                }
                return this._serverName;
            }
            if (cPeek == ']') {
                break;
            }
            iPutIndex = i2;
        }
        if (this._serverName == null || this._port < 0) {
            this._serverName = BufferUtil.to8859_1_String(buffer);
            this._port = 0;
        }
        return this._serverName;
    }

    public int getServerPort() {
        HttpURI httpURI;
        if (this._port <= 0) {
            if (this._serverName == null) {
                getServerName();
            }
            if (this._port <= 0) {
                if (this._serverName == null || (httpURI = this._uri) == null) {
                    EndPoint endPoint = this._endp;
                    this._port = endPoint == null ? 0 : endPoint.getLocalPort();
                } else {
                    this._port = httpURI.getPort();
                }
            }
        }
        int i2 = this._port;
        if (i2 > 0) {
            return i2;
        }
        if (getScheme().equalsIgnoreCase("https")) {
            return R2.attr.banner_indicator_selected_color;
        }
        return 80;
    }

    public ServletContext getServletContext() {
        return this._context;
    }

    public String getServletName() {
        UserIdentity.Scope scope = this._scope;
        if (scope != null) {
            return scope.getName();
        }
        return null;
    }

    public String getServletPath() {
        if (this._servletPath == null) {
            this._servletPath = "";
        }
        return this._servletPath;
    }

    public ServletResponse getServletResponse() {
        return this._connection.getResponse();
    }

    public HttpSession getSession() {
        return getSession(true);
    }

    public SessionManager getSessionManager() {
        return this._sessionManager;
    }

    public long getTimeStamp() {
        return this._timeStamp;
    }

    public Buffer getTimeStampBuffer() {
        if (this._timeStampBuffer == null) {
            long j2 = this._timeStamp;
            if (j2 > 0) {
                this._timeStampBuffer = HttpFields.__dateCache.formatBuffer(j2);
            }
        }
        return this._timeStampBuffer;
    }

    public HttpURI getUri() {
        return this._uri;
    }

    public UserIdentity getUserIdentity() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getUserIdentity();
        }
        return null;
    }

    public UserIdentity.Scope getUserIdentityScope() {
        return this._scope;
    }

    public Principal getUserPrincipal() {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).getUserIdentity().getUserPrincipal();
        }
        return null;
    }

    public boolean isAsyncStarted() {
        return this._async.isAsyncStarted();
    }

    public boolean isAsyncSupported() {
        return this._asyncSupported;
    }

    public boolean isHandled() {
        return this._handled;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return this._requestedSessionId != null && this._requestedSessionIdFromCookie;
    }

    public boolean isRequestedSessionIdFromURL() {
        return (this._requestedSessionId == null || this._requestedSessionIdFromCookie) ? false : true;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return (this._requestedSessionId == null || this._requestedSessionIdFromCookie) ? false : true;
    }

    public boolean isRequestedSessionIdValid() {
        HttpSession session;
        return (this._requestedSessionId == null || (session = getSession(false)) == null || !this._sessionManager.getSessionIdManager().getClusterId(this._requestedSessionId).equals(this._sessionManager.getClusterId(session))) ? false : true;
    }

    public boolean isSecure() {
        return this._connection.isConfidential(this);
    }

    public boolean isUserInRole(String str) {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.Deferred) {
            setAuthentication(((Authentication.Deferred) authentication).authenticate(this));
        }
        Authentication authentication2 = this._authentication;
        if (authentication2 instanceof Authentication.User) {
            return ((Authentication.User) authentication2).isUserInRole(this._scope, str);
        }
        return false;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public void login(String str, String str2) throws ServletException {
        Authentication authentication = this._authentication;
        if (!(authentication instanceof Authentication.Deferred)) {
            throw new ServletException("Authenticated as " + this._authentication);
        }
        Authentication authenticationLogin = ((Authentication.Deferred) authentication).login(str, str2, this);
        this._authentication = authenticationLogin;
        if (authenticationLogin == null) {
            throw new ServletException();
        }
    }

    public void logout() throws ServletException {
        Authentication authentication = this._authentication;
        if (authentication instanceof Authentication.User) {
            ((Authentication.User) authentication).logout();
        }
        this._authentication = Authentication.UNAUTHENTICATED;
    }

    public void mergeQueryString(String str) {
        boolean z2;
        MultiMap<String> multiMap = new MultiMap<>();
        UrlEncoded.decodeTo(str, multiMap, "UTF-8");
        if (!this._paramsExtracted) {
            extractParameters();
        }
        MultiMap<String> multiMap2 = this._parameters;
        if (multiMap2 == null || multiMap2.size() <= 0) {
            z2 = false;
        } else {
            z2 = false;
            for (Map.Entry<String, Object> entry : this._parameters.entrySet()) {
                String key = entry.getKey();
                if (multiMap.containsKey(key)) {
                    z2 = true;
                }
                Object value = entry.getValue();
                for (int i2 = 0; i2 < LazyList.size(value); i2++) {
                    multiMap.add(key, LazyList.get(value, i2));
                }
            }
        }
        String str2 = this._queryString;
        if (str2 != null && str2.length() > 0) {
            if (z2) {
                StringBuilder sb = new StringBuilder();
                MultiMap multiMap3 = new MultiMap();
                UrlEncoded.decodeTo(this._queryString, multiMap3, getQueryEncoding());
                MultiMap multiMap4 = new MultiMap();
                UrlEncoded.decodeTo(str, multiMap4, "UTF-8");
                for (Map.Entry entry2 : multiMap3.entrySet()) {
                    String str3 = (String) entry2.getKey();
                    if (!multiMap4.containsKey(str3)) {
                        Object value2 = entry2.getValue();
                        for (int i3 = 0; i3 < LazyList.size(value2); i3++) {
                            sb.append("&");
                            sb.append(str3);
                            sb.append("=");
                            sb.append(LazyList.get(value2, i3));
                        }
                    }
                }
                str = str + ((Object) sb);
            } else {
                str = str + "&" + this._queryString;
            }
        }
        setParameters(multiMap);
        setQueryString(str);
    }

    public HttpSession recoverNewSession(Object obj) {
        Map<Object, HttpSession> map = this._savedNewSessions;
        if (map == null) {
            return null;
        }
        return map.get(obj);
    }

    public void recycle() throws IOException {
        if (this._inputState == 2) {
            try {
                int i2 = this._reader.read();
                while (i2 != -1) {
                    i2 = this._reader.read();
                }
            } catch (Exception e2) {
                LOG.ignore(e2);
                this._reader = null;
            }
        }
        setAuthentication(Authentication.NOT_CHECKED);
        this._async.recycle();
        this._asyncSupported = true;
        this._handled = false;
        if (this._context != null) {
            throw new IllegalStateException("Request in context!");
        }
        if (this._attributes != null) {
            this._attributes.clearAttributes();
        }
        this._characterEncoding = null;
        this._contextPath = null;
        CookieCutter cookieCutter = this._cookies;
        if (cookieCutter != null) {
            cookieCutter.reset();
        }
        this._cookiesExtracted = false;
        this._context = null;
        this._serverName = null;
        this._method = null;
        this._pathInfo = null;
        this._port = 0;
        this._protocol = HttpVersions.HTTP_1_1;
        this._queryEncoding = null;
        this._queryString = null;
        this._requestedSessionId = null;
        this._requestedSessionIdFromCookie = false;
        this._session = null;
        this._sessionManager = null;
        this._requestURI = null;
        this._scope = null;
        this._scheme = "http";
        this._servletPath = null;
        this._timeStamp = 0L;
        this._timeStampBuffer = null;
        this._uri = null;
        MultiMap<String> multiMap = this._baseParameters;
        if (multiMap != null) {
            multiMap.clear();
        }
        this._parameters = null;
        this._paramsExtracted = false;
        this._inputState = 0;
        Map<Object, HttpSession> map = this._savedNewSessions;
        if (map != null) {
            map.clear();
        }
        this._savedNewSessions = null;
        this._multiPartInputStream = null;
    }

    public void removeAttribute(String str) {
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        if (this._attributes != null) {
            this._attributes.removeAttribute(str);
        }
        if (attribute == null || this._requestAttributeListeners == null) {
            return;
        }
        ServletRequestAttributeEvent servletRequestAttributeEvent = new ServletRequestAttributeEvent(this._context, this, str, attribute);
        int size = LazyList.size(this._requestAttributeListeners);
        for (int i2 = 0; i2 < size; i2++) {
            ServletRequestAttributeListener servletRequestAttributeListener = (ServletRequestAttributeListener) LazyList.get(this._requestAttributeListeners, i2);
            if (servletRequestAttributeListener instanceof ServletRequestAttributeListener) {
                servletRequestAttributeListener.attributeRemoved(servletRequestAttributeEvent);
            }
        }
    }

    public void removeEventListener(EventListener eventListener) {
        this._requestAttributeListeners = LazyList.remove(this._requestAttributeListeners, eventListener);
    }

    public void saveNewSession(Object obj, HttpSession httpSession) {
        if (this._savedNewSessions == null) {
            this._savedNewSessions = new HashMap();
        }
        this._savedNewSessions.put(obj, httpSession);
    }

    public void setAsyncSupported(boolean z2) {
        this._asyncSupported = z2;
    }

    public void setAttribute(String str, Object obj) {
        Object attribute = this._attributes == null ? null : this._attributes.getAttribute(str);
        if (str.startsWith("org.eclipse.jetty.")) {
            if ("org.eclipse.jetty.server.Request.queryEncoding".equals(str)) {
                setQueryEncoding(obj != null ? obj.toString() : null);
            } else if ("org.eclipse.jetty.server.sendContent".equals(str)) {
                try {
                    ((AbstractHttpConnection.Output) getServletResponse().getOutputStream()).sendContent(obj);
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            } else if ("org.eclipse.jetty.server.ResponseBuffer".equals(str)) {
                try {
                    ByteBuffer byteBuffer = (ByteBuffer) obj;
                    synchronized (byteBuffer) {
                        ((AbstractHttpConnection.Output) getServletResponse().getOutputStream()).sendResponse(byteBuffer.isDirect() ? new DirectNIOBuffer(byteBuffer, true) : new IndirectNIOBuffer(byteBuffer, true));
                    }
                } catch (IOException e3) {
                    throw new RuntimeException(e3);
                }
            } else if ("org.eclipse.jetty.io.EndPoint.maxIdleTime".equalsIgnoreCase(str)) {
                try {
                    getConnection().getEndPoint().setMaxIdleTime(Integer.valueOf(obj.toString()).intValue());
                } catch (IOException e4) {
                    throw new RuntimeException(e4);
                }
            }
        }
        if (this._attributes == null) {
            this._attributes = new AttributesMap();
        }
        this._attributes.setAttribute(str, obj);
        if (this._requestAttributeListeners != null) {
            ServletRequestAttributeEvent servletRequestAttributeEvent = new ServletRequestAttributeEvent(this._context, this, str, attribute == null ? obj : attribute);
            int size = LazyList.size(this._requestAttributeListeners);
            for (int i2 = 0; i2 < size; i2++) {
                ServletRequestAttributeListener servletRequestAttributeListener = (ServletRequestAttributeListener) LazyList.get(this._requestAttributeListeners, i2);
                if (servletRequestAttributeListener instanceof ServletRequestAttributeListener) {
                    if (attribute == null) {
                        servletRequestAttributeListener.attributeAdded(servletRequestAttributeEvent);
                    } else if (obj == null) {
                        servletRequestAttributeListener.attributeRemoved(servletRequestAttributeEvent);
                    } else {
                        servletRequestAttributeListener.attributeReplaced(servletRequestAttributeEvent);
                    }
                }
            }
        }
    }

    public void setAttributes(Attributes attributes) {
        this._attributes = attributes;
    }

    public void setAuthentication(Authentication authentication) {
        this._authentication = authentication;
    }

    public void setCharacterEncoding(String str) throws UnsupportedEncodingException {
        if (this._inputState != 0) {
            return;
        }
        this._characterEncoding = str;
        if (StringUtil.isUTF8(str)) {
            return;
        }
        "".getBytes(str);
    }

    public void setCharacterEncodingUnchecked(String str) {
        this._characterEncoding = str;
    }

    public final void setConnection(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
        this._async.setConnection(abstractHttpConnection);
        this._endp = abstractHttpConnection.getEndPoint();
        this._dns = abstractHttpConnection.getResolveNames();
    }

    public void setContentType(String str) {
        this._connection.getRequestFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, str);
    }

    public void setContext(ContextHandler.Context context) {
        this._newContext = this._context != context;
        this._context = context;
    }

    public void setContextPath(String str) {
        this._contextPath = str;
    }

    public void setCookies(Cookie[] cookieArr) {
        if (this._cookies == null) {
            this._cookies = new CookieCutter();
        }
        this._cookies.setCookies(cookieArr);
    }

    public void setDispatchTime(long j2) {
        this._dispatchTime = j2;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this._dispatcherType = dispatcherType;
    }

    public void setHandled(boolean z2) {
        this._handled = z2;
    }

    public void setMethod(String str) {
        this._method = str;
    }

    public void setParameters(MultiMap<String> multiMap) {
        if (multiMap == null) {
            multiMap = this._baseParameters;
        }
        this._parameters = multiMap;
        if (this._paramsExtracted && multiMap == null) {
            throw new IllegalStateException();
        }
    }

    public void setPathInfo(String str) {
        this._pathInfo = str;
    }

    public void setProtocol(String str) {
        this._protocol = str;
    }

    public void setQueryEncoding(String str) {
        this._queryEncoding = str;
        this._queryString = null;
    }

    public void setQueryString(String str) {
        this._queryString = str;
        this._queryEncoding = null;
    }

    public void setRemoteAddr(String str) {
        this._remoteAddr = str;
    }

    public void setRemoteHost(String str) {
        this._remoteHost = str;
    }

    public void setRequestURI(String str) {
        this._requestURI = str;
    }

    public void setRequestedSessionId(String str) {
        this._requestedSessionId = str;
    }

    public void setRequestedSessionIdFromCookie(boolean z2) {
        this._requestedSessionIdFromCookie = z2;
    }

    public void setScheme(String str) {
        this._scheme = str;
    }

    public void setServerName(String str) {
        this._serverName = str;
    }

    public void setServerPort(int i2) {
        this._port = i2;
    }

    public void setServletPath(String str) {
        this._servletPath = str;
    }

    public void setSession(HttpSession httpSession) {
        this._session = httpSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this._sessionManager = sessionManager;
    }

    public void setTimeStamp(long j2) {
        this._timeStamp = j2;
    }

    public void setUri(HttpURI httpURI) {
        this._uri = httpURI;
    }

    public void setUserIdentityScope(UserIdentity.Scope scope) {
        this._scope = scope;
    }

    public AsyncContext startAsync() throws IllegalStateException {
        if (!this._asyncSupported) {
            throw new IllegalStateException("!asyncSupported");
        }
        this._async.startAsync();
        return this._async;
    }

    public boolean takeNewContext() {
        boolean z2 = this._newContext;
        this._newContext = false;
        return z2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._handled ? StrPool.BRACKET_START : "(");
        sb.append(getMethod());
        sb.append(" ");
        sb.append(this._uri);
        sb.append(this._handled ? "]@" : ")@");
        sb.append(hashCode());
        sb.append(" ");
        sb.append(super.toString());
        return sb.toString();
    }

    public HttpSession getSession(boolean z2) {
        HttpSession httpSession = this._session;
        if (httpSession != null) {
            SessionManager sessionManager = this._sessionManager;
            if (sessionManager == null || sessionManager.isValid(httpSession)) {
                return this._session;
            }
            this._session = null;
        }
        if (!z2) {
            return null;
        }
        SessionManager sessionManager2 = this._sessionManager;
        if (sessionManager2 == null) {
            throw new IllegalStateException("No SessionManager");
        }
        HttpSession httpSessionNewHttpSession = sessionManager2.newHttpSession(this);
        this._session = httpSessionNewHttpSession;
        HttpCookie sessionCookie = this._sessionManager.getSessionCookie(httpSessionNewHttpSession, getContextPath(), isSecure());
        if (sessionCookie != null) {
            this._connection.getResponse().addCookie(sessionCookie);
        }
        return this._session;
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        if (this._asyncSupported) {
            this._async.startAsync(this._context, servletRequest, servletResponse);
            return this._async;
        }
        throw new IllegalStateException("!asyncSupported");
    }

    public Request(AbstractHttpConnection abstractHttpConnection) {
        setConnection(abstractHttpConnection);
    }
}
