package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.PLVFloatPermissionUtils;

/* loaded from: classes3.dex */
public class MeizuUtils {
    private static final String TAG = "MeizuUtils";

    public static void applyPermission(Fragment fragment) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.putExtra("packageName", fragment.getActivity().getPackageName());
            fragment.startActivityForResult(intent, 1010);
        } catch (Exception e2) {
            try {
                Log.e(TAG, "获取悬浮窗权限, 打开AppSecActivity失败, " + Log.getStackTraceString(e2));
                PLVFloatPermissionUtils.commonROMPermissionApplyInternal(fragment);
            } catch (Exception e3) {
                Log.e(TAG, "获取悬浮窗权限失败, 通用获取方法失败, " + Log.getStackTraceString(e3));
            }
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
