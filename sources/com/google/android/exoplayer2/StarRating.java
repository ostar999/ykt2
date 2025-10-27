package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes3.dex */
public final class StarRating extends Rating {
    public static final Bundleable.Creator<StarRating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.c2
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return StarRating.fromBundle(bundle);
        }
    };
    private static final int FIELD_MAX_STARS = 1;
    private static final int FIELD_STAR_RATING = 2;
    private static final int MAX_STARS_DEFAULT = 5;
    private static final int TYPE = 2;

    @IntRange(from = 1)
    private final int maxStars;
    private final float starRating;

    public StarRating(@IntRange(from = 1) int i2) {
        Assertions.checkArgument(i2 > 0, "maxStars must be a positive integer");
        this.maxStars = i2;
        this.starRating = -1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static StarRating fromBundle(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(keyForField(0), -1) == 2);
        int i2 = bundle.getInt(keyForField(1), 5);
        float f2 = bundle.getFloat(keyForField(2), -1.0f);
        return f2 == -1.0f ? new StarRating(i2) : new StarRating(i2, f2);
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof StarRating)) {
            return false;
        }
        StarRating starRating = (StarRating) obj;
        return this.maxStars == starRating.maxStars && this.starRating == starRating.starRating;
    }

    @IntRange(from = 1)
    public int getMaxStars() {
        return this.maxStars;
    }

    public float getStarRating() {
        return this.starRating;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.maxStars), Float.valueOf(this.starRating));
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.starRating != -1.0f;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), 2);
        bundle.putInt(keyForField(1), this.maxStars);
        bundle.putFloat(keyForField(2), this.starRating);
        return bundle;
    }

    public StarRating(@IntRange(from = 1) int i2, @FloatRange(from = 0.0d) float f2) {
        Assertions.checkArgument(i2 > 0, "maxStars must be a positive integer");
        Assertions.checkArgument(f2 >= 0.0f && f2 <= ((float) i2), "starRating is out of range [0, maxStars]");
        this.maxStars = i2;
        this.starRating = f2;
    }
}
