package com.hyphenate.chat.adapter;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMAChatRoom extends EMABase {
    public static final int EMChatroomLeaveReason_BE_KICKED = 0;
    public static final int EMChatroomLeaveReason_DESTROYED = 1;

    public enum EMAChatRoomStyle {
        EMAChatRoomStylePrivateOnlyOwnerInvite,
        EMAChatRoomStylePrivateMemberCanInvite,
        EMAChatRoomStylePublicJoinNeedApproval,
        EMAChatRoomStylePublicOpenJoin
    }

    public enum EMLeaveReason {
        BE_KICKED,
        DESTROYED
    }

    public EMAChatRoom() {
        nativeInit();
    }

    public EMAChatRoom(EMAChatRoom eMAChatRoom) {
        nativeInit(eMAChatRoom);
    }

    public EMAChatRoom(String str) {
        nativeInit(str);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public List<String> getAdministratorList() {
        return nativeGetAdministratorList();
    }

    public int getAffiliationsCount() {
        return nativegetAffiliationsCount();
    }

    public String getAnnouncement() {
        return nativeGetAnnouncement();
    }

    public List<String> getBlockList() {
        return nativeGetBlockList();
    }

    public String getDescription() {
        return nativeChatroomDescription();
    }

    public String getId() {
        return nativeChatroomId();
    }

    public int getMaxUserCount() {
        return nativegetMaxUserCount();
    }

    public List<String> getMemberList() {
        return nativegetMemberList();
    }

    public Map<String, Long> getMuteList() {
        return nativeGetMuteList();
    }

    public String getName() {
        return nativeChatroomSubject();
    }

    public String getOwner() {
        return nativegetOwner();
    }

    public List<String> getWhiteList() {
        return nativeGetWhiteList();
    }

    public boolean isAllMemberMuted() {
        return nativeIsAllMemberMuted();
    }

    public native String nativeChatroomDescription();

    public native String nativeChatroomId();

    public native String nativeChatroomSubject();

    public native void nativeFinalize();

    public native List<String> nativeGetAdministratorList();

    public native String nativeGetAnnouncement();

    public native List<String> nativeGetBlockList();

    public native Map<String, Long> nativeGetMuteList();

    public native List<String> nativeGetWhiteList();

    public native void nativeInit();

    public native void nativeInit(EMAChatRoom eMAChatRoom);

    public native void nativeInit(String str);

    public native boolean nativeIsAllMemberMuted();

    public native int nativePermissionType();

    public native int nativegetAffiliationsCount();

    public native int nativegetMaxUserCount();

    public native List<String> nativegetMemberList();

    public native String nativegetOwner();

    public int permissionType() {
        return nativePermissionType();
    }
}
