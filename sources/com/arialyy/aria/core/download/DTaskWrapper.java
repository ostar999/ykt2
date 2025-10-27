package com.arialyy.aria.core.download;

import com.arialyy.aria.core.TaskOptionParams;
import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.config.DownloadConfig;
import com.arialyy.aria.core.inf.ITaskOption;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public class DTaskWrapper extends AbsTaskWrapper<DownloadEntity> {
    private String groupHash;
    private boolean isGroupTask;
    private ITaskOption m3u8Option;
    private TaskOptionParams m3u8Params;
    private String mTempFilePath;
    private String mTempUrl;

    public DTaskWrapper(DownloadEntity downloadEntity) {
        super(downloadEntity);
        this.isGroupTask = false;
        this.m3u8Params = new TaskOptionParams();
    }

    public void generateM3u8Option(Class<? extends ITaskOption> cls) {
        this.m3u8Option = ComponentUtil.getInstance().buildTaskOption(cls, this.m3u8Params);
    }

    public String getGroupHash() {
        return this.groupHash;
    }

    @Override // com.arialyy.aria.core.wrapper.ITaskWrapper
    public String getKey() {
        return getEntity().getKey();
    }

    public TaskOptionParams getM3U8Params() {
        if (this.m3u8Params == null) {
            this.m3u8Params = new TaskOptionParams();
        }
        return this.m3u8Params;
    }

    public ITaskOption getM3u8Option() {
        return this.m3u8Option;
    }

    public String getTempFilePath() {
        return this.mTempFilePath;
    }

    public String getTempUrl() {
        return this.mTempUrl;
    }

    public boolean isGroupTask() {
        return this.isGroupTask;
    }

    public void setGroupHash(String str) {
        this.groupHash = str;
    }

    public void setGroupTask(boolean z2) {
        this.isGroupTask = z2;
    }

    public void setTempFilePath(String str) {
        this.mTempFilePath = str;
    }

    public void setTempUrl(String str) {
        this.mTempUrl = str;
    }

    @Override // com.arialyy.aria.core.wrapper.AbsTaskWrapper
    public DownloadConfig getConfig() {
        return this.isGroupTask ? Configuration.getInstance().dGroupCfg.getSubConfig() : Configuration.getInstance().downloadCfg;
    }
}
