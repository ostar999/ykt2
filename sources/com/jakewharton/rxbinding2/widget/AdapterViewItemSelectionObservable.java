package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class AdapterViewItemSelectionObservable extends InitialValueObservable<Integer> {
    private final AdapterView<?> view;

    public static final class Listener extends MainThreadDisposable implements AdapterView.OnItemSelectedListener {
        private final Observer<? super Integer> observer;
        private final AdapterView<?> view;

        public Listener(AdapterView<?> adapterView, Observer<? super Integer> observer) {
            this.view = adapterView;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnItemSelectedListener(null);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(Integer.valueOf(i2));
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(-1);
        }
    }

    public AdapterViewItemSelectionObservable(AdapterView<?> adapterView) {
        this.view = adapterView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnItemSelectedListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public Integer getInitialValue() {
        return Integer.valueOf(this.view.getSelectedItemPosition());
    }
}
