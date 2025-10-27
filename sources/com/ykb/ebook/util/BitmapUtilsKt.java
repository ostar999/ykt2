package com.ykb.ebook.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.renderscript.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\n\u0010\b\u001a\u00020\t*\u00020\u0005\u001a\u001a\u0010\n\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0014\u0010\r\u001a\u00020\u0005*\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\t¨\u0006\u000f"}, d2 = {"saveImageToGallery", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bitmap", "Landroid/graphics/Bitmap;", "title", "", "getMeanColor", "", "resizeAndRecycle", "newWidth", "newHeight", "stackBlur", "radius", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BitmapUtilsKt {
    public static final int getMeanColor(@NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int iRed = 0;
        int iBlue = 0;
        int iGreen = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            for (int i3 = 70; i3 < 100; i3++) {
                int pixel = bitmap.getPixel(MathKt__MathJVMKt.roundToInt((i2 * width) / 100.0f), MathKt__MathJVMKt.roundToInt((i3 * height) / 100.0f));
                iRed += Color.red(pixel);
                iGreen += Color.green(pixel);
                iBlue += Color.blue(pixel);
            }
        }
        return Color.rgb((iRed / 3000) + 3, (iGreen / 3000) + 3, (iBlue / 3000) + 3);
    }

    @NotNull
    public static final Bitmap resizeAndRecycle(@NotNull Bitmap bitmap, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Bitmap bitmapResize$default = Toolkit.resize$default(Toolkit.INSTANCE, bitmap, i2, i3, null, 8, null);
        bitmap.recycle();
        return bitmapResize$default;
    }

    public static final boolean saveImageToGallery(@NotNull Context context, @NotNull Bitmap bitmap, @NotNull String title) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(title, "title");
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", title + ".jpg");
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("relative_path", Environment.DIRECTORY_PICTURES);
            Uri uriInsert = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            if (uriInsert == null) {
                return false;
            }
            OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uriInsert);
            if (outputStreamOpenOutputStream == null) {
                return true;
            }
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStreamOpenOutputStream);
                CloseableKt.closeFinally(outputStreamOpenOutputStream, null);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(outputStreamOpenOutputStream, th);
                    throw th2;
                }
            }
        }
        String string = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        Intrinsics.checkNotNullExpressionValue(string, "getExternalStoragePublic…TORY_PICTURES).toString()");
        File file = new File(string, title + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                CloseableKt.closeFinally(fileOutputStream, null);
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                context.sendBroadcast(intent);
                return true;
            } finally {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @NotNull
    public static final Bitmap stackBlur(@NotNull Bitmap bitmap, int i2) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        return Toolkit.blur$default(Toolkit.INSTANCE, bitmap, i2, null, 4, null);
    }

    public static /* synthetic */ Bitmap stackBlur$default(Bitmap bitmap, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 8;
        }
        return stackBlur(bitmap, i2);
    }
}
