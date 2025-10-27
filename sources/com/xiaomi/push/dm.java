package com.xiaomi.push;

import com.xiaomi.push.dl;
import com.xiaomi.push.dl.c;
import java.io.File;
import java.util.Date;

/* loaded from: classes6.dex */
class dm extends dl.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24736a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ dl f322a;

    /* renamed from: a, reason: collision with other field name */
    File f323a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f324a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Date f325a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f326a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24737b;

    /* renamed from: b, reason: collision with other field name */
    final /* synthetic */ Date f327b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dm(dl dlVar, int i2, Date date, Date date2, String str, String str2, boolean z2) {
        super();
        this.f322a = dlVar;
        this.f24736a = i2;
        this.f325a = date;
        this.f327b = date2;
        this.f324a = str;
        this.f24737b = str2;
        this.f326a = z2;
    }

    @Override // com.xiaomi.push.dl.b, com.xiaomi.push.al.b
    public void b() {
        if (aa.d()) {
            try {
                File file = new File(this.f322a.f315a.getExternalFilesDir(null) + "/.logcache");
                file.mkdirs();
                if (file.isDirectory()) {
                    dk dkVar = new dk();
                    dkVar.a(this.f24736a);
                    this.f323a = dkVar.a(this.f322a.f315a, this.f325a, this.f327b, file);
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    @Override // com.xiaomi.push.al.b
    /* renamed from: c */
    public void mo328c() {
        File file = this.f323a;
        if (file != null && file.exists()) {
            this.f322a.f316a.add(this.f322a.new c(this.f324a, this.f24737b, this.f323a, this.f326a));
        }
        this.f322a.a(0L);
    }
}
