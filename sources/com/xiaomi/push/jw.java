package com.xiaomi.push;

import com.xiaomi.push.jx;
import java.io.ByteArrayOutputStream;

/* loaded from: classes6.dex */
public class jw {

    /* renamed from: a, reason: collision with root package name */
    private kb f25491a;

    /* renamed from: a, reason: collision with other field name */
    private final ki f919a;

    /* renamed from: a, reason: collision with other field name */
    private final ByteArrayOutputStream f920a;

    public jw() {
        this(new jx.a());
    }

    public jw(kd kdVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f920a = byteArrayOutputStream;
        ki kiVar = new ki(byteArrayOutputStream);
        this.f919a = kiVar;
        this.f25491a = kdVar.a(kiVar);
    }

    public byte[] a(jq jqVar) {
        this.f920a.reset();
        jqVar.b(this.f25491a);
        return this.f920a.toByteArray();
    }
}
