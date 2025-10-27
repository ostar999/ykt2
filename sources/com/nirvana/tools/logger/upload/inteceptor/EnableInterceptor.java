package com.nirvana.tools.logger.upload.inteceptor;

/* loaded from: classes4.dex */
public class EnableInterceptor implements BaseInterceptor {
    private boolean mEnabled;

    @Override // com.nirvana.tools.logger.upload.inteceptor.BaseInterceptor
    public boolean isAllowUploading() {
        return this.mEnabled;
    }

    public void setEnabled(boolean z2) {
        this.mEnabled = z2;
    }
}
