package com.hyphenate;

import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageReactionChange;
import java.util.List;

/* loaded from: classes4.dex */
public interface EMMessageListener {
    void onCmdMessageReceived(List<EMMessage> list);

    void onGroupMessageRead(List<EMGroupReadAck> list);

    @Deprecated
    void onMessageChanged(EMMessage eMMessage, Object obj);

    void onMessageDelivered(List<EMMessage> list);

    void onMessageRead(List<EMMessage> list);

    void onMessageRecalled(List<EMMessage> list);

    void onMessageReceived(List<EMMessage> list);

    void onReactionChanged(List<EMMessageReactionChange> list);

    void onReadAckForGroupMessageUpdated();
}
