package com.easefun.polyv.livecommon.module.modules.reward.presenter;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource;
import com.plv.livescenes.feature.pointreward.PLVRewardDataSource;
import com.plv.livescenes.feature.pointreward.vo.PLVDonateGoodResponseVO;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class PLVPointRewardPresenter implements IPLVPointRewardContract.IPointRewardPresenter {
    private static final String TAG = "PLVPointRewardPresenter";
    private static final boolean isFreeDonateInGiftCash = true;
    private String currentChannel;
    private PLVLiveChannelConfig.User currentUser;
    private PLVRewardDataSource rewardManager = new PLVRewardDataSource();
    private WeakReference<IPLVPointRewardContract.IPointRewardView> viewRef;

    /* JADX INFO: Access modifiers changed from: private */
    public static String createRewardErrorMessageFromException(Throwable e2) {
        PLVResponseApiBean pLVResponseApiBean;
        if (!(e2 instanceof HttpException)) {
            return e2.getMessage();
        }
        HttpException httpException = (HttpException) e2;
        try {
            return (httpException.response().errorBody() == null || (pLVResponseApiBean = (PLVResponseApiBean) PLVGsonUtil.fromJson(PLVResponseApiBean.class, httpException.response().errorBody().string())) == null) ? "" : pLVResponseApiBean.getCode() == 400 ? pLVResponseApiBean.getMessage() : PLVAppUtils.getString(R.string.plv_reward_make_fail);
        } catch (Exception e3) {
            PLVCommonLog.e(TAG, "createErrorMessageFromException:" + e3.getMessage());
            return "消息解析错误";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCashReward(PLVRewardSettingVO.CashDonateDTO plvRewardSettingVO) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGiftCashReward(PLVRewardSettingVO.GiftDonateDTO cashPays, String cashUnit, boolean isFreeDonate) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        for (PLVRewardSettingVO.GiftDonateDTO.GiftDetailDTO giftDetailDTO : cashPays.getCashPays()) {
            if ("Y".equals(giftDetailDTO.getEnabled())) {
                int i3 = i2 + 1;
                giftDetailDTO.setGoodId(i2);
                if (giftDetailDTO.isFree()) {
                    arrayList.add(giftDetailDTO);
                }
                i2 = i3;
            }
        }
        cashPays.setCashPays(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleGiftPointReward(PLVRewardSettingVO.GiftDonateDTO pointPays, String pointUnit) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        for (PLVRewardSettingVO.GiftDonateDTO.GiftDetailDTO giftDetailDTO : pointPays.getPointPays()) {
            if ("Y".equals(giftDetailDTO.getEnabled())) {
                giftDetailDTO.setGoodId(i2);
                arrayList.add(giftDetailDTO);
                i2++;
            }
        }
        pointPays.setPointPays(arrayList);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void destroy() {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference != null && weakReference.get() != null) {
            this.viewRef.get().destroy();
        }
        this.rewardManager.destroy();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void getPointRewardSetting() {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.rewardManager.getPointRewardSetting(this.currentChannel, new IPLVPointRewardDataSource.IPointRewardListener<PLVRewardSettingVO>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.presenter.PLVPointRewardPresenter.1
            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onFailed(Throwable throwable) {
                PLVCommonLog.exception(throwable);
                if (PLVPointRewardPresenter.this.viewRef == null || PLVPointRewardPresenter.this.viewRef.get() == null) {
                    return;
                }
                ((IPLVPointRewardContract.IPointRewardView) PLVPointRewardPresenter.this.viewRef.get()).showPointRewardEnable(false);
            }

            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onSuccess(PLVRewardSettingVO plvRewardSettingVO) {
                if (plvRewardSettingVO == null) {
                    return;
                }
                if (plvRewardSettingVO.getDonateCashEnabled()) {
                    PLVPointRewardPresenter.this.handleCashReward(plvRewardSettingVO.getCashDonate());
                }
                if (!plvRewardSettingVO.getDonateGiftEnabled() || plvRewardSettingVO.getGiftDonate() == null) {
                    return;
                }
                PLVRewardSettingVO.GiftDonateDTO giftDonate = plvRewardSettingVO.getGiftDonate();
                if ("POINT".equals(giftDonate.getPayWay())) {
                    PLVPointRewardPresenter.this.handleGiftPointReward(giftDonate, giftDonate.getPointUnit());
                } else if ("CASH".equals(giftDonate.getPayWay())) {
                    PLVPointRewardPresenter.this.handleGiftCashReward(giftDonate, giftDonate.getCashUnit(), true);
                }
                if (PLVPointRewardPresenter.this.viewRef == null || PLVPointRewardPresenter.this.viewRef.get() == null) {
                    return;
                }
                ((IPLVPointRewardContract.IPointRewardView) PLVPointRewardPresenter.this.viewRef.get()).initGiftRewardSetting(giftDonate.getPayWay(), giftDonate);
                ((IPLVPointRewardContract.IPointRewardView) PLVPointRewardPresenter.this.viewRef.get()).showPointRewardEnable(true);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void getRemainingRewardPoint() {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.rewardManager.getRemainingRewardPoint(this.currentChannel, this.currentUser.getViewerId(), this.currentUser.getViewerName(), new IPLVPointRewardDataSource.IPointRewardListener<String>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.presenter.PLVPointRewardPresenter.4
            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onFailed(Throwable throwable) {
                PLVToast.Builder.create().setText(PLVPointRewardPresenter.createRewardErrorMessageFromException(throwable)).show();
                throwable.printStackTrace();
            }

            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onSuccess(String point) {
                if (PLVPointRewardPresenter.this.viewRef == null || PLVPointRewardPresenter.this.viewRef.get() == null) {
                    return;
                }
                ((IPLVPointRewardContract.IPointRewardView) PLVPointRewardPresenter.this.viewRef.get()).updateRemainingPoint(point);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void init(String channel, PLVLiveChannelConfig.User user) {
        this.currentChannel = channel;
        this.currentUser = user;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void makeGiftCashReward(int goodId, int goodNum, String sessionId) {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.rewardManager.makeGiftCashReward(this.currentChannel, goodId, goodNum, this.currentUser.getViewerId(), this.currentUser.getViewerName(), this.currentUser.getViewerAvatar(), sessionId, new IPLVPointRewardDataSource.IPointRewardListener<PLVDonateGoodResponseVO>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.presenter.PLVPointRewardPresenter.3
            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onFailed(Throwable throwable) {
                PLVToast.Builder.create().setText(PLVPointRewardPresenter.createRewardErrorMessageFromException(throwable)).show();
                throwable.printStackTrace();
            }

            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onSuccess(PLVDonateGoodResponseVO vo) {
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void makeGiftPointReward(int goodId, int goodNum) {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        this.rewardManager.makePointReward(this.currentChannel, goodId, goodNum, this.currentUser.getViewerId(), this.currentUser.getViewerName(), this.currentUser.getViewerAvatar(), new IPLVPointRewardDataSource.IPointRewardListener<String>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.presenter.PLVPointRewardPresenter.2
            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onFailed(Throwable throwable) {
                PLVToast.Builder.create().setText(PLVPointRewardPresenter.createRewardErrorMessageFromException(throwable)).show();
                throwable.printStackTrace();
            }

            @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource.IPointRewardListener
            public void onSuccess(String point) {
                if (PLVPointRewardPresenter.this.viewRef == null || PLVPointRewardPresenter.this.viewRef.get() == null) {
                    return;
                }
                ((IPLVPointRewardContract.IPointRewardView) PLVPointRewardPresenter.this.viewRef.get()).updateRemainingPoint(point);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void registerView(@NonNull IPLVPointRewardContract.IPointRewardView v2) {
        this.viewRef = new WeakReference<>(v2);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardPresenter
    public void unregisterView() {
        WeakReference<IPLVPointRewardContract.IPointRewardView> weakReference = this.viewRef;
        if (weakReference != null) {
            weakReference.clear();
            this.viewRef = null;
        }
    }
}
