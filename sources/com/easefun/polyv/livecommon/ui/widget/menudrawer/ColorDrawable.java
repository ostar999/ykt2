package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
class ColorDrawable extends Drawable {
    private final Paint mPaint;
    private ColorState mState;

    public static final class ColorState extends Drawable.ConstantState {
        int mBaseColor;
        int mChangingConfigurations;
        int mUseColor;

        public ColorState(ColorState state) {
            if (state != null) {
                this.mBaseColor = state.mBaseColor;
                this.mUseColor = state.mUseColor;
            }
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new ColorDrawable(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources res) {
            return new ColorDrawable(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int i2 = this.mState.mUseColor;
        if ((i2 >>> 24) != 0) {
            this.mPaint.setColor(i2);
            canvas.drawRect(getBounds(), this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mState.mUseColor >>> 24;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mState.mChangingConfigurations;
    }

    public int getColor() {
        return this.mState.mUseColor;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        this.mState.mChangingConfigurations = getChangingConfigurations();
        return this.mState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        int i2 = this.mState.mUseColor >>> 24;
        if (i2 != 0) {
            return i2 != 255 ? -3 : -1;
        }
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        ColorState colorState = this.mState;
        int i2 = colorState.mBaseColor;
        int i3 = colorState.mUseColor;
        int i4 = ((((i2 >>> 24) * (alpha + (alpha >> 7))) >> 8) << 24) | ((i2 << 8) >>> 8);
        colorState.mUseColor = i4;
        if (i3 != i4) {
            invalidateSelf();
        }
    }

    public void setColor(int color) {
        ColorState colorState = this.mState;
        if (colorState.mBaseColor == color && colorState.mUseColor == color) {
            return;
        }
        invalidateSelf();
        ColorState colorState2 = this.mState;
        colorState2.mUseColor = color;
        colorState2.mBaseColor = color;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public ColorDrawable() {
        this((ColorState) null);
    }

    public ColorDrawable(int color) {
        this((ColorState) null);
        setColor(color);
    }

    private ColorDrawable(ColorState state) {
        this.mPaint = new Paint();
        this.mState = new ColorState(state);
    }
}
