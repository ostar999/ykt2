package com.vivo.push.e;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static String[] f24367a = {"com.vivo.pushservice", "com.vivo.pushdemo.test", "com.vivo.sdk.test", "com.vivo.hybrid"};

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<String> f24368b;

    /* renamed from: com.vivo.push.e.a$a, reason: collision with other inner class name */
    public static class C0404a {

        /* renamed from: a, reason: collision with root package name */
        private static a f24374a = new a(0);
    }

    public /* synthetic */ a(byte b3) {
        this();
    }

    public static a a() {
        return C0404a.f24374a;
    }

    public final ArrayList<String> b() {
        return new ArrayList<>(this.f24368b);
    }

    public final boolean c() {
        ArrayList<String> arrayList = this.f24368b;
        return (arrayList == null || arrayList.size() == 0) ? false : true;
    }

    private a() {
        this.f24368b = null;
        this.f24368b = new ArrayList<>();
    }
}
