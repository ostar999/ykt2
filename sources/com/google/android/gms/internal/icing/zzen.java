package com.google.android.gms.internal.icing;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzen<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzlw;

    public zzen(Iterator<Map.Entry<K, Object>> it) {
        this.zzlw = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzlw.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzlw.next();
        return next.getValue() instanceof zzei ? new zzek(next) : next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzlw.remove();
    }
}
