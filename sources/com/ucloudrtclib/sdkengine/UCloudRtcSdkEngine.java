package com.ucloudrtclib.sdkengine;

import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAuthInfo;
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
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCScreenShot;
import java.util.List;
import org.wrtca.customize.RtcNativeOperation;

/* loaded from: classes6.dex */
public interface UCloudRtcSdkEngine {
    public static final int SCREEN_CAPTURE_REQUEST_CODE = 1000;

    void addMixStream(UCloudRtcSdkStreamInfo[] uCloudRtcSdkStreamInfoArr);

    UCloudRtcSdkErrorCode adjustPlaybackSignalVolume(double d3);

    void adjustRecordVolume(int i2);

    UCloudRtcSdkErrorCode adjustUserPlaybackSignalVolume(String str, double d3);

    void changePushResolution(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile);

    UCloudRtcSdkErrorCode configLocalAudioPublish(boolean z2);

    UCloudRtcSdkErrorCode configLocalCameraPublish(boolean z2);

    UCloudRtcSdkErrorCode configLocalScreenPublish(boolean z2);

    void controlAudio(boolean z2);

    void controlAudioPlayOut(boolean z2);

    void controlAudioRecord(boolean z2);

    void controlLocalVideo(boolean z2);

    String copyAssetsFileToSdcard(String str);

    UCloudRtcSdkErrorCode cropPushResolution(int i2, int i3);

    void delMixStream(UCloudRtcSdkStreamInfo[] uCloudRtcSdkStreamInfoArr);

    void enableBTModule();

    UCloudRtcSdkAudioDevice getDefaultAudioDevice();

    RtcNativeOperation getNativeOpInterface();

    boolean getSpeakerOn();

    boolean isAudioOnlyMode();

    boolean isAutoPublish();

    boolean isAutoSubscribe();

    boolean isLocalAudioPublishEnabled();

    boolean isLocalCameraPublishEnabled();

    boolean isLocalScreenPublishEnabled();

    UCloudRtcSdkErrorCode joinChannel(UCloudRtcSdkAuthInfo uCloudRtcSdkAuthInfo);

    void kickOffOthers(int i2, List<String> list);

    UCloudRtcSdkErrorCode leaveChannel();

    UCloudRtcSdkErrorCode leaveChannelNonStopLocalPreview();

    void lockExtendDeviceInputBuffer();

    void messageNotify(String str);

    UCloudRtcSdkErrorCode muteLocalAudio(boolean z2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType);

    UCloudRtcSdkErrorCode muteLocalMic(boolean z2);

    UCloudRtcSdkErrorCode muteLocalVideo(boolean z2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType);

    UCloudRtcSdkErrorCode muteRemoteAudio(String str, boolean z2);

    UCloudRtcSdkErrorCode muteRemoteScreen(String str, boolean z2);

    UCloudRtcSdkErrorCode muteRemoteScreenSound(String str, boolean z2);

    UCloudRtcSdkErrorCode muteRemoteVideo(String str, boolean z2);

    void pauseAudioFile();

    UCloudRtcSdkErrorCode publish(UCloudRtcSdkMediaType uCloudRtcSdkMediaType, boolean z2, boolean z3);

    void queryMix();

    void releaseExtendDeviceInputBuffer();

    UCloudRtcSdkErrorCode renderLocalView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered);

    void resumeAudioFile();

    void setAudioDevice(UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice);

    UCloudRtcSdkErrorCode setAudioOnlyMode(boolean z2);

    UCloudRtcSdkErrorCode setAutoPublish(boolean z2);

    UCloudRtcSdkErrorCode setAutoSubscribe(boolean z2);

    void setCameraEventListener(UCloudRtcCameraEventListener uCloudRtcCameraEventListener);

    UCloudRtcSdkErrorCode setCameraId(int i2);

    UCloudRtcSdkErrorCode setClassType(UCloudRtcSdkRoomType uCloudRtcSdkRoomType);

    UCloudRtcSdkErrorCode setCustomizedVideoParam(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile);

    void setEventListener(UCloudRtcSdkEventListener uCloudRtcSdkEventListener);

    void setFlashOn(boolean z2);

    void setRenderViewMode(boolean z2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, UCloudRtcSdkScaleType uCloudRtcSdkScaleType);

    UCloudRtcSdkErrorCode setScreenProfile(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile);

    void setSpeakerOn(boolean z2);

    UCloudRtcSdkErrorCode setStreamRole(UCloudRtcSdkStreamRole uCloudRtcSdkStreamRole);

    void setVideoPreProcessListener(UcloudRtcVideoFramePreProcessListener ucloudRtcVideoFramePreProcessListener);

    UCloudRtcSdkErrorCode setVideoProfile(UCloudRtcSdkVideoProfile uCloudRtcSdkVideoProfile);

    UCloudRtcSdkErrorCode startCameraPreview(Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered);

    boolean startPlayAudioFile(String str);

    boolean startPlayAudioFile(String str, boolean z2, boolean z3);

    void startRecord(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile);

    void startRelay(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile);

    UCloudRtcSdkErrorCode startRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj, UCloudRtcSdkScaleType uCloudRtcSdkScaleType, UCloudRTCFirstFrameRendered uCloudRTCFirstFrameRendered);

    void stopPlayAudioFile();

    UCloudRtcSdkErrorCode stopPreview(UCloudRtcSdkMediaType uCloudRtcSdkMediaType);

    UCloudRtcSdkErrorCode stopPreview(UCloudRtcSdkMediaType uCloudRtcSdkMediaType, Object obj);

    void stopRecord();

    void stopRelay(String[] strArr);

    UCloudRtcSdkErrorCode stopRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    UCloudRtcSdkErrorCode stopRemoteView(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, Object obj);

    UCloudRtcSdkErrorCode subscribe(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    UCloudRtcSdkErrorCode switchCamera();

    UCloudRtcSdkErrorCode switchCameraSkipSameSide();

    void takeSnapShot(boolean z2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, UCloudRTCScreenShot uCloudRTCScreenShot);

    UCloudRtcSdkErrorCode unPublish(UCloudRtcSdkMediaType uCloudRtcSdkMediaType);

    UCloudRtcSdkErrorCode unPublishOnly(UCloudRtcSdkMediaType uCloudRtcSdkMediaType);

    UCloudRtcSdkErrorCode unSubscribe(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void updateMixConfig(UCloudRtcSdkMixProfile uCloudRtcSdkMixProfile);
}
