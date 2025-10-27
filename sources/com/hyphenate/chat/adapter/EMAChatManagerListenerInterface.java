package com.hyphenate.chat.adapter;

import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.List;

/* loaded from: classes4.dex */
public interface EMAChatManagerListenerInterface {
    void onMessageAttachmentsStatusChanged(EMAMessage eMAMessage, EMAError eMAError);

    void onMessageStatusChanged(EMAMessage eMAMessage, EMAError eMAError);

    void onReceiveCmdMessages(List<EMAMessage> list);

    void onReceiveHasDeliveredAcks(List<EMAMessage> list);

    void onReceiveHasReadAcks(List<EMAMessage> list);

    void onReceiveMessages(List<EMAMessage> list);

    void onReceivePrivateMessages(List<EMAMessage> list);

    void onReceiveReadAckForConversation(String str, String str2);

    void onReceiveReadAcksForGroupMessage(List<EMAGroupReadAck> list);

    void onReceiveRecallMessages(List<EMAMessage> list);

    void onUpdateConversationList(List<EMAConversation> list);

    void onUpdateGroupAcks();
}
