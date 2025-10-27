package com.easefun.polyv.livecommon.module.modules.commodity.viewmodel;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo;
import com.easefun.polyv.livecommon.module.modules.commodity.model.vo.PLVCommodityProductVO;
import com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.vo.PLVCommodityUiState;
import com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.commodity.PLVProductContentBean;
import com.plv.socket.event.commodity.PLVProductControlEvent;
import com.plv.socket.event.commodity.PLVProductEvent;
import com.plv.socket.event.commodity.PLVProductMenuSwitchEvent;
import com.plv.socket.event.commodity.PLVProductRemoveEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PLVCommodityViewModel implements IPLVLifecycleAwareDependComponent {
    private final PLVCommodityRepo commodityRepo;
    private final MutableLiveData<PLVCommodityUiState> commodityUiStateLiveData = new MutableLiveData<>();
    private final PLVCommodityUiState commodityUiState = new PLVCommodityUiState();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PLVCommodityViewModel(final PLVCommodityRepo commodityRepo) {
        this.commodityRepo = commodityRepo;
        initUiState();
        observeProductEvent();
    }

    private void initUiState() {
        PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
        pLVCommodityUiState.productContentBeanPushToShow = null;
        this.commodityUiStateLiveData.postValue(pLVCommodityUiState.copy());
    }

    private void observeProductEvent() {
        this.compositeDisposable.add(this.commodityRepo.productObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).doOnNext(new Consumer<PLVCommodityProductVO>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel.2
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVCommodityProductVO commodityProductVO) throws Exception {
                PLVCommodityViewModel.this.reduceProductEventUpdate(commodityProductVO);
            }
        }).doOnError(new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        }).retry().subscribe());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public void reduceProductEventUpdate(final PLVCommodityProductVO commodityProductVO) {
        PLVProductContentBean pLVProductContentBean;
        String message = commodityProductVO.getMessage().getMessage();
        PLVProductEvent pLVProductEvent = (PLVProductEvent) PLVGsonUtil.fromJson(PLVProductEvent.class, message);
        if (pLVProductEvent == null) {
            return;
        }
        if (pLVProductEvent.isProductControlEvent()) {
            PLVProductControlEvent pLVProductControlEvent = (PLVProductControlEvent) PLVEventHelper.toMessageEventModel(message, PLVProductControlEvent.class);
            if (pLVProductControlEvent == null || pLVProductControlEvent.getContent() == null) {
                return;
            }
            if (pLVProductControlEvent.isRedact()) {
                PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
                if (pLVCommodityUiState.productContentBeanPushToShow != null) {
                    pLVCommodityUiState.productContentBeanPushToShow = pLVProductControlEvent.getContent();
                    this.commodityUiStateLiveData.postValue(this.commodityUiState.copy());
                }
            }
            if (pLVProductControlEvent.isPush()) {
                this.commodityUiState.productContentBeanPushToShow = pLVProductControlEvent.getContent();
                this.commodityUiStateLiveData.postValue(this.commodityUiState.copy());
                return;
            }
            return;
        }
        if (pLVProductEvent.isProductRemoveEvent()) {
            PLVProductRemoveEvent pLVProductRemoveEvent = (PLVProductRemoveEvent) PLVEventHelper.toMessageEventModel(message, PLVProductRemoveEvent.class);
            if (pLVProductRemoveEvent == null || pLVProductRemoveEvent.getContent() == null || (pLVProductContentBean = this.commodityUiState.productContentBeanPushToShow) == null || pLVProductContentBean.getProductId() != pLVProductRemoveEvent.getContent().getProductId()) {
                return;
            }
            PLVCommodityUiState pLVCommodityUiState2 = this.commodityUiState;
            pLVCommodityUiState2.productContentBeanPushToShow = null;
            this.commodityUiStateLiveData.postValue(pLVCommodityUiState2.copy());
            return;
        }
        if (!pLVProductEvent.isProductMoveEvent() && pLVProductEvent.isProductMenuSwitchEvent()) {
            final PLVProductMenuSwitchEvent pLVProductMenuSwitchEvent = (PLVProductMenuSwitchEvent) PLVEventHelper.toMessageEventModel(message, PLVProductMenuSwitchEvent.class);
            boolean zBooleanValue = ((Boolean) PLVSugarUtil.getNullableOrDefault(new PLVSugarUtil.Supplier<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.viewmodel.PLVCommodityViewModel.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                public Boolean get() {
                    return Boolean.valueOf(pLVProductMenuSwitchEvent.getContent().isEnabled());
                }
            }, Boolean.FALSE)).booleanValue();
            PLVCommodityUiState pLVCommodityUiState3 = this.commodityUiState;
            if (pLVCommodityUiState3.hasProductView == zBooleanValue) {
                return;
            }
            pLVCommodityUiState3.hasProductView = zBooleanValue;
            if (!zBooleanValue) {
                pLVCommodityUiState3.productContentBeanPushToShow = null;
            }
            this.commodityUiStateLiveData.postValue(pLVCommodityUiState3.copy());
        }
    }

    public LiveData<PLVCommodityUiState> getCommodityUiStateLiveData() {
        return this.commodityUiStateLiveData;
    }

    public void notifyHasProductLayout(boolean hasProductLayout) {
        PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
        pLVCommodityUiState.hasProductView = hasProductLayout;
        this.commodityUiStateLiveData.postValue(pLVCommodityUiState.copy());
    }

    @Override // com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent
    public void onCleared() {
        this.compositeDisposable.dispose();
    }

    public void onCloseProductPush() {
        PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
        pLVCommodityUiState.productContentBeanPushToShow = null;
        this.commodityUiStateLiveData.postValue(pLVCommodityUiState.copy());
    }

    public void onLandscapeProductLayoutHide() {
        PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
        pLVCommodityUiState.showProductViewOnLandscape = false;
        this.commodityUiStateLiveData.postValue(pLVCommodityUiState.copy());
    }

    public void showProductLayoutOnLandscape() {
        PLVCommodityUiState pLVCommodityUiState = this.commodityUiState;
        pLVCommodityUiState.showProductViewOnLandscape = true;
        this.commodityUiStateLiveData.postValue(pLVCommodityUiState.copy());
    }
}
