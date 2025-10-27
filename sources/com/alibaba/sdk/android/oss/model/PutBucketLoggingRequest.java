package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public class PutBucketLoggingRequest extends OSSRequest {
    private String mBucketName;
    private String mTargetBucketName;
    private String mTargetPrefix;

    public String getBucketName() {
        return this.mBucketName;
    }

    public String getTargetBucketName() {
        return this.mTargetBucketName;
    }

    public String getTargetPrefix() {
        return this.mTargetPrefix;
    }

    public void setBucketName(String str) {
        this.mBucketName = str;
    }

    public void setTargetBucketName(String str) {
        this.mTargetBucketName = str;
    }

    public void setTargetPrefix(String str) {
        this.mTargetPrefix = str;
    }
}
