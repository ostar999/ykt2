package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public interface EMAThreadManagerListenerInterface {
    void onLeaveThread(EMAThreadInfo eMAThreadInfo, int i2);

    void onMemberExited(EMAThreadInfo eMAThreadInfo);

    void onMemberJoined(EMAThreadInfo eMAThreadInfo);

    void onThreadNameUpdated(EMAThreadInfo eMAThreadInfo);

    void onThreadNotifyChange(EMAThreadInfo eMAThreadInfo);
}
