package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 14)
/* loaded from: classes4.dex */
class PermissionDelegateImplV14 implements PermissionDelegate {
    private static Intent getVpnPermissionIntent(@NonNull Context context) {
        Intent intentPrepare = VpnService.prepare(context);
        return !PermissionUtils.areActivityIntent(context, intentPrepare) ? PermissionIntentManager.getApplicationDetailsIntent(context) : intentPrepare;
    }

    private static boolean isGrantedVpnPermission(@NonNull Context context) {
        return VpnService.prepare(context) == null;
    }

    @Override // com.hjq.permissions.PermissionDelegate
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.BIND_VPN_SERVICE) ? getVpnPermissionIntent(context) : PermissionIntentManager.getApplicationDetailsIntent(context);
    }

    @Override // com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        return false;
    }

    @Override // com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        if (PermissionUtils.equalsPermission(str, Permission.BIND_VPN_SERVICE)) {
            return isGrantedVpnPermission(context);
        }
        return true;
    }
}
