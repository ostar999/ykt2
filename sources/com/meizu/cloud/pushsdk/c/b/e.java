package com.meizu.cloud.pushsdk.c.b;

import com.meizu.cloud.pushsdk.b.c.i;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private final boolean f9334a;

    /* renamed from: b, reason: collision with root package name */
    private final i f9335b;

    /* renamed from: c, reason: collision with root package name */
    private final LinkedList<Long> f9336c;

    public e(boolean z2, i iVar, LinkedList<Long> linkedList) {
        this.f9334a = z2;
        this.f9335b = iVar;
        this.f9336c = linkedList;
    }

    public i a() {
        return this.f9335b;
    }

    public LinkedList<Long> b() {
        return this.f9336c;
    }

    public boolean c() {
        return this.f9334a;
    }
}
