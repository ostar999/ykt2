package com.luck.lib.camerax.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/* loaded from: classes4.dex */
public class FileUtils {
    public static final String POSTFIX = ".jpeg";
    public static final String POST_VIDEO = ".mp4";

    public static void close(@Nullable Closeable closeable) throws IOException {
        if (closeable instanceof Closeable) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static boolean copyPath(Context context, String str, String str2) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        FileOutputStream fileOutputStream = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            options.inSampleSize = BitmapUtils.computeSize(options.outWidth, options.outHeight);
            options.inJustDecodeBounds = false;
            Bitmap horizontalMirror = BitmapUtils.toHorizontalMirror(BitmapFactory.decodeFile(str, options));
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                try {
                    horizontalMirror.compress(horizontalMirror.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    horizontalMirror.recycle();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                    try {
                        fileOutputStream2.write(byteArrayOutputStream.toByteArray());
                        fileOutputStream2.flush();
                        deleteFile(context, str);
                        close(fileOutputStream2);
                        close(byteArrayOutputStream);
                        return true;
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        close(fileOutputStream);
                        close(byteArrayOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        close(fileOutputStream);
                        close(byteArrayOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    public static File createCameraFile(Context context, int i2, String str, String str2, String str3) {
        return createMediaFile(context, i2, str, str2, str3);
    }

    private static File createMediaFile(Context context, int i2, String str, String str2, String str3) {
        return createOutFile(context, i2, str, str2, str3);
    }

    private static File createOutFile(Context context, int i2, String str, String str2, String str3) {
        File file;
        File rootDirFile;
        Context applicationContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str3)) {
            if (TextUtils.equals("mounted", Environment.getExternalStorageState())) {
                rootDirFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                StringBuilder sb = new StringBuilder();
                sb.append(rootDirFile.getAbsolutePath());
                String str4 = File.separator;
                sb.append(str4);
                sb.append("Camera");
                sb.append(str4);
                file = new File(sb.toString());
            } else {
                rootDirFile = getRootDirFile(applicationContext, i2);
                file = new File(rootDirFile.getAbsolutePath() + File.separator);
            }
            if (!rootDirFile.exists()) {
                rootDirFile.mkdirs();
            }
        } else {
            File file2 = new File(str3);
            File parentFile = file2.getParentFile();
            Objects.requireNonNull(parentFile);
            if (!parentFile.exists()) {
                file2.getParentFile().mkdirs();
            }
            file = file2;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        boolean zIsEmpty = TextUtils.isEmpty(str);
        if (i2 == 2) {
            if (zIsEmpty) {
                str = DateUtils.getCreateFileName("VID_") + ".mp4";
            }
            return new File(file, str);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = ".jpeg";
        }
        if (zIsEmpty) {
            str = DateUtils.getCreateFileName("IMG_") + str2;
        }
        return new File(file, str);
    }

    public static File createTempFile(Context context, boolean z2) {
        File file = new File(context.getExternalFilesDir("").getAbsolutePath(), ".TemporaryCamera");
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(z2 ? ".mp4" : ".jpeg");
        return new File(file.getAbsolutePath(), sb.toString());
    }

    public static void deleteFile(Context context, String str) {
        try {
            if (isContent(str)) {
                context.getContentResolver().delete(Uri.parse(str), null, null);
            } else {
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static File getRootDirFile(Context context, int i2) {
        return i2 == 2 ? context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) : context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public static boolean isContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("content://");
    }

    public static Uri parUri(Context context, File file) {
        return Build.VERSION.SDK_INT > 23 ? FileProvider.getUriForFile(context, context.getPackageName() + ".luckProvider", file) : Uri.fromFile(file);
    }

    public static boolean writeFileFromIS(InputStream inputStream, OutputStream outputStream) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                bufferedOutputStream = new BufferedOutputStream(outputStream);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = bufferedInputStream2.read(bArr);
                        if (i2 == -1) {
                            outputStream.flush();
                            close(bufferedInputStream2);
                            close(bufferedOutputStream);
                            return true;
                        }
                        outputStream.write(bArr, 0, i2);
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    try {
                        e.printStackTrace();
                        close(bufferedInputStream);
                        close(bufferedOutputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        close(bufferedInputStream);
                        close(bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = bufferedInputStream2;
                    close(bufferedInputStream);
                    close(bufferedOutputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedOutputStream = null;
            }
        } catch (Exception e4) {
            e = e4;
            bufferedOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
        }
    }
}
