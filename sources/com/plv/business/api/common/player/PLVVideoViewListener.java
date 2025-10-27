package com.plv.business.api.common.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.business.model.video.PLVWatermarkVO;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes4.dex */
public class PLVVideoViewListener implements IPLVVideoViewListenerBinder, IPLVVideoViewListenerNotifyer {
    private static final String TAG = "PLVVideoViewListener";
    private IPLVVideoViewListenerEvent.OnAdvertisementCountDownListener onAdvertisementCountDownListener;
    private IPLVVideoViewListenerEvent.OnAuxiliaryPlayEndListener onAuxiliaryPlayEndListener;
    private IPLVVideoViewListenerEvent.OnBufferingUpdateListener onBufferingUpdateListener;
    private IPLVVideoViewListenerEvent.OnCoverImageOutListener onCoverImageOutListener;
    private IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener;
    private IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener;
    private IPLVVideoViewListenerEvent.OnGestureDoubleClickListener onGestureDoubleClickListener;
    private IPLVVideoViewListenerEvent.OnGestureLeftDownListener onGestureLeftDownListener;
    private IPLVVideoViewListenerEvent.OnGestureLeftUpListener onGestureLeftUpListener;
    private IPLVVideoViewListenerEvent.OnGestureRightDownListener onGestureRightDownListener;
    private IPLVVideoViewListenerEvent.OnGestureRightUpListener onGestureRightUpListener;
    private IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener onGestureSwipeLeftListener;
    private IPLVVideoViewListenerEvent.OnGestureSwipeRightListener onGestureSwipeRightListener;
    protected IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener;
    protected IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener;
    protected IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener;
    protected IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener;
    private IPLVVideoViewListenerEvent.OnRemindCallbackListener onRemindCallbackListener;
    private IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener;
    private IPLVVideoViewListenerEvent.OnVideoPauseListener onVideoPauseListener;
    private IPLVVideoViewListenerEvent.OnVideoPlayListener onVideoPlayListener;
    protected IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart;
    protected IPLVVideoViewListenerEvent.OnCompletionListener onCompletionListener = null;
    protected IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener = null;
    protected IPLVVideoViewListenerEvent.OnErrorListener onErrorListener = null;
    protected IPLVVideoViewListenerEvent.OnInfoListener onInfoListener = null;
    protected IPLVVideoViewListenerEvent.OnSeekCompleteListener onSeekCompleteListener = null;
    protected IPLVVideoViewListenerEvent.OnVideoSizeChangedListener onVideoSizeChangedListener = null;
    protected IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener = null;
    private IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener = null;

    public void clearAllListener() {
        PLVCommonLog.d(TAG, "clearAllListener");
        this.onVideoLoadSlowListener = null;
        this.onGetLogoListener = null;
        this.onGetMarqueeVoListener = null;
        this.onGetWatermarkVoListener = null;
        this.onDanmuServerOpenListener = null;
        this.onBufferingUpdateListener = null;
        this.onVideoPauseListener = null;
        this.onVideoPlayListener = null;
        this.onAuxiliaryPlayEndListener = null;
        this.onCoverImageOutListener = null;
        this.onGestureClickListener = null;
        this.onGestureLeftDownListener = null;
        this.onGestureLeftUpListener = null;
        this.onGestureRightUpListener = null;
        this.onGestureRightDownListener = null;
        this.onRemindCallbackListener = null;
        this.onGestureSwipeLeftListener = null;
        this.onGestureSwipeRightListener = null;
        this.onGestureDoubleClickListener = null;
        this.onAdvertisementCountDownListener = null;
        this.onPPTShowListener = null;
        this.onCompletionListener = null;
        this.onPreparedListener = null;
        this.onErrorListener = null;
        this.onInfoListener = null;
        this.onSeekCompleteListener = null;
        this.onVideoSizeChangedListener = null;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyAuxiliaryPlayEnd(boolean z2) {
        IPLVVideoViewListenerEvent.OnAuxiliaryPlayEndListener onAuxiliaryPlayEndListener = this.onAuxiliaryPlayEndListener;
        if (onAuxiliaryPlayEndListener != null) {
            onAuxiliaryPlayEndListener.onEnd(z2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyCoverImageOut(@NonNull String str, @Nullable String str2) {
        IPLVVideoViewListenerEvent.OnCoverImageOutListener onCoverImageOutListener = this.onCoverImageOutListener;
        if (onCoverImageOutListener != null) {
            onCoverImageOutListener.onOut(str, str2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyGestureAction(int i2, boolean z2, boolean z3) {
        IPLVVideoViewListenerEvent.OnGestureDoubleClickListener onGestureDoubleClickListener;
        if (i2 == 0) {
            IPLVVideoViewListenerEvent.OnGestureLeftUpListener onGestureLeftUpListener = this.onGestureLeftUpListener;
            if (onGestureLeftUpListener != null) {
                onGestureLeftUpListener.callback(z2, z3);
                return;
            }
            return;
        }
        if (i2 == 1) {
            IPLVVideoViewListenerEvent.OnGestureLeftDownListener onGestureLeftDownListener = this.onGestureLeftDownListener;
            if (onGestureLeftDownListener != null) {
                onGestureLeftDownListener.callback(z2, z3);
                return;
            }
            return;
        }
        if (i2 == 2) {
            IPLVVideoViewListenerEvent.OnGestureRightUpListener onGestureRightUpListener = this.onGestureRightUpListener;
            if (onGestureRightUpListener != null) {
                onGestureRightUpListener.callback(z2, z3);
                return;
            }
            return;
        }
        if (i2 == 3) {
            IPLVVideoViewListenerEvent.OnGestureRightDownListener onGestureRightDownListener = this.onGestureRightDownListener;
            if (onGestureRightDownListener != null) {
                onGestureRightDownListener.callback(z2, z3);
                return;
            }
            return;
        }
        if (i2 != 6) {
            if (i2 == 7 && (onGestureDoubleClickListener = this.onGestureDoubleClickListener) != null) {
                onGestureDoubleClickListener.callback();
                return;
            }
            return;
        }
        IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener = this.onGestureClickListener;
        if (onGestureClickListener != null) {
            onGestureClickListener.callback(z2, z3);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyGetLogo(String str, int i2, int i3, String str2) {
        IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener = this.onGetLogoListener;
        if (onGetLogoListener != null) {
            onGetLogoListener.onLogo(str, i2, i3, str2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyGetMarqueeVo(PLVLiveMarqueeVO pLVLiveMarqueeVO) {
        IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener = this.onGetMarqueeVoListener;
        if (onGetMarqueeVoListener != null) {
            onGetMarqueeVoListener.onGetMarqueeVo(pLVLiveMarqueeVO);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyGetWatermarkVO(PLVWatermarkVO pLVWatermarkVO) {
        IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener = this.onGetWatermarkVoListener;
        if (onGetWatermarkVoListener != null) {
            onGetWatermarkVoListener.onGetWatermarkVO(pLVWatermarkVO);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyLoadSlow(int i2, boolean z2) {
        IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener = this.onVideoLoadSlowListener;
        if (onVideoLoadSlowListener != null) {
            onVideoLoadSlowListener.onLoadSlow(i2, z2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public boolean notifyNetworkError() {
        IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener = this.onNetworkStateListener;
        if (onNetworkStateListener != null) {
            return onNetworkStateListener.onNetworkError();
        }
        return false;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public boolean notifyNetworkRecover() {
        IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener = this.onNetworkStateListener;
        if (onNetworkStateListener != null) {
            return onNetworkStateListener.onNetworkRecover();
        }
        return false;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyOnAdvertisementCountDown(int i2) {
        IPLVVideoViewListenerEvent.OnAdvertisementCountDownListener onAdvertisementCountDownListener = this.onAdvertisementCountDownListener;
        if (onAdvertisementCountDownListener != null) {
            onAdvertisementCountDownListener.onCountDown(i2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyOnAdvertisementCountDownEnd() {
        IPLVVideoViewListenerEvent.OnAdvertisementCountDownListener onAdvertisementCountDownListener = this.onAdvertisementCountDownListener;
        if (onAdvertisementCountDownListener != null) {
            onAdvertisementCountDownListener.onEnd();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnBufferingUpdate(int i2) {
        IPLVVideoViewListenerEvent.OnBufferingUpdateListener onBufferingUpdateListener = this.onBufferingUpdateListener;
        if (onBufferingUpdateListener != null) {
            onBufferingUpdateListener.onBufferingUpdate(i2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnCompletion() {
        IPLVVideoViewListenerEvent.OnCompletionListener onCompletionListener = this.onCompletionListener;
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion();
        }
    }

    public void notifyOnDanmuServerOpen(boolean z2) {
        IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener = this.onDanmuServerOpenListener;
        if (onDanmuServerOpenListener != null) {
            onDanmuServerOpenListener.onDanmuServerOpenListener(z2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnError(int i2, int i3) {
        IPLVVideoViewListenerEvent.OnErrorListener onErrorListener = this.onErrorListener;
        if (onErrorListener == null) {
            return true;
        }
        onErrorListener.onError(i2, i3);
        return true;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnInfo(int i2, int i3) {
        IPLVVideoViewListenerEvent.OnInfoListener onInfoListener = this.onInfoListener;
        if (onInfoListener == null) {
            return true;
        }
        onInfoListener.onInfo(i2, i3);
        return true;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnPrepared() {
        IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener = this.onPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPrepared();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnPreparing() {
        IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener = this.onPreparedListener;
        if (onPreparedListener != null) {
            onPreparedListener.onPreparing();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnSEIRefresh(int i2, byte[] bArr) {
        IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener = this.onSEIRefreshListener;
        if (onSEIRefreshListener != null) {
            onSEIRefreshListener.onSEIRefresh(i2, bArr);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnSeekComplete() {
        IPLVVideoViewListenerEvent.OnSeekCompleteListener onSeekCompleteListener = this.onSeekCompleteListener;
        if (onSeekCompleteListener != null) {
            onSeekCompleteListener.onSeekComplete();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyOnVideoPause() {
        IPLVVideoViewListenerEvent.OnVideoPauseListener onVideoPauseListener = this.onVideoPauseListener;
        if (onVideoPauseListener != null) {
            onVideoPauseListener.onPause();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyOnVideoPlay(boolean z2) {
        IPLVVideoViewListenerEvent.OnVideoPlayListener onVideoPlayListener = this.onVideoPlayListener;
        if (onVideoPlayListener != null) {
            onVideoPlayListener.onPlay(z2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public void notifyOnVideoSizeChanged(int i2, int i3, int i4, int i5) {
        IPLVVideoViewListenerEvent.OnVideoSizeChangedListener onVideoSizeChangedListener = this.onVideoSizeChangedListener;
        if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(i2, i3, i4, i5);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyPPTShow(int i2) {
        IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener = this.onPPTShowListener;
        if (onPPTShowListener != null) {
            onPPTShowListener.showPPTView(i2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyRemindCallback() {
        IPLVVideoViewListenerEvent.OnRemindCallbackListener onRemindCallbackListener = this.onRemindCallbackListener;
        if (onRemindCallbackListener != null) {
            onRemindCallbackListener.callback();
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyVideoViewRestart(boolean z2) {
        IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart = this.onVideoViewRestart;
        if (onVideoViewRestart != null) {
            onVideoViewRestart.restartLoad(z2);
        }
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnBufferingUpdateListener(IPLVVideoViewListenerEvent.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.onBufferingUpdateListener = onBufferingUpdateListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnCompletionListener(IPLVVideoViewListenerEvent.OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnCoverImageOutListener(IPLVVideoViewListenerEvent.OnCoverImageOutListener onCoverImageOutListener) {
        this.onCoverImageOutListener = onCoverImageOutListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener) {
        this.onDanmuServerOpenListener = onDanmuServerOpenListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnErrorListener(IPLVVideoViewListenerEvent.OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureClickListener(IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener) {
        this.onGestureClickListener = onGestureClickListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureDoubleClickListener(IPLVVideoViewListenerEvent.OnGestureDoubleClickListener onGestureDoubleClickListener) {
        this.onGestureDoubleClickListener = onGestureDoubleClickListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureLeftDownListener(IPLVVideoViewListenerEvent.OnGestureLeftDownListener onGestureLeftDownListener) {
        this.onGestureLeftDownListener = onGestureLeftDownListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureLeftUpListener(IPLVVideoViewListenerEvent.OnGestureLeftUpListener onGestureLeftUpListener) {
        this.onGestureLeftUpListener = onGestureLeftUpListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureRightDownListener(IPLVVideoViewListenerEvent.OnGestureRightDownListener onGestureRightDownListener) {
        this.onGestureRightDownListener = onGestureRightDownListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureRightUpListener(IPLVVideoViewListenerEvent.OnGestureRightUpListener onGestureRightUpListener) {
        this.onGestureRightUpListener = onGestureRightUpListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureSwipeLeftListener(IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener onGestureSwipeLeftListener) {
        this.onGestureSwipeLeftListener = onGestureSwipeLeftListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGestureSwipeRightListener(IPLVVideoViewListenerEvent.OnGestureSwipeRightListener onGestureSwipeRightListener) {
        this.onGestureSwipeRightListener = onGestureSwipeRightListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetLogoListener(IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener) {
        this.onGetLogoListener = onGetLogoListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener) {
        this.onGetMarqueeVoListener = onGetMarqueeVoListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener) {
        this.onGetWatermarkVoListener = onGetWatermarkVoListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnInfoListener(IPLVVideoViewListenerEvent.OnInfoListener onInfoListener) {
        this.onInfoListener = onInfoListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnNetworkStateListener(IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener) {
        this.onNetworkStateListener = onNetworkStateListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener) {
        this.onPPTShowListener = onPPTShowListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnPreparedListener(IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener) {
        this.onPreparedListener = onPreparedListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnSEIRefreshListener(IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener) {
        this.onSEIRefreshListener = onSEIRefreshListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnSeekCompleteListener(IPLVVideoViewListenerEvent.OnSeekCompleteListener onSeekCompleteListener) {
        this.onSeekCompleteListener = onSeekCompleteListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoLoadSlowListener(IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener) {
        this.onVideoLoadSlowListener = onVideoLoadSlowListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoPauseListener(IPLVVideoViewListenerEvent.OnVideoPauseListener onVideoPauseListener) {
        this.onVideoPauseListener = onVideoPauseListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoPlayListener(IPLVVideoViewListenerEvent.OnVideoPlayListener onVideoPlayListener) {
        this.onVideoPlayListener = onVideoPlayListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoSizeChangedListener(IPLVVideoViewListenerEvent.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.onVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder
    public void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart) {
        this.onVideoViewRestart = onVideoViewRestart;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewNotifyer
    public boolean notifyOnError(PLVPlayError pLVPlayError) {
        PLVCommonLog.d(TAG, "notifyOnError");
        IPLVVideoViewListenerEvent.OnErrorListener onErrorListener = this.onErrorListener;
        if (onErrorListener == null) {
            return true;
        }
        onErrorListener.onError(pLVPlayError);
        return true;
    }

    @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerNotifyer
    public void notifyGestureAction(int i2, boolean z2, boolean z3, int i3) {
        IPLVVideoViewListenerEvent.OnGestureSwipeRightListener onGestureSwipeRightListener;
        if (i2 != 4) {
            if (i2 == 5 && (onGestureSwipeRightListener = this.onGestureSwipeRightListener) != null) {
                onGestureSwipeRightListener.callback(z2, z3, i3);
                return;
            }
            return;
        }
        IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener onGestureSwipeLeftListener = this.onGestureSwipeLeftListener;
        if (onGestureSwipeLeftListener != null) {
            onGestureSwipeLeftListener.callback(z2, z3, i3);
        }
    }
}
