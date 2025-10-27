package com.plv.foundationsdk.log.elog.logcode.chat;

import com.mobile.auth.gatewayauth.ResultCode;

/* loaded from: classes4.dex */
public class PLVErrorCodeChatroomBKick extends PLVErrorCodeChatroomBase {
    private static final String EVENT = "chatBKick";
    private static final int FIRST_TAG = 7;

    public interface SecondCode {
        public static final int KICK_PARAM_EMPTY = 1;
        public static final int SHIELD_PARAM_EMPTY = 2;
        public static final int UNSHIELD_PARAM_EMPTY = 3;
    }

    public PLVErrorCodeChatroomBKick(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeChatroomBKick(i2).createModuleCode() + i2;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 7;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return (i2 == 1 || i2 == 2 || i2 == 3) ? ResultCode.MSG_ERROR_INVALID_PARAM : "unknown";
    }
}
