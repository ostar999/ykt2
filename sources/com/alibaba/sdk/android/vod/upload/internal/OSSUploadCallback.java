package com.alibaba.sdk.android.vod.upload.internal;

/* loaded from: classes2.dex */
public interface OSSUploadCallback {
    void onUploadFailed(String str, String str2);

    void onUploadProgress(long j2, long j3);

    void onUploadSucceed();

    void onUploadTokenExpired();
}
