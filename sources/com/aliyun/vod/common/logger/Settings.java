package com.aliyun.vod.common.logger;

/* loaded from: classes2.dex */
public final class Settings {
    private LogTool logTool;
    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private LogLevel logLevel = LogLevel.FULL;

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public LogTool getLogTool() {
        if (this.logTool == null) {
            this.logTool = new AndroidLogTool();
        }
        return this.logTool;
    }

    public int getMethodCount() {
        return this.methodCount;
    }

    public int getMethodOffset() {
        return this.methodOffset;
    }

    public Settings hideThreadInfo() {
        this.showThreadInfo = false;
        return this;
    }

    public boolean isShowThreadInfo() {
        return this.showThreadInfo;
    }

    public Settings logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Settings logTool(LogTool logTool) {
        this.logTool = logTool;
        return this;
    }

    public Settings methodCount(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.methodCount = i2;
        return this;
    }

    public Settings methodOffset(int i2) {
        this.methodOffset = i2;
        return this;
    }

    @Deprecated
    public Settings setLogLevel(LogLevel logLevel) {
        return logLevel(logLevel);
    }

    @Deprecated
    public Settings setMethodCount(int i2) {
        return methodCount(i2);
    }

    @Deprecated
    public Settings setMethodOffset(int i2) {
        return methodOffset(i2);
    }
}
