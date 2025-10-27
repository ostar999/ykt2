package com.aliyun.vod.common.logger;

/* loaded from: classes2.dex */
public class LoggerFactory {
    public static LoggerPrinter getFactory(String str, boolean z2) {
        LoggerPrinter loggerPrinter = new LoggerPrinter();
        loggerPrinter.init(str);
        LogLevel logLevel = LogLevel.NONE;
        if (z2) {
            logLevel = LogLevel.FULL;
        }
        loggerPrinter.getSettings().methodCount(3).logLevel(logLevel);
        return loggerPrinter;
    }
}
