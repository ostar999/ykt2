package com.easefun.polyv.livecloudclass.modules.media.danmu;

/* loaded from: classes3.dex */
public interface IPLVLCLandscapeMessageSender {

    public interface OnSendMessageListener {
        void onSend(String str);
    }

    void dismiss();

    void hideMessageSender();

    void openMessageSender();

    void setOnSendMessageListener(OnSendMessageListener onSendMessageListener);
}
