package com.plv.livescenes.log.upload;

import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.livescenes.log.PLVStatisticsBaseLive;

/* loaded from: classes4.dex */
public class PLVDocumentUploadELog extends PLVStatisticsBaseLive {
    private static final String DOCUMENT_UPLOAD_MODULE = "documentUpload";

    public interface DocumentUploadEvent {
        public static final String GET_TOKEN = "getToken";
        public static final String GET_TOKEN_SUCCESS = "getTokenSuccess";
        public static final String REFRESH_STS_TOKEN = "refreshSTSToken";
        public static final String SET_STS_TOKEN = "setSTSToken";
        public static final String UPLOAD_BEGIN = "uploadBegin";
        public static final String UPLOAD_RECOVER = "uploadRecover";
        public static final String UPLOAD_RECOVER_SUCCESS = "uploadRecoverSuccess";
        public static final String UPLOAD_RETRY = "uploadRetry";
        public static final String UPLOAD_RETRY_SUCCESS = "uploadRetrySuccess";
        public static final String UPLOAD_SUCCESS = "uploadSuccess";
    }

    public PLVDocumentUploadELog(PLVLogFileBase pLVLogFileBase, String str) {
        super(pLVLogFileBase, str);
    }

    @Override // com.plv.foundationsdk.model.log.PLVStatisticsBase
    public boolean isNeedBatches() {
        return true;
    }
}
