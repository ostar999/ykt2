package com.plv.rtc.zrtc;

import com.plv.rtc.zrtc.enummeration.ZRTCVideoMirrorMode;
import com.plv.rtc.zrtc.model.ZRTCCanvas;
import com.plv.rtc.zrtc.model.ZRTCRoomConfig;
import com.plv.rtc.zrtc.model.ZRTCUser;
import com.plv.rtc.zrtc.model.ZRTCVideoConfig;

/* loaded from: classes5.dex */
public interface PLVZRTCEngine {
    void enableHardwareDecoder(boolean z2);

    void loginRoom(String str, ZRTCUser zRTCUser, ZRTCRoomConfig zRTCRoomConfig);

    void logoutRoom(String str);

    void mutePublishStreamAudio(boolean z2);

    void mutePublishStreamVideo(boolean z2);

    void setVideoConfig(ZRTCVideoConfig zRTCVideoConfig);

    void setVideoMirrorMode(ZRTCVideoMirrorMode zRTCVideoMirrorMode);

    void startPlayingStream(String str, ZRTCCanvas zRTCCanvas);

    void startPreview(ZRTCCanvas zRTCCanvas);

    void startPublishingStream(String str);

    void stopPlayingStream(String str);

    void useFrontCamera(boolean z2);
}
