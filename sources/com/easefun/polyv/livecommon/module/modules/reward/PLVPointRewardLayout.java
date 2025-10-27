package com.easefun.polyv.livecommon.module.modules.reward;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract;
import com.easefun.polyv.livecommon.module.modules.reward.presenter.PLVPointRewardPresenter;
import com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView;
import com.easefun.polyv.livecommon.module.modules.reward.view.vo.PLVRewardItemVO;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class PLVPointRewardLayout extends FrameLayout implements IPLVPointRewardContract.IPointRewardView {
    private static final int REWARD_GIFT_CASH = 2;
    private static final int REWARD_GIFT_POINT = 1;
    private boolean isEnablePointReward;
    private IPLVPointRewardContract.IPointRewardPresenter presenter;
    private PLVRewardDialogView rewardDialogView;
    private OnPointRewardListener rewardListener;
    private int rewardType;
    private String sessionId;

    public PLVPointRewardLayout(@NonNull Context context) {
        this(context, null);
    }

    private void exchangeGiftCashRewardLayout() {
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView != null) {
            pLVRewardDialogView.getRewardTitleTextView().setText("道具打赏");
            this.rewardDialogView.getRemainingPointTextView().setVisibility(4);
        }
    }

    private void exchangeGiftPointRewardLayout() {
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView != null) {
            pLVRewardDialogView.getRewardTitleTextView().setText("积分打赏");
            this.rewardDialogView.getRemainingPointTextView().setVisibility(0);
        }
    }

    private void init() {
        PLVRewardDialogView pLVRewardDialogView = new PLVRewardDialogView((AppCompatActivity) getContext(), this);
        this.rewardDialogView = pLVRewardDialogView;
        pLVRewardDialogView.setMakeRewardListener(new PLVRewardDialogView.OnMakeRewardListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.PLVPointRewardLayout.1
            @Override // com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.OnMakeRewardListener
            public void onMakeReward(PLVBaseViewData data, int rewardNum) {
                PLVPointRewardLayout.this.makeReward(data, rewardNum);
            }
        });
        this.rewardDialogView.setShowListener(new PLVRewardDialogView.OnShowListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.PLVPointRewardLayout.2
            @Override // com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardDialogView.OnShowListener
            public void onShow() {
                if (PLVPointRewardLayout.this.rewardType == 1) {
                    PLVPointRewardLayout.this.presenter.getRemainingRewardPoint();
                }
            }
        });
        PLVPointRewardPresenter pLVPointRewardPresenter = new PLVPointRewardPresenter();
        this.presenter = pLVPointRewardPresenter;
        pLVPointRewardPresenter.registerView(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeReward(PLVBaseViewData data, int rewardNum) {
        if (data == null) {
            return;
        }
        Object data2 = data.getData();
        if (data2 instanceof PLVRewardItemVO) {
            PLVRewardItemVO pLVRewardItemVO = (PLVRewardItemVO) data2;
            int i2 = this.rewardType;
            if (i2 == 1) {
                this.presenter.makeGiftPointReward(pLVRewardItemVO.getGoodId(), rewardNum);
            } else if (i2 == 2) {
                this.presenter.makeGiftCashReward(pLVRewardItemVO.getGoodId(), rewardNum, this.sessionId);
            }
        }
    }

    public void changeScene(PLVLiveScene currentScene) {
        PLVRewardDialogView pLVRewardDialogView;
        if (currentScene == PLVLiveScene.CLOUDCLASS) {
            PLVRewardDialogView pLVRewardDialogView2 = this.rewardDialogView;
            if (pLVRewardDialogView2 != null) {
                pLVRewardDialogView2.getCloseButton().setVisibility(8);
                this.rewardDialogView.getMakeRewardButton().setBackgroundResource(R.drawable.plv_shape_point_reward_point_to_send_btn_pink);
                this.rewardDialogView.changeDialogTop(true);
                return;
            }
            return;
        }
        if (currentScene != PLVLiveScene.ECOMMERCE || (pLVRewardDialogView = this.rewardDialogView) == null) {
            return;
        }
        pLVRewardDialogView.getCloseButton().setVisibility(0);
        this.rewardDialogView.getMakeRewardButton().setBackgroundResource(R.drawable.plv_shape_point_reward_point_to_send_btn_orange);
        this.rewardDialogView.changeDialogTop(false);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public void destroy() {
    }

    public void initChannelConfig(@NonNull PLVLiveChannelConfig config, IPLVLiveRoomDataManager roomDataManager) {
        IPLVPointRewardContract.IPointRewardPresenter iPointRewardPresenter = this.presenter;
        if (iPointRewardPresenter != null && config != null) {
            iPointRewardPresenter.init(config.getChannelId(), config.getUser());
            if (config.isLive()) {
                this.presenter.getPointRewardSetting();
            }
        }
        if (roomDataManager != null) {
            this.sessionId = roomDataManager.getSessionId();
            roomDataManager.getSessionIdLiveData().observe((LifecycleOwner) getContext(), new Observer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.PLVPointRewardLayout.3
                @Override // androidx.lifecycle.Observer
                public void onChanged(@Nullable String s2) {
                    PLVPointRewardLayout.this.sessionId = s2;
                }
            });
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public void initGiftRewardSetting(String payWay, PLVRewardSettingVO.GiftDonateDTO giftReward) throws Resources.NotFoundException {
        ArrayList arrayList = new ArrayList();
        if (giftReward != null) {
            if ("POINT".equals(giftReward.getPayWay())) {
                this.rewardType = 1;
                exchangeGiftPointRewardLayout();
                Iterator<PLVRewardSettingVO.GiftDonateDTO.GiftDetailDTO> it = giftReward.getPointPays().iterator();
                while (it.hasNext()) {
                    arrayList.add(new PLVBaseViewData(new PLVRewardItemVO(it.next(), giftReward.getPointUnit()), 1));
                }
            } else if ("CASH".equals(giftReward.getPayWay())) {
                this.rewardType = 2;
                exchangeGiftCashRewardLayout();
                Iterator<PLVRewardSettingVO.GiftDonateDTO.GiftDetailDTO> it2 = giftReward.getCashPays().iterator();
                while (it2.hasNext()) {
                    arrayList.add(new PLVBaseViewData(new PLVRewardItemVO(it2.next(), giftReward.getCashUnit()), 2));
                }
            }
        }
        this.rewardDialogView.init(arrayList);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public boolean onBackPress() throws Resources.NotFoundException {
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView == null || !pLVRewardDialogView.isShown()) {
            return false;
        }
        this.rewardDialogView.hide();
        return true;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView == null || !this.isEnablePointReward) {
            return;
        }
        int i2 = newConfig.orientation;
        if (i2 == 2) {
            pLVRewardDialogView.changeToLandscape();
        } else if (i2 == 1) {
            pLVRewardDialogView.changeToPortrait();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        IPLVPointRewardContract.IPointRewardPresenter iPointRewardPresenter = this.presenter;
        if (iPointRewardPresenter != null) {
            iPointRewardPresenter.unregisterView();
            this.presenter.destroy();
        }
    }

    public void setOnPointRewardListener(OnPointRewardListener listener) {
        this.rewardListener = listener;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public void showPointRewardDialog(boolean enable) throws Resources.NotFoundException {
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView != null) {
            if (enable) {
                pLVRewardDialogView.show();
            } else {
                pLVRewardDialogView.hide();
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public void showPointRewardEnable(boolean enable) {
        OnPointRewardListener onPointRewardListener = this.rewardListener;
        if (onPointRewardListener != null) {
            onPointRewardListener.pointRewardEnable(enable);
        }
        this.isEnablePointReward = enable;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.reward.contract.IPLVPointRewardContract.IPointRewardView
    public void updateRemainingPoint(String remainingPoint) {
        PLVRewardDialogView pLVRewardDialogView = this.rewardDialogView;
        if (pLVRewardDialogView != null) {
            pLVRewardDialogView.updateRemainingPoint(remainingPoint);
        }
    }

    public PLVPointRewardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPointRewardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isEnablePointReward = false;
        this.rewardType = 1;
        init();
    }
}
