package com.plv.linkmic.processor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;

/* loaded from: classes4.dex */
public interface b {
    int addPublishStreamUrl(String str, boolean z2);

    int adjustRecordingSignalVolume(@IntRange(from = 0, to = 400) int i2);

    SurfaceView createRendererView(Context context);

    TextureView createTextureRenderView(Context context);

    void destroy();

    int enableLocalVideo(boolean z2);

    int enableTorch(boolean z2);

    String getLinkMicUid();

    boolean isScreenSharing();

    int joinChannel(String str);

    void leaveChannel(boolean z2);

    int muteAllRemoteAudio(boolean z2);

    int muteAllRemoteVideo(boolean z2);

    int muteLocalAudio(boolean z2);

    int muteLocalVideo(boolean z2);

    int muteRemoteAudio(String str, boolean z2);

    int muteRemoteVideo(String str, boolean z2);

    void releaseRenderView(View view);

    int removePublishStreamUrl(String str);

    int renewToken(String str);

    void requestAndStartShareScreen(Activity activity);

    void setBitrate(int i2);

    int setCameraZoomRatio(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    int setLocalPreviewMirror(boolean z2);

    int setLocalPushMirror(boolean z2);

    int setOnlyAudio(boolean z2);

    int setPushPictureResolutionType(int i2);

    int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio);

    void setScreenCaptureSource(Intent intent);

    int setVideoMuteImage(String str);

    int setupLocalVideo(View view, int i2, String str);

    int setupRemoteVideo(View view, int i2, String str, int i3);

    void setupRemoteVideo(View view, int i2, String str);

    void startPreview();

    int startPushImageStream(String str);

    int startShareScreen();

    int stopPushImageStream();

    int stopShareScreen();

    void switchBeauty(boolean z2);

    void switchCamera();

    int switchRoleToAudience();

    int switchRoleToBroadcaster();

    void takeSnapshot(String str, PLVSugarUtil.Consumer<Bitmap> consumer);

    int updateSEIFrameTimeStamp(String str);
}
