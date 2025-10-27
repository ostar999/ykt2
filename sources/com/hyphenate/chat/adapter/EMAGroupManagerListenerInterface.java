package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public interface EMAGroupManagerListenerInterface {
    void onAddAdminFromGroup(EMAGroup eMAGroup, String str);

    void onAddMutesFromGroup(EMAGroup eMAGroup, List<String> list, long j2);

    void onAllMemberMuteStateChanged(EMAGroup eMAGroup, boolean z2);

    void onAnnouncementChanged(EMAGroup eMAGroup, String str);

    void onAssignOwnerFromGroup(EMAGroup eMAGroup, String str, String str2);

    void onAutoAcceptInvitationFromGroup(EMAGroup eMAGroup, String str, String str2);

    void onDeleteShareFileFromGroup(EMAGroup eMAGroup, String str);

    void onLeaveGroup(EMAGroup eMAGroup, int i2);

    void onMemberExited(EMAGroup eMAGroup, String str);

    void onMemberJoined(EMAGroup eMAGroup, String str);

    void onReceiveAcceptionFromGroup(EMAGroup eMAGroup);

    void onReceiveInviteAcceptionFromGroup(EMAGroup eMAGroup, String str);

    void onReceiveInviteDeclineFromGroup(EMAGroup eMAGroup, String str, String str2);

    void onReceiveInviteFromGroup(String str, String str2, String str3, String str4);

    void onReceiveJoinGroupApplication(EMAGroup eMAGroup, String str, String str2);

    void onReceiveRejectionFromGroup(String str, String str2);

    void onRemoveAdminFromGroup(EMAGroup eMAGroup, String str);

    void onRemoveMutesFromGroup(EMAGroup eMAGroup, List<String> list);

    void onStateChangedFromGroup(EMAGroup eMAGroup, boolean z2);

    void onUpdateMyGroupList(List<EMAGroup> list);

    void onUpdateSpecificationFromGroup(EMAGroup eMAGroup);

    void onUploadShareFileFromGroup(EMAGroup eMAGroup, EMAMucShareFile eMAMucShareFile);

    void onWhiteListAdded(EMAGroup eMAGroup, List<String> list);

    void onWhiteListRemoved(EMAGroup eMAGroup, List<String> list);
}
