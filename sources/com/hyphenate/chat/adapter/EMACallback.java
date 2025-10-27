package com.hyphenate.chat.adapter;

import com.hyphenate.EMCallBack;

/* loaded from: classes4.dex */
public class EMACallback extends EMABase {
    private EMCallBack owner;

    public EMACallback(EMCallBack eMCallBack) {
        this.owner = eMCallBack;
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onError(int i2, String str) {
        EMCallBack eMCallBack = this.owner;
        if (eMCallBack != null) {
            eMCallBack.onError(i2, str);
            this.owner = null;
        }
    }

    public void onProgress(int i2, String str) {
        EMCallBack eMCallBack = this.owner;
        if (eMCallBack != null) {
            eMCallBack.onProgress(i2, str);
        }
    }

    public void onSuccess() {
        EMCallBack eMCallBack = this.owner;
        if (eMCallBack != null) {
            eMCallBack.onSuccess();
            this.owner = null;
        }
    }

    public void setOwner(EMCallBack eMCallBack) {
        this.owner = eMCallBack;
    }
}
