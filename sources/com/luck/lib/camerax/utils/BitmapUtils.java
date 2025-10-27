package com.luck.lib.camerax.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/* loaded from: classes4.dex */
public class BitmapUtils {
    public static int computeSize(int i2, int i3) {
        if (i2 % 2 == 1) {
            i2++;
        }
        if (i3 % 2 == 1) {
            i3++;
        }
        int iMax = Math.max(i2, i3);
        float fMin = Math.min(i2, i3) / iMax;
        if (fMin > 1.0f || fMin <= 0.5625d) {
            double d3 = fMin;
            if (d3 > 0.5625d || d3 <= 0.5d) {
                return (int) Math.ceil(iMax / (1280.0d / d3));
            }
            int i4 = iMax / 1280;
            if (i4 == 0) {
                return 1;
            }
            return i4;
        }
        if (iMax < 1664) {
            return 1;
        }
        if (iMax < 4990) {
            return 2;
        }
        if (iMax <= 4990 || iMax >= 10240) {
            return iMax / 1280;
        }
        return 4;
    }

    public static Bitmap toHorizontalMirror(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1.0f, 1.0f);
        matrix.postRotate(width > height ? 90.0f : 0.0f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
