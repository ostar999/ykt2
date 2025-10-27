package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* loaded from: classes4.dex */
final class RatingBarRatingChangeEventObservable extends InitialValueObservable<RatingBarChangeEvent> {
    private final RatingBar view;

    public static final class Listener extends MainThreadDisposable implements RatingBar.OnRatingBarChangeListener {
        private final Observer<? super RatingBarChangeEvent> observer;
        private final RatingBar view;

        public Listener(RatingBar ratingBar, Observer<? super RatingBarChangeEvent> observer) {
            this.view = ratingBar;
            this.observer = observer;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        public void onDispose() {
            this.view.setOnRatingBarChangeListener(null);
        }

        @Override // android.widget.RatingBar.OnRatingBarChangeListener
        public void onRatingChanged(RatingBar ratingBar, float f2, boolean z2) {
            if (isDisposed()) {
                return;
            }
            this.observer.onNext(RatingBarChangeEvent.create(ratingBar, f2, z2));
        }
    }

    public RatingBarRatingChangeEventObservable(RatingBar ratingBar) {
        this.view = ratingBar;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public void subscribeListener(Observer<? super RatingBarChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            Listener listener = new Listener(this.view, observer);
            this.view.setOnRatingBarChangeListener(listener);
            observer.onSubscribe(listener);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    public RatingBarChangeEvent getInitialValue() {
        RatingBar ratingBar = this.view;
        return RatingBarChangeEvent.create(ratingBar, ratingBar.getRating(), false);
    }
}
