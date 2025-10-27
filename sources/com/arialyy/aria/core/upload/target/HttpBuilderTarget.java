package com.arialyy.aria.core.upload.target;

import com.arialyy.aria.core.common.AbsBuilderTarget;
import com.arialyy.aria.core.common.HttpOption;
import com.arialyy.aria.core.upload.UploadEntity;

/* loaded from: classes2.dex */
public class HttpBuilderTarget extends AbsBuilderTarget<HttpBuilderTarget> {
    private UNormalConfigHandler<HttpBuilderTarget> mConfigHandler;

    public HttpBuilderTarget(String str) {
        UNormalConfigHandler<HttpBuilderTarget> uNormalConfigHandler = new UNormalConfigHandler<>(this, -1L);
        this.mConfigHandler = uNormalConfigHandler;
        uNormalConfigHandler.setFilePath(str);
        getTaskWrapper().setSupportBP(false);
        getTaskWrapper().setRequestType(5);
        ((UploadEntity) getEntity()).setTaskType(5);
        getTaskWrapper().setNewTask(true);
    }

    @Deprecated
    public HttpBuilderTarget forceUpload() {
        getTaskWrapper().setIgnoreFilePathOccupy(true);
        return this;
    }

    public HttpBuilderTarget option(HttpOption httpOption) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (httpOption == null) {
            throw new NullPointerException("任务配置为空");
        }
        getTaskWrapper().getOptionParams().setParams(httpOption);
        return this;
    }

    public HttpBuilderTarget setUploadUrl(String str) {
        this.mConfigHandler.setTempUrl(str);
        return this;
    }
}
