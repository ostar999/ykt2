package com.easefun.polyv.livecommon.module.config;

import com.plv.livescenes.config.PLVLiveChannelType;

/* loaded from: classes3.dex */
public enum PLVLiveScene {
    CLOUDCLASS,
    ECOMMERCE;

    public static boolean isCloudClassSceneSupportType(PLVLiveChannelType channelType) {
        return channelType == PLVLiveChannelType.PPT || channelType == PLVLiveChannelType.ALONE;
    }

    public static boolean isLiveEcommerceSceneSupportType(PLVLiveChannelType channelType) {
        return channelType == PLVLiveChannelType.ALONE;
    }
}
