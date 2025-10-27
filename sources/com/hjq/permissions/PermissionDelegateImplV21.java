package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 21)
/* loaded from: classes4.dex */
class PermissionDelegateImplV21 extends PermissionDelegateImplV19 {
    private static Intent getPackagePermissionIntent(@NonNull Context context) {
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        if (AndroidVersion.isAndroid10()) {
            intent.setData(PermissionUtils.getPackageNameUri(context));
        }
        return !PermissionUtils.areActivityIntent(context, intent) ? PermissionIntentManager.getApplicationDetailsIntent(context) : intent;
    }

    private static boolean isGrantedPackagePermission(@NonNull Context context) {
        return PermissionUtils.checkOpNoThrow(context, "android:get_usage_stats");
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.PACKAGE_USAGE_STATS) ? getPackagePermissionIntent(context) : super.getPermissionIntent(context, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        if (PermissionUtils.equalsPermission(str, Permission.PACKAGE_USAGE_STATS)) {
            return false;
        }
        return super.isDoNotAskAgainPermission(activity, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.PACKAGE_USAGE_STATS) ? isGrantedPackagePermission(context) : super.isGrantedPermission(context, str);
    }
}
