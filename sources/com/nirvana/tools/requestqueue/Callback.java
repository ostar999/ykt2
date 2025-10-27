package com.nirvana.tools.requestqueue;

import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* loaded from: classes4.dex */
public abstract class Callback<T extends Response> {
    protected long mExpiredTime = 0;
    protected ThreadStrategy mThreadStrategy;
    protected long mThreshold;

    public Callback(ThreadStrategy threadStrategy, long j2) {
        this.mThreadStrategy = threadStrategy;
        this.mThreshold = j2;
    }

    public long getExpiredTime() {
        return this.mExpiredTime;
    }

    public ThreadStrategy getThreadStrategy() {
        return this.mThreadStrategy;
    }

    public long getThreshold() {
        return this.mThreshold;
    }

    public abstract void onResult(T t2);

    public void setExpiredTime(long j2) {
        this.mExpiredTime = j2;
    }
}
