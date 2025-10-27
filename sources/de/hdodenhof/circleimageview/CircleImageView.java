package de.hdodenhof.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

/* loaded from: classes8.dex */
public class CircleImageView extends ImageView {
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_CIRCLE_BACKGROUND_COLOR = 0;
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private final Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBorderColor;
    private boolean mBorderOverlay;
    private final Paint mBorderPaint;
    private float mBorderRadius;
    private final RectF mBorderRect;
    private int mBorderWidth;
    private int mCircleBackgroundColor;
    private final Paint mCircleBackgroundPaint;
    private ColorFilter mColorFilter;
    private boolean mDisableCircularTransformation;
    private float mDrawableRadius;
    private final RectF mDrawableRect;
    private boolean mReady;
    private boolean mSetupPending;
    private final Matrix mShaderMatrix;
    private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    @RequiresApi(api = 21)
    public class OutlineProvider extends ViewOutlineProvider {
        private OutlineProvider() {
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            Rect rect = new Rect();
            CircleImageView.this.mBorderRect.roundOut(rect);
            outline.setRoundRect(rect, rect.width() / 2.0f);
        }
    }

    public CircleImageView(Context context) {
        super(context);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mCircleBackgroundPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 0;
        this.mCircleBackgroundColor = 0;
        init();
    }

    private void applyColorFilter() {
        Paint paint = this.mBitmapPaint;
        if (paint != null) {
            paint.setColorFilter(this.mColorFilter);
        }
    }

    private RectF calculateBounds() {
        int iMin = Math.min((getWidth() - getPaddingLeft()) - getPaddingRight(), (getHeight() - getPaddingTop()) - getPaddingBottom());
        float paddingLeft = getPaddingLeft() + ((r0 - iMin) / 2.0f);
        float paddingTop = getPaddingTop() + ((r1 - iMin) / 2.0f);
        float f2 = iMin;
        return new RectF(paddingLeft, paddingTop, paddingLeft + f2, f2 + paddingTop);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmapCreateBitmap = drawable instanceof ColorDrawable ? Bitmap.createBitmap(2, 2, BITMAP_CONFIG) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmapCreateBitmap;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        this.mReady = true;
        setOutlineProvider(new OutlineProvider());
        if (this.mSetupPending) {
            setup();
            this.mSetupPending = false;
        }
    }

    private void initializeBitmap() {
        if (this.mDisableCircularTransformation) {
            this.mBitmap = null;
        } else {
            this.mBitmap = getBitmapFromDrawable(getDrawable());
        }
        setup();
    }

    private void setup() {
        int i2;
        if (!this.mReady) {
            this.mSetupPending = true;
            return;
        }
        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }
        if (this.mBitmap == null) {
            invalidate();
            return;
        }
        Bitmap bitmap = this.mBitmap;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.mBitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setShader(this.mBitmapShader);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor);
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
        this.mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mCircleBackgroundPaint.setAntiAlias(true);
        this.mCircleBackgroundPaint.setColor(this.mCircleBackgroundColor);
        this.mBitmapHeight = this.mBitmap.getHeight();
        this.mBitmapWidth = this.mBitmap.getWidth();
        this.mBorderRect.set(calculateBounds());
        this.mBorderRadius = Math.min((this.mBorderRect.height() - this.mBorderWidth) / 2.0f, (this.mBorderRect.width() - this.mBorderWidth) / 2.0f);
        this.mDrawableRect.set(this.mBorderRect);
        if (!this.mBorderOverlay && (i2 = this.mBorderWidth) > 0) {
            this.mDrawableRect.inset(i2 - 1.0f, i2 - 1.0f);
        }
        this.mDrawableRadius = Math.min(this.mDrawableRect.height() / 2.0f, this.mDrawableRect.width() / 2.0f);
        applyColorFilter();
        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float fWidth;
        float fHeight;
        this.mShaderMatrix.set(null);
        float fWidth2 = 0.0f;
        if (this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * this.mBitmapHeight) {
            fWidth = this.mDrawableRect.height() / this.mBitmapHeight;
            fHeight = 0.0f;
            fWidth2 = (this.mDrawableRect.width() - (this.mBitmapWidth * fWidth)) * 0.5f;
        } else {
            fWidth = this.mDrawableRect.width() / this.mBitmapWidth;
            fHeight = (this.mDrawableRect.height() - (this.mBitmapHeight * fWidth)) * 0.5f;
        }
        this.mShaderMatrix.setScale(fWidth, fWidth);
        Matrix matrix = this.mShaderMatrix;
        RectF rectF = this.mDrawableRect;
        matrix.postTranslate(((int) (fWidth2 + 0.5f)) + rectF.left, ((int) (fHeight + 0.5f)) + rectF.top);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public int getCircleBackgroundColor() {
        return this.mCircleBackgroundColor;
    }

    @Override // android.widget.ImageView
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Deprecated
    public int getFillColor() {
        return getCircleBackgroundColor();
    }

    @Override // android.widget.ImageView
    public ImageView.ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    public boolean isBorderOverlay() {
        return this.mBorderOverlay;
    }

    public boolean isDisableCircularTransformation() {
        return this.mDisableCircularTransformation;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }
        if (this.mBitmap == null) {
            return;
        }
        if (this.mCircleBackgroundColor != 0) {
            canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mCircleBackgroundPaint);
        }
        canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mBitmapPaint);
        if (this.mBorderWidth > 0) {
            canvas.drawCircle(this.mBorderRect.centerX(), this.mBorderRect.centerY(), this.mBorderRadius, this.mBorderPaint);
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        setup();
    }

    @Override // android.widget.ImageView
    public void setAdjustViewBounds(boolean z2) {
        if (z2) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setBorderColor(@ColorInt int i2) {
        if (i2 == this.mBorderColor) {
            return;
        }
        this.mBorderColor = i2;
        this.mBorderPaint.setColor(i2);
        invalidate();
    }

    @Deprecated
    public void setBorderColorResource(@ColorRes int i2) {
        setBorderColor(getContext().getResources().getColor(i2));
    }

    public void setBorderOverlay(boolean z2) {
        if (z2 == this.mBorderOverlay) {
            return;
        }
        this.mBorderOverlay = z2;
        setup();
    }

    public void setBorderWidth(int i2) {
        if (i2 == this.mBorderWidth) {
            return;
        }
        this.mBorderWidth = i2;
        setup();
    }

    public void setCircleBackgroundColor(@ColorInt int i2) {
        if (i2 == this.mCircleBackgroundColor) {
            return;
        }
        this.mCircleBackgroundColor = i2;
        this.mCircleBackgroundPaint.setColor(i2);
        invalidate();
    }

    public void setCircleBackgroundColorResource(@ColorRes int i2) {
        setCircleBackgroundColor(getContext().getResources().getColor(i2));
    }

    @Override // android.widget.ImageView
    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter == this.mColorFilter) {
            return;
        }
        this.mColorFilter = colorFilter;
        applyColorFilter();
        invalidate();
    }

    public void setDisableCircularTransformation(boolean z2) {
        if (this.mDisableCircularTransformation == z2) {
            return;
        }
        this.mDisableCircularTransformation = z2;
        initializeBitmap();
    }

    @Deprecated
    public void setFillColor(@ColorInt int i2) {
        setCircleBackgroundColor(i2);
    }

    @Deprecated
    public void setFillColorResource(@ColorRes int i2) {
        setCircleBackgroundColorResource(i2);
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        initializeBitmap();
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
    }

    @Override // android.widget.ImageView
    public void setImageResource(@DrawableRes int i2) {
        super.setImageResource(i2);
        initializeBitmap();
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }

    @Override // android.view.View
    public void setPadding(int i2, int i3, int i4, int i5) {
        super.setPadding(i2, i3, i4, i5);
        setup();
    }

    @Override // android.view.View
    public void setPaddingRelative(int i2, int i3, int i4, int i5) {
        super.setPaddingRelative(i2, i3, i4, i5);
        setup();
    }

    @Override // android.widget.ImageView
    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mCircleBackgroundPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 0;
        this.mCircleBackgroundColor = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView, i2, 0);
        this.mBorderWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, 0);
        this.mBorderColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleImageView_civ_border_color, -16777216);
        this.mBorderOverlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CircleImageView_civ_border_overlay, false);
        int i3 = R.styleable.CircleImageView_civ_circle_background_color;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            this.mCircleBackgroundColor = typedArrayObtainStyledAttributes.getColor(i3, 0);
        } else {
            int i4 = R.styleable.CircleImageView_civ_fill_color;
            if (typedArrayObtainStyledAttributes.hasValue(i4)) {
                this.mCircleBackgroundColor = typedArrayObtainStyledAttributes.getColor(i4, 0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        init();
    }
}
