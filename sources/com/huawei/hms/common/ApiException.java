package com.huawei.hms.common;

import com.huawei.hms.support.api.client.Status;

/* loaded from: classes4.dex */
public class ApiException extends Exception {
    protected final Status mStatus;

    public ApiException(Status status) {
        StringBuilder sb = new StringBuilder();
        sb.append(status.getStatusCode());
        sb.append(": ");
        sb.append(status.getStatusMessage() != null ? status.getStatusMessage() : "");
        super(sb.toString());
        this.mStatus = status;
    }

    public int getStatusCode() {
        return this.mStatus.getStatusCode();
    }

    @Deprecated
    public String getStatusMessage() {
        return this.mStatus.getStatusMessage();
    }
}
