package com.hjq.permissions;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 31)
/* loaded from: classes4.dex */
class PermissionDelegateImplV31 extends PermissionDelegateImplV30 {
    private static Intent getAlarmPermissionIntent(@NonNull Context context) {
        Intent intent = new Intent("android.settings.REQUEST_SCHEDULE_EXACT_ALARM");
        intent.setData(PermissionUtils.getPackageNameUri(context));
        return !PermissionUtils.areActivityIntent(context, intent) ? PermissionIntentManager.getApplicationDetailsIntent(context) : intent;
    }

    private static boolean isGrantedAlarmPermission(@NonNull Context context) {
        return ((AlarmManager) context.getSystemService(AlarmManager.class)).canScheduleExactAlarms();
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.SCHEDULE_EXACT_ALARM) ? getAlarmPermissionIntent(context) : super.getPermissionIntent(context, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        if (PermissionUtils.equalsPermission(str, Permission.SCHEDULE_EXACT_ALARM)) {
            return false;
        }
        return (PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_SCAN) || PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_CONNECT) || PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_ADVERTISE)) ? (PermissionUtils.checkSelfPermission(activity, str) || PermissionUtils.shouldShowRequestPermissionRationale(activity, str)) ? false : true : (activity.getApplicationInfo().targetSdkVersion < 31 || !PermissionUtils.equalsPermission(str, Permission.ACCESS_BACKGROUND_LOCATION)) ? super.isDoNotAskAgainPermission(activity, str) : (PermissionUtils.checkSelfPermission(activity, Permission.ACCESS_FINE_LOCATION) || PermissionUtils.checkSelfPermission(activity, Permission.ACCESS_COARSE_LOCATION)) ? (PermissionUtils.checkSelfPermission(activity, str) || PermissionUtils.shouldShowRequestPermissionRationale(activity, str)) ? false : true : (PermissionUtils.shouldShowRequestPermissionRationale(activity, Permission.ACCESS_FINE_LOCATION) || PermissionUtils.shouldShowRequestPermissionRationale(activity, Permission.ACCESS_COARSE_LOCATION)) ? false : true;
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.SCHEDULE_EXACT_ALARM) ? isGrantedAlarmPermission(context) : (PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_SCAN) || PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_CONNECT) || PermissionUtils.equalsPermission(str, Permission.BLUETOOTH_ADVERTISE)) ? PermissionUtils.checkSelfPermission(context, str) : super.isGrantedPermission(context, str);
    }
}
