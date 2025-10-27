package com.plv.foundationsdk.log.elog.logcode.linkmic;

/* loaded from: classes4.dex */
public class PLVErrorCodeLinkMicSystemError extends PLVErrorCodeLinkMicBase {

    public interface SecondTag {
        public static final int ERROR_LINK_PERMISSION_DENIED = 1;
    }

    public PLVErrorCodeLinkMicSystemError(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return "linkMicSystem";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 5;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        if (i2 != 1) {
            return null;
        }
        return "系统未授予音视频权限#{" + getCode() + "}";
    }
}
