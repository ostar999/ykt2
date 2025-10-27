package com.google.android.exoplayer2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.DefaultMediaClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaSourceList;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
final class ExoPlayerImplInternal implements Handler.Callback, MediaPeriod.Callback, TrackSelector.InvalidationListener, MediaSourceList.MediaSourceListInfoRefreshListener, DefaultMediaClock.PlaybackParametersListener, PlayerMessage.Sender {
    private static final int ACTIVE_INTERVAL_MS = 10;
    private static final int IDLE_INTERVAL_MS = 1000;
    private static final long MIN_RENDERER_SLEEP_DURATION_MS = 2000;
    private static final int MSG_ADD_MEDIA_SOURCES = 18;
    private static final int MSG_ATTEMPT_RENDERER_ERROR_RECOVERY = 25;
    private static final int MSG_DO_SOME_WORK = 2;
    private static final int MSG_MOVE_MEDIA_SOURCES = 19;
    private static final int MSG_PERIOD_PREPARED = 8;
    private static final int MSG_PLAYBACK_PARAMETERS_CHANGED_INTERNAL = 16;
    private static final int MSG_PLAYLIST_UPDATE_REQUESTED = 22;
    private static final int MSG_PREPARE = 0;
    private static final int MSG_RELEASE = 7;
    private static final int MSG_REMOVE_MEDIA_SOURCES = 20;
    private static final int MSG_SEEK_TO = 3;
    private static final int MSG_SEND_MESSAGE = 14;
    private static final int MSG_SEND_MESSAGE_TO_TARGET_THREAD = 15;
    private static final int MSG_SET_FOREGROUND_MODE = 13;
    private static final int MSG_SET_MEDIA_SOURCES = 17;
    private static final int MSG_SET_OFFLOAD_SCHEDULING_ENABLED = 24;
    private static final int MSG_SET_PAUSE_AT_END_OF_WINDOW = 23;
    private static final int MSG_SET_PLAYBACK_PARAMETERS = 4;
    private static final int MSG_SET_PLAY_WHEN_READY = 1;
    private static final int MSG_SET_REPEAT_MODE = 11;
    private static final int MSG_SET_SEEK_PARAMETERS = 5;
    private static final int MSG_SET_SHUFFLE_ENABLED = 12;
    private static final int MSG_SET_SHUFFLE_ORDER = 21;
    private static final int MSG_SOURCE_CONTINUE_LOADING_REQUESTED = 9;
    private static final int MSG_STOP = 6;
    private static final int MSG_TRACK_SELECTION_INVALIDATED = 10;
    private static final String TAG = "ExoPlayerImplInternal";
    private final long backBufferDurationUs;
    private final BandwidthMeter bandwidthMeter;
    private final Clock clock;
    private boolean deliverPendingMessageAtStartPositionRequired;
    private final TrackSelectorResult emptyTrackSelectorResult;
    private int enabledRendererCount;
    private boolean foregroundMode;
    private final HandlerWrapper handler;
    private final HandlerThread internalPlaybackThread;
    private boolean isRebuffering;
    private final LivePlaybackSpeedControl livePlaybackSpeedControl;
    private final LoadControl loadControl;
    private final DefaultMediaClock mediaClock;
    private final MediaSourceList mediaSourceList;
    private int nextPendingMessageIndexHint;
    private boolean offloadSchedulingEnabled;
    private boolean pauseAtEndOfWindow;

    @Nullable
    private SeekPosition pendingInitialSeekPosition;
    private final ArrayList<PendingMessageInfo> pendingMessages;
    private boolean pendingPauseAtEndOfPeriod;

    @Nullable
    private ExoPlaybackException pendingRecoverableRendererError;
    private final Timeline.Period period;
    private PlaybackInfo playbackInfo;
    private PlaybackInfoUpdate playbackInfoUpdate;
    private final PlaybackInfoUpdateListener playbackInfoUpdateListener;
    private final Looper playbackLooper;
    private final MediaPeriodQueue queue;
    private final long releaseTimeoutMs;
    private boolean released;
    private final RendererCapabilities[] rendererCapabilities;
    private long rendererPositionUs;
    private final Renderer[] renderers;
    private final Set<Renderer> renderersToReset;
    private int repeatMode;
    private boolean requestForRendererSleep;
    private final boolean retainBackBufferFromKeyframe;
    private SeekParameters seekParameters;
    private long setForegroundModeTimeoutMs;
    private boolean shouldContinueLoading;
    private boolean shuffleModeEnabled;
    private final TrackSelector trackSelector;
    private final Timeline.Window window;

    public static final class MediaSourceListUpdateMessage {
        private final List<MediaSourceList.MediaSourceHolder> mediaSourceHolders;
        private final long positionUs;
        private final ShuffleOrder shuffleOrder;
        private final int windowIndex;

        private MediaSourceListUpdateMessage(List<MediaSourceList.MediaSourceHolder> list, ShuffleOrder shuffleOrder, int i2, long j2) {
            this.mediaSourceHolders = list;
            this.shuffleOrder = shuffleOrder;
            this.windowIndex = i2;
            this.positionUs = j2;
        }
    }

    public static class MoveMediaItemsMessage {
        public final int fromIndex;
        public final int newFromIndex;
        public final ShuffleOrder shuffleOrder;
        public final int toIndex;

        public MoveMediaItemsMessage(int i2, int i3, int i4, ShuffleOrder shuffleOrder) {
            this.fromIndex = i2;
            this.toIndex = i3;
            this.newFromIndex = i4;
            this.shuffleOrder = shuffleOrder;
        }
    }

    public static final class PendingMessageInfo implements Comparable<PendingMessageInfo> {
        public final PlayerMessage message;
        public int resolvedPeriodIndex;
        public long resolvedPeriodTimeUs;

        @Nullable
        public Object resolvedPeriodUid;

        public PendingMessageInfo(PlayerMessage playerMessage) {
            this.message = playerMessage;
        }

        public void setResolvedPosition(int i2, long j2, Object obj) {
            this.resolvedPeriodIndex = i2;
            this.resolvedPeriodTimeUs = j2;
            this.resolvedPeriodUid = obj;
        }

        @Override // java.lang.Comparable
        public int compareTo(PendingMessageInfo pendingMessageInfo) {
            Object obj = this.resolvedPeriodUid;
            if ((obj == null) != (pendingMessageInfo.resolvedPeriodUid == null)) {
                return obj != null ? -1 : 1;
            }
            if (obj == null) {
                return 0;
            }
            int i2 = this.resolvedPeriodIndex - pendingMessageInfo.resolvedPeriodIndex;
            return i2 != 0 ? i2 : Util.compareLong(this.resolvedPeriodTimeUs, pendingMessageInfo.resolvedPeriodTimeUs);
        }
    }

    public static final class PlaybackInfoUpdate {
        public int discontinuityReason;
        private boolean hasPendingChange;
        public boolean hasPlayWhenReadyChangeReason;
        public int operationAcks;
        public int playWhenReadyChangeReason;
        public PlaybackInfo playbackInfo;
        public boolean positionDiscontinuity;

        public PlaybackInfoUpdate(PlaybackInfo playbackInfo) {
            this.playbackInfo = playbackInfo;
        }

        public void incrementPendingOperationAcks(int i2) {
            this.hasPendingChange |= i2 > 0;
            this.operationAcks += i2;
        }

        public void setPlayWhenReadyChangeReason(int i2) {
            this.hasPendingChange = true;
            this.hasPlayWhenReadyChangeReason = true;
            this.playWhenReadyChangeReason = i2;
        }

        public void setPlaybackInfo(PlaybackInfo playbackInfo) {
            this.hasPendingChange |= this.playbackInfo != playbackInfo;
            this.playbackInfo = playbackInfo;
        }

        public void setPositionDiscontinuity(int i2) {
            if (this.positionDiscontinuity && this.discontinuityReason != 5) {
                Assertions.checkArgument(i2 == 5);
                return;
            }
            this.hasPendingChange = true;
            this.positionDiscontinuity = true;
            this.discontinuityReason = i2;
        }
    }

    public interface PlaybackInfoUpdateListener {
        void onPlaybackInfoUpdate(PlaybackInfoUpdate playbackInfoUpdate);
    }

    public static final class PositionUpdateForPlaylistChange {
        public final boolean endPlayback;
        public final boolean forceBufferingState;
        public final MediaSource.MediaPeriodId periodId;
        public final long periodPositionUs;
        public final long requestedContentPositionUs;
        public final boolean setTargetLiveOffset;

        public PositionUpdateForPlaylistChange(MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3, boolean z2, boolean z3, boolean z4) {
            this.periodId = mediaPeriodId;
            this.periodPositionUs = j2;
            this.requestedContentPositionUs = j3;
            this.forceBufferingState = z2;
            this.endPlayback = z3;
            this.setTargetLiveOffset = z4;
        }
    }

    public static final class SeekPosition {
        public final Timeline timeline;
        public final int windowIndex;
        public final long windowPositionUs;

        public SeekPosition(Timeline timeline, int i2, long j2) {
            this.timeline = timeline;
            this.windowIndex = i2;
            this.windowPositionUs = j2;
        }
    }

    public ExoPlayerImplInternal(Renderer[] rendererArr, TrackSelector trackSelector, TrackSelectorResult trackSelectorResult, LoadControl loadControl, BandwidthMeter bandwidthMeter, int i2, boolean z2, @Nullable AnalyticsCollector analyticsCollector, SeekParameters seekParameters, LivePlaybackSpeedControl livePlaybackSpeedControl, long j2, boolean z3, Looper looper, Clock clock, PlaybackInfoUpdateListener playbackInfoUpdateListener) {
        this.playbackInfoUpdateListener = playbackInfoUpdateListener;
        this.renderers = rendererArr;
        this.trackSelector = trackSelector;
        this.emptyTrackSelectorResult = trackSelectorResult;
        this.loadControl = loadControl;
        this.bandwidthMeter = bandwidthMeter;
        this.repeatMode = i2;
        this.shuffleModeEnabled = z2;
        this.seekParameters = seekParameters;
        this.livePlaybackSpeedControl = livePlaybackSpeedControl;
        this.releaseTimeoutMs = j2;
        this.setForegroundModeTimeoutMs = j2;
        this.pauseAtEndOfWindow = z3;
        this.clock = clock;
        this.backBufferDurationUs = loadControl.getBackBufferDurationUs();
        this.retainBackBufferFromKeyframe = loadControl.retainBackBufferFromKeyframe();
        PlaybackInfo playbackInfoCreateDummy = PlaybackInfo.createDummy(trackSelectorResult);
        this.playbackInfo = playbackInfoCreateDummy;
        this.playbackInfoUpdate = new PlaybackInfoUpdate(playbackInfoCreateDummy);
        this.rendererCapabilities = new RendererCapabilities[rendererArr.length];
        for (int i3 = 0; i3 < rendererArr.length; i3++) {
            rendererArr[i3].setIndex(i3);
            this.rendererCapabilities[i3] = rendererArr[i3].getCapabilities();
        }
        this.mediaClock = new DefaultMediaClock(this, clock);
        this.pendingMessages = new ArrayList<>();
        this.renderersToReset = Sets.newIdentityHashSet();
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        trackSelector.init(this, bandwidthMeter);
        this.deliverPendingMessageAtStartPositionRequired = true;
        Handler handler = new Handler(looper);
        this.queue = new MediaPeriodQueue(analyticsCollector, handler);
        this.mediaSourceList = new MediaSourceList(this, analyticsCollector, handler);
        HandlerThread handlerThread = new HandlerThread("ExoPlayer:Playback", -16);
        this.internalPlaybackThread = handlerThread;
        handlerThread.start();
        Looper looper2 = handlerThread.getLooper();
        this.playbackLooper = looper2;
        this.handler = clock.createHandler(looper2, this);
    }

    private void addMediaItemsInternal(MediaSourceListUpdateMessage mediaSourceListUpdateMessage, int i2) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        MediaSourceList mediaSourceList = this.mediaSourceList;
        if (i2 == -1) {
            i2 = mediaSourceList.getSize();
        }
        handleMediaSourceListInfoRefreshed(mediaSourceList.addMediaSources(i2, mediaSourceListUpdateMessage.mediaSourceHolders, mediaSourceListUpdateMessage.shuffleOrder), false);
    }

    private void attemptRendererErrorRecovery() throws ExoPlaybackException {
        seekToCurrentPosition(true);
    }

    private void deliverMessage(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.isCanceled()) {
            return;
        }
        try {
            playerMessage.getTarget().handleMessage(playerMessage.getType(), playerMessage.getPayload());
        } finally {
            playerMessage.markAsProcessed(true);
        }
    }

    private void disableRenderer(Renderer renderer) throws ExoPlaybackException {
        if (isRendererEnabled(renderer)) {
            this.mediaClock.onRendererDisabled(renderer);
            ensureStopped(renderer);
            renderer.disable();
            this.enabledRendererCount--;
        }
    }

    private void doSomeWork() throws ExoPlaybackException, IOException {
        boolean z2;
        boolean z3;
        int i2;
        boolean z4;
        long jUptimeMillis = this.clock.uptimeMillis();
        updatePeriods();
        int i3 = this.playbackInfo.playbackState;
        if (i3 == 1 || i3 == 4) {
            this.handler.removeMessages(2);
            return;
        }
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod == null) {
            scheduleNextWork(jUptimeMillis, 10L);
            return;
        }
        TraceUtil.beginSection("doSomeWork");
        updatePlaybackPositions();
        if (playingPeriod.prepared) {
            long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
            playingPeriod.mediaPeriod.discardBuffer(this.playbackInfo.positionUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
            z2 = true;
            z3 = true;
            int i4 = 0;
            while (true) {
                Renderer[] rendererArr = this.renderers;
                if (i4 >= rendererArr.length) {
                    break;
                }
                Renderer renderer = rendererArr[i4];
                if (isRendererEnabled(renderer)) {
                    renderer.render(this.rendererPositionUs, jElapsedRealtime);
                    z2 = z2 && renderer.isEnded();
                    boolean z5 = playingPeriod.sampleStreams[i4] != renderer.getStream();
                    boolean z6 = z5 || (!z5 && renderer.hasReadStreamToEnd()) || renderer.isReady() || renderer.isEnded();
                    z3 = z3 && z6;
                    if (!z6) {
                        renderer.maybeThrowStreamError();
                    }
                }
                i4++;
            }
        } else {
            playingPeriod.mediaPeriod.maybeThrowPrepareError();
            z2 = true;
            z3 = true;
        }
        long j2 = playingPeriod.info.durationUs;
        boolean z7 = z2 && playingPeriod.prepared && (j2 == C.TIME_UNSET || j2 <= this.playbackInfo.positionUs);
        if (z7 && this.pendingPauseAtEndOfPeriod) {
            this.pendingPauseAtEndOfPeriod = false;
            setPlayWhenReadyInternal(false, this.playbackInfo.playbackSuppressionReason, false, 5);
        }
        if (z7 && playingPeriod.info.isFinal) {
            setState(4);
            stopRenderers();
        } else if (this.playbackInfo.playbackState == 2 && shouldTransitionToReadyState(z3)) {
            setState(3);
            this.pendingRecoverableRendererError = null;
            if (shouldPlayWhenReady()) {
                startRenderers();
            }
        } else if (this.playbackInfo.playbackState == 3 && (this.enabledRendererCount != 0 ? !z3 : !isTimelineReady())) {
            this.isRebuffering = shouldPlayWhenReady();
            setState(2);
            if (this.isRebuffering) {
                notifyTrackSelectionRebuffer();
                this.livePlaybackSpeedControl.notifyRebuffer();
            }
            stopRenderers();
        }
        if (this.playbackInfo.playbackState == 2) {
            int i5 = 0;
            while (true) {
                Renderer[] rendererArr2 = this.renderers;
                if (i5 >= rendererArr2.length) {
                    break;
                }
                if (isRendererEnabled(rendererArr2[i5]) && this.renderers[i5].getStream() == playingPeriod.sampleStreams[i5]) {
                    this.renderers[i5].maybeThrowStreamError();
                }
                i5++;
            }
            PlaybackInfo playbackInfo = this.playbackInfo;
            if (!playbackInfo.isLoading && playbackInfo.totalBufferedDurationUs < 500000 && isLoadingPossible()) {
                throw new IllegalStateException("Playback stuck buffering and not loading");
            }
        }
        boolean z8 = this.offloadSchedulingEnabled;
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        if (z8 != playbackInfo2.offloadSchedulingEnabled) {
            this.playbackInfo = playbackInfo2.copyWithOffloadSchedulingEnabled(z8);
        }
        if ((shouldPlayWhenReady() && this.playbackInfo.playbackState == 3) || (i2 = this.playbackInfo.playbackState) == 2) {
            z4 = !maybeScheduleWakeup(jUptimeMillis, 10L);
        } else {
            if (this.enabledRendererCount == 0 || i2 == 4) {
                this.handler.removeMessages(2);
            } else {
                scheduleNextWork(jUptimeMillis, 1000L);
            }
            z4 = false;
        }
        PlaybackInfo playbackInfo3 = this.playbackInfo;
        if (playbackInfo3.sleepingForOffload != z4) {
            this.playbackInfo = playbackInfo3.copyWithSleepingForOffload(z4);
        }
        this.requestForRendererSleep = false;
        TraceUtil.endSection();
    }

    private void enableRenderer(int i2, boolean z2) throws ExoPlaybackException {
        Renderer renderer = this.renderers[i2];
        if (isRendererEnabled(renderer)) {
            return;
        }
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        boolean z3 = readingPeriod == this.queue.getPlayingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        RendererConfiguration rendererConfiguration = trackSelectorResult.rendererConfigurations[i2];
        Format[] formats = getFormats(trackSelectorResult.selections[i2]);
        boolean z4 = shouldPlayWhenReady() && this.playbackInfo.playbackState == 3;
        boolean z5 = !z2 && z4;
        this.enabledRendererCount++;
        this.renderersToReset.add(renderer);
        renderer.enable(rendererConfiguration, formats, readingPeriod.sampleStreams[i2], this.rendererPositionUs, z5, z3, readingPeriod.getStartPositionRendererTime(), readingPeriod.getRendererOffset());
        renderer.handleMessage(11, new Renderer.WakeupListener() { // from class: com.google.android.exoplayer2.ExoPlayerImplInternal.1
            @Override // com.google.android.exoplayer2.Renderer.WakeupListener
            public void onSleep(long j2) {
                if (j2 >= 2000) {
                    ExoPlayerImplInternal.this.requestForRendererSleep = true;
                }
            }

            @Override // com.google.android.exoplayer2.Renderer.WakeupListener
            public void onWakeup() {
                ExoPlayerImplInternal.this.handler.sendEmptyMessage(2);
            }
        });
        this.mediaClock.onRendererEnabled(renderer);
        if (z4) {
            renderer.start();
        }
    }

    private void enableRenderers() throws ExoPlaybackException {
        enableRenderers(new boolean[this.renderers.length]);
    }

    private void ensureStopped(Renderer renderer) throws ExoPlaybackException {
        if (renderer.getState() == 2) {
            renderer.stop();
        }
    }

    private ImmutableList<Metadata> extractMetadataFromTrackSelectionArray(ExoTrackSelection[] exoTrackSelectionArr) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        boolean z2 = false;
        for (ExoTrackSelection exoTrackSelection : exoTrackSelectionArr) {
            if (exoTrackSelection != null) {
                Metadata metadata = exoTrackSelection.getFormat(0).metadata;
                if (metadata == null) {
                    builder.add((ImmutableList.Builder) new Metadata(new Metadata.Entry[0]));
                } else {
                    builder.add((ImmutableList.Builder) metadata);
                    z2 = true;
                }
            }
        }
        return z2 ? builder.build() : ImmutableList.of();
    }

    private long getCurrentLiveOffsetUs() {
        PlaybackInfo playbackInfo = this.playbackInfo;
        return getLiveOffsetUs(playbackInfo.timeline, playbackInfo.periodId.periodUid, playbackInfo.positionUs);
    }

    private static Format[] getFormats(ExoTrackSelection exoTrackSelection) {
        int length = exoTrackSelection != null ? exoTrackSelection.length() : 0;
        Format[] formatArr = new Format[length];
        for (int i2 = 0; i2 < length; i2++) {
            formatArr[i2] = exoTrackSelection.getFormat(i2);
        }
        return formatArr;
    }

    private long getLiveOffsetUs(Timeline timeline, Object obj, long j2) {
        timeline.getWindow(timeline.getPeriodByUid(obj, this.period).windowIndex, this.window);
        Timeline.Window window = this.window;
        if (window.windowStartTimeMs != C.TIME_UNSET && window.isLive()) {
            Timeline.Window window2 = this.window;
            if (window2.isDynamic) {
                return Util.msToUs(window2.getCurrentUnixTimeMs() - this.window.windowStartTimeMs) - (j2 + this.period.getPositionInWindowUs());
            }
        }
        return C.TIME_UNSET;
    }

    private long getMaxRendererReadPositionUs() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null) {
            return 0L;
        }
        long rendererOffset = readingPeriod.getRendererOffset();
        if (!readingPeriod.prepared) {
            return rendererOffset;
        }
        int i2 = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i2 >= rendererArr.length) {
                return rendererOffset;
            }
            if (isRendererEnabled(rendererArr[i2]) && this.renderers[i2].getStream() == readingPeriod.sampleStreams[i2]) {
                long readingPositionUs = this.renderers[i2].getReadingPositionUs();
                if (readingPositionUs == Long.MIN_VALUE) {
                    return Long.MIN_VALUE;
                }
                rendererOffset = Math.max(readingPositionUs, rendererOffset);
            }
            i2++;
        }
    }

    private Pair<MediaSource.MediaPeriodId, Long> getPlaceholderFirstMediaPeriodPosition(Timeline timeline) {
        if (timeline.isEmpty()) {
            return Pair.create(PlaybackInfo.getDummyPeriodForEmptyTimeline(), 0L);
        }
        Pair<Object, Long> periodPosition = timeline.getPeriodPosition(this.window, this.period, timeline.getFirstWindowIndex(this.shuffleModeEnabled), C.TIME_UNSET);
        MediaSource.MediaPeriodId mediaPeriodIdResolveMediaPeriodIdForAds = this.queue.resolveMediaPeriodIdForAds(timeline, periodPosition.first, 0L);
        long jLongValue = ((Long) periodPosition.second).longValue();
        if (mediaPeriodIdResolveMediaPeriodIdForAds.isAd()) {
            timeline.getPeriodByUid(mediaPeriodIdResolveMediaPeriodIdForAds.periodUid, this.period);
            jLongValue = mediaPeriodIdResolveMediaPeriodIdForAds.adIndexInAdGroup == this.period.getFirstAdIndexToPlay(mediaPeriodIdResolveMediaPeriodIdForAds.adGroupIndex) ? this.period.getAdResumePositionUs() : 0L;
        }
        return Pair.create(mediaPeriodIdResolveMediaPeriodIdForAds, Long.valueOf(jLongValue));
    }

    private long getTotalBufferedDurationUs() {
        return getTotalBufferedDurationUs(this.playbackInfo.bufferedPositionUs);
    }

    private void handleContinueLoadingRequested(MediaPeriod mediaPeriod) {
        if (this.queue.isLoading(mediaPeriod)) {
            this.queue.reevaluateBuffer(this.rendererPositionUs);
            maybeContinueLoading();
        }
    }

    private void handleIoException(IOException iOException, int i2) {
        ExoPlaybackException exoPlaybackExceptionCreateForSource = ExoPlaybackException.createForSource(iOException, i2);
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod != null) {
            exoPlaybackExceptionCreateForSource = exoPlaybackExceptionCreateForSource.copyWithMediaPeriodId(playingPeriod.info.id);
        }
        Log.e(TAG, "Playback error", exoPlaybackExceptionCreateForSource);
        stopInternal(false, false);
        this.playbackInfo = this.playbackInfo.copyWithPlaybackError(exoPlaybackExceptionCreateForSource);
    }

    private void handleLoadingMediaPeriodChanged(boolean z2) {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        MediaSource.MediaPeriodId mediaPeriodId = loadingPeriod == null ? this.playbackInfo.periodId : loadingPeriod.info.id;
        boolean z3 = !this.playbackInfo.loadingMediaPeriodId.equals(mediaPeriodId);
        if (z3) {
            this.playbackInfo = this.playbackInfo.copyWithLoadingMediaPeriodId(mediaPeriodId);
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        playbackInfo.bufferedPositionUs = loadingPeriod == null ? playbackInfo.positionUs : loadingPeriod.getBufferedPositionUs();
        this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        if ((z3 || z2) && loadingPeriod != null && loadingPeriod.prepared) {
            updateLoadControlTrackSelection(loadingPeriod.getTrackGroups(), loadingPeriod.getTrackSelectorResult());
        }
    }

    private void handleMediaSourceListInfoRefreshed(Timeline timeline, boolean z2) throws Throwable {
        int i2;
        int i3;
        boolean z3;
        PositionUpdateForPlaylistChange positionUpdateForPlaylistChangeResolvePositionForPlaylistChange = resolvePositionForPlaylistChange(timeline, this.playbackInfo, this.pendingInitialSeekPosition, this.queue, this.repeatMode, this.shuffleModeEnabled, this.window, this.period);
        MediaSource.MediaPeriodId mediaPeriodId = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.periodId;
        long j2 = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.requestedContentPositionUs;
        boolean z4 = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.forceBufferingState;
        long jSeekToPeriodPosition = positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.periodPositionUs;
        boolean z5 = (this.playbackInfo.periodId.equals(mediaPeriodId) && jSeekToPeriodPosition == this.playbackInfo.positionUs) ? false : true;
        SeekPosition seekPosition = null;
        long j3 = C.TIME_UNSET;
        try {
            if (positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.endPlayback) {
                if (this.playbackInfo.playbackState != 1) {
                    setState(4);
                }
                resetInternal(false, false, false, true);
            }
            try {
                if (z5) {
                    i3 = 4;
                    z3 = false;
                    if (!timeline.isEmpty()) {
                        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
                            if (playingPeriod.info.id.equals(mediaPeriodId)) {
                                playingPeriod.info = this.queue.getUpdatedMediaPeriodInfo(timeline, playingPeriod.info);
                                playingPeriod.updateClipping();
                            }
                        }
                        jSeekToPeriodPosition = seekToPeriodPosition(mediaPeriodId, jSeekToPeriodPosition, z4);
                    }
                } else {
                    try {
                        i3 = 4;
                        z3 = false;
                        if (!this.queue.updateQueuedPeriods(timeline, this.rendererPositionUs, getMaxRendererReadPositionUs())) {
                            seekToCurrentPosition(false);
                        }
                    } catch (Throwable th) {
                        th = th;
                        i2 = 4;
                        PlaybackInfo playbackInfo = this.playbackInfo;
                        Timeline timeline2 = playbackInfo.timeline;
                        MediaSource.MediaPeriodId mediaPeriodId2 = playbackInfo.periodId;
                        if (positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.setTargetLiveOffset) {
                            j3 = jSeekToPeriodPosition;
                        }
                        SeekPosition seekPosition2 = seekPosition;
                        updateLivePlaybackSpeedControl(timeline, mediaPeriodId, timeline2, mediaPeriodId2, j3);
                        if (z5 || j2 != this.playbackInfo.requestedContentPositionUs) {
                            PlaybackInfo playbackInfo2 = this.playbackInfo;
                            Object obj = playbackInfo2.periodId.periodUid;
                            Timeline timeline3 = playbackInfo2.timeline;
                            this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, j2, this.playbackInfo.discontinuityStartPositionUs, z5 && z2 && !timeline3.isEmpty() && !timeline3.getPeriodByUid(obj, this.period).isPlaceholder, timeline.getIndexOfPeriod(obj) == -1 ? i2 : 3);
                        }
                        resetPendingPauseAtEndOfPeriod();
                        resolvePendingMessagePositions(timeline, this.playbackInfo.timeline);
                        this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline);
                        if (!timeline.isEmpty()) {
                            this.pendingInitialSeekPosition = seekPosition2;
                        }
                        handleLoadingMediaPeriodChanged(false);
                        throw th;
                    }
                }
                PlaybackInfo playbackInfo3 = this.playbackInfo;
                updateLivePlaybackSpeedControl(timeline, mediaPeriodId, playbackInfo3.timeline, playbackInfo3.periodId, positionUpdateForPlaylistChangeResolvePositionForPlaylistChange.setTargetLiveOffset ? jSeekToPeriodPosition : -9223372036854775807L);
                if (z5 || j2 != this.playbackInfo.requestedContentPositionUs) {
                    PlaybackInfo playbackInfo4 = this.playbackInfo;
                    Object obj2 = playbackInfo4.periodId.periodUid;
                    Timeline timeline4 = playbackInfo4.timeline;
                    this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, j2, this.playbackInfo.discontinuityStartPositionUs, (!z5 || !z2 || timeline4.isEmpty() || timeline4.getPeriodByUid(obj2, this.period).isPlaceholder) ? z3 : true, timeline.getIndexOfPeriod(obj2) == -1 ? i3 : 3);
                }
                resetPendingPauseAtEndOfPeriod();
                resolvePendingMessagePositions(timeline, this.playbackInfo.timeline);
                this.playbackInfo = this.playbackInfo.copyWithTimeline(timeline);
                if (!timeline.isEmpty()) {
                    this.pendingInitialSeekPosition = null;
                }
                handleLoadingMediaPeriodChanged(z3);
            } catch (Throwable th2) {
                th = th2;
                seekPosition = null;
            }
        } catch (Throwable th3) {
            th = th3;
            i2 = 4;
        }
    }

    private void handlePeriodPrepared(MediaPeriod mediaPeriod) throws ExoPlaybackException {
        if (this.queue.isLoading(mediaPeriod)) {
            MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
            loadingPeriod.handlePrepared(this.mediaClock.getPlaybackParameters().speed, this.playbackInfo.timeline);
            updateLoadControlTrackSelection(loadingPeriod.getTrackGroups(), loadingPeriod.getTrackSelectorResult());
            if (loadingPeriod == this.queue.getPlayingPeriod()) {
                resetRendererPosition(loadingPeriod.info.startPositionUs);
                enableRenderers();
                PlaybackInfo playbackInfo = this.playbackInfo;
                MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
                long j2 = loadingPeriod.info.startPositionUs;
                this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, j2, playbackInfo.requestedContentPositionUs, j2, false, 5);
            }
            maybeContinueLoading();
        }
    }

    private void handlePlaybackParameters(PlaybackParameters playbackParameters, boolean z2) throws ExoPlaybackException {
        handlePlaybackParameters(playbackParameters, playbackParameters.speed, true, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckResult
    private PlaybackInfo handlePositionDiscontinuity(MediaSource.MediaPeriodId mediaPeriodId, long j2, long j3, long j4, boolean z2, int i2) {
        List listOf;
        TrackGroupArray trackGroupArray;
        TrackSelectorResult trackSelectorResult;
        this.deliverPendingMessageAtStartPositionRequired = (!this.deliverPendingMessageAtStartPositionRequired && j2 == this.playbackInfo.positionUs && mediaPeriodId.equals(this.playbackInfo.periodId)) ? false : true;
        resetPendingPauseAtEndOfPeriod();
        PlaybackInfo playbackInfo = this.playbackInfo;
        TrackGroupArray trackGroupArray2 = playbackInfo.trackGroups;
        TrackSelectorResult trackSelectorResult2 = playbackInfo.trackSelectorResult;
        List list = playbackInfo.staticMetadata;
        if (this.mediaSourceList.isPrepared()) {
            MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
            TrackGroupArray trackGroups = playingPeriod == null ? TrackGroupArray.EMPTY : playingPeriod.getTrackGroups();
            TrackSelectorResult trackSelectorResult3 = playingPeriod == null ? this.emptyTrackSelectorResult : playingPeriod.getTrackSelectorResult();
            List listExtractMetadataFromTrackSelectionArray = extractMetadataFromTrackSelectionArray(trackSelectorResult3.selections);
            if (playingPeriod != null) {
                MediaPeriodInfo mediaPeriodInfo = playingPeriod.info;
                if (mediaPeriodInfo.requestedContentPositionUs != j3) {
                    playingPeriod.info = mediaPeriodInfo.copyWithRequestedContentPositionUs(j3);
                }
            }
            trackGroupArray = trackGroups;
            trackSelectorResult = trackSelectorResult3;
            listOf = listExtractMetadataFromTrackSelectionArray;
        } else if (mediaPeriodId.equals(this.playbackInfo.periodId)) {
            listOf = list;
            trackGroupArray = trackGroupArray2;
            trackSelectorResult = trackSelectorResult2;
        } else {
            trackGroupArray = TrackGroupArray.EMPTY;
            trackSelectorResult = this.emptyTrackSelectorResult;
            listOf = ImmutableList.of();
        }
        if (z2) {
            this.playbackInfoUpdate.setPositionDiscontinuity(i2);
        }
        return this.playbackInfo.copyWithNewPosition(mediaPeriodId, j2, j3, j4, getTotalBufferedDurationUs(), trackGroupArray, trackSelectorResult, listOf);
    }

    private boolean hasReachedServerSideInsertedAdsTransition(Renderer renderer, MediaPeriodHolder mediaPeriodHolder) {
        MediaPeriodHolder next = mediaPeriodHolder.getNext();
        return mediaPeriodHolder.info.isFollowedByTransitionToSameStream && next.prepared && ((renderer instanceof TextRenderer) || renderer.getReadingPositionUs() >= next.getStartPositionRendererTime());
    }

    private boolean hasReadingPeriodFinishedReading() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (!readingPeriod.prepared) {
            return false;
        }
        int i2 = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i2 >= rendererArr.length) {
                return true;
            }
            Renderer renderer = rendererArr[i2];
            SampleStream sampleStream = readingPeriod.sampleStreams[i2];
            if (renderer.getStream() != sampleStream || (sampleStream != null && !renderer.hasReadStreamToEnd() && !hasReachedServerSideInsertedAdsTransition(renderer, readingPeriod))) {
                break;
            }
            i2++;
        }
        return false;
    }

    private boolean isLoadingPossible() {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        return (loadingPeriod == null || loadingPeriod.getNextLoadPositionUs() == Long.MIN_VALUE) ? false : true;
    }

    private static boolean isRendererEnabled(Renderer renderer) {
        return renderer.getState() != 0;
    }

    private boolean isTimelineReady() {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        long j2 = playingPeriod.info.durationUs;
        return playingPeriod.prepared && (j2 == C.TIME_UNSET || this.playbackInfo.positionUs < j2 || !shouldPlayWhenReady());
    }

    private static boolean isUsingPlaceholderPeriod(PlaybackInfo playbackInfo, Timeline.Period period) {
        MediaSource.MediaPeriodId mediaPeriodId = playbackInfo.periodId;
        Timeline timeline = playbackInfo.timeline;
        return timeline.isEmpty() || timeline.getPeriodByUid(mediaPeriodId.periodUid, period).isPlaceholder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$release$0() {
        return Boolean.valueOf(this.released);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendMessageToTargetThread$1(PlayerMessage playerMessage) {
        try {
            deliverMessage(playerMessage);
        } catch (ExoPlaybackException e2) {
            Log.e(TAG, "Unexpected error delivering message on external thread.", e2);
            throw new RuntimeException(e2);
        }
    }

    private void maybeContinueLoading() {
        boolean zShouldContinueLoading = shouldContinueLoading();
        this.shouldContinueLoading = zShouldContinueLoading;
        if (zShouldContinueLoading) {
            this.queue.getLoadingPeriod().continueLoading(this.rendererPositionUs);
        }
        updateIsLoading();
    }

    private void maybeNotifyPlaybackInfoChanged() {
        this.playbackInfoUpdate.setPlaybackInfo(this.playbackInfo);
        if (this.playbackInfoUpdate.hasPendingChange) {
            this.playbackInfoUpdateListener.onPlaybackInfoUpdate(this.playbackInfoUpdate);
            this.playbackInfoUpdate = new PlaybackInfoUpdate(this.playbackInfo);
        }
    }

    private boolean maybeScheduleWakeup(long j2, long j3) {
        if (this.offloadSchedulingEnabled && this.requestForRendererSleep) {
            return false;
        }
        scheduleNextWork(j2, j3);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x0045, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0074, code lost:
    
        r3 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void maybeTriggerPendingMessages(long r8, long r10) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.maybeTriggerPendingMessages(long, long):void");
    }

    private void maybeUpdateLoadingPeriod() throws ExoPlaybackException {
        MediaPeriodInfo nextMediaPeriodInfo;
        this.queue.reevaluateBuffer(this.rendererPositionUs);
        if (this.queue.shouldLoadNextMediaPeriod() && (nextMediaPeriodInfo = this.queue.getNextMediaPeriodInfo(this.rendererPositionUs, this.playbackInfo)) != null) {
            MediaPeriodHolder mediaPeriodHolderEnqueueNextMediaPeriodHolder = this.queue.enqueueNextMediaPeriodHolder(this.rendererCapabilities, this.trackSelector, this.loadControl.getAllocator(), this.mediaSourceList, nextMediaPeriodInfo, this.emptyTrackSelectorResult);
            mediaPeriodHolderEnqueueNextMediaPeriodHolder.mediaPeriod.prepare(this, nextMediaPeriodInfo.startPositionUs);
            if (this.queue.getPlayingPeriod() == mediaPeriodHolderEnqueueNextMediaPeriodHolder) {
                resetRendererPosition(nextMediaPeriodInfo.startPositionUs);
            }
            handleLoadingMediaPeriodChanged(false);
        }
        if (!this.shouldContinueLoading) {
            maybeContinueLoading();
        } else {
            this.shouldContinueLoading = isLoadingPossible();
            updateIsLoading();
        }
    }

    private void maybeUpdatePlayingPeriod() throws ExoPlaybackException {
        boolean z2 = false;
        while (shouldAdvancePlayingPeriod()) {
            if (z2) {
                maybeNotifyPlaybackInfoChanged();
            }
            MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
            MediaPeriodHolder mediaPeriodHolderAdvancePlayingPeriod = this.queue.advancePlayingPeriod();
            MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolderAdvancePlayingPeriod.info;
            MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
            long j2 = mediaPeriodInfo.startPositionUs;
            PlaybackInfo playbackInfoHandlePositionDiscontinuity = handlePositionDiscontinuity(mediaPeriodId, j2, mediaPeriodInfo.requestedContentPositionUs, j2, true, 0);
            this.playbackInfo = playbackInfoHandlePositionDiscontinuity;
            Timeline timeline = playbackInfoHandlePositionDiscontinuity.timeline;
            updateLivePlaybackSpeedControl(timeline, mediaPeriodHolderAdvancePlayingPeriod.info.id, timeline, playingPeriod.info.id, C.TIME_UNSET);
            resetPendingPauseAtEndOfPeriod();
            updatePlaybackPositions();
            z2 = true;
        }
    }

    private void maybeUpdateReadingPeriod() {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null) {
            return;
        }
        int i2 = 0;
        if (readingPeriod.getNext() != null && !this.pendingPauseAtEndOfPeriod) {
            if (hasReadingPeriodFinishedReading()) {
                if (readingPeriod.getNext().prepared || this.rendererPositionUs >= readingPeriod.getNext().getStartPositionRendererTime()) {
                    TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
                    MediaPeriodHolder mediaPeriodHolderAdvanceReadingPeriod = this.queue.advanceReadingPeriod();
                    TrackSelectorResult trackSelectorResult2 = mediaPeriodHolderAdvanceReadingPeriod.getTrackSelectorResult();
                    if (mediaPeriodHolderAdvanceReadingPeriod.prepared && mediaPeriodHolderAdvanceReadingPeriod.mediaPeriod.readDiscontinuity() != C.TIME_UNSET) {
                        setAllRendererStreamsFinal(mediaPeriodHolderAdvanceReadingPeriod.getStartPositionRendererTime());
                        return;
                    }
                    for (int i3 = 0; i3 < this.renderers.length; i3++) {
                        boolean zIsRendererEnabled = trackSelectorResult.isRendererEnabled(i3);
                        boolean zIsRendererEnabled2 = trackSelectorResult2.isRendererEnabled(i3);
                        if (zIsRendererEnabled && !this.renderers[i3].isCurrentStreamFinal()) {
                            boolean z2 = this.rendererCapabilities[i3].getTrackType() == -2;
                            RendererConfiguration rendererConfiguration = trackSelectorResult.rendererConfigurations[i3];
                            RendererConfiguration rendererConfiguration2 = trackSelectorResult2.rendererConfigurations[i3];
                            if (!zIsRendererEnabled2 || !rendererConfiguration2.equals(rendererConfiguration) || z2) {
                                setCurrentStreamFinal(this.renderers[i3], mediaPeriodHolderAdvanceReadingPeriod.getStartPositionRendererTime());
                            }
                        }
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (!readingPeriod.info.isFinal && !this.pendingPauseAtEndOfPeriod) {
            return;
        }
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i2 >= rendererArr.length) {
                return;
            }
            Renderer renderer = rendererArr[i2];
            SampleStream sampleStream = readingPeriod.sampleStreams[i2];
            if (sampleStream != null && renderer.getStream() == sampleStream && renderer.hasReadStreamToEnd()) {
                long j2 = readingPeriod.info.durationUs;
                setCurrentStreamFinal(renderer, (j2 == C.TIME_UNSET || j2 == Long.MIN_VALUE) ? -9223372036854775807L : readingPeriod.getRendererOffset() + readingPeriod.info.durationUs);
            }
            i2++;
        }
    }

    private void maybeUpdateReadingRenderers() throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        if (readingPeriod == null || this.queue.getPlayingPeriod() == readingPeriod || readingPeriod.allRenderersInCorrectState || !replaceStreamsOrDisableRendererForTransition()) {
            return;
        }
        enableRenderers();
    }

    private void mediaSourceListUpdateRequestedInternal() throws Throwable {
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.createTimeline(), true);
    }

    private void moveMediaItemsInternal(MoveMediaItemsMessage moveMediaItemsMessage) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.moveMediaSourceRange(moveMediaItemsMessage.fromIndex, moveMediaItemsMessage.toIndex, moveMediaItemsMessage.newFromIndex, moveMediaItemsMessage.shuffleOrder), false);
    }

    private void notifyTrackSelectionDiscontinuity() {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onDiscontinuity();
                }
            }
        }
    }

    private void notifyTrackSelectionPlayWhenReadyChanged(boolean z2) {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlayWhenReadyChanged(z2);
                }
            }
        }
    }

    private void notifyTrackSelectionRebuffer() {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onRebuffer();
                }
            }
        }
    }

    private void prepareInternal() {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        resetInternal(false, false, false, true);
        this.loadControl.onPrepared();
        setState(this.playbackInfo.timeline.isEmpty() ? 4 : 2);
        this.mediaSourceList.prepare(this.bandwidthMeter.getTransferListener());
        this.handler.sendEmptyMessage(2);
    }

    private void releaseInternal() {
        resetInternal(true, false, true, false);
        this.loadControl.onReleased();
        setState(1);
        this.internalPlaybackThread.quit();
        synchronized (this) {
            this.released = true;
            notifyAll();
        }
    }

    private void removeMediaItemsInternal(int i2, int i3, ShuffleOrder shuffleOrder) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.removeMediaSourceRange(i2, i3, shuffleOrder), false);
    }

    private boolean replaceStreamsOrDisableRendererForTransition() throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i2 >= rendererArr.length) {
                return !z2;
            }
            Renderer renderer = rendererArr[i2];
            if (isRendererEnabled(renderer)) {
                boolean z3 = renderer.getStream() != readingPeriod.sampleStreams[i2];
                if (!trackSelectorResult.isRendererEnabled(i2) || z3) {
                    if (!renderer.isCurrentStreamFinal()) {
                        renderer.replaceStream(getFormats(trackSelectorResult.selections[i2]), readingPeriod.sampleStreams[i2], readingPeriod.getStartPositionRendererTime(), readingPeriod.getRendererOffset());
                    } else if (renderer.isEnded()) {
                        disableRenderer(renderer);
                    } else {
                        z2 = true;
                    }
                }
            }
            i2++;
        }
    }

    private void reselectTracksInternal() throws ExoPlaybackException {
        float f2 = this.mediaClock.getPlaybackParameters().speed;
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        boolean z2 = true;
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null && playingPeriod.prepared; playingPeriod = playingPeriod.getNext()) {
            TrackSelectorResult trackSelectorResultSelectTracks = playingPeriod.selectTracks(f2, this.playbackInfo.timeline);
            if (!trackSelectorResultSelectTracks.isEquivalent(playingPeriod.getTrackSelectorResult())) {
                if (z2) {
                    MediaPeriodHolder playingPeriod2 = this.queue.getPlayingPeriod();
                    boolean zRemoveAfter = this.queue.removeAfter(playingPeriod2);
                    boolean[] zArr = new boolean[this.renderers.length];
                    long jApplyTrackSelection = playingPeriod2.applyTrackSelection(trackSelectorResultSelectTracks, this.playbackInfo.positionUs, zRemoveAfter, zArr);
                    PlaybackInfo playbackInfo = this.playbackInfo;
                    boolean z3 = (playbackInfo.playbackState == 4 || jApplyTrackSelection == playbackInfo.positionUs) ? false : true;
                    PlaybackInfo playbackInfo2 = this.playbackInfo;
                    this.playbackInfo = handlePositionDiscontinuity(playbackInfo2.periodId, jApplyTrackSelection, playbackInfo2.requestedContentPositionUs, playbackInfo2.discontinuityStartPositionUs, z3, 5);
                    if (z3) {
                        resetRendererPosition(jApplyTrackSelection);
                    }
                    boolean[] zArr2 = new boolean[this.renderers.length];
                    int i2 = 0;
                    while (true) {
                        Renderer[] rendererArr = this.renderers;
                        if (i2 >= rendererArr.length) {
                            break;
                        }
                        Renderer renderer = rendererArr[i2];
                        boolean zIsRendererEnabled = isRendererEnabled(renderer);
                        zArr2[i2] = zIsRendererEnabled;
                        SampleStream sampleStream = playingPeriod2.sampleStreams[i2];
                        if (zIsRendererEnabled) {
                            if (sampleStream != renderer.getStream()) {
                                disableRenderer(renderer);
                            } else if (zArr[i2]) {
                                renderer.resetPosition(this.rendererPositionUs);
                            }
                        }
                        i2++;
                    }
                    enableRenderers(zArr2);
                } else {
                    this.queue.removeAfter(playingPeriod);
                    if (playingPeriod.prepared) {
                        playingPeriod.applyTrackSelection(trackSelectorResultSelectTracks, Math.max(playingPeriod.info.startPositionUs, playingPeriod.toPeriodTime(this.rendererPositionUs)), false);
                    }
                }
                handleLoadingMediaPeriodChanged(true);
                if (this.playbackInfo.playbackState != 4) {
                    maybeContinueLoading();
                    updatePlaybackPositions();
                    this.handler.sendEmptyMessage(2);
                    return;
                }
                return;
            }
            if (playingPeriod == readingPeriod) {
                z2 = false;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00a2 A[PHI: r4 r5 r7
      0x00a2: PHI (r4v3 com.google.android.exoplayer2.source.MediaSource$MediaPeriodId) = 
      (r4v2 com.google.android.exoplayer2.source.MediaSource$MediaPeriodId)
      (r4v9 com.google.android.exoplayer2.source.MediaSource$MediaPeriodId)
     binds: [B:30:0x0079, B:32:0x009e] A[DONT_GENERATE, DONT_INLINE]
      0x00a2: PHI (r5v2 long) = (r5v1 long), (r5v5 long) binds: [B:30:0x0079, B:32:0x009e] A[DONT_GENERATE, DONT_INLINE]
      0x00a2: PHI (r7v3 long) = (r7v2 long), (r7v5 long) binds: [B:30:0x0079, B:32:0x009e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void resetInternal(boolean r30, boolean r31, boolean r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.resetInternal(boolean, boolean, boolean, boolean):void");
    }

    private void resetPendingPauseAtEndOfPeriod() {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        this.pendingPauseAtEndOfPeriod = playingPeriod != null && playingPeriod.info.isLastInTimelineWindow && this.pauseAtEndOfWindow;
    }

    private void resetRendererPosition(long j2) throws ExoPlaybackException {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        long rendererTime = playingPeriod == null ? j2 + MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US : playingPeriod.toRendererTime(j2);
        this.rendererPositionUs = rendererTime;
        this.mediaClock.resetPosition(rendererTime);
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                renderer.resetPosition(this.rendererPositionUs);
            }
        }
        notifyTrackSelectionDiscontinuity();
    }

    private static void resolvePendingMessageEndOfStreamPosition(Timeline timeline, PendingMessageInfo pendingMessageInfo, Timeline.Window window, Timeline.Period period) {
        int i2 = timeline.getWindow(timeline.getPeriodByUid(pendingMessageInfo.resolvedPeriodUid, period).windowIndex, window).lastPeriodIndex;
        Object obj = timeline.getPeriod(i2, period, true).uid;
        long j2 = period.durationUs;
        pendingMessageInfo.setResolvedPosition(i2, j2 != C.TIME_UNSET ? j2 - 1 : Long.MAX_VALUE, obj);
    }

    private static boolean resolvePendingMessagePosition(PendingMessageInfo pendingMessageInfo, Timeline timeline, Timeline timeline2, int i2, boolean z2, Timeline.Window window, Timeline.Period period) {
        Object obj = pendingMessageInfo.resolvedPeriodUid;
        if (obj == null) {
            Pair<Object, Long> pairResolveSeekPosition = resolveSeekPosition(timeline, new SeekPosition(pendingMessageInfo.message.getTimeline(), pendingMessageInfo.message.getMediaItemIndex(), pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE ? C.TIME_UNSET : Util.msToUs(pendingMessageInfo.message.getPositionMs())), false, i2, z2, window, period);
            if (pairResolveSeekPosition == null) {
                return false;
            }
            pendingMessageInfo.setResolvedPosition(timeline.getIndexOfPeriod(pairResolveSeekPosition.first), ((Long) pairResolveSeekPosition.second).longValue(), pairResolveSeekPosition.first);
            if (pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE) {
                resolvePendingMessageEndOfStreamPosition(timeline, pendingMessageInfo, window, period);
            }
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(obj);
        if (indexOfPeriod == -1) {
            return false;
        }
        if (pendingMessageInfo.message.getPositionMs() == Long.MIN_VALUE) {
            resolvePendingMessageEndOfStreamPosition(timeline, pendingMessageInfo, window, period);
            return true;
        }
        pendingMessageInfo.resolvedPeriodIndex = indexOfPeriod;
        timeline2.getPeriodByUid(pendingMessageInfo.resolvedPeriodUid, period);
        if (period.isPlaceholder && timeline2.getWindow(period.windowIndex, window).firstPeriodIndex == timeline2.getIndexOfPeriod(pendingMessageInfo.resolvedPeriodUid)) {
            Pair<Object, Long> periodPosition = timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(pendingMessageInfo.resolvedPeriodUid, period).windowIndex, pendingMessageInfo.resolvedPeriodTimeUs + period.getPositionInWindowUs());
            pendingMessageInfo.setResolvedPosition(timeline.getIndexOfPeriod(periodPosition.first), ((Long) periodPosition.second).longValue(), periodPosition.first);
        }
        return true;
    }

    private void resolvePendingMessagePositions(Timeline timeline, Timeline timeline2) {
        if (timeline.isEmpty() && timeline2.isEmpty()) {
            return;
        }
        for (int size = this.pendingMessages.size() - 1; size >= 0; size--) {
            if (!resolvePendingMessagePosition(this.pendingMessages.get(size), timeline, timeline2, this.repeatMode, this.shuffleModeEnabled, this.window, this.period)) {
                this.pendingMessages.get(size).message.markAsProcessed(false);
                this.pendingMessages.remove(size);
            }
        }
        Collections.sort(this.pendingMessages);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.ExoPlayerImplInternal.PositionUpdateForPlaylistChange resolvePositionForPlaylistChange(com.google.android.exoplayer2.Timeline r30, com.google.android.exoplayer2.PlaybackInfo r31, @androidx.annotation.Nullable com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r32, com.google.android.exoplayer2.MediaPeriodQueue r33, int r34, boolean r35, com.google.android.exoplayer2.Timeline.Window r36, com.google.android.exoplayer2.Timeline.Period r37) {
        /*
            Method dump skipped, instructions count: 521
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.resolvePositionForPlaylistChange(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.PlaybackInfo, com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition, com.google.android.exoplayer2.MediaPeriodQueue, int, boolean, com.google.android.exoplayer2.Timeline$Window, com.google.android.exoplayer2.Timeline$Period):com.google.android.exoplayer2.ExoPlayerImplInternal$PositionUpdateForPlaylistChange");
    }

    @Nullable
    private static Pair<Object, Long> resolveSeekPosition(Timeline timeline, SeekPosition seekPosition, boolean z2, int i2, boolean z3, Timeline.Window window, Timeline.Period period) {
        Pair<Object, Long> periodPosition;
        Object objResolveSubsequentPeriod;
        Timeline timeline2 = seekPosition.timeline;
        if (timeline.isEmpty()) {
            return null;
        }
        Timeline timeline3 = timeline2.isEmpty() ? timeline : timeline2;
        try {
            periodPosition = timeline3.getPeriodPosition(window, period, seekPosition.windowIndex, seekPosition.windowPositionUs);
        } catch (IndexOutOfBoundsException unused) {
        }
        if (timeline.equals(timeline3)) {
            return periodPosition;
        }
        if (timeline.getIndexOfPeriod(periodPosition.first) != -1) {
            return (timeline3.getPeriodByUid(periodPosition.first, period).isPlaceholder && timeline3.getWindow(period.windowIndex, window).firstPeriodIndex == timeline3.getIndexOfPeriod(periodPosition.first)) ? timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(periodPosition.first, period).windowIndex, seekPosition.windowPositionUs) : periodPosition;
        }
        if (z2 && (objResolveSubsequentPeriod = resolveSubsequentPeriod(window, period, i2, z3, periodPosition.first, timeline3, timeline)) != null) {
            return timeline.getPeriodPosition(window, period, timeline.getPeriodByUid(objResolveSubsequentPeriod, period).windowIndex, C.TIME_UNSET);
        }
        return null;
    }

    @Nullable
    public static Object resolveSubsequentPeriod(Timeline.Window window, Timeline.Period period, int i2, boolean z2, Object obj, Timeline timeline, Timeline timeline2) {
        int indexOfPeriod = timeline.getIndexOfPeriod(obj);
        int periodCount = timeline.getPeriodCount();
        int nextPeriodIndex = indexOfPeriod;
        int indexOfPeriod2 = -1;
        for (int i3 = 0; i3 < periodCount && indexOfPeriod2 == -1; i3++) {
            nextPeriodIndex = timeline.getNextPeriodIndex(nextPeriodIndex, period, window, i2, z2);
            if (nextPeriodIndex == -1) {
                break;
            }
            indexOfPeriod2 = timeline2.getIndexOfPeriod(timeline.getUidOfPeriod(nextPeriodIndex));
        }
        if (indexOfPeriod2 == -1) {
            return null;
        }
        return timeline2.getUidOfPeriod(indexOfPeriod2);
    }

    private void scheduleNextWork(long j2, long j3) {
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessageAtTime(2, j2 + j3);
    }

    private void seekToCurrentPosition(boolean z2) throws ExoPlaybackException {
        MediaSource.MediaPeriodId mediaPeriodId = this.queue.getPlayingPeriod().info.id;
        long jSeekToPeriodPosition = seekToPeriodPosition(mediaPeriodId, this.playbackInfo.positionUs, true, false);
        if (jSeekToPeriodPosition != this.playbackInfo.positionUs) {
            PlaybackInfo playbackInfo = this.playbackInfo;
            this.playbackInfo = handlePositionDiscontinuity(mediaPeriodId, jSeekToPeriodPosition, playbackInfo.requestedContentPositionUs, playbackInfo.discontinuityStartPositionUs, z2, 5);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ac A[Catch: all -> 0x0148, TryCatch #1 {all -> 0x0148, blocks: (B:22:0x00a2, B:24:0x00ac, B:27:0x00b2, B:29:0x00b8, B:30:0x00bb, B:32:0x00c1, B:34:0x00cb, B:36:0x00d3, B:40:0x00db, B:42:0x00e5, B:44:0x00f5, B:48:0x00ff, B:52:0x0111, B:56:0x011a), top: B:74:0x00a2 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal.SeekPosition r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoPlayerImplInternal.seekToInternal(com.google.android.exoplayer2.ExoPlayerImplInternal$SeekPosition):void");
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId mediaPeriodId, long j2, boolean z2) throws ExoPlaybackException {
        return seekToPeriodPosition(mediaPeriodId, j2, this.queue.getPlayingPeriod() != this.queue.getReadingPeriod(), z2);
    }

    private void sendMessageInternal(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.getPositionMs() == C.TIME_UNSET) {
            sendMessageToTarget(playerMessage);
            return;
        }
        if (this.playbackInfo.timeline.isEmpty()) {
            this.pendingMessages.add(new PendingMessageInfo(playerMessage));
            return;
        }
        PendingMessageInfo pendingMessageInfo = new PendingMessageInfo(playerMessage);
        Timeline timeline = this.playbackInfo.timeline;
        if (!resolvePendingMessagePosition(pendingMessageInfo, timeline, timeline, this.repeatMode, this.shuffleModeEnabled, this.window, this.period)) {
            playerMessage.markAsProcessed(false);
        } else {
            this.pendingMessages.add(pendingMessageInfo);
            Collections.sort(this.pendingMessages);
        }
    }

    private void sendMessageToTarget(PlayerMessage playerMessage) throws ExoPlaybackException {
        if (playerMessage.getLooper() != this.playbackLooper) {
            this.handler.obtainMessage(15, playerMessage).sendToTarget();
            return;
        }
        deliverMessage(playerMessage);
        int i2 = this.playbackInfo.playbackState;
        if (i2 == 3 || i2 == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void sendMessageToTargetThread(final PlayerMessage playerMessage) {
        Looper looper = playerMessage.getLooper();
        if (looper.getThread().isAlive()) {
            this.clock.createHandler(looper, null).post(new Runnable() { // from class: com.google.android.exoplayer2.i1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6791c.lambda$sendMessageToTargetThread$1(playerMessage);
                }
            });
        } else {
            Log.w("TAG", "Trying to send message on a dead thread.");
            playerMessage.markAsProcessed(false);
        }
    }

    private void setAllRendererStreamsFinal(long j2) {
        for (Renderer renderer : this.renderers) {
            if (renderer.getStream() != null) {
                setCurrentStreamFinal(renderer, j2);
            }
        }
    }

    private void setCurrentStreamFinal(Renderer renderer, long j2) {
        renderer.setCurrentStreamFinal();
        if (renderer instanceof TextRenderer) {
            ((TextRenderer) renderer).setFinalStreamEndPositionUs(j2);
        }
    }

    private void setForegroundModeInternal(boolean z2, @Nullable AtomicBoolean atomicBoolean) {
        if (this.foregroundMode != z2) {
            this.foregroundMode = z2;
            if (!z2) {
                for (Renderer renderer : this.renderers) {
                    if (!isRendererEnabled(renderer) && this.renderersToReset.remove(renderer)) {
                        renderer.reset();
                    }
                }
            }
        }
        if (atomicBoolean != null) {
            synchronized (this) {
                atomicBoolean.set(true);
                notifyAll();
            }
        }
    }

    private void setMediaItemsInternal(MediaSourceListUpdateMessage mediaSourceListUpdateMessage) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        if (mediaSourceListUpdateMessage.windowIndex != -1) {
            this.pendingInitialSeekPosition = new SeekPosition(new PlaylistTimeline(mediaSourceListUpdateMessage.mediaSourceHolders, mediaSourceListUpdateMessage.shuffleOrder), mediaSourceListUpdateMessage.windowIndex, mediaSourceListUpdateMessage.positionUs);
        }
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.setMediaSources(mediaSourceListUpdateMessage.mediaSourceHolders, mediaSourceListUpdateMessage.shuffleOrder), false);
    }

    private void setOffloadSchedulingEnabledInternal(boolean z2) {
        if (z2 == this.offloadSchedulingEnabled) {
            return;
        }
        this.offloadSchedulingEnabled = z2;
        PlaybackInfo playbackInfo = this.playbackInfo;
        int i2 = playbackInfo.playbackState;
        if (z2 || i2 == 4 || i2 == 1) {
            this.playbackInfo = playbackInfo.copyWithOffloadSchedulingEnabled(z2);
        } else {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setPauseAtEndOfWindowInternal(boolean z2) throws ExoPlaybackException {
        this.pauseAtEndOfWindow = z2;
        resetPendingPauseAtEndOfPeriod();
        if (!this.pendingPauseAtEndOfPeriod || this.queue.getReadingPeriod() == this.queue.getPlayingPeriod()) {
            return;
        }
        seekToCurrentPosition(true);
        handleLoadingMediaPeriodChanged(false);
    }

    private void setPlayWhenReadyInternal(boolean z2, int i2, boolean z3, int i3) throws ExoPlaybackException {
        this.playbackInfoUpdate.incrementPendingOperationAcks(z3 ? 1 : 0);
        this.playbackInfoUpdate.setPlayWhenReadyChangeReason(i3);
        this.playbackInfo = this.playbackInfo.copyWithPlayWhenReady(z2, i2);
        this.isRebuffering = false;
        notifyTrackSelectionPlayWhenReadyChanged(z2);
        if (!shouldPlayWhenReady()) {
            stopRenderers();
            updatePlaybackPositions();
            return;
        }
        int i4 = this.playbackInfo.playbackState;
        if (i4 == 3) {
            startRenderers();
            this.handler.sendEmptyMessage(2);
        } else if (i4 == 2) {
            this.handler.sendEmptyMessage(2);
        }
    }

    private void setPlaybackParametersInternal(PlaybackParameters playbackParameters) throws ExoPlaybackException {
        this.mediaClock.setPlaybackParameters(playbackParameters);
        handlePlaybackParameters(this.mediaClock.getPlaybackParameters(), true);
    }

    private void setRepeatModeInternal(int i2) throws ExoPlaybackException {
        this.repeatMode = i2;
        if (!this.queue.updateRepeatMode(this.playbackInfo.timeline, i2)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void setSeekParametersInternal(SeekParameters seekParameters) {
        this.seekParameters = seekParameters;
    }

    private void setShuffleModeEnabledInternal(boolean z2) throws ExoPlaybackException {
        this.shuffleModeEnabled = z2;
        if (!this.queue.updateShuffleModeEnabled(this.playbackInfo.timeline, z2)) {
            seekToCurrentPosition(true);
        }
        handleLoadingMediaPeriodChanged(false);
    }

    private void setShuffleOrderInternal(ShuffleOrder shuffleOrder) throws Throwable {
        this.playbackInfoUpdate.incrementPendingOperationAcks(1);
        handleMediaSourceListInfoRefreshed(this.mediaSourceList.setShuffleOrder(shuffleOrder), false);
    }

    private void setState(int i2) {
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (playbackInfo.playbackState != i2) {
            this.playbackInfo = playbackInfo.copyWithPlaybackState(i2);
        }
    }

    private boolean shouldAdvancePlayingPeriod() {
        MediaPeriodHolder playingPeriod;
        MediaPeriodHolder next;
        return shouldPlayWhenReady() && !this.pendingPauseAtEndOfPeriod && (playingPeriod = this.queue.getPlayingPeriod()) != null && (next = playingPeriod.getNext()) != null && this.rendererPositionUs >= next.getStartPositionRendererTime() && next.allRenderersInCorrectState;
    }

    private boolean shouldContinueLoading() {
        if (!isLoadingPossible()) {
            return false;
        }
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        return this.loadControl.shouldContinueLoading(loadingPeriod == this.queue.getPlayingPeriod() ? loadingPeriod.toPeriodTime(this.rendererPositionUs) : loadingPeriod.toPeriodTime(this.rendererPositionUs) - loadingPeriod.info.startPositionUs, getTotalBufferedDurationUs(loadingPeriod.getNextLoadPositionUs()), this.mediaClock.getPlaybackParameters().speed);
    }

    private boolean shouldPlayWhenReady() {
        PlaybackInfo playbackInfo = this.playbackInfo;
        return playbackInfo.playWhenReady && playbackInfo.playbackSuppressionReason == 0;
    }

    private boolean shouldTransitionToReadyState(boolean z2) {
        if (this.enabledRendererCount == 0) {
            return isTimelineReady();
        }
        if (!z2) {
            return false;
        }
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (!playbackInfo.isLoading) {
            return true;
        }
        long targetLiveOffsetUs = shouldUseLivePlaybackSpeedControl(playbackInfo.timeline, this.queue.getPlayingPeriod().info.id) ? this.livePlaybackSpeedControl.getTargetLiveOffsetUs() : C.TIME_UNSET;
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        return (loadingPeriod.isFullyBuffered() && loadingPeriod.info.isFinal) || (loadingPeriod.info.id.isAd() && !loadingPeriod.prepared) || this.loadControl.shouldStartPlayback(getTotalBufferedDurationUs(), this.mediaClock.getPlaybackParameters().speed, this.isRebuffering, targetLiveOffsetUs);
    }

    private boolean shouldUseLivePlaybackSpeedControl(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (mediaPeriodId.isAd() || timeline.isEmpty()) {
            return false;
        }
        timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, this.window);
        if (!this.window.isLive()) {
            return false;
        }
        Timeline.Window window = this.window;
        return window.isDynamic && window.windowStartTimeMs != C.TIME_UNSET;
    }

    private void startRenderers() throws ExoPlaybackException {
        this.isRebuffering = false;
        this.mediaClock.start();
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                renderer.start();
            }
        }
    }

    private void stopInternal(boolean z2, boolean z3) {
        resetInternal(z2 || !this.foregroundMode, false, true, false);
        this.playbackInfoUpdate.incrementPendingOperationAcks(z3 ? 1 : 0);
        this.loadControl.onStopped();
        setState(1);
    }

    private void stopRenderers() throws ExoPlaybackException {
        this.mediaClock.stop();
        for (Renderer renderer : this.renderers) {
            if (isRendererEnabled(renderer)) {
                ensureStopped(renderer);
            }
        }
    }

    private void updateIsLoading() {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        boolean z2 = this.shouldContinueLoading || (loadingPeriod != null && loadingPeriod.mediaPeriod.isLoading());
        PlaybackInfo playbackInfo = this.playbackInfo;
        if (z2 != playbackInfo.isLoading) {
            this.playbackInfo = playbackInfo.copyWithIsLoading(z2);
        }
    }

    private void updateLivePlaybackSpeedControl(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline2, MediaSource.MediaPeriodId mediaPeriodId2, long j2) {
        if (timeline.isEmpty() || !shouldUseLivePlaybackSpeedControl(timeline, mediaPeriodId)) {
            float f2 = this.mediaClock.getPlaybackParameters().speed;
            PlaybackParameters playbackParameters = this.playbackInfo.playbackParameters;
            if (f2 != playbackParameters.speed) {
                this.mediaClock.setPlaybackParameters(playbackParameters);
                return;
            }
            return;
        }
        timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, this.window);
        this.livePlaybackSpeedControl.setLiveConfiguration((MediaItem.LiveConfiguration) Util.castNonNull(this.window.liveConfiguration));
        if (j2 != C.TIME_UNSET) {
            this.livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(getLiveOffsetUs(timeline, mediaPeriodId.periodUid, j2));
            return;
        }
        if (Util.areEqual(!timeline2.isEmpty() ? timeline2.getWindow(timeline2.getPeriodByUid(mediaPeriodId2.periodUid, this.period).windowIndex, this.window).uid : null, this.window.uid)) {
            return;
        }
        this.livePlaybackSpeedControl.setTargetLiveOffsetOverrideUs(C.TIME_UNSET);
    }

    private void updateLoadControlTrackSelection(TrackGroupArray trackGroupArray, TrackSelectorResult trackSelectorResult) {
        this.loadControl.onTracksSelected(this.renderers, trackGroupArray, trackSelectorResult.selections);
    }

    private void updatePeriods() throws ExoPlaybackException, IOException {
        if (this.playbackInfo.timeline.isEmpty() || !this.mediaSourceList.isPrepared()) {
            return;
        }
        maybeUpdateLoadingPeriod();
        maybeUpdateReadingPeriod();
        maybeUpdateReadingRenderers();
        maybeUpdatePlayingPeriod();
    }

    private void updatePlaybackPositions() throws ExoPlaybackException {
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        if (playingPeriod == null) {
            return;
        }
        long discontinuity = playingPeriod.prepared ? playingPeriod.mediaPeriod.readDiscontinuity() : -9223372036854775807L;
        if (discontinuity != C.TIME_UNSET) {
            resetRendererPosition(discontinuity);
            if (discontinuity != this.playbackInfo.positionUs) {
                PlaybackInfo playbackInfo = this.playbackInfo;
                this.playbackInfo = handlePositionDiscontinuity(playbackInfo.periodId, discontinuity, playbackInfo.requestedContentPositionUs, discontinuity, true, 5);
            }
        } else {
            long jSyncAndGetPositionUs = this.mediaClock.syncAndGetPositionUs(playingPeriod != this.queue.getReadingPeriod());
            this.rendererPositionUs = jSyncAndGetPositionUs;
            long periodTime = playingPeriod.toPeriodTime(jSyncAndGetPositionUs);
            maybeTriggerPendingMessages(this.playbackInfo.positionUs, periodTime);
            this.playbackInfo.positionUs = periodTime;
        }
        this.playbackInfo.bufferedPositionUs = this.queue.getLoadingPeriod().getBufferedPositionUs();
        this.playbackInfo.totalBufferedDurationUs = getTotalBufferedDurationUs();
        PlaybackInfo playbackInfo2 = this.playbackInfo;
        if (playbackInfo2.playWhenReady && playbackInfo2.playbackState == 3 && shouldUseLivePlaybackSpeedControl(playbackInfo2.timeline, playbackInfo2.periodId) && this.playbackInfo.playbackParameters.speed == 1.0f) {
            float adjustedPlaybackSpeed = this.livePlaybackSpeedControl.getAdjustedPlaybackSpeed(getCurrentLiveOffsetUs(), getTotalBufferedDurationUs());
            if (this.mediaClock.getPlaybackParameters().speed != adjustedPlaybackSpeed) {
                this.mediaClock.setPlaybackParameters(this.playbackInfo.playbackParameters.withSpeed(adjustedPlaybackSpeed));
                handlePlaybackParameters(this.playbackInfo.playbackParameters, this.mediaClock.getPlaybackParameters().speed, false, false);
            }
        }
    }

    private void updateTrackSelectionPlaybackSpeed(float f2) {
        for (MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod(); playingPeriod != null; playingPeriod = playingPeriod.getNext()) {
            for (ExoTrackSelection exoTrackSelection : playingPeriod.getTrackSelectorResult().selections) {
                if (exoTrackSelection != null) {
                    exoTrackSelection.onPlaybackSpeed(f2);
                }
            }
        }
    }

    private synchronized void waitUninterruptibly(Supplier<Boolean> supplier, long j2) {
        long jElapsedRealtime = this.clock.elapsedRealtime() + j2;
        boolean z2 = false;
        while (!supplier.get().booleanValue() && j2 > 0) {
            try {
                this.clock.onThreadBlocked();
                wait(j2);
            } catch (InterruptedException unused) {
                z2 = true;
            }
            j2 = jElapsedRealtime - this.clock.elapsedRealtime();
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
    }

    public void addMediaSources(int i2, List<MediaSourceList.MediaSourceHolder> list, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(18, i2, 0, new MediaSourceListUpdateMessage(list, shuffleOrder, -1, C.TIME_UNSET)).sendToTarget();
    }

    public void experimentalSetForegroundModeTimeoutMs(long j2) {
        this.setForegroundModeTimeoutMs = j2;
    }

    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        this.handler.obtainMessage(24, z2 ? 1 : 0, 0).sendToTarget();
    }

    public Looper getPlaybackLooper() {
        return this.playbackLooper;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) throws Throwable {
        int i2;
        MediaPeriodHolder readingPeriod;
        try {
            switch (message.what) {
                case 0:
                    prepareInternal();
                    break;
                case 1:
                    setPlayWhenReadyInternal(message.arg1 != 0, message.arg2, true, 1);
                    break;
                case 2:
                    doSomeWork();
                    break;
                case 3:
                    seekToInternal((SeekPosition) message.obj);
                    break;
                case 4:
                    setPlaybackParametersInternal((PlaybackParameters) message.obj);
                    break;
                case 5:
                    setSeekParametersInternal((SeekParameters) message.obj);
                    break;
                case 6:
                    stopInternal(false, true);
                    break;
                case 7:
                    releaseInternal();
                    return true;
                case 8:
                    handlePeriodPrepared((MediaPeriod) message.obj);
                    break;
                case 9:
                    handleContinueLoadingRequested((MediaPeriod) message.obj);
                    break;
                case 10:
                    reselectTracksInternal();
                    break;
                case 11:
                    setRepeatModeInternal(message.arg1);
                    break;
                case 12:
                    setShuffleModeEnabledInternal(message.arg1 != 0);
                    break;
                case 13:
                    setForegroundModeInternal(message.arg1 != 0, (AtomicBoolean) message.obj);
                    break;
                case 14:
                    sendMessageInternal((PlayerMessage) message.obj);
                    break;
                case 15:
                    sendMessageToTargetThread((PlayerMessage) message.obj);
                    break;
                case 16:
                    handlePlaybackParameters((PlaybackParameters) message.obj, false);
                    break;
                case 17:
                    setMediaItemsInternal((MediaSourceListUpdateMessage) message.obj);
                    break;
                case 18:
                    addMediaItemsInternal((MediaSourceListUpdateMessage) message.obj, message.arg1);
                    break;
                case 19:
                    moveMediaItemsInternal((MoveMediaItemsMessage) message.obj);
                    break;
                case 20:
                    removeMediaItemsInternal(message.arg1, message.arg2, (ShuffleOrder) message.obj);
                    break;
                case 21:
                    setShuffleOrderInternal((ShuffleOrder) message.obj);
                    break;
                case 22:
                    mediaSourceListUpdateRequestedInternal();
                    break;
                case 23:
                    setPauseAtEndOfWindowInternal(message.arg1 != 0);
                    break;
                case 24:
                    setOffloadSchedulingEnabledInternal(message.arg1 == 1);
                    break;
                case 25:
                    attemptRendererErrorRecovery();
                    break;
                default:
                    return false;
            }
        } catch (ExoPlaybackException e2) {
            e = e2;
            if (e.type == 1 && (readingPeriod = this.queue.getReadingPeriod()) != null) {
                e = e.copyWithMediaPeriodId(readingPeriod.info.id);
            }
            if (e.isRecoverable && this.pendingRecoverableRendererError == null) {
                Log.w(TAG, "Recoverable renderer error", e);
                this.pendingRecoverableRendererError = e;
                HandlerWrapper handlerWrapper = this.handler;
                handlerWrapper.sendMessageAtFrontOfQueue(handlerWrapper.obtainMessage(25, e));
            } else {
                ExoPlaybackException exoPlaybackException = this.pendingRecoverableRendererError;
                if (exoPlaybackException != null) {
                    exoPlaybackException.addSuppressed(e);
                    e = this.pendingRecoverableRendererError;
                }
                Log.e(TAG, "Playback error", e);
                stopInternal(true, false);
                this.playbackInfo = this.playbackInfo.copyWithPlaybackError(e);
            }
        } catch (ParserException e3) {
            int i3 = e3.dataType;
            if (i3 == 1) {
                i2 = e3.contentIsMalformed ? 3001 : 3003;
            } else {
                if (i3 == 4) {
                    i2 = e3.contentIsMalformed ? 3002 : 3004;
                }
                handleIoException(e3, i);
            }
            i = i2;
            handleIoException(e3, i);
        } catch (DrmSession.DrmSessionException e4) {
            handleIoException(e4, e4.errorCode);
        } catch (BehindLiveWindowException e5) {
            handleIoException(e5, 1002);
        } catch (DataSourceException e6) {
            handleIoException(e6, e6.reason);
        } catch (IOException e7) {
            handleIoException(e7, 2000);
        } catch (RuntimeException e8) {
            ExoPlaybackException exoPlaybackExceptionCreateForUnexpected = ExoPlaybackException.createForUnexpected(e8, ((e8 instanceof IllegalStateException) || (e8 instanceof IllegalArgumentException)) ? 1004 : 1000);
            Log.e(TAG, "Playback error", exoPlaybackExceptionCreateForUnexpected);
            stopInternal(true, false);
            this.playbackInfo = this.playbackInfo.copyWithPlaybackError(exoPlaybackExceptionCreateForUnexpected);
        }
        maybeNotifyPlaybackInfoChanged();
        return true;
    }

    public void moveMediaSources(int i2, int i3, int i4, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(19, new MoveMediaItemsMessage(i2, i3, i4, shuffleOrder)).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.DefaultMediaClock.PlaybackParametersListener
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(16, playbackParameters).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.MediaSourceList.MediaSourceListInfoRefreshListener
    public void onPlaylistUpdateRequested() {
        this.handler.sendEmptyMessage(22);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(8, mediaPeriod).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public void onTrackSelectionsInvalidated() {
        this.handler.sendEmptyMessage(10);
    }

    public void prepare() {
        this.handler.obtainMessage(0).sendToTarget();
    }

    public synchronized boolean release() {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            this.handler.sendEmptyMessage(7);
            waitUninterruptibly(new Supplier() { // from class: com.google.android.exoplayer2.h1
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return this.f6786c.lambda$release$0();
                }
            }, this.releaseTimeoutMs);
            return this.released;
        }
        return true;
    }

    public void removeMediaSources(int i2, int i3, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(20, i2, i3, shuffleOrder).sendToTarget();
    }

    public void seekTo(Timeline timeline, int i2, long j2) {
        this.handler.obtainMessage(3, new SeekPosition(timeline, i2, j2)).sendToTarget();
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Sender
    public synchronized void sendMessage(PlayerMessage playerMessage) {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            this.handler.obtainMessage(14, playerMessage).sendToTarget();
            return;
        }
        Log.w(TAG, "Ignoring messages sent after release.");
        playerMessage.markAsProcessed(false);
    }

    public synchronized boolean setForegroundMode(boolean z2) {
        if (!this.released && this.internalPlaybackThread.isAlive()) {
            if (z2) {
                this.handler.obtainMessage(13, 1, 0).sendToTarget();
                return true;
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            this.handler.obtainMessage(13, 0, 0, atomicBoolean).sendToTarget();
            waitUninterruptibly(new Supplier() { // from class: com.google.android.exoplayer2.j1
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return Boolean.valueOf(atomicBoolean.get());
                }
            }, this.setForegroundModeTimeoutMs);
            return atomicBoolean.get();
        }
        return true;
    }

    public void setMediaSources(List<MediaSourceList.MediaSourceHolder> list, int i2, long j2, ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(17, new MediaSourceListUpdateMessage(list, shuffleOrder, i2, j2)).sendToTarget();
    }

    public void setPauseAtEndOfWindow(boolean z2) {
        this.handler.obtainMessage(23, z2 ? 1 : 0, 0).sendToTarget();
    }

    public void setPlayWhenReady(boolean z2, int i2) {
        this.handler.obtainMessage(1, z2 ? 1 : 0, i2).sendToTarget();
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.handler.obtainMessage(4, playbackParameters).sendToTarget();
    }

    public void setRepeatMode(int i2) {
        this.handler.obtainMessage(11, i2, 0).sendToTarget();
    }

    public void setSeekParameters(SeekParameters seekParameters) {
        this.handler.obtainMessage(5, seekParameters).sendToTarget();
    }

    public void setShuffleModeEnabled(boolean z2) {
        this.handler.obtainMessage(12, z2 ? 1 : 0, 0).sendToTarget();
    }

    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        this.handler.obtainMessage(21, shuffleOrder).sendToTarget();
    }

    public void stop() {
        this.handler.obtainMessage(6).sendToTarget();
    }

    private void enableRenderers(boolean[] zArr) throws ExoPlaybackException {
        MediaPeriodHolder readingPeriod = this.queue.getReadingPeriod();
        TrackSelectorResult trackSelectorResult = readingPeriod.getTrackSelectorResult();
        for (int i2 = 0; i2 < this.renderers.length; i2++) {
            if (!trackSelectorResult.isRendererEnabled(i2) && this.renderersToReset.remove(this.renderers[i2])) {
                this.renderers[i2].reset();
            }
        }
        for (int i3 = 0; i3 < this.renderers.length; i3++) {
            if (trackSelectorResult.isRendererEnabled(i3)) {
                enableRenderer(i3, zArr[i3]);
            }
        }
        readingPeriod.allRenderersInCorrectState = true;
    }

    private long getTotalBufferedDurationUs(long j2) {
        MediaPeriodHolder loadingPeriod = this.queue.getLoadingPeriod();
        if (loadingPeriod == null) {
            return 0L;
        }
        return Math.max(0L, j2 - loadingPeriod.toPeriodTime(this.rendererPositionUs));
    }

    private void handlePlaybackParameters(PlaybackParameters playbackParameters, float f2, boolean z2, boolean z3) throws ExoPlaybackException {
        if (z2) {
            if (z3) {
                this.playbackInfoUpdate.incrementPendingOperationAcks(1);
            }
            this.playbackInfo = this.playbackInfo.copyWithPlaybackParameters(playbackParameters);
        }
        updateTrackSelectionPlaybackSpeed(playbackParameters.speed);
        for (Renderer renderer : this.renderers) {
            if (renderer != null) {
                renderer.setPlaybackSpeed(f2, playbackParameters.speed);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.handler.obtainMessage(9, mediaPeriod).sendToTarget();
    }

    private long seekToPeriodPosition(MediaSource.MediaPeriodId mediaPeriodId, long j2, boolean z2, boolean z3) throws ExoPlaybackException {
        stopRenderers();
        this.isRebuffering = false;
        if (z3 || this.playbackInfo.playbackState == 3) {
            setState(2);
        }
        MediaPeriodHolder playingPeriod = this.queue.getPlayingPeriod();
        MediaPeriodHolder next = playingPeriod;
        while (next != null && !mediaPeriodId.equals(next.info.id)) {
            next = next.getNext();
        }
        if (z2 || playingPeriod != next || (next != null && next.toRendererTime(j2) < 0)) {
            for (Renderer renderer : this.renderers) {
                disableRenderer(renderer);
            }
            if (next != null) {
                while (this.queue.getPlayingPeriod() != next) {
                    this.queue.advancePlayingPeriod();
                }
                this.queue.removeAfter(next);
                next.setRendererOffset(MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US);
                enableRenderers();
            }
        }
        if (next != null) {
            this.queue.removeAfter(next);
            if (!next.prepared) {
                next.info = next.info.copyWithStartPositionUs(j2);
            } else if (next.hasEnabledTracks) {
                long jSeekToUs = next.mediaPeriod.seekToUs(j2);
                next.mediaPeriod.discardBuffer(jSeekToUs - this.backBufferDurationUs, this.retainBackBufferFromKeyframe);
                j2 = jSeekToUs;
            }
            resetRendererPosition(j2);
            maybeContinueLoading();
        } else {
            this.queue.clear();
            resetRendererPosition(j2);
        }
        handleLoadingMediaPeriodChanged(false);
        this.handler.sendEmptyMessage(2);
        return j2;
    }
}
