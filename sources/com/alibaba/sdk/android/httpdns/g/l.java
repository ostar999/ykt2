package com.alibaba.sdk.android.httpdns.g;

/* loaded from: classes2.dex */
public class l<T> extends h<T> {

    /* renamed from: i, reason: collision with root package name */
    private int f2795i;

    public l(c<T> cVar, int i2) {
        super(cVar);
        this.f2795i = i2;
    }

    @Override // com.alibaba.sdk.android.httpdns.g.h, com.alibaba.sdk.android.httpdns.g.c
    /* renamed from: a */
    public T mo50a() {
        while (true) {
            try {
                return (T) super.mo50a();
            } catch (Throwable th) {
                if (this.f2795i <= 0) {
                    throw th;
                }
                this.f2795i--;
            }
        }
    }
}
