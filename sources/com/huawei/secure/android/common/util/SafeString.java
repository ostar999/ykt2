package com.huawei.secure.android.common.util;

import android.util.Log;

/* loaded from: classes4.dex */
public class SafeString {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8446a = "SafeString";

    /* renamed from: b, reason: collision with root package name */
    private static final String f8447b = "";

    public static String replace(String str, CharSequence charSequence, CharSequence charSequence2) {
        if (str != null && charSequence != null && charSequence2 != null) {
            try {
                return str.replace(charSequence, charSequence2);
            } catch (Exception e2) {
                Log.e(f8446a, "replace: " + e2.getMessage());
            }
        }
        return str;
    }

    public static String substring(String str, int i2) {
        if (str != null && str.length() >= i2 && i2 >= 0) {
            try {
                return str.substring(i2);
            } catch (Exception e2) {
                Log.e(f8446a, "substring exception: " + e2.getMessage());
            }
        }
        return "";
    }

    public static String substring(String str, int i2, int i3) {
        if (str != null && i2 >= 0 && i3 <= str.length() && i3 >= i2) {
            try {
                return str.substring(i2, i3);
            } catch (Exception e2) {
                Log.e(f8446a, "substring: " + e2.getMessage());
            }
        }
        return "";
    }
}
