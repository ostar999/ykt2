package com.plv.livescenes.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.model.log.PLVElogVersionTag;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import java.io.File;

/* loaded from: classes4.dex */
public class PLVStatisticsBaseLive extends PLVStatisticsBase {
    public PLVStatisticsBaseLive(PLVLogFileBase pLVLogFileBase) {
        super(pLVLogFileBase);
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public void addLogInfo() {
        super.addLogInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(PolyvLiveSDKClient.getInstance().getPolyvLiveAndroidVersion());
        sb.append(File.separator);
        sb.append(PLVElogVersionTag.VersionInfo.PLATFORMA_ANDROID.getCode());
        PLVElogVersionTag.VersionInfo versionInfo = PLVElogVersionTag.VersionInfo.DEVICE_TYPE_PHONE;
        sb.append(versionInfo.getCode());
        PLVElogVersionTag.VersionInfo versionInfo2 = PLVElogVersionTag.VersionInfo.FRAMEWORK_ORIGIN;
        sb.append(versionInfo2.getCode());
        PLVElogVersionTag.VersionInfo versionInfo3 = PLVElogVersionTag.VersionInfo.PROJECT_LIVE_SCENE;
        sb.append(versionInfo3.getCode());
        setVersion2(sb.toString());
        setAppId(PolyvLiveSDKClient.getInstance().getAppId());
        setUserId2(PolyvLiveSDKClient.getInstance().getUserId());
        setViewerId(PolyvLiveSDKClient.getInstance().getViewerId());
        setChannelId2(PolyvLiveSDKClient.getInstance().getChannelId());
        setProject(versionInfo3.getContent());
        setFramework(versionInfo2.getContent());
        setDeviceType(versionInfo.getContent());
        setELogSendType(PLVELogSendType.LIVE_ELOG);
    }

    public PLVStatisticsBaseLive(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }
}
