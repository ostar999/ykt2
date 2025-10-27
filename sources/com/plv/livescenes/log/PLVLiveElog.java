package com.plv.livescenes.log;

import android.text.TextUtils;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.business.api.common.player.listener.IPLVStaticLogsListener;
import com.plv.foundationsdk.config.PLVVideoViewConstant;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.hiclass.PLVHiClassGlobalConfig;
import com.plv.livescenes.model.PLVLiveElogVO;
import com.plv.livescenes.net.PLVApiManager;
import retrofit2.Call;

/* loaded from: classes4.dex */
public class PLVLiveElog implements IPLVStaticLogsListener {
    private static final int CLASS_CLOUD_TYPE = 52;
    private static final int HI_CLASS_TYPE = 72;
    private static volatile PLVLiveElog instance;

    private PLVLiveElog() {
    }

    private PLVLiveElogVO buildLogStringsInfo(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i2 = !TextUtils.isEmpty(PLVHiClassGlobalConfig.getToken()) ? 72 : 52;
        return new PLVLiveElogVO(new String[]{PLVUtils.MD5("polyv_sdk_api_innorchannelId" + PolyvLiveSDKClient.getInstance().getChannelId() + "ltype" + i2 + "timestamp" + jCurrentTimeMillis + PLVVideoViewConstant.PREFIX).toUpperCase()}, str, i2, PolyvLiveSDKClient.getInstance().getAppId(), jCurrentTimeMillis + "");
    }

    public static PLVLiveElog getInstance() {
        if (instance == null) {
            synchronized (PLVLiveElog.class) {
                if (instance == null) {
                    instance = new PLVLiveElog();
                }
            }
        }
        return instance;
    }

    @Override // com.plv.foundationsdk.log.elog.IPLVStaticELogs
    public Call<PLVBaseResponseBean> sendLogs(PLVELogSendType pLVELogSendType, String str) {
        PLVLiveElogVO pLVLiveElogVOBuildLogStringsInfo = buildLogStringsInfo(str);
        return PLVApiManager.getElogApi().sendLiveElog(PolyvLiveSDKClient.getInstance().getChannelId(), pLVLiveElogVOBuildLogStringsInfo.getLog(), pLVLiveElogVOBuildLogStringsInfo.getLtype(), pLVLiveElogVOBuildLogStringsInfo.getTimestamp(), pLVLiveElogVOBuildLogStringsInfo.getSign()[0]);
    }
}
