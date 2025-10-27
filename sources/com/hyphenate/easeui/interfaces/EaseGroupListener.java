package com.hyphenate.easeui.interfaces;

import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import d1.d;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EaseGroupListener implements EMGroupChangeListener {
    @Override // com.hyphenate.EMGroupChangeListener
    public void onAdminAdded(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onAdminRemoved(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onAllMemberMuteStateChanged(String str, boolean z2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onAnnouncementChanged(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onAutoAcceptInvitationFromGroup(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public abstract void onGroupDestroyed(String str, String str2);

    @Override // com.hyphenate.EMGroupChangeListener
    public void onInvitationAccepted(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onInvitationDeclined(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onInvitationReceived(String str, String str2, String str3, String str4) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onMemberExited(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onMemberJoined(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onMuteListAdded(String str, List<String> list, long j2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onMuteListRemoved(String str, List<String> list) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onOwnerChanged(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onRequestToJoinAccepted(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onRequestToJoinDeclined(String str, String str2, String str3, String str4) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onRequestToJoinReceived(String str, String str2, String str3, String str4) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onSharedFileAdded(String str, EMMucSharedFile eMMucSharedFile) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onSharedFileDeleted(String str, String str2) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public /* synthetic */ void onSpecificationChanged(EMGroup eMGroup) {
        d.a(this, eMGroup);
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public /* synthetic */ void onStateChanged(EMGroup eMGroup, boolean z2) {
        d.b(this, eMGroup, z2);
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public abstract void onUserRemoved(String str, String str2);

    @Override // com.hyphenate.EMGroupChangeListener
    public void onWhiteListAdded(String str, List<String> list) {
    }

    @Override // com.hyphenate.EMGroupChangeListener
    public void onWhiteListRemoved(String str, List<String> list) {
    }
}
