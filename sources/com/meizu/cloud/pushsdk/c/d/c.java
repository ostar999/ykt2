package com.meizu.cloud.pushsdk.c.d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class c implements d {

    /* renamed from: a, reason: collision with root package name */
    private int f9374a;

    /* renamed from: b, reason: collision with root package name */
    private AtomicLong f9375b = new AtomicLong(0);

    /* renamed from: c, reason: collision with root package name */
    private Map<Long, byte[]> f9376c = new ConcurrentHashMap();

    /* renamed from: d, reason: collision with root package name */
    private List<Long> f9377d = new CopyOnWriteArrayList();

    public c(int i2) {
        this.f9374a = i2;
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public void a(com.meizu.cloud.pushsdk.c.a.a aVar) throws IOException {
        b(aVar);
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public boolean a() {
        return true;
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public boolean a(long j2) {
        return this.f9377d.remove(Long.valueOf(j2)) && this.f9376c.remove(Long.valueOf(j2)) != null;
    }

    public long b(com.meizu.cloud.pushsdk.c.a.a aVar) throws IOException {
        byte[] bArrA = a.a((Map<String, String>) aVar.a());
        long andIncrement = this.f9375b.getAndIncrement();
        this.f9377d.add(Long.valueOf(andIncrement));
        this.f9376c.put(Long.valueOf(andIncrement), bArrA);
        return andIncrement;
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public long c() {
        return this.f9377d.size();
    }

    @Override // com.meizu.cloud.pushsdk.c.d.d
    public com.meizu.cloud.pushsdk.c.b.b d() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();
        int iC = (int) c();
        int i2 = this.f9374a;
        if (iC > i2) {
            iC = i2;
        }
        for (int i3 = 0; i3 < iC; i3++) {
            Long l2 = this.f9377d.get(i3);
            if (l2 != null) {
                com.meizu.cloud.pushsdk.c.a.c cVar = new com.meizu.cloud.pushsdk.c.a.c();
                cVar.a(a.a(this.f9376c.get(l2)));
                com.meizu.cloud.pushsdk.c.f.c.c("MemoryStore", " current key " + l2 + " payload " + cVar, new Object[0]);
                linkedList.add(l2);
                arrayList.add(cVar);
            }
        }
        return new com.meizu.cloud.pushsdk.c.b.b(arrayList, linkedList);
    }
}
