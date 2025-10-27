package com.plv.foundationsdk.log.elog.logcode.ppt;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodePPTBase extends PLVErrorCodeInfoBase {
    public static final String PPT_MODULE = "ppt";

    public PLVErrorCodePPTBase(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createModuleName() {
        return "ppt";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.PPT;
    }
}
