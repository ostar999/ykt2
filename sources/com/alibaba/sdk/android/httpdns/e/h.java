package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.HTTPDNSResult;
import com.alibaba.sdk.android.httpdns.RequestIpType;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.b.b f2742a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.probe.b f38a;

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2743b;

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, com.alibaba.sdk.android.httpdns.b.a> f39a = new ConcurrentHashMap<>();

    /* renamed from: e, reason: collision with root package name */
    private boolean f2744e = false;

    /* renamed from: com.alibaba.sdk.android.httpdns.e.h$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {

        /* renamed from: d, reason: collision with root package name */
        static final /* synthetic */ int[] f2749d;

        static {
            int[] iArr = new int[RequestIpType.values().length];
            f2749d = iArr;
            try {
                iArr[RequestIpType.v4.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2749d[RequestIpType.v6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2749d[RequestIpType.both.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public h(com.alibaba.sdk.android.httpdns.d.d dVar, com.alibaba.sdk.android.httpdns.probe.b bVar) {
        this.f2742a = new com.alibaba.sdk.android.httpdns.b.b(dVar.getContext(), dVar.getAccountId());
        this.f2743b = dVar;
        this.f38a = bVar;
    }

    private void a(String str, RequestIpType requestIpType, String str2, String[] strArr) {
        com.alibaba.sdk.android.httpdns.b.a aVar = this.f39a.get(com.alibaba.sdk.android.httpdns.j.a.a(str, requestIpType, str2));
        if (aVar == null) {
            return;
        }
        aVar.a(strArr);
        if (this.f2744e) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(aVar);
            this.f2742a.b(arrayList);
        }
    }

    private boolean a(String str, ArrayList<String> arrayList) {
        String strD = com.alibaba.sdk.android.httpdns.j.a.d(str);
        return strD != null && arrayList.contains(strD);
    }

    private com.alibaba.sdk.android.httpdns.b.a b(String str, RequestIpType requestIpType, String str2, String str3, String[] strArr, int i2) {
        String strA = com.alibaba.sdk.android.httpdns.j.a.a(str, requestIpType, str3);
        com.alibaba.sdk.android.httpdns.b.a aVar = this.f39a.get(strA);
        if (aVar == null) {
            com.alibaba.sdk.android.httpdns.b.a aVarA = com.alibaba.sdk.android.httpdns.b.a.a(str, requestIpType, str2, str3, strArr, i2);
            this.f39a.put(strA, aVarA);
            return aVarA;
        }
        aVar.a(System.currentTimeMillis());
        aVar.a(strArr);
        aVar.a(i2);
        aVar.a(str2);
        aVar.a(false);
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(boolean z2) {
        List<com.alibaba.sdk.android.httpdns.b.a> listA = this.f2742a.a();
        for (com.alibaba.sdk.android.httpdns.b.a aVar : listA) {
            this.f39a.put(com.alibaba.sdk.android.httpdns.j.a.a(aVar.getHost(), RequestIpType.values()[aVar.getType()], aVar.m31a()), aVar);
        }
        if (z2) {
            this.f2742a.a(listA);
        } else {
            ArrayList arrayList = new ArrayList();
            for (com.alibaba.sdk.android.httpdns.b.a aVar2 : listA) {
                if (aVar2.isExpired()) {
                    arrayList.add(aVar2);
                }
            }
            this.f2742a.a(arrayList);
        }
        for (final com.alibaba.sdk.android.httpdns.b.a aVar3 : listA) {
            if (!aVar3.isExpired() && RequestIpType.values()[aVar3.getType()] == RequestIpType.v4) {
                this.f38a.a(aVar3.getHost(), aVar3.getIps(), new com.alibaba.sdk.android.httpdns.probe.a() { // from class: com.alibaba.sdk.android.httpdns.e.h.1
                    @Override // com.alibaba.sdk.android.httpdns.probe.a
                    public void a(String str, String[] strArr) {
                        h.this.b(str, RequestIpType.v4, aVar3.m31a(), strArr);
                    }
                });
            }
        }
    }

    public HTTPDNSResult a(String str, RequestIpType requestIpType, String str2) {
        int i2 = AnonymousClass3.f2749d[requestIpType.ordinal()];
        if (i2 == 1) {
            com.alibaba.sdk.android.httpdns.b.a aVar = this.f39a.get(com.alibaba.sdk.android.httpdns.j.a.a(str, RequestIpType.v4, str2));
            if (aVar == null) {
                return null;
            }
            return new HTTPDNSResult(str, aVar.getIps(), new String[0], com.alibaba.sdk.android.httpdns.j.a.a(aVar.getExtra()), aVar.isExpired(), aVar.isFromDB());
        }
        if (i2 == 2) {
            com.alibaba.sdk.android.httpdns.b.a aVar2 = this.f39a.get(com.alibaba.sdk.android.httpdns.j.a.a(str, RequestIpType.v6, str2));
            if (aVar2 == null) {
                return null;
            }
            return new HTTPDNSResult(str, new String[0], aVar2.getIps(), com.alibaba.sdk.android.httpdns.j.a.a(aVar2.getExtra()), aVar2.isExpired(), aVar2.isFromDB());
        }
        String strA = com.alibaba.sdk.android.httpdns.j.a.a(str, RequestIpType.v4, str2);
        String strA2 = com.alibaba.sdk.android.httpdns.j.a.a(str, RequestIpType.v6, str2);
        com.alibaba.sdk.android.httpdns.b.a aVar3 = this.f39a.get(strA);
        com.alibaba.sdk.android.httpdns.b.a aVar4 = this.f39a.get(strA2);
        if (aVar3 == null || aVar4 == null) {
            return null;
        }
        if (!com.alibaba.sdk.android.httpdns.j.a.equals(aVar3.getExtra(), aVar4.getExtra())) {
            HttpDnsLog.w("extra is not same for v4 and v6");
        }
        String extra = aVar3.getExtra() != null ? aVar3.getExtra() : aVar4.getExtra();
        return new HTTPDNSResult(str, aVar3.getIps(), aVar4.getIps(), com.alibaba.sdk.android.httpdns.j.a.a(extra), aVar3.isExpired() || aVar4.isExpired(), aVar3.isFromDB() || aVar4.isFromDB());
    }

    public HashMap<String, RequestIpType> a() {
        HashMap<String, RequestIpType> map = new HashMap<>();
        for (com.alibaba.sdk.android.httpdns.b.a aVar : this.f39a.values()) {
            RequestIpType requestIpType = map.get(aVar.getHost());
            if (aVar.m31a() == null) {
                if (requestIpType == null) {
                    requestIpType = RequestIpType.values()[aVar.getType()];
                } else if (requestIpType.ordinal() != aVar.getType()) {
                    requestIpType = RequestIpType.both;
                }
                map.put(aVar.getHost(), requestIpType);
            }
        }
        return map;
    }

    public void a(RequestIpType requestIpType, k kVar) {
        com.alibaba.sdk.android.httpdns.b.a aVarB;
        ArrayList arrayList = new ArrayList();
        for (String str : kVar.b()) {
            int i2 = AnonymousClass3.f2749d[requestIpType.ordinal()];
            if (i2 == 1) {
                aVarB = b(str, RequestIpType.v4, null, null, kVar.m46a(str).getIps(), kVar.m46a(str).a());
            } else if (i2 == 2) {
                aVarB = b(str, RequestIpType.v6, null, null, kVar.m46a(str).getIpv6s(), kVar.m46a(str).a());
            } else if (i2 == 3) {
                arrayList.add(b(str, RequestIpType.v4, null, null, kVar.m46a(str).getIps(), kVar.m46a(str).a()));
                aVarB = b(str, RequestIpType.v6, null, null, kVar.m46a(str).getIpv6s(), kVar.m46a(str).a());
            }
            arrayList.add(aVarB);
        }
        if (this.f2744e) {
            this.f2742a.b(arrayList);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.String r10, com.alibaba.sdk.android.httpdns.RequestIpType r11, java.lang.String r12, java.lang.String r13, com.alibaba.sdk.android.httpdns.e.f r14) {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int[] r1 = com.alibaba.sdk.android.httpdns.e.h.AnonymousClass3.f2749d
            int r11 = r11.ordinal()
            r11 = r1[r11]
            r1 = 1
            if (r11 == r1) goto L52
            r1 = 2
            if (r11 == r1) goto L3f
            r1 = 3
            if (r11 == r1) goto L17
            goto L67
        L17:
            com.alibaba.sdk.android.httpdns.RequestIpType r4 = com.alibaba.sdk.android.httpdns.RequestIpType.v4
            java.lang.String[] r7 = r14.getIps()
            int r8 = r14.a()
            r2 = r9
            r3 = r10
            r5 = r12
            r6 = r13
            com.alibaba.sdk.android.httpdns.b.a r11 = r2.b(r3, r4, r5, r6, r7, r8)
            r0.add(r11)
            com.alibaba.sdk.android.httpdns.RequestIpType r3 = com.alibaba.sdk.android.httpdns.RequestIpType.v6
            java.lang.String[] r6 = r14.b()
            int r7 = r14.a()
            r1 = r9
            r2 = r10
            r4 = r12
            r5 = r13
            com.alibaba.sdk.android.httpdns.b.a r10 = r1.b(r2, r3, r4, r5, r6, r7)
            goto L64
        L3f:
            com.alibaba.sdk.android.httpdns.RequestIpType r3 = com.alibaba.sdk.android.httpdns.RequestIpType.v6
            java.lang.String[] r6 = r14.b()
            int r7 = r14.a()
            r1 = r9
            r2 = r10
            r4 = r12
            r5 = r13
            com.alibaba.sdk.android.httpdns.b.a r10 = r1.b(r2, r3, r4, r5, r6, r7)
            goto L64
        L52:
            com.alibaba.sdk.android.httpdns.RequestIpType r3 = com.alibaba.sdk.android.httpdns.RequestIpType.v4
            java.lang.String[] r6 = r14.getIps()
            int r7 = r14.a()
            r1 = r9
            r2 = r10
            r4 = r12
            r5 = r13
            com.alibaba.sdk.android.httpdns.b.a r10 = r1.b(r2, r3, r4, r5, r6, r7)
        L64:
            r0.add(r10)
        L67:
            boolean r10 = r9.f2744e
            if (r10 == 0) goto L70
            com.alibaba.sdk.android.httpdns.b.b r10 = r9.f2742a
            r10.b(r0)
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.e.h.a(java.lang.String, com.alibaba.sdk.android.httpdns.RequestIpType, java.lang.String, java.lang.String, com.alibaba.sdk.android.httpdns.e.f):void");
    }

    public void a(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            clear();
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.f39a.keySet());
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (a(str, arrayList)) {
                arrayList3.add(this.f39a.remove(str));
            }
        }
        if (arrayList3.size() <= 0 || !this.f2744e) {
            return;
        }
        this.f2742a.a(arrayList3);
    }

    public void b(String str, RequestIpType requestIpType, String str2, String[] strArr) {
        RequestIpType requestIpType2;
        int i2 = AnonymousClass3.f2749d[requestIpType.ordinal()];
        if (i2 == 1) {
            requestIpType2 = RequestIpType.v4;
        } else {
            if (i2 != 2) {
                HttpDnsLog.e("update both is impossible for " + str);
                return;
            }
            requestIpType2 = RequestIpType.v6;
        }
        a(str, requestIpType2, str2, strArr);
    }

    public void clear() {
        if (this.f2744e) {
            this.f2742a.a(new ArrayList(this.f39a.values()));
        }
        this.f39a.clear();
    }

    public void setCachedIPEnabled(boolean z2, final boolean z3) {
        this.f2744e = z2;
        this.f2743b.m35a().execute(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.e.h.2
            @Override // java.lang.Runnable
            public void run() {
                h.this.f(z3);
            }
        });
    }
}
