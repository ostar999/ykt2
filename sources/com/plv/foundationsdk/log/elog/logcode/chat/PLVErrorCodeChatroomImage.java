package com.plv.foundationsdk.log.elog.logcode.chat;

import com.mobile.auth.gatewayauth.ResultCode;

/* loaded from: classes4.dex */
public class PLVErrorCodeChatroomImage extends PLVErrorCodeChatroomBase {
    private static final String EVENT = "chatImage";
    private static final int FIRST_TAG = 6;

    public interface SecondCode {
        public static final int CHECK_IMG_FAIL = 5;
        public static final int IMG_PARAM_EMPTY = 1;
    }

    public PLVErrorCodeChatroomImage(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeChatroomImage(i2).createModuleCode() + i2;
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
        return i2 != 1 ? i2 != 5 ? "unknown" : "审核错误" : ResultCode.MSG_ERROR_INVALID_PARAM;
    }
}
