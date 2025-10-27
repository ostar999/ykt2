package com.pizidea.imagepicker.fileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import java.io.File;

/* loaded from: classes4.dex */
public final class FileProviders {
    private static final String FILE_PROVIDER_AUTHORITY = "com.yikaobang.yixue.fileProvider";

    private FileProviders() {
    }

    public static boolean checkUriPermission(@NonNull Context context, @NonNull Uri uri) {
        return checkUriPermission(context, uri, 3);
    }

    public static boolean checkUriReadPermission(@NonNull Context context, @NonNull Uri uri) {
        return checkUriPermission(context, uri, 1);
    }

    public static boolean checkUriWritePermission(@NonNull Context context, @NonNull Uri uri) {
        return checkUriPermission(context, uri, 2);
    }

    public static Uri getUriForFile(@NonNull Context context, @NonNull File file, @Nullable Intent intent) {
        return getUriForFile(context, file, intent, 3);
    }

    public static Uri getUriForReadFile(@NonNull Context context, @NonNull File file, @Nullable Intent intent) {
        return getUriForFile(context, file, intent, 1);
    }

    public static Uri getUriForWriteFile(@NonNull Context context, @NonNull File file, @Nullable Intent intent) {
        return getUriForFile(context, file, intent, 2);
    }

    private static boolean isAtLeastNougat() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static void revokeUriPermission(@NonNull Context context, @NonNull Uri uri) {
        revokeUriPermission(context, uri, 3);
    }

    public static void revokeUriReadPermission(@NonNull Context context, @NonNull Uri uri) {
        revokeUriPermission(context, uri, 1);
    }

    public static void revokeUriWritePermission(@NonNull Context context, @NonNull Uri uri) {
        revokeUriPermission(context, uri, 2);
    }

    private static boolean checkUriPermission(@NonNull Context context, @NonNull Uri uri, int i2) {
        return !isAtLeastNougat() || context.checkCallingUriPermission(uri, i2) == 0;
    }

    @NonNull
    private static Uri getUriForFile(@NonNull Context context, @NonNull File file, @Nullable Intent intent, int i2) {
        if (!isAtLeastNougat()) {
            return getUriForFile(file);
        }
        try {
            Uri uriForFile = FileProvider.getUriForFile(context, "com.yikaobang.yixue.fileProvider", file);
            if (intent != null) {
                intent.addFlags(i2);
                return uriForFile;
            }
            context.grantUriPermission(context.getPackageName(), uriForFile, i2);
            return uriForFile;
        } catch (Throwable unused) {
            return getUriForFile(file);
        }
    }

    private static void revokeUriPermission(@NonNull Context context, @NonNull Uri uri, int i2) {
        if (isAtLeastNougat()) {
            context.revokeUriPermission(uri, i2);
        }
    }

    @NonNull
    private static Uri getUriForFile(@NonNull File file) {
        try {
            return Uri.fromFile(file);
        } catch (Throwable unused) {
            return Uri.EMPTY;
        }
    }
}
