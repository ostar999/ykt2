package com.plv.foundationsdk.log.elog.logcode.socket;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodeSocketBase extends PLVErrorCodeInfoBase {
    public static final String MODULE_NAME = "socket";

    public PLVErrorCodeSocketBase(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createModuleName() {
        return MODULE_NAME;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.SOCKET;
    }
}
