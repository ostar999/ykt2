package com.jakewharton.rxbinding2.widget;

import android.database.DataSetObserver;
import android.widget.Adapter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class AdapterDataChangeObservable<T extends Adapter> extends InitialValueObservable<T> {
    private final T adapter;

    public static final class ObserverDisposable<T extends Adapter> extends MainThreadDisposable {
        private final T adapter;
        final DataSetObserver dataSetObserver;

        public ObserverDisposable(final T t2, final Observer<? super T> observer) {
            this.adapter = t2;
            this.dataSetObserver = new DataSetObserver() { // from class: com.jakewharton.rxbinding2.widget.AdapterDataChangeObservable.ObserverDisposable.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    if (ObserverDisposable.this.isDisposed()) {
                        return;
                    }
                    observer.onNext(t2);
                }
            };
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.adapter.unregisterDataSetObserver(this.dataSetObserver);
        }
    }

    public AdapterDataChangeObservable(T t2) {
        this.adapter = t2;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super T> observer) {
        if (Preconditions.checkMainThread(observer)) {
            ObserverDisposable observerDisposable = new ObserverDisposable(this.adapter, observer);
            this.adapter.registerDataSetObserver(observerDisposable.dataSetObserver);
            observer.onSubscribe(observerDisposable);
        }
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public T getInitialValue() {
        return this.adapter;
    }
}
