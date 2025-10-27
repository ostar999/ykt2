package com.plv.foundationsdk.log.elog.logcode.socket;

/* loaded from: classes4.dex */
public class PLVErrorCodeSocketConnect extends PLVErrorCodeSocketBase {
    private static final String EVENT = "socketConnectError";
    private static final int FIRST_TAG = 5;

    public interface SecondCode {
        public static final int TOKEN_EXPIRED = 3;
    }

    public PLVErrorCodeSocketConnect(int i2) {
        super(i2);
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
        return i2 == 3 ? "token鉴权失败" : "unknown";
    }
}
