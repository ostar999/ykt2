package com.google.android.exoplayer2;

import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.video.VideoSize;
import java.util.List;

/* loaded from: classes3.dex */
public class ForwardingPlayer implements Player {
    private final Player player;

    public static class ForwardingEventListener implements Player.EventListener {
        private final Player.EventListener eventListener;
        private final ForwardingPlayer forwardingPlayer;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ForwardingEventListener)) {
                return false;
            }
            ForwardingEventListener forwardingEventListener = (ForwardingEventListener) obj;
            if (this.forwardingPlayer.equals(forwardingEventListener.forwardingPlayer)) {
                return this.eventListener.equals(forwardingEventListener.eventListener);
            }
            return false;
        }

        public int hashCode() {
            return (this.forwardingPlayer.hashCode() * 31) + this.eventListener.hashCode();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onAvailableCommandsChanged(Player.Commands commands) {
            this.eventListener.onAvailableCommandsChanged(commands);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            this.eventListener.onEvents(this.forwardingPlayer, events);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsLoadingChanged(boolean z2) {
            this.eventListener.onIsLoadingChanged(z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onIsPlayingChanged(boolean z2) {
            this.eventListener.onIsPlayingChanged(z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onLoadingChanged(boolean z2) {
            this.eventListener.onIsLoadingChanged(z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMaxSeekToPreviousPositionChanged(long j2) {
            this.eventListener.onMaxSeekToPreviousPositionChanged(j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaItemTransition(@Nullable MediaItem mediaItem, int i2) {
            this.eventListener.onMediaItemTransition(mediaItem, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            this.eventListener.onMediaMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayWhenReadyChanged(boolean z2, int i2) {
            this.eventListener.onPlayWhenReadyChanged(z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            this.eventListener.onPlaybackParametersChanged(playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i2) {
            this.eventListener.onPlaybackStateChanged(i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackSuppressionReasonChanged(int i2) {
            this.eventListener.onPlaybackSuppressionReasonChanged(i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerError(PlaybackException playbackException) {
            this.eventListener.onPlayerError(playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerErrorChanged(@Nullable PlaybackException playbackException) {
            this.eventListener.onPlayerErrorChanged(playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlayerStateChanged(boolean z2, int i2) {
            this.eventListener.onPlayerStateChanged(z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            this.eventListener.onPlaylistMetadataChanged(mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(int i2) {
            this.eventListener.onPositionDiscontinuity(i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onRepeatModeChanged(int i2) {
            this.eventListener.onRepeatModeChanged(i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekBackIncrementChanged(long j2) {
            this.eventListener.onSeekBackIncrementChanged(j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekForwardIncrementChanged(long j2) {
            this.eventListener.onSeekForwardIncrementChanged(j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onSeekProcessed() {
            this.eventListener.onSeekProcessed();
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onShuffleModeEnabledChanged(boolean z2) {
            this.eventListener.onShuffleModeEnabledChanged(z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTimelineChanged(Timeline timeline, int i2) {
            this.eventListener.onTimelineChanged(timeline, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
            this.eventListener.onTrackSelectionParametersChanged(trackSelectionParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            this.eventListener.onTracksChanged(trackGroupArray, trackSelectionArray);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onTracksInfoChanged(TracksInfo tracksInfo) {
            this.eventListener.onTracksInfoChanged(tracksInfo);
        }

        private ForwardingEventListener(ForwardingPlayer forwardingPlayer, Player.EventListener eventListener) {
            this.forwardingPlayer = forwardingPlayer;
            this.eventListener = eventListener;
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
            this.eventListener.onPositionDiscontinuity(positionInfo, positionInfo2, i2);
        }
    }

    public static final class ForwardingListener extends ForwardingEventListener implements Player.Listener {
        private final Player.Listener listener;

        public ForwardingListener(ForwardingPlayer forwardingPlayer, Player.Listener listener) {
            super(listener);
            this.listener = listener;
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            this.listener.onAudioAttributesChanged(audioAttributes);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onAudioSessionIdChanged(int i2) {
            this.listener.onAudioSessionIdChanged(i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onCues(List<Cue> list) {
            this.listener.onCues(list);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            this.listener.onDeviceInfoChanged(deviceInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onDeviceVolumeChanged(int i2, boolean z2) {
            this.listener.onDeviceVolumeChanged(i2, z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onMetadata(Metadata metadata) {
            this.listener.onMetadata(metadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onRenderedFirstFrame() {
            this.listener.onRenderedFirstFrame();
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onSkipSilenceEnabledChanged(boolean z2) {
            this.listener.onSkipSilenceEnabledChanged(z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onSurfaceSizeChanged(int i2, int i3) {
            this.listener.onSurfaceSizeChanged(i2, i3);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onVideoSizeChanged(VideoSize videoSize) {
            this.listener.onVideoSizeChanged(videoSize);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onVolumeChanged(float f2) {
            this.listener.onVolumeChanged(f2);
        }
    }

    public ForwardingPlayer(Player player) {
        this.player = player;
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void addListener(Player.Listener listener) {
        this.player.addListener(new ForwardingListener(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(MediaItem mediaItem) {
        this.player.addMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(List<MediaItem> list) {
        this.player.addMediaItems(list);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean canAdvertiseSession() {
        return this.player.canAdvertiseSession();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearMediaItems() {
        this.player.clearMediaItems();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface() {
        this.player.clearVideoSurface();
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        this.player.clearVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        this.player.clearVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoTextureView(@Nullable TextureView textureView) {
        this.player.clearVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void decreaseDeviceVolume() {
        this.player.decreaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public Looper getApplicationLooper() {
        return this.player.getApplicationLooper();
    }

    @Override // com.google.android.exoplayer2.Player
    public AudioAttributes getAudioAttributes() {
        return this.player.getAudioAttributes();
    }

    @Override // com.google.android.exoplayer2.Player
    public Player.Commands getAvailableCommands() {
        return this.player.getAvailableCommands();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getBufferedPercentage() {
        return this.player.getBufferedPercentage();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getBufferedPosition() {
        return this.player.getBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentBufferedPosition() {
        return this.player.getContentBufferedPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentDuration() {
        return this.player.getContentDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getContentPosition() {
        return this.player.getContentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdGroupIndex() {
        return this.player.getCurrentAdGroupIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentAdIndexInAdGroup() {
        return this.player.getCurrentAdIndexInAdGroup();
    }

    @Override // com.google.android.exoplayer2.Player
    public List<Cue> getCurrentCues() {
        return this.player.getCurrentCues();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentLiveOffset() {
        return this.player.getCurrentLiveOffset();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public Object getCurrentManifest() {
        return this.player.getCurrentManifest();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public MediaItem getCurrentMediaItem() {
        return this.player.getCurrentMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentMediaItemIndex() {
        return this.player.getCurrentMediaItemIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getCurrentPeriodIndex() {
        return this.player.getCurrentPeriodIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getCurrentPosition() {
        return this.player.getCurrentPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public Timeline getCurrentTimeline() {
        return this.player.getCurrentTimeline();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackGroupArray getCurrentTrackGroups() {
        return this.player.getCurrentTrackGroups();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionArray getCurrentTrackSelections() {
        return this.player.getCurrentTrackSelections();
    }

    @Override // com.google.android.exoplayer2.Player
    public TracksInfo getCurrentTracksInfo() {
        return this.player.getCurrentTracksInfo();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public int getCurrentWindowIndex() {
        return this.player.getCurrentWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public DeviceInfo getDeviceInfo() {
        return this.player.getDeviceInfo();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getDeviceVolume() {
        return this.player.getDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getDuration() {
        return this.player.getDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getMaxSeekToPreviousPosition() {
        return this.player.getMaxSeekToPreviousPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaItem getMediaItemAt(int i2) {
        return this.player.getMediaItemAt(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public int getMediaItemCount() {
        return this.player.getMediaItemCount();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getMediaMetadata() {
        return this.player.getMediaMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getNextMediaItemIndex() {
        return this.player.getNextMediaItemIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public int getNextWindowIndex() {
        return this.player.getNextWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getPlayWhenReady() {
        return this.player.getPlayWhenReady();
    }

    @Override // com.google.android.exoplayer2.Player
    public PlaybackParameters getPlaybackParameters() {
        return this.player.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackState() {
        return this.player.getPlaybackState();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPlaybackSuppressionReason() {
        return this.player.getPlaybackSuppressionReason();
    }

    @Override // com.google.android.exoplayer2.Player
    @Nullable
    public PlaybackException getPlayerError() {
        return this.player.getPlayerError();
    }

    @Override // com.google.android.exoplayer2.Player
    public MediaMetadata getPlaylistMetadata() {
        return this.player.getPlaylistMetadata();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getPreviousMediaItemIndex() {
        return this.player.getPreviousMediaItemIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public int getPreviousWindowIndex() {
        return this.player.getPreviousWindowIndex();
    }

    @Override // com.google.android.exoplayer2.Player
    public int getRepeatMode() {
        return this.player.getRepeatMode();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekBackIncrement() {
        return this.player.getSeekBackIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getSeekForwardIncrement() {
        return this.player.getSeekForwardIncrement();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean getShuffleModeEnabled() {
        return this.player.getShuffleModeEnabled();
    }

    @Override // com.google.android.exoplayer2.Player
    public long getTotalBufferedDuration() {
        return this.player.getTotalBufferedDuration();
    }

    @Override // com.google.android.exoplayer2.Player
    public TrackSelectionParameters getTrackSelectionParameters() {
        return this.player.getTrackSelectionParameters();
    }

    @Override // com.google.android.exoplayer2.Player
    public VideoSize getVideoSize() {
        return this.player.getVideoSize();
    }

    @Override // com.google.android.exoplayer2.Player
    public float getVolume() {
        return this.player.getVolume();
    }

    public Player getWrappedPlayer() {
        return this.player;
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasNext() {
        return this.player.hasNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasNextMediaItem() {
        return this.player.hasNextMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasNextWindow() {
        return this.player.hasNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasPrevious() {
        return this.player.hasPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean hasPreviousMediaItem() {
        return this.player.hasPreviousMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean hasPreviousWindow() {
        return this.player.hasPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void increaseDeviceVolume() {
        this.player.increaseDeviceVolume();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCommandAvailable(int i2) {
        return this.player.isCommandAvailable(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentMediaItemDynamic() {
        return this.player.isCurrentMediaItemDynamic();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentMediaItemLive() {
        return this.player.isCurrentMediaItemLive();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isCurrentMediaItemSeekable() {
        return this.player.isCurrentMediaItemSeekable();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean isCurrentWindowDynamic() {
        return this.player.isCurrentWindowDynamic();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean isCurrentWindowLive() {
        return this.player.isCurrentWindowLive();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public boolean isCurrentWindowSeekable() {
        return this.player.isCurrentWindowSeekable();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isDeviceMuted() {
        return this.player.isDeviceMuted();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isLoading() {
        return this.player.isLoading();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlaying() {
        return this.player.isPlaying();
    }

    @Override // com.google.android.exoplayer2.Player
    public boolean isPlayingAd() {
        return this.player.isPlayingAd();
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItem(int i2, int i3) {
        this.player.moveMediaItem(i2, i3);
    }

    @Override // com.google.android.exoplayer2.Player
    public void moveMediaItems(int i2, int i3, int i4) {
        this.player.moveMediaItems(i2, i3, i4);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void next() {
        this.player.next();
    }

    @Override // com.google.android.exoplayer2.Player
    public void pause() {
        this.player.pause();
    }

    @Override // com.google.android.exoplayer2.Player
    public void play() {
        this.player.play();
    }

    @Override // com.google.android.exoplayer2.Player
    public void prepare() {
        this.player.prepare();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void previous() {
        this.player.previous();
    }

    @Override // com.google.android.exoplayer2.Player
    public void release() {
        this.player.release();
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeListener(Player.Listener listener) {
        this.player.removeListener(new ForwardingListener(this, listener));
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItem(int i2) {
        this.player.removeMediaItem(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void removeMediaItems(int i2, int i3) {
        this.player.removeMediaItems(i2, i3);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekBack() {
        this.player.seekBack();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekForward() {
        this.player.seekForward();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(long j2) {
        this.player.seekTo(j2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition() {
        this.player.seekToDefaultPosition();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNext() {
        this.player.seekToNext();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToNextMediaItem() {
        this.player.seekToNextMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void seekToNextWindow() {
        this.player.seekToNextWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPrevious() {
        this.player.seekToPrevious();
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToPreviousMediaItem() {
        this.player.seekToPreviousMediaItem();
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void seekToPreviousWindow() {
        this.player.seekToPreviousWindow();
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceMuted(boolean z2) {
        this.player.setDeviceMuted(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setDeviceVolume(int i2) {
        this.player.setDeviceVolume(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem) {
        this.player.setMediaItem(mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list) {
        this.player.setMediaItems(list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlayWhenReady(boolean z2) {
        this.player.setPlayWhenReady(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.player.setPlaybackParameters(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaybackSpeed(float f2) {
        this.player.setPlaybackSpeed(f2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setPlaylistMetadata(MediaMetadata mediaMetadata) {
        this.player.setPlaylistMetadata(mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setRepeatMode(int i2) {
        this.player.setRepeatMode(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setShuffleModeEnabled(boolean z2) {
        this.player.setShuffleModeEnabled(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setTrackSelectionParameters(TrackSelectionParameters trackSelectionParameters) {
        this.player.setTrackSelectionParameters(trackSelectionParameters);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurface(@Nullable Surface surface) {
        this.player.setVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceHolder(@Nullable SurfaceHolder surfaceHolder) {
        this.player.setVideoSurfaceHolder(surfaceHolder);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoSurfaceView(@Nullable SurfaceView surfaceView) {
        this.player.setVideoSurfaceView(surfaceView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVideoTextureView(@Nullable TextureView textureView) {
        this.player.setVideoTextureView(textureView);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setVolume(float f2) {
        this.player.setVolume(f2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void stop() {
        this.player.stop();
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItem(int i2, MediaItem mediaItem) {
        this.player.addMediaItem(i2, mediaItem);
    }

    @Override // com.google.android.exoplayer2.Player
    public void addMediaItems(int i2, List<MediaItem> list) {
        this.player.addMediaItems(i2, list);
    }

    @Override // com.google.android.exoplayer2.Player
    public void clearVideoSurface(@Nullable Surface surface) {
        this.player.clearVideoSurface(surface);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekTo(int i2, long j2) {
        this.player.seekTo(i2, j2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void seekToDefaultPosition(int i2) {
        this.player.seekToDefaultPosition(i2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, long j2) {
        this.player.setMediaItem(mediaItem, j2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, boolean z2) {
        this.player.setMediaItems(list, z2);
    }

    @Override // com.google.android.exoplayer2.Player
    @Deprecated
    public void stop(boolean z2) {
        this.player.stop(z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItem(MediaItem mediaItem, boolean z2) {
        this.player.setMediaItem(mediaItem, z2);
    }

    @Override // com.google.android.exoplayer2.Player
    public void setMediaItems(List<MediaItem> list, int i2, long j2) {
        this.player.setMediaItems(list, i2, j2);
    }
}
