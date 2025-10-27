package o1;

import android.app.Activity;
import android.content.Intent;
import c.h;
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine;
import com.ucloudrtclib.sdkengine.adapter.UCloudRtcEngineImpl;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkEngineType;
import com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCDataProvider;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCNotification;

/* loaded from: classes6.dex */
public final /* synthetic */ class a {
    public static UCloudRtcSdkEngine a(UCloudRtcSdkEventListener uCloudRtcSdkEventListener) {
        h.e();
        h.a(UCloudRtcEngineImpl.TAG, "createEngine UCloudRtcEngineImpl.getInstance");
        UCloudRtcEngineImpl uCloudRtcEngineImpl = UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0);
        uCloudRtcEngineImpl.setEventListener(uCloudRtcSdkEventListener);
        return uCloudRtcEngineImpl;
    }

    public static void b() {
        h.a(UCloudRtcEngineImpl.TAG, "destroy engine start");
        UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0).destroy();
        h.a(UCloudRtcEngineImpl.TAG, "destroy engine finish");
        h.f();
    }

    public static String c() {
        return "2.1.5_release_2eda0bb2_" + p.a.b();
    }

    public static void d(UCloudRTCDataProvider uCloudRTCDataProvider) {
        h.a(UCloudRtcEngineImpl.TAG, "onScreenCaptureResult UCloudRtcEngineImpl.getInstance");
        UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0).onRGBACaptureResult(uCloudRTCDataProvider);
    }

    public static void e(Intent intent) {
        h.a(UCloudRtcEngineImpl.TAG, "onScreenCaptureResult UCloudRtcEngineImpl.getInstance");
        UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0).onScreenCaptureResult(intent);
    }

    public static void f(UCloudRTCNotification uCloudRTCNotification) {
        h.a(UCloudRtcEngineImpl.TAG, "regScreenCaptureNotification UCloudRtcEngineImpl.getInstance");
        UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0).regScreenCaptureNotification(uCloudRTCNotification);
    }

    public static void g(Activity activity) {
        h.a(UCloudRtcEngineImpl.TAG, "requestScreenCapture UCloudRtcEngineImpl.getInstance");
        UCloudRtcEngineImpl.getInstance(UCloudRtcSdkEngineType.UCLOUD_RTC_SDK_ENGINE_TYPE_0).requestScreenCapture(activity);
    }
}
