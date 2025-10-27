package com.google.android.gms.internal.icing;

import java.util.Map;

/* loaded from: classes3.dex */
final class zzge implements Comparable<zzge>, Map.Entry<Object, Object> {
    private Object value;
    private final /* synthetic */ zzfz zznx;
    private final Object zzob;

    public zzge(zzfz zzfzVar, Map.Entry<Object, Object> entry) {
        this(zzfzVar, (Comparable) entry.getKey(), entry.getValue());
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzge zzgeVar) {
        return ((Comparable) getKey()).compareTo((Comparable) zzgeVar.getKey());
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return equals(this.zzob, entry.getKey()) && equals(this.value, entry.getValue());
    }

    @Override // java.util.Map.Entry
    public final /* synthetic */ Object getKey() {
        return this.zzob;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Object obj = this.zzob;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.value;
        return iHashCode ^ (obj2 != null ? obj2.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        this.zznx.zzdg();
        Object obj2 = this.value;
        this.value = obj;
        return obj2;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzob);
        String strValueOf2 = String.valueOf(this.value);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 1 + strValueOf2.length());
        sb.append(strValueOf);
        sb.append("=");
        sb.append(strValueOf2);
        return sb.toString();
    }

    public zzge(zzfz zzfzVar, Object obj, Object obj2) {
        this.zznx = zzfzVar;
        this.zzob = obj;
        this.value = obj2;
    }

    private static boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }
}
