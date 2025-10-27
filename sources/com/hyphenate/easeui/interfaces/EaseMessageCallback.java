package com.hyphenate.easeui.interfaces;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public abstract class EaseMessageCallback implements EMCallBack {
    @Override // com.hyphenate.EMCallBack
    public void onError(int i2, String str) {
    }

    @Override // com.hyphenate.EMCallBack
    public void onProgress(int i2, String str) {
    }

    @Override // com.hyphenate.EMCallBack
    public void onSuccess() {
    }

    public abstract void onSuccess(EMMessage eMMessage, int i2);
}
