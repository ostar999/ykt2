package com.easefun.polyv.livecommon.ui.widget.magicindicator;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

/* loaded from: classes3.dex */
public class PLVNavigatorHelper {
    private int mCurrentIndex;
    private int mLastIndex;
    private float mLastPositionOffsetSum;
    private OnNavigatorScrollListener mNavigatorScrollListener;
    private int mScrollState;
    private boolean mSkimOver;
    private int mTotalCount;
    private SparseBooleanArray mDeselectedItems = new SparseBooleanArray();
    private SparseArray<Float> mLeavedPercents = new SparseArray<>();

    public interface OnNavigatorScrollListener {
        void onDeselected(int index, int totalCount);

        void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);

        void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

        void onSelected(int index, int totalCount);
    }

    private void dispatchOnDeselected(int index) {
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onDeselected(index, this.mTotalCount);
        }
        this.mDeselectedItems.put(index, true);
    }

    private void dispatchOnEnter(int index, float enterPercent, boolean leftToRight, boolean force) {
        if (this.mSkimOver || index == this.mCurrentIndex || this.mScrollState == 1 || force) {
            OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
            if (onNavigatorScrollListener != null) {
                onNavigatorScrollListener.onEnter(index, this.mTotalCount, enterPercent, leftToRight);
            }
            this.mLeavedPercents.put(index, Float.valueOf(1.0f - enterPercent));
        }
    }

    private void dispatchOnLeave(int index, float leavePercent, boolean leftToRight, boolean force) {
        if (!this.mSkimOver && index != this.mLastIndex && this.mScrollState != 1) {
            int i2 = this.mCurrentIndex;
            if (((index != i2 - 1 && index != i2 + 1) || this.mLeavedPercents.get(index, Float.valueOf(0.0f)).floatValue() == 1.0f) && !force) {
                return;
            }
        }
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onLeave(index, this.mTotalCount, leavePercent, leftToRight);
        }
        this.mLeavedPercents.put(index, Float.valueOf(leavePercent));
    }

    private void dispatchOnSelected(int index) {
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onSelected(index, this.mTotalCount);
        }
        this.mDeselectedItems.put(index, false);
    }

    public int getCurrentIndex() {
        return this.mCurrentIndex;
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    public int getTotalCount() {
        return this.mTotalCount;
    }

    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        boolean z2;
        float f2 = position + positionOffset;
        float f3 = this.mLastPositionOffsetSum;
        boolean z3 = f3 <= f2;
        if (this.mScrollState == 0) {
            for (int i2 = 0; i2 < this.mTotalCount; i2++) {
                if (i2 != this.mCurrentIndex) {
                    if (!this.mDeselectedItems.get(i2)) {
                        dispatchOnDeselected(i2);
                    }
                    if (this.mLeavedPercents.get(i2, Float.valueOf(0.0f)).floatValue() != 1.0f) {
                        dispatchOnLeave(i2, 1.0f, false, true);
                    }
                }
            }
            dispatchOnEnter(this.mCurrentIndex, 1.0f, false, true);
            dispatchOnSelected(this.mCurrentIndex);
        } else {
            if (f2 == f3) {
                return;
            }
            int i3 = position + 1;
            if (positionOffset == 0.0f && z3) {
                i3 = position - 1;
                z2 = false;
            } else {
                z2 = true;
            }
            for (int i4 = 0; i4 < this.mTotalCount; i4++) {
                if (i4 != position && i4 != i3 && this.mLeavedPercents.get(i4, Float.valueOf(0.0f)).floatValue() != 1.0f) {
                    dispatchOnLeave(i4, 1.0f, z3, true);
                }
            }
            if (!z2) {
                float f4 = 1.0f - positionOffset;
                dispatchOnLeave(i3, f4, true, false);
                dispatchOnEnter(position, f4, true, false);
            } else if (z3) {
                dispatchOnLeave(position, positionOffset, true, false);
                dispatchOnEnter(i3, positionOffset, true, false);
            } else {
                float f5 = 1.0f - positionOffset;
                dispatchOnLeave(i3, f5, false, false);
                dispatchOnEnter(position, f5, false, false);
            }
        }
        this.mLastPositionOffsetSum = f2;
    }

    public void onPageSelected(int position) {
        this.mLastIndex = this.mCurrentIndex;
        this.mCurrentIndex = position;
        dispatchOnSelected(position);
        for (int i2 = 0; i2 < this.mTotalCount; i2++) {
            if (i2 != this.mCurrentIndex && !this.mDeselectedItems.get(i2)) {
                dispatchOnDeselected(i2);
            }
        }
    }

    public void setNavigatorScrollListener(OnNavigatorScrollListener navigatorScrollListener) {
        this.mNavigatorScrollListener = navigatorScrollListener;
    }

    public void setSkimOver(boolean skimOver) {
        this.mSkimOver = skimOver;
    }

    public void setTotalCount(int totalCount) {
        this.mTotalCount = totalCount;
        this.mDeselectedItems.clear();
        this.mLeavedPercents.clear();
    }
}
