package com.easefun.polyv.livecommon.module.utils.span;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.util.TypedValue;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public class PLVRadiusBackgroundSpan extends ReplacementSpan {
    private int mBgColor;
    private float mBgHeight;
    private Paint mBgPaint;
    private float mBgWidth;
    private Context mContext;
    private float mRadius;
    private float mRightMargin;
    private String mText;
    private int mTextColor;
    private Paint mTextPaint;
    private float mTextSize;

    public PLVRadiusBackgroundSpan(Context context, int bgColor, int textColor, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        initDefaultValue(context, bgColor, textColor, text);
        this.mBgWidth = caculateBgWidth(text);
        initPaint();
    }

    private float caculateBgWidth(String text) {
        if (text.length() <= 1) {
            return this.mBgHeight;
        }
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(this.mTextSize);
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width() + (TypedValue.applyDimension(1, 4.0f, this.mContext.getResources().getDisplayMetrics()) * 2.0f);
    }

    private void initDefaultValue(Context context, int bgColor, int textColor, String text) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mBgColor = bgColor;
        this.mText = text;
        this.mBgHeight = TypedValue.applyDimension(1, 12.0f, applicationContext.getResources().getDisplayMetrics());
        this.mRightMargin = TypedValue.applyDimension(1, 6.0f, this.mContext.getResources().getDisplayMetrics());
        this.mRadius = TypedValue.applyDimension(1, 6.0f, this.mContext.getResources().getDisplayMetrics());
        this.mTextSize = TypedValue.applyDimension(2, 10.0f, this.mContext.getResources().getDisplayMetrics());
        this.mTextColor = textColor;
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setColor(this.mBgColor);
        this.mBgPaint.setStyle(Paint.Style.FILL);
        this.mBgPaint.setAntiAlias(true);
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setColor(this.mTextColor);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
        Paint paint2 = new Paint();
        paint2.setColor(this.mBgColor);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f2 = fontMetrics.descent;
        float f3 = fontMetrics.ascent;
        float f4 = y2 + (((f2 - f3) - this.mBgHeight) / 2.0f) + f3;
        RectF rectF = new RectF(x2, f4, this.mBgWidth + x2, this.mBgHeight + f4);
        float f5 = this.mRadius;
        canvas.drawRoundRect(rectF, f5, f5, paint2);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(this.mTextColor);
        textPaint.setTextSize(this.mTextSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics2 = textPaint.getFontMetrics();
        float f6 = fontMetrics2.bottom;
        float f7 = fontMetrics2.top;
        canvas.drawText(this.mText, x2 + (this.mBgWidth / 2.0f), (f4 + ((this.mBgHeight - (f6 - f7)) / 2.0f)) - f7, textPaint);
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (this.mBgWidth + this.mRightMargin);
    }

    public float getmBgHeight() {
        return this.mBgHeight;
    }

    public float getmRadius() {
        return this.mRadius;
    }

    public float getmTextSize() {
        return this.mTextSize;
    }

    public void setRightMarginDpValue(int rightMarginDpValue) {
        this.mRightMargin = TypedValue.applyDimension(1, rightMarginDpValue, this.mContext.getResources().getDisplayMetrics());
    }

    public void setmBgHeight(float mBgHeight) {
        this.mBgHeight = mBgHeight;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }
}
