package com.jakewharton.rxbinding2.widget;

import android.widget.RatingBar;
import androidx.annotation.NonNull;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class AutoValue_RatingBarChangeEvent extends RatingBarChangeEvent {
    private final boolean fromUser;
    private final float rating;
    private final RatingBar view;

    public AutoValue_RatingBarChangeEvent(RatingBar ratingBar, float f2, boolean z2) {
        if (ratingBar == null) {
            throw new NullPointerException("Null view");
        }
        this.view = ratingBar;
        this.rating = f2;
        this.fromUser = z2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RatingBarChangeEvent)) {
            return false;
        }
        RatingBarChangeEvent ratingBarChangeEvent = (RatingBarChangeEvent) obj;
        return this.view.equals(ratingBarChangeEvent.view()) && Float.floatToIntBits(this.rating) == Float.floatToIntBits(ratingBarChangeEvent.rating()) && this.fromUser == ratingBarChangeEvent.fromUser();
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    public boolean fromUser() {
        return this.fromUser;
    }

    public int hashCode() {
        return ((((this.view.hashCode() ^ 1000003) * 1000003) ^ Float.floatToIntBits(this.rating)) * 1000003) ^ (this.fromUser ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag);
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    public float rating() {
        return this.rating;
    }

    public String toString() {
        return "RatingBarChangeEvent{view=" + this.view + ", rating=" + this.rating + ", fromUser=" + this.fromUser + "}";
    }

    @Override // com.jakewharton.rxbinding2.widget.RatingBarChangeEvent
    @NonNull
    public RatingBar view() {
        return this.view;
    }
}
