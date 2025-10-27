package com.easefun.polyv.mediasdk.player;

import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.easefun.polyv.mediasdk.player.misc.IMediaDataSource;

/* loaded from: classes3.dex */
public abstract class AbstractMediaPlayer implements IMediaPlayer {
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnSEIRefreshListener mOnSEIRefreshListener;
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener;
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;

    public final void notifyOnBufferingUpdate(int i2) {
        IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = this.mOnBufferingUpdateListener;
        if (onBufferingUpdateListener != null) {
            onBufferingUpdateListener.onBufferingUpdate(this, i2);
        }
    }

    public final void notifyOnCompletion() {
        IMediaPlayer.OnCompletionListener onCompletionListener = this.mOnCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(this);
        }
    }

    public final boolean notifyOnError(int i2, int i3) {
        IMediaPlayer.OnErrorListener onErrorListener = this.mOnErrorListener;
        return onErrorListener != null && onErrorListener.onError(this, i2, i3);
    }

    public final boolean notifyOnInfo(int i2, int i3) {
        IMediaPlayer.OnInfoListener onInfoListener = this.mOnInfoListener;
        return onInfoListener != null && onInfoListener.onInfo(this, i2, i3);
    }

    public final void notifyOnPrepared() {
        IMediaPlayer.OnPreparedListener onPreparedListener = this.mOnPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared(this);
        }
    }

    public final void notifyOnSEIRefresh(int i2, byte[] bArr) {
        IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener = this.mOnSEIRefreshListener;
        if (onSEIRefreshListener != null) {
            onSEIRefreshListener.onSEIRefresh(this, i2, bArr);
        }
    }

    public final void notifyOnSeekComplete() {
        IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener = this.mOnSeekCompleteListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete(this);
        }
    }

    public final void notifyOnTimedText(IjkTimedText ijkTimedText) {
        IMediaPlayer.OnTimedTextListener onTimedTextListener = this.mOnTimedTextListener;
        if (onTimedTextListener != null) {
            onTimedTextListener.onTimedText(this, ijkTimedText);
        }
    }

    public final void notifyOnVideoSizeChanged(int i2, int i3, int i4, int i5) {
        IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = this.mOnVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(this, i2, i3, i4, i5);
        }
    }

    public void resetListeners() {
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnTimedTextListener = null;
        this.mOnSEIRefreshListener = null;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public void setDataSource(IMediaDataSource iMediaDataSource) {
        throw new UnsupportedOperationException();
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnSEIRefreshListener(IMediaPlayer.OnSEIRefreshListener onSEIRefreshListener) {
        this.mOnSEIRefreshListener = onSEIRefreshListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer
    public final void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }
}
