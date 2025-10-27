package com.jakewharton.rxbinding2.widget;

import android.widget.CompoundButton;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class CompoundButtonCheckedChangeObservable extends InitialValueObservable<Boolean> {
    private final CompoundButton view;

    public static final class Listener extends MainThreadDisposable implements CompoundButton.OnCheckedChangeListener {
        private final Observer<? super Boolean> observer;
        private final CompoundButton view;

        public Listener(CompoundButton compoundButton, Observer<? super Boolean> observer) {
            this.view = compoundButton;
            this.observer = observer;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Boolean.valueOf(z2));
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnCheckedChangeListener(null);
        }
    }

    public CompoundButtonCheckedChangeObservable(CompoundButton compoundButton) {
        this.view = compoundButton;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super Boolean> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            observer.onSubscribe(listener);
            this.view.setOnCheckedChangeListener(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.view.isChecked());
    }
}
