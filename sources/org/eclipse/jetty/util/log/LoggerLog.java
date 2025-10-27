package org.eclipse.jetty.util.log;

import com.aliyun.vod.log.core.AliyunLogCommon;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes9.dex */
public class LoggerLog extends AbstractLogger {
    private volatile boolean _debug;
    private final Method _debugMAA;
    private final Method _debugMT;
    private final Method _getLoggerN;
    private final Method _getName;
    private final Method _infoMAA;
    private final Method _infoMT;
    private final Object _logger;
    private final Method _setDebugEnabledE;
    private final Method _warnMAA;
    private final Method _warnMT;

    public LoggerLog(Object obj) throws NoSuchMethodException, SecurityException {
        try {
            this._logger = obj;
            Class<?> cls = obj.getClass();
            this._debugMT = cls.getMethod("debug", String.class, Throwable.class);
            this._debugMAA = cls.getMethod("debug", String.class, Object[].class);
            this._infoMT = cls.getMethod(AliyunLogCommon.LogLevel.INFO, String.class, Throwable.class);
            this._infoMAA = cls.getMethod(AliyunLogCommon.LogLevel.INFO, String.class, Object[].class);
            this._warnMT = cls.getMethod(AliyunLogCommon.LogLevel.WARN, String.class, Throwable.class);
            this._warnMAA = cls.getMethod(AliyunLogCommon.LogLevel.WARN, String.class, Object[].class);
            Method method = cls.getMethod("isDebugEnabled", new Class[0]);
            this._setDebugEnabledE = cls.getMethod("setDebugEnabled", Boolean.TYPE);
            this._getLoggerN = cls.getMethod("getLogger", String.class);
            this._getName = cls.getMethod("getName", new Class[0]);
            this._debug = ((Boolean) method.invoke(obj, new Object[0])).booleanValue();
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Object... objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this._debug) {
            try {
                this._debugMAA.invoke(this._logger, objArr);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public String getName() {
        try {
            return (String) this._getName.invoke(this._logger, new Object[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void ignore(Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Object... objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this._infoMAA.invoke(this._logger, objArr);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public boolean isDebugEnabled() {
        return this._debug;
    }

    @Override // org.eclipse.jetty.util.log.AbstractLogger
    public Logger newLogger(String str) {
        try {
            return new LoggerLog(this._getLoggerN.invoke(this._logger, str));
        } catch (Exception e2) {
            e2.printStackTrace();
            return this;
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void setDebugEnabled(boolean z2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this._setDebugEnabledE.invoke(this._logger, Boolean.valueOf(z2));
            this._debug = z2;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Object... objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this._warnMAA.invoke(this._logger, objArr);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        info("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        warn("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        debug("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this._infoMT.invoke(this._logger, str, th);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this._warnMT.invoke(this._logger, str, th);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this._debug) {
            try {
                this._debugMT.invoke(this._logger, str, th);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
