package com.nirvana.tools.logger.env;

import com.nirvana.tools.logger.model.ACMLimitConfig;

/* loaded from: classes4.dex */
public interface ACMComponent {
    void clearLimitConfig();

    void setLimitConfig(ACMLimitConfig aCMLimitConfig);

    void setUploadEnabled(boolean z2);

    void uploadFailed();
}
