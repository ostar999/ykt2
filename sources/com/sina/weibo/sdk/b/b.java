package com.sina.weibo.sdk.b;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import cn.hutool.core.img.ImgUtil;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.File;

/* loaded from: classes6.dex */
public final class b {
    @TargetApi(19)
    public static String a(Context context, Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (b(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else {
                if (c(uri)) {
                    return a(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                }
                if (d(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = strArrSplit2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return a(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return e(uri) ? uri.getLastPathSegment() : b(context, uri);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String b(File file) {
        try {
            String absolutePath = file.getAbsolutePath();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(absolutePath, options);
            String str = options.outMimeType;
            if (!str.contains("jpg") && !str.contains(ImgUtil.IMAGE_TYPE_GIF) && !str.contains("png")) {
                if (!str.contains("jpeg")) {
                    return null;
                }
            }
            return MimeTypes.IMAGE_ALL;
        } catch (Exception unused) {
            return MimeTypes.ANY_TYPE;
        }
    }

    private static boolean c(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean d(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean e(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static long f(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return 0L;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
    }

    public static boolean c(File file) {
        if (file.getParent() != null) {
            file = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return !file.getCanonicalFile().equals(file.getAbsoluteFile());
    }

    private static boolean b(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static String b(Context context, Uri uri) {
        String string = uri.toString();
        return new File(context.getExternalFilesDir(null), string.substring(string.lastIndexOf("/"))).getAbsolutePath();
    }

    private static String a(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
        } catch (Exception unused) {
            if (cursorQuery != null) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        if (cursorQuery != null && cursorQuery.moveToFirst()) {
            String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
            cursorQuery.close();
            return string;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return b(context, uri);
    }
}
