package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.InlineMe;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class Timeline implements Bundleable {
    private static final int FIELD_PERIODS = 1;
    private static final int FIELD_SHUFFLED_WINDOW_INDICES = 2;
    private static final int FIELD_WINDOWS = 0;
    public static final Timeline EMPTY = new Timeline() { // from class: com.google.android.exoplayer2.Timeline.1
        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int i2, Period period, boolean z2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int i2, Window window, long j2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 0;
        }
    };
    public static final Bundleable.Creator<Timeline> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.f2
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return Timeline.fromBundle(bundle);
        }
    };

    public static final class Period implements Bundleable {
        public static final Bundleable.Creator<Period> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.g2
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Timeline.Period.fromBundle(bundle);
            }
        };
        private static final int FIELD_AD_PLAYBACK_STATE = 4;
        private static final int FIELD_DURATION_US = 1;
        private static final int FIELD_PLACEHOLDER = 3;
        private static final int FIELD_POSITION_IN_WINDOW_US = 2;
        private static final int FIELD_WINDOW_INDEX = 0;
        private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;
        public long durationUs;

        @Nullable
        public Object id;
        public boolean isPlaceholder;
        public long positionInWindowUs;

        @Nullable
        public Object uid;
        public int windowIndex;

        /* JADX INFO: Access modifiers changed from: private */
        public static Period fromBundle(Bundle bundle) {
            int i2 = bundle.getInt(keyForField(0), 0);
            long j2 = bundle.getLong(keyForField(1), C.TIME_UNSET);
            long j3 = bundle.getLong(keyForField(2), 0L);
            boolean z2 = bundle.getBoolean(keyForField(3));
            Bundle bundle2 = bundle.getBundle(keyForField(4));
            AdPlaybackState adPlaybackState = bundle2 != null ? (AdPlaybackState) AdPlaybackState.CREATOR.fromBundle(bundle2) : AdPlaybackState.NONE;
            Period period = new Period();
            period.set(null, null, i2, j2, j3, adPlaybackState, z2);
            return period;
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Period.class.equals(obj.getClass())) {
                return false;
            }
            Period period = (Period) obj;
            return Util.areEqual(this.id, period.id) && Util.areEqual(this.uid, period.uid) && this.windowIndex == period.windowIndex && this.durationUs == period.durationUs && this.positionInWindowUs == period.positionInWindowUs && this.isPlaceholder == period.isPlaceholder && Util.areEqual(this.adPlaybackState, period.adPlaybackState);
        }

        public int getAdCountInAdGroup(int i2) {
            return this.adPlaybackState.getAdGroup(i2).count;
        }

        public long getAdDurationUs(int i2, int i3) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.getAdGroup(i2);
            return adGroup.count != -1 ? adGroup.durationsUs[i3] : C.TIME_UNSET;
        }

        public int getAdGroupCount() {
            return this.adPlaybackState.adGroupCount;
        }

        public int getAdGroupIndexAfterPositionUs(long j2) {
            return this.adPlaybackState.getAdGroupIndexAfterPositionUs(j2, this.durationUs);
        }

        public int getAdGroupIndexForPositionUs(long j2) {
            return this.adPlaybackState.getAdGroupIndexForPositionUs(j2, this.durationUs);
        }

        public long getAdGroupTimeUs(int i2) {
            return this.adPlaybackState.getAdGroup(i2).timeUs;
        }

        public long getAdResumePositionUs() {
            return this.adPlaybackState.adResumePositionUs;
        }

        @Nullable
        public Object getAdsId() {
            return this.adPlaybackState.adsId;
        }

        public long getContentResumeOffsetUs(int i2) {
            return this.adPlaybackState.getAdGroup(i2).contentResumeOffsetUs;
        }

        public long getDurationMs() {
            return Util.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public int getFirstAdIndexToPlay(int i2) {
            return this.adPlaybackState.getAdGroup(i2).getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int i2, int i3) {
            return this.adPlaybackState.getAdGroup(i2).getNextAdIndexToPlay(i3);
        }

        public long getPositionInWindowMs() {
            return Util.usToMs(this.positionInWindowUs);
        }

        public long getPositionInWindowUs() {
            return this.positionInWindowUs;
        }

        public int getRemovedAdGroupCount() {
            return this.adPlaybackState.removedAdGroupCount;
        }

        public boolean hasPlayedAdGroup(int i2) {
            return !this.adPlaybackState.getAdGroup(i2).hasUnplayedAds();
        }

        public int hashCode() {
            Object obj = this.id;
            int iHashCode = (217 + (obj == null ? 0 : obj.hashCode())) * 31;
            Object obj2 = this.uid;
            int iHashCode2 = (((iHashCode + (obj2 != null ? obj2.hashCode() : 0)) * 31) + this.windowIndex) * 31;
            long j2 = this.durationUs;
            int i2 = (iHashCode2 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.positionInWindowUs;
            return ((((i2 + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.isPlaceholder ? 1 : 0)) * 31) + this.adPlaybackState.hashCode();
        }

        public boolean isServerSideInsertedAdGroup(int i2) {
            return this.adPlaybackState.getAdGroup(i2).isServerSideInserted;
        }

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3) {
            return set(obj, obj2, i2, j2, j3, AdPlaybackState.NONE, false);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt(keyForField(0), this.windowIndex);
            bundle.putLong(keyForField(1), this.durationUs);
            bundle.putLong(keyForField(2), this.positionInWindowUs);
            bundle.putBoolean(keyForField(3), this.isPlaceholder);
            bundle.putBundle(keyForField(4), this.adPlaybackState.toBundle());
            return bundle;
        }

        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3, AdPlaybackState adPlaybackState, boolean z2) {
            this.id = obj;
            this.uid = obj2;
            this.windowIndex = i2;
            this.durationUs = j2;
            this.positionInWindowUs = j3;
            this.adPlaybackState = adPlaybackState;
            this.isPlaceholder = z2;
            return this;
        }
    }

    public static final class RemotableTimeline extends Timeline {
        private final ImmutableList<Period> periods;
        private final int[] shuffledWindowIndices;
        private final int[] windowIndicesInShuffled;
        private final ImmutableList<Window> windows;

        public RemotableTimeline(ImmutableList<Window> immutableList, ImmutableList<Period> immutableList2, int[] iArr) {
            Assertions.checkArgument(immutableList.size() == iArr.length);
            this.windows = immutableList;
            this.periods = immutableList2;
            this.shuffledWindowIndices = iArr;
            this.windowIndicesInShuffled = new int[iArr.length];
            for (int i2 = 0; i2 < iArr.length; i2++) {
                this.windowIndicesInShuffled[iArr[i2]] = i2;
            }
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getFirstWindowIndex(boolean z2) {
            if (isEmpty()) {
                return -1;
            }
            if (z2) {
                return this.shuffledWindowIndices[0];
            }
            return 0;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getLastWindowIndex(boolean z2) {
            if (isEmpty()) {
                return -1;
            }
            return z2 ? this.shuffledWindowIndices[getWindowCount() - 1] : getWindowCount() - 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getNextWindowIndex(int i2, int i3, boolean z2) {
            if (i3 == 1) {
                return i2;
            }
            if (i2 != getLastWindowIndex(z2)) {
                return z2 ? this.shuffledWindowIndices[this.windowIndicesInShuffled[i2] + 1] : i2 + 1;
            }
            if (i3 == 2) {
                return getFirstWindowIndex(z2);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Period getPeriod(int i2, Period period, boolean z2) {
            Period period2 = this.periods.get(i2);
            period.set(period2.id, period2.uid, period2.windowIndex, period2.durationUs, period2.positionInWindowUs, period2.adPlaybackState, period2.isPlaceholder);
            return period;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.periods.size();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
            if (i3 == 1) {
                return i2;
            }
            if (i2 != getFirstWindowIndex(z2)) {
                return z2 ? this.shuffledWindowIndices[this.windowIndicesInShuffled[i2] - 1] : i2 - 1;
            }
            if (i3 == 2) {
                return getLastWindowIndex(z2);
            }
            return -1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Object getUidOfPeriod(int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Window getWindow(int i2, Window window, long j2) {
            Window window2 = this.windows.get(i2);
            window.set(window2.uid, window2.mediaItem, window2.manifest, window2.presentationStartTimeMs, window2.windowStartTimeMs, window2.elapsedRealtimeEpochOffsetMs, window2.isSeekable, window2.isDynamic, window2.liveConfiguration, window2.defaultPositionUs, window2.durationUs, window2.firstPeriodIndex, window2.lastPeriodIndex, window2.positionInFirstPeriodUs);
            window.isPlaceholder = window2.isPlaceholder;
            return window;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.windows.size();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Timeline fromBundle(Bundle bundle) {
        ImmutableList immutableListFromBundleListRetriever = fromBundleListRetriever(Window.CREATOR, BundleUtil.getBinder(bundle, keyForField(0)));
        ImmutableList immutableListFromBundleListRetriever2 = fromBundleListRetriever(Period.CREATOR, BundleUtil.getBinder(bundle, keyForField(1)));
        int[] intArray = bundle.getIntArray(keyForField(2));
        if (intArray == null) {
            intArray = generateUnshuffledIndices(immutableListFromBundleListRetriever.size());
        }
        return new RemotableTimeline(immutableListFromBundleListRetriever, immutableListFromBundleListRetriever2, intArray);
    }

    private static <T extends Bundleable> ImmutableList<T> fromBundleListRetriever(Bundleable.Creator<T> creator, @Nullable IBinder iBinder) {
        if (iBinder == null) {
            return ImmutableList.of();
        }
        ImmutableList.Builder builder = new ImmutableList.Builder();
        ImmutableList<Bundle> list = BundleListRetriever.getList(iBinder);
        for (int i2 = 0; i2 < list.size(); i2++) {
            builder.add((ImmutableList.Builder) creator.fromBundle(list.get(i2)));
        }
        return builder.build();
    }

    private static int[] generateUnshuffledIndices(int i2) {
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = i3;
        }
        return iArr;
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Timeline)) {
            return false;
        }
        Timeline timeline = (Timeline) obj;
        if (timeline.getWindowCount() != getWindowCount() || timeline.getPeriodCount() != getPeriodCount()) {
            return false;
        }
        Window window = new Window();
        Period period = new Period();
        Window window2 = new Window();
        Period period2 = new Period();
        for (int i2 = 0; i2 < getWindowCount(); i2++) {
            if (!getWindow(i2, window).equals(timeline.getWindow(i2, window2))) {
                return false;
            }
        }
        for (int i3 = 0; i3 < getPeriodCount(); i3++) {
            if (!getPeriod(i3, period, true).equals(timeline.getPeriod(i3, period2, true))) {
                return false;
            }
        }
        return true;
    }

    public int getFirstWindowIndex(boolean z2) {
        return isEmpty() ? -1 : 0;
    }

    public abstract int getIndexOfPeriod(Object obj);

    public int getLastWindowIndex(boolean z2) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public final int getNextPeriodIndex(int i2, Period period, Window window, int i3, boolean z2) {
        int i4 = getPeriod(i2, period).windowIndex;
        if (getWindow(i4, window).lastPeriodIndex != i2) {
            return i2 + 1;
        }
        int nextWindowIndex = getNextWindowIndex(i4, i3, z2);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).firstPeriodIndex;
    }

    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        if (i3 == 0) {
            if (i2 == getLastWindowIndex(z2)) {
                return -1;
            }
            return i2 + 1;
        }
        if (i3 == 1) {
            return i2;
        }
        if (i3 == 2) {
            return i2 == getLastWindowIndex(z2) ? getFirstWindowIndex(z2) : i2 + 1;
        }
        throw new IllegalStateException();
    }

    public final Period getPeriod(int i2, Period period) {
        return getPeriod(i2, period, false);
    }

    public abstract Period getPeriod(int i2, Period period, boolean z2);

    public Period getPeriodByUid(Object obj, Period period) {
        return getPeriod(getIndexOfPeriod(obj), period, true);
    }

    public abstract int getPeriodCount();

    @InlineMe(replacement = "this.getPeriodPositionUs(window, period, windowIndex, windowPositionUs)")
    @Deprecated
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i2, long j2) {
        return getPeriodPositionUs(window, period, i2, j2);
    }

    public final Pair<Object, Long> getPeriodPositionUs(Window window, Period period, int i2, long j2) {
        return (Pair) Assertions.checkNotNull(getPeriodPosition(window, period, i2, j2, 0L));
    }

    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        if (i3 == 0) {
            if (i2 == getFirstWindowIndex(z2)) {
                return -1;
            }
            return i2 - 1;
        }
        if (i3 == 1) {
            return i2;
        }
        if (i3 == 2) {
            return i2 == getFirstWindowIndex(z2) ? getLastWindowIndex(z2) : i2 - 1;
        }
        throw new IllegalStateException();
    }

    public abstract Object getUidOfPeriod(int i2);

    public final Window getWindow(int i2, Window window) {
        return getWindow(i2, window, 0L);
    }

    public abstract Window getWindow(int i2, Window window, long j2);

    public abstract int getWindowCount();

    public int hashCode() {
        Window window = new Window();
        Period period = new Period();
        int windowCount = 217 + getWindowCount();
        for (int i2 = 0; i2 < getWindowCount(); i2++) {
            windowCount = (windowCount * 31) + getWindow(i2, window).hashCode();
        }
        int periodCount = (windowCount * 31) + getPeriodCount();
        for (int i3 = 0; i3 < getPeriodCount(); i3++) {
            periodCount = (periodCount * 31) + getPeriod(i3, period, true).hashCode();
        }
        return periodCount;
    }

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public final boolean isLastPeriod(int i2, Period period, Window window, int i3, boolean z2) {
        return getNextPeriodIndex(i2, period, window, i3, z2) == -1;
    }

    public final Bundle toBundle(boolean z2) {
        ArrayList arrayList = new ArrayList();
        int windowCount = getWindowCount();
        Window window = new Window();
        for (int i2 = 0; i2 < windowCount; i2++) {
            arrayList.add(getWindow(i2, window, 0L).toBundle(z2));
        }
        ArrayList arrayList2 = new ArrayList();
        int periodCount = getPeriodCount();
        Period period = new Period();
        for (int i3 = 0; i3 < periodCount; i3++) {
            arrayList2.add(getPeriod(i3, period, false).toBundle());
        }
        int[] iArr = new int[windowCount];
        if (windowCount > 0) {
            iArr[0] = getFirstWindowIndex(true);
        }
        for (int i4 = 1; i4 < windowCount; i4++) {
            iArr[i4] = getNextWindowIndex(iArr[i4 - 1], 0, true);
        }
        Bundle bundle = new Bundle();
        BundleUtil.putBinder(bundle, keyForField(0), new BundleListRetriever(arrayList));
        BundleUtil.putBinder(bundle, keyForField(1), new BundleListRetriever(arrayList2));
        bundle.putIntArray(keyForField(2), iArr);
        return bundle;
    }

    @Nullable
    @InlineMe(replacement = "this.getPeriodPositionUs(window, period, windowIndex, windowPositionUs, defaultPositionProjectionUs)")
    @Deprecated
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i2, long j2, long j3) {
        return getPeriodPositionUs(window, period, i2, j2, j3);
    }

    @Nullable
    public final Pair<Object, Long> getPeriodPositionUs(Window window, Period period, int i2, long j2, long j3) {
        Assertions.checkIndex(i2, 0, getWindowCount());
        getWindow(i2, window, j3);
        if (j2 == C.TIME_UNSET) {
            j2 = window.getDefaultPositionUs();
            if (j2 == C.TIME_UNSET) {
                return null;
            }
        }
        int i3 = window.firstPeriodIndex;
        getPeriod(i3, period);
        while (i3 < window.lastPeriodIndex && period.positionInWindowUs != j2) {
            int i4 = i3 + 1;
            if (getPeriod(i4, period).positionInWindowUs > j2) {
                break;
            }
            i3 = i4;
        }
        getPeriod(i3, period, true);
        long jMin = j2 - period.positionInWindowUs;
        long j4 = period.durationUs;
        if (j4 != C.TIME_UNSET) {
            jMin = Math.min(jMin, j4 - 1);
        }
        return Pair.create(Assertions.checkNotNull(period.uid), Long.valueOf(Math.max(0L, jMin)));
    }

    public static final class Window implements Bundleable {
        private static final int FIELD_DEFAULT_POSITION_US = 9;
        private static final int FIELD_DURATION_US = 10;
        private static final int FIELD_ELAPSED_REALTIME_EPOCH_OFFSET_MS = 4;
        private static final int FIELD_FIRST_PERIOD_INDEX = 11;
        private static final int FIELD_IS_DYNAMIC = 6;
        private static final int FIELD_IS_PLACEHOLDER = 8;
        private static final int FIELD_IS_SEEKABLE = 5;
        private static final int FIELD_LAST_PERIOD_INDEX = 12;
        private static final int FIELD_LIVE_CONFIGURATION = 7;
        private static final int FIELD_MEDIA_ITEM = 1;
        private static final int FIELD_POSITION_IN_FIRST_PERIOD_US = 13;
        private static final int FIELD_PRESENTATION_START_TIME_MS = 2;
        private static final int FIELD_WINDOW_START_TIME_MS = 3;
        public long defaultPositionUs;
        public long durationUs;
        public long elapsedRealtimeEpochOffsetMs;
        public int firstPeriodIndex;
        public boolean isDynamic;

        @Deprecated
        public boolean isLive;
        public boolean isPlaceholder;
        public boolean isSeekable;
        public int lastPeriodIndex;

        @Nullable
        public MediaItem.LiveConfiguration liveConfiguration;

        @Nullable
        public Object manifest;
        public long positionInFirstPeriodUs;
        public long presentationStartTimeMs;

        @Nullable
        @Deprecated
        public Object tag;
        public long windowStartTimeMs;
        public static final Object SINGLE_WINDOW_UID = new Object();
        private static final Object FAKE_WINDOW_UID = new Object();
        private static final MediaItem EMPTY_MEDIA_ITEM = new MediaItem.Builder().setMediaId("com.google.android.exoplayer2.Timeline").setUri(Uri.EMPTY).build();
        public static final Bundleable.Creator<Window> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.h2
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return Timeline.Window.fromBundle(bundle);
            }
        };
        public Object uid = SINGLE_WINDOW_UID;
        public MediaItem mediaItem = EMPTY_MEDIA_ITEM;

        /* JADX INFO: Access modifiers changed from: private */
        public static Window fromBundle(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(keyForField(1));
            MediaItem mediaItem = bundle2 != null ? (MediaItem) MediaItem.CREATOR.fromBundle(bundle2) : null;
            long j2 = bundle.getLong(keyForField(2), C.TIME_UNSET);
            long j3 = bundle.getLong(keyForField(3), C.TIME_UNSET);
            long j4 = bundle.getLong(keyForField(4), C.TIME_UNSET);
            boolean z2 = bundle.getBoolean(keyForField(5), false);
            boolean z3 = bundle.getBoolean(keyForField(6), false);
            Bundle bundle3 = bundle.getBundle(keyForField(7));
            MediaItem.LiveConfiguration liveConfiguration = bundle3 != null ? (MediaItem.LiveConfiguration) MediaItem.LiveConfiguration.CREATOR.fromBundle(bundle3) : null;
            boolean z4 = bundle.getBoolean(keyForField(8), false);
            long j5 = bundle.getLong(keyForField(9), 0L);
            long j6 = bundle.getLong(keyForField(10), C.TIME_UNSET);
            int i2 = bundle.getInt(keyForField(11), 0);
            int i3 = bundle.getInt(keyForField(12), 0);
            long j7 = bundle.getLong(keyForField(13), 0L);
            Window window = new Window();
            window.set(FAKE_WINDOW_UID, mediaItem, null, j2, j3, j4, z2, z3, liveConfiguration, j5, j6, i2, i3, j7);
            window.isPlaceholder = z4;
            return window;
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bundle toBundle(boolean z2) {
            Bundle bundle = new Bundle();
            bundle.putBundle(keyForField(1), (z2 ? MediaItem.EMPTY : this.mediaItem).toBundle());
            bundle.putLong(keyForField(2), this.presentationStartTimeMs);
            bundle.putLong(keyForField(3), this.windowStartTimeMs);
            bundle.putLong(keyForField(4), this.elapsedRealtimeEpochOffsetMs);
            bundle.putBoolean(keyForField(5), this.isSeekable);
            bundle.putBoolean(keyForField(6), this.isDynamic);
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            if (liveConfiguration != null) {
                bundle.putBundle(keyForField(7), liveConfiguration.toBundle());
            }
            bundle.putBoolean(keyForField(8), this.isPlaceholder);
            bundle.putLong(keyForField(9), this.defaultPositionUs);
            bundle.putLong(keyForField(10), this.durationUs);
            bundle.putInt(keyForField(11), this.firstPeriodIndex);
            bundle.putInt(keyForField(12), this.lastPeriodIndex);
            bundle.putLong(keyForField(13), this.positionInFirstPeriodUs);
            return bundle;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Window.class.equals(obj.getClass())) {
                return false;
            }
            Window window = (Window) obj;
            return Util.areEqual(this.uid, window.uid) && Util.areEqual(this.mediaItem, window.mediaItem) && Util.areEqual(this.manifest, window.manifest) && Util.areEqual(this.liveConfiguration, window.liveConfiguration) && this.presentationStartTimeMs == window.presentationStartTimeMs && this.windowStartTimeMs == window.windowStartTimeMs && this.elapsedRealtimeEpochOffsetMs == window.elapsedRealtimeEpochOffsetMs && this.isSeekable == window.isSeekable && this.isDynamic == window.isDynamic && this.isPlaceholder == window.isPlaceholder && this.defaultPositionUs == window.defaultPositionUs && this.durationUs == window.durationUs && this.firstPeriodIndex == window.firstPeriodIndex && this.lastPeriodIndex == window.lastPeriodIndex && this.positionInFirstPeriodUs == window.positionInFirstPeriodUs;
        }

        public long getCurrentUnixTimeMs() {
            return Util.getNowUnixTimeMs(this.elapsedRealtimeEpochOffsetMs);
        }

        public long getDefaultPositionMs() {
            return Util.usToMs(this.defaultPositionUs);
        }

        public long getDefaultPositionUs() {
            return this.defaultPositionUs;
        }

        public long getDurationMs() {
            return Util.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInFirstPeriodMs() {
            return Util.usToMs(this.positionInFirstPeriodUs);
        }

        public long getPositionInFirstPeriodUs() {
            return this.positionInFirstPeriodUs;
        }

        public int hashCode() {
            int iHashCode = (((217 + this.uid.hashCode()) * 31) + this.mediaItem.hashCode()) * 31;
            Object obj = this.manifest;
            int iHashCode2 = (iHashCode + (obj == null ? 0 : obj.hashCode())) * 31;
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            int iHashCode3 = (iHashCode2 + (liveConfiguration != null ? liveConfiguration.hashCode() : 0)) * 31;
            long j2 = this.presentationStartTimeMs;
            int i2 = (iHashCode3 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.windowStartTimeMs;
            int i3 = (i2 + ((int) (j3 ^ (j3 >>> 32)))) * 31;
            long j4 = this.elapsedRealtimeEpochOffsetMs;
            int i4 = (((((((i3 + ((int) (j4 ^ (j4 >>> 32)))) * 31) + (this.isSeekable ? 1 : 0)) * 31) + (this.isDynamic ? 1 : 0)) * 31) + (this.isPlaceholder ? 1 : 0)) * 31;
            long j5 = this.defaultPositionUs;
            int i5 = (i4 + ((int) (j5 ^ (j5 >>> 32)))) * 31;
            long j6 = this.durationUs;
            int i6 = (((((i5 + ((int) (j6 ^ (j6 >>> 32)))) * 31) + this.firstPeriodIndex) * 31) + this.lastPeriodIndex) * 31;
            long j7 = this.positionInFirstPeriodUs;
            return i6 + ((int) (j7 ^ (j7 >>> 32)));
        }

        public boolean isLive() {
            Assertions.checkState(this.isLive == (this.liveConfiguration != null));
            return this.liveConfiguration != null;
        }

        public Window set(Object obj, @Nullable MediaItem mediaItem, @Nullable Object obj2, long j2, long j3, long j4, boolean z2, boolean z3, @Nullable MediaItem.LiveConfiguration liveConfiguration, long j5, long j6, int i2, int i3, long j7) {
            MediaItem.LocalConfiguration localConfiguration;
            this.uid = obj;
            this.mediaItem = mediaItem != null ? mediaItem : EMPTY_MEDIA_ITEM;
            this.tag = (mediaItem == null || (localConfiguration = mediaItem.localConfiguration) == null) ? null : localConfiguration.tag;
            this.manifest = obj2;
            this.presentationStartTimeMs = j2;
            this.windowStartTimeMs = j3;
            this.elapsedRealtimeEpochOffsetMs = j4;
            this.isSeekable = z2;
            this.isDynamic = z3;
            this.isLive = liveConfiguration != null;
            this.liveConfiguration = liveConfiguration;
            this.defaultPositionUs = j5;
            this.durationUs = j6;
            this.firstPeriodIndex = i2;
            this.lastPeriodIndex = i3;
            this.positionInFirstPeriodUs = j7;
            this.isPlaceholder = false;
            return this;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            return toBundle(false);
        }
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public final Bundle toBundle() {
        return toBundle(false);
    }
}
