package com.plv.foundationsdk.log.elog.logcode.socket;

/* loaded from: classes4.dex */
public class PLVErrorCodeSocketLogin extends PLVErrorCodeSocketBase {
    private static final String EVENT = "socketLoginError";
    private static final int FIRST_TAG = 6;

    public interface SecondCode {
        public static final int LOGINACK_KICK = 6;
        public static final int LOGINACK_NICK_AVATAR = 5;
        public static final int LOGINACK_PARAM = 3;
        public static final int LOGINACK_ROOM = 4;
        public static final int PARSE_LOGINACK_FAIL = 2;
    }

    public PLVErrorCodeSocketLogin(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 6;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? "unknown" : "已被踢出聊天室" : "头像/昵称不合法" : "房间不合法" : "参数非法" : "解析登录ack数据异常";
    }
}
