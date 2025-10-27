package com.plv.foundationsdk.log.elog.logcode.chat;

/* loaded from: classes4.dex */
public class PLVErrorCodeChatroomStatus extends PLVErrorCodeChatroomBase {
    private static final String EVENT = "chatStatus";
    private static final int FIRST_TAG = 4;

    public interface SecondCode {
        public static final int CHATROOM_OFFLINE = 3;
        public static final int CHATROOM_RESTRICT_MAX_VIEWER = 7;
    }

    public PLVErrorCodeChatroomStatus(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeChatroomStatus(i2).createModuleCode() + i2;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 4;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 3 ? i2 != 7 ? "unknown" : "超出最大同时在线人数限制" : "状态错误";
    }
}
