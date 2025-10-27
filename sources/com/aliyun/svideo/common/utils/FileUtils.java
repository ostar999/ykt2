package com.aliyun.svideo.common.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class FileUtils {
    public static void clearDirectory(File file) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                } else {
                    file2.delete();
                }
            }
        }
    }

    public static boolean deleteDirectory(File file) {
        clearDirectory(file);
        return file.delete();
    }

    public static boolean deleteFD(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
        file.renameTo(file2);
        return deleteFD(file2);
    }

    public static File getApplicationSdcardPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        return externalFilesDir == null ? context.getFilesDir() : externalFilesDir;
    }

    public static File getDbDir(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        }
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AliPlayerDemoDownload/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getDir(Context context) {
        String string;
        if (Build.VERSION.SDK_INT >= 29) {
            StringBuilder sb = new StringBuilder();
            sb.append(context.getExternalFilesDir(""));
            String str = File.separator;
            sb.append(str);
            sb.append("Media");
            sb.append(str);
            string = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory());
            String str2 = File.separator;
            sb2.append(str2);
            sb2.append("DCIM");
            sb2.append(str2);
            sb2.append("Camera");
            sb2.append(str2);
            string = sb2.toString();
        }
        File file = new File(string);
        if (!file.exists()) {
            file.mkdirs();
        }
        return string;
    }

    public static long getSdcardAvailableSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getAvailableBlocks() * statFs.getBlockSize();
    }

    public static long getSdcardTotalSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return statFs.getBlockCount() * statFs.getBlockSize();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String saveBitmap(Bitmap bitmap, String str) throws Throwable {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str, "snapShot_" + System.currentTimeMillis() + PictureMimeType.PNG);
        if (file2.exists()) {
            file2.delete();
        }
        FileOutputStream fileOutputStream = null;
        fileOutputStream = null;
        fileOutputStream = null;
        fileOutputStream = null;
        try {
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                try {
                    Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
                    bitmap.compress(compressFormat, 100, fileOutputStream2);
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    fileOutputStream2.close();
                    fileOutputStream = compressFormat;
                } catch (FileNotFoundException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = fileOutputStream;
                    }
                    return file2.getAbsolutePath();
                } catch (IOException e4) {
                    e = e4;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = fileOutputStream;
                    }
                    return file2.getAbsolutePath();
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
            } catch (IOException e7) {
                e = e7;
            }
            return file2.getAbsolutePath();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @RequiresApi(api = 29)
    public static void saveImgToMediaStore(Context context, String str, String str2) throws IOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", str2);
        contentValues.put("is_pending", (Integer) 1);
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriInsert = contentResolver.insert(MediaStore.Images.Media.getContentUri("external_primary"), contentValues);
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(uriInsert, "w", null);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorOpenFileDescriptor));
                byte[] bArr = new byte[2048];
                while (true) {
                    int i2 = bufferedInputStream.read(bArr);
                    if (i2 < 0) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, i2);
                    bufferedOutputStream.flush();
                }
                bufferedInputStream.close();
                bufferedOutputStream.close();
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
            } finally {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        contentValues.clear();
        contentValues.put("is_pending", (Integer) 0);
        contentResolver.update(uriInsert, contentValues, null, null);
    }

    @RequiresApi(api = 29)
    public static void saveVideoToMediaStore(Context context, String str) throws IOException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_display_name", jCurrentTimeMillis + "-video.mp4");
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("is_pending", (Integer) 1);
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriInsert = contentResolver.insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = contentResolver.openFileDescriptor(uriInsert, "w", null);
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorOpenFileDescriptor));
                byte[] bArr = new byte[2048];
                while (true) {
                    int i2 = bufferedInputStream.read(bArr);
                    if (i2 < 0) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, i2);
                    bufferedOutputStream.flush();
                }
                bufferedInputStream.close();
                bufferedOutputStream.close();
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                }
            } finally {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        contentValues.clear();
        contentValues.put("is_pending", (Integer) 0);
        contentResolver.update(uriInsert, contentValues, null, null);
    }

    public static boolean deleteFD(File file) {
        if (file.exists()) {
            return file.isDirectory() ? deleteDirectory(file) : file.delete();
        }
        return false;
    }
}
