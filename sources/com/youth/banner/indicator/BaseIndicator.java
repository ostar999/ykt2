package com.youth.banner.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.badge.BadgeDrawable;
import com.youth.banner.config.IndicatorConfig;

/* loaded from: classes8.dex */
public class BaseIndicator extends View implements Indicator {
    protected IndicatorConfig config;
    protected Paint mPaint;
    protected float offset;

    public BaseIndicator(Context context) {
        this(context, null);
    }

    @Override // com.youth.banner.indicator.Indicator
    public IndicatorConfig getIndicatorConfig() {
        return this.config;
    }

    @Override // com.youth.banner.indicator.Indicator
    @NonNull
    public View getIndicatorView() {
        if (this.config.isAttachToBanner()) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            int gravity = this.config.getGravity();
            if (gravity == 0) {
                layoutParams.gravity = BadgeDrawable.BOTTOM_START;
            } else if (gravity == 1) {
                layoutParams.gravity = 81;
            } else if (gravity == 2) {
                layoutParams.gravity = BadgeDrawable.BOTTOM_END;
            }
            layoutParams.leftMargin = this.config.getMargins().leftMargin;
            layoutParams.rightMargin = this.config.getMargins().rightMargin;
            layoutParams.topMargin = this.config.getMargins().topMargin;
            layoutParams.bottomMargin = this.config.getMargins().bottomMargin;
            setLayoutParams(layoutParams);
        }
        return this;
    }

    @Override // com.youth.banner.indicator.Indicator
    public void onPageChanged(int i2, int i3) {
        this.config.setIndicatorSize(i2);
        this.config.setCurrentPosition(i3);
        requestLayout();
    }

    public void onPageScrollStateChanged(int i2) {
    }

    public void onPageScrolled(int i2, float f2, int i3) {
        this.offset = f2;
        invalidate();
    }

    public void onPageSelected(int i2) {
        this.config.setCurrentPosition(i2);
        invalidate();
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.config = new IndicatorConfig();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setColor(0);
        this.mPaint.setColor(this.config.getNormalColor());
    }
}
