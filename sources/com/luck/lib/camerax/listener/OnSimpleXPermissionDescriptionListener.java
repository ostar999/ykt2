package com.luck.lib.camerax.listener;

import android.content.Context;
import android.view.ViewGroup;

/* loaded from: classes4.dex */
public interface OnSimpleXPermissionDescriptionListener {
    void onDismiss(ViewGroup viewGroup);

    void onPermissionDescription(Context context, ViewGroup viewGroup, String str);
}
