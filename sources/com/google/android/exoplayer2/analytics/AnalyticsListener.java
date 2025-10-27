package com.google.android.exoplayer2.analytics;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.common.base.Objects;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public interface AnalyticsListener {
    public static final int EVENT_AUDIO_ATTRIBUTES_CHANGED = 1016;
    public static final int EVENT_AUDIO_CODEC_ERROR = 1037;
    public static final int EVENT_AUDIO_DECODER_INITIALIZED = 1009;
    public static final int EVENT_AUDIO_DECODER_RELEASED = 1013;
    public static final int EVENT_AUDIO_DISABLED = 1014;
    public static final int EVENT_AUDIO_ENABLED = 1008;
    public static final int EVENT_AUDIO_INPUT_FORMAT_CHANGED = 1010;
    public static final int EVENT_AUDIO_POSITION_ADVANCING = 1011;
    public static final int EVENT_AUDIO_SESSION_ID = 1015;
    public static final int EVENT_AUDIO_SINK_ERROR = 1018;
    public static final int EVENT_AUDIO_UNDERRUN = 1012;
    public static final int EVENT_AVAILABLE_COMMANDS_CHANGED = 13;
    public static final int EVENT_BANDWIDTH_ESTIMATE = 1006;
    public static final int EVENT_DOWNSTREAM_FORMAT_CHANGED = 1004;
    public static final int EVENT_DRM_KEYS_LOADED = 1031;
    public static final int EVENT_DRM_KEYS_REMOVED = 1034;
    public static final int EVENT_DRM_KEYS_RESTORED = 1033;
    public static final int EVENT_DRM_SESSION_ACQUIRED = 1030;
    public static final int EVENT_DRM_SESSION_MANAGER_ERROR = 1032;
    public static final int EVENT_DRM_SESSION_RELEASED = 1035;
    public static final int EVENT_DROPPED_VIDEO_FRAMES = 1023;
    public static final int EVENT_IS_LOADING_CHANGED = 3;
    public static final int EVENT_IS_PLAYING_CHANGED = 7;
    public static final int EVENT_LOAD_CANCELED = 1002;
    public static final int EVENT_LOAD_COMPLETED = 1001;
    public static final int EVENT_LOAD_ERROR = 1003;
    public static final int EVENT_LOAD_STARTED = 1000;
    public static final int EVENT_MAX_SEEK_TO_PREVIOUS_POSITION_CHANGED = 18;
    public static final int EVENT_MEDIA_ITEM_TRANSITION = 1;
    public static final int EVENT_MEDIA_METADATA_CHANGED = 14;
    public static final int EVENT_METADATA = 1007;
    public static final int EVENT_PLAYBACK_PARAMETERS_CHANGED = 12;
    public static final int EVENT_PLAYBACK_STATE_CHANGED = 4;
    public static final int EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED = 6;
    public static final int EVENT_PLAYER_ERROR = 10;
    public static final int EVENT_PLAYER_RELEASED = 1036;
    public static final int EVENT_PLAYLIST_METADATA_CHANGED = 15;
    public static final int EVENT_PLAY_WHEN_READY_CHANGED = 5;
    public static final int EVENT_POSITION_DISCONTINUITY = 11;
    public static final int EVENT_RENDERED_FIRST_FRAME = 1027;
    public static final int EVENT_REPEAT_MODE_CHANGED = 8;
    public static final int EVENT_SEEK_BACK_INCREMENT_CHANGED = 16;
    public static final int EVENT_SEEK_FORWARD_INCREMENT_CHANGED = 17;
    public static final int EVENT_SHUFFLE_MODE_ENABLED_CHANGED = 9;
    public static final int EVENT_SKIP_SILENCE_ENABLED_CHANGED = 1017;
    public static final int EVENT_SURFACE_SIZE_CHANGED = 1029;
    public static final int EVENT_TIMELINE_CHANGED = 0;
    public static final int EVENT_TRACKS_CHANGED = 2;
    public static final int EVENT_UPSTREAM_DISCARDED = 1005;
    public static final int EVENT_VIDEO_CODEC_ERROR = 1038;
    public static final int EVENT_VIDEO_DECODER_INITIALIZED = 1021;
    public static final int EVENT_VIDEO_DECODER_RELEASED = 1024;
    public static final int EVENT_VIDEO_DISABLED = 1025;
    public static final int EVENT_VIDEO_ENABLED = 1020;
    public static final int EVENT_VIDEO_FRAME_PROCESSING_OFFSET = 1026;
    public static final int EVENT_VIDEO_INPUT_FORMAT_CHANGED = 1022;
    public static final int EVENT_VIDEO_SIZE_CHANGED = 1028;
    public static final int EVENT_VOLUME_CHANGED = 1019;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventFlags {
    }

    public static final class EventTime {

        @Nullable
        public final MediaSource.MediaPeriodId currentMediaPeriodId;
        public final long currentPlaybackPositionMs;
        public final Timeline currentTimeline;
        public final int currentWindowIndex;
        public final long eventPlaybackPositionMs;

        @Nullable
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final long realtimeMs;
        public final Timeline timeline;
        public final long totalBufferedDurationMs;
        public final int windowIndex;

        public EventTime(long j2, Timeline timeline, int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j3, Timeline timeline2, int i3, @Nullable MediaSource.MediaPeriodId mediaPeriodId2, long j4, long j5) {
            this.realtimeMs = j2;
            this.timeline = timeline;
            this.windowIndex = i2;
            this.mediaPeriodId = mediaPeriodId;
            this.eventPlaybackPositionMs = j3;
            this.currentTimeline = timeline2;
            this.currentWindowIndex = i3;
            this.currentMediaPeriodId = mediaPeriodId2;
            this.currentPlaybackPositionMs = j4;
            this.totalBufferedDurationMs = j5;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EventTime.class != obj.getClass()) {
                return false;
            }
            EventTime eventTime = (EventTime) obj;
            return this.realtimeMs == eventTime.realtimeMs && this.windowIndex == eventTime.windowIndex && this.eventPlaybackPositionMs == eventTime.eventPlaybackPositionMs && this.currentWindowIndex == eventTime.currentWindowIndex && this.currentPlaybackPositionMs == eventTime.currentPlaybackPositionMs && this.totalBufferedDurationMs == eventTime.totalBufferedDurationMs && Objects.equal(this.timeline, eventTime.timeline) && Objects.equal(this.mediaPeriodId, eventTime.mediaPeriodId) && Objects.equal(this.currentTimeline, eventTime.currentTimeline) && Objects.equal(this.currentMediaPeriodId, eventTime.currentMediaPeriodId);
        }

        public int hashCode() {
            return Objects.hashCode(Long.valueOf(this.realtimeMs), this.timeline, Integer.valueOf(this.windowIndex), this.mediaPeriodId, Long.valueOf(this.eventPlaybackPositionMs), this.currentTimeline, Integer.valueOf(this.currentWindowIndex), this.currentMediaPeriodId, Long.valueOf(this.currentPlaybackPositionMs), Long.valueOf(this.totalBufferedDurationMs));
        }
    }

    public static final class Events {
        private final SparseArray<EventTime> eventTimes;
        private final FlagSet flags;

        public Events(FlagSet flagSet, SparseArray<EventTime> sparseArray) {
            this.flags = flagSet;
            SparseArray<EventTime> sparseArray2 = new SparseArray<>(flagSet.size());
            for (int i2 = 0; i2 < flagSet.size(); i2++) {
                int i3 = flagSet.get(i2);
                sparseArray2.append(i3, (EventTime) Assertions.checkNotNull(sparseArray.get(i3)));
            }
            this.eventTimes = sparseArray2;
        }

        public boolean contains(int i2) {
            return this.flags.contains(i2);
        }

        public boolean containsAny(int... iArr) {
            return this.flags.containsAny(iArr);
        }

        public int get(int i2) {
            return this.flags.get(i2);
        }

        public EventTime getEventTime(int i2) {
            return (EventTime) Assertions.checkNotNull(this.eventTimes.get(i2));
        }

        public int size() {
            return this.flags.size();
        }
    }

    void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes);

    void onAudioCodecError(EventTime eventTime, Exception exc);

    @Deprecated
    void onAudioDecoderInitialized(EventTime eventTime, String str, long j2);

    void onAudioDecoderInitialized(EventTime eventTime, String str, long j2, long j3);

    void onAudioDecoderReleased(EventTime eventTime, String str);

    void onAudioDisabled(EventTime eventTime, DecoderCounters decoderCounters);

    void onAudioEnabled(EventTime eventTime, DecoderCounters decoderCounters);

    @Deprecated
    void onAudioInputFormatChanged(EventTime eventTime, Format format);

    void onAudioInputFormatChanged(EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation);

    void onAudioPositionAdvancing(EventTime eventTime, long j2);

    void onAudioSessionIdChanged(EventTime eventTime, int i2);

    void onAudioSinkError(EventTime eventTime, Exception exc);

    void onAudioUnderrun(EventTime eventTime, int i2, long j2, long j3);

    void onAvailableCommandsChanged(EventTime eventTime, Player.Commands commands);

    void onBandwidthEstimate(EventTime eventTime, int i2, long j2, long j3);

    @Deprecated
    void onDecoderDisabled(EventTime eventTime, int i2, DecoderCounters decoderCounters);

    @Deprecated
    void onDecoderEnabled(EventTime eventTime, int i2, DecoderCounters decoderCounters);

    @Deprecated
    void onDecoderInitialized(EventTime eventTime, int i2, String str, long j2);

    @Deprecated
    void onDecoderInputFormatChanged(EventTime eventTime, int i2, Format format);

    void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData);

    void onDrmKeysLoaded(EventTime eventTime);

    void onDrmKeysRemoved(EventTime eventTime);

    void onDrmKeysRestored(EventTime eventTime);

    @Deprecated
    void onDrmSessionAcquired(EventTime eventTime);

    void onDrmSessionAcquired(EventTime eventTime, int i2);

    void onDrmSessionManagerError(EventTime eventTime, Exception exc);

    void onDrmSessionReleased(EventTime eventTime);

    void onDroppedVideoFrames(EventTime eventTime, int i2, long j2);

    void onEvents(Player player, Events events);

    void onIsLoadingChanged(EventTime eventTime, boolean z2);

    void onIsPlayingChanged(EventTime eventTime, boolean z2);

    void onLoadCanceled(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadCompleted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadError(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2);

    void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    @Deprecated
    void onLoadingChanged(EventTime eventTime, boolean z2);

    void onMaxSeekToPreviousPositionChanged(EventTime eventTime, long j2);

    void onMediaItemTransition(EventTime eventTime, @Nullable MediaItem mediaItem, int i2);

    void onMediaMetadataChanged(EventTime eventTime, MediaMetadata mediaMetadata);

    void onMetadata(EventTime eventTime, Metadata metadata);

    void onPlayWhenReadyChanged(EventTime eventTime, boolean z2, int i2);

    void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters);

    void onPlaybackStateChanged(EventTime eventTime, int i2);

    void onPlaybackSuppressionReasonChanged(EventTime eventTime, int i2);

    void onPlayerError(EventTime eventTime, PlaybackException playbackException);

    void onPlayerReleased(EventTime eventTime);

    @Deprecated
    void onPlayerStateChanged(EventTime eventTime, boolean z2, int i2);

    void onPlaylistMetadataChanged(EventTime eventTime, MediaMetadata mediaMetadata);

    @Deprecated
    void onPositionDiscontinuity(EventTime eventTime, int i2);

    void onPositionDiscontinuity(EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2);

    void onRenderedFirstFrame(EventTime eventTime, Object obj, long j2);

    void onRepeatModeChanged(EventTime eventTime, int i2);

    void onSeekBackIncrementChanged(EventTime eventTime, long j2);

    void onSeekForwardIncrementChanged(EventTime eventTime, long j2);

    @Deprecated
    void onSeekProcessed(EventTime eventTime);

    @Deprecated
    void onSeekStarted(EventTime eventTime);

    void onShuffleModeChanged(EventTime eventTime, boolean z2);

    void onSkipSilenceEnabledChanged(EventTime eventTime, boolean z2);

    void onSurfaceSizeChanged(EventTime eventTime, int i2, int i3);

    void onTimelineChanged(EventTime eventTime, int i2);

    @Deprecated
    void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray);

    void onTracksInfoChanged(EventTime eventTime, TracksInfo tracksInfo);

    void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData);

    void onVideoCodecError(EventTime eventTime, Exception exc);

    @Deprecated
    void onVideoDecoderInitialized(EventTime eventTime, String str, long j2);

    void onVideoDecoderInitialized(EventTime eventTime, String str, long j2, long j3);

    void onVideoDecoderReleased(EventTime eventTime, String str);

    void onVideoDisabled(EventTime eventTime, DecoderCounters decoderCounters);

    void onVideoEnabled(EventTime eventTime, DecoderCounters decoderCounters);

    void onVideoFrameProcessingOffset(EventTime eventTime, long j2, int i2);

    @Deprecated
    void onVideoInputFormatChanged(EventTime eventTime, Format format);

    void onVideoInputFormatChanged(EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation);

    @Deprecated
    void onVideoSizeChanged(EventTime eventTime, int i2, int i3, int i4, float f2);

    void onVideoSizeChanged(EventTime eventTime, VideoSize videoSize);

    void onVolumeChanged(EventTime eventTime, float f2);
}
