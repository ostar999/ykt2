package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAGroup;
import com.hyphenate.chat.adapter.EMAGroupSetting;
import com.hyphenate.chat.adapter.EMAMucShareFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EMGroup extends EMBase<EMAGroup> {
    private List<EMMucSharedFile> shareFileList;

    public enum EMGroupPermissionType {
        member(0),
        admin(1),
        owner(2),
        none(-1);

        private int permissionType;

        EMGroupPermissionType(int i2) {
            this.permissionType = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMGroup(EMAGroup eMAGroup) {
        this.emaObject = eMAGroup;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getAdminList() {
        return ((EMAGroup) this.emaObject).getAdminList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getAnnouncement() {
        return ((EMAGroup) this.emaObject).getAnnouncement();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getBlackList() {
        return ((EMAGroup) this.emaObject).getGroupBans();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getDescription() {
        return ((EMAGroup) this.emaObject).getDescription();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getExtension() {
        EMAGroupSetting eMAGroupSettingGroupSetting = ((EMAGroup) this.emaObject).groupSetting();
        return eMAGroupSettingGroupSetting == null ? "" : eMAGroupSettingGroupSetting.extension();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getGroupId() {
        return ((EMAGroup) this.emaObject).groupId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getGroupName() {
        return ((EMAGroup) this.emaObject).groupSubject();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMGroupPermissionType getGroupPermissionType() {
        int iPermissionType = ((EMAGroup) this.emaObject).permissionType();
        EMGroupPermissionType eMGroupPermissionType = EMGroupPermissionType.member;
        if (iPermissionType == eMGroupPermissionType.permissionType) {
            return eMGroupPermissionType;
        }
        EMGroupPermissionType eMGroupPermissionType2 = EMGroupPermissionType.admin;
        if (iPermissionType == eMGroupPermissionType2.permissionType) {
            return eMGroupPermissionType2;
        }
        EMGroupPermissionType eMGroupPermissionType3 = EMGroupPermissionType.owner;
        return iPermissionType == eMGroupPermissionType3.permissionType ? eMGroupPermissionType3 : EMGroupPermissionType.none;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMaxUserCount() {
        EMAGroupSetting eMAGroupSettingGroupSetting = ((EMAGroup) this.emaObject).groupSetting();
        if (eMAGroupSettingGroupSetting == null) {
            return 0;
        }
        return eMAGroupSettingGroupSetting.maxUserCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMemberCount() {
        return ((EMAGroup) this.emaObject).getMemberCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getMembers() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(((EMAGroup) this.emaObject).getMembers());
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getMuteList() {
        return ((EMAGroup) this.emaObject).getGroupMuteList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getOwner() {
        return ((EMAGroup) this.emaObject).getOwner();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMucSharedFile> getShareFileList() {
        List<EMMucSharedFile> list = this.shareFileList;
        if (list == null) {
            this.shareFileList = new ArrayList();
        } else {
            list.clear();
        }
        Iterator<EMAMucShareFile> it = ((EMAGroup) this.emaObject).getShareFiles().iterator();
        while (it.hasNext()) {
            this.shareFileList.add(new EMMucSharedFile(it.next()));
        }
        return this.shareFileList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getWhiteList() {
        return ((EMAGroup) this.emaObject).getWhiteList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String groupSubject() {
        return ((EMAGroup) this.emaObject).groupSubject();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAllMemberMuted() {
        return ((EMAGroup) this.emaObject).isAllMemberMuted();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDisabled() {
        return ((EMAGroup) this.emaObject).isDisabled();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isMemberAllowToInvite() {
        EMAGroupSetting eMAGroupSettingGroupSetting = ((EMAGroup) this.emaObject).groupSetting();
        return eMAGroupSettingGroupSetting == null || eMAGroupSettingGroupSetting.style() == 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isMemberOnly() {
        EMAGroupSetting eMAGroupSettingGroupSetting = ((EMAGroup) this.emaObject).groupSetting();
        return eMAGroupSettingGroupSetting == null || eMAGroupSettingGroupSetting.style() == 0 || eMAGroupSettingGroupSetting.style() == 1 || eMAGroupSettingGroupSetting.style() == 2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isMsgBlocked() {
        return ((EMAGroup) this.emaObject).isMsgBlocked();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isPublic() {
        EMAGroupSetting eMAGroupSettingGroupSetting = ((EMAGroup) this.emaObject).groupSetting();
        if (eMAGroupSettingGroupSetting == null) {
            return true;
        }
        int iStyle = eMAGroupSettingGroupSetting.style();
        return (iStyle == 0 || iStyle == 1) ? false : true;
    }

    public String toString() {
        String groupName = getGroupName();
        return groupName != null ? groupName : getGroupId();
    }
}
