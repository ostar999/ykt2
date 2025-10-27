package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public final class CacheStats {
    private final long evictionCount;
    private final long hitCount;
    private final long loadExceptionCount;
    private final long loadSuccessCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(long j2, long j3, long j4, long j5, long j6, long j7) {
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 >= 0);
        Preconditions.checkArgument(j7 >= 0);
        this.hitCount = j2;
        this.missCount = j3;
        this.loadSuccessCount = j4;
        this.loadExceptionCount = j5;
        this.totalLoadTime = j6;
        this.evictionCount = j7;
    }

    public double averageLoadPenalty() {
        long j2 = this.loadSuccessCount + this.loadExceptionCount;
        if (j2 == 0) {
            return 0.0d;
        }
        return this.totalLoadTime / j2;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof CacheStats)) {
            return false;
        }
        CacheStats cacheStats = (CacheStats) obj;
        return this.hitCount == cacheStats.hitCount && this.missCount == cacheStats.missCount && this.loadSuccessCount == cacheStats.loadSuccessCount && this.loadExceptionCount == cacheStats.loadExceptionCount && this.totalLoadTime == cacheStats.totalLoadTime && this.evictionCount == cacheStats.evictionCount;
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount));
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long jRequestCount = requestCount();
        if (jRequestCount == 0) {
            return 1.0d;
        }
        return this.hitCount / jRequestCount;
    }

    public long loadCount() {
        return this.loadSuccessCount + this.loadExceptionCount;
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long j2 = this.loadSuccessCount;
        long j3 = this.loadExceptionCount;
        long j4 = j2 + j3;
        if (j4 == 0) {
            return 0.0d;
        }
        return j3 / j4;
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public CacheStats minus(CacheStats cacheStats) {
        return new CacheStats(Math.max(0L, this.hitCount - cacheStats.hitCount), Math.max(0L, this.missCount - cacheStats.missCount), Math.max(0L, this.loadSuccessCount - cacheStats.loadSuccessCount), Math.max(0L, this.loadExceptionCount - cacheStats.loadExceptionCount), Math.max(0L, this.totalLoadTime - cacheStats.totalLoadTime), Math.max(0L, this.evictionCount - cacheStats.evictionCount));
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long jRequestCount = requestCount();
        if (jRequestCount == 0) {
            return 0.0d;
        }
        return this.missCount / jRequestCount;
    }

    public CacheStats plus(CacheStats cacheStats) {
        return new CacheStats(this.hitCount + cacheStats.hitCount, this.missCount + cacheStats.missCount, this.loadSuccessCount + cacheStats.loadSuccessCount, this.loadExceptionCount + cacheStats.loadExceptionCount, this.totalLoadTime + cacheStats.totalLoadTime, this.evictionCount + cacheStats.evictionCount);
    }

    public long requestCount() {
        return this.hitCount + this.missCount;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }
}
