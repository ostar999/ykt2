package com.pizidea.imagepicker.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes4.dex */
public class SuperImageView extends AppCompatImageView {
    static final int DRAG = 1;
    static float MAX_SCALE = 3.0f;
    static final int NONE = 0;
    static final int ROTATE = 3;
    static final int ZOOM = 2;
    static final int ZOOM_OR_ROTATE = 4;
    float dist;
    float imageH;
    float imageW;
    PointF lastClickPos;
    long lastClickTime;
    private int leftMargins;
    Matrix matrix;
    PointF mid;
    int mode;
    PointF pA;
    PointF pB;
    float rotatedImageH;
    float rotatedImageW;
    double rotation;
    Matrix savedMatrix;
    private int topBottomMargins;
    float viewH;
    float viewW;

    public SuperImageView(Context context) {
        super(context);
        this.matrix = new Matrix();
        this.savedMatrix = new Matrix();
        this.mode = 0;
        this.pA = new PointF();
        this.pB = new PointF();
        this.mid = new PointF();
        this.lastClickPos = new PointF();
        this.lastClickTime = 0L;
        this.rotation = 0.0d;
        this.dist = 1.0f;
        this.leftMargins = 10;
        init();
    }

    private void centerBitmap() {
        RectF rectF = new RectF(0.0f, 0.0f, this.imageW, this.imageH);
        this.matrix.mapRect(rectF);
        this.matrix.postTranslate(((this.viewW - rectF.width()) - (this.leftMargins * 2)) / 2.0f, (-((this.viewH - rectF.height()) - this.topBottomMargins)) / 2.0f);
    }

    private void doubleClick(float f2, float f3) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float fAbs = Math.abs(fArr[0]) + Math.abs(fArr[1]);
        float fMax = Math.max((this.viewW - (this.leftMargins * 2)) / this.rotatedImageW, (this.viewH - this.topBottomMargins) / this.rotatedImageH);
        if (fAbs <= fMax + 0.01d) {
            float fMax2 = Math.max(fMax, MAX_SCALE) / fAbs;
            this.matrix.postScale(fMax2, fMax2, f2, f3);
        } else {
            float f4 = fMax / fAbs;
            this.matrix.postScale(f4, f4, f2, f3);
            fixTranslation();
        }
        setImageMatrix(this.matrix);
    }

    private void fixScale() {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float fAbs = Math.abs(fArr[0]) + Math.abs(fArr[1]);
        float fMax = Math.max((this.viewW - (this.leftMargins * 2)) / this.rotatedImageW, (this.viewH - this.topBottomMargins) / this.rotatedImageH);
        if (fAbs < fMax) {
            if (fAbs <= 0.0f) {
                this.matrix.setScale(fMax, fMax);
                return;
            }
            double d3 = fMax / fAbs;
            fArr[0] = (float) (fArr[0] * d3);
            fArr[1] = (float) (fArr[1] * d3);
            fArr[3] = (float) (fArr[3] * d3);
            fArr[4] = (float) (fArr[4] * d3);
            this.matrix.setValues(fArr);
        }
    }

    private void fixTranslation() {
        float f2;
        float f3;
        float f4;
        float f5 = 0.0f;
        RectF rectF = new RectF(0.0f, 0.0f, this.imageW, this.imageH);
        this.matrix.mapRect(rectF);
        float fHeight = rectF.height();
        float fWidth = rectF.width();
        float f6 = this.viewW;
        int i2 = this.leftMargins;
        if (fWidth < f6 - (i2 * 2)) {
            f2 = (((f6 - fWidth) / 2.0f) - rectF.left) + i2;
        } else {
            float f7 = rectF.left;
            if (f7 > i2) {
                f2 = i2 + (-f7);
            } else {
                float f8 = rectF.right;
                f2 = f8 < f6 - ((float) i2) ? (f6 - f8) - i2 : 0.0f;
            }
        }
        float f9 = this.viewH;
        int i3 = this.topBottomMargins;
        if (fHeight >= f9 - i3) {
            float f10 = rectF.top;
            if (f10 > i3 / 2) {
                f5 = (-f10) + (i3 / 2);
            } else {
                float f11 = rectF.bottom;
                if (f11 < f9 - (i3 / 2)) {
                    f3 = f9 - f11;
                    f4 = i3 / 2;
                }
            }
            this.matrix.postTranslate(f2, f5);
        }
        f3 = (f9 - fHeight) / 2.0f;
        f4 = rectF.top;
        f5 = f3 - f4;
        this.matrix.postTranslate(f2, f5);
    }

    private void init() {
        setScaleType(ImageView.ScaleType.MATRIX);
    }

    private void initImage() {
        if (this.viewW <= 0.0f || this.viewH <= 0.0f || this.imageW <= 0.0f || this.imageH <= 0.0f) {
            return;
        }
        this.mode = 0;
        this.matrix.setScale(0.0f, 0.0f);
        fixScale();
        fixTranslation();
        centerBitmap();
        MAX_SCALE = (float) (MAX_SCALE * 2.25d);
        setImageMatrix(this.matrix);
    }

    private float maxPostScale() {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        return Math.max(Math.min(this.viewW / this.rotatedImageW, this.viewH / this.rotatedImageH), MAX_SCALE) / (Math.abs(fArr[0]) + Math.abs(fArr[1]));
    }

    private void setImageWidthHeight() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        this.rotatedImageW = intrinsicWidth;
        this.imageW = intrinsicWidth;
        float intrinsicHeight = drawable.getIntrinsicHeight();
        this.rotatedImageH = intrinsicHeight;
        this.imageH = intrinsicHeight;
        initImage();
    }

    private float spacing(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return (float) Math.sqrt((f6 * f6) + (f7 * f7));
    }

    public double getImageRotation() {
        return this.rotation;
    }

    public RectF getMatrixRect() {
        Matrix matrix = this.matrix;
        RectF rectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        rectF.set(0.0f, 0.0f, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        matrix.mapRect(rectF);
        return rectF;
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.viewW = i2;
        this.viewH = i3;
        this.topBottomMargins = (i3 - i2) + (this.leftMargins * 2);
        if (i4 == 0) {
            initImage();
            return;
        }
        fixScale();
        fixTranslation();
        setImageMatrix(this.matrix);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0020, code lost:
    
        if (r2 != 6) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r24) {
        /*
            Method dump skipped, instructions count: 782
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pizidea.imagepicker.widget.SuperImageView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        setImageWidthHeight();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setImageWidthHeight();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i2) {
        super.setImageResource(i2);
        setImageWidthHeight();
    }

    public SuperImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.matrix = new Matrix();
        this.savedMatrix = new Matrix();
        this.mode = 0;
        this.pA = new PointF();
        this.pB = new PointF();
        this.mid = new PointF();
        this.lastClickPos = new PointF();
        this.lastClickTime = 0L;
        this.rotation = 0.0d;
        this.dist = 1.0f;
        this.leftMargins = 10;
        init();
    }

    public SuperImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.matrix = new Matrix();
        this.savedMatrix = new Matrix();
        this.mode = 0;
        this.pA = new PointF();
        this.pB = new PointF();
        this.mid = new PointF();
        this.lastClickPos = new PointF();
        this.lastClickTime = 0L;
        this.rotation = 0.0d;
        this.dist = 1.0f;
        this.leftMargins = 10;
        init();
    }
}
