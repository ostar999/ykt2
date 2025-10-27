package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;

/* loaded from: classes3.dex */
public final class TrackSelectionUtil {

    public interface AdaptiveTrackSelectionFactory {
        ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition);
    }

    private TrackSelectionUtil() {
    }

    public static LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (exoTrackSelection.isBlacklisted(i3, jElapsedRealtime)) {
                i2++;
            }
        }
        return new LoadErrorHandlingPolicy.FallbackOptions(1, 0, length, i2);
    }

    public static ExoTrackSelection[] createTrackSelectionsForDefinitions(ExoTrackSelection.Definition[] definitionArr, AdaptiveTrackSelectionFactory adaptiveTrackSelectionFactory) {
        ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
        boolean z2 = false;
        for (int i2 = 0; i2 < definitionArr.length; i2++) {
            ExoTrackSelection.Definition definition = definitionArr[i2];
            if (definition != null) {
                int[] iArr = definition.tracks;
                if (iArr.length <= 1 || z2) {
                    exoTrackSelectionArr[i2] = new FixedTrackSelection(definition.group, iArr[0], definition.type);
                } else {
                    exoTrackSelectionArr[i2] = adaptiveTrackSelectionFactory.createAdaptiveTrackSelection(definition);
                    z2 = true;
                }
            }
        }
        return exoTrackSelectionArr;
    }

    public static DefaultTrackSelector.Parameters updateParametersWithOverride(DefaultTrackSelector.Parameters parameters, int i2, TrackGroupArray trackGroupArray, boolean z2, @Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        DefaultTrackSelector.ParametersBuilder rendererDisabled = parameters.buildUpon().clearSelectionOverrides(i2).setRendererDisabled(i2, z2);
        if (selectionOverride != null) {
            rendererDisabled.setSelectionOverride(i2, trackGroupArray, selectionOverride);
        }
        return rendererDisabled.build();
    }
}
