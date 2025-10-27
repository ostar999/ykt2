package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.core.download.AbsGroupTaskWrapper;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.manager.SubTaskManager;
import com.arialyy.aria.util.ALog;
import java.util.List;

/* loaded from: classes2.dex */
public class GroupNormalTarget extends AbsNormalTarget<GroupNormalTarget> {
    private HttpGroupConfigHandler<GroupNormalTarget> mConfigHandler;

    public GroupNormalTarget(long j2) {
        this.mConfigHandler = new HttpGroupConfigHandler<>(this, j2);
        getTaskWrapper().setRequestType(2);
        getTaskWrapper().setNewTask(false);
    }

    public SubTaskManager getSubTaskManager() {
        return this.mConfigHandler.getSubTaskManager();
    }

    public GroupNormalTarget ignoreTaskOccupy() {
        ((AbsGroupTaskWrapper) getTaskWrapper()).setIgnoreTaskOccupy(true);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
    }

    public GroupNormalTarget modifyDirPath(String str) {
        return this.mConfigHandler.setDirPath(str);
    }

    public GroupNormalTarget modifySubFileName(List<String> list) {
        return (GroupNormalTarget) this.mConfigHandler.setSubFileName(list);
    }

    public GroupNormalTarget option(HttpOption httpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (httpOption == null) {
            throw new NullPointerException("任务配置为空");
        }
        getTaskWrapper().getOptionParams().setParams(httpOption);
        return this;
    }

    public GroupNormalTarget setFileSize(long j2) {
        if (j2 <= 0) {
            ALog.e(this.TAG, "文件大小不能小于 0");
            return this;
        }
        if (getEntity().getFileSize() <= 1 || getEntity().getFileSize() != j2) {
            getEntity().setFileSize(j2);
        }
        return this;
    }

    public GroupNormalTarget setGroupAlias(String str) {
        this.mConfigHandler.setGroupAlias(str);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }

    public GroupNormalTarget unknownSize() {
        ((DGTaskWrapper) getTaskWrapper()).setUnknownSize(true);
        return this;
    }

    public GroupNormalTarget updateUrls(List<String> list) {
        return (GroupNormalTarget) this.mConfigHandler.updateUrls(list);
    }
}
