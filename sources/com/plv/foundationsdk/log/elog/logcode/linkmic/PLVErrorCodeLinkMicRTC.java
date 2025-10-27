package com.plv.foundationsdk.log.elog.logcode.linkmic;

/* loaded from: classes4.dex */
public class PLVErrorCodeLinkMicRTC extends PLVErrorCodeLinkMicBase {

    public interface SecondTag {
        public static final int ERROR_ADD_PUBLISH_STREAM = 3;
        public static final int ERROR_ENGINE_CREATE_FAILED = 4;
        public static final int ERROR_JOIN_CHANNEL = 2;
        public static final int ERROR_ON_STREAM_PUBLISHED = 1;
    }

    public PLVErrorCodeLinkMicRTC(int i2) {
        super(i2);
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public String createEventName() {
        return "linkMicRTC";
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
    public int firstTag() {
        return 4;
    }

    @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
    public String getErrorMessage(int i2) {
        if (i2 == 1) {
            return "推流失败#{" + getCode() + "}";
        }
        if (i2 == 2) {
            return "加入Rtc房间失败#{" + getCode() + "}";
        }
        if (i2 != 3) {
            return "";
        }
        return "加入推流地址失败#{" + getCode() + "}";
    }
}
