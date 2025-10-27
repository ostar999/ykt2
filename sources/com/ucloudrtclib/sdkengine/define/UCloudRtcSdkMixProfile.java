package com.ucloudrtclib.sdkengine.define;

import core.data.MixProfile;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class UCloudRtcSdkMixProfile extends MixProfile {
    private static UCloudRtcSdkMixProfile instance;

    public class URTCMixParamsBuilder extends MixProfile.MixParamsBuilder<URTCMixParamsBuilder> {
        public URTCMixParamsBuilder() {
            super();
        }

        @Override // core.data.MixProfile.MixParamsBuilder
        public UCloudRtcSdkMixProfile build() {
            return new UCloudRtcSdkMixProfile(this.mType, this.mStreams, this.mPushUrl, this.mLayout, this.mCustom, this.mBgColor, this.mFrameRate, this.mBitrate, this.mVideoCodec, this.mQualityLevel, this.mAudioCodec, this.mMainViewUserId, this.mMainViewType, this.mWidth, this.mHeight, this.mRegion, this.mBucket, this.mWaterType, this.mWaterPosition, this.mWaterUrl, this.mMimeType, this.mStreamMode, this.mLayoutUserLimit, this.mKeyUser, this.mTaskTimeout);
        }

        public URTCMixParamsBuilder(int i2) {
            super(i2);
        }
    }

    public UCloudRtcSdkMixProfile() {
    }

    public static UCloudRtcSdkMixProfile getInstance() {
        if (instance == null) {
            synchronized (UCloudRtcSdkMixProfile.class) {
                if (instance == null) {
                    instance = new UCloudRtcSdkMixProfile();
                }
            }
        }
        return instance;
    }

    public UCloudRtcSdkMixProfile(int i2, JSONArray jSONArray, JSONArray jSONArray2, int i3, JSONArray jSONArray3, JSONObject jSONObject, int i4, int i5, String str, String str2, String str3, String str4, int i6, int i7, int i8, String str5, String str6, int i9, int i10, String str7, int i11, int i12, int i13, String str8, int i14) {
        super(i2, jSONArray, jSONArray2, i3, jSONArray3, jSONObject, i4, i5, str, str2, str3, str4, i6, i7, i8, str5, str6, i9, i10, str7, i11, i12, i13, str8, i14);
    }

    @Override // core.data.MixProfile
    public URTCMixParamsBuilder assembleMixParamsBuilder() {
        return new URTCMixParamsBuilder();
    }

    @Override // core.data.MixProfile
    public URTCMixParamsBuilder assembleRecordMixParamsBuilder() {
        return new URTCMixParamsBuilder(2);
    }

    @Override // core.data.MixProfile
    public URTCMixParamsBuilder assembleRelayMixParamsBuilder() {
        return new URTCMixParamsBuilder(1);
    }

    @Override // core.data.MixProfile
    public URTCMixParamsBuilder assembleUpdateMixParamsBuilder() {
        return new URTCMixParamsBuilder(4);
    }
}
