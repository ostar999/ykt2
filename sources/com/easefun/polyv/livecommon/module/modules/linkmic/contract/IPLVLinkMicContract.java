package com.easefun.polyv.livecommon.module.modules.linkmic.contract;

import android.content.Context;
import android.view.SurfaceView;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPLVLinkMicContract {

    public interface IPLVLinkMicPresenter {
        void cancelRequestJoinLinkMic();

        SurfaceView createRenderView(Context context);

        void destroy();

        boolean getIsAudioLinkMic();

        String getLinkMicId();

        String getMainTeacherLinkMicId();

        int getRTCListSize();

        boolean isAloneChannelTypeSupportRTC();

        boolean isJoinChannel();

        boolean isJoinLinkMic();

        boolean isTeacherOpenLinkMic();

        void leaveLinkMic();

        void muteAllAudio(boolean mute);

        void muteAllVideo(boolean mute);

        void muteAudio(String linkMicId, boolean mute);

        void muteAudio(boolean mute);

        void muteVideo(String linkMicId, boolean mute);

        void muteVideo(boolean mute);

        void releaseRenderView(SurfaceView renderView);

        void requestJoinLinkMic();

        void resetRequestPermissionList(ArrayList<String> permissions);

        void setIsAudioLinkMic(boolean isAudioLinkMic);

        void setIsTeacherOpenLinkMic(boolean isTeacherOpenLinkMic);

        void setLiveEnd();

        void setLiveStart();

        void setPushPictureResolutionType(int type);

        void setWatchRtc(boolean watchRtc);

        void setupRenderView(SurfaceView renderView, String linkMicId);

        void switchCamera();
    }

    public interface IPLVLinkMicView {
        int getMediaViewIndexInLinkMicList();

        boolean isMediaShowInLinkMicList();

        void onAdjustTeacherLocation(String linkMicId, int teacherPos, boolean isNeedSwitchToMain, Runnable onAdjustFinished);

        void onChangeListShowMode(PLVLinkMicListShowMode linkMicListShowMode);

        void onJoinChannelTimeout();

        void onJoinLinkMic();

        void onJoinRtcChannel();

        void onLeaveLinkMic();

        void onLeaveRtcChannel();

        void onLinkMicError(int errorCode, Throwable throwable);

        void onLocalUserMicVolumeChanged();

        void onNetQuality(int quality);

        void onNotInLinkMicList();

        void onPrepareLinkMicList(String linkMicUid, PLVLinkMicListShowMode linkMicListShowMode, List<PLVLinkMicItemDataBean> linkMicList);

        void onRTCPrepared();

        void onRemoteUserVolumeChanged(List<PLVLinkMicItemDataBean> linkMicList);

        void onSwitchFirstScreen(String linkMicId);

        void onSwitchPPTViewLocation(boolean toMainScreen);

        void onTeacherAllowJoin();

        void onTeacherCloseLinkMic();

        void onTeacherOpenLinkMic();

        void onUserMuteAudio(final String uid, final boolean mute, int pos);

        void onUserMuteVideo(final String uid, final boolean mute, int pos);

        void onUsersJoin(List<String> uids);

        void onUsersLeave(List<String> uids);

        void performClickInLinkMicListItem(int index);

        void updateAllLinkMicList();

        void updateFirstScreenChanged(String firstScreenLinkMicId, int oldPos, int newPos);
    }
}
