package com.aliyun.sls.android.producer;

/* loaded from: classes2.dex */
public interface LogProducerCallback {
    void onCall(int resultCode, String reqId, String errorMessage, int logBytes, int compressedBytes);
}
