package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public class OSSRequest {
    private boolean isAuthorizationRequired = true;
    private Enum CRC64 = CRC64Config.NULL;

    public enum CRC64Config {
        NULL,
        YES,
        NO
    }

    public Enum getCRC64() {
        return this.CRC64;
    }

    public boolean isAuthorizationRequired() {
        return this.isAuthorizationRequired;
    }

    public void setCRC64(Enum r12) {
        this.CRC64 = r12;
    }

    public void setIsAuthorizationRequired(boolean z2) {
        this.isAuthorizationRequired = z2;
    }
}
