package com.google.android.exoplayer2;

import android.os.Handler;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.collect.ImmutableList;

/* loaded from: classes3.dex */
final class MediaPeriodQueue {
    public static final long INITIAL_RENDERER_POSITION_OFFSET_US = 1000000000000L;
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;

    @Nullable
    private final AnalyticsCollector analyticsCollector;
    private final Handler analyticsCollectorHandler;
    private int length;

    @Nullable
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;

    @Nullable
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;

    @Nullable
    private MediaPeriodHolder playing;

    @Nullable
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private final Timeline.Period period = new Timeline.Period();
    private final Timeline.Window window = new Timeline.Window();

    public MediaPeriodQueue(@Nullable AnalyticsCollector analyticsCollector, Handler handler) {
        this.analyticsCollector = analyticsCollector;
        this.analyticsCollectorHandler = handler;
    }

    private boolean areDurationsCompatible(long j2, long j3) {
        return j2 == C.TIME_UNSET || j2 == j3;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodInfo mediaPeriodInfo, MediaPeriodInfo mediaPeriodInfo2) {
        return mediaPeriodInfo.startPositionUs == mediaPeriodInfo2.startPositionUs && mediaPeriodInfo.id.equals(mediaPeriodInfo2.id);
    }

    @Nullable
    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.requestedContentPositionUs, playbackInfo.positionUs);
    }

    @Nullable
    private MediaPeriodInfo getFollowingMediaPeriodInfo(Timeline timeline, MediaPeriodHolder mediaPeriodHolder, long j2) {
        long j3;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder.info;
        long rendererOffset = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - j2;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            long j4 = 0;
            int nextPeriodIndex = timeline.getNextPeriodIndex(timeline.getIndexOfPeriod(mediaPeriodInfo.id.periodUid), this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int i2 = timeline.getPeriod(nextPeriodIndex, this.period, true).windowIndex;
            Object obj = this.period.uid;
            long j5 = mediaPeriodInfo.id.windowSequenceNumber;
            if (timeline.getWindow(i2, this.window).firstPeriodIndex == nextPeriodIndex) {
                Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, i2, C.TIME_UNSET, Math.max(0L, rendererOffset));
                if (periodPosition == null) {
                    return null;
                }
                obj = periodPosition.first;
                long jLongValue = ((Long) periodPosition.second).longValue();
                MediaPeriodHolder next = mediaPeriodHolder.getNext();
                if (next == null || !next.uid.equals(obj)) {
                    j5 = this.nextWindowSequenceNumber;
                    this.nextWindowSequenceNumber = 1 + j5;
                } else {
                    j5 = next.info.id.windowSequenceNumber;
                }
                j3 = jLongValue;
                j4 = C.TIME_UNSET;
            } else {
                j3 = 0;
            }
            return getMediaPeriodInfo(timeline, resolveMediaPeriodIdForAds(timeline, obj, j3, j5, this.period), j4, j3);
        }
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (!mediaPeriodId.isAd()) {
            int firstAdIndexToPlay = this.period.getFirstAdIndexToPlay(mediaPeriodId.nextAdGroupIndex);
            if (firstAdIndexToPlay != this.period.getAdCountInAdGroup(mediaPeriodId.nextAdGroupIndex)) {
                return getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex, firstAdIndexToPlay, mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
            }
            return getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, getMinStartPositionAfterAdGroupUs(timeline, mediaPeriodId.periodUid, mediaPeriodId.nextAdGroupIndex), mediaPeriodInfo.durationUs, mediaPeriodId.windowSequenceNumber);
        }
        int i3 = mediaPeriodId.adGroupIndex;
        int adCountInAdGroup = this.period.getAdCountInAdGroup(i3);
        if (adCountInAdGroup == -1) {
            return null;
        }
        int nextAdIndexToPlay = this.period.getNextAdIndexToPlay(i3, mediaPeriodId.adIndexInAdGroup);
        if (nextAdIndexToPlay < adCountInAdGroup) {
            return getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, i3, nextAdIndexToPlay, mediaPeriodInfo.requestedContentPositionUs, mediaPeriodId.windowSequenceNumber);
        }
        long jLongValue2 = mediaPeriodInfo.requestedContentPositionUs;
        if (jLongValue2 == C.TIME_UNSET) {
            Timeline.Window window = this.window;
            Timeline.Period period = this.period;
            Pair<Object, Long> periodPosition2 = timeline.getPeriodPosition(window, period, period.windowIndex, C.TIME_UNSET, Math.max(0L, rendererOffset));
            if (periodPosition2 == null) {
                return null;
            }
            jLongValue2 = ((Long) periodPosition2.second).longValue();
        }
        return getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, Math.max(getMinStartPositionAfterAdGroupUs(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex), jLongValue2), mediaPeriodInfo.requestedContentPositionUs, mediaPeriodId.windowSequenceNumber);
    }

    @Nullable
    private MediaPeriodInfo getMediaPeriodInfo(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return mediaPeriodId.isAd() ? getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j2, mediaPeriodId.windowSequenceNumber) : getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, j3, j2, mediaPeriodId.windowSequenceNumber);
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Timeline timeline, Object obj, int i2, int i3, long j2, long j3) {
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, i2, i3, j3);
        long adDurationUs = timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        long adResumePositionUs = i3 == this.period.getFirstAdIndexToPlay(i2) ? this.period.getAdResumePositionUs() : 0L;
        return new MediaPeriodInfo(mediaPeriodId, (adDurationUs == C.TIME_UNSET || adResumePositionUs < adDurationUs) ? adResumePositionUs : Math.max(0L, adDurationUs - 1), j2, C.TIME_UNSET, adDurationUs, this.period.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex), false, false, false);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(Timeline timeline, Object obj, long j2, long j3, long j4) {
        long jMax = j2;
        timeline.getPeriodByUid(obj, this.period);
        int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(jMax);
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, j4, adGroupIndexAfterPositionUs);
        boolean zIsLastInPeriod = isLastInPeriod(mediaPeriodId);
        boolean zIsLastInWindow = isLastInWindow(timeline, mediaPeriodId);
        boolean zIsLastInTimeline = isLastInTimeline(timeline, mediaPeriodId, zIsLastInPeriod);
        boolean z2 = adGroupIndexAfterPositionUs != -1 && this.period.isServerSideInsertedAdGroup(adGroupIndexAfterPositionUs);
        long adGroupTimeUs = adGroupIndexAfterPositionUs != -1 ? this.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs) : -9223372036854775807L;
        long j5 = (adGroupTimeUs == C.TIME_UNSET || adGroupTimeUs == Long.MIN_VALUE) ? this.period.durationUs : adGroupTimeUs;
        if (j5 != C.TIME_UNSET && jMax >= j5) {
            jMax = Math.max(0L, j5 - 1);
        }
        return new MediaPeriodInfo(mediaPeriodId, jMax, j3, adGroupTimeUs, j5, z2, zIsLastInPeriod, zIsLastInWindow, zIsLastInTimeline);
    }

    private long getMinStartPositionAfterAdGroupUs(Timeline timeline, Object obj, int i2) {
        timeline.getPeriodByUid(obj, this.period);
        long adGroupTimeUs = this.period.getAdGroupTimeUs(i2);
        return adGroupTimeUs == Long.MIN_VALUE ? this.period.durationUs : adGroupTimeUs + this.period.getContentResumeOffsetUs(i2);
    }

    private boolean isLastInPeriod(MediaSource.MediaPeriodId mediaPeriodId) {
        return !mediaPeriodId.isAd() && mediaPeriodId.nextAdGroupIndex == -1;
    }

    private boolean isLastInTimeline(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, boolean z2) {
        int indexOfPeriod = timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        return !timeline.getWindow(timeline.getPeriod(indexOfPeriod, this.period).windowIndex, this.window).isDynamic && timeline.isLastPeriod(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && z2;
    }

    private boolean isLastInWindow(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (isLastInPeriod(mediaPeriodId)) {
            return timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, this.window).lastPeriodIndex == timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyQueueUpdate$0(ImmutableList.Builder builder, MediaSource.MediaPeriodId mediaPeriodId) {
        this.analyticsCollector.updateMediaPeriodQueueInfo(builder.build(), mediaPeriodId);
    }

    private void notifyQueueUpdate() {
        if (this.analyticsCollector != null) {
            final ImmutableList.Builder builder = ImmutableList.builder();
            for (MediaPeriodHolder next = this.playing; next != null; next = next.getNext()) {
                builder.add((ImmutableList.Builder) next.info.id);
            }
            MediaPeriodHolder mediaPeriodHolder = this.reading;
            final MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodHolder == null ? null : mediaPeriodHolder.info.id;
            this.analyticsCollectorHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.q1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6834c.lambda$notifyQueueUpdate$0(builder, mediaPeriodId);
                }
            });
        }
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Timeline timeline, Object obj) {
        int indexOfPeriod;
        int i2 = timeline.getPeriodByUid(obj, this.period).windowIndex;
        Object obj2 = this.oldFrontPeriodUid;
        if (obj2 != null && (indexOfPeriod = timeline.getIndexOfPeriod(obj2)) != -1 && timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i2) {
            return this.oldFrontPeriodWindowSequenceNumber;
        }
        for (MediaPeriodHolder next = this.playing; next != null; next = next.getNext()) {
            if (next.uid.equals(obj)) {
                return next.info.id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder next2 = this.playing; next2 != null; next2 = next2.getNext()) {
            int indexOfPeriod2 = timeline.getIndexOfPeriod(next2.uid);
            if (indexOfPeriod2 != -1 && timeline.getPeriod(indexOfPeriod2, this.period).windowIndex == i2) {
                return next2.info.id.windowSequenceNumber;
            }
        }
        long j2 = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j2;
        if (this.playing == null) {
            this.oldFrontPeriodUid = obj;
            this.oldFrontPeriodWindowSequenceNumber = j2;
        }
        return j2;
    }

    private boolean updateForPlaybackModeChange(Timeline timeline) {
        MediaPeriodHolder next = this.playing;
        if (next == null) {
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(next.uid);
        while (true) {
            indexOfPeriod = timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (next.getNext() != null && !next.info.isLastInTimelinePeriod) {
                next = next.getNext();
            }
            MediaPeriodHolder next2 = next.getNext();
            if (indexOfPeriod == -1 || next2 == null || timeline.getIndexOfPeriod(next2.uid) != indexOfPeriod) {
                break;
            }
            next = next2;
        }
        boolean zRemoveAfter = removeAfter(next);
        next.info = getUpdatedMediaPeriodInfo(timeline, next.info);
        return !zRemoveAfter;
    }

    @Nullable
    public MediaPeriodHolder advancePlayingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return null;
        }
        if (mediaPeriodHolder == this.reading) {
            this.reading = mediaPeriodHolder.getNext();
        }
        this.playing.release();
        int i2 = this.length - 1;
        this.length = i2;
        if (i2 == 0) {
            this.loading = null;
            MediaPeriodHolder mediaPeriodHolder2 = this.playing;
            this.oldFrontPeriodUid = mediaPeriodHolder2.uid;
            this.oldFrontPeriodWindowSequenceNumber = mediaPeriodHolder2.info.id.windowSequenceNumber;
        }
        this.playing = this.playing.getNext();
        notifyQueueUpdate();
        return this.playing;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.reading;
        Assertions.checkState((mediaPeriodHolder == null || mediaPeriodHolder.getNext() == null) ? false : true);
        this.reading = this.reading.getNext();
        notifyQueueUpdate();
        return this.reading;
    }

    public void clear() {
        if (this.length == 0) {
            return;
        }
        MediaPeriodHolder next = (MediaPeriodHolder) Assertions.checkStateNotNull(this.playing);
        this.oldFrontPeriodUid = next.uid;
        this.oldFrontPeriodWindowSequenceNumber = next.info.id.windowSequenceNumber;
        while (next != null) {
            next.release();
            next = next.getNext();
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
        notifyQueueUpdate();
    }

    public MediaPeriodHolder enqueueNextMediaPeriodHolder(RendererCapabilities[] rendererCapabilitiesArr, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, MediaPeriodInfo mediaPeriodInfo, TrackSelectorResult trackSelectorResult) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        MediaPeriodHolder mediaPeriodHolder2 = new MediaPeriodHolder(rendererCapabilitiesArr, mediaPeriodHolder == null ? INITIAL_RENDERER_POSITION_OFFSET_US : (mediaPeriodHolder.getRendererOffset() + this.loading.info.durationUs) - mediaPeriodInfo.startPositionUs, trackSelector, allocator, mediaSourceList, mediaPeriodInfo, trackSelectorResult);
        MediaPeriodHolder mediaPeriodHolder3 = this.loading;
        if (mediaPeriodHolder3 != null) {
            mediaPeriodHolder3.setNext(mediaPeriodHolder2);
        } else {
            this.playing = mediaPeriodHolder2;
            this.reading = mediaPeriodHolder2;
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder2;
        this.length++;
        notifyQueueUpdate();
        return mediaPeriodHolder2;
    }

    @Nullable
    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    @Nullable
    public MediaPeriodInfo getNextMediaPeriodInfo(long j2, PlaybackInfo playbackInfo) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null ? getFirstMediaPeriodInfo(playbackInfo) : getFollowingMediaPeriodInfo(playbackInfo.timeline, mediaPeriodHolder, j2);
    }

    @Nullable
    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    @Nullable
    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.MediaPeriodInfo getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline r19, com.google.android.exoplayer2.MediaPeriodInfo r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r2.id
            boolean r12 = r0.isLastInPeriod(r3)
            boolean r13 = r0.isLastInWindow(r1, r3)
            boolean r14 = r0.isLastInTimeline(r1, r3, r12)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = r2.id
            java.lang.Object r4 = r4.periodUid
            com.google.android.exoplayer2.Timeline$Period r5 = r0.period
            r1.getPeriodByUid(r4, r5)
            boolean r1 = r3.isAd()
            r4 = -1
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r1 != 0) goto L35
            int r1 = r3.nextAdGroupIndex
            if (r1 != r4) goto L2e
            goto L35
        L2e:
            com.google.android.exoplayer2.Timeline$Period r7 = r0.period
            long r7 = r7.getAdGroupTimeUs(r1)
            goto L36
        L35:
            r7 = r5
        L36:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L48
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r5 = r3.adGroupIndex
            int r6 = r3.adIndexInAdGroup
            long r5 = r1.getAdDurationUs(r5, r6)
        L46:
            r9 = r5
            goto L5c
        L48:
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 == 0) goto L55
            r5 = -9223372036854775808
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 != 0) goto L53
            goto L55
        L53:
            r9 = r7
            goto L5c
        L55:
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r5 = r1.getDurationUs()
            goto L46
        L5c:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L6c
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r4 = r3.adGroupIndex
            boolean r1 = r1.isServerSideInsertedAdGroup(r4)
        L6a:
            r11 = r1
            goto L7c
        L6c:
            int r1 = r3.nextAdGroupIndex
            if (r1 == r4) goto L7a
            com.google.android.exoplayer2.Timeline$Period r4 = r0.period
            boolean r1 = r4.isServerSideInsertedAdGroup(r1)
            if (r1 == 0) goto L7a
            r1 = 1
            goto L6a
        L7a:
            r1 = 0
            goto L6a
        L7c:
            com.google.android.exoplayer2.MediaPeriodInfo r15 = new com.google.android.exoplayer2.MediaPeriodInfo
            long r4 = r2.startPositionUs
            long r1 = r2.requestedContentPositionUs
            r16 = r1
            r1 = r15
            r2 = r3
            r3 = r4
            r5 = r16
            r1.<init>(r2, r3, r5, r7, r9, r11, r12, r13, r14)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.MediaPeriodQueue.getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.MediaPeriodInfo):com.google.android.exoplayer2.MediaPeriodInfo");
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder != null && mediaPeriodHolder.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long j2) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.reevaluateBuffer(j2);
        }
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        boolean z2 = false;
        Assertions.checkState(mediaPeriodHolder != null);
        if (mediaPeriodHolder.equals(this.loading)) {
            return false;
        }
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.getNext() != null) {
            mediaPeriodHolder = mediaPeriodHolder.getNext();
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                z2 = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.setNext(null);
        notifyQueueUpdate();
        return z2;
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object obj, long j2) {
        return resolveMediaPeriodIdForAds(timeline, obj, j2, resolvePeriodIndexToWindowSequenceNumber(timeline, obj), this.period);
    }

    public boolean shouldLoadNextMediaPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null || (!mediaPeriodHolder.info.isFinal && mediaPeriodHolder.isFullyBuffered() && this.loading.info.durationUs != C.TIME_UNSET && this.length < 100);
    }

    public boolean updateQueuedPeriods(Timeline timeline, long j2, long j3) {
        MediaPeriodInfo updatedMediaPeriodInfo;
        MediaPeriodHolder next = this.playing;
        MediaPeriodHolder mediaPeriodHolder = null;
        while (next != null) {
            MediaPeriodInfo mediaPeriodInfo = next.info;
            if (mediaPeriodHolder == null) {
                updatedMediaPeriodInfo = getUpdatedMediaPeriodInfo(timeline, mediaPeriodInfo);
            } else {
                MediaPeriodInfo followingMediaPeriodInfo = getFollowingMediaPeriodInfo(timeline, mediaPeriodHolder, j2);
                if (followingMediaPeriodInfo == null) {
                    return !removeAfter(mediaPeriodHolder);
                }
                if (!canKeepMediaPeriodHolder(mediaPeriodInfo, followingMediaPeriodInfo)) {
                    return !removeAfter(mediaPeriodHolder);
                }
                updatedMediaPeriodInfo = followingMediaPeriodInfo;
            }
            next.info = updatedMediaPeriodInfo.copyWithRequestedContentPositionUs(mediaPeriodInfo.requestedContentPositionUs);
            if (!areDurationsCompatible(mediaPeriodInfo.durationUs, updatedMediaPeriodInfo.durationUs)) {
                next.updateClipping();
                long j4 = updatedMediaPeriodInfo.durationUs;
                return (removeAfter(next) || (next == this.reading && !next.info.isFollowedByTransitionToSameStream && ((j3 > Long.MIN_VALUE ? 1 : (j3 == Long.MIN_VALUE ? 0 : -1)) == 0 || (j3 > ((j4 > C.TIME_UNSET ? 1 : (j4 == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : next.toRendererTime(j4)) ? 1 : (j3 == ((j4 > C.TIME_UNSET ? 1 : (j4 == C.TIME_UNSET ? 0 : -1)) == 0 ? Long.MAX_VALUE : next.toRendererTime(j4)) ? 0 : -1)) >= 0))) ? false : true;
            }
            mediaPeriodHolder = next;
            next = next.getNext();
        }
        return true;
    }

    public boolean updateRepeatMode(Timeline timeline, int i2) {
        this.repeatMode = i2;
        return updateForPlaybackModeChange(timeline);
    }

    public boolean updateShuffleModeEnabled(Timeline timeline, boolean z2) {
        this.shuffleModeEnabled = z2;
        return updateForPlaybackModeChange(timeline);
    }

    private static MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object obj, long j2, long j3, Timeline.Period period) {
        timeline.getPeriodByUid(obj, period);
        int adGroupIndexForPositionUs = period.getAdGroupIndexForPositionUs(j2);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaSource.MediaPeriodId(obj, j3, period.getAdGroupIndexAfterPositionUs(j2));
        }
        return new MediaSource.MediaPeriodId(obj, adGroupIndexForPositionUs, period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j3);
    }
}
