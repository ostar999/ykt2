package com.plv.foundationsdk.log.elog.logcode;

import com.plv.foundationsdk.log.elog.logcode.PLVELogErrorCodeBase;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public abstract class PLVErrorCodeInfoBase implements PLVElogCodeModuleDes, PLVFoundationVO {
    private int code;
    private String message;

    public PLVErrorCodeInfoBase(int i2) {
        this.code = createModuleCode() + i2;
        this.message = getErrorMessage(i2);
    }

    public int createModuleCode() {
        return PLVELogErrorCodeBase.Platform.ANDROID_PLATFORM + moduleCode() + PLVELogErrorCodeBase.FirstTag.getFirstTagCode(firstTag());
    }

    public int getCode() {
        return this.code;
    }

    public abstract String getErrorMessage(int i2);

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
