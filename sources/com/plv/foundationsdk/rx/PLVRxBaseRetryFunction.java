package com.plv.foundationsdk.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVRxBaseRetryFunction implements Function<Observable<Throwable>, ObservableSource<?>> {
    private int currentRetryCount;
    private int maxConnectCount;
    private long waitRetryTime;

    public PLVRxBaseRetryFunction(int i2, long j2) {
        this.maxConnectCount = i2;
        this.waitRetryTime = j2;
    }

    public static /* synthetic */ int access$008(PLVRxBaseRetryFunction pLVRxBaseRetryFunction) {
        int i2 = pLVRxBaseRetryFunction.currentRetryCount;
        pLVRxBaseRetryFunction.currentRetryCount = i2 + 1;
        return i2;
    }

    @Override // io.reactivex.functions.Function
    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() { // from class: com.plv.foundationsdk.rx.PLVRxBaseRetryFunction.1
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(Throwable th) throws Exception {
                if (!(th instanceof IOException)) {
                    return Observable.error(th);
                }
                if (PLVRxBaseRetryFunction.this.currentRetryCount >= PLVRxBaseRetryFunction.this.maxConnectCount) {
                    return Observable.error(th);
                }
                PLVRxBaseRetryFunction.access$008(PLVRxBaseRetryFunction.this);
                return Observable.just(1).delay(PLVRxBaseRetryFunction.this.waitRetryTime, TimeUnit.MILLISECONDS);
            }
        });
    }
}
