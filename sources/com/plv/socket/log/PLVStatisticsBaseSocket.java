package com.plv.socket.log;

import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.model.log.PLVElogVersionTag;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.socket.socketio.PLVSocketIOClient;
import java.io.File;

/* loaded from: classes5.dex */
public class PLVStatisticsBaseSocket extends PLVStatisticsBase {
    public PLVStatisticsBaseSocket(PLVLogFileBase pLVLogFileBase) {
        super(pLVLogFileBase);
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public void addLogInfo() {
        super.addLogInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(PLVSocketIOClient.getInstance().getSdkVersion());
        sb.append(File.separator);
        sb.append(PLVElogVersionTag.VersionInfo.PLATFORMA_ANDROID.getCode());
        PLVElogVersionTag.VersionInfo versionInfo = PLVElogVersionTag.VersionInfo.DEVICE_TYPE_PHONE;
        sb.append(versionInfo.getCode());
        PLVElogVersionTag.VersionInfo versionInfo2 = PLVElogVersionTag.VersionInfo.FRAMEWORK_ORIGIN;
        sb.append(versionInfo2.getCode());
        PLVElogVersionTag.VersionInfo versionInfo3 = PLVElogVersionTag.VersionInfo.PROJECT_LIVE_SCENE;
        sb.append(versionInfo3.getCode());
        setVersion2(sb.toString());
        setAppId(PLVSocketIOClient.getInstance().getAccountAppId());
        setUserId2(PLVSocketIOClient.getInstance().getAccountUserId());
        setViewerId(PLVSocketIOClient.getInstance().getSocketUserId());
        setChannelId2(PLVSocketIOClient.getInstance().getChannelId());
        setProject(versionInfo3.getContent());
        setFramework(versionInfo2.getContent());
        setDeviceType(versionInfo.getContent());
        setELogSendType(PLVELogSendType.LIVE_ELOG);
    }

    public PLVStatisticsBaseSocket(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }
}
