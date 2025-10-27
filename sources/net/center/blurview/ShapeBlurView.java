package net.center.blurview;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import net.center.blurview.impl.AndroidStockBlurImpl;
import net.center.blurview.impl.AndroidXBlurImpl;
import net.center.blurview.impl.BlurImpl;
import net.center.blurview.impl.EmptyBlurImpl;
import net.center.blurview.impl.SupportLibraryBlurImpl;

/* loaded from: classes9.dex */
public class ShapeBlurView extends View {
    private static int BLUR_IMPL = 0;
    public static final int DEFAULT_BORDER_COLOR = -1;
    private static final float DEFAULT_BORDER_WIDTH = 0.0f;
    private static final float DEFAULT_RADIUS = 0.0f;
    private static int RENDERING_COUNT;
    private static StopException STOP_EXCEPTION = new StopException();
    private int blurMode;
    private float cRadius;
    private final Path cornerPath;
    private float[] cornerRids;
    private float cx;
    private float cy;
    private final Paint mBitmapPaint;
    private Bitmap mBitmapToBlur;
    private final BlurImpl mBlurImpl;
    private float mBlurRadius;
    private Bitmap mBlurredBitmap;
    private Canvas mBlurringCanvas;
    private ColorStateList mBorderColor;
    private final Paint mBorderPaint;
    private final RectF mBorderRect;
    private float mBorderWidth;
    private Context mContext;
    private final float[] mCornerRadii;
    private View mDecorView;
    private boolean mDifferentRoot;
    private boolean mDirty;
    private float mDownSampleFactor;
    private boolean mIsRendering;
    private int mOverlayColor;
    private final RectF mRectFDst;
    private final Rect mRectSrc;
    private Matrix matrix;
    private final ViewTreeObserver.OnPreDrawListener preDrawListener;
    private BitmapShader shader;

    public static class Builder {
        private int blurMode;
        private float mBlurRadius;
        private ColorStateList mBorderColor;
        private float mBorderWidth;
        private Context mContext;
        private final float[] mCornerRadii;
        private float mDownSampleFactor;
        private int mOverlayColor;

        public Builder setBlurMode(int i2) {
            this.blurMode = i2;
            return this;
        }

        public Builder setBlurRadius(@FloatRange(from = 0.0d, to = 25.0d) float f2) {
            this.mBlurRadius = f2;
            return this;
        }

        public Builder setBorderColor(@ColorRes int i2) {
            return setBorderColor(ColorStateList.valueOf(ContextCompat.getColor(this.mContext, i2)));
        }

        public Builder setBorderWidth(@DimenRes int i2) {
            return setBorderWidth(this.mContext.getResources().getDimension(i2));
        }

        public Builder setCornerRadius(int i2, float f2) {
            this.mCornerRadii[i2] = f2;
            return this;
        }

        public Builder setCornerRadiusDimen(@DimenRes int i2) throws Resources.NotFoundException {
            float dimension = this.mContext.getResources().getDimension(i2);
            return setCornerRadius(dimension, dimension, dimension, dimension);
        }

        public Builder setDownSampleFactor(float f2) {
            if (f2 <= 0.0f) {
                throw new IllegalArgumentException("DownSample factor must be greater than 0.");
            }
            this.mDownSampleFactor = f2;
            return this;
        }

        public Builder setOverlayColor(int i2) {
            this.mOverlayColor = i2;
            return this;
        }

        private Builder(Context context) {
            this.mDownSampleFactor = -1.0f;
            this.mOverlayColor = -1;
            this.mBlurRadius = -1.0f;
            this.mBorderWidth = -1.0f;
            this.mBorderColor = null;
            this.blurMode = -1;
            this.mCornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
            this.mContext = context.getApplicationContext();
        }

        public Builder setBorderColor(ColorStateList colorStateList) {
            if (colorStateList == null) {
                colorStateList = ColorStateList.valueOf(-1);
            }
            this.mBorderColor = colorStateList;
            return this;
        }

        public Builder setBorderWidth(float f2) {
            this.mBorderWidth = f2;
            return this;
        }

        public Builder setCornerRadius(float f2) {
            return setCornerRadius(f2, f2, f2, f2);
        }

        public Builder setCornerRadius(float f2, float f3, float f4, float f5) {
            float[] fArr = this.mCornerRadii;
            fArr[0] = f2;
            fArr[1] = f3;
            fArr[3] = f4;
            fArr[2] = f5;
            return this;
        }
    }

    public static class StopException extends RuntimeException {
        private StopException() {
        }
    }

    public ShapeBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRectSrc = new Rect();
        this.mRectFDst = new RectF();
        this.blurMode = 0;
        this.cx = 0.0f;
        this.cy = 0.0f;
        this.cRadius = 0.0f;
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f};
        this.mCornerRadii = fArr;
        this.cornerPath = new Path();
        this.mBorderRect = new RectF();
        this.mBorderWidth = 0.0f;
        this.mBorderColor = ColorStateList.valueOf(-1);
        this.matrix = new Matrix();
        this.preDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: net.center.blurview.ShapeBlurView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                int[] iArr = new int[2];
                Bitmap bitmap = ShapeBlurView.this.mBlurredBitmap;
                View view = ShapeBlurView.this.mDecorView;
                if (view != null && ShapeBlurView.this.isShown() && ShapeBlurView.this.prepare()) {
                    boolean z2 = ShapeBlurView.this.mBlurredBitmap != bitmap;
                    view.getLocationOnScreen(iArr);
                    int i2 = -iArr[0];
                    int i3 = -iArr[1];
                    ShapeBlurView.this.getLocationOnScreen(iArr);
                    int i4 = i2 + iArr[0];
                    int i5 = i3 + iArr[1];
                    ShapeBlurView.this.mBitmapToBlur.eraseColor(ShapeBlurView.this.mOverlayColor & 16777215);
                    int iSave = ShapeBlurView.this.mBlurringCanvas.save();
                    ShapeBlurView.this.mIsRendering = true;
                    ShapeBlurView.access$608();
                    try {
                        ShapeBlurView.this.mBlurringCanvas.scale((ShapeBlurView.this.mBitmapToBlur.getWidth() * 1.0f) / ShapeBlurView.this.getWidth(), (ShapeBlurView.this.mBitmapToBlur.getHeight() * 1.0f) / ShapeBlurView.this.getHeight());
                        ShapeBlurView.this.mBlurringCanvas.translate(-i4, -i5);
                        if (view.getBackground() != null) {
                            view.getBackground().draw(ShapeBlurView.this.mBlurringCanvas);
                        }
                        view.draw(ShapeBlurView.this.mBlurringCanvas);
                    } catch (StopException unused) {
                    } catch (Throwable th) {
                        ShapeBlurView.this.mIsRendering = false;
                        ShapeBlurView.access$610();
                        ShapeBlurView.this.mBlurringCanvas.restoreToCount(iSave);
                        throw th;
                    }
                    ShapeBlurView.this.mIsRendering = false;
                    ShapeBlurView.access$610();
                    ShapeBlurView.this.mBlurringCanvas.restoreToCount(iSave);
                    ShapeBlurView shapeBlurView = ShapeBlurView.this;
                    shapeBlurView.blur(shapeBlurView.mBitmapToBlur, ShapeBlurView.this.mBlurredBitmap);
                    if (z2 || ShapeBlurView.this.mDifferentRoot) {
                        ShapeBlurView.this.invalidate();
                    }
                }
                return true;
            }
        };
        this.mContext = context;
        this.mBlurImpl = getBlurImpl();
        try {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeBlurView);
            this.mBlurRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.ShapeBlurView_blur_radius, TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics()));
            this.mDownSampleFactor = typedArrayObtainStyledAttributes.getFloat(R.styleable.ShapeBlurView_blur_down_sample, 4.0f);
            this.mOverlayColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ShapeBlurView_blur_overlay_color, 0);
            float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_corner_radius, -1);
            fArr[0] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_corner_radius_top_left, -1);
            fArr[1] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_corner_radius_top_right, -1);
            fArr[2] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_corner_radius_bottom_right, -1);
            fArr[3] = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_corner_radius_bottom_left, -1);
            initCornerData(dimensionPixelSize);
            this.blurMode = typedArrayObtainStyledAttributes.getInt(R.styleable.ShapeBlurView_blur_mode, 0);
            float dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeBlurView_blur_border_width, -1);
            this.mBorderWidth = dimensionPixelSize2;
            if (dimensionPixelSize2 < 0.0f) {
                this.mBorderWidth = 0.0f;
            }
            ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R.styleable.ShapeBlurView_blur_border_color);
            this.mBorderColor = colorStateList;
            if (colorStateList == null) {
                this.mBorderColor = ColorStateList.valueOf(-1);
            }
            typedArrayObtainStyledAttributes.recycle();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Paint paint = new Paint();
        this.mBitmapPaint = paint;
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mBorderPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setColor(this.mBorderColor.getColorForState(getState(), -1));
        paint2.setStrokeWidth(this.mBorderWidth);
    }

    public static /* synthetic */ int access$608() {
        int i2 = RENDERING_COUNT;
        RENDERING_COUNT = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$610() {
        int i2 = RENDERING_COUNT;
        RENDERING_COUNT = i2 - 1;
        return i2;
    }

    public static Builder build(Context context) {
        return new Builder(context);
    }

    private void drawCircleRectBitmap(Canvas canvas, Bitmap bitmap, int i2) {
        try {
            this.mRectFDst.right = getMeasuredWidth();
            this.mRectFDst.bottom = getMeasuredHeight();
            this.mRectSrc.right = bitmap.getWidth();
            this.mRectSrc.bottom = bitmap.getHeight();
            this.mBitmapPaint.reset();
            this.mBitmapPaint.setAntiAlias(true);
            if (this.shader == null) {
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                this.shader = new BitmapShader(bitmap, tileMode, tileMode);
            }
            if (this.matrix == null) {
                Matrix matrix = new Matrix();
                this.matrix = matrix;
                matrix.postScale(this.mRectFDst.width() / this.mRectSrc.width(), this.mRectFDst.height() / this.mRectSrc.height());
            }
            this.shader.setLocalMatrix(this.matrix);
            this.mBitmapPaint.setShader(this.shader);
            if (this.mRectFDst.width() >= this.mRectSrc.width()) {
                this.cx = this.mRectFDst.width() / 2.0f;
                this.cy = this.mRectFDst.height() / 2.0f;
                this.cRadius = Math.min(this.mRectFDst.width(), this.mRectFDst.height()) / 2.0f;
                this.mBorderRect.set(this.mRectFDst);
            } else {
                this.cx = this.mRectSrc.width() / 2.0f;
                this.cy = this.mRectSrc.height() / 2.0f;
                this.cRadius = Math.min(this.mRectSrc.width(), this.mRectSrc.height()) / 2.0f;
                this.mBorderRect.set(this.mRectSrc);
            }
            canvas.drawCircle(this.cx, this.cy, this.cRadius, this.mBitmapPaint);
            this.mBitmapPaint.reset();
            this.mBitmapPaint.setAntiAlias(true);
            this.mBitmapPaint.setColor(i2);
            canvas.drawCircle(this.cx, this.cy, this.cRadius, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                if (this.mBorderRect.width() > this.mBorderRect.height()) {
                    float fAbs = Math.abs(this.mBorderRect.height() - this.mBorderRect.width()) / 2.0f;
                    RectF rectF = this.mBorderRect;
                    rectF.left = fAbs;
                    rectF.right = Math.min(rectF.width(), this.mBorderRect.height()) + fAbs;
                    RectF rectF2 = this.mBorderRect;
                    rectF2.bottom = Math.min(rectF2.width(), this.mBorderRect.height());
                } else if (this.mBorderRect.width() < this.mBorderRect.height()) {
                    float fAbs2 = Math.abs(this.mBorderRect.height() - this.mBorderRect.width()) / 2.0f;
                    RectF rectF3 = this.mBorderRect;
                    rectF3.top = fAbs2;
                    rectF3.right = Math.min(rectF3.width(), this.mBorderRect.height());
                    RectF rectF4 = this.mBorderRect;
                    rectF4.bottom = Math.min(rectF4.width(), this.mBorderRect.height()) + fAbs2;
                } else {
                    RectF rectF5 = this.mBorderRect;
                    rectF5.right = Math.min(rectF5.width(), this.mBorderRect.height());
                    RectF rectF6 = this.mBorderRect;
                    rectF6.bottom = Math.min(rectF6.width(), this.mBorderRect.height());
                }
                RectF rectF7 = this.mBorderRect;
                float f2 = this.mBorderWidth;
                rectF7.inset(f2 / 2.0f, f2 / 2.0f);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void drawOvalRectBitmap(Canvas canvas, Bitmap bitmap, int i2) {
        try {
            this.mRectFDst.right = getWidth();
            this.mRectFDst.bottom = getHeight();
            this.mBitmapPaint.reset();
            this.mBitmapPaint.setAntiAlias(true);
            if (this.shader == null) {
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                this.shader = new BitmapShader(bitmap, tileMode, tileMode);
            }
            if (this.matrix == null) {
                Matrix matrix = new Matrix();
                this.matrix = matrix;
                matrix.postScale(this.mRectFDst.width() / bitmap.getWidth(), this.mRectFDst.height() / bitmap.getHeight());
            }
            this.shader.setLocalMatrix(this.matrix);
            this.mBitmapPaint.setShader(this.shader);
            canvas.drawOval(this.mRectFDst, this.mBitmapPaint);
            this.mBitmapPaint.reset();
            this.mBitmapPaint.setAntiAlias(true);
            this.mBitmapPaint.setColor(i2);
            canvas.drawOval(this.mRectFDst, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                this.mBorderRect.set(this.mRectFDst);
                RectF rectF = this.mBorderRect;
                float f2 = this.mBorderWidth;
                rectF.inset(f2 / 2.0f, f2 / 2.0f);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void drawRoundRectBitmap(Canvas canvas, Bitmap bitmap, int i2) {
        try {
            this.mRectFDst.right = getWidth();
            this.mRectFDst.bottom = getHeight();
            this.cornerPath.addRoundRect(this.mRectFDst, this.cornerRids, Path.Direction.CW);
            this.cornerPath.close();
            canvas.clipPath(this.cornerPath);
            this.mRectSrc.right = bitmap.getWidth();
            this.mRectSrc.bottom = bitmap.getHeight();
            canvas.drawBitmap(bitmap, this.mRectSrc, this.mRectFDst, (Paint) null);
            this.mBitmapPaint.setColor(i2);
            canvas.drawRect(this.mRectFDst, this.mBitmapPaint);
            float f2 = this.mBorderWidth;
            if (f2 > 0.0f) {
                this.mBorderPaint.setStrokeWidth(f2 * 2.0f);
                canvas.drawPath(this.cornerPath, this.mBorderPaint);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void initCornerData(float f2) {
        int length = this.mCornerRadii.length;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            float[] fArr = this.mCornerRadii;
            if (fArr[i2] < 0.0f) {
                fArr[i2] = 0.0f;
            } else {
                z2 = true;
            }
        }
        if (!z2) {
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            int length2 = this.mCornerRadii.length;
            for (int i3 = 0; i3 < length2; i3++) {
                this.mCornerRadii[i3] = f2;
            }
        }
        initCornerRids();
    }

    private void initCornerRids() {
        float[] fArr = this.cornerRids;
        if (fArr == null) {
            float[] fArr2 = this.mCornerRadii;
            float f2 = fArr2[1];
            float f3 = fArr2[2];
            float f4 = fArr2[3];
            this.cornerRids = new float[]{fArr2[0], fArr2[0], f2, f2, f3, f3, f4, f4};
            return;
        }
        float[] fArr3 = this.mCornerRadii;
        fArr[0] = fArr3[0];
        fArr[1] = fArr3[0];
        float f5 = fArr3[1];
        fArr[2] = f5;
        fArr[3] = f5;
        float f6 = fArr3[2];
        fArr[4] = f6;
        fArr[5] = f6;
        float f7 = fArr3[3];
        fArr[6] = f7;
        fArr[7] = f7;
    }

    private void releaseBitmap() {
        Bitmap bitmap = this.mBitmapToBlur;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBitmapToBlur = null;
        }
        Bitmap bitmap2 = this.mBlurredBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mBlurredBitmap = null;
        }
        if (this.matrix != null) {
            this.matrix = null;
        }
        if (this.shader != null) {
            this.shader = null;
        }
        this.mContext = null;
    }

    public void blur(Bitmap bitmap, Bitmap bitmap2) {
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.shader = new BitmapShader(bitmap2, tileMode, tileMode);
        this.mBlurImpl.blur(bitmap, bitmap2);
    }

    public int dp2px(float f2) {
        return (int) ((f2 * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        if (this.mIsRendering) {
            throw STOP_EXCEPTION;
        }
        if (RENDERING_COUNT > 0) {
            return;
        }
        super.draw(canvas);
    }

    public void drawBlurredBitmap(Canvas canvas, Bitmap bitmap, int i2) {
        if (bitmap != null) {
            int i3 = this.blurMode;
            if (i3 == 1) {
                drawCircleRectBitmap(canvas, bitmap, i2);
            } else if (i3 == 2) {
                drawOvalRectBitmap(canvas, bitmap, i2);
            } else {
                drawRoundRectBitmap(canvas, bitmap, i2);
            }
        }
    }

    public View getActivityDecorView() {
        Context context = getContext();
        for (int i2 = 0; i2 < 4 && !(context instanceof Activity) && (context instanceof ContextWrapper); i2++) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return ((Activity) context).getWindow().getDecorView();
        }
        return null;
    }

    public BlurImpl getBlurImpl() {
        if (BLUR_IMPL == 0) {
            try {
                AndroidStockBlurImpl androidStockBlurImpl = new AndroidStockBlurImpl();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                androidStockBlurImpl.prepare(getContext(), bitmapCreateBitmap, 4.0f);
                androidStockBlurImpl.release();
                bitmapCreateBitmap.recycle();
                BLUR_IMPL = 3;
            } catch (Throwable unused) {
            }
        }
        if (BLUR_IMPL == 0) {
            try {
                getClass().getClassLoader().loadClass("androidx.renderscript.RenderScript");
                AndroidXBlurImpl androidXBlurImpl = new AndroidXBlurImpl();
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                androidXBlurImpl.prepare(getContext(), bitmapCreateBitmap2, 4.0f);
                androidXBlurImpl.release();
                bitmapCreateBitmap2.recycle();
                BLUR_IMPL = 1;
            } catch (Throwable unused2) {
            }
        }
        if (BLUR_IMPL == 0) {
            try {
                getClass().getClassLoader().loadClass("android.support.v8.renderscript.RenderScript");
                SupportLibraryBlurImpl supportLibraryBlurImpl = new SupportLibraryBlurImpl();
                Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                supportLibraryBlurImpl.prepare(getContext(), bitmapCreateBitmap3, 4.0f);
                supportLibraryBlurImpl.release();
                bitmapCreateBitmap3.recycle();
                BLUR_IMPL = 2;
            } catch (Throwable unused3) {
            }
        }
        if (BLUR_IMPL == 0) {
            BLUR_IMPL = -1;
        }
        int i2 = BLUR_IMPL;
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? new EmptyBlurImpl() : new AndroidStockBlurImpl() : new SupportLibraryBlurImpl() : new AndroidXBlurImpl();
    }

    public int getBlurMode() {
        return this.blurMode;
    }

    @ColorInt
    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
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

    @NonNull
    public int[] getState() {
        return StateSet.WILD_CARD;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View activityDecorView = getActivityDecorView();
        this.mDecorView = activityDecorView;
        if (activityDecorView == null) {
            this.mDifferentRoot = false;
            return;
        }
        activityDecorView.getViewTreeObserver().addOnPreDrawListener(this.preDrawListener);
        boolean z2 = this.mDecorView.getRootView() != getRootView();
        this.mDifferentRoot = z2;
        if (z2) {
            this.mDecorView.postInvalidate();
        }
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        View view = this.mDecorView;
        if (view != null) {
            view.getViewTreeObserver().removeOnPreDrawListener(this.preDrawListener);
        }
        release();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBlurredBitmap(canvas, this.mBlurredBitmap, this.mOverlayColor);
    }

    public boolean prepare() {
        Bitmap bitmap;
        float f2 = this.mBlurRadius;
        if (f2 == 0.0f) {
            release();
            return false;
        }
        float f3 = this.mDownSampleFactor;
        float f4 = f2 / f3;
        if (f4 > 25.0f) {
            f3 = (f3 * f4) / 25.0f;
            f4 = 25.0f;
        }
        int width = getWidth();
        int height = getHeight();
        int iMax = Math.max(1, (int) (width / f3));
        int iMax2 = Math.max(1, (int) (height / f3));
        boolean z2 = this.mDirty;
        if (this.mBlurringCanvas == null || (bitmap = this.mBlurredBitmap) == null || bitmap.getWidth() != iMax || this.mBlurredBitmap.getHeight() != iMax2) {
            releaseBitmap();
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMax, iMax2, Bitmap.Config.ARGB_8888);
                this.mBitmapToBlur = bitmapCreateBitmap;
                if (bitmapCreateBitmap == null) {
                    release();
                    return false;
                }
                this.mBlurringCanvas = new Canvas(this.mBitmapToBlur);
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(iMax, iMax2, Bitmap.Config.ARGB_8888);
                this.mBlurredBitmap = bitmapCreateBitmap2;
                if (bitmapCreateBitmap2 == null) {
                    release();
                    return false;
                }
                z2 = true;
            } catch (OutOfMemoryError unused) {
                release();
                return false;
            } catch (Throwable unused2) {
                release();
                return false;
            }
        }
        if (z2) {
            if (!this.mBlurImpl.prepare(getContext(), this.mBitmapToBlur, f4)) {
                return false;
            }
            this.mDirty = false;
        }
        return true;
    }

    public void refreshView(Builder builder) {
        boolean z2;
        if (builder == null) {
            return;
        }
        boolean z3 = true;
        if (builder.blurMode == -1 || this.blurMode == builder.blurMode) {
            z2 = false;
        } else {
            this.blurMode = builder.blurMode;
            z2 = true;
        }
        if (builder.mBorderColor != null && !this.mBorderColor.equals(builder.mBorderColor)) {
            ColorStateList colorStateList = builder.mBorderColor;
            this.mBorderColor = colorStateList;
            this.mBorderPaint.setColor(colorStateList.getColorForState(getState(), -1));
            if (this.mBorderWidth > 0.0f) {
                z2 = true;
            }
        }
        if (builder.mBorderWidth > 0.0f) {
            float f2 = builder.mBorderWidth;
            this.mBorderWidth = f2;
            this.mBorderPaint.setStrokeWidth(f2);
            z2 = true;
        }
        if (this.mCornerRadii[0] != builder.mCornerRadii[0] || this.mCornerRadii[1] != builder.mCornerRadii[1] || this.mCornerRadii[2] != builder.mCornerRadii[2] || this.mCornerRadii[3] != builder.mCornerRadii[3]) {
            this.mCornerRadii[0] = builder.mCornerRadii[0];
            this.mCornerRadii[1] = builder.mCornerRadii[1];
            this.mCornerRadii[3] = builder.mCornerRadii[3];
            this.mCornerRadii[2] = builder.mCornerRadii[2];
            initCornerRids();
            z2 = true;
        }
        if (builder.mOverlayColor != -1 && this.mOverlayColor != builder.mOverlayColor) {
            this.mOverlayColor = builder.mOverlayColor;
            z2 = true;
        }
        if (builder.mBlurRadius > 0.0f && this.mBlurRadius != builder.mBlurRadius) {
            this.mBlurRadius = builder.mBlurRadius;
            this.mDirty = true;
            z2 = true;
        }
        if (builder.mDownSampleFactor <= 0.0f || this.mDownSampleFactor == builder.mDownSampleFactor) {
            z3 = z2;
        } else {
            this.mDownSampleFactor = builder.mDownSampleFactor;
            this.mDirty = true;
            releaseBitmap();
        }
        if (z3) {
            invalidate();
        }
    }

    public void release() {
        releaseBitmap();
        this.mBlurImpl.release();
    }
}
