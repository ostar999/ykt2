package com.aliyun.svideo.common.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.aliyun.svideo.common.R;
import com.hjq.permissions.Permission;

/* loaded from: classes2.dex */
public class PermissionUtils {
    private static final String TAG = "com.aliyun.svideo.common.utils.PermissionUtils";
    public static final String[] PERMISSION_MANIFEST = {Permission.CAMERA, "android.permission.BLUETOOTH", Permission.RECORD_AUDIO, Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_STORAGE = {Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_CAMERA = {Permission.CAMERA, Permission.RECORD_AUDIO, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};
    public static final int[] NO_PERMISSION_TIP = {R.string.alivc_common_no_camera_permission, R.string.alivc_common_no_record_bluetooth_permission, R.string.alivc_common_no_record_audio_permission, R.string.alivc_common_no_read_phone_state_permission, R.string.alivc_common_no_write_external_storage_permission, R.string.alivc_common_no_read_external_storage_permission};

    private static boolean checkPermission(Context context, String str) {
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }

    public static boolean checkPermissionsGroup(Context context, String[] strArr) {
        for (String str : strArr) {
            if (!checkPermission(context, str)) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = 19)
    public static boolean checkXiaomi(Context context, String[] strArr) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        String packageName = context.getPackageName();
        for (String str : strArr) {
            if (appOpsManager.checkOp(str, Binder.getCallingUid(), packageName) == 1) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] strArr, int i2) {
        if (checkPermissionsGroup(activity, strArr)) {
            return;
        }
        ActivityCompat.requestPermissions(activity, strArr, i2);
    }

    public static void showNoPermissionTip(Context context, String str) throws IllegalAccessException, IllegalArgumentException {
        ToastUtils.show(context, str, 1);
    }
}
