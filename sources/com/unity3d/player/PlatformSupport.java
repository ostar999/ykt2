package com.unity3d.player;

import android.os.Build;

/* loaded from: classes6.dex */
public class PlatformSupport {
    static final boolean MARSHMALLOW_SUPPORT;
    static final boolean NOUGAT_SUPPORT;

    static {
        int i2 = Build.VERSION.SDK_INT;
        MARSHMALLOW_SUPPORT = true;
        NOUGAT_SUPPORT = i2 >= 24;
    }
}
