package com.vivo.push.b;

/* loaded from: classes6.dex */
public class s extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f24272a;

    /* renamed from: b, reason: collision with root package name */
    private int f24273b;

    public s(int i2) {
        super(i2);
        this.f24272a = null;
        this.f24273b = 0;
    }

    @Override // com.vivo.push.o
    public void c(com.vivo.push.a aVar) {
        aVar.a("req_id", this.f24272a);
        aVar.a("status_msg_code", this.f24273b);
    }

    @Override // com.vivo.push.o
    public void d(com.vivo.push.a aVar) {
        this.f24272a = aVar.a("req_id");
        this.f24273b = aVar.b("status_msg_code", this.f24273b);
    }

    public final String g() {
        return this.f24272a;
    }

    public final int h() {
        return this.f24273b;
    }

    @Override // com.vivo.push.o
    public String toString() {
        return "OnReceiveCommand";
    }
}
