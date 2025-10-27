package com.lwkandroid.widget.ngv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
class Utils {
    public static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format, int quality) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, quality, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable bytes2Drawable(Context context, byte[] bytes) {
        return bitmap2Drawable(context, getBitmap(bytes));
    }

    public static byte[] drawable2Bytes(Drawable drawable) {
        return drawable2Bytes(drawable, Bitmap.CompressFormat.PNG, 100);
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmapCreateBitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static byte[] drawable2Bytes(Drawable drawable, Bitmap.CompressFormat format, int quality) {
        if (drawable == null) {
            return null;
        }
        return bitmap2Bytes(getBitmap(drawable), format, quality);
    }

    public static Bitmap getBitmap(byte[] data) {
        return getBitmap(data, 0);
    }

    public static Bitmap getBitmap(final byte[] data, final int offset) {
        if (data.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(data, offset, data.length);
    }
}
