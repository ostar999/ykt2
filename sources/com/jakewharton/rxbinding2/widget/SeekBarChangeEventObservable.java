package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class SeekBarChangeEventObservable extends InitialValueObservable<SeekBarChangeEvent> {
    private final SeekBar view;

    public static final class Listener extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final Observer<? super SeekBarChangeEvent> observer;
        private final SeekBar view;

        public Listener(SeekBar seekBar, Observer<? super SeekBarChangeEvent> observer) {
            this.view = seekBar;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnSeekBarChangeListener(null);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(SeekBarProgressChangeEvent.create(seekBar, i2, z2));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(SeekBarStartChangeEvent.create(seekBar));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(SeekBarStopChangeEvent.create(seekBar));
        }
    }

    public SeekBarChangeEventObservable(SeekBar seekBar) {
        this.view = seekBar;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super SeekBarChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnSeekBarChangeListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public SeekBarChangeEvent getInitialValue() {
        SeekBar seekBar = this.view;
        return SeekBarProgressChangeEvent.create(seekBar, seekBar.getProgress(), false);
    }
}
