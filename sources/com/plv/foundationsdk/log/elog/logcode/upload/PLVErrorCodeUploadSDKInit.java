package com.plv.foundationsdk.log.elog.logcode.upload;

/* loaded from: classes4.dex */
public class PLVErrorCodeUploadSDKInit extends PLVErrorCodeUploadBase {
    private static final String EVENT = "uploadSDKInit";
    private static final int FIRST_TAG = 0;

    public interface SecondCode {
        public static final int EVENT_CONTEXT_NULL = 1;
        public static final int EVENT_UNKNOWN = 0;
    }

    public PLVErrorCodeUploadSDKInit(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeUploadSDKInit(i2).createModuleCode() + i2;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 0;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 0 ? i2 != 1 ? "unknown" : "SDK 初始化参数为空" : "未找到相应错误类型";
    }
}
