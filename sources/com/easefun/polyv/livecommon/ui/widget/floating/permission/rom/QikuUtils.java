package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;

/* loaded from: classes3.dex */
public class QikuUtils {
    private static final String TAG = "QikuUtils";

    public static void applyPermission(Fragment fragment) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
            return;
        }
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
        } else {
            Log.e(TAG, "can't open permission page with particular name, please use \"adb shell dumpsys activity\" command and tell me the name of the float window permission page");
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

    private static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
}
