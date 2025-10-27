package com.hyphenate.chat.adapter;

import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMPageResult;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMAChatRoomManager extends EMABase {
    public EMAChatRoom addChatroomAdmin(String str, String str2, EMAError eMAError) {
        return nativeAddChatroomAdmin(str, str2, eMAError);
    }

    public void addListener(EMAChatRoomManagerListener eMAChatRoomManagerListener) {
        nativeAddListener(eMAChatRoomManagerListener);
    }

    public EMAChatRoom addToWhiteList(String str, List<String> list, EMAError eMAError) {
        return nativeAddToWhiteList(str, list, eMAError);
    }

    public EMAChatRoom blockChatroomMembers(String str, List<String> list, EMAError eMAError) {
        return nativeBlockChatroomMembers(str, list, eMAError);
    }

    public EMAChatRoom changeChatroomDescription(String str, String str2, EMAError eMAError) {
        return nativeChangeChatroomDescription(str, str2, eMAError);
    }

    public EMAChatRoom changeChatroomSubject(String str, String str2, EMAError eMAError) {
        return nativeChangeChatroomSubject(str, str2, eMAError);
    }

    public boolean checkIfInWhiteList(String str, EMAError eMAError) {
        return nativeCheckIfInWhiteList(str, eMAError);
    }

    public void clearListeners() {
        nativeClearListeners();
    }

    public EMAChatRoom createChatRoom(String str, String str2, String str3, int i2, int i3, List<String> list, EMAError eMAError) {
        return nativeCreateChatRoom(str, str2, str3, i2, i3, list, eMAError);
    }

    public void destroyChatroom(String str, EMAError eMAError) {
        nativeDestroyChatroom(str, eMAError);
    }

    public List<EMAChatRoom> fetchAllChatrooms(EMAError eMAError) {
        return nativeFetchAllChatrooms(eMAError);
    }

    public String fetchChatRoomAnnouncement(String str, EMAError eMAError) {
        return nativeFetchChatroomAnnouncement(str, eMAError);
    }

    public List<String> fetchChatRoomBlackList(String str, int i2, int i3, EMAError eMAError) {
        return nativeFetchChatRoomBlackList(str, i2, i3, eMAError);
    }

    public Map<String, Long> fetchChatRoomMuteList(String str, int i2, int i3, EMAError eMAError) {
        return nativeFetchChatroomMutes(str, i2, i3, eMAError);
    }

    public List<String> fetchChatRoomWhiteList(String str, EMAError eMAError) {
        return nativeFetchChatRoomWhiteList(str, eMAError);
    }

    public String fetchChatroomAttributes(String str, List<String> list, EMAError eMAError) {
        return nativeFetchChatroomAttributes(str, list, eMAError);
    }

    public EMCursorResult<String> fetchChatroomMembers(String str, String str2, int i2, EMAError eMAError) {
        return nativeFetchChatroomMembers(str, str2, i2, eMAError);
    }

    public EMAChatRoom fetchChatroomSpecification(String str, EMAError eMAError, boolean z2) {
        return nativeFetchChatroomSpecification(str, eMAError, z2);
    }

    public EMCursorResult<EMAChatRoom> fetchChatroomsWithCursor(String str, int i2, EMAError eMAError) {
        return nativeFetchChatroomsWithCursor(str, i2, eMAError);
    }

    public EMPageResult<EMAChatRoom> fetchChatroomsWithPage(int i2, int i3, EMAError eMAError) {
        return nativefetchChatroomsWithPage(i2, i3, eMAError);
    }

    public EMAChatRoom getChatroom(String str) {
        return nativeGetChatroom(str);
    }

    public EMAChatRoom joinChatRoom(String str, EMAError eMAError) {
        return nativeJoinChatRoom(str, eMAError);
    }

    public void leaveChatRoom(String str, EMAError eMAError) {
        nativeLeaveChatRoom(str, eMAError);
    }

    public EMAChatRoom muteAllMembers(String str, EMAError eMAError) {
        return nativeMuteAllMembers(str, eMAError);
    }

    public EMAChatRoom muteChatroomMembers(String str, List<String> list, long j2, EMAError eMAError) {
        return nativeMuteChatroomMembers(str, list, j2, eMAError);
    }

    public native EMAChatRoom nativeAddChatroomAdmin(String str, String str2, EMAError eMAError);

    public native void nativeAddListener(EMAChatRoomManagerListener eMAChatRoomManagerListener);

    public native EMAChatRoom nativeAddToWhiteList(String str, List<String> list, EMAError eMAError);

    public native EMAChatRoom nativeBlockChatroomMembers(String str, List<String> list, EMAError eMAError);

    public native EMAChatRoom nativeChangeChatroomDescription(String str, String str2, EMAError eMAError);

    public native EMAChatRoom nativeChangeChatroomSubject(String str, String str2, EMAError eMAError);

    public native boolean nativeCheckIfInWhiteList(String str, EMAError eMAError);

    public native void nativeClearListeners();

    public native EMAChatRoom nativeCreateChatRoom(String str, String str2, String str3, int i2, int i3, List<String> list, EMAError eMAError);

    public native void nativeDestroyChatroom(String str, EMAError eMAError);

    public native List<EMAChatRoom> nativeFetchAllChatrooms(EMAError eMAError);

    public native List<String> nativeFetchChatRoomBlackList(String str, int i2, int i3, EMAError eMAError);

    public native List<String> nativeFetchChatRoomWhiteList(String str, EMAError eMAError);

    public native String nativeFetchChatroomAnnouncement(String str, EMAError eMAError);

    public native String nativeFetchChatroomAttributes(String str, List<String> list, EMAError eMAError);

    public native EMCursorResult<String> nativeFetchChatroomMembers(String str, String str2, int i2, EMAError eMAError);

    public native Map<String, Long> nativeFetchChatroomMutes(String str, int i2, int i3, EMAError eMAError);

    public native EMAChatRoom nativeFetchChatroomSpecification(String str, EMAError eMAError, boolean z2);

    public native EMCursorResult<EMAChatRoom> nativeFetchChatroomsWithCursor(String str, int i2, EMAError eMAError);

    public native EMAChatRoom nativeGetChatroom(String str);

    public native EMAChatRoom nativeJoinChatRoom(String str, EMAError eMAError);

    public native void nativeLeaveChatRoom(String str, EMAError eMAError);

    public native EMAChatRoom nativeMuteAllMembers(String str, EMAError eMAError);

    public native EMAChatRoom nativeMuteChatroomMembers(String str, List<String> list, long j2, EMAError eMAError);

    public native EMAChatRoom nativeRemoveChatRoomMembers(String str, List<String> list, EMAError eMAError);

    public native EMAChatRoom nativeRemoveChatroomAdmin(String str, String str2, EMAError eMAError);

    public native String nativeRemoveChatroomAttributes(String str, List<String> list, boolean z2, EMAError eMAError);

    public native EMAChatRoom nativeRemoveFromWhiteList(String str, List<String> list, EMAError eMAError);

    public native void nativeRemoveListener(EMAChatRoomManagerListener eMAChatRoomManagerListener);

    public native String nativeSetChatroomAttributes(String str, String str2, boolean z2, EMAError eMAError);

    public native EMAChatRoom nativeTransferChatroomOwner(String str, String str2, EMAError eMAError);

    public native EMAChatRoom nativeUnblockChatroomMembers(String str, List<String> list, EMAError eMAError);

    public native EMAChatRoom nativeUnmuteAllMembers(String str, EMAError eMAError);

    public native EMAChatRoom nativeUnmuteChatroomMembers(String str, List<String> list, EMAError eMAError);

    public native void nativeUpdateChatroomAnnouncement(String str, String str2, EMAError eMAError);

    public native EMPageResult<EMAChatRoom> nativefetchChatroomsWithPage(int i2, int i3, EMAError eMAError);

    public EMAChatRoom removeChatRoomAdmin(String str, String str2, EMAError eMAError) {
        return nativeRemoveChatroomAdmin(str, str2, eMAError);
    }

    public EMAChatRoom removeChatRoomMembers(String str, List<String> list, EMAError eMAError) {
        return nativeRemoveChatRoomMembers(str, list, eMAError);
    }

    public String removeChatroomAttributes(String str, List<String> list, boolean z2, EMAError eMAError) {
        return nativeRemoveChatroomAttributes(str, list, z2, eMAError);
    }

    public EMAChatRoom removeFromWhiteList(String str, List<String> list, EMAError eMAError) {
        return nativeRemoveFromWhiteList(str, list, eMAError);
    }

    public void removeListener(EMAChatRoomManagerListener eMAChatRoomManagerListener) {
        nativeRemoveListener(eMAChatRoomManagerListener);
    }

    public String setChatroomAttributes(String str, String str2, boolean z2, EMAError eMAError) {
        return nativeSetChatroomAttributes(str, str2, z2, eMAError);
    }

    public EMAChatRoom transferChatroomOwner(String str, String str2, EMAError eMAError) {
        return nativeTransferChatroomOwner(str, str2, eMAError);
    }

    public EMAChatRoom unblockChatRoomMembers(String str, List<String> list, EMAError eMAError) {
        return nativeUnblockChatroomMembers(str, list, eMAError);
    }

    public EMAChatRoom unmuteAllMembers(String str, EMAError eMAError) {
        return nativeUnmuteAllMembers(str, eMAError);
    }

    public EMAChatRoom unmuteChatRoomMembers(String str, List<String> list, EMAError eMAError) {
        return nativeUnmuteChatroomMembers(str, list, eMAError);
    }

    public void updateChatRoomAnnouncement(String str, String str2, EMAError eMAError) {
        nativeUpdateChatroomAnnouncement(str, str2, eMAError);
    }
}
