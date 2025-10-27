package com.plv.livescenes.chatroom.send.custom;

/* loaded from: classes4.dex */
public interface PLVSendCustomMsgListener {
    void onSendFail(Object obj, int i2);

    void onSendFail(Object obj, Throwable th);

    void onSuccess(Object obj);
}
