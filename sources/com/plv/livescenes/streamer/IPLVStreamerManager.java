package com.plv.livescenes.streamer;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.easefun.polyv.livescenes.streamer.listener.IPLVSStreamerOnLiveStatusChangeListener;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
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
public interface IPLVStreamerManager {
    public static final int ERROR_PERMISSION_DENIED = 1060501;
    public static final int SERVER_BITRATE_HIGH = 360;
    public static final int SERVER_BITRATE_SUPER = 720;

    void addEventHandler(PLVStreamerEventListener pLVStreamerEventListener);

    void addGetSessionIdFromServerListener(IPLVOnGetSessionIdInnerListener iPLVOnGetSessionIdInnerListener);

    void addOnEnableLocalCameraListener(IPLVStreamerOnEnableLocalCameraListener iPLVStreamerOnEnableLocalCameraListener);

    void addOnLiveStreamingStartListener(IPLVStreamerOnLiveStreamingStartListener iPLVStreamerOnLiveStreamingStartListener);

    void addStreamerServerTimeoutListener(IPLVStreamerOnServerTimeoutDueToNetBrokenListener iPLVStreamerOnServerTimeoutDueToNetBrokenListener);

    void adjustRecordingSignalVolume(int i2);

    SurfaceView createRendererView(Context context);

    void destroy();

    void disableAutoJoinChannel();

    void enableLocalCamera(boolean z2);

    void enableLocalMicrophone(boolean z2);

    boolean enableTorch(boolean z2);

    void exitScreenCapture();

    int getCurrentNetQuality();

    String getLinkMicUid();

    void getLinkStatus(String str, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener);

    int getLiveDuration();

    long getLiveStartTimestamp();

    int getMaxSupportedBitrate();

    void initEngine(@NonNull PLVLinkMicEngineParam pLVLinkMicEngineParam, PLVStreamerListener pLVStreamerListener);

    boolean isLiveStreaming();

    boolean isScreenSharing();

    void joinChannel();

    void leaveChannel();

    void leaveChannel(boolean z2);

    void listenLiveStatusChange(int i2, int i3, IPLVSStreamerOnLiveStatusChangeListener iPLVSStreamerOnLiveStatusChangeListener);

    void releaseRenderView(SurfaceView surfaceView);

    void removeEventHandler(PLVStreamerEventListener pLVStreamerEventListener);

    void requestScreenCapture(Activity activity);

    void resetRequestPermissionList(ArrayList<String> arrayList);

    void setBitrate(int i2);

    void setCameraZoomRatio(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    void setLocalPreviewMirror(boolean z2);

    void setLocalPushMirror(boolean z2);

    void setMixLayoutType(int i2);

    void setOnLiveTimingListener(IPLVStreamerOnLiveTimingListener iPLVStreamerOnLiveTimingListener);

    boolean setOnlyAudio(boolean z2);

    void setPushPictureResolutionType(int i2);

    void setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio);

    void setShareScreenListener(IPLVScreenShareListener iPLVScreenShareListener);

    void setupLocalVideo(SurfaceView surfaceView);

    void setupLocalVideo(SurfaceView surfaceView, int i2);

    void setupRemoteVideo(SurfaceView surfaceView, String str);

    void startLiveStream();

    void startLiveStream(boolean z2);

    void startPreview();

    void stopLiveStream();

    void switchBeauty(boolean z2);

    void switchCamera(boolean z2);

    void switchRoleToAudience();

    void switchRoleToBroadcaster();

    void updateMixLayoutUsers(List<? extends PLVRTCMixUser> list);
}
