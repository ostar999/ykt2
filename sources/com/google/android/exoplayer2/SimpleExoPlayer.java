package com.google.android.exoplayer2;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.AudioBecomingNoisyManager;
import com.google.android.exoplayer2.AudioFocusManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.StreamVolumeManager;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeoutException;

@Deprecated
/* loaded from: classes3.dex */
public class SimpleExoPlayer extends BasePlayer implements ExoPlayer, ExoPlayer.AudioComponent, ExoPlayer.VideoComponent, ExoPlayer.TextComponent, ExoPlayer.DeviceComponent {
    private static final String TAG = "SimpleExoPlayer";
    private final AnalyticsCollector analyticsCollector;
    private final Context applicationContext;
    private AudioAttributes audioAttributes;
    private final AudioBecomingNoisyManager audioBecomingNoisyManager;

    @Nullable
    private DecoderCounters audioDecoderCounters;
    private final AudioFocusManager audioFocusManager;

    @Nullable
    private Format audioFormat;
    private int audioSessionId;

    @Nullable
    private CameraMotionListener cameraMotionListener;
    private final ComponentListener componentListener;
    private final ConditionVariable constructorFinished;
    private List<Cue> currentCues;
    private final long detachSurfaceTimeoutMs;
    private DeviceInfo deviceInfo;
    private final FrameMetadataListener frameMetadataListener;
    private boolean hasNotifiedFullWrongThreadWarning;
    private boolean isPriorityTaskManagerRegistered;

    @Nullable
    private AudioTrack keepSessionIdAudioTrack;
    private final CopyOnWriteArraySet<Player.Listener> listeners;

    @Nullable
    private Surface ownedSurface;
    private final ExoPlayerImpl player;
    private boolean playerReleased;

    @Nullable
    private PriorityTaskManager priorityTaskManager;
    protected final Renderer[] renderers;
    private boolean skipSilenceEnabled;

    @Nullable
    private SphericalGLSurfaceView sphericalGLSurfaceView;
    private final StreamVolumeManager streamVolumeManager;
    private int surfaceHeight;

    @Nullable
    private SurfaceHolder surfaceHolder;
    private boolean surfaceHolderSurfaceIsVideoOutput;
    private int surfaceWidth;

    @Nullable
    private TextureView textureView;
    private boolean throwsWhenUsingWrongThread;
    private int videoChangeFrameRateStrategy;

    @Nullable
    private DecoderCounters videoDecoderCounters;

    @Nullable
    private Format videoFormat;

    @Nullable
    private VideoFrameMetadataListener videoFrameMetadataListener;

    @Nullable
    private Object videoOutput;
    private int videoScalingMode;
    private VideoSize videoSize;
    private float volume;
    private final WakeLockManager wakeLockManager;
    private final WifiLockManager wifiLockManager;

    public final class ComponentListener implements VideoRendererEventListener, AudioRendererEventListener, TextOutput, MetadataOutput, SurfaceHolder.Callback, TextureView.SurfaceTextureListener, SphericalGLSurfaceView.VideoSurfaceListener, AudioFocusManager.PlayerControl, AudioBecomingNoisyManager.EventListener, StreamVolumeManager.Listener, Player.EventListener, ExoPlayer.AudioOffloadListener {
        private ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void executePlayerCommand(int i2) {
            boolean playWhenReady = SimpleExoPlayer.this.getPlayWhenReady();
            SimpleExoPlayer.this.updatePlayWhenReady(playWhenReady, i2, SimpleExoPlayer.getPlayWhenReadyChangeReason(playWhenReady, i2));
        }

        @Override // com.google.android.exoplayer2.AudioBecomingNoisyManager.EventListener
        public void onAudioBecomingNoisy() {
            SimpleExoPlayer.this.updatePlayWhenReady(false, -1, 3);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioCodecError(Exception exc) {
            SimpleExoPlayer.this.analyticsCollector.onAudioCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderInitialized(String str, long j2, long j3) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDecoderInitialized(str, j2, j3);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDecoderReleased(String str) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.analyticsCollector.onAudioDisabled(decoderCounters);
            SimpleExoPlayer.this.audioFormat = null;
            SimpleExoPlayer.this.audioDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.audioDecoderCounters = decoderCounters;
            SimpleExoPlayer.this.analyticsCollector.onAudioEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public /* synthetic */ void onAudioInputFormatChanged(Format format) {
            com.google.android.exoplayer2.audio.d.f(this, format);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.audioFormat = format;
            SimpleExoPlayer.this.analyticsCollector.onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioPositionAdvancing(long j2) {
            SimpleExoPlayer.this.analyticsCollector.onAudioPositionAdvancing(j2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioSinkError(Exception exc) {
            SimpleExoPlayer.this.analyticsCollector.onAudioSinkError(exc);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onAudioUnderrun(int i2, long j2, long j3) {
            SimpleExoPlayer.this.analyticsCollector.onAudioUnderrun(i2, j2, j3);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            w1.a(this, commands);
        }

        @Override // com.google.android.exoplayer2.text.TextOutput
        public void onCues(List<Cue> list) {
            SimpleExoPlayer.this.currentCues = list;
            Iterator it = SimpleExoPlayer.this.listeners.iterator();
            while (it.hasNext()) {
                ((Player.Listener) it.next()).onCues(list);
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onDroppedFrames(int i2, long j2) {
            SimpleExoPlayer.this.analyticsCollector.onDroppedFrames(i2, j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onEvents(Player player, Player.Events events) {
            w1.b(this, player, events);
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public /* synthetic */ void onExperimentalOffloadSchedulingEnabledChanged(boolean z2) {
            h.a(this, z2);
        }

        @Override // com.google.android.exoplayer2.ExoPlayer.AudioOffloadListener
        public void onExperimentalSleepingForOffloadChanged(boolean z2) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean z2) {
            if (SimpleExoPlayer.this.priorityTaskManager != null) {
                if (z2 && !SimpleExoPlayer.this.isPriorityTaskManagerRegistered) {
                    SimpleExoPlayer.this.priorityTaskManager.add(0);
                    SimpleExoPlayer.this.isPriorityTaskManagerRegistered = true;
                } else {
                    if (z2 || !SimpleExoPlayer.this.isPriorityTaskManagerRegistered) {
                        return;
                    }
                    SimpleExoPlayer.this.priorityTaskManager.remove(0);
                    SimpleExoPlayer.this.isPriorityTaskManagerRegistered = false;
                }
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onIsPlayingChanged(boolean z2) {
            w1.d(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onLoadingChanged(boolean z2) {
            w1.e(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j2) {
            w1.f(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i2) {
            w1.g(this, mediaItem, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            w1.h(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.metadata.MetadataOutput
        public void onMetadata(Metadata metadata) {
            SimpleExoPlayer.this.analyticsCollector.onMetadata(metadata);
            SimpleExoPlayer.this.player.onMetadata(metadata);
            Iterator it = SimpleExoPlayer.this.listeners.iterator();
            while (it.hasNext()) {
                ((Player.Listener) it.next()).onMetadata(metadata);
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean z2, int i2) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            w1.j(this, playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i2) {
            SimpleExoPlayer.this.updateWakeAndWifiLock();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
            w1.l(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerError(PlaybackException playbackException) {
            w1.m(this, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
            w1.n(this, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerStateChanged(boolean z2, int i2) {
            w1.o(this, z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            w1.p(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPositionDiscontinuity(int i2) {
            w1.q(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
            w1.r(this, positionInfo, positionInfo2, i2);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onRenderedFirstFrame(Object obj, long j2) {
            SimpleExoPlayer.this.analyticsCollector.onRenderedFirstFrame(obj, j2);
            if (SimpleExoPlayer.this.videoOutput == obj) {
                Iterator it = SimpleExoPlayer.this.listeners.iterator();
                while (it.hasNext()) {
                    ((Player.Listener) it.next()).onRenderedFirstFrame();
                }
            }
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onRepeatModeChanged(int i2) {
            w1.s(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekBackIncrementChanged(long j2) {
            w1.t(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekForwardIncrementChanged(long j2) {
            w1.u(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekProcessed() {
            w1.v(this);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z2) {
            w1.w(this, z2);
        }

        @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
        public void onSkipSilenceEnabledChanged(boolean z2) {
            if (SimpleExoPlayer.this.skipSilenceEnabled == z2) {
                return;
            }
            SimpleExoPlayer.this.skipSilenceEnabled = z2;
            SimpleExoPlayer.this.notifySkipSilenceEnabledChanged();
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamTypeChanged(int i2) {
            DeviceInfo deviceInfoCreateDeviceInfo = SimpleExoPlayer.createDeviceInfo(SimpleExoPlayer.this.streamVolumeManager);
            if (deviceInfoCreateDeviceInfo.equals(SimpleExoPlayer.this.deviceInfo)) {
                return;
            }
            SimpleExoPlayer.this.deviceInfo = deviceInfoCreateDeviceInfo;
            Iterator it = SimpleExoPlayer.this.listeners.iterator();
            while (it.hasNext()) {
                ((Player.Listener) it.next()).onDeviceInfoChanged(deviceInfoCreateDeviceInfo);
            }
        }

        @Override // com.google.android.exoplayer2.StreamVolumeManager.Listener
        public void onStreamVolumeChanged(int i2, boolean z2) {
            Iterator it = SimpleExoPlayer.this.listeners.iterator();
            while (it.hasNext()) {
                ((Player.Listener) it.next()).onDeviceVolumeChanged(i2, z2);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
            SimpleExoPlayer.this.setSurfaceTextureInternal(surfaceTexture);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i2, i3);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            SimpleExoPlayer.this.setVideoOutputInternal(null);
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i2, i3);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTimelineChanged(Timeline timeline, int i2) {
            w1.x(this, timeline, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
            w1.y(this, trackSelectionParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            w1.z(this, trackGroupArray, trackSelectionArray);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTracksInfoChanged(TracksInfo tracksInfo) {
            w1.A(this, tracksInfo);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoCodecError(Exception exc) {
            SimpleExoPlayer.this.analyticsCollector.onVideoCodecError(exc);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderInitialized(String str, long j2, long j3) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDecoderInitialized(str, j2, j3);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDecoderReleased(String str) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDecoderReleased(str);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoDisabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.analyticsCollector.onVideoDisabled(decoderCounters);
            SimpleExoPlayer.this.videoFormat = null;
            SimpleExoPlayer.this.videoDecoderCounters = null;
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoEnabled(DecoderCounters decoderCounters) {
            SimpleExoPlayer.this.videoDecoderCounters = decoderCounters;
            SimpleExoPlayer.this.analyticsCollector.onVideoEnabled(decoderCounters);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoFrameProcessingOffset(long j2, int i2) {
            SimpleExoPlayer.this.analyticsCollector.onVideoFrameProcessingOffset(j2, i2);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public /* synthetic */ void onVideoInputFormatChanged(Format format) {
            com.google.android.exoplayer2.video.d.i(this, format);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
            SimpleExoPlayer.this.videoFormat = format;
            SimpleExoPlayer.this.analyticsCollector.onVideoInputFormatChanged(format, decoderReuseEvaluation);
        }

        @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
        public void onVideoSizeChanged(VideoSize videoSize) {
            SimpleExoPlayer.this.videoSize = videoSize;
            SimpleExoPlayer.this.analyticsCollector.onVideoSizeChanged(videoSize);
            Iterator it = SimpleExoPlayer.this.listeners.iterator();
            while (it.hasNext()) {
                ((Player.Listener) it.next()).onVideoSizeChanged(videoSize);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceCreated(Surface surface) {
            SimpleExoPlayer.this.setVideoOutputInternal(surface);
        }

        @Override // com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView.VideoSurfaceListener
        public void onVideoSurfaceDestroyed(Surface surface) {
            SimpleExoPlayer.this.setVideoOutputInternal(null);
        }

        @Override // com.google.android.exoplayer2.AudioFocusManager.PlayerControl
        public void setVolumeMultiplier(float f2) {
            SimpleExoPlayer.this.sendVolumeToRenderers();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(i3, i4);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (SimpleExoPlayer.this.surfaceHolderSurfaceIsVideoOutput) {
                SimpleExoPlayer.this.setVideoOutputInternal(surfaceHolder.getSurface());
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (SimpleExoPlayer.this.surfaceHolderSurfaceIsVideoOutput) {
                SimpleExoPlayer.this.setVideoOutputInternal(null);
            }
            SimpleExoPlayer.this.maybeNotifySurfaceSizeChanged(0, 0);
        }
    }

    public static final class FrameMetadataListener implements VideoFrameMetadataListener, CameraMotionListener, PlayerMessage.Target {
        public static final int MSG_SET_CAMERA_MOTION_LISTENER = 8;
        public static final int MSG_SET_SPHERICAL_SURFACE_VIEW = 10000;
        public static final int MSG_SET_VIDEO_FRAME_METADATA_LISTENER = 7;

        @Nullable
        private CameraMotionListener cameraMotionListener;

        @Nullable
        private CameraMotionListener internalCameraMotionListener;

        @Nullable
        private VideoFrameMetadataListener internalVideoFrameMetadataListener;

        @Nullable
        private VideoFrameMetadataListener videoFrameMetadataListener;

        private FrameMetadataListener() {
        }

        @Override // com.google.android.exoplayer2.PlayerMessage.Target
        public void handleMessage(int i2, @Nullable Object obj) {
            if (i2 == 7) {
                this.videoFrameMetadataListener = (VideoFrameMetadataListener) obj;
                return;
            }
            if (i2 == 8) {
                this.cameraMotionListener = (CameraMotionListener) obj;
                return;
            }
            if (i2 != 10000) {
                return;
            }
            SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) obj;
            if (sphericalGLSurfaceView == null) {
                this.internalVideoFrameMetadataListener = null;
                this.internalCameraMotionListener = null;
            } else {
                this.internalVideoFrameMetadataListener = sphericalGLSurfaceView.getVideoFrameMetadataListener();
                this.internalCameraMotionListener = sphericalGLSurfaceView.getCameraMotionListener();
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotion(long j2, float[] fArr) {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotion(j2, fArr);
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotion(j2, fArr);
            }
        }

        @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
        public void onCameraMotionReset() {
            CameraMotionListener cameraMotionListener = this.internalCameraMotionListener;
            if (cameraMotionListener != null) {
                cameraMotionListener.onCameraMotionReset();
            }
            CameraMotionListener cameraMotionListener2 = this.cameraMotionListener;
            if (cameraMotionListener2 != null) {
                cameraMotionListener2.onCameraMotionReset();
            }
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
        public void onVideoFrameAboutToBeRendered(long j2, long j3, Format format, @Nullable MediaFormat mediaFormat) {
            VideoFrameMetadataListener videoFrameMetadataListener = this.internalVideoFrameMetadataListener;
            if (videoFrameMetadataListener != null) {
                videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2, j3, format, mediaFormat);
            }
            VideoFrameMetadataListener videoFrameMetadataListener2 = this.videoFrameMetadataListener;
            if (videoFrameMetadataListener2 != null) {
                videoFrameMetadataListener2.onVideoFrameAboutToBeRendered(j2, j3, format, mediaFormat);
            }
        }
    }

    @Deprecated
    public SimpleExoPlayer(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector, boolean z2, Clock clock, Looper looper) {
        this(new ExoPlayer.Builder(context, renderersFactory, mediaSourceFactory, trackSelector, loadControl, bandwidthMeter, analyticsCollector).setUseLazyPreparation(z2).setClock(clock).setLooper(looper));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DeviceInfo createDeviceInfo(StreamVolumeManager streamVolumeManager) {
        return new DeviceInfo(0, streamVolumeManager.getMinVolume(), streamVolumeManager.getMaxVolume());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getPlayWhenReadyChangeReason(boolean z2, int i2) {
        return (!z2 || i2 == 1) ? 1 : 2;
    }

    private int initializeKeepSessionIdAudioTrack(int i2) {
        AudioTrack audioTrack = this.keepSessionIdAudioTrack;
        if (audioTrack != null && audioTrack.getAudioSessionId() != i2) {
            this.keepSessionIdAudioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        if (this.keepSessionIdAudioTrack == null) {
            this.keepSessionIdAudioTrack = new AudioTrack(3, 4000, 4, 2, 2, 0, i2);
        }
        return this.keepSessionIdAudioTrack.getAudioSessionId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeNotifySurfaceSizeChanged(int i2, int i3) {
        if (i2 == this.surfaceWidth && i3 == this.surfaceHeight) {
            return;
        }
        this.surfaceWidth = i2;
        this.surfaceHeight = i3;
        this.analyticsCollector.onSurfaceSizeChanged(i2, i3);
        Iterator<Player.Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSurfaceSizeChanged(i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySkipSilenceEnabledChanged() {
        this.analyticsCollector.onSkipSilenceEnabledChanged(this.skipSilenceEnabled);
        Iterator<Player.Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onSkipSilenceEnabledChanged(this.skipSilenceEnabled);
        }
    }

    private void removeSurfaceCallbacks() {
        if (this.sphericalGLSurfaceView != null) {
            this.player.createMessage(this.frameMetadataListener).setType(10000).setPayload(null).send();
            this.sphericalGLSurfaceView.removeVideoSurfaceListener(this.componentListener);
            this.sphericalGLSurfaceView = null;
        }
        TextureView textureView = this.textureView;
        if (textureView != null) {
            if (textureView.getSurfaceTextureListener() != this.componentListener) {
                Log.w(TAG, "SurfaceTextureListener already unset or replaced.");
            } else {
                this.textureView.setSurfaceTextureListener(null);
            }
            this.textureView = null;
        }
        SurfaceHolder surfaceHolder = this.surfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.removeCallback(this.componentListener);
            this.surfaceHolder = null;
        }
    }

    private void sendRendererMessage(int i2, int i3, @Nullable Object obj) {
        for (Renderer renderer : this.renderers) {
            if (renderer.getTrackType() == i2) {
                this.player.createMessage(renderer).setType(i3).setPayload(obj).send();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendVolumeToRenderers() {
        sendRendererMessage(1, 2, Float.valueOf(this.volume * this.audioFocusManager.getVolumeMultiplier()));
    }

    private void setNonVideoOutputSurfaceHolderInternal(SurfaceHolder surfaceHolder) {
        this.surfaceHolderSurfaceIsVideoOutput = false;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = this.surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            Rect surfaceFrame = this.surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceTextureInternal(SurfaceTexture surfaceTexture) {
        Surface surface = new Surface(surfaceTexture);
        setVideoOutputInternal(surface);
        this.ownedSurface = surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoOutputInternal(@Nullable Object obj) {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        Renderer[] rendererArr = this.renderers;
        int length = rendererArr.length;
        int i2 = 0;
        while (true) {
            z2 = true;
            if (i2 >= length) {
                break;
            }
            Renderer renderer = rendererArr[i2];
            if (renderer.getTrackType() == 2) {
                arrayList.add(this.player.createMessage(renderer).setType(1).setPayload(obj).send());
            }
            i2++;
        }
        Object obj2 = this.videoOutput;
        if (obj2 == null || obj2 == obj) {
            z2 = false;
        } else {
            try {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((PlayerMessage) it.next()).blockUntilDelivered(this.detachSurfaceTimeoutMs);
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (TimeoutException unused2) {
            }
            z2 = false;
            Object obj3 = this.videoOutput;
            Surface surface = this.ownedSurface;
            if (obj3 == surface) {
                surface.release();
                this.ownedSurface = null;
            }
        }
        this.videoOutput = obj;
        if (z2) {
            this.player.stop(false, ExoPlaybackException.createForUnexpected(new ExoTimeoutException(3), 1003));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayWhenReady(boolean z2, int i2, int i3) {
        int i4 = 0;
        boolean z3 = z2 && i2 != -1;
        if (z3 && i2 != 1) {
            i4 = 1;
        }
        this.player.setPlayWhenReady(z3, i4, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWakeAndWifiLock() {
        int playbackState = getPlaybackState();
        if (playbackState != 1) {
            if (playbackState == 2 || playbackState == 3) {
                this.wakeLockManager.setStayAwake(getPlayWhenReady() && !experimentalIsSleepingForOffload());
                this.wifiLockManager.setStayAwake(getPlayWhenReady());
                return;
            } else if (playbackState != 4) {
                throw new IllegalStateException();
            }
        }
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
    }

    private void verifyApplicationThread() {
        this.constructorFinished.blockUninterruptible();
        if (Thread.currentThread() != getApplicationLooper().getThread()) {
            String invariant = Util.formatInvariant("Player is accessed on the wrong thread.\nCurrent thread: '%s'\nExpected thread: '%s'\nSee https://exoplayer.dev/issues/player-accessed-on-wrong-thread", Thread.currentThread().getName(), getApplicationLooper().getThread().getName());
            if (this.throwsWhenUsingWrongThread) {
                throw new IllegalStateException(invariant);
            }
            Log.w(TAG, invariant, this.hasNotifiedFullWrongThreadWarning ? null : new IllegalStateException());
            this.hasNotifiedFullWrongThreadWarning = true;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAnalyticsListener(AnalyticsListener analyticsListener) {
        Assertions.checkNotNull(analyticsListener);
        this.analyticsCollector.addListener(analyticsListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.player.addAudioOffloadListener(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.add(listener);
        addListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i2, List<MediaItem> list) {
        verifyApplicationThread();
        this.player.addMediaItems(i2, list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.addMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(List<MediaSource> list) {
        verifyApplicationThread();
        this.player.addMediaSources(list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void clearAuxEffectInfo() {
        setAuxEffectInfo(new AuxEffectInfo(0, 0.0f));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        if (this.cameraMotionListener != cameraMotionListener) {
            return;
        }
        this.player.createMessage(this.frameMetadataListener).setType(8).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        if (this.videoFrameMetadataListener != videoFrameMetadataListener) {
            return;
        }
        this.player.createMessage(this.frameMetadataListener).setType(7).setPayload(null).send();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(null);
        maybeNotifySurfaceSizeChanged(0, 0);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null || surfaceHolder != this.surfaceHolder) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        verifyApplicationThread();
        clearVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null || textureView != this.textureView) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public PlayerMessage createMessage(PlayerMessage.Target target) {
        verifyApplicationThread();
        return this.player.createMessage(target);
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.decreaseVolume();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean experimentalIsSleepingForOffload() {
        verifyApplicationThread();
        return this.player.experimentalIsSleepingForOffload();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void experimentalSetOffloadSchedulingEnabled(boolean z2) {
        verifyApplicationThread();
        this.player.experimentalSetOffloadSchedulingEnabled(z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public AnalyticsCollector getAnalyticsCollector() {
        return this.analyticsCollector;
    }

    @Override // com.google.android.exoplayer2.Player
    public Looper getApplicationLooper() {
        return this.player.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.audioAttributes;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.AudioComponent getAudioComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public DecoderCounters getAudioDecoderCounters() {
        return this.audioDecoderCounters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public Format getAudioFormat() {
        return this.audioFormat;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public int getAudioSessionId() {
        return this.audioSessionId;
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        verifyApplicationThread();
        return this.player.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        verifyApplicationThread();
        return this.player.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Clock getClock() {
        return this.player.getClock();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        verifyApplicationThread();
        return this.player.getContentBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        verifyApplicationThread();
        return this.player.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        verifyApplicationThread();
        return this.player.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        verifyApplicationThread();
        return this.player.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        verifyApplicationThread();
        return this.currentCues;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentMediaItemIndex() {
        verifyApplicationThread();
        return this.player.getCurrentMediaItemIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        verifyApplicationThread();
        return this.player.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        verifyApplicationThread();
        return this.player.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        verifyApplicationThread();
        return this.player.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        verifyApplicationThread();
        return this.player.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        verifyApplicationThread();
        return this.player.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    public TracksInfo getCurrentTracksInfo() {
        verifyApplicationThread();
        return this.player.getCurrentTracksInfo();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.DeviceComponent getDeviceComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        verifyApplicationThread();
        return this.deviceInfo;
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        verifyApplicationThread();
        return this.streamVolumeManager.getVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        verifyApplicationThread();
        return this.player.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getMaxSeekToPreviousPosition() {
        verifyApplicationThread();
        return this.player.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.player.getMediaMetadata();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public boolean getPauseAtEndOfMediaItems() {
        verifyApplicationThread();
        return this.player.getPauseAtEndOfMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        verifyApplicationThread();
        return this.player.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public Looper getPlaybackLooper() {
        return this.player.getPlaybackLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        verifyApplicationThread();
        return this.player.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        verifyApplicationThread();
        return this.player.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        verifyApplicationThread();
        return this.player.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.player.getPlaylistMetadata();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererCount() {
        verifyApplicationThread();
        return this.player.getRendererCount();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public int getRendererType(int i2) {
        verifyApplicationThread();
        return this.player.getRendererType(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        verifyApplicationThread();
        return this.player.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        verifyApplicationThread();
        return this.player.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        verifyApplicationThread();
        return this.player.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public SeekParameters getSeekParameters() {
        verifyApplicationThread();
        return this.player.getSeekParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        verifyApplicationThread();
        return this.player.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public boolean getSkipSilenceEnabled() {
        return this.skipSilenceEnabled;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.TextComponent getTextComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        verifyApplicationThread();
        return this.player.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionParameters getTrackSelectionParameters() {
        verifyApplicationThread();
        return this.player.getTrackSelectionParameters();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public TrackSelector getTrackSelector() {
        verifyApplicationThread();
        return this.player.getTrackSelector();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoChangeFrameRateStrategy() {
        return this.videoChangeFrameRateStrategy;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public ExoPlayer.VideoComponent getVideoComponent() {
        return this;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public DecoderCounters getVideoDecoderCounters() {
        return this.videoDecoderCounters;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Nullable
    public Format getVideoFormat() {
        return this.videoFormat;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public int getVideoScalingMode() {
        return this.videoScalingMode;
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.videoSize;
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.volume;
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        verifyApplicationThread();
        this.streamVolumeManager.increaseVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        verifyApplicationThread();
        return this.streamVolumeManager.isMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        verifyApplicationThread();
        return this.player.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        verifyApplicationThread();
        return this.player.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i2, int i3, int i4) {
        verifyApplicationThread();
        this.player.moveMediaItems(i2, i3, i4);
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        verifyApplicationThread();
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, 2);
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
        this.player.prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        AudioTrack audioTrack;
        verifyApplicationThread();
        if (Util.SDK_INT < 21 && (audioTrack = this.keepSessionIdAudioTrack) != null) {
            audioTrack.release();
            this.keepSessionIdAudioTrack = null;
        }
        this.audioBecomingNoisyManager.setEnabled(false);
        this.streamVolumeManager.release();
        this.wakeLockManager.setStayAwake(false);
        this.wifiLockManager.setStayAwake(false);
        this.audioFocusManager.release();
        this.player.release();
        this.analyticsCollector.release();
        removeSurfaceCallbacks();
        Surface surface = this.ownedSurface;
        if (surface != null) {
            surface.release();
            this.ownedSurface = null;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
            this.isPriorityTaskManagerRegistered = false;
        }
        this.currentCues = Collections.emptyList();
        this.playerReleased = true;
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAnalyticsListener(AnalyticsListener analyticsListener) {
        this.analyticsCollector.removeListener(analyticsListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void removeAudioOffloadListener(ExoPlayer.AudioOffloadListener audioOffloadListener) {
        this.player.removeAudioOffloadListener(audioOffloadListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        Assertions.checkNotNull(listener);
        this.listeners.remove(listener);
        removeListener((Player.EventListener) listener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i2, int i3) {
        verifyApplicationThread();
        this.player.removeMediaItems(i2, i3);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void retry() {
        verifyApplicationThread();
        prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i2, long j2) {
        verifyApplicationThread();
        this.analyticsCollector.notifySeekStarted();
        this.player.seekTo(i2, j2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        if (!Util.areEqual(this.audioAttributes, audioAttributes)) {
            this.audioAttributes = audioAttributes;
            sendRendererMessage(1, 3, audioAttributes);
            this.streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(audioAttributes.usage));
            this.analyticsCollector.onAudioAttributesChanged(audioAttributes);
            Iterator<Player.Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                it.next().onAudioAttributesChanged(audioAttributes);
            }
        }
        AudioFocusManager audioFocusManager = this.audioFocusManager;
        if (!z2) {
            audioAttributes = null;
        }
        audioFocusManager.setAudioAttributes(audioAttributes);
        boolean playWhenReady = getPlayWhenReady();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(playWhenReady, getPlaybackState());
        updatePlayWhenReady(playWhenReady, iUpdateAudioFocus, getPlayWhenReadyChangeReason(playWhenReady, iUpdateAudioFocus));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAudioSessionId(int i2) {
        verifyApplicationThread();
        if (this.audioSessionId == i2) {
            return;
        }
        if (i2 == 0) {
            i2 = Util.SDK_INT < 21 ? initializeKeepSessionIdAudioTrack(0) : Util.generateAudioSessionIdV21(this.applicationContext);
        } else if (Util.SDK_INT < 21) {
            initializeKeepSessionIdAudioTrack(i2);
        }
        this.audioSessionId = i2;
        sendRendererMessage(1, 10, Integer.valueOf(i2));
        sendRendererMessage(2, 10, Integer.valueOf(i2));
        this.analyticsCollector.onAudioSessionIdChanged(i2);
        Iterator<Player.Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onAudioSessionIdChanged(i2);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        verifyApplicationThread();
        sendRendererMessage(1, 6, auxEffectInfo);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setCameraMotionListener(CameraMotionListener cameraMotionListener) {
        verifyApplicationThread();
        this.cameraMotionListener = cameraMotionListener;
        this.player.createMessage(this.frameMetadataListener).setType(8).setPayload(cameraMotionListener).send();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z2) {
        verifyApplicationThread();
        this.streamVolumeManager.setMuted(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i2) {
        verifyApplicationThread();
        this.streamVolumeManager.setVolume(i2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setForegroundMode(boolean z2) {
        verifyApplicationThread();
        this.player.setForegroundMode(z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setHandleAudioBecomingNoisy(boolean z2) {
        verifyApplicationThread();
        if (this.playerReleased) {
            return;
        }
        this.audioBecomingNoisyManager.setEnabled(z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void setHandleWakeLock(boolean z2) {
        setWakeMode(z2 ? 1 : 0);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z2) {
        verifyApplicationThread();
        this.player.setMediaItems(list, z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list) {
        verifyApplicationThread();
        this.player.setMediaSources(list);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPauseAtEndOfMediaItems(boolean z2) {
        verifyApplicationThread();
        this.player.setPauseAtEndOfMediaItems(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z2) {
        verifyApplicationThread();
        int iUpdateAudioFocus = this.audioFocusManager.updateAudioFocus(z2, getPlaybackState());
        updatePlayWhenReady(z2, iUpdateAudioFocus, getPlayWhenReadyChangeReason(z2, iUpdateAudioFocus));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        verifyApplicationThread();
        this.player.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        this.player.setPlaylistMetadata(mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
        verifyApplicationThread();
        if (Util.areEqual(this.priorityTaskManager, priorityTaskManager)) {
            return;
        }
        if (this.isPriorityTaskManagerRegistered) {
            ((PriorityTaskManager) Assertions.checkNotNull(this.priorityTaskManager)).remove(0);
        }
        if (priorityTaskManager == null || !isLoading()) {
            this.isPriorityTaskManagerRegistered = false;
        } else {
            priorityTaskManager.add(0);
            this.isPriorityTaskManagerRegistered = true;
        }
        this.priorityTaskManager = priorityTaskManager;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int i2) {
        verifyApplicationThread();
        this.player.setRepeatMode(i2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setSeekParameters(@Nullable SeekParameters seekParameters) {
        verifyApplicationThread();
        this.player.setSeekParameters(seekParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean z2) {
        verifyApplicationThread();
        this.player.setShuffleModeEnabled(z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setShuffleOrder(ShuffleOrder shuffleOrder) {
        verifyApplicationThread();
        this.player.setShuffleOrder(shuffleOrder);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.AudioComponent
    public void setSkipSilenceEnabled(boolean z2) {
        verifyApplicationThread();
        if (this.skipSilenceEnabled == z2) {
            return;
        }
        this.skipSilenceEnabled = z2;
        sendRendererMessage(1, 9, Boolean.valueOf(z2));
        notifySkipSilenceEnabledChanged();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void setThrowsWhenUsingWrongThread(boolean z2) {
        this.throwsWhenUsingWrongThread = z2;
    }

    @Override // com.google.android.exoplayer2.Player
    public void setTrackSelectionParameters(TrackSelectionParameters trackSelectionParameters) {
        verifyApplicationThread();
        this.player.setTrackSelectionParameters(trackSelectionParameters);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoChangeFrameRateStrategy(int i2) {
        verifyApplicationThread();
        if (this.videoChangeFrameRateStrategy == i2) {
            return;
        }
        this.videoChangeFrameRateStrategy = i2;
        sendRendererMessage(2, 5, Integer.valueOf(i2));
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        verifyApplicationThread();
        this.videoFrameMetadataListener = videoFrameMetadataListener;
        this.player.createMessage(this.frameMetadataListener).setType(7).setPayload(videoFrameMetadataListener).send();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer, com.google.android.exoplayer2.ExoPlayer.VideoComponent
    public void setVideoScalingMode(int i2) {
        verifyApplicationThread();
        this.videoScalingMode = i2;
        sendRendererMessage(2, 4, Integer.valueOf(i2));
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
        verifyApplicationThread();
        removeSurfaceCallbacks();
        setVideoOutputInternal(surface);
        int i2 = surface == null ? 0 : -1;
        maybeNotifySurfaceSizeChanged(i2, i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        verifyApplicationThread();
        if (surfaceHolder == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.surfaceHolderSurfaceIsVideoOutput = true;
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this.componentListener);
        Surface surface = surfaceHolder.getSurface();
        if (surface == null || !surface.isValid()) {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            setVideoOutputInternal(surface);
            Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
            maybeNotifySurfaceSizeChanged(surfaceFrame.width(), surfaceFrame.height());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        verifyApplicationThread();
        if (surfaceView instanceof VideoDecoderOutputBufferRenderer) {
            removeSurfaceCallbacks();
            setVideoOutputInternal(surfaceView);
            setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
        } else {
            if (!(surfaceView instanceof SphericalGLSurfaceView)) {
                setVideoSurfaceHolder(surfaceView == null ? null : surfaceView.getHolder());
                return;
            }
            removeSurfaceCallbacks();
            this.sphericalGLSurfaceView = (SphericalGLSurfaceView) surfaceView;
            this.player.createMessage(this.frameMetadataListener).setType(10000).setPayload(this.sphericalGLSurfaceView).send();
            this.sphericalGLSurfaceView.addVideoSurfaceListener(this.componentListener);
            setVideoOutputInternal(this.sphericalGLSurfaceView.getVideoSurface());
            setNonVideoOutputSurfaceHolderInternal(surfaceView.getHolder());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
        verifyApplicationThread();
        if (textureView == null) {
            clearVideoSurface();
            return;
        }
        removeSurfaceCallbacks();
        this.textureView = textureView;
        if (textureView.getSurfaceTextureListener() != null) {
            Log.w(TAG, "Replacing existing SurfaceTextureListener.");
        }
        textureView.setSurfaceTextureListener(this.componentListener);
        SurfaceTexture surfaceTexture = textureView.isAvailable() ? textureView.getSurfaceTexture() : null;
        if (surfaceTexture == null) {
            setVideoOutputInternal(null);
            maybeNotifySurfaceSizeChanged(0, 0);
        } else {
            setSurfaceTextureInternal(surfaceTexture);
            maybeNotifySurfaceSizeChanged(textureView.getWidth(), textureView.getHeight());
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f2) {
        verifyApplicationThread();
        float fConstrainValue = Util.constrainValue(f2, 0.0f, 1.0f);
        if (this.volume == fConstrainValue) {
            return;
        }
        this.volume = fConstrainValue;
        sendVolumeToRenderers();
        this.analyticsCollector.onVolumeChanged(fConstrainValue);
        Iterator<Player.Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onVolumeChanged(fConstrainValue);
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setWakeMode(int i2) {
        verifyApplicationThread();
        if (i2 == 0) {
            this.wakeLockManager.setEnabled(false);
            this.wifiLockManager.setEnabled(false);
        } else if (i2 == 1) {
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(false);
        } else {
            if (i2 != 2) {
                return;
            }
            this.wakeLockManager.setEnabled(true);
            this.wifiLockManager.setEnabled(true);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        stop(false);
    }

    @Deprecated
    public static final class Builder {
        private final ExoPlayer.Builder wrappedBuilder;

        @Deprecated
        public Builder(Context context) {
            this.wrappedBuilder = new ExoPlayer.Builder(context);
        }

        @Deprecated
        public SimpleExoPlayer build() {
            return this.wrappedBuilder.buildSimpleExoPlayer();
        }

        @Deprecated
        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            this.wrappedBuilder.experimentalSetForegroundModeTimeoutMs(j2);
            return this;
        }

        @Deprecated
        public Builder setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
            this.wrappedBuilder.setAnalyticsCollector(analyticsCollector);
            return this;
        }

        @Deprecated
        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
            this.wrappedBuilder.setAudioAttributes(audioAttributes, z2);
            return this;
        }

        @Deprecated
        public Builder setBandwidthMeter(BandwidthMeter bandwidthMeter) {
            this.wrappedBuilder.setBandwidthMeter(bandwidthMeter);
            return this;
        }

        @VisibleForTesting
        @Deprecated
        public Builder setClock(Clock clock) {
            this.wrappedBuilder.setClock(clock);
            return this;
        }

        @Deprecated
        public Builder setDetachSurfaceTimeoutMs(long j2) {
            this.wrappedBuilder.setDetachSurfaceTimeoutMs(j2);
            return this;
        }

        @Deprecated
        public Builder setHandleAudioBecomingNoisy(boolean z2) {
            this.wrappedBuilder.setHandleAudioBecomingNoisy(z2);
            return this;
        }

        @Deprecated
        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            this.wrappedBuilder.setLivePlaybackSpeedControl(livePlaybackSpeedControl);
            return this;
        }

        @Deprecated
        public Builder setLoadControl(LoadControl loadControl) {
            this.wrappedBuilder.setLoadControl(loadControl);
            return this;
        }

        @Deprecated
        public Builder setLooper(Looper looper) {
            this.wrappedBuilder.setLooper(looper);
            return this;
        }

        @Deprecated
        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            this.wrappedBuilder.setMediaSourceFactory(mediaSourceFactory);
            return this;
        }

        @Deprecated
        public Builder setPauseAtEndOfMediaItems(boolean z2) {
            this.wrappedBuilder.setPauseAtEndOfMediaItems(z2);
            return this;
        }

        @Deprecated
        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            this.wrappedBuilder.setPriorityTaskManager(priorityTaskManager);
            return this;
        }

        @Deprecated
        public Builder setReleaseTimeoutMs(long j2) {
            this.wrappedBuilder.setReleaseTimeoutMs(j2);
            return this;
        }

        @Deprecated
        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j2) {
            this.wrappedBuilder.setSeekBackIncrementMs(j2);
            return this;
        }

        @Deprecated
        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j2) {
            this.wrappedBuilder.setSeekForwardIncrementMs(j2);
            return this;
        }

        @Deprecated
        public Builder setSeekParameters(SeekParameters seekParameters) {
            this.wrappedBuilder.setSeekParameters(seekParameters);
            return this;
        }

        @Deprecated
        public Builder setSkipSilenceEnabled(boolean z2) {
            this.wrappedBuilder.setSkipSilenceEnabled(z2);
            return this;
        }

        @Deprecated
        public Builder setTrackSelector(TrackSelector trackSelector) {
            this.wrappedBuilder.setTrackSelector(trackSelector);
            return this;
        }

        @Deprecated
        public Builder setUseLazyPreparation(boolean z2) {
            this.wrappedBuilder.setUseLazyPreparation(z2);
            return this;
        }

        @Deprecated
        public Builder setVideoChangeFrameRateStrategy(int i2) {
            this.wrappedBuilder.setVideoChangeFrameRateStrategy(i2);
            return this;
        }

        @Deprecated
        public Builder setVideoScalingMode(int i2) {
            this.wrappedBuilder.setVideoScalingMode(i2);
            return this;
        }

        @Deprecated
        public Builder setWakeMode(int i2) {
            this.wrappedBuilder.setWakeMode(i2);
            return this;
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory);
        }

        @Deprecated
        public Builder(Context context, ExtractorsFactory extractorsFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, new DefaultMediaSourceFactory(context, extractorsFactory));
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory, ExtractorsFactory extractorsFactory) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory, new DefaultMediaSourceFactory(context, extractorsFactory));
        }

        @Deprecated
        public Builder(Context context, RenderersFactory renderersFactory, TrackSelector trackSelector, MediaSourceFactory mediaSourceFactory, LoadControl loadControl, BandwidthMeter bandwidthMeter, AnalyticsCollector analyticsCollector) {
            this.wrappedBuilder = new ExoPlayer.Builder(context, renderersFactory, mediaSourceFactory, trackSelector, loadControl, bandwidthMeter, analyticsCollector);
        }
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public ExoPlaybackException getPlayerError() {
        verifyApplicationThread();
        return this.player.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean z2) {
        verifyApplicationThread();
        this.audioFocusManager.updateAudioFocus(getPlayWhenReady(), 1);
        this.player.stop(z2);
        this.currentCues = Collections.emptyList();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSource(int i2, MediaSource mediaSource) {
        verifyApplicationThread();
        this.player.addMediaSource(i2, mediaSource);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void addMediaSources(int i2, List<MediaSource> list) {
        verifyApplicationThread();
        this.player.addMediaSources(i2, list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        verifyApplicationThread();
        this.player.setMediaItems(list, i2, j2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, boolean z2) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource, z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, boolean z2) {
        verifyApplicationThread();
        this.player.setMediaSources(list, z2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void addListener(Player.EventListener eventListener) {
        Assertions.checkNotNull(eventListener);
        this.player.addEventListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void removeListener(Player.EventListener eventListener) {
        this.player.removeEventListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
        verifyApplicationThread();
        if (surface == null || surface != this.videoOutput) {
            return;
        }
        clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSource(MediaSource mediaSource, long j2) {
        verifyApplicationThread();
        this.player.setMediaSource(mediaSource, j2);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    public void setMediaSources(List<MediaSource> list, int i2, long j2) {
        verifyApplicationThread();
        this.player.setMediaSources(list, i2, j2);
    }

    public SimpleExoPlayer(Builder builder) {
        this(builder.wrappedBuilder);
    }

    public SimpleExoPlayer(ExoPlayer.Builder builder) throws Throwable {
        SimpleExoPlayer simpleExoPlayer;
        ComponentListener componentListener;
        FrameMetadataListener frameMetadataListener;
        Handler handler;
        ExoPlayerImpl exoPlayerImpl;
        ConditionVariable conditionVariable = new ConditionVariable();
        this.constructorFinished = conditionVariable;
        try {
            Context applicationContext = builder.context.getApplicationContext();
            this.applicationContext = applicationContext;
            AnalyticsCollector analyticsCollector = builder.analyticsCollectorSupplier.get();
            this.analyticsCollector = analyticsCollector;
            this.priorityTaskManager = builder.priorityTaskManager;
            this.audioAttributes = builder.audioAttributes;
            this.videoScalingMode = builder.videoScalingMode;
            this.videoChangeFrameRateStrategy = builder.videoChangeFrameRateStrategy;
            this.skipSilenceEnabled = builder.skipSilenceEnabled;
            this.detachSurfaceTimeoutMs = builder.detachSurfaceTimeoutMs;
            componentListener = new ComponentListener();
            this.componentListener = componentListener;
            frameMetadataListener = new FrameMetadataListener();
            this.frameMetadataListener = frameMetadataListener;
            this.listeners = new CopyOnWriteArraySet<>();
            handler = new Handler(builder.looper);
            Renderer[] rendererArrCreateRenderers = builder.renderersFactorySupplier.get().createRenderers(handler, componentListener, componentListener, componentListener, componentListener);
            this.renderers = rendererArrCreateRenderers;
            this.volume = 1.0f;
            if (Util.SDK_INT < 21) {
                this.audioSessionId = initializeKeepSessionIdAudioTrack(0);
            } else {
                this.audioSessionId = Util.generateAudioSessionIdV21(applicationContext);
            }
            this.currentCues = Collections.emptyList();
            this.throwsWhenUsingWrongThread = true;
            Player.Commands.Builder builder2 = new Player.Commands.Builder();
            int[] iArr = new int[8];
            iArr[0] = 21;
            iArr[1] = 22;
            iArr[2] = 23;
            try {
                iArr[3] = 24;
                iArr[4] = 25;
                iArr[5] = 26;
                iArr[6] = 27;
                iArr[7] = 28;
                exoPlayerImpl = new ExoPlayerImpl(rendererArrCreateRenderers, builder.trackSelectorSupplier.get(), builder.mediaSourceFactorySupplier.get(), builder.loadControlSupplier.get(), builder.bandwidthMeterSupplier.get(), analyticsCollector, builder.useLazyPreparation, builder.seekParameters, builder.seekBackIncrementMs, builder.seekForwardIncrementMs, builder.livePlaybackSpeedControl, builder.releaseTimeoutMs, builder.pauseAtEndOfMediaItems, builder.clock, builder.looper, this, builder2.addAll(iArr).build());
                simpleExoPlayer = this;
            } catch (Throwable th) {
                th = th;
                simpleExoPlayer = this;
            }
        } catch (Throwable th2) {
            th = th2;
            simpleExoPlayer = this;
        }
        try {
            simpleExoPlayer.player = exoPlayerImpl;
            exoPlayerImpl.addEventListener(componentListener);
            exoPlayerImpl.addAudioOffloadListener(componentListener);
            long j2 = builder.foregroundModeTimeoutMs;
            if (j2 > 0) {
                exoPlayerImpl.experimentalSetForegroundModeTimeoutMs(j2);
            }
            AudioBecomingNoisyManager audioBecomingNoisyManager = new AudioBecomingNoisyManager(builder.context, handler, componentListener);
            simpleExoPlayer.audioBecomingNoisyManager = audioBecomingNoisyManager;
            audioBecomingNoisyManager.setEnabled(builder.handleAudioBecomingNoisy);
            AudioFocusManager audioFocusManager = new AudioFocusManager(builder.context, handler, componentListener);
            simpleExoPlayer.audioFocusManager = audioFocusManager;
            audioFocusManager.setAudioAttributes(builder.handleAudioFocus ? simpleExoPlayer.audioAttributes : null);
            StreamVolumeManager streamVolumeManager = new StreamVolumeManager(builder.context, handler, componentListener);
            simpleExoPlayer.streamVolumeManager = streamVolumeManager;
            streamVolumeManager.setStreamType(Util.getStreamTypeForAudioUsage(simpleExoPlayer.audioAttributes.usage));
            WakeLockManager wakeLockManager = new WakeLockManager(builder.context);
            simpleExoPlayer.wakeLockManager = wakeLockManager;
            wakeLockManager.setEnabled(builder.wakeMode != 0);
            WifiLockManager wifiLockManager = new WifiLockManager(builder.context);
            simpleExoPlayer.wifiLockManager = wifiLockManager;
            wifiLockManager.setEnabled(builder.wakeMode == 2);
            simpleExoPlayer.deviceInfo = createDeviceInfo(streamVolumeManager);
            simpleExoPlayer.videoSize = VideoSize.UNKNOWN;
            simpleExoPlayer.sendRendererMessage(1, 10, Integer.valueOf(simpleExoPlayer.audioSessionId));
            simpleExoPlayer.sendRendererMessage(2, 10, Integer.valueOf(simpleExoPlayer.audioSessionId));
            simpleExoPlayer.sendRendererMessage(1, 3, simpleExoPlayer.audioAttributes);
            simpleExoPlayer.sendRendererMessage(2, 4, Integer.valueOf(simpleExoPlayer.videoScalingMode));
            simpleExoPlayer.sendRendererMessage(2, 5, Integer.valueOf(simpleExoPlayer.videoChangeFrameRateStrategy));
            simpleExoPlayer.sendRendererMessage(1, 9, Boolean.valueOf(simpleExoPlayer.skipSilenceEnabled));
            simpleExoPlayer.sendRendererMessage(2, 7, frameMetadataListener);
            simpleExoPlayer.sendRendererMessage(6, 8, frameMetadataListener);
            conditionVariable.open();
        } catch (Throwable th3) {
            th = th3;
            simpleExoPlayer.constructorFinished.open();
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource) {
        prepare(mediaSource, true, true);
    }

    @Override // com.google.android.exoplayer2.ExoPlayer
    @Deprecated
    public void prepare(MediaSource mediaSource, boolean z2, boolean z3) {
        verifyApplicationThread();
        setMediaSources(Collections.singletonList(mediaSource), z2);
        prepare();
    }
}
