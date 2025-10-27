package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;

/* loaded from: classes3.dex */
public class OppoUtils {
    private static final String TAG = "OppoUtils";

    public static void applyOppoPermission(Fragment fragment) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"));
            fragment.startActivityForResult(intent, 1010);
        } catch (Exception e2) {
            e2.printStackTrace();
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
