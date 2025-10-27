package com.ruffian.library.widget.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import com.ruffian.library.widget.R;

@Deprecated
/* loaded from: classes6.dex */
public class RImageViewHelper {
    private Paint mBitmapPaint;
    private Path mBitmapPath;
    private BitmapShader mBitmapShader;
    private Paint mBorderPaint;
    private Drawable mIconNormal;
    private Drawable mIconPressed;
    private Drawable mIconSelected;
    private Drawable mIconUnable;
    private StateListDrawable mStateDrawable;
    private ImageView mView;
    private float mCorner = -1.0f;
    private float mCornerTopLeft = 0.0f;
    private float mCornerTopRight = 0.0f;
    private float mCornerBottomLeft = 0.0f;
    private float mCornerBottomRight = 0.0f;
    private float[] mCornerBorderRadii = new float[8];
    private float[] mCornerBitmapRadii = new float[8];
    private int mBorderWidth = 0;
    private int mBorderColor = -16777216;
    private boolean mIsNormal = true;
    private boolean mIsCircle = false;
    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();
    protected int[][] states = new int[4][];

    /* renamed from: com.ruffian.library.widget.helper.RImageViewHelper$1, reason: invalid class name */
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
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_START.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_END.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.MATRIX.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public RImageViewHelper(Context context, ImageView imageView, AttributeSet attributeSet) {
        this.mView = imageView;
        initAttributeSet(context, attributeSet);
    }

    private void drawBitmap(Canvas canvas) {
        Drawable drawable = this.mView.getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmapFromDrawable = getBitmapFromDrawable(drawable, this.mView.getScaleType(), drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), getWidth(), getHeight());
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(bitmapFromDrawable, tileMode, tileMode);
        this.mBitmapShader = bitmapShader;
        this.mBitmapPaint.setShader(bitmapShader);
        updateCornerBitmapRadii();
        updateDrawableAndBorderRect();
        if (this.mIsCircle) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, Math.min(this.mDrawableRect.width() / 2.0f, this.mDrawableRect.width() / 2.0f), this.mBitmapPaint);
            return;
        }
        this.mBitmapPath.reset();
        this.mBitmapPath.addRoundRect(this.mDrawableRect, this.mCornerBitmapRadii, Path.Direction.CCW);
        canvas.drawPath(this.mBitmapPath, this.mBitmapPaint);
    }

    private void drawBorder(Canvas canvas) {
        if (this.mBorderWidth > 0) {
            this.mBorderPaint.setColor(this.mBorderColor);
            this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
            if (this.mIsCircle) {
                canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, Math.min((this.mBorderRect.width() - this.mBorderWidth) / 2.0f, (this.mBorderRect.height() - this.mBorderWidth) / 2.0f), this.mBorderPaint);
            } else {
                updateCornerBorderRadii();
                Path path = new Path();
                path.addRoundRect(this.mBorderRect, this.mCornerBorderRadii, Path.Direction.CW);
                canvas.drawPath(path, this.mBorderPaint);
            }
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable, ImageView.ScaleType scaleType, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        Matrix matrix = this.mView.getMatrix();
        int paddingLeft = this.mView.getPaddingLeft() + this.mBorderWidth;
        int paddingTop = this.mView.getPaddingTop() + this.mBorderWidth;
        int paddingRight = this.mView.getPaddingRight() + this.mBorderWidth;
        float paddingBottom = (i5 - paddingTop) - (this.mView.getPaddingBottom() + this.mBorderWidth);
        float f4 = i4;
        float f5 = i5;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.translate(paddingLeft, paddingTop);
        canvas.scale(((i4 - paddingLeft) - paddingRight) / f4, paddingBottom / f5);
        float f6 = 0.0f;
        switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()]) {
            case 2:
            case 3:
            case 4:
                matrix.setRectToRect(new RectF(0.0f, 0.0f, i2, i3), new RectF(0.0f, 0.0f, f4, f5), scaleTypeToScaleToFit(scaleType));
                break;
            case 5:
                drawable.setBounds(0, 0, i4, i5);
                matrix = null;
                break;
            case 6:
                if (i5 * i2 > i4 * i3) {
                    float f7 = f5 / i3;
                    float f8 = (f4 - (i2 * f7)) * 0.5f;
                    f2 = f7;
                    f3 = 0.0f;
                    f6 = f8;
                } else {
                    f2 = f4 / i2;
                    f3 = (f5 - (i3 * f2)) * 0.5f;
                }
                matrix.setScale(f2, f2);
                matrix.postTranslate(Math.round(f6), Math.round(f3));
                break;
            case 7:
                float fMin = (i2 > i4 || i3 > i5) ? Math.min(f4 / i2, f5 / i3) : 1.0f;
                float fRound = Math.round((f4 - (i2 * fMin)) * 0.5f);
                float fRound2 = Math.round((f5 - (i3 * fMin)) * 0.5f);
                matrix.setScale(fMin, fMin);
                matrix.postTranslate(fRound, fRound2);
                break;
            case 8:
                if (matrix.isIdentity()) {
                    matrix = null;
                    break;
                }
                break;
            default:
                matrix.setTranslate(Math.round((i4 - i2) * 0.5f), Math.round((i5 - i3) * 0.5f));
                break;
        }
        if (matrix != null) {
            canvas.concat(matrix);
        }
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private int getHeight() {
        return this.mView.getHeight();
    }

    private int getWidth() {
        return this.mView.getWidth();
    }

    private void init() {
        updateCornerBorderRadii();
        updateCornerBitmapRadii();
        if (this.mIsCircle || this.mCorner > 0.0f || this.mCornerTopLeft != 0.0f || this.mCornerTopRight != 0.0f || this.mCornerBottomRight != 0.0f || this.mCornerBottomLeft != 0.0f) {
            this.mIsNormal = false;
        }
        if (this.mBorderPaint == null) {
            this.mBorderPaint = new Paint(1);
        }
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        if (this.mBitmapPaint == null) {
            this.mBitmapPaint = new Paint(1);
        }
        if (this.mBitmapPath == null) {
            this.mBitmapPath = new Path();
        }
    }

    private void initAttributeSet(Context context, AttributeSet attributeSet) {
        if (context == null || attributeSet == null) {
            setup();
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
        typedArrayObtainStyledAttributes.recycle();
        if (this.mIconNormal == null) {
            this.mIconNormal = this.mView.getDrawable();
        }
        init();
        setup();
    }

    private void invalidate() {
        this.mView.invalidate();
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ImageView.ScaleType scaleType) {
        int i2 = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()];
        return i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? Matrix.ScaleToFit.CENTER : Matrix.ScaleToFit.FILL : Matrix.ScaleToFit.CENTER : Matrix.ScaleToFit.END : Matrix.ScaleToFit.START;
    }

    private void setIcon() {
        this.mView.setImageDrawable(this.mStateDrawable);
        this.mView.invalidate();
    }

    private void setup() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        this.mStateDrawable = stateListDrawable;
        if (this.mIconPressed == null) {
            this.mIconPressed = this.mIconNormal;
        }
        if (this.mIconUnable == null) {
            this.mIconUnable = this.mIconNormal;
        }
        if (this.mIconSelected == null) {
            this.mIconSelected = this.mIconNormal;
        }
        int[][] iArr = this.states;
        int[] iArr2 = {-16842910};
        iArr[0] = iArr2;
        iArr[1] = new int[]{android.R.attr.state_pressed};
        iArr[2] = new int[]{android.R.attr.state_selected};
        iArr[3] = new int[]{android.R.attr.state_enabled};
        stateListDrawable.addState(iArr2, this.mIconUnable);
        this.mStateDrawable.addState(this.states[1], this.mIconPressed);
        this.mStateDrawable.addState(this.states[2], this.mIconSelected);
        this.mStateDrawable.addState(this.states[3], this.mIconNormal);
        setIcon();
    }

    private void updateCornerBitmapRadii() {
        float f2 = this.mCorner;
        int i2 = 0;
        if (f2 < 0.0f) {
            if (f2 < 0.0f) {
                float[] fArr = this.mCornerBitmapRadii;
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
            float[] fArr2 = this.mCornerBitmapRadii;
            if (i2 >= fArr2.length) {
                return;
            }
            fArr2[i2] = this.mCorner;
            i2++;
        }
    }

    private void updateCornerBorderRadii() {
        float f2 = this.mCorner;
        int i2 = 0;
        if (f2 < 0.0f) {
            if (f2 < 0.0f) {
                float[] fArr = this.mCornerBorderRadii;
                float f3 = this.mCornerTopLeft;
                fArr[0] = f3 == 0.0f ? f3 : this.mBorderWidth + f3;
                if (f3 != 0.0f) {
                    f3 += this.mBorderWidth;
                }
                fArr[1] = f3;
                float f4 = this.mCornerTopRight;
                fArr[2] = f4 == 0.0f ? f4 : this.mBorderWidth + f4;
                if (f4 != 0.0f) {
                    f4 += this.mBorderWidth;
                }
                fArr[3] = f4;
                float f5 = this.mCornerBottomRight;
                fArr[4] = f5 == 0.0f ? f5 : this.mBorderWidth + f5;
                if (f5 != 0.0f) {
                    f5 += this.mBorderWidth;
                }
                fArr[5] = f5;
                float f6 = this.mCornerBottomLeft;
                fArr[6] = f6 == 0.0f ? f6 : this.mBorderWidth + f6;
                if (f6 != 0.0f) {
                    f6 += this.mBorderWidth;
                }
                fArr[7] = f6;
                return;
            }
            return;
        }
        while (true) {
            float[] fArr2 = this.mCornerBorderRadii;
            if (i2 >= fArr2.length) {
                return;
            }
            float f7 = this.mCorner;
            if (f7 != 0.0f) {
                f7 += this.mBorderWidth;
            }
            fArr2[i2] = f7;
            i2++;
        }
    }

    private void updateDrawableAndBorderRect() {
        float f2 = this.mBorderWidth / 2.0f;
        if (!this.mIsCircle) {
            this.mBorderRect.set(f2, f2, getWidth() - f2, getHeight() - f2);
            RectF rectF = this.mDrawableRect;
            RectF rectF2 = this.mBorderRect;
            rectF.set(rectF2.left + f2, rectF2.top + f2, rectF2.right - f2, rectF2.bottom - f2);
            return;
        }
        float fMin = Math.min(getWidth(), getHeight());
        this.mBorderRect.set(f2, f2, getWidth() - f2, getHeight() - f2);
        RectF rectF3 = this.mDrawableRect;
        RectF rectF4 = this.mBorderRect;
        float f3 = fMin - f2;
        rectF3.set(rectF4.left + f2, rectF4.top + f2, f3, f3);
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getBorderWidth() {
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

    public Drawable getIconNormal() {
        return this.mIconNormal;
    }

    public Drawable getIconPressed() {
        return this.mIconPressed;
    }

    public Drawable getIconSelected() {
        return this.mIconSelected;
    }

    public Drawable getIconUnable() {
        return this.mIconUnable;
    }

    public boolean isNormal() {
        return this.mIsNormal;
    }

    public void onDraw(Canvas canvas) {
        drawBitmap(canvas);
        drawBorder(canvas);
    }

    public RImageViewHelper setBorderColor(@ColorInt int i2) {
        this.mBorderColor = i2;
        invalidate();
        return this;
    }

    public RImageViewHelper setBorderWidth(int i2) {
        this.mBorderWidth = i2;
        invalidate();
        return this;
    }

    public RImageViewHelper setCorner(float f2) {
        this.mCorner = f2;
        init();
        invalidate();
        return this;
    }

    public RImageViewHelper setCornerBottomLeft(float f2) {
        this.mCorner = -1.0f;
        this.mCornerBottomLeft = f2;
        init();
        invalidate();
        return this;
    }

    public RImageViewHelper setCornerBottomRight(float f2) {
        this.mCorner = -1.0f;
        this.mCornerBottomRight = f2;
        init();
        invalidate();
        return this;
    }

    public RImageViewHelper setCornerTopLeft(float f2) {
        this.mCorner = -1.0f;
        this.mCornerTopLeft = f2;
        init();
        invalidate();
        return this;
    }

    public RImageViewHelper setCornerTopRight(float f2) {
        this.mCorner = -1.0f;
        this.mCornerTopRight = f2;
        init();
        invalidate();
        return this;
    }

    public RImageViewHelper setIconNormal(Drawable drawable) {
        this.mIconNormal = drawable;
        if (this.mIconPressed == null) {
            this.mIconPressed = drawable;
        }
        if (this.mIconUnable == null) {
            this.mIconUnable = drawable;
        }
        if (this.mIconSelected == null) {
            this.mIconSelected = drawable;
        }
        setIcon();
        return this;
    }

    public RImageViewHelper setIconPressed(Drawable drawable) {
        this.mIconPressed = drawable;
        setIcon();
        return this;
    }

    public RImageViewHelper setIconSelected(Drawable drawable) {
        this.mIconSelected = drawable;
        setIcon();
        return this;
    }

    public RImageViewHelper setIconUnable(Drawable drawable) {
        this.mIconUnable = drawable;
        setIcon();
        return this;
    }

    public RImageViewHelper setCorner(float f2, float f3, float f4, float f5) {
        this.mCorner = -1.0f;
        this.mCornerTopLeft = f2;
        this.mCornerTopRight = f3;
        this.mCornerBottomRight = f4;
        this.mCornerBottomLeft = f5;
        init();
        invalidate();
        return this;
    }
}
