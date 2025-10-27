package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.RunAsToken;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class ServletHolder extends Holder<Servlet> implements UserIdentity.Scope, Comparable {
    private static final Logger LOG = Log.getLogger((Class<?>) ServletHolder.class);
    public static final Map<String, String> NO_MAPPED_ROLES = Collections.emptyMap();
    private transient Config _config;
    private transient boolean _enabled;
    private String _forcedPath;
    private IdentityService _identityService;
    private boolean _initOnStartup;
    private int _initOrder;
    private ServletRegistration.Dynamic _registration;
    private Map<String, String> _roleMap;
    private String _runAsRole;
    private RunAsToken _runAsToken;
    private transient Servlet _servlet;
    private transient long _unavailable;
    private transient UnavailableException _unavailableEx;

    public class Config extends Holder<Servlet>.HolderConfig implements ServletConfig {
        public Config() {
            super();
        }

        public String getServletName() {
            return ServletHolder.this.getName();
        }
    }

    public class Registration extends Holder<Servlet>.HolderRegistration implements ServletRegistration.Dynamic {
        protected MultipartConfigElement _multipartConfig;

        public Registration() {
            super();
        }

        public Set<String> addMapping(String... strArr) {
            ServletHolder.this.illegalStateIfContextStarted();
            HashSet hashSet = null;
            for (String str : strArr) {
                ServletMapping servletMapping = ServletHolder.this._servletHandler.getServletMapping(str);
                if (servletMapping != null && !servletMapping.isDefault()) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(str);
                }
            }
            if (hashSet != null) {
                return hashSet;
            }
            ServletMapping servletMapping2 = new ServletMapping();
            servletMapping2.setServletName(ServletHolder.this.getName());
            servletMapping2.setPathSpecs(strArr);
            ServletHolder.this._servletHandler.addServletMapping(servletMapping2);
            return Collections.emptySet();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ String getClassName() {
            return super.getClassName();
        }

        public int getInitOrder() {
            return ServletHolder.this.getInitOrder();
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ String getInitParameter(String str) {
            return super.getInitParameter(str);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ Map getInitParameters() {
            return super.getInitParameters();
        }

        public Collection<String> getMappings() {
            String[] pathSpecs;
            ServletMapping[] servletMappings = ServletHolder.this._servletHandler.getServletMappings();
            ArrayList arrayList = new ArrayList();
            if (servletMappings != null) {
                for (ServletMapping servletMapping : servletMappings) {
                    if (servletMapping.getServletName().equals(getName()) && (pathSpecs = servletMapping.getPathSpecs()) != null && pathSpecs.length > 0) {
                        arrayList.addAll(Arrays.asList(pathSpecs));
                    }
                }
            }
            return arrayList;
        }

        public MultipartConfigElement getMultipartConfig() {
            return this._multipartConfig;
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        public String getRunAsRole() {
            return ServletHolder.this._runAsRole;
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ void setAsyncSupported(boolean z2) {
            super.setAsyncSupported(z2);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ void setDescription(String str) {
            super.setDescription(str);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ boolean setInitParameter(String str, String str2) {
            return super.setInitParameter(str, str2);
        }

        @Override // org.eclipse.jetty.servlet.Holder.HolderRegistration
        public /* bridge */ /* synthetic */ Set setInitParameters(Map map) {
            return super.setInitParameters(map);
        }

        public void setLoadOnStartup(int i2) {
            ServletHolder.this.illegalStateIfContextStarted();
            ServletHolder.this.setInitOrder(i2);
        }

        public void setMultipartConfig(MultipartConfigElement multipartConfigElement) {
            this._multipartConfig = multipartConfigElement;
        }

        public void setRunAsRole(String str) {
            ServletHolder.this._runAsRole = str;
        }

        public Set<String> setServletSecurity(ServletSecurityElement servletSecurityElement) {
            return ServletHolder.this._servletHandler.setServletSecurity(this, servletSecurityElement);
        }
    }

    public class SingleThreadedWrapper implements Servlet {
        Stack<Servlet> _stack;

        private SingleThreadedWrapper() {
            this._stack = new Stack<>();
        }

        public void destroy() {
            synchronized (this) {
                while (this._stack.size() > 0) {
                    try {
                        this._stack.pop().destroy();
                    } catch (Exception e2) {
                        ServletHolder.LOG.warn(e2);
                    }
                }
            }
        }

        public ServletConfig getServletConfig() {
            return ServletHolder.this._config;
        }

        public String getServletInfo() {
            return null;
        }

        public void init(ServletConfig servletConfig) throws ServletException {
            synchronized (this) {
                if (this._stack.size() == 0) {
                    try {
                        Servlet servletNewInstance = ServletHolder.this.newInstance();
                        servletNewInstance.init(servletConfig);
                        this._stack.push(servletNewInstance);
                    } catch (ServletException e2) {
                        throw e2;
                    } catch (Exception e3) {
                        throw new ServletException(e3);
                    }
                }
            }
        }

        public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
            Servlet servletNewInstance;
            synchronized (this) {
                if (this._stack.size() > 0) {
                    servletNewInstance = this._stack.pop();
                } else {
                    try {
                        servletNewInstance = ServletHolder.this.newInstance();
                        servletNewInstance.init(ServletHolder.this._config);
                    } catch (Exception e2) {
                        throw new ServletException(e2);
                    } catch (ServletException e3) {
                        throw e3;
                    }
                }
            }
            try {
                servletNewInstance.service(servletRequest, servletResponse);
                synchronized (this) {
                    this._stack.push(servletNewInstance);
                }
            } catch (Throwable th) {
                synchronized (this) {
                    this._stack.push(servletNewInstance);
                    throw th;
                }
            }
        }
    }

    public ServletHolder() {
        this(Holder.Source.EMBEDDED);
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x008b: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]) (LINE:140), block:B:49:0x008b */
    private void initServlet() throws Throwable {
        Object obj;
        Object runAs;
        Object obj2 = null;
        try {
            try {
                if (this._servlet == null) {
                    this._servlet = newInstance();
                }
                if (this._config == null) {
                    this._config = new Config();
                }
                IdentityService identityService = this._identityService;
                runAs = identityService != null ? identityService.setRunAs(identityService.getSystemUserIdentity(), this._runAsToken) : null;
            } catch (UnavailableException e2) {
                e = e2;
            } catch (ServletException e3) {
                e = e3;
            } catch (Exception e4) {
                e = e4;
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (isJspServlet()) {
                    initJspServlet();
                }
                initMultiPart();
                this._servlet.init(this._config);
                IdentityService identityService2 = this._identityService;
                if (identityService2 != null) {
                    identityService2.unsetRunAs(runAs);
                }
            } catch (UnavailableException e5) {
                e = e5;
                makeUnavailable(e);
                this._servlet = null;
                this._config = null;
                throw e;
            } catch (ServletException e6) {
                e = e6;
                makeUnavailable((Throwable) (e.getCause() == null ? e : e.getCause()));
                this._servlet = null;
                this._config = null;
                throw e;
            } catch (Exception e7) {
                e = e7;
                makeUnavailable(e);
                this._servlet = null;
                this._config = null;
                throw new ServletException(toString(), e);
            } catch (Throwable th2) {
                Object obj3 = runAs;
                th = th2;
                obj2 = obj3;
                IdentityService identityService3 = this._identityService;
                if (identityService3 != null) {
                    identityService3.unsetRunAs(obj2);
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            obj2 = obj;
        }
    }

    private boolean isJspServlet() {
        Servlet servlet = this._servlet;
        boolean zIsJspServlet = false;
        if (servlet == null) {
            return false;
        }
        for (Class<?> superclass = servlet.getClass(); superclass != null && !zIsJspServlet; superclass = superclass.getSuperclass()) {
            zIsJspServlet = isJspServlet(superclass.getName());
        }
        return zIsJspServlet;
    }

    private void makeUnavailable(UnavailableException unavailableException) {
        if (this._unavailableEx != unavailableException || this._unavailable == 0) {
            this._servletHandler.getServletContext().log("unavailable", unavailableException);
            this._unavailableEx = unavailableException;
            this._unavailable = -1L;
            if (unavailableException.isPermanent()) {
                this._unavailable = -1L;
            } else if (this._unavailableEx.getUnavailableSeconds() > 0) {
                this._unavailable = System.currentTimeMillis() + (this._unavailableEx.getUnavailableSeconds() * 1000);
            } else {
                this._unavailable = System.currentTimeMillis() + 5000;
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.UnavailableException */
    public void checkServletType() throws UnavailableException {
        Class<? extends T> cls = this._class;
        if (cls == 0 || !Servlet.class.isAssignableFrom(cls)) {
            throw new UnavailableException("Servlet " + this._class + " is not a javax.servlet.Servlet");
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        String str;
        if (!(obj instanceof ServletHolder)) {
            return 1;
        }
        ServletHolder servletHolder = (ServletHolder) obj;
        int iCompareTo = 0;
        if (servletHolder == this) {
            return 0;
        }
        int i2 = servletHolder._initOrder;
        int i3 = this._initOrder;
        if (i2 < i3) {
            return 1;
        }
        if (i2 > i3) {
            return -1;
        }
        String str2 = this._className;
        if (str2 != null && (str = servletHolder._className) != null) {
            iCompareTo = str2.compareTo(str);
        }
        if (iCompareTo == 0) {
            iCompareTo = this._name.compareTo(servletHolder._name);
        }
        if (iCompareTo == 0) {
            return hashCode() <= obj.hashCode() ? -1 : 1;
        }
        return iCompareTo;
    }

    @Override // org.eclipse.jetty.servlet.Holder
    public void destroyInstance(Object obj) throws Exception {
        if (obj == null) {
            return;
        }
        Servlet servlet = (Servlet) obj;
        getServletHandler().destroyServlet(servlet);
        servlet.destroy();
    }

    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        String str;
        this._unavailable = 0L;
        if (this._enabled) {
            try {
                super.doStart();
                try {
                    checkServletType();
                    IdentityService identityService = this._servletHandler.getIdentityService();
                    this._identityService = identityService;
                    if (identityService != null && (str = this._runAsRole) != null) {
                        this._runAsToken = identityService.newRunAsToken(str);
                    }
                    this._config = new Config();
                    Class<? extends T> cls = this._class;
                    if (cls != 0 && SingleThreadModel.class.isAssignableFrom(cls)) {
                        this._servlet = new SingleThreadedWrapper();
                    }
                    if (this._extInstance || this._initOnStartup) {
                        try {
                            initServlet();
                        } catch (Exception e2) {
                            if (!this._servletHandler.isStartWithUnavailable()) {
                                throw e2;
                            }
                            LOG.ignore(e2);
                        }
                    }
                } catch (UnavailableException e3) {
                    makeUnavailable((UnavailableException) e3);
                    if (!this._servletHandler.isStartWithUnavailable()) {
                        throw e3;
                    }
                    LOG.ignore(e3);
                }
            } catch (UnavailableException e4) {
                makeUnavailable((UnavailableException) e4);
                if (!this._servletHandler.isStartWithUnavailable()) {
                    throw e4;
                }
                LOG.ignore(e4);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x004b  */
    @Override // org.eclipse.jetty.servlet.Holder, org.eclipse.jetty.util.component.AbstractLifeCycle
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void doStop() throws java.lang.Exception {
        /*
            r5 = this;
            javax.servlet.Servlet r0 = r5._servlet
            r1 = 0
            if (r0 == 0) goto L47
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            if (r0 == 0) goto L14
            org.eclipse.jetty.server.UserIdentity r2 = r0.getSystemUserIdentity()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            org.eclipse.jetty.security.RunAsToken r3 = r5._runAsToken     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.lang.Object r0 = r0.setRunAs(r2, r3)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            goto L15
        L14:
            r0 = r1
        L15:
            javax.servlet.Servlet r2 = r5._servlet     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L27
            r5.destroyInstance(r2)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L27
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L47
            r2.unsetRunAs(r0)
            goto L47
        L22:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L3f
        L27:
            r2 = move-exception
            r4 = r2
            r2 = r0
            r0 = r4
            goto L30
        L2c:
            r0 = move-exception
            goto L3f
        L2e:
            r0 = move-exception
            r2 = r1
        L30:
            org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.servlet.ServletHolder.LOG     // Catch: java.lang.Throwable -> L3d
            r3.warn(r0)     // Catch: java.lang.Throwable -> L3d
            org.eclipse.jetty.security.IdentityService r0 = r5._identityService
            if (r0 == 0) goto L47
            r0.unsetRunAs(r2)
            goto L47
        L3d:
            r0 = move-exception
            r1 = r2
        L3f:
            org.eclipse.jetty.security.IdentityService r2 = r5._identityService
            if (r2 == 0) goto L46
            r2.unsetRunAs(r1)
        L46:
            throw r0
        L47:
            boolean r0 = r5._extInstance
            if (r0 != 0) goto L4d
            r5._servlet = r1
        L4d:
            r5._config = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.ServletHolder.doStop():void");
    }

    public boolean equals(Object obj) {
        return compareTo(obj) == 0;
    }

    @Override // org.eclipse.jetty.server.UserIdentity.Scope
    public String getContextPath() {
        return this._config.getServletContext().getContextPath();
    }

    public String getForcedPath() {
        return this._forcedPath;
    }

    public int getInitOrder() {
        return this._initOrder;
    }

    public ServletRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    public Map<String, String> getRoleMap() {
        Map<String, String> map = this._roleMap;
        return map == null ? NO_MAPPED_ROLES : map;
    }

    @Override // org.eclipse.jetty.server.UserIdentity.Scope
    public Map<String, String> getRoleRefMap() {
        return this._roleMap;
    }

    public String getRunAsRole() {
        return this._runAsRole;
    }

    public synchronized Servlet getServlet() throws ServletException {
        long j2 = this._unavailable;
        if (j2 != 0) {
            if (j2 < 0 || (j2 > 0 && System.currentTimeMillis() < this._unavailable)) {
                throw this._unavailableEx;
            }
            this._unavailable = 0L;
            this._unavailableEx = null;
        }
        if (this._servlet == null) {
            initServlet();
        }
        return this._servlet;
    }

    public Servlet getServletInstance() {
        return this._servlet;
    }

    public UnavailableException getUnavailableException() {
        return this._unavailableEx;
    }

    public String getUserRoleLink(String str) {
        String str2;
        Map<String, String> map = this._roleMap;
        return (map == null || (str2 = map.get(str)) == null) ? str : str2;
    }

    public void handle(Request request, ServletRequest servletRequest, ServletResponse servletResponse) throws UnavailableException, ServletException, IOException {
        if (this._class == null) {
            throw new UnavailableException("Servlet Not Initialized");
        }
        Servlet servlet = this._servlet;
        synchronized (this) {
            if (!isStarted()) {
                throw new UnavailableException("Servlet not initialized", -1);
            }
            if (this._unavailable != 0 || !this._initOnStartup) {
                servlet = getServlet();
            }
            if (servlet == null) {
                throw new UnavailableException("Could not instantiate " + this._class);
            }
        }
        boolean zIsAsyncSupported = request.isAsyncSupported();
        try {
            try {
                String str = this._forcedPath;
                if (str != null) {
                    servletRequest.setAttribute(Dispatcher.__JSP_FILE, str);
                }
                IdentityService identityService = this._identityService;
                runAs = identityService != null ? identityService.setRunAs(request.getResolvedUserIdentity(), this._runAsToken) : null;
                if (!isAsyncSupported()) {
                    request.setAsyncSupported(false);
                }
                MultipartConfigElement multipartConfig = ((Registration) getRegistration()).getMultipartConfig();
                if (multipartConfig != null) {
                    servletRequest.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, multipartConfig);
                }
                servlet.service(servletRequest, servletResponse);
                request.setAsyncSupported(zIsAsyncSupported);
                IdentityService identityService2 = this._identityService;
                if (identityService2 != null) {
                    identityService2.unsetRunAs(runAs);
                }
            } catch (UnavailableException e2) {
                makeUnavailable(e2);
                throw this._unavailableEx;
            }
        } catch (Throwable th) {
            request.setAsyncSupported(zIsAsyncSupported);
            IdentityService identityService3 = this._identityService;
            if (identityService3 != null) {
                identityService3.unsetRunAs(runAs);
            }
            servletRequest.setAttribute("javax.servlet.error.servlet_name", getName());
            throw th;
        }
    }

    public int hashCode() {
        String str = this._name;
        return str == null ? System.identityHashCode(this) : str.hashCode();
    }

    public void initJspServlet() throws Exception {
        ContextHandler contextHandler = ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler();
        contextHandler.setAttribute("org.apache.catalina.jsp_classpath", contextHandler.getClassPath());
        setInitParameter("com.sun.appserv.jsp.classpath", Loader.getClassPath(contextHandler.getClassLoader().getParent()));
        if ("?".equals(getInitParameter("classpath"))) {
            String classPath = contextHandler.getClassPath();
            LOG.debug("classpath=" + classPath, new Object[0]);
            if (classPath != null) {
                setInitParameter("classpath", classPath);
            }
        }
    }

    public void initMultiPart() throws Exception {
        if (((Registration) getRegistration()).getMultipartConfig() != null) {
            ((ContextHandler.Context) getServletHandler().getServletContext()).getContextHandler().addEventListener(new Request.MultiPartCleanerListener());
        }
    }

    public boolean isAvailable() throws ServletException {
        if (isStarted() && this._unavailable == 0) {
            return true;
        }
        try {
            getServlet();
        } catch (Exception e2) {
            LOG.ignore(e2);
        }
        return isStarted() && this._unavailable == 0;
    }

    public boolean isEnabled() {
        return this._enabled;
    }

    public boolean isSetInitOrder() {
        return this._initOnStartup;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.servlet.ServletException */
    public Servlet newInstance() throws IllegalAccessException, ServletException, InstantiationException {
        try {
            ServletContext servletContext = getServletHandler().getServletContext();
            return servletContext == null ? getHeldClass().newInstance() : ((ServletContextHandler.Context) servletContext).createServlet(getHeldClass());
        } catch (ServletException e2) {
            Throwable rootCause = e2.getRootCause();
            if (rootCause instanceof InstantiationException) {
                throw ((InstantiationException) rootCause);
            }
            if (rootCause instanceof IllegalAccessException) {
                throw ((IllegalAccessException) rootCause);
            }
            throw e2;
        }
    }

    public void setEnabled(boolean z2) {
        this._enabled = z2;
    }

    public void setForcedPath(String str) {
        this._forcedPath = str;
    }

    public void setInitOrder(int i2) {
        this._initOnStartup = true;
        this._initOrder = i2;
    }

    public void setRunAsRole(String str) {
        this._runAsRole = str;
    }

    public synchronized void setServlet(Servlet servlet) {
        if (servlet != null) {
            if (!(servlet instanceof SingleThreadModel)) {
                this._extInstance = true;
                this._servlet = servlet;
                setHeldClass(servlet.getClass());
                if (getName() == null) {
                    setName(servlet.getClass().getName() + "-" + super.hashCode());
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public synchronized void setUserRoleLink(String str, String str2) {
        if (this._roleMap == null) {
            this._roleMap = new HashMap();
        }
        this._roleMap.put(str, str2);
    }

    public ServletHolder(Holder.Source source) {
        super(source);
        this._initOnStartup = false;
        this._enabled = true;
    }

    public ServletHolder(Servlet servlet) {
        this(Holder.Source.EMBEDDED);
        setServlet(servlet);
    }

    private boolean isJspServlet(String str) {
        if (str == null) {
            return false;
        }
        return "org.apache.jasper.servlet.JspServlet".equals(str);
    }

    public ServletHolder(String str, Class<? extends Servlet> cls) {
        this(Holder.Source.EMBEDDED);
        setName(str);
        setHeldClass(cls);
    }

    public ServletHolder(String str, Servlet servlet) {
        this(Holder.Source.EMBEDDED);
        setName(str);
        setServlet(servlet);
    }

    private void makeUnavailable(Throwable th) {
        if (th instanceof UnavailableException) {
            makeUnavailable((UnavailableException) th);
            return;
        }
        ServletContext servletContext = this._servletHandler.getServletContext();
        if (servletContext == null) {
            LOG.info("unavailable", th);
        } else {
            servletContext.log("unavailable", th);
        }
        this._unavailableEx = new UnavailableException(String.valueOf(th), -1, th) { // from class: org.eclipse.jetty.servlet.ServletHolder.1
            final /* synthetic */ Throwable val$e;

            {
                this.val$e = th;
                initCause(th);
            }
        };
        this._unavailable = -1L;
    }

    public ServletHolder(Class<? extends Servlet> cls) {
        this(Holder.Source.EMBEDDED);
        setHeldClass(cls);
    }
}
