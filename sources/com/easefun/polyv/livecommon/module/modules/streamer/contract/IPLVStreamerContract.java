package com.easefun.polyv.livecommon.module.modules.streamer.contract;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.data.PLVStreamerData;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.socket.user.PLVSocketUserBean;
import io.socket.client.Ack;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVStreamerContract {

    public interface IStreamerPresenter {
        void closeAllUserLinkMic();

        void controlUserLinkMic(int position, boolean isAllowJoin);

        void controlUserLinkMicInLinkMicList(int position, boolean isAllowJoin);

        SurfaceView createRenderView(Context context);

        void destroy();

        boolean enableLocalVideo(boolean enable);

        boolean enableRecordingAudioVolume(boolean enable);

        boolean enableTorch(boolean enable);

        void exitShareScreen();

        int getBitrate();

        @NonNull
        PLVStreamerData getData();

        int getMaxBitrate();

        int getNetworkQuality();

        int getStreamerStatus();

        void guestTryJoinLinkMic();

        void init();

        boolean isRecoverStream();

        boolean isScreenSharing();

        void muteAllUserAudio(boolean isMute);

        void muteUserMedia(int position, boolean isVideoType, boolean isMute);

        void muteUserMediaInLinkMicList(int position, boolean isVideoType, boolean isMute);

        void registerView(@NonNull IStreamerView v2);

        void releaseRenderView(SurfaceView renderView);

        void requestMemberList();

        void requestShareScreen(Activity activity);

        void setBitrate(int bitrate);

        boolean setCameraDirection(boolean front);

        void setDocumentAndStreamerViewPosition(boolean documentInMainScreen);

        void setFrontCameraMirror(boolean enable);

        void setMixLayoutType(int mixLayoutType);

        void setPushPictureResolutionType(int type);

        void setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio resolutionRatio);

        void setRecoverStream(boolean recoverStream);

        void setUserPermissionSpeaker(String userId, boolean isSetPermission, Ack ack);

        void setupRenderView(SurfaceView renderView, String linkMicId);

        void startLiveStream();

        void stopLiveStream();

        void unregisterView(IStreamerView v2);

        void zoomLocalCamera(float scaleFactor);
    }

    public interface IStreamerView {
        void onAddMemberListData(int pos);

        void onCameraDirection(boolean front, int pos);

        void onDocumentStreamerViewChange(boolean documentInMainScreen);

        void onFirstScreenChange(String linkMicUserId, boolean isFirstScreen);

        void onGuestMediaStatusChanged(int pos);

        void onGuestRTCStatusChanged(int pos);

        void onLocalUserMicVolumeChanged(int volume);

        void onNetworkQuality(int quality);

        void onReachTheInteractNumLimit();

        void onRemoteUserVolumeChanged(List<PLVMemberItemDataBean> linkMicList);

        void onRemoveMemberListData(int pos);

        void onScreenShareChange(int position, boolean isShare, int extra);

        void onSetPermissionChange(String type, boolean isGranted, boolean isCurrentUser, PLVSocketUserBean user);

        void onShowNetBroken();

        void onStatesToStreamEnded();

        void onStatesToStreamStarted();

        void onStreamLiveStatusChanged(boolean isLive);

        void onStreamerEngineCreatedSuccess(String linkMicUid, List<PLVLinkMicItemDataBean> linkMicList);

        void onStreamerError(int errorCode, Throwable throwable);

        void onUpdateMemberListData(List<PLVMemberItemDataBean> dataBeanList);

        void onUpdateSocketUserData(int pos);

        void onUpdateStreamerTime(int secondsSinceStartTiming);

        void onUserMuteAudio(final String uid, final boolean mute, int streamerListPos, int memberListPos);

        void onUserMuteVideo(final String uid, final boolean mute, int streamerListPos, int memberListPos);

        void onUserRequest(String uid);

        void onUsersJoin(List<PLVLinkMicItemDataBean> dataBeanList);

        void onUsersLeave(List<PLVLinkMicItemDataBean> dataBeanList);

        void setPresenter(@NonNull IStreamerPresenter presenter);
    }
}
