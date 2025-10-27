package com.hjq.permissions;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = 34)
/* loaded from: classes4.dex */
class PermissionDelegateImplV34 extends PermissionDelegateImplV33 {
    @Override // com.hjq.permissions.PermissionDelegateImplV33, com.hjq.permissions.PermissionDelegateImplV31, com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isDoNotAskAgainPermission(@NonNull Activity activity, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_VISUAL_USER_SELECTED) ? (PermissionUtils.checkSelfPermission(activity, str) || PermissionUtils.shouldShowRequestPermissionRationale(activity, str)) ? false : true : super.isDoNotAskAgainPermission(activity, str);
    }

    @Override // com.hjq.permissions.PermissionDelegateImplV33, com.hjq.permissions.PermissionDelegateImplV31, com.hjq.permissions.PermissionDelegateImplV30, com.hjq.permissions.PermissionDelegateImplV29, com.hjq.permissions.PermissionDelegateImplV28, com.hjq.permissions.PermissionDelegateImplV26, com.hjq.permissions.PermissionDelegateImplV23, com.hjq.permissions.PermissionDelegateImplV21, com.hjq.permissions.PermissionDelegateImplV19, com.hjq.permissions.PermissionDelegateImplV18, com.hjq.permissions.PermissionDelegateImplV14, com.hjq.permissions.PermissionDelegate
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String str) {
        return PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_VISUAL_USER_SELECTED) ? PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED) : (!PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_IMAGES) || PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES)) ? (!PermissionUtils.equalsPermission(str, Permission.READ_MEDIA_VIDEO) || PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VIDEO)) ? super.isGrantedPermission(context, str) : PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED) : PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_VISUAL_USER_SELECTED);
    }
}
