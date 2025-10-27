package com.plv.business.api.common.player.listener;

import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;

/* loaded from: classes4.dex */
public interface IPLVVideoViewListenerBinder {
    void setOnBufferingUpdateListener(IPLVVideoViewListenerEvent.OnBufferingUpdateListener onBufferingUpdateListener);

    void setOnCompletionListener(IPLVVideoViewListenerEvent.OnCompletionListener onCompletionListener);

    void setOnCoverImageOutListener(IPLVVideoViewListenerEvent.OnCoverImageOutListener onCoverImageOutListener);

    void setOnDanmuServerOpenListener(IPLVVideoViewListenerEvent.OnDanmuServerOpenListener onDanmuServerOpenListener);

    void setOnErrorListener(IPLVVideoViewListenerEvent.OnErrorListener onErrorListener);

    void setOnGestureClickListener(IPLVVideoViewListenerEvent.OnGestureClickListener onGestureClickListener);

    void setOnGestureDoubleClickListener(IPLVVideoViewListenerEvent.OnGestureDoubleClickListener onGestureDoubleClickListener);

    void setOnGestureLeftDownListener(IPLVVideoViewListenerEvent.OnGestureLeftDownListener onGestureLeftDownListener);

    void setOnGestureLeftUpListener(IPLVVideoViewListenerEvent.OnGestureLeftUpListener onGestureLeftUpListener);

    void setOnGestureRightDownListener(IPLVVideoViewListenerEvent.OnGestureRightDownListener onGestureRightDownListener);

    void setOnGestureRightUpListener(IPLVVideoViewListenerEvent.OnGestureRightUpListener onGestureRightUpListener);

    void setOnGestureSwipeLeftListener(IPLVVideoViewListenerEvent.OnGestureSwipeLeftListener onGestureSwipeLeftListener);

    void setOnGestureSwipeRightListener(IPLVVideoViewListenerEvent.OnGestureSwipeRightListener onGestureSwipeRightListener);

    void setOnGetLogoListener(IPLVVideoViewListenerEvent.OnGetLogoListener onGetLogoListener);

    void setOnGetMarqueeVoListener(IPLVVideoViewListenerEvent.OnGetMarqueeVoListener onGetMarqueeVoListener);

    void setOnGetWatermarkVOListener(IPLVVideoViewListenerEvent.OnGetWatermarkVoListener onGetWatermarkVoListener);

    void setOnInfoListener(IPLVVideoViewListenerEvent.OnInfoListener onInfoListener);

    void setOnNetworkStateListener(IPLVVideoViewListenerEvent.OnNetworkStateListener onNetworkStateListener);

    void setOnPPTShowListener(IPLVVideoViewListenerEvent.OnPPTShowListener onPPTShowListener);

    void setOnPreparedListener(IPLVVideoViewListenerEvent.OnPreparedListener onPreparedListener);

    void setOnSEIRefreshListener(IPLVVideoViewListenerEvent.OnSEIRefreshListener onSEIRefreshListener);

    void setOnSeekCompleteListener(IPLVVideoViewListenerEvent.OnSeekCompleteListener onSeekCompleteListener);

    void setOnVideoLoadSlowListener(IPLVVideoViewListenerEvent.OnVideoLoadSlowListener onVideoLoadSlowListener);

    void setOnVideoPauseListener(IPLVVideoViewListenerEvent.OnVideoPauseListener onVideoPauseListener);

    void setOnVideoPlayListener(IPLVVideoViewListenerEvent.OnVideoPlayListener onVideoPlayListener);

    void setOnVideoSizeChangedListener(IPLVVideoViewListenerEvent.OnVideoSizeChangedListener onVideoSizeChangedListener);

    void setOnVideoViewRestartListener(IPLVVideoViewListenerEvent.OnVideoViewRestart onVideoViewRestart);
}
