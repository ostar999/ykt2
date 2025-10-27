package com.nostra13.universalimageloader.core.display;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/* loaded from: classes4.dex */
public class CircleBitmapDisplayer implements BitmapDisplayer {
    protected final Integer strokeColor;
    protected final float strokeWidth;

    public static class CircleDrawable extends Drawable {
        protected final BitmapShader bitmapShader;
        protected final RectF mBitmapRect;
        protected final RectF mRect = new RectF();
        protected final Paint paint;
        protected float radius;
        protected final Paint strokePaint;
        protected float strokeRadius;
        protected final float strokeWidth;

        public CircleDrawable(Bitmap bitmap, Integer num, float f2) {
            this.radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            this.bitmapShader = bitmapShader;
            this.mBitmapRect = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
            Paint paint = new Paint();
            this.paint = paint;
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            if (num == null) {
                this.strokePaint = null;
            } else {
                Paint paint2 = new Paint();
                this.strokePaint = paint2;
                paint2.setStyle(Paint.Style.STROKE);
                paint2.setColor(num.intValue());
                paint2.setStrokeWidth(f2);
                paint2.setAntiAlias(true);
            }
            this.strokeWidth = f2;
            this.strokeRadius = this.radius - (f2 / 2.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            float f2 = this.radius;
            canvas.drawCircle(f2, f2, f2, this.paint);
            Paint paint = this.strokePaint;
            if (paint != null) {
                float f3 = this.radius;
                canvas.drawCircle(f3, f3, this.strokeRadius, paint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mRect.set(0.0f, 0.0f, rect.width(), rect.height());
            float fMin = Math.min(rect.width(), rect.height()) / 2;
            this.radius = fMin;
            this.strokeRadius = fMin - (this.strokeWidth / 2.0f);
            Matrix matrix = new Matrix();
            matrix.setRectToRect(this.mBitmapRect, this.mRect, Matrix.ScaleToFit.FILL);
            this.bitmapShader.setLocalMatrix(matrix);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i2) {
            this.paint.setAlpha(i2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.paint.setColorFilter(colorFilter);
        }
    }

    public CircleBitmapDisplayer() {
        this(null);
    }

    @Override // com.nostra13.universalimageloader.core.display.BitmapDisplayer
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(new CircleDrawable(bitmap, this.strokeColor, this.strokeWidth));
    }

    public CircleBitmapDisplayer(Integer num) {
        this(num, 0.0f);
    }

    public CircleBitmapDisplayer(Integer num, float f2) {
        this.strokeColor = num;
        this.strokeWidth = f2;
    }
}
