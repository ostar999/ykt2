package com.bumptech.glide.repackaged.com.google.common.base;

/* loaded from: classes2.dex */
public final class Strings {
    public static String emptyToNull(String str) {
        if (isNullOrEmpty(str)) {
            return null;
        }
        return str;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
