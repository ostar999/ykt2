package com.google.android.exoplayer2.transformer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.transformer.FrameworkMuxer;
import com.google.android.exoplayer2.transformer.Muxer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@RequiresApi(18)
/* loaded from: classes3.dex */
public final class Transformer {
    public static final int PROGRESS_STATE_AVAILABLE = 1;
    public static final int PROGRESS_STATE_NO_TRANSFORMATION = 4;
    public static final int PROGRESS_STATE_UNAVAILABLE = 2;
    public static final int PROGRESS_STATE_WAITING_FOR_AVAILABILITY = 0;
    private final Clock clock;
    private final Context context;
    private Listener listener;
    private final Looper looper;
    private final MediaSourceFactory mediaSourceFactory;
    private final Muxer.Factory muxerFactory;

    @Nullable
    private MuxerWrapper muxerWrapper;

    @Nullable
    private ExoPlayer player;
    private int progressState;
    private final Transformation transformation;

    public static final class Builder {
        private Clock clock;
        private Context context;
        private boolean flattenForSlowMotion;
        private Listener listener;
        private Looper looper;
        private MediaSourceFactory mediaSourceFactory;
        private Muxer.Factory muxerFactory;
        private String outputMimeType;
        private boolean removeAudio;
        private boolean removeVideo;

        public Transformer build() {
            Assertions.checkStateNotNull(this.context);
            if (this.mediaSourceFactory == null) {
                DefaultExtractorsFactory defaultExtractorsFactory = new DefaultExtractorsFactory();
                if (this.flattenForSlowMotion) {
                    defaultExtractorsFactory.setMp4ExtractorFlags(4);
                }
                this.mediaSourceFactory = new DefaultMediaSourceFactory(this.context, defaultExtractorsFactory);
            }
            boolean zSupportsOutputMimeType = this.muxerFactory.supportsOutputMimeType(this.outputMimeType);
            String strValueOf = String.valueOf(this.outputMimeType);
            Assertions.checkState(zSupportsOutputMimeType, strValueOf.length() != 0 ? "Unsupported output MIME type: ".concat(strValueOf) : new String("Unsupported output MIME type: "));
            return new Transformer(this.context, this.mediaSourceFactory, this.muxerFactory, new Transformation(this.removeAudio, this.removeVideo, this.flattenForSlowMotion, this.outputMimeType, null, null), this.listener, this.looper, this.clock);
        }

        @VisibleForTesting
        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context.getApplicationContext();
            return this;
        }

        public Builder setFlattenForSlowMotion(boolean z2) {
            this.flattenForSlowMotion = z2;
            return this;
        }

        public Builder setListener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setLooper(Looper looper) {
            this.looper = looper;
            return this;
        }

        public Builder setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
            this.mediaSourceFactory = mediaSourceFactory;
            return this;
        }

        @VisibleForTesting
        public Builder setMuxerFactory(Muxer.Factory factory) {
            this.muxerFactory = factory;
            return this;
        }

        public Builder setOutputMimeType(String str) {
            this.outputMimeType = str;
            return this;
        }

        public Builder setRemoveAudio(boolean z2) {
            this.removeAudio = z2;
            return this;
        }

        public Builder setRemoveVideo(boolean z2) {
            this.removeVideo = z2;
            return this;
        }

        public Builder() {
            this.muxerFactory = new FrameworkMuxer.Factory();
            this.outputMimeType = "video/mp4";
            this.listener = new Listener(this) { // from class: com.google.android.exoplayer2.transformer.Transformer.Builder.1
                @Override // com.google.android.exoplayer2.transformer.Transformer.Listener
                public /* synthetic */ void onTransformationCompleted(MediaItem mediaItem) {
                    b.a(this, mediaItem);
                }

                @Override // com.google.android.exoplayer2.transformer.Transformer.Listener
                public /* synthetic */ void onTransformationError(MediaItem mediaItem, Exception exc) {
                    b.b(this, mediaItem, exc);
                }
            };
            this.looper = Util.getCurrentOrMainLooper();
            this.clock = Clock.DEFAULT;
        }

        private Builder(Transformer transformer) {
            this.context = transformer.context;
            this.mediaSourceFactory = transformer.mediaSourceFactory;
            this.muxerFactory = transformer.muxerFactory;
            this.removeAudio = transformer.transformation.removeAudio;
            this.removeVideo = transformer.transformation.removeVideo;
            this.flattenForSlowMotion = transformer.transformation.flattenForSlowMotion;
            this.outputMimeType = transformer.transformation.outputMimeType;
            this.listener = transformer.listener;
            this.looper = transformer.looper;
            this.clock = transformer.clock;
        }
    }

    public interface Listener {
        void onTransformationCompleted(MediaItem mediaItem);

        void onTransformationError(MediaItem mediaItem, Exception exc);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressState {
    }

    public final class TransformerPlayerListener implements Player.Listener {
        private final MediaItem mediaItem;
        private final MuxerWrapper muxerWrapper;

        public TransformerPlayerListener(MediaItem mediaItem, MuxerWrapper muxerWrapper) {
            this.mediaItem = mediaItem;
            this.muxerWrapper = muxerWrapper;
        }

        private void handleTransformationEnded(@Nullable Exception exc) {
            try {
                Transformer.this.releaseResources(false);
            } catch (IllegalStateException e2) {
                if (exc == null) {
                    exc = e2;
                }
            }
            if (exc == null) {
                Transformer.this.listener.onTransformationCompleted(this.mediaItem);
            } else {
                Transformer.this.listener.onTransformationError(this.mediaItem, exc);
            }
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
        public /* synthetic */ void onPlayWhenReadyChanged(boolean z2, int i2) {
            x1.m(this, z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            x1.n(this, playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i2) {
            if (i2 == 4) {
                handleTransformationEnded(null);
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
            x1.p(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(PlaybackException playbackException) {
            handleTransformationEnded(playbackException);
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
        public /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
            x1.t(this, positionInfo, positionInfo2, i2);
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
        public void onTimelineChanged(Timeline timeline, int i2) {
            if (Transformer.this.progressState != 0) {
                return;
            }
            Timeline.Window window = new Timeline.Window();
            timeline.getWindow(0, window);
            if (window.isPlaceholder) {
                return;
            }
            long j2 = window.durationUs;
            Transformer.this.progressState = (j2 <= 0 || j2 == C.TIME_UNSET) ? 2 : 1;
            ((ExoPlayer) Assertions.checkNotNull(Transformer.this.player)).play();
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
        public void onTracksInfoChanged(TracksInfo tracksInfo) {
            if (this.muxerWrapper.getTrackCount() == 0) {
                handleTransformationEnded(new IllegalStateException("The output does not contain any tracks. Check that at least one of the input sample formats is supported."));
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
            x1.D(this, videoSize);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onVolumeChanged(float f2) {
            x1.E(this, f2);
        }
    }

    public static final class TransformerRenderersFactory implements RenderersFactory {
        private final TransformerMediaClock mediaClock = new TransformerMediaClock();
        private final MuxerWrapper muxerWrapper;
        private final Transformation transformation;

        public TransformerRenderersFactory(MuxerWrapper muxerWrapper, Transformation transformation) {
            this.muxerWrapper = muxerWrapper;
            this.transformation = transformation;
        }

        @Override // com.google.android.exoplayer2.RenderersFactory
        public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
            Transformation transformation = this.transformation;
            boolean z2 = transformation.removeAudio;
            char c3 = 1;
            Renderer[] rendererArr = new Renderer[(z2 || transformation.removeVideo) ? 1 : 2];
            if (z2) {
                c3 = 0;
            } else {
                rendererArr[0] = new TransformerAudioRenderer(this.muxerWrapper, this.mediaClock, transformation);
            }
            Transformation transformation2 = this.transformation;
            if (!transformation2.removeVideo) {
                rendererArr[c3] = new TransformerMuxingVideoRenderer(this.muxerWrapper, this.mediaClock, transformation2);
            }
            return rendererArr;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.transformer");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResources(boolean z2) {
        verifyApplicationThread();
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.release();
            this.player = null;
        }
        MuxerWrapper muxerWrapper = this.muxerWrapper;
        if (muxerWrapper != null) {
            muxerWrapper.release(z2);
            this.muxerWrapper = null;
        }
        this.progressState = 4;
    }

    private void verifyApplicationThread() {
        if (Looper.myLooper() != this.looper) {
            throw new IllegalStateException("Transformer is accessed on the wrong thread.");
        }
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public void cancel() {
        releaseResources(true);
    }

    public Looper getApplicationLooper() {
        return this.looper;
    }

    public int getProgress(ProgressHolder progressHolder) {
        verifyApplicationThread();
        if (this.progressState == 1) {
            Player player = (Player) Assertions.checkNotNull(this.player);
            progressHolder.progress = Math.min((int) ((player.getCurrentPosition() * 100) / player.getDuration()), 99);
        }
        return this.progressState;
    }

    public void setListener(Listener listener) {
        verifyApplicationThread();
        this.listener = listener;
    }

    public void startTransformation(MediaItem mediaItem, String str) throws IOException {
        startTransformation(mediaItem, this.muxerFactory.create(str, this.transformation.outputMimeType));
    }

    private Transformer(Context context, MediaSourceFactory mediaSourceFactory, Muxer.Factory factory, Transformation transformation, Listener listener, Looper looper, Clock clock) {
        Assertions.checkState((transformation.removeAudio && transformation.removeVideo) ? false : true, "Audio and video cannot both be removed.");
        this.context = context;
        this.mediaSourceFactory = mediaSourceFactory;
        this.muxerFactory = factory;
        this.transformation = transformation;
        this.listener = listener;
        this.looper = looper;
        this.clock = clock;
        this.progressState = 4;
    }

    @RequiresApi(26)
    public void startTransformation(MediaItem mediaItem, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        startTransformation(mediaItem, this.muxerFactory.create(parcelFileDescriptor, this.transformation.outputMimeType));
    }

    private void startTransformation(MediaItem mediaItem, Muxer muxer) {
        verifyApplicationThread();
        if (this.player == null) {
            MuxerWrapper muxerWrapper = new MuxerWrapper(muxer, this.muxerFactory, this.transformation.outputMimeType);
            this.muxerWrapper = muxerWrapper;
            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(this.context);
            defaultTrackSelector.setParameters(new DefaultTrackSelector.ParametersBuilder(this.context).setForceHighestSupportedBitrate(true).build());
            ExoPlayer exoPlayerBuild = new ExoPlayer.Builder(this.context, new TransformerRenderersFactory(muxerWrapper, this.transformation)).setMediaSourceFactory(this.mediaSourceFactory).setTrackSelector(defaultTrackSelector).setLoadControl(new DefaultLoadControl.Builder().setBufferDurationsMs(50000, 50000, 250, 500).build()).setLooper(this.looper).setClock(this.clock).build();
            this.player = exoPlayerBuild;
            exoPlayerBuild.setMediaItem(mediaItem);
            this.player.addListener((Player.Listener) new TransformerPlayerListener(mediaItem, muxerWrapper));
            this.player.prepare();
            this.progressState = 0;
            return;
        }
        throw new IllegalStateException("There is already a transformation in progress.");
    }
}
