package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAChatRoomManagerListener extends EMABase implements EMAChatRoomManagerListenerInterface {
    public static final int BE_KICKED = 0;
    public static final int BE_KICKED_FOR_OFFLINE = 2;
    public static final int DESTROYED = 1;

    public EMAChatRoomManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onAddAdmin(EMAChatRoom eMAChatRoom, String str) {
    }

    public void onAddMuteList(EMAChatRoom eMAChatRoom, List<String> list, long j2) {
    }

    public void onAllMemberMuteStateChanged(EMAChatRoom eMAChatRoom, boolean z2) {
    }

    public void onAnnouncementChanged(EMAChatRoom eMAChatRoom, String str) {
    }

    public void onAttributesRemoved(String str, String str2, String str3) {
    }

    public void onAttributesUpdate(String str, String str2, String str3) {
    }

    public void onLeaveChatRoom(EMAChatRoom eMAChatRoom, int i2) {
    }

    public void onMemberJoinedChatRoom(EMAChatRoom eMAChatRoom, String str) {
    }

    public void onMemberLeftChatRoom(EMAChatRoom eMAChatRoom, String str) {
    }

    public void onOwnerChanged(EMAChatRoom eMAChatRoom, String str, String str2) {
    }

    public void onRemoveAdmin(EMAChatRoom eMAChatRoom, String str) {
    }

    public void onRemoveMutes(EMAChatRoom eMAChatRoom, List<String> list) {
    }

    public void onUpdateSpecificationFromChatroom(EMAChatRoom eMAChatRoom) {
    }

    public void onWhiteListAdded(EMAChatRoom eMAChatRoom, List<String> list) {
    }

    public void onWhiteListRemoved(EMAChatRoom eMAChatRoom, List<String> list) {
    }
}
