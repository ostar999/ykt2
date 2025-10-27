package com.alipay.sdk.data;

import android.content.Context;
import java.util.HashMap;
import java.util.concurrent.Callable;

/* loaded from: classes2.dex */
final class d implements Callable<String> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f3260a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ HashMap f3261b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ c f3262c;

    public d(c cVar, Context context, HashMap map) {
        this.f3262c = cVar;
        this.f3260a = context;
        this.f3261b = map;
    }

    private String a() throws Exception {
        return c.a(this.f3260a, this.f3261b);
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        return c.a(this.f3260a, this.f3261b);
    }
}
