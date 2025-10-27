package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import android.widget.Toast;

/* loaded from: classes3.dex */
public class HuaweiUtils {
    private static final String TAG = "HuaweiUtils";

    public static void applyPermission(Fragment fragment) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
            if (RomUtils.getEmuiVersion() == 3.1d) {
                fragment.startActivityForResult(intent, 1010);
            } else {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
                fragment.startActivityForResult(intent, 1010);
            }
        } catch (ActivityNotFoundException e2) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem"));
            fragment.startActivityForResult(intent2, 1010);
            e2.printStackTrace();
            Log.e(TAG, Log.getStackTraceString(e2));
        } catch (SecurityException e3) {
            Intent intent3 = new Intent();
            intent3.setFlags(268435456);
            intent3.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
            fragment.startActivityForResult(intent3, 1010);
            Log.e(TAG, Log.getStackTraceString(e3));
        } catch (Exception e4) {
            Toast.makeText(fragment.getActivity(), "进入设置页面失败，请手动设置", 1).show();
            Log.e(TAG, Log.getStackTraceString(e4));
        }
    }

    public static boolean checkFloatWindowPermission(Context context) {
        return checkOp(context, 24);
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        try {
            Class cls = Integer.TYPE;
            return ((Integer) AppOpsManager.class.getDeclaredMethod("checkOp", cls, cls, String.class).invoke(appOpsManager, Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName())).intValue() == 0;
        } catch (Exception e2) {
            Log.e(TAG, Log.getStackTraceString(e2));
            return false;
        }
    }
}
