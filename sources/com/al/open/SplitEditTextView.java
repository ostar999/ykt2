package com.al.open;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class SplitEditTextView extends AppCompatEditText {
    public static final int CONTENT_SHOW_MODE_PASSWORD = 1;
    public static final int CONTENT_SHOW_MODE_TEXT = 2;
    public static final int INPUT_BOX_STYLE_CONNECT = 1;
    public static final int INPUT_BOX_STYLE_SINGLE = 2;
    public static final int INPUT_BOX_STYLE_UNDERLINE = 3;
    private CursorRunnable cursorRunnable;
    private OnInputListener inputListener;
    private int mBorderColor;
    private Float mBorderSize;
    private float mCircleRadius;
    private int mContentNumber;
    private int mContentShowMode;
    private float mCornerSize;
    private int mCursorColor;
    private int mCursorDuration;
    private int mCursorHeight;
    private float mCursorWidth;
    private int mDivisionColor;
    private float mDivisionLineSize;
    private boolean mInputBoxSquare;
    private int mInputBoxStyle;
    private Paint mPaintBorder;
    private Paint mPaintContent;
    private Paint mPaintCursor;
    private Paint mPaintDivisionLine;
    private Paint mPaintUnderline;
    private RectF mRectFConnect;
    private RectF mRectFSingleBox;
    private float mSpaceSize;
    private int mTextColor;
    private float mTextSize;
    private int mUnderlineFocusColor;
    private int mUnderlineNormalColor;

    public class CursorRunnable implements Runnable {
        private CursorRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SplitEditTextView.this.mPaintCursor.setAlpha(SplitEditTextView.this.mPaintCursor.getAlpha() == 0 ? 255 : 0);
            SplitEditTextView.this.invalidate();
            SplitEditTextView.this.postDelayed(this, r0.mCursorDuration);
        }
    }

    public SplitEditTextView(Context context) {
        this(context, null);
    }

    private float dp2px(float f2) {
        return TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics());
    }

    private void drawConnectStyle(Canvas canvas) {
        this.mRectFConnect.setEmpty();
        this.mRectFConnect.set(this.mBorderSize.floatValue() / 2.0f, this.mBorderSize.floatValue() / 2.0f, getWidth() - (this.mBorderSize.floatValue() / 2.0f), getHeight() - (this.mBorderSize.floatValue() / 2.0f));
        RectF rectF = this.mRectFConnect;
        float f2 = this.mCornerSize;
        canvas.drawRoundRect(rectF, f2, f2, this.mPaintBorder);
        drawDivisionLine(canvas);
    }

    private void drawContent(Canvas canvas) {
        int height = getHeight() / 2;
        String strTrim = getText().toString().trim();
        int i2 = 0;
        if (this.mContentShowMode == 1) {
            this.mPaintContent.setColor(-16777216);
            while (i2 < strTrim.length()) {
                canvas.drawCircle(getDrawContentStartX(i2), height, this.mCircleRadius, this.mPaintContent);
                i2++;
            }
            return;
        }
        this.mPaintContent.setColor(this.mTextColor);
        float textBaseline = getTextBaseline(this.mPaintContent, height);
        while (i2 < strTrim.length()) {
            float drawContentStartX = getDrawContentStartX(i2);
            String strValueOf = String.valueOf(strTrim.charAt(i2));
            canvas.drawText(strValueOf, drawContentStartX - (this.mPaintContent.measureText(strValueOf) / 2.0f), textBaseline, this.mPaintContent);
            i2++;
        }
    }

    private void drawCursor(Canvas canvas) {
        if (this.mCursorHeight > getHeight()) {
            throw new InflateException("cursor height must smaller than view height");
        }
        float drawContentStartX = getDrawContentStartX(getText().toString().trim().length());
        if (this.mCursorHeight == 0) {
            this.mCursorHeight = getHeight() / 2;
        }
        canvas.drawLine(drawContentStartX, ((getHeight() - this.mCursorHeight) / 2) + this.mBorderSize.floatValue(), drawContentStartX, (getHeight() - r0) - this.mBorderSize.floatValue(), this.mPaintCursor);
    }

    private void drawDivisionLine(Canvas canvas) {
        float height = getHeight() - this.mBorderSize.floatValue();
        int i2 = 0;
        while (i2 < this.mContentNumber - 1) {
            int i3 = i2 + 1;
            float contentItemWidth = (i3 * getContentItemWidth()) + (i2 * this.mDivisionLineSize) + this.mBorderSize.floatValue() + (this.mDivisionLineSize / 2.0f);
            canvas.drawLine(contentItemWidth, this.mBorderSize.floatValue(), contentItemWidth, height, this.mPaintDivisionLine);
            i2 = i3;
        }
    }

    private void drawSingleStyle(Canvas canvas) {
        int i2 = 0;
        while (i2 < this.mContentNumber) {
            this.mRectFSingleBox.setEmpty();
            float f2 = i2;
            i2++;
            this.mRectFSingleBox.set((getContentItemWidth() * f2) + (this.mSpaceSize * f2) + (this.mBorderSize.floatValue() * f2 * 2.0f) + (this.mBorderSize.floatValue() / 2.0f), this.mBorderSize.floatValue() / 2.0f, (((f2 * this.mSpaceSize) + (i2 * getContentItemWidth())) + ((i2 * 2) * this.mBorderSize.floatValue())) - (this.mBorderSize.floatValue() / 2.0f), getHeight() - (this.mBorderSize.floatValue() / 2.0f));
            RectF rectF = this.mRectFSingleBox;
            float f3 = this.mCornerSize;
            canvas.drawRoundRect(rectF, f3, f3, this.mPaintBorder);
        }
    }

    private void drawUnderlineStyle(Canvas canvas) {
        String strTrim = getText().toString().trim();
        for (int i2 = 0; i2 < this.mContentNumber; i2++) {
            float f2 = i2;
            float contentItemWidth = (getContentItemWidth() * f2) + (f2 * this.mSpaceSize);
            float contentItemWidth2 = getContentItemWidth() + contentItemWidth;
            float height = getHeight() - (this.mBorderSize.floatValue() / 2.0f);
            if (this.mUnderlineFocusColor != 0) {
                if (strTrim.length() >= i2) {
                    this.mPaintUnderline.setColor(this.mUnderlineFocusColor);
                } else {
                    this.mPaintUnderline.setColor(this.mUnderlineNormalColor);
                }
            }
            canvas.drawLine(contentItemWidth, height, contentItemWidth2, height, this.mPaintUnderline);
        }
    }

    private float getContentItemWidth() {
        float width;
        float fFloatValue;
        float fFloatValue2;
        int i2 = this.mInputBoxStyle;
        if (i2 == 2) {
            float width2 = getWidth();
            int i3 = this.mContentNumber;
            width = width2 - ((i3 - 1) * this.mSpaceSize);
            fFloatValue = i3 * 2;
            fFloatValue2 = this.mBorderSize.floatValue();
        } else if (i2 != 3) {
            width = getWidth() - (this.mDivisionLineSize * (this.mContentNumber - 1));
            fFloatValue = this.mBorderSize.floatValue();
            fFloatValue2 = 2.0f;
        } else {
            width = getWidth();
            fFloatValue = this.mContentNumber - 1;
            fFloatValue2 = this.mSpaceSize;
        }
        return (width - (fFloatValue * fFloatValue2)) / this.mContentNumber;
    }

    private float getContentItemWidthOnMeasure(int i2) {
        float f2;
        float fFloatValue;
        float fFloatValue2;
        int i3 = this.mInputBoxStyle;
        if (i3 == 2) {
            int i4 = this.mContentNumber;
            f2 = i2 - ((i4 - 1) * this.mSpaceSize);
            fFloatValue = i4 * 2;
            fFloatValue2 = this.mBorderSize.floatValue();
        } else if (i3 != 3) {
            f2 = i2 - (this.mDivisionLineSize * (this.mContentNumber - 1));
            fFloatValue = this.mBorderSize.floatValue();
            fFloatValue2 = 2.0f;
        } else {
            f2 = i2;
            fFloatValue = this.mContentNumber - 1;
            fFloatValue2 = this.mSpaceSize;
        }
        return (f2 - (fFloatValue * fFloatValue2)) / this.mContentNumber;
    }

    private float getDrawContentStartX(int i2) {
        float contentItemWidth;
        float f2;
        float fFloatValue;
        float fFloatValue2;
        int i3 = this.mInputBoxStyle;
        if (i3 == 2) {
            float f3 = i2;
            contentItemWidth = (getContentItemWidth() / 2.0f) + (getContentItemWidth() * f3) + (f3 * this.mSpaceSize);
            f2 = (i2 * 2) + 1;
            fFloatValue = this.mBorderSize.floatValue();
        } else {
            if (i3 != 3) {
                float f4 = i2;
                contentItemWidth = (getContentItemWidth() / 2.0f) + (getContentItemWidth() * f4) + (f4 * this.mDivisionLineSize);
                fFloatValue2 = this.mBorderSize.floatValue();
                return contentItemWidth + fFloatValue2;
            }
            f2 = i2;
            contentItemWidth = (getContentItemWidth() / 2.0f) + (this.mSpaceSize * f2);
            fFloatValue = getContentItemWidth();
        }
        fFloatValue2 = f2 * fFloatValue;
        return contentItemWidth + fFloatValue2;
    }

    private float getTextBaseline(Paint paint, float f2) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f3 = fontMetrics.bottom;
        return f2 + (((f3 - fontMetrics.top) / 2.0f) - f3);
    }

    private void init() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Paint paint = new Paint(1);
        this.mPaintBorder = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPaintBorder.setStrokeWidth(this.mBorderSize.floatValue());
        this.mPaintBorder.setColor(this.mBorderColor);
        Paint paint2 = new Paint(1);
        this.mPaintDivisionLine = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mPaintDivisionLine.setStrokeWidth(this.mDivisionLineSize);
        this.mPaintDivisionLine.setColor(this.mDivisionColor);
        Paint paint3 = new Paint(1);
        this.mPaintContent = paint3;
        paint3.setTextSize(this.mTextSize);
        Paint paint4 = new Paint(1);
        this.mPaintCursor = paint4;
        paint4.setStrokeWidth(this.mCursorWidth);
        this.mPaintCursor.setColor(this.mCursorColor);
        Paint paint5 = new Paint(1);
        this.mPaintUnderline = paint5;
        paint5.setStrokeWidth(this.mBorderSize.floatValue());
        this.mPaintUnderline.setColor(this.mUnderlineNormalColor);
        this.mRectFSingleBox = new RectF();
        this.mRectFConnect = new RectF();
        setSingleLine();
        setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= 29) {
            setTextSelectHandle(android.R.color.transparent);
        } else {
            try {
                Field declaredField = TextView.class.getDeclaredField("mTextSelectHandleRes");
                declaredField.setAccessible(true);
                declaredField.set(this, Integer.valueOf(android.R.color.transparent));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(this.mContentNumber)});
    }

    private void initAttrs(Context context, AttributeSet attributeSet) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SplitEditTextView);
        this.mBorderSize = Float.valueOf(typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_borderSize, dp2px(1.0f)));
        this.mBorderColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SplitEditTextView_borderColor, -16777216);
        this.mCornerSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_corner_size, 0.0f);
        this.mDivisionLineSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_divisionLineSize, dp2px(1.0f));
        this.mDivisionColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SplitEditTextView_divisionLineColor, -16777216);
        this.mCircleRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_circleRadius, dp2px(5.0f));
        this.mContentNumber = typedArrayObtainStyledAttributes.getInt(R.styleable.SplitEditTextView_contentNumber, 6);
        this.mContentShowMode = typedArrayObtainStyledAttributes.getInteger(R.styleable.SplitEditTextView_contentShowMode, 1);
        this.mInputBoxStyle = typedArrayObtainStyledAttributes.getInteger(R.styleable.SplitEditTextView_inputBoxStyle, 1);
        this.mSpaceSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_spaceSize, dp2px(10.0f));
        this.mTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_android_textSize, sp2px(16.0f));
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SplitEditTextView_android_textColor, -16777216);
        this.mInputBoxSquare = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SplitEditTextView_inputBoxSquare, true);
        this.mCursorColor = typedArrayObtainStyledAttributes.getColor(R.styleable.SplitEditTextView_cursorColor, -16777216);
        this.mCursorDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.SplitEditTextView_cursorDuration, 500);
        this.mCursorWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_cursorWidth, dp2px(2.0f));
        this.mCursorHeight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.SplitEditTextView_cursorHeight, 0.0f);
        this.mUnderlineNormalColor = typedArrayObtainStyledAttributes.getInt(R.styleable.SplitEditTextView_underlineNormalColor, -16777216);
        this.mUnderlineFocusColor = typedArrayObtainStyledAttributes.getInt(R.styleable.SplitEditTextView_underlineFocusColor, 0);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }

    private float sp2px(float f2) {
        return TypedValue.applyDimension(2, f2, Resources.getSystem().getDisplayMetrics());
    }

    public int getContentShowMode() {
        return this.mContentShowMode;
    }

    public int getInputBoxStyle() {
        return this.mInputBoxStyle;
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        CursorRunnable cursorRunnable = new CursorRunnable();
        this.cursorRunnable = cursorRunnable;
        postDelayed(cursorRunnable, this.mCursorDuration);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.cursorRunnable);
        super.onDetachedFromWindow();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        int i2 = this.mInputBoxStyle;
        if (i2 == 2) {
            drawSingleStyle(canvas);
        } else if (i2 != 3) {
            drawConnectStyle(canvas);
        } else {
            drawUnderlineStyle(canvas);
        }
        drawContent(canvas);
        drawCursor(canvas);
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.mInputBoxSquare) {
            int size = View.MeasureSpec.getSize(i2);
            float contentItemWidthOnMeasure = getContentItemWidthOnMeasure(size);
            if (this.mInputBoxStyle != 3) {
                setMeasuredDimension(size, (int) (contentItemWidthOnMeasure + (this.mBorderSize.floatValue() * 2.0f)));
            } else {
                setMeasuredDimension(size, (int) (contentItemWidthOnMeasure + this.mBorderSize.floatValue()));
            }
        }
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        super.onTextChanged(charSequence, i2, i3, i4);
        String strTrim = charSequence.toString().trim();
        if (this.inputListener != null) {
            if (strTrim.length() == this.mContentNumber) {
                this.inputListener.onInputFinished(strTrim);
            } else {
                this.inputListener.onInputChanged(strTrim);
            }
        }
    }

    public void setContentShowMode(int i2) {
        if (i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("the value of the parameter must be one of{1:EDIT_SHOW_MODE_PASSWORD} or {2:EDIT_SHOW_MODE_TEXT}");
        }
        this.mContentShowMode = i2;
        invalidate();
    }

    public void setInputBoxStyle(int i2) {
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            throw new IllegalArgumentException("the value of the parameter must be one of{1:INPUT_BOX_STYLE_CONNECT}, {2:INPUT_BOX_STYLE_SINGLE} or {3:INPUT_BOX_STYLE_UNDERLINE}");
        }
        this.mInputBoxStyle = i2;
        requestLayout();
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.inputListener = onInputListener;
    }

    public SplitEditTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SplitEditTextView(Context context, AttributeSet attributeSet, int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        super(context, attributeSet, i2);
        initAttrs(context, attributeSet);
    }
}
