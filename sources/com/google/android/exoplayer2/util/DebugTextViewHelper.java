package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.TextView;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
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
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class DebugTextViewHelper implements Player.Listener, Runnable {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private final ExoPlayer player;
    private boolean started;
    private final TextView textView;

    public DebugTextViewHelper(ExoPlayer exoPlayer, TextView textView) {
        Assertions.checkArgument(exoPlayer.getApplicationLooper() == Looper.getMainLooper());
        this.player = exoPlayer;
        this.textView = textView;
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters decoderCounters) {
        if (decoderCounters == null) {
            return "";
        }
        decoderCounters.ensureUpdated();
        int i2 = decoderCounters.skippedInputBufferCount;
        int i3 = decoderCounters.skippedOutputBufferCount;
        int i4 = decoderCounters.renderedOutputBufferCount;
        int i5 = decoderCounters.droppedBufferCount;
        int i6 = decoderCounters.maxConsecutiveDroppedBufferCount;
        int i7 = decoderCounters.droppedToKeyframeCount;
        StringBuilder sb = new StringBuilder(93);
        sb.append(" sib:");
        sb.append(i2);
        sb.append(" sb:");
        sb.append(i3);
        sb.append(" rb:");
        sb.append(i4);
        sb.append(" db:");
        sb.append(i5);
        sb.append(" mcdb:");
        sb.append(i6);
        sb.append(" dk:");
        sb.append(i7);
        return sb.toString();
    }

    private static String getPixelAspectRatioString(float f2) {
        if (f2 == -1.0f || f2 == 1.0f) {
            return "";
        }
        String strValueOf = String.valueOf(String.format(Locale.US, "%.02f", Float.valueOf(f2)));
        return strValueOf.length() != 0 ? " par:".concat(strValueOf) : new String(" par:");
    }

    private static String getVideoFrameProcessingOffsetAverageString(long j2, int i2) {
        return i2 == 0 ? "N/A" : String.valueOf((long) (j2 / i2));
    }

    public String getAudioString() {
        Format audioFormat = this.player.getAudioFormat();
        DecoderCounters audioDecoderCounters = this.player.getAudioDecoderCounters();
        if (audioFormat == null || audioDecoderCounters == null) {
            return "";
        }
        String str = audioFormat.sampleMimeType;
        String str2 = audioFormat.id;
        int i2 = audioFormat.sampleRate;
        int i3 = audioFormat.channelCount;
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(audioDecoderCounters);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length() + String.valueOf(decoderCountersBufferCountString).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" hz:");
        sb.append(i2);
        sb.append(" ch:");
        sb.append(i3);
        sb.append(decoderCountersBufferCountString);
        sb.append(")");
        return sb.toString();
    }

    public String getDebugString() {
        String playerStateString = getPlayerStateString();
        String videoString = getVideoString();
        String audioString = getAudioString();
        StringBuilder sb = new StringBuilder(String.valueOf(playerStateString).length() + String.valueOf(videoString).length() + String.valueOf(audioString).length());
        sb.append(playerStateString);
        sb.append(videoString);
        sb.append(audioString);
        return sb.toString();
    }

    public String getPlayerStateString() {
        int playbackState = this.player.getPlaybackState();
        return String.format("playWhenReady:%s playbackState:%s item:%s", Boolean.valueOf(this.player.getPlayWhenReady()), playbackState != 1 ? playbackState != 2 ? playbackState != 3 ? playbackState != 4 ? "unknown" : "ended" : "ready" : "buffering" : PLVLinkMicItemDataBean.STATUS_IDLE, Integer.valueOf(this.player.getCurrentMediaItemIndex()));
    }

    public String getVideoString() {
        Format videoFormat = this.player.getVideoFormat();
        DecoderCounters videoDecoderCounters = this.player.getVideoDecoderCounters();
        if (videoFormat == null || videoDecoderCounters == null) {
            return "";
        }
        String str = videoFormat.sampleMimeType;
        String str2 = videoFormat.id;
        int i2 = videoFormat.width;
        int i3 = videoFormat.height;
        String pixelAspectRatioString = getPixelAspectRatioString(videoFormat.pixelWidthHeightRatio);
        String decoderCountersBufferCountString = getDecoderCountersBufferCountString(videoDecoderCounters);
        String videoFrameProcessingOffsetAverageString = getVideoFrameProcessingOffsetAverageString(videoDecoderCounters.totalVideoFrameProcessingOffsetUs, videoDecoderCounters.videoFrameProcessingOffsetCount);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 39 + String.valueOf(str2).length() + String.valueOf(pixelAspectRatioString).length() + String.valueOf(decoderCountersBufferCountString).length() + String.valueOf(videoFrameProcessingOffsetAverageString).length());
        sb.append("\n");
        sb.append(str);
        sb.append("(id:");
        sb.append(str2);
        sb.append(" r:");
        sb.append(i2);
        sb.append("x");
        sb.append(i3);
        sb.append(pixelAspectRatioString);
        sb.append(decoderCountersBufferCountString);
        sb.append(" vfpo: ");
        sb.append(videoFrameProcessingOffsetAverageString);
        sb.append(")");
        return sb.toString();
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
        x1.a(this, audioAttributes);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onAudioSessionIdChanged(int i2) {
        x1.b(this, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
        x1.c(this, commands);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onCues(List list) {
        x1.d(this, list);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
        x1.e(this, deviceInfo);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onDeviceVolumeChanged(int i2, boolean z2) {
        x1.f(this, i2, z2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onEvents(Player player, Player.Events events) {
        x1.g(this, player, events);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onIsLoadingChanged(boolean z2) {
        x1.h(this, z2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onIsPlayingChanged(boolean z2) {
        x1.i(this, z2);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onLoadingChanged(boolean z2) {
        w1.e(this, z2);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j2) {
        w1.f(this, j2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i2) {
        x1.j(this, mediaItem, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
        x1.k(this, mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onMetadata(Metadata metadata) {
        x1.l(this, metadata);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(boolean z2, int i2) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        x1.n(this, playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(int i2) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
        x1.p(this, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlayerError(PlaybackException playbackException) {
        x1.q(this, playbackException);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
        x1.r(this, playbackException);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlayerStateChanged(boolean z2, int i2) {
        w1.o(this, z2, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
        x1.s(this, mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPositionDiscontinuity(int i2) {
        w1.q(this, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onRenderedFirstFrame() {
        x1.u(this);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onRepeatModeChanged(int i2) {
        x1.v(this, i2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onSeekBackIncrementChanged(long j2) {
        x1.w(this, j2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onSeekForwardIncrementChanged(long j2) {
        x1.x(this, j2);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onSeekProcessed() {
        w1.v(this);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z2) {
        x1.y(this, z2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z2) {
        x1.z(this, z2);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onSurfaceSizeChanged(int i2, int i3) {
        x1.A(this, i2, i3);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTimelineChanged(Timeline timeline, int i2) {
        x1.B(this, timeline, i2);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
        w1.y(this, trackSelectionParameters);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        w1.z(this, trackGroupArray, trackSelectionArray);
    }

    @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTracksInfoChanged(TracksInfo tracksInfo) {
        x1.C(this, tracksInfo);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
        x1.D(this, videoSize);
    }

    @Override // com.google.android.exoplayer2.Player.Listener
    public /* synthetic */ void onVolumeChanged(float f2) {
        x1.E(this, f2);
    }

    @Override // java.lang.Runnable
    public final void run() {
        updateAndPost();
    }

    public final void start() {
        if (this.started) {
            return;
        }
        this.started = true;
        this.player.addListener((Player.Listener) this);
        updateAndPost();
    }

    public final void stop() {
        if (this.started) {
            this.started = false;
            this.player.removeListener((Player.Listener) this);
            this.textView.removeCallbacks(this);
        }
    }

    @SuppressLint({"SetTextI18n"})
    public final void updateAndPost() {
        this.textView.setText(getDebugString());
        this.textView.removeCallbacks(this);
        this.textView.postDelayed(this, 1000L);
    }
}
