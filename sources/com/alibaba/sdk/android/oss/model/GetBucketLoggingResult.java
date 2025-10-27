package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public class GetBucketLoggingResult extends OSSResult {
    private boolean mLoggingEnabled = false;
    private String mTargetBucketName;
    private String mTargetPrefix;

    public String getTargetBucketName() {
        return this.mTargetBucketName;
    }

    public String getTargetPrefix() {
        return this.mTargetPrefix;
    }

    public boolean loggingEnabled() {
        return this.mLoggingEnabled;
    }

    public void setLoggingEnabled(boolean z2) {
        this.mLoggingEnabled = z2;
    }

    public void setTargetBucketName(String str) {
        this.mTargetBucketName = str;
    }

    public void setTargetPrefix(String str) {
        this.mTargetPrefix = str;
    }
}
