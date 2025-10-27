package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase;

import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;

/* loaded from: classes3.dex */
public class PLVBeautySwitchUseCase {
    private final PLVBeautyRepo beautyRepo;

    public PLVBeautySwitchUseCase(PLVBeautyRepo beautyRepo) {
        this.beautyRepo = beautyRepo;
    }

    private void beautySwitchOff() {
        this.beautyRepo.setBeautySwitch(false);
        this.beautyRepo.closeBeautyOption();
        this.beautyRepo.closeFilterOption();
    }

    private void beautySwitchOn() {
        this.beautyRepo.setBeautySwitch(true);
        for (PLVBeautyOption pLVBeautyOption : this.beautyRepo.getBeautyOptionList()) {
            this.beautyRepo.updateBeautyOption(pLVBeautyOption, pLVBeautyOption.getIntensity());
        }
        PLVFilterOption lastUsedFilterOption = this.beautyRepo.getLastUsedFilterOption();
        if (lastUsedFilterOption != null) {
            this.beautyRepo.updateFilterOption(lastUsedFilterOption, lastUsedFilterOption.getIntensity());
        }
    }

    public void switchBeauty(boolean switchOn) {
        if (switchOn) {
            beautySwitchOn();
        } else {
            beautySwitchOff();
        }
    }
}
