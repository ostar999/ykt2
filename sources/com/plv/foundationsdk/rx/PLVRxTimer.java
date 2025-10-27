package com.plv.foundationsdk.rx;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVRxTimer {
    public static <T> Disposable delay(long j2, Consumer<T> consumer) {
        return Observable.just(Long.valueOf(j2)).delay(j2, TimeUnit.MILLISECONDS).compose(new PLVRxBaseTransformer()).subscribe((Consumer<? super R>) consumer);
    }

    public static <T> Disposable timer(int i2, int i3, Consumer<T> consumer) {
        return Observable.interval(i2, i3, TimeUnit.MILLISECONDS).compose(new PLVRxBaseTransformer()).subscribe((Consumer<? super R>) consumer);
    }

    public static Disposable timer(int i2, Consumer<Long> consumer) {
        return Observable.interval(0L, i2, TimeUnit.MILLISECONDS).compose(new PLVRxBaseTransformer()).subscribe(consumer);
    }

    public static <T> Disposable delay(long j2, Consumer<T> consumer, TimeUnit timeUnit) {
        return Observable.just(Long.valueOf(j2)).delay(j2, timeUnit).compose(new PLVRxBaseTransformer()).subscribe((Consumer<? super R>) consumer);
    }

    public static Disposable timer(int i2, int i3, Consumer<Long> consumer, boolean z2) {
        if (!z2) {
            return Observable.interval(i2, i3, TimeUnit.MILLISECONDS).subscribe(consumer);
        }
        return Observable.interval(i2, i3, TimeUnit.MILLISECONDS).observeOn(Schedulers.io()).subscribe(consumer);
    }
}
