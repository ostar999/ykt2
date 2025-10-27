package com.plv.rtc.trtc;

import android.graphics.Bitmap;
import android.view.SurfaceView;
import androidx.annotation.FloatRange;
import com.plv.rtc.trtc.PLVTRTCDef;

/* loaded from: classes5.dex */
public interface PLVTRTCEngine {
    void destroy();

    void enableAudioVolumeEvaluation(int i2);

    boolean enableTorch(boolean z2);

    void enterRoom(PLVTRTCDef.TRTCParams tRTCParams, int i2);

    void exitRoom();

    void muteLocalAudio(boolean z2);

    void muteLocalVideo(boolean z2);

    int setCameraZoomRatio(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    void setListener(PLVTRTCEventListener pLVTRTCEventListener);

    void setLocalRenderParams(PLVTRTCDef.TRTCRenderParams tRTCRenderParams);

    void setLocalViewFillMode(int i2);

    void setMixTranscodingConfig(PLVTRTCDef.TRTCTranscodingConfig tRTCTranscodingConfig);

    void setRemoteRenderParams(String str, int i2, PLVTRTCDef.TRTCRenderParams tRTCRenderParams);

    void setVideoEncoderMirror(boolean z2);

    void setVideoEncoderParam(PLVTRTCDef.TRTCVideoEncParam tRTCVideoEncParam);

    void setVideoMuteImage(Bitmap bitmap, int i2);

    void startLocalAudio(int i2);

    void startLocalPreview(boolean z2, SurfaceView surfaceView);

    void startPublishCDNStream(PLVTRTCDef.TRTCPublishCDNParam tRTCPublishCDNParam);

    void startPublishing(String str, int i2);

    void startRemoteView(String str, int i2, SurfaceView surfaceView);

    void startScreenCapture(int i2, PLVTRTCDef.TRTCVideoEncParam tRTCVideoEncParam);

    void stopLocalPreview();

    void stopPublishCDNStream();

    void stopPublishing();

    void stopRemoteView(String str, int i2);

    void stopScreenCapture();

    void switchBeauty(boolean z2);

    void switchCamera();

    void switchRole(int i2);
}
