package com.easefun.polyv.livecommon.module.modules.beauty.model.datasource;

import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PLVBeautySdkDataSource {
    private IPLVBeautyManager.InitCallback beautyInitCallback;
    private ObservableEmitter<Boolean> beautyInitFinishEmitter;
    public final Observable<Boolean> beautyInitFinishObservable = Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautySdkDataSource.1
        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(@NotNull ObservableEmitter<Boolean> emitter) throws Exception {
            PLVBeautySdkDataSource.this.beautyInitFinishEmitter = emitter;
            emitter.onNext(Boolean.valueOf(PLVBeautySdkDataSource.this.initFinish));
        }
    });
    private boolean initFinish = false;

    public PLVBeautySdkDataSource() {
        setBeautyInitCallback();
    }

    private void setBeautyInitCallback() {
        this.beautyInitCallback = new IPLVBeautyManager.InitCallback() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.model.datasource.PLVBeautySdkDataSource.2
            @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
            public void onFinishInit(Integer code) {
                PLVBeautySdkDataSource.this.initFinish = code != null && code.intValue() == 0;
                if (PLVBeautySdkDataSource.this.beautyInitFinishEmitter != null) {
                    PLVBeautySdkDataSource.this.beautyInitFinishEmitter.onNext(Boolean.valueOf(PLVBeautySdkDataSource.this.initFinish));
                }
            }

            @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
            public void onStartInit() {
            }
        };
        PLVBeautyManager.getInstance().addInitCallback(new WeakReference<>(this.beautyInitCallback));
    }

    public void clearBeautyOption() {
        PLVBeautyManager.getInstance().clearBeautyOption();
    }

    public List<PLVFilterOption> getSupportFilterOptions() {
        return new ArrayList(PLVBeautyManager.getInstance().getSupportFilterOption());
    }

    public void updateBeautyOption(PLVBeautyOption beautyOption) {
        PLVBeautyManager.getInstance().updateBeautyOption(beautyOption);
    }

    public void updateFilterOption(PLVFilterOption filterOption) {
        PLVBeautyManager.getInstance().setFilterOption(filterOption);
    }
}
