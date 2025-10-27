package com.google.android.gms.internal.icing;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes3.dex */
final class zzbz<T> extends zzbx<T> {
    private final T zzdv;

    public zzbz(T t2) {
        this.zzdv = t2;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof zzbz) {
            return this.zzdv.equals(((zzbz) obj).zzdv);
        }
        return false;
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final T get() {
        return this.zzdv;
    }

    public final int hashCode() {
        return this.zzdv.hashCode() + 1502476572;
    }

    @Override // com.google.android.gms.internal.icing.zzbx
    public final boolean isPresent() {
        return true;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzdv);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 13);
        sb.append("Optional.of(");
        sb.append(strValueOf);
        sb.append(")");
        return sb.toString();
    }
}
