package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerImplInternal;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.ListenerSet;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
final class ExoPlayerImpl extends BasePlayer {
    private static final String TAG = "ExoPlayerImpl";

    @Nullable
    private final AnalyticsCollector analyticsCollector;
    private final Looper applicationLooper;
    private final CopyOnWriteArraySet<ExoPlayer.AudioOffloadListener> audioOffloadListeners;
    private Player.Commands availableCommands;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    final TrackSelectorResult emptyTrackSelectorResult;
    private boolean foregroundMode;
    private final ExoPlayerImplInternal internalPlayer;
    private final ListenerSet<Player.EventListener> listeners;
    private int maskingPeriodIndex;
    private int maskingWindowIndex;
    private long maskingWindowPositionMs;
    private MediaMetadata mediaMetadata;
    private final MediaSourceFactory mediaSourceFactory;
    private final List<MediaSourceHolderSnapshot> mediaSourceHolderSnapshots;
    private boolean pauseAtEndOfMediaItems;
    private boolean pendingDiscontinuity;
    private int pendingDiscontinuityReason;
    private int pendingOperationAcks;
    private int pendingPlayWhenReadyChangeReason;
    private final Timeline.Period period;
    final Player.Commands permanentAvailableCommands;
    private PlaybackInfo playbackInfo;
    private final HandlerWrapper playbackInfoUpdateHandler;
    private final ExoPlayerImplInternal.PlaybackInfoUpdateListener playbackInfoUpdateListener;
    private MediaMetadata playlistMetadata;
    private final Renderer[] renderers;
    private int repeatMode;
    private final long seekBackIncrementMs;
    private final long seekForwardIncrementMs;
    private SeekParameters seekParameters;
    private boolean shuffleModeEnabled;
    private ShuffleOrder shuffleOrder;
    private MediaMetadata staticAndDynamicMediaMetadata;
    private final TrackSelector trackSelector;
    private final boolean useLazyPreparation;

    public static final class MediaSourceHolderSnapshot implements MediaSourceInfoHolder {
        private Timeline timeline;
        private final Object uid;

        public MediaSourceHolderSnapshot(Object obj, Timeline timeline) {
            this.uid = obj;
            this.timeline = timeline;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Timeline getTimeline() {
            return this.timeline;
        }

        @Override // com.google.android.exoplayer2.MediaSourceInfoHolder
        public Object getUid() {
            return this.uid;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.exoplayer");
    }

    @SuppressLint({"HandlerLeak"})
    public ExoPlayerImpl(Renderer[] rendererArr, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, @Nullable AnalyticsCollector analyticsCollector, boolean z2, SeekParameters seekParameters, long j2, long j3, LivePlaybackSpeedControl livePlaybackSpeedControl, long j4, boolean z3, Clock clock, Looper looper, @Nullable Player player, Player.Commands commands) {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 30 + String.valueOf(str).length());
        sb.append("Init ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append(StrPool.BRACKET_END);
        Log.i(TAG, sb.toString());
        Assertions.checkState(rendererArr.length > 0);
        this.renderers = (Renderer[]) Assertions.checkNotNull(rendererArr);
        this.trackSelector = (TrackSelector) Assertions.checkNotNull(trackSelector);
        this.mediaSourceFactory = mediaSourceFactory;
        this.bandwidthMeter = bandwidthMeter;
        this.analyticsCollector = analyticsCollector;
        this.useLazyPreparation = z2;
        this.seekParameters = seekParameters;
        this.seekBackIncrementMs = j2;
        this.seekForwardIncrementMs = j3;
        this.pauseAtEndOfMediaItems = z3;
        this.applicationLooper = looper;
        this.clock = clock;
        this.repeatMode = 0;
        final Player player2 = player != null ? player : this;
        this.listeners = new ListenerSet<>(looper, clock, new ListenerSet.IterationFinishedEvent() { // from class: com.google.android.exoplayer2.x0
            @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
            public final void invoke(Object obj, FlagSet flagSet) {
                ExoPlayerImpl.lambda$new$0(player2, (Player.EventListener) obj, flagSet);
            }
        });
        this.audioOffloadListeners = new CopyOnWriteArraySet<>();
        this.mediaSourceHolderSnapshots = new ArrayList();
        this.shuffleOrder = new ShuffleOrder.DefaultShuffleOrder(0);
        TrackSelectorResult trackSelectorResult = new TrackSelectorResult(new RendererConfiguration[rendererArr.length], new ExoTrackSelection[rendererArr.length], TracksInfo.EMPTY, null);
        this.emptyTrackSelectorResult = trackSelectorResult;
        this.period = new Timeline.Period();
        Player.Commands commandsBuild = new Player.Commands.Builder().addAll(1, 2, 3, 13, 14, 15, 16, 17, 18, 19, 20, 30).addIf(29, trackSelector.isSetParametersSupported()).addAll(commands).build();
        this.permanentAvailableCommands = commandsBuild;
        this.availableCommands = new Player.Commands.Builder().addAll(commandsBuild).add(4).add(10).build();
        MediaMetadata mediaMetadata = MediaMetadata.EMPTY;
        this.mediaMetadata = mediaMetadata;
        this.playlistMetadata = mediaMetadata;
        this.staticAndDynamicMediaMetadata = mediaMetadata;
        this.maskingWindowIndex = -1;
        this.playbackInfoUpdateHandler = clock.createHandler(looper, null);
        ExoPlayerImplInternal.PlaybackInfoUpdateListener playbackInfoUpdateListener = new ExoPlayerImplInternal.PlaybackInfoUpdateListener() { // from class: com.google.android.exoplayer2.y0
            @Override // com.google.android.exoplayer2.ExoPlayerImplInternal.PlaybackInfoUpdateListener
            public final void onPlaybackInfoUpdate(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
                this.f7016a.lambda$new$2(playbackInfoUpdate);
            }
        };
        this.playbackInfoUpdateListener = playbackInfoUpdateListener;
        this.playbackInfo = PlaybackInfo.createDummy(trackSelectorResult);
        if (analyticsCollector != null) {
            analyticsCollector.setPlayer(player2, looper);
            addListener(analyticsCollector);
            bandwidthMeter.addEventListener(new Handler(looper), analyticsCollector);
        }
        this.internalPlayer = new ExoPlayerImplInternal(rendererArr, trackSelector, trackSelectorResult, loadControl, bandwidthMeter, this.repeatMode, this.shuffleModeEnabled, analyticsCollector, seekParameters, livePlaybackSpeedControl, j4, z3, looper, clock, playbackInfoUpdateListener);
    }

    private List<MediaSourceList.MediaSourceHolder> addMediaSourceHolders(int i2, List<MediaSource> list) {
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < list.size(); i3++) {
            MediaSourceList.MediaSourceHolder mediaSourceHolder = new MediaSourceList.MediaSourceHolder(list.get(i3), this.useLazyPreparation);
            arrayList.add(mediaSourceHolder);
            this.mediaSourceHolderSnapshots.add(i3 + i2, new MediaSourceHolderSnapshot(mediaSourceHolder.uid, mediaSourceHolder.mediaSource.getTimeline()));
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndInsert(i2, arrayList.size());
        return arrayList;
    }

    private MediaMetadata buildUpdatedMediaMetadata() {
        MediaItem currentMediaItem = getCurrentMediaItem();
        return currentMediaItem == null ? this.staticAndDynamicMediaMetadata : this.staticAndDynamicMediaMetadata.buildUpon().populate(currentMediaItem.mediaMetadata).build();
    }

    private Timeline createMaskingTimeline() {
        return new PlaylistTimeline(this.mediaSourceHolderSnapshots, this.shuffleOrder);
    }

    private List<MediaSource> createMediaSources(List<MediaItem> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(this.mediaSourceFactory.createMediaSource(list.get(i2)));
        }
        return arrayList;
    }

    private Pair<Boolean, Integer> evaluateMediaItemTransitionReason(PlaybackInfo playbackInfo, PlaybackInfo playbackInfo2, boolean z2, int i2, boolean z3) {
        Timeline timeline = playbackInfo2.timeline;
        Timeline timeline2 = playbackInfo.timeline;
        if (timeline2.isEmpty() && timeline.isEmpty()) {
            return new Pair<>(Boolean.FALSE, -1);
        }
        int i3 = 3;
        if (timeline2.isEmpty() != timeline.isEmpty()) {
            return new Pair<>(Boolean.TRUE, 3);
        }
        if (timeline.getWindow(timeline.getPeriodByUid(playbackInfo2.periodId.periodUid, this.period).windowIndex, this.window).uid.equals(timeline2.getWindow(timeline2.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).uid)) {
            return (z2 && i2 == 0 && playbackInfo2.periodId.windowSequenceNumber < playbackInfo.periodId.windowSequenceNumber) ? new Pair<>(Boolean.TRUE, 0) : new Pair<>(Boolean.FALSE, -1);
        }
        if (z2 && i2 == 0) {
            i3 = 1;
        } else if (z2 && i2 == 1) {
            i3 = 2;
        } else if (!z3) {
            throw new IllegalStateException();
        }
        return new Pair<>(Boolean.TRUE, Integer.valueOf(i3));
    }

    private long getCurrentPositionUsInternal(PlaybackInfo playbackInfo) {
        return playbackInfo.timeline.isEmpty() ? Util.msToUs(this.maskingWindowPositionMs) : playbackInfo.periodId.isAd() ? playbackInfo.positionUs : periodPositionUsToWindowPositionUs(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.positionUs);
    }

    private int getCurrentWindowIndexInternal() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowIndex;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex;
    }

    @Nullable
    private Pair<Object, Long> getPeriodPositionAfterTimelineChanged(Timeline timeline, Timeline timeline2) {
        long contentPosition = getContentPosition();
        if (timeline.isEmpty() || timeline2.isEmpty()) {
            boolean z2 = !timeline.isEmpty() && timeline2.isEmpty();
            int currentWindowIndexInternal = z2 ? -1 : getCurrentWindowIndexInternal();
            if (z2) {
                contentPosition = -9223372036854775807L;
            }
            return getPeriodPositionOrMaskWindowPosition(timeline2, currentWindowIndexInternal, contentPosition);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, getCurrentMediaItemIndex(), Util.msToUs(contentPosition));
        Object obj = ((Pair) Util.castNonNull(periodPosition)).first;
        if (timeline2.getIndexOfPeriod(obj) != -1) {
            return periodPosition;
        }
        Object objResolveSubsequentPeriod = ExoPlayerImplInternal.resolveSubsequentPeriod(this.window, this.period, this.repeatMode, this.shuffleModeEnabled, obj, timeline, timeline2);
        if (objResolveSubsequentPeriod == null) {
            return getPeriodPositionOrMaskWindowPosition(timeline2, -1, C.TIME_UNSET);
        }
        timeline2.getPeriodByUid(objResolveSubsequentPeriod, this.period);
        int i2 = this.period.windowIndex;
        return getPeriodPositionOrMaskWindowPosition(timeline2, i2, timeline2.getWindow(i2, this.window).getDefaultPositionMs());
    }

    @Nullable
    private Pair<Object, Long> getPeriodPositionOrMaskWindowPosition(Timeline timeline, int i2, long j2) {
        if (timeline.isEmpty()) {
            this.maskingWindowIndex = i2;
            if (j2 == C.TIME_UNSET) {
                j2 = 0;
            }
            this.maskingWindowPositionMs = j2;
            this.maskingPeriodIndex = 0;
            return null;
        }
        if (i2 == -1 || i2 >= timeline.getWindowCount()) {
            i2 = timeline.getFirstWindowIndex(this.shuffleModeEnabled);
            j2 = timeline.getWindow(i2, this.window).getDefaultPositionMs();
        }
        return timeline.getPeriodPosition(this.window, this.period, i2, Util.msToUs(j2));
    }

    private Player.PositionInfo getPositionInfo(long j2) {
        MediaItem mediaItem;
        Object obj;
        int indexOfPeriod;
        Object obj2;
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        if (this.playbackInfo.timeline.isEmpty()) {
            mediaItem = null;
            obj = null;
            indexOfPeriod = -1;
            obj2 = null;
        } else {
            PlaybackInfo playbackInfo = this.playbackInfo;
            Object obj3 = playbackInfo.periodId.periodUid;
            playbackInfo.timeline.getPeriodByUid(obj3, this.period);
            indexOfPeriod = this.playbackInfo.timeline.getIndexOfPeriod(obj3);
            obj = obj3;
            obj2 = this.playbackInfo.timeline.getWindow(currentMediaItemIndex, this.window).uid;
            mediaItem = this.window.mediaItem;
        }
        long jUsToMs = Util.usToMs(j2);
        long jUsToMs2 = this.playbackInfo.periodId.isAd() ? Util.usToMs(getRequestedContentPositionUs(this.playbackInfo)) : jUsToMs;
        MediaSource.MediaPeriodId mediaPeriodId = this.playbackInfo.periodId;
        return new Player.PositionInfo(obj2, currentMediaItemIndex, mediaItem, obj, indexOfPeriod, jUsToMs, jUsToMs2, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
    }

    private Player.PositionInfo getPreviousPositionInfo(int i2, PlaybackInfo playbackInfo, int i3) {
        int i4;
        Object obj;
        MediaItem mediaItem;
        Object obj2;
        int i5;
        long requestedContentPositionUs;
        long requestedContentPositionUs2;
        Timeline.Period period = new Timeline.Period();
        if (playbackInfo.timeline.isEmpty()) {
            i4 = i3;
            obj = null;
            mediaItem = null;
            obj2 = null;
            i5 = -1;
        } else {
            Object obj3 = playbackInfo.periodId.periodUid;
            playbackInfo.timeline.getPeriodByUid(obj3, period);
            int i6 = period.windowIndex;
            int indexOfPeriod = playbackInfo.timeline.getIndexOfPeriod(obj3);
            Object obj4 = playbackInfo.timeline.getWindow(i6, this.window).uid;
            mediaItem = this.window.mediaItem;
            obj2 = obj3;
            i5 = indexOfPeriod;
            obj = obj4;
            i4 = i6;
        }
        if (i2 == 0) {
            requestedContentPositionUs = period.positionInWindowUs + period.durationUs;
            if (playbackInfo.periodId.isAd()) {
                MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
                requestedContentPositionUs = period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
                requestedContentPositionUs2 = getRequestedContentPositionUs(playbackInfo);
            } else {
                if (playbackInfo.periodId.nextAdGroupIndex != -1 && this.playbackInfo.periodId.isAd()) {
                    requestedContentPositionUs = getRequestedContentPositionUs(this.playbackInfo);
                }
                requestedContentPositionUs2 = requestedContentPositionUs;
            }
        } else if (playbackInfo.periodId.isAd()) {
            requestedContentPositionUs = playbackInfo.positionUs;
            requestedContentPositionUs2 = getRequestedContentPositionUs(playbackInfo);
        } else {
            requestedContentPositionUs = period.positionInWindowUs + playbackInfo.positionUs;
            requestedContentPositionUs2 = requestedContentPositionUs;
        }
        long jUsToMs = Util.usToMs(requestedContentPositionUs);
        long jUsToMs2 = Util.usToMs(requestedContentPositionUs2);
        MediaSource.MediaPeriodId mediaPeriodId2 = playbackInfo.periodId;
        return new Player.PositionInfo(obj, i4, mediaItem, obj2, i5, jUsToMs, jUsToMs2, mediaPeriodId2.adGroupIndex, mediaPeriodId2.adIndexInAdGroup);
    }

    private static long getRequestedContentPositionUs(PlaybackInfo playbackInfo) {
        Timeline.Window window = new Timeline.Window();
        Timeline.Period period = new Timeline.Period();
        playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, period);
        return playbackInfo.requestedContentPositionUs == C.TIME_UNSET ? playbackInfo.timeline.getWindow(period.windowIndex, window).getDefaultPositionUs() : period.getPositionInWindowUs() + playbackInfo.requestedContentPositionUs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handlePlaybackInfo, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        long j2;
        boolean z2;
        long jPeriodPositionUsToWindowPositionUs;
        int i2 = this.pendingOperationAcks - playbackInfoUpdate.operationAcks;
        this.pendingOperationAcks = i2;
        boolean z3 = true;
        if (playbackInfoUpdate.positionDiscontinuity) {
            this.pendingDiscontinuityReason = playbackInfoUpdate.discontinuityReason;
            this.pendingDiscontinuity = true;
        }
        if (playbackInfoUpdate.hasPlayWhenReadyChangeReason) {
            this.pendingPlayWhenReadyChangeReason = playbackInfoUpdate.playWhenReadyChangeReason;
        }
        if (i2 == 0) {
            Timeline timeline = playbackInfoUpdate.playbackInfo.timeline;
            if (!this.playbackInfo.timeline.isEmpty() && timeline.isEmpty()) {
                this.maskingWindowIndex = -1;
                this.maskingWindowPositionMs = 0L;
                this.maskingPeriodIndex = 0;
            }
            if (!timeline.isEmpty()) {
                List<Timeline> childTimelines = ((PlaylistTimeline) timeline).getChildTimelines();
                Assertions.checkState(childTimelines.size() == this.mediaSourceHolderSnapshots.size());
                for (int i3 = 0; i3 < childTimelines.size(); i3++) {
                    this.mediaSourceHolderSnapshots.get(i3).timeline = childTimelines.get(i3);
                }
            }
            if (this.pendingDiscontinuity) {
                if (playbackInfoUpdate.playbackInfo.periodId.equals(this.playbackInfo.periodId) && playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs == this.playbackInfo.positionUs) {
                    z3 = false;
                }
                if (z3) {
                    if (timeline.isEmpty() || playbackInfoUpdate.playbackInfo.periodId.isAd()) {
                        jPeriodPositionUsToWindowPositionUs = playbackInfoUpdate.playbackInfo.discontinuityStartPositionUs;
                    } else {
                        PlaybackInfo playbackInfo = playbackInfoUpdate.playbackInfo;
                        jPeriodPositionUsToWindowPositionUs = periodPositionUsToWindowPositionUs(timeline, playbackInfo.periodId, playbackInfo.discontinuityStartPositionUs);
                    }
                    j2 = jPeriodPositionUsToWindowPositionUs;
                } else {
                    j2 = -9223372036854775807L;
                }
                z2 = z3;
            } else {
                j2 = -9223372036854775807L;
                z2 = false;
            }
            this.pendingDiscontinuity = false;
            updatePlaybackInfo(playbackInfoUpdate.playbackInfo, 1, this.pendingPlayWhenReadyChangeReason, false, z2, this.pendingDiscontinuityReason, j2, -1);
        }
    }

    private static boolean isPlaying(PlaybackInfo playbackInfo) {
        return playbackInfo.playbackState == 3 && playbackInfo.playWhenReady && playbackInfo.playbackSuppressionReason == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(Player player, Player.EventListener eventListener, FlagSet flagSet) {
        eventListener.onEvents(player, new Player.Events(flagSet));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(final ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate) {
        this.playbackInfoUpdateHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.h0
            @Override // java.lang.Runnable
            public final void run() {
                this.f6784c.lambda$new$1(playbackInfoUpdate);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMetadata$7(Player.EventListener eventListener) {
        eventListener.onMediaMetadataChanged(this.mediaMetadata);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$release$5(Player.EventListener eventListener) {
        eventListener.onPlayerError(ExoPlaybackException.createForUnexpected(new ExoTimeoutException(1), 1003));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlaylistMetadata$8(Player.EventListener eventListener) {
        eventListener.onPlaylistMetadataChanged(this.playlistMetadata);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAvailableCommands$24(Player.EventListener eventListener) {
        eventListener.onAvailableCommandsChanged(this.availableCommands);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$10(int i2, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, Player.EventListener eventListener) {
        eventListener.onPositionDiscontinuity(i2);
        eventListener.onPositionDiscontinuity(positionInfo, positionInfo2, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$12(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlayerErrorChanged(playbackInfo.playbackError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$13(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlayerError(playbackInfo.playbackError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$14(PlaybackInfo playbackInfo, TrackSelectionArray trackSelectionArray, Player.EventListener eventListener) {
        eventListener.onTracksChanged(playbackInfo.trackGroups, trackSelectionArray);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$15(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onTracksInfoChanged(playbackInfo.trackSelectorResult.tracksInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$17(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onLoadingChanged(playbackInfo.isLoading);
        eventListener.onIsLoadingChanged(playbackInfo.isLoading);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$18(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlayerStateChanged(playbackInfo.playWhenReady, playbackInfo.playbackState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$19(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlaybackStateChanged(playbackInfo.playbackState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$20(PlaybackInfo playbackInfo, int i2, Player.EventListener eventListener) {
        eventListener.onPlayWhenReadyChanged(playbackInfo.playWhenReady, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$21(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlaybackSuppressionReasonChanged(playbackInfo.playbackSuppressionReason);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$22(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onIsPlayingChanged(isPlaying(playbackInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$23(PlaybackInfo playbackInfo, Player.EventListener eventListener) {
        eventListener.onPlaybackParametersChanged(playbackInfo.playbackParameters);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updatePlaybackInfo$9(PlaybackInfo playbackInfo, int i2, Player.EventListener eventListener) {
        eventListener.onTimelineChanged(playbackInfo.timeline, i2);
    }

    private PlaybackInfo maskTimelineAndPosition(PlaybackInfo playbackInfo, Timeline timeline, @Nullable Pair<Object, Long> pair) {
        Assertions.checkArgument(timeline.isEmpty() || pair != null);
        Timeline timeline2 = playbackInfo.timeline;
        PlaybackInfo playbackInfoCopyWithTimeline = playbackInfo.copyWithTimeline(timeline);
        if (timeline.isEmpty()) {
            MediaSource.MediaPeriodId dummyPeriodForEmptyTimeline = PlaybackInfo.getDummyPeriodForEmptyTimeline();
            long jMsToUs = Util.msToUs(this.maskingWindowPositionMs);
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithTimeline.copyWithNewPosition(dummyPeriodForEmptyTimeline, jMsToUs, jMsToUs, jMsToUs, 0L, TrackGroupArray.EMPTY, this.emptyTrackSelectorResult, ImmutableList.of()).copyWithLoadingMediaPeriodId(dummyPeriodForEmptyTimeline);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            return playbackInfoCopyWithLoadingMediaPeriodId;
        }
        Object obj = playbackInfoCopyWithTimeline.periodId.periodUid;
        boolean z2 = !obj.equals(((Pair) Util.castNonNull(pair)).first);
        MediaSource.MediaPeriodId mediaPeriodId = z2 ? new MediaSource.MediaPeriodId(pair.first) : playbackInfoCopyWithTimeline.periodId;
        long jLongValue = ((Long) pair.second).longValue();
        long jMsToUs2 = Util.msToUs(getContentPosition());
        if (!timeline2.isEmpty()) {
            jMsToUs2 -= timeline2.getPeriodByUid(obj, this.period).getPositionInWindowUs();
        }
        if (z2 || jLongValue < jMsToUs2) {
            Assertions.checkState(!mediaPeriodId.isAd());
            PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId2 = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, 0L, z2 ? TrackGroupArray.EMPTY : playbackInfoCopyWithTimeline.trackGroups, z2 ? this.emptyTrackSelectorResult : playbackInfoCopyWithTimeline.trackSelectorResult, z2 ? ImmutableList.of() : playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
            playbackInfoCopyWithLoadingMediaPeriodId2.bufferedPositionUs = jLongValue;
            return playbackInfoCopyWithLoadingMediaPeriodId2;
        }
        if (jLongValue == jMsToUs2) {
            int indexOfPeriod = timeline.getIndexOfPeriod(playbackInfoCopyWithTimeline.loadingMediaPeriodId.periodUid);
            if (indexOfPeriod == -1 || timeline.getPeriod(indexOfPeriod, this.period).windowIndex != timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex) {
                timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
                long adDurationUs = mediaPeriodId.isAd() ? this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) : this.period.durationUs;
                playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.discontinuityStartPositionUs, adDurationUs - playbackInfoCopyWithTimeline.positionUs, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata).copyWithLoadingMediaPeriodId(mediaPeriodId);
                playbackInfoCopyWithTimeline.bufferedPositionUs = adDurationUs;
            }
        } else {
            Assertions.checkState(!mediaPeriodId.isAd());
            long jMax = Math.max(0L, playbackInfoCopyWithTimeline.totalBufferedDurationUs - (jLongValue - jMsToUs2));
            long j2 = playbackInfoCopyWithTimeline.bufferedPositionUs;
            if (playbackInfoCopyWithTimeline.loadingMediaPeriodId.equals(playbackInfoCopyWithTimeline.periodId)) {
                j2 = jLongValue + jMax;
            }
            playbackInfoCopyWithTimeline = playbackInfoCopyWithTimeline.copyWithNewPosition(mediaPeriodId, jLongValue, jLongValue, jLongValue, jMax, playbackInfoCopyWithTimeline.trackGroups, playbackInfoCopyWithTimeline.trackSelectorResult, playbackInfoCopyWithTimeline.staticMetadata);
            playbackInfoCopyWithTimeline.bufferedPositionUs = j2;
        }
        return playbackInfoCopyWithTimeline;
    }

    private long periodPositionUsToWindowPositionUs(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return j2 + this.period.getPositionInWindowUs();
    }

    private PlaybackInfo removeMediaItemsInternal(int i2, int i3) {
        boolean z2 = false;
        Assertions.checkArgument(i2 >= 0 && i3 >= i2 && i3 <= this.mediaSourceHolderSnapshots.size());
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        Timeline currentTimeline = getCurrentTimeline();
        int size = this.mediaSourceHolderSnapshots.size();
        this.pendingOperationAcks++;
        removeMediaSourceHolders(i2, i3);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        int i4 = playbackInfoMaskTimelineAndPosition.playbackState;
        if (i4 != 1 && i4 != 4 && i2 < i3 && i3 == size && currentMediaItemIndex >= playbackInfoMaskTimelineAndPosition.timeline.getWindowCount()) {
            z2 = true;
        }
        if (z2) {
            playbackInfoMaskTimelineAndPosition = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(4);
        }
        this.internalPlayer.removeMediaSources(i2, i3, this.shuffleOrder);
        return playbackInfoMaskTimelineAndPosition;
    }

    private void removeMediaSourceHolders(int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            this.mediaSourceHolderSnapshots.remove(i4);
        }
        this.shuffleOrder = this.shuffleOrder.cloneAndRemove(i2, i3);
    }

    private void setMediaSourcesInternal(List<MediaSource> list, int i2, long j2, boolean z2) {
        int i3;
        long j3;
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        long currentPosition = getCurrentPosition();
        this.pendingOperationAcks++;
        if (!this.mediaSourceHolderSnapshots.isEmpty()) {
            removeMediaSourceHolders(0, this.mediaSourceHolderSnapshots.size());
        }
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(0, list);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        if (!timelineCreateMaskingTimeline.isEmpty() && i2 >= timelineCreateMaskingTimeline.getWindowCount()) {
            throw new IllegalSeekPositionException(timelineCreateMaskingTimeline, i2, j2);
        }
        if (z2) {
            int firstWindowIndex = timelineCreateMaskingTimeline.getFirstWindowIndex(this.shuffleModeEnabled);
            j3 = C.TIME_UNSET;
            i3 = firstWindowIndex;
        } else if (i2 == -1) {
            i3 = currentWindowIndexInternal;
            j3 = currentPosition;
        } else {
            i3 = i2;
            j3 = j2;
        }
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionOrMaskWindowPosition(timelineCreateMaskingTimeline, i3, j3));
        int i4 = playbackInfoMaskTimelineAndPosition.playbackState;
        if (i3 != -1 && i4 != 1) {
            i4 = (timelineCreateMaskingTimeline.isEmpty() || i3 >= timelineCreateMaskingTimeline.getWindowCount()) ? 4 : 2;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoMaskTimelineAndPosition.copyWithPlaybackState(i4);
        this.internalPlayer.setMediaSources(listAddMediaSourceHolders, i3, Util.msToUs(j3), this.shuffleOrder);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 0, 1, false, (this.playbackInfo.periodId.periodUid.equals(playbackInfoCopyWithPlaybackState.periodId.periodUid) || this.playbackInfo.timeline.isEmpty()) ? false : true, 4, getCurrentPositionUsInternal(playbackInfoCopyWithPlaybackState), -1);
    }

    private void updateAvailableCommands() {
        Player.Commands commands = this.availableCommands;
        Player.Commands availableCommands = getAvailableCommands(this.permanentAvailableCommands);
        this.availableCommands = availableCommands;
        if (availableCommands.equals(commands)) {
            return;
        }
        this.listeners.queueEvent(13, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.v0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f6979a.lambda$updateAvailableCommands$24((Player.EventListener) obj);
            }
        });
    }

    private void updatePlaybackInfo(final PlaybackInfo playbackInfo, final int i2, final int i3, boolean z2, boolean z3, final int i4, long j2, int i5) {
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        this.playbackInfo = playbackInfo;
        Pair<Boolean, Integer> pairEvaluateMediaItemTransitionReason = evaluateMediaItemTransitionReason(playbackInfo, playbackInfo2, z3, i4, !playbackInfo2.timeline.equals(playbackInfo.timeline));
        boolean zBooleanValue = ((Boolean) pairEvaluateMediaItemTransitionReason.first).booleanValue();
        final int iIntValue = ((Integer) pairEvaluateMediaItemTransitionReason.second).intValue();
        MediaMetadata mediaMetadataBuildUpdatedMediaMetadata = this.mediaMetadata;
        final MediaItem mediaItem = null;
        if (zBooleanValue) {
            if (!playbackInfo.timeline.isEmpty()) {
                mediaItem = playbackInfo.timeline.getWindow(playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period).windowIndex, this.window).mediaItem;
            }
            this.staticAndDynamicMediaMetadata = MediaMetadata.EMPTY;
        }
        if (zBooleanValue || !playbackInfo2.staticMetadata.equals(playbackInfo.staticMetadata)) {
            this.staticAndDynamicMediaMetadata = this.staticAndDynamicMediaMetadata.buildUpon().populateFromMetadata(playbackInfo.staticMetadata).build();
            mediaMetadataBuildUpdatedMediaMetadata = buildUpdatedMediaMetadata();
        }
        boolean z4 = !mediaMetadataBuildUpdatedMediaMetadata.equals(this.mediaMetadata);
        this.mediaMetadata = mediaMetadataBuildUpdatedMediaMetadata;
        if (!playbackInfo2.timeline.equals(playbackInfo.timeline)) {
            this.listeners.queueEvent(0, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.a1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$9(playbackInfo, i2, (Player.EventListener) obj);
                }
            });
        }
        if (z3) {
            final Player.PositionInfo previousPositionInfo = getPreviousPositionInfo(i4, playbackInfo2, i5);
            final Player.PositionInfo positionInfo = getPositionInfo(j2);
            this.listeners.queueEvent(11, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.i0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$10(i4, previousPositionInfo, positionInfo, (Player.EventListener) obj);
                }
            });
        }
        if (zBooleanValue) {
            this.listeners.queueEvent(1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.j0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaItemTransition(mediaItem, iIntValue);
                }
            });
        }
        if (playbackInfo2.playbackError != playbackInfo.playbackError) {
            this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.k0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$12(playbackInfo, (Player.EventListener) obj);
                }
            });
            if (playbackInfo.playbackError != null) {
                this.listeners.queueEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.l0
                    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                    public final void invoke(Object obj) {
                        ExoPlayerImpl.lambda$updatePlaybackInfo$13(playbackInfo, (Player.EventListener) obj);
                    }
                });
            }
        }
        TrackSelectorResult trackSelectorResult = playbackInfo2.trackSelectorResult;
        TrackSelectorResult trackSelectorResult2 = playbackInfo.trackSelectorResult;
        if (trackSelectorResult != trackSelectorResult2) {
            this.trackSelector.onSelectionActivated(trackSelectorResult2.info);
            final TrackSelectionArray trackSelectionArray = new TrackSelectionArray(playbackInfo.trackSelectorResult.selections);
            this.listeners.queueEvent(2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.m0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$14(playbackInfo, trackSelectionArray, (Player.EventListener) obj);
                }
            });
            this.listeners.queueEvent(2, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.n0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$15(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (z4) {
            final MediaMetadata mediaMetadata = this.mediaMetadata;
            this.listeners.queueEvent(14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.o0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onMediaMetadataChanged(mediaMetadata);
                }
            });
        }
        if (playbackInfo2.isLoading != playbackInfo.isLoading) {
            this.listeners.queueEvent(3, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.p0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$17(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (playbackInfo2.playbackState != playbackInfo.playbackState || playbackInfo2.playWhenReady != playbackInfo.playWhenReady) {
            this.listeners.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.q0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$18(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (playbackInfo2.playbackState != playbackInfo.playbackState) {
            this.listeners.queueEvent(4, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.b1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$19(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (playbackInfo2.playWhenReady != playbackInfo.playWhenReady) {
            this.listeners.queueEvent(5, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.c1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$20(playbackInfo, i3, (Player.EventListener) obj);
                }
            });
        }
        if (playbackInfo2.playbackSuppressionReason != playbackInfo.playbackSuppressionReason) {
            this.listeners.queueEvent(6, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.d1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$21(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (isPlaying(playbackInfo2) != isPlaying(playbackInfo)) {
            this.listeners.queueEvent(7, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.e1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$22(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (!playbackInfo2.playbackParameters.equals(playbackInfo.playbackParameters)) {
            this.listeners.queueEvent(12, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.f1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$updatePlaybackInfo$23(playbackInfo, (Player.EventListener) obj);
                }
            });
        }
        if (z2) {
            this.listeners.queueEvent(-1, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.g1
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onSeekProcessed();
                }
            });
        }
        updateAvailableCommands();
        this.listeners.flushEvents();
        if (playbackInfo2.offloadSchedulingEnabled != playbackInfo.offloadSchedulingEnabled) {
            Iterator<ExoPlayer.AudioOffloadListener> it = this.audioOffloadListeners.iterator();
            while (it.hasNext()) {
                it.next().onExperimentalOffloadSchedulingEnabledChanged(playbackInfo.offloadSchedulingEnabled);
            }
        }
        if (playbackInfo2.sleepingForOffload != playbackInfo.sleepingForOffload) {
            Iterator<ExoPlayer.AudioOffloadListener> it2 = this.audioOffloadListeners.iterator();
            while (it2.hasNext()) {
                it2.next().onExperimentalSleepingForOffloadChanged(playbackInfo.sleepingForOffload);
            }
        }
    }

    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.audioOffloadListeners.add(audioOffloadListener);
    }

    public void addEventListener(Player.EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        addEventListener(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i2, List<MediaItem> list) {
        addMediaSources(Math.min(i2, this.mediaSourceHolderSnapshots.size()), createMediaSources(list));
    }

    public void addMediaSource(MediaSource mediaSource) {
        addMediaSources(Collections.singletonList(mediaSource));
    }

    public void addMediaSources(List<MediaSource> list) {
        addMediaSources(this.mediaSourceHolderSnapshots.size(), list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
    }

    public PlayerMessage createMessage(PlayerMessage.Target target) {
        return new PlayerMessage(this.internalPlayer, target, this.playbackInfo.timeline, getCurrentMediaItemIndex(), this.clock, this.internalPlayer.getPlaybackLooper());
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
    }

    public boolean experimentalIsSleepingForOffload() {
        return this.playbackInfo.sleepingForOffload;
    }

    public void experimentalSetForegroundModeTimeoutMs(long j2) {
        this.internalPlayer.experimentalSetForegroundModeTimeoutMs(j2);
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        this.internalPlayer.experimentalSetOffloadSchedulingEnabled(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.applicationLooper;
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return AudioAttributes.DEFAULT;
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.availableCommands;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        if (!isPlayingAd()) {
            return getContentBufferedPosition();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.loadingMediaPeriodId.equals(playbackInfo.periodId) ? Util.usToMs(this.playbackInfo.bufferedPositionUs) : getDuration();
    }

    public Clock getClock() {
        return this.clock;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingWindowPositionMs;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.loadingMediaPeriodId.windowSequenceNumber != playbackInfo.periodId.windowSequenceNumber) {
            return playbackInfo.timeline.getWindow(getCurrentMediaItemIndex(), this.window).getDurationMs();
        }
        long j2 = playbackInfo.bufferedPositionUs;
        if (this.playbackInfo.loadingMediaPeriodId.isAd()) {
            PlaybackInfo playbackInfo2 = this.playbackInfo;
            Timeline.Period periodByUid = playbackInfo2.timeline.getPeriodByUid(playbackInfo2.loadingMediaPeriodId.periodUid, this.period);
            long adGroupTimeUs = periodByUid.getAdGroupTimeUs(this.playbackInfo.loadingMediaPeriodId.adGroupIndex);
            j2 = adGroupTimeUs == Long.MIN_VALUE ? periodByUid.durationUs : adGroupTimeUs;
        }
        PlaybackInfo playbackInfo3 = this.playbackInfo;
        return Util.usToMs(periodPositionUsToWindowPositionUs(playbackInfo3.timeline, playbackInfo3.loadingMediaPeriodId, j2));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        if (!isPlayingAd()) {
            return getCurrentPosition();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        playbackInfo.timeline.getPeriodByUid(playbackInfo.periodId.periodUid, this.period);
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        return playbackInfo2.requestedContentPositionUs == C.TIME_UNSET ? playbackInfo2.timeline.getWindow(getCurrentMediaItemIndex(), this.window).getDefaultPositionMs() : this.period.getPositionInWindowMs() + Util.usToMs(this.playbackInfo.requestedContentPositionUs);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adGroupIndex;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        if (isPlayingAd()) {
            return this.playbackInfo.periodId.adIndexInAdGroup;
        }
        return -1;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentMediaItemIndex() {
        int currentWindowIndexInternal = getCurrentWindowIndexInternal();
        if (currentWindowIndexInternal == -1) {
            return 0;
        }
        return currentWindowIndexInternal;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        if (this.playbackInfo.timeline.isEmpty()) {
            return this.maskingPeriodIndex;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.timeline.getIndexOfPeriod(playbackInfo.periodId.periodUid);
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return Util.usToMs(getCurrentPositionUsInternal(this.playbackInfo));
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.playbackInfo.timeline;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.playbackInfo.trackGroups;
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return new TrackSelectionArray(this.playbackInfo.trackSelectorResult.selections);
    }

    @Override // com.google.android.exoplayer2.Player
    public TracksInfo getCurrentTracksInfo() {
        return this.playbackInfo.trackSelectorResult.tracksInfo;
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return DeviceInfo.UNKNOWN;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        if (!isPlayingAd()) {
            return getContentDuration();
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
        playbackInfo.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        return Util.usToMs(this.period.getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup));
    }

    @Override // com.google.android.exoplayer2.Player
    public long getMaxSeekToPreviousPosition() {
        return C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.mediaMetadata;
    }

    public boolean getPauseAtEndOfMediaItems() {
        return this.pauseAtEndOfMediaItems;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.playbackInfo.playWhenReady;
    }

    public Looper getPlaybackLooper() {
        return this.internalPlayer.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.playbackInfo.playbackParameters;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.playbackInfo.playbackState;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.playbackInfo.playbackSuppressionReason;
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.playlistMetadata;
    }

    public int getRendererCount() {
        return this.renderers.length;
    }

    public int getRendererType(int i2) {
        return this.renderers[i2].getTrackType();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.repeatMode;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.seekBackIncrementMs;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.seekForwardIncrementMs;
    }

    public SeekParameters getSeekParameters() {
        return this.seekParameters;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.shuffleModeEnabled;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return Util.usToMs(this.playbackInfo.totalBufferedDurationUs);
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionParameters getTrackSelectionParameters() {
        return this.trackSelector.getParameters();
    }

    @Nullable
    public TrackSelector getTrackSelector() {
        return this.trackSelector;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return VideoSize.UNKNOWN;
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return 1.0f;
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return false;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.playbackInfo.isLoading;
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.playbackInfo.periodId.isAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i2, int i3, int i4) {
        Assertions.checkArgument(i2 >= 0 && i2 <= i3 && i3 <= this.mediaSourceHolderSnapshots.size() && i4 >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        int iMin = Math.min(i4, this.mediaSourceHolderSnapshots.size() - (i3 - i2));
        Util.moveItems(this.mediaSourceHolderSnapshots, i2, i3, iMin);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.moveMediaSources(i2, i3, iMin, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    public void onMetadata(Metadata metadata) {
        this.staticAndDynamicMediaMetadata = this.staticAndDynamicMediaMetadata.buildUpon().populateFromMetadata(metadata).build();
        MediaMetadata mediaMetadataBuildUpdatedMediaMetadata = buildUpdatedMediaMetadata();
        if (mediaMetadataBuildUpdatedMediaMetadata.equals(this.mediaMetadata)) {
            return;
        }
        this.mediaMetadata = mediaMetadataBuildUpdatedMediaMetadata;
        this.listeners.sendEvent(14, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.w0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f7012a.lambda$onMetadata$7((Player.EventListener) obj);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.playbackState != 1) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackError = playbackInfo.copyWithPlaybackError(null);
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackError.copyWithPlaybackState(playbackInfoCopyWithPlaybackError.timeline.isEmpty() ? 4 : 2);
        this.pendingOperationAcks++;
        this.internalPlayer.prepare();
        updatePlaybackInfo(playbackInfoCopyWithPlaybackState, 1, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        String hexString = Integer.toHexString(System.identityHashCode(this));
        String str = Util.DEVICE_DEBUG_INFO;
        String strRegisteredModules = ExoPlayerLibraryInfo.registeredModules();
        StringBuilder sb = new StringBuilder(String.valueOf(hexString).length() + 36 + String.valueOf(str).length() + String.valueOf(strRegisteredModules).length());
        sb.append("Release ");
        sb.append(hexString);
        sb.append(" [");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        sb.append("] [");
        sb.append(str);
        sb.append("] [");
        sb.append(strRegisteredModules);
        sb.append(StrPool.BRACKET_END);
        Log.i(TAG, sb.toString());
        if (!this.internalPlayer.release()) {
            this.listeners.sendEvent(10, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.r0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ExoPlayerImpl.lambda$release$5((Player.EventListener) obj);
                }
            });
        }
        this.listeners.release();
        this.playbackInfoUpdateHandler.removeCallbacksAndMessages(null);
        AnalyticsCollector analyticsCollector = this.analyticsCollector;
        if (analyticsCollector != null) {
            this.bandwidthMeter.removeEventListener(analyticsCollector);
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = this.playbackInfo.copyWithPlaybackState(1);
        this.playbackInfo = playbackInfoCopyWithPlaybackState;
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId = playbackInfoCopyWithPlaybackState.copyWithLoadingMediaPeriodId(playbackInfoCopyWithPlaybackState.periodId);
        this.playbackInfo = playbackInfoCopyWithLoadingMediaPeriodId;
        playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
        this.playbackInfo.totalBufferedDurationUs = 0L;
    }

    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.audioOffloadListeners.remove(audioOffloadListener);
    }

    public void removeEventListener(Player.EventListener eventListener) {
        this.listeners.remove(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        removeEventListener(listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i2, int i3) {
        PlaybackInfo playbackInfoRemoveMediaItemsInternal = removeMediaItemsInternal(i2, Math.min(i3, this.mediaSourceHolderSnapshots.size()));
        updatePlaybackInfo(playbackInfoRemoveMediaItemsInternal, 0, 1, false, !playbackInfoRemoveMediaItemsInternal.periodId.periodUid.equals(this.playbackInfo.periodId.periodUid), 4, getCurrentPositionUsInternal(playbackInfoRemoveMediaItemsInternal), -1);
    }

    @Deprecated
    public void retry() {
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i2, long j2) {
        Timeline timeline = this.playbackInfo.timeline;
        if (i2 < 0 || (!timeline.isEmpty() && i2 >= timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(timeline, i2, j2);
        }
        this.pendingOperationAcks++;
        if (isPlayingAd()) {
            Log.w(TAG, "seekTo ignored because an ad is playing");
            ExoPlayerImplInternal.PlaybackInfoUpdate playbackInfoUpdate = new ExoPlayerImplInternal.PlaybackInfoUpdate(this.playbackInfo);
            playbackInfoUpdate.incrementPendingOperationAcks(1);
            this.playbackInfoUpdateListener.onPlaybackInfoUpdate(playbackInfoUpdate);
            return;
        }
        int i3 = getPlaybackState() != 1 ? 2 : 1;
        int currentMediaItemIndex = getCurrentMediaItemIndex();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo.copyWithPlaybackState(i3), timeline, getPeriodPositionOrMaskWindowPosition(timeline, i2, j2));
        this.internalPlayer.seekTo(timeline, i2, Util.msToUs(j2));
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, true, true, 1, getCurrentPositionUsInternal(playbackInfoMaskTimelineAndPosition), currentMediaItemIndex);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z2) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i2) {
    }

    public void setForegroundMode(boolean z2) {
        if (this.foregroundMode != z2) {
            this.foregroundMode = z2;
            if (this.internalPlayer.setForegroundMode(z2)) {
                return;
            }
            stop(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(2), 1003));
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z2) {
        setMediaSources(createMediaSources(list), z2);
    }

    public void setMediaSource(MediaSource mediaSource) {
        setMediaSources(Collections.singletonList(mediaSource));
    }

    public void setMediaSources(List<MediaSource> list) {
        setMediaSources(list, true);
    }

    public void setPauseAtEndOfMediaItems(boolean z2) {
        if (this.pauseAtEndOfMediaItems == z2) {
            return;
        }
        this.pauseAtEndOfMediaItems = z2;
        this.internalPlayer.setPauseAtEndOfWindow(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z2) {
        setPlayWhenReady(z2, 0, 1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (playbackParameters == null) {
            playbackParameters = PlaybackParameters.DEFAULT;
        }
        if (this.playbackInfo.playbackParameters.equals(playbackParameters)) {
            return;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackParameters = this.playbackInfo.copyWithPlaybackParameters(playbackParameters);
        this.pendingOperationAcks++;
        this.internalPlayer.setPlaybackParameters(playbackParameters);
        updatePlaybackInfo(playbackInfoCopyWithPlaybackParameters, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        Assertions.checkNotNull(mediaMetadata);
        if (mediaMetadata.equals(this.playlistMetadata)) {
            return;
        }
        this.playlistMetadata = mediaMetadata;
        this.listeners.sendEvent(15, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.s0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                this.f6839a.lambda$setPlaylistMetadata$8((Player.EventListener) obj);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(final int i2) {
        if (this.repeatMode != i2) {
            this.repeatMode = i2;
            this.internalPlayer.setRepeatMode(i2);
            this.listeners.queueEvent(8, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.t0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onRepeatModeChanged(i2);
                }
            });
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        if (seekParameters == null) {
            seekParameters = SeekParameters.DEFAULT;
        }
        if (this.seekParameters.equals(seekParameters)) {
            return;
        }
        this.seekParameters = seekParameters;
        this.internalPlayer.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(final boolean z2) {
        if (this.shuffleModeEnabled != z2) {
            this.shuffleModeEnabled = z2;
            this.internalPlayer.setShuffleModeEnabled(z2);
            this.listeners.queueEvent(9, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.z0
                @Override // com.google.android.exoplayer2.util.ListenerSet.Event
                public final void invoke(Object obj) {
                    ((Player.EventListener) obj).onShuffleModeEnabledChanged(z2);
                }
            });
            updateAvailableCommands();
            this.listeners.flushEvents();
        }
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionOrMaskWindowPosition(timelineCreateMaskingTimeline, getCurrentMediaItemIndex(), getCurrentPosition()));
        this.pendingOperationAcks++;
        this.shuffleOrder = shuffleOrder;
        this.internalPlayer.setShuffleOrder(shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setTrackSelectionParameters(final TrackSelectionParameters trackSelectionParameters) {
        if (!this.trackSelector.isSetParametersSupported() || trackSelectionParameters.equals(this.trackSelector.getParameters())) {
            return;
        }
        this.trackSelector.setParameters(trackSelectionParameters);
        this.listeners.queueEvent(19, new ListenerSet.Event() { // from class: com.google.android.exoplayer2.u0
            @Override // com.google.android.exoplayer2.util.ListenerSet.Event
            public final void invoke(Object obj) {
                ((Player.EventListener) obj).onTrackSelectionParametersChanged(trackSelectionParameters);
            }
        });
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f2) {
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        stop(false);
    }

    public void addMediaSource(int i2, MediaSource mediaSource) {
        addMediaSources(i2, Collections.singletonList(mediaSource));
    }

    public void addMediaSources(int i2, List<MediaSource> list) {
        Assertions.checkArgument(i2 >= 0);
        Timeline currentTimeline = getCurrentTimeline();
        this.pendingOperationAcks++;
        List<MediaSourceList.MediaSourceHolder> listAddMediaSourceHolders = addMediaSourceHolders(i2, list);
        Timeline timelineCreateMaskingTimeline = createMaskingTimeline();
        PlaybackInfo playbackInfoMaskTimelineAndPosition = maskTimelineAndPosition(this.playbackInfo, timelineCreateMaskingTimeline, getPeriodPositionAfterTimelineChanged(currentTimeline, timelineCreateMaskingTimeline));
        this.internalPlayer.addMediaSources(i2, listAddMediaSourceHolders, this.shuffleOrder);
        updatePlaybackInfo(playbackInfoMaskTimelineAndPosition, 0, 1, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    public ImmutableList<Cue> getCurrentCues() {
        return ImmutableList.of();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        return this.playbackInfo.playbackError;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        setMediaSources(createMediaSources(list), i2, j2);
    }

    public void setMediaSource(MediaSource mediaSource, long j2) {
        setMediaSources(Collections.singletonList(mediaSource), 0, j2);
    }

    public void setMediaSources(List<MediaSource> list, boolean z2) {
        setMediaSourcesInternal(list, -1, C.TIME_UNSET, z2);
    }

    public void setPlayWhenReady(boolean z2, int i2, int i3) {
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.playWhenReady == z2 && playbackInfo.playbackSuppressionReason == i2) {
            return;
        }
        this.pendingOperationAcks++;
        PlaybackInfo playbackInfoCopyWithPlayWhenReady = playbackInfo.copyWithPlayWhenReady(z2, i2);
        this.internalPlayer.setPlayWhenReady(z2, i2);
        updatePlaybackInfo(playbackInfoCopyWithPlayWhenReady, 0, i3, false, false, 5, C.TIME_UNSET, -1);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean z2) {
        stop(z2, null);
    }

    public void setMediaSources(List<MediaSource> list, int i2, long j2) {
        setMediaSourcesInternal(list, i2, j2, false);
    }

    public void stop(boolean z2, @Nullable ExoPlaybackException exoPlaybackException) {
        PlaybackInfo playbackInfoCopyWithLoadingMediaPeriodId;
        if (z2) {
            playbackInfoCopyWithLoadingMediaPeriodId = removeMediaItemsInternal(0, this.mediaSourceHolderSnapshots.size()).copyWithPlaybackError(null);
        } else {
            PlaybackInfo playbackInfo = this.playbackInfo;
            playbackInfoCopyWithLoadingMediaPeriodId = playbackInfo.copyWithLoadingMediaPeriodId(playbackInfo.periodId);
            playbackInfoCopyWithLoadingMediaPeriodId.bufferedPositionUs = playbackInfoCopyWithLoadingMediaPeriodId.positionUs;
            playbackInfoCopyWithLoadingMediaPeriodId.totalBufferedDurationUs = 0L;
        }
        PlaybackInfo playbackInfoCopyWithPlaybackState = playbackInfoCopyWithLoadingMediaPeriodId.copyWithPlaybackState(1);
        if (exoPlaybackException != null) {
            playbackInfoCopyWithPlaybackState = playbackInfoCopyWithPlaybackState.copyWithPlaybackError(exoPlaybackException);
        }
        PlaybackInfo playbackInfo2 = playbackInfoCopyWithPlaybackState;
        this.pendingOperationAcks++;
        this.internalPlayer.stop();
        updatePlaybackInfo(playbackInfo2, 0, 1, false, playbackInfo2.timeline.isEmpty() && !this.playbackInfo.timeline.isEmpty(), 4, getCurrentPositionUsInternal(playbackInfo2), -1);
    }

    public void setMediaSource(MediaSource mediaSource, boolean z2) {
        setMediaSources(Collections.singletonList(mediaSource), z2);
    }

    @Deprecated
    public void prepare(MediaSource mediaSource) {
        setMediaSource(mediaSource);
        prepare();
    }

    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z2, boolean z3) {
        setMediaSource(mediaSource, z2);
        prepare();
    }
}
