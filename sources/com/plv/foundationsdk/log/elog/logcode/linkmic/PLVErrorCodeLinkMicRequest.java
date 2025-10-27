package com.plv.foundationsdk.log.elog.logcode.linkmic;

/* loaded from: classes4.dex */
public class PLVErrorCodeLinkMicRequest extends PLVErrorCodeLinkMicBase {

    public interface SecondTag {
        public static final int GET_LINK_MIC_LIST_DATA_PARSE_ERROR = 15;
        public static final int GET_LINK_MIC_LIST_PARAM_ERROR = 13;
        public static final int GET_LINK_MIC_LIST_RESPONSE_CODE_ERROR = 14;
        public static final int GET_SESSION_ID_DATA_PARSE_ERROR = 9;
        public static final int GET_SESSION_ID_JSON_RESPONSE_CODE_ERROR = 8;
        public static final int GET_SESSION_ID_PARAM_ERROR = 7;
        public static final int GET_TOKEN_DATA_PARSE_ERROR = 6;
        public static final int GET_TOKEN_JSON_RESPONSE_CODE_ERROR = 5;
        public static final int GET_TOKEN_PARAM_ERROR = 4;
        public static final int MIX_ACTION_DATA_PARSE_ERROR = 21;
        public static final int MIX_ACTION_PARAM_ERROR = 19;
        public static final int MIX_ACTION_RESPONSE_CODE_ERROR = 20;
        public static final int NOTIFY_LIVE_END_DATA_PARSE_ERROR = 18;
        public static final int NOTIFY_LIVE_END_PARAM_ERROR = 16;
        public static final int NOTIFY_LIVE_END_RESPONSE_CODE_ERROR = 17;
        public static final int NOTIFY_STREAM_DATA_PARSE_ERROR = 3;
        public static final int NOTIFY_STREAM_JSON_RESPONSE_CODE_ERROR = 2;
        public static final int NOTIFY_STREAM_PARAM_ERROR = 1;
        public static final int TEACHER_LOGIN_DATA_PARSE_ERROR = 12;
        public static final int TEACHER_LOGIN_JSON_RESPONSE_CODE_ERROR = 11;
        public static final int TEACHER_LOGIN_PARAM_ERROR = 10;
    }

    public PLVErrorCodeLinkMicRequest(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return "linkMicRequest";
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
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            default:
                switch (i2) {
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                        break;
                    default:
                        return "";
                }
        }
        return "推流失败#{" + getCode() + "}";
    }
}
