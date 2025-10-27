package com.plv.foundationsdk.log.elog.logcode.play;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodePlayBase extends PLVErrorCodeInfoBase {
    public static final String MODULE_NAME = "play";

    public PLVErrorCodePlayBase(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createModuleName() {
        return "play";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.VIDEO_PLAY;
    }
}
