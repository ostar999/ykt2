package com.plv.livescenes.log.socket;

import com.plv.foundationsdk.log.elog.logcode.socket.PLVErrorCodeSocketBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.PLVStatisticsBaseLive;

/* loaded from: classes4.dex */
public class PLVSocketELog extends PLVStatisticsBaseLive {

    public interface Event {
        public static final String CONNECT = "connect";
        public static final String LOGINING = "logining";
        public static final String LOGINSUCCESS = "loginSuccess";
        public static final String RECONNECTING = "reconnecting";
        public static final String RECONNECTSUCCESS = "reconnectSuccess";
        public static final String RELOGIN = "relogin";
    }

    public PLVSocketELog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
        this.module = PLVErrorCodeSocketBase.MODULE_NAME;
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }
}
