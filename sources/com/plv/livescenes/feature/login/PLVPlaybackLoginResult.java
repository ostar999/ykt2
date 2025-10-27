package com.plv.livescenes.feature.login;

import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.plv.livescenes.config.PLVLiveChannelType;

/* loaded from: classes4.dex */
public class PLVPlaybackLoginResult {
    private PLVLiveChannelType channelType;

    public PLVPlaybackLoginResult(PLVLiveChannelType pLVLiveChannelType) {
        this.channelType = pLVLiveChannelType;
    }

    public PolyvLiveChannelType getChannelType() {
        return PolyvLiveChannelType.mapFromNewType(getChannelTypeNew());
    }

    public PLVLiveChannelType getChannelTypeNew() {
        return this.channelType;
    }
}
