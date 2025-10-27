package com.easefun.polyv.livecommon.module.modules.reward.contract;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;

/* loaded from: classes3.dex */
public interface IPLVPointRewardContract {

    public interface IPointRewardPresenter {
        void destroy();

        void getPointRewardSetting();

        void getRemainingRewardPoint();

        void init(String channel, PLVLiveChannelConfig.User user);

        void makeGiftCashReward(int goodId, int goodNum, String sessionId);

        void makeGiftPointReward(int goodId, int goodNum);

        void registerView(@NonNull IPointRewardView v2);

        void unregisterView();
    }

    public interface IPointRewardView {
        void destroy();

        void initGiftRewardSetting(String payWay, PLVRewardSettingVO.GiftDonateDTO giftReward);

        boolean onBackPress();

        void showPointRewardDialog(boolean enable);

        void showPointRewardEnable(boolean enable);

        void updateRemainingPoint(String remainingPoint);
    }
}
