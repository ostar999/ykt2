package net.polyv.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.lang.reflect.Array;
import tv.polyv.jni.NativeBitmapFactory;

/* loaded from: classes9.dex */
public class DrawingCacheHolder {
    public Bitmap bitmap;
    public Bitmap[][] bitmapArray;
    public Canvas canvas;
    public boolean drawn;
    public Object extra;
    public int height;
    private int mDensity;
    public int width;

    private void eraseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.eraseColor(0);
        }
    }

    private void eraseBitmapArray() {
        if (this.bitmapArray != null) {
            for (int i2 = 0; i2 < this.bitmapArray.length; i2++) {
                int i3 = 0;
                while (true) {
                    Bitmap[] bitmapArr = this.bitmapArray[i2];
                    if (i3 < bitmapArr.length) {
                        eraseBitmap(bitmapArr[i3]);
                        i3++;
                    }
                }
            }
        }
    }

    private void recycleBitmapArray() {
        Bitmap[][] bitmapArr = this.bitmapArray;
        this.bitmapArray = null;
        if (bitmapArr != null) {
            for (int i2 = 0; i2 < bitmapArr.length; i2++) {
                int i3 = 0;
                while (true) {
                    Bitmap[] bitmapArr2 = bitmapArr[i2];
                    if (i3 < bitmapArr2.length) {
                        Bitmap bitmap = bitmapArr2[i3];
                        if (bitmap != null) {
                            bitmap.recycle();
                            bitmapArr[i2][i3] = null;
                        }
                        i3++;
                    }
                }
            }
        }
    }

    public void buildCache(int i2, int i3, int i4, boolean z2, int i5) {
        Bitmap bitmap;
        boolean z3 = true;
        if (!z2 ? i2 > this.width || i3 > this.height : i2 != this.width || i3 != this.height) {
            z3 = false;
        }
        if (z3 && (bitmap = this.bitmap) != null) {
            bitmap.eraseColor(0);
            this.canvas.setBitmap(this.bitmap);
            recycleBitmapArray();
            return;
        }
        if (this.bitmap != null) {
            recycle();
        }
        this.width = i2;
        this.height = i3;
        Bitmap.Config config = Bitmap.Config.ARGB_4444;
        if (i5 == 32) {
            config = Bitmap.Config.ARGB_8888;
        }
        Bitmap bitmapCreateBitmap = NativeBitmapFactory.createBitmap(i2, i3, config);
        this.bitmap = bitmapCreateBitmap;
        if (i4 > 0) {
            this.mDensity = i4;
            bitmapCreateBitmap.setDensity(i4);
        }
        Canvas canvas = this.canvas;
        if (canvas != null) {
            canvas.setBitmap(this.bitmap);
            return;
        }
        Canvas canvas2 = new Canvas(this.bitmap);
        this.canvas = canvas2;
        canvas2.setDensity(i4);
    }

    public final synchronized boolean draw(Canvas canvas, float f2, float f3, Paint paint) {
        if (this.bitmapArray == null) {
            Bitmap bitmap = this.bitmap;
            if (bitmap == null) {
                return false;
            }
            canvas.drawBitmap(bitmap, f2, f3, paint);
            return true;
        }
        for (int i2 = 0; i2 < this.bitmapArray.length; i2++) {
            int i3 = 0;
            while (true) {
                Bitmap[] bitmapArr = this.bitmapArray[i2];
                if (i3 < bitmapArr.length) {
                    Bitmap bitmap2 = bitmapArr[i3];
                    if (bitmap2 != null) {
                        float width = (bitmap2.getWidth() * i3) + f2;
                        if (width <= canvas.getWidth() && bitmap2.getWidth() + width >= 0.0f) {
                            float height = (bitmap2.getHeight() * i2) + f3;
                            if (height <= canvas.getHeight() && bitmap2.getHeight() + height >= 0.0f) {
                                canvas.drawBitmap(bitmap2, width, height, paint);
                            }
                        }
                    }
                    i3++;
                }
            }
        }
        return true;
    }

    public void erase() {
        eraseBitmap(this.bitmap);
        eraseBitmapArray();
    }

    public synchronized void recycle() {
        Bitmap bitmap = this.bitmap;
        this.bitmap = null;
        this.height = 0;
        this.width = 0;
        if (bitmap != null) {
            bitmap.recycle();
        }
        recycleBitmapArray();
        this.extra = null;
    }

    @SuppressLint({"NewApi"})
    public void splitWith(int i2, int i3, int i4, int i5) {
        int i6;
        recycleBitmapArray();
        int i7 = this.width;
        if (i7 <= 0 || (i6 = this.height) <= 0 || this.bitmap == null) {
            return;
        }
        if (i7 > i4 || i6 > i5) {
            int iMin = Math.min(i4, i2);
            int iMin2 = Math.min(i5, i3);
            int i8 = this.width;
            int i9 = (i8 / iMin) + (i8 % iMin == 0 ? 0 : 1);
            int i10 = this.height;
            int i11 = (i10 / iMin2) + (i10 % iMin2 == 0 ? 0 : 1);
            int i12 = i8 / i9;
            int i13 = i10 / i11;
            Bitmap[][] bitmapArr = (Bitmap[][]) Array.newInstance((Class<?>) Bitmap.class, i11, i9);
            if (this.canvas == null) {
                Canvas canvas = new Canvas();
                this.canvas = canvas;
                int i14 = this.mDensity;
                if (i14 > 0) {
                    canvas.setDensity(i14);
                }
            }
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            for (int i15 = 0; i15 < i11; i15++) {
                for (int i16 = 0; i16 < i9; i16++) {
                    Bitmap[] bitmapArr2 = bitmapArr[i15];
                    Bitmap bitmapCreateBitmap = NativeBitmapFactory.createBitmap(i12, i13, Bitmap.Config.ARGB_8888);
                    bitmapArr2[i16] = bitmapCreateBitmap;
                    int i17 = this.mDensity;
                    if (i17 > 0) {
                        bitmapCreateBitmap.setDensity(i17);
                    }
                    this.canvas.setBitmap(bitmapCreateBitmap);
                    int i18 = i16 * i12;
                    int i19 = i15 * i13;
                    rect.set(i18, i19, i18 + i12, i19 + i13);
                    rect2.set(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
                    this.canvas.drawBitmap(this.bitmap, rect, rect2, (Paint) null);
                }
            }
            this.canvas.setBitmap(this.bitmap);
            this.bitmapArray = bitmapArr;
        }
    }
}
