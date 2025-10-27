package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.vo;

import androidx.annotation.Nullable;
import com.plv.beauty.api.options.PLVFilterOption;
import com.plv.foundationsdk.component.livedata.Event;

/* loaded from: classes3.dex */
public class PLVBeautyUiState {
    public boolean isBeautyMenuShowing;
    public boolean isBeautyModuleInitSuccess;
    public boolean isBeautyOn;
    public boolean isBeautySupport;

    @Nullable
    public PLVFilterOption lastUsedFilterOption;
    public Event<Boolean> requestingShowMenu = null;

    public PLVBeautyUiState copy() {
        PLVBeautyUiState pLVBeautyUiState = new PLVBeautyUiState();
        pLVBeautyUiState.isBeautySupport = this.isBeautySupport;
        pLVBeautyUiState.isBeautyModuleInitSuccess = this.isBeautyModuleInitSuccess;
        pLVBeautyUiState.isBeautyMenuShowing = this.isBeautyMenuShowing;
        pLVBeautyUiState.isBeautyOn = this.isBeautyOn;
        pLVBeautyUiState.requestingShowMenu = this.requestingShowMenu;
        pLVBeautyUiState.lastUsedFilterOption = this.lastUsedFilterOption;
        return pLVBeautyUiState;
    }
}
