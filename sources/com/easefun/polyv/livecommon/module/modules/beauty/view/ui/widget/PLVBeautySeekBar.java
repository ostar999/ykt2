package com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVBeautySeekBar extends AppCompatSeekBar {
    private final float[] backgroundShadowRadii;
    private boolean drawShadow;
    private int indicatorProgress;
    private final int indicatorRadius;
    private final float[] progressShadowRadii;
    private final Paint shadowPaint;
    private final Path shadowPath;
    private final int shadowRadius;
    private final RectF shadowRect;
    private final float shadowWidth;
    private final int splitTrackMargin;
    private final Rect thumbRectWithSplitTrackMargin;

    public PLVBeautySeekBar(Context context) {
        super(context);
        this.splitTrackMargin = ConvertUtils.dp2px(2.0f);
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.shadowWidth = ConvertUtils.dp2px(0.5f);
        int iDp2px = ConvertUtils.dp2px(3.0f);
        this.shadowRadius = iDp2px;
        this.shadowPaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautySeekBar.1
            {
                setColor(0);
                setAntiAlias(true);
                setShadowLayer(PLVBeautySeekBar.this.shadowWidth, 0.0f, 0.0f, PLVFormatUtils.parseColor("#66000000"));
                setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            }
        };
        this.progressShadowRadii = new float[]{iDp2px, iDp2px, 0.0f, 0.0f, 0.0f, 0.0f, iDp2px, iDp2px};
        this.backgroundShadowRadii = new float[]{0.0f, 0.0f, iDp2px, iDp2px, iDp2px, iDp2px, 0.0f, 0.0f};
        this.thumbRectWithSplitTrackMargin = new Rect();
        this.shadowRect = new RectF();
        this.shadowPath = new Path();
        this.indicatorProgress = -1;
        this.drawShadow = true;
        initView(null);
    }

    private void drawIndicator(Canvas canvas) {
        int min = Build.VERSION.SDK_INT >= 26 ? getMin() : 0;
        int i2 = this.indicatorProgress;
        if (i2 < min || i2 > getMax()) {
            return;
        }
        int iSave = canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        int color = this.shadowPaint.getColor();
        Xfermode xfermode = this.shadowPaint.getXfermode();
        this.shadowPaint.setColor(-1);
        this.shadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawCircle(((((getWidth() - getPaddingLeft()) - getPaddingRight()) * (this.indicatorProgress - min)) * 1.0f) / (getMax() - min), ((getHeight() - getPaddingTop()) - getPaddingBottom()) / 2.0f, this.indicatorRadius, this.shadowPaint);
        this.shadowPaint.setColor(color);
        this.shadowPaint.setXfermode(xfermode);
        canvas.restoreToCount(iSave);
    }

    private void drawThumb(Canvas canvas) {
        int iSave = canvas.save();
        canvas.translate(getPaddingLeft() - getThumbOffset(), getPaddingTop());
        getThumb().draw(canvas);
        if (this.drawShadow) {
            Rect bounds = getThumb().getBounds();
            RectF rectF = this.shadowRect;
            float f2 = bounds.left;
            float f3 = this.shadowWidth;
            rectF.set(f2 - f3, bounds.top - f3, bounds.right + f3, bounds.bottom + f3);
            canvas.drawOval(this.shadowRect, this.shadowPaint);
        }
        canvas.restoreToCount(iSave);
    }

    private void drawTrackShadow(Canvas canvas) {
        Drawable progressDrawable;
        if (this.drawShadow && (progressDrawable = getProgressDrawable()) != null) {
            int iSave = canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop());
            Rect bounds = progressDrawable.getBounds();
            RectF rectF = this.shadowRect;
            float f2 = bounds.left;
            float f3 = this.shadowWidth;
            float f4 = f2 - f3;
            float f5 = bounds.top - f3;
            float thumbOffset = this.thumbRectWithSplitTrackMargin.left - getThumbOffset();
            float f6 = this.shadowWidth;
            rectF.set(f4, f5, thumbOffset + f6, bounds.bottom + f6);
            RectF rectF2 = this.shadowRect;
            if (rectF2.right > rectF2.left) {
                this.shadowPath.reset();
                this.shadowPath.addRoundRect(this.shadowRect, this.progressShadowRadii, Path.Direction.CCW);
                canvas.drawPath(this.shadowPath, this.shadowPaint);
            }
            RectF rectF3 = this.shadowRect;
            float thumbOffset2 = this.thumbRectWithSplitTrackMargin.right - getThumbOffset();
            float f7 = this.shadowWidth;
            rectF3.set(thumbOffset2 - f7, bounds.top - f7, bounds.right + f7, bounds.bottom + f7);
            RectF rectF4 = this.shadowRect;
            if (rectF4.right > rectF4.left) {
                this.shadowPath.reset();
                this.shadowPath.addRoundRect(this.shadowRect, this.backgroundShadowRadii, Path.Direction.CCW);
                canvas.drawPath(this.shadowPath, this.shadowPaint);
            }
            canvas.restoreToCount(iSave);
        }
    }

    private void initView(@Nullable AttributeSet attributeSet) {
        setLayerType(1, null);
        parseAttrs(attributeSet);
    }

    private void parseAttrs(@Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.PLVBeautySeekBar);
        this.drawShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PLVBeautySeekBar_plvDrawShadow, this.drawShadow);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void splitTrackAndNotDrawThumb(Canvas canvas) {
        canvas.translate(getPaddingLeft() - getThumbOffset(), getPaddingTop());
        Rect bounds = getThumb().getBounds();
        Rect rect = this.thumbRectWithSplitTrackMargin;
        int i2 = bounds.left;
        int i3 = this.splitTrackMargin;
        rect.set(i2 - i3, bounds.top, bounds.right + i3, bounds.bottom);
        if (Build.VERSION.SDK_INT >= 26) {
            canvas.clipOutRect(this.thumbRectWithSplitTrackMargin);
        } else {
            canvas.clipRect(this.thumbRectWithSplitTrackMargin, Region.Op.DIFFERENCE);
        }
        canvas.translate(-(getPaddingLeft() - getThumbOffset()), -getPaddingTop());
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public synchronized void onDraw(Canvas canvas) {
        if (getThumb() == null) {
            super.onDraw(canvas);
            return;
        }
        int iSave = canvas.save();
        splitTrackAndNotDrawThumb(canvas);
        super.onDraw(canvas);
        canvas.restoreToCount(iSave);
        drawTrackShadow(canvas);
        drawIndicator(canvas);
        drawThumb(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        if (this.drawShadow && View.MeasureSpec.getMode(heightMeasureSpec) == Integer.MIN_VALUE) {
            float f2 = measuredHeight;
            if ((this.shadowWidth * 2.0f) + f2 < View.MeasureSpec.getSize(heightMeasureSpec)) {
                setMeasuredDimension(getMeasuredWidth(), (int) (f2 + (this.shadowWidth * 2.0f)));
            }
        }
    }

    public void setDrawShadow(boolean drawShadow) {
        this.drawShadow = drawShadow;
        requestLayout();
    }

    public void setIndicatorProgress(int indicatorProgress) {
        this.indicatorProgress = indicatorProgress;
        invalidate();
    }

    public PLVBeautySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.splitTrackMargin = ConvertUtils.dp2px(2.0f);
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.shadowWidth = ConvertUtils.dp2px(0.5f);
        int iDp2px = ConvertUtils.dp2px(3.0f);
        this.shadowRadius = iDp2px;
        this.shadowPaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautySeekBar.1
            {
                setColor(0);
                setAntiAlias(true);
                setShadowLayer(PLVBeautySeekBar.this.shadowWidth, 0.0f, 0.0f, PLVFormatUtils.parseColor("#66000000"));
                setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            }
        };
        this.progressShadowRadii = new float[]{iDp2px, iDp2px, 0.0f, 0.0f, 0.0f, 0.0f, iDp2px, iDp2px};
        this.backgroundShadowRadii = new float[]{0.0f, 0.0f, iDp2px, iDp2px, iDp2px, iDp2px, 0.0f, 0.0f};
        this.thumbRectWithSplitTrackMargin = new Rect();
        this.shadowRect = new RectF();
        this.shadowPath = new Path();
        this.indicatorProgress = -1;
        this.drawShadow = true;
        initView(attrs);
    }

    public PLVBeautySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.splitTrackMargin = ConvertUtils.dp2px(2.0f);
        this.indicatorRadius = ConvertUtils.dp2px(2.0f);
        this.shadowWidth = ConvertUtils.dp2px(0.5f);
        int iDp2px = ConvertUtils.dp2px(3.0f);
        this.shadowRadius = iDp2px;
        this.shadowPaint = new Paint() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.view.ui.widget.PLVBeautySeekBar.1
            {
                setColor(0);
                setAntiAlias(true);
                setShadowLayer(PLVBeautySeekBar.this.shadowWidth, 0.0f, 0.0f, PLVFormatUtils.parseColor("#66000000"));
                setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            }
        };
        this.progressShadowRadii = new float[]{iDp2px, iDp2px, 0.0f, 0.0f, 0.0f, 0.0f, iDp2px, iDp2px};
        this.backgroundShadowRadii = new float[]{0.0f, 0.0f, iDp2px, iDp2px, iDp2px, iDp2px, 0.0f, 0.0f};
        this.thumbRectWithSplitTrackMargin = new Rect();
        this.shadowRect = new RectF();
        this.shadowPath = new Path();
        this.indicatorProgress = -1;
        this.drawShadow = true;
        initView(attrs);
    }
}
