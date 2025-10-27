package com.easefun.polyv.livecommon.module.modules.popover;

import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2;
import com.easefun.polyv.livecommon.module.modules.reward.OnPointRewardListener;
import com.easefun.polyv.livecommon.module.modules.reward.PLVPointRewardLayout;

/* loaded from: classes3.dex */
public interface IPLVPopoverLayout {
    void destroy();

    IPLVInteractLayout getInteractLayout();

    PLVPointRewardLayout getRewardView();

    void init(PLVLiveScene scene, IPLVLiveRoomDataManager roomDataManager);

    boolean onBackPress();

    void setOnOpenInsideWebViewListener(PLVInteractLayout2.OnOpenInsideWebViewListener listener);

    void setOnPointRewardListener(OnPointRewardListener listener);
}
