package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase;

import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.easefun.polyv.livecommon.module.modules.beauty.model.config.PLVBeautyEnums;
import com.easefun.polyv.livecommon.module.modules.beauty.model.config.PLVBeautyOptionDefaultConfig;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVBeautyOptionListInitUseCase {
    private final PLVBeautyRepo beautyRepo;

    public PLVBeautyOptionListInitUseCase(PLVBeautyRepo beautyRepo) {
        this.beautyRepo = beautyRepo;
    }

    public List<PLVBeautyOption> initBeautyOptionList() {
        List<PLVBeautyOption> beautyOptionList = this.beautyRepo.getBeautyOptionList();
        ArrayList arrayList = new ArrayList(PLVBeautyEnums.BeautyOption.values().length);
        for (PLVBeautyEnums.BeautyOption beautyOption : PLVBeautyEnums.BeautyOption.values()) {
            if (beautyOptionList.contains(beautyOption.beautyOption) && PLVBeautyManager.getInstance().isBeautyOptionSupport(beautyOption.beautyOption)) {
                arrayList.add(beautyOption.beautyOption);
            }
        }
        return arrayList;
    }

    public List<PLVBeautyOption> initDetailOptionList() {
        List<PLVBeautyOption> beautyOptionList = this.beautyRepo.getBeautyOptionList();
        ArrayList arrayList = new ArrayList(PLVBeautyEnums.DetailOption.values().length);
        for (PLVBeautyEnums.DetailOption detailOption : PLVBeautyEnums.DetailOption.values()) {
            if (beautyOptionList.contains(detailOption.beautyOption) && PLVBeautyManager.getInstance().isBeautyOptionSupport(detailOption.beautyOption)) {
                arrayList.add(detailOption.beautyOption);
            }
        }
        return arrayList;
    }

    public List<PLVFilterOption> initFilterOptionList() {
        List<PLVFilterOption> filterOptionList = this.beautyRepo.getFilterOptionList();
        Collections.sort(filterOptionList, new Comparator<PLVFilterOption>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautyOptionListInitUseCase.1
            @Override // java.util.Comparator
            public int compare(PLVFilterOption o12, PLVFilterOption o2) {
                List<String> list = PLVBeautyOptionDefaultConfig.DEFAULT_FILTER_KEY_ORDER;
                int iIndexOf = list.indexOf(o12.getName());
                int iIndexOf2 = list.indexOf(o2.getName());
                if (iIndexOf < 0) {
                    iIndexOf = Integer.MAX_VALUE;
                }
                if (iIndexOf2 < 0) {
                    iIndexOf2 = Integer.MAX_VALUE;
                }
                if (iIndexOf == iIndexOf2) {
                    return 0;
                }
                return iIndexOf < iIndexOf2 ? -1 : 1;
            }
        });
        return filterOptionList;
    }
}
