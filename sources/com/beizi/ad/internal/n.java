package com.beizi.ad.internal;

import com.beizi.ad.internal.utilities.HaoboLog;
import java.util.ArrayList;
import java.util.LinkedList;

/* loaded from: classes2.dex */
public abstract class n implements e {

    /* renamed from: a, reason: collision with root package name */
    private LinkedList<com.beizi.ad.internal.b.a> f4231a;

    /* renamed from: b, reason: collision with root package name */
    private long f4232b = -1;

    /* renamed from: c, reason: collision with root package name */
    private ArrayList<String> f4233c = new ArrayList<>();

    @Override // com.beizi.ad.internal.e
    public long a(long j2) {
        long j3 = this.f4232b;
        if (j3 > 0) {
            return j2 - j3;
        }
        return -1L;
    }

    @Override // com.beizi.ad.internal.e
    public LinkedList<com.beizi.ad.internal.b.a> b() {
        return this.f4231a;
    }

    public abstract void e();

    public void g() {
        this.f4232b = System.currentTimeMillis();
    }

    public void h() {
        if (this.f4233c.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("Mediation Classes: \n");
        for (int size = this.f4233c.size(); size > 0; size--) {
            sb.append(String.format("%d: %s\n", Integer.valueOf(size), this.f4233c.get(size - 1)));
        }
        HaoboLog.i(HaoboLog.mediationLogTag, sb.toString());
        this.f4233c.clear();
    }

    public com.beizi.ad.internal.b.a i() {
        LinkedList<com.beizi.ad.internal.b.a> linkedList = this.f4231a;
        if (linkedList == null || linkedList.getFirst() == null) {
            return null;
        }
        this.f4233c.add(this.f4231a.getFirst().a());
        return this.f4231a.removeFirst();
    }

    public void a(LinkedList<com.beizi.ad.internal.b.a> linkedList) {
        this.f4231a = linkedList;
    }
}
