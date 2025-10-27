package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseTrackSelection implements ExoTrackSelection {
    private final long[] excludeUntilTimes;
    private final Format[] formats;
    protected final TrackGroup group;
    private int hashCode;
    protected final int length;
    protected final int[] tracks;
    private final int type;

    public BaseTrackSelection(TrackGroup trackGroup, int... iArr) {
        this(trackGroup, iArr, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(Format format, Format format2) {
        return format2.bitrate - format.bitrate;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public boolean blacklist(int i2, long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        boolean zIsBlacklisted = isBlacklisted(i2, jElapsedRealtime);
        int i3 = 0;
        while (i3 < this.length && !zIsBlacklisted) {
            zIsBlacklisted = (i3 == i2 || isBlacklisted(i3, jElapsedRealtime)) ? false : true;
            i3++;
        }
        if (!zIsBlacklisted) {
            return false;
        }
        long[] jArr = this.excludeUntilTimes;
        jArr[i2] = Math.max(jArr[i2], Util.addWithOverflowDefault(jElapsedRealtime, j2, Long.MAX_VALUE));
        return true;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void disable() {
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void enable() {
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BaseTrackSelection baseTrackSelection = (BaseTrackSelection) obj;
        return this.group == baseTrackSelection.group && Arrays.equals(this.tracks, baseTrackSelection.tracks);
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public int evaluateQueueSize(long j2, List<? extends MediaChunk> list) {
        return list.size();
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final Format getFormat(int i2) {
        return this.formats[i2];
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int getIndexInTrackGroup(int i2) {
        return this.tracks[i2];
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public final Format getSelectedFormat() {
        return this.formats[getSelectedIndex()];
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public final int getSelectedIndexInTrackGroup() {
        return this.tracks[getSelectedIndex()];
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final TrackGroup getTrackGroup() {
        return this.group;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = (System.identityHashCode(this.group) * 31) + Arrays.hashCode(this.tracks);
        }
        return this.hashCode;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int indexOf(Format format) {
        for (int i2 = 0; i2 < this.length; i2++) {
            if (this.formats[i2] == format) {
                return i2;
            }
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public boolean isBlacklisted(int i2, long j2) {
        return this.excludeUntilTimes[i2] > j2;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int length() {
        return this.tracks.length;
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public /* synthetic */ void onDiscontinuity() {
        f.a(this);
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public /* synthetic */ void onPlayWhenReadyChanged(boolean z2) {
        f.b(this, z2);
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public void onPlaybackSpeed(float f2) {
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public /* synthetic */ void onRebuffer() {
        f.c(this);
    }

    @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
    public /* synthetic */ boolean shouldCancelChunkLoad(long j2, Chunk chunk, List list) {
        return f.d(this, j2, chunk, list);
    }

    public BaseTrackSelection(TrackGroup trackGroup, int[] iArr, int i2) {
        int i3 = 0;
        Assertions.checkState(iArr.length > 0);
        this.type = i2;
        this.group = (TrackGroup) Assertions.checkNotNull(trackGroup);
        int length = iArr.length;
        this.length = length;
        this.formats = new Format[length];
        for (int i4 = 0; i4 < iArr.length; i4++) {
            this.formats[i4] = trackGroup.getFormat(iArr[i4]);
        }
        Arrays.sort(this.formats, new Comparator() { // from class: com.google.android.exoplayer2.trackselection.a
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BaseTrackSelection.lambda$new$0((Format) obj, (Format) obj2);
            }
        });
        this.tracks = new int[this.length];
        while (true) {
            int i5 = this.length;
            if (i3 >= i5) {
                this.excludeUntilTimes = new long[i5];
                return;
            } else {
                this.tracks[i3] = trackGroup.indexOf(this.formats[i3]);
                i3++;
            }
        }
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public final int indexOf(int i2) {
        for (int i3 = 0; i3 < this.length; i3++) {
            if (this.tracks[i3] == i2) {
                return i3;
            }
        }
        return -1;
    }
}
