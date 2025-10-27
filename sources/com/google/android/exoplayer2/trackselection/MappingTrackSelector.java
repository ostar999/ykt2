package com.google.android.exoplayer2.trackselection;

import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.b2;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class MappingTrackSelector extends TrackSelector {

    @Nullable
    private MappedTrackInfo currentMappedTrackInfo;

    @VisibleForTesting
    public static TracksInfo buildTracksInfo(TrackSelection[] trackSelectionArr, MappedTrackInfo mappedTrackInfo) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i2 = 0; i2 < mappedTrackInfo.getRendererCount(); i2++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
            TrackSelection trackSelection = trackSelectionArr[i2];
            for (int i3 = 0; i3 < trackGroups.length; i3++) {
                TrackGroup trackGroup = trackGroups.get(i3);
                int i4 = trackGroup.length;
                int[] iArr = new int[i4];
                boolean[] zArr = new boolean[i4];
                for (int i5 = 0; i5 < trackGroup.length; i5++) {
                    iArr[i5] = mappedTrackInfo.getTrackSupport(i2, i3, i5);
                    zArr[i5] = (trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i5) == -1) ? false : true;
                }
                builder.add((ImmutableList.Builder) new TracksInfo.TrackGroupInfo(trackGroup, iArr, mappedTrackInfo.getRendererType(i2), zArr));
            }
        }
        TrackGroupArray unmappedTrackGroups = mappedTrackInfo.getUnmappedTrackGroups();
        for (int i6 = 0; i6 < unmappedTrackGroups.length; i6++) {
            TrackGroup trackGroup2 = unmappedTrackGroups.get(i6);
            int[] iArr2 = new int[trackGroup2.length];
            Arrays.fill(iArr2, 0);
            builder.add((ImmutableList.Builder) new TracksInfo.TrackGroupInfo(trackGroup2, iArr2, MimeTypes.getTrackType(trackGroup2.getFormat(0).sampleMimeType), new boolean[trackGroup2.length]));
        }
        return new TracksInfo(builder.build());
    }

    private static int findRenderer(RendererCapabilities[] rendererCapabilitiesArr, TrackGroup trackGroup, int[] iArr, boolean z2) throws ExoPlaybackException {
        int length = rendererCapabilitiesArr.length;
        int i2 = 0;
        boolean z3 = true;
        for (int i3 = 0; i3 < rendererCapabilitiesArr.length; i3++) {
            RendererCapabilities rendererCapabilities = rendererCapabilitiesArr[i3];
            int iMax = 0;
            for (int i4 = 0; i4 < trackGroup.length; i4++) {
                iMax = Math.max(iMax, b2.d(rendererCapabilities.supportsFormat(trackGroup.getFormat(i4))));
            }
            boolean z4 = iArr[i3] == 0;
            if (iMax > i2 || (iMax == i2 && z2 && !z3 && z4)) {
                length = i3;
                z3 = z4;
                i2 = iMax;
            }
        }
        return length;
    }

    private static int[] getFormatSupport(RendererCapabilities rendererCapabilities, TrackGroup trackGroup) throws ExoPlaybackException {
        int[] iArr = new int[trackGroup.length];
        for (int i2 = 0; i2 < trackGroup.length; i2++) {
            iArr[i2] = rendererCapabilities.supportsFormat(trackGroup.getFormat(i2));
        }
        return iArr;
    }

    private static int[] getMixedMimeTypeAdaptationSupports(RendererCapabilities[] rendererCapabilitiesArr) throws ExoPlaybackException {
        int length = rendererCapabilitiesArr.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = rendererCapabilitiesArr[i2].supportsMixedMimeTypeAdaptation();
        }
        return iArr;
    }

    @Nullable
    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.currentMappedTrackInfo;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final void onSelectionActivated(@Nullable Object obj) {
        this.currentMappedTrackInfo = (MappedTrackInfo) obj;
    }

    public abstract Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException;

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        int[] iArr = new int[rendererCapabilitiesArr.length + 1];
        int length = rendererCapabilitiesArr.length + 1;
        TrackGroup[][] trackGroupArr = new TrackGroup[length][];
        int[][][] iArr2 = new int[rendererCapabilitiesArr.length + 1][][];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = trackGroupArray.length;
            trackGroupArr[i2] = new TrackGroup[i3];
            iArr2[i2] = new int[i3][];
        }
        int[] mixedMimeTypeAdaptationSupports = getMixedMimeTypeAdaptationSupports(rendererCapabilitiesArr);
        for (int i4 = 0; i4 < trackGroupArray.length; i4++) {
            TrackGroup trackGroup = trackGroupArray.get(i4);
            int iFindRenderer = findRenderer(rendererCapabilitiesArr, trackGroup, iArr, MimeTypes.getTrackType(trackGroup.getFormat(0).sampleMimeType) == 5);
            int[] formatSupport = iFindRenderer == rendererCapabilitiesArr.length ? new int[trackGroup.length] : getFormatSupport(rendererCapabilitiesArr[iFindRenderer], trackGroup);
            int i5 = iArr[iFindRenderer];
            trackGroupArr[iFindRenderer][i5] = trackGroup;
            iArr2[iFindRenderer][i5] = formatSupport;
            iArr[iFindRenderer] = i5 + 1;
        }
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilitiesArr.length];
        String[] strArr = new String[rendererCapabilitiesArr.length];
        int[] iArr3 = new int[rendererCapabilitiesArr.length];
        for (int i6 = 0; i6 < rendererCapabilitiesArr.length; i6++) {
            int i7 = iArr[i6];
            trackGroupArrayArr[i6] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[i6], i7));
            iArr2[i6] = (int[][]) Util.nullSafeArrayCopy(iArr2[i6], i7);
            strArr[i6] = rendererCapabilitiesArr[i6].getName();
            iArr3[i6] = rendererCapabilitiesArr[i6].getTrackType();
        }
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(strArr, iArr3, trackGroupArrayArr, mixedMimeTypeAdaptationSupports, iArr2, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(trackGroupArr[rendererCapabilitiesArr.length], iArr[rendererCapabilitiesArr.length])));
        Pair<RendererConfiguration[], ExoTrackSelection[]> pairSelectTracks = selectTracks(mappedTrackInfo, iArr2, mixedMimeTypeAdaptationSupports, mediaPeriodId, timeline);
        return new TrackSelectorResult((RendererConfiguration[]) pairSelectTracks.first, (ExoTrackSelection[]) pairSelectTracks.second, buildTracksInfo((TrackSelection[]) pairSelectTracks.second, mappedTrackInfo), mappedTrackInfo);
    }

    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        private final int rendererCount;
        private final int[][][] rendererFormatSupports;
        private final int[] rendererMixedMimeTypeAdaptiveSupports;
        private final String[] rendererNames;
        private final TrackGroupArray[] rendererTrackGroups;
        private final int[] rendererTrackTypes;
        private final TrackGroupArray unmappedTrackGroups;

        @VisibleForTesting
        public MappedTrackInfo(String[] strArr, int[] iArr, TrackGroupArray[] trackGroupArrayArr, int[] iArr2, int[][][] iArr3, TrackGroupArray trackGroupArray) {
            this.rendererNames = strArr;
            this.rendererTrackTypes = iArr;
            this.rendererTrackGroups = trackGroupArrayArr;
            this.rendererFormatSupports = iArr3;
            this.rendererMixedMimeTypeAdaptiveSupports = iArr2;
            this.unmappedTrackGroups = trackGroupArray;
            this.rendererCount = iArr.length;
        }

        public int getAdaptiveSupport(int i2, int i3, boolean z2) {
            int i4 = this.rendererTrackGroups[i2].get(i3).length;
            int[] iArr = new int[i4];
            int i5 = 0;
            for (int i6 = 0; i6 < i4; i6++) {
                int trackSupport = getTrackSupport(i2, i3, i6);
                if (trackSupport == 4 || (z2 && trackSupport == 3)) {
                    iArr[i5] = i6;
                    i5++;
                }
            }
            return getAdaptiveSupport(i2, i3, Arrays.copyOf(iArr, i5));
        }

        public int getRendererCount() {
            return this.rendererCount;
        }

        public String getRendererName(int i2) {
            return this.rendererNames[i2];
        }

        public int getRendererSupport(int i2) {
            int iMax = 0;
            for (int[] iArr : this.rendererFormatSupports[i2]) {
                for (int i3 : iArr) {
                    int iD = b2.d(i3);
                    int i4 = 1;
                    if (iD != 0 && iD != 1 && iD != 2) {
                        if (iD != 3) {
                            if (iD == 4) {
                                return 3;
                            }
                            throw new IllegalStateException();
                        }
                        i4 = 2;
                    }
                    iMax = Math.max(iMax, i4);
                }
            }
            return iMax;
        }

        public int getRendererType(int i2) {
            return this.rendererTrackTypes[i2];
        }

        public TrackGroupArray getTrackGroups(int i2) {
            return this.rendererTrackGroups[i2];
        }

        public int getTrackSupport(int i2, int i3, int i4) {
            return b2.d(this.rendererFormatSupports[i2][i3][i4]);
        }

        public int getTypeSupport(int i2) {
            int iMax = 0;
            for (int i3 = 0; i3 < this.rendererCount; i3++) {
                if (this.rendererTrackTypes[i3] == i2) {
                    iMax = Math.max(iMax, getRendererSupport(i3));
                }
            }
            return iMax;
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.unmappedTrackGroups;
        }

        public int getAdaptiveSupport(int i2, int i3, int[] iArr) {
            int i4 = 0;
            int iMin = 16;
            String str = null;
            boolean z2 = false;
            int i5 = 0;
            while (i4 < iArr.length) {
                String str2 = this.rendererTrackGroups[i2].get(i3).getFormat(iArr[i4]).sampleMimeType;
                int i6 = i5 + 1;
                if (i5 == 0) {
                    str = str2;
                } else {
                    z2 |= !Util.areEqual(str, str2);
                }
                iMin = Math.min(iMin, b2.c(this.rendererFormatSupports[i2][i3][i4]));
                i4++;
                i5 = i6;
            }
            return z2 ? Math.min(iMin, this.rendererMixedMimeTypeAdaptiveSupports[i2]) : iMin;
        }
    }
}
