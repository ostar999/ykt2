package com.psychiatrygarden.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.ViewPagerActivity;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/* loaded from: classes6.dex */
public class ImageFactory {
    public static Bitmap getBitmap(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = 1;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static int getBitmapSize(String path) {
        Bitmap bitmap = getBitmap(path);
        if (bitmap != null) {
            return bitmap.getAllocationByteCount();
        }
        return 0;
    }

    private static String getFileExtension(String fileName) {
        int iLastIndexOf = fileName.lastIndexOf(StrPool.DOT);
        return (iLastIndexOf == -1 || iLastIndexOf >= fileName.length() + (-1)) ? "" : fileName.substring(iLastIndexOf + 1).toLowerCase();
    }

    public static Bitmap getImageBitmap(ViewPagerActivity activity, String url, int id) {
        BitmapFactory.Options options;
        HttpResponse httpResponseExecute;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        Bitmap bitmapDecodeStream = null;
        try {
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            options.inSampleSize = displayMetrics.widthPixels / displayMetrics.heightPixels;
            httpResponseExecute = defaultHttpClient.execute(httpGet);
        } catch (Exception unused) {
        } catch (Throwable th) {
            defaultHttpClient.getConnectionManager().shutdown();
            throw th;
        }
        if (200 == httpResponseExecute.getStatusLine().getStatusCode()) {
            Bitmap bitmapDecodeStream2 = BitmapFactory.decodeStream(httpResponseExecute.getEntity().getContent(), null, options);
            defaultHttpClient.getConnectionManager().shutdown();
            return bitmapDecodeStream2;
        }
        bitmapDecodeStream = BitmapFactory.decodeStream(activity.getResources().openRawResource(id), null, options);
        defaultHttpClient.getConnectionManager().shutdown();
        return bitmapDecodeStream;
    }

    public static Bitmap getImageBitmap2(Activity activity, String url, int id) {
        BitmapFactory.Options options;
        HttpResponse httpResponseExecute;
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        Bitmap bitmapDecodeStream = null;
        try {
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            options.inSampleSize = displayMetrics.widthPixels / displayMetrics.heightPixels;
            httpResponseExecute = defaultHttpClient.execute(httpGet);
        } catch (Exception unused) {
        } catch (Throwable th) {
            defaultHttpClient.getConnectionManager().shutdown();
            throw th;
        }
        if (200 == httpResponseExecute.getStatusLine().getStatusCode()) {
            Bitmap bitmapDecodeStream2 = BitmapFactory.decodeStream(httpResponseExecute.getEntity().getContent(), null, options);
            defaultHttpClient.getConnectionManager().shutdown();
            return bitmapDecodeStream2;
        }
        bitmapDecodeStream = BitmapFactory.decodeStream(activity.getResources().openRawResource(id), null, options);
        defaultHttpClient.getConnectionManager().shutdown();
        return bitmapDecodeStream;
    }

    public static float getImageSize(String path) {
        try {
            return (new File(path).length() / 1024.0f) / 1024.0f;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0.0f;
        }
    }

    private static String getMimeTypeFromExtension(String extension) {
        return extension.equalsIgnoreCase("png") ? "image/png" : (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) ? "image/jpeg" : extension.equalsIgnoreCase(ImgUtil.IMAGE_TYPE_GIF) ? MimeTypes.IMAGE_GIF : "unknown";
    }

    public static String getMimeTypeFromFile(String filePath) {
        return getMimeTypeFromExtension(getFileExtension(new File(filePath).getName()));
    }

    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(imgPath, options);
        options.inJustDecodeBounds = false;
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        int i4 = (i2 <= i3 || ((float) i2) <= pixelW) ? (i2 >= i3 || ((float) i3) <= pixelH) ? 1 : (int) (i3 / pixelH) : (int) (i2 / pixelW);
        options.inSampleSize = i4 > 0 ? i4 : 1;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static File saveBitmapFile(Bitmap bitmap, String filepath) throws IOException {
        File file = new File(filepath);
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap sizeRatio(java.lang.String r7) {
        /*
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r1 = 1
            r0.inJustDecodeBounds = r1
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.RGB_565
            r0.inPreferredConfig = r2
            android.graphics.BitmapFactory.decodeFile(r7, r0)
            r2 = 0
            r0.inJustDecodeBounds = r2
            int r2 = r0.outWidth
            int r3 = r0.outHeight
            int r4 = r2 / 2
            float r4 = (float) r4
            int r5 = r3 / 2
            float r5 = (float) r5
            if (r2 <= r3) goto L27
            float r6 = (float) r2
            int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r6 <= 0) goto L27
            float r2 = (float) r2
            float r2 = r2 / r5
        L25:
            int r2 = (int) r2
            goto L32
        L27:
            if (r2 >= r3) goto L31
            float r2 = (float) r3
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L31
            float r2 = (float) r3
            float r2 = r2 / r4
            goto L25
        L31:
            r2 = r1
        L32:
            if (r2 > 0) goto L35
            goto L36
        L35:
            r1 = r2
        L36:
            r0.inSampleSize = r1
            android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeFile(r7, r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.ImageFactory.sizeRatio(java.lang.String):android.graphics.Bitmap");
    }

    public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 100;
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        while (byteArrayOutputStream.toByteArray().length / 1024 > maxSize) {
            byteArrayOutputStream.reset();
            i2 -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        storeImage(ratio(image, pixelW, pixelH), outPath);
    }

    public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(outPath));
    }

    public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        storeImage(ratio(imgPath, pixelW, pixelH), outPath);
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap ratio(android.graphics.Bitmap r6, float r7, float r8) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG
            r2 = 100
            r6.compress(r1, r2, r0)
            byte[] r1 = r0.toByteArray()
            int r1 = r1.length
            r2 = 1024(0x400, float:1.435E-42)
            int r1 = r1 / r2
            if (r1 <= r2) goto L20
            r0.reset()
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG
            r2 = 50
            r6.compress(r1, r2, r0)
        L20:
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream
            byte[] r1 = r0.toByteArray()
            r6.<init>(r1)
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options
            r1.<init>()
            r2 = 1
            r1.inJustDecodeBounds = r2
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.RGB_565
            r1.inPreferredConfig = r3
            r3 = 0
            android.graphics.BitmapFactory.decodeStream(r6, r3, r1)
            r6 = 0
            r1.inJustDecodeBounds = r6
            int r6 = r1.outWidth
            int r4 = r1.outHeight
            if (r6 <= r4) goto L4b
            float r5 = (float) r6
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 <= 0) goto L4b
            float r6 = (float) r6
            float r6 = r6 / r7
        L49:
            int r6 = (int) r6
            goto L56
        L4b:
            if (r6 >= r4) goto L55
            float r6 = (float) r4
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 <= 0) goto L55
            float r6 = (float) r4
            float r6 = r6 / r8
            goto L49
        L55:
            r6 = r2
        L56:
            if (r6 > 0) goto L59
            goto L5a
        L59:
            r2 = r6
        L5a:
            r1.inSampleSize = r2
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream
            byte[] r7 = r0.toByteArray()
            r6.<init>(r7)
            android.graphics.Bitmap r6 = android.graphics.BitmapFactory.decodeStream(r6, r3, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.utils.ImageFactory.ratio(android.graphics.Bitmap, float, float):android.graphics.Bitmap");
    }

    public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static Bitmap getImageBitmap(Activity activity, String url) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inJustDecodeBounds = false;
                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                options.inSampleSize = displayMetrics.widthPixels / displayMetrics.heightPixels;
                HttpResponse httpResponseExecute = defaultHttpClient.execute(httpGet);
                if (200 == httpResponseExecute.getStatusLine().getStatusCode()) {
                    return BitmapFactory.decodeStream(httpResponseExecute.getEntity().getContent(), null, options);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        } finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
    }
}
