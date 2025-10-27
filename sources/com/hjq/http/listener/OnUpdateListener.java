package com.hjq.http.listener;

/* loaded from: classes4.dex */
public interface OnUpdateListener<T> extends OnHttpListener<T> {
    void onByte(long j2, long j3);

    void onProgress(int i2);
}
