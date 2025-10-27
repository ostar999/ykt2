package com.hyphenate.easeui.modules.chat.interfaces;

import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface OnTranslateMessageListener {
    void translateMessageFail(EMMessage eMMessage, int i2, String str);

    void translateMessageSuccess(EMMessage eMMessage);
}
