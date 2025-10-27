package com.luck.picture.lib.interfaces;

import androidx.fragment.app.Fragment;

/* loaded from: classes4.dex */
public interface OnPermissionsInterceptListener {
    boolean hasPermissions(Fragment fragment);

    void requestPermission(Fragment fragment, String[] strArr, OnCallbackListener<Boolean> onCallbackListener);
}
