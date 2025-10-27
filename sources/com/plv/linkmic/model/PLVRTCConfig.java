package com.plv.linkmic.model;

import com.plv.linkmic.PLVLinkMicConstant;

/* loaded from: classes4.dex */
public class PLVRTCConfig {
    private int frameRate = 15;
    private PLVLinkMicConstant.PushResolutionRatio resolutionRatio = PLVLinkMicConstant.PushResolutionRatio.RATIO_4_3;
    private String rtcType;
    private String uid;

    public PLVRTCConfig frameRate(int i2) {
        this.frameRate = i2;
        return this;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public PLVLinkMicConstant.PushResolutionRatio getResolutionRatio() {
        return this.resolutionRatio;
    }

    public String getRtcType() {
        return this.rtcType;
    }

    public String getUid() {
        return this.uid;
    }

    public PLVRTCConfig resolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.resolutionRatio = pushResolutionRatio;
        return this;
    }

    public PLVRTCConfig rtcType(String str) {
        this.rtcType = str;
        return this;
    }

    public PLVRTCConfig uid(String str) {
        this.uid = str;
        return this;
    }
}
