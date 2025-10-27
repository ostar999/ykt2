package com.bumptech.glide.repackaged.com.google.common.collect;

import java.lang.reflect.Array;

/* loaded from: classes2.dex */
final class Platform {
    public static <T> T[] newArray(T[] tArr, int i2) {
        return (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
    }
}
