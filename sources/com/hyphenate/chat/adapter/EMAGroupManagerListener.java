package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAGroupManagerListener extends EMABase implements EMAGroupManagerListenerInterface {
    public EMAGroupManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onAddAdminFromGroup(EMAGroup eMAGroup, String str) {
    }

    public void onAddMutesFromGroup(EMAGroup eMAGroup, List<String> list, long j2) {
    }

    public void onAllMemberMuteStateChanged(EMAGroup eMAGroup, boolean z2) {
    }

    public void onAnnouncementChanged(EMAGroup eMAGroup, String str) {
    }

    public void onAssignOwnerFromGroup(EMAGroup eMAGroup, String str, String str2) {
    }

    public void onAutoAcceptInvitationFromGroup(EMAGroup eMAGroup, String str, String str2) {
    }

    public void onDeleteShareFileFromGroup(EMAGroup eMAGroup, String str) {
    }

    public void onLeaveGroup(EMAGroup eMAGroup, int i2) {
    }

    public void onMemberExited(EMAGroup eMAGroup, String str) {
    }

    public void onMemberJoined(EMAGroup eMAGroup, String str) {
    }

    public void onReceiveAcceptionFromGroup(EMAGroup eMAGroup) {
    }

    public void onReceiveInviteAcceptionFromGroup(EMAGroup eMAGroup, String str) {
    }

    public void onReceiveInviteDeclineFromGroup(EMAGroup eMAGroup, String str, String str2) {
    }

    public void onReceiveInviteFromGroup(String str, String str2, String str3, String str4) {
    }

    public void onReceiveJoinGroupApplication(EMAGroup eMAGroup, String str, String str2) {
    }

    public void onReceiveRejectionFromGroup(String str, String str2) {
    }

    public void onRemoveAdminFromGroup(EMAGroup eMAGroup, String str) {
    }

    public void onRemoveMutesFromGroup(EMAGroup eMAGroup, List<String> list) {
    }

    public void onStateChangedFromGroup(EMAGroup eMAGroup, boolean z2) {
    }

    public void onUpdateMyGroupList(List<EMAGroup> list) {
    }

    public void onUpdateSpecificationFromGroup(EMAGroup eMAGroup) {
    }

    public void onUploadShareFileFromGroup(EMAGroup eMAGroup, EMAMucShareFile eMAMucShareFile) {
    }

    public void onWhiteListAdded(EMAGroup eMAGroup, List<String> list) {
    }

    public void onWhiteListRemoved(EMAGroup eMAGroup, List<String> list) {
    }
}
