package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class SlideDrawable extends Drawable implements Drawable.Callback {
    private boolean mIsRtl;
    private float mOffset;
    private final Rect mTmpRect = new Rect();
    private Drawable mWrapped;

    public SlideDrawable(Drawable wrapped) {
        this.mWrapped = wrapped;
    }

    @Override // android.graphics.drawable.Drawable
    public void clearColorFilter() {
        this.mWrapped.clearColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mWrapped.copyBounds(this.mTmpRect);
        canvas.save();
        if (this.mIsRtl) {
            canvas.translate(this.mTmpRect.width() * 0.33333334f * this.mOffset, 0.0f);
        } else {
            canvas.translate(this.mTmpRect.width() * 0.33333334f * (-this.mOffset), 0.0f);
        }
        this.mWrapped.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mWrapped.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return super.getConstantState();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable getCurrent() {
        return this.mWrapped.getCurrent();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mWrapped.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWrapped.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.mWrapped.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.mWrapped.getMinimumWidth();
    }

    public float getOffset() {
        return this.mOffset;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mWrapped.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect padding) {
        return this.mWrapped.getPadding(padding);
    }

    @Override // android.graphics.drawable.Drawable
    public int[] getState() {
        return this.mWrapped.getState();
    }

    @Override // android.graphics.drawable.Drawable
    public Region getTransparentRegion() {
        return this.mWrapped.getTransparentRegion();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        if (who == this.mWrapped) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mWrapped.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mWrapped.setBounds(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] state) {
        this.mWrapped.setState(state);
        return super.onStateChange(state);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (who == this.mWrapped) {
            scheduleSelf(what, when);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mWrapped.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setChangingConfigurations(int configs) {
        this.mWrapped.setChangingConfigurations(configs);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.mWrapped.setColorFilter(cf);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean dither) {
        this.mWrapped.setDither(dither);
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean filter) {
        this.mWrapped.setFilterBitmap(filter);
    }

    public void setIsRtl(boolean isRtl) {
        this.mIsRtl = isRtl;
        invalidateSelf();
    }

    public void setOffset(float offset) {
        this.mOffset = offset;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setState(int[] stateSet) {
        return this.mWrapped.setState(stateSet);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisible(visible, restart);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (who == this.mWrapped) {
            unscheduleSelf(what);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(int color, PorterDuff.Mode mode) {
        this.mWrapped.setColorFilter(color, mode);
    }
}
