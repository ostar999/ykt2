package com.plv.livescenes.feature.login;

import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.config.PLVLiveStatusType;

/* loaded from: classes4.dex */
public class PLVLiveLoginResult {
    private PLVLiveChannelType channelType;
    private PLVLiveStatusType liveStatus;

    public PLVLiveLoginResult(PLVLiveChannelType pLVLiveChannelType, PLVLiveStatusType pLVLiveStatusType) {
        this.channelType = pLVLiveChannelType;
        this.liveStatus = pLVLiveStatusType;
    }

    public PolyvLiveChannelType getChannelType() {
        return PolyvLiveChannelType.mapFromNewType(getChannelTypeNew());
    }

    public PLVLiveChannelType getChannelTypeNew() {
        return this.channelType;
    }

    public PLVLiveStatusType getLiveStatus() {
        return this.liveStatus;
    }
}
