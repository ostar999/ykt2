package com.google.android.exoplayer2.extractor.mp3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;

/* loaded from: classes3.dex */
final class VbriSeeker implements Seeker {
    private static final String TAG = "VbriSeeker";
    private final long dataEndPosition;
    private final long durationUs;
    private final long[] positions;
    private final long[] timesUs;

    private VbriSeeker(long[] jArr, long[] jArr2, long j2, long j3) {
        this.timesUs = jArr;
        this.positions = jArr2;
        this.durationUs = j2;
        this.dataEndPosition = j3;
    }

    @Nullable
    public static VbriSeeker create(long j2, long j3, MpegAudioUtil.Header header, ParsableByteArray parsableByteArray) {
        int unsignedByte;
        parsableByteArray.skipBytes(10);
        int i2 = parsableByteArray.readInt();
        if (i2 <= 0) {
            return null;
        }
        int i3 = header.sampleRate;
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(i2, (i3 >= 32000 ? R2.attr.contrast : R2.attr.bl_enabled_gradient_centerColor) * 1000000, i3);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        int unsignedShort3 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        long j4 = j3 + header.frameSize;
        long[] jArr = new long[unsignedShort];
        long[] jArr2 = new long[unsignedShort];
        int i4 = 0;
        long j5 = j3;
        while (i4 < unsignedShort) {
            int i5 = unsignedShort2;
            long j6 = j4;
            jArr[i4] = (i4 * jScaleLargeTimestamp) / unsignedShort;
            jArr2[i4] = Math.max(j5, j6);
            if (unsignedShort3 == 1) {
                unsignedByte = parsableByteArray.readUnsignedByte();
            } else if (unsignedShort3 == 2) {
                unsignedByte = parsableByteArray.readUnsignedShort();
            } else if (unsignedShort3 == 3) {
                unsignedByte = parsableByteArray.readUnsignedInt24();
            } else {
                if (unsignedShort3 != 4) {
                    return null;
                }
                unsignedByte = parsableByteArray.readUnsignedIntToInt();
            }
            j5 += unsignedByte * i5;
            i4++;
            j4 = j6;
            unsignedShort2 = i5;
        }
        if (j2 != -1 && j2 != j5) {
            StringBuilder sb = new StringBuilder(67);
            sb.append("VBRI data size mismatch: ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j5);
            Log.w(TAG, sb.toString());
        }
        return new VbriSeeker(jArr, jArr2, jScaleLargeTimestamp, j5);
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
        int iBinarySearchFloor = Util.binarySearchFloor(this.timesUs, j2, true, true);
        SeekPoint seekPoint = new SeekPoint(this.timesUs[iBinarySearchFloor], this.positions[iBinarySearchFloor]);
        if (seekPoint.timeUs >= j2 || iBinarySearchFloor == this.timesUs.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i2 = iBinarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(this.timesUs[i2], this.positions[i2]));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return this.timesUs[Util.binarySearchFloor(this.positions, j2, true, true)];
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }
}
