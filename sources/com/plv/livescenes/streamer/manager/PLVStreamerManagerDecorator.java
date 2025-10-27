package com.plv.livescenes.streamer.manager;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;
import com.easefun.polyv.livescenes.streamer.listener.IPLVSStreamerOnLiveStatusChangeListener;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnEnableLocalCameraListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStreamingStartListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveTimingListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnServerTimeoutDueToNetBrokenListener;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.listener.PLVStreamerListener;
import com.plv.livescenes.streamer.mix.PLVRTCMixUser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
class PLVStreamerManagerDecorator implements IPLVStreamerManager {
    private IPLVStreamerManager target;

    public PLVStreamerManagerDecorator(IPLVStreamerManager iPLVStreamerManager) {
        this.target = iPLVStreamerManager;
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addEventHandler(PLVStreamerEventListener pLVStreamerEventListener) {
        this.target.addEventHandler(pLVStreamerEventListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addGetSessionIdFromServerListener(IPLVOnGetSessionIdInnerListener iPLVOnGetSessionIdInnerListener) {
        this.target.addGetSessionIdFromServerListener(iPLVOnGetSessionIdInnerListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addOnEnableLocalCameraListener(IPLVStreamerOnEnableLocalCameraListener iPLVStreamerOnEnableLocalCameraListener) {
        this.target.addOnEnableLocalCameraListener(iPLVStreamerOnEnableLocalCameraListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addOnLiveStreamingStartListener(IPLVStreamerOnLiveStreamingStartListener iPLVStreamerOnLiveStreamingStartListener) {
        this.target.addOnLiveStreamingStartListener(iPLVStreamerOnLiveStreamingStartListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void addStreamerServerTimeoutListener(IPLVStreamerOnServerTimeoutDueToNetBrokenListener iPLVStreamerOnServerTimeoutDueToNetBrokenListener) {
        this.target.addStreamerServerTimeoutListener(iPLVStreamerOnServerTimeoutDueToNetBrokenListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void adjustRecordingSignalVolume(int i2) {
        this.target.adjustRecordingSignalVolume(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public SurfaceView createRendererView(Context context) {
        return this.target.createRendererView(context);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void destroy() {
        this.target.destroy();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void disableAutoJoinChannel() {
        this.target.disableAutoJoinChannel();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void enableLocalCamera(boolean z2) {
        this.target.enableLocalCamera(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void enableLocalMicrophone(boolean z2) {
        this.target.enableLocalMicrophone(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean enableTorch(boolean z2) {
        return this.target.enableTorch(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void exitScreenCapture() {
        this.target.exitScreenCapture();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getCurrentNetQuality() {
        return this.target.getCurrentNetQuality();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public String getLinkMicUid() {
        return this.target.getLinkMicUid();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void getLinkStatus(String str, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener) {
        this.target.getLinkStatus(str, iPLVLinkMicDataRepoListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getLiveDuration() {
        return this.target.getLiveDuration();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public long getLiveStartTimestamp() {
        return this.target.getLiveStartTimestamp();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public int getMaxSupportedBitrate() {
        return this.target.getMaxSupportedBitrate();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void initEngine(PLVLinkMicEngineParam pLVLinkMicEngineParam, PLVStreamerListener pLVStreamerListener) {
        this.target.initEngine(pLVLinkMicEngineParam, pLVStreamerListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean isLiveStreaming() {
        return this.target.isLiveStreaming();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean isScreenSharing() {
        return this.target.isScreenSharing();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void joinChannel() {
        this.target.joinChannel();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void leaveChannel() {
        this.target.leaveChannel();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void listenLiveStatusChange(int i2, int i3, IPLVSStreamerOnLiveStatusChangeListener iPLVSStreamerOnLiveStatusChangeListener) {
        this.target.listenLiveStatusChange(i2, i3, iPLVSStreamerOnLiveStatusChangeListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void releaseRenderView(SurfaceView surfaceView) {
        this.target.releaseRenderView(surfaceView);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void removeEventHandler(PLVStreamerEventListener pLVStreamerEventListener) {
        this.target.removeEventHandler(pLVStreamerEventListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void requestScreenCapture(Activity activity) {
        this.target.requestScreenCapture(activity);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void resetRequestPermissionList(ArrayList<String> arrayList) {
        this.target.resetRequestPermissionList(arrayList);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setBitrate(int i2) {
        this.target.setBitrate(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setCameraZoomRatio(float f2) {
        this.target.setCameraZoomRatio(f2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setLocalPreviewMirror(boolean z2) {
        this.target.setLocalPreviewMirror(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setLocalPushMirror(boolean z2) {
        this.target.setLocalPushMirror(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setMixLayoutType(int i2) {
        this.target.setMixLayoutType(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setOnLiveTimingListener(IPLVStreamerOnLiveTimingListener iPLVStreamerOnLiveTimingListener) {
        this.target.setOnLiveTimingListener(iPLVStreamerOnLiveTimingListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public boolean setOnlyAudio(boolean z2) {
        return this.target.setOnlyAudio(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setPushPictureResolutionType(int i2) {
        this.target.setPushPictureResolutionType(i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.target.setPushResolutionRatio(pushResolutionRatio);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setShareScreenListener(IPLVScreenShareListener iPLVScreenShareListener) {
        this.target.setShareScreenListener(iPLVScreenShareListener);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupLocalVideo(SurfaceView surfaceView) {
        this.target.setupLocalVideo(surfaceView);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupRemoteVideo(SurfaceView surfaceView, String str) {
        this.target.setupRemoteVideo(surfaceView, str);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startLiveStream() {
        this.target.startLiveStream();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startPreview() {
        this.target.startPreview();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void stopLiveStream() {
        this.target.stopLiveStream();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchBeauty(boolean z2) {
        this.target.switchBeauty(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchCamera(boolean z2) {
        this.target.switchCamera(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchRoleToAudience() {
        this.target.switchRoleToAudience();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void switchRoleToBroadcaster() {
        this.target.switchRoleToBroadcaster();
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void updateMixLayoutUsers(List<? extends PLVRTCMixUser> list) {
        this.target.updateMixLayoutUsers(list);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void leaveChannel(boolean z2) {
        this.target.leaveChannel(z2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void setupLocalVideo(SurfaceView surfaceView, int i2) {
        this.target.setupLocalVideo(surfaceView, i2);
    }

    @Override // com.plv.livescenes.streamer.IPLVStreamerManager
    public void startLiveStream(boolean z2) {
        this.target.startLiveStream(z2);
    }
}
