package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes6.dex */
public final class bp extends m implements Cloneable {

    /* renamed from: b, reason: collision with root package name */
    static ArrayList<bo> f17741b;

    /* renamed from: a, reason: collision with root package name */
    public ArrayList<bo> f17742a = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a((Collection) this.f17742a, 0);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        if (f17741b == null) {
            f17741b = new ArrayList<>();
            f17741b.add(new bo());
        }
        this.f17742a = (ArrayList) kVar.a((k) f17741b, 0, true);
    }
}
