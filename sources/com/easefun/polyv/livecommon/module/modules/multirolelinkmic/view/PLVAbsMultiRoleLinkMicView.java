package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.plv.linkmic.model.PLVNetworkStatusVO;
import com.plv.livescenes.document.event.PLVSwitchRoomEvent;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class PLVAbsMultiRoleLinkMicView implements IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView {
    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onChangeLinkMicZoom(@Nullable Map<String, PLVUpdateMicSiteEvent> updateMicSiteEventMap) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onInitLinkMicList(String myLinkMicId, List<PLVLinkMicItemDataBean> linkMicList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onJoinDiscuss(String groupId, String groupName, @Nullable PLVSwitchRoomEvent switchRoomEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLeaderCancelHelp() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLeaderRequestHelp() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLeaveDiscuss(@Nullable PLVSwitchRoomEvent switchRoomEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLessonEnd(long inClassTime, boolean isFromApi, @Nullable PLVHCStudentLessonListVO.DataVO dataVO) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLessonLateTooLong(long willAutoStopLessonTimeMs) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLessonPreparing(long serverTime, long lessonStartTime) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLessonStarted() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLinkMicEngineCreatedSuccess() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLinkMicError(int errorCode, Throwable throwable) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLinkMicListChanged(List<PLVLinkMicItemDataBean> dataBeanList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onLocalUserVolumeChanged(int volume) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onMemberItemChanged(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onMemberItemInsert(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onMemberItemRemove(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onMemberListChanged(List<PLVMemberItemDataBean> dataBeanList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onNetworkQuality(int quality) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onReachTheInteractNumLimit() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onRejoinRoomSuccess() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onRemoteNetworkStatus(PLVNetworkStatusVO networkStatusVO) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onRemoteUserVolumeChanged() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onRemoveLinkMicZoom(PLVRemoveMicSiteEvent removeMicSiteEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onRepeatLogin(String desc) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherControlMyLinkMic(boolean isAllowJoin) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherInfo(String nick) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherJoinDiscuss(boolean isJoin) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherMuteMyMedia(boolean isVideoType, boolean isMute) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherScreenStream(PLVLinkMicItemDataBean linkMicItemDataBean, boolean isOpen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onTeacherSendBroadcast(String content) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUpdateLinkMicZoom(PLVUpdateMicSiteEvent updateMicSiteEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUpstreamNetworkStatus(PLVNetworkStatusVO networkStatusVO) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserExisted(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserHasGroupLeader(boolean isHasGroupLeader, String nick, boolean isGroupChanged, boolean isLeaderChanged, String groupName, @Nullable String leaderId) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserMuteAudio(String uid, boolean mute, int linkMicListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserMuteVideo(String uid, boolean mute, int linkMicListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public boolean onUserNeedAnswerLinkMic() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUserRaiseHand(int raiseHandCount, boolean isRaiseHand, int linkMicListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUsersJoin(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onUsersLeave(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void onWillJoinDiscuss(long countdownTimeMs) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.contract.IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicView
    public void setPresenter(@NonNull IPLVMultiRoleLinkMicContract.IMultiRoleLinkMicPresenter presenter) {
    }
}
