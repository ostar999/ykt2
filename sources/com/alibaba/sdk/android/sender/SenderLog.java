package com.alibaba.sdk.android.sender;

import com.alibaba.sdk.android.logger.BaseSdkLogApi;
import com.alibaba.sdk.android.logger.ILog;
import com.alibaba.sdk.android.logger.ILogger;
import com.alibaba.sdk.android.logger.LogLevel;

/* loaded from: classes2.dex */
public class SenderLog {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final BaseSdkLogApi f2871a = new BaseSdkLogApi("Sender", false);
    }

    private SenderLog() {
    }

    public static void addILogger(ILogger iLogger) {
        a.f2871a.addILogger(iLogger);
    }

    public static void enable(boolean z2) {
        a.f2871a.enable(z2);
    }

    public static ILog getLogger(Object obj) {
        return a.f2871a.getLogger(obj);
    }

    public static void removeILogger(ILogger iLogger) {
        a.f2871a.removeILogger(iLogger);
    }

    public static void setILogger(ILogger iLogger) {
        a.f2871a.setILogger(iLogger);
    }

    public static void setLevel(LogLevel logLevel) {
        a.f2871a.setLevel(logLevel);
    }
}
