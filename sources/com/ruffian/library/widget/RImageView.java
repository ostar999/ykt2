package com.ruffian.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.ruffian.library.widget.rounded.RoundDrawable;

/* loaded from: classes6.dex */
public class RImageView extends AppCompatImageView {
    private int mBorderColor;
    private float mBorderWidth;
    private ColorFilter mColorFilter;
    private float mCorner;
    private float mCornerBottomLeft;
    private float mCornerBottomRight;
    private float mCornerTopLeft;
    private float mCornerTopRight;
    private Drawable mDrawable;
    private boolean mIsCircle;
    private int mResource;
    private ImageView.ScaleType mScaleType;
    private PorterDuff.Mode mTintMode;

    public RImageView(Context context) {
        this(context, null);
    }

    private void drawEmptyBitmap() {
        if (this.mDrawable == null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            if (measuredWidth <= 0 || measuredHeight <= 0) {
                return;
            }
            Drawable background = getBackground();
            if (background == null) {
                setImageBitmap(Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ALPHA_8));
            } else {
                background.setBounds(0, 0, measuredWidth, measuredHeight);
                setImageDrawable(background);
            }
        }
    }

    private void initAttributeSet(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RImageView);
        this.mIsCircle = typedArrayObtainStyledAttributes.getBoolean(R.styleable.RImageView_is_circle, false);
        this.mCorner = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_corner_radius, -1);
        this.mCornerTopLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_corner_radius_top_left, 0);
        this.mCornerTopRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_corner_radius_top_right, 0);
        this.mCornerBottomLeft = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_corner_radius_bottom_left, 0);
        this.mCornerBottomRight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_corner_radius_bottom_right, 0);
        this.mBorderWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RImageView_border_width, 0);
        this.mBorderColor = typedArrayObtainStyledAttributes.getColor(R.styleable.RImageView_border_color, -16777216);
        attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "tint", 0);
        int attributeIntValue = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "tintMode", 0);
        if (attributeIntValue != 0) {
            this.mTintMode = wrapTintMode(attributeIntValue);
        }
        setImageTintList(getImageTintList());
        typedArrayObtainStyledAttributes.recycle();
        updateDrawableAttrs();
    }

    private Drawable resolveResource() throws Resources.NotFoundException {
        Resources resources = getResources();
        Drawable drawable = null;
        if (resources == null) {
            return null;
        }
        int i2 = this.mResource;
        if (i2 != 0) {
            try {
                drawable = resources.getDrawable(i2);
            } catch (Exception unused) {
                this.mResource = 0;
            }
        }
        return RoundDrawable.fromDrawable(drawable);
    }

    private void updateAttrs(Drawable drawable, ImageView.ScaleType scaleType) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof RoundDrawable) {
            ((RoundDrawable) drawable).setParams(scaleType, this.mBorderWidth, this.mBorderColor, this.mIsCircle, this.mCorner, this.mCornerTopLeft, this.mCornerTopRight, this.mCornerBottomLeft, this.mCornerBottomRight);
            return;
        }
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i2 = 0; i2 < numberOfLayers; i2++) {
                updateAttrs(layerDrawable.getDrawable(i2), scaleType);
            }
        }
    }

    private void updateDrawableAttrs() {
        updateAttrs(this.mDrawable, this.mScaleType);
        setColorFilter();
    }

    private PorterDuff.Mode wrapTintMode(int i2) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return PorterDuff.Mode.SRC_ATOP;
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public float getCorner() {
        return this.mCorner;
    }

    public float getCornerBottomLeft() {
        return this.mCornerBottomLeft;
    }

    public float getCornerBottomRight() {
        return this.mCornerBottomRight;
    }

    public float getCornerTopLeft() {
        return this.mCornerTopLeft;
    }

    public float getCornerTopRight() {
        return this.mCornerTopRight;
    }

    public RImageView isCircle(boolean z2) {
        this.mIsCircle = z2;
        updateDrawableAttrs();
        return this;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawEmptyBitmap();
    }

    public RImageView setBorderColor(@ColorInt int i2) {
        this.mBorderColor = i2;
        updateDrawableAttrs();
        return this;
    }

    public RImageView setBorderWidth(int i2) {
        this.mBorderWidth = i2;
        updateDrawableAttrs();
        return this;
    }

    public void setColorFilter() {
        Drawable drawable;
        ColorFilter colorFilter = this.mColorFilter;
        if (colorFilter == null || (drawable = this.mDrawable) == null) {
            return;
        }
        drawable.setColorFilter(colorFilter);
    }

    public RImageView setCorner(float f2) {
        this.mCorner = f2;
        updateDrawableAttrs();
        return this;
    }

    public RImageView setCornerBottomLeft(float f2) {
        this.mCorner = -1.0f;
        this.mCornerBottomLeft = f2;
        updateDrawableAttrs();
        return this;
    }

    public RImageView setCornerBottomRight(float f2) {
        this.mCorner = -1.0f;
        this.mCornerBottomRight = f2;
        updateDrawableAttrs();
        return this;
    }

    public RImageView setCornerTopLeft(float f2) {
        this.mCorner = -1.0f;
        this.mCornerTopLeft = f2;
        updateDrawableAttrs();
        return this;
    }

    public RImageView setCornerTopRight(float f2) {
        this.mCorner = -1.0f;
        this.mCornerTopRight = f2;
        updateDrawableAttrs();
        return this;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        this.mResource = 0;
        this.mDrawable = RoundDrawable.fromBitmap(bitmap);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        this.mResource = 0;
        this.mDrawable = RoundDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(@DrawableRes int i2) {
        if (this.mResource != i2) {
            this.mResource = i2;
            this.mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(this.mDrawable);
        }
    }

    @Override // android.widget.ImageView
    public void setImageTintList(@Nullable ColorStateList colorStateList) {
        super.setImageTintList(colorStateList);
        if (colorStateList != null) {
            this.mColorFilter = new PorterDuffColorFilter(colorStateList.getDefaultColor(), this.mTintMode);
        }
        setColorFilter();
    }

    @Override // android.widget.ImageView
    public void setImageTintMode(@Nullable PorterDuff.Mode mode) {
        super.setImageTintMode(mode);
        this.mTintMode = mode;
        setColorFilter();
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            updateDrawableAttrs();
            invalidate();
        }
    }

    public RImageView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCorner = -1.0f;
        this.mCornerTopLeft = 0.0f;
        this.mCornerTopRight = 0.0f;
        this.mCornerBottomLeft = 0.0f;
        this.mCornerBottomRight = 0.0f;
        this.mBorderWidth = 0.0f;
        this.mBorderColor = -16777216;
        this.mIsCircle = false;
        this.mTintMode = PorterDuff.Mode.SRC_ATOP;
        initAttributeSet(context, attributeSet);
    }

    public RImageView setCorner(float f2, float f3, float f4, float f5) {
        this.mCorner = -1.0f;
        this.mCornerTopLeft = f2;
        this.mCornerTopRight = f3;
        this.mCornerBottomRight = f4;
        this.mCornerBottomLeft = f5;
        updateDrawableAttrs();
        return this;
    }
}
