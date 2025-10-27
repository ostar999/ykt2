package com.arialyy.aria.core.upload.target;

import com.arialyy.aria.core.common.AbsNormalTarget;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
public class HttpNormalTarget extends AbsNormalTarget<HttpNormalTarget> {
    private UNormalConfigHandler<HttpNormalTarget> mConfigHandler;

    public HttpNormalTarget(long j2) {
        this.mConfigHandler = new UNormalConfigHandler<>(this, j2);
        getTaskWrapper().setSupportBP(false);
        getTaskWrapper().setRequestType(5);
        getTaskWrapper().setNewTask(false);
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean isRunning() {
        return this.mConfigHandler.isRunning();
    }

    public HttpNormalTarget option(HttpOption httpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (httpOption == null) {
            throw new NullPointerException("任务配置为空");
        }
        getTaskWrapper().getOptionParams().setParams(httpOption);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget, com.arialyy.aria.core.common.controller.INormalFeature
    public void resume() {
        ALog.e(this.TAG, "http上传任务没有恢复功能");
    }

    public HttpNormalTarget setUploadUrl(String str) {
        this.mConfigHandler.setTempUrl(str);
        return this;
    }

    @Override // com.arialyy.aria.core.common.AbsNormalTarget
    public boolean taskExists() {
        return this.mConfigHandler.taskExists();
    }
}
