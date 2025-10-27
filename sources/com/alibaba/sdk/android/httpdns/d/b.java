package com.alibaba.sdk.android.httpdns.d;

import com.alibaba.sdk.android.httpdns.RequestIpType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with other field name */
    private HashSet<String> f13a = new HashSet<>();

    /* renamed from: a, reason: collision with root package name */
    private HashMap<String, CountDownLatch> f2710a = new HashMap<>();

    public synchronized void a(String str, RequestIpType requestIpType, String str2) {
        String strB = com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType, str2);
        this.f13a.remove(strB);
        CountDownLatch countDownLatchRemove = this.f2710a.remove(strB);
        if (countDownLatchRemove != null) {
            countDownLatchRemove.countDown();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized boolean m32a(String str, RequestIpType requestIpType, String str2) {
        boolean z2;
        String strB = com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType, str2);
        z2 = !this.f13a.contains(strB);
        if (z2) {
            this.f13a.add(strB);
            this.f2710a.put(strB, new CountDownLatch(1));
        }
        return z2;
    }

    public boolean a(String str, RequestIpType requestIpType, String str2, long j2, TimeUnit timeUnit) {
        CountDownLatch countDownLatch = this.f2710a.get(com.alibaba.sdk.android.httpdns.j.a.b(str, requestIpType, str2));
        if (countDownLatch != null) {
            return countDownLatch.await(j2, timeUnit);
        }
        return true;
    }
}
