package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public abstract class EMAThreadManagerListener extends EMABase implements EMAThreadManagerListenerInterface {
    public EMAThreadManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onLeaveThread(EMAThreadInfo eMAThreadInfo, int i2) {
    }

    public void onMemberExited(EMAThreadInfo eMAThreadInfo) {
    }

    public void onMemberJoined(EMAThreadInfo eMAThreadInfo) {
    }

    public void onThreadNameUpdated(EMAThreadInfo eMAThreadInfo) {
    }

    public void onThreadNotifyChange(EMAThreadInfo eMAThreadInfo) {
    }
}
