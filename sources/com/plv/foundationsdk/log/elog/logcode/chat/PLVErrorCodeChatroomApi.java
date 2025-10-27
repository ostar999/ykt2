package com.plv.foundationsdk.log.elog.logcode.chat;

/* loaded from: classes4.dex */
public class PLVErrorCodeChatroomApi extends PLVErrorCodeChatroomBase {
    private static final String EVENT = "chatApiError";
    private static final int FIRST_TAG = 1;

    public interface SecondCode {
        public static final int UPLOAD_IMG_FAIL = 17;
    }

    public PLVErrorCodeChatroomApi(int i2) {
        super(i2);
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
        return i2 == 17 ? "上传图片失败" : "unknown";
    }
}
