package com.youth.banner.transformer;

import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes8.dex */
public class RotateDownPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate;

    public RotateDownPageTransformer() {
        this.mMaxRotate = DEFAULT_MAX_ROTATE;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(@NonNull View view, float f2) {
        if (f2 < -1.0f) {
            view.setRotation(this.mMaxRotate * (-1.0f));
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight());
        } else if (f2 > 1.0f) {
            view.setRotation(this.mMaxRotate);
            view.setPivotX(view.getWidth() * 0);
            view.setPivotY(view.getHeight());
        } else if (f2 < 0.0f) {
            view.setPivotX(view.getWidth() * (((-f2) * 0.5f) + 0.5f));
            view.setPivotY(view.getHeight());
            view.setRotation(this.mMaxRotate * f2);
        } else {
            view.setPivotX(view.getWidth() * 0.5f * (1.0f - f2));
            view.setPivotY(view.getHeight());
            view.setRotation(this.mMaxRotate * f2);
        }
    }

    public RotateDownPageTransformer(float f2) {
        this.mMaxRotate = f2;
    }
}
