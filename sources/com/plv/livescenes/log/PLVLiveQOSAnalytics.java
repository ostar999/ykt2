package com.plv.livescenes.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.model.log.PLVProjectInfo;

/* loaded from: classes4.dex */
public class PLVLiveQOSAnalytics extends com.plv.foundationsdk.log.qos.PLVLiveQOSAnalytics {
    private static PLVLiveQOSAnalytics instance;

    public static PLVLiveQOSAnalytics getInstance() {
        if (instance == null) {
            instance = new PLVLiveQOSAnalytics();
        }
        return instance;
    }

    @Override // com.plv.foundationsdk.log.PLVAnalyticsBase
    public PLVProjectInfo buildProjectInfo() {
        return new PLVProjectInfo(PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidVersion(), PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdkName(), PolyvLiveSDKClient.getInstance().getViewerId(), PolyvLiveSDKClient.getInstance().getUserId());
    }

    @Override // com.plv.foundationsdk.log.PLVAnalyticsBase
    public String projectName() {
        return PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidSdkName();
    }
}
