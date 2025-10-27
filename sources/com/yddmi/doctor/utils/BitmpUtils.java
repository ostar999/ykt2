package com.yddmi.doctor.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/* loaded from: classes6.dex */
public class BitmpUtils {
    public static byte[] bitmapToByte(Bitmap bitmap, boolean z2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        if (z2) {
            bitmap.recycle();
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return byteArray;
    }

    public static Bitmap byteToBitmap(byte[] bArr) {
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    private static byte[] compressBitmapSize(Bitmap bitmap, int i2, long j2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        int size = byteArrayOutputStream.size();
        for (int i3 = i2; size > j2 && i3 > 0; i3--) {
            byteArrayOutputStream.reset();
            scaleBitmap(bitmap, i3 * 0.1f).compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] compressBitmapSpecifySize(Bitmap bitmap, long j2) {
        long j3;
        byte[] bArrCompressBitmapSize;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int size = byteArrayOutputStream.size();
        if (size <= j2) {
            bArrCompressBitmapSize = byteArrayOutputStream.toByteArray();
        } else {
            while (true) {
                j3 = size;
                if (j3 <= j2 || i2 <= 0) {
                    break;
                }
                byteArrayOutputStream.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
                size = byteArrayOutputStream.size();
                i2 -= 10;
            }
            bArrCompressBitmapSize = j3 > j2 ? compressBitmapSize(bitmap, i2, j2) : byteArrayOutputStream.toByteArray();
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bArrCompressBitmapSize;
    }

    public static Bitmap compressBitmapToSize(Bitmap bitmap, int i2, String str) {
        int i3;
        int i4 = i2 * 1024;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        str.hashCode();
        Bitmap.CompressFormat compressFormat = !str.equals("png") ? !str.equals("webp") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.WEBP : Bitmap.CompressFormat.PNG;
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int i5 = 100;
        while (byteArray.length > i4 && i5 > 0) {
            byteArrayOutputStream.reset();
            i5 -= 10;
            bitmap.compress(compressFormat, i5, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
        }
        if (byteArray.length <= i4) {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, byteArrayOutputStream2);
        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length, options);
        byteArrayOutputStream.reset();
        bitmapDecodeByteArray.compress(compressFormat, 100, byteArrayOutputStream);
        for (byte[] byteArray3 = byteArrayOutputStream.toByteArray(); byteArray3.length > i4 && (i3 = options.inSampleSize) < 32; byteArray3 = byteArrayOutputStream.toByteArray()) {
            options.inSampleSize = i3 * 2;
            bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length, options);
            byteArrayOutputStream.reset();
            bitmapDecodeByteArray.compress(compressFormat, 100, byteArrayOutputStream);
        }
        return bitmapDecodeByteArray;
    }

    public static Bitmap compressToBitmap(Bitmap bitmap, long j2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        for (int size = byteArrayOutputStream.size(); size > j2 && i2 > 0; size = byteArrayOutputStream.size()) {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
            i2 -= 10;
        }
        return bitmap;
    }

    public static Bitmap getUrlBitmap(String str) throws IOException {
        URL url;
        Bitmap bitmapDecodeStream = null;
        try {
            url = new URL(str);
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
            url = null;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            bitmapDecodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmapDecodeStream;
        } catch (IOException e3) {
            e3.printStackTrace();
            return bitmapDecodeStream;
        }
    }

    public static String saveBitmapToLocal(Context context, Bitmap bitmap) throws IOException {
        return saveBitmapToLocal(context, bitmap, Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/", UUID.randomUUID().toString() + ".jpg", true);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float f2) {
        return scaleBitmap(bitmap, f2, false);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, float f2, boolean z2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(f2, f2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (!bitmapCreateBitmap.equals(bitmap) && z2) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static String saveBitmapToLocal(Context context, Bitmap bitmap, String str, String str2, boolean z2) throws IOException {
        File fileMakeFilePath = FileUtils.makeFilePath(str, str2);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileMakeFilePath));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        if (z2) {
            intent.setData(UriUtils.getUri(context, fileMakeFilePath));
            context.sendBroadcast(intent);
        }
        return fileMakeFilePath.getAbsolutePath();
    }
}
