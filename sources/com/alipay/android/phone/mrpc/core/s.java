package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* loaded from: classes2.dex */
public final class s {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f3002a;

    public static final boolean a(Context context) {
        Boolean bool = f3002a;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            Boolean boolValueOf = Boolean.valueOf((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0);
            f3002a = boolValueOf;
            return boolValueOf.booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }
}
