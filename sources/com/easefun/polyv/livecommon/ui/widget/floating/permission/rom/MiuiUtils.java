package com.easefun.polyv.livecommon.ui.widget.floating.permission.rom;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;

/* loaded from: classes3.dex */
public class MiuiUtils {
    private static final String TAG = "MiuiUtils";

    public static void applyMiuiPermission(Fragment fragment) throws Throwable {
        int miuiVersion = getMiuiVersion();
        if (miuiVersion == 5) {
            goToMiuiPermissionActivity_V5(fragment);
            return;
        }
        if (miuiVersion == 6) {
            goToMiuiPermissionActivity_V6(fragment);
            return;
        }
        if (miuiVersion == 7) {
            goToMiuiPermissionActivity_V7(fragment);
            return;
        }
        if (miuiVersion >= 8) {
            goToMiuiPermissionActivity_V8(fragment);
            return;
        }
        Log.e(TAG, "this is a special MIUI rom version, its version code " + miuiVersion);
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

    public static int getMiuiVersion() throws Throwable {
        String systemProperty = RomUtils.getSystemProperty("ro.miui.ui.version.name");
        if (systemProperty == null) {
            return -1;
        }
        try {
            return Integer.parseInt(systemProperty.substring(1));
        } catch (Exception e2) {
            Log.e(TAG, "get miui version code error, version : " + systemProperty);
            Log.e(TAG, Log.getStackTraceString(e2));
            return -1;
        }
    }

    public static void goToMiuiPermissionActivity_V5(Fragment fragment) {
        String packageName = fragment.getActivity().getPackageName();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", packageName, null));
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
        } else {
            Log.e(TAG, "intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V6(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V7(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    public static void goToMiuiPermissionActivity_V8(Fragment fragment) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent, fragment.getActivity())) {
            fragment.startActivityForResult(intent, 1010);
            return;
        }
        Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent2.setPackage("com.miui.securitycenter");
        intent2.putExtra("extra_pkgname", fragment.getActivity().getPackageName());
        if (isIntentAvailable(intent2, fragment.getActivity())) {
            fragment.startActivityForResult(intent2, 1010);
        } else {
            Log.e(TAG, "Intent is not available!");
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
}
