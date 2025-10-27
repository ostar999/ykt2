package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import java.util.List;

/* loaded from: classes3.dex */
public interface ExoTrackSelection extends TrackSelection {

    public static final class Definition {
        public final TrackGroup group;
        public final int[] tracks;
        public final int type;

        public Definition(TrackGroup trackGroup, int... iArr) {
            this(trackGroup, iArr, 0);
        }

        public Definition(TrackGroup trackGroup, int[] iArr, int i2) {
            this.group = trackGroup;
            this.tracks = iArr;
            this.type = i2;
        }
    }

    public interface Factory {
        ExoTrackSelection[] createTrackSelections(Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline);
    }

    boolean blacklist(int i2, long j2);

    void disable();

    void enable();

    int evaluateQueueSize(long j2, List<? extends MediaChunk> list);

    Format getSelectedFormat();

    int getSelectedIndex();

    int getSelectedIndexInTrackGroup();

    @Nullable
    Object getSelectionData();

    int getSelectionReason();

    boolean isBlacklisted(int i2, long j2);

    void onDiscontinuity();

    void onPlayWhenReadyChanged(boolean z2);

    void onPlaybackSpeed(float f2);

    void onRebuffer();

    boolean shouldCancelChunkLoad(long j2, Chunk chunk, List<? extends MediaChunk> list);

    void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr);
}
