package com.hyphenate.chat.adapter;

import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAChatManagerListener extends EMABase implements EMAChatManagerListenerInterface {
    public EMAChatManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onMessageAttachmentsStatusChanged(EMAMessage eMAMessage, EMAError eMAError) {
    }

    public void onMessageStatusChanged(EMAMessage eMAMessage, EMAError eMAError) {
    }

    public void onReceiveCmdMessages(List<EMAMessage> list) {
    }

    public void onReceiveHasDeliveredAcks(List<EMAMessage> list) {
    }

    public void onReceiveHasReadAcks(List<EMAMessage> list) {
    }

    public void onReceiveMessages(List<EMAMessage> list) {
    }

    public void onReceivePrivateMessages(List<EMAMessage> list) {
    }

    public void onReceiveReadAckForConversation(String str, String str2) {
    }

    public void onReceiveReadAcksForGroupMessage(List<EMAGroupReadAck> list) {
    }

    public void onReceiveRecallMessages(List<EMAMessage> list) {
    }

    public void onUpdateConversationList(List<EMAConversation> list) {
    }

    public void onUpdateGroupAcks() {
    }
}
