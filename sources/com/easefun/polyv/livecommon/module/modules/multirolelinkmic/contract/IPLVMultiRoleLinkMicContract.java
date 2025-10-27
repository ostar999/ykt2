package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.presenter.data.PLVMultiRoleLinkMicData;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.livescenes.document.event.PLVSwitchRoomEvent;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.net.IPLVDataRequestListener;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import io.socket.client.Ack;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IPLVMultiRoleLinkMicContract {

    public interface IMultiRoleLinkMicPresenter {
        void answerLinkMicInvitation();

        void closeAllUserLinkMic();

        void controlUserLinkMic(int memberListPos, boolean isAllowJoin);

        View createRenderView(Context context);

        void destroy();

        @NonNull
        PLVMultiRoleLinkMicData getData();

        int getLessonStatus();

        int getLimitLinkNumber();

        void init();

        boolean isInClassStatus();

        boolean isJoinDiscuss();

        boolean isMyLinkMicId(String linkMicId);

        boolean isTeacherType();

        void joinChannel();

        void leaveChannel();

        void muteAllUserAudio(boolean isMute);

        boolean muteAudio(boolean mute);

        boolean muteVideo(boolean mute);

        void registerView(@NonNull IMultiRoleLinkMicView v2);

        void releaseRenderView(View renderView);

        void requestMemberList();

        void sendCupEvent(int linkMicListPos, Ack ack);

        void sendRaiseHandEvent(int raiseHandTime);

        void setMediaPermission(int memberListPos, boolean isVideoType, boolean isMute);

        void setMediaPermission(int memberListPos, boolean isVideoType, boolean isMute, Ack ack);

        void setMediaPermissionInLinkMicList(int linkMicListPos, boolean isVideoType, boolean isMute, Ack ack);

        void setPaintPermission(int memberListPos, boolean isHasPermission, Ack ack);

        void setPaintPermissionInLinkMicList(int linkMicListPos, boolean isHasPermission, Ack ack);

        void setPushPictureResolutionType(int type);

        void setupRenderView(View renderView, String linkMicId);

        void setupRenderView(View renderView, String linkMicId, int streamType);

        void startLesson(IPLVDataRequestListener<String> listener);

        void stopLesson(IPLVDataRequestListener<String> listener);

        void switchCamera();

        void switchCamera(boolean front);

        void switchRoleToAudience();

        void switchRoleToBroadcaster();

        void unregisterView(IMultiRoleLinkMicView v2);
    }

    public interface IMultiRoleLinkMicView {
        void onChangeLinkMicZoom(@Nullable Map<String, PLVUpdateMicSiteEvent> updateMicSiteEventMap);

        void onInitLinkMicList(String myLinkMicId, List<PLVLinkMicItemDataBean> linkMicList);

        void onJoinDiscuss(String groupId, String groupName, @Nullable PLVSwitchRoomEvent switchRoomEvent);

        void onLeaderCancelHelp();

        void onLeaderRequestHelp();

        void onLeaveDiscuss(@Nullable PLVSwitchRoomEvent switchRoomEvent);

        void onLessonEnd(long inClassTime, boolean isFromApi, @Nullable PLVHCStudentLessonListVO.DataVO dataVO);

        void onLessonLateTooLong(long willAutoStopLessonTimeMs);

        void onLessonPreparing(long serverTime, long lessonStartTime);

        void onLessonStarted();

        void onLinkMicEngineCreatedSuccess();

        void onLinkMicError(int errorCode, Throwable throwable);

        void onLinkMicListChanged(List<PLVLinkMicItemDataBean> dataBeanList);

        void onLocalUserVolumeChanged(int volume);

        void onMemberItemChanged(int pos);

        void onMemberItemInsert(int pos);

        void onMemberItemRemove(int pos);

        void onMemberListChanged(List<PLVMemberItemDataBean> dataBeanList);

        void onNetworkQuality(int quality);

        void onReachTheInteractNumLimit();

        void onRejoinRoomSuccess();

        void onRemoteNetworkStatus(PLVNetworkStatusVO networkStatusVO);

        void onRemoteUserVolumeChanged();

        void onRemoveLinkMicZoom(PLVRemoveMicSiteEvent removeMicSiteEvent);

        void onRepeatLogin(String desc);

        void onTeacherControlMyLinkMic(boolean isAllowJoin);

        void onTeacherInfo(String nick);

        void onTeacherJoinDiscuss(boolean isJoin);

        void onTeacherMuteMyMedia(boolean isVideoType, boolean isMute);

        void onTeacherScreenStream(PLVLinkMicItemDataBean linkMicItemDataBean, boolean isOpen);

        void onTeacherSendBroadcast(String content);

        void onUpdateLinkMicZoom(PLVUpdateMicSiteEvent updateMicSiteEvent);

        void onUpstreamNetworkStatus(PLVNetworkStatusVO networkStatusVO);

        void onUserExisted(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos);

        void onUserHasGroupLeader(boolean isHasGroupLeader, String nick, boolean isGroupChanged, boolean isLeaderChanged, String groupName, @Nullable String leaderId);

        void onUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos);

        void onUserMuteAudio(final String uid, final boolean mute, int linkMicListPos, int memberListPos);

        void onUserMuteVideo(final String uid, final boolean mute, int linkMicListPos, int memberListPos);

        boolean onUserNeedAnswerLinkMic();

        void onUserRaiseHand(int raiseHandCount, boolean isRaiseHand, int linkMicListPos, int memberListPos);

        void onUsersJoin(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onUsersLeave(PLVLinkMicItemDataBean linkMicItemDataBean, int position);

        void onWillJoinDiscuss(long countdownTimeMs);

        void setPresenter(@NonNull IMultiRoleLinkMicPresenter presenter);
    }
}
