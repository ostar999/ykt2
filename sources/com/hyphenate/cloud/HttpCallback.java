package com.hyphenate.cloud;

/* loaded from: classes4.dex */
public interface HttpCallback {
    void onError(int i2, String str);

    void onProgress(long j2, long j3);

    void onSuccess(String str);
}
