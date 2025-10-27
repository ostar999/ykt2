package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.vo;

import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVBeautyOptionsUiState {
    public List<PLVBeautyOption> beautyOptions;
    public List<PLVBeautyOption> detailOptions;
    public List<PLVFilterOption> filterOptions;

    public PLVBeautyOptionsUiState copy() {
        PLVBeautyOptionsUiState pLVBeautyOptionsUiState = new PLVBeautyOptionsUiState();
        pLVBeautyOptionsUiState.beautyOptions = this.beautyOptions;
        pLVBeautyOptionsUiState.filterOptions = this.filterOptions;
        pLVBeautyOptionsUiState.detailOptions = this.detailOptions;
        return pLVBeautyOptionsUiState;
    }
}
