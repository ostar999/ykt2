package com.easefun.polyv.livescenes.log;

import com.plv.business.api.common.player.listener.IPLVStaticLogsListener;

@Deprecated
/* loaded from: classes3.dex */
public class PLVELogRequestManager {
    private PLVELogRequestManager() {
    }

    public static IPLVStaticLogsListener getInstance() {
        return com.plv.livescenes.log.PLVELogRequestManager.getInstance();
    }
}
