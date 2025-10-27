package com.beizi.fusion.g;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* loaded from: classes2.dex */
public class j {
    public static File a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            File externalFilesDir = ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) ? context.getExternalFilesDir(null) : null;
            return externalFilesDir == null ? context.getFilesDir() : externalFilesDir;
        } catch (Exception unused) {
            return context.getFilesDir();
        }
    }

    public static File b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            File externalCacheDir = ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) ? context.getExternalCacheDir() : null;
            return externalCacheDir == null ? context.getCacheDir() : externalCacheDir;
        } catch (Exception unused) {
            return context.getCacheDir();
        }
    }
}
