package com.alibaba.sdk.android.vod.upload.internal;

/* loaded from: classes2.dex */
public interface OSSUploadListener {
    void onUploadFailed(String str, String str2);

    void onUploadProgress(Object obj, long j2, long j3);

    void onUploadRetry(String str, String str2);

    void onUploadRetryResume();

    void onUploadSucceed();

    void onUploadTokenExpired();
}
