package com.zhpan.bannerview.transform;

import android.view.View;
import androidx.viewpager2.widget.ViewPager2;

/* loaded from: classes8.dex */
public class ScaleInTransformer implements ViewPager2.PageTransformer {
    private static final float DEFAULT_CENTER = 0.5f;
    public static final float DEFAULT_MIN_SCALE = 0.85f;
    private final float mMinScale;

    public ScaleInTransformer(float f2) {
        this.mMinScale = f2;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View view, float f2) {
        int width = view.getWidth();
        view.setPivotY(view.getHeight() / 2.0f);
        float f3 = width;
        view.setPivotX(f3 / 2.0f);
        if (f2 < -1.0f) {
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
            view.setPivotX(f3);
            return;
        }
        if (f2 > 1.0f) {
            view.setPivotX(0.0f);
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
        } else {
            if (f2 < 0.0f) {
                float f4 = this.mMinScale;
                float f5 = ((f2 + 1.0f) * (1.0f - f4)) + f4;
                view.setScaleX(f5);
                view.setScaleY(f5);
                view.setPivotX(f3 * (((-f2) * 0.5f) + 0.5f));
                return;
            }
            float f6 = 1.0f - f2;
            float f7 = this.mMinScale;
            float f8 = ((1.0f - f7) * f6) + f7;
            view.setScaleX(f8);
            view.setScaleY(f8);
            view.setPivotX(f3 * f6 * 0.5f);
        }
    }
}
