package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase;

import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.easefun.polyv.livecommon.module.modules.beauty.model.config.PLVBeautyOptionDefaultConfig;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class PLVBeautyResetUseCase {
    private final PLVBeautyRepo beautyRepo;

    public PLVBeautyResetUseCase(PLVBeautyRepo beautyRepo) {
        this.beautyRepo = beautyRepo;
    }

    public void reset() {
        for (PLVBeautyOption pLVBeautyOption : this.beautyRepo.getBeautyOptionList()) {
            this.beautyRepo.updateBeautyOption(pLVBeautyOption, PLVBeautyOptionDefaultConfig.DEFAULT_BEAUTY_OPTION_VALUE.get(pLVBeautyOption).floatValue());
        }
        Iterator<PLVFilterOption> it = this.beautyRepo.getFilterOptionList().iterator();
        while (it.hasNext()) {
            this.beautyRepo.updateFilterOption(it.next(), 0.5f);
        }
        this.beautyRepo.updateFilterOption(PLVFilterOption.getNoEffectOption(), 0.5f);
    }
}
