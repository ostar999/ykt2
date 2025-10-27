package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.DefaultLivePlaybackSpeedControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AuxEffectInfo;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.video.spherical.CameraMotionListener;
import com.google.common.base.Supplier;
import java.util.List;

/* loaded from: classes3.dex */
public interface ExoPlayer extends Player {
    public static final long DEFAULT_DETACH_SURFACE_TIMEOUT_MS = 2000;
    public static final long DEFAULT_RELEASE_TIMEOUT_MS = 500;

    @Deprecated
    public interface AudioComponent {
        @Deprecated
        void clearAuxEffectInfo();

        @Deprecated
        AudioAttributes getAudioAttributes();

        @Deprecated
        int getAudioSessionId();

        @Deprecated
        boolean getSkipSilenceEnabled();

        @Deprecated
        float getVolume();

        @Deprecated
        void setAudioAttributes(AudioAttributes audioAttributes, boolean z2);

        @Deprecated
        void setAudioSessionId(int i2);

        @Deprecated
        void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

        @Deprecated
        void setSkipSilenceEnabled(boolean z2);

        @Deprecated
        void setVolume(float f2);
    }

    public interface AudioOffloadListener {
        void onExperimentalOffloadSchedulingEnabledChanged(boolean z2);

        void onExperimentalSleepingForOffloadChanged(boolean z2);
    }

    public static final class Builder {
        Supplier<AnalyticsCollector> analyticsCollectorSupplier;
        AudioAttributes audioAttributes;
        Supplier<BandwidthMeter> bandwidthMeterSupplier;
        boolean buildCalled;
        Clock clock;
        final Context context;
        long detachSurfaceTimeoutMs;
        long foregroundModeTimeoutMs;
        boolean handleAudioBecomingNoisy;
        boolean handleAudioFocus;
        LivePlaybackSpeedControl livePlaybackSpeedControl;
        Supplier<LoadControl> loadControlSupplier;
        Looper looper;
        Supplier<MediaSourceFactory> mediaSourceFactorySupplier;
        boolean pauseAtEndOfMediaItems;

        @Nullable
        PriorityTaskManager priorityTaskManager;
        long releaseTimeoutMs;
        Supplier<RenderersFactory> renderersFactorySupplier;
        long seekBackIncrementMs;
        long seekForwardIncrementMs;
        SeekParameters seekParameters;
        boolean skipSilenceEnabled;
        Supplier<TrackSelector> trackSelectorSupplier;
        boolean useLazyPreparation;
        int videoChangeFrameRateStrategy;
        int videoScalingMode;
        int wakeMode;

        public Builder(final Context context) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.s
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$0(context);
                }
            }, (Supplier<MediaSourceFactory>) new Supplier() { // from class: com.google.android.exoplayer2.t
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$1(context);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$0(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$new$1(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$10(TrackSelector trackSelector) {
            return trackSelector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$new$11(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$new$12(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$new$13(AnalyticsCollector analyticsCollector) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$new$14(Context context) {
            return new DefaultTrackSelector(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ AnalyticsCollector lambda$new$16() {
            return new AnalyticsCollector((Clock) Assertions.checkNotNull(this.clock));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$2(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$new$3(Context context) {
            return new DefaultMediaSourceFactory(context, new DefaultExtractorsFactory());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$4(Context context) {
            return new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$new$5(MediaSourceFactory mediaSourceFactory) {
            return mediaSourceFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$6(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$new$7(MediaSourceFactory mediaSourceFactory) {
            return mediaSourceFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$new$8(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$new$9(MediaSourceFactory mediaSourceFactory) {
            return mediaSourceFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ AnalyticsCollector lambda$setAnalyticsCollector$22(AnalyticsCollector analyticsCollector) {
            return analyticsCollector;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ BandwidthMeter lambda$setBandwidthMeter$21(BandwidthMeter bandwidthMeter) {
            return bandwidthMeter;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LoadControl lambda$setLoadControl$20(LoadControl loadControl) {
            return loadControl;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ MediaSourceFactory lambda$setMediaSourceFactory$18(MediaSourceFactory mediaSourceFactory) {
            return mediaSourceFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ RenderersFactory lambda$setRenderersFactory$17(RenderersFactory renderersFactory) {
            return renderersFactory;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelector lambda$setTrackSelector$19(TrackSelector trackSelector) {
            return trackSelector;
        }

        public ExoPlayer build() {
            return buildSimpleExoPlayer();
        }

        public SimpleExoPlayer buildSimpleExoPlayer() {
            Assertions.checkState(!this.buildCalled);
            this.buildCalled = true;
            return new SimpleExoPlayer(this);
        }

        public Builder experimentalSetForegroundModeTimeoutMs(long j2) {
            Assertions.checkState(!this.buildCalled);
            this.foregroundModeTimeoutMs = j2;
            return this;
        }

        public Builder setAnalyticsCollector(final AnalyticsCollector analyticsCollector) {
            Assertions.checkState(!this.buildCalled);
            this.analyticsCollectorSupplier = new Supplier() { // from class: com.google.android.exoplayer2.z
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setAnalyticsCollector$22(analyticsCollector);
                }
            };
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes, boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.audioAttributes = audioAttributes;
            this.handleAudioFocus = z2;
            return this;
        }

        public Builder setBandwidthMeter(final BandwidthMeter bandwidthMeter) {
            Assertions.checkState(!this.buildCalled);
            this.bandwidthMeterSupplier = new Supplier() { // from class: com.google.android.exoplayer2.a0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setBandwidthMeter$21(bandwidthMeter);
                }
            };
            return this;
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            Assertions.checkState(!this.buildCalled);
            this.clock = clock;
            return this;
        }

        public Builder setDetachSurfaceTimeoutMs(long j2) {
            Assertions.checkState(!this.buildCalled);
            this.detachSurfaceTimeoutMs = j2;
            return this;
        }

        public Builder setHandleAudioBecomingNoisy(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.handleAudioBecomingNoisy = z2;
            return this;
        }

        public Builder setLivePlaybackSpeedControl(LivePlaybackSpeedControl livePlaybackSpeedControl) {
            Assertions.checkState(!this.buildCalled);
            this.livePlaybackSpeedControl = livePlaybackSpeedControl;
            return this;
        }

        public Builder setLoadControl(final LoadControl loadControl) {
            Assertions.checkState(!this.buildCalled);
            this.loadControlSupplier = new Supplier() { // from class: com.google.android.exoplayer2.b0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setLoadControl$20(loadControl);
                }
            };
            return this;
        }

        public Builder setLooper(Looper looper) {
            Assertions.checkState(!this.buildCalled);
            this.looper = looper;
            return this;
        }

        public Builder setMediaSourceFactory(final MediaSourceFactory mediaSourceFactory) {
            Assertions.checkState(!this.buildCalled);
            this.mediaSourceFactorySupplier = new Supplier() { // from class: com.google.android.exoplayer2.y
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setMediaSourceFactory$18(mediaSourceFactory);
                }
            };
            return this;
        }

        public Builder setPauseAtEndOfMediaItems(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.pauseAtEndOfMediaItems = z2;
            return this;
        }

        public Builder setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            Assertions.checkState(!this.buildCalled);
            this.priorityTaskManager = priorityTaskManager;
            return this;
        }

        public Builder setReleaseTimeoutMs(long j2) {
            Assertions.checkState(!this.buildCalled);
            this.releaseTimeoutMs = j2;
            return this;
        }

        public Builder setRenderersFactory(final RenderersFactory renderersFactory) {
            Assertions.checkState(!this.buildCalled);
            this.renderersFactorySupplier = new Supplier() { // from class: com.google.android.exoplayer2.v
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setRenderersFactory$17(renderersFactory);
                }
            };
            return this;
        }

        public Builder setSeekBackIncrementMs(@IntRange(from = 1) long j2) {
            Assertions.checkArgument(j2 > 0);
            Assertions.checkState(!this.buildCalled);
            this.seekBackIncrementMs = j2;
            return this;
        }

        public Builder setSeekForwardIncrementMs(@IntRange(from = 1) long j2) {
            Assertions.checkArgument(j2 > 0);
            Assertions.checkState(!this.buildCalled);
            this.seekForwardIncrementMs = j2;
            return this;
        }

        public Builder setSeekParameters(SeekParameters seekParameters) {
            Assertions.checkState(!this.buildCalled);
            this.seekParameters = seekParameters;
            return this;
        }

        public Builder setSkipSilenceEnabled(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.skipSilenceEnabled = z2;
            return this;
        }

        public Builder setTrackSelector(final TrackSelector trackSelector) {
            Assertions.checkState(!this.buildCalled);
            this.trackSelectorSupplier = new Supplier() { // from class: com.google.android.exoplayer2.u
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$setTrackSelector$19(trackSelector);
                }
            };
            return this;
        }

        public Builder setUseLazyPreparation(boolean z2) {
            Assertions.checkState(!this.buildCalled);
            this.useLazyPreparation = z2;
            return this;
        }

        public Builder setVideoChangeFrameRateStrategy(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.videoChangeFrameRateStrategy = i2;
            return this;
        }

        public Builder setVideoScalingMode(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.videoScalingMode = i2;
            return this;
        }

        public Builder setWakeMode(int i2) {
            Assertions.checkState(!this.buildCalled);
            this.wakeMode = i2;
            return this;
        }

        public Builder(final Context context, final RenderersFactory renderersFactory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.w
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$2(renderersFactory);
                }
            }, (Supplier<MediaSourceFactory>) new Supplier() { // from class: com.google.android.exoplayer2.x
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$3(context);
                }
            });
        }

        public Builder(final Context context, final MediaSourceFactory mediaSourceFactory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.n
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$4(context);
                }
            }, (Supplier<MediaSourceFactory>) new Supplier() { // from class: com.google.android.exoplayer2.o
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$5(mediaSourceFactory);
                }
            });
        }

        public Builder(Context context, final RenderersFactory renderersFactory, final MediaSourceFactory mediaSourceFactory) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.p
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$6(renderersFactory);
                }
            }, (Supplier<MediaSourceFactory>) new Supplier() { // from class: com.google.android.exoplayer2.q
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$7(mediaSourceFactory);
                }
            });
        }

        public Builder(Context context, final RenderersFactory renderersFactory, final MediaSourceFactory mediaSourceFactory, final TrackSelector trackSelector, final LoadControl loadControl, final BandwidthMeter bandwidthMeter, final AnalyticsCollector analyticsCollector) {
            this(context, (Supplier<RenderersFactory>) new Supplier() { // from class: com.google.android.exoplayer2.f0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$8(renderersFactory);
                }
            }, (Supplier<MediaSourceFactory>) new Supplier() { // from class: com.google.android.exoplayer2.g0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$9(mediaSourceFactory);
                }
            }, (Supplier<TrackSelector>) new Supplier() { // from class: com.google.android.exoplayer2.j
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$10(trackSelector);
                }
            }, (Supplier<LoadControl>) new Supplier() { // from class: com.google.android.exoplayer2.k
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$11(loadControl);
                }
            }, (Supplier<BandwidthMeter>) new Supplier() { // from class: com.google.android.exoplayer2.l
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$12(bandwidthMeter);
                }
            }, (Supplier<AnalyticsCollector>) new Supplier() { // from class: com.google.android.exoplayer2.m
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$13(analyticsCollector);
                }
            });
        }

        private Builder(final Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSourceFactory> supplier2) {
            this(context, supplier, supplier2, (Supplier<TrackSelector>) new Supplier() { // from class: com.google.android.exoplayer2.c0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return ExoPlayer.Builder.lambda$new$14(context);
                }
            }, (Supplier<LoadControl>) new Supplier() { // from class: com.google.android.exoplayer2.d0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return new DefaultLoadControl();
                }
            }, (Supplier<BandwidthMeter>) new Supplier() { // from class: com.google.android.exoplayer2.e0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return DefaultBandwidthMeter.getSingletonInstance(context);
                }
            }, (Supplier<AnalyticsCollector>) null);
        }

        private Builder(Context context, Supplier<RenderersFactory> supplier, Supplier<MediaSourceFactory> supplier2, Supplier<TrackSelector> supplier3, Supplier<LoadControl> supplier4, Supplier<BandwidthMeter> supplier5, @Nullable Supplier<AnalyticsCollector> supplier6) {
            this.context = context;
            this.renderersFactorySupplier = supplier;
            this.mediaSourceFactorySupplier = supplier2;
            this.trackSelectorSupplier = supplier3;
            this.loadControlSupplier = supplier4;
            this.bandwidthMeterSupplier = supplier5;
            this.analyticsCollectorSupplier = supplier6 == null ? new Supplier() { // from class: com.google.android.exoplayer2.i
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return this.f6787c.lambda$new$16();
                }
            } : supplier6;
            this.looper = Util.getCurrentOrMainLooper();
            this.audioAttributes = AudioAttributes.DEFAULT;
            this.wakeMode = 0;
            this.videoScalingMode = 1;
            this.videoChangeFrameRateStrategy = 0;
            this.useLazyPreparation = true;
            this.seekParameters = SeekParameters.DEFAULT;
            this.seekBackIncrementMs = 5000L;
            this.seekForwardIncrementMs = C.DEFAULT_SEEK_FORWARD_INCREMENT_MS;
            this.livePlaybackSpeedControl = new DefaultLivePlaybackSpeedControl.Builder().build();
            this.clock = Clock.DEFAULT;
            this.releaseTimeoutMs = 500L;
            this.detachSurfaceTimeoutMs = ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
        }
    }

    @Deprecated
    public interface DeviceComponent {
        @Deprecated
        void decreaseDeviceVolume();

        @Deprecated
        DeviceInfo getDeviceInfo();

        @Deprecated
        int getDeviceVolume();

        @Deprecated
        void increaseDeviceVolume();

        @Deprecated
        boolean isDeviceMuted();

        @Deprecated
        void setDeviceMuted(boolean z2);

        @Deprecated
        void setDeviceVolume(int i2);
    }

    @Deprecated
    public interface TextComponent {
        @Deprecated
        List<Cue> getCurrentCues();
    }

    @Deprecated
    public interface VideoComponent {
        @Deprecated
        void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void clearVideoSurface();

        @Deprecated
        void clearVideoSurface(@Nullable Surface surface);

        @Deprecated
        void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void clearVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void clearVideoTextureView(@Nullable TextureView textureView);

        @Deprecated
        int getVideoChangeFrameRateStrategy();

        @Deprecated
        int getVideoScalingMode();

        @Deprecated
        VideoSize getVideoSize();

        @Deprecated
        void setCameraMotionListener(CameraMotionListener cameraMotionListener);

        @Deprecated
        void setVideoChangeFrameRateStrategy(int i2);

        @Deprecated
        void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

        @Deprecated
        void setVideoScalingMode(int i2);

        @Deprecated
        void setVideoSurface(@Nullable Surface surface);

        @Deprecated
        void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder);

        @Deprecated
        void setVideoSurfaceView(@Nullable SurfaceView surfaceView);

        @Deprecated
        void setVideoTextureView(@Nullable TextureView textureView);
    }

    void addAnalyticsListener(AnalyticsListener analyticsListener);

    void addAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    void addListener(Player.EventListener eventListener);

    void addMediaSource(int i2, MediaSource mediaSource);

    void addMediaSource(MediaSource mediaSource);

    void addMediaSources(int i2, List<MediaSource> list);

    void addMediaSources(List<MediaSource> list);

    void clearAuxEffectInfo();

    void clearCameraMotionListener(CameraMotionListener cameraMotionListener);

    void clearVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    PlayerMessage createMessage(PlayerMessage.Target target);

    boolean experimentalIsSleepingForOffload();

    void experimentalSetOffloadSchedulingEnabled(boolean z2);

    AnalyticsCollector getAnalyticsCollector();

    @Nullable
    @Deprecated
    AudioComponent getAudioComponent();

    @Nullable
    DecoderCounters getAudioDecoderCounters();

    @Nullable
    Format getAudioFormat();

    int getAudioSessionId();

    Clock getClock();

    @Nullable
    @Deprecated
    DeviceComponent getDeviceComponent();

    boolean getPauseAtEndOfMediaItems();

    Looper getPlaybackLooper();

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    ExoPlaybackException getPlayerError();

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    /* bridge */ /* synthetic */ PlaybackException getPlayerError();

    int getRendererCount();

    int getRendererType(int i2);

    SeekParameters getSeekParameters();

    boolean getSkipSilenceEnabled();

    @Nullable
    @Deprecated
    TextComponent getTextComponent();

    @Nullable
    TrackSelector getTrackSelector();

    int getVideoChangeFrameRateStrategy();

    @Nullable
    @Deprecated
    VideoComponent getVideoComponent();

    @Nullable
    DecoderCounters getVideoDecoderCounters();

    @Nullable
    Format getVideoFormat();

    int getVideoScalingMode();

    @Deprecated
    void prepare(MediaSource mediaSource);

    @Deprecated
    void prepare(MediaSource mediaSource, boolean z2, boolean z3);

    void removeAnalyticsListener(AnalyticsListener analyticsListener);

    void removeAudioOffloadListener(AudioOffloadListener audioOffloadListener);

    @Deprecated
    void removeListener(Player.EventListener eventListener);

    @Deprecated
    void retry();

    void setAudioAttributes(AudioAttributes audioAttributes, boolean z2);

    void setAudioSessionId(int i2);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setCameraMotionListener(CameraMotionListener cameraMotionListener);

    void setForegroundMode(boolean z2);

    void setHandleAudioBecomingNoisy(boolean z2);

    @Deprecated
    void setHandleWakeLock(boolean z2);

    void setMediaSource(MediaSource mediaSource);

    void setMediaSource(MediaSource mediaSource, long j2);

    void setMediaSource(MediaSource mediaSource, boolean z2);

    void setMediaSources(List<MediaSource> list);

    void setMediaSources(List<MediaSource> list, int i2, long j2);

    void setMediaSources(List<MediaSource> list, boolean z2);

    void setPauseAtEndOfMediaItems(boolean z2);

    void setPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager);

    void setSeekParameters(@Nullable SeekParameters seekParameters);

    void setShuffleOrder(ShuffleOrder shuffleOrder);

    void setSkipSilenceEnabled(boolean z2);

    @Deprecated
    void setThrowsWhenUsingWrongThread(boolean z2);

    void setVideoChangeFrameRateStrategy(int i2);

    void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    void setVideoScalingMode(int i2);

    void setWakeMode(int i2);
}
