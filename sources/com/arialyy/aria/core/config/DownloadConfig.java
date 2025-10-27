package com.arialyy.aria.core.config;

import com.arialyy.aria.core.event.DMaxNumEvent;
import com.arialyy.aria.core.event.DSpeedEvent;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.util.ALog;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class DownloadConfig extends BaseTaskConfig implements Serializable {
    int threadNum = 3;
    boolean useBlock = true;
    boolean useHeadRequest = false;

    public int getThreadNum() {
        return this.threadNum;
    }

    @Override // com.arialyy.aria.core.config.BaseConfig
    public int getType() {
        return 1;
    }

    public boolean isUseBlock() {
        return this.useBlock;
    }

    public boolean isUseHeadRequest() {
        return this.useHeadRequest;
    }

    public DownloadConfig setThreadNum(int i2) throws Throwable {
        this.threadNum = i2;
        save();
        return this;
    }

    public DownloadConfig setUseBlock(boolean z2) throws Throwable {
        this.useBlock = z2;
        save();
        return this;
    }

    public DownloadConfig setUseHeadRequest(boolean z2) throws Throwable {
        this.useHeadRequest = z2;
        save();
        return this;
    }

    @Override // com.arialyy.aria.core.config.BaseTaskConfig
    public DownloadConfig setMaxSpeed(int i2) throws Throwable {
        super.setMaxSpeed(i2);
        EventMsgUtil.getDefault().post(new DSpeedEvent(i2));
        return this;
    }

    @Override // com.arialyy.aria.core.config.BaseTaskConfig
    public DownloadConfig setMaxTaskNum(int i2) throws Throwable {
        if (i2 <= 0) {
            ALog.e(this.TAG, "下载任务最大任务数不能小于0");
            return this;
        }
        super.setMaxTaskNum(i2);
        EventMsgUtil.getDefault().post(new DMaxNumEvent(i2));
        return this;
    }
}
