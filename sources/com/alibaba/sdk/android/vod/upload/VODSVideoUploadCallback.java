package com.alibaba.sdk.android.vod.upload;

/* loaded from: classes2.dex */
public interface VODSVideoUploadCallback {
    void onSTSTokenExpried();

    void onUploadFailed(String str, String str2);

    void onUploadProgress(long j2, long j3);

    void onUploadRetry(String str, String str2);

    void onUploadRetryResume();

    void onUploadSucceed(String str, String str2);
}
