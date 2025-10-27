package com.makeramen.roundedimageview;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import java.util.HashSet;

/* loaded from: classes4.dex */
public class RoundedDrawable extends Drawable {
    public static final int DEFAULT_BORDER_COLOR = -16777216;
    public static final String TAG = "RoundedDrawable";
    private final Bitmap mBitmap;
    private final int mBitmapHeight;
    private final Paint mBitmapPaint;
    private final RectF mBitmapRect;
    private final int mBitmapWidth;
    private ColorStateList mBorderColor;
    private final Paint mBorderPaint;
    private final RectF mBorderRect;
    private float mBorderWidth;
    private float mCornerRadius;
    private final boolean[] mCornersRounded;
    private boolean mOval;
    private boolean mRebuildShader;
    private ImageView.ScaleType mScaleType;
    private final Matrix mShaderMatrix;
    private final RectF mSquareCornersRect;
    private Shader.TileMode mTileModeX;
    private Shader.TileMode mTileModeY;
    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();

    /* renamed from: com.makeramen.roundedimageview.RoundedDrawable$1, reason: invalid class name */
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
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public RoundedDrawable(Bitmap bitmap) {
        RectF rectF = new RectF();
        this.mBitmapRect = rectF;
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mSquareCornersRect = new RectF();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mTileModeX = tileMode;
        this.mTileModeY = tileMode;
        this.mRebuildShader = true;
        this.mCornerRadius = 0.0f;
        this.mCornersRounded = new boolean[]{true, true, true, true};
        this.mOval = false;
        this.mBorderWidth = 0.0f;
        this.mBorderColor = ColorStateList.valueOf(-16777216);
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mBitmap = bitmap;
        int width = bitmap.getWidth();
        this.mBitmapWidth = width;
        int height = bitmap.getHeight();
        this.mBitmapHeight = height;
        rectF.set(0.0f, 0.0f, width, height);
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mBorderPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
        paint2.setStrokeWidth(this.mBorderWidth);
    }

    private static boolean all(boolean[] zArr) {
        for (boolean z2 : zArr) {
            if (z2) {
                return false;
            }
        }
        return true;
    }

    private static boolean any(boolean[] zArr) {
        for (boolean z2 : zArr) {
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            Log.w(TAG, "Failed to create bitmap from drawable!");
            return null;
        }
    }

    public static RoundedDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new RoundedDrawable(bitmap);
        }
        return null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable == null || (drawable instanceof RoundedDrawable)) {
            return drawable;
        }
        if (!(drawable instanceof LayerDrawable)) {
            Bitmap bitmapDrawableToBitmap = drawableToBitmap(drawable);
            return bitmapDrawableToBitmap != null ? new RoundedDrawable(bitmapDrawableToBitmap) : drawable;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            layerDrawable.setDrawableByLayerId(layerDrawable.getId(i2), fromDrawable(layerDrawable.getDrawable(i2)));
        }
        return layerDrawable;
    }

    private static boolean only(int i2, boolean[] zArr) {
        int length = zArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                return true;
            }
            if (zArr[i3] != (i3 == i2)) {
                return false;
            }
            i3++;
        }
    }

    private void redrawBitmapForSquareCorners(Canvas canvas) {
        if (all(this.mCornersRounded) || this.mCornerRadius == 0.0f) {
            return;
        }
        RectF rectF = this.mDrawableRect;
        float f2 = rectF.left;
        float f3 = rectF.top;
        float fWidth = rectF.width() + f2;
        float fHeight = this.mDrawableRect.height() + f3;
        float f4 = this.mCornerRadius;
        if (!this.mCornersRounded[0]) {
            this.mSquareCornersRect.set(f2, f3, f2 + f4, f3 + f4);
            canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
        }
        if (!this.mCornersRounded[1]) {
            this.mSquareCornersRect.set(fWidth - f4, f3, fWidth, f4);
            canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
        }
        if (!this.mCornersRounded[2]) {
            this.mSquareCornersRect.set(fWidth - f4, fHeight - f4, fWidth, fHeight);
            canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
        }
        if (this.mCornersRounded[3]) {
            return;
        }
        this.mSquareCornersRect.set(f2, fHeight - f4, f4 + f2, fHeight);
        canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
    }

    private void redrawBorderForSquareCorners(Canvas canvas) {
        float f2;
        if (all(this.mCornersRounded) || this.mCornerRadius == 0.0f) {
            return;
        }
        RectF rectF = this.mDrawableRect;
        float f3 = rectF.left;
        float f4 = rectF.top;
        float fWidth = rectF.width() + f3;
        float fHeight = f4 + this.mDrawableRect.height();
        float f5 = this.mCornerRadius;
        float f6 = this.mBorderWidth / 2.0f;
        if (!this.mCornersRounded[0]) {
            canvas.drawLine(f3 - f6, f4, f3 + f5, f4, this.mBorderPaint);
            canvas.drawLine(f3, f4 - f6, f3, f4 + f5, this.mBorderPaint);
        }
        if (!this.mCornersRounded[1]) {
            canvas.drawLine((fWidth - f5) - f6, f4, fWidth, f4, this.mBorderPaint);
            canvas.drawLine(fWidth, f4 - f6, fWidth, f4 + f5, this.mBorderPaint);
        }
        if (this.mCornersRounded[2]) {
            f2 = f5;
        } else {
            f2 = f5;
            canvas.drawLine((fWidth - f5) - f6, fHeight, fWidth + f6, fHeight, this.mBorderPaint);
            canvas.drawLine(fWidth, fHeight - f2, fWidth, fHeight, this.mBorderPaint);
        }
        if (this.mCornersRounded[3]) {
            return;
        }
        canvas.drawLine(f3 - f6, fHeight, f3 + f2, fHeight, this.mBorderPaint);
        canvas.drawLine(f3, fHeight - f2, f3, fHeight, this.mBorderPaint);
    }

    private void updateShaderMatrix() {
        float fWidth;
        float fHeight;
        int i2 = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
        if (i2 == 1) {
            this.mBorderRect.set(this.mBounds);
            RectF rectF = this.mBorderRect;
            float f2 = this.mBorderWidth;
            rectF.inset(f2 / 2.0f, f2 / 2.0f);
            this.mShaderMatrix.reset();
            this.mShaderMatrix.setTranslate((int) (((this.mBorderRect.width() - this.mBitmapWidth) * 0.5f) + 0.5f), (int) (((this.mBorderRect.height() - this.mBitmapHeight) * 0.5f) + 0.5f));
        } else if (i2 == 2) {
            this.mBorderRect.set(this.mBounds);
            RectF rectF2 = this.mBorderRect;
            float f3 = this.mBorderWidth;
            rectF2.inset(f3 / 2.0f, f3 / 2.0f);
            this.mShaderMatrix.reset();
            float fWidth2 = 0.0f;
            if (this.mBitmapWidth * this.mBorderRect.height() > this.mBorderRect.width() * this.mBitmapHeight) {
                fWidth = this.mBorderRect.height() / this.mBitmapHeight;
                fHeight = 0.0f;
                fWidth2 = (this.mBorderRect.width() - (this.mBitmapWidth * fWidth)) * 0.5f;
            } else {
                fWidth = this.mBorderRect.width() / this.mBitmapWidth;
                fHeight = (this.mBorderRect.height() - (this.mBitmapHeight * fWidth)) * 0.5f;
            }
            this.mShaderMatrix.setScale(fWidth, fWidth);
            Matrix matrix = this.mShaderMatrix;
            float f4 = this.mBorderWidth;
            matrix.postTranslate(((int) (fWidth2 + 0.5f)) + (f4 / 2.0f), ((int) (fHeight + 0.5f)) + (f4 / 2.0f));
        } else if (i2 == 3) {
            this.mShaderMatrix.reset();
            float fMin = (((float) this.mBitmapWidth) > this.mBounds.width() || ((float) this.mBitmapHeight) > this.mBounds.height()) ? Math.min(this.mBounds.width() / this.mBitmapWidth, this.mBounds.height() / this.mBitmapHeight) : 1.0f;
            float fWidth3 = (int) (((this.mBounds.width() - (this.mBitmapWidth * fMin)) * 0.5f) + 0.5f);
            float fHeight2 = (int) (((this.mBounds.height() - (this.mBitmapHeight * fMin)) * 0.5f) + 0.5f);
            this.mShaderMatrix.setScale(fMin, fMin);
            this.mShaderMatrix.postTranslate(fWidth3, fHeight2);
            this.mBorderRect.set(this.mBitmapRect);
            this.mShaderMatrix.mapRect(this.mBorderRect);
            RectF rectF3 = this.mBorderRect;
            float f5 = this.mBorderWidth;
            rectF3.inset(f5 / 2.0f, f5 / 2.0f);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        } else if (i2 == 5) {
            this.mBorderRect.set(this.mBitmapRect);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.END);
            this.mShaderMatrix.mapRect(this.mBorderRect);
            RectF rectF4 = this.mBorderRect;
            float f6 = this.mBorderWidth;
            rectF4.inset(f6 / 2.0f, f6 / 2.0f);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        } else if (i2 == 6) {
            this.mBorderRect.set(this.mBitmapRect);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.START);
            this.mShaderMatrix.mapRect(this.mBorderRect);
            RectF rectF5 = this.mBorderRect;
            float f7 = this.mBorderWidth;
            rectF5.inset(f7 / 2.0f, f7 / 2.0f);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        } else if (i2 != 7) {
            this.mBorderRect.set(this.mBitmapRect);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.CENTER);
            this.mShaderMatrix.mapRect(this.mBorderRect);
            RectF rectF6 = this.mBorderRect;
            float f8 = this.mBorderWidth;
            rectF6.inset(f8 / 2.0f, f8 / 2.0f);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        } else {
            this.mBorderRect.set(this.mBounds);
            RectF rectF7 = this.mBorderRect;
            float f9 = this.mBorderWidth;
            rectF7.inset(f9 / 2.0f, f9 / 2.0f);
            this.mShaderMatrix.reset();
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        }
        this.mDrawableRect.set(this.mBorderRect);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.mRebuildShader) {
            BitmapShader bitmapShader = new BitmapShader(this.mBitmap, this.mTileModeX, this.mTileModeY);
            Shader.TileMode tileMode = this.mTileModeX;
            Shader.TileMode tileMode2 = Shader.TileMode.CLAMP;
            if (tileMode == tileMode2 && this.mTileModeY == tileMode2) {
                bitmapShader.setLocalMatrix(this.mShaderMatrix);
            }
            this.mBitmapPaint.setShader(bitmapShader);
            this.mRebuildShader = false;
        }
        if (this.mOval) {
            if (this.mBorderWidth <= 0.0f) {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
                return;
            } else {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
                return;
            }
        }
        if (!any(this.mCornersRounded)) {
            canvas.drawRect(this.mDrawableRect, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                canvas.drawRect(this.mBorderRect, this.mBorderPaint);
                return;
            }
            return;
        }
        float f2 = this.mCornerRadius;
        if (this.mBorderWidth <= 0.0f) {
            canvas.drawRoundRect(this.mDrawableRect, f2, f2, this.mBitmapPaint);
            redrawBitmapForSquareCorners(canvas);
        } else {
            canvas.drawRoundRect(this.mDrawableRect, f2, f2, this.mBitmapPaint);
            canvas.drawRoundRect(this.mBorderRect, f2, f2, this.mBorderPaint);
            redrawBitmapForSquareCorners(canvas);
            redrawBorderForSquareCorners(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mBitmapPaint.getAlpha();
    }

    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mBitmapPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public Bitmap getSourceBitmap() {
        return this.mBitmap;
    }

    public Shader.TileMode getTileModeX() {
        return this.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mTileModeY;
    }

    public boolean isOval() {
        return this.mOval;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mBorderColor.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(@NonNull Rect rect) {
        super.onBoundsChange(rect);
        this.mBounds.set(rect);
        updateShaderMatrix();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        int colorForState = this.mBorderColor.getColorForState(iArr, 0);
        if (this.mBorderPaint.getColor() == colorForState) {
            return super.onStateChange(iArr);
        }
        this.mBorderPaint.setColor(colorForState);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mBitmapPaint.setAlpha(i2);
        invalidateSelf();
    }

    public RoundedDrawable setBorderColor(@ColorInt int i2) {
        return setBorderColor(ColorStateList.valueOf(i2));
    }

    public RoundedDrawable setBorderWidth(float f2) {
        this.mBorderWidth = f2;
        this.mBorderPaint.setStrokeWidth(f2);
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public RoundedDrawable setCornerRadius(float f2) {
        setCornerRadius(f2, f2, f2, f2);
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z2) {
        this.mBitmapPaint.setDither(z2);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z2) {
        this.mBitmapPaint.setFilterBitmap(z2);
        invalidateSelf();
    }

    public RoundedDrawable setOval(boolean z2) {
        this.mOval = z2;
        return this;
    }

    public RoundedDrawable setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            updateShaderMatrix();
        }
        return this;
    }

    public RoundedDrawable setTileModeX(Shader.TileMode tileMode) {
        if (this.mTileModeX != tileMode) {
            this.mTileModeX = tileMode;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public RoundedDrawable setTileModeY(Shader.TileMode tileMode) {
        if (this.mTileModeY != tileMode) {
            this.mTileModeY = tileMode;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }

    public float getCornerRadius(int i2) {
        if (this.mCornersRounded[i2]) {
            return this.mCornerRadius;
        }
        return 0.0f;
    }

    public RoundedDrawable setBorderColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.mBorderColor = colorStateList;
        this.mBorderPaint.setColor(colorStateList.getColorForState(getState(), -16777216));
        return this;
    }

    public RoundedDrawable setCornerRadius(int i2, float f2) {
        if (f2 != 0.0f) {
            float f3 = this.mCornerRadius;
            if (f3 != 0.0f && f3 != f2) {
                throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
            }
        }
        if (f2 == 0.0f) {
            if (only(i2, this.mCornersRounded)) {
                this.mCornerRadius = 0.0f;
            }
            this.mCornersRounded[i2] = false;
        } else {
            if (this.mCornerRadius == 0.0f) {
                this.mCornerRadius = f2;
            }
            this.mCornersRounded[i2] = true;
        }
        return this;
    }

    public RoundedDrawable setCornerRadius(float f2, float f3, float f4, float f5) {
        HashSet hashSet = new HashSet(4);
        hashSet.add(Float.valueOf(f2));
        hashSet.add(Float.valueOf(f3));
        hashSet.add(Float.valueOf(f4));
        hashSet.add(Float.valueOf(f5));
        hashSet.remove(Float.valueOf(0.0f));
        if (hashSet.size() <= 1) {
            if (!hashSet.isEmpty()) {
                float fFloatValue = ((Float) hashSet.iterator().next()).floatValue();
                if (!Float.isInfinite(fFloatValue) && !Float.isNaN(fFloatValue) && fFloatValue >= 0.0f) {
                    this.mCornerRadius = fFloatValue;
                } else {
                    throw new IllegalArgumentException("Invalid radius value: " + fFloatValue);
                }
            } else {
                this.mCornerRadius = 0.0f;
            }
            boolean[] zArr = this.mCornersRounded;
            zArr[0] = f2 > 0.0f;
            zArr[1] = f3 > 0.0f;
            zArr[2] = f4 > 0.0f;
            zArr[3] = f5 > 0.0f;
            return this;
        }
        throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
    }
}
