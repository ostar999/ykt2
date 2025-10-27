package com.makeramen.roundedimageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;

/* loaded from: classes4.dex */
public class RoundedImageView extends ImageView {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final float DEFAULT_BORDER_WIDTH = 0.0f;
    public static final float DEFAULT_RADIUS = 0.0f;
    public static final Shader.TileMode DEFAULT_TILE_MODE = Shader.TileMode.CLAMP;
    private static final ImageView.ScaleType[] SCALE_TYPES = {ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE};
    public static final String TAG = "RoundedImageView";
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private static final int TILE_MODE_UNDEFINED = -2;
    private Drawable mBackgroundDrawable;
    private int mBackgroundResource;
    private ColorStateList mBorderColor;
    private float mBorderWidth;
    private ColorFilter mColorFilter;
    private boolean mColorMod;
    private final float[] mCornerRadii;
    private Drawable mDrawable;
    private boolean mHasColorFilter;
    private boolean mIsOval;
    private boolean mMutateBackground;
    private int mResource;
    private ImageView.ScaleType mScaleType;
    private Shader.TileMode mTileModeX;
    private Shader.TileMode mTileModeY;

    /* renamed from: com.makeramen.roundedimageview.RoundedImageView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public RoundedImageView(Context context) {
        super(context);
        this.mCornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
        this.mBorderColor = ColorStateList.valueOf(-16777216);
        this.mBorderWidth = 0.0f;
        this.mColorFilter = null;
        this.mColorMod = false;
        this.mHasColorFilter = false;
        this.mIsOval = false;
        this.mMutateBackground = false;
        Shader.TileMode tileMode = DEFAULT_TILE_MODE;
        this.mTileModeX = tileMode;
        this.mTileModeY = tileMode;
    }

    private void applyColorMod() {
        Drawable drawable = this.mDrawable;
        if (drawable == null || !this.mColorMod) {
            return;
        }
        Drawable drawableMutate = drawable.mutate();
        this.mDrawable = drawableMutate;
        if (this.mHasColorFilter) {
            drawableMutate.setColorFilter(this.mColorFilter);
        }
    }

    private static Shader.TileMode parseTileMode(int i2) {
        if (i2 == 0) {
            return Shader.TileMode.CLAMP;
        }
        if (i2 == 1) {
            return Shader.TileMode.REPEAT;
        }
        if (i2 != 2) {
            return null;
        }
        return Shader.TileMode.MIRROR;
    }

    private Drawable resolveBackgroundResource() throws Resources.NotFoundException {
        Resources resources = getResources();
        Drawable drawable = null;
        if (resources == null) {
            return null;
        }
        int i2 = this.mBackgroundResource;
        if (i2 != 0) {
            try {
                drawable = resources.getDrawable(i2);
            } catch (Exception e2) {
                Log.w(TAG, "Unable to find resource: " + this.mBackgroundResource, e2);
                this.mBackgroundResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(drawable);
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
            } catch (Exception e2) {
                Log.w(TAG, "Unable to find resource: " + this.mResource, e2);
                this.mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(drawable);
    }

    private void updateAttrs(Drawable drawable, ImageView.ScaleType scaleType) {
        if (drawable == null) {
            return;
        }
        if (drawable instanceof RoundedDrawable) {
            RoundedDrawable roundedDrawable = (RoundedDrawable) drawable;
            roundedDrawable.setScaleType(scaleType).setBorderWidth(this.mBorderWidth).setBorderColor(this.mBorderColor).setOval(this.mIsOval).setTileModeX(this.mTileModeX).setTileModeY(this.mTileModeY);
            float[] fArr = this.mCornerRadii;
            if (fArr != null) {
                roundedDrawable.setCornerRadius(fArr[0], fArr[1], fArr[2], fArr[3]);
            }
            applyColorMod();
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

    private void updateBackgroundDrawableAttrs(boolean z2) {
        if (this.mMutateBackground) {
            if (z2) {
                this.mBackgroundDrawable = RoundedDrawable.fromDrawable(this.mBackgroundDrawable);
            }
            updateAttrs(this.mBackgroundDrawable, ImageView.ScaleType.FIT_XY);
        }
    }

    private void updateDrawableAttrs() {
        updateAttrs(this.mDrawable, this.mScaleType);
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @ColorInt
    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public float getCornerRadius() {
        return getMaxCornerRadius();
    }

    public float getMaxCornerRadius() {
        float fMax = 0.0f;
        for (float f2 : this.mCornerRadii) {
            fMax = Math.max(f2, fMax);
        }
        return fMax;
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public Shader.TileMode getTileModeX() {
        return this.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mTileModeY;
    }

    public boolean isOval() {
        return this.mIsOval;
    }

    public void mutateBackground(boolean z2) {
        if (this.mMutateBackground == z2) {
            return;
        }
        this.mMutateBackground = z2;
        updateBackgroundDrawableAttrs(true);
        invalidate();
    }

    public boolean mutatesBackground() {
        return this.mMutateBackground;
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        ColorDrawable colorDrawable = new ColorDrawable(i2);
        this.mBackgroundDrawable = colorDrawable;
        setBackgroundDrawable(colorDrawable);
    }

    @Override // android.view.View
    @Deprecated
    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        updateBackgroundDrawableAttrs(true);
        super.setBackgroundDrawable(this.mBackgroundDrawable);
    }

    @Override // android.view.View
    public void setBackgroundResource(@DrawableRes int i2) throws Resources.NotFoundException {
        if (this.mBackgroundResource != i2) {
            this.mBackgroundResource = i2;
            Drawable drawableResolveBackgroundResource = resolveBackgroundResource();
            this.mBackgroundDrawable = drawableResolveBackgroundResource;
            setBackgroundDrawable(drawableResolveBackgroundResource);
        }
    }

    public void setBorderColor(@ColorInt int i2) {
        setBorderColor(ColorStateList.valueOf(i2));
    }

    public void setBorderWidth(@DimenRes int i2) {
        setBorderWidth(getResources().getDimension(i2));
    }

    @Override // android.widget.ImageView
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            this.mHasColorFilter = true;
            this.mColorMod = true;
            applyColorMod();
            invalidate();
        }
    }

    public void setCornerRadius(float f2) {
        setCornerRadius(f2, f2, f2, f2);
    }

    public void setCornerRadiusDimen(@DimenRes int i2) throws Resources.NotFoundException {
        float dimension = getResources().getDimension(i2);
        setCornerRadius(dimension, dimension, dimension, dimension);
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromBitmap(bitmap);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override // android.widget.ImageView
    public void setImageResource(@DrawableRes int i2) {
        if (this.mResource != i2) {
            this.mResource = i2;
            this.mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(this.mDrawable);
        }
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    public void setOval(boolean z2) {
        this.mIsOval = z2;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    super.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    public void setTileModeX(Shader.TileMode tileMode) {
        if (this.mTileModeX == tileMode) {
            return;
        }
        this.mTileModeX = tileMode;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public void setTileModeY(Shader.TileMode tileMode) {
        if (this.mTileModeY == tileMode) {
            return;
        }
        this.mTileModeY = tileMode;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public float getCornerRadius(int i2) {
        return this.mCornerRadii[i2];
    }

    public void setBorderColor(ColorStateList colorStateList) {
        if (this.mBorderColor.equals(colorStateList)) {
            return;
        }
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(-16777216);
        }
        this.mBorderColor = colorStateList;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        if (this.mBorderWidth > 0.0f) {
            invalidate();
        }
    }

    public void setBorderWidth(float f2) {
        if (this.mBorderWidth == f2) {
            return;
        }
        this.mBorderWidth = f2;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public void setCornerRadius(int i2, float f2) {
        float[] fArr = this.mCornerRadii;
        if (fArr[i2] == f2) {
            return;
        }
        fArr[i2] = f2;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public void setCornerRadiusDimen(int i2, @DimenRes int i3) {
        setCornerRadius(i2, getResources().getDimensionPixelSize(i3));
    }

    public void setCornerRadius(float f2, float f3, float f4, float f5) {
        float[] fArr = this.mCornerRadii;
        if (fArr[0] == f2 && fArr[1] == f3 && fArr[2] == f5 && fArr[3] == f4) {
            return;
        }
        fArr[0] = f2;
        fArr[1] = f3;
        fArr[3] = f4;
        fArr[2] = f5;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public RoundedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundedImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f};
        this.mCornerRadii = fArr;
        this.mBorderColor = ColorStateList.valueOf(-16777216);
        this.mBorderWidth = 0.0f;
        this.mColorFilter = null;
        this.mColorMod = false;
        this.mHasColorFilter = false;
        this.mIsOval = false;
        this.mMutateBackground = false;
        Shader.TileMode tileMode = DEFAULT_TILE_MODE;
        this.mTileModeX = tileMode;
        this.mTileModeY = tileMode;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundedImageView, i2, 0);
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundedImageView_android_scaleType, -1);
        if (i3 >= 0) {
            setScaleType(SCALE_TYPES[i3]);
        } else {
            setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius, -1);
        fArr[0] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius_top_left, -1);
        fArr[1] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius_top_right, -1);
        fArr[2] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius_bottom_right, -1);
        fArr[3] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius_bottom_left, -1);
        int length = fArr.length;
        boolean z2 = false;
        for (int i4 = 0; i4 < length; i4++) {
            float[] fArr2 = this.mCornerRadii;
            if (fArr2[i4] < 0.0f) {
                fArr2[i4] = 0.0f;
            } else {
                z2 = true;
            }
        }
        if (!z2) {
            dimensionPixelSize = dimensionPixelSize < 0.0f ? 0.0f : dimensionPixelSize;
            int length2 = this.mCornerRadii.length;
            for (int i5 = 0; i5 < length2; i5++) {
                this.mCornerRadii[i5] = dimensionPixelSize;
            }
        }
        float dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_riv_border_width, -1);
        this.mBorderWidth = dimensionPixelSize2;
        if (dimensionPixelSize2 < 0.0f) {
            this.mBorderWidth = 0.0f;
        }
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.RoundedImageView_riv_border_color);
        this.mBorderColor = colorStateList;
        if (colorStateList == null) {
            this.mBorderColor = ColorStateList.valueOf(-16777216);
        }
        this.mMutateBackground = typedArrayObtainStyledAttributes.getBoolean(R.styleable.RoundedImageView_riv_mutate_background, false);
        this.mIsOval = typedArrayObtainStyledAttributes.getBoolean(R.styleable.RoundedImageView_riv_oval, false);
        int i6 = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundedImageView_riv_tile_mode, -2);
        if (i6 != -2) {
            setTileModeX(parseTileMode(i6));
            setTileModeY(parseTileMode(i6));
        }
        int i7 = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundedImageView_riv_tile_mode_x, -2);
        if (i7 != -2) {
            setTileModeX(parseTileMode(i7));
        }
        int i8 = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundedImageView_riv_tile_mode_y, -2);
        if (i8 != -2) {
            setTileModeY(parseTileMode(i8));
        }
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(true);
        if (this.mMutateBackground) {
            super.setBackgroundDrawable(this.mBackgroundDrawable);
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
