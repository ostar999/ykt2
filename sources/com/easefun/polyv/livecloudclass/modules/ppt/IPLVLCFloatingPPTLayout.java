package com.easefun.polyv.livecloudclass.modules.ppt;

import android.view.View;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;

/* loaded from: classes3.dex */
public interface IPLVLCFloatingPPTLayout {

    public interface IPLVOnClickCloseFloatingView {
        void onClickCloseFloatingView();
    }

    void destroy();

    PLVSwitchViewAnchorLayout getPPTSwitchView();

    IPLVLCPPTView getPPTView();

    void hide();

    boolean isPPTInFloatingLayout();

    void setIsLowLatencyWatch(boolean z2);

    void setOnClickCloseListener(IPLVOnClickCloseFloatingView iPLVOnClickCloseFloatingView);

    void setOnFloatingViewClickListener(View.OnClickListener onClickListener);

    void setServerEnablePPT(boolean z2);

    void show();
}
