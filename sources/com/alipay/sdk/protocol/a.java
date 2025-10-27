package com.alipay.sdk.protocol;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public enum a {
    None("none"),
    WapPay("js://wappay"),
    Update("js://update");


    /* renamed from: d, reason: collision with root package name */
    private String f3312d;

    a(String str) {
        this.f3312d = str;
    }

    public static a a(String str) {
        if (TextUtils.isEmpty(str)) {
            return None;
        }
        a aVar = None;
        for (a aVar2 : values()) {
            if (str.startsWith(aVar2.f3312d)) {
                return aVar2;
            }
        }
        return aVar;
    }
}
