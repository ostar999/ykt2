package me.dkzwm.widget.srl.extra.footer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.indicator.IIndicator;
import me.dkzwm.widget.srl.utils.PixelUtl;

/* loaded from: classes9.dex */
public class MaterialFooter<T extends IIndicator> extends View implements IRefreshView<T> {
    private float mBarExtraLength;
    private Paint mBarPaint;
    private int mBarWidth;
    private int mCircleRadius;
    private int mColorIndex;
    private int[] mColors;
    protected int mDefaultHeightInDP;
    private boolean mFromFront;
    private boolean mGrowing;
    private double mGrowingTime;
    private boolean mIsSpinning;
    private long mLastDrawProgressTime;
    private boolean mMustInvalidate;
    private float mProgress;
    private RectF mProgressBounds;
    protected int mStyle;

    public MaterialFooter(Context context) {
        this(context, null);
    }

    private void reset() {
        this.mMustInvalidate = false;
        this.mLastDrawProgressTime = 0L;
        this.mGrowingTime = 0.0d;
        this.mFromFront = true;
        this.mBarExtraLength = 0.0f;
        this.mProgress = 0.0f;
        this.mColorIndex = 0;
        this.mIsSpinning = false;
        this.mBarPaint.setColor(this.mColors[0]);
        invalidate();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getCustomHeight() {
        return PixelUtl.dp2px(getContext(), this.mDefaultHeightInDP);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getStyle() {
        return this.mStyle;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getType() {
        return 1;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    @NonNull
    public View getView() {
        return this;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f2;
        super.onDraw(canvas);
        if (!this.mMustInvalidate) {
            this.mColorIndex = 0;
        }
        if (this.mIsSpinning) {
            long jUptimeMillis = this.mLastDrawProgressTime > 0 ? SystemClock.uptimeMillis() - this.mLastDrawProgressTime : 0L;
            float f3 = (jUptimeMillis * 180.0f) / 1000.0f;
            double d3 = this.mGrowingTime + jUptimeMillis;
            this.mGrowingTime = d3;
            if (d3 > 600.0d) {
                this.mGrowingTime = d3 % 600.0d;
                this.mFromFront = !this.mFromFront;
            }
            float fCos = (((float) Math.cos(((this.mGrowingTime / 600.0d) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
            float f4 = 254;
            if (this.mFromFront) {
                f2 = fCos * f4;
            } else {
                f2 = f4 * (1.0f - fCos);
                this.mProgress += this.mBarExtraLength - f2;
            }
            float f5 = this.mProgress + f3;
            this.mProgress = f5;
            if (f5 > 360.0f) {
                this.mProgress = f5 - 360.0f;
            }
            this.mLastDrawProgressTime = SystemClock.uptimeMillis();
            float f6 = this.mBarExtraLength;
            float f7 = f4 / 2.0f;
            if (f6 < f7 && f2 < f7 && ((f2 > f6 && !this.mGrowing) || (f2 < f6 && this.mGrowing))) {
                Paint paint = this.mBarPaint;
                int[] iArr = this.mColors;
                paint.setColor(iArr[this.mColorIndex % iArr.length]);
                this.mColorIndex++;
            }
            this.mGrowing = f2 > this.mBarExtraLength;
            this.mBarExtraLength = f2;
            canvas.drawArc(this.mProgressBounds, this.mProgress - 90.0f, 16 + f2, false, this.mBarPaint);
            canvas.save();
        } else {
            canvas.drawArc(this.mProgressBounds, 270.0f, this.mProgress * 360.0f, false, this.mBarPaint);
        }
        if (this.mMustInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onFingerUp(SmoothRefreshLayout smoothRefreshLayout, T t2) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onPureScrollPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        if (t2.hasJustLeftStartPosition()) {
            this.mIsSpinning = false;
            this.mMustInvalidate = false;
            this.mProgress = 1.0f;
            invalidate();
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshBegin(SmoothRefreshLayout smoothRefreshLayout, T t2) {
        this.mProgress = 1.0f;
        this.mIsSpinning = true;
        this.mMustInvalidate = true;
        this.mColorIndex = 0;
        invalidate();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshComplete(SmoothRefreshLayout smoothRefreshLayout, boolean z2) {
        this.mMustInvalidate = false;
        this.mProgress = 1.0f;
        this.mIsSpinning = false;
        invalidate();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPositionChanged(SmoothRefreshLayout smoothRefreshLayout, byte b3, T t2) {
        float fMin = Math.min(1.0f, t2.getCurrentPercentOfLoadMoreOffset());
        if (b3 == 2) {
            this.mIsSpinning = false;
            this.mMustInvalidate = false;
            this.mProgress = fMin;
            invalidate();
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPrepare(SmoothRefreshLayout smoothRefreshLayout) {
        reset();
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onReset(SmoothRefreshLayout smoothRefreshLayout) {
        reset();
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        RectF rectF = this.mProgressBounds;
        int i6 = i2 / 2;
        int i7 = this.mCircleRadius;
        int i8 = this.mBarWidth;
        int i9 = i3 / 2;
        rectF.set((i6 - i7) - i8, (i9 - i7) - i8, i6 + i7 + i8, i9 + i7 + i8);
    }

    public void setDefaultHeightInDP(@IntRange(from = 0) int i2) {
        this.mDefaultHeightInDP = i2;
        requestLayout();
    }

    public void setProgressBarColors(@NonNull int[] iArr) {
        this.mColors = iArr;
        invalidate();
    }

    public void setProgressBarRadius(int i2) {
        this.mCircleRadius = i2;
        if (this.mStyle == 1) {
            requestLayout();
        } else {
            invalidate();
        }
    }

    public void setProgressBarWidth(int i2) {
        this.mBarWidth = i2;
        this.mBarPaint.setStrokeWidth(i2);
        if (this.mStyle == 1) {
            requestLayout();
        } else {
            invalidate();
        }
    }

    public void setStyle(int i2) {
        this.mStyle = i2;
        requestLayout();
    }

    public MaterialFooter(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialFooter(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mStyle = 0;
        this.mDefaultHeightInDP = 64;
        this.mColors = new int[]{SupportMenu.CATEGORY_MASK, -16776961, -16711936, -16777216};
        this.mBarPaint = new Paint(1);
        this.mProgressBounds = new RectF();
        this.mProgress = 0.0f;
        this.mColorIndex = 0;
        this.mFromFront = true;
        this.mGrowing = false;
        this.mGrowingTime = 0.0d;
        this.mBarExtraLength = 0.0f;
        this.mLastDrawProgressTime = 0L;
        this.mIsSpinning = false;
        int iDp2px = PixelUtl.dp2px(context, 3.0f);
        this.mBarWidth = iDp2px;
        this.mCircleRadius = iDp2px * 4;
        this.mBarPaint.setStyle(Paint.Style.STROKE);
        this.mBarPaint.setDither(true);
        this.mBarPaint.setStrokeWidth(this.mBarWidth);
    }
}
