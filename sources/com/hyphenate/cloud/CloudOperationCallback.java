package com.hyphenate.cloud;

/* loaded from: classes4.dex */
public interface CloudOperationCallback {
    void onError(String str);

    void onProgress(int i2);

    void onSuccess(String str);
}
