package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class AdapterViewItemClickObservable extends Observable<Integer> {
    private final AdapterView<?> view;

    public static final class Listener extends MainThreadDisposable implements AdapterView.OnItemClickListener {
        private final Observer<? super Integer> observer;
        private final AdapterView<?> view;

        public Listener(AdapterView<?> adapterView, Observer<? super Integer> observer) {
            this.view = adapterView;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnItemClickListener(null);
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Integer.valueOf(i2));
        }
    }

    public AdapterViewItemClickObservable(AdapterView<?> adapterView) {
        this.view = adapterView;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnItemClickListener(listener);
        }
    }
}
