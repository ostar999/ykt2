package com.easefun.polyv.livecommon.module.modules.beauty.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.easefun.polyv.livecommon.module.modules.beauty.model.config.PLVBeautyOptionDefaultConfig;
import com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource;
import com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautySdkDataSource;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVBeautyRepo {
    private final PLVBeautySdkDataSource beautyModuleDataSource;
    private final PLVBeautyLocalDataSource localDataSource;

    public PLVBeautyRepo(final PLVBeautyLocalDataSource localDataSource, final PLVBeautySdkDataSource beautyModuleDataSource) {
        this.localDataSource = localDataSource;
        this.beautyModuleDataSource = beautyModuleDataSource;
    }

    private static Map<PLVBeautyOption, Float> createDefaultBeautyOptionIntensityMap() {
        for (Map.Entry<PLVBeautyOption, Float> entry : PLVBeautyOptionDefaultConfig.DEFAULT_BEAUTY_OPTION_VALUE.entrySet()) {
            entry.getKey().intensity(entry.getValue().floatValue());
        }
        return new EnumMap(PLVBeautyOptionDefaultConfig.DEFAULT_BEAUTY_OPTION_VALUE);
    }

    private List<PLVFilterOption> createDefaultFilterOptionList() {
        List<PLVFilterOption> supportFilterOptions = this.beautyModuleDataSource.getSupportFilterOptions();
        Iterator<PLVFilterOption> it = supportFilterOptions.iterator();
        while (it.hasNext()) {
            it.next().intensity(0.5f);
        }
        return supportFilterOptions;
    }

    public void closeBeautyOption() {
        this.beautyModuleDataSource.clearBeautyOption();
    }

    public void closeFilterOption() {
        this.beautyModuleDataSource.updateFilterOption(null);
    }

    public Observable<Boolean> getBeautyInitFinishObservable() {
        return this.beautyModuleDataSource.beautyInitFinishObservable;
    }

    public List<PLVBeautyOption> getBeautyOptionList() {
        Map<PLVBeautyOption, Float> value = this.localDataSource.getBeautyOptionIntensityLiveData().getValue();
        if (value != null) {
            for (Map.Entry<PLVBeautyOption, Float> entry : value.entrySet()) {
                entry.getKey().intensity(entry.getValue().floatValue());
            }
        } else {
            value = createDefaultBeautyOptionIntensityMap();
            this.localDataSource.updateBeautyOptionIntensityMap(value);
        }
        return new ArrayList(value.keySet());
    }

    public LiveData<Boolean> getBeautySwitchLiveData() {
        return this.localDataSource.getBeautySwitchLiveData();
    }

    public List<PLVFilterOption> getFilterOptionList() {
        List<PLVFilterOption> listCreateDefaultFilterOptionList = createDefaultFilterOptionList();
        Map<String, Float> value = this.localDataSource.getFilterKeyIntensityLiveData().getValue();
        if (value != null) {
            for (PLVFilterOption pLVFilterOption : listCreateDefaultFilterOptionList) {
                if (value.containsKey(pLVFilterOption.getKey())) {
                    pLVFilterOption.intensity(value.get(pLVFilterOption.getKey()).floatValue());
                }
            }
        }
        return new ArrayList(listCreateDefaultFilterOptionList);
    }

    public LiveData<String> getLastUsedFilterKeyLiveData() {
        return this.localDataSource.getLastUsedFilterKeyLiveData();
    }

    @Nullable
    public PLVFilterOption getLastUsedFilterOption() {
        String value = this.localDataSource.getLastUsedFilterKeyLiveData().getValue();
        if (value == null) {
            return null;
        }
        for (PLVFilterOption pLVFilterOption : getFilterOptionList()) {
            if (value.equals(pLVFilterOption.getKey())) {
                return pLVFilterOption;
            }
        }
        return null;
    }

    public void setBeautySwitch(boolean on) {
        this.localDataSource.updateBeautySwitch(on);
    }

    public void updateBeautyOption(@NonNull PLVBeautyOption beautyOption, float intensity) {
        Map<PLVBeautyOption, Float> value = this.localDataSource.getBeautyOptionIntensityLiveData().getValue();
        if (value == null) {
            value = createDefaultBeautyOptionIntensityMap();
        }
        beautyOption.intensity(intensity);
        value.put(beautyOption, Float.valueOf(intensity));
        this.localDataSource.updateBeautyOptionIntensityMap(value);
        this.beautyModuleDataSource.updateBeautyOption(beautyOption);
    }

    public void updateFilterOption(PLVFilterOption filterOption, float intensity) {
        if (filterOption == null) {
            filterOption = PLVFilterOption.getNoEffectOption();
        }
        Map<String, Float> value = this.localDataSource.getFilterKeyIntensityLiveData().getValue();
        if (value == null) {
            value = new HashMap<>(16);
        }
        filterOption.intensity(intensity);
        value.put(filterOption.getKey(), Float.valueOf(intensity));
        this.localDataSource.updateFilterKeyIntensityMap(value);
        this.localDataSource.updateLastUsedFilterKey(filterOption.getKey());
        this.beautyModuleDataSource.updateFilterOption(filterOption);
    }
}
