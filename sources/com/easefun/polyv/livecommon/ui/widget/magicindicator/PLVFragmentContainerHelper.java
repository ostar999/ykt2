package com.easefun.polyv.livecommon.ui.widget.magicindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(11)
/* loaded from: classes3.dex */
public class PLVFragmentContainerHelper {
    private int mLastSelectedIndex;
    private ValueAnimator mScrollAnimator;
    private List<PLVMagicIndicator> mMagicIndicators = new ArrayList();
    private int mDuration = 150;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            PLVFragmentContainerHelper.this.dispatchPageScrollStateChanged(0);
            PLVFragmentContainerHelper.this.mScrollAnimator = null;
        }
    };
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper.2
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            float fFloatValue = ((Float) animation.getAnimatedValue()).floatValue();
            int i2 = (int) fFloatValue;
            float f2 = fFloatValue - i2;
            if (fFloatValue < 0.0f) {
                i2--;
                f2 += 1.0f;
            }
            PLVFragmentContainerHelper.this.dispatchPageScrolled(i2, f2, 0);
        }
    };

    public PLVFragmentContainerHelper() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPageScrollStateChanged(int state) {
        Iterator<PLVMagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageScrollStateChanged(state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Iterator<PLVMagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    private void dispatchPageSelected(int pageIndex) {
        Iterator<PLVMagicIndicator> it = this.mMagicIndicators.iterator();
        while (it.hasNext()) {
            it.next().onPageSelected(pageIndex);
        }
    }

    public static PLVPositionData getImitativePositionData(List<PLVPositionData> positionDataList, int index) {
        PLVPositionData pLVPositionData;
        if (index >= 0 && index <= positionDataList.size() - 1) {
            return positionDataList.get(index);
        }
        PLVPositionData pLVPositionData2 = new PLVPositionData();
        if (index < 0) {
            pLVPositionData = positionDataList.get(0);
        } else {
            index = (index - positionDataList.size()) + 1;
            pLVPositionData = positionDataList.get(positionDataList.size() - 1);
        }
        pLVPositionData2.setLeft(pLVPositionData.getLeft() + (pLVPositionData.width() * index));
        pLVPositionData2.setTop(pLVPositionData.getTop());
        pLVPositionData2.setRight(pLVPositionData.getRight() + (pLVPositionData.width() * index));
        pLVPositionData2.setBottom(pLVPositionData.getBottom());
        pLVPositionData2.setContentLeft(pLVPositionData.getContentLeft() + (pLVPositionData.width() * index));
        pLVPositionData2.setContentTop(pLVPositionData.getContentTop());
        pLVPositionData2.setContentRight(pLVPositionData.getContentRight() + (index * pLVPositionData.width()));
        pLVPositionData2.setContentBottom(pLVPositionData.getContentBottom());
        return pLVPositionData2;
    }

    public void attachMagicIndicator(PLVMagicIndicator magicIndicator) {
        this.mMagicIndicators.add(magicIndicator);
    }

    public void handlePageSelected(int selectedIndex) {
        handlePageSelected(selectedIndex, true);
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        } else {
            this.mInterpolator = interpolator;
        }
    }

    public void handlePageSelected(int selectedIndex, boolean smooth) {
        if (this.mLastSelectedIndex == selectedIndex) {
            return;
        }
        if (smooth) {
            ValueAnimator valueAnimator = this.mScrollAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                dispatchPageScrollStateChanged(2);
            }
            dispatchPageSelected(selectedIndex);
            float fFloatValue = this.mLastSelectedIndex;
            ValueAnimator valueAnimator2 = this.mScrollAnimator;
            if (valueAnimator2 != null) {
                fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                this.mScrollAnimator.cancel();
                this.mScrollAnimator = null;
            }
            ValueAnimator valueAnimator3 = new ValueAnimator();
            this.mScrollAnimator = valueAnimator3;
            valueAnimator3.setFloatValues(fFloatValue, selectedIndex);
            this.mScrollAnimator.addUpdateListener(this.mAnimatorUpdateListener);
            this.mScrollAnimator.addListener(this.mAnimatorListener);
            this.mScrollAnimator.setInterpolator(this.mInterpolator);
            this.mScrollAnimator.setDuration(this.mDuration);
            this.mScrollAnimator.start();
        } else {
            dispatchPageSelected(selectedIndex);
            ValueAnimator valueAnimator4 = this.mScrollAnimator;
            if (valueAnimator4 != null && valueAnimator4.isRunning()) {
                dispatchPageScrolled(this.mLastSelectedIndex, 0.0f, 0);
            }
            dispatchPageScrollStateChanged(0);
            dispatchPageScrolled(selectedIndex, 0.0f, 0);
        }
        this.mLastSelectedIndex = selectedIndex;
    }

    public PLVFragmentContainerHelper(PLVMagicIndicator magicIndicator) {
        this.mMagicIndicators.add(magicIndicator);
    }
}
