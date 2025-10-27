package com.plv.livescenes.playback.video.api;

import com.plv.business.api.common.player.listener.IPLVVideoViewListenerBinder;
import com.plv.livescenes.playback.video.api.IPLVPlaybackListenerEvent;

/* loaded from: classes5.dex */
public interface IPLVPlaybackVideoViewPlayBinder extends IPLVVideoViewListenerBinder {
    void setOnPlaybackDataReadyListener(IPLVPlaybackListenerEvent.OnPlaybackDataReadyListener onPlaybackDataReadyListener);

    void setOnRetryListener(IPLVPlaybackListenerEvent.OnRetryListener onRetryListener);
}
