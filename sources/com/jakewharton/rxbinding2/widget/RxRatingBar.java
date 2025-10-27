package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public final class RxRatingBar {
    private RxRatingBar() {
        throw new AssertionError("No instances.");
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Boolean> isIndicator(@NonNull final RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new Consumer<Boolean>() { // from class: com.jakewharton.rxbinding2.widget.RxRatingBar.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) {
                ratingBar.setIsIndicator(bool.booleanValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static Consumer<? super Float> rating(@NonNull final RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new Consumer<Float>() { // from class: com.jakewharton.rxbinding2.widget.RxRatingBar.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Float f2) {
                ratingBar.setRating(f2.floatValue());
            }
        };
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<RatingBarChangeEvent> ratingChangeEvents(@NonNull RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new RatingBarRatingChangeEventObservable(ratingBar);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<Float> ratingChanges(@NonNull RatingBar ratingBar) {
        Preconditions.checkNotNull(ratingBar, "view == null");
        return new RatingBarRatingChangeObservable(ratingBar);
    }
}
