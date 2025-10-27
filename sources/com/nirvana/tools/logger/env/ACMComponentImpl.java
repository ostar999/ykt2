package com.nirvana.tools.logger.env;

import com.nirvana.tools.logger.model.ACMLimitConfig;
import com.nirvana.tools.logger.upload.AbstractACMUploadManager;

/* loaded from: classes4.dex */
public class ACMComponentImpl implements ACMComponent {
    private AbstractACMUploadManager mUploadManager;

    public ACMComponentImpl(AbstractACMUploadManager abstractACMUploadManager) {
        this.mUploadManager = abstractACMUploadManager;
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void clearLimitConfig() {
        this.mUploadManager.clearLimitConfig();
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setLimitConfig(ACMLimitConfig aCMLimitConfig) {
        this.mUploadManager.setLimitConfig(aCMLimitConfig);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void setUploadEnabled(boolean z2) {
        this.mUploadManager.setUploadEnable(z2);
    }

    @Override // com.nirvana.tools.logger.env.ACMComponent
    public void uploadFailed() {
        this.mUploadManager.uploadFailed();
    }
}
