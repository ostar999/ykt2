package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class ViewLayoutChangeEventObservable extends Observable<ViewLayoutChangeEvent> {
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnLayoutChangeListener {
        private final Observer<? super ViewLayoutChangeEvent> observer;
        private final View view;

        public Listener(View view, Observer<? super ViewLayoutChangeEvent> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.removeOnLayoutChangeListener(this);
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(ViewLayoutChangeEvent.create(view, i2, i3, i4, i5, i6, i7, i8, i9));
        }
    }

    public ViewLayoutChangeEventObservable(View view) {
        this.view = view;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super ViewLayoutChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.addOnLayoutChangeListener(listener);
        }
    }
}
