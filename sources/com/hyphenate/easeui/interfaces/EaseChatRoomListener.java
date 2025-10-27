package com.hyphenate.easeui.interfaces;

import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.chat.EMChatRoom;
import d1.b;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class EaseChatRoomListener implements EMChatRoomChangeListener {
    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onAdminAdded(String str, String str2) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onAdminRemoved(String str, String str2) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onAllMemberMuteStateChanged(String str, boolean z2) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onAnnouncementChanged(String str, String str2) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public /* synthetic */ void onAttributesRemoved(String str, List list, String str2) {
        b.a(this, str, list, str2);
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public /* synthetic */ void onAttributesUpdate(String str, Map map, String str2) {
        b.b(this, str, map, str2);
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public abstract void onChatRoomDestroyed(String str, String str2);

    @Override // com.hyphenate.EMChatRoomChangeListener
    public abstract void onMemberExited(String str, String str2, String str3);

    @Override // com.hyphenate.EMChatRoomChangeListener
    public abstract void onMemberJoined(String str, String str2);

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onMuteListAdded(String str, List<String> list, long j2) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onMuteListRemoved(String str, List<String> list) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onOwnerChanged(String str, String str2, String str3) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public abstract void onRemovedFromChatRoom(int i2, String str, String str2, String str3);

    @Override // com.hyphenate.EMChatRoomChangeListener
    public /* synthetic */ void onSpecificationChanged(EMChatRoom eMChatRoom) {
        b.c(this, eMChatRoom);
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onWhiteListAdded(String str, List<String> list) {
    }

    @Override // com.hyphenate.EMChatRoomChangeListener
    public void onWhiteListRemoved(String str, List<String> list) {
    }
}
