package com.easefun.polyv.livecommon.module.modules.linkmic.view;

import com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicListShowMode;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVAbsLinkMicView implements IPLVLinkMicContract.IPLVLinkMicView {
    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public int getMediaViewIndexInLinkMicList() {
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView, com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout
    public boolean isMediaShowInLinkMicList() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onAdjustTeacherLocation(String linkMicId, int teacherPos, boolean isNeedSwitchToMain, Runnable onAdjustFinished) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onChangeListShowMode(PLVLinkMicListShowMode linkMicListShowMode) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinChannelTimeout() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinLinkMic() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onJoinRtcChannel() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLeaveLinkMic() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLeaveRtcChannel() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLinkMicError(int errorCode, Throwable throwable) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onLocalUserMicVolumeChanged() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onNetQuality(int quality) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onNotInLinkMicList() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onPrepareLinkMicList(String linkMicUid, PLVLinkMicListShowMode linkMicListShowMode, List<PLVLinkMicItemDataBean> linkMicList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onRTCPrepared() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onRemoteUserVolumeChanged(List<PLVLinkMicItemDataBean> linkMicList) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onSwitchFirstScreen(String linkMicId) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onSwitchPPTViewLocation(boolean toMainScreen) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherAllowJoin() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherCloseLinkMic() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onTeacherOpenLinkMic() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUserMuteAudio(String uid, boolean mute, int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUserMuteVideo(String uid, boolean mute, int pos) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUsersJoin(List<String> uids) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void onUsersLeave(List<String> uids) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void performClickInLinkMicListItem(int index) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void updateAllLinkMicList() {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.linkmic.contract.IPLVLinkMicContract.IPLVLinkMicView
    public void updateFirstScreenChanged(String firstScreenLinkMicId, int oldPos, int newPos) {
    }
}
