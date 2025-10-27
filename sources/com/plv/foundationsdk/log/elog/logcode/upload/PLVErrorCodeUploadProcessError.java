package com.plv.foundationsdk.log.elog.logcode.upload;

/* loaded from: classes4.dex */
public class PLVErrorCodeUploadProcessError extends PLVErrorCodeUploadBase {
    private static final String EVENT = "uploadError";
    private static final int FIRST_TAG = 4;

    public interface SecondCode {
        public static final int ERROR_COPY_FILE = 1;
        public static final int ERROR_FILE_EXIST_IN_LOCAL = 2;
        public static final int ERROR_FILE_EXIST_IN_SERVER = 3;
        public static final int ERROR_OSS_UPLOAD_FAILED = 4;
        public static final int ERROR_REFRES_OSS_CLIENT_STS_TOKEN = 5;
    }

    public PLVErrorCodeUploadProcessError(int i2) {
        super(i2);
    }

    public static int getCode(int i2) {
        return new PLVErrorCodeUploadProcessError(i2).createModuleCode() + i2;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 4;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "unknown" : "刷新 OSS Client STS token 接口失败" : "阿里 OSS 上传失败" : "文档已存在服务端（接口返回）" : "文档上传任务已存在（本地判断）" : "拷贝文档到沙盒失败";
    }
}
