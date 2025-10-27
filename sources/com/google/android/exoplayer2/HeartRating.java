package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes3.dex */
public final class HeartRating extends Rating {
    public static final Bundleable.Creator<HeartRating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.l1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return HeartRating.fromBundle(bundle);
        }
    };
    private static final int FIELD_IS_HEART = 2;
    private static final int FIELD_RATED = 1;
    private static final int TYPE = 0;
    private final boolean isHeart;
    private final boolean rated;

    public HeartRating() {
        this.rated = false;
        this.isHeart = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HeartRating fromBundle(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(keyForField(0), -1) == 0);
        return bundle.getBoolean(keyForField(1), false) ? new HeartRating(bundle.getBoolean(keyForField(2), false)) : new HeartRating();
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof HeartRating)) {
            return false;
        }
        HeartRating heartRating = (HeartRating) obj;
        return this.isHeart == heartRating.isHeart && this.rated == heartRating.rated;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.rated), Boolean.valueOf(this.isHeart));
    }

    public boolean isHeart() {
        return this.isHeart;
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.rated;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), 0);
        bundle.putBoolean(keyForField(1), this.rated);
        bundle.putBoolean(keyForField(2), this.isHeart);
        return bundle;
    }

    public HeartRating(boolean z2) {
        this.rated = true;
        this.isHeart = z2;
    }
}
