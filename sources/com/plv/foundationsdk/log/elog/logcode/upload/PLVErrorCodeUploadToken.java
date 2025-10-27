package com.plv.foundationsdk.log.elog.logcode.upload;

/* loaded from: classes4.dex */
public class PLVErrorCodeUploadToken extends PLVErrorCodeUploadBase {
    private static final String EVENT = "uploadToken";
    private static final int FIRST_TAG = 1;

    public interface SecondCode {
        public static final int GET_UPLOAD_TOKEN_DATA_PARSE_ERROR = 3;
        public static final int GET_UPLOAD_TOKEN_JSON_RESPONSE_CODE_ERROR = 2;
        public static final int GET_UPLOAD_TOKEN_PARAM_ERROR = 1;
    }

    public PLVErrorCodeUploadToken(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeUploadToken(i2).createModuleCode() + i2;
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
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            return "unknown";
        }
        return "上传失败#{" + getCode() + "}";
    }
}
