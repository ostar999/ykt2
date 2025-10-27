package com.youth.banner.transformer;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes8.dex */
public class ScaleInTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale;

    public ScaleInTransformer() {
        this.mMinScale = 0.85f;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(@NonNull View view, float f2) {
        int width = view.getWidth();
        view.setPivotY(view.getHeight() / 2);
        view.setPivotX(width / 2);
        if (f2 < -1.0f) {
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
            view.setPivotX(width);
            return;
        }
        if (f2 > 1.0f) {
            view.setPivotX(0.0f);
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
        } else {
            if (f2 < 0.0f) {
                float f3 = this.mMinScale;
                float f4 = ((f2 + 1.0f) * (1.0f - f3)) + f3;
                view.setScaleX(f4);
                view.setScaleY(f4);
                view.setPivotX(width * (((-f2) * 0.5f) + 0.5f));
                return;
            }
            float f5 = 1.0f - f2;
            float f6 = this.mMinScale;
            float f7 = ((1.0f - f6) * f5) + f6;
            view.setScaleX(f7);
            view.setScaleY(f7);
            view.setPivotX(width * f5 * 0.5f);
        }
    }

    public ScaleInTransformer(float f2) {
        this.mMinScale = f2;
    }
}
