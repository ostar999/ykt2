package com.ucloudrtclib.sdkengine.define;

import com.yikaobang.yixue.R2;
import core.data.Convert;
import d.a;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkVideoProfile implements Convert<UCloudRtcSdkVideoProfile, a.w> {
    UCLOUD_RTC_SDK_VIDEO_PROFILE_320_180,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_352_288,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_480_360,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_640_360,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_640_480,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_1280_720,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_1920_1080,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_640_360_2,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_1280_720_2,
    UCLOUD_RTC_SDK_VIDEO_PROFILE_EXTEND,
    UCLOUD_RTC_SDK_VIDEO_RESOLUTION_STANDARD,
    UCLOUD_RTC_SDK_VIDEO_RESOLUTION_HIGH,
    UCLOUD_RTC_SDK_VIDEO_RESOLUTION_SUPER_HIGH;

    public int fps = 20;
    public int width = 1280;
    public int height = 720;
    public int startBitrate = 600;
    public int minBitrate = 500;
    public int maxBitrate = 1000;
    public int profile = 5;

    /* renamed from: com.ucloudrtclib.sdkengine.define.UCloudRtcSdkVideoProfile$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile;

        static {
            int[] iArr = new int[UCloudRtcSdkVideoProfile.values().length];
            $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile = iArr;
            try {
                iArr[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_320_180.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_352_288.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_480_360.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_640_360.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_640_360_2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_EXTEND.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_640_480.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_1280_720.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_1280_720_2.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[UCloudRtcSdkVideoProfile.UCLOUD_RTC_SDK_VIDEO_PROFILE_1920_1080.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    UCloudRtcSdkVideoProfile() {
    }

    public static UCloudRtcSdkVideoProfile adapter(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile2 = UCLOUD_RTC_SDK_VIDEO_RESOLUTION_STANDARD;
        switch (AnonymousClass1.$SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[uCloudRtcSdkVideoProfile.ordinal()]) {
            case 6:
                int i2 = uCloudRtcSdkVideoProfile.width * uCloudRtcSdkVideoProfile.height;
                if (i2 >= 921600) {
                    uCloudRtcSdkVideoProfile2 = UCLOUD_RTC_SDK_VIDEO_RESOLUTION_HIGH;
                }
                if (i2 >= 2073600) {
                    break;
                }
                break;
        }
        return UCLOUD_RTC_SDK_VIDEO_RESOLUTION_SUPER_HIGH;
    }

    public static UCloudRtcSdkVideoProfile matchValue(int i2) {
        for (UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile : values()) {
            if (uCloudRtcSdkVideoProfile.ordinal() == i2) {
                return uCloudRtcSdkVideoProfile;
            }
        }
        return UCLOUD_RTC_SDK_VIDEO_PROFILE_352_288;
    }

    public UCloudRtcSdkVideoProfile extendParams(int i2, int i3, int i4) {
        this.profile = 9;
        this.fps = i2;
        this.width = i3;
        this.height = i4;
        return this;
    }

    public int getFps() {
        return this.fps;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMaxBitrate() {
        return this.maxBitrate;
    }

    public int getMinBitrate() {
        return this.minBitrate;
    }

    public int getStartBitrate() {
        return this.startBitrate;
    }

    public int getWidth() {
        return this.width;
    }

    @Override // core.data.Convert
    public UCloudRtcSdkVideoProfile toProxy(a.w wVar, UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        return null;
    }

    public void updateParams() {
        if (equals(UCLOUD_RTC_SDK_VIDEO_RESOLUTION_SUPER_HIGH)) {
            throw new IllegalArgumentException("如果需要自定义720P以上分辨率，请联系UCloud商务");
        }
        switch (AnonymousClass1.$SwitchMap$com$ucloudrtclib$sdkengine$define$UCloudRtcSdkVideoProfile[ordinal()]) {
            case 1:
                this.width = 320;
                this.height = 180;
                return;
            case 2:
                this.width = R2.attr.arcLabelPaddingRight;
                this.height = R2.attr.alignContent;
                return;
            case 3:
                this.width = 480;
                this.height = 360;
                return;
            case 4:
            case 5:
                this.width = 640;
                this.height = 360;
                return;
            case 6:
            case 7:
                this.width = 640;
                this.height = 480;
                return;
            case 8:
            case 9:
                this.width = 1280;
                this.height = 720;
                return;
            case 10:
                this.width = R2.attr.iconTint;
                this.height = R2.attr.color_hot_circle_one_end;
                return;
            default:
                return;
        }
    }

    @Override // core.data.Convert
    public a.w toCore(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile, a.w wVar) {
        if (wVar == null) {
            a.w wVar2 = new a.w();
            wVar2.a(uCloudRtcSdkVideoProfile.fps, wVar.f26707b, wVar.f26708c, wVar.f26709d, wVar.f26710e, wVar.f26711f);
            return wVar2;
        }
        wVar.f26712g = uCloudRtcSdkVideoProfile.profile;
        wVar.f26706a = uCloudRtcSdkVideoProfile.fps;
        wVar.f26707b = uCloudRtcSdkVideoProfile.width;
        wVar.f26708c = uCloudRtcSdkVideoProfile.height;
        wVar.f26709d = uCloudRtcSdkVideoProfile.startBitrate;
        wVar.f26710e = uCloudRtcSdkVideoProfile.minBitrate;
        wVar.f26711f = uCloudRtcSdkVideoProfile.maxBitrate;
        return wVar;
    }

    public UCloudRtcSdkVideoProfile extendParams(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.profile = 9;
        this.fps = i2;
        this.width = i3;
        this.height = i4;
        this.startBitrate = i5;
        this.minBitrate = i6;
        this.maxBitrate = i7;
        return this;
    }
}
