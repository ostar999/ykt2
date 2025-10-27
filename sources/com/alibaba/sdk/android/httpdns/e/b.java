package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.DegradationFilter;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    DegradationFilter f2734a;

    public void a(DegradationFilter degradationFilter) {
        this.f2734a = degradationFilter;
    }

    public boolean a(String str) {
        DegradationFilter degradationFilter = this.f2734a;
        return degradationFilter != null && degradationFilter.shouldDegradeHttpDNS(str);
    }
}
