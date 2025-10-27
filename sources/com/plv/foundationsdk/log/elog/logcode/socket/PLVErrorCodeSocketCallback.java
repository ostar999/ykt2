package com.plv.foundationsdk.log.elog.logcode.socket;

/* loaded from: classes4.dex */
public class PLVErrorCodeSocketCallback extends PLVErrorCodeSocketBase {
    private static final String EVENT = "callback";
    private int firstTag;

    public static class SecondCodeNN {
        private static final int FIRST_TAG = 99;
        public static final int RECONNECT_TIMEOUT = 0;

        public static int getCode(int i2) {
            return new PLVErrorCodeSocketCallback(i2, 99).createModuleCode() + i2;
        }
    }

    public PLVErrorCodeSocketCallback(int i2, int i3) {
        super(i2);
        this.firstTag = i3;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return "callback";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return this.firstTag;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return (this.firstTag == 99 && i2 == 0) ? "连接聊天室超时" : "unknown";
    }
}
