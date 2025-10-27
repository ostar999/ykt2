package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewTreeObserver;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
final class ViewTreeObserverPreDrawObservable extends Observable<Object> {
    private final Callable<Boolean> proceedDrawingPass;
    private final View view;

    public static final class Listener extends MainThreadDisposable implements ViewTreeObserver.OnPreDrawListener {
        private final Observer<? super Object> observer;
        private final Callable<Boolean> proceedDrawingPass;
        private final View view;

        public Listener(View view, Callable<Boolean> callable, Observer<? super Object> observer) {
            this.view = view;
            this.proceedDrawingPass = callable;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.getViewTreeObserver().removeOnPreDrawListener(this);
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (isDisposed()) {
                return true;
            }
            this.observer.onNext(Notification.INSTANCE);
            try {
                return this.proceedDrawingPass.call().booleanValue();
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return true;
            }
        }
    }

    public ViewTreeObserverPreDrawObservable(View view, Callable<Boolean> callable) {
        this.view = view;
        this.proceedDrawingPass = callable;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, this.proceedDrawingPass, observer);
            observer.onSubscribe(listener);
            this.view.getViewTreeObserver().addOnPreDrawListener(listener);
        }
    }
}
