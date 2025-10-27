package com.ucloudrtclib.sdkengine;

import android.app.Application;
import android.content.Context;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioVideoMode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkCaptureMode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkLogLevel;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkPushEncode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkPushOrientation;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkVideoOutputOrientationMode;
import d.b;

/* loaded from: classes6.dex */
public class UCloudRtcSdkEnv {
    private static final String TAG = "UCloudRtcSdkEnv";

    public static void enableAppUsageReport(boolean z2) {
        b.a(z2);
    }

    public static Application getApplication() {
        return b.a();
    }

    public static int getAudioVideoMode() {
        return b.b();
    }

    public static int getCameraType() {
        return b.c();
    }

    public static UCloudRtcSdkCaptureMode getCaptureMode() {
        return UCloudRtcSdkCaptureMode.matchValue(b.d());
    }

    public static UCloudRtcSdkPushEncode getEncodeMode() {
        return UCloudRtcSdkPushEncode.matchValue(b.h());
    }

    public static boolean getHttpsVerify() {
        return b.a.f26736v;
    }

    public static UCloudRtcSdkPushOrientation getPushOrientation() {
        return UCloudRtcSdkPushOrientation.matchValue(b.i());
    }

    public static int getReConnectTimes() {
        return b.j();
    }

    public static long getSDkLogDirSize() {
        return b.k();
    }

    public static UCloudRtcSdkMode getSdkMode() {
        return UCloudRtcSdkMode.matchValue(b.l());
    }

    public static boolean getTestSwitch() {
        return b.m();
    }

    public static UCloudRtcSdkVideoOutputOrientationMode getVideoOutputOrientation() {
        return UCloudRtcSdkVideoOutputOrientationMode.matchValue(b.p());
    }

    public static void initCameraId(String str) {
        b.a(str);
    }

    public static void initEnv(Context context) {
        b.a(context);
    }

    public static boolean isAppUsageReport() {
        return b.r();
    }

    public static boolean isFrontCameraMirror() {
        return b.s();
    }

    public static boolean isFrontCameraMirrorOnlyRemote() {
        return b.t();
    }

    public static boolean isLogReport() {
        return b.u();
    }

    public static boolean isPermissionIgnore() {
        return b.v();
    }

    public static boolean isSupportScreenCapture() {
        return b.w();
    }

    public static boolean isWriteToLogCat() {
        return b.x();
    }

    public static void setAudioVideoMode(UCloudRtcSdkAudioVideoMode uCloudRtcSdkAudioVideoMode) {
        b.a(uCloudRtcSdkAudioVideoMode.ordinal());
    }

    public static void setCameraType(int i2) {
        b.b(i2);
    }

    public static void setCaptureMode(UCloudRtcSdkCaptureMode uCloudRtcSdkCaptureMode) {
        b.c(uCloudRtcSdkCaptureMode.ordinal());
    }

    public static void setEncodeMode(UCloudRtcSdkPushEncode uCloudRtcSdkPushEncode) {
        b.e(uCloudRtcSdkPushEncode.ordinal());
    }

    public static void setFrontCameraMirror(boolean z2) {
        b.c(z2);
    }

    public static void setFrontCameraMirrorOnlyRemote(boolean z2) {
        b.d(z2);
    }

    public static void setHttpsVerify(boolean z2) {
        b.a.f26736v = z2;
    }

    public static void setLogLevel(UCloudRtcSdkLogLevel uCloudRtcSdkLogLevel) {
        b.d(uCloudRtcSdkLogLevel.ordinal());
    }

    public static void setLogReport(boolean z2) {
        b.f(z2);
    }

    public static void setPermissionIgnore(boolean z2) {
        b.g(z2);
    }

    public static void setPrivateDeploy(boolean z2) {
        b.h(z2);
    }

    public static void setPrivateDeployRoomURL(String str) {
        b.d(str);
    }

    public static void setPushOrientation(UCloudRtcSdkPushOrientation uCloudRtcSdkPushOrientation) {
        b.f(uCloudRtcSdkPushOrientation.ordinal());
    }

    public static void setReConnectTimes(int i2) {
        b.g(i2);
    }

    public static void setSDKLogDirSize(long j2) {
        b.a(j2);
    }

    public static void setSdkMode(UCloudRtcSdkMode uCloudRtcSdkMode) {
        b.h(uCloudRtcSdkMode.ordinal());
    }

    public static void setTestSwitch(boolean z2) {
        b.i(z2);
    }

    public static void setTokenSecKey(String str) {
        b.e(str);
    }

    public static void setVideoHardWareAcceleration(boolean z2) {
        b.j(z2);
    }

    public static void setVideoOutputOrientation(UCloudRtcSdkVideoOutputOrientationMode uCloudRtcSdkVideoOutputOrientationMode) {
        b.j(uCloudRtcSdkVideoOutputOrientationMode.ordinal());
    }

    public static void setVideoTrackOpenCamera(boolean z2) {
        b.k(z2);
    }

    public static void setWriteToLogCat(boolean z2) {
        b.l(z2);
    }

    public static void unInitEnv() {
        b.y();
    }

    public boolean getVideoTrackOpenCamera() {
        return b.q();
    }
}
