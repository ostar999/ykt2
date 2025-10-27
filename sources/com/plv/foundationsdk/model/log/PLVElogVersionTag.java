package com.plv.foundationsdk.model.log;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.plv.socket.event.sclass.PLVInLiveAckResult;

/* loaded from: classes4.dex */
public class PLVElogVersionTag {

    public enum VersionInfo {
        PLATFORMA_ANDROID("Android", HiAnalyticsConstant.KeyAndValue.NUMBER_01),
        FRAMEWORK_ORIGIN("origin", HiAnalyticsConstant.KeyAndValue.NUMBER_01),
        FRAMEWORK_RN("RN", "02"),
        FRAMEWORK_API_CLOUD("APICloud", "03"),
        PROJECT_VOD("vod", HiAnalyticsConstant.KeyAndValue.NUMBER_01),
        PROJECT_LIVE(PLVInLiveAckResult.STATUS_LIVE, "02"),
        PROJECT_RTMP(PLVInLiveAckResult.STATUS_LIVE, "03"),
        PROJECT_CLOUD_CLASS("cloudClass", "04"),
        PROJECT_SHORT_VIDEO("shortVideo", "05"),
        PROJECT_CLOUD_CLASS_STREAM("cloudClassStream", "06"),
        PROJECT_CLOUD_CLASS_I_PAD_STREAM("cloudClassIPadStream", "07"),
        PROJECT_LIVE_SCENE("liveScene", "08"),
        DEVICE_TYPE_PHONE(AliyunLogCommon.TERMINAL_TYPE, "1"),
        DEVICE_TYPE_PAD("pad", "2"),
        DEVICE_TYPE_TV("tv", "3");

        private String code;
        private String content;

        VersionInfo(String str, String str2) {
            this.content = str;
            this.code = str2;
        }

        public String getCode() {
            return this.code;
        }

        public String getContent() {
            return this.content;
        }
    }
}
