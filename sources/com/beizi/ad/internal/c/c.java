package com.beizi.ad.internal.c;

import java.io.File;

/* loaded from: classes2.dex */
class c {

    /* renamed from: a, reason: collision with root package name */
    public final File f4061a;

    /* renamed from: b, reason: collision with root package name */
    public final com.beizi.ad.internal.c.a.c f4062b;

    /* renamed from: c, reason: collision with root package name */
    public final com.beizi.ad.internal.c.a.a f4063c;

    /* renamed from: d, reason: collision with root package name */
    public final com.beizi.ad.internal.c.b.c f4064d;

    public c(File file, com.beizi.ad.internal.c.a.c cVar, com.beizi.ad.internal.c.a.a aVar, com.beizi.ad.internal.c.b.c cVar2) {
        this.f4061a = file;
        this.f4062b = cVar;
        this.f4063c = aVar;
        this.f4064d = cVar2;
    }

    public File a(String str) {
        return new File(this.f4061a, this.f4062b.a(str));
    }
}
