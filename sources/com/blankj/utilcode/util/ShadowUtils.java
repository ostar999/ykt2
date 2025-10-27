package com.blankj.utilcode.util;

import android.R;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public class ShadowUtils {
    private static final int SHADOW_TAG = -16;

    public static class Config {
        private static final int SHADOW_COLOR_DEFAULT = 1140850688;
        private static final int SHADOW_SIZE = UtilsBridge.dp2px(8.0f);
        private float mShadowRadius = -1.0f;
        private float mShadowSizeNormal = -1.0f;
        private float mShadowSizePressed = -1.0f;
        private float mShadowMaxSizeNormal = -1.0f;
        private float mShadowMaxSizePressed = -1.0f;
        private int mShadowColorNormal = SHADOW_COLOR_DEFAULT;
        private int mShadowColorPressed = SHADOW_COLOR_DEFAULT;
        private boolean isCircle = false;

        private float getShadowMaxSizeNormal() {
            if (this.mShadowMaxSizeNormal == -1.0f) {
                this.mShadowMaxSizeNormal = getShadowSizeNormal();
            }
            return this.mShadowMaxSizeNormal;
        }

        private float getShadowMaxSizePressed() {
            if (this.mShadowMaxSizePressed == -1.0f) {
                this.mShadowMaxSizePressed = getShadowSizePressed();
            }
            return this.mShadowMaxSizePressed;
        }

        private float getShadowRadius() {
            if (this.mShadowRadius < 0.0f) {
                this.mShadowRadius = 0.0f;
            }
            return this.mShadowRadius;
        }

        private float getShadowSizeNormal() {
            if (this.mShadowSizeNormal == -1.0f) {
                this.mShadowSizeNormal = SHADOW_SIZE;
            }
            return this.mShadowSizeNormal;
        }

        private float getShadowSizePressed() {
            if (this.mShadowSizePressed == -1.0f) {
                this.mShadowSizePressed = getShadowSizeNormal();
            }
            return this.mShadowSizePressed;
        }

        public Drawable apply(Drawable drawable) {
            if (drawable == null) {
                drawable = new ColorDrawable(0);
            }
            StateListDrawable stateListDrawable = new StateListDrawable();
            Drawable drawable2 = drawable;
            stateListDrawable.addState(new int[]{R.attr.state_pressed}, new ShadowDrawable(drawable2, getShadowRadius(), getShadowSizeNormal(), getShadowMaxSizeNormal(), this.mShadowColorPressed, this.isCircle));
            stateListDrawable.addState(StateSet.WILD_CARD, new ShadowDrawable(drawable2, getShadowRadius(), getShadowSizePressed(), getShadowMaxSizePressed(), this.mShadowColorNormal, this.isCircle));
            return stateListDrawable;
        }

        public Config setCircle() {
            this.isCircle = true;
            if (this.mShadowRadius == -1.0f) {
                return this;
            }
            throw new IllegalArgumentException("Set circle needn't set radius.");
        }

        public Config setShadowColor(int i2) {
            return setShadowColor(i2, i2);
        }

        public Config setShadowMaxSize(int i2) {
            return setShadowMaxSize(i2, i2);
        }

        public Config setShadowRadius(float f2) {
            this.mShadowRadius = f2;
            if (this.isCircle) {
                throw new IllegalArgumentException("Set circle needn't set radius.");
            }
            return this;
        }

        public Config setShadowSize(int i2) {
            return setShadowSize(i2, i2);
        }

        public Config setShadowColor(int i2, int i3) {
            this.mShadowColorNormal = i2;
            this.mShadowColorPressed = i3;
            return this;
        }

        public Config setShadowMaxSize(int i2, int i3) {
            this.mShadowMaxSizeNormal = i2;
            this.mShadowMaxSizePressed = i3;
            return this;
        }

        public Config setShadowSize(int i2, int i3) {
            this.mShadowSizeNormal = i2;
            this.mShadowSizePressed = i3;
            return this;
        }
    }

    public static class DrawableWrapper extends Drawable implements Drawable.Callback {
        private Drawable mDrawable;

        public DrawableWrapper(Drawable drawable) {
            setWrappedDrawable(drawable);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mDrawable.draw(canvas);
        }

        @Override // android.graphics.drawable.Drawable
        public int getChangingConfigurations() {
            return this.mDrawable.getChangingConfigurations();
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable getCurrent() {
            return this.mDrawable.getCurrent();
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mDrawable.getIntrinsicHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mDrawable.getIntrinsicWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumHeight() {
            return this.mDrawable.getMinimumHeight();
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumWidth() {
            return this.mDrawable.getMinimumWidth();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return this.mDrawable.getOpacity();
        }

        @Override // android.graphics.drawable.Drawable
        public boolean getPadding(Rect rect) {
            return this.mDrawable.getPadding(rect);
        }

        @Override // android.graphics.drawable.Drawable
        public int[] getState() {
            return this.mDrawable.getState();
        }

        @Override // android.graphics.drawable.Drawable
        public Region getTransparentRegion() {
            return this.mDrawable.getTransparentRegion();
        }

        public Drawable getWrappedDrawable() {
            return this.mDrawable;
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(Drawable drawable) {
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isAutoMirrored() {
            return DrawableCompat.isAutoMirrored(this.mDrawable);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return this.mDrawable.isStateful();
        }

        @Override // android.graphics.drawable.Drawable
        public void jumpToCurrentState() {
            DrawableCompat.jumpToCurrentState(this.mDrawable);
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            this.mDrawable.setBounds(rect);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean onLevelChange(int i2) {
            return this.mDrawable.setLevel(i2);
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
            scheduleSelf(runnable, j2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i2) {
            this.mDrawable.setAlpha(i2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAutoMirrored(boolean z2) {
            DrawableCompat.setAutoMirrored(this.mDrawable, z2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setChangingConfigurations(int i2) {
            this.mDrawable.setChangingConfigurations(i2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mDrawable.setColorFilter(colorFilter);
        }

        @Override // android.graphics.drawable.Drawable
        public void setDither(boolean z2) {
            this.mDrawable.setDither(z2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setFilterBitmap(boolean z2) {
            this.mDrawable.setFilterBitmap(z2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setHotspot(float f2, float f3) {
            DrawableCompat.setHotspot(this.mDrawable, f2, f3);
        }

        @Override // android.graphics.drawable.Drawable
        public void setHotspotBounds(int i2, int i3, int i4, int i5) {
            DrawableCompat.setHotspotBounds(this.mDrawable, i2, i3, i4, i5);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean setState(int[] iArr) {
            return this.mDrawable.setState(iArr);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTint(int i2) {
            DrawableCompat.setTint(this.mDrawable, i2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList colorStateList) {
            DrawableCompat.setTintList(this.mDrawable, colorStateList);
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintMode(PorterDuff.Mode mode) {
            DrawableCompat.setTintMode(this.mDrawable, mode);
        }

        @Override // android.graphics.drawable.Drawable
        public boolean setVisible(boolean z2, boolean z3) {
            return super.setVisible(z2, z3) || this.mDrawable.setVisible(z2, z3);
        }

        public void setWrappedDrawable(Drawable drawable) {
            Drawable drawable2 = this.mDrawable;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            this.mDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            unscheduleSelf(runnable);
        }
    }

    public static void apply(View... viewArr) {
        if (viewArr == null) {
            return;
        }
        for (View view : viewArr) {
            apply(view, new Config());
        }
    }

    public static void apply(View view, Config config) {
        if (view == null || config == null) {
            return;
        }
        Drawable background = view.getBackground();
        Object tag = view.getTag(-16);
        if (tag instanceof Drawable) {
            ViewCompat.setBackground(view, (Drawable) tag);
            return;
        }
        Drawable drawableApply = config.apply(background);
        ViewCompat.setBackground(view, drawableApply);
        view.setTag(-16, drawableApply);
    }

    public static class ShadowDrawable extends DrawableWrapper {
        private static final double COS_45 = Math.cos(Math.toRadians(45.0d));
        private boolean isCircle;
        private boolean mAddPaddingForCorners;
        private RectF mContentBounds;
        private float mCornerRadius;
        private Paint mCornerShadowPaint;
        private Path mCornerShadowPath;
        private boolean mDirty;
        private Paint mEdgeShadowPaint;
        private float mMaxShadowSize;
        private float mRawMaxShadowSize;
        private float mRawShadowSize;
        private float mRotation;
        private float mShadowBottomScale;
        private final int mShadowEndColor;
        private float mShadowHorizScale;
        private float mShadowMultiplier;
        private float mShadowSize;
        private final int mShadowStartColor;
        private float mShadowTopScale;

        public ShadowDrawable(Drawable drawable, float f2, float f3, float f4, int i2, boolean z2) {
            super(drawable);
            this.mShadowMultiplier = 1.0f;
            this.mShadowTopScale = 1.0f;
            this.mShadowHorizScale = 1.0f;
            this.mShadowBottomScale = 1.0f;
            this.mDirty = true;
            this.mAddPaddingForCorners = false;
            this.mShadowStartColor = i2;
            this.mShadowEndColor = i2 & 16777215;
            this.isCircle = z2;
            if (z2) {
                this.mShadowMultiplier = 1.0f;
                this.mShadowTopScale = 1.0f;
                this.mShadowHorizScale = 1.0f;
                this.mShadowBottomScale = 1.0f;
            }
            Paint paint = new Paint(5);
            this.mCornerShadowPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mCornerRadius = Math.round(f2);
            this.mContentBounds = new RectF();
            Paint paint2 = new Paint(this.mCornerShadowPaint);
            this.mEdgeShadowPaint = paint2;
            paint2.setAntiAlias(false);
            setShadowSize(f3, f4);
        }

        private void buildComponents(Rect rect) {
            if (this.isCircle) {
                this.mCornerRadius = rect.width() / 2;
            }
            float f2 = this.mRawMaxShadowSize;
            float f3 = this.mShadowMultiplier * f2;
            this.mContentBounds.set(rect.left + f2, rect.top + f3, rect.right - f2, rect.bottom - f3);
            Drawable wrappedDrawable = getWrappedDrawable();
            RectF rectF = this.mContentBounds;
            wrappedDrawable.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            buildShadowCorners();
        }

        private void buildShadowCorners() {
            if (!this.isCircle) {
                float f2 = this.mCornerRadius;
                RectF rectF = new RectF(-f2, -f2, f2, f2);
                RectF rectF2 = new RectF(rectF);
                float f3 = this.mShadowSize;
                rectF2.inset(-f3, -f3);
                Path path = this.mCornerShadowPath;
                if (path == null) {
                    this.mCornerShadowPath = new Path();
                } else {
                    path.reset();
                }
                this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
                this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f);
                this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
                this.mCornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
                this.mCornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
                this.mCornerShadowPath.close();
                float f4 = -rectF2.top;
                if (f4 > 0.0f) {
                    this.mCornerShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, f4, new int[]{0, this.mShadowStartColor, this.mShadowEndColor}, new float[]{0.0f, this.mCornerRadius / f4, 1.0f}, Shader.TileMode.CLAMP));
                }
                this.mEdgeShadowPaint.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, this.mShadowStartColor, this.mShadowEndColor, Shader.TileMode.CLAMP));
                this.mEdgeShadowPaint.setAntiAlias(false);
                return;
            }
            float fWidth = (this.mContentBounds.width() / 2.0f) - 1.0f;
            float f5 = -fWidth;
            RectF rectF3 = new RectF(f5, f5, fWidth, fWidth);
            RectF rectF4 = new RectF(rectF3);
            float f6 = this.mShadowSize;
            rectF4.inset(-f6, -f6);
            Path path2 = this.mCornerShadowPath;
            if (path2 == null) {
                this.mCornerShadowPath = new Path();
            } else {
                path2.reset();
            }
            this.mCornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
            this.mCornerShadowPath.moveTo(f5, 0.0f);
            this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
            this.mCornerShadowPath.arcTo(rectF4, 180.0f, 180.0f, false);
            this.mCornerShadowPath.arcTo(rectF4, 0.0f, 180.0f, false);
            this.mCornerShadowPath.arcTo(rectF3, 180.0f, 180.0f, false);
            this.mCornerShadowPath.arcTo(rectF3, 0.0f, 180.0f, false);
            this.mCornerShadowPath.close();
            float f7 = -rectF4.top;
            if (f7 > 0.0f) {
                this.mCornerShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, f7, new int[]{0, this.mShadowStartColor, this.mShadowEndColor}, new float[]{0.0f, fWidth / f7, 1.0f}, Shader.TileMode.CLAMP));
            }
        }

        private static float calculateHorizontalPadding(float f2, float f3, boolean z2) {
            return z2 ? (float) (f2 + ((1.0d - COS_45) * f3)) : f2;
        }

        private float calculateVerticalPadding(float f2, float f3, boolean z2) {
            return z2 ? (float) ((f2 * this.mShadowMultiplier) + ((1.0d - COS_45) * f3)) : f2 * this.mShadowMultiplier;
        }

        private void drawShadow(Canvas canvas) {
            int i2;
            float f2;
            int i3;
            float f3;
            float f4;
            float f5;
            if (this.isCircle) {
                int iSave = canvas.save();
                canvas.translate(this.mContentBounds.centerX(), this.mContentBounds.centerY());
                canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
                canvas.restoreToCount(iSave);
                return;
            }
            int iSave2 = canvas.save();
            canvas.rotate(this.mRotation, this.mContentBounds.centerX(), this.mContentBounds.centerY());
            float f6 = this.mCornerRadius;
            float f7 = (-f6) - this.mShadowSize;
            float f8 = f6 * 2.0f;
            boolean z2 = this.mContentBounds.width() - f8 > 0.0f;
            boolean z3 = this.mContentBounds.height() - f8 > 0.0f;
            float f9 = this.mRawShadowSize;
            float f10 = f9 - (this.mShadowTopScale * f9);
            float f11 = f9 - (this.mShadowHorizScale * f9);
            float f12 = f9 - (this.mShadowBottomScale * f9);
            float f13 = f6 == 0.0f ? 1.0f : f6 / (f11 + f6);
            float f14 = f6 == 0.0f ? 1.0f : f6 / (f10 + f6);
            float f15 = f6 == 0.0f ? 1.0f : f6 / (f12 + f6);
            int iSave3 = canvas.save();
            RectF rectF = this.mContentBounds;
            canvas.translate(rectF.left + f6, rectF.top + f6);
            canvas.scale(f13, f14);
            canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
            if (z2) {
                canvas.scale(1.0f / f13, 1.0f);
                i2 = iSave3;
                f2 = f15;
                i3 = iSave2;
                f3 = f14;
                canvas.drawRect(0.0f, f7, this.mContentBounds.width() - f8, -this.mCornerRadius, this.mEdgeShadowPaint);
            } else {
                i2 = iSave3;
                f2 = f15;
                i3 = iSave2;
                f3 = f14;
            }
            canvas.restoreToCount(i2);
            int iSave4 = canvas.save();
            RectF rectF2 = this.mContentBounds;
            canvas.translate(rectF2.right - f6, rectF2.bottom - f6);
            float f16 = f2;
            canvas.scale(f13, f16);
            canvas.rotate(180.0f);
            canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
            if (z2) {
                canvas.scale(1.0f / f13, 1.0f);
                f4 = f3;
                f5 = f16;
                canvas.drawRect(0.0f, f7, this.mContentBounds.width() - f8, -this.mCornerRadius, this.mEdgeShadowPaint);
            } else {
                f4 = f3;
                f5 = f16;
            }
            canvas.restoreToCount(iSave4);
            int iSave5 = canvas.save();
            RectF rectF3 = this.mContentBounds;
            canvas.translate(rectF3.left + f6, rectF3.bottom - f6);
            canvas.scale(f13, f5);
            canvas.rotate(270.0f);
            canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
            if (z3) {
                canvas.scale(1.0f / f5, 1.0f);
                canvas.drawRect(0.0f, f7, this.mContentBounds.height() - f8, -this.mCornerRadius, this.mEdgeShadowPaint);
            }
            canvas.restoreToCount(iSave5);
            int iSave6 = canvas.save();
            RectF rectF4 = this.mContentBounds;
            canvas.translate(rectF4.right - f6, rectF4.top + f6);
            float f17 = f4;
            canvas.scale(f13, f17);
            canvas.rotate(90.0f);
            canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
            if (z3) {
                canvas.scale(1.0f / f17, 1.0f);
                canvas.drawRect(0.0f, f7, this.mContentBounds.height() - f8, -this.mCornerRadius, this.mEdgeShadowPaint);
            }
            canvas.restoreToCount(iSave6);
            canvas.restoreToCount(i3);
        }

        private static int toEven(float f2) {
            int iRound = Math.round(f2);
            return iRound % 2 == 1 ? iRound - 1 : iRound;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.mDirty) {
                buildComponents(getBounds());
                this.mDirty = false;
            }
            drawShadow(canvas);
            super.draw(canvas);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getChangingConfigurations() {
            return super.getChangingConfigurations();
        }

        public float getCornerRadius() {
            return this.mCornerRadius;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ Drawable getCurrent() {
            return super.getCurrent();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getIntrinsicHeight() {
            return super.getIntrinsicHeight();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getIntrinsicWidth() {
            return super.getIntrinsicWidth();
        }

        public float getMaxShadowSize() {
            return this.mRawMaxShadowSize;
        }

        public float getMinHeight() {
            float f2 = this.mRawMaxShadowSize;
            return (Math.max(f2, this.mCornerRadius + ((this.mShadowMultiplier * f2) / 2.0f)) * 2.0f) + (this.mRawMaxShadowSize * this.mShadowMultiplier * 2.0f);
        }

        public float getMinWidth() {
            float f2 = this.mRawMaxShadowSize;
            return (Math.max(f2, this.mCornerRadius + (f2 / 2.0f)) * 2.0f) + (this.mRawMaxShadowSize * 2.0f);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getMinimumHeight() {
            return super.getMinimumHeight();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int getMinimumWidth() {
            return super.getMinimumWidth();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public boolean getPadding(Rect rect) {
            int iCeil = (int) Math.ceil(calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
            int iCeil2 = (int) Math.ceil(calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
            rect.set(iCeil2, iCeil, iCeil2, iCeil);
            return true;
        }

        public float getShadowSize() {
            return this.mRawShadowSize;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ int[] getState() {
            return super.getState();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ Region getTransparentRegion() {
            return super.getTransparentRegion();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper
        public /* bridge */ /* synthetic */ Drawable getWrappedDrawable() {
            return super.getWrappedDrawable();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void invalidateDrawable(Drawable drawable) {
            super.invalidateDrawable(drawable);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean isAutoMirrored() {
            return super.isAutoMirrored();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean isStateful() {
            return super.isStateful();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void jumpToCurrentState() {
            super.jumpToCurrentState();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            this.mDirty = true;
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
            super.scheduleDrawable(drawable, runnable, j2);
        }

        public void setAddPaddingForCorners(boolean z2) {
            this.mAddPaddingForCorners = z2;
            invalidateSelf();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public void setAlpha(int i2) {
            super.setAlpha(i2);
            this.mCornerShadowPaint.setAlpha(i2);
            this.mEdgeShadowPaint.setAlpha(i2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setAutoMirrored(boolean z2) {
            super.setAutoMirrored(z2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setChangingConfigurations(int i2) {
            super.setChangingConfigurations(i2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
            super.setColorFilter(colorFilter);
        }

        public void setCornerRadius(float f2) {
            float fRound = Math.round(f2);
            if (this.mCornerRadius == fRound) {
                return;
            }
            this.mCornerRadius = fRound;
            this.mDirty = true;
            invalidateSelf();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setDither(boolean z2) {
            super.setDither(z2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setFilterBitmap(boolean z2) {
            super.setFilterBitmap(z2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setHotspot(float f2, float f3) {
            super.setHotspot(f2, f3);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setHotspotBounds(int i2, int i3, int i4, int i5) {
            super.setHotspotBounds(i2, i3, i4, i5);
        }

        public void setMaxShadowSize(float f2) {
            setShadowSize(this.mRawShadowSize, f2);
        }

        public final void setRotation(float f2) {
            if (this.mRotation != f2) {
                this.mRotation = f2;
                invalidateSelf();
            }
        }

        public void setShadowSize(float f2, float f3) {
            if (f2 < 0.0f || f3 < 0.0f) {
                throw new IllegalArgumentException("invalid shadow size");
            }
            float even = toEven(f2);
            float even2 = toEven(f3);
            if (even > even2) {
                even = even2;
            }
            if (this.mRawShadowSize == even && this.mRawMaxShadowSize == even2) {
                return;
            }
            this.mRawShadowSize = even;
            this.mRawMaxShadowSize = even2;
            this.mShadowSize = Math.round(even * this.mShadowMultiplier);
            this.mMaxShadowSize = even2;
            this.mDirty = true;
            invalidateSelf();
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean setState(int[] iArr) {
            return super.setState(iArr);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTint(int i2) {
            super.setTint(i2);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ void setTintMode(PorterDuff.Mode mode) {
            super.setTintMode(mode);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable
        public /* bridge */ /* synthetic */ boolean setVisible(boolean z2, boolean z3) {
            return super.setVisible(z2, z3);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper
        public /* bridge */ /* synthetic */ void setWrappedDrawable(Drawable drawable) {
            super.setWrappedDrawable(drawable);
        }

        @Override // com.blankj.utilcode.util.ShadowUtils.DrawableWrapper, android.graphics.drawable.Drawable.Callback
        public /* bridge */ /* synthetic */ void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            super.unscheduleDrawable(drawable, runnable);
        }

        public void setShadowSize(float f2) {
            setShadowSize(f2, this.mRawMaxShadowSize);
        }
    }
}
