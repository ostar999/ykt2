package com.alibaba.sdk.android.httpdns.h;

import com.alibaba.sdk.android.httpdns.g.j;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private InterfaceC0021a f2796a;

    /* renamed from: a, reason: collision with other field name */
    private c f55a = new c();

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2797b;

    /* renamed from: com.alibaba.sdk.android.httpdns.h.a$a, reason: collision with other inner class name */
    public interface InterfaceC0021a {
        void e(boolean z2);
    }

    public a(com.alibaba.sdk.android.httpdns.d.d dVar, InterfaceC0021a interfaceC0021a) {
        this.f2797b = dVar;
        this.f2796a = interfaceC0021a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String[] strArr, int[] iArr) {
        InterfaceC0021a interfaceC0021a;
        boolean z2 = !com.alibaba.sdk.android.httpdns.j.a.a(this.f2797b.m39b(), str);
        if (!this.f2797b.a(str, strArr, iArr) || (interfaceC0021a = this.f2796a) == null) {
            return;
        }
        interfaceC0021a.e(z2);
    }

    public void b(final String str, boolean z2) {
        if (!z2 && com.alibaba.sdk.android.httpdns.j.a.a(this.f2797b.m39b(), str)) {
            HttpDnsLog.d("region " + str + " is same, do not update serverIps");
            return;
        }
        String[] strArrM53a = this.f55a.m53a(str);
        int[] iArrA = this.f55a.a(str);
        if (strArrM53a != null) {
            a(str, strArrM53a, iArrA);
        } else {
            f.a(this.f2797b.a(), str, new j<e>() { // from class: com.alibaba.sdk.android.httpdns.h.a.1
                @Override // com.alibaba.sdk.android.httpdns.g.j
                public void a(e eVar) {
                    if (!eVar.f()) {
                        HttpDnsLog.i("disable service by server response " + eVar.toString());
                        a.this.f2797b.setEnabled(false);
                        return;
                    }
                    if (!a.this.f2797b.isEnabled()) {
                        a.this.f2797b.setEnabled(true);
                    }
                    if (eVar.m54a() != null) {
                        a.this.a(str, eVar.m54a(), eVar.a());
                        a.this.f55a.b(str, eVar.m54a(), eVar.a());
                    }
                }

                @Override // com.alibaba.sdk.android.httpdns.g.j
                public void b(Throwable th) {
                    HttpDnsLog.w("update server ips fail", th);
                }
            });
        }
    }

    public void f() {
        b(this.f2797b.m39b(), true);
    }
}
