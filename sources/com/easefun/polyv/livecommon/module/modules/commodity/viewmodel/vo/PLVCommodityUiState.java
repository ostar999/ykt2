package com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo;

import androidx.annotation.Nullable;
import com.plv.socket.event.commodity.PLVProductContentBean;

/* loaded from: classes3.dex */
public class PLVCommodityUiState {

    @Nullable
    public PLVProductContentBean productContentBeanPushToShow = null;
    public boolean hasProductView = false;
    public boolean showProductViewOnLandscape = false;

    public PLVCommodityUiState copy() {
        PLVCommodityUiState pLVCommodityUiState = new PLVCommodityUiState();
        pLVCommodityUiState.productContentBeanPushToShow = this.productContentBeanPushToShow;
        pLVCommodityUiState.hasProductView = this.hasProductView;
        pLVCommodityUiState.showProductViewOnLandscape = this.showProductViewOnLandscape;
        return pLVCommodityUiState;
    }
}
