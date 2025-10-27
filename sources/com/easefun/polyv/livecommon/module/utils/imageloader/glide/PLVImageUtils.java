package com.easefun.polyv.livecommon.module.utils.imageloader.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import java.io.File;
import java.io.FileOutputStream;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes3.dex */
public class PLVImageUtils {
    private static final int ALLOW_LENGTH = 2097152;
    private static final String TAG = "PLVImageUtils";

    public static int calculateInSampleSize(String filePath, BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int i2 = options.outHeight;
        int i3 = options.outWidth;
        if (i2 <= reqHeight && i3 <= reqWidth) {
            return 1;
        }
        int iRound = Math.round(i2 / reqHeight);
        int iRound2 = Math.round(i3 / reqWidth);
        return iRound < iRound2 ? iRound : iRound2;
    }

    public static Bitmap compressImage(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.isFile() || file.length() < 1048576) {
            return null;
        }
        long length = file.length() / 1048576;
        int i2 = length > 20 ? 20 : length > 15 ? 30 : length > 10 ? 40 : length > 5 ? 55 : 70;
        String absolutePath = createTmpFile(file).getAbsolutePath();
        compressImage(filePath, absolutePath, i2, true);
        return BitmapFactory.decodeFile(absolutePath);
    }

    private static File createTmpFile(File srcFile) {
        File file = new File(PLVSDCardUtils.createPath(PLVAppUtils.getApp(), "PolyvImg/tmp"), srcFile.getName());
        if (file.getAbsolutePath().equals(srcFile.getAbsolutePath())) {
            file = new File(file.getParent(), "nc_" + file.getName());
        }
        PLVSDCardUtils.createNoMediaFile(file.getParent());
        return file;
    }

    public static String fixImageUrl(String origin) {
        if (TextUtils.isEmpty(origin)) {
            return origin;
        }
        if (origin.startsWith("//")) {
            return URIUtil.HTTP_COLON + origin;
        }
        if (!origin.startsWith("/")) {
            return origin;
        }
        return "http://livestatic.videocc.net" + origin;
    }

    public static int[] getImgWh(String imgFilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgFilePath, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(filePath, options, 500, 500);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int readPictureDegree(String path) {
        int i2;
        try {
            int attributeInt = new ExifInterface(path).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                i2 = 180;
            } else if (attributeInt == 6) {
                i2 = 90;
            } else {
                if (attributeInt != 8) {
                    return 0;
                }
                i2 = 270;
            }
            return i2;
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "readPictureDegree:" + e2.getMessage());
            return 0;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degress);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String compressImage(String filePath, String targetPath, int quality, boolean isSampleSize) throws Exception {
        Bitmap smallBitmap = isSampleSize ? getSmallBitmap(filePath) : BitmapFactory.decodeFile(filePath);
        File file = new File(targetPath);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                } else if (!file.delete()) {
                    PLVCommonLog.e(TAG, "fail to delete outputFile ");
                    throw new Exception("delete fail");
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    if (smallBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream2)) {
                        fileOutputStream2.close();
                        return file.getPath();
                    }
                    throw new Exception("compress fail");
                } catch (Exception e2) {
                    throw e2;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                throw e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
