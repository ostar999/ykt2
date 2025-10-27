package org.eclipse.jetty.util.log;

import cn.hutool.core.text.StrPool;
import java.util.logging.Level;
import k.a;

/* loaded from: classes9.dex */
public class JavaUtilLog extends AbstractLogger {
    private java.util.logging.Logger _logger;
    private Level configuredLevel;

    public JavaUtilLog() {
        this("org.eclipse.jetty.util.log");
    }

    private String format(String str, Object... objArr) {
        String strValueOf = String.valueOf(str);
        StringBuilder sb = new StringBuilder();
        int length = 0;
        for (Object obj : objArr) {
            int iIndexOf = strValueOf.indexOf(StrPool.EMPTY_JSON, length);
            if (iIndexOf < 0) {
                sb.append(strValueOf.substring(length));
                sb.append(" ");
                sb.append(obj);
                length = strValueOf.length();
            } else {
                sb.append(strValueOf.substring(length, iIndexOf));
                sb.append(String.valueOf(obj));
                length = iIndexOf + 2;
            }
        }
        sb.append(strValueOf.substring(length));
        return sb.toString();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Object... objArr) {
        this._logger.log(Level.FINE, format(str, objArr));
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public String getName() {
        return this._logger.getName();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void ignore(Throwable th) {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Object... objArr) {
        this._logger.log(Level.INFO, format(str, objArr));
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public boolean isDebugEnabled() {
        return this._logger.isLoggable(Level.FINE);
    }

    @Override // org.eclipse.jetty.util.log.AbstractLogger
    public Logger newLogger(String str) {
        return new JavaUtilLog(str);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void setDebugEnabled(boolean z2) throws SecurityException {
        if (!z2) {
            this._logger.setLevel(this.configuredLevel);
        } else {
            this.configuredLevel = this._logger.getLevel();
            this._logger.setLevel(Level.FINE);
        }
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Object... objArr) {
        this._logger.log(Level.WARNING, format(str, objArr));
    }

    public JavaUtilLog(String str) throws SecurityException {
        this._logger = java.util.logging.Logger.getLogger(str);
        if (Boolean.parseBoolean(Log.__props.getProperty("org.eclipse.jetty.util.log.DEBUG", a.f27524v))) {
            this._logger.setLevel(Level.FINE);
        }
        this.configuredLevel = this._logger.getLevel();
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(Throwable th) {
        debug("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(Throwable th) {
        info("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(Throwable th) {
        warn("", th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void debug(String str, Throwable th) {
        this._logger.log(Level.FINE, str, th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void info(String str, Throwable th) {
        this._logger.log(Level.INFO, str, th);
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public void warn(String str, Throwable th) {
        this._logger.log(Level.WARNING, str, th);
    }
}
