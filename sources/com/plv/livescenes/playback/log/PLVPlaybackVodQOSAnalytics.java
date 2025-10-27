package com.plv.livescenes.playback.log;

import com.easefun.polyv.businesssdk.vodplayer.PolyvVodSDKClient;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.qos.PLVVodQOSAnalytics;
import com.plv.foundationsdk.model.log.PLVProjectInfo;

/* loaded from: classes5.dex */
public class PLVPlaybackVodQOSAnalytics extends PLVVodQOSAnalytics {
    private static PLVPlaybackVodQOSAnalytics instance;

    public static PLVPlaybackVodQOSAnalytics getInstance() {
        if (instance == null) {
            instance = new PLVPlaybackVodQOSAnalytics();
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
