package com.hyphenate;

import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import java.util.List;

/* loaded from: classes4.dex */
public interface EMGroupChangeListener {
    void onAdminAdded(String str, String str2);

    void onAdminRemoved(String str, String str2);

    void onAllMemberMuteStateChanged(String str, boolean z2);

    void onAnnouncementChanged(String str, String str2);

    void onAutoAcceptInvitationFromGroup(String str, String str2, String str3);

    void onGroupDestroyed(String str, String str2);

    void onInvitationAccepted(String str, String str2, String str3);

    void onInvitationDeclined(String str, String str2, String str3);

    void onInvitationReceived(String str, String str2, String str3, String str4);

    void onMemberExited(String str, String str2);

    void onMemberJoined(String str, String str2);

    void onMuteListAdded(String str, List<String> list, long j2);

    void onMuteListRemoved(String str, List<String> list);

    void onOwnerChanged(String str, String str2, String str3);

    void onRequestToJoinAccepted(String str, String str2, String str3);

    void onRequestToJoinDeclined(String str, String str2, String str3, String str4);

    void onRequestToJoinReceived(String str, String str2, String str3, String str4);

    void onSharedFileAdded(String str, EMMucSharedFile eMMucSharedFile);

    void onSharedFileDeleted(String str, String str2);

    void onSpecificationChanged(EMGroup eMGroup);

    void onStateChanged(EMGroup eMGroup, boolean z2);

    void onUserRemoved(String str, String str2);

    void onWhiteListAdded(String str, List<String> list);

    void onWhiteListRemoved(String str, List<String> list);
}
