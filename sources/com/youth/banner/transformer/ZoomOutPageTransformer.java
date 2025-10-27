package com.youth.banner.transformer;

import android.view.View;

/* loaded from: classes8.dex */
public class ZoomOutPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinAlpha;
    private float mMinScale;

    public ZoomOutPageTransformer() {
        this.mMinScale = 0.85f;
        this.mMinAlpha = 0.5f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View view, float f2) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (f2 < -1.0f) {
            view.setAlpha(0.0f);
            return;
        }
        if (f2 > 1.0f) {
            view.setAlpha(0.0f);
            return;
        }
        float fMax = Math.max(this.mMinScale, 1.0f - Math.abs(f2));
        float f3 = 1.0f - fMax;
        float f4 = (height * f3) / 2.0f;
        float f5 = (width * f3) / 2.0f;
        if (f2 < 0.0f) {
            view.setTranslationX(f5 - (f4 / 2.0f));
        } else {
            view.setTranslationX((-f5) + (f4 / 2.0f));
        }
        view.setScaleX(fMax);
        view.setScaleY(fMax);
        float f6 = this.mMinAlpha;
        float f7 = this.mMinScale;
        view.setAlpha(f6 + (((fMax - f7) / (1.0f - f7)) * (1.0f - f6)));
    }

    public ZoomOutPageTransformer(float f2, float f3) {
        this.mMinScale = f2;
        this.mMinAlpha = f3;
    }
}
