package com.hjq.permissions;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final /* synthetic */ class a {
    public static void a(IPermissionInterceptor iPermissionInterceptor, @NonNull Activity activity, @NonNull List list, @NonNull List list2, boolean z2, @Nullable OnPermissionCallback onPermissionCallback) {
        if (onPermissionCallback == null) {
            return;
        }
        onPermissionCallback.onDenied(list2, z2);
    }

    public static void b(IPermissionInterceptor iPermissionInterceptor, @NonNull Activity activity, @NonNull List list, boolean z2, @Nullable OnPermissionCallback onPermissionCallback) {
    }

    public static void c(IPermissionInterceptor iPermissionInterceptor, @NonNull Activity activity, @NonNull List list, @NonNull List list2, boolean z2, @Nullable OnPermissionCallback onPermissionCallback) {
        if (onPermissionCallback == null) {
            return;
        }
        onPermissionCallback.onGranted(list2, z2);
    }

    public static void d(IPermissionInterceptor iPermissionInterceptor, @NonNull Activity activity, @NonNull List list, @Nullable OnPermissionCallback onPermissionCallback) {
        PermissionFragment.launch(activity, new ArrayList(list), iPermissionInterceptor, onPermissionCallback);
    }
}
