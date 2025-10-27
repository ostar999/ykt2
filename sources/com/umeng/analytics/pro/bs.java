package com.umeng.analytics.pro;

/* loaded from: classes6.dex */
public final class bs {

    /* renamed from: a, reason: collision with root package name */
    public final String f22632a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f22633b;

    /* renamed from: c, reason: collision with root package name */
    public final int f22634c;

    public bs() {
        this("", (byte) 0, 0);
    }

    public boolean a(bs bsVar) {
        return this.f22632a.equals(bsVar.f22632a) && this.f22633b == bsVar.f22633b && this.f22634c == bsVar.f22634c;
    }

    public boolean equals(Object obj) {
        if (obj instanceof bs) {
            return a((bs) obj);
        }
        return false;
    }

    public String toString() {
        return "<TMessage name:'" + this.f22632a + "' type: " + ((int) this.f22633b) + " seqid:" + this.f22634c + ">";
    }

    public bs(String str, byte b3, int i2) {
        this.f22632a = str;
        this.f22633b = b3;
        this.f22634c = i2;
    }
}
