package com.easefun.polyv.mediasdk.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.misc.IMediaDataSource;
import com.easefun.polyv.mediasdk.player.misc.ITrackInfo;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
public class MediaPlayerProxy implements IMediaPlayer {
    protected final IMediaPlayer mBackEndMediaPlayer;

    public MediaPlayerProxy(IMediaPlayer iMediaPlayer) {
        this.mBackEndMediaPlayer = iMediaPlayer;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getAudioSessionId() {
        return this.mBackEndMediaPlayer.getAudioSessionId();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public long getCurrentPosition() {
        return this.mBackEndMediaPlayer.getCurrentPosition();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public String getDataSource() {
        return this.mBackEndMediaPlayer.getDataSource();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public long getDuration() {
        return this.mBackEndMediaPlayer.getDuration();
    }

    public IMediaPlayer getInternalMediaPlayer() {
        return this.mBackEndMediaPlayer;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public MediaInfo getMediaInfo() {
        return this.mBackEndMediaPlayer.getMediaInfo();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public ITrackInfo[] getTrackInfo() {
        return this.mBackEndMediaPlayer.getTrackInfo();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoHeight() {
        return this.mBackEndMediaPlayer.getVideoHeight();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarDen() {
        return this.mBackEndMediaPlayer.getVideoSarDen();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoSarNum() {
        return this.mBackEndMediaPlayer.getVideoSarNum();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public int getVideoWidth() {
        return this.mBackEndMediaPlayer.getVideoWidth();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isLooping() {
        return this.mBackEndMediaPlayer.isLooping();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isPlayable() {
        return false;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public boolean isPlaying() {
        return this.mBackEndMediaPlayer.isPlaying();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void pause() throws IllegalStateException {
        this.mBackEndMediaPlayer.pause();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void prepareAsync() throws IllegalStateException {
        this.mBackEndMediaPlayer.prepareAsync();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void release() {
        this.mBackEndMediaPlayer.release();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void reset() {
        this.mBackEndMediaPlayer.reset();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void seekTo(long j2) throws IllegalStateException {
        this.mBackEndMediaPlayer.seekTo(j2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setAudioStreamType(int i2) {
        this.mBackEndMediaPlayer.setAudioStreamType(i2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mBackEndMediaPlayer.setDataSource(context, uri);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mBackEndMediaPlayer.setDisplay(surfaceHolder);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setKeepInBackground(boolean z2) {
        this.mBackEndMediaPlayer.setKeepInBackground(z2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLogEnabled(boolean z2) {
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setLooping(boolean z2) {
        this.mBackEndMediaPlayer.setLooping(z2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnBufferingUpdateListener(final IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        if (onBufferingUpdateListener != null) {
            this.mBackEndMediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.3
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
                public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i2) {
                    onBufferingUpdateListener.onBufferingUpdate(MediaPlayerProxy.this, i2);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnBufferingUpdateListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnCompletionListener(final IMediaPlayer.OnCompletionListener onCompletionListener) {
        if (onCompletionListener != null) {
            this.mBackEndMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.2
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    onCompletionListener.onCompletion(MediaPlayerProxy.this);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnCompletionListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnErrorListener(final IMediaPlayer.OnErrorListener onErrorListener) {
        if (onErrorListener != null) {
            this.mBackEndMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.6
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
                public boolean onError(IMediaPlayer iMediaPlayer, int i2, int i3) {
                    return onErrorListener.onError(MediaPlayerProxy.this, i2, i3);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnErrorListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnInfoListener(final IMediaPlayer.OnInfoListener onInfoListener) {
        if (onInfoListener != null) {
            this.mBackEndMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.7
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
                public boolean onInfo(IMediaPlayer iMediaPlayer, int i2, int i3) {
                    return onInfoListener.onInfo(MediaPlayerProxy.this, i2, i3);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnInfoListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnPreparedListener(final IMediaPlayer.OnPreparedListener onPreparedListener) {
        if (onPreparedListener != null) {
            this.mBackEndMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.1
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    onPreparedListener.onPrepared(MediaPlayerProxy.this);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnPreparedListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnSEIRefreshListener(final IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener) {
        if (onSEIRefreshListener != null) {
            this.mBackEndMediaPlayer.setOnSEIRefreshListener(new IMediaPlayer.OnSEIRefreshListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.9
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSEIRefreshListener
                public void onSEIRefresh(IMediaPlayer iMediaPlayer, int i2, byte[] bArr) {
                    onSEIRefreshListener.onSEIRefresh(MediaPlayerProxy.this, i2, bArr);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnSEIRefreshListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnSeekCompleteListener(final IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        if (onSeekCompleteListener != null) {
            this.mBackEndMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.4
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
                public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                    onSeekCompleteListener.onSeekComplete(MediaPlayerProxy.this);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnSeekCompleteListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnTimedTextListener(final IMediaPlayer.OnTimedTextListener onTimedTextListener) {
        if (onTimedTextListener != null) {
            this.mBackEndMediaPlayer.setOnTimedTextListener(new IMediaPlayer.OnTimedTextListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.8
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnTimedTextListener
                public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
                    onTimedTextListener.onTimedText(MediaPlayerProxy.this, ijkTimedText);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnTimedTextListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setOnVideoSizeChangedListener(final IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        if (onVideoSizeChangedListener != null) {
            this.mBackEndMediaPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() { // from class: com.easefun.polyv.mediasdk.player.MediaPlayerProxy.5
                @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
                public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i2, int i3, int i4, int i5) {
                    onVideoSizeChangedListener.onVideoSizeChanged(MediaPlayerProxy.this, i2, i3, i4, i5);
                }
            });
        } else {
            this.mBackEndMediaPlayer.setOnVideoSizeChangedListener(null);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setScreenOnWhilePlaying(boolean z2) {
        this.mBackEndMediaPlayer.setScreenOnWhilePlaying(z2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(14)
    public void setSurface(Surface surface) {
        this.mBackEndMediaPlayer.setSurface(surface);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setVolume(float f2, float f3) {
        this.mBackEndMediaPlayer.setVolume(f2, f3);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setWakeMode(Context context, int i2) {
        this.mBackEndMediaPlayer.setWakeMode(context, i2);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void start() throws IllegalStateException {
        this.mBackEndMediaPlayer.start();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void stop() throws IllegalStateException {
        this.mBackEndMediaPlayer.stop();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    @TargetApi(14)
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mBackEndMediaPlayer.setDataSource(context, uri, map);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mBackEndMediaPlayer.setDataSource(fileDescriptor);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.mBackEndMediaPlayer.setDataSource(str);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(String str, byte[] bArr, Map<String, String> map) {
        this.mBackEndMediaPlayer.setDataSource(str, bArr, map);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        this.mBackEndMediaPlayer.setDataSource(iMediaDataSource);
    }
}
