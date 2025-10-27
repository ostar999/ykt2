package com.meizu.cloud.pushsdk.b.c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public class b extends j {

    /* renamed from: a, reason: collision with root package name */
    private static final g f9071a = g.a("application/x-www-form-urlencoded");

    /* renamed from: b, reason: collision with root package name */
    private final List<String> f9072b;

    /* renamed from: c, reason: collision with root package name */
    private final List<String> f9073c;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final List<String> f9074a = new ArrayList();

        /* renamed from: b, reason: collision with root package name */
        private final List<String> f9075b = new ArrayList();

        public a a(String str, String str2) {
            this.f9074a.add(f.a(str, HttpUrl.FORM_ENCODE_SET, false, false, true, true));
            this.f9075b.add(f.a(str2, HttpUrl.FORM_ENCODE_SET, false, false, true, true));
            return this;
        }

        public b a() {
            return new b(this.f9074a, this.f9075b);
        }

        public a b(String str, String str2) {
            this.f9074a.add(f.a(str, HttpUrl.FORM_ENCODE_SET, true, false, true, true));
            this.f9075b.add(f.a(str2, HttpUrl.FORM_ENCODE_SET, true, false, true, true));
            return this;
        }
    }

    private b(List<String> list, List<String> list2) {
        this.f9072b = m.a(list);
        this.f9073c = m.a(list2);
    }

    private long a(com.meizu.cloud.pushsdk.b.g.b bVar, boolean z2) {
        com.meizu.cloud.pushsdk.b.g.a aVar = z2 ? new com.meizu.cloud.pushsdk.b.g.a() : bVar.b();
        int size = this.f9072b.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                aVar.b(38);
            }
            aVar.b(this.f9072b.get(i2));
            aVar.b(61);
            aVar.b(this.f9073c.get(i2));
        }
        if (!z2) {
            return 0L;
        }
        long jA = aVar.a();
        aVar.j();
        return jA;
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public g a() {
        return f9071a;
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException {
        a(bVar, false);
    }

    @Override // com.meizu.cloud.pushsdk.b.c.j
    public long b() {
        return a((com.meizu.cloud.pushsdk.b.g.b) null, true);
    }
}
