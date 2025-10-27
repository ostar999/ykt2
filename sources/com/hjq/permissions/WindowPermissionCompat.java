package com.hjq.permissions;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
final class WindowPermissionCompat {
    private static final int OP_SYSTEM_ALERT_WINDOW_DEFAULT_VALUE = 24;
    private static final String OP_SYSTEM_ALERT_WINDOW_FIELD_NAME = "OP_SYSTEM_ALERT_WINDOW";

    public static Intent getPermissionIntent(@NonNull Context context) {
        if (!AndroidVersion.isAndroid6()) {
            if (PhoneRomUtils.isEmui()) {
                return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getEmuiWindowPermissionPageIntent(context), PermissionIntentManager.getApplicationDetailsIntent(context));
            }
            if (PhoneRomUtils.isMiui()) {
                return StartActivityManager.addSubIntentToMainIntent(PhoneRomUtils.isMiuiOptimization() ? PermissionIntentManager.getMiuiWindowPermissionPageIntent(context) : null, PermissionIntentManager.getApplicationDetailsIntent(context));
            }
            return PhoneRomUtils.isColorOs() ? StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getColorOsWindowPermissionPageIntent(context), PermissionIntentManager.getApplicationDetailsIntent(context)) : PhoneRomUtils.isOriginOs() ? StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getOriginOsWindowPermissionPageIntent(context), PermissionIntentManager.getApplicationDetailsIntent(context)) : PhoneRomUtils.isOneUi() ? StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getOneUiWindowPermissionPageIntent(context), PermissionIntentManager.getApplicationDetailsIntent(context)) : PermissionIntentManager.getApplicationDetailsIntent(context);
        }
        if (AndroidVersion.isAndroid11() && PhoneRomUtils.isMiui() && PhoneRomUtils.isMiuiOptimization()) {
            return StartActivityManager.addSubIntentToMainIntent(PermissionIntentManager.getMiuiPermissionPageIntent(context), PermissionIntentManager.getApplicationDetailsIntent(context));
        }
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(PermissionUtils.getPackageNameUri(context));
        return PermissionUtils.areActivityIntent(context, intent) ? intent : PermissionIntentManager.getApplicationDetailsIntent(context);
    }

    public static boolean isGrantedPermission(@NonNull Context context) {
        if (AndroidVersion.isAndroid6()) {
            return Settings.canDrawOverlays(context);
        }
        if (AndroidVersion.isAndroid4_4()) {
            return PermissionUtils.checkOpNoThrow(context, OP_SYSTEM_ALERT_WINDOW_FIELD_NAME, 24);
        }
        return true;
    }
}
