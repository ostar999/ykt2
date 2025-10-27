package com.easefun.polyv.businesssdk.api.common.player.listener;

import com.easefun.polyv.businesssdk.api.common.player.PolyvPlayError;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveMarqueeVO;
import com.plv.business.api.common.player.PLVPlayError;
import com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent;
import com.plv.business.model.video.PLVLiveMarqueeVO;

@Deprecated
/* loaded from: classes3.dex */
public interface IPolyvVideoViewListenerEvent extends IPLVVideoViewListenerEvent {

    @Deprecated
    public static abstract class OnErrorListener implements IPLVVideoViewListenerEvent.OnErrorListener {
        public abstract void onError(PolyvPlayError polyvPlayError);

        @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnErrorListener
        public final void onError(PLVPlayError pLVPlayError) {
            onError(new PolyvPlayError(pLVPlayError.playPath, pLVPlayError.errorCode, pLVPlayError.errorDescribe, pLVPlayError.playStage));
        }
    }

    @Deprecated
    public static abstract class OnGetMarqueeVoListener implements IPLVVideoViewListenerEvent.OnGetMarqueeVoListener {
        public abstract void onGetMarqueeVo(PolyvLiveMarqueeVO polyvLiveMarqueeVO);

        @Override // com.plv.business.api.common.player.listener.IPLVVideoViewListenerEvent.OnGetMarqueeVoListener
        public final void onGetMarqueeVo(PLVLiveMarqueeVO pLVLiveMarqueeVO) {
            PolyvLiveMarqueeVO polyvLiveMarqueeVO = new PolyvLiveMarqueeVO(pLVLiveMarqueeVO.marquee, pLVLiveMarqueeVO.marqueeType, pLVLiveMarqueeVO.marqueeFontColor, pLVLiveMarqueeVO.marqueeFontSize, pLVLiveMarqueeVO.marqueeOpacity, pLVLiveMarqueeVO.marqueeSetting, pLVLiveMarqueeVO.marqueeAutoZoomEnabled);
            polyvLiveMarqueeVO.setUserId(pLVLiveMarqueeVO.getUserId());
            polyvLiveMarqueeVO.setChannelId(pLVLiveMarqueeVO.getChannelId());
            onGetMarqueeVo(polyvLiveMarqueeVO);
        }
    }
}
