package com.aliyun.sls.android.producer.utils;

import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public final /* synthetic */ class a implements Runnable {

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ Object[] f3573c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ Method f3574d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ Object f3575e;

    public /* synthetic */ a(Object[] objArr, Method method, Object obj) {
        this.f3573c = objArr;
        this.f3574d = method;
        this.f3575e = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ContextUtils.lambda$getActivityThread$0(this.f3573c, this.f3574d, this.f3575e);
    }
}
