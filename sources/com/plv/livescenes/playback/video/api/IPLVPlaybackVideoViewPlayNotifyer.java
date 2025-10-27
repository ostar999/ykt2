package com.plv.livescenes.playback.video.api;

import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;

/* loaded from: classes5.dex */
public interface IPLVPlaybackVideoViewPlayNotifyer {
    void notifyOnPlaybackDataReady(PLVPlaybackDataVO pLVPlaybackDataVO);

    boolean notifyRetryFailed();
}
