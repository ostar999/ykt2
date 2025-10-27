package com.alibaba.sdk.android.vod.upload.common;

import com.aliyun.vod.log.util.UUIDGenerator;

/* loaded from: classes2.dex */
public class RequestIDSession {
    private static RequestIDSession requestIDSession;
    private boolean canModify = true;
    private String requestID;

    public static RequestIDSession getInstance() {
        if (requestIDSession == null) {
            synchronized (RequestIDSession.class) {
                if (requestIDSession == null) {
                    requestIDSession = new RequestIDSession();
                }
            }
        }
        return requestIDSession;
    }

    public String getRequestID() {
        if (this.requestID == null) {
            this.requestID = UUIDGenerator.generateRequestID();
        }
        return this.requestID;
    }

    public void setRequestID(String str) {
        this.requestID = str;
    }

    public void updateRequestID() {
        if (this.canModify) {
            this.requestID = UUIDGenerator.generateRequestID();
        }
    }

    public void setRequestID(String str, boolean z2) {
        this.requestID = str;
        this.canModify = z2;
    }
}
