package com.hyphenate.easeui.interfaces;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.adapter.EaseBaseMessageAdapter;

/* loaded from: classes4.dex */
public interface IChatAdapterProvider {
    EaseBaseMessageAdapter<EMMessage> provideMessageAdaper();
}
