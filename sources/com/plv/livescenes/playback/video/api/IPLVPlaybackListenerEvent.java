package com.plv.livescenes.playback.video.api;

import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;

/* loaded from: classes5.dex */
public interface IPLVPlaybackListenerEvent extends IPLVVideoViewListenerEvent {

    public interface OnPlaybackDataReadyListener {
        void onPlaybackDataReady(PLVPlaybackDataVO pLVPlaybackDataVO);
    }

    public interface OnRetryListener {
        boolean onRetryFailed();
    }
}
