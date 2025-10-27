package com.plv.livescenes.log;

import com.easefun.polyv.businesssdk.vodplayer.PolyvVodSDKClient;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.business.model.video.PLVLogVideoLableVO;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.model.log.PLVLogFileBase;

/* loaded from: classes4.dex */
public class PLVStatisticsPlayback extends PLVStatisticsBaseLive {
    public PLVStatisticsPlayback(PLVLogFileBase pLVLogFileBase) {
        super(pLVLogFileBase);
    }

    @Override // com.plv.livescenes.log.PLVStatisticsBaseLive, com.plv.foundationsdk.model.log.PLVStatisticsBase
    public void addLogInfo() {
        super.addLogInfo();
        setViewerId(PolyvLiveSDKClient.getInstance().getViewerId());
        setUserId2(PolyvVodSDKClient.getInstance().getUserId());
        PLVLogVideoLableVO polyvLogVideoLable = PolyvVodSDKClient.getInstance().getPolyvLogVideoLable();
        if (polyvLogVideoLable != null) {
            setVid(polyvLogVideoLable.getVideoId());
            setPlayId(polyvLogVideoLable.getPlayId());
        }
        setELogSendType(PLVELogSendType.VOD_ELOG);
    }

    public PLVStatisticsPlayback(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }
}
