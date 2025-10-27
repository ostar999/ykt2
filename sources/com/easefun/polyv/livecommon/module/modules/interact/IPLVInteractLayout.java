package com.easefun.polyv.livecommon.module.modules.interact;

import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2;
import com.plv.socket.event.interact.PLVShowPushCardEvent;

/* loaded from: classes3.dex */
public interface IPLVInteractLayout {
    void destroy();

    void init(IPLVLiveRoomDataManager liveRoomDataManager);

    void init(IPLVLiveRoomDataManager liveRoomDataManager, @Nullable PLVLiveScene scene);

    boolean onBackPress();

    void onCallDynamicFunction(String event);

    void setOnOpenInsideWebViewListener(PLVInteractLayout2.OnOpenInsideWebViewListener listener);

    void showBulletin();

    void showCardPush(PLVShowPushCardEvent showPushCardEvent);
}
