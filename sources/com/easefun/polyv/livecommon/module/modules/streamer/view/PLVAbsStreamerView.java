package com.easefun.polyv.livecommon.module.modules.streamer.view;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVAbsStreamerView implements IPLVStreamerContract.IStreamerView {
    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onAddMemberListData(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onCameraDirection(boolean front, int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onDocumentStreamerViewChange(boolean documentInMainScreen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onFirstScreenChange(String linkMicUserId, boolean isFirstScreen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onGuestMediaStatusChanged(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onGuestRTCStatusChanged(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onLocalUserMicVolumeChanged(int volume) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onNetworkQuality(int quality) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onReachTheInteractNumLimit() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onRemoteUserVolumeChanged(List<PLVMemberItemDataBean> linkMicList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onRemoveMemberListData(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onScreenShareChange(int position, boolean isShare, int extra) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onSetPermissionChange(String type, boolean isGranted, boolean isCurrentUser, PLVSocketUserBean user) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onShowNetBroken() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onStatesToStreamEnded() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onStatesToStreamStarted() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onStreamLiveStatusChanged(boolean isLive) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onStreamerEngineCreatedSuccess(String linkMicUid, List<PLVLinkMicItemDataBean> linkMicList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onStreamerError(int errorCode, Throwable throwable) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUpdateMemberListData(List<PLVMemberItemDataBean> dataBeanList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUpdateSocketUserData(int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUpdateStreamerTime(int secondsSinceStartTiming) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUserMuteAudio(String uid, boolean mute, int streamerListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUserMuteVideo(String uid, boolean mute, int streamerListPos, int memberListPos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUserRequest(String uid) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUsersJoin(List<PLVLinkMicItemDataBean> dataBeanList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void onUsersLeave(List<PLVLinkMicItemDataBean> dataBeanList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract.IStreamerView
    public void setPresenter(@NonNull IPLVStreamerContract.IStreamerPresenter presenter) {
    }
}
