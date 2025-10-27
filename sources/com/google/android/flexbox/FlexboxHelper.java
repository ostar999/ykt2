package com.google.android.flexbox;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.widget.CompoundButtonCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
class FlexboxHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INITIAL_CAPACITY = 10;
    private static final long MEASURE_SPEC_WIDTH_MASK = 4294967295L;
    private boolean[] mChildrenFrozen;
    private final FlexContainer mFlexContainer;

    @Nullable
    int[] mIndexToFlexLine;

    @Nullable
    long[] mMeasureSpecCache;

    @Nullable
    private long[] mMeasuredSizeCache;

    public static class FlexLinesResult {
        int mChildState;
        List<FlexLine> mFlexLines;

        public void reset() {
            this.mFlexLines = null;
            this.mChildState = 0;
        }
    }

    public static class Order implements Comparable<Order> {
        int index;
        int order;

        private Order() {
        }

        @NonNull
        public String toString() {
            return "Order{order=" + this.order + ", index=" + this.index + '}';
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull Order another) {
            int i2 = this.order;
            int i3 = another.order;
            return i2 != i3 ? i2 - i3 : this.index - another.index;
        }
    }

    public FlexboxHelper(FlexContainer flexContainer) {
        this.mFlexContainer = flexContainer;
    }

    private void addFlexLine(List<FlexLine> flexLines, FlexLine flexLine, int viewIndex, int usedCrossSizeSoFar) {
        flexLine.mSumCrossSizeBefore = usedCrossSizeSoFar;
        this.mFlexContainer.onNewFlexLineAdded(flexLine);
        flexLine.mLastIndex = viewIndex;
        flexLines.add(flexLine);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkSizeConstraints(android.view.View r7, int r8) {
        /*
            r6 = this;
            android.view.ViewGroup$LayoutParams r0 = r7.getLayoutParams()
            com.google.android.flexbox.FlexItem r0 = (com.google.android.flexbox.FlexItem) r0
            int r1 = r7.getMeasuredWidth()
            int r2 = r7.getMeasuredHeight()
            int r3 = r0.getMinWidth()
            r4 = 1
            if (r1 >= r3) goto L1b
            int r1 = r0.getMinWidth()
        L19:
            r3 = r4
            goto L27
        L1b:
            int r3 = r0.getMaxWidth()
            if (r1 <= r3) goto L26
            int r1 = r0.getMaxWidth()
            goto L19
        L26:
            r3 = 0
        L27:
            int r5 = r0.getMinHeight()
            if (r2 >= r5) goto L32
            int r2 = r0.getMinHeight()
            goto L3e
        L32:
            int r5 = r0.getMaxHeight()
            if (r2 <= r5) goto L3d
            int r2 = r0.getMaxHeight()
            goto L3e
        L3d:
            r4 = r3
        L3e:
            if (r4 == 0) goto L55
            r0 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r0)
            r7.measure(r1, r0)
            r6.updateMeasureCache(r8, r1, r0, r7)
            com.google.android.flexbox.FlexContainer r0 = r6.mFlexContainer
            r0.updateViewCache(r8, r7)
        L55:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.flexbox.FlexboxHelper.checkSizeConstraints(android.view.View, int):void");
    }

    private List<FlexLine> constructFlexLinesForAlignContentCenter(List<FlexLine> flexLines, int size, int totalCrossSize) {
        int i2 = (size - totalCrossSize) / 2;
        ArrayList arrayList = new ArrayList();
        FlexLine flexLine = new FlexLine();
        flexLine.mCrossSize = i2;
        int size2 = flexLines.size();
        for (int i3 = 0; i3 < size2; i3++) {
            if (i3 == 0) {
                arrayList.add(flexLine);
            }
            arrayList.add(flexLines.get(i3));
            if (i3 == flexLines.size() - 1) {
                arrayList.add(flexLine);
            }
        }
        return arrayList;
    }

    @NonNull
    private List<Order> createOrders(int childCount) {
        ArrayList arrayList = new ArrayList(childCount);
        for (int i2 = 0; i2 < childCount; i2++) {
            FlexItem flexItem = (FlexItem) this.mFlexContainer.getFlexItemAt(i2).getLayoutParams();
            Order order = new Order();
            order.order = flexItem.getOrder();
            order.index = i2;
            arrayList.add(order);
        }
        return arrayList;
    }

    private void ensureChildrenFrozen(int size) {
        boolean[] zArr = this.mChildrenFrozen;
        if (zArr == null) {
            this.mChildrenFrozen = new boolean[Math.max(size, 10)];
        } else if (zArr.length < size) {
            this.mChildrenFrozen = new boolean[Math.max(zArr.length * 2, size)];
        } else {
            Arrays.fill(zArr, false);
        }
    }

    private void evaluateMinimumSizeForCompoundButton(CompoundButton compoundButton) {
        FlexItem flexItem = (FlexItem) compoundButton.getLayoutParams();
        int minWidth = flexItem.getMinWidth();
        int minHeight = flexItem.getMinHeight();
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(compoundButton);
        int minimumWidth = buttonDrawable == null ? 0 : buttonDrawable.getMinimumWidth();
        int minimumHeight = buttonDrawable != null ? buttonDrawable.getMinimumHeight() : 0;
        if (minWidth == -1) {
            minWidth = minimumWidth;
        }
        flexItem.setMinWidth(minWidth);
        if (minHeight == -1) {
            minHeight = minimumHeight;
        }
        flexItem.setMinHeight(minHeight);
    }

    private void expandFlexItems(int widthMeasureSpec, int heightMeasureSpec, FlexLine flexLine, int maxMainSize, int paddingAlongMainAxis, boolean calledRecursively) {
        int i2;
        int i3;
        int iMax;
        double d3;
        int i4;
        double d4;
        float f2 = flexLine.mTotalFlexGrow;
        float f3 = 0.0f;
        if (f2 <= 0.0f || maxMainSize < (i2 = flexLine.mMainSize)) {
            return;
        }
        float f4 = (maxMainSize - i2) / f2;
        flexLine.mMainSize = paddingAlongMainAxis + flexLine.mDividerLengthInMainSize;
        if (!calledRecursively) {
            flexLine.mCrossSize = Integer.MIN_VALUE;
        }
        int i5 = 0;
        boolean z2 = false;
        int i6 = 0;
        float f5 = 0.0f;
        while (i5 < flexLine.mItemCount) {
            int i7 = flexLine.mFirstIndex + i5;
            View reorderedFlexItemAt = this.mFlexContainer.getReorderedFlexItemAt(i7);
            if (reorderedFlexItemAt == null || reorderedFlexItemAt.getVisibility() == 8) {
                i3 = i2;
            } else {
                FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                int flexDirection = this.mFlexContainer.getFlexDirection();
                if (flexDirection == 0 || flexDirection == 1) {
                    int i8 = i2;
                    int measuredWidth = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr = this.mMeasuredSizeCache;
                    if (jArr != null) {
                        measuredWidth = extractLowerInt(jArr[i7]);
                    }
                    int measuredHeight = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr2 = this.mMeasuredSizeCache;
                    i3 = i8;
                    if (jArr2 != null) {
                        measuredHeight = extractHigherInt(jArr2[i7]);
                    }
                    if (!this.mChildrenFrozen[i7] && flexItem.getFlexGrow() > 0.0f) {
                        float flexGrow = measuredWidth + (flexItem.getFlexGrow() * f4);
                        if (i5 == flexLine.mItemCount - 1) {
                            flexGrow += f5;
                            f5 = 0.0f;
                        }
                        int iRound = Math.round(flexGrow);
                        if (iRound > flexItem.getMaxWidth()) {
                            iRound = flexItem.getMaxWidth();
                            this.mChildrenFrozen[i7] = true;
                            flexLine.mTotalFlexGrow -= flexItem.getFlexGrow();
                            z2 = true;
                        } else {
                            f5 += flexGrow - iRound;
                            double d5 = f5;
                            if (d5 > 1.0d) {
                                iRound++;
                                d3 = d5 - 1.0d;
                            } else if (d5 < -1.0d) {
                                iRound--;
                                d3 = d5 + 1.0d;
                            }
                            f5 = (float) d3;
                        }
                        int childHeightMeasureSpecInternal = getChildHeightMeasureSpecInternal(heightMeasureSpec, flexItem, flexLine.mSumCrossSizeBefore);
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iRound, 1073741824);
                        reorderedFlexItemAt.measure(iMakeMeasureSpec, childHeightMeasureSpecInternal);
                        int measuredWidth2 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight2 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(i7, iMakeMeasureSpec, childHeightMeasureSpecInternal, reorderedFlexItemAt);
                        this.mFlexContainer.updateViewCache(i7, reorderedFlexItemAt);
                        measuredWidth = measuredWidth2;
                        measuredHeight = measuredHeight2;
                    }
                    int iMax2 = Math.max(i6, measuredHeight + flexItem.getMarginTop() + flexItem.getMarginBottom() + this.mFlexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt));
                    flexLine.mMainSize += measuredWidth + flexItem.getMarginLeft() + flexItem.getMarginRight();
                    iMax = iMax2;
                } else {
                    int measuredHeight3 = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr3 = this.mMeasuredSizeCache;
                    if (jArr3 != null) {
                        measuredHeight3 = extractHigherInt(jArr3[i7]);
                    }
                    int measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr4 = this.mMeasuredSizeCache;
                    if (jArr4 != null) {
                        measuredWidth3 = extractLowerInt(jArr4[i7]);
                    }
                    if (this.mChildrenFrozen[i7] || flexItem.getFlexGrow() <= f3) {
                        i4 = i2;
                    } else {
                        float flexGrow2 = measuredHeight3 + (flexItem.getFlexGrow() * f4);
                        if (i5 == flexLine.mItemCount - 1) {
                            flexGrow2 += f5;
                            f5 = f3;
                        }
                        int iRound2 = Math.round(flexGrow2);
                        if (iRound2 > flexItem.getMaxHeight()) {
                            iRound2 = flexItem.getMaxHeight();
                            this.mChildrenFrozen[i7] = true;
                            flexLine.mTotalFlexGrow -= flexItem.getFlexGrow();
                            i4 = i2;
                            z2 = true;
                        } else {
                            f5 += flexGrow2 - iRound2;
                            i4 = i2;
                            double d6 = f5;
                            if (d6 > 1.0d) {
                                iRound2++;
                                d4 = d6 - 1.0d;
                            } else if (d6 < -1.0d) {
                                iRound2--;
                                d4 = d6 + 1.0d;
                            }
                            f5 = (float) d4;
                        }
                        int childWidthMeasureSpecInternal = getChildWidthMeasureSpecInternal(widthMeasureSpec, flexItem, flexLine.mSumCrossSizeBefore);
                        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iRound2, 1073741824);
                        reorderedFlexItemAt.measure(childWidthMeasureSpecInternal, iMakeMeasureSpec2);
                        measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight4 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(i7, childWidthMeasureSpecInternal, iMakeMeasureSpec2, reorderedFlexItemAt);
                        this.mFlexContainer.updateViewCache(i7, reorderedFlexItemAt);
                        measuredHeight3 = measuredHeight4;
                    }
                    iMax = Math.max(i6, measuredWidth3 + flexItem.getMarginLeft() + flexItem.getMarginRight() + this.mFlexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt));
                    flexLine.mMainSize += measuredHeight3 + flexItem.getMarginTop() + flexItem.getMarginBottom();
                    i3 = i4;
                }
                flexLine.mCrossSize = Math.max(flexLine.mCrossSize, iMax);
                i6 = iMax;
            }
            i5++;
            i2 = i3;
            f3 = 0.0f;
        }
        int i9 = i2;
        if (!z2 || i9 == flexLine.mMainSize) {
            return;
        }
        expandFlexItems(widthMeasureSpec, heightMeasureSpec, flexLine, maxMainSize, paddingAlongMainAxis, true);
    }

    private int getChildHeightMeasureSpecInternal(int heightMeasureSpec, FlexItem flexItem, int padding) {
        FlexContainer flexContainer = this.mFlexContainer;
        int childHeightMeasureSpec = flexContainer.getChildHeightMeasureSpec(heightMeasureSpec, flexContainer.getPaddingTop() + this.mFlexContainer.getPaddingBottom() + flexItem.getMarginTop() + flexItem.getMarginBottom() + padding, flexItem.getHeight());
        int size = View.MeasureSpec.getSize(childHeightMeasureSpec);
        return size > flexItem.getMaxHeight() ? View.MeasureSpec.makeMeasureSpec(flexItem.getMaxHeight(), View.MeasureSpec.getMode(childHeightMeasureSpec)) : size < flexItem.getMinHeight() ? View.MeasureSpec.makeMeasureSpec(flexItem.getMinHeight(), View.MeasureSpec.getMode(childHeightMeasureSpec)) : childHeightMeasureSpec;
    }

    private int getChildWidthMeasureSpecInternal(int widthMeasureSpec, FlexItem flexItem, int padding) {
        FlexContainer flexContainer = this.mFlexContainer;
        int childWidthMeasureSpec = flexContainer.getChildWidthMeasureSpec(widthMeasureSpec, flexContainer.getPaddingLeft() + this.mFlexContainer.getPaddingRight() + flexItem.getMarginLeft() + flexItem.getMarginRight() + padding, flexItem.getWidth());
        int size = View.MeasureSpec.getSize(childWidthMeasureSpec);
        return size > flexItem.getMaxWidth() ? View.MeasureSpec.makeMeasureSpec(flexItem.getMaxWidth(), View.MeasureSpec.getMode(childWidthMeasureSpec)) : size < flexItem.getMinWidth() ? View.MeasureSpec.makeMeasureSpec(flexItem.getMinWidth(), View.MeasureSpec.getMode(childWidthMeasureSpec)) : childWidthMeasureSpec;
    }

    private int getFlexItemMarginEndCross(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getMarginBottom() : flexItem.getMarginRight();
    }

    private int getFlexItemMarginEndMain(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getMarginRight() : flexItem.getMarginBottom();
    }

    private int getFlexItemMarginStartCross(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getMarginTop() : flexItem.getMarginLeft();
    }

    private int getFlexItemMarginStartMain(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getMarginLeft() : flexItem.getMarginTop();
    }

    private int getFlexItemSizeCross(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getHeight() : flexItem.getWidth();
    }

    private int getFlexItemSizeMain(FlexItem flexItem, boolean isMainHorizontal) {
        return isMainHorizontal ? flexItem.getWidth() : flexItem.getHeight();
    }

    private int getPaddingEndCross(boolean isMainHorizontal) {
        return isMainHorizontal ? this.mFlexContainer.getPaddingBottom() : this.mFlexContainer.getPaddingEnd();
    }

    private int getPaddingEndMain(boolean isMainHorizontal) {
        return isMainHorizontal ? this.mFlexContainer.getPaddingEnd() : this.mFlexContainer.getPaddingBottom();
    }

    private int getPaddingStartCross(boolean isMainHorizontal) {
        return isMainHorizontal ? this.mFlexContainer.getPaddingTop() : this.mFlexContainer.getPaddingStart();
    }

    private int getPaddingStartMain(boolean isMainHorizontal) {
        return isMainHorizontal ? this.mFlexContainer.getPaddingStart() : this.mFlexContainer.getPaddingTop();
    }

    private int getViewMeasuredSizeCross(View view, boolean isMainHorizontal) {
        return isMainHorizontal ? view.getMeasuredHeight() : view.getMeasuredWidth();
    }

    private int getViewMeasuredSizeMain(View view, boolean isMainHorizontal) {
        return isMainHorizontal ? view.getMeasuredWidth() : view.getMeasuredHeight();
    }

    private boolean isLastFlexItem(int childIndex, int childCount, FlexLine flexLine) {
        return childIndex == childCount - 1 && flexLine.getItemCountNotGone() != 0;
    }

    private boolean isWrapRequired(View view, int mode, int maxSize, int currentLength, int childLength, FlexItem flexItem, int index, int indexInFlexLine, int flexLinesSize) {
        if (this.mFlexContainer.getFlexWrap() == 0) {
            return false;
        }
        if (flexItem.isWrapBefore()) {
            return true;
        }
        if (mode == 0) {
            return false;
        }
        int maxLine = this.mFlexContainer.getMaxLine();
        if (maxLine != -1 && maxLine <= flexLinesSize + 1) {
            return false;
        }
        int decorationLengthMainAxis = this.mFlexContainer.getDecorationLengthMainAxis(view, index, indexInFlexLine);
        if (decorationLengthMainAxis > 0) {
            childLength += decorationLengthMainAxis;
        }
        return maxSize < currentLength + childLength;
    }

    private void shrinkFlexItems(int widthMeasureSpec, int heightMeasureSpec, FlexLine flexLine, int maxMainSize, int paddingAlongMainAxis, boolean calledRecursively) {
        int i2;
        int i3;
        int iMax;
        int i4 = flexLine.mMainSize;
        float f2 = flexLine.mTotalFlexShrink;
        float f3 = 0.0f;
        if (f2 <= 0.0f || maxMainSize > i4) {
            return;
        }
        float f4 = (i4 - maxMainSize) / f2;
        flexLine.mMainSize = paddingAlongMainAxis + flexLine.mDividerLengthInMainSize;
        if (!calledRecursively) {
            flexLine.mCrossSize = Integer.MIN_VALUE;
        }
        int i5 = 0;
        boolean z2 = false;
        int i6 = 0;
        float f5 = 0.0f;
        while (i5 < flexLine.mItemCount) {
            int i7 = flexLine.mFirstIndex + i5;
            View reorderedFlexItemAt = this.mFlexContainer.getReorderedFlexItemAt(i7);
            if (reorderedFlexItemAt == null || reorderedFlexItemAt.getVisibility() == 8) {
                i2 = i4;
                i3 = i5;
            } else {
                FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                int flexDirection = this.mFlexContainer.getFlexDirection();
                if (flexDirection == 0 || flexDirection == 1) {
                    i2 = i4;
                    int i8 = i5;
                    int measuredWidth = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr = this.mMeasuredSizeCache;
                    if (jArr != null) {
                        measuredWidth = extractLowerInt(jArr[i7]);
                    }
                    int measuredHeight = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr2 = this.mMeasuredSizeCache;
                    if (jArr2 != null) {
                        measuredHeight = extractHigherInt(jArr2[i7]);
                    }
                    if (this.mChildrenFrozen[i7] || flexItem.getFlexShrink() <= 0.0f) {
                        i3 = i8;
                    } else {
                        float flexShrink = measuredWidth - (flexItem.getFlexShrink() * f4);
                        i3 = i8;
                        if (i3 == flexLine.mItemCount - 1) {
                            flexShrink += f5;
                            f5 = 0.0f;
                        }
                        int iRound = Math.round(flexShrink);
                        if (iRound < flexItem.getMinWidth()) {
                            iRound = flexItem.getMinWidth();
                            this.mChildrenFrozen[i7] = true;
                            flexLine.mTotalFlexShrink -= flexItem.getFlexShrink();
                            z2 = true;
                        } else {
                            f5 += flexShrink - iRound;
                            double d3 = f5;
                            if (d3 > 1.0d) {
                                iRound++;
                                f5 -= 1.0f;
                            } else if (d3 < -1.0d) {
                                iRound--;
                                f5 += 1.0f;
                            }
                        }
                        int childHeightMeasureSpecInternal = getChildHeightMeasureSpecInternal(heightMeasureSpec, flexItem, flexLine.mSumCrossSizeBefore);
                        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iRound, 1073741824);
                        reorderedFlexItemAt.measure(iMakeMeasureSpec, childHeightMeasureSpecInternal);
                        int measuredWidth2 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight2 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(i7, iMakeMeasureSpec, childHeightMeasureSpecInternal, reorderedFlexItemAt);
                        this.mFlexContainer.updateViewCache(i7, reorderedFlexItemAt);
                        measuredWidth = measuredWidth2;
                        measuredHeight = measuredHeight2;
                    }
                    int iMax2 = Math.max(i6, measuredHeight + flexItem.getMarginTop() + flexItem.getMarginBottom() + this.mFlexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt));
                    flexLine.mMainSize += measuredWidth + flexItem.getMarginLeft() + flexItem.getMarginRight();
                    iMax = iMax2;
                } else {
                    int measuredHeight3 = reorderedFlexItemAt.getMeasuredHeight();
                    long[] jArr3 = this.mMeasuredSizeCache;
                    if (jArr3 != null) {
                        measuredHeight3 = extractHigherInt(jArr3[i7]);
                    }
                    int measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                    long[] jArr4 = this.mMeasuredSizeCache;
                    if (jArr4 != null) {
                        measuredWidth3 = extractLowerInt(jArr4[i7]);
                    }
                    if (this.mChildrenFrozen[i7] || flexItem.getFlexShrink() <= f3) {
                        i2 = i4;
                        i3 = i5;
                    } else {
                        float flexShrink2 = measuredHeight3 - (flexItem.getFlexShrink() * f4);
                        if (i5 == flexLine.mItemCount - 1) {
                            flexShrink2 += f5;
                            f5 = f3;
                        }
                        int iRound2 = Math.round(flexShrink2);
                        if (iRound2 < flexItem.getMinHeight()) {
                            iRound2 = flexItem.getMinHeight();
                            this.mChildrenFrozen[i7] = true;
                            flexLine.mTotalFlexShrink -= flexItem.getFlexShrink();
                            i2 = i4;
                            i3 = i5;
                            z2 = true;
                        } else {
                            f5 += flexShrink2 - iRound2;
                            i2 = i4;
                            i3 = i5;
                            double d4 = f5;
                            if (d4 > 1.0d) {
                                iRound2++;
                                f5 -= 1.0f;
                            } else if (d4 < -1.0d) {
                                iRound2--;
                                f5 += 1.0f;
                            }
                        }
                        int childWidthMeasureSpecInternal = getChildWidthMeasureSpecInternal(widthMeasureSpec, flexItem, flexLine.mSumCrossSizeBefore);
                        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iRound2, 1073741824);
                        reorderedFlexItemAt.measure(childWidthMeasureSpecInternal, iMakeMeasureSpec2);
                        measuredWidth3 = reorderedFlexItemAt.getMeasuredWidth();
                        int measuredHeight4 = reorderedFlexItemAt.getMeasuredHeight();
                        updateMeasureCache(i7, childWidthMeasureSpecInternal, iMakeMeasureSpec2, reorderedFlexItemAt);
                        this.mFlexContainer.updateViewCache(i7, reorderedFlexItemAt);
                        measuredHeight3 = measuredHeight4;
                    }
                    iMax = Math.max(i6, measuredWidth3 + flexItem.getMarginLeft() + flexItem.getMarginRight() + this.mFlexContainer.getDecorationLengthCrossAxis(reorderedFlexItemAt));
                    flexLine.mMainSize += measuredHeight3 + flexItem.getMarginTop() + flexItem.getMarginBottom();
                }
                flexLine.mCrossSize = Math.max(flexLine.mCrossSize, iMax);
                i6 = iMax;
            }
            i5 = i3 + 1;
            i4 = i2;
            f3 = 0.0f;
        }
        int i9 = i4;
        if (!z2 || i9 == flexLine.mMainSize) {
            return;
        }
        shrinkFlexItems(widthMeasureSpec, heightMeasureSpec, flexLine, maxMainSize, paddingAlongMainAxis, true);
    }

    private int[] sortOrdersIntoReorderedIndices(int childCount, List<Order> orders, SparseIntArray orderCache) {
        Collections.sort(orders);
        orderCache.clear();
        int[] iArr = new int[childCount];
        int i2 = 0;
        for (Order order : orders) {
            int i3 = order.index;
            iArr[i2] = i3;
            orderCache.append(i3, order.order);
            i2++;
        }
        return iArr;
    }

    private void stretchViewHorizontally(View view, int crossSize, int index) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int iMin = Math.min(Math.max(((crossSize - flexItem.getMarginLeft()) - flexItem.getMarginRight()) - this.mFlexContainer.getDecorationLengthCrossAxis(view), flexItem.getMinWidth()), flexItem.getMaxWidth());
        long[] jArr = this.mMeasuredSizeCache;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(jArr != null ? extractHigherInt(jArr[index]) : view.getMeasuredHeight(), 1073741824);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iMin, 1073741824);
        view.measure(iMakeMeasureSpec2, iMakeMeasureSpec);
        updateMeasureCache(index, iMakeMeasureSpec2, iMakeMeasureSpec, view);
        this.mFlexContainer.updateViewCache(index, view);
    }

    private void stretchViewVertically(View view, int crossSize, int index) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int iMin = Math.min(Math.max(((crossSize - flexItem.getMarginTop()) - flexItem.getMarginBottom()) - this.mFlexContainer.getDecorationLengthCrossAxis(view), flexItem.getMinHeight()), flexItem.getMaxHeight());
        long[] jArr = this.mMeasuredSizeCache;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(jArr != null ? extractLowerInt(jArr[index]) : view.getMeasuredWidth(), 1073741824);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(iMin, 1073741824);
        view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        updateMeasureCache(index, iMakeMeasureSpec, iMakeMeasureSpec2, view);
        this.mFlexContainer.updateViewCache(index, view);
    }

    private void updateMeasureCache(int index, int widthMeasureSpec, int heightMeasureSpec, View view) {
        long[] jArr = this.mMeasureSpecCache;
        if (jArr != null) {
            jArr[index] = makeCombinedLong(widthMeasureSpec, heightMeasureSpec);
        }
        long[] jArr2 = this.mMeasuredSizeCache;
        if (jArr2 != null) {
            jArr2[index] = makeCombinedLong(view.getMeasuredWidth(), view.getMeasuredHeight());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void calculateFlexLines(FlexLinesResult flexLinesResult, int i2, int i3, int i4, int i5, int i6, @Nullable List<FlexLine> list) {
        int i7;
        FlexLinesResult flexLinesResult2;
        int i8;
        int i9;
        int i10;
        List<FlexLine> list2;
        int i11;
        View view;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19 = i2;
        int i20 = i3;
        int i21 = i6;
        boolean zIsMainAxisDirectionHorizontal = this.mFlexContainer.isMainAxisDirectionHorizontal();
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        List<FlexLine> arrayList = list == null ? new ArrayList() : list;
        flexLinesResult.mFlexLines = arrayList;
        int i22 = i21 == -1 ? 1 : 0;
        int paddingStartMain = getPaddingStartMain(zIsMainAxisDirectionHorizontal);
        int paddingEndMain = getPaddingEndMain(zIsMainAxisDirectionHorizontal);
        int paddingStartCross = getPaddingStartCross(zIsMainAxisDirectionHorizontal);
        int paddingEndCross = getPaddingEndCross(zIsMainAxisDirectionHorizontal);
        FlexLine flexLine = new FlexLine();
        int i23 = i5;
        flexLine.mFirstIndex = i23;
        int i24 = paddingEndMain + paddingStartMain;
        flexLine.mMainSize = i24;
        int flexItemCount = this.mFlexContainer.getFlexItemCount();
        int i25 = i22;
        int i26 = Integer.MIN_VALUE;
        int i27 = 0;
        int iCombineMeasuredStates = 0;
        int i28 = 0;
        while (true) {
            if (i23 >= flexItemCount) {
                i7 = iCombineMeasuredStates;
                flexLinesResult2 = flexLinesResult;
                break;
            }
            View reorderedFlexItemAt = this.mFlexContainer.getReorderedFlexItemAt(i23);
            if (reorderedFlexItemAt != null) {
                if (reorderedFlexItemAt.getVisibility() != 8) {
                    if (reorderedFlexItemAt instanceof CompoundButton) {
                        evaluateMinimumSizeForCompoundButton((CompoundButton) reorderedFlexItemAt);
                    }
                    FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                    int i29 = flexItemCount;
                    if (flexItem.getAlignSelf() == 4) {
                        flexLine.mIndicesAlignSelfStretch.add(Integer.valueOf(i23));
                    }
                    int flexItemSizeMain = getFlexItemSizeMain(flexItem, zIsMainAxisDirectionHorizontal);
                    if (flexItem.getFlexBasisPercent() != -1.0f && mode == 1073741824) {
                        flexItemSizeMain = Math.round(size * flexItem.getFlexBasisPercent());
                    }
                    if (zIsMainAxisDirectionHorizontal) {
                        int childWidthMeasureSpec = this.mFlexContainer.getChildWidthMeasureSpec(i19, i24 + getFlexItemMarginStartMain(flexItem, true) + getFlexItemMarginEndMain(flexItem, true), flexItemSizeMain);
                        i8 = size;
                        i9 = mode;
                        int childHeightMeasureSpec = this.mFlexContainer.getChildHeightMeasureSpec(i20, paddingStartCross + paddingEndCross + getFlexItemMarginStartCross(flexItem, true) + getFlexItemMarginEndCross(flexItem, true) + i27, getFlexItemSizeCross(flexItem, true));
                        reorderedFlexItemAt.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                        updateMeasureCache(i23, childWidthMeasureSpec, childHeightMeasureSpec, reorderedFlexItemAt);
                        i10 = childWidthMeasureSpec;
                    } else {
                        i8 = size;
                        i9 = mode;
                        int childWidthMeasureSpec2 = this.mFlexContainer.getChildWidthMeasureSpec(i20, paddingStartCross + paddingEndCross + getFlexItemMarginStartCross(flexItem, false) + getFlexItemMarginEndCross(flexItem, false) + i27, getFlexItemSizeCross(flexItem, false));
                        int childHeightMeasureSpec2 = this.mFlexContainer.getChildHeightMeasureSpec(i19, getFlexItemMarginStartMain(flexItem, false) + i24 + getFlexItemMarginEndMain(flexItem, false), flexItemSizeMain);
                        reorderedFlexItemAt.measure(childWidthMeasureSpec2, childHeightMeasureSpec2);
                        updateMeasureCache(i23, childWidthMeasureSpec2, childHeightMeasureSpec2, reorderedFlexItemAt);
                        i10 = childHeightMeasureSpec2;
                    }
                    this.mFlexContainer.updateViewCache(i23, reorderedFlexItemAt);
                    checkSizeConstraints(reorderedFlexItemAt, i23);
                    iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, reorderedFlexItemAt.getMeasuredState());
                    int i30 = i27;
                    int i31 = i24;
                    FlexLine flexLine2 = flexLine;
                    int i32 = i23;
                    list2 = arrayList;
                    int i33 = i10;
                    if (isWrapRequired(reorderedFlexItemAt, i9, i8, flexLine.mMainSize, getFlexItemMarginEndMain(flexItem, zIsMainAxisDirectionHorizontal) + getViewMeasuredSizeMain(reorderedFlexItemAt, zIsMainAxisDirectionHorizontal) + getFlexItemMarginStartMain(flexItem, zIsMainAxisDirectionHorizontal), flexItem, i32, i28, arrayList.size())) {
                        if (flexLine2.getItemCountNotGone() > 0) {
                            addFlexLine(list2, flexLine2, i32 > 0 ? i32 - 1 : 0, i30);
                            i27 = flexLine2.mCrossSize + i30;
                        } else {
                            i27 = i30;
                        }
                        if (!zIsMainAxisDirectionHorizontal) {
                            i11 = i3;
                            view = reorderedFlexItemAt;
                            i23 = i32;
                            if (flexItem.getWidth() == -1) {
                                FlexContainer flexContainer = this.mFlexContainer;
                                view.measure(flexContainer.getChildWidthMeasureSpec(i11, flexContainer.getPaddingLeft() + this.mFlexContainer.getPaddingRight() + flexItem.getMarginLeft() + flexItem.getMarginRight() + i27, flexItem.getWidth()), i33);
                                checkSizeConstraints(view, i23);
                            }
                        } else if (flexItem.getHeight() == -1) {
                            FlexContainer flexContainer2 = this.mFlexContainer;
                            i11 = i3;
                            i23 = i32;
                            view = reorderedFlexItemAt;
                            view.measure(i33, flexContainer2.getChildHeightMeasureSpec(i11, flexContainer2.getPaddingTop() + this.mFlexContainer.getPaddingBottom() + flexItem.getMarginTop() + flexItem.getMarginBottom() + i27, flexItem.getHeight()));
                            checkSizeConstraints(view, i23);
                        } else {
                            i11 = i3;
                            view = reorderedFlexItemAt;
                            i23 = i32;
                        }
                        flexLine = new FlexLine();
                        i13 = 1;
                        flexLine.mItemCount = 1;
                        i12 = i31;
                        flexLine.mMainSize = i12;
                        flexLine.mFirstIndex = i23;
                        i15 = Integer.MIN_VALUE;
                        i14 = 0;
                    } else {
                        i11 = i3;
                        view = reorderedFlexItemAt;
                        i23 = i32;
                        flexLine = flexLine2;
                        i12 = i31;
                        i13 = 1;
                        flexLine.mItemCount++;
                        i14 = i28 + 1;
                        i27 = i30;
                        i15 = i26;
                    }
                    flexLine.mAnyItemsHaveFlexGrow = (flexLine.mAnyItemsHaveFlexGrow ? 1 : 0) | (flexItem.getFlexGrow() != 0.0f ? i13 : 0);
                    flexLine.mAnyItemsHaveFlexShrink = (flexLine.mAnyItemsHaveFlexShrink ? 1 : 0) | (flexItem.getFlexShrink() != 0.0f ? i13 : 0);
                    int[] iArr = this.mIndexToFlexLine;
                    if (iArr != null) {
                        iArr[i23] = list2.size();
                    }
                    flexLine.mMainSize += getViewMeasuredSizeMain(view, zIsMainAxisDirectionHorizontal) + getFlexItemMarginStartMain(flexItem, zIsMainAxisDirectionHorizontal) + getFlexItemMarginEndMain(flexItem, zIsMainAxisDirectionHorizontal);
                    flexLine.mTotalFlexGrow += flexItem.getFlexGrow();
                    flexLine.mTotalFlexShrink += flexItem.getFlexShrink();
                    this.mFlexContainer.onNewFlexItemAdded(view, i23, i14, flexLine);
                    int iMax = Math.max(i15, getViewMeasuredSizeCross(view, zIsMainAxisDirectionHorizontal) + getFlexItemMarginStartCross(flexItem, zIsMainAxisDirectionHorizontal) + getFlexItemMarginEndCross(flexItem, zIsMainAxisDirectionHorizontal) + this.mFlexContainer.getDecorationLengthCrossAxis(view));
                    flexLine.mCrossSize = Math.max(flexLine.mCrossSize, iMax);
                    if (zIsMainAxisDirectionHorizontal) {
                        if (this.mFlexContainer.getFlexWrap() != 2) {
                            flexLine.mMaxBaseline = Math.max(flexLine.mMaxBaseline, view.getBaseline() + flexItem.getMarginTop());
                        } else {
                            flexLine.mMaxBaseline = Math.max(flexLine.mMaxBaseline, (view.getMeasuredHeight() - view.getBaseline()) + flexItem.getMarginBottom());
                        }
                    }
                    i16 = i29;
                    if (isLastFlexItem(i23, i16, flexLine)) {
                        addFlexLine(list2, flexLine, i23, i27);
                        i27 += flexLine.mCrossSize;
                    }
                    i17 = i6;
                    if (i17 == -1 || list2.size() <= 0 || list2.get(list2.size() - i13).mLastIndex < i17 || i23 < i17 || i25 != 0) {
                        i18 = i4;
                    } else {
                        i27 = -flexLine.getCrossSize();
                        i18 = i4;
                        i25 = i13;
                    }
                    if (i27 > i18 && i25 != 0) {
                        flexLinesResult2 = flexLinesResult;
                        i7 = iCombineMeasuredStates;
                        break;
                    }
                    i28 = i14;
                    i26 = iMax;
                    i23++;
                    i19 = i2;
                    flexItemCount = i16;
                    i20 = i11;
                    i24 = i12;
                    arrayList = list2;
                    size = i8;
                    i21 = i17;
                    mode = i9;
                } else {
                    flexLine.mGoneItemCount++;
                    flexLine.mItemCount++;
                    if (isLastFlexItem(i23, flexItemCount, flexLine)) {
                        addFlexLine(arrayList, flexLine, i23, i27);
                    }
                }
            } else if (isLastFlexItem(i23, flexItemCount, flexLine)) {
                addFlexLine(arrayList, flexLine, i23, i27);
            }
            i8 = size;
            i9 = mode;
            i11 = i20;
            i17 = i21;
            list2 = arrayList;
            i12 = i24;
            i16 = flexItemCount;
            i23++;
            i19 = i2;
            flexItemCount = i16;
            i20 = i11;
            i24 = i12;
            arrayList = list2;
            size = i8;
            i21 = i17;
            mode = i9;
        }
        flexLinesResult2.mChildState = i7;
    }

    public void calculateHorizontalFlexLines(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec) {
        calculateFlexLines(result, widthMeasureSpec, heightMeasureSpec, Integer.MAX_VALUE, 0, -1, null);
    }

    public void calculateHorizontalFlexLinesToIndex(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec, int needsCalcAmount, int toIndex, List<FlexLine> existingLines) {
        calculateFlexLines(result, widthMeasureSpec, heightMeasureSpec, needsCalcAmount, 0, toIndex, existingLines);
    }

    public void calculateVerticalFlexLines(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec) {
        calculateFlexLines(result, heightMeasureSpec, widthMeasureSpec, Integer.MAX_VALUE, 0, -1, null);
    }

    public void calculateVerticalFlexLinesToIndex(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec, int needsCalcAmount, int toIndex, List<FlexLine> existingLines) {
        calculateFlexLines(result, heightMeasureSpec, widthMeasureSpec, needsCalcAmount, 0, toIndex, existingLines);
    }

    public void clearFlexLines(List<FlexLine> flexLines, int fromFlexItem) {
        int i2 = this.mIndexToFlexLine[fromFlexItem];
        if (i2 == -1) {
            i2 = 0;
        }
        if (flexLines.size() > i2) {
            flexLines.subList(i2, flexLines.size()).clear();
        }
        int[] iArr = this.mIndexToFlexLine;
        int length = iArr.length - 1;
        if (fromFlexItem > length) {
            Arrays.fill(iArr, -1);
        } else {
            Arrays.fill(iArr, fromFlexItem, length, -1);
        }
        long[] jArr = this.mMeasureSpecCache;
        int length2 = jArr.length - 1;
        if (fromFlexItem > length2) {
            Arrays.fill(jArr, 0L);
        } else {
            Arrays.fill(jArr, fromFlexItem, length2, 0L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int[] createReorderedIndices(View viewBeforeAdded, int indexForViewBeforeAdded, ViewGroup.LayoutParams paramsForViewBeforeAdded, SparseIntArray orderCache) {
        int flexItemCount = this.mFlexContainer.getFlexItemCount();
        List<Order> listCreateOrders = createOrders(flexItemCount);
        Order order = new Order();
        if (viewBeforeAdded == null || !(paramsForViewBeforeAdded instanceof FlexItem)) {
            order.order = 1;
        } else {
            order.order = ((FlexItem) paramsForViewBeforeAdded).getOrder();
        }
        if (indexForViewBeforeAdded == -1 || indexForViewBeforeAdded == flexItemCount || indexForViewBeforeAdded >= this.mFlexContainer.getFlexItemCount()) {
            order.index = flexItemCount;
        } else {
            order.index = indexForViewBeforeAdded;
            while (indexForViewBeforeAdded < flexItemCount) {
                listCreateOrders.get(indexForViewBeforeAdded).index++;
                indexForViewBeforeAdded++;
            }
        }
        listCreateOrders.add(order);
        return sortOrdersIntoReorderedIndices(flexItemCount + 1, listCreateOrders, orderCache);
    }

    public void determineCrossSize(int widthMeasureSpec, int heightMeasureSpec, int paddingAlongCrossAxis) {
        int mode;
        int size;
        int flexDirection = this.mFlexContainer.getFlexDirection();
        if (flexDirection == 0 || flexDirection == 1) {
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
            mode = mode2;
            size = size2;
        } else {
            if (flexDirection != 2 && flexDirection != 3) {
                throw new IllegalArgumentException("Invalid flex direction: " + flexDirection);
            }
            mode = View.MeasureSpec.getMode(widthMeasureSpec);
            size = View.MeasureSpec.getSize(widthMeasureSpec);
        }
        List<FlexLine> flexLinesInternal = this.mFlexContainer.getFlexLinesInternal();
        if (mode == 1073741824) {
            int sumOfCrossSize = this.mFlexContainer.getSumOfCrossSize() + paddingAlongCrossAxis;
            int i2 = 0;
            if (flexLinesInternal.size() == 1) {
                flexLinesInternal.get(0).mCrossSize = size - paddingAlongCrossAxis;
                return;
            }
            if (flexLinesInternal.size() >= 2) {
                int alignContent = this.mFlexContainer.getAlignContent();
                if (alignContent == 1) {
                    int i3 = size - sumOfCrossSize;
                    FlexLine flexLine = new FlexLine();
                    flexLine.mCrossSize = i3;
                    flexLinesInternal.add(0, flexLine);
                    return;
                }
                if (alignContent == 2) {
                    this.mFlexContainer.setFlexLines(constructFlexLinesForAlignContentCenter(flexLinesInternal, size, sumOfCrossSize));
                    return;
                }
                if (alignContent == 3) {
                    if (sumOfCrossSize >= size) {
                        return;
                    }
                    float size3 = (size - sumOfCrossSize) / (flexLinesInternal.size() - 1);
                    ArrayList arrayList = new ArrayList();
                    int size4 = flexLinesInternal.size();
                    float f2 = 0.0f;
                    while (i2 < size4) {
                        arrayList.add(flexLinesInternal.get(i2));
                        if (i2 != flexLinesInternal.size() - 1) {
                            FlexLine flexLine2 = new FlexLine();
                            if (i2 == flexLinesInternal.size() - 2) {
                                flexLine2.mCrossSize = Math.round(f2 + size3);
                                f2 = 0.0f;
                            } else {
                                flexLine2.mCrossSize = Math.round(size3);
                            }
                            int i4 = flexLine2.mCrossSize;
                            f2 += size3 - i4;
                            if (f2 > 1.0f) {
                                flexLine2.mCrossSize = i4 + 1;
                                f2 -= 1.0f;
                            } else if (f2 < -1.0f) {
                                flexLine2.mCrossSize = i4 - 1;
                                f2 += 1.0f;
                            }
                            arrayList.add(flexLine2);
                        }
                        i2++;
                    }
                    this.mFlexContainer.setFlexLines(arrayList);
                    return;
                }
                if (alignContent == 4) {
                    if (sumOfCrossSize >= size) {
                        this.mFlexContainer.setFlexLines(constructFlexLinesForAlignContentCenter(flexLinesInternal, size, sumOfCrossSize));
                        return;
                    }
                    int size5 = (size - sumOfCrossSize) / (flexLinesInternal.size() * 2);
                    ArrayList arrayList2 = new ArrayList();
                    FlexLine flexLine3 = new FlexLine();
                    flexLine3.mCrossSize = size5;
                    for (FlexLine flexLine4 : flexLinesInternal) {
                        arrayList2.add(flexLine3);
                        arrayList2.add(flexLine4);
                        arrayList2.add(flexLine3);
                    }
                    this.mFlexContainer.setFlexLines(arrayList2);
                    return;
                }
                if (alignContent == 5 && sumOfCrossSize < size) {
                    float size6 = (size - sumOfCrossSize) / flexLinesInternal.size();
                    int size7 = flexLinesInternal.size();
                    float f3 = 0.0f;
                    while (i2 < size7) {
                        FlexLine flexLine5 = flexLinesInternal.get(i2);
                        float f4 = flexLine5.mCrossSize + size6;
                        if (i2 == flexLinesInternal.size() - 1) {
                            f4 += f3;
                            f3 = 0.0f;
                        }
                        int iRound = Math.round(f4);
                        f3 += f4 - iRound;
                        if (f3 > 1.0f) {
                            iRound++;
                            f3 -= 1.0f;
                        } else if (f3 < -1.0f) {
                            iRound--;
                            f3 += 1.0f;
                        }
                        flexLine5.mCrossSize = iRound;
                        i2++;
                    }
                }
            }
        }
    }

    public void determineMainSize(int widthMeasureSpec, int heightMeasureSpec) {
        determineMainSize(widthMeasureSpec, heightMeasureSpec, 0);
    }

    public void ensureIndexToFlexLine(int size) {
        int[] iArr = this.mIndexToFlexLine;
        if (iArr == null) {
            this.mIndexToFlexLine = new int[Math.max(size, 10)];
        } else if (iArr.length < size) {
            this.mIndexToFlexLine = Arrays.copyOf(this.mIndexToFlexLine, Math.max(iArr.length * 2, size));
        }
    }

    public void ensureMeasureSpecCache(int size) {
        long[] jArr = this.mMeasureSpecCache;
        if (jArr == null) {
            this.mMeasureSpecCache = new long[Math.max(size, 10)];
        } else if (jArr.length < size) {
            this.mMeasureSpecCache = Arrays.copyOf(this.mMeasureSpecCache, Math.max(jArr.length * 2, size));
        }
    }

    public void ensureMeasuredSizeCache(int size) {
        long[] jArr = this.mMeasuredSizeCache;
        if (jArr == null) {
            this.mMeasuredSizeCache = new long[Math.max(size, 10)];
        } else if (jArr.length < size) {
            this.mMeasuredSizeCache = Arrays.copyOf(this.mMeasuredSizeCache, Math.max(jArr.length * 2, size));
        }
    }

    public int extractHigherInt(long longValue) {
        return (int) (longValue >> 32);
    }

    public int extractLowerInt(long longValue) {
        return (int) longValue;
    }

    public boolean isOrderChangedFromLastMeasurement(SparseIntArray orderCache) {
        int flexItemCount = this.mFlexContainer.getFlexItemCount();
        if (orderCache.size() != flexItemCount) {
            return true;
        }
        for (int i2 = 0; i2 < flexItemCount; i2++) {
            View flexItemAt = this.mFlexContainer.getFlexItemAt(i2);
            if (flexItemAt != null && ((FlexItem) flexItemAt.getLayoutParams()).getOrder() != orderCache.get(i2)) {
                return true;
            }
        }
        return false;
    }

    public void layoutSingleChildHorizontal(View view, FlexLine flexLine, int left, int top2, int right, int bottom) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int alignItems = this.mFlexContainer.getAlignItems();
        if (flexItem.getAlignSelf() != -1) {
            alignItems = flexItem.getAlignSelf();
        }
        int i2 = flexLine.mCrossSize;
        if (alignItems != 0) {
            if (alignItems == 1) {
                if (this.mFlexContainer.getFlexWrap() == 2) {
                    view.layout(left, (top2 - i2) + view.getMeasuredHeight() + flexItem.getMarginTop(), right, (bottom - i2) + view.getMeasuredHeight() + flexItem.getMarginTop());
                    return;
                } else {
                    int i3 = top2 + i2;
                    view.layout(left, (i3 - view.getMeasuredHeight()) - flexItem.getMarginBottom(), right, i3 - flexItem.getMarginBottom());
                    return;
                }
            }
            if (alignItems == 2) {
                int measuredHeight = (((i2 - view.getMeasuredHeight()) + flexItem.getMarginTop()) - flexItem.getMarginBottom()) / 2;
                if (this.mFlexContainer.getFlexWrap() != 2) {
                    int i4 = top2 + measuredHeight;
                    view.layout(left, i4, right, view.getMeasuredHeight() + i4);
                    return;
                } else {
                    int i5 = top2 - measuredHeight;
                    view.layout(left, i5, right, view.getMeasuredHeight() + i5);
                    return;
                }
            }
            if (alignItems == 3) {
                if (this.mFlexContainer.getFlexWrap() != 2) {
                    int iMax = Math.max(flexLine.mMaxBaseline - view.getBaseline(), flexItem.getMarginTop());
                    view.layout(left, top2 + iMax, right, bottom + iMax);
                    return;
                } else {
                    int iMax2 = Math.max((flexLine.mMaxBaseline - view.getMeasuredHeight()) + view.getBaseline(), flexItem.getMarginBottom());
                    view.layout(left, top2 - iMax2, right, bottom - iMax2);
                    return;
                }
            }
            if (alignItems != 4) {
                return;
            }
        }
        if (this.mFlexContainer.getFlexWrap() != 2) {
            view.layout(left, top2 + flexItem.getMarginTop(), right, bottom + flexItem.getMarginTop());
        } else {
            view.layout(left, top2 - flexItem.getMarginBottom(), right, bottom - flexItem.getMarginBottom());
        }
    }

    public void layoutSingleChildVertical(View view, FlexLine flexLine, boolean isRtl, int left, int top2, int right, int bottom) {
        FlexItem flexItem = (FlexItem) view.getLayoutParams();
        int alignItems = this.mFlexContainer.getAlignItems();
        if (flexItem.getAlignSelf() != -1) {
            alignItems = flexItem.getAlignSelf();
        }
        int i2 = flexLine.mCrossSize;
        if (alignItems != 0) {
            if (alignItems == 1) {
                if (isRtl) {
                    view.layout((left - i2) + view.getMeasuredWidth() + flexItem.getMarginLeft(), top2, (right - i2) + view.getMeasuredWidth() + flexItem.getMarginLeft(), bottom);
                    return;
                } else {
                    view.layout(((left + i2) - view.getMeasuredWidth()) - flexItem.getMarginRight(), top2, ((right + i2) - view.getMeasuredWidth()) - flexItem.getMarginRight(), bottom);
                    return;
                }
            }
            if (alignItems == 2) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                int measuredWidth = (((i2 - view.getMeasuredWidth()) + MarginLayoutParamsCompat.getMarginStart(marginLayoutParams)) - MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams)) / 2;
                if (isRtl) {
                    view.layout(left - measuredWidth, top2, right - measuredWidth, bottom);
                    return;
                } else {
                    view.layout(left + measuredWidth, top2, right + measuredWidth, bottom);
                    return;
                }
            }
            if (alignItems != 3 && alignItems != 4) {
                return;
            }
        }
        if (isRtl) {
            view.layout(left - flexItem.getMarginRight(), top2, right - flexItem.getMarginRight(), bottom);
        } else {
            view.layout(left + flexItem.getMarginLeft(), top2, right + flexItem.getMarginLeft(), bottom);
        }
    }

    @VisibleForTesting
    public long makeCombinedLong(int widthMeasureSpec, int heightMeasureSpec) {
        return (widthMeasureSpec & 4294967295L) | (heightMeasureSpec << 32);
    }

    public void stretchViews() {
        stretchViews(0);
    }

    public void calculateHorizontalFlexLines(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec, int needsCalcAmount, int fromIndex, @Nullable List<FlexLine> existingLines) {
        calculateFlexLines(result, widthMeasureSpec, heightMeasureSpec, needsCalcAmount, fromIndex, -1, existingLines);
    }

    public void calculateVerticalFlexLines(FlexLinesResult result, int widthMeasureSpec, int heightMeasureSpec, int needsCalcAmount, int fromIndex, @Nullable List<FlexLine> existingLines) {
        calculateFlexLines(result, heightMeasureSpec, widthMeasureSpec, needsCalcAmount, fromIndex, -1, existingLines);
    }

    public void determineMainSize(int widthMeasureSpec, int heightMeasureSpec, int fromIndex) {
        int size;
        int paddingLeft;
        int paddingRight;
        ensureChildrenFrozen(this.mFlexContainer.getFlexItemCount());
        if (fromIndex >= this.mFlexContainer.getFlexItemCount()) {
            return;
        }
        int flexDirection = this.mFlexContainer.getFlexDirection();
        int flexDirection2 = this.mFlexContainer.getFlexDirection();
        if (flexDirection2 == 0 || flexDirection2 == 1) {
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            size = View.MeasureSpec.getSize(widthMeasureSpec);
            int largestMainSize = this.mFlexContainer.getLargestMainSize();
            if (mode != 1073741824) {
                size = Math.min(largestMainSize, size);
            }
            paddingLeft = this.mFlexContainer.getPaddingLeft();
            paddingRight = this.mFlexContainer.getPaddingRight();
        } else {
            if (flexDirection2 != 2 && flexDirection2 != 3) {
                throw new IllegalArgumentException("Invalid flex direction: " + flexDirection);
            }
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (mode2 != 1073741824) {
                size = this.mFlexContainer.getLargestMainSize();
            }
            paddingLeft = this.mFlexContainer.getPaddingTop();
            paddingRight = this.mFlexContainer.getPaddingBottom();
        }
        int i2 = paddingLeft + paddingRight;
        int[] iArr = this.mIndexToFlexLine;
        List<FlexLine> flexLinesInternal = this.mFlexContainer.getFlexLinesInternal();
        int size2 = flexLinesInternal.size();
        for (int i3 = iArr != null ? iArr[fromIndex] : 0; i3 < size2; i3++) {
            FlexLine flexLine = flexLinesInternal.get(i3);
            int i4 = flexLine.mMainSize;
            if (i4 < size && flexLine.mAnyItemsHaveFlexGrow) {
                expandFlexItems(widthMeasureSpec, heightMeasureSpec, flexLine, size, i2, false);
            } else if (i4 > size && flexLine.mAnyItemsHaveFlexShrink) {
                shrinkFlexItems(widthMeasureSpec, heightMeasureSpec, flexLine, size, i2, false);
            }
        }
    }

    public void stretchViews(int fromIndex) {
        View reorderedFlexItemAt;
        if (fromIndex >= this.mFlexContainer.getFlexItemCount()) {
            return;
        }
        int flexDirection = this.mFlexContainer.getFlexDirection();
        if (this.mFlexContainer.getAlignItems() != 4) {
            for (FlexLine flexLine : this.mFlexContainer.getFlexLinesInternal()) {
                for (Integer num : flexLine.mIndicesAlignSelfStretch) {
                    View reorderedFlexItemAt2 = this.mFlexContainer.getReorderedFlexItemAt(num.intValue());
                    if (flexDirection == 0 || flexDirection == 1) {
                        stretchViewVertically(reorderedFlexItemAt2, flexLine.mCrossSize, num.intValue());
                    } else {
                        if (flexDirection != 2 && flexDirection != 3) {
                            throw new IllegalArgumentException("Invalid flex direction: " + flexDirection);
                        }
                        stretchViewHorizontally(reorderedFlexItemAt2, flexLine.mCrossSize, num.intValue());
                    }
                }
            }
            return;
        }
        int[] iArr = this.mIndexToFlexLine;
        List<FlexLine> flexLinesInternal = this.mFlexContainer.getFlexLinesInternal();
        int size = flexLinesInternal.size();
        for (int i2 = iArr != null ? iArr[fromIndex] : 0; i2 < size; i2++) {
            FlexLine flexLine2 = flexLinesInternal.get(i2);
            int i3 = flexLine2.mItemCount;
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = flexLine2.mFirstIndex + i4;
                if (i4 < this.mFlexContainer.getFlexItemCount() && (reorderedFlexItemAt = this.mFlexContainer.getReorderedFlexItemAt(i5)) != null && reorderedFlexItemAt.getVisibility() != 8) {
                    FlexItem flexItem = (FlexItem) reorderedFlexItemAt.getLayoutParams();
                    if (flexItem.getAlignSelf() == -1 || flexItem.getAlignSelf() == 4) {
                        if (flexDirection == 0 || flexDirection == 1) {
                            stretchViewVertically(reorderedFlexItemAt, flexLine2.mCrossSize, i5);
                        } else {
                            if (flexDirection != 2 && flexDirection != 3) {
                                throw new IllegalArgumentException("Invalid flex direction: " + flexDirection);
                            }
                            stretchViewHorizontally(reorderedFlexItemAt, flexLine2.mCrossSize, i5);
                        }
                    }
                }
            }
        }
    }

    public int[] createReorderedIndices(SparseIntArray orderCache) {
        int flexItemCount = this.mFlexContainer.getFlexItemCount();
        return sortOrdersIntoReorderedIndices(flexItemCount, createOrders(flexItemCount), orderCache);
    }
}
