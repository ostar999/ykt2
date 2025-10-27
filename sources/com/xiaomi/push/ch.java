package com.xiaomi.push;

/* loaded from: classes6.dex */
class ch implements cp {

    /* renamed from: a, reason: collision with root package name */
    private long f24683a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ cn f263a;

    /* renamed from: a, reason: collision with other field name */
    private String f264a;

    /* renamed from: b, reason: collision with root package name */
    private long f24684b;

    private ch(cn cnVar) {
        this.f263a = cnVar;
    }

    public /* synthetic */ ch(cn cnVar, cb cbVar) {
        this(cnVar);
    }

    public long a() {
        return this.f24683a;
    }

    @Override // com.xiaomi.push.cp
    public void a(String str, long j2, long j3) {
        this.f264a = str;
        this.f24683a = j2;
        this.f24684b = j3;
        this.f263a.f270a.obtainMessage(1).sendToTarget();
    }

    public long b() {
        return this.f24684b;
    }
}
