package com.easefun.polyv.livecommon.module.modules.beauty.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livecommon.module.modules.beauty.model.PLVBeautyRepo;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautyOptionListInitUseCase;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautyResetUseCase;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.usecase.PLVBeautySwitchUseCase;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.vo.PLVBeautyOptionsUiState;
import com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.vo.PLVBeautyUiState;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent;
import com.plv.foundationsdk.component.livedata.Event;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVBeautyViewModel implements IPLVLifecycleAwareDependComponent {
    private final PLVBeautyRepo beautyRepo;
    private final PLVBeautyOptionListInitUseCase optionListInitUseCase;
    private final PLVBeautyResetUseCase resetUseCase;
    private final PLVBeautySwitchUseCase switchUseCase;
    private final MutableLiveData<PLVBeautyUiState> beautyUiStateLiveData = new MutableLiveData<>();
    private final PLVBeautyUiState uiState = new PLVBeautyUiState();
    private final MutableLiveData<PLVBeautyOptionsUiState> beautyOptionsUiStateLiveData = new MutableLiveData<>();
    private final PLVBeautyOptionsUiState optionsUiState = new PLVBeautyOptionsUiState();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public PLVBeautyViewModel(final PLVBeautyRepo beautyRepo, final PLVBeautyOptionListInitUseCase optionListInitUseCase, final PLVBeautySwitchUseCase switchUseCase, final PLVBeautyResetUseCase resetUseCase) {
        this.beautyRepo = beautyRepo;
        this.optionListInitUseCase = optionListInitUseCase;
        this.switchUseCase = switchUseCase;
        this.resetUseCase = resetUseCase;
        initUiState();
        observeBeautyInit();
        observeBeautySwitch();
        observeLastUsedFilterOption();
    }

    private void initUiState() {
        this.uiState.isBeautySupport = PLVBeautyManager.getInstance().isBeautySupport();
        PLVBeautyUiState pLVBeautyUiState = this.uiState;
        pLVBeautyUiState.isBeautyModuleInitSuccess = false;
        pLVBeautyUiState.isBeautyMenuShowing = false;
        pLVBeautyUiState.isBeautyOn = ((Boolean) PLVSugarUtil.getOrDefault(this.beautyRepo.getBeautySwitchLiveData().getValue(), Boolean.TRUE)).booleanValue();
        this.uiState.lastUsedFilterOption = this.beautyRepo.getLastUsedFilterOption();
        this.beautyUiStateLiveData.postValue(this.uiState.copy());
        updateBeautyOptionList();
        switchBeautyOption(this.uiState.isBeautyOn);
    }

    private void observeBeautyInit() {
        this.disposables.add(this.beautyRepo.getBeautyInitFinishObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean initSuccess) throws Exception {
                PLVBeautyViewModel.this.uiState.isBeautyModuleInitSuccess = initSuccess != null && initSuccess.booleanValue();
                PLVBeautyViewModel.this.beautyUiStateLiveData.postValue(PLVBeautyViewModel.this.uiState.copy());
            }
        }).retry().delay(200L, TimeUnit.MILLISECONDS, Schedulers.computation(), false).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean initSuccess) throws Exception {
                if (initSuccess == null || !initSuccess.booleanValue()) {
                    return;
                }
                PLVBeautyViewModel pLVBeautyViewModel = PLVBeautyViewModel.this;
                pLVBeautyViewModel.switchBeautyOption(pLVBeautyViewModel.uiState.isBeautyOn);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        }));
    }

    private void observeBeautySwitch() {
        this.beautyRepo.getBeautySwitchLiveData().observeForever(new Observer<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable Boolean switchOn) {
                PLVBeautyViewModel.this.uiState.isBeautyOn = switchOn == null || switchOn.booleanValue();
                PLVBeautyViewModel.this.beautyUiStateLiveData.postValue(PLVBeautyViewModel.this.uiState.copy());
            }
        });
    }

    private void observeLastUsedFilterOption() {
        this.beautyRepo.getLastUsedFilterKeyLiveData().observeForever(new Observer<String>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.viewmodel.PLVBeautyViewModel.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable String s2) {
                PLVBeautyViewModel.this.uiState.lastUsedFilterOption = PLVBeautyViewModel.this.beautyRepo.getLastUsedFilterOption();
                PLVBeautyViewModel.this.beautyUiStateLiveData.postValue(PLVBeautyViewModel.this.uiState.copy());
            }
        });
    }

    public LiveData<PLVBeautyOptionsUiState> getBeautyOptionsUiState() {
        return this.beautyOptionsUiStateLiveData;
    }

    public LiveData<PLVBeautyUiState> getUiState() {
        return this.beautyUiStateLiveData;
    }

    public void hideBeautyMenu() {
        this.uiState.requestingShowMenu = new Event<>(Boolean.FALSE);
        this.beautyUiStateLiveData.postValue(this.uiState.copy());
    }

    @Override // com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent
    public void onCleared() {
        this.disposables.dispose();
    }

    public void resetAllOption() {
        this.resetUseCase.reset();
    }

    public void setMenuShowing(boolean showing) {
        PLVBeautyUiState pLVBeautyUiState = this.uiState;
        pLVBeautyUiState.isBeautyMenuShowing = showing;
        this.beautyUiStateLiveData.postValue(pLVBeautyUiState.copy());
    }

    public void showBeautyMenu() {
        this.uiState.requestingShowMenu = new Event<>(Boolean.TRUE);
        this.beautyUiStateLiveData.postValue(this.uiState.copy());
    }

    public void switchBeautyOption(boolean switchOn) {
        this.switchUseCase.switchBeauty(switchOn);
    }

    public void updateBeautyOption(PLVBeautyOption option, float intensity) {
        if (this.uiState.isBeautyOn) {
            this.beautyRepo.updateBeautyOption(option, intensity);
        }
    }

    public void updateBeautyOptionList() {
        this.optionsUiState.beautyOptions = this.optionListInitUseCase.initBeautyOptionList();
        this.optionsUiState.filterOptions = this.optionListInitUseCase.initFilterOptionList();
        this.optionsUiState.detailOptions = this.optionListInitUseCase.initDetailOptionList();
        this.beautyOptionsUiStateLiveData.postValue(this.optionsUiState.copy());
    }

    public void updateFilterOption(PLVFilterOption option, float intensity) {
        if (this.uiState.isBeautyOn) {
            this.beautyRepo.updateFilterOption(option, intensity);
        }
    }
}
