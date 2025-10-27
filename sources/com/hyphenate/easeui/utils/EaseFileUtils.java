package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.hyphenate.easeui.manager.EasePreferenceManager;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import java.io.File;

/* loaded from: classes4.dex */
public class EaseFileUtils {
    private static final String TAG = "EaseFileUtils";

    public static void deleteFile(Context context, Uri uri) {
        if (isFileExistByUri(context, uri)) {
            String filePath = getFilePath(context, uri);
            if (TextUtils.isEmpty(filePath)) {
                try {
                    context.getContentResolver().delete(uri, null, null);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        return EMFileHelper.getInstance().getFilename(uri);
    }

    public static String getFilePath(Context context, Uri uri) {
        return EMFileHelper.getInstance().getFilePath(uri);
    }

    private static String getLastSubFromUri(Uri uri) {
        if (uri == null) {
            return "";
        }
        String string = uri.toString();
        return !string.contains("/") ? "" : string.substring(string.lastIndexOf("/") + 1);
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isFileExistByUri(Context context, Uri uri) {
        return EMFileHelper.getInstance().isFileExist(uri);
    }

    public static boolean isFileProvider(Context context, Uri uri) {
        return (context.getApplicationInfo().packageName + ".fileProvider").equalsIgnoreCase(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isOtherFileProvider(Context context, Uri uri) {
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(scheme) || TextUtils.isEmpty(authority)) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationInfo().packageName);
        sb.append(".fileProvider");
        return !sb.toString().equalsIgnoreCase(uri.getAuthority()) && "content".equalsIgnoreCase(uri.getScheme()) && authority.contains(".fileProvider".toLowerCase());
    }

    private static boolean isQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static boolean saveUriPermission(Context context, Uri uri, Intent intent) {
        if (context == null || uri == null || !uriStartWithContent(uri)) {
            return false;
        }
        String lastSubFromUri = null;
        try {
            context.getContentResolver().takePersistableUriPermission(uri, (intent != null ? intent.getFlags() : 0) & 3);
            lastSubFromUri = getLastSubFromUri(uri);
            EMLog.d(TAG, "saveUriPermission last part of Uri: " + lastSubFromUri);
        } catch (SecurityException e2) {
            EMLog.e("EaseFileUtils", "saveUriPermission failed e: " + e2.getMessage());
        }
        if (!TextUtils.isEmpty(lastSubFromUri)) {
            EasePreferenceManager.getInstance().putString(lastSubFromUri, uri.toString());
            return true;
        }
        return false;
    }

    public static Uri takePersistableUriPermission(Context context, Uri uri) {
        if (context == null || uri == null || !uriStartWithContent(uri)) {
            return null;
        }
        String lastSubFromUri = getLastSubFromUri(uri);
        if (!TextUtils.isEmpty(lastSubFromUri)) {
            String string = EasePreferenceManager.getInstance().getString(lastSubFromUri);
            if (!TextUtils.isEmpty(string)) {
                try {
                    context.getContentResolver().takePersistableUriPermission(Uri.parse(string), 1);
                    return Uri.parse(string);
                } catch (SecurityException e2) {
                    EMLog.e("EaseFileUtils", "takePersistableUriPermission failed e: " + e2.getMessage());
                    return null;
                }
            }
        }
        try {
            context.getContentResolver().takePersistableUriPermission(uri, 1);
            return uri;
        } catch (SecurityException e3) {
            EMLog.e("EaseFileUtils", "takePersistableUriPermission failed e: " + e3.getMessage());
            return null;
        }
    }

    public static boolean uriStartWithContent(Uri uri) {
        return "content".equalsIgnoreCase(uri.getScheme());
    }

    public static boolean uriStartWithFile(Uri uri) {
        return "file".equalsIgnoreCase(uri.getScheme()) && uri.toString().length() > 7;
    }
}
