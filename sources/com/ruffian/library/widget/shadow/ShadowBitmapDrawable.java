package com.ruffian.library.widget.shadow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import com.ruffian.library.widget.utils.ReflectUtils;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class ShadowBitmapDrawable extends BitmapDrawable {
    private RectF mBoundsF = new RectF();
    private float[] mRoundRadii;
    private int mShadowColor;
    private int mShadowDx;
    private int mShadowDy;
    private float mShadowRadius;

    private void updateBounds(Rect rect) throws SecurityException {
        if (rect == null) {
            rect = getBounds();
        }
        this.mBoundsF.set(rect.left + this.mShadowRadius + Math.abs(this.mShadowDx), rect.top + this.mShadowRadius + Math.abs(this.mShadowDy), (rect.right - this.mShadowRadius) - Math.abs(this.mShadowDx), (rect.bottom - this.mShadowRadius) - Math.abs(this.mShadowDy));
        ReflectUtils.invokeMethod(this, "setBitmap", new Class[]{Bitmap.class}, new Object[]{makeShadowBitmap((int) this.mBoundsF.width(), (int) this.mBoundsF.height(), this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor, this.mRoundRadii)});
    }

    public Bitmap makeShadowBitmap(int i2, int i3, float f2, float f3, float f4, int i4, float[] fArr) {
        if (i2 <= 0 || i3 <= 0) {
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        RectF rectF = new RectF(f2, f2, i2 - f2, i3 - f2);
        rectF.top += Math.abs(f4);
        rectF.bottom -= Math.abs(f4);
        rectF.left += Math.abs(f3);
        rectF.right -= Math.abs(f3);
        Paint paint = new Paint(5);
        paint.setColor(i4);
        paint.setShadowLayer(f2, f3, f4, i4);
        Path path = new Path();
        path.addRoundRect(rectF, fArr, Path.Direction.CW);
        canvas.drawPath(path, paint);
        return bitmapCreateBitmap;
    }

    @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) throws SecurityException {
        super.onBoundsChange(rect);
        if (rect.right - rect.left <= 0 || rect.bottom - rect.top <= 0) {
            return;
        }
        updateBounds(rect);
    }

    public void updateParameter(int i2, float f2, int i3, int i4, float[] fArr) throws SecurityException {
        boolean z2 = (this.mShadowColor == i2 && this.mShadowRadius == f2 && this.mShadowDx == i3 && this.mShadowDy == i4 && Arrays.equals(this.mRoundRadii, fArr)) ? false : true;
        this.mShadowColor = i2;
        this.mRoundRadii = fArr;
        this.mShadowRadius = f2;
        this.mShadowDx = i3;
        this.mShadowDy = i4;
        if (z2) {
            ReflectUtils.invokeMethod(this, "setBitmap", new Class[]{Bitmap.class}, new Object[]{makeShadowBitmap((int) this.mBoundsF.width(), (int) this.mBoundsF.height(), this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor, this.mRoundRadii)});
        }
    }
}
