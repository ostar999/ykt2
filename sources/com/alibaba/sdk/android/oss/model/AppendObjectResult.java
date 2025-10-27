package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public class AppendObjectResult extends OSSResult {
    private long nextPosition;
    private String objectCRC64;

    public long getNextPosition() {
        return this.nextPosition;
    }

    public String getObjectCRC64() {
        return this.objectCRC64;
    }

    public void setNextPosition(Long l2) {
        this.nextPosition = l2.longValue();
    }

    public void setObjectCRC64(String str) {
        this.objectCRC64 = str;
    }
}
