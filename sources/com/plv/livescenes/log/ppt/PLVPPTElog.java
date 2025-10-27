package com.plv.livescenes.log.ppt;

import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.PLVStatisticsBaseLive;

/* loaded from: classes4.dex */
public class PLVPPTElog extends PLVStatisticsBaseLive {

    public interface PPTEvent {
        public static final String DOCUMENT_DETAILITEM_SELECT = "documentDetailitemSelect";
        public static final String DOCUMENT_DETAILLIST_REFRESH = "documentDetaillistRefresh";
        public static final String DOCUMENT_LISTLITEM_SELECT = "documentListlitemSelect";
        public static final String DOCUMENT_LIST_REQUEST = "documentListRequest";
        public static final String DOCUMENT_OPEN = "documentOpen";
        public static final String DOCUMENT_TAB_SELECT = "documentTabSelect";
        public static final String DOCUMENT_WHITEBOARD_SELECT = "documentWhiteboardSelect";
        public static final String PPT_LOAD_FAILED = "pptLoadFailed";
        public static final String PPT_LOAD_FINISH = "pptLoadFinish";
        public static final String PPT_LOAD_START = "pptLoadStart";
        public static final String PPT_RECEIVE_WEB_MESSAGE = "pptReceiveWebMessage";
        public static final String PPT_SEND_WEB_MESSAGE = "pptSendWebMessage";
        public static final String PPT_WEB_INIT = "pptWebInit";
    }

    public PLVPPTElog(PLVLogFileBase pLVLogFileBase) {
        super(pLVLogFileBase);
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }

    public PLVPPTElog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }
}
