package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;

/* loaded from: classes3.dex */
public abstract class Rating implements Bundleable {
    public static final Bundleable.Creator<Rating> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.z1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return Rating.fromBundle(bundle);
        }
    };
    static final int FIELD_RATING_TYPE = 0;
    static final int RATING_TYPE_DEFAULT = -1;
    static final int RATING_TYPE_HEART = 0;
    static final int RATING_TYPE_PERCENTAGE = 1;
    static final int RATING_TYPE_STAR = 2;
    static final int RATING_TYPE_THUMB = 3;
    public static final float RATING_UNSET = -1.0f;

    /* JADX INFO: Access modifiers changed from: private */
    public static Rating fromBundle(Bundle bundle) {
        int i2 = bundle.getInt(keyForField(0), -1);
        if (i2 == 0) {
            return (Rating) HeartRating.CREATOR.fromBundle(bundle);
        }
        if (i2 == 1) {
            return (Rating) PercentageRating.CREATOR.fromBundle(bundle);
        }
        if (i2 == 2) {
            return (Rating) StarRating.CREATOR.fromBundle(bundle);
        }
        if (i2 == 3) {
            return (Rating) ThumbRating.CREATOR.fromBundle(bundle);
        }
        StringBuilder sb = new StringBuilder(44);
        sb.append("Encountered unknown rating type: ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public abstract boolean isRated();
}
