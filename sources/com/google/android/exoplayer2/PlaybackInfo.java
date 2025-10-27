package com.google.android.exoplayer2;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.common.collect.ImmutableList;
import java.util.List;

/* loaded from: classes3.dex */
final class PlaybackInfo {
    private static final MediaSource.MediaPeriodId PLACEHOLDER_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    public volatile long bufferedPositionUs;
    public final long discontinuityStartPositionUs;
    public final boolean isLoading;
    public final MediaSource.MediaPeriodId loadingMediaPeriodId;
    public final boolean offloadSchedulingEnabled;
    public final MediaSource.MediaPeriodId periodId;
    public final boolean playWhenReady;

    @Nullable
    public final ExoPlaybackException playbackError;
    public final PlaybackParameters playbackParameters;
    public final int playbackState;
    public final int playbackSuppressionReason;
    public volatile long positionUs;
    public final long requestedContentPositionUs;
    public final boolean sleepingForOffload;
    public final List<Metadata> staticMetadata;
    public final Timeline timeline;
    public volatile long totalBufferedDurationUs;
    public final TrackGroupArray trackGroups;
    public final TrackSelectorResult trackSelectorResult;

    public PlaybackInfo(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3, int i2, @Nullable ExoPlaybackException exoPlaybackException, boolean z2, TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult, List<Metadata> list, MediaSource.MediaPeriodId mediaPeriodId2, boolean z3, int i3, PlaybackParameters playbackParameters, long j4, long j5, long j6, boolean z4, boolean z5) {
        this.timeline = timeline;
        this.periodId = mediaPeriodId;
        this.requestedContentPositionUs = j2;
        this.discontinuityStartPositionUs = j3;
        this.playbackState = i2;
        this.playbackError = exoPlaybackException;
        this.isLoading = z2;
        this.trackGroups = trackGroupArray;
        this.trackSelectorResult = trackSelectorResult;
        this.staticMetadata = list;
        this.loadingMediaPeriodId = mediaPeriodId2;
        this.playWhenReady = z3;
        this.playbackSuppressionReason = i3;
        this.playbackParameters = playbackParameters;
        this.bufferedPositionUs = j4;
        this.totalBufferedDurationUs = j5;
        this.positionUs = j6;
        this.offloadSchedulingEnabled = z4;
        this.sleepingForOffload = z5;
    }

    public static PlaybackInfo createDummy(TrackSelectorResult trackSelectorResult) {
        Timeline timeline = Timeline.EMPTY;
        MediaSource.MediaPeriodId mediaPeriodId = PLACEHOLDER_MEDIA_PERIOD_ID;
        return new PlaybackInfo(timeline, mediaPeriodId, C.TIME_UNSET, 0L, 1, null, false, TrackGroupArray.EMPTY, trackSelectorResult, ImmutableList.of(), mediaPeriodId, false, 0, PlaybackParameters.DEFAULT, 0L, 0L, 0L, false, false);
    }

    public static MediaSource.MediaPeriodId getDummyPeriodForEmptyTimeline() {
        return PLACEHOLDER_MEDIA_PERIOD_ID;
    }

    @CheckResult
    public PlaybackInfo copyWithIsLoading(boolean z2) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, z2, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithLoadingMediaPeriodId(MediaSource.MediaPeriodId mediaPeriodId) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, mediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithNewPosition(MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3, long j4, long j5, TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult, List<Metadata> list) {
        return new PlaybackInfo(this.timeline, mediaPeriodId, j3, j4, this.playbackState, this.playbackError, this.isLoading, trackGroupArray, trackSelectorResult, list, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, j5, j2, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithOffloadSchedulingEnabled(boolean z2) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, z2, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithPlayWhenReady(boolean z2, int i2) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, z2, i2, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithPlaybackError(@Nullable ExoPlaybackException exoPlaybackException) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, exoPlaybackException, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithPlaybackParameters(PlaybackParameters playbackParameters) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithPlaybackState(int i2) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, i2, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }

    @CheckResult
    public PlaybackInfo copyWithSleepingForOffload(boolean z2) {
        return new PlaybackInfo(this.timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, z2);
    }

    @CheckResult
    public PlaybackInfo copyWithTimeline(Timeline timeline) {
        return new PlaybackInfo(timeline, this.periodId, this.requestedContentPositionUs, this.discontinuityStartPositionUs, this.playbackState, this.playbackError, this.isLoading, this.trackGroups, this.trackSelectorResult, this.staticMetadata, this.loadingMediaPeriodId, this.playWhenReady, this.playbackSuppressionReason, this.playbackParameters, this.bufferedPositionUs, this.totalBufferedDurationUs, this.positionUs, this.offloadSchedulingEnabled, this.sleepingForOffload);
    }
}
