package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.common.AbsBuilderTarget;
import com.arialyy.aria.core.common.FtpOption;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.core.manager.SubTaskManager;
import com.arialyy.aria.util.CommonUtil;

/* loaded from: classes2.dex */
public class FtpDirBuilderTarget extends AbsBuilderTarget<FtpDirBuilderTarget> {
    private FtpDirConfigHandler<FtpDirBuilderTarget> mConfigHandler = new FtpDirConfigHandler<>(this, -1);
    private String url;

    public FtpDirBuilderTarget(String str) {
        this.url = str;
        getEntity().setGroupHash(str);
        getTaskWrapper().setNewTask(true);
    }

    public SubTaskManager getSubTaskManager() {
        return this.mConfigHandler.getSubTaskManager();
    }

    public FtpDirBuilderTarget option(FtpOption ftpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (ftpOption == null) {
            throw new NullPointerException("ftp 任务配置为空");
        }
        ftpOption.setUrlEntity(CommonUtil.getFtpUrlInfo(this.url));
        getTaskWrapper().getOptionParams().setParams(ftpOption);
        return this;
    }

    public FtpDirBuilderTarget setDirPath(String str) {
        return this.mConfigHandler.setDirPath(str);
    }

    public FtpDirBuilderTarget setGroupAlias(String str) {
        this.mConfigHandler.setGroupAlias(str);
        return this;
    }

    @Override // com.arialyy.aria.core.inf.AbsTarget
    public DownloadGroupEntity getEntity() {
        return (DownloadGroupEntity) super.getEntity();
    }
}
