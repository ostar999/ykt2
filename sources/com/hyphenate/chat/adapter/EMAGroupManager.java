package com.hyphenate.chat.adapter;

import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMAGroupManager extends EMABase {
    public EMAGroupManager() {
    }

    public EMAGroupManager(EMAGroupManager eMAGroupManager) {
        nativeInit(eMAGroupManager);
    }

    public EMAGroup acceptInvitationFromGroup(String str, String str2, EMAError eMAError) {
        return nativeacceptInvitationFromGroup(str, str2, eMAError);
    }

    public void acceptJoinGroupApplication(String str, String str2, EMAError eMAError) {
        nativeAcceptJoinGroupApplication(str, str2, eMAError);
    }

    public EMAGroup addGroupAdmin(String str, String str2, EMAError eMAError) {
        return nativeAddGroupAdmin(str, str2, eMAError);
    }

    public EMAGroup addGroupMembers(String str, List<String> list, String str2, EMAError eMAError) {
        return nativeAddGroupMembers(str, list, str2, eMAError);
    }

    public void addListener(EMAGroupManagerListener eMAGroupManagerListener) {
        nativeAddListener(eMAGroupManagerListener);
    }

    public EMAGroup addToWhiteList(String str, List<String> list, EMAError eMAError) {
        return nativeAddToWhiteList(str, list, eMAError);
    }

    public List<EMAGroup> allMyGroups(EMAError eMAError) {
        return nativeAllMyGroups(eMAError);
    }

    public EMAGroup applyJoinPublicGroup(String str, String str2, String str3, EMAError eMAError) {
        return nativeApplyJoinPublicGroup(str, str2, str3, eMAError);
    }

    public EMAGroup blockGroupMembers(String str, List<String> list, EMAError eMAError, String str2) {
        return nativeBlockGroupMembers(str, list, eMAError, str2);
    }

    public EMAGroup blockGroupMessage(String str, EMAError eMAError) {
        return nativeBlockGroupMessage(str, eMAError);
    }

    public EMAGroup changeGroupDescription(String str, String str2, EMAError eMAError) {
        return nativeChangeGroupDescription(str, str2, eMAError);
    }

    public EMAGroup changeGroupSubject(String str, String str2, EMAError eMAError) {
        return nativeChangeGroupSubject(str, str2, eMAError);
    }

    public boolean checkIfInWhiteList(String str, EMAError eMAError) {
        return nativeCheckIfInWhiteList(str, eMAError);
    }

    public void clearListeners() {
        nativeClearListeners();
    }

    public EMAGroup createGroup(String str, String str2, String str3, EMAGroupSetting eMAGroupSetting, List<String> list, boolean z2, EMAError eMAError) {
        return nativeCreateGroup(str, str2, str3, eMAGroupSetting, list, z2, eMAError);
    }

    public void declineInvitationFromGroup(String str, String str2, String str3, EMAError eMAError) {
        nativedeclineInvitationFromGroup(str, str2, str3, eMAError);
    }

    public void declineJoinGroupApplication(String str, String str2, String str3, EMAError eMAError) {
        nativeDeclineJoinGroupApplication(str, str2, str3, eMAError);
    }

    public void deleteGroupShareFile(String str, String str2, EMAError eMAError) {
        nativeDeleteGroupShareFile(str, str2, eMAError);
    }

    public void destroyGroup(String str, EMAError eMAError) {
        nativeDestroyGroup(str, eMAError);
    }

    public void downloadGroupShareFile(String str, String str2, String str3, EMACallback eMACallback, EMAError eMAError) {
        nativeDownloadGroupShareFile(str, str2, str3, eMACallback, eMAError);
    }

    public List<EMAGroup> fetchAllMyGroups(EMAError eMAError) {
        return nativeFetchAllMyGroups(eMAError);
    }

    public List<EMAGroup> fetchAllMyGroupsWithPage(int i2, int i3, EMAError eMAError) {
        return nativeFetchAllMyGroupsWithPage(i2, i3, eMAError);
    }

    public List<EMAGroup> fetchAllMyGroupsWithPage(int i2, int i3, boolean z2, boolean z3, EMAError eMAError) {
        return nativeFetchAllMyGroupsWithPage(i2, i3, z2, z3, eMAError);
    }

    public String fetchGroupAnnouncement(String str, EMAError eMAError) {
        return nativeFetchGroupAnnouncement(str, eMAError);
    }

    public List<String> fetchGroupBlackList(String str, int i2, int i3, EMAError eMAError) {
        return nativeFetchGroupBlackList(str, i2, i3, eMAError);
    }

    public EMCursorResult<String> fetchGroupMembers(String str, String str2, int i2, EMAError eMAError) {
        return nativeFetchGroupMembers(str, str2, i2, eMAError);
    }

    public Map<String, Long> fetchGroupMutes(String str, int i2, int i3, EMAError eMAError) {
        return nativeFetchGroupMutes(str, i2, i3, eMAError);
    }

    public List<EMAMucShareFile> fetchGroupShareFiles(String str, int i2, int i3, EMAError eMAError) {
        return nativeFetchGroupShareFiles(str, i2, i3, eMAError);
    }

    public EMAGroup fetchGroupSpecification(String str, EMAError eMAError, boolean z2) {
        return nativeFetchGroupSpecification(str, eMAError, z2);
    }

    public List<String> fetchGroupWhiteList(String str, EMAError eMAError) {
        return nativeFetchGroupWhiteList(str, eMAError);
    }

    public EMCursorResult<EMGroupInfo> fetchPublicGroupsWithCursor(String str, int i2, EMAError eMAError) {
        return nativeFetchPublicGroupsWithCursor(str, i2, eMAError);
    }

    public EMAGroup joinPublicGroup(String str, EMAError eMAError) {
        return nativeJoinPublicGroup(str, eMAError);
    }

    public void leaveGroup(String str, EMAError eMAError) {
        nativeLeaveGroup(str, eMAError);
    }

    public void loadAllMyGroupsFromDB() {
        nativeLoadAllMyGroupsFromDB();
    }

    public EMAGroup muteAllMembers(String str, EMAError eMAError) {
        return nativeMuteAllMembers(str, eMAError);
    }

    public EMAGroup muteGroupMembers(String str, List<String> list, long j2, EMAError eMAError) {
        return nativeMuteGroupMembers(str, list, j2, eMAError);
    }

    public native void nativeAcceptJoinGroupApplication(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeAddGroupAdmin(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeAddGroupMembers(String str, List<String> list, String str2, EMAError eMAError);

    public native void nativeAddListener(EMAGroupManagerListener eMAGroupManagerListener);

    public native EMAGroup nativeAddToWhiteList(String str, List<String> list, EMAError eMAError);

    public native List<EMAGroup> nativeAllMyGroups(EMAError eMAError);

    public native EMAGroup nativeApplyJoinPublicGroup(String str, String str2, String str3, EMAError eMAError);

    public native EMAGroup nativeBlockGroupMembers(String str, List<String> list, EMAError eMAError, String str2);

    public native EMAGroup nativeBlockGroupMessage(String str, EMAError eMAError);

    public native EMAGroup nativeChangeGroupDescription(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeChangeGroupSubject(String str, String str2, EMAError eMAError);

    public native boolean nativeCheckIfInWhiteList(String str, EMAError eMAError);

    public native void nativeClearListeners();

    public native EMAGroup nativeCreateGroup(String str, String str2, String str3, EMAGroupSetting eMAGroupSetting, List<String> list, boolean z2, EMAError eMAError);

    public native void nativeDeclineJoinGroupApplication(String str, String str2, String str3, EMAError eMAError);

    public native void nativeDeleteGroupShareFile(String str, String str2, EMAError eMAError);

    public native void nativeDestroyGroup(String str, EMAError eMAError);

    public native void nativeDownloadGroupShareFile(String str, String str2, String str3, EMACallback eMACallback, EMAError eMAError);

    public native List<EMAGroup> nativeFetchAllMyGroups(EMAError eMAError);

    public native List<EMAGroup> nativeFetchAllMyGroupsWithPage(int i2, int i3, EMAError eMAError);

    public native List<EMAGroup> nativeFetchAllMyGroupsWithPage(int i2, int i3, boolean z2, boolean z3, EMAError eMAError);

    public native String nativeFetchGroupAnnouncement(String str, EMAError eMAError);

    public native List<String> nativeFetchGroupBlackList(String str, int i2, int i3, EMAError eMAError);

    public native EMCursorResult<String> nativeFetchGroupMembers(String str, String str2, int i2, EMAError eMAError);

    public native Map<String, Long> nativeFetchGroupMutes(String str, int i2, int i3, EMAError eMAError);

    public native List<EMAMucShareFile> nativeFetchGroupShareFiles(String str, int i2, int i3, EMAError eMAError);

    public native EMAGroup nativeFetchGroupSpecification(String str, EMAError eMAError, boolean z2);

    public native List<String> nativeFetchGroupWhiteList(String str, EMAError eMAError);

    public native EMCursorResult<EMGroupInfo> nativeFetchPublicGroupsWithCursor(String str, int i2, EMAError eMAError);

    public native void nativeInit(EMAGroupManager eMAGroupManager);

    public native EMAGroup nativeJoinPublicGroup(String str, EMAError eMAError);

    public native void nativeLeaveGroup(String str, EMAError eMAError);

    public native void nativeLoadAllMyGroupsFromDB();

    public native EMAGroup nativeMuteAllMembers(String str, EMAError eMAError);

    public native EMAGroup nativeMuteGroupMembers(String str, List<String> list, long j2, EMAError eMAError);

    public native EMAGroup nativeRemoveFromWhiteList(String str, List<String> list, EMAError eMAError);

    public native EMAGroup nativeRemoveGroupAdmin(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeRemoveGroupMembers(String str, List<String> list, EMAError eMAError);

    public native void nativeRemoveListener(EMAGroupManagerListener eMAGroupManagerListener);

    public native EMAGroup nativeSearchPublicGroup(String str, EMAError eMAError);

    public native EMAGroup nativeTransferGroupOwner(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeUnMuteGroupMembers(String str, List<String> list, EMAError eMAError);

    public native EMAGroup nativeUnblockGroupMembers(String str, List<String> list, EMAError eMAError);

    public native EMAGroup nativeUnblockGroupMessage(String str, EMAError eMAError);

    public native EMAGroup nativeUnmuteAllMembers(String str, EMAError eMAError);

    public native void nativeUpdateGroupAnnouncement(String str, String str2, EMAError eMAError);

    public native EMAGroup nativeUpdateGroupExtension(String str, String str2, EMAError eMAError);

    public native EMAMucShareFile nativeUploadGroupShareFile(String str, String str2, EMACallback eMACallback, EMAError eMAError);

    public native EMAGroup nativeacceptInvitationFromGroup(String str, String str2, EMAError eMAError);

    public native void nativedeclineInvitationFromGroup(String str, String str2, String str3, EMAError eMAError);

    public EMAGroup removeFromWhiteList(String str, List<String> list, EMAError eMAError) {
        return nativeRemoveFromWhiteList(str, list, eMAError);
    }

    public EMAGroup removeGroupAdmin(String str, String str2, EMAError eMAError) {
        return nativeRemoveGroupAdmin(str, str2, eMAError);
    }

    public EMAGroup removeGroupMembers(String str, List<String> list, EMAError eMAError) {
        return nativeRemoveGroupMembers(str, list, eMAError);
    }

    public void removeListener(EMAGroupManagerListener eMAGroupManagerListener) {
        nativeRemoveListener(eMAGroupManagerListener);
    }

    public EMAGroup searchPublicGroup(String str, EMAError eMAError) {
        return nativeSearchPublicGroup(str, eMAError);
    }

    public EMAGroup transferGroupOwner(String str, String str2, EMAError eMAError) {
        return nativeTransferGroupOwner(str, str2, eMAError);
    }

    public EMAGroup unMuteGroupMembers(String str, List<String> list, EMAError eMAError) {
        return nativeUnMuteGroupMembers(str, list, eMAError);
    }

    public EMAGroup unblockGroupMembers(String str, List<String> list, EMAError eMAError) {
        return nativeUnblockGroupMembers(str, list, eMAError);
    }

    public EMAGroup unblockGroupMessage(String str, EMAError eMAError) {
        return nativeUnblockGroupMessage(str, eMAError);
    }

    public EMAGroup unmuteAllMembers(String str, EMAError eMAError) {
        return nativeUnmuteAllMembers(str, eMAError);
    }

    public void updateGroupAnnouncement(String str, String str2, EMAError eMAError) {
        nativeUpdateGroupAnnouncement(str, str2, eMAError);
    }

    public EMAGroup updateGroupExtension(String str, String str2, EMAError eMAError) {
        return nativeUpdateGroupExtension(str, str2, eMAError);
    }

    public EMAMucShareFile uploadGroupShareFile(String str, String str2, EMACallback eMACallback, EMAError eMAError) {
        return nativeUploadGroupShareFile(str, str2, eMACallback, eMAError);
    }
}
