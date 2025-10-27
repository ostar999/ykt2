package com.easefun.polyv.businesssdk.model.video;

import com.plv.business.model.video.PLVLiveChannelVO;
import com.plv.foundationsdk.utils.PLVReflectionUtil;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvLiveChannelVO extends PLVLiveChannelVO {
    public static PolyvLiveChannelVO mapFromNewType(PLVLiveChannelVO pLVLiveChannelVO) {
        if (pLVLiveChannelVO == null) {
            return null;
        }
        return (PolyvLiveChannelVO) PLVReflectionUtil.copyField(pLVLiveChannelVO, new PolyvLiveChannelVO());
    }
}
