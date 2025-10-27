package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 18)
/* loaded from: classes4.dex */
class PermissionDelegateImplV18 extends PermissionDelegateImplV14 {
    @Override // com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.BIND_NOTIFICATION_LISTENER_SERVICE) ? NotificationListenerPermissionCompat.getPermissionIntent(context) : super.getPermissionIntent(context, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        if (PermissionUtils.equalsPermission(str, Permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {
            return false;
        }
        return super.isDoNotAskAgainPermission(activity, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.BIND_NOTIFICATION_LISTENER_SERVICE) ? NotificationListenerPermissionCompat.isGrantedPermission(context) : super.isGrantedPermission(context, str);
    }
}
