package com.easefun.polyv.livecloudclass.modules.linkmic;

/* loaded from: classes3.dex */
public interface IPLVLCLinkMicControlBar {

    public interface OnPLCLinkMicControlBarListener {
        void onClickCameraFrontOfBack(boolean z2);

        void onClickCameraOpenOrClose(boolean z2);

        void onClickMicroPhoneOpenOrClose(boolean z2);

        void onClickRingOffLinkMic();

        void onClickRingUpLinkMic();
    }

    void hide();

    void setCameraOpenOrClose(boolean z2);

    void setIsAudio(boolean z2);

    void setIsTeacherOpenLinkMic(boolean z2);

    void setJoinLinkMicSuccess();

    void setLeaveLinkMic();

    void setMicrophoneOpenOrClose(boolean z2);

    void setOnPLCLinkMicControlBarListener(OnPLCLinkMicControlBarListener onPLCLinkMicControlBarListener);

    void show();
}
