package com.vivo.push;

/* loaded from: classes6.dex */
final class i implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e f24382a;

    public i(e eVar) {
        this.f24382a = eVar;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        if (i2 != 0) {
            this.f24382a.f24358k = null;
            this.f24382a.f24357j.b("APP_TOKEN");
        } else {
            this.f24382a.f24358k = "";
            this.f24382a.f24357j.a("APP_TOKEN", "");
            this.f24382a.m();
            this.f24382a.f24357j.b("APP_TAGS");
        }
    }
}
