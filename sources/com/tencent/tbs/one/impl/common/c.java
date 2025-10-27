package com.tencent.tbs.one.impl.common;

import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.a.o;

/* loaded from: classes6.dex */
public class c<T> extends m<T> {
    @Override // com.tencent.tbs.one.impl.a.m
    public void a(final int i2, final int i3) {
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.common.c.1
            @Override // java.lang.Runnable
            public final void run() {
                c.super.a(i2, i3);
            }
        });
    }

    @Override // com.tencent.tbs.one.impl.a.m
    public void a(final int i2, final String str, final Throwable th) {
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.common.c.3
            @Override // java.lang.Runnable
            public final void run() {
                c.super.a(i2, str, th);
            }
        });
    }

    @Override // com.tencent.tbs.one.impl.a.m
    public void a(final T t2) {
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.common.c.2
            @Override // java.lang.Runnable
            public final void run() {
                c.super.a(t2);
            }
        });
    }
}
