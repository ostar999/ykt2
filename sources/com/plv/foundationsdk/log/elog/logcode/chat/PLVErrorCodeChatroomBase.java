package com.plv.foundationsdk.log.elog.logcode.chat;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodeChatroomBase extends PLVErrorCodeInfoBase {
    public static final String MODULE_NAME = "chat";

    public PLVErrorCodeChatroomBase(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createModuleName() {
        return "chat";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int moduleCode() {
        return PLVELogErrorCodeBase.Module.CHAT;
    }
}
