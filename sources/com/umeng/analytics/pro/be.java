package com.umeng.analytics.pro;

import com.umeng.analytics.pro.bo;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public class be {

    /* renamed from: a, reason: collision with root package name */
    private final ByteArrayOutputStream f22556a;

    /* renamed from: b, reason: collision with root package name */
    private final cg f22557b;

    /* renamed from: c, reason: collision with root package name */
    private bu f22558c;

    public be() {
        this(new bo.a());
    }

    public byte[] a(av avVar) throws bb {
        this.f22556a.reset();
        avVar.write(this.f22558c);
        return this.f22556a.toByteArray();
    }

    public String b(av avVar) throws bb {
        return new String(a(avVar));
    }

    public be(bw bwVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f22556a = byteArrayOutputStream;
        cg cgVar = new cg(byteArrayOutputStream);
        this.f22557b = cgVar;
        this.f22558c = bwVar.a(cgVar);
    }

    public String a(av avVar, String str) throws bb {
        try {
            return new String(a(avVar), str);
        } catch (UnsupportedEncodingException unused) {
            throw new bb("JVM DOES NOT SUPPORT ENCODING: " + str);
        }
    }
}
