package com.beizi.ad.internal.utilities;

import android.content.Context;
import com.beizi.ad.internal.utilities.HaoboLogListener;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class HaoboLog {
    private static final boolean mIsDebug = false;
    public static String baseLogTag = "BEIZISDK";
    public static String mediationLogTag = baseLogTag + "-MEDIATION";
    public static String publicFunctionsLogTag = baseLogTag + "-INTERFACE";
    public static String httpReqLogTag = baseLogTag + "-REQUEST";
    public static String httpRespLogTag = baseLogTag + "-RESPONSE";
    public static String pbLogTag = baseLogTag + "-PB";
    public static String xmlLogTag = baseLogTag + "-XML";
    public static String jsonLogTag = baseLogTag + "-JSON";
    public static String jsLogTag = baseLogTag + "-JS";
    public static String mraidLogTag = baseLogTag + "-MRAID";
    public static String browserLogTag = baseLogTag + "-APPBROWSER";
    public static String nativeLogTag = baseLogTag + "-NATIVE";
    public static String videoLogTag = baseLogTag + "-VIDEO";
    public static String fixLog = baseLogTag + "-FIX";
    public static String pingerLogTag = baseLogTag + "-PINGER";
    public static String lruDiskUsageLogTag = baseLogTag + "-LRUDISKUSAGE";
    public static String httpProxyCacheServerLogTag = baseLogTag + "-CACHESERVER";
    public static String httpUrlSourceLogTag = baseLogTag + "-URLSOURCE";
    public static String proxyCacheLogTag = baseLogTag + "-PROXYCACHE";
    public static String proxyCacheUtilsLogTag = baseLogTag + "-PROXYCACHEUTILS";
    public static String storageUtilsLogTag = baseLogTag + "-STORAGEUTILS";
    private static SoftReference<Context> sLogContext = new SoftReference<>(null);
    private static String lastRequest = "";
    private static String lastResponse = "";
    private static String lastLog = "";
    private static final ArrayList<HaoboLogListener> listeners = new ArrayList<>();

    public static synchronized void clearLastResponse() {
        lastResponse = "";
    }

    public static void d(String str, String str2) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.D, str, str2);
            logIfLoggable(str, str2, 4, null);
        }
    }

    public static void e(String str, String str2) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.E, str, str2);
            logIfLoggable(str, str2, 6, null);
        }
    }

    public static synchronized String getLastLogRequest() {
        return lastLog;
    }

    public static synchronized String getLastRequest() {
        return lastRequest;
    }

    public static synchronized String getLastResponse() {
        return lastResponse;
    }

    public static String getString(int i2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static void i(String str, String str2) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.I, str, str2);
            logIfLoggable(str, str2, 4, null);
        }
    }

    private static void logIfLoggable(String str, String str2, int i2, Throwable th) {
    }

    private static synchronized void notifyListener(HaoboLogListener.LOG_LEVEL log_level, String str, String str2) {
        notifyListener(log_level, str, str2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized boolean registerListener(com.beizi.ad.internal.utilities.HaoboLogListener r2) {
        /*
            java.lang.Class<com.beizi.ad.internal.utilities.HaoboLog> r0 = com.beizi.ad.internal.utilities.HaoboLog.class
            monitor-enter(r0)
            if (r2 == 0) goto L12
            java.util.ArrayList<com.beizi.ad.internal.utilities.HaoboLogListener> r1 = com.beizi.ad.internal.utilities.HaoboLog.listeners     // Catch: java.lang.Throwable -> Lf
            boolean r2 = r1.add(r2)     // Catch: java.lang.Throwable -> Lf
            if (r2 == 0) goto L12
            r2 = 1
            goto L13
        Lf:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        L12:
            r2 = 0
        L13:
            monitor-exit(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.utilities.HaoboLog.registerListener(com.beizi.ad.internal.utilities.HaoboLogListener):boolean");
    }

    public static void setErrorContext(Context context) {
        sLogContext = new SoftReference<>(context);
    }

    public static synchronized void setLastLogRequest(String str) {
        lastLog = str;
    }

    public static synchronized void setLastRequest(String str) {
        lastRequest = str;
    }

    public static synchronized void setLastResponse(String str) {
        lastResponse = str;
    }

    public static synchronized void unregisterAllListeners() {
        listeners.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized boolean unregisterListener(com.beizi.ad.internal.utilities.HaoboLogListener r2) {
        /*
            java.lang.Class<com.beizi.ad.internal.utilities.HaoboLog> r0 = com.beizi.ad.internal.utilities.HaoboLog.class
            monitor-enter(r0)
            if (r2 == 0) goto L12
            java.util.ArrayList<com.beizi.ad.internal.utilities.HaoboLogListener> r1 = com.beizi.ad.internal.utilities.HaoboLog.listeners     // Catch: java.lang.Throwable -> Lf
            boolean r2 = r1.remove(r2)     // Catch: java.lang.Throwable -> Lf
            if (r2 == 0) goto L12
            r2 = 1
            goto L13
        Lf:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        L12:
            r2 = 0
        L13:
            monitor-exit(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.beizi.ad.internal.utilities.HaoboLog.unregisterListener(com.beizi.ad.internal.utilities.HaoboLogListener):boolean");
    }

    public static void v(String str, String str2) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.V, str, str2);
            logIfLoggable(str, str2, 4, null);
        }
    }

    public static void w(String str, String str2) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.W, str, str2);
            logIfLoggable(str, str2, 5, null);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.D, str, str2, th);
            logIfLoggable(str, str2, 4, th);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.E, str, str2, th);
            logIfLoggable(str, str2, 6, th);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.I, str, str2, th);
            logIfLoggable(str, str2, 4, th);
        }
    }

    private static synchronized void notifyListener(HaoboLogListener.LOG_LEVEL log_level, String str, String str2, Throwable th) {
        Iterator<HaoboLogListener> it = listeners.iterator();
        while (it.hasNext()) {
            HaoboLogListener next = it.next();
            if (log_level.ordinal() >= next.getLogLevel().ordinal()) {
                if (th != null) {
                    next.onReceiveMessage(log_level, str, str2, th);
                } else {
                    next.onReceiveMessage(log_level, str, str2);
                }
            }
        }
    }

    public static void v(String str, String str2, Throwable th) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.V, str, str2, th);
            logIfLoggable(str, str2, 4, th);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (str2 != null) {
            notifyListener(HaoboLogListener.LOG_LEVEL.W, str, str2, th);
            logIfLoggable(str, str2, 5, th);
        }
    }

    public static String getString(int i2, long j2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Long.valueOf(j2));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, String str) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, String str, int i3) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, str, Integer.valueOf(i3));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, int i3, int i4, int i5, int i6) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, boolean z2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Boolean.valueOf(z2));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, String str, String str2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, int i3, String str, String str2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Integer.valueOf(i3), str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, String str, int i3, String str2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, str, Integer.valueOf(i3), str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, int i3, String str) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Integer.valueOf(i3), str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, int i3, int i4, int i5, int i6, String str, boolean z2) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6), str, Boolean.valueOf(z2));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, boolean z2, int i3) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Boolean.valueOf(z2), Integer.valueOf(i3));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getString(int i2, int i3, int i4) {
        try {
            Context context = sLogContext.get();
            if (context == null) {
                return null;
            }
            return context.getString(i2, Integer.valueOf(i3), Integer.valueOf(i4));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
