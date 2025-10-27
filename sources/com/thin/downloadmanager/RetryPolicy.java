package com.thin.downloadmanager;

/* loaded from: classes6.dex */
public interface RetryPolicy {
    float getBackOffMultiplier();

    int getCurrentRetryCount();

    int getCurrentTimeout();

    void retry() throws RetryError;
}
