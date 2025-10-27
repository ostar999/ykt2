package com.plv.foundationsdk.log.elog.logcode.ppt;

/* loaded from: classes4.dex */
public class PLVErrorCodePPTRequest extends PLVErrorCodePPTBase {
    private static final String PPT_REQUEST_EVENT = "PPTRequest";
    private static final int PPT_REQUEST_TAG = 1;

    public interface PLVPPTRequestCode {
        public static final int REQUEST_DOCUMENT_CONVERT_STATUS_ERROR = 8;
        public static final int REQUEST_DOCUMENT_CONVERT_STATUS_PARAMS_FAILED = 7;
        public static final int REQUEST_DOCUMENT_CONVERT_STATUS_PARSE_RROR = 9;
        public static final int REQUEST_DOCUMENT_DELETE_ERROR = 5;
        public static final int REQUEST_DOCUMENT_DELETE_PARAMS_FAILED = 4;
        public static final int REQUEST_DOCUMENT_DELETE_PARASE_ERROR = 6;
        public static final int REQUEST_DOCUMENT_LIST_ERROR = 2;
        public static final int REQUEST_DOCUMENT_LIST_PARAMS_FAILED = 1;
        public static final int REQUEST_DOCUMENT_LIST_PARASE_ERROR = 3;
    }

    public PLVErrorCodePPTRequest(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return PPT_REQUEST_EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 1;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 3:
                return "请求文档列表失败 #{" + getCode() + "}";
            case 4:
            case 5:
            case 6:
                return "删除文档失败, #{" + getCode() + "}";
            case 7:
            case 8:
            case 9:
                return "文档转码失败, #{" + getCode() + "}";
            default:
                return "";
        }
    }
}
