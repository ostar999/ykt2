package com.plv.rtc.urtc;

import android.app.Activity;
import android.content.Intent;
import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkRoomType;
import com.plv.rtc.urtc.enummeration.URTCSdkScaleType;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamRole;
import com.plv.rtc.urtc.enummeration.URTCSdkVideoProfile;
import com.plv.rtc.urtc.listener.URTCFirstFrameRendered;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.plv.rtc.urtc.model.URTCSdkAuthInfo;
import com.plv.rtc.urtc.model.URTCSdkMixProfile;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import java.util.List;

/* loaded from: classes5.dex */
public interface PLVURTCEngine {
    void adjustRecordVolume(int i2);

    void changePushResolution(URTCSdkVideoProfile uRTCSdkVideoProfile);

    int configLocalAudioPublish(boolean z2);

    int configLocalCameraPublish(boolean z2);

    int configLocalScreenPublish(boolean z2);

    void controlAudio(boolean z2);

    void controlAudioPlayOut(boolean z2);

    void controlAudioRecord(boolean z2);

    void controlLocalVideo(boolean z2);

    void destroy();

    URTCSdkAudioDevice getDefaultAudioDevice();

    boolean getSpeakerOn();

    boolean isAudioOnlyMode();

    boolean isAutoPublish();

    boolean isAutoSubscribe();

    boolean isLocalAudioPublishEnabled();

    boolean isLocalCameraPublishEnabled();

    boolean isLocalScreenPublishEnabled();

    int joinChannel(URTCSdkAuthInfo uRTCSdkAuthInfo);

    void kickOffOthers(int i2, List<String> list);

    int leaveChannel();

    int leaveChannelNonStopLocalPreview();

    void lockExtendDeviceInputBuffer();

    void messageNotify(String str);

    int muteLocalMic(boolean z2);

    int muteLocalVideo(boolean z2, URTCSdkMediaType uRTCSdkMediaType);

    int muteRemoteAudio(String str, boolean z2);

    int muteRemoteScreen(String str, boolean z2);

    int muteRemoteVideo(String str, boolean z2);

    void onScreenCaptureResult(Intent intent);

    int publish(URTCSdkMediaType uRTCSdkMediaType, boolean z2, boolean z3);

    void releaseExtendDeviceInputBuffer();

    int renderLocalView(URTCSdkStreamInfo uRTCSdkStreamInfo, Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered);

    void requestScreenCapture(Activity activity);

    void setAudioDevice(URTCSdkAudioDevice uRTCSdkAudioDevice);

    int setAudioOnlyMode(boolean z2);

    int setAutoPublish(boolean z2);

    int setAutoSubscribe(boolean z2);

    int setClassType(URTCSdkRoomType uRTCSdkRoomType);

    void setEventListener(URtcSdkEventListener uRtcSdkEventListener);

    void setFlashOn(boolean z2);

    void setRenderViewMode(boolean z2, URTCSdkStreamInfo uRTCSdkStreamInfo, URTCSdkScaleType uRTCSdkScaleType);

    int setScreenProfile(URTCSdkVideoProfile uRTCSdkVideoProfile);

    void setSpeakerOn(boolean z2);

    int setStreamRole(URTCSdkStreamRole uRTCSdkStreamRole);

    int setVideoProfile(URTCSdkVideoProfile uRTCSdkVideoProfile);

    int startCameraPreview(Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered);

    void startRelay(URTCSdkMixProfile uRTCSdkMixProfile);

    int startRemoteView(URTCSdkStreamInfo uRTCSdkStreamInfo, Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered);

    int stopPreview(URTCSdkMediaType uRTCSdkMediaType);

    int stopPreview(URTCSdkMediaType uRTCSdkMediaType, Object obj);

    void stopRelay(String[] strArr);

    int stopRemoteView(URTCSdkStreamInfo uRTCSdkStreamInfo);

    int subscribe(URTCSdkStreamInfo uRTCSdkStreamInfo);

    int switchCamera();

    int unPublish(URTCSdkMediaType uRTCSdkMediaType);

    int unPublishOnly(URTCSdkMediaType uRTCSdkMediaType);

    int unSubscribe(URTCSdkStreamInfo uRTCSdkStreamInfo);

    void updateMixConfig(URTCSdkMixProfile uRTCSdkMixProfile);
}
