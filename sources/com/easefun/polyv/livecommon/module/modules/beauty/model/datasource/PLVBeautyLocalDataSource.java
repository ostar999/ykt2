package com.easefun.polyv.livecommon.module.modules.beauty.model.datasource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.foundationsdk.component.livedata.PLVAutoSaveLiveData;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVBeautyLocalDataSource {
    private final MutableLiveData<Map<PLVBeautyOption, Float>> beautyOptionIntensityLiveData = new PLVAutoSaveLiveData<Map<PLVBeautyOption, Float>>("plv_beauty_option_intensity") { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource.1
    };
    private final MutableLiveData<Map<String, Float>> filterKeyIntensityLiveData = new PLVAutoSaveLiveData<Map<String, Float>>("plv_filter_option_intensity") { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource.2
    };
    private final MutableLiveData<String> lastUsedFilterKeyLiveData = new PLVAutoSaveLiveData<String>("plv_last_used_filter_key") { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource.3
    };
    private final MutableLiveData<Boolean> beautySwitchLiveData = new PLVAutoSaveLiveData<Boolean>("plv_beauty_switch") { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautyLocalDataSource.4
    };

    public LiveData<Map<PLVBeautyOption, Float>> getBeautyOptionIntensityLiveData() {
        return this.beautyOptionIntensityLiveData;
    }

    public LiveData<Boolean> getBeautySwitchLiveData() {
        return this.beautySwitchLiveData;
    }

    public LiveData<Map<String, Float>> getFilterKeyIntensityLiveData() {
        return this.filterKeyIntensityLiveData;
    }

    public LiveData<String> getLastUsedFilterKeyLiveData() {
        return this.lastUsedFilterKeyLiveData;
    }

    public void updateBeautyOptionIntensityMap(Map<PLVBeautyOption, Float> beautyOptionIntensityMap) {
        this.beautyOptionIntensityLiveData.postValue(beautyOptionIntensityMap);
    }

    public void updateBeautySwitch(boolean beautySwitch) {
        this.beautySwitchLiveData.postValue(Boolean.valueOf(beautySwitch));
    }

    public void updateFilterKeyIntensityMap(Map<String, Float> filterKeyIntensityMap) {
        this.filterKeyIntensityLiveData.postValue(filterKeyIntensityMap);
    }

    public void updateLastUsedFilterKey(String filterKey) {
        this.lastUsedFilterKeyLiveData.postValue(filterKey);
    }
}
