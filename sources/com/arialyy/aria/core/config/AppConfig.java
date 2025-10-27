package com.arialyy.aria.core.config;

import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.AriaCrashHandler;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class AppConfig extends BaseConfig implements Serializable {
    int logLevel;
    boolean useAriaCrashHandler;
    boolean netCheck = true;
    boolean useBroadcast = false;
    boolean notNetRetry = false;

    public int getLogLevel() {
        return this.logLevel;
    }

    @Override // com.arialyy.aria.core.config.BaseConfig
    public int getType() {
        return 3;
    }

    public boolean getUseAriaCrashHandler() {
        return this.useAriaCrashHandler;
    }

    public boolean isNetCheck() {
        return this.netCheck;
    }

    public boolean isNotNetRetry() {
        return this.notNetRetry;
    }

    public boolean isUseBroadcast() {
        return this.useBroadcast;
    }

    public AppConfig setLogLevel(int i2) throws Throwable {
        this.logLevel = i2;
        ALog.LOG_LEVEL = i2;
        save();
        return this;
    }

    public AppConfig setNetCheck(boolean z2) throws Throwable {
        this.netCheck = z2;
        save();
        return this;
    }

    public AppConfig setNotNetRetry(boolean z2) throws Throwable {
        this.notNetRetry = z2;
        save();
        return this;
    }

    public AppConfig setUseAriaCrashHandler(boolean z2) throws Throwable {
        this.useAriaCrashHandler = z2;
        if (z2) {
            Thread.setDefaultUncaughtExceptionHandler(new AriaCrashHandler());
        } else {
            Thread.setDefaultUncaughtExceptionHandler(null);
        }
        save();
        return this;
    }

    public AppConfig setUseBroadcast(boolean z2) throws Throwable {
        this.useBroadcast = z2;
        save();
        return this;
    }
}
