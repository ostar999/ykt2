package com.plv.foundationsdk.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes4.dex */
public class PLVRxRetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    private PLVRxRetryWithDelayListener listener;
    private final int maxRetries;
    private int retryCount;
    private final int retryDelayMillis;

    public interface PLVRxRetryWithDelayListener {
        void onRetry(Throwable th);

        void onRetryEnd(Throwable th);
    }

    public PLVRxRetryWithDelay(int i2, int i3) {
        this.maxRetries = i2;
        this.retryDelayMillis = i3;
    }

    public static /* synthetic */ int access$004(PLVRxRetryWithDelay pLVRxRetryWithDelay) {
        int i2 = pLVRxRetryWithDelay.retryCount + 1;
        pLVRxRetryWithDelay.retryCount = i2;
        return i2;
    }

    @Override // io.reactivex.functions.Function
    public Observable<?> apply(@NotNull Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() { // from class: com.plv.foundationsdk.rx.PLVRxRetryWithDelay.1
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(@NotNull Throwable th) throws Exception {
                if (PLVRxRetryWithDelay.access$004(PLVRxRetryWithDelay.this) > PLVRxRetryWithDelay.this.maxRetries) {
                    PLVRxRetryWithDelay.this.listener.onRetryEnd(th);
                    return Observable.error(th);
                }
                if (PLVRxRetryWithDelay.this.listener != null) {
                    PLVRxRetryWithDelay.this.listener.onRetry(th);
                }
                return Observable.timer(PLVRxRetryWithDelay.this.retryDelayMillis, TimeUnit.MILLISECONDS);
            }
        });
    }

    public PLVRxRetryWithDelay(int i2, int i3, PLVRxRetryWithDelayListener pLVRxRetryWithDelayListener) {
        this.maxRetries = i2;
        this.retryDelayMillis = i3;
        this.listener = pLVRxRetryWithDelayListener;
    }
}
