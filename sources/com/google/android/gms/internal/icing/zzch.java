package com.google.android.gms.internal.icing;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
final class zzch {
    private final ConcurrentHashMap<zzck, List<Throwable>> zzeb = new ConcurrentHashMap<>(16, 0.75f, 10);
    private final ReferenceQueue<Throwable> zzec = new ReferenceQueue<>();

    public final List<Throwable> zza(Throwable th, boolean z2) {
        Reference<? extends Throwable> referencePoll = this.zzec.poll();
        while (referencePoll != null) {
            this.zzeb.remove(referencePoll);
            referencePoll = this.zzec.poll();
        }
        List<Throwable> list = this.zzeb.get(new zzck(th, null));
        if (list != null) {
            return list;
        }
        Vector vector = new Vector(2);
        List<Throwable> listPutIfAbsent = this.zzeb.putIfAbsent(new zzck(th, this.zzec), vector);
        return listPutIfAbsent == null ? vector : listPutIfAbsent;
    }
}
