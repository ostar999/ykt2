package com.plv.rtc;

import android.content.Context;
import android.content.Intent;
import android.view.SurfaceView;
import androidx.annotation.FloatRange;
import com.plv.rtc.b.a;
import com.plv.rtc.model.PLVARTCEncoderConfiguration;
import com.plv.rtc.transcode.IPLVARTCTranscoding;

/* loaded from: classes5.dex */
public abstract class PLVARTCEngine {
    public static PLVARTCEngine create(Context context, String str, IPLVARtcEngineEventHandler iPLVARtcEngineEventHandler) {
        return new a(context, str, iPLVARtcEngineEventHandler);
    }

    public abstract int addPublishStreamUrl(String str, boolean z2);

    public abstract int adjustRecordingSignalVolume(int i2);

    public abstract SurfaceView createRenderView(Context context);

    public abstract void destroy();

    public abstract int enableAudioVolumeIndication(int i2, int i3);

    public abstract int enableDualStreamMode(boolean z2);

    public abstract int enableLocalAudio(boolean z2);

    public abstract int enableLocalVideo(boolean z2);

    public abstract int enableVideo();

    public abstract int enableWebSdkInteroperability(boolean z2);

    public abstract boolean isSpeakerphoneEnabled();

    public abstract int joinChannel(String str, String str2, String str3, int i2);

    public abstract int leaveChannel();

    public abstract int muteLocalAudioStream(boolean z2);

    public abstract int muteLocalVideoStream(boolean z2);

    public abstract int recoverDefaultSource();

    public abstract int removePublishStreamUrl(String str);

    public abstract int renewToken(String str);

    public abstract int setAudioProfile(int i2, int i3);

    public abstract int setCameraTorchOn(boolean z2);

    public abstract int setCameraZoomFactor(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    public abstract int setChannelProfile(int i2);

    public abstract int setClientRole(int i2);

    public abstract int setLiveTranscoding(IPLVARTCTranscoding iPLVARTCTranscoding);

    public abstract int setLocalVideoMirrorMode(int i2);

    public abstract int setLogFile(String str);

    public abstract int setParameters(String str);

    public abstract int setVideoEncoderConfiguration(PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration);

    public abstract int setupLocalVideo(SurfaceView surfaceView, int i2, int i3);

    public abstract int setupRemoteVideo(SurfaceView surfaceView, int i2, int i3);

    public abstract int startPreview();

    public abstract int startShareScreen(PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration, Intent intent);

    public abstract int stopPreview();

    public abstract int stopShareScreen();

    public abstract void switchBeauty(boolean z2);

    public abstract int switchCamera();
}
