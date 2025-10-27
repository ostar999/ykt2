package com.plv.foundationsdk.log.elog.logcode.socket;

/* loaded from: classes4.dex */
public class PLVErrorCodeSocketParam extends PLVErrorCodeSocketBase {
    private static final String EVENT = "socketParamError";
    private static final int FIRST_TAG = 0;

    public interface SecondCode {
        public static final int APPID_ERROR = 8;
        public static final int APPSECRET_ERROR = 9;
        public static final int AVATAR_ERROR = 5;
        public static final int CHANNELID_ERROR = 2;
        public static final int LOGININFO_ERROR = 7;
        public static final int LOGINVO_ERROR = 1;
        public static final int NICKNAME_ERROR = 4;
        public static final int USERID_ERROR = 3;
        public static final int USERTYPE_ERROR = 6;
    }

    public PLVErrorCodeSocketParam(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 0;
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
            case 8:
            case 9:
                return "参数非法";
            case 7:
                return "构建登录信息失败";
            default:
                return "unknown";
        }
    }
}
