package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.b2;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private final AtomicReference<Parameters> parametersReference;
    private final ExoTrackSelection.Factory trackSelectionFactory;
    private static final int[] NO_TRACKS = new int[0];
    private static final Ordering<Integer> FORMAT_VALUE_ORDERING = Ordering.from(new Comparator() { // from class: com.google.android.exoplayer2.trackselection.b
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return DefaultTrackSelector.lambda$static$0((Integer) obj, (Integer) obj2);
        }
    });
    private static final Ordering<Integer> NO_ORDER = Ordering.from(new Comparator() { // from class: com.google.android.exoplayer2.trackselection.c
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return DefaultTrackSelector.lambda$static$1((Integer) obj, (Integer) obj2);
        }
    });

    public static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        private final int bitrate;
        private final int channelCount;
        private final boolean isDefaultSelectionFlag;
        public final boolean isWithinConstraints;
        private final boolean isWithinRendererCapabilities;

        @Nullable
        private final String language;
        private final int localeLanguageMatchIndex;
        private final int localeLanguageScore;
        private final Parameters parameters;
        private final int preferredLanguageIndex;
        private final int preferredLanguageScore;
        private final int preferredMimeTypeMatchIndex;
        private final int preferredRoleFlagsScore;
        private final int sampleRate;

        public AudioTrackScore(Format format, Parameters parameters, int i2) {
            int i3;
            int formatLanguageScore;
            int formatLanguageScore2;
            this.parameters = parameters;
            this.language = DefaultTrackSelector.normalizeUndeterminedLanguageToNull(format.language);
            int i4 = 0;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(i2, false);
            int i5 = 0;
            while (true) {
                i3 = Integer.MAX_VALUE;
                if (i5 >= parameters.preferredAudioLanguages.size()) {
                    formatLanguageScore = 0;
                    i5 = Integer.MAX_VALUE;
                    break;
                } else {
                    formatLanguageScore = DefaultTrackSelector.getFormatLanguageScore(format, parameters.preferredAudioLanguages.get(i5), false);
                    if (formatLanguageScore > 0) {
                        break;
                    } else {
                        i5++;
                    }
                }
            }
            this.preferredLanguageIndex = i5;
            this.preferredLanguageScore = formatLanguageScore;
            this.preferredRoleFlagsScore = Integer.bitCount(format.roleFlags & parameters.preferredAudioRoleFlags);
            boolean z2 = true;
            this.isDefaultSelectionFlag = (format.selectionFlags & 1) != 0;
            int i6 = format.channelCount;
            this.channelCount = i6;
            this.sampleRate = format.sampleRate;
            int i7 = format.bitrate;
            this.bitrate = i7;
            if ((i7 != -1 && i7 > parameters.maxAudioBitrate) || (i6 != -1 && i6 > parameters.maxAudioChannelCount)) {
                z2 = false;
            }
            this.isWithinConstraints = z2;
            String[] systemLanguageCodes = Util.getSystemLanguageCodes();
            int i8 = 0;
            while (true) {
                if (i8 >= systemLanguageCodes.length) {
                    formatLanguageScore2 = 0;
                    i8 = Integer.MAX_VALUE;
                    break;
                } else {
                    formatLanguageScore2 = DefaultTrackSelector.getFormatLanguageScore(format, systemLanguageCodes[i8], false);
                    if (formatLanguageScore2 > 0) {
                        break;
                    } else {
                        i8++;
                    }
                }
            }
            this.localeLanguageMatchIndex = i8;
            this.localeLanguageScore = formatLanguageScore2;
            while (true) {
                if (i4 < parameters.preferredAudioMimeTypes.size()) {
                    String str = format.sampleMimeType;
                    if (str != null && str.equals(parameters.preferredAudioMimeTypes.get(i4))) {
                        i3 = i4;
                        break;
                    }
                    i4++;
                } else {
                    break;
                }
            }
            this.preferredMimeTypeMatchIndex = i3;
        }

        @Override // java.lang.Comparable
        public int compareTo(AudioTrackScore audioTrackScore) {
            Ordering orderingReverse = (this.isWithinConstraints && this.isWithinRendererCapabilities) ? DefaultTrackSelector.FORMAT_VALUE_ORDERING : DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse();
            ComparisonChain comparisonChainCompare = ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, audioTrackScore.isWithinRendererCapabilities).compare(Integer.valueOf(this.preferredLanguageIndex), Integer.valueOf(audioTrackScore.preferredLanguageIndex), Ordering.natural().reverse()).compare(this.preferredLanguageScore, audioTrackScore.preferredLanguageScore).compare(this.preferredRoleFlagsScore, audioTrackScore.preferredRoleFlagsScore).compareFalseFirst(this.isWithinConstraints, audioTrackScore.isWithinConstraints).compare(Integer.valueOf(this.preferredMimeTypeMatchIndex), Integer.valueOf(audioTrackScore.preferredMimeTypeMatchIndex), Ordering.natural().reverse()).compare(Integer.valueOf(this.bitrate), Integer.valueOf(audioTrackScore.bitrate), this.parameters.forceLowestBitrate ? DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse() : DefaultTrackSelector.NO_ORDER).compareFalseFirst(this.isDefaultSelectionFlag, audioTrackScore.isDefaultSelectionFlag).compare(Integer.valueOf(this.localeLanguageMatchIndex), Integer.valueOf(audioTrackScore.localeLanguageMatchIndex), Ordering.natural().reverse()).compare(this.localeLanguageScore, audioTrackScore.localeLanguageScore).compare(Integer.valueOf(this.channelCount), Integer.valueOf(audioTrackScore.channelCount), orderingReverse).compare(Integer.valueOf(this.sampleRate), Integer.valueOf(audioTrackScore.sampleRate), orderingReverse);
            Integer numValueOf = Integer.valueOf(this.bitrate);
            Integer numValueOf2 = Integer.valueOf(audioTrackScore.bitrate);
            if (!Util.areEqual(this.language, audioTrackScore.language)) {
                orderingReverse = DefaultTrackSelector.NO_ORDER;
            }
            return comparisonChainCompare.compare(numValueOf, numValueOf2, orderingReverse).result();
        }
    }

    public static final class OtherTrackScore implements Comparable<OtherTrackScore> {
        private final boolean isDefault;
        private final boolean isWithinRendererCapabilities;

        public OtherTrackScore(Format format, int i2) {
            this.isDefault = (format.selectionFlags & 1) != 0;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(i2, false);
        }

        @Override // java.lang.Comparable
        public int compareTo(OtherTrackScore otherTrackScore) {
            return ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, otherTrackScore.isWithinRendererCapabilities).compareFalseFirst(this.isDefault, otherTrackScore.isDefault).result();
        }
    }

    public static final class Parameters extends TrackSelectionParameters implements Bundleable {
        public static final Bundleable.Creator<Parameters> CREATOR;

        @Deprecated
        public static final Parameters DEFAULT;
        public static final Parameters DEFAULT_WITHOUT_CONTEXT;
        private static final int FIELD_ALLOW_AUDIO_MIXED_CHANNEL_COUNT_ADAPTIVENESS = 1006;
        private static final int FIELD_ALLOW_AUDIO_MIXED_MIME_TYPE_ADAPTIVENESS = 1004;
        private static final int FIELD_ALLOW_AUDIO_MIXED_SAMPLE_RATE_ADAPTIVENESS = 1005;
        private static final int FIELD_ALLOW_MULTIPLE_ADAPTIVE_SELECTIONS = 1010;
        private static final int FIELD_ALLOW_VIDEO_MIXED_MIME_TYPE_ADAPTIVENESS = 1001;
        private static final int FIELD_ALLOW_VIDEO_NON_SEAMLESS_ADAPTIVENESS = 1002;
        private static final int FIELD_DISABLED_TEXT_TRACK_SELECTION_FLAGS = 1007;
        private static final int FIELD_EXCEED_AUDIO_CONSTRAINTS_IF_NCESSARY = 1003;
        private static final int FIELD_EXCEED_RENDERER_CAPABILITIES_IF_NECESSARY = 1008;
        private static final int FIELD_EXCEED_VIDEO_CONSTRAINTS_IF_NECESSARY = 1000;
        private static final int FIELD_RENDERER_DISABLED_INDICES = 1014;
        private static final int FIELD_SELECTION_OVERRIDES = 1013;
        private static final int FIELD_SELECTION_OVERRIDES_RENDERER_INDICES = 1011;
        private static final int FIELD_SELECTION_OVERRIDES_TRACK_GROUP_ARRAYS = 1012;
        private static final int FIELD_TUNNELING_ENABLED = 1009;
        public final boolean allowAudioMixedChannelCountAdaptiveness;
        public final boolean allowAudioMixedMimeTypeAdaptiveness;
        public final boolean allowAudioMixedSampleRateAdaptiveness;
        public final boolean allowMultipleAdaptiveSelections;
        public final boolean allowVideoMixedMimeTypeAdaptiveness;
        public final boolean allowVideoNonSeamlessAdaptiveness;
        public final int disabledTextTrackSelectionFlags;
        public final boolean exceedAudioConstraintsIfNecessary;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        private final SparseBooleanArray rendererDisabledFlags;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        public final boolean tunnelingEnabled;

        static {
            Parameters parametersBuild = new ParametersBuilder().build();
            DEFAULT_WITHOUT_CONTEXT = parametersBuild;
            DEFAULT = parametersBuild;
            CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.trackselection.d
                @Override // com.google.android.exoplayer2.Bundleable.Creator
                public final Bundleable fromBundle(Bundle bundle) {
                    return DefaultTrackSelector.Parameters.lambda$static$0(bundle);
                }
            };
        }

        private static boolean areRendererDisabledFlagsEqual(SparseBooleanArray sparseBooleanArray, SparseBooleanArray sparseBooleanArray2) {
            int size = sparseBooleanArray.size();
            if (sparseBooleanArray2.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (sparseBooleanArray2.indexOfKey(sparseBooleanArray.keyAt(i2)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2) {
            int size = sparseArray.size();
            if (sparseArray2.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                int iIndexOfKey = sparseArray2.indexOfKey(sparseArray.keyAt(i2));
                if (iIndexOfKey < 0 || !areSelectionOverridesEqual(sparseArray.valueAt(i2), sparseArray2.valueAt(iIndexOfKey))) {
                    return false;
                }
            }
            return true;
        }

        public static Parameters getDefaults(Context context) {
            return new ParametersBuilder(context).build();
        }

        private static int[] getKeysFromSparseBooleanArray(SparseBooleanArray sparseBooleanArray) {
            int[] iArr = new int[sparseBooleanArray.size()];
            for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                iArr[i2] = sparseBooleanArray.keyAt(i2);
            }
            return iArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Parameters lambda$static$0(Bundle bundle) {
            return new ParametersBuilder(bundle).build();
        }

        private static void putSelectionOverridesToBundle(Bundle bundle, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            SparseArray sparseArray2 = new SparseArray();
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                int iKeyAt = sparseArray.keyAt(i2);
                for (Map.Entry<TrackGroupArray, SelectionOverride> entry : sparseArray.valueAt(i2).entrySet()) {
                    SelectionOverride value = entry.getValue();
                    if (value != null) {
                        sparseArray2.put(arrayList2.size(), value);
                    }
                    arrayList2.add(entry.getKey());
                    arrayList.add(Integer.valueOf(iKeyAt));
                }
                bundle.putIntArray(keyForField(1011), Ints.toArray(arrayList));
                bundle.putParcelableArrayList(keyForField(1012), BundleableUtil.toBundleArrayList(arrayList2));
                bundle.putSparseParcelableArray(keyForField(1013), BundleableUtil.toBundleSparseArray(sparseArray2));
            }
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || Parameters.class != obj.getClass()) {
                return false;
            }
            Parameters parameters = (Parameters) obj;
            return super.equals(parameters) && this.exceedVideoConstraintsIfNecessary == parameters.exceedVideoConstraintsIfNecessary && this.allowVideoMixedMimeTypeAdaptiveness == parameters.allowVideoMixedMimeTypeAdaptiveness && this.allowVideoNonSeamlessAdaptiveness == parameters.allowVideoNonSeamlessAdaptiveness && this.exceedAudioConstraintsIfNecessary == parameters.exceedAudioConstraintsIfNecessary && this.allowAudioMixedMimeTypeAdaptiveness == parameters.allowAudioMixedMimeTypeAdaptiveness && this.allowAudioMixedSampleRateAdaptiveness == parameters.allowAudioMixedSampleRateAdaptiveness && this.allowAudioMixedChannelCountAdaptiveness == parameters.allowAudioMixedChannelCountAdaptiveness && this.disabledTextTrackSelectionFlags == parameters.disabledTextTrackSelectionFlags && this.exceedRendererCapabilitiesIfNecessary == parameters.exceedRendererCapabilitiesIfNecessary && this.tunnelingEnabled == parameters.tunnelingEnabled && this.allowMultipleAdaptiveSelections == parameters.allowMultipleAdaptiveSelections && areRendererDisabledFlagsEqual(this.rendererDisabledFlags, parameters.rendererDisabledFlags) && areSelectionOverridesEqual(this.selectionOverrides, parameters.selectionOverrides);
        }

        public final boolean getRendererDisabled(int i2) {
            return this.rendererDisabledFlags.get(i2);
        }

        @Nullable
        @Deprecated
        public final SelectionOverride getSelectionOverride(int i2, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(i2);
            if (map != null) {
                return map.get(trackGroupArray);
            }
            return null;
        }

        @Deprecated
        public final boolean hasSelectionOverride(int i2, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(i2);
            return map != null && map.containsKey(trackGroupArray);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public int hashCode() {
            return ((((((((((((((((((((((super.hashCode() + 31) * 31) + (this.exceedVideoConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowVideoMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowVideoNonSeamlessAdaptiveness ? 1 : 0)) * 31) + (this.exceedAudioConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowAudioMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedSampleRateAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedChannelCountAdaptiveness ? 1 : 0)) * 31) + this.disabledTextTrackSelectionFlags) * 31) + (this.exceedRendererCapabilitiesIfNecessary ? 1 : 0)) * 31) + (this.tunnelingEnabled ? 1 : 0)) * 31) + (this.allowMultipleAdaptiveSelections ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters, com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = super.toBundle();
            bundle.putBoolean(keyForField(1000), this.exceedVideoConstraintsIfNecessary);
            bundle.putBoolean(keyForField(1001), this.allowVideoMixedMimeTypeAdaptiveness);
            bundle.putBoolean(keyForField(1002), this.allowVideoNonSeamlessAdaptiveness);
            bundle.putBoolean(keyForField(1003), this.exceedAudioConstraintsIfNecessary);
            bundle.putBoolean(keyForField(1004), this.allowAudioMixedMimeTypeAdaptiveness);
            bundle.putBoolean(keyForField(1005), this.allowAudioMixedSampleRateAdaptiveness);
            bundle.putBoolean(keyForField(1006), this.allowAudioMixedChannelCountAdaptiveness);
            bundle.putInt(keyForField(1007), this.disabledTextTrackSelectionFlags);
            bundle.putBoolean(keyForField(1008), this.exceedRendererCapabilitiesIfNecessary);
            bundle.putBoolean(keyForField(1009), this.tunnelingEnabled);
            bundle.putBoolean(keyForField(1010), this.allowMultipleAdaptiveSelections);
            putSelectionOverridesToBundle(bundle, this.selectionOverrides);
            bundle.putIntArray(keyForField(1014), getKeysFromSparseBooleanArray(this.rendererDisabledFlags));
            return bundle;
        }

        private Parameters(ParametersBuilder parametersBuilder) {
            super(parametersBuilder);
            this.exceedVideoConstraintsIfNecessary = parametersBuilder.exceedVideoConstraintsIfNecessary;
            this.allowVideoMixedMimeTypeAdaptiveness = parametersBuilder.allowVideoMixedMimeTypeAdaptiveness;
            this.allowVideoNonSeamlessAdaptiveness = parametersBuilder.allowVideoNonSeamlessAdaptiveness;
            this.exceedAudioConstraintsIfNecessary = parametersBuilder.exceedAudioConstraintsIfNecessary;
            this.allowAudioMixedMimeTypeAdaptiveness = parametersBuilder.allowAudioMixedMimeTypeAdaptiveness;
            this.allowAudioMixedSampleRateAdaptiveness = parametersBuilder.allowAudioMixedSampleRateAdaptiveness;
            this.allowAudioMixedChannelCountAdaptiveness = parametersBuilder.allowAudioMixedChannelCountAdaptiveness;
            this.disabledTextTrackSelectionFlags = parametersBuilder.disabledTextTrackSelectionFlags;
            this.exceedRendererCapabilitiesIfNecessary = parametersBuilder.exceedRendererCapabilitiesIfNecessary;
            this.tunnelingEnabled = parametersBuilder.tunnelingEnabled;
            this.allowMultipleAdaptiveSelections = parametersBuilder.allowMultipleAdaptiveSelections;
            this.selectionOverrides = parametersBuilder.selectionOverrides;
            this.rendererDisabledFlags = parametersBuilder.rendererDisabledFlags;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        private static boolean areSelectionOverridesEqual(Map<TrackGroupArray, SelectionOverride> map, Map<TrackGroupArray, SelectionOverride> map2) {
            if (map2.size() != map.size()) {
                return false;
            }
            for (Map.Entry<TrackGroupArray, SelectionOverride> entry : map.entrySet()) {
                TrackGroupArray key = entry.getKey();
                if (!map2.containsKey(key) || !Util.areEqual(entry.getValue(), map2.get(key))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static final class ParametersBuilder extends TrackSelectionParameters.Builder {
        private boolean allowAudioMixedChannelCountAdaptiveness;
        private boolean allowAudioMixedMimeTypeAdaptiveness;
        private boolean allowAudioMixedSampleRateAdaptiveness;
        private boolean allowMultipleAdaptiveSelections;
        private boolean allowVideoMixedMimeTypeAdaptiveness;
        private boolean allowVideoNonSeamlessAdaptiveness;
        private int disabledTextTrackSelectionFlags;
        private boolean exceedAudioConstraintsIfNecessary;
        private boolean exceedRendererCapabilitiesIfNecessary;
        private boolean exceedVideoConstraintsIfNecessary;
        private final SparseBooleanArray rendererDisabledFlags;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        private boolean tunnelingEnabled;

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> cloneSelectionOverrides(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2 = new SparseArray<>();
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                sparseArray2.put(sparseArray.keyAt(i2), new HashMap(sparseArray.valueAt(i2)));
            }
            return sparseArray2;
        }

        private void init() {
            this.exceedVideoConstraintsIfNecessary = true;
            this.allowVideoMixedMimeTypeAdaptiveness = false;
            this.allowVideoNonSeamlessAdaptiveness = true;
            this.exceedAudioConstraintsIfNecessary = true;
            this.allowAudioMixedMimeTypeAdaptiveness = false;
            this.allowAudioMixedSampleRateAdaptiveness = false;
            this.allowAudioMixedChannelCountAdaptiveness = false;
            this.disabledTextTrackSelectionFlags = 0;
            this.exceedRendererCapabilitiesIfNecessary = true;
            this.tunnelingEnabled = false;
            this.allowMultipleAdaptiveSelections = true;
        }

        private SparseBooleanArray makeSparseBooleanArrayFromTrueKeys(@Nullable int[] iArr) {
            if (iArr == null) {
                return new SparseBooleanArray();
            }
            SparseBooleanArray sparseBooleanArray = new SparseBooleanArray(iArr.length);
            for (int i2 : iArr) {
                sparseBooleanArray.append(i2, true);
            }
            return sparseBooleanArray;
        }

        private void setSelectionOverridesFromBundle(Bundle bundle) {
            int[] intArray = bundle.getIntArray(Parameters.keyForField(1011));
            List listFromBundleNullableList = BundleableUtil.fromBundleNullableList(TrackGroupArray.CREATOR, bundle.getParcelableArrayList(Parameters.keyForField(1012)), ImmutableList.of());
            SparseArray sparseArrayFromBundleNullableSparseArray = BundleableUtil.fromBundleNullableSparseArray(SelectionOverride.CREATOR, bundle.getSparseParcelableArray(Parameters.keyForField(1013)), new SparseArray());
            if (intArray == null || intArray.length != listFromBundleNullableList.size()) {
                return;
            }
            for (int i2 = 0; i2 < intArray.length; i2++) {
                setSelectionOverride(intArray[i2], (TrackGroupArray) listFromBundleNullableList.get(i2), (SelectionOverride) sparseArrayFromBundleNullableSparseArray.get(i2));
            }
        }

        @Deprecated
        public final ParametersBuilder clearSelectionOverride(int i2, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(i2);
            if (map != null && map.containsKey(trackGroupArray)) {
                map.remove(trackGroupArray);
                if (map.isEmpty()) {
                    this.selectionOverrides.remove(i2);
                }
            }
            return this;
        }

        @Deprecated
        public final ParametersBuilder clearSelectionOverrides(int i2) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(i2);
            if (map != null && !map.isEmpty()) {
                this.selectionOverrides.remove(i2);
            }
            return this;
        }

        public ParametersBuilder setAllowAudioMixedChannelCountAdaptiveness(boolean z2) {
            this.allowAudioMixedChannelCountAdaptiveness = z2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedMimeTypeAdaptiveness(boolean z2) {
            this.allowAudioMixedMimeTypeAdaptiveness = z2;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedSampleRateAdaptiveness(boolean z2) {
            this.allowAudioMixedSampleRateAdaptiveness = z2;
            return this;
        }

        public ParametersBuilder setAllowMultipleAdaptiveSelections(boolean z2) {
            this.allowMultipleAdaptiveSelections = z2;
            return this;
        }

        public ParametersBuilder setAllowVideoMixedMimeTypeAdaptiveness(boolean z2) {
            this.allowVideoMixedMimeTypeAdaptiveness = z2;
            return this;
        }

        public ParametersBuilder setAllowVideoNonSeamlessAdaptiveness(boolean z2) {
            this.allowVideoNonSeamlessAdaptiveness = z2;
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int i2) {
            this.disabledTextTrackSelectionFlags = i2;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public /* bridge */ /* synthetic */ TrackSelectionParameters.Builder setDisabledTrackTypes(Set set) {
            return setDisabledTrackTypes((Set<Integer>) set);
        }

        public ParametersBuilder setExceedAudioConstraintsIfNecessary(boolean z2) {
            this.exceedAudioConstraintsIfNecessary = z2;
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean z2) {
            this.exceedRendererCapabilitiesIfNecessary = z2;
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean z2) {
            this.exceedVideoConstraintsIfNecessary = z2;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int i2, boolean z2) {
            if (this.rendererDisabledFlags.get(i2) == z2) {
                return this;
            }
            if (z2) {
                this.rendererDisabledFlags.put(i2, true);
            } else {
                this.rendererDisabledFlags.delete(i2);
            }
            return this;
        }

        @Deprecated
        public final ParametersBuilder setSelectionOverride(int i2, TrackGroupArray trackGroupArray, @Nullable SelectionOverride selectionOverride) {
            Map<TrackGroupArray, SelectionOverride> map = this.selectionOverrides.get(i2);
            if (map == null) {
                map = new HashMap<>();
                this.selectionOverrides.put(i2, map);
            }
            if (map.containsKey(trackGroupArray) && Util.areEqual(map.get(trackGroupArray), selectionOverride)) {
                return this;
            }
            map.put(trackGroupArray, selectionOverride);
            return this;
        }

        public ParametersBuilder setTunnelingEnabled(boolean z2) {
            this.tunnelingEnabled = z2;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public Parameters build() {
            return new Parameters(this);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearVideoSizeConstraints() {
            super.clearVideoSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearViewportSizeConstraints() {
            super.clearViewportSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder set(TrackSelectionParameters trackSelectionParameters) {
            super.set(trackSelectionParameters);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setDisabledTrackTypes(Set<Integer> set) {
            super.setDisabledTrackTypes(set);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceHighestSupportedBitrate(boolean z2) {
            super.setForceHighestSupportedBitrate(z2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceLowestBitrate(boolean z2) {
            super.setForceLowestBitrate(z2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioBitrate(int i2) {
            super.setMaxAudioBitrate(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioChannelCount(int i2) {
            super.setMaxAudioChannelCount(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoBitrate(int i2) {
            super.setMaxVideoBitrate(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoFrameRate(int i2) {
            super.setMaxVideoFrameRate(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSize(int i2, int i3) {
            super.setMaxVideoSize(i2, i3);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSizeSd() {
            super.setMaxVideoSizeSd();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoBitrate(int i2) {
            super.setMinVideoBitrate(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoFrameRate(int i2) {
            super.setMinVideoFrameRate(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoSize(int i2, int i3) {
            super.setMinVideoSize(i2, i3);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguage(@Nullable String str) {
            super.setPreferredAudioLanguage(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguages(String... strArr) {
            super.setPreferredAudioLanguages(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeType(@Nullable String str) {
            super.setPreferredAudioMimeType(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeTypes(String... strArr) {
            super.setPreferredAudioMimeTypes(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioRoleFlags(int i2) {
            super.setPreferredAudioRoleFlags(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguage(@Nullable String str) {
            super.setPreferredTextLanguage(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            super.setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguages(String... strArr) {
            super.setPreferredTextLanguages(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextRoleFlags(int i2) {
            super.setPreferredTextRoleFlags(i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeType(@Nullable String str) {
            super.setPreferredVideoMimeType(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeTypes(String... strArr) {
            super.setPreferredVideoMimeTypes(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean z2) {
            super.setSelectUndeterminedTextLanguage(z2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setTrackSelectionOverrides(TrackSelectionOverrides trackSelectionOverrides) {
            super.setTrackSelectionOverrides(trackSelectionOverrides);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSize(int i2, int i3, boolean z2) {
            super.setViewportSize(i2, i3, z2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean z2) {
            super.setViewportSizeToPhysicalDisplaySize(context, z2);
            return this;
        }

        @Deprecated
        public ParametersBuilder() {
            this.selectionOverrides = new SparseArray<>();
            this.rendererDisabledFlags = new SparseBooleanArray();
            init();
        }

        @Deprecated
        public final ParametersBuilder clearSelectionOverrides() {
            if (this.selectionOverrides.size() == 0) {
                return this;
            }
            this.selectionOverrides.clear();
            return this;
        }

        public ParametersBuilder(Context context) {
            super(context);
            this.selectionOverrides = new SparseArray<>();
            this.rendererDisabledFlags = new SparseBooleanArray();
            init();
        }

        private ParametersBuilder(Parameters parameters) {
            super(parameters);
            this.disabledTextTrackSelectionFlags = parameters.disabledTextTrackSelectionFlags;
            this.exceedVideoConstraintsIfNecessary = parameters.exceedVideoConstraintsIfNecessary;
            this.allowVideoMixedMimeTypeAdaptiveness = parameters.allowVideoMixedMimeTypeAdaptiveness;
            this.allowVideoNonSeamlessAdaptiveness = parameters.allowVideoNonSeamlessAdaptiveness;
            this.exceedAudioConstraintsIfNecessary = parameters.exceedAudioConstraintsIfNecessary;
            this.allowAudioMixedMimeTypeAdaptiveness = parameters.allowAudioMixedMimeTypeAdaptiveness;
            this.allowAudioMixedSampleRateAdaptiveness = parameters.allowAudioMixedSampleRateAdaptiveness;
            this.allowAudioMixedChannelCountAdaptiveness = parameters.allowAudioMixedChannelCountAdaptiveness;
            this.exceedRendererCapabilitiesIfNecessary = parameters.exceedRendererCapabilitiesIfNecessary;
            this.tunnelingEnabled = parameters.tunnelingEnabled;
            this.allowMultipleAdaptiveSelections = parameters.allowMultipleAdaptiveSelections;
            this.selectionOverrides = cloneSelectionOverrides(parameters.selectionOverrides);
            this.rendererDisabledFlags = parameters.rendererDisabledFlags.clone();
        }

        private ParametersBuilder(Bundle bundle) {
            super(bundle);
            Parameters parameters = Parameters.DEFAULT_WITHOUT_CONTEXT;
            setExceedVideoConstraintsIfNecessary(bundle.getBoolean(Parameters.keyForField(1000), parameters.exceedVideoConstraintsIfNecessary));
            setAllowVideoMixedMimeTypeAdaptiveness(bundle.getBoolean(Parameters.keyForField(1001), parameters.allowVideoMixedMimeTypeAdaptiveness));
            setAllowVideoNonSeamlessAdaptiveness(bundle.getBoolean(Parameters.keyForField(1002), parameters.allowVideoNonSeamlessAdaptiveness));
            setExceedAudioConstraintsIfNecessary(bundle.getBoolean(Parameters.keyForField(1003), parameters.exceedAudioConstraintsIfNecessary));
            setAllowAudioMixedMimeTypeAdaptiveness(bundle.getBoolean(Parameters.keyForField(1004), parameters.allowAudioMixedMimeTypeAdaptiveness));
            setAllowAudioMixedSampleRateAdaptiveness(bundle.getBoolean(Parameters.keyForField(1005), parameters.allowAudioMixedSampleRateAdaptiveness));
            setAllowAudioMixedChannelCountAdaptiveness(bundle.getBoolean(Parameters.keyForField(1006), parameters.allowAudioMixedChannelCountAdaptiveness));
            setDisabledTextTrackSelectionFlags(bundle.getInt(Parameters.keyForField(1007), parameters.disabledTextTrackSelectionFlags));
            setExceedRendererCapabilitiesIfNecessary(bundle.getBoolean(Parameters.keyForField(1008), parameters.exceedRendererCapabilitiesIfNecessary));
            setTunnelingEnabled(bundle.getBoolean(Parameters.keyForField(1009), parameters.tunnelingEnabled));
            setAllowMultipleAdaptiveSelections(bundle.getBoolean(Parameters.keyForField(1010), parameters.allowMultipleAdaptiveSelections));
            this.selectionOverrides = new SparseArray<>();
            setSelectionOverridesFromBundle(bundle);
            this.rendererDisabledFlags = makeSparseBooleanArrayFromTrueKeys(bundle.getIntArray(Parameters.keyForField(1014)));
        }
    }

    public static final class SelectionOverride implements Bundleable {
        public static final Bundleable.Creator<SelectionOverride> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.trackselection.e
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return DefaultTrackSelector.SelectionOverride.lambda$static$0(bundle);
            }
        };
        private static final int FIELD_GROUP_INDEX = 0;
        private static final int FIELD_TRACKS = 1;
        private static final int FIELD_TRACK_TYPE = 2;
        public final int groupIndex;
        public final int length;
        public final int[] tracks;
        public final int type;

        public SelectionOverride(int i2, int... iArr) {
            this(i2, iArr, 0);
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ SelectionOverride lambda$static$0(Bundle bundle) {
            boolean z2 = false;
            int i2 = bundle.getInt(keyForField(0), -1);
            int[] intArray = bundle.getIntArray(keyForField(1));
            int i3 = bundle.getInt(keyForField(2), -1);
            if (i2 >= 0 && i3 >= 0) {
                z2 = true;
            }
            Assertions.checkArgument(z2);
            Assertions.checkNotNull(intArray);
            return new SelectionOverride(i2, intArray, i3);
        }

        public boolean containsTrack(int i2) {
            for (int i3 : this.tracks) {
                if (i3 == i2) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || SelectionOverride.class != obj.getClass()) {
                return false;
            }
            SelectionOverride selectionOverride = (SelectionOverride) obj;
            return this.groupIndex == selectionOverride.groupIndex && Arrays.equals(this.tracks, selectionOverride.tracks) && this.type == selectionOverride.type;
        }

        public int hashCode() {
            return (((this.groupIndex * 31) + Arrays.hashCode(this.tracks)) * 31) + this.type;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(keyForField(0), this.groupIndex);
            bundle.putIntArray(keyForField(1), this.tracks);
            bundle.putInt(keyForField(2), this.type);
            return bundle;
        }

        public SelectionOverride(int i2, int[] iArr, int i3) {
            this.groupIndex = i2;
            int[] iArrCopyOf = Arrays.copyOf(iArr, iArr.length);
            this.tracks = iArrCopyOf;
            this.length = iArr.length;
            this.type = i3;
            Arrays.sort(iArrCopyOf);
        }
    }

    public static final class TextTrackScore implements Comparable<TextTrackScore> {
        private final boolean hasCaptionRoleFlags;
        private final boolean isDefault;
        private final boolean isForced;
        public final boolean isWithinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final int preferredLanguageIndex;
        private final int preferredLanguageScore;
        private final int preferredRoleFlagsScore;
        private final int selectedAudioLanguageScore;

        public TextTrackScore(Format format, Parameters parameters, int i2, @Nullable String str) {
            int formatLanguageScore;
            boolean z2 = false;
            this.isWithinRendererCapabilities = DefaultTrackSelector.isSupported(i2, false);
            int i3 = format.selectionFlags & (~parameters.disabledTextTrackSelectionFlags);
            this.isDefault = (i3 & 1) != 0;
            this.isForced = (i3 & 2) != 0;
            ImmutableList<String> immutableListOf = parameters.preferredTextLanguages.isEmpty() ? ImmutableList.of("") : parameters.preferredTextLanguages;
            int i4 = 0;
            while (true) {
                if (i4 >= immutableListOf.size()) {
                    i4 = Integer.MAX_VALUE;
                    formatLanguageScore = 0;
                    break;
                } else {
                    formatLanguageScore = DefaultTrackSelector.getFormatLanguageScore(format, immutableListOf.get(i4), parameters.selectUndeterminedTextLanguage);
                    if (formatLanguageScore > 0) {
                        break;
                    } else {
                        i4++;
                    }
                }
            }
            this.preferredLanguageIndex = i4;
            this.preferredLanguageScore = formatLanguageScore;
            int iBitCount = Integer.bitCount(format.roleFlags & parameters.preferredTextRoleFlags);
            this.preferredRoleFlagsScore = iBitCount;
            this.hasCaptionRoleFlags = (format.roleFlags & R2.attr.columnOrderPreserved) != 0;
            int formatLanguageScore2 = DefaultTrackSelector.getFormatLanguageScore(format, str, DefaultTrackSelector.normalizeUndeterminedLanguageToNull(str) == null);
            this.selectedAudioLanguageScore = formatLanguageScore2;
            if (formatLanguageScore > 0 || ((parameters.preferredTextLanguages.isEmpty() && iBitCount > 0) || this.isDefault || (this.isForced && formatLanguageScore2 > 0))) {
                z2 = true;
            }
            this.isWithinConstraints = z2;
        }

        @Override // java.lang.Comparable
        public int compareTo(TextTrackScore textTrackScore) {
            ComparisonChain comparisonChainCompare = ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, textTrackScore.isWithinRendererCapabilities).compare(Integer.valueOf(this.preferredLanguageIndex), Integer.valueOf(textTrackScore.preferredLanguageIndex), Ordering.natural().reverse()).compare(this.preferredLanguageScore, textTrackScore.preferredLanguageScore).compare(this.preferredRoleFlagsScore, textTrackScore.preferredRoleFlagsScore).compareFalseFirst(this.isDefault, textTrackScore.isDefault).compare(Boolean.valueOf(this.isForced), Boolean.valueOf(textTrackScore.isForced), this.preferredLanguageScore == 0 ? Ordering.natural() : Ordering.natural().reverse()).compare(this.selectedAudioLanguageScore, textTrackScore.selectedAudioLanguageScore);
            if (this.preferredRoleFlagsScore == 0) {
                comparisonChainCompare = comparisonChainCompare.compareTrueFirst(this.hasCaptionRoleFlags, textTrackScore.hasCaptionRoleFlags);
            }
            return comparisonChainCompare.result();
        }
    }

    public static final class VideoTrackScore implements Comparable<VideoTrackScore> {
        private final int bitrate;
        public final boolean isWithinMaxConstraints;
        private final boolean isWithinMinConstraints;
        private final boolean isWithinRendererCapabilities;
        private final Parameters parameters;
        private final int pixelCount;
        private final int preferredMimeTypeMatchIndex;

        /* JADX WARN: Removed duplicated region for block: B:21:0x0033  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x005e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public VideoTrackScore(com.google.android.exoplayer2.Format r7, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters r8, int r9, boolean r10) {
            /*
                r6 = this;
                r6.<init>()
                r6.parameters = r8
                r0 = -1082130432(0xffffffffbf800000, float:-1.0)
                r1 = 1
                r2 = 0
                r3 = -1
                if (r10 == 0) goto L33
                int r4 = r7.width
                if (r4 == r3) goto L14
                int r5 = r8.maxVideoWidth
                if (r4 > r5) goto L33
            L14:
                int r4 = r7.height
                if (r4 == r3) goto L1c
                int r5 = r8.maxVideoHeight
                if (r4 > r5) goto L33
            L1c:
                float r4 = r7.frameRate
                int r5 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r5 == 0) goto L29
                int r5 = r8.maxVideoFrameRate
                float r5 = (float) r5
                int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
                if (r4 > 0) goto L33
            L29:
                int r4 = r7.bitrate
                if (r4 == r3) goto L31
                int r5 = r8.maxVideoBitrate
                if (r4 > r5) goto L33
            L31:
                r4 = r1
                goto L34
            L33:
                r4 = r2
            L34:
                r6.isWithinMaxConstraints = r4
                if (r10 == 0) goto L5e
                int r10 = r7.width
                if (r10 == r3) goto L40
                int r4 = r8.minVideoWidth
                if (r10 < r4) goto L5e
            L40:
                int r10 = r7.height
                if (r10 == r3) goto L48
                int r4 = r8.minVideoHeight
                if (r10 < r4) goto L5e
            L48:
                float r10 = r7.frameRate
                int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r0 == 0) goto L55
                int r0 = r8.minVideoFrameRate
                float r0 = (float) r0
                int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r10 < 0) goto L5e
            L55:
                int r10 = r7.bitrate
                if (r10 == r3) goto L5f
                int r0 = r8.minVideoBitrate
                if (r10 < r0) goto L5e
                goto L5f
            L5e:
                r1 = r2
            L5f:
                r6.isWithinMinConstraints = r1
                boolean r9 = com.google.android.exoplayer2.trackselection.DefaultTrackSelector.isSupported(r9, r2)
                r6.isWithinRendererCapabilities = r9
                int r9 = r7.bitrate
                r6.bitrate = r9
                int r9 = r7.getPixelCount()
                r6.pixelCount = r9
            L71:
                com.google.common.collect.ImmutableList<java.lang.String> r9 = r8.preferredVideoMimeTypes
                int r9 = r9.size()
                if (r2 >= r9) goto L8d
                java.lang.String r9 = r7.sampleMimeType
                if (r9 == 0) goto L8a
                com.google.common.collect.ImmutableList<java.lang.String> r10 = r8.preferredVideoMimeTypes
                java.lang.Object r10 = r10.get(r2)
                boolean r9 = r9.equals(r10)
                if (r9 == 0) goto L8a
                goto L90
            L8a:
                int r2 = r2 + 1
                goto L71
            L8d:
                r2 = 2147483647(0x7fffffff, float:NaN)
            L90:
                r6.preferredMimeTypeMatchIndex = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.VideoTrackScore.<init>(com.google.android.exoplayer2.Format, com.google.android.exoplayer2.trackselection.DefaultTrackSelector$Parameters, int, boolean):void");
        }

        @Override // java.lang.Comparable
        public int compareTo(VideoTrackScore videoTrackScore) {
            Ordering orderingReverse = (this.isWithinMaxConstraints && this.isWithinRendererCapabilities) ? DefaultTrackSelector.FORMAT_VALUE_ORDERING : DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse();
            return ComparisonChain.start().compareFalseFirst(this.isWithinRendererCapabilities, videoTrackScore.isWithinRendererCapabilities).compareFalseFirst(this.isWithinMaxConstraints, videoTrackScore.isWithinMaxConstraints).compareFalseFirst(this.isWithinMinConstraints, videoTrackScore.isWithinMinConstraints).compare(Integer.valueOf(this.preferredMimeTypeMatchIndex), Integer.valueOf(videoTrackScore.preferredMimeTypeMatchIndex), Ordering.natural().reverse()).compare(Integer.valueOf(this.bitrate), Integer.valueOf(videoTrackScore.bitrate), this.parameters.forceLowestBitrate ? DefaultTrackSelector.FORMAT_VALUE_ORDERING.reverse() : DefaultTrackSelector.NO_ORDER).compare(Integer.valueOf(this.pixelCount), Integer.valueOf(videoTrackScore.pixelCount), orderingReverse).compare(Integer.valueOf(this.bitrate), Integer.valueOf(videoTrackScore.bitrate), orderingReverse).result();
        }
    }

    @Deprecated
    public DefaultTrackSelector() {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, new AdaptiveTrackSelection.Factory());
    }

    private void applyTrackTypeOverride(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, ExoTrackSelection.Definition[] definitionArr, int i2, TrackSelectionOverrides.TrackSelectionOverride trackSelectionOverride, int i3) {
        for (int i4 = 0; i4 < definitionArr.length; i4++) {
            if (i3 == i4) {
                definitionArr[i4] = new ExoTrackSelection.Definition(trackSelectionOverride.trackGroup, Ints.toArray(trackSelectionOverride.trackIndices));
            } else if (mappedTrackInfo.getRendererType(i4) == i2) {
                definitionArr[i4] = null;
            }
        }
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i2, @Nullable String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, List<Integer> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            int iIntValue = list.get(size).intValue();
            if (!isSupportedAdaptiveVideoTrack(trackGroup.getFormat(iIntValue), str, iArr[iIntValue], i2, i3, i4, i5, i6, i7, i8, i9, i10)) {
                list.remove(size);
            }
        }
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup trackGroup, int[] iArr, int i2, int i3, boolean z2, boolean z3, boolean z4) {
        Format format = trackGroup.getFormat(i2);
        int[] iArr2 = new int[trackGroup.length];
        int i4 = 0;
        for (int i5 = 0; i5 < trackGroup.length; i5++) {
            if (i5 == i2 || isSupportedAdaptiveAudioTrack(trackGroup.getFormat(i5), iArr[i5], format, i3, z2, z3, z4)) {
                iArr2[i4] = i5;
                i4++;
            }
        }
        return Arrays.copyOf(iArr2, i4);
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i2, @Nullable String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, List<Integer> list) {
        int i11 = 0;
        for (int i12 = 0; i12 < list.size(); i12++) {
            int iIntValue = list.get(i12).intValue();
            if (isSupportedAdaptiveVideoTrack(trackGroup.getFormat(iIntValue), str, iArr[iIntValue], i2, i3, i4, i5, i6, i7, i8, i9, i10)) {
                i11++;
            }
        }
        return i11;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup trackGroup, int[] iArr, boolean z2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, boolean z3) {
        String str;
        int i13;
        int i14;
        HashSet hashSet;
        if (trackGroup.length < 2) {
            return NO_TRACKS;
        }
        List<Integer> viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup, i11, i12, z3);
        if (viewportFilteredTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        if (z2) {
            str = null;
        } else {
            HashSet hashSet2 = new HashSet();
            String str2 = null;
            int i15 = 0;
            int i16 = 0;
            while (i16 < viewportFilteredTrackIndices.size()) {
                String str3 = trackGroup.getFormat(viewportFilteredTrackIndices.get(i16).intValue()).sampleMimeType;
                if (hashSet2.add(str3)) {
                    i13 = i15;
                    i14 = i16;
                    hashSet = hashSet2;
                    int adaptiveVideoTrackCountForMimeType = getAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i2, str3, i3, i4, i5, i6, i7, i8, i9, i10, viewportFilteredTrackIndices);
                    if (adaptiveVideoTrackCountForMimeType > i13) {
                        i15 = adaptiveVideoTrackCountForMimeType;
                        str2 = str3;
                    }
                    i16 = i14 + 1;
                    hashSet2 = hashSet;
                } else {
                    i13 = i15;
                    i14 = i16;
                    hashSet = hashSet2;
                }
                i15 = i13;
                i16 = i14 + 1;
                hashSet2 = hashSet;
            }
            str = str2;
        }
        filterAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i2, str, i3, i4, i5, i6, i7, i8, i9, i10, viewportFilteredTrackIndices);
        return viewportFilteredTrackIndices.size() < 2 ? NO_TRACKS : Ints.toArray(viewportFilteredTrackIndices);
    }

    private SparseArray<Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer>> getApplicableOverrides(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, Parameters parameters) {
        SparseArray<Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer>> sparseArray = new SparseArray<>();
        int rendererCount = mappedTrackInfo.getRendererCount();
        for (int i2 = 0; i2 < rendererCount; i2++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
            for (int i3 = 0; i3 < trackGroups.length; i3++) {
                maybeUpdateApplicableOverrides(sparseArray, parameters.trackSelectionOverrides.getOverride(trackGroups.get(i3)), i2);
            }
        }
        TrackGroupArray unmappedTrackGroups = mappedTrackInfo.getUnmappedTrackGroups();
        for (int i4 = 0; i4 < unmappedTrackGroups.length; i4++) {
            maybeUpdateApplicableOverrides(sparseArray, parameters.trackSelectionOverrides.getOverride(unmappedTrackGroups.get(i4)), -1);
        }
        return sparseArray;
    }

    public static int getFormatLanguageScore(Format format, @Nullable String str, boolean z2) {
        if (!TextUtils.isEmpty(str) && str.equals(format.language)) {
            return 4;
        }
        String strNormalizeUndeterminedLanguageToNull = normalizeUndeterminedLanguageToNull(str);
        String strNormalizeUndeterminedLanguageToNull2 = normalizeUndeterminedLanguageToNull(format.language);
        if (strNormalizeUndeterminedLanguageToNull2 == null || strNormalizeUndeterminedLanguageToNull == null) {
            return (z2 && strNormalizeUndeterminedLanguageToNull2 == null) ? 1 : 0;
        }
        if (strNormalizeUndeterminedLanguageToNull2.startsWith(strNormalizeUndeterminedLanguageToNull) || strNormalizeUndeterminedLanguageToNull.startsWith(strNormalizeUndeterminedLanguageToNull2)) {
            return 3;
        }
        return Util.splitAtFirst(strNormalizeUndeterminedLanguageToNull2, "-")[0].equals(Util.splitAtFirst(strNormalizeUndeterminedLanguageToNull, "-")[0]) ? 2 : 0;
    }

    private ExoTrackSelection.Definition getLegacyRendererOverride(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, Parameters parameters, int i2) {
        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
        SelectionOverride selectionOverride = parameters.getSelectionOverride(i2, trackGroups);
        if (selectionOverride == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroups.get(selectionOverride.groupIndex), selectionOverride.tracks, selectionOverride.type);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Point getMaxVideoSizeInViewport(boolean r3, int r4, int r5, int r6, int r7) {
        /*
            if (r3 == 0) goto L10
            r3 = 1
            r0 = 0
            if (r6 <= r7) goto L8
            r1 = r3
            goto L9
        L8:
            r1 = r0
        L9:
            if (r4 <= r5) goto Lc
            goto Ld
        Lc:
            r3 = r0
        Ld:
            if (r1 == r3) goto L10
            goto L13
        L10:
            r2 = r5
            r5 = r4
            r4 = r2
        L13:
            int r3 = r6 * r4
            int r0 = r7 * r5
            if (r3 < r0) goto L23
            android.graphics.Point r3 = new android.graphics.Point
            int r4 = com.google.android.exoplayer2.util.Util.ceilDivide(r0, r6)
            r3.<init>(r5, r4)
            return r3
        L23:
            android.graphics.Point r5 = new android.graphics.Point
            int r3 = com.google.android.exoplayer2.util.Util.ceilDivide(r3, r7)
            r5.<init>(r3, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.getMaxVideoSizeInViewport(boolean, int, int, int, int):android.graphics.Point");
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup trackGroup, int i2, int i3, boolean z2) {
        int i4;
        ArrayList arrayList = new ArrayList(trackGroup.length);
        for (int i5 = 0; i5 < trackGroup.length; i5++) {
            arrayList.add(Integer.valueOf(i5));
        }
        if (i2 != Integer.MAX_VALUE && i3 != Integer.MAX_VALUE) {
            int i6 = Integer.MAX_VALUE;
            for (int i7 = 0; i7 < trackGroup.length; i7++) {
                Format format = trackGroup.getFormat(i7);
                int i8 = format.width;
                if (i8 > 0 && (i4 = format.height) > 0) {
                    Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(z2, i2, i3, i8, i4);
                    int i9 = format.width;
                    int i10 = format.height;
                    int i11 = i9 * i10;
                    if (i9 >= ((int) (maxVideoSizeInViewport.x * FRACTION_TO_CONSIDER_FULLSCREEN)) && i10 >= ((int) (maxVideoSizeInViewport.y * FRACTION_TO_CONSIDER_FULLSCREEN)) && i11 < i6) {
                        i6 = i11;
                    }
                }
            }
            if (i6 != Integer.MAX_VALUE) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    int pixelCount = trackGroup.getFormat(((Integer) arrayList.get(size)).intValue()).getPixelCount();
                    if (pixelCount == -1 || pixelCount > i6) {
                        arrayList.remove(size);
                    }
                }
            }
        }
        return arrayList;
    }

    private boolean hasLegacyRendererOverride(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, Parameters parameters, int i2) {
        return parameters.hasSelectionOverride(i2, mappedTrackInfo.getTrackGroups(i2));
    }

    private boolean isRendererDisabled(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, Parameters parameters, int i2) {
        return parameters.getRendererDisabled(i2) || parameters.disabledTrackTypes.contains(Integer.valueOf(mappedTrackInfo.getRendererType(i2)));
    }

    public static boolean isSupported(int i2, boolean z2) {
        int iD = b2.d(i2);
        return iD == 4 || (z2 && iD == 3);
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int i2, Format format2, int i3, boolean z2, boolean z3, boolean z4) {
        int i4;
        int i5;
        String str;
        int i6;
        if (!isSupported(i2, false) || (i4 = format.bitrate) == -1 || i4 > i3) {
            return false;
        }
        if (!z4 && ((i6 = format.channelCount) == -1 || i6 != format2.channelCount)) {
            return false;
        }
        if (z2 || ((str = format.sampleMimeType) != null && TextUtils.equals(str, format2.sampleMimeType))) {
            return z3 || ((i5 = format.sampleRate) != -1 && i5 == format2.sampleRate);
        }
        return false;
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, @Nullable String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        int i12;
        if ((format.roleFlags & 16384) != 0 || !isSupported(i2, false) || (i2 & i3) == 0) {
            return false;
        }
        if (str != null && !Util.areEqual(format.sampleMimeType, str)) {
            return false;
        }
        int i13 = format.width;
        if (i13 != -1 && (i8 > i13 || i13 > i4)) {
            return false;
        }
        int i14 = format.height;
        if (i14 != -1 && (i9 > i14 || i14 > i5)) {
            return false;
        }
        float f2 = format.frameRate;
        return (f2 == -1.0f || (((float) i10) <= f2 && f2 <= ((float) i6))) && (i12 = format.bitrate) != -1 && i11 <= i12 && i12 <= i7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(Integer num, Integer num2) {
        if (num.intValue() == -1) {
            return num2.intValue() == -1 ? 0 : -1;
        }
        if (num2.intValue() == -1) {
            return 1;
        }
        return num.intValue() - num2.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$1(Integer num, Integer num2) {
        return 0;
    }

    private static void maybeConfigureRenderersForTunneling(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr) {
        boolean z2;
        boolean z3 = false;
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < mappedTrackInfo.getRendererCount(); i4++) {
            int rendererType = mappedTrackInfo.getRendererType(i4);
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i4];
            if ((rendererType == 1 || rendererType == 2) && exoTrackSelection != null && rendererSupportsTunneling(iArr[i4], mappedTrackInfo.getTrackGroups(i4), exoTrackSelection)) {
                if (rendererType == 1) {
                    if (i3 != -1) {
                        z2 = false;
                        break;
                    }
                    i3 = i4;
                } else {
                    if (i2 != -1) {
                        z2 = false;
                        break;
                    }
                    i2 = i4;
                }
            }
        }
        z2 = true;
        if (i3 != -1 && i2 != -1) {
            z3 = true;
        }
        if (z2 && z3) {
            RendererConfiguration rendererConfiguration = new RendererConfiguration(true);
            rendererConfigurationArr[i3] = rendererConfiguration;
            rendererConfigurationArr[i2] = rendererConfiguration;
        }
    }

    private void maybeUpdateApplicableOverrides(SparseArray<Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer>> sparseArray, @Nullable TrackSelectionOverrides.TrackSelectionOverride trackSelectionOverride, int i2) {
        if (trackSelectionOverride == null) {
            return;
        }
        int trackType = trackSelectionOverride.getTrackType();
        Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer> pair = sparseArray.get(trackType);
        if (pair == null || ((TrackSelectionOverrides.TrackSelectionOverride) pair.first).trackIndices.isEmpty()) {
            sparseArray.put(trackType, Pair.create(trackSelectionOverride, Integer.valueOf(i2)));
        }
    }

    @Nullable
    public static String normalizeUndeterminedLanguageToNull(@Nullable String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, C.LANGUAGE_UNDETERMINED)) {
            return null;
        }
        return str;
    }

    private static boolean rendererSupportsTunneling(int[][] iArr, TrackGroupArray trackGroupArray, ExoTrackSelection exoTrackSelection) {
        if (exoTrackSelection == null) {
            return false;
        }
        int iIndexOf = trackGroupArray.indexOf(exoTrackSelection.getTrackGroup());
        for (int i2 = 0; i2 < exoTrackSelection.length(); i2++) {
            if (b2.e(iArr[iIndexOf][exoTrackSelection.getIndexInTrackGroup(i2)]) != 32) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    private static ExoTrackSelection.Definition selectAdaptiveVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i2, Parameters parameters) {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        Parameters parameters2 = parameters;
        int i3 = parameters2.allowVideoNonSeamlessAdaptiveness ? 24 : 16;
        boolean z2 = parameters2.allowVideoMixedMimeTypeAdaptiveness && (i2 & i3) != 0;
        int i4 = 0;
        while (i4 < trackGroupArray2.length) {
            TrackGroup trackGroup = trackGroupArray2.get(i4);
            int i5 = i4;
            int[] adaptiveVideoTracksForGroup = getAdaptiveVideoTracksForGroup(trackGroup, iArr[i4], z2, i3, parameters2.maxVideoWidth, parameters2.maxVideoHeight, parameters2.maxVideoFrameRate, parameters2.maxVideoBitrate, parameters2.minVideoWidth, parameters2.minVideoHeight, parameters2.minVideoFrameRate, parameters2.minVideoBitrate, parameters2.viewportWidth, parameters2.viewportHeight, parameters2.viewportOrientationMayChange);
            if (adaptiveVideoTracksForGroup.length > 0) {
                return new ExoTrackSelection.Definition(trackGroup, adaptiveVideoTracksForGroup);
            }
            i4 = i5 + 1;
            trackGroupArray2 = trackGroupArray;
            parameters2 = parameters;
        }
        return null;
    }

    @Nullable
    private static ExoTrackSelection.Definition selectFixedVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) {
        int i2 = -1;
        TrackGroup trackGroup = null;
        VideoTrackScore videoTrackScore = null;
        for (int i3 = 0; i3 < trackGroupArray.length; i3++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i3);
            List<Integer> viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup2, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            int[] iArr2 = iArr[i3];
            for (int i4 = 0; i4 < trackGroup2.length; i4++) {
                Format format = trackGroup2.getFormat(i4);
                if ((format.roleFlags & 16384) == 0 && isSupported(iArr2[i4], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    VideoTrackScore videoTrackScore2 = new VideoTrackScore(format, parameters, iArr2[i4], viewportFilteredTrackIndices.contains(Integer.valueOf(i4)));
                    if ((videoTrackScore2.isWithinMaxConstraints || parameters.exceedVideoConstraintsIfNecessary) && (videoTrackScore == null || videoTrackScore2.compareTo(videoTrackScore) > 0)) {
                        trackGroup = trackGroup2;
                        i2 = i4;
                        videoTrackScore = videoTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i2);
    }

    private void setParametersInternal(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (this.parametersReference.getAndSet(parameters).equals(parameters)) {
            return;
        }
        invalidate();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public boolean isSetParametersSupported() {
        return true;
    }

    public ExoTrackSelection.Definition[] selectAllTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, Parameters parameters) throws ExoPlaybackException {
        boolean z2;
        String str;
        int i2;
        AudioTrackScore audioTrackScore;
        String str2;
        int i3;
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] definitionArr = new ExoTrackSelection.Definition[rendererCount];
        int i4 = 0;
        boolean z3 = false;
        int i5 = 0;
        boolean z4 = false;
        while (true) {
            if (i5 >= rendererCount) {
                break;
            }
            if (2 == mappedTrackInfo.getRendererType(i5)) {
                if (!z3) {
                    ExoTrackSelection.Definition definitionSelectVideoTrack = selectVideoTrack(mappedTrackInfo.getTrackGroups(i5), iArr[i5], iArr2[i5], parameters, true);
                    definitionArr[i5] = definitionSelectVideoTrack;
                    z3 = definitionSelectVideoTrack != null;
                }
                z4 |= mappedTrackInfo.getTrackGroups(i5).length > 0;
            }
            i5++;
        }
        int i6 = 0;
        int i7 = -1;
        AudioTrackScore audioTrackScore2 = null;
        String str3 = null;
        while (i6 < rendererCount) {
            if (z2 == mappedTrackInfo.getRendererType(i6)) {
                boolean z5 = (parameters.allowMultipleAdaptiveSelections || !z4) ? z2 : false;
                i2 = i7;
                audioTrackScore = audioTrackScore2;
                str2 = str3;
                i3 = i6;
                Pair<ExoTrackSelection.Definition, AudioTrackScore> pairSelectAudioTrack = selectAudioTrack(mappedTrackInfo.getTrackGroups(i6), iArr[i6], iArr2[i6], parameters, z5);
                if (pairSelectAudioTrack != null && (audioTrackScore == null || ((AudioTrackScore) pairSelectAudioTrack.second).compareTo(audioTrackScore) > 0)) {
                    if (i2 != -1) {
                        definitionArr[i2] = null;
                    }
                    ExoTrackSelection.Definition definition = (ExoTrackSelection.Definition) pairSelectAudioTrack.first;
                    definitionArr[i3] = definition;
                    str3 = definition.group.getFormat(definition.tracks[0]).language;
                    audioTrackScore2 = (AudioTrackScore) pairSelectAudioTrack.second;
                    i7 = i3;
                }
                i6 = i3 + 1;
                z2 = true;
            } else {
                i2 = i7;
                audioTrackScore = audioTrackScore2;
                str2 = str3;
                i3 = i6;
            }
            i7 = i2;
            audioTrackScore2 = audioTrackScore;
            str3 = str2;
            i6 = i3 + 1;
            z2 = true;
        }
        String str4 = str3;
        int i8 = -1;
        TextTrackScore textTrackScore = null;
        while (i4 < rendererCount) {
            int rendererType = mappedTrackInfo.getRendererType(i4);
            if (rendererType == 1) {
                str = str4;
            } else if (rendererType == 2) {
                str = str4;
            } else if (rendererType != 3) {
                definitionArr[i4] = selectOtherTrack(rendererType, mappedTrackInfo.getTrackGroups(i4), iArr[i4], parameters);
                str = str4;
            } else {
                str = str4;
                Pair<ExoTrackSelection.Definition, TextTrackScore> pairSelectTextTrack = selectTextTrack(mappedTrackInfo.getTrackGroups(i4), iArr[i4], parameters, str);
                if (pairSelectTextTrack != null && (textTrackScore == null || ((TextTrackScore) pairSelectTextTrack.second).compareTo(textTrackScore) > 0)) {
                    if (i8 != -1) {
                        definitionArr[i8] = null;
                    }
                    definitionArr[i4] = (ExoTrackSelection.Definition) pairSelectTextTrack.first;
                    textTrackScore = (TextTrackScore) pairSelectTextTrack.second;
                    i8 = i4;
                }
            }
            i4++;
            str4 = str;
        }
        return definitionArr;
    }

    @Nullable
    public Pair<ExoTrackSelection.Definition, AudioTrackScore> selectAudioTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i2, Parameters parameters, boolean z2) throws ExoPlaybackException {
        ExoTrackSelection.Definition definition = null;
        int i3 = -1;
        int i4 = -1;
        AudioTrackScore audioTrackScore = null;
        for (int i5 = 0; i5 < trackGroupArray.length; i5++) {
            TrackGroup trackGroup = trackGroupArray.get(i5);
            int[] iArr2 = iArr[i5];
            for (int i6 = 0; i6 < trackGroup.length; i6++) {
                if (isSupported(iArr2[i6], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    AudioTrackScore audioTrackScore2 = new AudioTrackScore(trackGroup.getFormat(i6), parameters, iArr2[i6]);
                    if ((audioTrackScore2.isWithinConstraints || parameters.exceedAudioConstraintsIfNecessary) && (audioTrackScore == null || audioTrackScore2.compareTo(audioTrackScore) > 0)) {
                        i3 = i5;
                        i4 = i6;
                        audioTrackScore = audioTrackScore2;
                    }
                }
            }
        }
        if (i3 == -1) {
            return null;
        }
        TrackGroup trackGroup2 = trackGroupArray.get(i3);
        if (!parameters.forceHighestSupportedBitrate && !parameters.forceLowestBitrate && z2) {
            int[] adaptiveAudioTracks = getAdaptiveAudioTracks(trackGroup2, iArr[i3], i4, parameters.maxAudioBitrate, parameters.allowAudioMixedMimeTypeAdaptiveness, parameters.allowAudioMixedSampleRateAdaptiveness, parameters.allowAudioMixedChannelCountAdaptiveness);
            if (adaptiveAudioTracks.length > 1) {
                definition = new ExoTrackSelection.Definition(trackGroup2, adaptiveAudioTracks);
            }
        }
        if (definition == null) {
            definition = new ExoTrackSelection.Definition(trackGroup2, i4);
        }
        return Pair.create(definition, (AudioTrackScore) Assertions.checkNotNull(audioTrackScore));
    }

    @Nullable
    public ExoTrackSelection.Definition selectOtherTrack(int i2, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) throws ExoPlaybackException {
        TrackGroup trackGroup = null;
        OtherTrackScore otherTrackScore = null;
        int i3 = 0;
        for (int i4 = 0; i4 < trackGroupArray.length; i4++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i4);
            int[] iArr2 = iArr[i4];
            for (int i5 = 0; i5 < trackGroup2.length; i5++) {
                if (isSupported(iArr2[i5], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    OtherTrackScore otherTrackScore2 = new OtherTrackScore(trackGroup2.getFormat(i5), iArr2[i5]);
                    if (otherTrackScore == null || otherTrackScore2.compareTo(otherTrackScore) > 0) {
                        trackGroup = trackGroup2;
                        i3 = i5;
                        otherTrackScore = otherTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i3);
    }

    @Nullable
    public Pair<ExoTrackSelection.Definition, TextTrackScore> selectTextTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters, @Nullable String str) throws ExoPlaybackException {
        int i2 = -1;
        TrackGroup trackGroup = null;
        TextTrackScore textTrackScore = null;
        for (int i3 = 0; i3 < trackGroupArray.length; i3++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i3);
            int[] iArr2 = iArr[i3];
            for (int i4 = 0; i4 < trackGroup2.length; i4++) {
                if (isSupported(iArr2[i4], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    TextTrackScore textTrackScore2 = new TextTrackScore(trackGroup2.getFormat(i4), parameters, iArr2[i4], str);
                    if (textTrackScore2.isWithinConstraints && (textTrackScore == null || textTrackScore2.compareTo(textTrackScore) > 0)) {
                        trackGroup = trackGroup2;
                        i2 = i4;
                        textTrackScore = textTrackScore2;
                    }
                }
            }
        }
        if (trackGroup == null) {
            return null;
        }
        return Pair.create(new ExoTrackSelection.Definition(trackGroup, i2), (TextTrackScore) Assertions.checkNotNull(textTrackScore));
    }

    @Override // com.google.android.exoplayer2.trackselection.MappingTrackSelector
    public final Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        Parameters parameters = this.parametersReference.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] definitionArrSelectAllTracks = selectAllTracks(mappedTrackInfo, iArr, iArr2, parameters);
        SparseArray<Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer>> applicableOverrides = getApplicableOverrides(mappedTrackInfo, parameters);
        for (int i2 = 0; i2 < applicableOverrides.size(); i2++) {
            Pair<TrackSelectionOverrides.TrackSelectionOverride, Integer> pairValueAt = applicableOverrides.valueAt(i2);
            applyTrackTypeOverride(mappedTrackInfo, definitionArrSelectAllTracks, applicableOverrides.keyAt(i2), (TrackSelectionOverrides.TrackSelectionOverride) pairValueAt.first, ((Integer) pairValueAt.second).intValue());
        }
        for (int i3 = 0; i3 < rendererCount; i3++) {
            if (hasLegacyRendererOverride(mappedTrackInfo, parameters, i3)) {
                definitionArrSelectAllTracks[i3] = getLegacyRendererOverride(mappedTrackInfo, parameters, i3);
            }
        }
        for (int i4 = 0; i4 < rendererCount; i4++) {
            if (isRendererDisabled(mappedTrackInfo, parameters, i4)) {
                definitionArrSelectAllTracks[i4] = null;
            }
        }
        ExoTrackSelection[] exoTrackSelectionArrCreateTrackSelections = this.trackSelectionFactory.createTrackSelections(definitionArrSelectAllTracks, getBandwidthMeter(), mediaPeriodId, timeline);
        RendererConfiguration[] rendererConfigurationArr = new RendererConfiguration[rendererCount];
        for (int i5 = 0; i5 < rendererCount; i5++) {
            boolean z2 = true;
            if ((parameters.getRendererDisabled(i5) || parameters.disabledTrackTypes.contains(Integer.valueOf(mappedTrackInfo.getRendererType(i5)))) || (mappedTrackInfo.getRendererType(i5) != -2 && exoTrackSelectionArrCreateTrackSelections[i5] == null)) {
                z2 = false;
            }
            rendererConfigurationArr[i5] = z2 ? RendererConfiguration.DEFAULT : null;
        }
        if (parameters.tunnelingEnabled) {
            maybeConfigureRenderersForTunneling(mappedTrackInfo, iArr, rendererConfigurationArr, exoTrackSelectionArrCreateTrackSelections);
        }
        return Pair.create(rendererConfigurationArr, exoTrackSelectionArrCreateTrackSelections);
    }

    @Nullable
    public ExoTrackSelection.Definition selectVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i2, Parameters parameters, boolean z2) throws ExoPlaybackException {
        ExoTrackSelection.Definition definitionSelectAdaptiveVideoTrack = (parameters.forceHighestSupportedBitrate || parameters.forceLowestBitrate || !z2) ? null : selectAdaptiveVideoTrack(trackGroupArray, iArr, i2, parameters);
        return definitionSelectAdaptiveVideoTrack == null ? selectFixedVideoTrack(trackGroupArray, iArr, parameters) : definitionSelectAdaptiveVideoTrack;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public void setParameters(TrackSelectionParameters trackSelectionParameters) {
        if (trackSelectionParameters instanceof Parameters) {
            setParametersInternal((Parameters) trackSelectionParameters);
        }
        setParametersInternal(new ParametersBuilder(this.parametersReference.get()).set(trackSelectionParameters).build());
    }

    @Deprecated
    public DefaultTrackSelector(ExoTrackSelection.Factory factory) {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, factory);
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector
    public Parameters getParameters() {
        return this.parametersReference.get();
    }

    public DefaultTrackSelector(Context context) {
        this(context, new AdaptiveTrackSelection.Factory());
    }

    public DefaultTrackSelector(Context context, ExoTrackSelection.Factory factory) {
        this(Parameters.getDefaults(context), factory);
    }

    public DefaultTrackSelector(Parameters parameters, ExoTrackSelection.Factory factory) {
        this.trackSelectionFactory = factory;
        this.parametersReference = new AtomicReference<>(parameters);
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParametersInternal(parametersBuilder.build());
    }
}
