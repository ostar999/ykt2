package org.eclipse.jetty.server.handler;

import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes9.dex */
public class ContextHandler extends ScopedHandler implements Attributes, Server.Graceful {
    public static final String MANAGED_ATTRIBUTES = "org.eclipse.jetty.server.context.ManagedAttributes";
    private static final int __AVAILABLE = 1;
    private static final int __SHUTDOWN = 2;
    private static final int __STOPPED = 0;
    private static final int __UNAVAILABLE = 3;
    private final CopyOnWriteArrayList<AliasCheck> _aliasChecks;
    private boolean _aliases;
    private boolean _allowNullPathInfo;
    private final AttributesMap _attributes;
    private volatile int _availability;
    private boolean _available;
    private Resource _baseResource;
    private ClassLoader _classLoader;
    private boolean _compactPath;
    private Set<String> _connectors;
    private Object _contextAttributeListeners;
    private final AttributesMap _contextAttributes;
    private Object _contextListeners;
    private String _contextPath;
    private String _displayName;
    private Object _durableListeners;
    private ErrorHandler _errorHandler;
    private EventListener[] _eventListeners;
    private final Map<String, String> _initParams;
    private Map<String, String> _localeEncodingMap;
    private Logger _logger;
    private Map<String, Object> _managedAttributes;
    private int _maxFormContentSize;
    private int _maxFormKeys;
    private MimeTypes _mimeTypes;
    private String[] _protectedTargets;
    private Object _requestAttributeListeners;
    private Object _requestListeners;
    protected Context _scontext;
    private boolean _shutdown;
    private String[] _vhosts;
    private String[] _welcomeFiles;
    private static final Logger LOG = Log.getLogger((Class<?>) ContextHandler.class);
    private static final ThreadLocal<Context> __context = new ThreadLocal<>();

    public interface AliasCheck {
        boolean check(String str, Resource resource);
    }

    public static class ApproveNonExistentDirectoryAliases implements AliasCheck {
        @Override // org.eclipse.jetty.server.handler.ContextHandler.AliasCheck
        public boolean check(String str, Resource resource) {
            int iLastIndexOf = str.lastIndexOf(47);
            if (iLastIndexOf < 0 || resource.exists()) {
                return false;
            }
            return resource.getAlias().toString().endsWith(str.substring(iLastIndexOf));
        }
    }

    public static class ApprovePathPrefixAliases implements AliasCheck {
        @Override // org.eclipse.jetty.server.handler.ContextHandler.AliasCheck
        public boolean check(String str, Resource resource) {
            int iLastIndexOf = str.lastIndexOf(47);
            if (iLastIndexOf < 0 || iLastIndexOf == str.length() - 1) {
                return false;
            }
            return resource.toString().endsWith(str.substring(iLastIndexOf));
        }
    }

    public static class ApproveSameSuffixAliases implements AliasCheck {
        @Override // org.eclipse.jetty.server.handler.ContextHandler.AliasCheck
        public boolean check(String str, Resource resource) {
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf < 0) {
                return false;
            }
            return resource.toString().endsWith(str.substring(iLastIndexOf));
        }
    }

    public static class CLDump implements Dumpable {
        final ClassLoader _loader;

        public CLDump(ClassLoader classLoader) {
            this._loader = classLoader;
        }

        @Override // org.eclipse.jetty.util.component.Dumpable
        public String dump() {
            return AggregateLifeCycle.dump(this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v7, types: [org.eclipse.jetty.server.handler.ContextHandler$CLDump] */
        @Override // org.eclipse.jetty.util.component.Dumpable
        public void dump(Appendable appendable, String str) throws IOException {
            ClassLoader parent;
            appendable.append(String.valueOf(this._loader)).append("\n");
            ClassLoader classLoader = this._loader;
            if (classLoader == null || (parent = classLoader.getParent()) == null) {
                return;
            }
            if (!(parent instanceof Dumpable)) {
                parent = new CLDump(parent);
            }
            ClassLoader classLoader2 = this._loader;
            if (classLoader2 instanceof URLClassLoader) {
                AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(((URLClassLoader) classLoader2).getURLs()), Collections.singleton(parent));
            } else {
                AggregateLifeCycle.dump(appendable, str, Collections.singleton(parent));
            }
        }
    }

    public class Context implements ServletContext {
        private static final String __unimplmented = "Unimplemented - use org.eclipse.jetty.servlet.ServletContextHandler";
        protected int _majorVersion = 3;
        protected int _minorVersion = 0;
        protected boolean _enabled = true;

        public Context() {
        }

        public FilterRegistration.Dynamic addFilter(String str, Class<? extends Filter> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void addListener(String str) {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
            try {
                addListener((Class<? extends EventListener>) (ContextHandler.this._classLoader == null ? Loader.loadClass(ContextHandler.class, str) : ContextHandler.this._classLoader.loadClass(str)));
            } catch (ClassNotFoundException e2) {
                throw new IllegalArgumentException(e2);
            }
        }

        public ServletRegistration.Dynamic addServlet(String str, Class<? extends Servlet> cls) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public <T extends Filter> T createFilter(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
        public <T extends EventListener> T createListener(Class<T> cls) throws ServletException {
            try {
                return cls.newInstance();
            } catch (IllegalAccessException e2) {
                throw new ServletException(e2);
            } catch (InstantiationException e3) {
                throw new ServletException(e3);
            }
        }

        public <T extends Servlet> T createServlet(Class<T> cls) throws ServletException {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public void declareRoles(String... strArr) {
            if (!ContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            }
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            }
        }

        public synchronized Object getAttribute(String str) {
            Object attribute;
            attribute = ContextHandler.this.getAttribute(str);
            if (attribute == null && ContextHandler.this._contextAttributes != null) {
                attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            }
            return attribute;
        }

        public synchronized Enumeration getAttributeNames() {
            HashSet hashSet;
            hashSet = new HashSet();
            if (ContextHandler.this._contextAttributes != null) {
                Enumeration<String> attributeNames = ContextHandler.this._contextAttributes.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    hashSet.add(attributeNames.nextElement());
                }
            }
            Enumeration<String> attributeNames2 = ContextHandler.this._attributes.getAttributeNames();
            while (attributeNames2.hasMoreElements()) {
                hashSet.add(attributeNames2.nextElement());
            }
            return Collections.enumeration(hashSet);
        }

        public ClassLoader getClassLoader() throws AccessControlException {
            AccessController.checkPermission(new RuntimePermission("getClassLoader"));
            return ContextHandler.this._classLoader;
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x009b  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00af A[PHI: r4 r8
          0x00af: PHI (r4v6 int) = (r4v2 int), (r4v2 int), (r4v2 int), (r4v9 int), (r4v2 int) binds: [B:6:0x0020, B:21:0x005b, B:23:0x0062, B:87:0x00af, B:15:0x0044] A[DONT_GENERATE, DONT_INLINE]
          0x00af: PHI (r8v8 java.lang.String) = 
          (r8v1 java.lang.String)
          (r8v1 java.lang.String)
          (r8v1 java.lang.String)
          (r8v12 java.lang.String)
          (r8v1 java.lang.String)
         binds: [B:6:0x0020, B:21:0x005b, B:23:0x0062, B:87:0x00af, B:15:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:90:0x00a1 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public javax.servlet.ServletContext getContext(java.lang.String r18) {
            /*
                Method dump skipped, instructions count: 319
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.Context.getContext(java.lang.String):javax.servlet.ServletContext");
        }

        public ContextHandler getContextHandler() {
            return ContextHandler.this;
        }

        public String getContextPath() {
            return (ContextHandler.this._contextPath == null || !ContextHandler.this._contextPath.equals("/")) ? ContextHandler.this._contextPath : "";
        }

        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public int getEffectiveMajorVersion() {
            return this._majorVersion;
        }

        public int getEffectiveMinorVersion() {
            return this._minorVersion;
        }

        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public FilterRegistration getFilterRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public String getInitParameter(String str) {
            return ContextHandler.this.getInitParameter(str);
        }

        public Enumeration getInitParameterNames() {
            return ContextHandler.this.getInitParameterNames();
        }

        public JspConfigDescriptor getJspConfigDescriptor() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public int getMajorVersion() {
            return 3;
        }

        public String getMimeType(String str) {
            Buffer mimeByExtension;
            if (ContextHandler.this._mimeTypes == null || (mimeByExtension = ContextHandler.this._mimeTypes.getMimeByExtension(str)) == null) {
                return null;
            }
            return mimeByExtension.toString();
        }

        public int getMinorVersion() {
            return 0;
        }

        public RequestDispatcher getNamedDispatcher(String str) {
            return null;
        }

        public String getRealPath(String str) {
            File file;
            if (str == null) {
                return null;
            }
            if (str.length() == 0) {
                str = "/";
            } else if (str.charAt(0) != '/') {
                str = "/" + str;
            }
            try {
                Resource resource = ContextHandler.this.getResource(str);
                if (resource != null && (file = resource.getFile()) != null) {
                    return file.getCanonicalPath();
                }
            } catch (Exception e2) {
                ContextHandler.LOG.ignore(e2);
            }
            return null;
        }

        public RequestDispatcher getRequestDispatcher(String str) {
            String strSubstring;
            if (str == null || !str.startsWith("/")) {
                return null;
            }
            try {
                int iIndexOf = str.indexOf(63);
                if (iIndexOf > 0) {
                    strSubstring = str.substring(iIndexOf + 1);
                    str = str.substring(0, iIndexOf);
                } else {
                    strSubstring = null;
                }
                String strCanonicalPath = URIUtil.canonicalPath(URIUtil.decodePath(str));
                if (strCanonicalPath != null) {
                    return new Dispatcher(ContextHandler.this, URIUtil.addPaths(getContextPath(), str), strCanonicalPath, strSubstring);
                }
            } catch (Exception e2) {
                ContextHandler.LOG.ignore(e2);
            }
            return null;
        }

        public URL getResource(String str) throws MalformedURLException {
            Resource resource = ContextHandler.this.getResource(str);
            if (resource == null || !resource.exists()) {
                return null;
            }
            return resource.getURL();
        }

        public InputStream getResourceAsStream(String str) {
            try {
                URL resource = getResource(str);
                if (resource == null) {
                    return null;
                }
                return Resource.newResource(resource).getInputStream();
            } catch (Exception e2) {
                ContextHandler.LOG.ignore(e2);
                return null;
            }
        }

        public Set getResourcePaths(String str) {
            return ContextHandler.this.getResourcePaths(str);
        }

        public String getServerInfo() {
            return "jetty/" + Server.getVersion();
        }

        @Deprecated
        public Servlet getServlet(String str) throws ServletException {
            return null;
        }

        public String getServletContextName() {
            String displayName = ContextHandler.this.getDisplayName();
            return displayName == null ? ContextHandler.this.getContextPath() : displayName;
        }

        @Deprecated
        public Enumeration getServletNames() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        public ServletRegistration getServletRegistration(String str) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        @Deprecated
        public Enumeration getServlets() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }

        public SessionCookieConfig getSessionCookieConfig() {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public boolean isEnabled() {
            return this._enabled;
        }

        public void log(Exception exc, String str) {
            ContextHandler.this._logger.warn(str, exc);
        }

        public synchronized void removeAttribute(String str) {
            ContextHandler.this.checkManagedAttribute(str, null);
            if (ContextHandler.this._contextAttributes == null) {
                ContextHandler.this._attributes.removeAttribute(str);
                return;
            }
            Object attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            ContextHandler.this._contextAttributes.removeAttribute(str);
            if (attribute != null && ContextHandler.this._contextAttributeListeners != null) {
                ServletContextAttributeEvent servletContextAttributeEvent = new ServletContextAttributeEvent(ContextHandler.this._scontext, str, attribute);
                for (int i2 = 0; i2 < LazyList.size(ContextHandler.this._contextAttributeListeners); i2++) {
                    ((ServletContextAttributeListener) LazyList.get(ContextHandler.this._contextAttributeListeners, i2)).attributeRemoved(servletContextAttributeEvent);
                }
            }
        }

        public synchronized void setAttribute(String str, Object obj) {
            ContextHandler.this.checkManagedAttribute(str, obj);
            Object attribute = ContextHandler.this._contextAttributes.getAttribute(str);
            if (obj == null) {
                ContextHandler.this._contextAttributes.removeAttribute(str);
            } else {
                ContextHandler.this._contextAttributes.setAttribute(str, obj);
            }
            if (ContextHandler.this._contextAttributeListeners != null) {
                ServletContextAttributeEvent servletContextAttributeEvent = new ServletContextAttributeEvent(ContextHandler.this._scontext, str, attribute == null ? obj : attribute);
                for (int i2 = 0; i2 < LazyList.size(ContextHandler.this._contextAttributeListeners); i2++) {
                    ServletContextAttributeListener servletContextAttributeListener = (ServletContextAttributeListener) LazyList.get(ContextHandler.this._contextAttributeListeners, i2);
                    if (attribute == null) {
                        servletContextAttributeListener.attributeAdded(servletContextAttributeEvent);
                    } else if (obj == null) {
                        servletContextAttributeListener.attributeRemoved(servletContextAttributeEvent);
                    } else {
                        servletContextAttributeListener.attributeReplaced(servletContextAttributeEvent);
                    }
                }
            }
        }

        public void setEffectiveMajorVersion(int i2) {
            this._majorVersion = i2;
        }

        public void setEffectiveMinorVersion(int i2) {
            this._minorVersion = i2;
        }

        public void setEnabled(boolean z2) {
            this._enabled = z2;
        }

        public boolean setInitParameter(String str, String str2) {
            if (ContextHandler.this.getInitParameter(str) != null) {
                return false;
            }
            ContextHandler.this.getInitParams().put(str, str2);
            return true;
        }

        public void setJspConfigDescriptor(JspConfigDescriptor jspConfigDescriptor) {
        }

        public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
        }

        public String toString() {
            return "ServletContext@" + ContextHandler.this.toString();
        }

        public FilterRegistration.Dynamic addFilter(String str, Filter filter) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String str, Servlet servlet) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public void log(String str) {
            ContextHandler.this._logger.info(str, new Object[0]);
        }

        public FilterRegistration.Dynamic addFilter(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String str, String str2) {
            ContextHandler.LOG.warn(__unimplmented, new Object[0]);
            return null;
        }

        public void log(String str, Throwable th) {
            ContextHandler.this._logger.warn(str, th);
        }

        public <T extends EventListener> void addListener(T t2) {
            if (this._enabled) {
                ContextHandler.this.addEventListener(t2);
                ContextHandler.this.restrictEventListener(t2);
                return;
            }
            throw new UnsupportedOperationException();
        }

        public void addListener(Class<? extends EventListener> cls) {
            if (this._enabled) {
                try {
                    EventListener eventListenerCreateListener = createListener(cls);
                    ContextHandler.this.addEventListener(eventListenerCreateListener);
                    ContextHandler.this.restrictEventListener(eventListenerCreateListener);
                    return;
                } catch (ServletException e2) {
                    throw new IllegalArgumentException((Throwable) e2);
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    public ContextHandler() {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", -1).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", -1).intValue();
        this._compactPath = false;
        this._aliases = false;
        this._aliasChecks = new CopyOnWriteArrayList<>();
        this._shutdown = false;
        this._available = true;
        this._scontext = new Context();
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
        addAliasCheck(new ApproveNonExistentDirectoryAliases());
    }

    public static Context getCurrentContext() {
        return __context.get();
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(StrPool.DOT) ? str.substring(0, str.length() - 1) : str;
    }

    public void addAliasCheck(AliasCheck aliasCheck) {
        this._aliasChecks.add(aliasCheck);
    }

    public void addEventListener(EventListener eventListener) {
        if (!isStarted() && !isStarting()) {
            this._durableListeners = LazyList.add(this._durableListeners, eventListener);
        }
        setEventListeners((EventListener[]) LazyList.addToArray(getEventListeners(), eventListener, EventListener.class));
    }

    public void addLocaleEncoding(String str, String str2) {
        if (this._localeEncodingMap == null) {
            this._localeEncodingMap = new HashMap();
        }
        this._localeEncodingMap.put(str, str2);
    }

    public void addVirtualHosts(String[] strArr) {
        if (strArr == null) {
            return;
        }
        ArrayList arrayList = this._vhosts != null ? new ArrayList(Arrays.asList(this._vhosts)) : new ArrayList();
        for (String str : strArr) {
            String strNormalizeHostname = normalizeHostname(str);
            if (!arrayList.contains(strNormalizeHostname)) {
                arrayList.add(strNormalizeHostname);
            }
        }
        this._vhosts = (String[]) arrayList.toArray(new String[0]);
    }

    public void callContextDestroyed(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextDestroyed(servletContextEvent);
    }

    public void callContextInitialized(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        servletContextListener.contextInitialized(servletContextEvent);
    }

    public boolean checkContext(String str, Request request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String name;
        DispatcherType dispatcherType = request.getDispatcherType();
        int i2 = this._availability;
        if (i2 != 0 && i2 != 2) {
            if (i2 != 3) {
                if (DispatcherType.REQUEST.equals(dispatcherType) && request.isHandled()) {
                    return false;
                }
                String[] strArr = this._vhosts;
                if (strArr != null && strArr.length > 0) {
                    String strNormalizeHostname = normalizeHostname(request.getServerName());
                    boolean zRegionMatches = false;
                    int i3 = 0;
                    while (!zRegionMatches) {
                        String[] strArr2 = this._vhosts;
                        if (i3 >= strArr2.length) {
                            break;
                        }
                        String str2 = strArr2[i3];
                        if (str2 != null) {
                            zRegionMatches = str2.startsWith("*.") ? str2.regionMatches(true, 2, strNormalizeHostname, strNormalizeHostname.indexOf(StrPool.DOT) + 1, str2.length() - 2) : str2.equalsIgnoreCase(strNormalizeHostname);
                        }
                        i3++;
                    }
                    if (!zRegionMatches) {
                        return false;
                    }
                }
                Set<String> set = this._connectors;
                if (set != null && set.size() > 0 && ((name = AbstractHttpConnection.getCurrentConnection().getConnector().getName()) == null || !this._connectors.contains(name))) {
                    return false;
                }
                if (this._contextPath.length() > 1) {
                    if (!str.startsWith(this._contextPath)) {
                        return false;
                    }
                    if (str.length() > this._contextPath.length() && str.charAt(this._contextPath.length()) != '/') {
                        return false;
                    }
                    if (!this._allowNullPathInfo && this._contextPath.length() == str.length()) {
                        request.setHandled(true);
                        if (request.getQueryString() != null) {
                            httpServletResponse.sendRedirect(URIUtil.addPaths(request.getRequestURI(), "/") + "?" + request.getQueryString());
                        } else {
                            httpServletResponse.sendRedirect(URIUtil.addPaths(request.getRequestURI(), "/"));
                        }
                        return false;
                    }
                }
                return true;
            }
            request.setHandled(true);
            httpServletResponse.sendError(503);
        }
        return false;
    }

    public void checkManagedAttribute(String str, Object obj) {
        Map<String, Object> map = this._managedAttributes;
        if (map == null || !map.containsKey(str)) {
            return;
        }
        setManagedAttribute(str, obj);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void clearAttributes() {
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            checkManagedAttribute(attributeNames.nextElement(), null);
        }
        this._attributes.clearAttributes();
    }

    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    public void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        DispatcherType dispatcherType = request.getDispatcherType();
        boolean zTakeNewContext = request.takeNewContext();
        try {
            if (zTakeNewContext) {
                try {
                    Object obj = this._requestAttributeListeners;
                    if (obj != null) {
                        int size = LazyList.size(obj);
                        for (int i2 = 0; i2 < size; i2++) {
                            request.addEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i2));
                        }
                    }
                    Object obj2 = this._requestListeners;
                    if (obj2 != null) {
                        int size2 = LazyList.size(obj2);
                        ServletRequestEvent servletRequestEvent = new ServletRequestEvent(this._scontext, httpServletRequest);
                        for (int i3 = 0; i3 < size2; i3++) {
                            ((ServletRequestListener) LazyList.get(this._requestListeners, i3)).requestInitialized(servletRequestEvent);
                        }
                    }
                } catch (HttpException e2) {
                    LOG.debug(e2);
                    request.setHandled(true);
                    httpServletResponse.sendError(e2.getStatus(), e2.getReason());
                    if (!zTakeNewContext) {
                        return;
                    }
                    if (this._requestListeners != null) {
                        ServletRequestEvent servletRequestEvent2 = new ServletRequestEvent(this._scontext, httpServletRequest);
                        int size3 = LazyList.size(this._requestListeners);
                        while (true) {
                            int i4 = size3 - 1;
                            if (size3 <= 0) {
                                break;
                            }
                            ((ServletRequestListener) LazyList.get(this._requestListeners, i4)).requestDestroyed(servletRequestEvent2);
                            size3 = i4;
                        }
                    }
                    Object obj3 = this._requestAttributeListeners;
                    if (obj3 == null) {
                        return;
                    }
                    int size4 = LazyList.size(obj3);
                    while (true) {
                        int i5 = size4 - 1;
                        if (size4 <= 0) {
                            return;
                        }
                        request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i5));
                        size4 = i5;
                    }
                }
            }
            if (DispatcherType.REQUEST.equals(dispatcherType) && isProtectedTarget(str)) {
                throw new HttpException(404);
            }
            if (never()) {
                nextHandle(str, request, httpServletRequest, httpServletResponse);
            } else {
                ScopedHandler scopedHandler = this._nextScope;
                if (scopedHandler == null || scopedHandler != this._handler) {
                    Handler handler = this._handler;
                    if (handler != null) {
                        handler.handle(str, request, httpServletRequest, httpServletResponse);
                    }
                } else {
                    scopedHandler.doHandle(str, request, httpServletRequest, httpServletResponse);
                }
            }
            if (!zTakeNewContext) {
                return;
            }
            if (this._requestListeners != null) {
                ServletRequestEvent servletRequestEvent3 = new ServletRequestEvent(this._scontext, httpServletRequest);
                int size5 = LazyList.size(this._requestListeners);
                while (true) {
                    int i6 = size5 - 1;
                    if (size5 <= 0) {
                        break;
                    }
                    ((ServletRequestListener) LazyList.get(this._requestListeners, i6)).requestDestroyed(servletRequestEvent3);
                    size5 = i6;
                }
            }
            Object obj4 = this._requestAttributeListeners;
            if (obj4 == null) {
                return;
            }
            int size6 = LazyList.size(obj4);
            while (true) {
                int i7 = size6 - 1;
                if (size6 <= 0) {
                    return;
                }
                request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i7));
                size6 = i7;
            }
        } catch (Throwable th) {
            if (zTakeNewContext) {
                if (this._requestListeners != null) {
                    ServletRequestEvent servletRequestEvent4 = new ServletRequestEvent(this._scontext, httpServletRequest);
                    int size7 = LazyList.size(this._requestListeners);
                    while (true) {
                        int i8 = size7 - 1;
                        if (size7 <= 0) {
                            break;
                        }
                        ((ServletRequestListener) LazyList.get(this._requestListeners, i8)).requestDestroyed(servletRequestEvent4);
                        size7 = i8;
                    }
                }
                Object obj5 = this._requestAttributeListeners;
                if (obj5 != null) {
                    int size8 = LazyList.size(obj5);
                    while (true) {
                        int i9 = size8 - 1;
                        if (size8 <= 0) {
                            break;
                        }
                        request.removeEventListener((EventListener) LazyList.get(this._requestAttributeListeners, i9));
                        size8 = i9;
                    }
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|(1:4)|5|(10:(23:7|(4:18|(1:20)(1:21)|22|(1:24)(22:25|(3:27|(1:29)|30)(2:31|(1:33)(1:34))|35|(1:37)|93|40|99|41|97|42|95|43|44|92|45|(3:49|(1:51)(1:52)|53)|54|(1:56)|57|(1:59)(2:60|(1:62)(2:63|(1:65)(1:66)))|67|(4:69|(1:71)|72|73)(1:101)))(1:16)|17|35|(0)(0)|93|40|99|41|97|42|95|43|44|92|45|(4:47|49|(0)(0)|53)|54|(0)|57|(0)(0)|67|(0)(0))(1:38)|92|45|(0)|54|(0)|57|(0)(0)|67|(0)(0))|39|93|40|99|41|97|42|95|43|44|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0164, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0165, code lost:
    
        r3 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0167, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0168, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x016b, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x016c, code lost:
    
        r3 = null;
        r9 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0171, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0172, code lost:
    
        r3 = null;
        r9 = null;
        r14 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00df A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ee A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f4 A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0106 A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x012c A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0130 A[Catch: all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:45:0x00d2, B:47:0x00df, B:49:0x00e5, B:51:0x00ee, B:53:0x00f9, B:52:0x00f4, B:54:0x0100, B:56:0x0106, B:57:0x0126, B:59:0x012c, B:60:0x0130, B:62:0x0134, B:63:0x0138, B:65:0x013c, B:66:0x0140), top: B:92:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0147  */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doScope(java.lang.String r18, org.eclipse.jetty.server.Request r19, javax.servlet.http.HttpServletRequest r20, javax.servlet.http.HttpServletResponse r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 403
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doScope(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x007f  */
    @Override // org.eclipse.jetty.server.handler.ScopedHandler, org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStart() throws java.lang.Exception {
        /*
            r5 = this;
            r0 = 0
            r5._availability = r0
            java.lang.String r0 = r5._contextPath
            if (r0 == 0) goto L83
            java.lang.String r0 = r5.getDisplayName()
            if (r0 != 0) goto L12
            java.lang.String r0 = r5.getContextPath()
            goto L16
        L12:
            java.lang.String r0 = r5.getDisplayName()
        L16:
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.util.log.Log.getLogger(r0)
            r5._logger = r0
            r0 = 0
            java.lang.ClassLoader r1 = r5._classLoader     // Catch: java.lang.Throwable -> L73
            if (r1 == 0) goto L32
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L73
            java.lang.ClassLoader r2 = r1.getContextClassLoader()     // Catch: java.lang.Throwable -> L2f
            java.lang.ClassLoader r3 = r5._classLoader     // Catch: java.lang.Throwable -> L71
            r1.setContextClassLoader(r3)     // Catch: java.lang.Throwable -> L71
            goto L34
        L2f:
            r3 = move-exception
            r2 = r0
            goto L76
        L32:
            r1 = r0
            r2 = r1
        L34:
            org.eclipse.jetty.http.MimeTypes r3 = r5._mimeTypes     // Catch: java.lang.Throwable -> L71
            if (r3 != 0) goto L3f
            org.eclipse.jetty.http.MimeTypes r3 = new org.eclipse.jetty.http.MimeTypes     // Catch: java.lang.Throwable -> L71
            r3.<init>()     // Catch: java.lang.Throwable -> L71
            r5._mimeTypes = r3     // Catch: java.lang.Throwable -> L71
        L3f:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r3 = org.eclipse.jetty.server.handler.ContextHandler.__context     // Catch: java.lang.Throwable -> L71
            java.lang.Object r4 = r3.get()     // Catch: java.lang.Throwable -> L71
            org.eclipse.jetty.server.handler.ContextHandler$Context r4 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r4     // Catch: java.lang.Throwable -> L71
            org.eclipse.jetty.server.handler.ContextHandler$Context r0 = r5._scontext     // Catch: java.lang.Throwable -> L6e
            r3.set(r0)     // Catch: java.lang.Throwable -> L6e
            r5.startContext()     // Catch: java.lang.Throwable -> L6e
            monitor-enter(r5)     // Catch: java.lang.Throwable -> L6e
            boolean r0 = r5._shutdown     // Catch: java.lang.Throwable -> L6b
            if (r0 == 0) goto L56
            r0 = 2
            goto L5d
        L56:
            boolean r0 = r5._available     // Catch: java.lang.Throwable -> L6b
            if (r0 == 0) goto L5c
            r0 = 1
            goto L5d
        L5c:
            r0 = 3
        L5d:
            r5._availability = r0     // Catch: java.lang.Throwable -> L6b
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6b
            r3.set(r4)
            java.lang.ClassLoader r0 = r5._classLoader
            if (r0 == 0) goto L6a
            r1.setContextClassLoader(r2)
        L6a:
            return
        L6b:
            r0 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6b
            throw r0     // Catch: java.lang.Throwable -> L6e
        L6e:
            r3 = move-exception
            r0 = r4
            goto L76
        L71:
            r3 = move-exception
            goto L76
        L73:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L76:
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r4 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r4.set(r0)
            java.lang.ClassLoader r0 = r5._classLoader
            if (r0 == 0) goto L82
            r1.setContextClassLoader(r2)
        L82:
            throw r3
        L83:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Null contextPath"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStart():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b4  */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r11 = this;
            java.lang.String r0 = "stopped {}"
            r1 = 0
            r11._availability = r1
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r2 = org.eclipse.jetty.server.handler.ContextHandler.__context
            java.lang.Object r3 = r2.get()
            org.eclipse.jetty.server.handler.ContextHandler$Context r3 = (org.eclipse.jetty.server.handler.ContextHandler.Context) r3
            org.eclipse.jetty.server.handler.ContextHandler$Context r4 = r11._scontext
            r2.set(r4)
            r2 = 1
            r4 = 0
            java.lang.ClassLoader r5 = r11._classLoader     // Catch: java.lang.Throwable -> L9e
            if (r5 == 0) goto L2c
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L9e
            java.lang.ClassLoader r6 = r5.getContextClassLoader()     // Catch: java.lang.Throwable -> L26
            java.lang.ClassLoader r7 = r11._classLoader     // Catch: java.lang.Throwable -> L9c
            r5.setContextClassLoader(r7)     // Catch: java.lang.Throwable -> L9c
            goto L2e
        L26:
            r6 = move-exception
            r10 = r6
            r6 = r4
            r4 = r10
            goto La2
        L2c:
            r5 = r4
            r6 = r5
        L2e:
            super.doStop()     // Catch: java.lang.Throwable -> L9c
            java.lang.Object r7 = r11._contextListeners     // Catch: java.lang.Throwable -> L9c
            if (r7 == 0) goto L53
            javax.servlet.ServletContextEvent r7 = new javax.servlet.ServletContextEvent     // Catch: java.lang.Throwable -> L9c
            org.eclipse.jetty.server.handler.ContextHandler$Context r8 = r11._scontext     // Catch: java.lang.Throwable -> L9c
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L9c
            java.lang.Object r8 = r11._contextListeners     // Catch: java.lang.Throwable -> L9c
            int r8 = org.eclipse.jetty.util.LazyList.size(r8)     // Catch: java.lang.Throwable -> L9c
        L42:
            int r9 = r8 + (-1)
            if (r8 <= 0) goto L53
            java.lang.Object r8 = r11._contextListeners     // Catch: java.lang.Throwable -> L9c
            java.lang.Object r8 = org.eclipse.jetty.util.LazyList.get(r8, r9)     // Catch: java.lang.Throwable -> L9c
            javax.servlet.ServletContextListener r8 = (javax.servlet.ServletContextListener) r8     // Catch: java.lang.Throwable -> L9c
            r8.contextDestroyed(r7)     // Catch: java.lang.Throwable -> L9c
            r8 = r9
            goto L42
        L53:
            java.lang.Object r7 = r11._durableListeners     // Catch: java.lang.Throwable -> L9c
            java.lang.Class<java.util.EventListener> r8 = java.util.EventListener.class
            java.lang.Object r7 = org.eclipse.jetty.util.LazyList.toArray(r7, r8)     // Catch: java.lang.Throwable -> L9c
            java.util.EventListener[] r7 = (java.util.EventListener[]) r7     // Catch: java.lang.Throwable -> L9c
            java.util.EventListener[] r7 = (java.util.EventListener[]) r7     // Catch: java.lang.Throwable -> L9c
            r11.setEventListeners(r7)     // Catch: java.lang.Throwable -> L9c
            r11._durableListeners = r4     // Catch: java.lang.Throwable -> L9c
            org.eclipse.jetty.server.handler.ErrorHandler r7 = r11._errorHandler     // Catch: java.lang.Throwable -> L9c
            if (r7 == 0) goto L6b
            r7.stop()     // Catch: java.lang.Throwable -> L9c
        L6b:
            org.eclipse.jetty.server.handler.ContextHandler$Context r7 = r11._scontext     // Catch: java.lang.Throwable -> L9c
            java.util.Enumeration r7 = r7.getAttributeNames()     // Catch: java.lang.Throwable -> L9c
        L71:
            boolean r8 = r7.hasMoreElements()     // Catch: java.lang.Throwable -> L9c
            if (r8 == 0) goto L81
            java.lang.Object r8 = r7.nextElement()     // Catch: java.lang.Throwable -> L9c
            java.lang.String r8 = (java.lang.String) r8     // Catch: java.lang.Throwable -> L9c
            r11.checkManagedAttribute(r8, r4)     // Catch: java.lang.Throwable -> L9c
            goto L71
        L81:
            org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.server.handler.ContextHandler.LOG
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r1] = r11
            r4.info(r0, r2)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r0.set(r3)
            java.lang.ClassLoader r0 = r11._classLoader
            if (r0 == 0) goto L96
            r5.setContextClassLoader(r6)
        L96:
            org.eclipse.jetty.util.AttributesMap r0 = r11._contextAttributes
            r0.clearAttributes()
            return
        L9c:
            r4 = move-exception
            goto La2
        L9e:
            r5 = move-exception
            r6 = r4
            r4 = r5
            r5 = r6
        La2:
            org.eclipse.jetty.util.log.Logger r7 = org.eclipse.jetty.server.handler.ContextHandler.LOG
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r1] = r11
            r7.info(r0, r2)
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ContextHandler$Context> r0 = org.eclipse.jetty.server.handler.ContextHandler.__context
            r0.set(r3)
            java.lang.ClassLoader r0 = r11._classLoader
            if (r0 == 0) goto Lb7
            r5.setContextClassLoader(r6)
        Lb7:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ContextHandler.doStop():void");
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        AggregateLifeCycle.dump(appendable, str, Collections.singletonList(new CLDump(getClassLoader())), TypeUtil.asList(getHandlers()), getBeans(), this._initParams.entrySet(), this._attributes.getAttributeEntrySet(), this._contextAttributes.getAttributeEntrySet());
    }

    public List<AliasCheck> getAliasChecks() {
        return this._aliasChecks;
    }

    public boolean getAllowNullPathInfo() {
        return this._allowNullPathInfo;
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Object getAttribute(String str) {
        return this._attributes.getAttribute(str);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public Enumeration getAttributeNames() {
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        return this._attributes;
    }

    public Resource getBaseResource() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource;
    }

    public ClassLoader getClassLoader() {
        return this._classLoader;
    }

    public String getClassPath() {
        ClassLoader classLoader = this._classLoader;
        if (classLoader == null || !(classLoader instanceof URLClassLoader)) {
            return null;
        }
        URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
        StringBuilder sb = new StringBuilder();
        for (URL url : uRLs) {
            try {
                File file = newResource(url).getFile();
                if (file != null && file.exists()) {
                    if (sb.length() > 0) {
                        sb.append(File.pathSeparatorChar);
                    }
                    sb.append(file.getAbsolutePath());
                }
            } catch (IOException e2) {
                LOG.debug(e2);
            }
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String[] getConnectorNames() {
        Set<String> set = this._connectors;
        if (set == null || set.size() == 0) {
            return null;
        }
        Set<String> set2 = this._connectors;
        return (String[]) set2.toArray(new String[set2.size()]);
    }

    public String getContextPath() {
        return this._contextPath;
    }

    public String getDisplayName() {
        return this._displayName;
    }

    public ErrorHandler getErrorHandler() {
        return this._errorHandler;
    }

    public EventListener[] getEventListeners() {
        return this._eventListeners;
    }

    public String getInitParameter(String str) {
        return this._initParams.get(str);
    }

    public Enumeration getInitParameterNames() {
        return Collections.enumeration(this._initParams.keySet());
    }

    public Map<String, String> getInitParams() {
        return this._initParams;
    }

    public String getLocaleEncoding(String str) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Logger getLogger() {
        return this._logger;
    }

    public int getMaxFormContentSize() {
        return this._maxFormContentSize;
    }

    public int getMaxFormKeys() {
        return this._maxFormKeys;
    }

    public MimeTypes getMimeTypes() {
        if (this._mimeTypes == null) {
            this._mimeTypes = new MimeTypes();
        }
        return this._mimeTypes;
    }

    public String[] getProtectedTargets() {
        String[] strArr = this._protectedTargets;
        if (strArr == null) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    public Resource getResource(String str) throws MalformedURLException {
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        }
        if (this._baseResource == null) {
            return null;
        }
        try {
            String strCanonicalPath = URIUtil.canonicalPath(str);
            Resource resourceAddPath = this._baseResource.addPath(strCanonicalPath);
            if (this._aliases || resourceAddPath.getAlias() == null) {
                return resourceAddPath;
            }
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Aliased resource: " + resourceAddPath + "~=" + resourceAddPath.getAlias(), new Object[0]);
            }
            Iterator<AliasCheck> it = this._aliasChecks.iterator();
            while (it.hasNext()) {
                AliasCheck next = it.next();
                if (next.check(strCanonicalPath, resourceAddPath)) {
                    Logger logger2 = LOG;
                    if (logger2.isDebugEnabled()) {
                        logger2.debug("Aliased resource: " + resourceAddPath + " approved by " + next, new Object[0]);
                    }
                    return resourceAddPath;
                }
            }
            return null;
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

    public Set<String> getResourcePaths(String str) {
        try {
            String strCanonicalPath = URIUtil.canonicalPath(str);
            Resource resource = getResource(strCanonicalPath);
            if (resource != null && resource.exists()) {
                if (!strCanonicalPath.endsWith("/")) {
                    strCanonicalPath = strCanonicalPath + "/";
                }
                String[] list = resource.list();
                if (list != null) {
                    HashSet hashSet = new HashSet();
                    for (String str2 : list) {
                        hashSet.add(strCanonicalPath + str2);
                    }
                    return hashSet;
                }
            }
        } catch (Exception e2) {
            LOG.ignore(e2);
        }
        return Collections.emptySet();
    }

    public Context getServletContext() {
        return this._scontext;
    }

    public String[] getVirtualHosts() {
        return this._vhosts;
    }

    public String[] getWelcomeFiles() {
        return this._welcomeFiles;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object, org.eclipse.jetty.server.handler.ContextHandler$Context] */
    public void handle(Runnable runnable) throws Throwable {
        ClassLoader classLoader;
        Thread threadCurrentThread;
        ClassLoader contextClassLoader = null;
        try {
            ThreadLocal threadLocal = __context;
            ?? r2 = (Context) threadLocal.get();
            try {
                threadLocal.set(this._scontext);
                if (this._classLoader != null) {
                    threadCurrentThread = Thread.currentThread();
                    try {
                        contextClassLoader = threadCurrentThread.getContextClassLoader();
                        threadCurrentThread.setContextClassLoader(this._classLoader);
                    } catch (Throwable th) {
                        th = th;
                        classLoader = contextClassLoader;
                        contextClassLoader = r2;
                        __context.set(contextClassLoader);
                        if (classLoader != null) {
                            threadCurrentThread.setContextClassLoader(classLoader);
                        }
                        throw th;
                    }
                } else {
                    threadCurrentThread = null;
                }
                runnable.run();
                threadLocal.set(r2);
                if (contextClassLoader != null) {
                    threadCurrentThread.setContextClassLoader(contextClassLoader);
                }
            } catch (Throwable th2) {
                th = th2;
                classLoader = null;
                threadCurrentThread = null;
            }
        } catch (Throwable th3) {
            th = th3;
            classLoader = null;
            threadCurrentThread = null;
        }
    }

    public boolean isAliases() {
        return this._aliases;
    }

    public boolean isAvailable() {
        boolean z2;
        synchronized (this) {
            z2 = this._available;
        }
        return z2;
    }

    public boolean isCompactPath() {
        return this._compactPath;
    }

    public boolean isProtectedTarget(String str) {
        boolean z2 = false;
        if (str != null && this._protectedTargets != null) {
            while (str.startsWith("//")) {
                str = URIUtil.compactPath(str);
            }
            int i2 = 0;
            while (!z2) {
                String[] strArr = this._protectedTargets;
                if (i2 >= strArr.length) {
                    break;
                }
                int i3 = i2 + 1;
                boolean zStartsWithIgnoreCase = StringUtil.startsWithIgnoreCase(str, strArr[i2]);
                i2 = i3;
                z2 = zStartsWithIgnoreCase;
            }
        }
        return z2;
    }

    public boolean isShutdown() {
        boolean z2;
        synchronized (this) {
            z2 = !this._shutdown;
        }
        return z2;
    }

    public synchronized Class<?> loadClass(String str) throws ClassNotFoundException {
        if (str == null) {
            return null;
        }
        ClassLoader classLoader = this._classLoader;
        if (classLoader == null) {
            return Loader.loadClass(getClass(), str);
        }
        return classLoader.loadClass(str);
    }

    public Resource newResource(URL url) throws IOException {
        return Resource.newResource(url);
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void removeAttribute(String str) {
        checkManagedAttribute(str, null);
        this._attributes.removeAttribute(str);
    }

    public void removeVirtualHosts(String[] strArr) {
        String[] strArr2;
        if (strArr == null || (strArr2 = this._vhosts) == null || strArr2.length == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(this._vhosts));
        for (String str : strArr) {
            String strNormalizeHostname = normalizeHostname(str);
            if (arrayList.contains(strNormalizeHostname)) {
                arrayList.remove(strNormalizeHostname);
            }
        }
        if (arrayList.isEmpty()) {
            this._vhosts = null;
        } else {
            this._vhosts = (String[]) arrayList.toArray(new String[0]);
        }
    }

    public void restrictEventListener(EventListener eventListener) {
    }

    public void setAliases(boolean z2) {
        this._aliases = z2;
    }

    public void setAllowNullPathInfo(boolean z2) {
        this._allowNullPathInfo = z2;
    }

    @Override // org.eclipse.jetty.util.Attributes
    public void setAttribute(String str, Object obj) {
        checkManagedAttribute(str, obj);
        this._attributes.setAttribute(str, obj);
    }

    public void setAttributes(Attributes attributes) {
        this._attributes.clearAttributes();
        this._attributes.addAll(attributes);
        Enumeration<String> attributeNames = this._attributes.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String strNextElement = attributeNames.nextElement();
            checkManagedAttribute(strNextElement, attributes.getAttribute(strNextElement));
        }
    }

    public void setAvailable(boolean z2) {
        synchronized (this) {
            this._available = z2;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public void setBaseResource(Resource resource) {
        this._baseResource = resource;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this._classLoader = classLoader;
    }

    public void setCompactPath(boolean z2) {
        this._compactPath = z2;
    }

    public void setConnectorNames(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            this._connectors = null;
        } else {
            this._connectors = new HashSet(Arrays.asList(strArr));
        }
    }

    public void setContextPath(String str) {
        if (str != null && str.length() > 1 && str.endsWith("/")) {
            throw new IllegalArgumentException("ends with /");
        }
        this._contextPath = str;
        if (getServer() != null) {
            if (getServer().isStarting() || getServer().isStarted()) {
                Handler[] childHandlersByClass = getServer().getChildHandlersByClass(ContextHandlerCollection.class);
                for (int i2 = 0; childHandlersByClass != null && i2 < childHandlersByClass.length; i2++) {
                    ((ContextHandlerCollection) childHandlersByClass[i2]).mapContexts();
                }
            }
        }
    }

    public void setDisplayName(String str) {
        this._displayName = str;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler != null) {
            errorHandler.setServer(getServer());
        }
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) this._errorHandler, (Object) errorHandler, "errorHandler", true);
        }
        this._errorHandler = errorHandler;
    }

    public void setEventListeners(EventListener[] eventListenerArr) {
        this._contextListeners = null;
        this._contextAttributeListeners = null;
        this._requestListeners = null;
        this._requestAttributeListeners = null;
        this._eventListeners = eventListenerArr;
        for (int i2 = 0; eventListenerArr != null && i2 < eventListenerArr.length; i2++) {
            EventListener eventListener = this._eventListeners[i2];
            if (eventListener instanceof ServletContextListener) {
                this._contextListeners = LazyList.add(this._contextListeners, eventListener);
            }
            if (eventListener instanceof ServletContextAttributeListener) {
                this._contextAttributeListeners = LazyList.add(this._contextAttributeListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestListener) {
                this._requestListeners = LazyList.add(this._requestListeners, eventListener);
            }
            if (eventListener instanceof ServletRequestAttributeListener) {
                this._requestAttributeListeners = LazyList.add(this._requestAttributeListeners, eventListener);
            }
        }
    }

    public String setInitParameter(String str, String str2) {
        return this._initParams.put(str, str2);
    }

    public void setLogger(Logger logger) {
        this._logger = logger;
    }

    public void setManagedAttribute(String str, Object obj) {
        getServer().getContainer().update((Object) this, this._managedAttributes.put(str, obj), obj, str, true);
    }

    public void setMaxFormContentSize(int i2) {
        this._maxFormContentSize = i2;
    }

    public void setMaxFormKeys(int i2) {
        this._maxFormKeys = i2;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public void setProtectedTargets(String[] strArr) {
        if (strArr == null) {
            this._protectedTargets = null;
            return;
        }
        String[] strArr2 = new String[strArr.length];
        this._protectedTargets = strArr2;
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
    }

    public void setResourceBase(String str) {
        try {
            setBaseResource(newResource(str));
        } catch (Exception e2) {
            Logger logger = LOG;
            logger.warn(e2.toString(), new Object[0]);
            logger.debug(e2);
            throw new IllegalArgumentException(str);
        }
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        if (this._errorHandler == null) {
            super.setServer(server);
            return;
        }
        Server server2 = getServer();
        if (server2 != null && server2 != server) {
            server2.getContainer().update((Object) this, (Object) this._errorHandler, (Object) null, "error", true);
        }
        super.setServer(server);
        if (server != null && server != server2) {
            server.getContainer().update((Object) this, (Object) null, (Object) this._errorHandler, "error", true);
        }
        this._errorHandler.setServer(server);
    }

    @Override // org.eclipse.jetty.server.Server.Graceful
    public void setShutdown(boolean z2) {
        synchronized (this) {
            this._shutdown = z2;
            this._availability = isRunning() ? this._shutdown ? 2 : this._available ? 1 : 3 : 0;
        }
    }

    public void setVirtualHosts(String[] strArr) {
        if (strArr == null) {
            this._vhosts = strArr;
            return;
        }
        this._vhosts = new String[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            this._vhosts[i2] = normalizeHostname(strArr[i2]);
        }
    }

    public void setWelcomeFiles(String[] strArr) {
        this._welcomeFiles = strArr;
    }

    public void startContext() throws Exception {
        String str = this._initParams.get(MANAGED_ATTRIBUTES);
        if (str != null) {
            this._managedAttributes = new HashMap();
            for (String str2 : str.split(",")) {
                this._managedAttributes.put(str2, null);
            }
            Enumeration attributeNames = this._scontext.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String str3 = (String) attributeNames.nextElement();
                checkManagedAttribute(str3, this._scontext.getAttribute(str3));
            }
        }
        super.doStart();
        ErrorHandler errorHandler = this._errorHandler;
        if (errorHandler != null) {
            errorHandler.start();
        }
        if (this._contextListeners != null) {
            ServletContextEvent servletContextEvent = new ServletContextEvent(this._scontext);
            for (int i2 = 0; i2 < LazyList.size(this._contextListeners); i2++) {
                callContextInitialized((ServletContextListener) LazyList.get(this._contextListeners, i2), servletContextEvent);
            }
        }
    }

    public String toString() {
        String name;
        String[] virtualHosts = getVirtualHosts();
        StringBuilder sb = new StringBuilder();
        Package r2 = getClass().getPackage();
        if (r2 != null && (name = r2.getName()) != null && name.length() > 0) {
            for (String str : name.split("\\.")) {
                sb.append(str.charAt(0));
                sb.append('.');
            }
        }
        sb.append(getClass().getSimpleName());
        sb.append('{');
        sb.append(getContextPath());
        sb.append(',');
        sb.append(getBaseResource());
        if (virtualHosts != null && virtualHosts.length > 0) {
            sb.append(',');
            sb.append(virtualHosts[0]);
        }
        sb.append('}');
        return sb.toString();
    }

    public Resource newResource(String str) throws IOException {
        return Resource.newResource(str);
    }

    public String getLocaleEncoding(Locale locale) {
        Map<String, String> map = this._localeEncodingMap;
        if (map == null) {
            return null;
        }
        String str = map.get(locale.toString());
        return str == null ? this._localeEncodingMap.get(locale.getLanguage()) : str;
    }

    public ContextHandler(Context context) {
        this._contextPath = "/";
        this._maxFormKeys = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormKeys", -1).intValue();
        this._maxFormContentSize = Integer.getInteger("org.eclipse.jetty.server.Request.maxFormContentSize", -1).intValue();
        this._compactPath = false;
        this._aliases = false;
        this._aliasChecks = new CopyOnWriteArrayList<>();
        this._shutdown = false;
        this._available = true;
        this._scontext = context;
        this._attributes = new AttributesMap();
        this._contextAttributes = new AttributesMap();
        this._initParams = new HashMap();
        addAliasCheck(new ApproveNonExistentDirectoryAliases());
    }

    public ContextHandler(String str) {
        this();
        setContextPath(str);
    }

    public ContextHandler(HandlerContainer handlerContainer, String str) {
        this();
        setContextPath(str);
        if (handlerContainer instanceof HandlerWrapper) {
            ((HandlerWrapper) handlerContainer).setHandler(this);
        } else if (handlerContainer instanceof HandlerCollection) {
            ((HandlerCollection) handlerContainer).addHandler(this);
        }
    }
}
