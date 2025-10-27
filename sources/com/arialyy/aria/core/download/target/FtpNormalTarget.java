package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.FtpOption;
import com.arialyy.aria.core.common.SFtpOption;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class FtpNormalTarget extends AbsNormalTarget<FtpNormalTarget> {
    private DNormalConfigHandler<FtpNormalTarget> mConfigHandler;

    public FtpNormalTarget(long j2) {
        this.mConfigHandler = new DNormalConfigHandler<>(this, j2);
        getTaskWrapper().setRequestType(3);
        getTaskWrapper().setNewTask(false);
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
    }

    public FtpNormalTarget modifyFilePath(String str) {
        getEntity().setFileName(this.mConfigHandler.getUrl().substring(this.mConfigHandler.getUrl().lastIndexOf("/") + 1));
        this.mConfigHandler.setTempFilePath(str);
        return this;
    }

    public FtpNormalTarget option(FtpOption ftpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (ftpOption == null) {
            throw new NullPointerException("ftp 任务配置为空");
        }
        ftpOption.setUrlEntity(CommonUtil.getFtpUrlInfo(getEntity().getUrl()));
        getTaskWrapper().getOptionParams().setParams(ftpOption);
        return this;
    }

    public FtpNormalTarget sftpOption(SFtpOption sFtpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (sFtpOption == null) {
            throw new NullPointerException("ftp 任务配置为空");
        }
        sFtpOption.setUrlEntity(CommonUtil.getFtpUrlInfo(this.mConfigHandler.getUrl()));
        getTaskWrapper().getOptionParams().setParams(sFtpOption);
        getEntity().setTaskType(12);
        getTaskWrapper().setRequestType(12);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }

    public FtpNormalTarget updateUrl(String str) {
        return (FtpNormalTarget) this.mConfigHandler.updateUrl(str);
    }

    @Override // com.arialyy.aria.core.inf.AbsTarget
    public DownloadEntity getEntity() {
        return (DownloadEntity) super.getEntity();
    }
}
