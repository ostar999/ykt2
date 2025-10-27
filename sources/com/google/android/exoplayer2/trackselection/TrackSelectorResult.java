package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public final class TrackSelectorResult {

    @Nullable
    public final Object info;
    public final int length;
    public final RendererConfiguration[] rendererConfigurations;
    public final ExoTrackSelection[] selections;
    public final TracksInfo tracksInfo;

    @Deprecated
    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, @Nullable Object obj) {
        this(rendererConfigurationArr, exoTrackSelectionArr, TracksInfo.EMPTY, obj);
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult) {
        if (trackSelectorResult == null || trackSelectorResult.selections.length != this.selections.length) {
            return false;
        }
        for (int i2 = 0; i2 < this.selections.length; i2++) {
            if (!isEquivalent(trackSelectorResult, i2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRendererEnabled(int i2) {
        return this.rendererConfigurations[i2] != null;
    }

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, TracksInfo tracksInfo, @Nullable Object obj) {
        this.rendererConfigurations = rendererConfigurationArr;
        this.selections = (ExoTrackSelection[]) exoTrackSelectionArr.clone();
        this.tracksInfo = tracksInfo;
        this.info = obj;
        this.length = rendererConfigurationArr.length;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult, int i2) {
        return trackSelectorResult != null && Util.areEqual(this.rendererConfigurations[i2], trackSelectorResult.rendererConfigurations[i2]) && Util.areEqual(this.selections[i2], trackSelectorResult.selections[i2]);
    }
}
