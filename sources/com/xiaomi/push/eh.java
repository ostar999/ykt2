package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class eh extends ei {

    /* renamed from: a, reason: collision with root package name */
    public static String f24753a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f24754b = "";

    public eh(Context context, int i2) {
        super(context, i2);
    }

    private String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] strArrSplit = str2.split(",");
        if (strArrSplit.length <= 10) {
            return str2;
        }
        int length = strArrSplit.length;
        while (true) {
            length--;
            if (length < strArrSplit.length - 10) {
                return str;
            }
            str = str + strArrSplit[length];
        }
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 12;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.BroadcastAction;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        String str;
        if (TextUtils.isEmpty(f24753a)) {
            str = "";
        } else {
            str = "" + a(dy.f337a, f24753a);
            f24753a = "";
        }
        if (TextUtils.isEmpty(f24754b)) {
            return str;
        }
        String str2 = str + a(dy.f24750b, f24754b);
        f24754b = "";
        return str2;
    }
}
