package com.xiaomi.push;

import java.io.ByteArrayOutputStream;

/* loaded from: classes6.dex */
public class jt extends ByteArrayOutputStream {
    public jt() {
    }

    public jt(int i2) {
        super(i2);
    }

    public int a() {
        return ((ByteArrayOutputStream) this).count;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m658a() {
        return ((ByteArrayOutputStream) this).buf;
    }
}
