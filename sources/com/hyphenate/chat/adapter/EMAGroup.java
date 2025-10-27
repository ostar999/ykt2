package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAGroup extends EMABase {
    public static final int EMGroupLeaveReason_BE_KICKED = 0;
    public static final int EMGroupLeaveReason_DESTROYED = 1;

    public enum EMGroupLeaveReason {
        BE_KICKED,
        DESTROYED
    }

    public EMAGroup() {
        nativeInit();
    }

    public EMAGroup(EMAGroup eMAGroup) {
        nativeInit(eMAGroup);
    }

    public void addMember(String str) {
        nativeaddMember(str);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public List<String> getAdminList() {
        return nativeGetAdminList();
    }

    public String getAnnouncement() {
        return nativeGetAnnouncement();
    }

    public String getDescription() {
        return nativeGroupDescription();
    }

    public List<String> getGroupBans() {
        return nativeGetGroupBans();
    }

    public List<String> getGroupMuteList() {
        return nativeGetGroupMuteList();
    }

    public int getMemberCount() {
        return nativeGroupMembersCount();
    }

    public List<String> getMembers() {
        return nativeGroupMembers();
    }

    public String getOwner() {
        return nativeGroupOwner();
    }

    public List<EMAMucShareFile> getShareFiles() {
        return nativeGetShareFiles();
    }

    public List<String> getWhiteList() {
        return nativeGetWhiteList();
    }

    public List<String> groupBlockList() {
        return nativeGroupBlockList();
    }

    public String groupId() {
        return nativeGroupId();
    }

    public EMAGroupSetting groupSetting() {
        return nativeGroupSetting();
    }

    public String groupSubject() {
        return nativegroupSubject();
    }

    public boolean isAllMemberMuted() {
        return nativeIsAllMemberMuted();
    }

    public boolean isDisabled() {
        return nativeIsDisabled();
    }

    public boolean isMsgBlocked() {
        return nativeIsMessageBlocked();
    }

    public boolean isPushEnabled() {
        return nativeIsPushEnabled();
    }

    public native void nativeFinalize();

    public native List<String> nativeGetAdminList();

    public native String nativeGetAnnouncement();

    public native List<String> nativeGetGroupBans();

    public native List<String> nativeGetGroupMuteList();

    public native List<EMAMucShareFile> nativeGetShareFiles();

    public native List<String> nativeGetWhiteList();

    public native List<String> nativeGroupBlockList();

    public native String nativeGroupDescription();

    public native String nativeGroupId();

    public native List<String> nativeGroupMembers();

    public native int nativeGroupMembersCount();

    public native String nativeGroupOwner();

    public native EMAGroupSetting nativeGroupSetting();

    public native void nativeInit();

    public native void nativeInit(EMAGroup eMAGroup);

    public native boolean nativeIsAllMemberMuted();

    public native boolean nativeIsDisabled();

    public native boolean nativeIsMessageBlocked();

    public native boolean nativeIsPushEnabled();

    public native int nativePermissionType();

    public native void nativeaddMember(String str);

    public native String nativegroupSubject();

    public native void nativesetAffiliationsCount(int i2);

    public native void nativesetDescription(String str);

    public native void nativesetGroupName(String str);

    public native void nativesetMsgBlocked(boolean z2);

    public native void nativesetOwner(String str);

    public int permissionType() {
        return nativePermissionType();
    }

    public void setAffiliationsCount(int i2) {
        nativesetAffiliationsCount(i2);
    }

    public void setDescription(String str) {
        nativesetDescription(str);
    }

    public void setGroupName(String str) {
        nativesetGroupName(str);
    }

    public void setMsgBlocked(boolean z2) {
        nativesetMsgBlocked(z2);
    }

    public void setOwner(String str) {
        nativesetOwner(str);
    }
}
