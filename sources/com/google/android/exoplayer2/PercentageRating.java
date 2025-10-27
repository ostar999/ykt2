package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes3.dex */
public final class PercentageRating extends Rating {
    public static final Bundleable.Creator<PercentageRating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.s1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return PercentageRating.fromBundle(bundle);
        }
    };
    private static final int FIELD_PERCENT = 1;
    private static final int TYPE = 1;
    private final float percent;

    public PercentageRating() {
        this.percent = -1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PercentageRating fromBundle(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(keyForField(0), -1) == 1);
        float f2 = bundle.getFloat(keyForField(1), -1.0f);
        return f2 == -1.0f ? new PercentageRating() : new PercentageRating(f2);
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PercentageRating) && this.percent == ((PercentageRating) obj).percent;
    }

    public float getPercent() {
        return this.percent;
    }

    public int hashCode() {
        return Objects.hashCode(Float.valueOf(this.percent));
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.percent != -1.0f;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), 1);
        bundle.putFloat(keyForField(1), this.percent);
        return bundle;
    }

    public PercentageRating(@FloatRange(from = 0.0d, to = 100.0d) float f2) {
        Assertions.checkArgument(f2 >= 0.0f && f2 <= 100.0f, "percent must be in the range of [0, 100]");
        this.percent = f2;
    }
}
