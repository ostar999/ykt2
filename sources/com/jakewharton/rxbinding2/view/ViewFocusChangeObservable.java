package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class ViewFocusChangeObservable extends InitialValueObservable<Boolean> {
    private final View view;

    public static final class Listener extends MainThreadDisposable implements View.OnFocusChangeListener {
        private final Observer<? super Boolean> observer;
        private final View view;

        public Listener(View view, Observer<? super Boolean> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnFocusChangeListener(null);
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Boolean.valueOf(z2));
        }
    }

    public ViewFocusChangeObservable(View view) {
        this.view = view;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super Boolean> observer) {
        Listener listener = new Listener(this.view, observer);
        observer.onSubscribe(listener);
        this.view.setOnFocusChangeListener(listener);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.view.hasFocus());
    }
}
