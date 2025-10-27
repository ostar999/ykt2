package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.m3u8.M3U8LiveOption;
import com.arialyy.aria.core.download.m3u8.M3U8VodOption;

/* loaded from: classes2.dex */
public class HttpNormalTarget extends AbsNormalTarget<HttpNormalTarget> {
    private DNormalConfigHandler<HttpNormalTarget> mConfigHandler;

    public HttpNormalTarget(long j2) {
        this.mConfigHandler = new DNormalConfigHandler<>(this, j2);
        getTaskWrapper().setRequestType(getTaskWrapper().getEntity().getTaskType());
        getTaskWrapper().setNewTask(false);
    }

    public String getContentDisposition() {
        return getEntity().getDisposition();
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
    }

    public HttpNormalTarget m3u8LiveOption(M3U8LiveOption m3U8LiveOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (m3U8LiveOption == null) {
            throw new NullPointerException("m3u8任务设置为空");
        }
        getTaskWrapper().setRequestType(8);
        ((DTaskWrapper) getTaskWrapper()).getM3U8Params().setParams(m3U8LiveOption);
        return this;
    }

    public M3U8NormalTarget m3u8VodOption(M3U8VodOption m3U8VodOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (m3U8VodOption == null) {
            throw new NullPointerException("m3u8任务设置为空");
        }
        getTaskWrapper().setRequestType(7);
        getTaskWrapper().getEntity().setFileSize(m3U8VodOption.getFileSize());
        ((DTaskWrapper) getTaskWrapper()).getM3U8Params().setParams(m3U8VodOption);
        return new M3U8NormalTarget((DTaskWrapper) getTaskWrapper());
    }

    public HttpNormalTarget modifyFilePath(String str) {
        this.mConfigHandler.setTempFilePath(str);
        return this;
    }

    public HttpNormalTarget option(HttpOption httpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (httpOption == null) {
            throw new NullPointerException("任务配置为空");
        }
        getTaskWrapper().getOptionParams().setParams(httpOption);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }

    public HttpNormalTarget updateUrl(String str) {
        return (HttpNormalTarget) this.mConfigHandler.updateUrl(str);
    }

    @Override // com.arialyy.aria.core.inf.AbsTarget
    public DownloadEntity getEntity() {
        return (DownloadEntity) super.getEntity();
    }

    public M3U8NormalTarget m3u8VodOption() {
        return new M3U8NormalTarget((DTaskWrapper) getTaskWrapper());
    }
}
