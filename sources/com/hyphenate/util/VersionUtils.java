package com.hyphenate.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.RequiresApi;

/* loaded from: classes4.dex */
public class VersionUtils {
    @RequiresApi(api = 29)
    public static boolean isExternalStorageLegacy() {
        return Environment.isExternalStorageLegacy();
    }

    public static boolean isTargetQ(Context context) {
        return Build.VERSION.SDK_INT >= 29 && context.getApplicationInfo().targetSdkVersion >= 29;
    }
}
