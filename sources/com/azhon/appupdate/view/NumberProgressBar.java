package com.azhon.appupdate.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import com.catchpig.mvvm.R;
import com.google.android.material.timepicker.TimeModel;

/* loaded from: classes2.dex */
public class NumberProgressBar extends View {
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final int PROGRESS_TEXT_VISIBLE = 0;
    private final float default_progress_text_offset;
    private final int default_reached_color;
    private final int default_text_color;
    private final float default_text_size;
    private final int default_unreached_color;
    private String mCurrentDrawText;
    private int mCurrentProgress;
    private boolean mDrawReachedBar;
    private float mDrawTextEnd;
    private float mDrawTextStart;
    private float mDrawTextWidth;
    private boolean mDrawUnreachedBar;
    private boolean mIfDrawText;
    private int mMaxProgress;
    private float mOffset;
    private String mPrefix;
    private int mReachedBarColor;
    private float mReachedBarHeight;
    private Paint mReachedBarPaint;
    private RectF mReachedRectF;
    private String mSuffix;
    private int mTextColor;
    private Paint mTextPaint;
    private float mTextSize;
    private int mUnreachedBarColor;
    private float mUnreachedBarHeight;
    private Paint mUnreachedBarPaint;
    private RectF mUnreachedRectF;

    public enum ProgressTextVisibility {
        Visible,
        Invisible
    }

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    private void calculateDrawRectF() {
        this.mCurrentDrawText = String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf((getProgress() * 100) / getMax()));
        String str = this.mPrefix + this.mCurrentDrawText + this.mSuffix;
        this.mCurrentDrawText = str;
        this.mDrawTextWidth = this.mTextPaint.measureText(str);
        if (getProgress() == 0) {
            this.mDrawReachedBar = false;
            this.mDrawTextStart = getPaddingLeft();
        } else {
            this.mDrawReachedBar = true;
            this.mReachedRectF.left = getPaddingLeft();
            this.mReachedRectF.top = (getHeight() / 2.0f) - (this.mReachedBarHeight / 2.0f);
            this.mReachedRectF.right = (((((getWidth() - getPaddingLeft()) - getPaddingRight()) / (getMax() * 1.0f)) * getProgress()) - this.mOffset) + getPaddingLeft();
            this.mReachedRectF.bottom = (getHeight() / 2.0f) + (this.mReachedBarHeight / 2.0f);
            this.mDrawTextStart = this.mReachedRectF.right + this.mOffset;
        }
        this.mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f));
        if (this.mDrawTextStart + this.mDrawTextWidth >= getWidth() - getPaddingRight()) {
            float width = (getWidth() - getPaddingRight()) - this.mDrawTextWidth;
            this.mDrawTextStart = width;
            this.mReachedRectF.right = width - this.mOffset;
        }
        float f2 = this.mDrawTextStart + this.mDrawTextWidth + this.mOffset;
        if (f2 >= getWidth() - getPaddingRight()) {
            this.mDrawUnreachedBar = false;
            return;
        }
        this.mDrawUnreachedBar = true;
        RectF rectF = this.mUnreachedRectF;
        rectF.left = f2;
        rectF.right = getWidth() - getPaddingRight();
        this.mUnreachedRectF.top = (getHeight() / 2.0f) + ((-this.mUnreachedBarHeight) / 2.0f);
        this.mUnreachedRectF.bottom = (getHeight() / 2.0f) + (this.mUnreachedBarHeight / 2.0f);
    }

    private void calculateDrawRectFWithoutProgressText() {
        this.mReachedRectF.left = getPaddingLeft();
        this.mReachedRectF.top = (getHeight() / 2.0f) - (this.mReachedBarHeight / 2.0f);
        this.mReachedRectF.right = ((((getWidth() - getPaddingLeft()) - getPaddingRight()) / (getMax() * 1.0f)) * getProgress()) + getPaddingLeft();
        this.mReachedRectF.bottom = (getHeight() / 2.0f) + (this.mReachedBarHeight / 2.0f);
        RectF rectF = this.mUnreachedRectF;
        rectF.left = this.mReachedRectF.right;
        rectF.right = getWidth() - getPaddingRight();
        this.mUnreachedRectF.top = (getHeight() / 2.0f) + ((-this.mUnreachedBarHeight) / 2.0f);
        this.mUnreachedRectF.bottom = (getHeight() / 2.0f) + (this.mUnreachedBarHeight / 2.0f);
    }

    private void initializePainters() {
        Paint paint = new Paint(1);
        this.mReachedBarPaint = paint;
        paint.setColor(this.mReachedBarColor);
        Paint paint2 = new Paint(1);
        this.mUnreachedBarPaint = paint2;
        paint2.setColor(this.mUnreachedBarColor);
        Paint paint3 = new Paint(1);
        this.mTextPaint = paint3;
        paint3.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
    }

    private int measure(int i2, boolean z2) {
        int paddingTop;
        int paddingBottom;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (z2) {
            paddingTop = getPaddingLeft();
            paddingBottom = getPaddingRight();
        } else {
            paddingTop = getPaddingTop();
            paddingBottom = getPaddingBottom();
        }
        int i3 = paddingTop + paddingBottom;
        if (mode == 1073741824) {
            return size;
        }
        int suggestedMinimumWidth = (z2 ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight()) + i3;
        return mode == Integer.MIN_VALUE ? z2 ? Math.max(suggestedMinimumWidth, size) : Math.min(suggestedMinimumWidth, size) : suggestedMinimumWidth;
    }

    public float dp2px(float f2) {
        return (f2 * getResources().getDisplayMetrics().density) + 0.5f;
    }

    public int getMax() {
        return this.mMaxProgress;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public int getProgress() {
        return this.mCurrentProgress;
    }

    public float getProgressTextSize() {
        return this.mTextSize;
    }

    public boolean getProgressTextVisibility() {
        return this.mIfDrawText;
    }

    public int getReachedBarColor() {
        return this.mReachedBarColor;
    }

    public float getReachedBarHeight() {
        return this.mReachedBarHeight;
    }

    public String getSuffix() {
        return this.mSuffix;
    }

    @Override // android.view.View
    public int getSuggestedMinimumHeight() {
        return Math.max((int) this.mTextSize, Math.max((int) this.mReachedBarHeight, (int) this.mUnreachedBarHeight));
    }

    @Override // android.view.View
    public int getSuggestedMinimumWidth() {
        return (int) this.mTextSize;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public int getUnreachedBarColor() {
        return this.mUnreachedBarColor;
    }

    public float getUnreachedBarHeight() {
        return this.mUnreachedBarHeight;
    }

    public void incrementProgressBy(int i2) {
        if (i2 > 0) {
            setProgress(getProgress() + i2);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mIfDrawText) {
            calculateDrawRectF();
        } else {
            calculateDrawRectFWithoutProgressText();
        }
        if (this.mDrawReachedBar) {
            canvas.drawRect(this.mReachedRectF, this.mReachedBarPaint);
        }
        if (this.mDrawUnreachedBar) {
            canvas.drawRect(this.mUnreachedRectF, this.mUnreachedBarPaint);
        }
        if (this.mIfDrawText) {
            canvas.drawText(this.mCurrentDrawText, this.mDrawTextStart, this.mDrawTextEnd, this.mTextPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        setMeasuredDimension(measure(i2, true), measure(i3, false));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        this.mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
        this.mTextSize = bundle.getFloat("text_size");
        this.mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
        this.mUnreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
        this.mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
        this.mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
        initializePainters();
        setMax(bundle.getInt(INSTANCE_MAX));
        setProgress(bundle.getInt("progress"));
        setPrefix(bundle.getString("prefix"));
        setSuffix(bundle.getString(INSTANCE_SUFFIX));
        setProgressTextVisibility(bundle.getBoolean(INSTANCE_TEXT_VISIBILITY) ? ProgressTextVisibility.Visible : ProgressTextVisibility.Invisible);
        super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat("text_size", getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt("progress", getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString("prefix", getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        return bundle;
    }

    public void setMax(int i2) {
        if (i2 > 0) {
            this.mMaxProgress = i2;
            invalidate();
        }
    }

    public void setPrefix(String str) {
        if (str == null) {
            this.mPrefix = "";
        } else {
            this.mPrefix = str;
        }
    }

    public void setProgress(int i2) {
        if (i2 > getMax() || i2 < 0) {
            return;
        }
        this.mCurrentProgress = i2;
        invalidate();
    }

    public void setProgressTextColor(int i2) {
        this.mTextColor = i2;
        this.mTextPaint.setColor(i2);
        invalidate();
    }

    public void setProgressTextSize(float f2) {
        this.mTextSize = f2;
        this.mTextPaint.setTextSize(f2);
        invalidate();
    }

    public void setProgressTextVisibility(ProgressTextVisibility progressTextVisibility) {
        this.mIfDrawText = progressTextVisibility == ProgressTextVisibility.Visible;
        invalidate();
    }

    public void setReachedBarColor(int i2) {
        this.mReachedBarColor = i2;
        this.mReachedBarPaint.setColor(i2);
        invalidate();
    }

    public void setReachedBarHeight(float f2) {
        this.mReachedBarHeight = f2;
    }

    public void setSuffix(String str) {
        if (str == null) {
            this.mSuffix = "";
        } else {
            this.mSuffix = str;
        }
    }

    public void setUnreachedBarColor(int i2) {
        this.mUnreachedBarColor = i2;
        this.mUnreachedBarPaint.setColor(i2);
        invalidate();
    }

    public void setUnreachedBarHeight(float f2) {
        this.mUnreachedBarHeight = f2;
    }

    public float sp2px(float f2) {
        return f2 * getResources().getDisplayMetrics().scaledDensity;
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mMaxProgress = 100;
        this.mCurrentProgress = 0;
        this.mSuffix = "%";
        this.mPrefix = "";
        int iRgb = Color.rgb(255, 137, 91);
        this.default_text_color = iRgb;
        int iRgb2 = Color.rgb(255, 137, 91);
        this.default_reached_color = iRgb2;
        int iRgb3 = Color.rgb(204, 204, 204);
        this.default_unreached_color = iRgb3;
        this.mUnreachedRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        this.mReachedRectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        this.mDrawUnreachedBar = true;
        this.mDrawReachedBar = true;
        this.mIfDrawText = true;
        this.mReachedBarHeight = dp2px(1.5f);
        this.mUnreachedBarHeight = dp2px(1.0f);
        float fSp2px = sp2px(10.0f);
        this.default_text_size = fSp2px;
        this.default_progress_text_offset = dp2px(3.0f);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.AppUpdate_NumberProgressBar, i2, 0);
        this.mReachedBarColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AppUpdate_NumberProgressBar_progress_reached_color, iRgb2);
        this.mUnreachedBarColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AppUpdate_NumberProgressBar_progress_unreached_color, iRgb3);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.AppUpdate_NumberProgressBar_progress_text_color, iRgb);
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.AppUpdate_NumberProgressBar_progress_text_size, fSp2px);
        typedArrayObtainStyledAttributes.recycle();
        initializePainters();
    }
}
