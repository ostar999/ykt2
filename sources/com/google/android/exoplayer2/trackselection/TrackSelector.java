package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes3.dex */
public abstract class TrackSelector {

    @Nullable
    private BandwidthMeter bandwidthMeter;

    @Nullable
    private InvalidationListener listener;

    public interface InvalidationListener {
        void onTrackSelectionsInvalidated();
    }

    public final BandwidthMeter getBandwidthMeter() {
        return (BandwidthMeter) Assertions.checkNotNull(this.bandwidthMeter);
    }

    public TrackSelectionParameters getParameters() {
        return TrackSelectionParameters.DEFAULT_WITHOUT_CONTEXT;
    }

    public final void init(InvalidationListener invalidationListener, BandwidthMeter bandwidthMeter) {
        this.listener = invalidationListener;
        this.bandwidthMeter = bandwidthMeter;
    }

    public final void invalidate() {
        InvalidationListener invalidationListener = this.listener;
        if (invalidationListener != null) {
            invalidationListener.onTrackSelectionsInvalidated();
        }
    }

    public boolean isSetParametersSupported() {
        return false;
    }

    public abstract void onSelectionActivated(@Nullable Object obj);

    public abstract TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    public void setParameters(TrackSelectionParameters trackSelectionParameters) {
    }
}
