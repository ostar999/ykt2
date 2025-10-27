package com.pizidea.imagepicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.TypedValue;
import com.nostra13.universalimageloader.utils.L;

/* loaded from: classes4.dex */
public class Util {
    private Util() {
        throw new AssertionError("No Instances");
    }

    public static int dp2px(Context context, float f2) {
        return Math.round(TypedValue.applyDimension(1, f2, context.getResources().getDisplayMetrics()));
    }

    public static boolean isStorageEnable() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        L.d("TestFile", "SD card is not available/writable right now.");
        return false;
    }

    public static Bitmap rotate(Bitmap bitmap, int i2) {
        if (i2 == 0 || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i2, bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f);
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == bitmapCreateBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return bitmapCreateBitmap;
        } catch (OutOfMemoryError unused) {
            return bitmap;
        }
    }
}
