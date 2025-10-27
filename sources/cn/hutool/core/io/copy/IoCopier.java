package cn.hutool.core.io.copy;

import cn.hutool.core.io.StreamProgress;

/* loaded from: classes.dex */
public abstract class IoCopier<S, T> {
    protected final int bufferSize;
    protected final long count;
    protected boolean flushEveryBuffer;
    protected StreamProgress progress;

    public IoCopier(int i2, long j2, StreamProgress streamProgress) {
        this.bufferSize = i2 <= 0 ? 8192 : i2;
        this.count = j2 <= 0 ? Long.MAX_VALUE : j2;
        this.progress = streamProgress;
    }

    public int bufferSize(long j2) {
        return (int) Math.min(this.bufferSize, j2);
    }

    public abstract long copy(S s2, T t2);

    public IoCopier<S, T> setFlushEveryBuffer(boolean z2) {
        this.flushEveryBuffer = z2;
        return this;
    }
}
