package com.jakewharton.rxbinding2.view;

import android.view.KeyEvent;
import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* loaded from: classes4.dex */
final class ViewKeyObservable extends Observable<KeyEvent> {
    private final Predicate<? super KeyEvent> handled;
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnKeyListener {
        private final Predicate<? super KeyEvent> handled;
        private final Observer<? super KeyEvent> observer;
        private final View view;

        public Listener(View view, Predicate<? super KeyEvent> predicate, Observer<? super KeyEvent> observer) {
            this.view = view;
            this.handled = predicate;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnKeyListener(null);
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i2, KeyEvent keyEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.handled.test(keyEvent)) {
                    return false;
                }
                this.observer.onNext(keyEvent);
                return true;
            } catch (Exception e2) {
                this.observer.onError(e2);
                dispose();
                return false;
            }
        }
    }

    public ViewKeyObservable(View view, Predicate<? super KeyEvent> predicate) {
        this.view = view;
        this.handled = predicate;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super KeyEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, this.handled, observer);
            observer.onSubscribe(listener);
            this.view.setOnKeyListener(listener);
        }
    }
}
