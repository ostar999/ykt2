package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes6.dex */
public final class bm extends m implements Cloneable {

    /* renamed from: c, reason: collision with root package name */
    static ArrayList<String> f17708c;

    /* renamed from: a, reason: collision with root package name */
    public String f17709a = "";

    /* renamed from: b, reason: collision with root package name */
    public ArrayList<String> f17710b = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f17709a, 0);
        ArrayList<String> arrayList = this.f17710b;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 1);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f17709a = kVar.b(0, true);
        if (f17708c == null) {
            ArrayList<String> arrayList = new ArrayList<>();
            f17708c = arrayList;
            arrayList.add("");
        }
        this.f17710b = (ArrayList) kVar.a((k) f17708c, 1, false);
    }
}
