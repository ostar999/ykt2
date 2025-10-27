package com.plv.livescenes.playback.video;

import com.plv.business.api.common.player.PLVVideoViewListener;
import com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent;
import com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayBinder;
import com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayNotifyer;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;

/* loaded from: classes5.dex */
public class PLVPlaybackVideoViewListener extends PLVVideoViewListener implements IPLVPlaybackVideoViewPlayBinder, IPLVPlaybackVideoViewPlayNotifyer {
    private IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener onPlaybackDataReadyListener;
    private IPLVPlaybackListenerEvent.OnRetryListener onRetryListener;

    @Override // com.plv.business.api.common.player.PLVVideoViewListener
    public void clearAllListener() {
        super.clearAllListener();
        this.onRetryListener = null;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayNotifyer
    public void notifyOnPlaybackDataReady(PLVPlaybackDataVO pLVPlaybackDataVO) {
        IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener onPlaybackDataReadyListener = this.onPlaybackDataReadyListener;
        if (onPlaybackDataReadyListener != null) {
            onPlaybackDataReadyListener.onPlaybackDataReady(pLVPlaybackDataVO);
        }
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayNotifyer
    public boolean notifyRetryFailed() {
        IPLVPlaybackListenerEvent.OnRetryListener onRetryListener = this.onRetryListener;
        if (onRetryListener != null) {
            return onRetryListener.onRetryFailed();
        }
        return false;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayBinder
    public void setOnPlaybackDataReadyListener(IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener onPlaybackDataReadyListener) {
        this.onPlaybackDataReadyListener = onPlaybackDataReadyListener;
    }

    @Override // com.plv.livescenes.playback.video.api.IPLVPlaybackVideoViewPlayBinder
    public void setOnRetryListener(IPLVPlaybackListenerEvent.OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }
}
