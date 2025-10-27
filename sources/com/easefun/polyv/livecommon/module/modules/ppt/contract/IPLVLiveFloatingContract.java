package com.easefun.polyv.livecommon.module.modules.ppt.contract;

/* loaded from: classes3.dex */
public interface IPLVLiveFloatingContract {

    public interface IPLVLiveFloatingPresenter {
        void destroy();

        void init(IPLVLiveFloatingView view);
    }

    public interface IPLVLiveFloatingView {
        void updateTeacherInfo(String actor, String nick);
    }
}
