package com.arialyy.aria.core.upload;

import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.config.UploadConfig;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public class UTaskWrapper extends AbsTaskWrapper<UploadEntity> {
    private String tempUrl;

    public UTaskWrapper(UploadEntity uploadEntity) {
        super(uploadEntity);
    }

    @Override // com.arialyy.aria.core.wrapper.ITaskWrapper
    public String getKey() {
        return getEntity().getKey();
    }

    public String getTempUrl() {
        return this.tempUrl;
    }

    public void setTempUrl(String str) {
        this.tempUrl = str;
    }

    @Override // com.arialyy.aria.core.wrapper.AbsTaskWrapper
    public UploadConfig getConfig() {
        return Configuration.getInstance().uploadCfg;
    }
}
