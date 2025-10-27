package com.psychiatrygarden.widget;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/* loaded from: classes6.dex */
public class BottomSheetBehaviorFix<V extends View> extends BottomSheetBehavior<V> implements ViewPager.OnPageChangeListener {
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int position) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }
}
