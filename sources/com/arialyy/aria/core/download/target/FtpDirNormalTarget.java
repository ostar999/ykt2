package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.FtpOption;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.manager.SubTaskManager;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class FtpDirNormalTarget extends AbsNormalTarget<FtpDirNormalTarget> {
    private FtpDirConfigHandler<FtpDirNormalTarget> mConfigHandler;
    private FtpOption option;

    public FtpDirNormalTarget(long j2) {
        this.mConfigHandler = new FtpDirConfigHandler<>(this, j2);
        getTaskWrapper().setNewTask(false);
    }

    public SubTaskManager getSubTaskManager() {
        return this.mConfigHandler.getSubTaskManager();
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
    }

    public FtpDirNormalTarget modifyDirPath(String str) {
        return this.mConfigHandler.setDirPath(str);
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public void onPre() throws IllegalAccessException, SecurityException, IllegalArgumentException {
        super.onPre();
        if (this.option == null) {
            this.option = new FtpOption();
        }
        this.option.setUrlEntity(CommonUtil.getFtpUrlInfo(getEntity().getKey()));
        getTaskWrapper().getOptionParams().setParams(this.option);
    }

    public FtpDirNormalTarget option(FtpOption ftpOption) {
        if (ftpOption == null) {
            throw new NullPointerException("ftp 任务配置为空");
        }
        this.option = ftpOption;
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }

    @Override // com.arialyy.aria.core.inf.AbsTarget
    public DownloadGroupEntity getEntity() {
        return (DownloadGroupEntity) super.getEntity();
    }
}
