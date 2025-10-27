package com.hjq.toast;

import android.app.Application;

/* loaded from: classes4.dex */
public class GlobalToast extends CustomToast {
    private final ToastImpl mToastImpl;

    public GlobalToast(Application application) {
        this.mToastImpl = new ToastImpl(application, (CustomToast) this);
    }

    @Override // com.hjq.toast.config.IToast
    public void cancel() {
        this.mToastImpl.cancel();
    }

    @Override // com.hjq.toast.config.IToast
    public void show() {
        this.mToastImpl.show();
    }
}
