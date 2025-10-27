package com.luck.picture.lib.permissions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.luck.picture.lib.utils.SdkVersionUtils;

/* loaded from: classes4.dex */
public class PermissionUtil {
    public static final String ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION = "android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION";

    public static void goIntentSetting(Fragment fragment, boolean z2, int i2) {
        try {
            if (SdkVersionUtils.isR() && z2) {
                fragment.startActivityForResult(new Intent(ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), i2);
            } else {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", fragment.getActivity().getPackageName(), null));
                fragment.startActivityForResult(intent, i2);
            }
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
