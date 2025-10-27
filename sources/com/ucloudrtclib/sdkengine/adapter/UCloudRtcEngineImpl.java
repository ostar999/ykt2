package com.ucloudrtclib.sdkengine.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import b.a;
import c.h;
import c.j;
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine;
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEnv;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAuthInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkEngineType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkErrorCode;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMixProfile;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkRoomType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkScaleType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamRole;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkVideoProfile;
import com.ucloudrtclib.sdkengine.listener.UCloudRtcCameraEventListener;
import com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener;
import com.ucloudrtclib.sdkengine.listener.UcloudRtcVideoFramePreProcessListener;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCDataProvider;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCNotification;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCScreenShot;
import core.data.StreamInfo;
import d.a;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.wrtca.customize.RtcNativeOperation;
import org.wrtca.util.ContextUtils;

/* loaded from: classes6.dex */
public class UCloudRtcEngineImpl implements UCloudRtcSdkEngine {
    public static final String TAG = "UCloudRtcEngineImpl";
    private static a coreRtcEngine;
    private static UCloudRtcSdkEngineType mEngineWorkType;
    private static UCloudRtcEngineImpl sdkImpl;
    private UCloudRtcCallBackAdapter mCallBackAdapter = null;
    public Object localRenderViewObj = null;

    private UCloudRtcEngineImpl() {
    }

    private boolean checkCoreExisted() {
        return coreRtcEngine != null;
    }

    private void destroyEngine() {
        h.a(TAG, "SDK ENGINE DESTROY SDKIMPL TASK START");
    }

    public static UCloudRtcEngineImpl getInstance(UCloudRtcSdkEngineType uCloudRtcSdkEngineType) {
        mEngineWorkType = uCloudRtcSdkEngineType;
        if (sdkImpl == null) {
            sdkImpl = new UCloudRtcEngineImpl();
        }
        if (coreRtcEngine == null) {
            p.a.g(o1.a.c());
            coreRtcEngine = p.a.e();
        }
        return sdkImpl;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void addMixStream(UCloudRtcSdkStreamInfo[] uCloudRtcSdkStreamInfoArr) {
        if (!checkCoreExisted() || uCloudRtcSdkStreamInfoArr == null || uCloudRtcSdkStreamInfoArr.length <= 0) {
            return;
        }
        int length = uCloudRtcSdkStreamInfoArr.length;
        StreamInfo[] streamInfoArr = new StreamInfo[length];
        for (int i2 = 0; i2 < length; i2++) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = uCloudRtcSdkStreamInfoArr[i2];
            streamInfoArr[i2] = uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, (StreamInfo) null);
        }
        coreRtcEngine.b(streamInfoArr);
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode adjustPlaybackSignalVolume(double d3) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.adjustPlaybackSignalVolume(d3)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void adjustRecordVolume(int i2) {
        if (checkCoreExisted()) {
            coreRtcEngine.adjustRecordVolume(i2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode adjustUserPlaybackSignalVolume(String str, double d3) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.adjustUserPlaybackSignalVolume(str, d3)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void changePushResolution(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        if (checkCoreExisted()) {
            coreRtcEngine.f(uCloudRtcSdkVideoProfile.ordinal());
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode configLocalAudioPublish(boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.configLocalAudioPublish(z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode configLocalCameraPublish(boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.configLocalCameraPublish(z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode configLocalScreenPublish(boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.configLocalScreenPublish(z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void controlAudio(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.controlAudio(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void controlAudioPlayOut(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.controlAudioPlayOut(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void controlAudioRecord(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.controlAudioRecord(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void controlLocalVideo(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.controlLocalVideo(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public String copyAssetsFileToSdcard(String str) throws IOException {
        String str2 = UCloudRtcSdkEnv.getApplication().getExternalFilesDir("") + "/assets";
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        String str3 = str2 + "/" + str;
        if (new File(str3).exists()) {
            return str3;
        }
        try {
            InputStream inputStreamOpen = ContextUtils.getApplicationContext().getResources().getAssets().open(str);
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStreamOpen.close();
            return str3;
        } catch (IOException e2) {
            e2.printStackTrace();
            h.c(TAG, " AssetsFile is not exists, name is: " + str);
            return null;
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode cropPushResolution(int i2, int i3) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.cropPushResolution(i2, i3)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void delMixStream(UCloudRtcSdkStreamInfo[] uCloudRtcSdkStreamInfoArr) {
        if (!checkCoreExisted() || uCloudRtcSdkStreamInfoArr == null || uCloudRtcSdkStreamInfoArr.length <= 0) {
            return;
        }
        int length = uCloudRtcSdkStreamInfoArr.length;
        StreamInfo[] streamInfoArr = new StreamInfo[length];
        for (int i2 = 0; i2 < length; i2++) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = uCloudRtcSdkStreamInfoArr[i2];
            streamInfoArr[i2] = uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, (StreamInfo) null);
        }
        coreRtcEngine.b(streamInfoArr);
    }

    public void destroy() {
        if (coreRtcEngine != null) {
            Log.d(TAG, "RTC engine destroy start");
            p.a.f();
            Log.d(TAG, "RTC engine destroy end");
        }
        this.mCallBackAdapter = null;
        coreRtcEngine = null;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void enableBTModule() {
        if (checkCoreExisted()) {
            coreRtcEngine.c();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkAudioDevice getDefaultAudioDevice() {
        return checkCoreExisted() ? UCloudRtcSdkAudioDevice.matchValue(coreRtcEngine.getDefaultAudioDevice()) : UCloudRtcSdkAudioDevice.UCLOUD_RTC_SDK_AUDIODEVICE_NONE;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public RtcNativeOperation getNativeOpInterface() {
        return j.a();
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean getSpeakerOn() {
        if (checkCoreExisted()) {
            return coreRtcEngine.getSpeakerOn();
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isAudioOnlyMode() {
        if (checkCoreExisted()) {
            return coreRtcEngine.isAudioOnlyMode();
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isAutoPublish() {
        if (checkCoreExisted()) {
            return coreRtcEngine.isAutoPublish();
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isAutoSubscribe() {
        if (checkCoreExisted()) {
            return coreRtcEngine.isAutoSubscribe();
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isLocalAudioPublishEnabled() {
        if (!checkCoreExisted()) {
            return false;
        }
        coreRtcEngine.isLocalAudioPublishEnabled();
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isLocalCameraPublishEnabled() {
        if (!checkCoreExisted()) {
            return false;
        }
        coreRtcEngine.isLocalCameraPublishEnabled();
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean isLocalScreenPublishEnabled() {
        if (!checkCoreExisted()) {
            return false;
        }
        coreRtcEngine.isLocalScreenPublishEnabled();
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode joinChannel(UCloudRtcSdkAuthInfo uCloudRtcSdkAuthInfo) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(uCloudRtcSdkAuthInfo)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void kickOffOthers(int i2, List<String> list) {
        if (checkCoreExisted()) {
            coreRtcEngine.kickOffOthers(i2, list);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode leaveChannel() {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.leaveChannel()) : UCloudRtcSdkErrorCode.NET_ERR_NOT_IN_ROOM;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode leaveChannelNonStopLocalPreview() {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.leaveChannelNonStopLocalPreview()) : UCloudRtcSdkErrorCode.NET_ERR_NOT_IN_ROOM;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void lockExtendDeviceInputBuffer() {
        if (checkCoreExisted()) {
            coreRtcEngine.lockExtendDeviceInputBuffer();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void messageNotify(String str) {
        if (checkCoreExisted()) {
            coreRtcEngine.messageNotify(str);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteLocalAudio(boolean z2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(z2, uCloudRtcSdkMediaType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteLocalMic(boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.muteLocalMic(z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteLocalVideo(boolean z2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.b(z2, uCloudRtcSdkMediaType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteRemoteAudio(String str, boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.muteRemoteAudio(str, z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteRemoteScreen(String str, boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.muteRemoteScreen(str, z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteRemoteScreenSound(String str, boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.muteRemoteScreenSound(str, z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode muteRemoteVideo(String str, boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.muteRemoteVideo(str, z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    public void onRGBACaptureResult(UCloudRTCDataProvider uCloudRTCDataProvider) {
        p.a.c(uCloudRTCDataProvider);
    }

    public void onScreenCaptureResult(Intent intent) {
        p.a.h(intent);
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void pauseAudioFile() {
        if (checkCoreExisted()) {
            coreRtcEngine.pauseAudioFile();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode publish(UCloudRtcSdkMediaType uCloudRtcSdkMediaType, boolean z2, boolean z3) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(uCloudRtcSdkMediaType.ordinal(), z2, z3)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void queryMix() {
        if (checkCoreExisted()) {
            coreRtcEngine.queryMix();
        }
    }

    public void regScreenCaptureNotification(UCloudRTCNotification uCloudRTCNotification) {
        p.a.d(uCloudRTCNotification);
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void releaseExtendDeviceInputBuffer() {
        if (checkCoreExisted()) {
            coreRtcEngine.releaseExtendDeviceInputBuffer();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode renderLocalView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        StreamInfo streamInfo = new StreamInfo();
        uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
        UCloudRtcFirstFrameAdapter uCloudRtcFirstFrameAdapter = uCloudRTCFirstFrameRendered != null ? new UCloudRtcFirstFrameAdapter(uCloudRTCFirstFrameRendered) : null;
        if (uCloudRtcSdkScaleType == null) {
            uCloudRtcSdkScaleType = UCloudRtcSdkScaleType.UCLOUD_RTC_SDK_SCALE_ASPECT_FILL;
        }
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(streamInfo, obj, uCloudRtcSdkScaleType.ordinal(), uCloudRtcFirstFrameAdapter));
    }

    public void requestScreenCapture(Activity activity) {
        p.a.i(activity);
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void resumeAudioFile() {
        if (checkCoreExisted()) {
            coreRtcEngine.resumeAudioFile();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setAudioDevice(UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice) {
        if (checkCoreExisted()) {
            coreRtcEngine.j(uCloudRtcSdkAudioDevice.ordinal());
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setAudioOnlyMode(boolean z2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.setAudioOnlyMode(z2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setAutoPublish(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.setAutoPublish(z2);
        }
        return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setAutoSubscribe(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.setAutoSubscribe(z2);
        }
        return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setCameraEventListener(UCloudRtcCameraEventListener uCloudRtcCameraEventListener) {
        if (checkCoreExisted()) {
            coreRtcEngine.a(uCloudRtcCameraEventListener);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setCameraId(int i2) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.setCameraId(i2)) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setClassType(UCloudRtcSdkRoomType uCloudRtcSdkRoomType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.d(uCloudRtcSdkRoomType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_CODE_OK;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setCustomizedVideoParam(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        a.w wVar = new a.w();
        uCloudRtcSdkVideoProfile.toCore(uCloudRtcSdkVideoProfile, wVar);
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(wVar));
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setEventListener(UCloudRtcSdkEventListener uCloudRtcSdkEventListener) {
        UCloudRtcCallBackAdapter uCloudRtcCallBackAdapter = new UCloudRtcCallBackAdapter(uCloudRtcSdkEventListener);
        this.mCallBackAdapter = uCloudRtcCallBackAdapter;
        b.a aVar = coreRtcEngine;
        if (aVar != null) {
            aVar.b(uCloudRtcCallBackAdapter);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setFlashOn(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.setFlashOn(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setRenderViewMode(boolean z2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, UCloudRtcSdkScaleType uCloudRtcSdkScaleType) {
        if (checkCoreExisted()) {
            coreRtcEngine.a(z2, uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, (StreamInfo) null), uCloudRtcSdkScaleType.ordinal());
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setScreenProfile(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.c(uCloudRtcSdkVideoProfile.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setSpeakerOn(boolean z2) {
        if (checkCoreExisted()) {
            coreRtcEngine.setSpeakerOn(z2);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setStreamRole(UCloudRtcSdkStreamRole uCloudRtcSdkStreamRole) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.b(uCloudRtcSdkStreamRole.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_CODE_OK;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void setVideoPreProcessListener(UcloudRtcVideoFramePreProcessListener ucloudRtcVideoFramePreProcessListener) {
        if (checkCoreExisted()) {
            coreRtcEngine.a(ucloudRtcVideoFramePreProcessListener);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode setVideoProfile(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.i(uCloudRtcSdkVideoProfile.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode startCameraPreview(Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered) {
        if (checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(obj, uCloudRtcSdkScaleType.ordinal(), uCloudRTCFirstFrameRendered != null ? new UCloudRtcFirstFrameAdapter(uCloudRTCFirstFrameRendered) : null));
        }
        return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean startPlayAudioFile(String str) {
        if (checkCoreExisted()) {
            return coreRtcEngine.startPlayAudioFile(str);
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void startRecord(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile) {
        if (checkCoreExisted()) {
            coreRtcEngine.b(uCloudRtcSdkMixProfile);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void startRelay(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile) {
        if (checkCoreExisted()) {
            coreRtcEngine.a(uCloudRtcSdkMixProfile);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode startRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        StreamInfo streamInfo = new StreamInfo();
        uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
        UCloudRtcFirstFrameAdapter uCloudRtcFirstFrameAdapter = uCloudRTCFirstFrameRendered != null ? new UCloudRtcFirstFrameAdapter(uCloudRTCFirstFrameRendered) : null;
        if (uCloudRtcSdkScaleType == null) {
            uCloudRtcSdkScaleType = UCloudRtcSdkScaleType.UCLOUD_RTC_SDK_SCALE_ASPECT_FILL;
        }
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.b(streamInfo, obj, uCloudRtcSdkScaleType.ordinal(), uCloudRtcFirstFrameAdapter));
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void stopPlayAudioFile() {
        if (checkCoreExisted()) {
            coreRtcEngine.stopPlayAudioFile();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode stopPreview(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(uCloudRtcSdkMediaType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void stopRecord() {
        if (checkCoreExisted()) {
            coreRtcEngine.stopRecord();
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void stopRelay(String[] strArr) {
        if (checkCoreExisted()) {
            coreRtcEngine.stopRelay(strArr);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode stopRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        StreamInfo streamInfo = new StreamInfo();
        uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(streamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode subscribe(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        StreamInfo streamInfo = new StreamInfo();
        uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.b(streamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode switchCamera() {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.switchCamera()) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode switchCameraSkipSameSide() {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.switchCameraSkipSameSide()) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void takeSnapShot(boolean z2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, UCloudRTCScreenShot uCloudRTCScreenShot) {
        if (checkCoreExisted()) {
            coreRtcEngine.a(z2, uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, (StreamInfo) null), uCloudRTCScreenShot);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode unPublish(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.e(uCloudRtcSdkMediaType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode unPublishOnly(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        return checkCoreExisted() ? UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.g(uCloudRtcSdkMediaType.ordinal())) : UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode unSubscribe(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        if (!checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
        }
        StreamInfo streamInfo = new StreamInfo();
        uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
        return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.c(streamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public void updateMixConfig(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile) {
        if (checkCoreExisted()) {
            coreRtcEngine.c(uCloudRtcSdkMixProfile);
        }
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public boolean startPlayAudioFile(String str, boolean z2, boolean z3) {
        if (checkCoreExisted()) {
            return coreRtcEngine.startPlayAudioFile(str, z2, z3);
        }
        return false;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode stopPreview(UCloudRtcSdkMediaType uCloudRtcSdkMediaType, Object obj) {
        if (checkCoreExisted()) {
            return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(uCloudRtcSdkMediaType.ordinal(), obj));
        }
        return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }

    @Override // com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine
    public UCloudRtcSdkErrorCode stopRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj) {
        if (checkCoreExisted()) {
            StreamInfo streamInfo = new StreamInfo();
            uCloudRtcSdkStreamInfo.toCore(uCloudRtcSdkStreamInfo, streamInfo);
            return UCloudRtcSdkErrorCode.matchValue(coreRtcEngine.a(streamInfo, obj));
        }
        return UCloudRtcSdkErrorCode.NET_ERR_NOT_INIT;
    }
}
