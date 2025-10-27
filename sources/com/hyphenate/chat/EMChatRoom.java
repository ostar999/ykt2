package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAChatRoom;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMChatRoom extends EMBase<EMAChatRoom> {

    public enum EMChatRoomPermissionType {
        member(0),
        admin(1),
        owner(2),
        none(-1);

        private int permissionType;

        EMChatRoomPermissionType(int i2) {
            this.permissionType = i2;
        }
    }

    public enum EMChatRoomStyle {
        EMChatRoomStylePrivateOnlyOwnerInvite,
        EMChatRoomStylePrivateMemberCanInvite,
        EMChatRoomStylePublicJoinNeedApproval,
        EMChatRoomStylePublicOpenJoin
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMAChatRoom] */
    public EMChatRoom() {
        this.emaObject = new EMAChatRoom();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMAChatRoom] */
    public EMChatRoom(EMAChatRoom eMAChatRoom) {
        this.emaObject = new EMAChatRoom(eMAChatRoom);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMAChatRoom] */
    public EMChatRoom(String str) {
        this.emaObject = new EMAChatRoom(str);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.hyphenate.chat.adapter.EMAChatRoom] */
    public EMChatRoom(String str, String str2) {
        this.emaObject = new EMAChatRoom(str);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getAdminList() {
        return ((EMAChatRoom) this.emaObject).getAdministratorList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getAnnouncement() {
        return ((EMAChatRoom) this.emaObject).getAnnouncement();
    }

    @Deprecated
    public List<String> getBlackList() {
        return getBlacklist();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getBlacklist() {
        return ((EMAChatRoom) this.emaObject).getBlockList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMChatRoomPermissionType getChatRoomPermissionType() {
        int iPermissionType = ((EMAChatRoom) this.emaObject).permissionType();
        EMChatRoomPermissionType eMChatRoomPermissionType = EMChatRoomPermissionType.member;
        if (iPermissionType == eMChatRoomPermissionType.permissionType) {
            return eMChatRoomPermissionType;
        }
        EMChatRoomPermissionType eMChatRoomPermissionType2 = EMChatRoomPermissionType.admin;
        if (iPermissionType == eMChatRoomPermissionType2.permissionType) {
            return eMChatRoomPermissionType2;
        }
        EMChatRoomPermissionType eMChatRoomPermissionType3 = EMChatRoomPermissionType.owner;
        return iPermissionType == eMChatRoomPermissionType3.permissionType ? eMChatRoomPermissionType3 : EMChatRoomPermissionType.none;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getDescription() {
        return ((EMAChatRoom) this.emaObject).getDescription();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getId() {
        return ((EMAChatRoom) this.emaObject).getId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMaxUsers() {
        return ((EMAChatRoom) this.emaObject).getMaxUserCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMemberCount() {
        return ((EMAChatRoom) this.emaObject).getAffiliationsCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getMemberList() {
        return ((EMAChatRoom) this.emaObject).getMemberList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<String, Long> getMuteList() {
        return ((EMAChatRoom) this.emaObject).getMuteList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getName() {
        return ((EMAChatRoom) this.emaObject).getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getOwner() {
        return ((EMAChatRoom) this.emaObject).getOwner();
    }

    @Deprecated
    public List<String> getWhiteList() {
        return getWhitelist();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getWhitelist() {
        return ((EMAChatRoom) this.emaObject).getWhiteList();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAllMemberMuted() {
        return ((EMAChatRoom) this.emaObject).isAllMemberMuted();
    }
}
