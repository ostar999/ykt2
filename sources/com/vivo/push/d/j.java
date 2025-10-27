package com.vivo.push.d;

import java.util.List;

/* loaded from: classes6.dex */
final class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24325a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f24326b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f24327c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f24328d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ h f24329e;

    public j(h hVar, int i2, List list, List list2, String str) {
        this.f24329e = hVar;
        this.f24325a = i2;
        this.f24326b = list;
        this.f24327c = list2;
        this.f24328d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        h hVar = this.f24329e;
        ((z) hVar).f24347b.onDelAlias(((com.vivo.push.l) hVar).f24388a, this.f24325a, this.f24326b, this.f24327c, this.f24328d);
    }
}
