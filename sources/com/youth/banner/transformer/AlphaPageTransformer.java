package com.youth.banner.transformer;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes8.dex */
public class AlphaPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha;

    public AlphaPageTransformer() {
        this.mMinAlpha = 0.5f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(@NonNull View view, float f2) {
        view.setScaleX(0.999f);
        if (f2 < -1.0f) {
            view.setAlpha(this.mMinAlpha);
            return;
        }
        if (f2 > 1.0f) {
            view.setAlpha(this.mMinAlpha);
        } else if (f2 < 0.0f) {
            float f3 = this.mMinAlpha;
            view.setAlpha(f3 + ((1.0f - f3) * (f2 + 1.0f)));
        } else {
            float f4 = this.mMinAlpha;
            view.setAlpha(f4 + ((1.0f - f4) * (1.0f - f2)));
        }
    }

    public AlphaPageTransformer(float f2) {
        this.mMinAlpha = f2;
    }
}
