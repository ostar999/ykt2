package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

@RequiresApi(23)
/* loaded from: classes4.dex */
final class ViewScrollChangeEventObservable extends Observable<ViewScrollChangeEvent> {
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnScrollChangeListener {
        private final Observer<? super ViewScrollChangeEvent> observer;
        private final View view;

        public Listener(View view, Observer<? super ViewScrollChangeEvent> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnScrollChangeListener(null);
        }

        @Override // android.view.View.OnScrollChangeListener
        public void onScrollChange(View view, int i2, int i3, int i4, int i5) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(ViewScrollChangeEvent.create(view, i2, i3, i4, i5));
        }
    }

    public ViewScrollChangeEventObservable(View view) {
        this.view = view;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super ViewScrollChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnScrollChangeListener(listener);
        }
    }
}
