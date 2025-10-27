package com.xiaomi.push;

import java.net.InetSocketAddress;

/* loaded from: classes6.dex */
public final class da {

    /* renamed from: a, reason: collision with root package name */
    private int f24716a;

    /* renamed from: a, reason: collision with other field name */
    private String f294a;

    public da(String str, int i2) {
        this.f294a = str;
        this.f24716a = i2;
    }

    public static da a(String str, int i2) throws NumberFormatException {
        int iLastIndexOf = str.lastIndexOf(":");
        if (iLastIndexOf != -1) {
            String strSubstring = str.substring(0, iLastIndexOf);
            try {
                int i3 = Integer.parseInt(str.substring(iLastIndexOf + 1));
                if (i3 > 0) {
                    i2 = i3;
                }
            } catch (NumberFormatException unused) {
            }
            str = strSubstring;
        }
        return new da(str, i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static InetSocketAddress m311a(String str, int i2) throws NumberFormatException {
        da daVarA = a(str, i2);
        return new InetSocketAddress(daVarA.m312a(), daVarA.a());
    }

    public int a() {
        return this.f24716a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m312a() {
        return this.f294a;
    }

    public String toString() {
        if (this.f24716a <= 0) {
            return this.f294a;
        }
        return this.f294a + ":" + this.f24716a;
    }
}
