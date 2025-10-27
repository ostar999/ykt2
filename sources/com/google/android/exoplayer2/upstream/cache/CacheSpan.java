package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import java.io.File;

/* loaded from: classes3.dex */
public class CacheSpan implements Comparable<CacheSpan> {

    @Nullable
    public final File file;
    public final boolean isCached;
    public final String key;
    public final long lastTouchTimestamp;
    public final long length;
    public final long position;

    public CacheSpan(String str, long j2, long j3) {
        this(str, j2, j3, C.TIME_UNSET, null);
    }

    public boolean isHoleSpan() {
        return !this.isCached;
    }

    public boolean isOpenEnded() {
        return this.length == -1;
    }

    public String toString() {
        long j2 = this.position;
        long j3 = this.length;
        StringBuilder sb = new StringBuilder(44);
        sb.append(StrPool.BRACKET_START);
        sb.append(j2);
        sb.append(", ");
        sb.append(j3);
        sb.append(StrPool.BRACKET_END);
        return sb.toString();
    }

    public CacheSpan(String str, long j2, long j3, long j4, @Nullable File file) {
        this.key = str;
        this.position = j2;
        this.length = j3;
        this.isCached = file != null;
        this.file = file;
        this.lastTouchTimestamp = j4;
    }

    @Override // java.lang.Comparable
    public int compareTo(CacheSpan cacheSpan) {
        if (!this.key.equals(cacheSpan.key)) {
            return this.key.compareTo(cacheSpan.key);
        }
        long j2 = this.position - cacheSpan.position;
        if (j2 == 0) {
            return 0;
        }
        return j2 < 0 ? -1 : 1;
    }
}
