package com.plv.rtc.urtc;

import android.content.Context;
import com.plv.rtc.urtc.a.c;
import com.plv.rtc.urtc.enummeration.URTCSdkCaptureMode;
import com.plv.rtc.urtc.enummeration.URTCSdkLogLevel;
import com.plv.rtc.urtc.enummeration.URTCSdkMode;
import com.plv.rtc.urtc.enummeration.URTCSdkPushEncode;
import com.plv.rtc.urtc.enummeration.URTCSdkPushOrientation;
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEnv;

/* loaded from: classes5.dex */
public class URTCSdkEnv {
    public static void initEnv(Context context) {
        UCloudRtcSdkEnv.initEnv(context);
    }

    public static void setCameraType(int i2) {
        UCloudRtcSdkEnv.setCameraType(i2);
    }

    public static void setCaptureMode(URTCSdkCaptureMode uRTCSdkCaptureMode) {
        UCloudRtcSdkEnv.setCaptureMode(c.a(uRTCSdkCaptureMode));
    }

    public static void setEncodeMode(URTCSdkPushEncode uRTCSdkPushEncode) {
        UCloudRtcSdkEnv.setEncodeMode(c.a(uRTCSdkPushEncode));
    }

    public static void setHttpsVerify(boolean z2) {
        UCloudRtcSdkEnv.setHttpsVerify(z2);
    }

    public static void setLogLevel(URTCSdkLogLevel uRTCSdkLogLevel) {
        UCloudRtcSdkEnv.setLogLevel(c.a(uRTCSdkLogLevel));
    }

    public static void setLogReport(boolean z2) {
        UCloudRtcSdkEnv.setLogReport(z2);
    }

    public static void setPushOrientation(URTCSdkPushOrientation uRTCSdkPushOrientation) {
        UCloudRtcSdkEnv.setPushOrientation(c.a(uRTCSdkPushOrientation));
    }

    public static void setReConnectTimes(int i2) {
        UCloudRtcSdkEnv.setReConnectTimes(i2);
    }

    public static void setSdkMode(URTCSdkMode uRTCSdkMode) {
        UCloudRtcSdkEnv.setSdkMode(c.a(uRTCSdkMode));
    }

    public static void setTokenSecKey(String str) {
        UCloudRtcSdkEnv.setTokenSecKey(str);
    }

    public static void setWriteToLogCat(boolean z2) {
        UCloudRtcSdkEnv.setWriteToLogCat(z2);
    }
}
