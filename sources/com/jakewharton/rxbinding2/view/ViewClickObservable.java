package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class ViewClickObservable extends Observable<Object> {
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final Observer<? super Object> observer;
        private final View view;

        public Listener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Notification.INSTANCE);
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnClickListener(null);
        }
    }

    public ViewClickObservable(View view) {
        this.view = view;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnClickListener(listener);
        }
    }
}
