package com.shawnlin.numberpicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.CharPool;
import com.google.android.material.timepicker.TimeModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes6.dex */
public class NumberPicker extends LinearLayout {
    public static final int ASCENDING = 0;
    public static final int CENTER = 1;
    private static final int DEFAULT_DIVIDER_COLOR = -16777216;
    private static final float DEFAULT_FADING_EDGE_STRENGTH = 0.9f;
    private static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0f;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int DEFAULT_MAX_FLING_VELOCITY_COEFFICIENT = 8;
    private static final int DEFAULT_MAX_HEIGHT = 180;
    private static final int DEFAULT_MAX_VALUE = 100;
    private static final int DEFAULT_MIN_VALUE = 1;
    private static final int DEFAULT_MIN_WIDTH = 64;
    private static final int DEFAULT_TEXT_ALIGN = 1;
    private static final int DEFAULT_TEXT_COLOR = -16777216;
    private static final float DEFAULT_TEXT_SIZE = 25.0f;
    private static final int DEFAULT_WHEEL_ITEM_COUNT = 3;
    public static final int DESCENDING = 1;
    public static final int HORIZONTAL = 0;
    public static final int LEFT = 2;
    public static final int RIGHT = 0;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    public static final int SIDE_LINES = 0;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    public static final int UNDERLINE = 1;
    private static final int UNSCALED_DEFAULT_DIVIDER_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_DIVIDER_THICKNESS = 2;
    public static final int VERTICAL = 1;
    private boolean mAccessibilityDescriptionEnabled;
    private final Scroller mAdjustScroller;
    private int mBottomDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private Context mContext;
    private int mCurrentScrollOffset;
    private String[] mDisplayedValues;
    private int mDividerColor;
    private int mDividerDistance;
    private Drawable mDividerDrawable;
    private int mDividerLength;
    private int mDividerThickness;
    private int mDividerType;
    private boolean mFadingEdgeEnabled;
    private float mFadingEdgeStrength;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private boolean mHideWheelUntilFocused;
    private int mInitialScrollOffset;
    private int mItemSpacing;
    private float mLastDownEventX;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventX;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLeftDividerLeft;
    private float mLineSpacingMultiplier;
    private long mLongPressUpdateInterval;
    private int mMaxFlingVelocityCoefficient;
    private int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private int mMinHeight;
    private int mMinValue;
    private int mMinWidth;
    private int mMinimumFlingVelocity;
    private NumberFormat mNumberFormatter;
    private View.OnClickListener mOnClickListener;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private int mOrder;
    private int mOrientation;
    private int mPreviousScrollerX;
    private int mPreviousScrollerY;
    private int mRealWheelItemCount;
    private int mRightDividerRight;
    private int mScrollState;
    private boolean mScrollerEnabled;
    private final EditText mSelectedText;
    private int mSelectedTextAlign;
    private float mSelectedTextCenterX;
    private float mSelectedTextCenterY;
    private int mSelectedTextColor;
    private float mSelectedTextSize;
    private boolean mSelectedTextStrikeThru;
    private boolean mSelectedTextUnderline;
    private Typeface mSelectedTypeface;
    private int mSelectorElementSize;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private int mSelectorTextGapWidth;
    private final Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private int mTextAlign;
    private int mTextColor;
    private float mTextSize;
    private boolean mTextStrikeThru;
    private boolean mTextUnderline;
    private int mTopDividerTop;
    private int mTouchSlop;
    private Typeface mTypeface;
    private int mValue;
    private VelocityTracker mVelocityTracker;
    private ViewConfiguration mViewConfiguration;
    private int mWheelItemCount;
    private int mWheelMiddleItemIndex;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred;
    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311, CharPool.DASHED};

    @Retention(RetentionPolicy.SOURCE)
    public @interface Align {
    }

    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        public ChangeCurrentByOneFromLongPressCommand() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStep(boolean increment) {
            this.mIncrement = increment;
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker numberPicker = NumberPicker.this;
            numberPicker.postDelayed(this, numberPicker.mLongPressUpdateInterval);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerType {
    }

    public interface Formatter {
        String format(int value);
    }

    public class InputTextFilter extends NumberKeyListener {
        public InputTextFilter() {
        }

        @Override // android.text.method.NumberKeyListener, android.text.InputFilter
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (NumberPicker.this.mSetSelectionCommand != null) {
                NumberPicker.this.mSetSelectionCommand.cancel();
            }
            if (NumberPicker.this.mDisplayedValues == null) {
                CharSequence charSequenceFilter = super.filter(source, start, end, dest, dstart, dend);
                if (charSequenceFilter == null) {
                    charSequenceFilter = source.subSequence(start, end);
                }
                String str = String.valueOf(dest.subSequence(0, dstart)) + ((Object) charSequenceFilter) + ((Object) dest.subSequence(dend, dest.length()));
                return "".equals(str) ? str : (NumberPicker.this.getSelectedPos(str) > NumberPicker.this.mMaxValue || str.length() > String.valueOf(NumberPicker.this.mMaxValue).length()) ? "" : charSequenceFilter;
            }
            String strValueOf = String.valueOf(source.subSequence(start, end));
            if (TextUtils.isEmpty(strValueOf)) {
                return "";
            }
            String str2 = String.valueOf(dest.subSequence(0, dstart)) + ((Object) strValueOf) + ((Object) dest.subSequence(dend, dest.length()));
            String lowerCase = String.valueOf(str2).toLowerCase();
            for (String str3 : NumberPicker.this.mDisplayedValues) {
                if (str3.toLowerCase().startsWith(lowerCase)) {
                    NumberPicker.this.postSetSelectionCommand(str2.length(), str3.length());
                    return str3.subSequence(dstart, str3.length());
                }
            }
            return "";
        }

        @Override // android.text.method.NumberKeyListener
        public char[] getAcceptedChars() {
            return NumberPicker.DIGIT_CHARACTERS;
        }

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 1;
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScrollState {
        }

        void onScrollStateChange(NumberPicker view, int scrollState);
    }

    public interface OnValueChangeListener {
        void onValueChange(NumberPicker picker, int oldVal, int newVal);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Order {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public static class SetSelectionCommand implements Runnable {
        private final EditText mInputText;
        private boolean mPosted;
        private int mSelectionEnd;
        private int mSelectionStart;

        public SetSelectionCommand(EditText inputText) {
            this.mInputText = inputText;
        }

        public void cancel() {
            if (this.mPosted) {
                this.mInputText.removeCallbacks(this);
                this.mPosted = false;
            }
        }

        public void post(int selectionStart, int selectionEnd) {
            this.mSelectionStart = selectionStart;
            this.mSelectionEnd = selectionEnd;
            if (this.mPosted) {
                return;
            }
            this.mInputText.post(this);
            this.mPosted = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mPosted = false;
            this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    public static class TwoDigitFormatter implements Formatter {
        java.util.Formatter mFmt;
        char mZeroDigit;
        final StringBuilder mBuilder = new StringBuilder();
        final Object[] mArgs = new Object[1];

        public TwoDigitFormatter() {
            init(Locale.getDefault());
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }

        private static char getZeroDigit(Locale locale) {
            return new DecimalFormatSymbols(locale).getZeroDigit();
        }

        private void init(Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mZeroDigit = getZeroDigit(locale);
        }

        @Override // com.shawnlin.numberpicker.NumberPicker.Formatter
        public String format(int value) {
            Locale locale = Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(locale)) {
                init(locale);
            }
            this.mArgs[0] = Integer.valueOf(value);
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
            this.mFmt.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, this.mArgs);
            return this.mFmt.toString();
        }
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeValueByOne(boolean increment) {
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        smoothScroll(increment, 1);
    }

    private int computeScrollExtent(boolean isHorizontalMode) {
        return isHorizontalMode ? getWidth() : getHeight();
    }

    private int computeScrollOffset(boolean isHorizontalMode) {
        if (isHorizontalMode) {
            return this.mCurrentScrollOffset;
        }
        return 0;
    }

    private int computeScrollRange(boolean isHorizontalMode) {
        if (isHorizontalMode) {
            return ((this.mMaxValue - this.mMinValue) + 1) * this.mSelectorElementSize;
        }
        return 0;
    }

    private void decrementSelectorIndices(int[] selectorIndices) {
        for (int length = selectorIndices.length - 1; length > 0; length--) {
            selectorIndices[length] = selectorIndices[length - 1];
        }
        int i2 = selectorIndices[1] - 1;
        if (this.mWrapSelectorWheel && i2 < this.mMinValue) {
            i2 = this.mMaxValue;
        }
        selectorIndices[0] = i2;
        ensureCachedScrollSelectorValue(i2);
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private void drawHorizontalDividers(Canvas canvas) {
        int bottom;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = this.mDividerType;
        if (i7 != 0) {
            if (i7 != 1) {
                return;
            }
            int i8 = this.mDividerLength;
            if (i8 <= 0 || i8 > (i6 = this.mMaxWidth)) {
                i4 = this.mLeftDividerLeft;
                i5 = this.mRightDividerRight;
            } else {
                i4 = (i6 - i8) / 2;
                i5 = i8 + i4;
            }
            int i9 = this.mBottomDividerBottom;
            this.mDividerDrawable.setBounds(i4, i9 - this.mDividerThickness, i5, i9);
            this.mDividerDrawable.draw(canvas);
            return;
        }
        int i10 = this.mDividerLength;
        if (i10 <= 0 || i10 > (i3 = this.mMaxHeight)) {
            bottom = getBottom();
            i2 = 0;
        } else {
            i2 = (i3 - i10) / 2;
            bottom = i10 + i2;
        }
        int i11 = this.mLeftDividerLeft;
        this.mDividerDrawable.setBounds(i11, i2, this.mDividerThickness + i11, bottom);
        this.mDividerDrawable.draw(canvas);
        int i12 = this.mRightDividerRight;
        this.mDividerDrawable.setBounds(i12 - this.mDividerThickness, i2, i12, bottom);
        this.mDividerDrawable.draw(canvas);
    }

    private void drawText(String text, float x2, float y2, Paint paint, Canvas canvas) {
        if (!text.contains("\n")) {
            canvas.drawText(text, x2, y2, paint);
            return;
        }
        String[] strArrSplit = text.split("\n");
        float fAbs = Math.abs(paint.descent() + paint.ascent()) * this.mLineSpacingMultiplier;
        float length = y2 - (((strArrSplit.length - 1) * fAbs) / 2.0f);
        for (String str : strArrSplit) {
            canvas.drawText(str, x2, length, paint);
            length += fAbs;
        }
    }

    private void drawVerticalDividers(Canvas canvas) {
        int right;
        int i2;
        int i3;
        int i4 = this.mDividerLength;
        if (i4 <= 0 || i4 > (i3 = this.mMaxWidth)) {
            right = getRight();
            i2 = 0;
        } else {
            i2 = (i3 - i4) / 2;
            right = i4 + i2;
        }
        int i5 = this.mDividerType;
        if (i5 != 0) {
            if (i5 != 1) {
                return;
            }
            int i6 = this.mBottomDividerBottom;
            this.mDividerDrawable.setBounds(i2, i6 - this.mDividerThickness, right, i6);
            this.mDividerDrawable.draw(canvas);
            return;
        }
        int i7 = this.mTopDividerTop;
        this.mDividerDrawable.setBounds(i2, i7, right, this.mDividerThickness + i7);
        this.mDividerDrawable.draw(canvas);
        int i8 = this.mBottomDividerBottom;
        this.mDividerDrawable.setBounds(i2, i8 - this.mDividerThickness, right, i8);
        this.mDividerDrawable.draw(canvas);
    }

    private void ensureCachedScrollSelectorValue(int selectorIndex) {
        String number;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(selectorIndex) != null) {
            return;
        }
        int i2 = this.mMinValue;
        if (selectorIndex < i2 || selectorIndex > this.mMaxValue) {
            number = "";
        } else {
            String[] strArr = this.mDisplayedValues;
            if (strArr != null) {
                int i3 = selectorIndex - i2;
                if (i3 >= strArr.length) {
                    sparseArray.remove(selectorIndex);
                    return;
                }
                number = strArr[i3];
            } else {
                number = formatNumber(selectorIndex);
            }
        }
        sparseArray.put(selectorIndex, number);
    }

    private void ensureScrollWheelAdjusted() {
        int i2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i2 == 0) {
            return;
        }
        int iAbs = Math.abs(i2);
        int i3 = this.mSelectorElementSize;
        if (iAbs > i3 / 2) {
            if (i2 > 0) {
                i3 = -i3;
            }
            i2 += i3;
        }
        int i4 = i2;
        if (isHorizontalMode()) {
            this.mPreviousScrollerX = 0;
            this.mAdjustScroller.startScroll(0, 0, i4, 0, 800);
        } else {
            this.mPreviousScrollerY = 0;
            this.mAdjustScroller.startScroll(0, 0, 0, i4, 800);
        }
        invalidate();
    }

    private void fling(int velocity) {
        if (isHorizontalMode()) {
            this.mPreviousScrollerX = 0;
            if (velocity > 0) {
                this.mFlingScroller.fling(0, 0, velocity, 0, 0, Integer.MAX_VALUE, 0, 0);
            } else {
                this.mFlingScroller.fling(Integer.MAX_VALUE, 0, velocity, 0, 0, Integer.MAX_VALUE, 0, 0);
            }
        } else {
            this.mPreviousScrollerY = 0;
            if (velocity > 0) {
                this.mFlingScroller.fling(0, 0, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
            } else {
                this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
            }
        }
        invalidate();
    }

    private String formatNumber(int value) {
        Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(value) : formatNumberWithLocale(value);
    }

    private String formatNumberWithLocale(int value) {
        return this.mNumberFormatter.format(value);
    }

    private float getFadingEdgeStrength(boolean isHorizontalMode) {
        if (isHorizontalMode && this.mFadingEdgeEnabled) {
            return this.mFadingEdgeStrength;
        }
        return 0.0f;
    }

    private float getMaxTextSize() {
        return Math.max(this.mTextSize, this.mSelectedTextSize);
    }

    private float getPaintCenterY(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0.0f;
        }
        return Math.abs(fontMetrics.top + fontMetrics.bottom) / 2.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectedPos(String value) {
        try {
            if (this.mDisplayedValues == null) {
                return Integer.parseInt(value);
            }
            for (int i2 = 0; i2 < this.mDisplayedValues.length; i2++) {
                value = value.toLowerCase();
                if (this.mDisplayedValues[i2].toLowerCase().startsWith(value)) {
                    return this.mMinValue + i2;
                }
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException unused) {
            return this.mMinValue;
        }
    }

    private int[] getSelectorIndices() {
        return this.mSelectorIndices;
    }

    public static Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    private int getWrappedSelectorIndex(int selectorIndex) {
        int i2 = this.mMaxValue;
        if (selectorIndex > i2) {
            int i3 = this.mMinValue;
            return (i3 + ((selectorIndex - i2) % (i2 - i3))) - 1;
        }
        int i4 = this.mMinValue;
        return selectorIndex < i4 ? (i2 - ((i4 - selectorIndex) % (i2 - i4))) + 1 : selectorIndex;
    }

    private void incrementSelectorIndices(int[] selectorIndices) {
        int i2 = 0;
        while (i2 < selectorIndices.length - 1) {
            int i3 = i2 + 1;
            selectorIndices[i2] = selectorIndices[i3];
            i2 = i3;
        }
        int i4 = selectorIndices[selectorIndices.length - 2] + 1;
        if (this.mWrapSelectorWheel && i4 > this.mMaxValue) {
            i4 = this.mMinValue;
        }
        selectorIndices[selectorIndices.length - 1] = i4;
        ensureCachedScrollSelectorValue(i4);
    }

    private void initializeFadingEdges() {
        if (isHorizontalMode()) {
            setHorizontalFadingEdgeEnabled(true);
            setVerticalFadingEdgeEnabled(false);
            setFadingEdgeLength(((getRight() - getLeft()) - ((int) this.mTextSize)) / 2);
        } else {
            setHorizontalFadingEdgeEnabled(false);
            setVerticalFadingEdgeEnabled(true);
            setFadingEdgeLength(((getBottom() - getTop()) - ((int) this.mTextSize)) / 2);
        }
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] selectorIndices = getSelectorIndices();
        int length = (int) (((selectorIndices.length - 1) * this.mTextSize) + this.mSelectedTextSize);
        float length2 = selectorIndices.length;
        if (isHorizontalMode()) {
            this.mSelectorTextGapWidth = (int) (((getRight() - getLeft()) - length) / length2);
            this.mSelectorElementSize = ((int) getMaxTextSize()) + this.mSelectorTextGapWidth;
            this.mInitialScrollOffset = (int) (this.mSelectedTextCenterX - (r0 * this.mWheelMiddleItemIndex));
        } else {
            this.mSelectorTextGapHeight = (int) (((getBottom() - getTop()) - length) / length2);
            this.mSelectorElementSize = ((int) getMaxTextSize()) + this.mSelectorTextGapHeight;
            this.mInitialScrollOffset = (int) (this.mSelectedTextCenterY - (r0 * this.mWheelMiddleItemIndex));
        }
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] selectorIndices = getSelectorIndices();
        int value = getValue();
        for (int i2 = 0; i2 < selectorIndices.length; i2++) {
            int wrappedSelectorIndex = (i2 - this.mWheelMiddleItemIndex) + value;
            if (this.mWrapSelectorWheel) {
                wrappedSelectorIndex = getWrappedSelectorIndex(wrappedSelectorIndex);
            }
            selectorIndices[i2] = wrappedSelectorIndex;
            ensureCachedScrollSelectorValue(wrappedSelectorIndex);
        }
    }

    private boolean isWrappingAllowed() {
        return this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length - 1;
    }

    private int makeMeasureSpec(int measureSpec, int maxSize) {
        if (maxSize == -1) {
            return measureSpec;
        }
        int size = View.MeasureSpec.getSize(measureSpec);
        int mode = View.MeasureSpec.getMode(measureSpec);
        if (mode == Integer.MIN_VALUE) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), 1073741824);
        }
        if (mode == 0) {
            return View.MeasureSpec.makeMeasureSpec(maxSize, 1073741824);
        }
        if (mode == 1073741824) {
            return measureSpec;
        }
        throw new IllegalArgumentException("Unknown measure mode: " + mode);
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        if (isHorizontalMode()) {
            int finalX = scroller.getFinalX() - scroller.getCurrX();
            int i2 = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalX) % this.mSelectorElementSize);
            if (i2 != 0) {
                int iAbs = Math.abs(i2);
                int i3 = this.mSelectorElementSize;
                if (iAbs > i3 / 2) {
                    i2 = i2 > 0 ? i2 - i3 : i2 + i3;
                }
                scrollBy(finalX + i2, 0);
                return true;
            }
        } else {
            int finalY = scroller.getFinalY() - scroller.getCurrY();
            int i4 = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementSize);
            if (i4 != 0) {
                int iAbs2 = Math.abs(i4);
                int i5 = this.mSelectorElementSize;
                if (iAbs2 > i5 / 2) {
                    i4 = i4 > 0 ? i4 - i5 : i4 + i5;
                }
                scrollBy(0, finalY + i4);
                return true;
            }
        }
        return false;
    }

    private void notifyChange(int previous, int current) {
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, previous, current);
        }
    }

    private void onScrollStateChange(int scrollState) {
        if (this.mScrollState == scrollState) {
            return;
        }
        this.mScrollState = scrollState;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, scrollState);
        }
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            ensureScrollWheelAdjusted();
            updateInputTextView();
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean increment, long delayMillis) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(increment);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, delayMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSetSelectionCommand(int selectionStart, int selectionEnd) {
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand(this.mSelectedText);
        } else {
            setSelectionCommand.post(selectionStart, selectionEnd);
        }
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }

    private void removeAllCallbacks() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand != null) {
            setSelectionCommand.cancel();
        }
    }

    private void removeChangeCurrentByOneFromLongPress() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size2 = View.MeasureSpec.getSize(measureSpec);
        if (mode != Integer.MIN_VALUE) {
            if (mode == 1073741824) {
                size = size2;
            }
        } else if (size2 < size) {
            size = 16777216 | size2;
        }
        return size | ((-16777216) & childMeasuredState);
    }

    private int resolveSizeAndStateRespectingMinSize(int minSize, int measuredSize, int measureSpec) {
        return minSize != -1 ? resolveSizeAndState(Math.max(minSize, measuredSize), measureSpec, 0) : measuredSize;
    }

    private void setValueInternal(int current, boolean notifyChange) {
        if (this.mValue == current) {
            return;
        }
        int wrappedSelectorIndex = this.mWrapSelectorWheel ? getWrappedSelectorIndex(current) : Math.min(Math.max(current, this.mMinValue), this.mMaxValue);
        int i2 = this.mValue;
        this.mValue = wrappedSelectorIndex;
        if (this.mScrollState != 2) {
            updateInputTextView();
        }
        if (notifyChange) {
            notifyChange(i2, wrappedSelectorIndex);
        }
        initializeSelectorWheelIndices();
        updateAccessibilityDescription();
        invalidate();
    }

    private void setWidthAndHeight() {
        if (isHorizontalMode()) {
            this.mMinHeight = -1;
            this.mMaxHeight = (int) dpToPx(64.0f);
            this.mMinWidth = (int) dpToPx(180.0f);
            this.mMaxWidth = -1;
            return;
        }
        this.mMinHeight = -1;
        this.mMaxHeight = (int) dpToPx(180.0f);
        this.mMinWidth = (int) dpToPx(64.0f);
        this.mMaxWidth = -1;
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(2, sp, getResources().getDisplayMetrics());
    }

    private Formatter stringToFormatter(final String formatter) {
        if (TextUtils.isEmpty(formatter)) {
            return null;
        }
        return new Formatter() { // from class: com.shawnlin.numberpicker.NumberPicker.1
            @Override // com.shawnlin.numberpicker.NumberPicker.Formatter
            public String format(int i2) {
                return String.format(Locale.getDefault(), formatter, Integer.valueOf(i2));
            }
        };
    }

    private void tryComputeMaxWidth() {
        int i2;
        if (this.mComputeMaxWidth) {
            this.mSelectorWheelPaint.setTextSize(getMaxTextSize());
            String[] strArr = this.mDisplayedValues;
            int i3 = 0;
            if (strArr == null) {
                float f2 = 0.0f;
                for (int i4 = 0; i4 <= 9; i4++) {
                    float fMeasureText = this.mSelectorWheelPaint.measureText(formatNumber(i4));
                    if (fMeasureText > f2) {
                        f2 = fMeasureText;
                    }
                }
                for (int i5 = this.mMaxValue; i5 > 0; i5 /= 10) {
                    i3++;
                }
                i2 = (int) (i3 * f2);
            } else {
                int length = strArr.length;
                int i6 = 0;
                while (i3 < length) {
                    float fMeasureText2 = this.mSelectorWheelPaint.measureText(strArr[i3]);
                    if (fMeasureText2 > i6) {
                        i6 = (int) fMeasureText2;
                    }
                    i3++;
                }
                i2 = i6;
            }
            int paddingLeft = i2 + this.mSelectedText.getPaddingLeft() + this.mSelectedText.getPaddingRight();
            if (this.mMaxWidth != paddingLeft) {
                this.mMaxWidth = Math.max(paddingLeft, this.mMinWidth);
                invalidate();
            }
        }
    }

    private void updateAccessibilityDescription() {
        if (this.mAccessibilityDescriptionEnabled) {
            setContentDescription(String.valueOf(getValue()));
        }
    }

    private void updateInputTextView() {
        String[] strArr = this.mDisplayedValues;
        String number = strArr == null ? formatNumber(this.mValue) : strArr[this.mValue - this.mMinValue];
        if (TextUtils.isEmpty(number) || number.equals(this.mSelectedText.getText().toString())) {
            return;
        }
        this.mSelectedText.setText(number);
    }

    private void updateWrapSelectorWheel() {
        this.mWrapSelectorWheel = isWrappingAllowed() && this.mWrapSelectorWheelPreferred;
    }

    @Override // android.view.View
    public int computeHorizontalScrollExtent() {
        return computeScrollExtent(isHorizontalMode());
    }

    @Override // android.view.View
    public int computeHorizontalScrollOffset() {
        return computeScrollOffset(isHorizontalMode());
    }

    @Override // android.view.View
    public int computeHorizontalScrollRange() {
        return computeScrollRange(isHorizontalMode());
    }

    @Override // android.view.View
    public void computeScroll() {
        if (isScrollerEnabled()) {
            Scroller scroller = this.mFlingScroller;
            if (scroller.isFinished()) {
                scroller = this.mAdjustScroller;
                if (scroller.isFinished()) {
                    return;
                }
            }
            scroller.computeScrollOffset();
            if (isHorizontalMode()) {
                int currX = scroller.getCurrX();
                if (this.mPreviousScrollerX == 0) {
                    this.mPreviousScrollerX = scroller.getStartX();
                }
                scrollBy(currX - this.mPreviousScrollerX, 0);
                this.mPreviousScrollerX = currX;
            } else {
                int currY = scroller.getCurrY();
                if (this.mPreviousScrollerY == 0) {
                    this.mPreviousScrollerY = scroller.getStartY();
                }
                scrollBy(0, currY - this.mPreviousScrollerY);
                this.mPreviousScrollerY = currY;
            }
            if (scroller.isFinished()) {
                onScrollerFinished(scroller);
            } else {
                postInvalidate();
            }
        }
    }

    @Override // android.view.View
    public int computeVerticalScrollExtent() {
        return computeScrollExtent(isHorizontalMode());
    }

    @Override // android.view.View
    public int computeVerticalScrollOffset() {
        return computeScrollOffset(!isHorizontalMode());
    }

    @Override // android.view.View
    public int computeVerticalScrollRange() {
        return computeScrollRange(!isHorizontalMode());
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 19 || keyCode == 20) {
            int action = event.getAction();
            if (action == 0) {
                if (!this.mWrapSelectorWheel) {
                    if (keyCode == 20) {
                    }
                }
                requestFocus();
                this.mLastHandledDownDpadKeyCode = keyCode;
                removeAllCallbacks();
                if (this.mFlingScroller.isFinished()) {
                    changeValueByOne(keyCode == 20);
                }
                return true;
            }
            if (action == 1 && this.mLastHandledDownDpadKeyCode == keyCode) {
                this.mLastHandledDownDpadKeyCode = -1;
                return true;
            }
        } else if (keyCode == 23 || keyCode == 66) {
            removeAllCallbacks();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction() & 255;
        if (action == 1 || action == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTouchEvent(event);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent event) {
        int action = event.getAction() & 255;
        if (action == 1 || action == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(event);
    }

    @Override // android.view.ViewGroup, android.view.View
    @CallSuper
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mDividerDrawable;
        if (drawable != null && drawable.isStateful() && this.mDividerDrawable.setState(getDrawableState())) {
            invalidateDrawable(this.mDividerDrawable);
        }
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        return getFadingEdgeStrength(!isHorizontalMode());
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public float getDividerDistance() {
        return pxToDp(this.mDividerDistance);
    }

    public float getDividerThickness() {
        return pxToDp(this.mDividerThickness);
    }

    public Formatter getFormatter() {
        return this.mFormatter;
    }

    @Override // android.view.View
    public float getLeftFadingEdgeStrength() {
        return getFadingEdgeStrength(isHorizontalMode());
    }

    public float getLineSpacingMultiplier() {
        return this.mLineSpacingMultiplier;
    }

    public int getMaxFlingVelocityCoefficient() {
        return this.mMaxFlingVelocityCoefficient;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getOrder() {
        return this.mOrder;
    }

    @Override // android.widget.LinearLayout
    public int getOrientation() {
        return this.mOrientation;
    }

    @Override // android.view.View
    public float getRightFadingEdgeStrength() {
        return getFadingEdgeStrength(isHorizontalMode());
    }

    public int getSelectedTextAlign() {
        return this.mSelectedTextAlign;
    }

    public int getSelectedTextColor() {
        return this.mSelectedTextColor;
    }

    public float getSelectedTextSize() {
        return this.mSelectedTextSize;
    }

    public boolean getSelectedTextStrikeThru() {
        return this.mSelectedTextStrikeThru;
    }

    public boolean getSelectedTextUnderline() {
        return this.mSelectedTextUnderline;
    }

    public int getTextAlign() {
        return this.mTextAlign;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public float getTextSize() {
        return spToPx(this.mTextSize);
    }

    public boolean getTextStrikeThru() {
        return this.mTextStrikeThru;
    }

    public boolean getTextUnderline() {
        return this.mTextUnderline;
    }

    @Override // android.view.View
    public float getTopFadingEdgeStrength() {
        return getFadingEdgeStrength(!isHorizontalMode());
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getWheelItemCount() {
        return this.mWheelItemCount;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public boolean isAccessibilityDescriptionEnabled() {
        return this.mAccessibilityDescriptionEnabled;
    }

    public boolean isAscendingOrder() {
        return getOrder() == 0;
    }

    public boolean isFadingEdgeEnabled() {
        return this.mFadingEdgeEnabled;
    }

    public boolean isHorizontalMode() {
        return getOrientation() == 0;
    }

    public boolean isScrollerEnabled() {
        return this.mScrollerEnabled;
    }

    @Override // android.view.ViewGroup, android.view.View
    @CallSuper
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mDividerDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mNumberFormatter = NumberFormat.getInstance();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllCallbacks();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onDraw(Canvas canvas) {
        float right;
        float baseline;
        int i2;
        int i3;
        canvas.save();
        boolean z2 = !this.mHideWheelUntilFocused || hasFocus();
        if (isHorizontalMode()) {
            right = this.mCurrentScrollOffset;
            baseline = this.mSelectedText.getBaseline() + this.mSelectedText.getTop();
            if (this.mRealWheelItemCount < 3) {
                canvas.clipRect(this.mLeftDividerLeft, 0, this.mRightDividerRight, getBottom());
            }
        } else {
            right = (getRight() - getLeft()) / 2.0f;
            baseline = this.mCurrentScrollOffset;
            if (this.mRealWheelItemCount < 3) {
                canvas.clipRect(0, this.mTopDividerTop, getRight(), this.mBottomDividerBottom);
            }
        }
        int[] selectorIndices = getSelectorIndices();
        int i4 = 0;
        while (i4 < selectorIndices.length) {
            if (i4 == this.mWheelMiddleItemIndex) {
                this.mSelectorWheelPaint.setTextAlign(Paint.Align.values()[this.mSelectedTextAlign]);
                this.mSelectorWheelPaint.setTextSize(this.mSelectedTextSize);
                this.mSelectorWheelPaint.setColor(this.mSelectedTextColor);
                this.mSelectorWheelPaint.setStrikeThruText(this.mSelectedTextStrikeThru);
                this.mSelectorWheelPaint.setUnderlineText(this.mSelectedTextUnderline);
                this.mSelectorWheelPaint.setTypeface(this.mSelectedTypeface);
            } else {
                this.mSelectorWheelPaint.setTextAlign(Paint.Align.values()[this.mTextAlign]);
                this.mSelectorWheelPaint.setTextSize(this.mTextSize);
                this.mSelectorWheelPaint.setColor(this.mTextColor);
                this.mSelectorWheelPaint.setStrikeThruText(this.mTextStrikeThru);
                this.mSelectorWheelPaint.setUnderlineText(this.mTextUnderline);
                this.mSelectorWheelPaint.setTypeface(this.mTypeface);
            }
            String str = this.mSelectorIndexToStringCache.get(selectorIndices[isAscendingOrder() ? i4 : (selectorIndices.length - i4) - 1]);
            if (str != null) {
                if ((z2 && i4 != this.mWheelMiddleItemIndex) || (i4 == this.mWheelMiddleItemIndex && this.mSelectedText.getVisibility() != 0)) {
                    float paintCenterY = !isHorizontalMode() ? getPaintCenterY(this.mSelectorWheelPaint.getFontMetrics()) + baseline : baseline;
                    if (i4 == this.mWheelMiddleItemIndex || this.mItemSpacing == 0) {
                        i2 = 0;
                        i3 = 0;
                    } else if (isHorizontalMode()) {
                        i2 = i4 > this.mWheelMiddleItemIndex ? this.mItemSpacing : -this.mItemSpacing;
                        i3 = 0;
                    } else {
                        i3 = i4 > this.mWheelMiddleItemIndex ? this.mItemSpacing : -this.mItemSpacing;
                        i2 = 0;
                    }
                    drawText(str, right + i2, paintCenterY + i3, this.mSelectorWheelPaint, canvas);
                }
                if (isHorizontalMode()) {
                    right += this.mSelectorElementSize;
                } else {
                    baseline += this.mSelectorElementSize;
                }
            }
            i4++;
        }
        canvas.restore();
        if (!z2 || this.mDividerDrawable == null) {
            return;
        }
        if (isHorizontalMode()) {
            drawHorizontalDividers(canvas);
        } else {
            drawVerticalDividers(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(NumberPicker.class.getName());
        event.setScrollable(isScrollerEnabled());
        int i2 = this.mMinValue;
        int i3 = this.mValue + i2;
        int i4 = this.mSelectorElementSize;
        int i5 = i3 * i4;
        int i6 = (this.mMaxValue - i2) * i4;
        if (isHorizontalMode()) {
            event.setScrollX(i5);
            event.setMaxScrollX(i6);
        } else {
            event.setScrollY(i5);
            event.setMaxScrollY(i6);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isEnabled() || (event.getAction() & 255) != 0) {
            return false;
        }
        removeAllCallbacks();
        getParent().requestDisallowInterceptTouchEvent(true);
        if (isHorizontalMode()) {
            float x2 = event.getX();
            this.mLastDownEventX = x2;
            this.mLastDownOrMoveEventX = x2;
            if (!this.mFlingScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
                onScrollerFinished(this.mFlingScroller);
                onScrollStateChange(0);
            } else if (this.mAdjustScroller.isFinished()) {
                float f2 = this.mLastDownEventX;
                int i2 = this.mLeftDividerLeft;
                if (f2 >= i2 && f2 <= this.mRightDividerRight) {
                    View.OnClickListener onClickListener = this.mOnClickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(this);
                    }
                } else if (f2 < i2) {
                    postChangeCurrentByOneFromLongPress(false);
                } else if (f2 > this.mRightDividerRight) {
                    postChangeCurrentByOneFromLongPress(true);
                }
            } else {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
                onScrollerFinished(this.mAdjustScroller);
            }
        } else {
            float y2 = event.getY();
            this.mLastDownEventY = y2;
            this.mLastDownOrMoveEventY = y2;
            if (!this.mFlingScroller.isFinished()) {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
                onScrollStateChange(0);
            } else if (this.mAdjustScroller.isFinished()) {
                float f3 = this.mLastDownEventY;
                int i3 = this.mTopDividerTop;
                if (f3 >= i3 && f3 <= this.mBottomDividerBottom) {
                    View.OnClickListener onClickListener2 = this.mOnClickListener;
                    if (onClickListener2 != null) {
                        onClickListener2.onClick(this);
                    }
                } else if (f3 < i3) {
                    postChangeCurrentByOneFromLongPress(false);
                } else if (f3 > this.mBottomDividerBottom) {
                    postChangeCurrentByOneFromLongPress(true);
                }
            } else {
                this.mFlingScroller.forceFinished(true);
                this.mAdjustScroller.forceFinished(true);
            }
        }
        return true;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth2 = this.mSelectedText.getMeasuredWidth();
        int measuredHeight2 = this.mSelectedText.getMeasuredHeight();
        int i2 = (measuredWidth - measuredWidth2) / 2;
        int i3 = (measuredHeight - measuredHeight2) / 2;
        this.mSelectedText.layout(i2, i3, measuredWidth2 + i2, measuredHeight2 + i3);
        this.mSelectedTextCenterX = (this.mSelectedText.getX() + (this.mSelectedText.getMeasuredWidth() / 2.0f)) - 2.0f;
        this.mSelectedTextCenterY = (this.mSelectedText.getY() + (this.mSelectedText.getMeasuredHeight() / 2.0f)) - 5.0f;
        if (changed) {
            initializeSelectorWheel();
            initializeFadingEdges();
            int i4 = (this.mDividerThickness * 2) + this.mDividerDistance;
            if (!isHorizontalMode()) {
                int height = ((getHeight() - this.mDividerDistance) / 2) - this.mDividerThickness;
                this.mTopDividerTop = height;
                this.mBottomDividerBottom = height + i4;
            } else {
                int width = ((getWidth() - this.mDividerDistance) / 2) - this.mDividerThickness;
                this.mLeftDividerLeft = width;
                this.mRightDividerRight = width + i4;
                this.mBottomDividerBottom = getHeight();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(makeMeasureSpec(widthMeasureSpec, this.mMaxWidth), makeMeasureSpec(heightMeasureSpec, this.mMaxHeight));
        setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), widthMeasureSpec), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), heightMeasureSpec));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !isScrollerEnabled()) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        int action = event.getAction() & 255;
        if (action == 1) {
            removeChangeCurrentByOneFromLongPress();
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
            if (isHorizontalMode()) {
                int xVelocity = (int) velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > this.mMinimumFlingVelocity) {
                    fling(xVelocity);
                    onScrollStateChange(2);
                } else {
                    int x2 = (int) event.getX();
                    if (((int) Math.abs(x2 - this.mLastDownEventX)) <= this.mTouchSlop) {
                        int i2 = (x2 / this.mSelectorElementSize) - this.mWheelMiddleItemIndex;
                        if (i2 > 0) {
                            changeValueByOne(true);
                        } else if (i2 < 0) {
                            changeValueByOne(false);
                        } else {
                            ensureScrollWheelAdjusted();
                        }
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                    onScrollStateChange(0);
                }
            } else {
                int yVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    int y2 = (int) event.getY();
                    if (((int) Math.abs(y2 - this.mLastDownEventY)) <= this.mTouchSlop) {
                        int i3 = (y2 / this.mSelectorElementSize) - this.mWheelMiddleItemIndex;
                        if (i3 > 0) {
                            changeValueByOne(true);
                        } else if (i3 < 0) {
                            changeValueByOne(false);
                        } else {
                            ensureScrollWheelAdjusted();
                        }
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                    onScrollStateChange(0);
                }
            }
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        } else if (action == 2) {
            if (isHorizontalMode()) {
                float x3 = event.getX();
                if (this.mScrollState == 1) {
                    scrollBy((int) (x3 - this.mLastDownOrMoveEventX), 0);
                    invalidate();
                } else if (((int) Math.abs(x3 - this.mLastDownEventX)) > this.mTouchSlop) {
                    removeAllCallbacks();
                    onScrollStateChange(1);
                }
                this.mLastDownOrMoveEventX = x3;
            } else {
                float y3 = event.getY();
                if (this.mScrollState == 1) {
                    scrollBy(0, (int) (y3 - this.mLastDownOrMoveEventY));
                    invalidate();
                } else if (((int) Math.abs(y3 - this.mLastDownEventY)) > this.mTouchSlop) {
                    removeAllCallbacks();
                    onScrollStateChange(1);
                }
                this.mLastDownOrMoveEventY = y3;
            }
        }
        return true;
    }

    @Override // android.view.View
    public void scrollBy(int x2, int y2) {
        int i2;
        if (isScrollerEnabled()) {
            int[] selectorIndices = getSelectorIndices();
            int i3 = this.mCurrentScrollOffset;
            int maxTextSize = (int) getMaxTextSize();
            if (isHorizontalMode()) {
                if (isAscendingOrder()) {
                    boolean z2 = this.mWrapSelectorWheel;
                    if (!z2 && x2 > 0 && selectorIndices[this.mWheelMiddleItemIndex] <= this.mMinValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    } else if (!z2 && x2 < 0 && selectorIndices[this.mWheelMiddleItemIndex] >= this.mMaxValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    }
                } else {
                    boolean z3 = this.mWrapSelectorWheel;
                    if (!z3 && x2 > 0 && selectorIndices[this.mWheelMiddleItemIndex] >= this.mMaxValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    } else if (!z3 && x2 < 0 && selectorIndices[this.mWheelMiddleItemIndex] <= this.mMinValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    }
                }
                this.mCurrentScrollOffset += x2;
            } else {
                if (isAscendingOrder()) {
                    boolean z4 = this.mWrapSelectorWheel;
                    if (!z4 && y2 > 0 && selectorIndices[this.mWheelMiddleItemIndex] <= this.mMinValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    } else if (!z4 && y2 < 0 && selectorIndices[this.mWheelMiddleItemIndex] >= this.mMaxValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    }
                } else {
                    boolean z5 = this.mWrapSelectorWheel;
                    if (!z5 && y2 > 0 && selectorIndices[this.mWheelMiddleItemIndex] >= this.mMaxValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    } else if (!z5 && y2 < 0 && selectorIndices[this.mWheelMiddleItemIndex] <= this.mMinValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        return;
                    }
                }
                this.mCurrentScrollOffset += y2;
            }
            while (true) {
                int i4 = this.mCurrentScrollOffset;
                if (i4 - this.mInitialScrollOffset <= maxTextSize) {
                    break;
                }
                this.mCurrentScrollOffset = i4 - this.mSelectorElementSize;
                if (isAscendingOrder()) {
                    decrementSelectorIndices(selectorIndices);
                } else {
                    incrementSelectorIndices(selectorIndices);
                }
                setValueInternal(selectorIndices[this.mWheelMiddleItemIndex], true);
                if (!this.mWrapSelectorWheel && selectorIndices[this.mWheelMiddleItemIndex] < this.mMinValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            while (true) {
                i2 = this.mCurrentScrollOffset;
                if (i2 - this.mInitialScrollOffset >= (-maxTextSize)) {
                    break;
                }
                this.mCurrentScrollOffset = i2 + this.mSelectorElementSize;
                if (isAscendingOrder()) {
                    incrementSelectorIndices(selectorIndices);
                } else {
                    decrementSelectorIndices(selectorIndices);
                }
                setValueInternal(selectorIndices[this.mWheelMiddleItemIndex], true);
                if (!this.mWrapSelectorWheel && selectorIndices[this.mWheelMiddleItemIndex] > this.mMaxValue) {
                    this.mCurrentScrollOffset = this.mInitialScrollOffset;
                }
            }
            if (i3 != i2) {
                if (isHorizontalMode()) {
                    onScrollChanged(this.mCurrentScrollOffset, 0, i3, 0);
                } else {
                    onScrollChanged(0, this.mCurrentScrollOffset, 0, i3);
                }
            }
        }
    }

    public void setAccessibilityDescriptionEnabled(boolean enabled) {
        this.mAccessibilityDescriptionEnabled = enabled;
    }

    public void setDisplayedValues(String[] displayedValues) {
        if (this.mDisplayedValues == displayedValues) {
            return;
        }
        this.mDisplayedValues = displayedValues;
        if (displayedValues != null) {
            this.mSelectedText.setRawInputType(655360);
        } else {
            this.mSelectedText.setRawInputType(2);
        }
        updateInputTextView();
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    public void setDividerColor(@ColorInt int color) {
        this.mDividerColor = color;
        this.mDividerDrawable = new ColorDrawable(color);
    }

    public void setDividerColorResource(@ColorRes int colorId) {
        setDividerColor(ContextCompat.getColor(this.mContext, colorId));
    }

    public void setDividerDistance(int distance) {
        this.mDividerDistance = distance;
    }

    public void setDividerDistanceResource(@DimenRes int dimenId) {
        setDividerDistance(getResources().getDimensionPixelSize(dimenId));
    }

    public void setDividerThickness(int thickness) {
        this.mDividerThickness = thickness;
    }

    public void setDividerThicknessResource(@DimenRes int dimenId) {
        setDividerThickness(getResources().getDimensionPixelSize(dimenId));
    }

    public void setDividerType(int dividerType) {
        this.mDividerType = dividerType;
        invalidate();
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.mSelectedText.setEnabled(enabled);
    }

    public void setFadingEdgeEnabled(boolean fadingEdgeEnabled) {
        this.mFadingEdgeEnabled = fadingEdgeEnabled;
    }

    public void setFadingEdgeStrength(float strength) {
        this.mFadingEdgeStrength = strength;
    }

    public void setFormatter(Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        initializeSelectorWheelIndices();
        updateInputTextView();
    }

    public void setItemSpacing(int itemSpacing) {
        this.mItemSpacing = itemSpacing;
    }

    public void setLineSpacingMultiplier(float multiplier) {
        this.mLineSpacingMultiplier = multiplier;
    }

    public void setMaxFlingVelocityCoefficient(int coefficient) {
        this.mMaxFlingVelocityCoefficient = coefficient;
        this.mMaximumFlingVelocity = this.mViewConfiguration.getScaledMaximumFlingVelocity() / this.mMaxFlingVelocityCoefficient;
    }

    public void setMaxValue(int maxValue) {
        if (maxValue < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = maxValue;
        if (maxValue < this.mValue) {
            this.mValue = maxValue;
        }
        updateWrapSelectorWheel();
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
        if (minValue > this.mValue) {
            this.mValue = minValue;
        }
        updateWrapSelectorWheel();
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnLongPressUpdateInterval(long intervalMillis) {
        this.mLongPressUpdateInterval = intervalMillis;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        this.mOnValueChangeListener = onValueChangedListener;
    }

    public void setOrder(int order) {
        this.mOrder = order;
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
        setWidthAndHeight();
        requestLayout();
    }

    public void setScrollerEnabled(boolean scrollerEnabled) {
        this.mScrollerEnabled = scrollerEnabled;
    }

    public void setSelectedTextAlign(int align) {
        this.mSelectedTextAlign = align;
    }

    public void setSelectedTextColor(@ColorInt int color) {
        this.mSelectedTextColor = color;
        this.mSelectedText.setTextColor(color);
    }

    public void setSelectedTextColorResource(@ColorRes int colorId) {
        setSelectedTextColor(ContextCompat.getColor(this.mContext, colorId));
    }

    public void setSelectedTextSize(float textSize) {
        this.mSelectedTextSize = textSize;
        this.mSelectedText.setTextSize(pxToSp(textSize));
    }

    public void setSelectedTextStrikeThru(boolean strikeThruText) {
        this.mSelectedTextStrikeThru = strikeThruText;
    }

    public void setSelectedTextUnderline(boolean underlineText) {
        this.mSelectedTextUnderline = underlineText;
    }

    public void setSelectedTypeface(Typeface typeface) {
        this.mSelectedTypeface = typeface;
        if (typeface != null) {
            this.mSelectorWheelPaint.setTypeface(typeface);
            return;
        }
        Typeface typeface2 = this.mTypeface;
        if (typeface2 != null) {
            this.mSelectorWheelPaint.setTypeface(typeface2);
        } else {
            this.mSelectorWheelPaint.setTypeface(Typeface.MONOSPACE);
        }
    }

    public void setTextAlign(int align) {
        this.mTextAlign = align;
    }

    public void setTextColor(@ColorInt int color) {
        this.mTextColor = color;
        this.mSelectorWheelPaint.setColor(color);
    }

    public void setTextColorResource(@ColorRes int colorId) {
        setTextColor(ContextCompat.getColor(this.mContext, colorId));
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        this.mSelectorWheelPaint.setTextSize(textSize);
    }

    public void setTextStrikeThru(boolean strikeThruText) {
        this.mTextStrikeThru = strikeThruText;
    }

    public void setTextUnderline(boolean underlineText) {
        this.mTextUnderline = underlineText;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
        if (typeface == null) {
            this.mSelectedText.setTypeface(Typeface.MONOSPACE);
        } else {
            this.mSelectedText.setTypeface(typeface);
            setSelectedTypeface(this.mSelectedTypeface);
        }
    }

    public void setValue(int value) {
        setValueInternal(value, false);
    }

    public void setWheelItemCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Wheel item count must be >= 1");
        }
        this.mRealWheelItemCount = count;
        int iMax = Math.max(count, 3);
        this.mWheelItemCount = iMax;
        this.mWheelMiddleItemIndex = iMax / 2;
        this.mSelectorIndices = new int[iMax];
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        this.mWrapSelectorWheelPreferred = wrapSelectorWheel;
        updateWrapSelectorWheel();
    }

    public void smoothScroll(boolean increment, int steps) {
        int i2 = (increment ? -this.mSelectorElementSize : this.mSelectorElementSize) * steps;
        if (isHorizontalMode()) {
            this.mPreviousScrollerX = 0;
            this.mFlingScroller.startScroll(0, 0, i2, 0, 300);
        } else {
            this.mPreviousScrollerY = 0;
            this.mFlingScroller.startScroll(0, 0, 0, i2, 300);
        }
        invalidate();
    }

    public void smoothScrollToPosition(int position) {
        int i2 = getSelectorIndices()[this.mWheelMiddleItemIndex];
        if (i2 == position) {
            return;
        }
        smoothScroll(position > i2, Math.abs(position - i2));
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public float getFadingEdgeStrength() {
        return this.mFadingEdgeStrength;
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mSelectedTextAlign = 1;
        this.mSelectedTextColor = -16777216;
        this.mSelectedTextSize = DEFAULT_TEXT_SIZE;
        this.mTextAlign = 1;
        this.mTextColor = -16777216;
        this.mTextSize = DEFAULT_TEXT_SIZE;
        this.mMinValue = 1;
        this.mMaxValue = 100;
        this.mLongPressUpdateInterval = 300L;
        this.mSelectorIndexToStringCache = new SparseArray<>();
        this.mWheelItemCount = 3;
        this.mRealWheelItemCount = 3;
        this.mWheelMiddleItemIndex = 3 / 2;
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mWrapSelectorWheelPreferred = true;
        this.mDividerColor = -16777216;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        this.mFadingEdgeEnabled = true;
        this.mFadingEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;
        this.mScrollerEnabled = true;
        this.mLineSpacingMultiplier = 1.0f;
        this.mMaxFlingVelocityCoefficient = 8;
        this.mAccessibilityDescriptionEnabled = true;
        this.mItemSpacing = 0;
        this.mContext = context;
        this.mNumberFormatter = NumberFormat.getInstance();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.NumberPicker, defStyle, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.NumberPicker_np_divider);
        if (drawable != null) {
            drawable.setCallback(this);
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            this.mDividerDrawable = drawable;
        } else {
            int color = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_np_dividerColor, this.mDividerColor);
            this.mDividerColor = color;
            setDividerColor(color);
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int iApplyDimension = (int) TypedValue.applyDimension(1, 48.0f, displayMetrics);
        int iApplyDimension2 = (int) TypedValue.applyDimension(1, 2.0f, displayMetrics);
        this.mDividerDistance = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_dividerDistance, iApplyDimension);
        this.mDividerLength = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_dividerLength, 0);
        this.mDividerThickness = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_dividerThickness, iApplyDimension2);
        this.mDividerType = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_dividerType, 0);
        this.mOrder = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_order, 0);
        this.mOrientation = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_orientation, 1);
        float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_width, -1);
        float dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_height, -1);
        setWidthAndHeight();
        this.mComputeMaxWidth = true;
        this.mValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_value, this.mValue);
        this.mMaxValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_max, this.mMaxValue);
        this.mMinValue = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_min, this.mMinValue);
        this.mSelectedTextAlign = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_selectedTextAlign, this.mSelectedTextAlign);
        this.mSelectedTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_np_selectedTextColor, this.mSelectedTextColor);
        this.mSelectedTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.NumberPicker_np_selectedTextSize, spToPx(this.mSelectedTextSize));
        this.mSelectedTextStrikeThru = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_selectedTextStrikeThru, this.mSelectedTextStrikeThru);
        this.mSelectedTextUnderline = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_selectedTextUnderline, this.mSelectedTextUnderline);
        this.mSelectedTypeface = Typeface.create(typedArrayObtainStyledAttributes.getString(R.styleable.NumberPicker_np_selectedTypeface), 0);
        this.mTextAlign = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_textAlign, this.mTextAlign);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.NumberPicker_np_textColor, this.mTextColor);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.NumberPicker_np_textSize, spToPx(this.mTextSize));
        this.mTextStrikeThru = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_textStrikeThru, this.mTextStrikeThru);
        this.mTextUnderline = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_textUnderline, this.mTextUnderline);
        this.mTypeface = Typeface.create(typedArrayObtainStyledAttributes.getString(R.styleable.NumberPicker_np_typeface), 0);
        this.mFormatter = stringToFormatter(typedArrayObtainStyledAttributes.getString(R.styleable.NumberPicker_np_formatter));
        this.mFadingEdgeEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_fadingEdgeEnabled, this.mFadingEdgeEnabled);
        this.mFadingEdgeStrength = typedArrayObtainStyledAttributes.getFloat(R.styleable.NumberPicker_np_fadingEdgeStrength, this.mFadingEdgeStrength);
        this.mScrollerEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_scrollerEnabled, this.mScrollerEnabled);
        this.mWheelItemCount = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_wheelItemCount, this.mWheelItemCount);
        this.mLineSpacingMultiplier = typedArrayObtainStyledAttributes.getFloat(R.styleable.NumberPicker_np_lineSpacingMultiplier, this.mLineSpacingMultiplier);
        this.mMaxFlingVelocityCoefficient = typedArrayObtainStyledAttributes.getInt(R.styleable.NumberPicker_np_maxFlingVelocityCoefficient, this.mMaxFlingVelocityCoefficient);
        this.mHideWheelUntilFocused = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_hideWheelUntilFocused, false);
        this.mAccessibilityDescriptionEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_accessibilityDescriptionEnabled, true);
        this.mItemSpacing = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.NumberPicker_np_itemSpacing, 0);
        setWillNotDraw(false);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.number_picker_material, (ViewGroup) this, true);
        EditText editText = (EditText) findViewById(R.id.np__numberpicker_input);
        this.mSelectedText = editText;
        editText.setEnabled(false);
        editText.setFocusable(false);
        editText.setImeOptions(1);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        this.mSelectorWheelPaint = paint;
        setSelectedTextColor(this.mSelectedTextColor);
        setTextColor(this.mTextColor);
        setTextSize(this.mTextSize);
        setSelectedTextSize(this.mSelectedTextSize);
        setTypeface(this.mTypeface);
        setSelectedTypeface(this.mSelectedTypeface);
        setFormatter(this.mFormatter);
        updateInputTextView();
        setValue(this.mValue);
        setMaxValue(this.mMaxValue);
        setMinValue(this.mMinValue);
        setWheelItemCount(this.mWheelItemCount);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.NumberPicker_np_wrapSelectorWheel, this.mWrapSelectorWheel);
        this.mWrapSelectorWheel = z2;
        setWrapSelectorWheel(z2);
        if (dimensionPixelSize != -1.0f && dimensionPixelSize2 != -1.0f) {
            setScaleX(dimensionPixelSize / this.mMinWidth);
            setScaleY(dimensionPixelSize2 / this.mMaxHeight);
        } else if (dimensionPixelSize != -1.0f) {
            float f2 = dimensionPixelSize / this.mMinWidth;
            setScaleX(f2);
            setScaleY(f2);
        } else if (dimensionPixelSize2 != -1.0f) {
            float f3 = dimensionPixelSize2 / this.mMaxHeight;
            setScaleX(f3);
            setScaleY(f3);
        }
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mViewConfiguration = viewConfiguration;
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumFlingVelocity = this.mViewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = this.mViewConfiguration.getScaledMaximumFlingVelocity() / this.mMaxFlingVelocityCoefficient;
        this.mFlingScroller = new Scroller(context, null, true);
        this.mAdjustScroller = new Scroller(context, new DecelerateInterpolator(2.5f));
        int i2 = Build.VERSION.SDK_INT;
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (i2 >= 26 && getFocusable() == 16) {
            setFocusable(1);
            setFocusableInTouchMode(true);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setSelectedTextSize(@DimenRes int dimenId) {
        setSelectedTextSize(getResources().getDimension(dimenId));
    }

    public void setTextSize(@DimenRes int dimenId) {
        setTextSize(getResources().getDimension(dimenId));
    }

    public void setFormatter(final String formatter) {
        if (TextUtils.isEmpty(formatter)) {
            return;
        }
        setFormatter(stringToFormatter(formatter));
    }

    public void setTypeface(String string, int style) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        setTypeface(Typeface.create(string, style));
    }

    private void postChangeCurrentByOneFromLongPress(boolean increment) {
        postChangeCurrentByOneFromLongPress(increment, ViewConfiguration.getLongPressTimeout());
    }

    public void setSelectedTypeface(String string, int style) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        setSelectedTypeface(Typeface.create(string, style));
    }

    public void setFormatter(@StringRes int stringId) {
        setFormatter(getResources().getString(stringId));
    }

    public void setTypeface(String string) {
        setTypeface(string, 0);
    }

    public void setSelectedTypeface(String string) {
        setSelectedTypeface(string, 0);
    }

    public void setTypeface(@StringRes int stringId, int style) {
        setTypeface(getResources().getString(stringId), style);
    }

    public void setSelectedTypeface(@StringRes int stringId, int style) {
        setSelectedTypeface(getResources().getString(stringId), style);
    }

    public void setTypeface(@StringRes int stringId) {
        setTypeface(stringId, 0);
    }

    public void setSelectedTypeface(@StringRes int stringId) {
        setSelectedTypeface(stringId, 0);
    }
}
