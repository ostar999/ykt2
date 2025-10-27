package com.jakewharton.rxbinding2.widget;

import android.widget.RadioGroup;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class RadioGroupCheckedChangeObservable extends InitialValueObservable<Integer> {
    private final RadioGroup view;

    public static final class Listener extends MainThreadDisposable implements RadioGroup.OnCheckedChangeListener {
        private int lastChecked = -1;
        private final Observer<? super Integer> observer;
        private final RadioGroup view;

        public Listener(RadioGroup radioGroup, Observer<? super Integer> observer) {
            this.view = radioGroup;
            this.observer = observer;
        }

        @Override // android.widget.RadioGroup.OnCheckedChangeListener
        public void onCheckedChanged(RadioGroup radioGroup, int i2) {
            if (isDisposed() || i2 == this.lastChecked) {
                return;
            }
            this.lastChecked = i2;
            this.observer.onNext(Integer.valueOf(i2));
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnCheckedChangeListener(null);
        }
    }

    public RadioGroupCheckedChangeObservable(RadioGroup radioGroup) {
        this.view = radioGroup;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnCheckedChangeListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public Integer getInitialValue() {
        return Integer.valueOf(this.view.getCheckedRadioButtonId());
    }
}
