package org.eclipse.jetty.util.log;

import cn.hutool.core.text.StrPool;

/* loaded from: classes9.dex */
public abstract class AbstractLogger implements Logger {
    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jetty.util.log.Logger
    public final Logger getLogger(String str) {
        if (isBlank(str)) {
            return this;
        }
        String name = getName();
        if (!isBlank(name) && Log.getRootLogger() != this) {
            str = name + StrPool.DOT + str;
        }
        Logger logger = Log.getLoggers().get(str);
        if (logger != null) {
            return logger;
        }
        Logger loggerNewLogger = newLogger(str);
        Logger loggerPutIfAbsent = Log.getMutableLoggers().putIfAbsent(str, loggerNewLogger);
        return loggerPutIfAbsent == null ? loggerNewLogger : loggerPutIfAbsent;
    }

    public abstract Logger newLogger(String str);
}
