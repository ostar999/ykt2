package com.plv.foundationsdk.log.elog.logcode.socket;

/* loaded from: classes4.dex */
public class PLVErrorCodeSocketApi extends PLVErrorCodeSocketBase {
    private static final String EVENT = "socketApiError";
    private static final int FIRST_TAG = 1;

    public interface SecondCode {
        public static final int TOKEN_EXCEPTION = 3;
    }

    public PLVErrorCodeSocketApi(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 1;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 == 3 ? "token接口调用异常" : "unknown";
    }
}
