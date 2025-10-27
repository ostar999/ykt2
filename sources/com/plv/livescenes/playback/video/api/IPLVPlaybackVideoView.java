package com.plv.livescenes.playback.video.api;

import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayType;
import com.plv.livescenes.playback.vo.PLVPlaybackDataVO;

/* loaded from: classes5.dex */
public interface IPLVPlaybackVideoView {
    void enableRetry(boolean z2);

    PolyvPlayType getPlayType();

    PLVPlaybackDataVO getPlaybackData();

    void setMaxRetryCount(int i2);
}
