package com.plv.foundationsdk.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes4.dex */
public class PLVRxBaseTransformer<U, D> implements ObservableTransformer<U, D> {
    @Override // io.reactivex.ObservableTransformer
    public ObservableSource<D> apply(Observable<U> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
