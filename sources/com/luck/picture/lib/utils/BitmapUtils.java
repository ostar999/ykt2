package com.luck.picture.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class BitmapUtils {
    private static int calculateInSampleSize(int i2, int i3, int i4, int i5) {
        int i6 = 1;
        if (i3 > i5 || i2 > i4) {
            while (true) {
                if (i3 / i6 <= i5 && i2 / i6 <= i4) {
                    break;
                }
                i6 *= 2;
            }
        }
        return i6;
    }

    private static int calculateMaxBitmapSize(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getSize(point);
        }
        int iSqrt = (int) Math.sqrt(Math.pow(point.x, 2.0d) + Math.pow(point.y, 2.0d));
        Canvas canvas = new Canvas();
        int iMin = Math.min(canvas.getMaximumBitmapWidth(), canvas.getMaximumBitmapHeight());
        if (iMin > 0) {
            iSqrt = Math.min(iSqrt, iMin);
        }
        int maxTextureSize = PSEglUtils.getMaxTextureSize();
        return maxTextureSize > 0 ? Math.min(iSqrt, maxTextureSize) : iSqrt;
    }

    public static int[] getMaxImageSize(Context context, int i2, int i3, int i4, int i5) {
        if (i2 == 0 && i3 == 0) {
            i2 = i4;
            i3 = i5;
        }
        if (MediaUtils.isLongImage(i2, i3)) {
            return new int[]{-1, -1};
        }
        int iCalculateMaxBitmapSize = calculateMaxBitmapSize(context);
        int iCalculateInSampleSize = calculateInSampleSize(i2, i3, iCalculateMaxBitmapSize, iCalculateMaxBitmapSize);
        return new int[]{i2 / iCalculateInSampleSize, i3 / iCalculateInSampleSize};
    }

    public static int readPictureDegree(Context context, String str) throws IOException {
        ExifInterface exifInterface;
        InputStream contentResolverOpenInputStream = null;
        try {
            if (PictureMimeType.isContent(str)) {
                contentResolverOpenInputStream = PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str));
                exifInterface = new ExifInterface(contentResolverOpenInputStream);
            } else {
                exifInterface = new ExifInterface(str);
            }
            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                PictureFileUtils.close(contentResolverOpenInputStream);
                return 180;
            }
            if (attributeInt == 6) {
                PictureFileUtils.close(contentResolverOpenInputStream);
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            PictureFileUtils.close(contentResolverOpenInputStream);
            return 270;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        } finally {
            PictureFileUtils.close(contentResolverOpenInputStream);
        }
    }

    public static void rotateImage(Context context, String str) throws Throwable {
        try {
            int pictureDegree = readPictureDegree(context, str);
            if (pictureDegree > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                File file = new File(str);
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                if (bitmapDecodeFile != null) {
                    bitmapDecodeFile = rotatingImage(bitmapDecodeFile, pictureDegree);
                }
                if (bitmapDecodeFile != null) {
                    saveBitmapFile(bitmapDecodeFile, file);
                    bitmapDecodeFile.recycle();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Bitmap rotatingImage(Bitmap bitmap, int i2) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static void saveBitmapFile(Bitmap bitmap, File file) throws Throwable {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bufferedOutputStream2);
                    bufferedOutputStream2.flush();
                    PictureFileUtils.close(bufferedOutputStream2);
                } catch (Exception e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    PictureFileUtils.close(bufferedOutputStream);
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    PictureFileUtils.close(bufferedOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }
}
