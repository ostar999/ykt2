package com.plv.business.api.common.player;

import com.easefun.polyv.mediasdk.player.IMediaPlayer;
import com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PLVMediaPlayerListenerHolder implements IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnVideoSizeChangedListener {
    public final WeakReference<IPLVVideoViewNotifyer> mWeakMediaPlayer;

    public PLVMediaPlayerListenerHolder(IPLVVideoViewNotifyer iPLVVideoViewNotifyer) {
        this.mWeakMediaPlayer = new WeakReference<>(iPLVVideoViewNotifyer);
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i2) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer != null) {
            iPLVVideoViewNotifyer.notifyOnBufferingUpdate(i2);
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnCompletionListener
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer != null) {
            iPLVVideoViewNotifyer.notifyOnCompletion();
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnErrorListener
    public boolean onError(IMediaPlayer iMediaPlayer, int i2, int i3) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer == null) {
            return true;
        }
        iPLVVideoViewNotifyer.notifyOnError(i2, i3);
        return true;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnInfoListener
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i2, int i3) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer == null) {
            return true;
        }
        iPLVVideoViewNotifyer.notifyOnInfo(i2, i3);
        return true;
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnPreparedListener
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer != null) {
            iPLVVideoViewNotifyer.notifyOnPrepared();
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer != null) {
            iPLVVideoViewNotifyer.notifyOnSeekComplete();
        }
    }

    @Override // com.easefun.polyv.mediasdk.player.IMediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i2, int i3, int i4, int i5) {
        IPLVVideoViewNotifyer iPLVVideoViewNotifyer = this.mWeakMediaPlayer.get();
        if (iPLVVideoViewNotifyer != null) {
            iPLVVideoViewNotifyer.notifyOnVideoSizeChanged(i2, i3, 1, 1);
        }
    }
}
