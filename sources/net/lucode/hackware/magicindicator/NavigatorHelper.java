package net.lucode.hackware.magicindicator;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

/* loaded from: classes9.dex */
public class NavigatorHelper {
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
        void onDeselected(int i2, int i3);

        void onEnter(int i2, int i3, float f2, boolean z2);

        void onLeave(int i2, int i3, float f2, boolean z2);

        void onSelected(int i2, int i3);
    }

    private void dispatchOnDeselected(int i2) {
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onDeselected(i2, this.mTotalCount);
        }
        this.mDeselectedItems.put(i2, true);
    }

    private void dispatchOnEnter(int i2, float f2, boolean z2, boolean z3) {
        if (this.mSkimOver || i2 == this.mCurrentIndex || this.mScrollState == 1 || z3) {
            OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
            if (onNavigatorScrollListener != null) {
                onNavigatorScrollListener.onEnter(i2, this.mTotalCount, f2, z2);
            }
            this.mLeavedPercents.put(i2, Float.valueOf(1.0f - f2));
        }
    }

    private void dispatchOnLeave(int i2, float f2, boolean z2, boolean z3) {
        if (!this.mSkimOver && i2 != this.mLastIndex && this.mScrollState != 1) {
            int i3 = this.mCurrentIndex;
            if (((i2 != i3 - 1 && i2 != i3 + 1) || this.mLeavedPercents.get(i2, Float.valueOf(0.0f)).floatValue() == 1.0f) && !z3) {
                return;
            }
        }
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onLeave(i2, this.mTotalCount, f2, z2);
        }
        this.mLeavedPercents.put(i2, Float.valueOf(f2));
    }

    private void dispatchOnSelected(int i2) {
        OnNavigatorScrollListener onNavigatorScrollListener = this.mNavigatorScrollListener;
        if (onNavigatorScrollListener != null) {
            onNavigatorScrollListener.onSelected(i2, this.mTotalCount);
        }
        this.mDeselectedItems.put(i2, false);
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

    public void onPageScrollStateChanged(int i2) {
        this.mScrollState = i2;
    }

    public void onPageScrolled(int i2, float f2, int i3) {
        boolean z2;
        float f3 = i2 + f2;
        float f4 = this.mLastPositionOffsetSum;
        boolean z3 = f4 <= f3;
        if (this.mScrollState == 0) {
            for (int i4 = 0; i4 < this.mTotalCount; i4++) {
                if (i4 != this.mCurrentIndex) {
                    if (!this.mDeselectedItems.get(i4)) {
                        dispatchOnDeselected(i4);
                    }
                    if (this.mLeavedPercents.get(i4, Float.valueOf(0.0f)).floatValue() != 1.0f) {
                        dispatchOnLeave(i4, 1.0f, false, true);
                    }
                }
            }
            dispatchOnEnter(this.mCurrentIndex, 1.0f, false, true);
            dispatchOnSelected(this.mCurrentIndex);
        } else {
            if (f3 == f4) {
                return;
            }
            int i5 = i2 + 1;
            if (f2 == 0.0f && z3) {
                i5 = i2 - 1;
                z2 = false;
            } else {
                z2 = true;
            }
            for (int i6 = 0; i6 < this.mTotalCount; i6++) {
                if (i6 != i2 && i6 != i5 && this.mLeavedPercents.get(i6, Float.valueOf(0.0f)).floatValue() != 1.0f) {
                    dispatchOnLeave(i6, 1.0f, z3, true);
                }
            }
            if (!z2) {
                float f5 = 1.0f - f2;
                dispatchOnLeave(i5, f5, true, false);
                dispatchOnEnter(i2, f5, true, false);
            } else if (z3) {
                dispatchOnLeave(i2, f2, true, false);
                dispatchOnEnter(i5, f2, true, false);
            } else {
                float f6 = 1.0f - f2;
                dispatchOnLeave(i5, f6, false, false);
                dispatchOnEnter(i2, f6, false, false);
            }
        }
        this.mLastPositionOffsetSum = f3;
    }

    public void onPageSelected(int i2) {
        this.mLastIndex = this.mCurrentIndex;
        this.mCurrentIndex = i2;
        dispatchOnSelected(i2);
        for (int i3 = 0; i3 < this.mTotalCount; i3++) {
            if (i3 != this.mCurrentIndex && !this.mDeselectedItems.get(i3)) {
                dispatchOnDeselected(i3);
            }
        }
    }

    public void setNavigatorScrollListener(OnNavigatorScrollListener onNavigatorScrollListener) {
        this.mNavigatorScrollListener = onNavigatorScrollListener;
    }

    public void setSkimOver(boolean z2) {
        this.mSkimOver = z2;
    }

    public void setTotalCount(int i2) {
        this.mTotalCount = i2;
        this.mDeselectedItems.clear();
        this.mLeavedPercents.clear();
    }
}
