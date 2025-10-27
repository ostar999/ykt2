package com.luck.lib.camerax.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;

/* loaded from: classes4.dex */
public class SimpleXPermissionUtil {
    public static void goIntentSetting(Activity activity, int i2) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
            activity.startActivityForResult(intent, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull @Size(min = 1) String... strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllGranted(int[] iArr) {
        if (iArr.length <= 0) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 != 0) {
                return false;
            }
        }
        return true;
    }
}
