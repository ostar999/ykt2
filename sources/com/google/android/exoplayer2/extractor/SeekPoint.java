package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;

/* loaded from: classes3.dex */
public final class SeekPoint {
    public static final SeekPoint START = new SeekPoint(0, 0);
    public final long position;
    public final long timeUs;

    public SeekPoint(long j2, long j3) {
        this.timeUs = j2;
        this.position = j3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SeekPoint.class != obj.getClass()) {
            return false;
        }
        SeekPoint seekPoint = (SeekPoint) obj;
        return this.timeUs == seekPoint.timeUs && this.position == seekPoint.position;
    }

    public int hashCode() {
        return (((int) this.timeUs) * 31) + ((int) this.position);
    }

    public String toString() {
        long j2 = this.timeUs;
        long j3 = this.position;
        StringBuilder sb = new StringBuilder(60);
        sb.append("[timeUs=");
        sb.append(j2);
        sb.append(", position=");
        sb.append(j3);
        sb.append(StrPool.BRACKET_END);
        return sb.toString();
    }
}
