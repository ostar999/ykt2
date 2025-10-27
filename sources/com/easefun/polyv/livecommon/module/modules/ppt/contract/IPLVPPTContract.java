package com.easefun.polyv.livecommon.module.modules.ppt.contract;

/* loaded from: classes3.dex */
public interface IPLVPPTContract {

    public interface IPLVPPTPresenter {
        void destroy();

        void init(IPLVPPTView view);

        void notifyIsWatchLowLatency(boolean isLowLatency);

        void sendPPTBrushMsg(String msg);
    }

    public interface IPLVPPTView {
        void hideLoading();

        void sendMsgToWebView(String msg);

        void sendMsgToWebView(String msg, String event);

        void switchPPTViewLocation(boolean toMainScreen);
    }
}
