package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.d.d;
import com.alibaba.sdk.android.httpdns.probe.c;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with other field name */
    private List<IPProbeItem> f59a;

    /* renamed from: b, reason: collision with root package name */
    private d f2827b;

    /* renamed from: a, reason: collision with root package name */
    private c.a f2826a = new c.a() { // from class: com.alibaba.sdk.android.httpdns.probe.b.1
        @Override // com.alibaba.sdk.android.httpdns.probe.c.a
        public Socket a() {
            return new Socket();
        }
    };

    /* renamed from: a, reason: collision with other field name */
    private ConcurrentSkipListSet<String> f60a = new ConcurrentSkipListSet<>();

    public b(d dVar) {
        this.f2827b = dVar;
    }

    private IPProbeItem a(String str) {
        List<IPProbeItem> list = this.f59a;
        if (list == null || list.size() <= 0) {
            return null;
        }
        Iterator it = new ArrayList(this.f59a).iterator();
        while (it.hasNext()) {
            IPProbeItem iPProbeItem = (IPProbeItem) it.next();
            if (str.equals(iPProbeItem.getHostName())) {
                return iPProbeItem;
            }
        }
        return null;
    }

    public void a(String str, String[] strArr, final a aVar) {
        IPProbeItem iPProbeItemA;
        if (this.f2827b.m41b() || (iPProbeItemA = a(str)) == null || strArr == null || strArr.length <= 1 || this.f60a.contains(str)) {
            return;
        }
        this.f60a.add(str);
        this.f2827b.m35a().execute(new c(this.f2826a, str, strArr, iPProbeItemA, new a() { // from class: com.alibaba.sdk.android.httpdns.probe.b.2
            @Override // com.alibaba.sdk.android.httpdns.probe.a
            public void a(String str2, String[] strArr2) {
                b.this.f60a.remove(str2);
                a aVar2 = aVar;
                if (aVar2 != null) {
                    aVar2.a(str2, strArr2);
                }
            }
        }));
    }

    public void c(List<IPProbeItem> list) {
        this.f59a = list;
    }
}
