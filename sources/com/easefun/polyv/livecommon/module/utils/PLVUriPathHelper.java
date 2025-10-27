package com.easefun.polyv.livecommon.module.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.luck.picture.lib.config.PictureMimeType;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.thirdpart.blankj.utilcode.util.CloseUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class PLVUriPathHelper {
    public static final String COMPRESS_IMAGE = "compressImage:";
    private static final String TAG = "PLVUriPathHelper";

    /* JADX WARN: Multi-variable type inference failed */
    private static File compressImage(Context context, Bitmap bitmap) throws Throwable {
        String str;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        if (Build.VERSION.SDK_INT >= 24) {
            str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        } else {
            Date date = new Date();
            str = (date.getYear() + date.getMonth() + date.getDate() + date.getHours() + date.getMinutes() + date.getSeconds()) + "";
        }
        FileOutputStream fileOutputStream = null;
        File externalFilesDir = context.getExternalFilesDir(null);
        PLVCommonLog.e("uri", COMPRESS_IMAGE + externalFilesDir);
        File file = new File(externalFilesDir.getAbsolutePath(), str + PictureMimeType.PNG);
        int i2 = 1;
        i2 = 1;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream2.flush();
                    Closeable[] closeableArr = {fileOutputStream2};
                    CloseUtils.closeIO(closeableArr);
                    i2 = closeableArr;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    PLVCommonLog.e(TAG, COMPRESS_IMAGE + e.getMessage());
                    Closeable[] closeableArr2 = {fileOutputStream};
                    CloseUtils.closeIO(closeableArr2);
                    i2 = closeableArr2;
                    return file;
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    PLVCommonLog.e(TAG, COMPRESS_IMAGE + e.getMessage());
                    Closeable[] closeableArr3 = {fileOutputStream};
                    CloseUtils.closeIO(closeableArr3);
                    i2 = closeableArr3;
                    return file;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    Closeable[] closeableArr4 = new Closeable[i2];
                    closeableArr4[0] = fileOutputStream;
                    CloseUtils.closeIO(closeableArr4);
                    throw th;
                }
            } catch (FileNotFoundException e4) {
                e = e4;
            } catch (IOException e5) {
                e = e5;
            }
            return file;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void copyFile(Context context, Uri srcUri, File dstFile) throws Throwable {
        FileOutputStream fileOutputStream;
        InputStream inputStream = null;
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStreamOpenInputStream == null) {
                CloseUtils.closeIO(inputStreamOpenInputStream, null);
                return;
            }
            try {
                fileOutputStream = new FileOutputStream(dstFile);
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
            try {
                copyStream(inputStreamOpenInputStream, fileOutputStream);
                CloseUtils.closeIO(inputStreamOpenInputStream, fileOutputStream);
            } catch (Exception e3) {
                e = e3;
                inputStream = inputStreamOpenInputStream;
                try {
                    e.printStackTrace();
                    PLVCommonLog.e(TAG, "copyFile:" + e.getMessage());
                    CloseUtils.closeIO(inputStream, fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    CloseUtils.closeIO(inputStream, fileOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = inputStreamOpenInputStream;
                CloseUtils.closeIO(inputStream, fileOutputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
    }

    public static int copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] bArr = new byte[2048];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(input, 2048);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(output, 2048);
        int i2 = 0;
        while (true) {
            try {
                int i3 = bufferedInputStream.read(bArr, 0, 2048);
                if (i3 == -1) {
                    bufferedOutputStream.flush();
                    CloseUtils.closeIO(bufferedInputStream, bufferedOutputStream);
                    return i2;
                }
                bufferedOutputStream.write(bArr, 0, i3);
                i2 += i3;
            } catch (Throwable th) {
                CloseUtils.closeIO(bufferedInputStream, bufferedOutputStream);
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getDataColumn(android.content.Context r8, android.net.Uri r9, java.lang.String r10, java.lang.String[] r11) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}
            r7 = 0
            android.content.ContentResolver r1 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            r6 = 0
            r2 = r9
            r4 = r10
            r5 = r11
            android.database.Cursor r10 = r1.query(r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            if (r10 == 0) goto L27
            boolean r11 = r10.moveToFirst()     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L45
            if (r11 == 0) goto L27
            int r11 = r10.getColumnIndexOrThrow(r0)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L45
            java.lang.String r8 = r10.getString(r11)     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> L45
            r10.close()
            return r8
        L27:
            if (r10 == 0) goto L50
        L29:
            r10.close()
            goto L50
        L2d:
            r8 = move-exception
            goto L47
        L2f:
            r10 = r7
        L30:
            java.lang.String r11 = getFilePathFromURI(r8, r9)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4d
            boolean r0 = android.text.TextUtils.isEmpty(r11)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4d
            if (r0 != 0) goto L3b
            goto L3f
        L3b:
            java.lang.String r11 = getFilePathForN(r9, r8)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4d
        L3f:
            if (r10 == 0) goto L44
            r10.close()
        L44:
            return r11
        L45:
            r8 = move-exception
            r7 = r10
        L47:
            if (r7 == 0) goto L4c
            r7.close()
        L4c:
            throw r8
        L4d:
            if (r10 == 0) goto L50
            goto L29
        L50:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.module.utils.PLVUriPathHelper.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static String getFileName(Uri uri) {
        String path;
        int iLastIndexOf;
        if (uri == null || (iLastIndexOf = (path = uri.getPath()).lastIndexOf(47)) == -1) {
            return null;
        }
        return path.substring(iLastIndexOf + 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.io.InputStream] */
    private static String getFilePathForN(Uri uri, Context context) throws Throwable {
        FileOutputStream fileOutputStream;
        Exception e2;
        Cursor cursorQuery = context.getContentResolver().query(uri, null, null, null, null);
        int columnIndex = cursorQuery.getColumnIndex("_display_name");
        int columnIndex2 = cursorQuery.getColumnIndex("_size");
        cursorQuery.moveToFirst();
        String string = cursorQuery.getString(columnIndex);
        Long.toString(cursorQuery.getLong(columnIndex2));
        File file = new File(context.getFilesDir(), string);
        Object obj = null;
        try {
            try {
                uri = context.getContentResolver().openInputStream(uri);
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Exception e3) {
                    e2 = e3;
                    fileOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    context = null;
                    obj = uri;
                    CloseUtils.closeIO(new Closeable[]{obj, context});
                    throw th;
                }
            } catch (Exception e4) {
                fileOutputStream = null;
                e2 = e4;
                uri = 0;
            } catch (Throwable th2) {
                th = th2;
                context = null;
                CloseUtils.closeIO(new Closeable[]{obj, context});
                throw th;
            }
            try {
                byte[] bArr = new byte[Math.min(uri.available(), 1048576)];
                while (true) {
                    int i2 = uri.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                }
                CloseUtils.closeIO(new Closeable[]{uri, fileOutputStream});
            } catch (Exception e5) {
                e2 = e5;
                Log.e("Exception", e2.getMessage());
                CloseUtils.closeIO(new Closeable[]{uri, fileOutputStream});
                return file.getPath();
            }
            return file.getPath();
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) throws Throwable {
        File filesDir = context.getFilesDir();
        String fileName = getFileName(contentUri);
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        File file = new File(filesDir + File.separator + fileName);
        copyFile(context, contentUri, file);
        return file.getAbsolutePath();
    }

    public static String getPath(final Context context, final Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), PLVFormatUtils.parseLong(DocumentsContract.getDocumentId(uri))), null, null);
                }
                if (isMediaDocument(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = strArrSplit2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String getPrivatePath(Context context, Uri uri) {
        try {
            return compressImage(context, MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri)).getAbsolutePath();
        } catch (IOException e2) {
            PLVCommonLog.e(TAG, "getPrivatePath:" + e2.getMessage());
            return "";
        }
    }

    public static String getRealFileName(final Context context, final Uri uri) {
        int columnIndex;
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
        String string = null;
        if (cursorQuery != null) {
            if (cursorQuery.moveToFirst() && (columnIndex = cursorQuery.getColumnIndex("_display_name")) > -1) {
                string = cursorQuery.getString(columnIndex);
            }
            cursorQuery.close();
        }
        return string;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
