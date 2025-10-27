package com.hjq.permissions;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPermissionInterceptor {
    void deniedPermissionRequest(@NonNull Activity activity, @NonNull List<String> list, @NonNull List<String> list2, boolean z2, @Nullable OnPermissionCallback onPermissionCallback);

    void finishPermissionRequest(@NonNull Activity activity, @NonNull List<String> list, boolean z2, @Nullable OnPermissionCallback onPermissionCallback);

    void grantedPermissionRequest(@NonNull Activity activity, @NonNull List<String> list, @NonNull List<String> list2, boolean z2, @Nullable OnPermissionCallback onPermissionCallback);

    void launchPermissionRequest(@NonNull Activity activity, @NonNull List<String> list, @Nullable OnPermissionCallback onPermissionCallback);
}
