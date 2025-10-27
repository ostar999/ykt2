package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public interface EMAChatRoomManagerListenerInterface {
    void onAddAdmin(EMAChatRoom eMAChatRoom, String str);

    void onAddMuteList(EMAChatRoom eMAChatRoom, List<String> list, long j2);

    void onAllMemberMuteStateChanged(EMAChatRoom eMAChatRoom, boolean z2);

    void onAnnouncementChanged(EMAChatRoom eMAChatRoom, String str);

    void onAttributesRemoved(String str, String str2, String str3);

    void onAttributesUpdate(String str, String str2, String str3);

    void onLeaveChatRoom(EMAChatRoom eMAChatRoom, int i2);

    void onMemberJoinedChatRoom(EMAChatRoom eMAChatRoom, String str);

    void onMemberLeftChatRoom(EMAChatRoom eMAChatRoom, String str);

    void onOwnerChanged(EMAChatRoom eMAChatRoom, String str, String str2);

    void onRemoveAdmin(EMAChatRoom eMAChatRoom, String str);

    void onRemoveMutes(EMAChatRoom eMAChatRoom, List<String> list);

    void onUpdateSpecificationFromChatroom(EMAChatRoom eMAChatRoom);

    void onWhiteListAdded(EMAChatRoom eMAChatRoom, List<String> list);

    void onWhiteListRemoved(EMAChatRoom eMAChatRoom, List<String> list);
}
