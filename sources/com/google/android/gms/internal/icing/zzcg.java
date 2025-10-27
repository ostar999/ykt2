package com.google.android.gms.internal.icing;

import java.io.Serializable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes3.dex */
final class zzcg<T> implements zzcc<T>, Serializable {

    @NullableDecl
    private final T zzea;

    public zzcg(@NullableDecl T t2) {
        this.zzea = t2;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof zzcg)) {
            return false;
        }
        T t2 = this.zzea;
        T t3 = ((zzcg) obj).zzea;
        if (t2 != t3) {
            return t2 != null && t2.equals(t3);
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final T get() {
        return this.zzea;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzea});
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzea);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 22);
        sb.append("Suppliers.ofInstance(");
        sb.append(strValueOf);
        sb.append(")");
        return sb.toString();
    }
}
