package com.plv.livescenes.log;

import com.plv.business.api.common.player.listener.IPLVStaticLogsListener;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import com.plv.livescenes.playback.log.PLVPlaybackVodElog;
import retrofit2.Call;

/* loaded from: classes4.dex */
public class PLVELogRequestManager implements IPLVStaticLogsListener {
    private static volatile PLVELogRequestManager instance;

    /* renamed from: com.plv.livescenes.log.PLVELogRequestManager$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$plv$foundationsdk$model$log$PLVELogSendType;

        static {
            int[] iArr = new int[PLVELogSendType.values().length];
            $SwitchMap$com$plv$foundationsdk$model$log$PLVELogSendType = iArr;
            try {
                iArr[PLVELogSendType.VOD_ELOG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$plv$foundationsdk$model$log$PLVELogSendType[PLVELogSendType.LIVE_ELOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private PLVELogRequestManager() {
    }

    public static IPLVStaticLogsListener getInstance() {
        if (instance == null) {
            synchronized (PLVELogRequestManager.class) {
                if (instance == null) {
                    instance = new PLVELogRequestManager();
                }
            }
        }
        return instance;
    }

    @Override // com.plv.foundationsdk.log.elog.IPLVStaticELogs
    public Call<PLVBaseResponseBean> sendLogs(PLVELogSendType pLVELogSendType, String str) {
        return AnonymousClass1.$SwitchMap$com$plv$foundationsdk$model$log$PLVELogSendType[pLVELogSendType.ordinal()] != 1 ? PLVLiveElog.getInstance().sendLogs(pLVELogSendType, str) : PLVPlaybackVodElog.getInstance().sendLogs(pLVELogSendType, str);
    }
}
