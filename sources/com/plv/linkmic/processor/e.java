package com.plv.linkmic.processor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;

/* loaded from: classes4.dex */
public class e implements c {
    @Override // com.plv.linkmic.processor.c
    public boolean a(PLVLinkMicEngineToken pLVLinkMicEngineToken, PLVRTCConfig pLVRTCConfig, Context context, a aVar) {
        return false;
    }

    @Override // com.plv.linkmic.processor.b
    public int addPublishStreamUrl(String str, boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int adjustRecordingSignalVolume(int i2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public SurfaceView createRendererView(Context context) {
        return null;
    }

    @Override // com.plv.linkmic.processor.b
    public TextureView createTextureRenderView(Context context) {
        return null;
    }

    @Override // com.plv.linkmic.processor.b
    public void destroy() {
    }

    @Override // com.plv.linkmic.processor.b
    public int enableLocalVideo(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int enableTorch(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public String getLinkMicUid() {
        return null;
    }

    @Override // com.plv.linkmic.processor.b
    public boolean isScreenSharing() {
        return false;
    }

    @Override // com.plv.linkmic.processor.b
    public int joinChannel(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void leaveChannel(boolean z2) {
    }

    @Override // com.plv.linkmic.processor.b
    public int muteAllRemoteAudio(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int muteAllRemoteVideo(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int muteLocalAudio(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int muteLocalVideo(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int muteRemoteAudio(String str, boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int muteRemoteVideo(String str, boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void releaseRenderView(View view) {
    }

    @Override // com.plv.linkmic.processor.b
    public int removePublishStreamUrl(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int renewToken(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void requestAndStartShareScreen(Activity activity) {
    }

    @Override // com.plv.linkmic.processor.b
    public void setBitrate(int i2) {
    }

    @Override // com.plv.linkmic.processor.b
    public int setCameraZoomRatio(float f2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setLocalPreviewMirror(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setLocalPushMirror(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setOnlyAudio(boolean z2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setPushPictureResolutionType(int i2) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void setScreenCaptureSource(Intent intent) {
    }

    @Override // com.plv.linkmic.processor.b
    public int setVideoMuteImage(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setupLocalVideo(View view, int i2, String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int setupRemoteVideo(View view, int i2, String str, int i3) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void setupRemoteVideo(View view, int i2, String str) {
    }

    @Override // com.plv.linkmic.processor.b
    public void startPreview() {
    }

    @Override // com.plv.linkmic.processor.b
    @Deprecated
    public int startPushImageStream(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int startShareScreen() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    @Deprecated
    public int stopPushImageStream() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int stopShareScreen() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void switchBeauty(boolean z2) {
    }

    @Override // com.plv.linkmic.processor.b
    public void switchCamera() {
    }

    @Override // com.plv.linkmic.processor.b
    public int switchRoleToAudience() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public int switchRoleToBroadcaster() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void takeSnapshot(String str, PLVSugarUtil.Consumer<Bitmap> consumer) {
    }

    @Override // com.plv.linkmic.processor.b
    @Deprecated
    public int updateSEIFrameTimeStamp(String str) {
        return 0;
    }
}
