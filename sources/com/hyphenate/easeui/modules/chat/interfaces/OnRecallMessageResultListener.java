package com.hyphenate.easeui.modules.chat.interfaces;

import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface OnRecallMessageResultListener {
    void recallFail(int i2, String str);

    void recallSuccess(EMMessage eMMessage);
}
