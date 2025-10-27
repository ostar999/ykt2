package com.hyphenate.easeui.modules.chat.interfaces;

import com.hyphenate.chat.EMMessage;

/* loaded from: classes4.dex */
public interface IRecyclerViewHandle {
    void canUseDefaultRefresh(boolean z2);

    void lastMsgScrollToBottom(EMMessage eMMessage);

    void moveToPosition(int i2);

    void refreshMessage(EMMessage eMMessage);

    void refreshMessages();

    void refreshToLatest();

    void removeMessage(EMMessage eMMessage);
}
