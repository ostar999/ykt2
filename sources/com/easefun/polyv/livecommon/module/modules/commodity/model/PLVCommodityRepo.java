package com.easefun.polyv.livecommon.module.modules.commodity.model;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.commodity.model.vo.PLVCommodityProductVO;
import com.easefun.polyv.livecommon.module.modules.socket.PLVSocketMessage;
import com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBus;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.commodity.PLVProductEvent;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes3.dex */
public class PLVCommodityRepo implements IPLVLifecycleAwareDependComponent {
    private Emitter<PLVCommodityProductVO> productEmitter;
    public Observable<PLVCommodityProductVO> productObservable = Observable.create(new ObservableOnSubscribe<PLVCommodityProductVO>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo.1
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NonNull ObservableEmitter<PLVCommodityProductVO> emitter) throws Exception {
            PLVCommodityRepo.this.productEmitter = emitter;
        }
    });
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PLVCommodityRepo() {
        observeSocketMessage();
    }

    private void observeSocketMessage() {
        this.compositeDisposable.add(PLVRxBus.get().toObservable(PLVSocketMessage.class).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).filter(new Predicate<PLVSocketMessage>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo.4
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVSocketMessage message) throws Exception {
                return "message".equals(message.getListenEvent()) && "PRODUCT_MESSAGE".equals(message.getEvent()) && (PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<String>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo.4.1
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public String get() {
                        return PLVSocketWrapper.getInstance().getLoginVO().getChannelId();
                    }
                }) != null);
            }
        }).doOnNext(new Consumer<PLVSocketMessage>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo.3
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVSocketMessage message) throws Exception {
                PLVCommodityRepo.this.productEmitter.onNext(new PLVCommodityProductVO((PLVProductEvent) PLVGsonUtil.fromJson(PLVProductEvent.class, message.getMessage()), message));
            }
        }).doOnError(new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.commodity.model.PLVCommodityRepo.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        }).retry().subscribe());
    }

    @Override // com.plv.foundationsdk.component.di.IPLVLifecycleAwareDependComponent
    public void onCleared() {
        this.compositeDisposable.dispose();
    }
}
