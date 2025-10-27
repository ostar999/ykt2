package net.lucode.hackware.magicindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

@TargetApi(11)
/* loaded from: classes9.dex */
public class FragmentContainerHelper {
    private int mLastSelectedIndex;
    private ValueAnimator mScrollAnimator;
    private List<MagicIndicator> mMagicIndicators = new ArrayList();
    private int mDuration = 150;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() { // from class: net.lucode.hackware.magicindicator.FragmentContainerHelper.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            FragmentContainerHelper.this.dispatchPageScrollStateChanged(0);
            FragmentContainerHelper.this.mScrollAnimator = null;
        }
    };
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: net.lucode.hackware.magicindicator.FragmentContainerHelper.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            int i2 = (int) fFloatValue;
            float f2 = fFloatValue - i2;
            if (fFloatValue < 0.0f) {
                i2--;
                f2 += 1.0f;
            }
            FragmentContainerHelper.this.dispatchPageScrolled(i2, f2, 0);
        }
    };

    public FragmentContainerHelper() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPageScrollStateChanged(int i2) {
        Iterator<MagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageScrollStateChanged(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPageScrolled(int i2, float f2, int i3) {
        Iterator<MagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageScrolled(i2, f2, i3);
        }
    }

    private void dispatchPageSelected(int i2) {
        Iterator<MagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageSelected(i2);
        }
    }

    public static PositionData getImitativePositionData(List<PositionData> list, int i2) {
        PositionData positionData;
        if (i2 >= 0 && i2 <= list.size() - 1) {
            return list.get(i2);
        }
        PositionData positionData2 = new PositionData();
        if (i2 < 0) {
            positionData = list.get(0);
        } else {
            i2 = (i2 - list.size()) + 1;
            positionData = list.get(list.size() - 1);
        }
        positionData2.mLeft = positionData.mLeft + (positionData.width() * i2);
        positionData2.mTop = positionData.mTop;
        positionData2.mRight = positionData.mRight + (positionData.width() * i2);
        positionData2.mBottom = positionData.mBottom;
        positionData2.mContentLeft = positionData.mContentLeft + (positionData.width() * i2);
        positionData2.mContentTop = positionData.mContentTop;
        positionData2.mContentRight = positionData.mContentRight + (i2 * positionData.width());
        positionData2.mContentBottom = positionData.mContentBottom;
        return positionData2;
    }

    public void attachMagicIndicator(MagicIndicator magicIndicator) {
        this.mMagicIndicators.add(magicIndicator);
    }

    public void handlePageSelected(int i2) {
        handlePageSelected(i2, true);
    }

    public void setDuration(int i2) {
        this.mDuration = i2;
    }

    public void setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
    }

    public void handlePageSelected(int i2, boolean z2) {
        if (this.mLastSelectedIndex == i2) {
            return;
        }
        if (z2) {
            ValueAnimator valueAnimator = this.mScrollAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                dispatchPageScrollStateChanged(2);
            }
            dispatchPageSelected(i2);
            float fFloatValue = this.mLastSelectedIndex;
            ValueAnimator valueAnimator2 = this.mScrollAnimator;
            if (valueAnimator2 != null) {
                fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                this.mScrollAnimator.cancel();
                this.mScrollAnimator = null;
            }
            ValueAnimator valueAnimator3 = new ValueAnimator();
            this.mScrollAnimator = valueAnimator3;
            valueAnimator3.setFloatValues(fFloatValue, i2);
            this.mScrollAnimator.addUpdateListener(this.mAnimatorUpdateListener);
            this.mScrollAnimator.addListener(this.mAnimatorListener);
            this.mScrollAnimator.setInterpolator(this.mInterpolator);
            this.mScrollAnimator.setDuration(this.mDuration);
            this.mScrollAnimator.start();
        } else {
            dispatchPageSelected(i2);
            ValueAnimator valueAnimator4 = this.mScrollAnimator;
            if (valueAnimator4 != null && valueAnimator4.isRunning()) {
                dispatchPageScrolled(this.mLastSelectedIndex, 0.0f, 0);
            }
            dispatchPageScrollStateChanged(0);
            dispatchPageScrolled(i2, 0.0f, 0);
        }
        this.mLastSelectedIndex = i2;
    }

    public FragmentContainerHelper(MagicIndicator magicIndicator) {
        this.mMagicIndicators.add(magicIndicator);
    }
}
