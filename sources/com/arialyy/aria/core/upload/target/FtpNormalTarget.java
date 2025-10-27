package com.arialyy.aria.core.upload.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.FtpOption;
import com.arialyy.aria.core.common.SFtpOption;
import com.arialyy.aria.core.upload.UploadEntity;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class FtpNormalTarget extends AbsNormalTarget<FtpNormalTarget> {
    private UNormalConfigHandler<FtpNormalTarget> mConfigHandler;

    public FtpNormalTarget(long j2) {
        this.mConfigHandler = new UNormalConfigHandler<>(this, j2);
        getTaskWrapper().setRequestType(6);
        getTaskWrapper().setNewTask(false);
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
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
        sFtpOption.setUrlEntity(CommonUtil.getFtpUrlInfo(getEntity().getUrl()));
        getTaskWrapper().getOptionParams().setParams(sFtpOption);
        getEntity().setTaskType(13);
        getTaskWrapper().setRequestType(13);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }

    @Override // com.arialyy.aria.core.inf.AbsTarget
    public UploadEntity getEntity() {
        return (UploadEntity) super.getEntity();
    }
}
