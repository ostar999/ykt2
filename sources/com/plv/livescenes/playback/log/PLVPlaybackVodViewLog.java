package com.plv.livescenes.playback.log;

import com.easefun.polyv.businesssdk.vodplayer.PolyvVodSDKClient;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.viewlog.PLVVodViewLog;
import com.plv.foundationsdk.model.log.PLVProjectInfo;

/* loaded from: classes5.dex */
public class PLVPlaybackVodViewLog extends PLVVodViewLog {
    private static PLVPlaybackVodViewLog instance;

    public static PLVPlaybackVodViewLog getInstance() {
        if (instance == null) {
            instance = new PLVPlaybackVodViewLog();
        }
        return instance;
    }

    @Override // com.plv.foundationsdk.log.PLVAnalyticsBase
    public PLVProjectInfo buildProjectInfo() {
        return new PLVProjectInfo(PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidVersion(), PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdkName(), PolyvLiveSDKClient.getInstance().getViewerId(), PolyvVodSDKClient.getInstance().getUserId());
    }

    @Override // com.plv.foundationsdk.log.PLVAnalyticsBase
    public String projectName() {
        return PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdkName();
    }
}
