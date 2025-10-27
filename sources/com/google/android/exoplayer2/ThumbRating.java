package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

/* loaded from: classes3.dex */
public final class ThumbRating extends Rating {
    public static final Bundleable.Creator<ThumbRating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.e2
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return ThumbRating.fromBundle(bundle);
        }
    };
    private static final int FIELD_IS_THUMBS_UP = 2;
    private static final int FIELD_RATED = 1;
    private static final int TYPE = 3;
    private final boolean isThumbsUp;
    private final boolean rated;

    public ThumbRating() {
        this.rated = false;
        this.isThumbsUp = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ThumbRating fromBundle(Bundle bundle) {
        Assertions.checkArgument(bundle.getInt(keyForField(0), -1) == 3);
        return bundle.getBoolean(keyForField(1), false) ? new ThumbRating(bundle.getBoolean(keyForField(2), false)) : new ThumbRating();
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ThumbRating)) {
            return false;
        }
        ThumbRating thumbRating = (ThumbRating) obj;
        return this.isThumbsUp == thumbRating.isThumbsUp && this.rated == thumbRating.rated;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.rated), Boolean.valueOf(this.isThumbsUp));
    }

    @Override // com.google.android.exoplayer2.Rating
    public boolean isRated() {
        return this.rated;
    }

    public boolean isThumbsUp() {
        return this.isThumbsUp;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(keyForField(0), 3);
        bundle.putBoolean(keyForField(1), this.rated);
        bundle.putBoolean(keyForField(2), this.isThumbsUp);
        return bundle;
    }

    public ThumbRating(boolean z2) {
        this.rated = true;
        this.isThumbsUp = z2;
    }
}
