package com.psychiatrygarden.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class ViewPagerHelper {
    private static int previousValue;

    public static void setCurrentItem(final ViewPager2 pager, int item, long duration) {
        previousValue = 0;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, -(pager.getHeight() * (item - pager.getCurrentItem())));
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.ViewPagerHelper.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                int iIntValue = ((Integer) animation.getAnimatedValue()).intValue();
                pager.fakeDragBy(-(iIntValue - ViewPagerHelper.previousValue));
                int unused = ViewPagerHelper.previousValue = iIntValue;
            }
        });
        valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.psychiatrygarden.utils.ViewPagerHelper.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                pager.endFakeDrag();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                pager.beginFakeDrag();
            }
        });
        valueAnimatorOfInt.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfInt.setDuration(duration);
        valueAnimatorOfInt.start();
    }

    public static void setRecyclerviewScrollDuration(RecyclerView recyclerView, int duration) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (recyclerView == null) {
            return;
        }
        try {
            Field declaredField = RecyclerView.class.getDeclaredField("mViewFlinger");
            declaredField.setAccessible(true);
            Field declaredField2 = Class.forName("androidx.recyclerview.widget.RecyclerView$ViewFlinger").getDeclaredField("mOverScroller");
            declaredField2.setAccessible(true);
            CustomOverScroll customOverScroll = new CustomOverScroll(recyclerView.getContext(), new DecelerateInterpolator());
            customOverScroll.setDuration(duration);
            declaredField2.set(declaredField.get(recyclerView), customOverScroll);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public static void setViewPage2ScrollDuration(ViewPager2 viewPager2, final int duration) throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = ViewPager2.class.getDeclaredField("mRecyclerView");
            declaredField.setAccessible(true);
            final RecyclerView recyclerView = (RecyclerView) declaredField.get(viewPager2);
            if (recyclerView != null) {
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.utils.ViewPagerHelper.3
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                        super.onScrollStateChanged(recyclerView2, newState);
                        if (newState == 2) {
                            ViewPagerHelper.setRecyclerviewScrollDuration(recyclerView, duration);
                        }
                    }
                });
            }
        } catch (IllegalAccessException | NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public static class CustomOverScroll extends OverScroller {
        private int mDuration;

        public CustomOverScroll(Context context) {
            super(context);
            this.mDuration = -1;
        }

        public void setDuration(int mDuration) {
            this.mDuration = mDuration;
        }

        @Override // android.widget.OverScroller
        public void startScroll(int startX, int startY, int dx, int dy) {
            int i2 = this.mDuration;
            if (i2 > 0) {
                super.startScroll(startX, startY, dx, dy, i2);
            } else {
                super.startScroll(startX, startY, dx, dy);
            }
        }

        public CustomOverScroll(Context context, Interpolator interpolator) {
            super(context, interpolator);
            this.mDuration = -1;
        }

        @Override // android.widget.OverScroller
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            int i2 = this.mDuration;
            if (i2 > 0) {
                super.startScroll(startX, startY, dx, dy, i2);
            } else {
                super.startScroll(startX, startY, dx, dy, duration);
            }
        }

        public CustomOverScroll(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY) {
            super(context, interpolator, bounceCoefficientX, bounceCoefficientY);
            this.mDuration = -1;
        }

        public CustomOverScroll(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY, boolean flywheel) {
            super(context, interpolator, bounceCoefficientX, bounceCoefficientY, flywheel);
            this.mDuration = -1;
        }
    }
}
