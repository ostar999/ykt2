package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.animation.AnimationUtils;

/* loaded from: classes3.dex */
class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    private static float accInterp(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (float) (1.0d - Math.cos((f2 * 3.141592653589793d) / 2.0d));
    }

    private static float decInterp(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (float) Math.sin((f2 * 3.141592653589793d) / 2.0d);
    }

    @Override // com.google.android.material.tabs.TabIndicatorInterpolator
    public void setIndicatorBoundsForOffset(TabLayout tabLayout, View view, View view2, float f2, @NonNull Drawable drawable) {
        float fDecInterp;
        float fAccInterp;
        RectF rectFCalculateIndicatorWidthForTab = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view);
        RectF rectFCalculateIndicatorWidthForTab2 = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, view2);
        if (rectFCalculateIndicatorWidthForTab.left < rectFCalculateIndicatorWidthForTab2.left) {
            fDecInterp = accInterp(f2);
            fAccInterp = decInterp(f2);
        } else {
            fDecInterp = decInterp(f2);
            fAccInterp = accInterp(f2);
        }
        drawable.setBounds(AnimationUtils.lerp((int) rectFCalculateIndicatorWidthForTab.left, (int) rectFCalculateIndicatorWidthForTab2.left, fDecInterp), drawable.getBounds().top, AnimationUtils.lerp((int) rectFCalculateIndicatorWidthForTab.right, (int) rectFCalculateIndicatorWidthForTab2.right, fAccInterp), drawable.getBounds().bottom);
    }
}
