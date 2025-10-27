package com.plv.foundationsdk.log.elog.logcode.chat;

import com.mobile.auth.gatewayauth.ResultCode;

/* loaded from: classes4.dex */
public class PLVErrorCodeChatroomSpeak extends PLVErrorCodeChatroomBase {
    private static final String EVENT = "chatSpeak";
    private static final int FIRST_TAG = 5;

    public interface SecondCode {
        public static final int TEXT_PARAM_EMPTY = 1;
    }

    public PLVErrorCodeChatroomSpeak(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeChatroomSpeak(i2).createModuleCode() + i2;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 5;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 == 1 ? ResultCode.MSG_ERROR_INVALID_PARAM : "unknown";
    }
}
