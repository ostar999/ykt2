package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 19)
/* loaded from: classes4.dex */
class PermissionDelegateImplV19 extends PermissionDelegateImplV18 {
    @Override // com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.SYSTEM_ALERT_WINDOW) ? WindowPermissionCompat.getPermissionIntent(context) : PermissionUtils.equalsPermission(str, Permission.GET_INSTALLED_APPS) ? GetInstalledAppsPermissionCompat.getPermissionIntent(context) : PermissionUtils.equalsPermission(str, Permission.NOTIFICATION_SERVICE) ? NotificationPermissionCompat.getPermissionIntent(context) : (AndroidVersion.isAndroid13() || !PermissionUtils.equalsPermission(str, Permission.POST_NOTIFICATIONS)) ? super.getPermissionIntent(context, str) : NotificationPermissionCompat.getPermissionIntent(context);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        if (PermissionUtils.equalsPermission(str, Permission.SYSTEM_ALERT_WINDOW)) {
            return false;
        }
        if (PermissionUtils.equalsPermission(str, Permission.GET_INSTALLED_APPS)) {
            return GetInstalledAppsPermissionCompat.isDoNotAskAgainPermission(activity);
        }
        if (PermissionUtils.equalsPermission(str, Permission.NOTIFICATION_SERVICE)) {
            return false;
        }
        if (AndroidVersion.isAndroid13() || !PermissionUtils.equalsPermission(str, Permission.POST_NOTIFICATIONS)) {
            return super.isDoNotAskAgainPermission(activity, str);
        }
        return false;
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.SYSTEM_ALERT_WINDOW) ? WindowPermissionCompat.isGrantedPermission(context) : PermissionUtils.equalsPermission(str, Permission.GET_INSTALLED_APPS) ? GetInstalledAppsPermissionCompat.isGrantedPermission(context) : PermissionUtils.equalsPermission(str, Permission.NOTIFICATION_SERVICE) ? NotificationPermissionCompat.isGrantedPermission(context) : (AndroidVersion.isAndroid13() || !PermissionUtils.equalsPermission(str, Permission.POST_NOTIFICATIONS)) ? super.isGrantedPermission(context, str) : NotificationPermissionCompat.isGrantedPermission(context);
    }
}
