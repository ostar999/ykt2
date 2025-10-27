package com.xiaomi.push;

import java.util.Map;

/* loaded from: classes6.dex */
public class gd implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    public static String f24924a = "wcc-ml-test10.bj";

    /* renamed from: b, reason: collision with root package name */
    public static final String f24925b = ae.f24594a;

    /* renamed from: c, reason: collision with root package name */
    public static String f24926c;

    /* renamed from: a, reason: collision with other field name */
    private int f485a;

    /* renamed from: a, reason: collision with other field name */
    private gg f486a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f487a = gc.f470a;

    /* renamed from: b, reason: collision with other field name */
    private boolean f488b = true;

    /* renamed from: d, reason: collision with root package name */
    private String f24927d;

    /* renamed from: e, reason: collision with root package name */
    private String f24928e;

    /* renamed from: f, reason: collision with root package name */
    private String f24929f;

    public gd(Map<String, Integer> map, int i2, String str, gg ggVar) {
        a(map, i2, str, ggVar);
    }

    public static final String a() {
        String str = f24926c;
        return str != null ? str : ab.m193a() ? "sandbox.xmpush.xiaomi.com" : ab.b() ? f24925b : "app.chat.xiaomi.net";
    }

    public static final void a(String str) {
        f24926c = str;
    }

    private void a(Map<String, Integer> map, int i2, String str, gg ggVar) {
        this.f485a = i2;
        this.f24927d = str;
        this.f486a = ggVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m457a() {
        return this.f485a;
    }

    public void a(boolean z2) {
        this.f487a = z2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m458a() {
        return this.f487a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] mo459a() {
        return null;
    }

    public String b() {
        return this.f24929f;
    }

    public void b(String str) {
        this.f24929f = str;
    }

    public String c() {
        if (this.f24928e == null) {
            this.f24928e = a();
        }
        return this.f24928e;
    }

    public void c(String str) {
        this.f24928e = str;
    }
}
