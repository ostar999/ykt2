package com.hyphenate;

import com.hyphenate.chat.EMChatRoom;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public interface EMChatRoomChangeListener {
    void onAdminAdded(String str, String str2);

    void onAdminRemoved(String str, String str2);

    void onAllMemberMuteStateChanged(String str, boolean z2);

    void onAnnouncementChanged(String str, String str2);

    void onAttributesRemoved(String str, List<String> list, String str2);

    void onAttributesUpdate(String str, Map<String, String> map, String str2);

    void onChatRoomDestroyed(String str, String str2);

    void onMemberExited(String str, String str2, String str3);

    void onMemberJoined(String str, String str2);

    void onMuteListAdded(String str, List<String> list, long j2);

    void onMuteListRemoved(String str, List<String> list);

    void onOwnerChanged(String str, String str2, String str3);

    void onRemovedFromChatRoom(int i2, String str, String str2, String str3);

    void onSpecificationChanged(EMChatRoom eMChatRoom);

    void onWhiteListAdded(String str, List<String> list);

    void onWhiteListRemoved(String str, List<String> list);
}
