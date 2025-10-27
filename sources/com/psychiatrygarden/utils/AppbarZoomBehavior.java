package com.psychiatrygarden.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.appbar.AppBarLayout;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class AppbarZoomBehavior extends AppBarLayout.Behavior {
    private static final float MAX_ZOOM_HEIGHT = 500.0f;
    private boolean isAnimate;
    private boolean isExpand;
    private int mAppbarHeight;
    private ImageView mImageView;
    private int mImageViewHeight;
    private int mLastBottom;
    private float mScaleValue;
    private float mTotalDy;
    ValueAnimator valueAnimator;

    public AppbarZoomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isExpand = false;
    }

    private void init(AppBarLayout abl) {
        abl.setClipChildren(false);
        this.mAppbarHeight = abl.getHeight();
        ImageView imageView = (ImageView) abl.findViewById(R.id.headerimg);
        this.mImageView = imageView;
        if (imageView != null) {
            this.mImageViewHeight = imageView.getHeight();
        }
    }

    private void recovery(final AppBarLayout abl) {
        if (this.mTotalDy > 0.0f) {
            this.mTotalDy = 0.0f;
            if (this.isAnimate) {
                ValueAnimator duration = ValueAnimator.ofFloat(this.mScaleValue, 1.0f).setDuration(150L);
                this.valueAnimator = duration;
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.utils.AppbarZoomBehavior.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fFloatValue = ((Float) animation.getAnimatedValue()).floatValue();
                        ViewCompat.setScaleX(AppbarZoomBehavior.this.mImageView, fFloatValue);
                        ViewCompat.setScaleY(AppbarZoomBehavior.this.mImageView, fFloatValue);
                        abl.setBottom((int) (AppbarZoomBehavior.this.mLastBottom - ((AppbarZoomBehavior.this.mLastBottom - AppbarZoomBehavior.this.mAppbarHeight) * animation.getAnimatedFraction())));
                        if (AppbarZoomBehavior.this.isExpand) {
                            EventBus.getDefault().post(EventBusConstant.PULL_DOWN_LOAD_REFRESH);
                        }
                        AppbarZoomBehavior.this.isExpand = false;
                    }
                });
                this.valueAnimator.start();
                return;
            }
            ViewCompat.setScaleX(this.mImageView, 1.0f);
            ViewCompat.setScaleY(this.mImageView, 1.0f);
            abl.setBottom(this.mAppbarHeight);
            if (this.isExpand) {
                EventBus.getDefault().post(EventBusConstant.PULL_DOWN_LOAD_REFRESH);
            }
            this.isExpand = false;
        }
    }

    private void zoomHeaderImageView(AppBarLayout abl, int dy) {
        float f2 = this.mTotalDy + (-dy);
        this.mTotalDy = f2;
        if (f2 >= 400.0f && !this.isExpand) {
            this.isExpand = true;
        }
        float fMin = Math.min(f2, MAX_ZOOM_HEIGHT);
        this.mTotalDy = fMin;
        float fMax = Math.max(1.0f, (fMin / MAX_ZOOM_HEIGHT) + 1.0f);
        this.mScaleValue = fMax;
        ViewCompat.setScaleX(this.mImageView, fMax);
        ViewCompat.setScaleY(this.mImageView, this.mScaleValue);
        int i2 = this.mAppbarHeight + ((int) ((this.mImageViewHeight / 2) * (this.mScaleValue - 1.0f)));
        this.mLastBottom = i2;
        abl.setBottom(i2);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.HeaderBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public /* bridge */ /* synthetic */ boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout abl, int layoutDirection) {
        boolean zOnLayoutChild = super.onLayoutChild(parent, abl, layoutDirection);
        init(abl);
        return zOnLayoutChild;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100.0f) {
            this.isAnimate = false;
        }
        return super.onNestedPreFling(coordinatorLayout, (CoordinatorLayout) child, target, velocityX, velocityY);
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (this.mImageView != null && child.getBottom() >= this.mAppbarHeight && dy < 0 && type == 0) {
            zoomHeaderImageView(child, dy);
            return;
        }
        if (this.mImageView != null && child.getBottom() > this.mAppbarHeight && dy > 0 && type == 0) {
            consumed[1] = dy;
            zoomHeaderImageView(child, dy);
            return;
        }
        ValueAnimator valueAnimator = this.valueAnimator;
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull View directTargetChild, View target, int nestedScrollAxes, int type) {
        this.isAnimate = true;
        return true;
    }

    @Override // com.google.android.material.appbar.AppBarLayout.BaseBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
    }
}
