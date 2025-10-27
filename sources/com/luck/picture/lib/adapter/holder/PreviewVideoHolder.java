package com.luck.picture.lib.adapter.holder;

import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.holder.BasePreviewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.File;
import java.util.List;

/* loaded from: classes4.dex */
public class PreviewVideoHolder extends BasePreviewHolder {
    public ImageView ivPlayButton;
    private Player.Listener mPlayerListener;
    public PlayerView mPlayerView;
    public ProgressBar progress;

    public PreviewVideoHolder(@NonNull View view) {
        super(view);
        this.mPlayerListener = new Player.Listener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.3
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
                if (i2 == 3) {
                    PreviewVideoHolder.this.playerIngUI();
                } else if (i2 == 4) {
                    PreviewVideoHolder.this.playerDefaultUI();
                }
            }

            @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
            public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
                x1.p(this, i2);
            }

            @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
            public void onPlayerError(@NonNull PlaybackException playbackException) {
                PreviewVideoHolder.this.playerDefaultUI();
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
        };
        this.ivPlayButton = (ImageView) view.findViewById(R.id.iv_play_video);
        this.mPlayerView = (PlayerView) view.findViewById(R.id.playerView);
        this.progress = (ProgressBar) view.findViewById(R.id.progress);
        this.ivPlayButton.setVisibility(PictureSelectionConfig.getInstance().isPreviewZoomEffect ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDefaultUI() {
        this.ivPlayButton.setVisibility(0);
        this.progress.setVisibility(8);
        this.coverImageView.setVisibility(0);
        this.mPlayerView.setVisibility(8);
        BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = this.mPreviewEventListener;
        if (onPreviewEventListener != null) {
            onPreviewEventListener.onPreviewVideoTitle(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerIngUI() {
        if (this.progress.getVisibility() == 0) {
            this.progress.setVisibility(8);
        }
        if (this.ivPlayButton.getVisibility() == 0) {
            this.ivPlayButton.setVisibility(8);
        }
        if (this.coverImageView.getVisibility() == 0) {
            this.coverImageView.setVisibility(8);
        }
        if (this.mPlayerView.getVisibility() == 8) {
            this.mPlayerView.setVisibility(0);
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void bindData(final LocalMedia localMedia, int i2) {
        super.bindData(localMedia, i2);
        final String availablePath = localMedia.getAvailablePath();
        this.mPlayerView.setUseController(false);
        setScaleDisplaySize(localMedia);
        this.ivPlayButton.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PreviewVideoHolder.this.progress.setVisibility(0);
                PreviewVideoHolder.this.ivPlayButton.setVisibility(8);
                PreviewVideoHolder.this.mPreviewEventListener.onPreviewVideoTitle(localMedia.getFileName());
                ExoPlayer exoPlayerBuild = new ExoPlayer.Builder(PreviewVideoHolder.this.itemView.getContext()).build();
                PreviewVideoHolder.this.mPlayerView.setPlayer(exoPlayerBuild);
                exoPlayerBuild.setMediaItem(MediaItem.fromUri(PictureMimeType.isContent(availablePath) ? Uri.parse(availablePath) : Uri.fromFile(new File(availablePath))));
                exoPlayerBuild.prepare();
                exoPlayerBuild.play();
                exoPlayerBuild.addListener(PreviewVideoHolder.this.mPlayerListener);
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.PreviewVideoHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BasePreviewHolder.OnPreviewEventListener onPreviewEventListener = PreviewVideoHolder.this.mPreviewEventListener;
                if (onPreviewEventListener != null) {
                    onPreviewEventListener.onBackPressed();
                }
            }
        });
    }

    public void releaseVideo() {
        if (this.mPlayerView.getPlayer() != null) {
            this.mPlayerView.getPlayer().removeListener(this.mPlayerListener);
            this.mPlayerListener = null;
            this.mPlayerView.getPlayer().release();
            playerDefaultUI();
        }
    }

    @Override // com.luck.picture.lib.adapter.holder.BasePreviewHolder
    public void setScaleDisplaySize(LocalMedia localMedia) {
        float width;
        int height;
        if (this.config.isPreviewZoomEffect || this.screenWidth >= this.screenHeight) {
            return;
        }
        if (localMedia.getWidth() > localMedia.getHeight()) {
            width = localMedia.getHeight();
            height = localMedia.getWidth();
        } else {
            width = localMedia.getWidth();
            height = localMedia.getHeight();
        }
        int i2 = (int) (this.screenWidth / (width / height));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mPlayerView.getLayoutParams();
        layoutParams.width = this.screenWidth;
        int i3 = this.screenHeight;
        if (i2 > i3) {
            i3 = this.screenAppInHeight;
        }
        layoutParams.height = i3;
        layoutParams.gravity = 17;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.coverImageView.getLayoutParams();
        layoutParams2.width = this.screenWidth;
        int i4 = this.screenHeight;
        if (i2 > i4) {
            i4 = this.screenAppInHeight;
        }
        layoutParams2.height = i4;
        layoutParams2.gravity = 17;
    }
}
