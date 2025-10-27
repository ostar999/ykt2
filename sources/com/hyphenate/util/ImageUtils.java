package com.hyphenate.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/* loaded from: classes4.dex */
public class ImageUtils {
    public static final int SCALE_IMAGE_HEIGHT = 960;
    public static final int SCALE_IMAGE_WIDTH = 640;

    public static int calculateInSampleSize(BitmapFactory.Options options, int i2, int i3) {
        int i4 = options.outHeight;
        int i5 = options.outWidth;
        if (i4 <= i3 && i5 <= i2) {
            return 1;
        }
        int iRound = Math.round(i4 / i3);
        int iRound2 = Math.round(i5 / i2);
        return iRound > iRound2 ? iRound : iRound2;
    }

    private static void checkBitmapOrientation(int i2, BitmapFactory.Options options) {
        if (i2 == 90 || i2 == 270) {
            int i3 = options.outHeight;
            options.outHeight = options.outWidth;
            options.outWidth = i3;
        }
    }

    public static Bitmap decodeScaleImage(Context context, int i2, int i3, int i4) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), i2, options);
        options.inSampleSize = calculateInSampleSize(options, i3, i4);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), i2, options);
    }

    @RequiresApi(api = 24)
    public static Bitmap decodeScaleImage(Context context, Uri uri, int i2, int i3) throws IOException {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(context, uri);
        int iCalculateInSampleSize = calculateInSampleSize(bitmapOptions, i2, i3);
        EMLog.d("img", "original wid" + bitmapOptions.outWidth + " original height:" + bitmapOptions.outHeight + " sample:" + iCalculateInSampleSize);
        bitmapOptions.inSampleSize = iCalculateInSampleSize;
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap bitmapByUri = getBitmapByUri(context, uri, bitmapOptions);
        int pictureDegree = readPictureDegree(context, uri);
        if (bitmapByUri == null || pictureDegree == 0) {
            return bitmapByUri;
        }
        Bitmap bitmapRotateImageView = rotateImageView(pictureDegree, bitmapByUri);
        bitmapByUri.recycle();
        return bitmapRotateImageView;
    }

    public static Bitmap decodeScaleImage(String str, int i2, int i3) {
        BitmapFactory.Options bitmapOptions = getBitmapOptions(str);
        int iCalculateInSampleSize = calculateInSampleSize(bitmapOptions, i2, i3);
        EMLog.d("img", "original wid" + bitmapOptions.outWidth + " original height:" + bitmapOptions.outHeight + " sample:" + iCalculateInSampleSize);
        bitmapOptions.inSampleSize = iCalculateInSampleSize;
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, bitmapOptions);
        int pictureDegree = readPictureDegree(str);
        if (bitmapDecodeFile == null || pictureDegree == 0) {
            return bitmapDecodeFile;
        }
        Bitmap bitmapRotateImageView = rotateImageView(pictureDegree, bitmapDecodeFile);
        bitmapDecodeFile.recycle();
        return bitmapRotateImageView;
    }

    public static Bitmap getBitmapByUri(Context context, Uri uri, BitmapFactory.Options options) throws IOException {
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor(), null, options);
        parcelFileDescriptorOpenFileDescriptor.close();
        return bitmapDecodeFileDescriptor;
    }

    public static BitmapFactory.Options getBitmapOptions(Context context, Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        BitmapFactory.decodeFileDescriptor(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor(), null, options);
        parcelFileDescriptorOpenFileDescriptor.close();
        if (Build.VERSION.SDK_INT >= 24) {
            checkBitmapOrientation(readPictureDegree(context, uri), options);
        }
        return options;
    }

    public static BitmapFactory.Options getBitmapOptions(Context context, String str) {
        if (TextUtils.isEmpty(str) || !EMFileHelper.getInstance().isFileExist(context, str)) {
            return null;
        }
        String filePath = EMFileHelper.getInstance().getFilePath(context, str);
        if (!TextUtils.isEmpty(filePath)) {
            return getBitmapOptions(filePath);
        }
        try {
            return getBitmapOptions(context, Uri.parse(str));
        } catch (IOException e2) {
            EMLog.e("img", "get bitmap options fail by " + e2.getMessage());
            return null;
        }
    }

    public static BitmapFactory.Options getBitmapOptions(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        checkBitmapOrientation(readPictureDegree(str), options);
        return options;
    }

    public static long getFileLength(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        return EMFileHelper.getInstance().getFileLength(str);
    }

    public static String getFilename(Context context, String str) {
        return (!TextUtils.isEmpty(str) && EMFileHelper.getInstance().isFileExist(context, Uri.parse(str))) ? EMFileHelper.getInstance().getFilename(str) : "";
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        return getRoundedCornerBitmap(bitmap, 6.0f);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float f2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f2, f2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    @RequiresApi(api = 24)
    public static Uri getScaledImage(Context context, Uri uri) throws IOException {
        if (uri == null) {
            return uri;
        }
        try {
            if (EMFileHelper.getInstance().getFileLength(uri) <= OSSConstants.MIN_PART_SIZE_LIMIT) {
                EMLog.d("img", "use original small image");
                return uri;
            }
            Bitmap bitmapDecodeScaleImage = decodeScaleImage(context, uri, 640, 960);
            File fileCreateTempFile = File.createTempFile("image", ".jpg", context.getFilesDir());
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            if (bitmapDecodeScaleImage != null) {
                bitmapDecodeScaleImage.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            }
            fileOutputStream.close();
            EMLog.d("img", "compared to small fle" + fileCreateTempFile.getAbsolutePath() + " size:" + fileCreateTempFile.length());
            return Uri.fromFile(fileCreateTempFile);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getScaledImage(Context context, String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            return str;
        }
        long length = file.length();
        EMLog.d("img", "original img size:" + length);
        if (length <= OSSConstants.MIN_PART_SIZE_LIMIT) {
            EMLog.d("img", "use original small image");
            return str;
        }
        Bitmap bitmapDecodeScaleImage = decodeScaleImage(str, 640, 960);
        try {
            File fileCreateTempFile = File.createTempFile("image", ".jpg", context.getFilesDir());
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            if (bitmapDecodeScaleImage != null) {
                bitmapDecodeScaleImage.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            }
            fileOutputStream.close();
            EMLog.d("img", "compared to small fle" + fileCreateTempFile.getAbsolutePath() + " size:" + fileCreateTempFile.length());
            return fileCreateTempFile.getAbsolutePath();
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static String getScaledImage(Context context, String str, int i2) throws IOException {
        File file = new File(str);
        if (file.exists()) {
            long length = file.length();
            EMLog.d("img", "original img size:" + length);
            if (length > OSSConstants.MIN_PART_SIZE_LIMIT) {
                Bitmap bitmapDecodeScaleImage = decodeScaleImage(str, 640, 960);
                try {
                    File file2 = new File(context.getExternalCacheDir(), "eaemobTemp" + i2 + ".jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    if (bitmapDecodeScaleImage != null) {
                        bitmapDecodeScaleImage.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
                    }
                    fileOutputStream.close();
                    EMLog.d("img", "compared to small fle" + file2.getAbsolutePath() + " size:" + file2.length());
                    return file2.getAbsolutePath();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return str;
    }

    public static String getScaledImageByUri(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        EMLog.d("img", "original localPath: " + str);
        Uri uri = Uri.parse(str);
        if (!EMFileHelper.getInstance().isFileExist(context, str)) {
            return str;
        }
        String filePath = EMFileHelper.getInstance().getFilePath(context, uri);
        if (!TextUtils.isEmpty(filePath)) {
            return getScaledImage(context, filePath);
        }
        Uri scaledImage = Build.VERSION.SDK_INT >= 24 ? getScaledImage(context, uri) : null;
        return scaledImage == null ? str : scaledImage.toString();
    }

    public static String getThumbnailImage(String str, int i2) throws IOException {
        Bitmap bitmapDecodeScaleImage = decodeScaleImage(str, i2, i2);
        try {
            File fileCreateTempFile = File.createTempFile("image", ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
            if (bitmapDecodeScaleImage != null) {
                bitmapDecodeScaleImage.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
            }
            fileOutputStream.close();
            EMLog.d("img", "generate thumbnail image at:" + fileCreateTempFile.getAbsolutePath() + " size:" + fileCreateTempFile.length());
            return fileCreateTempFile.getAbsolutePath();
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static Bitmap getVideoThumbnail(String str, int i2, int i3, int i4) {
        Bitmap bitmapCreateVideoThumbnail = ThumbnailUtils.createVideoThumbnail(str, i4);
        EMLog.d("getVideoThumbnail", "video thumb width:" + bitmapCreateVideoThumbnail.getWidth());
        EMLog.d("getVideoThumbnail", "video thumb height:" + bitmapCreateVideoThumbnail.getHeight());
        return ThumbnailUtils.extractThumbnail(bitmapCreateVideoThumbnail, i2, i3, 2);
    }

    public static Bitmap mergeImages(int i2, int i3, List<Bitmap> list) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawColor(-3355444);
        EMLog.d("img", "merge images to size:" + i2 + "*" + i3 + " with images:" + list.size());
        int i4 = list.size() <= 4 ? 2 : 3;
        int i5 = (i2 - 4) / i4;
        int i6 = 0;
        for (int i7 = 0; i7 < i4; i7++) {
            for (int i8 = 0; i8 < i4; i8++) {
                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(list.get(i6), i5, i5, true);
                Bitmap roundedCornerBitmap = getRoundedCornerBitmap(bitmapCreateScaledBitmap, 2.0f);
                bitmapCreateScaledBitmap.recycle();
                canvas.drawBitmap(roundedCornerBitmap, (i8 * i5) + i8 + 2, (i7 * i5) + i7 + 2, (Paint) null);
                roundedCornerBitmap.recycle();
                i6++;
                if (i6 == list.size()) {
                    return bitmapCreateBitmap;
                }
            }
        }
        return bitmapCreateBitmap;
    }

    @RequiresApi(api = 24)
    public static int readPictureDegree(Context context, Uri uri) throws IOException {
        int i2 = 0;
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            int attributeInt = new ExifInterface(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                i2 = 180;
            } else if (attributeInt == 6) {
                i2 = 90;
            } else if (attributeInt == 8) {
                i2 = 270;
            }
            parcelFileDescriptorOpenFileDescriptor.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return i2;
    }

    public static int readPictureDegree(String str) {
        int i2;
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
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
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static Bitmap rotateImageView(int i2, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String saveVideoThumb(File file, int i2, int i3, int i4) throws IOException {
        FileOutputStream fileOutputStream;
        Bitmap videoThumbnail = getVideoThumbnail(file.getAbsolutePath(), i2, i3, i4);
        File file2 = new File(PathUtil.getInstance().getVideoPath(), "th" + file.getName());
        try {
            file2.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            fileOutputStream = new FileOutputStream(file2);
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            fileOutputStream = null;
        }
        videoThumbnail.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        if (fileOutputStream != null) {
            try {
                fileOutputStream.flush();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
        return file2.getAbsolutePath();
    }
}
