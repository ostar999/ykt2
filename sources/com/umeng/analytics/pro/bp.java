package com.umeng.analytics.pro;

/* loaded from: classes6.dex */
public class bp {

    /* renamed from: a, reason: collision with root package name */
    public final String f22624a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f22625b;

    /* renamed from: c, reason: collision with root package name */
    public final short f22626c;

    public bp() {
        this("", (byte) 0, (short) 0);
    }

    public boolean a(bp bpVar) {
        return this.f22625b == bpVar.f22625b && this.f22626c == bpVar.f22626c;
    }

    public String toString() {
        return "<TField name:'" + this.f22624a + "' type:" + ((int) this.f22625b) + " field-id:" + ((int) this.f22626c) + ">";
    }

    public bp(String str, byte b3, short s2) {
        this.f22624a = str;
        this.f22625b = b3;
        this.f22626c = s2;
    }
}
