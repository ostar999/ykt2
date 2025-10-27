package com.plv.livescenes.linkmic.manager;

import android.text.TextUtils;
import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.PLVLinkMicDataConfig;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.socket.socketio.PLVSocketIOClient;

/* loaded from: classes4.dex */
public class PLVLinkMicConfig {
    private static final String TAG = "PLVLinkMicConfig";
    private static volatile PLVLinkMicConfig instance;
    private int frameRate;
    private String linkMicUid;
    private PLVLiveChannelType liveChannelType;
    private String rtcType;
    private String sessionId = "";
    private boolean isSupportScreenShare = false;
    private boolean pureRtcOnlySubscribeMainScreenVideo = true;
    private boolean pureRtcWatchEnabled = false;
    private boolean quickLiveEnable = false;

    private PLVLinkMicConfig() {
    }

    public static PLVLinkMicConfig getInstance() {
        if (instance == null) {
            synchronized (PolyvLiveSDKClient.class) {
                if (instance == null) {
                    instance = new PLVLinkMicConfig();
                }
            }
        }
        return instance;
    }

    public void clear() {
        this.linkMicUid = "";
        this.rtcType = "";
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public String getLinkMicUid() {
        if (TextUtils.isEmpty(this.linkMicUid)) {
            PLVCommonLog.e(TAG, "获取了null 的连麦ID");
        }
        return this.linkMicUid;
    }

    public PolyvLiveChannelType getLiveChannelType() {
        return PolyvLiveChannelType.mapFromNewType(getLiveChannelTypeNew());
    }

    public PLVLiveChannelType getLiveChannelTypeNew() {
        return this.liveChannelType;
    }

    public String getRtcType() {
        return this.rtcType;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void init(String str, boolean z2) {
        String str2 = this.rtcType;
        if (str2 == null) {
            PLVCommonLog.exception(new Throwable("rtcType is null"));
            return;
        }
        if (!z2) {
            str2.hashCode();
            switch (str2) {
                case "trtc":
                case "urtc":
                    this.linkMicUid = str;
                    break;
                case "agora":
                    this.linkMicUid = Math.abs((int) System.currentTimeMillis()) + "";
                    break;
                default:
                    PLVCommonLog.exception(new Throwable("rtcType is empty"));
                    break;
            }
        } else {
            this.linkMicUid = str;
        }
        PLVSocketIOClient.getInstance().setMicId(this.linkMicUid);
    }

    public boolean isLowLatencyPureRtcWatch() {
        return PLVLinkMicConstant.RtcType.RTC_TYPE_U.equals(getRtcType()) && isPureRtcWatchEnabled();
    }

    public boolean isLowLatencyWatchEnabled() {
        return (PLVLinkMicConstant.RtcType.RTC_TYPE_T.equals(getRtcType()) && this.quickLiveEnable) || (PLVLinkMicConstant.RtcType.RTC_TYPE_U.equals(getRtcType()) && isPureRtcWatchEnabled());
    }

    public boolean isPureRtcOnlySubscribeMainScreenVideo() {
        return this.pureRtcOnlySubscribeMainScreenVideo;
    }

    public boolean isPureRtcWatchEnabled() {
        return this.pureRtcWatchEnabled;
    }

    public boolean isSupportScreenShare() {
        return !this.rtcType.equals(PLVLinkMicConstant.RtcType.RTC_TYPE_A);
    }

    public PLVLinkMicConfig setFrameRate(int i2) {
        this.frameRate = i2;
        return this;
    }

    public PLVLinkMicConfig setLiveChannelType(PLVLiveChannelType pLVLiveChannelType) {
        this.liveChannelType = pLVLiveChannelType;
        return this;
    }

    public PLVLinkMicConfig setPureRtcOnlySubscribeMainScreenVideo(boolean z2) {
        this.pureRtcOnlySubscribeMainScreenVideo = z2;
        return this;
    }

    public PLVLinkMicConfig setPureRtcWatchEnabled(boolean z2) {
        this.pureRtcWatchEnabled = z2;
        PLVLinkMicDataConfig.pureRtcWatchEnabled = z2;
        return this;
    }

    public PLVLinkMicConfig setQuickLiveEnable(boolean z2) {
        this.quickLiveEnable = z2;
        return this;
    }

    public PLVLinkMicConfig setRtcType(String str) {
        this.rtcType = str;
        return this;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public PLVLinkMicConfig setupHiClassConfig(String str, boolean z2, String str2, long j2) {
        PLVLinkMicDataConfig.token = str;
        PLVLinkMicDataConfig.isTeacherType = z2;
        PLVLinkMicDataConfig.courseCode = str2;
        PLVLinkMicDataConfig.lessonId = j2;
        return this;
    }
}
