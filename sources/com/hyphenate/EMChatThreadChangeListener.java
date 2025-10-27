package com.hyphenate;

import com.hyphenate.chat.EMChatThreadEvent;

/* loaded from: classes4.dex */
public interface EMChatThreadChangeListener {
    void onChatThreadCreated(EMChatThreadEvent eMChatThreadEvent);

    void onChatThreadDestroyed(EMChatThreadEvent eMChatThreadEvent);

    void onChatThreadUpdated(EMChatThreadEvent eMChatThreadEvent);

    void onChatThreadUserRemoved(EMChatThreadEvent eMChatThreadEvent);
}
