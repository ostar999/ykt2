package com.plv.foundationsdk.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;

/* loaded from: classes4.dex */
public class PLVRxBus {
    private final Relay<Object> mBus = PublishRelay.create().toSerialized();

    public static class Holder {
        private static final PLVRxBus BUS = new PLVRxBus();

        private Holder() {
        }
    }

    public static PLVRxBus get() {
        return Holder.BUS;
    }

    public boolean hasObservers() {
        return this.mBus.hasObservers();
    }

    public void post(Object obj) {
        if (hasObservers()) {
            this.mBus.accept(obj);
        }
    }

    public <T> Observable<T> toObservable(Class<T> cls) {
        return (Observable<T>) this.mBus.ofType(cls);
    }

    public Observable<Object> toObservable() {
        return this.mBus;
    }
}
