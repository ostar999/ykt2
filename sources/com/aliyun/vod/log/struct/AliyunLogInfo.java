package com.aliyun.vod.log.struct;

import com.aliyun.vod.log.core.AliyunLogger;
import com.aliyun.vod.log.core.AliyunLoggerManager;

/* loaded from: classes2.dex */
public class AliyunLogInfo {
    private String mLoggerTag;

    public AliyunLogInfo(String str) {
        this.mLoggerTag = str;
    }

    public String getRequestID() {
        AliyunLogger logger = AliyunLoggerManager.getLogger(this.mLoggerTag);
        if (logger != null) {
            return logger.getRequestID();
        }
        return null;
    }
}
