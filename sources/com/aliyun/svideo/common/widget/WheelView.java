package com.aliyun.svideo.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;
import androidx.core.widget.ScrollerCompat;
import cn.hutool.core.text.StrPool;
import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public class WheelView extends View {
    private static final int DEFAULT_DIVIDER_COLOR = -695533;
    private static final int DEFAULT_DIVIDER_HEIGHT = 2;
    private static final int DEFAULT_DIVIDER_MARGIN_HORIZONTAL = 0;
    private static final int DEFAULT_INTERVAL_REVISE_DURATION = 300;
    private static final int DEFAULT_ITEM_PADDING_DP_H = 5;
    private static final int DEFAULT_ITEM_PADDING_DP_V = 2;
    private static final int DEFAULT_MARGIN_END_OF_HINT_DP = 8;
    private static final int DEFAULT_MARGIN_START_OF_HINT_DP = 8;
    private static final int DEFAULT_MAX_SCROLL_BY_INDEX_DURATION = 600;
    private static final int DEFAULT_MIN_SCROLL_BY_INDEX_DURATION = 300;
    private static final int DEFAULT_SHOW_COUNT = 3;
    private static final int DEFAULT_TEXT_COLOR_NORMAL = -13421773;
    private static final int DEFAULT_TEXT_COLOR_SELECTED = -695533;
    private static final int DEFAULT_TEXT_SIZE_HINT_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_NORMAL_SP = 14;
    private static final int DEFAULT_TEXT_SIZE_SELECTED_SP = 16;
    private static final int HANDLER_INTERVAL_REFRESH = 30;
    private static final int HANDLER_WHAT_LISTENER_VALUE_CHANGED = 2;
    private static final int HANDLER_WHAT_REFRESH = 1;
    private float currY;
    private float dividerY0;
    private float dividerY1;
    private float downY;
    private float downYGlobal;
    private String mAlterHint;
    private CharSequence[] mAlterTextArrayWithMeasureHint;
    private CharSequence[] mAlterTextArrayWithoutMeasureHint;
    private int mCurrDrawFirstItemIndex;
    private int mCurrDrawFirstItemY;
    private int mCurrDrawGlobalY;
    private boolean mCurrentItemIndexEffect;
    private String[] mDisplayedValues;
    private int mDividerColor;
    private int mDividerHeight;
    private int mDividerIndex0;
    private int mDividerIndex1;
    private int mDividerMarginL;
    private int mDividerMarginR;
    private String mEmptyItemHint;
    private boolean mFlagMayPress;
    private float mFriction;
    private Handler mHandler;
    private Handler mHandlerLayout;
    private HandlerThread mHandlerThread;
    private boolean mHasInit;
    private String mHintText;
    private int mItemHeight;
    private int mItemPaddingHorizental;
    private int mItemPaddingVertical;
    private int mMarginEndOfHint;
    private int mMarginStartOfHint;
    private int mMaxHeightOfDisplayedValues;
    private int mMaxShowIndex;
    private int mMaxValue;
    private int mMaxWidthOfAlterArrayWithMeasureHint;
    private int mMaxWidthOfAlterArrayWithoutMeasureHint;
    private int mMaxWidthOfDisplayedValues;
    private int mMinShowIndex;
    private int mMinValue;
    private int mMiniVerlocityFling;
    private int mNotWrapLimitYBottom;
    private int mNotWrapLimitYTop;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private OnValueChangeListenerRelativeToRaw mOnValueChangeListenerRaw;
    private Paint mPaintDivider;
    private Paint mPaintHint;
    private Paint mPaintText;
    private boolean mPendingWrapToLinear;
    private int mPrivPickedIndex;
    private int mScaledTouchSlop;
    private int mScrollState;
    private ScrollerCompat mScroller;
    private int mShowCount;
    private boolean mShowDivider;
    private int mSpecModeH;
    private int mSpecModeW;
    private int mTextColorHint;
    private int mTextColorNormal;
    private int mTextColorSelected;
    private int mTextSizeHint;
    private float mTextSizeHintCenterYOffset;
    private int mTextSizeNormal;
    private float mTextSizeNormalCenterYOffset;
    private int mTextSizeSelected;
    private float mTextSizeSelectedCenterYOffset;
    private VelocityTracker mVelocityTracker;
    private float mViewCenterX;
    private int mViewHeight;
    private int mViewWidth;
    private int mWidthOfAlterHint;
    private int mWidthOfHintText;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelCheck;

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(WheelView wheelView, int i2);
    }

    public interface OnValueChangeListener {
        void onValueChange(WheelView wheelView, int i2, int i3);
    }

    public interface OnValueChangeListenerRelativeToRaw {
        void onValueChangeRelativeToRaw(WheelView wheelView, int i2, int i3, String[] strArr);
    }

    public WheelView(Context context) {
        this(context, null);
    }

    private void click(MotionEvent motionEvent) {
        float y2 = motionEvent.getY();
        for (int i2 = 0; i2 < this.mShowCount; i2++) {
            int i3 = this.mItemHeight;
            if (i3 * i2 <= y2 && y2 < i3 * (i2 + 1)) {
                clickItem(i2);
                return;
            }
        }
    }

    private void clickItem(int i2) {
        int i3;
        if (i2 < 0 || i2 >= (i3 = this.mShowCount)) {
            return;
        }
        scrollByIndexSmoothly(i2 - (i3 / 2));
    }

    private String[] convertCharSequenceArrayToStringArray(CharSequence[] charSequenceArr) {
        if (charSequenceArr == null) {
            return null;
        }
        String[] strArr = new String[charSequenceArr.length];
        for (int i2 = 0; i2 < charSequenceArr.length; i2++) {
            strArr[i2] = charSequenceArr[i2].toString();
        }
        return strArr;
    }

    private void correctPositionByDefaultValue(int i2, boolean z2) {
        int i3 = i2 - ((this.mShowCount - 1) / 2);
        this.mCurrDrawFirstItemIndex = i3;
        int indexByRawIndex = getIndexByRawIndex(i3, getOneRecycleSize(), z2);
        this.mCurrDrawFirstItemIndex = indexByRawIndex;
        int i4 = this.mItemHeight;
        if (i4 == 0) {
            this.mCurrentItemIndexEffect = true;
        } else {
            this.mCurrDrawGlobalY = indexByRawIndex * i4;
        }
    }

    private int dp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void drawContent(Canvas canvas) {
        float evaluateSize;
        float evaluateSize2;
        float f2;
        int evaluateColor;
        float f3 = 0.0f;
        int i2 = 0;
        while (i2 < this.mShowCount + 1) {
            float f4 = this.mCurrDrawFirstItemY + (this.mItemHeight * i2);
            int indexByRawIndex = getIndexByRawIndex(this.mCurrDrawFirstItemIndex + i2, getOneRecycleSize(), this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
            int i3 = this.mShowCount;
            if (i2 == i3 / 2) {
                f2 = (this.mCurrDrawFirstItemY + r0) / this.mItemHeight;
                evaluateColor = getEvaluateColor(f2, this.mTextColorNormal, this.mTextColorSelected);
                evaluateSize = getEvaluateSize(f2, this.mTextSizeNormal, this.mTextSizeSelected);
                evaluateSize2 = getEvaluateSize(f2, this.mTextSizeNormalCenterYOffset, this.mTextSizeSelectedCenterYOffset);
            } else if (i2 == (i3 / 2) + 1) {
                float f5 = 1.0f - f3;
                int evaluateColor2 = getEvaluateColor(f5, this.mTextColorNormal, this.mTextColorSelected);
                float evaluateSize3 = getEvaluateSize(f5, this.mTextSizeNormal, this.mTextSizeSelected);
                float evaluateSize4 = getEvaluateSize(f5, this.mTextSizeNormalCenterYOffset, this.mTextSizeSelectedCenterYOffset);
                f2 = f3;
                evaluateColor = evaluateColor2;
                evaluateSize = evaluateSize3;
                evaluateSize2 = evaluateSize4;
            } else {
                int i4 = this.mTextColorNormal;
                evaluateSize = this.mTextSizeNormal;
                evaluateSize2 = this.mTextSizeNormalCenterYOffset;
                f2 = f3;
                evaluateColor = i4;
            }
            this.mPaintText.setColor(evaluateColor);
            this.mPaintText.setTextSize(evaluateSize);
            if (indexByRawIndex >= 0 && indexByRawIndex < getOneRecycleSize()) {
                canvas.drawText(this.mDisplayedValues[indexByRawIndex + this.mMinShowIndex].toString(), this.mViewCenterX, f4 + (this.mItemHeight / 2) + evaluateSize2, this.mPaintText);
            } else if (!TextUtils.isEmpty(this.mEmptyItemHint)) {
                canvas.drawText(this.mEmptyItemHint, this.mViewCenterX, f4 + (this.mItemHeight / 2) + evaluateSize2, this.mPaintText);
            }
            i2++;
            f3 = f2;
        }
    }

    private void drawHint(Canvas canvas) {
        if (TextUtils.isEmpty(this.mHintText)) {
            return;
        }
        canvas.drawText(this.mHintText, this.mViewCenterX + ((this.mMaxWidthOfDisplayedValues + this.mWidthOfHintText) / 2) + this.mMarginStartOfHint, ((this.dividerY0 + this.dividerY1) / 2.0f) + this.mTextSizeHintCenterYOffset, this.mPaintHint);
    }

    private void drawLine(Canvas canvas) {
        if (this.mShowDivider) {
            canvas.drawLine(getPaddingLeft() + this.mDividerMarginL, this.dividerY0, (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR, this.dividerY0, this.mPaintDivider);
            canvas.drawLine(getPaddingLeft() + this.mDividerMarginL, this.dividerY1, (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR, this.dividerY1, this.mPaintDivider);
        }
    }

    private int getEvaluateColor(float f2, int i2, int i3) {
        int i4 = (i2 & (-16777216)) >>> 24;
        int i5 = (i2 & 16711680) >>> 16;
        int i6 = (i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8;
        return ((int) (((i2 & 255) >>> 0) + ((((i3 & 255) >>> 0) - r9) * f2))) | (((int) (i4 + (((((-16777216) & i3) >>> 24) - i4) * f2))) << 24) | (((int) (i5 + ((((16711680 & i3) >>> 16) - i5) * f2))) << 16) | (((int) (i6 + ((((65280 & i3) >>> 8) - i6) * f2))) << 8);
    }

    private float getEvaluateSize(float f2, float f3, float f4) {
        return f3 + ((f4 - f3) * f2);
    }

    private int getIndexByRawIndex(int i2, int i3, boolean z2) {
        if (i3 <= 0) {
            return 0;
        }
        if (!z2) {
            return i2;
        }
        int i4 = i2 % i3;
        return i4 < 0 ? i4 + i3 : i4;
    }

    private int getMaxWidthOfTextArray(CharSequence[] charSequenceArr, Paint paint) {
        if (charSequenceArr == null) {
            return 0;
        }
        int iMax = 0;
        for (CharSequence charSequence : charSequenceArr) {
            if (charSequence != null) {
                iMax = Math.max(getTextWidth(charSequence, paint), iMax);
            }
        }
        return iMax;
    }

    private Message getMsg(int i2) {
        return getMsg(i2, 0, 0, null);
    }

    private float getTextCenterYOffset(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0.0f;
        }
        return Math.abs(fontMetrics.top + fontMetrics.bottom) / 2.0f;
    }

    private int getTextWidth(CharSequence charSequence, Paint paint) {
        if (TextUtils.isEmpty(charSequence)) {
            return 0;
        }
        return (int) (paint.measureText(charSequence.toString()) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWillPickIndexByGlobalY(int i2) {
        int i3 = this.mItemHeight;
        boolean z2 = false;
        if (i3 == 0) {
            return 0;
        }
        int i4 = (i2 / i3) + (this.mShowCount / 2);
        int oneRecycleSize = getOneRecycleSize();
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            z2 = true;
        }
        int indexByRawIndex = getIndexByRawIndex(i4, oneRecycleSize, z2);
        if (indexByRawIndex >= 0 && indexByRawIndex < getOneRecycleSize()) {
            return indexByRawIndex + this.mMinShowIndex;
        }
        throw new IllegalArgumentException("getWillPickIndexByGlobalY illegal index : " + indexByRawIndex + " getOneRecycleSize() : " + getOneRecycleSize() + " mWrapSelectorWheel : " + this.mWrapSelectorWheel);
    }

    private void inflateDisplayedValuesIfNull() {
        if (this.mDisplayedValues == null) {
            this.mDisplayedValues = new String[]{"0"};
        }
    }

    private void init(Context context) {
        this.mScroller = ScrollerCompat.create(context);
        this.mMiniVerlocityFling = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        this.mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (this.mTextSizeNormal == 0) {
            this.mTextSizeNormal = sp2px(context, 14.0f);
        }
        if (this.mTextSizeSelected == 0) {
            this.mTextSizeSelected = sp2px(context, 16.0f);
        }
        if (this.mTextSizeHint == 0) {
            this.mTextSizeHint = sp2px(context, 14.0f);
        }
        if (this.mMarginStartOfHint == 0) {
            this.mMarginStartOfHint = dp2px(context, 8.0f);
        }
        if (this.mMarginEndOfHint == 0) {
            this.mMarginEndOfHint = dp2px(context, 8.0f);
        }
        this.mPaintDivider.setColor(this.mDividerColor);
        this.mPaintDivider.setAntiAlias(true);
        this.mPaintDivider.setStyle(Paint.Style.STROKE);
        this.mPaintDivider.setStrokeWidth(this.mDividerHeight);
        this.mPaintText.setColor(this.mTextColorNormal);
        this.mPaintText.setAntiAlias(true);
        this.mPaintText.setTextAlign(Paint.Align.CENTER);
        this.mPaintHint.setColor(this.mTextColorHint);
        this.mPaintHint.setAntiAlias(true);
        this.mPaintHint.setTextAlign(Paint.Align.CENTER);
        this.mPaintHint.setTextSize(this.mTextSizeHint);
        int i2 = this.mShowCount;
        if (i2 % 2 == 0) {
            this.mShowCount = i2 + 1;
        }
        if (this.mMinShowIndex == -1 || this.mMaxShowIndex == -1) {
            updateValueForInit();
        }
        initHandler();
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.WheelView);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.WheelView_npv_ShowCount) {
                this.mShowCount = typedArrayObtainStyledAttributes.getInt(index, 3);
            } else if (index == R.styleable.WheelView_npv_DividerColor) {
                this.mDividerColor = typedArrayObtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.WheelView_npv_DividerHeight) {
                this.mDividerHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 2);
            } else if (index == R.styleable.WheelView_npv_DividerMarginLeft) {
                this.mDividerMarginL = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.WheelView_npv_DividerMarginRight) {
                this.mDividerMarginR = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.WheelView_npv_TextArray) {
                this.mDisplayedValues = convertCharSequenceArrayToStringArray(typedArrayObtainStyledAttributes.getTextArray(index));
            } else if (index == R.styleable.WheelView_npv_TextColorNormal) {
                this.mTextColorNormal = typedArrayObtainStyledAttributes.getColor(index, DEFAULT_TEXT_COLOR_NORMAL);
            } else if (index == R.styleable.WheelView_npv_TextColorSelected) {
                this.mTextColorSelected = typedArrayObtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.WheelView_npv_TextColorHint) {
                this.mTextColorHint = typedArrayObtainStyledAttributes.getColor(index, -695533);
            } else if (index == R.styleable.WheelView_npv_TextSizeNormal) {
                this.mTextSizeNormal = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.WheelView_npv_TextSizeSelected) {
                this.mTextSizeSelected = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 16.0f));
            } else if (index == R.styleable.WheelView_npv_TextSizeHint) {
                this.mTextSizeHint = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, sp2px(context, 14.0f));
            } else if (index == R.styleable.WheelView_npv_MinValue) {
                this.mMinShowIndex = typedArrayObtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.WheelView_npv_MaxValue) {
                this.mMaxShowIndex = typedArrayObtainStyledAttributes.getInteger(index, 0);
            } else if (index == R.styleable.WheelView_npv_WrapSelectorWheel) {
                this.mWrapSelectorWheel = typedArrayObtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.WheelView_npv_ShowDivider) {
                this.mShowDivider = typedArrayObtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.WheelView_npv_HintText) {
                this.mHintText = typedArrayObtainStyledAttributes.getString(index);
            } else if (index == R.styleable.WheelView_npv_AlternativeHint) {
                this.mAlterHint = typedArrayObtainStyledAttributes.getString(index);
            } else if (index == R.styleable.WheelView_npv_EmptyItemHint) {
                this.mEmptyItemHint = typedArrayObtainStyledAttributes.getString(index);
            } else if (index == R.styleable.WheelView_npv_MarginStartOfHint) {
                this.mMarginStartOfHint = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.WheelView_npv_MarginEndOfHint) {
                this.mMarginEndOfHint = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 8.0f));
            } else if (index == R.styleable.WheelView_npv_ItemPaddingVertical) {
                this.mItemPaddingVertical = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 2.0f));
            } else if (index == R.styleable.WheelView_npv_ItemPaddingHorizental) {
                this.mItemPaddingHorizental = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, dp2px(context, 5.0f));
            } else if (index == R.styleable.WheelView_npv_AlternativeTextArrayWithMeasureHint) {
                this.mAlterTextArrayWithMeasureHint = typedArrayObtainStyledAttributes.getTextArray(index);
            } else if (index == R.styleable.WheelView_npv_AlternativeTextArrayWithoutMeasureHint) {
                this.mAlterTextArrayWithoutMeasureHint = typedArrayObtainStyledAttributes.getTextArray(index);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void initHandler() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread-For-Refreshing");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper()) { // from class: com.aliyun.svideo.common.widget.WheelView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int willPickIndexByGlobalY;
                int i2;
                Object obj;
                int i3 = message.what;
                int i4 = 0;
                if (i3 != 1) {
                    if (i3 != 2) {
                        return;
                    }
                    WheelView.this.onScrollStateChange(0);
                    if (message.arg1 != message.arg2 && ((obj = message.obj) == null || !(obj instanceof Boolean) || ((Boolean) obj).booleanValue())) {
                        if (WheelView.this.mOnValueChangeListener != null) {
                            OnValueChangeListener onValueChangeListener = WheelView.this.mOnValueChangeListener;
                            WheelView wheelView = WheelView.this;
                            onValueChangeListener.onValueChange(wheelView, message.arg1 + wheelView.mMinValue, message.arg2 + WheelView.this.mMinValue);
                        }
                        if (WheelView.this.mOnValueChangeListenerRaw != null) {
                            OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw = WheelView.this.mOnValueChangeListenerRaw;
                            WheelView wheelView2 = WheelView.this;
                            onValueChangeListenerRelativeToRaw.onValueChangeRelativeToRaw(wheelView2, message.arg1, message.arg2, wheelView2.mDisplayedValues);
                        }
                    }
                    WheelView.this.mPrivPickedIndex = message.arg2;
                    if (WheelView.this.mPendingWrapToLinear) {
                        WheelView.this.mPendingWrapToLinear = false;
                        WheelView.this.internalSetWrapToLinear();
                        return;
                    }
                    return;
                }
                if (!WheelView.this.mScroller.isFinished()) {
                    if (WheelView.this.mScrollState == 0) {
                        WheelView.this.onScrollStateChange(1);
                    }
                    WheelView.this.mHandler.sendMessageDelayed(WheelView.this.getMsg(1, 0, 0, message.obj), 30L);
                    return;
                }
                if (WheelView.this.mCurrDrawFirstItemY != 0) {
                    if (WheelView.this.mScrollState == 0) {
                        WheelView.this.onScrollStateChange(1);
                    }
                    if (WheelView.this.mCurrDrawFirstItemY < (-WheelView.this.mItemHeight) / 2) {
                        i2 = (int) (((WheelView.this.mItemHeight + WheelView.this.mCurrDrawFirstItemY) * 300.0f) / WheelView.this.mItemHeight);
                        WheelView.this.mScroller.startScroll(0, WheelView.this.mCurrDrawGlobalY, 0, WheelView.this.mItemHeight + WheelView.this.mCurrDrawFirstItemY, i2 * 2);
                        WheelView wheelView3 = WheelView.this;
                        willPickIndexByGlobalY = wheelView3.getWillPickIndexByGlobalY(wheelView3.mCurrDrawGlobalY + WheelView.this.mItemHeight + WheelView.this.mCurrDrawFirstItemY);
                    } else {
                        i2 = (int) (((-WheelView.this.mCurrDrawFirstItemY) * 300.0f) / WheelView.this.mItemHeight);
                        WheelView.this.mScroller.startScroll(0, WheelView.this.mCurrDrawGlobalY, 0, WheelView.this.mCurrDrawFirstItemY, i2 * 2);
                        WheelView wheelView4 = WheelView.this;
                        willPickIndexByGlobalY = wheelView4.getWillPickIndexByGlobalY(wheelView4.mCurrDrawGlobalY + WheelView.this.mCurrDrawFirstItemY);
                    }
                    i4 = i2;
                    WheelView.this.postInvalidate();
                } else {
                    WheelView.this.onScrollStateChange(0);
                    WheelView wheelView5 = WheelView.this;
                    willPickIndexByGlobalY = wheelView5.getWillPickIndexByGlobalY(wheelView5.mCurrDrawGlobalY);
                }
                Handler handler = WheelView.this.mHandler;
                WheelView wheelView6 = WheelView.this;
                handler.sendMessageDelayed(wheelView6.getMsg(2, wheelView6.mPrivPickedIndex, willPickIndexByGlobalY, message.obj), i4 * 2);
            }
        };
        this.mHandlerLayout = new Handler() { // from class: com.aliyun.svideo.common.widget.WheelView.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                WheelView.this.requestLayout();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalSetWrapToLinear() {
        correctPositionByDefaultValue(getPickedIndexRelativeToRaw() - this.mMinShowIndex, false);
        this.mWrapSelectorWheel = false;
        postInvalidate();
    }

    private boolean isStringEqual(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    private int limitY(int i2) {
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            return i2;
        }
        int i3 = this.mNotWrapLimitYBottom;
        return (i2 >= i3 && i2 <= (i3 = this.mNotWrapLimitYTop)) ? i2 : i3;
    }

    private int measureHeight(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        this.mSpecModeH = mode;
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = getPaddingTop() + getPaddingBottom() + (this.mShowCount * (this.mMaxHeightOfDisplayedValues + (this.mItemPaddingVertical * 2)));
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    private int measureWidth(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        this.mSpecModeW = mode;
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight() + Math.max(this.mMaxWidthOfAlterArrayWithMeasureHint, Math.max(this.mMaxWidthOfDisplayedValues, this.mMaxWidthOfAlterArrayWithoutMeasureHint) + (((Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) != 0 ? this.mMarginStartOfHint : 0) + Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) + (Math.max(this.mWidthOfHintText, this.mWidthOfAlterHint) == 0 ? 0 : this.mMarginEndOfHint) + (this.mItemPaddingHorizental * 2)) * 2));
        return mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollStateChange(int i2) {
        if (this.mScrollState == i2) {
            return;
        }
        this.mScrollState = i2;
        OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChange(this, i2);
        }
    }

    private int refineValueByLimit(int i2, int i3, int i4, boolean z2) {
        return z2 ? i2 > i4 ? (((i2 - i4) % getOneRecycleSize()) + i3) - 1 : i2 < i3 ? ((i2 - i3) % getOneRecycleSize()) + i4 + 1 : i2 : i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void scrollByIndexSmoothly(int i2) {
        scrollByIndexSmoothly(i2, true);
    }

    private int sp2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    private void stopScrolling() {
        ScrollerCompat scrollerCompat = this.mScroller;
        if (scrollerCompat == null || scrollerCompat.isFinished()) {
            return;
        }
        this.mScroller.abortAnimation();
        postInvalidate();
    }

    private void updateContent(String[] strArr) {
        this.mDisplayedValues = strArr;
        updateWrapStateByContent();
    }

    private void updateContentAndIndex(String[] strArr) {
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = strArr.length - 1;
        this.mDisplayedValues = strArr;
        updateWrapStateByContent();
    }

    private void updateDividerAttr() {
        int i2 = this.mShowCount / 2;
        this.mDividerIndex0 = i2;
        this.mDividerIndex1 = i2 + 1;
        int i3 = this.mViewHeight;
        this.dividerY0 = (i2 * i3) / r0;
        this.dividerY1 = (r2 * i3) / r0;
        if (this.mDividerMarginL < 0) {
            this.mDividerMarginL = 0;
        }
        if (this.mDividerMarginR < 0) {
            this.mDividerMarginR = 0;
        }
        if (this.mDividerMarginL + this.mDividerMarginR != 0 && getPaddingLeft() + this.mDividerMarginL >= (this.mViewWidth - getPaddingRight()) - this.mDividerMarginR) {
            int paddingLeft = getPaddingLeft() + this.mDividerMarginL + getPaddingRight();
            int i4 = this.mDividerMarginR;
            int i5 = (paddingLeft + i4) - this.mViewWidth;
            int i6 = this.mDividerMarginL;
            float f2 = i5;
            this.mDividerMarginL = (int) (i6 - ((i6 * f2) / (i6 + i4)));
            this.mDividerMarginR = (int) (i4 - ((f2 * i4) / (r2 + i4)));
        }
    }

    private void updateFontAttr() {
        int i2 = this.mTextSizeNormal;
        int i3 = this.mItemHeight;
        if (i2 > i3) {
            this.mTextSizeNormal = i3;
        }
        if (this.mTextSizeSelected > i3) {
            this.mTextSizeSelected = i3;
        }
        Paint paint = this.mPaintHint;
        if (paint == null) {
            throw new IllegalArgumentException("mPaintHint should not be null.");
        }
        paint.setTextSize(this.mTextSizeHint);
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        Paint paint2 = this.mPaintText;
        if (paint2 == null) {
            throw new IllegalArgumentException("mPaintText should not be null.");
        }
        paint2.setTextSize(this.mTextSizeSelected);
        this.mTextSizeSelectedCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
        this.mPaintText.setTextSize(this.mTextSizeNormal);
        this.mTextSizeNormalCenterYOffset = getTextCenterYOffset(this.mPaintText.getFontMetrics());
    }

    private void updateMaxHeightOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxHeightOfDisplayedValues = (int) ((this.mPaintText.getFontMetrics().bottom - this.mPaintText.getFontMetrics().top) + 0.5d);
        this.mPaintText.setTextSize(textSize);
    }

    private void updateMaxWHOfDisplayedValues(boolean z2) {
        updateMaxWidthOfDisplayedValues();
        updateMaxHeightOfDisplayedValues();
        if (z2) {
            if (this.mSpecModeW == Integer.MIN_VALUE || this.mSpecModeH == Integer.MIN_VALUE) {
                this.mHandlerLayout.sendEmptyMessage(0);
            }
        }
    }

    private void updateMaxWidthOfDisplayedValues() {
        float textSize = this.mPaintText.getTextSize();
        this.mPaintText.setTextSize(this.mTextSizeSelected);
        this.mMaxWidthOfDisplayedValues = getMaxWidthOfTextArray(this.mDisplayedValues, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithMeasureHint, this.mPaintText);
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = getMaxWidthOfTextArray(this.mAlterTextArrayWithoutMeasureHint, this.mPaintText);
        this.mPaintText.setTextSize(this.mTextSizeHint);
        this.mWidthOfAlterHint = getTextWidth(this.mAlterHint, this.mPaintText);
        this.mPaintText.setTextSize(textSize);
    }

    private void updateNotWrapYLimit() {
        this.mNotWrapLimitYTop = 0;
        this.mNotWrapLimitYBottom = (-this.mShowCount) * this.mItemHeight;
        if (this.mDisplayedValues != null) {
            int oneRecycleSize = getOneRecycleSize();
            int i2 = this.mShowCount;
            int i3 = this.mItemHeight;
            this.mNotWrapLimitYTop = ((oneRecycleSize - (i2 / 2)) - 1) * i3;
            this.mNotWrapLimitYBottom = (-(i2 / 2)) * i3;
        }
    }

    private void updateValue() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        this.mMinShowIndex = 0;
        this.mMaxShowIndex = this.mDisplayedValues.length - 1;
    }

    private void updateValueForInit() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        if (this.mMinShowIndex == -1) {
            this.mMinShowIndex = 0;
        }
        if (this.mMaxShowIndex == -1) {
            this.mMaxShowIndex = this.mDisplayedValues.length - 1;
        }
        setMinAndMaxShowIndex(this.mMinShowIndex, this.mMaxShowIndex, false);
    }

    private void updateWrapStateByContent() {
        this.mWrapSelectorWheelCheck = this.mDisplayedValues.length > this.mShowCount;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mItemHeight != 0 && this.mScroller.computeScrollOffset()) {
            this.mCurrDrawGlobalY = this.mScroller.getCurrY();
            int iFloor = (int) Math.floor(r0 / this.mItemHeight);
            this.mCurrDrawFirstItemIndex = iFloor;
            this.mCurrDrawFirstItemY = -(this.mCurrDrawGlobalY - (iFloor * this.mItemHeight));
            postInvalidate();
        }
    }

    public String getContentByCurrValue() {
        return this.mDisplayedValues[getValue() - this.mMinValue];
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public int getOneRecycleSize() {
        return (this.mMaxShowIndex - this.mMinShowIndex) + 1;
    }

    public int getPickedIndexRelativeToRaw() {
        int i2 = this.mCurrDrawFirstItemY;
        if (i2 == 0) {
            return getWillPickIndexByGlobalY(this.mCurrDrawGlobalY);
        }
        int i3 = this.mItemHeight;
        return i2 < (-i3) / 2 ? getWillPickIndexByGlobalY(this.mCurrDrawGlobalY + i3 + i2) : getWillPickIndexByGlobalY(this.mCurrDrawGlobalY + i2);
    }

    public int getRawContentSize() {
        String[] strArr = this.mDisplayedValues;
        if (strArr != null) {
            return strArr.length;
        }
        return 0;
    }

    public int getValue() {
        return getPickedIndexRelativeToRaw() + this.mMinValue;
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public boolean getWrapSelectorWheelAbsolutely() {
        return this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandlerThread.quit();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawContent(canvas);
        drawLine(canvas);
        drawHint(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        updateMaxWHOfDisplayedValues(false);
        setMeasuredDimension(measureWidth(i2), measureHeight(i3));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSizeChanged(int r1, int r2, int r3, int r4) {
        /*
            r0 = this;
            super.onSizeChanged(r1, r2, r3, r4)
            r0.mViewWidth = r1
            r0.mViewHeight = r2
            int r3 = r0.mShowCount
            int r2 = r2 / r3
            r0.mItemHeight = r2
            int r2 = r0.getPaddingLeft()
            int r1 = r1 + r2
            int r2 = r0.getPaddingRight()
            int r1 = r1 - r2
            float r1 = (float) r1
            r2 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r2
            r0.mViewCenterX = r1
            int r1 = r0.getOneRecycleSize()
            r2 = 0
            r3 = 1
            if (r1 <= r3) goto L3d
            boolean r1 = r0.mHasInit
            if (r1 == 0) goto L30
            int r1 = r0.getValue()
            int r4 = r0.mMinValue
            int r1 = r1 - r4
            goto L3e
        L30:
            boolean r1 = r0.mCurrentItemIndexEffect
            if (r1 == 0) goto L3d
            int r1 = r0.mCurrDrawFirstItemIndex
            int r4 = r0.mShowCount
            int r4 = r4 - r3
            int r4 = r4 / 2
            int r1 = r1 + r4
            goto L3e
        L3d:
            r1 = r2
        L3e:
            boolean r4 = r0.mWrapSelectorWheel
            if (r4 == 0) goto L47
            boolean r4 = r0.mWrapSelectorWheelCheck
            if (r4 == 0) goto L47
            r2 = r3
        L47:
            r0.correctPositionByDefaultValue(r1, r2)
            r0.updateFontAttr()
            r0.updateNotWrapYLimit()
            r0.updateDividerAttr()
            r0.mHasInit = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.svideo.common.widget.WheelView.onSizeChanged(int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.svideo.common.widget.WheelView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void refreshByNewDisplayedValues(String[] strArr) {
        int minValue = getMinValue();
        int maxValue = (getMaxValue() - minValue) + 1;
        int length = strArr.length - 1;
        if ((length - minValue) + 1 > maxValue) {
            setDisplayedValues(strArr);
            setMaxValue(length);
        } else {
            setMaxValue(length);
            setDisplayedValues(strArr);
        }
    }

    public void setDisplayedValues(String[] strArr, boolean z2) {
        setDisplayedValuesAndPickedIndex(strArr, 0, z2);
    }

    public void setDisplayedValuesAndPickedIndex(String[] strArr, int i2, boolean z2) {
        stopScrolling();
        if (strArr == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("pickedIndex should not be negative, now pickedIndex is " + i2);
        }
        updateContent(strArr);
        updateMaxWHOfDisplayedValues(true);
        updateNotWrapYLimit();
        updateValue();
        this.mPrivPickedIndex = this.mMinShowIndex + i2;
        correctPositionByDefaultValue(i2, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        if (z2) {
            this.mHandler.sendMessageDelayed(getMsg(1), 0L);
            postInvalidate();
        }
    }

    public void setDividerColor(int i2) {
        if (this.mDividerColor == i2) {
            return;
        }
        this.mDividerColor = i2;
        this.mPaintDivider.setColor(i2);
        postInvalidate();
    }

    public void setFriction(float f2) {
        if (f2 > 0.0f) {
            ViewConfiguration.get(getContext());
            this.mFriction = ViewConfiguration.getScrollFriction() / f2;
        } else {
            throw new IllegalArgumentException("you should set a a positive float friction, now friction is " + f2);
        }
    }

    public void setHintText(String str) {
        if (isStringEqual(this.mHintText, str)) {
            return;
        }
        this.mHintText = str;
        this.mTextSizeHintCenterYOffset = getTextCenterYOffset(this.mPaintHint.getFontMetrics());
        this.mWidthOfHintText = getTextWidth(this.mHintText, this.mPaintHint);
        this.mHandlerLayout.sendEmptyMessage(0);
    }

    public void setHintTextColor(int i2) {
        if (this.mTextColorHint == i2) {
            return;
        }
        this.mTextColorHint = i2;
        this.mPaintHint.setColor(i2);
        postInvalidate();
    }

    public void setMaxValue(int i2) {
        String[] strArr = this.mDisplayedValues;
        if (strArr == null) {
            throw new NullPointerException("mDisplayedValues should not be null");
        }
        int i3 = this.mMinValue;
        if ((i2 - i3) + 1 > strArr.length) {
            throw new IllegalArgumentException("(maxValue - mMinValue + 1) should not be larger than mDisplayedValues.length now  (maxValue - mMinValue + 1) is " + ((i2 - this.mMinValue) + 1) + " and mDisplayedValues.length is " + this.mDisplayedValues.length);
        }
        this.mMaxValue = i2;
        int i4 = this.mMinShowIndex;
        int i5 = (i2 - i3) + i4;
        this.mMaxShowIndex = i5;
        setMinAndMaxShowIndex(i4, i5);
        updateNotWrapYLimit();
    }

    public void setMinAndMaxShowIndex(int i2, int i3) {
        setMinAndMaxShowIndex(i2, i3, true);
    }

    public void setMinValue(int i2) {
        this.mMinValue = i2;
        this.mMinShowIndex = 0;
        updateNotWrapYLimit();
    }

    public void setNormalTextColor(int i2) {
        if (this.mTextColorNormal == i2) {
            return;
        }
        this.mTextColorNormal = i2;
        postInvalidate();
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setOnValueChangedListenerRelativeToRaw(OnValueChangeListenerRelativeToRaw onValueChangeListenerRelativeToRaw) {
        this.mOnValueChangeListenerRaw = onValueChangeListenerRelativeToRaw;
    }

    public void setPickedIndexRelativeToMin(int i2) {
        if (i2 < 0 || i2 >= getOneRecycleSize()) {
            return;
        }
        this.mPrivPickedIndex = this.mMinShowIndex + i2;
        correctPositionByDefaultValue(i2, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public void setPickedIndexRelativeToRaw(int i2) {
        int i3 = this.mMinShowIndex;
        if (i3 <= -1 || i3 > i2 || i2 > this.mMaxShowIndex) {
            return;
        }
        this.mPrivPickedIndex = i2;
        correctPositionByDefaultValue(i2 - i3, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        postInvalidate();
    }

    public void setSelectedTextColor(int i2) {
        if (this.mTextColorSelected == i2) {
            return;
        }
        this.mTextColorSelected = i2;
        postInvalidate();
    }

    public void setValue(int i2) {
        int i3 = this.mMinValue;
        if (i2 < i3) {
            throw new IllegalArgumentException("should not set a value less than mMinValue, value is " + i2);
        }
        if (i2 <= this.mMaxValue) {
            setPickedIndexRelativeToRaw(i2 - i3);
            return;
        }
        throw new IllegalArgumentException("should not set a value larger than mMaxValue, value is " + i2);
    }

    public void setWrapSelectorWheel(boolean z2) {
        if (this.mWrapSelectorWheel != z2) {
            if (z2) {
                this.mWrapSelectorWheel = z2;
                updateWrapStateByContent();
                postInvalidate();
            } else if (this.mScrollState == 0) {
                internalSetWrapToLinear();
            } else {
                this.mPendingWrapToLinear = true;
            }
        }
    }

    public void smoothScrollToValue(int i2) {
        smoothScrollToValue(getValue(), i2, true);
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Message getMsg(int i2, int i3, int i4, Object obj) {
        Message messageObtain = Message.obtain();
        messageObtain.what = i2;
        messageObtain.arg1 = i3;
        messageObtain.arg2 = i4;
        messageObtain.obj = obj;
        return messageObtain;
    }

    private void scrollByIndexSmoothly(int i2, boolean z2) {
        int pickedIndexRelativeToRaw;
        int pickedIndexRelativeToRaw2;
        int i3;
        int i4;
        if ((!this.mWrapSelectorWheel || !this.mWrapSelectorWheelCheck) && ((pickedIndexRelativeToRaw2 = (pickedIndexRelativeToRaw = getPickedIndexRelativeToRaw()) + i2) > (i3 = this.mMaxShowIndex) || pickedIndexRelativeToRaw2 < (i3 = this.mMinShowIndex))) {
            i2 = i3 - pickedIndexRelativeToRaw;
        }
        int i5 = this.mCurrDrawFirstItemY;
        int i6 = this.mItemHeight;
        if (i5 < (-i6) / 2) {
            int i7 = i6 + i5;
            int i8 = (int) (((i5 + i6) * 300.0f) / i6);
            i4 = i2 < 0 ? (-i8) - (i2 * 300) : i8 + (i2 * 300);
            i5 = i7;
        } else {
            int i9 = (int) (((-i5) * 300.0f) / i6);
            i4 = i2 < 0 ? i9 - (i2 * 300) : i9 + (i2 * 300);
        }
        int i10 = i5 + (i2 * i6);
        if (i4 < 300) {
            i4 = 300;
        }
        if (i4 > 600) {
            i4 = 600;
        }
        this.mScroller.startScroll(0, this.mCurrDrawGlobalY, 0, i10, i4);
        if (z2) {
            this.mHandler.sendMessageDelayed(getMsg(1), i4 / 4);
        } else {
            this.mHandler.sendMessageDelayed(getMsg(1, 0, 0, new Boolean(z2)), i4 / 4);
        }
        postInvalidate();
    }

    public void setDisplayedValues(String[] strArr) {
        stopScrolling();
        if (strArr == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        if ((this.mMaxValue - this.mMinValue) + 1 <= strArr.length) {
            updateContent(strArr);
            updateMaxWHOfDisplayedValues(true);
            this.mPrivPickedIndex = this.mMinShowIndex + 0;
            correctPositionByDefaultValue(0, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
            postInvalidate();
            this.mHandlerLayout.sendEmptyMessage(0);
            return;
        }
        throw new IllegalArgumentException("mMaxValue - mMinValue + 1 should not be larger than mDisplayedValues.length, now ((mMaxValue - mMinValue + 1) is " + ((this.mMaxValue - this.mMinValue) + 1) + " newDisplayedValues.length is " + strArr.length + ", you need to set MaxValue and MinValue before setDisplayedValues(String[])");
    }

    public void setMinAndMaxShowIndex(int i2, int i3, boolean z2) {
        if (i2 > i3) {
            throw new IllegalArgumentException("minShowIndex should be less than maxShowIndex, minShowIndex is " + i2 + ", maxShowIndex is " + i3 + StrPool.DOT);
        }
        String[] strArr = this.mDisplayedValues;
        if (strArr == null) {
            throw new IllegalArgumentException("mDisplayedValues should not be null, you need to set mDisplayedValues first.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("minShowIndex should not be less than 0, now minShowIndex is " + i2);
        }
        if (i2 > strArr.length - 1) {
            throw new IllegalArgumentException("minShowIndex should not be larger than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " minShowIndex is " + i2);
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("maxShowIndex should not be less than 0, now maxShowIndex is " + i3);
        }
        if (i3 > strArr.length - 1) {
            throw new IllegalArgumentException("maxShowIndex should not be larger than (mDisplayedValues.length - 1), now (mDisplayedValues.length - 1) is " + (this.mDisplayedValues.length - 1) + " maxShowIndex is " + i3);
        }
        this.mMinShowIndex = i2;
        this.mMaxShowIndex = i3;
        if (z2) {
            this.mPrivPickedIndex = i2 + 0;
            correctPositionByDefaultValue(0, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
            postInvalidate();
        }
    }

    public void smoothScrollToValue(int i2, boolean z2) {
        smoothScrollToValue(getValue(), i2, z2);
    }

    public WheelView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTextColorNormal = DEFAULT_TEXT_COLOR_NORMAL;
        this.mTextColorSelected = -695533;
        this.mTextColorHint = -695533;
        this.mTextSizeNormal = 0;
        this.mTextSizeSelected = 0;
        this.mTextSizeHint = 0;
        this.mWidthOfHintText = 0;
        this.mWidthOfAlterHint = 0;
        this.mMarginStartOfHint = 0;
        this.mMarginEndOfHint = 0;
        this.mItemPaddingVertical = 0;
        this.mItemPaddingHorizental = 0;
        this.mDividerColor = -695533;
        this.mDividerHeight = 2;
        this.mDividerMarginL = 0;
        this.mDividerMarginR = 0;
        this.mShowCount = 3;
        this.mDividerIndex0 = 0;
        this.mDividerIndex1 = 0;
        this.mMinShowIndex = -1;
        this.mMaxShowIndex = -1;
        this.mMinValue = 0;
        this.mMaxValue = 0;
        this.mMaxWidthOfDisplayedValues = 0;
        this.mMaxHeightOfDisplayedValues = 0;
        this.mMaxWidthOfAlterArrayWithMeasureHint = 0;
        this.mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
        this.mPrivPickedIndex = 0;
        this.mMiniVerlocityFling = 150;
        this.mScaledTouchSlop = 8;
        this.mFriction = 1.0f;
        this.mTextSizeNormalCenterYOffset = 0.0f;
        this.mTextSizeSelectedCenterYOffset = 0.0f;
        this.mTextSizeHintCenterYOffset = 0.0f;
        this.mShowDivider = true;
        this.mWrapSelectorWheel = true;
        this.mCurrentItemIndexEffect = false;
        this.mHasInit = false;
        this.mWrapSelectorWheelCheck = true;
        this.mPendingWrapToLinear = false;
        this.mPaintDivider = new Paint();
        this.mPaintText = new Paint();
        this.mPaintHint = new Paint();
        this.mScrollState = 0;
        this.downYGlobal = 0.0f;
        this.downY = 0.0f;
        this.currY = 0.0f;
        this.mFlagMayPress = false;
        this.mCurrDrawFirstItemIndex = 0;
        this.mCurrDrawFirstItemY = 0;
        this.mCurrDrawGlobalY = 0;
        this.mSpecModeW = 0;
        this.mSpecModeH = 0;
        initAttr(context, attributeSet);
        init(context);
    }

    public void smoothScrollToValue(int i2, int i3) {
        smoothScrollToValue(i2, i3, true);
    }

    public void smoothScrollToValue(int i2, int i3, boolean z2) {
        int i4;
        int iRefineValueByLimit = refineValueByLimit(i2, this.mMinValue, this.mMaxValue, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        int iRefineValueByLimit2 = refineValueByLimit(i3, this.mMinValue, this.mMaxValue, this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck);
        if (this.mWrapSelectorWheel && this.mWrapSelectorWheelCheck) {
            i4 = iRefineValueByLimit2 - iRefineValueByLimit;
            int oneRecycleSize = getOneRecycleSize() / 2;
            if (i4 < (-oneRecycleSize) || oneRecycleSize < i4) {
                int oneRecycleSize2 = getOneRecycleSize();
                i4 = i4 > 0 ? i4 - oneRecycleSize2 : i4 + oneRecycleSize2;
            }
        } else {
            i4 = iRefineValueByLimit2 - iRefineValueByLimit;
        }
        setValue(iRefineValueByLimit);
        if (iRefineValueByLimit == iRefineValueByLimit2) {
            return;
        }
        scrollByIndexSmoothly(i4, z2);
    }
}
