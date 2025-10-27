package com.plv.livescenes.feature.pointreward;

import com.plv.livescenes.feature.pointreward.vo.PLVDonateGoodResponseVO;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;

/* loaded from: classes4.dex */
public interface IPLVPointRewardDataSource {

    public interface IPointRewardListener<T> {
        void onFailed(Throwable th);

        void onSuccess(T t2);
    }

    void destroy();

    void getPointRewardSetting(String str, IPointRewardListener<PLVRewardSettingVO> iPointRewardListener);

    void getRemainingRewardPoint(String str, String str2, String str3, IPointRewardListener<String> iPointRewardListener);

    void makeGiftCashReward(String str, int i2, int i3, String str2, String str3, String str4, String str5, IPointRewardListener<PLVDonateGoodResponseVO> iPointRewardListener);

    void makePointReward(String str, int i2, int i3, String str2, String str3, String str4, IPointRewardListener<String> iPointRewardListener);
}
