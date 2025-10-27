package com.google.android.gms.internal.icing;

import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes3.dex */
final class zzce<T> implements zzcc<T>, Serializable {

    @NullableDecl
    private transient T value;
    private final zzcc<T> zzdw;
    private volatile transient boolean zzdx;

    public zzce(zzcc<T> zzccVar) {
        this.zzdw = (zzcc) zzca.checkNotNull(zzccVar);
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final T get() {
        if (!this.zzdx) {
            synchronized (this) {
                if (!this.zzdx) {
                    T t2 = this.zzdw.get();
                    this.value = t2;
                    this.zzdx = true;
                    return t2;
                }
            }
        }
        return this.value;
    }

    public final String toString() {
        Object string;
        if (this.zzdx) {
            String strValueOf = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 25);
            sb.append("<supplier that returned ");
            sb.append(strValueOf);
            sb.append(">");
            string = sb.toString();
        } else {
            string = this.zzdw;
        }
        String strValueOf2 = String.valueOf(string);
        StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 19);
        sb2.append("Suppliers.memoize(");
        sb2.append(strValueOf2);
        sb2.append(")");
        return sb2.toString();
    }
}
