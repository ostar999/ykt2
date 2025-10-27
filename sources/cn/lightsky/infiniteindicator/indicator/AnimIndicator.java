package cn.lightsky.infiniteindicator.indicator;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.R;
import cn.lightsky.infiniteindicator.recycle.RecyleAdapter;

/* loaded from: classes.dex */
public class AnimIndicator extends LinearLayout implements PageIndicator {
    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private AnimatorSet mAnimationIn;
    private AnimatorSet mAnimationOut;
    private int mAnimatorResId;
    private int mCurrentPage;
    private int mIndicatorBackground;
    private int mIndicatorHeight;
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private RecyleAdapter mRecyleAdapter;
    private int theme;

    public class ReverseInterpolator implements Interpolator {
        private ReverseInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            return Math.abs(1.0f - f2);
        }
    }

    @RequiresApi(api = 11)
    public AnimIndicator(Context context) {
        super(context);
        this.mCurrentPage = 0;
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mIndicatorBackground = R.drawable.white_radius;
        this.theme = 0;
        init(context, null);
    }

    private void handleTypedArray(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AnimIndicator);
            this.mIndicatorWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.AnimIndicator_ci_width, -1);
            this.mIndicatorHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.AnimIndicator_ci_height, -1);
            this.mIndicatorMargin = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.AnimIndicator_ci_margin, -1);
            this.mAnimatorResId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AnimIndicator_ci_animator, R.animator.scale_with_alpha);
            this.mIndicatorBackground = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AnimIndicator_ci_drawable, R.drawable.white_radius);
            typedArrayObtainStyledAttributes.recycle();
        }
        int iDip2px = this.mIndicatorWidth;
        if (iDip2px == -1) {
            iDip2px = dip2px(5.0f);
        }
        this.mIndicatorWidth = iDip2px;
        int iDip2px2 = this.mIndicatorHeight;
        if (iDip2px2 == -1) {
            iDip2px2 = dip2px(5.0f);
        }
        this.mIndicatorHeight = iDip2px2;
        int iDip2px3 = this.mIndicatorMargin;
        if (iDip2px3 == -1) {
            iDip2px3 = dip2px(5.0f);
        }
        this.mIndicatorMargin = iDip2px3;
    }

    @RequiresApi(api = 11)
    private void init(Context context, AttributeSet attributeSet) {
        setOrientation(0);
        setGravity(17);
        handleTypedArray(context, attributeSet);
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
        this.mAnimationOut = animatorSet;
        animatorSet.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
        this.mAnimationIn = animatorSet2;
        animatorSet2.setInterpolator(new ReverseInterpolator());
    }

    @RequiresApi(api = 11)
    private void invalidIndicators() {
        int realCount;
        removeAllViews();
        RecyleAdapter recyleAdapter = this.mRecyleAdapter;
        if (recyleAdapter != null && (realCount = recyleAdapter.getRealCount()) >= 2) {
            for (int i2 = 0; i2 < realCount; i2++) {
                View view = new View(getContext());
                if (this.theme == 0) {
                    view.setBackgroundResource(this.mIndicatorBackground);
                } else {
                    int i3 = R.drawable.shape_point_selected_night;
                    this.mIndicatorBackground = i3;
                    view.setBackgroundResource(i3);
                }
                addView(view, this.mIndicatorWidth, this.mIndicatorHeight);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                int i4 = this.mIndicatorMargin;
                layoutParams.leftMargin = i4;
                layoutParams.rightMargin = i4;
                view.setLayoutParams(layoutParams);
                this.mAnimationOut.setTarget(view);
                this.mAnimationOut.start();
            }
            this.mAnimationOut.setTarget(getChildAt(this.mCurrentPage));
            this.mAnimationOut.start();
        }
    }

    public int dip2px(float f2) {
        return (int) ((f2 * getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    @RequiresApi(api = 11)
    public void notifyDataSetChanged() {
        this.mCurrentPage = 0;
        invalidIndicators();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i2, float f2, int i3) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    @RequiresApi(api = 11)
    public void onPageSelected(int i2) {
        if (getChildAt(this.mCurrentPage) == null) {
            return;
        }
        this.mAnimationIn.setTarget(getChildAt(this.mCurrentPage));
        this.mAnimationIn.start();
        this.mAnimationOut.setTarget(getChildAt(i2));
        this.mAnimationOut.start();
        this.mCurrentPage = i2;
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    @RequiresApi(api = 11)
    public void setCurrentItem(int i2) {
        this.mCurrentPage = i2;
        invalidIndicators();
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    @RequiresApi(api = 11)
    public void setViewPager(ViewPager viewPager, int i2) {
        this.mRecyleAdapter = (RecyleAdapter) viewPager.getAdapter();
        this.theme = i2;
        invalidIndicators();
    }

    @RequiresApi(api = 11)
    public AnimIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentPage = 0;
        this.mAnimatorResId = R.animator.scale_with_alpha;
        this.mIndicatorBackground = R.drawable.white_radius;
        this.theme = 0;
        init(context, attributeSet);
    }
}
