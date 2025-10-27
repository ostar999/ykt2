package com.plv.livescenes.playback.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.business.api.common.player.listener.IPLVStaticLogsListener;
import com.plv.business.model.log.PLVElogEntity;
import com.plv.business.net.PLVCommonApiManager;
import com.plv.foundationsdk.config.PLVVideoViewConstant;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import com.plv.foundationsdk.utils.PLVUtils;
import retrofit2.Call;

/* loaded from: classes5.dex */
public class PLVPlaybackVodElog implements IPLVStaticLogsListener {
    private static volatile PLVPlaybackVodElog instance;

    private PLVPlaybackVodElog() {
    }

    private PLVElogEntity buildLogStringsInfo(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return new PLVElogEntity(String.valueOf(jCurrentTimeMillis), new String[]{PLVUtils.MD5("polyv_sdk_api_innorltype2timestamp" + jCurrentTimeMillis + "userId" + PolyvLiveSDKClient.getInstance().getUserId() + PLVVideoViewConstant.PREFIX).toUpperCase()}, str, 2);
    }

    public static PLVPlaybackVodElog getInstance() {
        if (instance == null) {
            synchronized (PLVPlaybackVodElog.class) {
                if (instance == null) {
                    instance = new PLVPlaybackVodElog();
                }
            }
        }
        return instance;
    }

    @Override // com.plv.foundationsdk.log.elog.IPLVStaticELogs
    public Call<PLVBaseResponseBean> sendLogs(PLVELogSendType pLVELogSendType, String str) {
        PLVElogEntity pLVElogEntityBuildLogStringsInfo = buildLogStringsInfo(str);
        return PLVCommonApiManager.getElogApi().sendVodElog(PolyvLiveSDKClient.getInstance().getUserId(), pLVElogEntityBuildLogStringsInfo.getLog(), pLVElogEntityBuildLogStringsInfo.getLtype(), pLVElogEntityBuildLogStringsInfo.getPtime(), pLVElogEntityBuildLogStringsInfo.getSign()[0]);
    }
}
