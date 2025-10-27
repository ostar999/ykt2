package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class ImageFilterView extends AppCompatImageView {
    private Drawable mAltDrawable;
    private float mCrossfade;
    private Drawable mDrawable;
    private ImageMatrix mImageMatrix;
    LayerDrawable mLayer;
    Drawable[] mLayers;
    private boolean mOverlay;
    float mPanX;
    float mPanY;
    private Path mPath;
    RectF mRect;
    float mRotate;
    private float mRound;
    private float mRoundPercent;
    ViewOutlineProvider mViewOutlineProvider;
    float mZoom;

    public static class ImageMatrix {

        /* renamed from: m, reason: collision with root package name */
        float[] f1792m = new float[20];
        ColorMatrix mColorMatrix = new ColorMatrix();
        ColorMatrix mTmpColorMatrix = new ColorMatrix();
        float mBrightness = 1.0f;
        float mSaturation = 1.0f;
        float mContrast = 1.0f;
        float mWarmth = 1.0f;

        private void brightness(float brightness) {
            float[] fArr = this.f1792m;
            fArr[0] = brightness;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = brightness;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = brightness;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void saturation(float saturationStrength) {
            float f2 = 1.0f - saturationStrength;
            float f3 = 0.2999f * f2;
            float f4 = 0.587f * f2;
            float f5 = f2 * 0.114f;
            float[] fArr = this.f1792m;
            fArr[0] = f3 + saturationStrength;
            fArr[1] = f4;
            fArr[2] = f5;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = f3;
            fArr[6] = f4 + saturationStrength;
            fArr[7] = f5;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = f3;
            fArr[11] = f4;
            fArr[12] = f5 + saturationStrength;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        private void warmth(float warmth) {
            float fLog;
            float fPow;
            if (warmth <= 0.0f) {
                warmth = 0.01f;
            }
            float f2 = (5000.0f / warmth) / 100.0f;
            if (f2 > 66.0f) {
                double d3 = f2 - 60.0f;
                fPow = ((float) Math.pow(d3, -0.13320475816726685d)) * 329.69873f;
                fLog = ((float) Math.pow(d3, 0.07551484555006027d)) * 288.12216f;
            } else {
                fLog = (((float) Math.log(f2)) * 99.4708f) - 161.11957f;
                fPow = 255.0f;
            }
            float fLog2 = f2 < 66.0f ? f2 > 19.0f ? (((float) Math.log(f2 - 10.0f)) * 138.51773f) - 305.0448f : 0.0f : 255.0f;
            float fMin = Math.min(255.0f, Math.max(fPow, 0.0f));
            float fMin2 = Math.min(255.0f, Math.max(fLog, 0.0f));
            float fMin3 = Math.min(255.0f, Math.max(fLog2, 0.0f));
            float fLog3 = (((float) Math.log(50.0f)) * 99.4708f) - 161.11957f;
            float fLog4 = (((float) Math.log(40.0f)) * 138.51773f) - 305.0448f;
            float fMin4 = Math.min(255.0f, Math.max(255.0f, 0.0f));
            float fMin5 = Math.min(255.0f, Math.max(fLog3, 0.0f));
            float fMin6 = fMin3 / Math.min(255.0f, Math.max(fLog4, 0.0f));
            float[] fArr = this.f1792m;
            fArr[0] = fMin / fMin4;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            fArr[4] = 0.0f;
            fArr[5] = 0.0f;
            fArr[6] = fMin2 / fMin5;
            fArr[7] = 0.0f;
            fArr[8] = 0.0f;
            fArr[9] = 0.0f;
            fArr[10] = 0.0f;
            fArr[11] = 0.0f;
            fArr[12] = fMin6;
            fArr[13] = 0.0f;
            fArr[14] = 0.0f;
            fArr[15] = 0.0f;
            fArr[16] = 0.0f;
            fArr[17] = 0.0f;
            fArr[18] = 1.0f;
            fArr[19] = 0.0f;
        }

        public void updateMatrix(ImageView view) {
            boolean z2;
            this.mColorMatrix.reset();
            float f2 = this.mSaturation;
            boolean z3 = true;
            if (f2 != 1.0f) {
                saturation(f2);
                this.mColorMatrix.set(this.f1792m);
                z2 = true;
            } else {
                z2 = false;
            }
            float f3 = this.mContrast;
            if (f3 != 1.0f) {
                this.mTmpColorMatrix.setScale(f3, f3, f3, 1.0f);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                z2 = true;
            }
            float f4 = this.mWarmth;
            if (f4 != 1.0f) {
                warmth(f4);
                this.mTmpColorMatrix.set(this.f1792m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
                z2 = true;
            }
            float f5 = this.mBrightness;
            if (f5 != 1.0f) {
                brightness(f5);
                this.mTmpColorMatrix.set(this.f1792m);
                this.mColorMatrix.postConcat(this.mTmpColorMatrix);
            } else {
                z3 = z2;
            }
            if (z3) {
                view.setColorFilter(new ColorMatrixColorFilter(this.mColorMatrix));
            } else {
                view.clearColorFilter();
            }
        }
    }

    public ImageFilterView(Context context) {
        super(context);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.ImageFilterView);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            this.mAltDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ImageFilterView_altSrc);
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i2);
                if (index == R.styleable.ImageFilterView_crossfade) {
                    this.mCrossfade = typedArrayObtainStyledAttributes.getFloat(index, 0.0f);
                } else if (index == R.styleable.ImageFilterView_warmth) {
                    setWarmth(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_saturation) {
                    setSaturation(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_contrast) {
                    setContrast(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_brightness) {
                    setBrightness(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_round) {
                    setRound(typedArrayObtainStyledAttributes.getDimension(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_roundPercent) {
                    setRoundPercent(typedArrayObtainStyledAttributes.getFloat(index, 0.0f));
                } else if (index == R.styleable.ImageFilterView_overlay) {
                    setOverlay(typedArrayObtainStyledAttributes.getBoolean(index, this.mOverlay));
                } else if (index == R.styleable.ImageFilterView_imagePanX) {
                    setImagePanX(typedArrayObtainStyledAttributes.getFloat(index, this.mPanX));
                } else if (index == R.styleable.ImageFilterView_imagePanY) {
                    setImagePanY(typedArrayObtainStyledAttributes.getFloat(index, this.mPanY));
                } else if (index == R.styleable.ImageFilterView_imageRotate) {
                    setImageRotate(typedArrayObtainStyledAttributes.getFloat(index, this.mRotate));
                } else if (index == R.styleable.ImageFilterView_imageZoom) {
                    setImageZoom(typedArrayObtainStyledAttributes.getFloat(index, this.mZoom));
                }
            }
            typedArrayObtainStyledAttributes.recycle();
            Drawable drawable = getDrawable();
            this.mDrawable = drawable;
            if (this.mAltDrawable == null || drawable == null) {
                Drawable drawable2 = getDrawable();
                this.mDrawable = drawable2;
                if (drawable2 != null) {
                    Drawable[] drawableArr = this.mLayers;
                    Drawable drawableMutate = drawable2.mutate();
                    this.mDrawable = drawableMutate;
                    drawableArr[0] = drawableMutate;
                    return;
                }
                return;
            }
            Drawable[] drawableArr2 = this.mLayers;
            Drawable drawableMutate2 = getDrawable().mutate();
            this.mDrawable = drawableMutate2;
            drawableArr2[0] = drawableMutate2;
            this.mLayers[1] = this.mAltDrawable.mutate();
            LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
            this.mLayer = layerDrawable;
            layerDrawable.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            if (!this.mOverlay) {
                this.mLayer.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            super.setImageDrawable(this.mLayer);
        }
    }

    private void setMatrix() {
        if (Float.isNaN(this.mPanX) && Float.isNaN(this.mPanY) && Float.isNaN(this.mZoom) && Float.isNaN(this.mRotate)) {
            return;
        }
        float f2 = Float.isNaN(this.mPanX) ? 0.0f : this.mPanX;
        float f3 = Float.isNaN(this.mPanY) ? 0.0f : this.mPanY;
        float f4 = Float.isNaN(this.mZoom) ? 1.0f : this.mZoom;
        float f5 = Float.isNaN(this.mRotate) ? 0.0f : this.mRotate;
        Matrix matrix = new Matrix();
        matrix.reset();
        float intrinsicWidth = getDrawable().getIntrinsicWidth();
        float intrinsicHeight = getDrawable().getIntrinsicHeight();
        float width = getWidth();
        float height = getHeight();
        float f6 = f4 * (intrinsicWidth * height < intrinsicHeight * width ? width / intrinsicWidth : height / intrinsicHeight);
        matrix.postScale(f6, f6);
        float f7 = intrinsicWidth * f6;
        float f8 = f6 * intrinsicHeight;
        matrix.postTranslate((((f2 * (width - f7)) + width) - f7) * 0.5f, (((f3 * (height - f8)) + height) - f8) * 0.5f);
        matrix.postRotate(f5, width / 2.0f, height / 2.0f);
        setImageMatrix(matrix);
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void setOverlay(boolean overlay) {
        this.mOverlay = overlay;
    }

    private void updateViewMatrix() {
        if (Float.isNaN(this.mPanX) && Float.isNaN(this.mPanY) && Float.isNaN(this.mZoom) && Float.isNaN(this.mRotate)) {
            setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            setMatrix();
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public float getBrightness() {
        return this.mImageMatrix.mBrightness;
    }

    public float getContrast() {
        return this.mImageMatrix.mContrast;
    }

    public float getCrossfade() {
        return this.mCrossfade;
    }

    public float getImagePanX() {
        return this.mPanX;
    }

    public float getImagePanY() {
        return this.mPanY;
    }

    public float getImageRotate() {
        return this.mRotate;
    }

    public float getImageZoom() {
        return this.mZoom;
    }

    public float getRound() {
        return this.mRound;
    }

    public float getRoundPercent() {
        return this.mRoundPercent;
    }

    public float getSaturation() {
        return this.mImageMatrix.mSaturation;
    }

    public float getWarmth() {
        return this.mImageMatrix.mWarmth;
    }

    @Override // android.view.View
    public void layout(int l2, int t2, int r2, int b3) {
        super.layout(l2, t2, r2, b3);
        setMatrix();
    }

    public void setAltImageResource(int resId) {
        Drawable drawableMutate = AppCompatResources.getDrawable(getContext(), resId).mutate();
        this.mAltDrawable = drawableMutate;
        Drawable[] drawableArr = this.mLayers;
        drawableArr[0] = this.mDrawable;
        drawableArr[1] = drawableMutate;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setBrightness(float brightness) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mBrightness = brightness;
        imageMatrix.updateMatrix(this);
    }

    public void setContrast(float contrast) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mContrast = contrast;
        imageMatrix.updateMatrix(this);
    }

    public void setCrossfade(float crossfade) {
        this.mCrossfade = crossfade;
        if (this.mLayers != null) {
            if (!this.mOverlay) {
                this.mLayer.getDrawable(0).setAlpha((int) ((1.0f - this.mCrossfade) * 255.0f));
            }
            this.mLayer.getDrawable(1).setAlpha((int) (this.mCrossfade * 255.0f));
            super.setImageDrawable(this.mLayer);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (this.mAltDrawable == null || drawable == null) {
            super.setImageDrawable(drawable);
            return;
        }
        Drawable drawableMutate = drawable.mutate();
        this.mDrawable = drawableMutate;
        Drawable[] drawableArr = this.mLayers;
        drawableArr[0] = drawableMutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setImagePanX(float pan) {
        this.mPanX = pan;
        updateViewMatrix();
    }

    public void setImagePanY(float pan) {
        this.mPanY = pan;
        updateViewMatrix();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int resId) {
        if (this.mAltDrawable == null) {
            super.setImageResource(resId);
            return;
        }
        Drawable drawableMutate = AppCompatResources.getDrawable(getContext(), resId).mutate();
        this.mDrawable = drawableMutate;
        Drawable[] drawableArr = this.mLayers;
        drawableArr[0] = drawableMutate;
        drawableArr[1] = this.mAltDrawable;
        LayerDrawable layerDrawable = new LayerDrawable(this.mLayers);
        this.mLayer = layerDrawable;
        super.setImageDrawable(layerDrawable);
        setCrossfade(this.mCrossfade);
    }

    public void setImageRotate(float rotation) {
        this.mRotate = rotation;
        updateViewMatrix();
    }

    public void setImageZoom(float zoom) {
        this.mZoom = zoom;
        updateViewMatrix();
    }

    @RequiresApi(21)
    public void setRound(float round) {
        if (Float.isNaN(round)) {
            this.mRound = round;
            float f2 = this.mRoundPercent;
            this.mRoundPercent = -1.0f;
            setRoundPercent(f2);
            return;
        }
        boolean z2 = this.mRound != round;
        this.mRound = round;
        if (round != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.2
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), ImageFilterView.this.mRound);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            this.mRect.set(0.0f, 0.0f, getWidth(), getHeight());
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.mRect;
            float f3 = this.mRound;
            path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    @RequiresApi(21)
    public void setRoundPercent(float round) {
        boolean z2 = this.mRoundPercent != round;
        this.mRoundPercent = round;
        if (round != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (this.mViewOutlineProvider == null) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: androidx.constraintlayout.utils.widget.ImageFilterView.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, ImageFilterView.this.getWidth(), ImageFilterView.this.getHeight(), (Math.min(r3, r4) * ImageFilterView.this.mRoundPercent) / 2.0f);
                    }
                };
                this.mViewOutlineProvider = viewOutlineProvider;
                setOutlineProvider(viewOutlineProvider);
            }
            setClipToOutline(true);
            int width = getWidth();
            int height = getHeight();
            float fMin = (Math.min(width, height) * this.mRoundPercent) / 2.0f;
            this.mRect.set(0.0f, 0.0f, width, height);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, fMin, fMin, Path.Direction.CW);
        } else {
            setClipToOutline(false);
        }
        if (z2) {
            invalidateOutline();
        }
    }

    public void setSaturation(float saturation) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mSaturation = saturation;
        imageMatrix.updateMatrix(this);
    }

    public void setWarmth(float warmth) {
        ImageMatrix imageMatrix = this.mImageMatrix;
        imageMatrix.mWarmth = warmth;
        imageMatrix.updateMatrix(this);
    }

    public ImageFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attrs);
    }

    public ImageFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mImageMatrix = new ImageMatrix();
        this.mOverlay = true;
        this.mAltDrawable = null;
        this.mDrawable = null;
        this.mCrossfade = 0.0f;
        this.mRoundPercent = 0.0f;
        this.mRound = Float.NaN;
        this.mLayers = new Drawable[2];
        this.mPanX = Float.NaN;
        this.mPanY = Float.NaN;
        this.mZoom = Float.NaN;
        this.mRotate = Float.NaN;
        init(context, attrs);
    }
}
