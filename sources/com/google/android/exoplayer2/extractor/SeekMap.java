package com.google.android.exoplayer2.extractor;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public interface SeekMap {

    public static final class SeekPoints {
        public final SeekPoint first;
        public final SeekPoint second;

        public SeekPoints(SeekPoint seekPoint) {
            this(seekPoint, seekPoint);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SeekPoints.class != obj.getClass()) {
                return false;
            }
            SeekPoints seekPoints = (SeekPoints) obj;
            return this.first.equals(seekPoints.first) && this.second.equals(seekPoints.second);
        }

        public int hashCode() {
            return (this.first.hashCode() * 31) + this.second.hashCode();
        }

        public String toString() {
            String string;
            String strValueOf = String.valueOf(this.first);
            if (this.first.equals(this.second)) {
                string = "";
            } else {
                String strValueOf2 = String.valueOf(this.second);
                StringBuilder sb = new StringBuilder(strValueOf2.length() + 2);
                sb.append(", ");
                sb.append(strValueOf2);
                string = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder(strValueOf.length() + 2 + String.valueOf(string).length());
            sb2.append(StrPool.BRACKET_START);
            sb2.append(strValueOf);
            sb2.append(string);
            sb2.append(StrPool.BRACKET_END);
            return sb2.toString();
        }

        public SeekPoints(SeekPoint seekPoint, SeekPoint seekPoint2) {
            this.first = (SeekPoint) Assertions.checkNotNull(seekPoint);
            this.second = (SeekPoint) Assertions.checkNotNull(seekPoint2);
        }
    }

    public static class Unseekable implements SeekMap {
        private final long durationUs;
        private final SeekPoints startSeekPoints;

        public Unseekable(long j2) {
            this(j2, 0L);
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return this.durationUs;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekPoints getSeekPoints(long j2) {
            return this.startSeekPoints;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return false;
        }

        public Unseekable(long j2, long j3) {
            this.durationUs = j2;
            this.startSeekPoints = new SeekPoints(j3 == 0 ? SeekPoint.START : new SeekPoint(0L, j3));
        }
    }

    long getDurationUs();

    SeekPoints getSeekPoints(long j2);

    boolean isSeekable();
}
