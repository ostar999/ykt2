package org.eclipse.jetty.util.log;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jetty.util.Loader;

/* loaded from: classes9.dex */
public class Log {
    public static final String EXCEPTION = "EXCEPTION ";
    public static final String IGNORED = "IGNORED ";
    private static Logger LOG;
    public static boolean __ignored;
    private static boolean __initialized;
    public static String __logClass;
    private static final ConcurrentMap<String, Logger> __loggers = new ConcurrentHashMap();
    protected static Properties __props = new Properties();

    static {
        AccessController.doPrivileged(new PrivilegedAction<Object>() { // from class: org.eclipse.jetty.util.log.Log.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
            /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Class, java.lang.Class<org.eclipse.jetty.util.log.Log>] */
            /* JADX WARN: Type inference failed for: r2v1 */
            /* JADX WARN: Type inference failed for: r2v11 */
            /* JADX WARN: Type inference failed for: r2v12 */
            /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
            @Override // java.security.PrivilegedAction
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public java.lang.Object run() throws java.lang.Throwable {
                /*
                    r7 = this;
                    java.lang.String r0 = "jetty-logging.properties"
                    r1 = 1
                    java.lang.Class<org.eclipse.jetty.util.log.Log> r2 = org.eclipse.jetty.util.log.Log.class
                    java.net.URL r0 = org.eclipse.jetty.util.Loader.getResource(r2, r0, r1)
                    r1 = 0
                    if (r0 == 0) goto L42
                    java.io.InputStream r2 = r0.openStream()     // Catch: java.lang.Throwable -> L1e java.io.IOException -> L20
                    java.util.Properties r3 = org.eclipse.jetty.util.log.Log.__props     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
                    r3.load(r2)     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L1c
                L15:
                    org.eclipse.jetty.util.IO.close(r2)
                    goto L42
                L19:
                    r0 = move-exception
                    r1 = r2
                    goto L3e
                L1c:
                    r3 = move-exception
                    goto L22
                L1e:
                    r0 = move-exception
                    goto L3e
                L20:
                    r3 = move-exception
                    r2 = r1
                L22:
                    java.io.PrintStream r4 = java.lang.System.err     // Catch: java.lang.Throwable -> L19
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L19
                    r5.<init>()     // Catch: java.lang.Throwable -> L19
                    java.lang.String r6 = "Unable to load "
                    r5.append(r6)     // Catch: java.lang.Throwable -> L19
                    r5.append(r0)     // Catch: java.lang.Throwable -> L19
                    java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L19
                    r4.println(r0)     // Catch: java.lang.Throwable -> L19
                    java.io.PrintStream r0 = java.lang.System.err     // Catch: java.lang.Throwable -> L19
                    r3.printStackTrace(r0)     // Catch: java.lang.Throwable -> L19
                    goto L15
                L3e:
                    org.eclipse.jetty.util.IO.close(r1)
                    throw r0
                L42:
                    java.util.Properties r0 = java.lang.System.getProperties()
                    java.util.Enumeration r0 = r0.propertyNames()
                L4a:
                    boolean r2 = r0.hasMoreElements()
                    if (r2 == 0) goto L62
                    java.lang.Object r2 = r0.nextElement()
                    java.lang.String r2 = (java.lang.String) r2
                    java.lang.String r3 = java.lang.System.getProperty(r2)
                    if (r3 == 0) goto L4a
                    java.util.Properties r4 = org.eclipse.jetty.util.log.Log.__props
                    r4.setProperty(r2, r3)
                    goto L4a
                L62:
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.class"
                    java.lang.String r3 = "org.eclipse.jetty.util.log.Slf4jLog"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    org.eclipse.jetty.util.log.Log.__logClass = r0
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.IGNORED"
                    java.lang.String r3 = "false"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    boolean r0 = java.lang.Boolean.parseBoolean(r0)
                    org.eclipse.jetty.util.log.Log.__ignored = r0
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.log.Log.AnonymousClass1.run():java.lang.Object");
            }
        });
    }

    @Deprecated
    public static void debug(Throwable th) {
        if (isDebugEnabled()) {
            LOG.debug(EXCEPTION, th);
        }
    }

    @Deprecated
    public static Logger getLog() {
        initialized();
        return LOG;
    }

    public static Logger getLogger(Class<?> cls) {
        return getLogger(cls.getName());
    }

    public static Map<String, Logger> getLoggers() {
        return Collections.unmodifiableMap(__loggers);
    }

    public static ConcurrentMap<String, Logger> getMutableLoggers() {
        return __loggers;
    }

    public static Logger getRootLogger() {
        initialized();
        return LOG;
    }

    @Deprecated
    public static void ignore(Throwable th) {
        if (initialized()) {
            LOG.ignore(th);
        }
    }

    @Deprecated
    public static void info(String str) {
        if (initialized()) {
            LOG.info(str, new Object[0]);
        }
    }

    private static void initStandardLogging(Throwable th) {
        if (th != null && __ignored) {
            th.printStackTrace();
        }
        if (LOG == null) {
            StdErrLog stdErrLog = new StdErrLog();
            LOG = stdErrLog;
            stdErrLog.debug("Logging to {} via {}", stdErrLog, StdErrLog.class.getName());
        }
    }

    public static boolean initialized() {
        boolean z2 = true;
        if (LOG != null) {
            return true;
        }
        synchronized (Log.class) {
            if (__initialized) {
                if (LOG == null) {
                    z2 = false;
                }
                return z2;
            }
            __initialized = true;
            try {
                Class clsLoadClass = Loader.loadClass(Log.class, __logClass);
                Logger logger = LOG;
                if (logger == null || !logger.getClass().equals(clsLoadClass)) {
                    Logger logger2 = (Logger) clsLoadClass.newInstance();
                    LOG = logger2;
                    logger2.debug("Logging to {} via {}", logger2, clsLoadClass.getName());
                }
            } catch (Throwable th) {
                initStandardLogging(th);
            }
            return LOG != null;
        }
    }

    @Deprecated
    public static boolean isDebugEnabled() {
        if (initialized()) {
            return LOG.isDebugEnabled();
        }
        return false;
    }

    public static boolean isIgnored() {
        return __ignored;
    }

    public static void setLog(Logger logger) {
        LOG = logger;
    }

    public static void setLogToParent(String str) {
        ClassLoader classLoader = Log.class.getClassLoader();
        if (classLoader == null || classLoader.getParent() == null) {
            setLog(getLogger(str));
            return;
        }
        try {
            setLog(new LoggerLog(classLoader.getParent().loadClass("org.eclipse.jetty.util.log.Log").getMethod("getLogger", String.class).invoke(null, str)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Deprecated
    public static void warn(String str) {
        if (initialized()) {
            LOG.warn(str, new Object[0]);
        }
    }

    public static Logger getLogger(String str) {
        if (!initialized()) {
            return null;
        }
        if (str == null) {
            return LOG;
        }
        Logger logger = __loggers.get(str);
        return logger == null ? LOG.getLogger(str) : logger;
    }

    @Deprecated
    public static void debug(String str) {
        if (initialized()) {
            LOG.debug(str, new Object[0]);
        }
    }

    @Deprecated
    public static void info(String str, Object obj) {
        if (initialized()) {
            LOG.info(str, obj);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj) {
        if (initialized()) {
            LOG.warn(str, obj);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj) {
        if (initialized()) {
            LOG.debug(str, obj);
        }
    }

    @Deprecated
    public static void info(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.info(str, obj, obj2);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.warn(str, obj, obj2);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.debug(str, obj, obj2);
        }
    }

    @Deprecated
    public static void warn(String str, Throwable th) {
        if (initialized()) {
            LOG.warn(str, th);
        }
    }

    @Deprecated
    public static void warn(Throwable th) {
        if (initialized()) {
            LOG.warn(EXCEPTION, th);
        }
    }
}
