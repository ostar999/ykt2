package com.umeng.socialize.shareboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;

/* loaded from: classes6.dex */
public class SocializeImageView extends ImageButton {
    public static int BG_SHAPE_CIRCULAR = 1;
    public static int BG_SHAPE_NONE = 0;
    public static int BG_SHAPE_ROUNDED_SQUARE = 2;
    private int mAngle;
    private int mBgShape;
    private int mIconPressedColor;
    private boolean mIsPressEffect;
    private boolean mIsSelected;
    private int mNormalColor;
    protected Paint mNormalPaint;
    private int mPressedColor;
    protected Paint mPressedPaint;
    private RectF mSquareRect;

    public SocializeImageView(Context context) {
        super(context);
        init();
    }

    private void drawCircle(Canvas canvas, Paint paint) {
        float measuredWidth = getMeasuredWidth() / 2;
        canvas.drawCircle(measuredWidth, measuredWidth, measuredWidth, paint);
    }

    private void drawRect(Canvas canvas, Paint paint) {
        if (this.mSquareRect == null) {
            RectF rectF = new RectF();
            this.mSquareRect = rectF;
            rectF.left = 0.0f;
            rectF.top = 0.0f;
            rectF.right = getMeasuredWidth();
            this.mSquareRect.bottom = getMeasuredWidth();
        }
        RectF rectF2 = this.mSquareRect;
        int i2 = this.mAngle;
        canvas.drawRoundRect(rectF2, i2, i2, paint);
    }

    private void init() {
        setBackground(null);
        setClickable(false);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public int dip2px(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mIsPressEffect) {
            if (!isPressed()) {
                if (BG_SHAPE_NONE == this.mBgShape) {
                    clearColorFilter();
                    return;
                } else {
                    this.mIsSelected = false;
                    invalidate();
                    return;
                }
            }
            if (BG_SHAPE_NONE != this.mBgShape) {
                this.mIsSelected = true;
                invalidate();
            } else {
                int i2 = this.mIconPressedColor;
                if (i2 != 0) {
                    setColorFilter(i2, PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Paint paint;
        int i2 = this.mBgShape;
        if (i2 == BG_SHAPE_NONE) {
            super.onDraw(canvas);
            return;
        }
        if (this.mIsSelected) {
            if (this.mIsPressEffect && (paint = this.mPressedPaint) != null) {
                if (i2 == BG_SHAPE_CIRCULAR) {
                    drawCircle(canvas, paint);
                } else if (i2 == BG_SHAPE_ROUNDED_SQUARE) {
                    drawRect(canvas, paint);
                }
            }
        } else if (i2 == BG_SHAPE_CIRCULAR) {
            drawCircle(canvas, this.mNormalPaint);
        } else if (i2 == BG_SHAPE_ROUNDED_SQUARE) {
            drawRect(canvas, this.mNormalPaint);
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        setBackgroundColor(i2, 0);
    }

    public void setBackgroundShape(int i2) {
        setBackgroundShape(i2, 0);
    }

    public void setPressEffectEnable(boolean z2) {
        this.mIsPressEffect = z2;
    }

    public void setPressedColor(int i2) {
        setPressEffectEnable(i2 != 0);
        this.mIconPressedColor = i2;
    }

    public void setBackgroundColor(int i2, int i3) {
        this.mNormalColor = i2;
        this.mPressedColor = i3;
        setPressEffectEnable(i3 != 0);
        if (this.mNormalColor != 0) {
            Paint paint = new Paint();
            this.mNormalPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mNormalPaint.setAntiAlias(true);
            this.mNormalPaint.setColor(i2);
        }
        if (this.mPressedColor != 0) {
            Paint paint2 = new Paint();
            this.mPressedPaint = paint2;
            paint2.setStyle(Paint.Style.FILL);
            this.mPressedPaint.setAntiAlias(true);
            this.mPressedPaint.setColor(i3);
        }
    }

    public void setBackgroundShape(int i2, int i3) {
        this.mBgShape = i2;
        if (i2 != BG_SHAPE_ROUNDED_SQUARE) {
            this.mAngle = 0;
        } else {
            this.mAngle = (int) ((i3 * getResources().getDisplayMetrics().density) + 0.5f);
        }
    }

    public SocializeImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SocializeImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }

    @TargetApi(21)
    public SocializeImageView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        init();
    }
}
