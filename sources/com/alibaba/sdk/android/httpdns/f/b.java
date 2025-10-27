package com.alibaba.sdk.android.httpdns.f;

import android.content.Context;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: d, reason: collision with root package name */
    private String f2780d;

    /* renamed from: i, reason: collision with root package name */
    private boolean f2781i = true;

    /* renamed from: b, reason: collision with root package name */
    private static a f2778b = new a();

    /* renamed from: a, reason: collision with root package name */
    private static StringBuilder f2777a = new StringBuilder();

    /* renamed from: e, reason: collision with root package name */
    private static HashMap<String, Boolean> f2779e = new HashMap<>();

    private b(String str) {
        this.f2780d = str;
    }

    public static b a() {
        return new b(f2777a.toString());
    }

    public static b a(String str) {
        return new b(str);
    }

    public static void a(Context context) {
    }

    public static void a(String str, boolean z2) {
    }

    public void a(String str, String str2, String str3, int i2) {
    }

    public void a(String str, String str2, String str3, int i2, int i3) {
    }

    public void g(String str) {
        this.f2780d = str;
        StringBuilder sb = f2777a;
        sb.append("&&");
        sb.append(str);
    }

    public void h(String str) {
    }
}
