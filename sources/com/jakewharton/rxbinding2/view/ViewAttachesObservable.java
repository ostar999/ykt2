package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class ViewAttachesObservable extends Observable<Object> {
    private final boolean callOnAttach;
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        private final boolean callOnAttach;
        private final Observer<? super Object> observer;
        private final View view;

        public Listener(View view, boolean z2, Observer<? super Object> observer) {
            this.view = view;
            this.callOnAttach = z2;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            if (!this.callOnAttach || isDisposed()) {
                return;
            }
            this.observer.onNext(Notification.INSTANCE);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (this.callOnAttach || isDisposed()) {
                return;
            }
            this.observer.onNext(Notification.INSTANCE);
        }
    }

    public ViewAttachesObservable(View view, boolean z2) {
        this.view = view;
        this.callOnAttach = z2;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, this.callOnAttach, observer);
            observer.onSubscribe(listener);
            this.view.addOnAttachStateChangeListener(listener);
        }
    }
}
