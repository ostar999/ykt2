package com.ruffian.library.widget.rounded;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes6.dex */
public class RoundDrawable extends Drawable {
    private Bitmap mBitmap;
    private final int mBitmapHeight;
    private final Paint mBitmapPaint;
    private final RectF mBitmapRect;
    private final int mBitmapWidth;
    private int mBorderColor;
    private final Paint mBorderPaint;
    private final RectF mBorderRect;
    private float mBorderWidth;
    private final RectF mBounds;
    private final RectF mBoundsFinal;
    private boolean mCircle;
    private float mCorner;
    private float mCornerBottomLeft;
    private float mCornerBottomRight;
    private float[] mCornerRadii;
    private float mCornerTopLeft;
    private float mCornerTopRight;
    private final RectF mDrawableRect;
    private Path mPath;
    private boolean mRebuildShader;
    private RectF mRectF;
    private ImageView.ScaleType mScaleType;
    private final Matrix mShaderMatrix;

    /* renamed from: com.ruffian.library.widget.rounded.RoundDrawable$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 3;
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

    public RoundDrawable(Bitmap bitmap) {
        RectF rectF = new RectF();
        this.mBitmapRect = rectF;
        this.mBorderRect = new RectF();
        this.mDrawableRect = new RectF();
        this.mPath = new Path();
        this.mRectF = new RectF();
        this.mBounds = new RectF();
        this.mBoundsFinal = new RectF();
        this.mRebuildShader = true;
        this.mShaderMatrix = new Matrix();
        this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        this.mCorner = -1.0f;
        this.mCornerTopLeft = 0.0f;
        this.mCornerTopRight = 0.0f;
        this.mCornerBottomLeft = 0.0f;
        this.mCornerBottomRight = 0.0f;
        this.mCornerRadii = new float[8];
        this.mBorderWidth = 0.0f;
        this.mBorderColor = -16777216;
        this.mCircle = true;
        this.mBitmap = bitmap;
        int width = bitmap.getWidth();
        this.mBitmapWidth = width;
        int height = bitmap.getHeight();
        this.mBitmapHeight = height;
        rectF.set(0.0f, 0.0f, width, height);
        Paint paint = new Paint(1);
        this.mBitmapPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Paint paint2 = new Paint(1);
        this.mBorderPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        updateBorder();
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
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static RoundDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new RoundDrawable(bitmap);
        }
        return null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable == null || (drawable instanceof RoundDrawable)) {
            return drawable;
        }
        if (!(drawable instanceof LayerDrawable)) {
            Bitmap bitmapDrawableToBitmap = drawableToBitmap(drawable);
            return bitmapDrawableToBitmap != null ? new RoundDrawable(bitmapDrawableToBitmap) : drawable;
        }
        Drawable.ConstantState constantState = drawable.mutate().getConstantState();
        if (constantState != null) {
            drawable = constantState.newDrawable();
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            layerDrawable.setDrawableByLayerId(layerDrawable.getId(i2), fromDrawable(layerDrawable.getDrawable(i2)));
        }
        return layerDrawable;
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ImageView.ScaleType scaleType) {
        int i2 = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()];
        return i2 != 4 ? i2 != 5 ? i2 != 6 ? i2 != 7 ? Matrix.ScaleToFit.CENTER : Matrix.ScaleToFit.FILL : Matrix.ScaleToFit.START : Matrix.ScaleToFit.END : Matrix.ScaleToFit.CENTER;
    }

    private void updateBorder() {
        this.mBorderPaint.setColor(this.mBorderColor);
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    private void updateBorderPath() {
        this.mPath.reset();
        this.mPath.addRoundRect(this.mBorderRect, this.mCornerRadii, Path.Direction.CCW);
    }

    private void updateConner() {
        float f2 = this.mCorner;
        int i2 = 0;
        if (f2 < 0.0f) {
            if (f2 < 0.0f) {
                float[] fArr = this.mCornerRadii;
                float f3 = this.mCornerTopLeft;
                fArr[0] = f3;
                fArr[1] = f3;
                float f4 = this.mCornerTopRight;
                fArr[2] = f4;
                fArr[3] = f4;
                float f5 = this.mCornerBottomRight;
                fArr[4] = f5;
                fArr[5] = f5;
                float f6 = this.mCornerBottomLeft;
                fArr[6] = f6;
                fArr[7] = f6;
                return;
            }
            return;
        }
        while (true) {
            float[] fArr2 = this.mCornerRadii;
            if (i2 >= fArr2.length) {
                return;
            }
            fArr2[i2] = this.mCorner;
            i2++;
        }
    }

    private void updateDrawablePath() {
        this.mPath.reset();
        this.mPath.addRoundRect(this.mDrawableRect, this.mCornerRadii, Path.Direction.CCW);
    }

    private void updateShaderMatrix() {
        float fMin;
        float fHeight;
        int i2;
        float fWidth;
        float fWidth2;
        float fHeight2;
        float f2 = this.mBorderWidth / 2.0f;
        this.mBounds.set(this.mBoundsFinal);
        int i3 = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
        if (i3 != 1) {
            if (i3 == 2) {
                float fMin2 = Math.min(this.mBounds.height(), this.mBitmapRect.height());
                float fMin3 = Math.min(this.mBounds.width(), this.mBitmapRect.width());
                float fHeight3 = (this.mBounds.height() - this.mBitmapRect.height()) / 2.0f;
                float fWidth3 = (this.mBounds.width() - this.mBitmapRect.width()) / 2.0f;
                float f3 = fHeight3 > 0.0f ? fHeight3 : 0.0f;
                fWidth = fWidth3 > 0.0f ? fWidth3 : 0.0f;
                RectF rectF = new RectF(fWidth, f3, fMin3 + fWidth, fMin2 + f3);
                this.mRectF = rectF;
                boolean z2 = this.mCircle;
                rectF.inset(z2 ? this.mBorderWidth : f2, z2 ? this.mBorderWidth : f2);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.postTranslate(((int) (fWidth3 + 0.5f)) + f2, ((int) (fHeight3 + 0.5f)) + f2);
            } else if (i3 == 3) {
                this.mRectF.set(this.mBounds);
                RectF rectF2 = this.mRectF;
                boolean z3 = this.mCircle;
                rectF2.inset(z3 ? this.mBorderWidth : f2, z3 ? this.mBorderWidth : f2);
                if (this.mBitmapWidth * this.mRectF.height() > this.mRectF.width() * this.mBitmapHeight) {
                    fWidth2 = this.mRectF.height() / this.mBitmapHeight;
                    fWidth = (this.mRectF.width() - (this.mBitmapWidth * fWidth2)) * 0.5f;
                    fHeight2 = 0.0f;
                } else {
                    fWidth2 = this.mRectF.width() / this.mBitmapWidth;
                    fHeight2 = (this.mRectF.height() - (this.mBitmapHeight * fWidth2)) * 0.5f;
                }
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setScale(fWidth2, fWidth2);
                this.mShaderMatrix.postTranslate(((int) (fWidth + 0.5f)) + f2, ((int) (fHeight2 + 0.5f)) + f2);
            } else if (i3 != 7) {
                RectF rectF3 = this.mBounds;
                boolean z4 = this.mCircle;
                rectF3.inset(z4 ? this.mBorderWidth : f2, z4 ? this.mBorderWidth : f2);
                this.mRectF.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, scaleTypeToScaleToFit(this.mScaleType));
                this.mShaderMatrix.mapRect(this.mRectF);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mRectF, Matrix.ScaleToFit.FILL);
            } else {
                RectF rectF4 = this.mBounds;
                boolean z5 = this.mCircle;
                rectF4.inset(z5 ? this.mBorderWidth : f2, z5 ? this.mBorderWidth : f2);
                this.mRectF.set(this.mBounds);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mRectF, Matrix.ScaleToFit.FILL);
            }
        } else {
            if (this.mBitmapWidth > this.mBounds.width() || this.mBitmapHeight > this.mBounds.height()) {
                fMin = Math.min(this.mBounds.width() / this.mBitmapWidth, this.mBounds.height() / this.mBitmapHeight);
                if (this.mBounds.height() < this.mBounds.width()) {
                    fHeight = this.mBounds.height();
                    i2 = this.mBitmapWidth;
                } else if (this.mBounds.height() > this.mBounds.width()) {
                    fHeight = this.mBitmapHeight * fMin;
                    fWidth = this.mBounds.width();
                } else {
                    fHeight = this.mBitmapHeight * fMin;
                    i2 = this.mBitmapWidth;
                }
                fWidth = i2 * fMin;
            } else {
                fHeight = this.mBitmapHeight;
                fWidth = this.mBitmapWidth;
                fMin = 1.0f;
            }
            float fWidth4 = (int) (((this.mBounds.width() - (this.mBitmapWidth * fMin)) * 0.5f) + 0.5f);
            float fHeight4 = (int) (((this.mBounds.height() - (this.mBitmapHeight * fMin)) * 0.5f) + 0.5f);
            RectF rectF5 = new RectF(fWidth4, fHeight4, fWidth + fWidth4, fHeight + fHeight4);
            this.mRectF = rectF5;
            boolean z6 = this.mCircle;
            rectF5.inset(z6 ? this.mBorderWidth : f2, z6 ? this.mBorderWidth : f2);
            this.mShaderMatrix.reset();
            this.mShaderMatrix.setScale(fMin, fMin);
            this.mShaderMatrix.postTranslate(fWidth4, fHeight4);
        }
        if (this.mCircle) {
            RectF rectF6 = this.mBorderRect;
            RectF rectF7 = this.mRectF;
            rectF6.set(rectF7.left - f2, rectF7.top - f2, rectF7.right + f2, rectF7.bottom + f2);
        } else {
            this.mBorderRect.set(this.mBoundsFinal);
            this.mBorderRect.inset(f2, f2);
        }
        this.mDrawableRect.set(this.mRectF);
        this.mRebuildShader = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.mRebuildShader) {
            Bitmap bitmap = this.mBitmap;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            bitmapShader.setLocalMatrix(this.mShaderMatrix);
            this.mBitmapPaint.setShader(bitmapShader);
            this.mRebuildShader = false;
        }
        if (!this.mCircle) {
            updateDrawablePath();
            canvas.drawPath(this.mPath, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                updateBorderPath();
                canvas.drawPath(this.mPath, this.mBorderPaint);
                return;
            }
            return;
        }
        float fWidth = this.mDrawableRect.width() / 2.0f;
        RectF rectF = this.mDrawableRect;
        float f2 = fWidth + rectF.left;
        float fHeight = rectF.height() / 2.0f;
        RectF rectF2 = this.mDrawableRect;
        canvas.drawCircle(f2, fHeight + rectF2.top, Math.min(Math.min(this.mBitmapHeight, this.mBitmapWidth), Math.min(rectF2.width() / 2.0f, this.mDrawableRect.height() / 2.0f)), this.mBitmapPaint);
        if (this.mBorderWidth > 0.0f) {
            float fWidth2 = this.mBorderRect.width() / 2.0f;
            RectF rectF3 = this.mBorderRect;
            float f3 = fWidth2 + rectF3.left;
            float fHeight2 = rectF3.height() / 2.0f;
            RectF rectF4 = this.mBorderRect;
            canvas.drawCircle(f3, fHeight2 + rectF4.top, Math.min(Math.min(this.mBitmapHeight, this.mBitmapWidth), Math.min(rectF4.width() / 2.0f, this.mBorderRect.height() / 2.0f)), this.mBorderPaint);
        }
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mBoundsFinal.set(rect);
        updateShaderMatrix();
        updateConner();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mBitmapPaint.setAlpha(i2);
        invalidateSelf();
    }

    public RoundDrawable setBorderColor(int i2) {
        this.mBorderColor = i2;
        updateBorder();
        invalidateSelf();
        return this;
    }

    public RoundDrawable setBorderWidth(float f2) {
        this.mBorderWidth = f2;
        updateBorder();
        updateShaderMatrix();
        invalidateSelf();
        return this;
    }

    public RoundDrawable setCircle(boolean z2) {
        this.mCircle = z2;
        updateShaderMatrix();
        invalidateSelf();
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public RoundDrawable setConner(float f2, float f3, float f4, float f5, float f6) {
        this.mCorner = f2;
        this.mCornerTopLeft = f3;
        this.mCornerTopRight = f4;
        this.mCornerBottomLeft = f5;
        this.mCornerBottomRight = f6;
        updateConner();
        invalidateSelf();
        return this;
    }

    public void setParams(ImageView.ScaleType scaleType, float f2, int i2, boolean z2, float f3, float f4, float f5, float f6, float f7) {
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
        }
        this.mBorderWidth = f2;
        this.mBorderColor = i2;
        updateBorder();
        this.mCircle = z2;
        this.mCorner = f3;
        this.mCornerTopLeft = f4;
        this.mCornerTopRight = f5;
        this.mCornerBottomLeft = f6;
        this.mCornerBottomRight = f7;
        updateConner();
        updateShaderMatrix();
        invalidateSelf();
    }

    public RoundDrawable setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            updateShaderMatrix();
            invalidateSelf();
        }
        return this;
    }
}
