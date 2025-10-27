package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.CheckResult;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class AdPlaybackState implements Bundleable {
    public static final int AD_STATE_AVAILABLE = 1;
    public static final int AD_STATE_ERROR = 4;
    public static final int AD_STATE_PLAYED = 3;
    public static final int AD_STATE_SKIPPED = 2;
    public static final int AD_STATE_UNAVAILABLE = 0;
    private static final int FIELD_AD_GROUPS = 1;
    private static final int FIELD_AD_RESUME_POSITION_US = 2;
    private static final int FIELD_CONTENT_DURATION_US = 3;
    private static final int FIELD_REMOVED_AD_GROUP_COUNT = 4;
    public final int adGroupCount;
    private final AdGroup[] adGroups;
    public final long adResumePositionUs;

    @Nullable
    public final Object adsId;
    public final long contentDurationUs;
    public final int removedAdGroupCount;
    public static final AdPlaybackState NONE = new AdPlaybackState(null, new AdGroup[0], 0, C.TIME_UNSET, 0);
    private static final AdGroup REMOVED_AD_GROUP = new AdGroup(0).withAdCount(0);
    public static final Bundleable.Creator<AdPlaybackState> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.ads.a
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return AdPlaybackState.fromBundle(bundle);
        }
    };

    public static final class AdGroup implements Bundleable {
        public static final Bundleable.Creator<AdGroup> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.ads.b
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return AdPlaybackState.AdGroup.fromBundle(bundle);
            }
        };
        private static final int FIELD_CONTENT_RESUME_OFFSET_US = 5;
        private static final int FIELD_COUNT = 1;
        private static final int FIELD_DURATIONS_US = 4;
        private static final int FIELD_IS_SERVER_SIDE_INSERTED = 6;
        private static final int FIELD_STATES = 3;
        private static final int FIELD_TIME_US = 0;
        private static final int FIELD_URIS = 2;
        public final long contentResumeOffsetUs;
        public final int count;
        public final long[] durationsUs;
        public final boolean isServerSideInserted;
        public final int[] states;
        public final long timeUs;
        public final Uri[] uris;

        public AdGroup(long j2) {
            this(j2, -1, new int[0], new Uri[0], new long[0], 0L, false);
        }

        @CheckResult
        private static long[] copyDurationsUsWithSpaceForAdCount(long[] jArr, int i2) {
            int length = jArr.length;
            int iMax = Math.max(i2, length);
            long[] jArrCopyOf = Arrays.copyOf(jArr, iMax);
            Arrays.fill(jArrCopyOf, length, iMax, C.TIME_UNSET);
            return jArrCopyOf;
        }

        @CheckResult
        private static int[] copyStatesWithSpaceForAdCount(int[] iArr, int i2) {
            int length = iArr.length;
            int iMax = Math.max(i2, length);
            int[] iArrCopyOf = Arrays.copyOf(iArr, iMax);
            Arrays.fill(iArrCopyOf, length, iMax, 0);
            return iArrCopyOf;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static AdGroup fromBundle(Bundle bundle) {
            long j2 = bundle.getLong(keyForField(0));
            int i2 = bundle.getInt(keyForField(1), -1);
            ArrayList parcelableArrayList = bundle.getParcelableArrayList(keyForField(2));
            int[] intArray = bundle.getIntArray(keyForField(3));
            long[] longArray = bundle.getLongArray(keyForField(4));
            long j3 = bundle.getLong(keyForField(5));
            boolean z2 = bundle.getBoolean(keyForField(6));
            if (intArray == null) {
                intArray = new int[0];
            }
            return new AdGroup(j2, i2, intArray, parcelableArrayList == null ? new Uri[0] : (Uri[]) parcelableArrayList.toArray(new Uri[0]), longArray == null ? new long[0] : longArray, j3, z2);
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || AdGroup.class != obj.getClass()) {
                return false;
            }
            AdGroup adGroup = (AdGroup) obj;
            return this.timeUs == adGroup.timeUs && this.count == adGroup.count && Arrays.equals(this.uris, adGroup.uris) && Arrays.equals(this.states, adGroup.states) && Arrays.equals(this.durationsUs, adGroup.durationsUs) && this.contentResumeOffsetUs == adGroup.contentResumeOffsetUs && this.isServerSideInserted == adGroup.isServerSideInserted;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(@IntRange(from = -1) int i2) {
            int i3;
            int i4 = i2 + 1;
            while (true) {
                int[] iArr = this.states;
                if (i4 >= iArr.length || this.isServerSideInserted || (i3 = iArr[i4]) == 0 || i3 == 1) {
                    break;
                }
                i4++;
            }
            return i4;
        }

        public boolean hasUnplayedAds() {
            if (this.count == -1) {
                return true;
            }
            for (int i2 = 0; i2 < this.count; i2++) {
                int i3 = this.states[i2];
                if (i3 == 0 || i3 == 1) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            int i2 = this.count * 31;
            long j2 = this.timeUs;
            int iHashCode = (((((((i2 + ((int) (j2 ^ (j2 >>> 32)))) * 31) + Arrays.hashCode(this.uris)) * 31) + Arrays.hashCode(this.states)) * 31) + Arrays.hashCode(this.durationsUs)) * 31;
            long j3 = this.contentResumeOffsetUs;
            return ((iHashCode + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.isServerSideInserted ? 1 : 0);
        }

        public boolean shouldPlayAdGroup() {
            return this.count == -1 || getFirstAdIndexToPlay() < this.count;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.timeUs);
            bundle.putInt(keyForField(1), this.count);
            bundle.putParcelableArrayList(keyForField(2), new ArrayList<>(Arrays.asList(this.uris)));
            bundle.putIntArray(keyForField(3), this.states);
            bundle.putLongArray(keyForField(4), this.durationsUs);
            bundle.putLong(keyForField(5), this.contentResumeOffsetUs);
            bundle.putBoolean(keyForField(6), this.isServerSideInserted);
            return bundle;
        }

        @CheckResult
        public AdGroup withAdCount(int i2) {
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, i2);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(this.durationsUs, i2);
            return new AdGroup(this.timeUs, i2, iArrCopyStatesWithSpaceForAdCount, (Uri[]) Arrays.copyOf(this.uris, i2), jArrCopyDurationsUsWithSpaceForAdCount, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdDurationsUs(long[] jArr) {
            int length = jArr.length;
            Uri[] uriArr = this.uris;
            if (length < uriArr.length) {
                jArr = copyDurationsUsWithSpaceForAdCount(jArr, uriArr.length);
            } else if (this.count != -1 && jArr.length > uriArr.length) {
                jArr = Arrays.copyOf(jArr, uriArr.length);
            }
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, jArr, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdState(int i2, @IntRange(from = 0) int i3) {
            int i4 = this.count;
            Assertions.checkArgument(i4 == -1 || i3 < i4);
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, i3 + 1);
            int i5 = iArrCopyStatesWithSpaceForAdCount[i3];
            Assertions.checkArgument(i5 == 0 || i5 == 1 || i5 == i2);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = this.durationsUs;
            if (jArrCopyDurationsUsWithSpaceForAdCount.length != iArrCopyStatesWithSpaceForAdCount.length) {
                jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(jArrCopyDurationsUsWithSpaceForAdCount, iArrCopyStatesWithSpaceForAdCount.length);
            }
            long[] jArr = jArrCopyDurationsUsWithSpaceForAdCount;
            Uri[] uriArr = this.uris;
            if (uriArr.length != iArrCopyStatesWithSpaceForAdCount.length) {
                uriArr = (Uri[]) Arrays.copyOf(uriArr, iArrCopyStatesWithSpaceForAdCount.length);
            }
            iArrCopyStatesWithSpaceForAdCount[i3] = i2;
            return new AdGroup(this.timeUs, this.count, iArrCopyStatesWithSpaceForAdCount, uriArr, jArr, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAdUri(Uri uri, @IntRange(from = 0) int i2) {
            int[] iArrCopyStatesWithSpaceForAdCount = copyStatesWithSpaceForAdCount(this.states, i2 + 1);
            long[] jArrCopyDurationsUsWithSpaceForAdCount = this.durationsUs;
            if (jArrCopyDurationsUsWithSpaceForAdCount.length != iArrCopyStatesWithSpaceForAdCount.length) {
                jArrCopyDurationsUsWithSpaceForAdCount = copyDurationsUsWithSpaceForAdCount(jArrCopyDurationsUsWithSpaceForAdCount, iArrCopyStatesWithSpaceForAdCount.length);
            }
            long[] jArr = jArrCopyDurationsUsWithSpaceForAdCount;
            Uri[] uriArr = (Uri[]) Arrays.copyOf(this.uris, iArrCopyStatesWithSpaceForAdCount.length);
            uriArr[i2] = uri;
            iArrCopyStatesWithSpaceForAdCount[i2] = 1;
            return new AdGroup(this.timeUs, this.count, iArrCopyStatesWithSpaceForAdCount, uriArr, jArr, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withAllAdsSkipped() {
            if (this.count == -1) {
                return new AdGroup(this.timeUs, 0, new int[0], new Uri[0], new long[0], this.contentResumeOffsetUs, this.isServerSideInserted);
            }
            int[] iArr = this.states;
            int length = iArr.length;
            int[] iArrCopyOf = Arrays.copyOf(iArr, length);
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArrCopyOf[i2];
                if (i3 == 1 || i3 == 0) {
                    iArrCopyOf[i2] = 2;
                }
            }
            return new AdGroup(this.timeUs, length, iArrCopyOf, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withContentResumeOffsetUs(long j2) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, j2, this.isServerSideInserted);
        }

        @CheckResult
        public AdGroup withIsServerSideInserted(boolean z2) {
            return new AdGroup(this.timeUs, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, z2);
        }

        @CheckResult
        public AdGroup withTimeUs(long j2) {
            return new AdGroup(j2, this.count, this.states, this.uris, this.durationsUs, this.contentResumeOffsetUs, this.isServerSideInserted);
        }

        private AdGroup(long j2, int i2, int[] iArr, Uri[] uriArr, long[] jArr, long j3, boolean z2) {
            Assertions.checkArgument(iArr.length == uriArr.length);
            this.timeUs = j2;
            this.count = i2;
            this.states = iArr;
            this.uris = uriArr;
            this.durationsUs = jArr;
            this.contentResumeOffsetUs = j3;
            this.isServerSideInserted = z2;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdState {
    }

    public AdPlaybackState(Object obj, long... jArr) {
        this(obj, createEmptyAdGroups(jArr), 0L, C.TIME_UNSET, 0);
    }

    private static AdGroup[] createEmptyAdGroups(long[] jArr) {
        int length = jArr.length;
        AdGroup[] adGroupArr = new AdGroup[length];
        for (int i2 = 0; i2 < length; i2++) {
            adGroupArr[i2] = new AdGroup(jArr[i2]);
        }
        return adGroupArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AdPlaybackState fromBundle(Bundle bundle) {
        AdGroup[] adGroupArr;
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(keyForField(1));
        if (parcelableArrayList == null) {
            adGroupArr = new AdGroup[0];
        } else {
            AdGroup[] adGroupArr2 = new AdGroup[parcelableArrayList.size()];
            for (int i2 = 0; i2 < parcelableArrayList.size(); i2++) {
                adGroupArr2[i2] = (AdGroup) AdGroup.CREATOR.fromBundle((Bundle) parcelableArrayList.get(i2));
            }
            adGroupArr = adGroupArr2;
        }
        return new AdPlaybackState(null, adGroupArr, bundle.getLong(keyForField(2), 0L), bundle.getLong(keyForField(3), C.TIME_UNSET), bundle.getInt(keyForField(4)));
    }

    private boolean isPositionBeforeAdGroup(long j2, long j3, int i2) {
        if (j2 == Long.MIN_VALUE) {
            return false;
        }
        long j4 = getAdGroup(i2).timeUs;
        return j4 == Long.MIN_VALUE ? j3 == C.TIME_UNSET || j2 < j3 : j2 < j4;
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AdPlaybackState.class != obj.getClass()) {
            return false;
        }
        AdPlaybackState adPlaybackState = (AdPlaybackState) obj;
        return Util.areEqual(this.adsId, adPlaybackState.adsId) && this.adGroupCount == adPlaybackState.adGroupCount && this.adResumePositionUs == adPlaybackState.adResumePositionUs && this.contentDurationUs == adPlaybackState.contentDurationUs && this.removedAdGroupCount == adPlaybackState.removedAdGroupCount && Arrays.equals(this.adGroups, adPlaybackState.adGroups);
    }

    public AdGroup getAdGroup(@IntRange(from = 0) int i2) {
        int i3 = this.removedAdGroupCount;
        return i2 < i3 ? REMOVED_AD_GROUP : this.adGroups[i2 - i3];
    }

    public int getAdGroupIndexAfterPositionUs(long j2, long j3) {
        if (j2 == Long.MIN_VALUE) {
            return -1;
        }
        if (j3 != C.TIME_UNSET && j2 >= j3) {
            return -1;
        }
        int i2 = this.removedAdGroupCount;
        while (i2 < this.adGroupCount && ((getAdGroup(i2).timeUs != Long.MIN_VALUE && getAdGroup(i2).timeUs <= j2) || !getAdGroup(i2).shouldPlayAdGroup())) {
            i2++;
        }
        if (i2 < this.adGroupCount) {
            return i2;
        }
        return -1;
    }

    public int getAdGroupIndexForPositionUs(long j2, long j3) {
        int i2 = this.adGroupCount - 1;
        while (i2 >= 0 && isPositionBeforeAdGroup(j2, j3, i2)) {
            i2--;
        }
        if (i2 < 0 || !getAdGroup(i2).hasUnplayedAds()) {
            return -1;
        }
        return i2;
    }

    public int hashCode() {
        int i2 = this.adGroupCount * 31;
        Object obj = this.adsId;
        return ((((((((i2 + (obj == null ? 0 : obj.hashCode())) * 31) + ((int) this.adResumePositionUs)) * 31) + ((int) this.contentDurationUs)) * 31) + this.removedAdGroupCount) * 31) + Arrays.hashCode(this.adGroups);
    }

    public boolean isAdInErrorState(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        AdGroup adGroup;
        int i4;
        return i2 < this.adGroupCount && (i4 = (adGroup = getAdGroup(i2)).count) != -1 && i3 < i4 && adGroup.states[i3] == 4;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        for (AdGroup adGroup : this.adGroups) {
            arrayList.add(adGroup.toBundle());
        }
        bundle.putParcelableArrayList(keyForField(1), arrayList);
        bundle.putLong(keyForField(2), this.adResumePositionUs);
        bundle.putLong(keyForField(3), this.contentDurationUs);
        bundle.putInt(keyForField(4), this.removedAdGroupCount);
        return bundle;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdPlaybackState(adsId=");
        sb.append(this.adsId);
        sb.append(", adResumePositionUs=");
        sb.append(this.adResumePositionUs);
        sb.append(", adGroups=[");
        for (int i2 = 0; i2 < this.adGroups.length; i2++) {
            sb.append("adGroup(timeUs=");
            sb.append(this.adGroups[i2].timeUs);
            sb.append(", ads=[");
            for (int i3 = 0; i3 < this.adGroups[i2].states.length; i3++) {
                sb.append("ad(state=");
                int i4 = this.adGroups[i2].states[i3];
                if (i4 == 0) {
                    sb.append('_');
                } else if (i4 == 1) {
                    sb.append('R');
                } else if (i4 == 2) {
                    sb.append('S');
                } else if (i4 == 3) {
                    sb.append('P');
                } else if (i4 != 4) {
                    sb.append('?');
                } else {
                    sb.append('!');
                }
                sb.append(", durationUs=");
                sb.append(this.adGroups[i2].durationsUs[i3]);
                sb.append(')');
                if (i3 < this.adGroups[i2].states.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("])");
            if (i2 < this.adGroups.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("])");
        return sb.toString();
    }

    @CheckResult
    public AdPlaybackState withAdCount(@IntRange(from = 0) int i2, @IntRange(from = 1) int i3) {
        Assertions.checkArgument(i3 > 0);
        int i4 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        if (adGroupArr[i4].count == i3) {
            return this;
        }
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i4] = this.adGroups[i4].withAdCount(i3);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(long[][] jArr) {
        Assertions.checkState(this.removedAdGroupCount == 0);
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        for (int i2 = 0; i2 < this.adGroupCount; i2++) {
            adGroupArr2[i2] = adGroupArr2[i2].withAdDurationsUs(jArr[i2]);
        }
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdGroupTimeUs(@IntRange(from = 0) int i2, long j2) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = this.adGroups[i3].withTimeUs(j2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdLoadError(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        int i4 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i4] = adGroupArr2[i4].withAdState(4, i3);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdResumePositionUs(long j2) {
        return this.adResumePositionUs == j2 ? this : new AdPlaybackState(this.adsId, this.adGroups, j2, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withAdUri(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3, Uri uri) {
        int i4 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i4] = adGroupArr2[i4].withAdUri(uri, i3);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withContentDurationUs(long j2) {
        return this.contentDurationUs == j2 ? this : new AdPlaybackState(this.adsId, this.adGroups, this.adResumePositionUs, j2, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withContentResumeOffsetUs(@IntRange(from = 0) int i2, long j2) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        if (adGroupArr[i3].contentResumeOffsetUs == j2) {
            return this;
        }
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withContentResumeOffsetUs(j2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withIsServerSideInserted(@IntRange(from = 0) int i2, boolean z2) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        if (adGroupArr[i3].isServerSideInserted == z2) {
            return this;
        }
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withIsServerSideInserted(z2);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withNewAdGroup(@IntRange(from = 0) int i2, long j2) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup adGroup = new AdGroup(j2);
        AdGroup[] adGroupArr = (AdGroup[]) Util.nullSafeArrayAppend(this.adGroups, adGroup);
        System.arraycopy(adGroupArr, i3, adGroupArr, i3 + 1, this.adGroups.length - i3);
        adGroupArr[i3] = adGroup;
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withPlayedAd(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        int i4 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i4] = adGroupArr2[i4].withAdState(3, i3);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withRemovedAdGroupCount(@IntRange(from = 0) int i2) {
        int i3 = this.removedAdGroupCount;
        if (i3 == i2) {
            return this;
        }
        Assertions.checkArgument(i2 > i3);
        int i4 = this.adGroupCount - i2;
        AdGroup[] adGroupArr = new AdGroup[i4];
        System.arraycopy(this.adGroups, i2 - this.removedAdGroupCount, adGroupArr, 0, i4);
        return new AdPlaybackState(this.adsId, adGroupArr, this.adResumePositionUs, this.contentDurationUs, i2);
    }

    @CheckResult
    public AdPlaybackState withSkippedAd(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        int i4 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i4] = adGroupArr2[i4].withAdState(2, i3);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    @CheckResult
    public AdPlaybackState withSkippedAdGroup(@IntRange(from = 0) int i2) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAllAdsSkipped();
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }

    private AdPlaybackState(@Nullable Object obj, AdGroup[] adGroupArr, long j2, long j3, int i2) {
        this.adsId = obj;
        this.adResumePositionUs = j2;
        this.contentDurationUs = j3;
        this.adGroupCount = adGroupArr.length + i2;
        this.adGroups = adGroupArr;
        this.removedAdGroupCount = i2;
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(@IntRange(from = 0) int i2, long... jArr) {
        int i3 = i2 - this.removedAdGroupCount;
        AdGroup[] adGroupArr = this.adGroups;
        AdGroup[] adGroupArr2 = (AdGroup[]) Util.nullSafeArrayCopy(adGroupArr, adGroupArr.length);
        adGroupArr2[i3] = adGroupArr2[i3].withAdDurationsUs(jArr);
        return new AdPlaybackState(this.adsId, adGroupArr2, this.adResumePositionUs, this.contentDurationUs, this.removedAdGroupCount);
    }
}
