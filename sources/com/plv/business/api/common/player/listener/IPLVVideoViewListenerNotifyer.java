package com.plv.business.api.common.player.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.model.video.PLVLiveMarqueeVO;
import com.plv.business.model.video.PLVWatermarkVO;

/* loaded from: classes4.dex */
public interface IPLVVideoViewListenerNotifyer extends IPLVVideoViewNotifyer {
    void notifyAuxiliaryPlayEnd(boolean z2);

    void notifyCoverImageOut(@NonNull String str, @Nullable String str2);

    void notifyGestureAction(int i2, boolean z2, boolean z3);

    void notifyGestureAction(int i2, boolean z2, boolean z3, int i3);

    void notifyGetLogo(String str, int i2, int i3, String str2);

    void notifyGetMarqueeVo(PLVLiveMarqueeVO pLVLiveMarqueeVO);

    void notifyGetWatermarkVO(PLVWatermarkVO pLVWatermarkVO);

    void notifyLoadSlow(int i2, boolean z2);

    boolean notifyNetworkError();

    boolean notifyNetworkRecover();

    void notifyOnAdvertisementCountDown(int i2);

    void notifyOnAdvertisementCountDownEnd();

    void notifyOnVideoPause();

    void notifyOnVideoPlay(boolean z2);

    void notifyPPTShow(int i2);

    void notifyRemindCallback();

    void notifyVideoViewRestart(boolean z2);
}
