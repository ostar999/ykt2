package com.xiaomi.push;

/* loaded from: classes6.dex */
public class jy {

    /* renamed from: a, reason: collision with root package name */
    public final byte f25502a;

    /* renamed from: a, reason: collision with other field name */
    public final String f927a;

    /* renamed from: a, reason: collision with other field name */
    public final short f928a;

    public jy() {
        this("", (byte) 0, (short) 0);
    }

    public jy(String str, byte b3, short s2) {
        this.f927a = str;
        this.f25502a = b3;
        this.f928a = s2;
    }

    public String toString() {
        return "<TField name:'" + this.f927a + "' type:" + ((int) this.f25502a) + " field-id:" + ((int) this.f928a) + ">";
    }
}
