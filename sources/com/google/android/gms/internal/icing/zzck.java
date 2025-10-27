package com.google.android.gms.internal.icing;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
final class zzck extends WeakReference<Throwable> {
    private final int zzef;

    public zzck(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, referenceQueue);
        if (th == null) {
            throw new NullPointerException("The referent cannot be null");
        }
        this.zzef = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == zzck.class) {
            if (this == obj) {
                return true;
            }
            zzck zzckVar = (zzck) obj;
            if (this.zzef == zzckVar.zzef && get() == zzckVar.get()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zzef;
    }
}
