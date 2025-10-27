package com.thin.downloadmanager;

/* loaded from: classes6.dex */
public class DefaultRetryPolicy implements RetryPolicy {
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;
    public static final int DEFAULT_MAX_RETRIES = 1;
    public static final int DEFAULT_TIMEOUT_MS = 5000;
    private final float mBackoffMultiplier;
    private int mCurrentRetryCount;
    private int mCurrentTimeoutMs;
    private final int mMaxNumRetries;

    public DefaultRetryPolicy() {
        this(5000, 1, 1.0f);
    }

    @Override // com.thin.downloadmanager.RetryPolicy
    public float getBackOffMultiplier() {
        return this.mBackoffMultiplier;
    }

    @Override // com.thin.downloadmanager.RetryPolicy
    public int getCurrentRetryCount() {
        return this.mCurrentRetryCount;
    }

    @Override // com.thin.downloadmanager.RetryPolicy
    public int getCurrentTimeout() {
        return this.mCurrentTimeoutMs;
    }

    public boolean hasAttemptRemaining() {
        return this.mCurrentRetryCount <= this.mMaxNumRetries;
    }

    @Override // com.thin.downloadmanager.RetryPolicy
    public void retry() throws RetryError {
        this.mCurrentRetryCount++;
        int i2 = this.mCurrentTimeoutMs;
        this.mCurrentTimeoutMs = (int) (i2 + (i2 * this.mBackoffMultiplier));
        if (!hasAttemptRemaining()) {
            throw new RetryError();
        }
    }

    public DefaultRetryPolicy(int i2, int i3, float f2) {
        this.mCurrentTimeoutMs = i2;
        this.mMaxNumRetries = i3;
        this.mBackoffMultiplier = f2;
    }
}
