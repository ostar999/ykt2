package com.jakewharton.rxbinding2.widget;

import android.widget.SeekBar;
import androidx.annotation.Nullable;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class SeekBarChangeObservable extends InitialValueObservable<Integer> {

    @Nullable
    private final Boolean shouldBeFromUser;
    private final SeekBar view;

    public static final class Listener extends MainThreadDisposable implements SeekBar.OnSeekBarChangeListener {
        private final Observer<? super Integer> observer;
        private final Boolean shouldBeFromUser;
        private final SeekBar view;

        public Listener(SeekBar seekBar, Boolean bool, Observer<? super Integer> observer) {
            this.view = seekBar;
            this.shouldBeFromUser = bool;
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
            Boolean bool = this.shouldBeFromUser;
            if (bool == null || bool.booleanValue() == z2) {
                this.observer.onNext(Integer.valueOf(i2));
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    public SeekBarChangeObservable(SeekBar seekBar, @Nullable Boolean bool) {
        this.view = seekBar;
        this.shouldBeFromUser = bool;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, this.shouldBeFromUser, observer);
            this.view.setOnSeekBarChangeListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public Integer getInitialValue() {
        return Integer.valueOf(this.view.getProgress());
    }
}
