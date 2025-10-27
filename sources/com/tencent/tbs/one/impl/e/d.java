package com.tencent.tbs.one.impl.e;

import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.e.e;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class d extends com.tencent.tbs.one.impl.a.c<e<com.tencent.tbs.one.impl.common.d>> {

    /* renamed from: e, reason: collision with root package name */
    public boolean f22147e;

    /* renamed from: f, reason: collision with root package name */
    public com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> f22148f;

    /* renamed from: g, reason: collision with root package name */
    public File f22149g;

    public d(boolean z2, com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> bVar, File file) {
        super(com.tencent.tbs.one.impl.common.f.e(file, ".lock"), com.heytap.mcssdk.constant.a.f7153q);
        this.f22147e = z2;
        this.f22148f = bVar;
        this.f22149g = file;
    }

    @Override // com.tencent.tbs.one.impl.a.c, com.tencent.tbs.one.impl.a.b
    public final void a(int i2, String str, Throwable th) throws IOException {
        File file = this.f22149g;
        com.tencent.tbs.one.impl.a.d.c(file);
        f.f(file);
        super.a(i2, str, th);
    }

    @Override // com.tencent.tbs.one.impl.a.c, com.tencent.tbs.one.impl.a.b
    public final void a(e<com.tencent.tbs.one.impl.common.d> eVar) throws IOException {
        f.f(this.f22149g);
        super.a((d) eVar);
    }

    @Override // com.tencent.tbs.one.impl.a.c
    public final void a(Exception exc) throws IOException {
        a(301, "Failed to wait for DEPS installation lock " + this.f22149g, exc);
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        super.b();
        this.f22148f.b();
    }

    @Override // com.tencent.tbs.one.impl.a.c
    public final void c() throws Throwable {
        File file = this.f22149g;
        if (f.g(file)) {
            if (file.exists()) {
                if (!this.f22147e) {
                    try {
                        a(e.a(e.a.EXISTING, com.tencent.tbs.one.impl.common.d.a(file)));
                        return;
                    } catch (TBSOneException unused) {
                    }
                }
                com.tencent.tbs.one.impl.a.d.c(file);
            }
            f.e(file);
        } else if (file.exists()) {
            com.tencent.tbs.one.impl.a.d.c(file);
        }
        this.f22148f.a(new m<e<com.tencent.tbs.one.impl.common.d>>() { // from class: com.tencent.tbs.one.impl.e.d.1
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                d.this.a(i3);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) throws IOException {
                d.this.a(i2, str, th);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final /* bridge */ /* synthetic */ void a(e<com.tencent.tbs.one.impl.common.d> eVar) throws IOException {
                d.this.a(eVar);
            }
        });
    }
}
