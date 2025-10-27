package com.unity3d.splash.services.core.log;

import android.util.Log;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.umeng.analytics.pro.am;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class DeviceLog {
    private static boolean FORCE_DEBUG_LOG = false;
    public static final int LOGLEVEL_DEBUG = 8;
    private static final int LOGLEVEL_ERROR = 1;
    public static final int LOGLEVEL_INFO = 4;
    private static final int LOGLEVEL_WARNING = 2;
    private static boolean LOG_DEBUG = true;
    private static boolean LOG_ERROR = true;
    private static boolean LOG_INFO = true;
    private static boolean LOG_WARNING = true;
    private static final int MAX_DEBUG_MSG_LENGTH = 3072;
    private static final HashMap _deviceLogLevel;

    /* renamed from: com.unity3d.splash.services.core.log.DeviceLog$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel;

        static {
            int[] iArr = new int[UnityAdsLogLevel.values().length];
            $SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel = iArr;
            try {
                iArr[UnityAdsLogLevel.INFO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel[UnityAdsLogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel[UnityAdsLogLevel.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel[UnityAdsLogLevel.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum UnityAdsLogLevel {
        INFO,
        DEBUG,
        WARNING,
        ERROR
    }

    static {
        HashMap map = new HashMap();
        _deviceLogLevel = map;
        if (map.size() == 0) {
            map.put(UnityAdsLogLevel.INFO, new DeviceLogLevel(am.aC));
            map.put(UnityAdsLogLevel.DEBUG, new DeviceLogLevel("d"));
            map.put(UnityAdsLogLevel.WARNING, new DeviceLogLevel("w"));
            map.put(UnityAdsLogLevel.ERROR, new DeviceLogLevel(AliyunLogKey.KEY_EVENT));
        }
        if (new File("/data/local/tmp/UnityAdsForceDebugMode").exists()) {
            FORCE_DEBUG_LOG = true;
        }
    }

    private static String checkMessage(String str) {
        return (str == null || str.length() == 0) ? "DO NOT USE EMPTY MESSAGES, use DeviceLog.entered() instead" : str;
    }

    private static DeviceLogEntry createLogEntry(UnityAdsLogLevel unityAdsLogLevel, String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        DeviceLogLevel logLevel = getLogLevel(unityAdsLogLevel);
        if (logLevel == null) {
            return null;
        }
        int i2 = 0;
        boolean z2 = false;
        while (i2 < stackTrace.length) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            if (stackTraceElement.getClassName().equals(DeviceLog.class.getName())) {
                z2 = true;
            }
            if (!stackTraceElement.getClassName().equals(DeviceLog.class.getName()) && z2) {
                break;
            }
            i2++;
        }
        StackTraceElement stackTraceElement2 = i2 < stackTrace.length ? stackTrace[i2] : null;
        if (stackTraceElement2 != null) {
            return new DeviceLogEntry(logLevel, str, stackTraceElement2);
        }
        return null;
    }

    public static void debug(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (LOG_DEBUG || FORCE_DEBUG_LOG) {
            if (str.length() <= 3072) {
                write(UnityAdsLogLevel.DEBUG, checkMessage(str));
                return;
            }
            debug(str.substring(0, 3072));
            if (str.length() < 30720) {
                debug(str.substring(3072));
            }
        }
    }

    public static void debug(String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        debug(String.format(str, objArr));
    }

    public static void entered() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        debug("ENTERED METHOD");
    }

    public static void error(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        write(UnityAdsLogLevel.ERROR, checkMessage(str));
    }

    public static void error(String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        error(String.format(str, objArr));
    }

    public static void exception(String str, Exception exc) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String str2 = "";
        if (str != null) {
            str2 = "" + str;
        }
        if (exc != null) {
            str2 = str2 + ": " + exc.getMessage();
        }
        if (exc != null && exc.getCause() != null) {
            str2 = str2 + ": " + exc.getCause().getMessage();
        }
        write(UnityAdsLogLevel.ERROR, str2);
    }

    private static DeviceLogLevel getLogLevel(UnityAdsLogLevel unityAdsLogLevel) {
        return (DeviceLogLevel) _deviceLogLevel.get(unityAdsLogLevel);
    }

    public static void info(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        write(UnityAdsLogLevel.INFO, checkMessage(str));
    }

    public static void info(String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        info(String.format(str, objArr));
    }

    public static void setLogLevel(int i2) {
        if (i2 >= 8) {
            LOG_ERROR = true;
            LOG_WARNING = true;
            LOG_INFO = true;
            LOG_DEBUG = true;
            return;
        }
        if (i2 >= 4) {
            LOG_ERROR = true;
            LOG_WARNING = true;
            LOG_INFO = true;
            LOG_DEBUG = false;
            return;
        }
        if (i2 >= 2) {
            LOG_ERROR = true;
            LOG_WARNING = true;
            LOG_INFO = false;
            LOG_DEBUG = false;
            return;
        }
        if (i2 > 0) {
            LOG_ERROR = true;
            LOG_WARNING = false;
            LOG_INFO = false;
            LOG_DEBUG = false;
            return;
        }
        LOG_ERROR = false;
        LOG_WARNING = false;
        LOG_INFO = false;
        LOG_DEBUG = false;
    }

    public static void warning(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        write(UnityAdsLogLevel.WARNING, checkMessage(str));
    }

    public static void warning(String str, Object... objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        warning(String.format(str, objArr));
    }

    private static void write(UnityAdsLogLevel unityAdsLogLevel, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i2 = AnonymousClass1.$SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel[unityAdsLogLevel.ordinal()];
        if (FORCE_DEBUG_LOG ? true : i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? true : LOG_ERROR : LOG_WARNING : LOG_DEBUG : LOG_INFO) {
            writeToLog(createLogEntry(unityAdsLogLevel, str));
        }
    }

    private static void writeToLog(DeviceLogEntry deviceLogEntry) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method;
        if (deviceLogEntry == null || deviceLogEntry.getLogLevel() == null) {
            return;
        }
        try {
            method = Log.class.getMethod(deviceLogEntry.getLogLevel().getReceivingMethodName(), String.class, String.class);
        } catch (Exception e2) {
            Log.e("UnityAds", "Writing to log failed!", e2);
            method = null;
        }
        if (method != null) {
            try {
                method.invoke(null, deviceLogEntry.getLogLevel().getLogTag(), deviceLogEntry.getParsedMessage());
            } catch (Exception e3) {
                Log.e("UnityAds", "Writing to log failed!", e3);
            }
        }
    }
}
