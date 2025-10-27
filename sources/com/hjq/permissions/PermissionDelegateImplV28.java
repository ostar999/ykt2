package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 28)
/* loaded from: classes4.dex */
class PermissionDelegateImplV28 extends PermissionDelegateImplV26 {
    @Override // com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.ACCEPT_HANDOVER) ? (PermissionUtils.checkSelfPermission(activity, str) || PermissionUtils.shouldShowRequestPermissionRationale(activity, str)) ? false : true : super.isDoNotAskAgainPermission(activity, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.ACCEPT_HANDOVER) ? PermissionUtils.checkSelfPermission(context, str) : super.isGrantedPermission(context, str);
    }
}
