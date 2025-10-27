package com.easefun.polyv.livecommon.module.modules.popover;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout;
import com.easefun.polyv.livecommon.module.modules.interact.PLVInteractLayout2;
import com.easefun.polyv.livecommon.module.modules.reward.OnPointRewardListener;
import com.easefun.polyv.livecommon.module.modules.reward.PLVPointRewardLayout;

/* loaded from: classes3.dex */
public class PLVPopoverLayout extends RelativeLayout implements IPLVPopoverLayout {
    private IPLVInteractLayout plvLayoutInteract;
    private PLVPointRewardLayout plvLayoutReward;

    public PLVPopoverLayout(@NonNull Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_popover_layout, (ViewGroup) this, true);
        this.plvLayoutReward = (PLVPointRewardLayout) findViewById(R.id.plv_layout_reward);
        this.plvLayoutInteract = (IPLVInteractLayout) findViewById(R.id.plv_layout_interact);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public void destroy() {
        IPLVInteractLayout iPLVInteractLayout = this.plvLayoutInteract;
        if (iPLVInteractLayout != null) {
            iPLVInteractLayout.destroy();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public IPLVInteractLayout getInteractLayout() {
        return this.plvLayoutInteract;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public PLVPointRewardLayout getRewardView() {
        return this.plvLayoutReward;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public void init(PLVLiveScene scene, IPLVLiveRoomDataManager roomDataManager) {
        this.plvLayoutReward.initChannelConfig(PLVLiveChannelConfigFiller.generateNewChannelConfig(), roomDataManager);
        this.plvLayoutReward.changeScene(scene);
        this.plvLayoutInteract.init(roomDataManager, scene);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public boolean onBackPress() {
        return this.plvLayoutInteract.onBackPress() || this.plvLayoutReward.onBackPress();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public void setOnOpenInsideWebViewListener(PLVInteractLayout2.OnOpenInsideWebViewListener listener) {
        this.plvLayoutInteract.setOnOpenInsideWebViewListener(listener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.popover.IPLVPopoverLayout
    public void setOnPointRewardListener(OnPointRewardListener listener) {
        PLVPointRewardLayout pLVPointRewardLayout = this.plvLayoutReward;
        if (pLVPointRewardLayout != null) {
            pLVPointRewardLayout.setOnPointRewardListener(listener);
        }
    }

    public PLVPopoverLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPopoverLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
