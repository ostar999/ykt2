package com.yddmi.doctor.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import java.io.File;

/* loaded from: classes6.dex */
public class UriUtils {
    public static String getFileprovider(Context context) {
        return context.getApplicationContext().getApplicationInfo().processName + ".fileProvider";
    }

    public static Uri getUri(Context context, File file) {
        return Build.VERSION.SDK_INT >= 24 ? FileProvider.getUriForFile(context, getFileprovider(context), file) : Uri.fromFile(file);
    }

    public static Uri getUriFromBitmap(Context context, Bitmap bitmap) {
        return Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, (String) null, (String) null));
    }

    public static Uri getUriFromIntent(Context context, Intent intent) {
        String uriPath;
        Uri data = intent.getData();
        int i2 = Build.VERSION.SDK_INT;
        String path = null;
        if (DocumentsContract.isDocumentUri(context, data)) {
            String documentId = DocumentsContract.getDocumentId(data);
            if ("com.android.providers.media.documents".equals(data.getAuthority())) {
                uriPath = getUriPath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=" + documentId.split(":")[1]);
            } else if ("com.android.providers.downloads.documents".equals(data.getAuthority())) {
                uriPath = getUriPath(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId).longValue()), null);
            }
            path = uriPath;
        } else if ("content".equalsIgnoreCase(data.getScheme())) {
            path = getUriPath(context, data, null);
        } else if ("file".equalsIgnoreCase(data.getScheme())) {
            path = data.getPath();
        }
        File file = new File(path);
        return i2 >= 24 ? FileProvider.getUriForFile(context, getFileprovider(context), file) : Uri.fromFile(file);
    }

    private static String getUriPath(Context context, Uri uri, String str) {
        Cursor cursorQuery = context.getContentResolver().query(uri, null, str, null, null);
        if (cursorQuery != null) {
            string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("_data")) : null;
            cursorQuery.close();
        }
        return string;
    }

    public static Uri getUri(Context context, String str) {
        File file = new File(str);
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(context.getApplicationContext(), getFileprovider(context), file);
        }
        return Uri.fromFile(file);
    }
}
