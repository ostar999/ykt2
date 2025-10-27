package com.plv.livescenes.log.chat;

import com.easefun.polyv.livescenes.log.PolyvStatisticsBaseLive;
import com.plv.foundationsdk.model.log.PLVLogFileBase;

/* loaded from: classes4.dex */
public class PLVChatroomELog extends PolyvStatisticsBaseLive {

    public interface Event {
        public static final String CLOSE_CHATROOM_FAIL = "closeChatroomFail";
        public static final String GET_KICKUSERS_FAIL = "getKickUsersFail";
        public static final String GET_LISTUSERS_FAIL = "getListUsersFail";
        public static final String LOAD_HISTORY_FAIL = "loadHistoryFail";
    }

    public PLVChatroomELog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
        this.module = "chat";
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }
}
