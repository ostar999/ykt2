package com.plv.foundationsdk.log.elog.logcode.ppt;

/* loaded from: classes4.dex */
public class PLVErrorCodePPTWeb extends PLVErrorCodePPTBase {
    private static final String PPT_WEB_EVENT = "pptWeb";
    private static final int PPT_WEB_TAG = 4;

    public interface PLVPPTPlayCode {
        public static final int PPT_LOAD_FAILED = 1;
        public static final int PPT_RECEIVE_JSMESSAGE_ERROR = 3;
        public static final int PPT_SEND_JSMESSAGE_ERROR = 2;
    }

    public PLVErrorCodePPTWeb(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return PPT_WEB_EVENT;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 4;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? "" : "js 消息 接收数据格式不对" : "js 消息 参数传递错误" : "ppt 加载失败";
    }
}
