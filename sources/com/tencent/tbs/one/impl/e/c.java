package com.tencent.tbs.one.impl.e;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.e.e;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class c extends com.tencent.tbs.one.impl.a.c<e<File>> {

    /* renamed from: e, reason: collision with root package name */
    public d.a f22131e;

    /* renamed from: f, reason: collision with root package name */
    public com.tencent.tbs.one.impl.a.b<e<File>> f22132f;

    /* renamed from: g, reason: collision with root package name */
    public File f22133g;

    /* renamed from: h, reason: collision with root package name */
    public Context f22134h;

    public c(Context context, d.a aVar, com.tencent.tbs.one.impl.a.b<e<File>> bVar, File file, int i2) {
        super(com.tencent.tbs.one.impl.common.f.e(file, ".lock"), i2);
        this.f22131e = aVar;
        this.f22132f = bVar;
        this.f22133g = file;
        this.f22134h = context;
    }

    @Override // com.tencent.tbs.one.impl.a.c, com.tencent.tbs.one.impl.a.b
    public final void a(int i2, String str, Throwable th) throws IOException {
        File file = this.f22133g;
        com.tencent.tbs.one.impl.a.d.c(file);
        f.f(file);
        super.a(i2, str, th);
    }

    @Override // com.tencent.tbs.one.impl.a.c, com.tencent.tbs.one.impl.a.b
    public final void a(e<File> eVar) throws IOException {
        com.tencent.tbs.one.impl.a.g.a("ExclusiveComponentInstallationJob finish try to doDex2oat", new Object[0]);
        File file = eVar.f22177b;
        try {
            String str = this.f22131e.f21992a;
            String[] strArr = com.tencent.tbs.one.impl.common.e.a(new File(file, "MANIFEST")).f22000c;
            if (strArr != null && strArr.length > 0 && !TextUtils.isEmpty(strArr[0])) {
                File[] fileArr = {new File(file, strArr[0])};
                com.tencent.tbs.one.impl.a.g.a("do dexopt for component %s,entryClass=%s,installDir=%s", str, fileArr[0], file);
                com.tencent.tbs.one.impl.c.a.c.a(this.f22134h, fileArr, file.getAbsolutePath(), file.getAbsolutePath(), null, false, null, false);
            }
        } catch (Throwable th) {
            com.tencent.tbs.one.impl.a.g.c("exception occured in dex2oat,exception=%s", Log.getStackTraceString(th));
        }
        f.f(this.f22133g);
        super.a((c) eVar);
    }

    @Override // com.tencent.tbs.one.impl.a.c
    public final void a(Exception exc) throws IOException {
        a(311, "Failed to wait for component installation lock " + this.f22133g, exc);
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        super.b();
        this.f22132f.b();
    }

    @Override // com.tencent.tbs.one.impl.a.c
    public final void c() throws IOException {
        File file = this.f22133g;
        if (f.g(file)) {
            if (file.exists()) {
                a(e.a(e.a.EXISTING, file));
                return;
            }
            f.e(file);
        } else if (file.exists()) {
            com.tencent.tbs.one.impl.a.d.c(file);
        }
        this.f22132f.a(new m<e<File>>() { // from class: com.tencent.tbs.one.impl.e.c.1
            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                c.this.a(i3);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str, Throwable th) throws IOException {
                c.this.a(i2, str, th);
            }

            @Override // com.tencent.tbs.one.impl.a.m
            public final /* bridge */ /* synthetic */ void a(e<File> eVar) throws IOException {
                c.this.a(eVar);
            }
        });
    }
}
