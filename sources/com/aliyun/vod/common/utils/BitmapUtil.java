package com.aliyun.vod.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static boolean writeBitmap(String str, Bitmap bitmap, int i2, int i3, Bitmap.CompressFormat compressFormat, int i4) throws IOException {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(i2 / width, i3 / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        boolean zWriteBitmap = writeBitmap(str, bitmapCreateBitmap, compressFormat, i4);
        bitmapCreateBitmap.recycle();
        return zWriteBitmap;
    }

    public static boolean writeBitmap(String str, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i2) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            boolean zCompress = bitmap.compress(compressFormat, i2, fileOutputStream);
            try {
                fileOutputStream.close();
                return zCompress;
            } catch (IOException unused) {
                return false;
            }
        } catch (FileNotFoundException e2) {
            Log.e(TAG, "unable to open output file", e2);
            return false;
        }
    }
}
