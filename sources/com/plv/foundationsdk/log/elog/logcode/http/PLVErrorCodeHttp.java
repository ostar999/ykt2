package com.plv.foundationsdk.log.elog.logcode.http;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public class PLVErrorCodeHttp extends PLVErrorCodeInfoBase {
    public static final String HTTP_EVENT = "httpBaseEvent";
    public static final String HTTP_MODULE = "http";
    public static final int HTTP_TAG = 0;

    public interface HttpBaseCode {
        public static final int NETWORK_DISCONNECT = 0;
    }

    public PLVErrorCodeHttp(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return HTTP_EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createModuleName() {
        return "http";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 0;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 == 0 ? "网络断开 请检查网络" : "";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.HTTP;
    }
}
