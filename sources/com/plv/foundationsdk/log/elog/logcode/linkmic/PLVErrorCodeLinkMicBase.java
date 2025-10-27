package com.plv.foundationsdk.log.elog.logcode.linkmic;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodeLinkMicBase extends PLVErrorCodeInfoBase {
    public static final String LINK_MODULE = "link";

    public static class LinkMicFirstTagCode {
        static final int LINK_REQUEST_1 = 1;
        static final int RTC = 4;
        static final int SYSTEM = 5;
        static final int link_request_2 = 2;
        static final int link_request_3 = 3;
    }

    public PLVErrorCodeLinkMicBase(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public final String createModuleName() {
        return LINK_MODULE;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.LINK;
    }
}
