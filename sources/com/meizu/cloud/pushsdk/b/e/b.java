package com.meizu.cloud.pushsdk.b.e;

import com.meizu.cloud.pushsdk.b.c.g;
import com.meizu.cloud.pushsdk.b.c.j;
import com.meizu.cloud.pushsdk.b.g.e;
import com.meizu.cloud.pushsdk.b.g.f;
import com.meizu.cloud.pushsdk.b.g.k;
import java.io.IOException;

/* loaded from: classes4.dex */
public class b extends j {

    /* renamed from: a, reason: collision with root package name */
    private final j f9168a;

    /* renamed from: b, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.b.g.b f9169b;

    /* renamed from: c, reason: collision with root package name */
    private d f9170c;

    public b(j jVar, com.meizu.cloud.pushsdk.b.d.a aVar) {
        this.f9168a = jVar;
        if (aVar != null) {
            this.f9170c = new d(aVar);
        }
    }

    private k a(k kVar) {
        return new e(kVar) { // from class: com.meizu.cloud.pushsdk.b.e.b.1

            /* renamed from: a, reason: collision with root package name */
            long f9171a = 0;

            /* renamed from: b, reason: collision with root package name */
            long f9172b = 0;

            @Override // com.meizu.cloud.pushsdk.b.g.e, com.meizu.cloud.pushsdk.b.g.k
            public void a(com.meizu.cloud.pushsdk.b.g.a aVar, long j2) throws IOException {
                super.a(aVar, j2);
                if (this.f9172b == 0) {
                    this.f9172b = b.this.b();
                }
                this.f9171a += j2;
                if (b.this.f9170c != null) {
                    b.this.f9170c.obtainMessage(1, new com.meizu.cloud.pushsdk.b.f.a(this.f9171a, this.f9172b)).sendToTarget();
                }
            }
        };
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public g a() {
        return this.f9168a.a();
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException {
        if (this.f9169b == null) {
            this.f9169b = f.a(a((k) bVar));
        }
        this.f9168a.a(this.f9169b);
        this.f9169b.flush();
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public long b() throws IOException {
        return this.f9168a.b();
    }
}
