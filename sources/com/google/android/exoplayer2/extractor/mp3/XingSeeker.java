package com.google.android.exoplayer2.extractor.mp3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
final class XingSeeker implements Seeker {
    private static final String TAG = "XingSeeker";
    private final long dataEndPosition;
    private final long dataSize;
    private final long dataStartPosition;
    private final long durationUs;

    @Nullable
    private final long[] tableOfContents;
    private final int xingFrameSize;

    private XingSeeker(long j2, int i2, long j3) {
        this(j2, i2, j3, -1L, null);
    }

    @Nullable
    public static XingSeeker create(long j2, long j3, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int unsignedIntToInt;
        int i2 = header.samplesPerFrame;
        int i3 = header.sampleRate;
        int i4 = parsableByteArray.readInt();
        if ((i4 & 1) != 1 || (unsignedIntToInt = parsableByteArray.readUnsignedIntToInt()) == 0) {
            return null;
        }
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(unsignedIntToInt, i2 * 1000000, i3);
        if ((i4 & 6) != 6) {
            return new XingSeeker(j3, header.frameSize, jScaleLargeTimestamp);
        }
        long unsignedInt = parsableByteArray.readUnsignedInt();
        long[] jArr = new long[100];
        for (int i5 = 0; i5 < 100; i5++) {
            jArr[i5] = parsableByteArray.readUnsignedByte();
        }
        if (j2 != -1) {
            long j4 = j3 + unsignedInt;
            if (j2 != j4) {
                StringBuilder sb = new StringBuilder(67);
                sb.append("XING data size mismatch: ");
                sb.append(j2);
                sb.append(", ");
                sb.append(j4);
                Log.w(TAG, sb.toString());
            }
        }
        return new XingSeeker(j3, header.frameSize, jScaleLargeTimestamp, unsignedInt, jArr);
    }

    private long getTimeUsForTableIndex(int i2) {
        return (this.durationUs * i2) / 100;
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return this.dataEndPosition;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long j2) {
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.dataStartPosition + this.xingFrameSize));
        }
        long jConstrainValue = Util.constrainValue(j2, 0L, this.durationUs);
        double d3 = (jConstrainValue * 100.0d) / this.durationUs;
        double d4 = 0.0d;
        if (d3 > 0.0d) {
            if (d3 >= 100.0d) {
                d4 = 256.0d;
            } else {
                int i2 = (int) d3;
                double d5 = ((long[]) Assertions.checkStateNotNull(this.tableOfContents))[i2];
                d4 = d5 + ((d3 - i2) * ((i2 == 99 ? 256.0d : r3[i2 + 1]) - d5));
            }
        }
        return new SeekMap.SeekPoints(new SeekPoint(jConstrainValue, this.dataStartPosition + Util.constrainValue(Math.round((d4 / 256.0d) * this.dataSize), this.xingFrameSize, this.dataSize - 1)));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        long j3 = j2 - this.dataStartPosition;
        if (!isSeekable() || j3 <= this.xingFrameSize) {
            return 0L;
        }
        long[] jArr = (long[]) Assertions.checkStateNotNull(this.tableOfContents);
        double d3 = (j3 * 256.0d) / this.dataSize;
        int iBinarySearchFloor = Util.binarySearchFloor(jArr, (long) d3, true, true);
        long timeUsForTableIndex = getTimeUsForTableIndex(iBinarySearchFloor);
        long j4 = jArr[iBinarySearchFloor];
        int i2 = iBinarySearchFloor + 1;
        long timeUsForTableIndex2 = getTimeUsForTableIndex(i2);
        return timeUsForTableIndex + Math.round((j4 == (iBinarySearchFloor == 99 ? 256L : jArr[i2]) ? 0.0d : (d3 - j4) / (r0 - j4)) * (timeUsForTableIndex2 - timeUsForTableIndex));
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    private XingSeeker(long j2, int i2, long j3, long j4, @Nullable long[] jArr) {
        this.dataStartPosition = j2;
        this.xingFrameSize = i2;
        this.durationUs = j3;
        this.tableOfContents = jArr;
        this.dataSize = j4;
        this.dataEndPosition = j4 != -1 ? j2 + j4 : -1L;
    }
}
