package com.google.android.exoplayer2.transformer;

import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class MuxerWrapper {
    private static final long MAX_TRACK_WRITE_AHEAD_US = Util.msToUs(500);
    private final String containerMimeType;
    private boolean isReady;
    private long minTrackTimeUs;
    private final Muxer muxer;
    private final Muxer.Factory muxerFactory;
    private int trackCount;
    private int trackFormatCount;
    private final SparseIntArray trackTypeToIndex = new SparseIntArray();
    private final SparseLongArray trackTypeToTimeUs = new SparseLongArray();
    private int previousTrackType = -2;

    public MuxerWrapper(Muxer muxer, Muxer.Factory factory, String str) {
        this.muxer = muxer;
        this.muxerFactory = factory;
        this.containerMimeType = str;
    }

    private boolean canWriteSampleOfType(int i2) {
        long j2 = this.trackTypeToTimeUs.get(i2, C.TIME_UNSET);
        Assertions.checkState(j2 != C.TIME_UNSET);
        if (!this.isReady) {
            return false;
        }
        if (this.trackTypeToTimeUs.size() == 1) {
            return true;
        }
        if (i2 != this.previousTrackType) {
            this.minTrackTimeUs = Util.minValue(this.trackTypeToTimeUs);
        }
        return j2 - this.minTrackTimeUs <= MAX_TRACK_WRITE_AHEAD_US;
    }

    public void addTrackFormat(Format format) {
        Assertions.checkState(this.trackCount > 0, "All tracks should be registered before the formats are added.");
        Assertions.checkState(this.trackFormatCount < this.trackCount, "All track formats have already been added.");
        String str = format.sampleMimeType;
        boolean z2 = MimeTypes.isAudio(str) || MimeTypes.isVideo(str);
        String strValueOf = String.valueOf(str);
        Assertions.checkState(z2, strValueOf.length() != 0 ? "Unsupported track format: ".concat(strValueOf) : new String("Unsupported track format: "));
        int trackType = MimeTypes.getTrackType(str);
        boolean z3 = this.trackTypeToIndex.get(trackType, -1) == -1;
        StringBuilder sb = new StringBuilder(44);
        sb.append("There is already a track of type ");
        sb.append(trackType);
        Assertions.checkState(z3, sb.toString());
        this.trackTypeToIndex.put(trackType, this.muxer.addTrack(format));
        this.trackTypeToTimeUs.put(trackType, 0L);
        int i2 = this.trackFormatCount + 1;
        this.trackFormatCount = i2;
        if (i2 == this.trackCount) {
            this.isReady = true;
        }
    }

    public void endTrack(int i2) {
        this.trackTypeToIndex.delete(i2);
        this.trackTypeToTimeUs.delete(i2);
    }

    public int getTrackCount() {
        return this.trackCount;
    }

    public void registerTrack() {
        Assertions.checkState(this.trackFormatCount == 0, "Tracks cannot be registered after track formats have been added.");
        this.trackCount++;
    }

    public void release(boolean z2) {
        this.isReady = false;
        this.muxer.release(z2);
    }

    public boolean supportsSampleMimeType(@Nullable String str) {
        return this.muxerFactory.supportsSampleMimeType(str, this.containerMimeType);
    }

    public boolean writeSample(int i2, @Nullable ByteBuffer byteBuffer, boolean z2, long j2) {
        int i3 = this.trackTypeToIndex.get(i2, -1);
        boolean z3 = i3 != -1;
        StringBuilder sb = new StringBuilder(68);
        sb.append("Could not write sample because there is no track of type ");
        sb.append(i2);
        Assertions.checkState(z3, sb.toString());
        if (!canWriteSampleOfType(i2)) {
            return false;
        }
        if (byteBuffer == null) {
            return true;
        }
        this.muxer.writeSampleData(i3, byteBuffer, z2, j2);
        this.trackTypeToTimeUs.put(i2, j2);
        this.previousTrackType = i2;
        return true;
    }
}
