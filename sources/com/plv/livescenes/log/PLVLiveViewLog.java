package com.plv.livescenes.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.model.log.PLVProjectInfo;

/* loaded from: classes4.dex */
public class PLVLiveViewLog extends com.plv.foundationsdk.log.viewlog.PLVLiveViewLog {
    public static final String VIEW_LOG_PTYPE_NORMAL = "0";
    public static final String VIEW_LOG_PTYPE_PRTC = "2";
    private static PLVLiveViewLog instance;

    public static PLVLiveViewLog getInstance() {
        if (instance == null) {
            instance = new PLVLiveViewLog();
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
