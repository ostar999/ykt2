package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzcr extends zzcp<Boolean> implements zzee<Boolean>, zzfq, RandomAccess {
    private static final zzcr zzge;
    private int size;
    private boolean[] zzgf;

    static {
        zzcr zzcrVar = new zzcr(new boolean[0], 0);
        zzge = zzcrVar;
        zzcrVar.zzai();
    }

    public zzcr() {
        this(new boolean[10], 0);
    }

    public static zzcr zzak() {
        return zzge;
    }

    private final void zzh(int i2) {
        if (i2 < 0 || i2 >= this.size) {
            throw new IndexOutOfBoundsException(zzi(i2));
        }
    }

    private final String zzi(int i2) {
        int i3 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i2);
        sb.append(", Size:");
        sb.append(i3);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i2, Object obj) {
        int i3;
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        if (i2 < 0 || i2 > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i2));
        }
        boolean[] zArr = this.zzgf;
        if (i3 < zArr.length) {
            System.arraycopy(zArr, i2, zArr, i2 + 1, i3 - i2);
        } else {
            boolean[] zArr2 = new boolean[((i3 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i2);
            System.arraycopy(this.zzgf, i2, zArr2, i2 + 1, this.size - i2);
            this.zzgf = zArr2;
        }
        this.zzgf[i2] = zBooleanValue;
        this.size++;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzcr)) {
            return super.addAll(collection);
        }
        zzcr zzcrVar = (zzcr) collection;
        int i2 = zzcrVar.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size;
        if (Integer.MAX_VALUE - i3 < i2) {
            throw new OutOfMemoryError();
        }
        int i4 = i3 + i2;
        boolean[] zArr = this.zzgf;
        if (i4 > zArr.length) {
            this.zzgf = Arrays.copyOf(zArr, i4);
        }
        System.arraycopy(zzcrVar.zzgf, 0, this.zzgf, this.size, zzcrVar.size);
        this.size = i4;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcr)) {
            return super.equals(obj);
        }
        zzcr zzcrVar = (zzcr) obj;
        if (this.size != zzcrVar.size) {
            return false;
        }
        boolean[] zArr = zzcrVar.zzgf;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (this.zzgf[i2] != zArr[i2]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        zzh(i2);
        return Boolean.valueOf(this.zzgf[i2]);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzg = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            iZzg = (iZzg * 31) + zzeb.zzg(this.zzgf[i2]);
        }
        return iZzg;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i2 = 0; i2 < this.size; i2++) {
            if (obj.equals(Boolean.valueOf(this.zzgf[i2]))) {
                boolean[] zArr = this.zzgf;
                System.arraycopy(zArr, i2 + 1, zArr, i2, (this.size - i2) - 1);
                this.size--;
                ((AbstractList) this).modCount++;
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractList
    public final void removeRange(int i2, int i3) {
        zzaj();
        if (i3 < i2) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        boolean[] zArr = this.zzgf;
        System.arraycopy(zArr, i3, zArr, i2, this.size - i3);
        this.size -= i3 - i2;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        zzh(i2);
        boolean[] zArr = this.zzgf;
        boolean z2 = zArr[i2];
        zArr[i2] = zBooleanValue;
        return Boolean.valueOf(z2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Boolean> zzj(int i2) {
        if (i2 >= this.size) {
            return new zzcr(Arrays.copyOf(this.zzgf, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    private zzcr(boolean[] zArr, int i2) {
        this.zzgf = zArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        zzaj();
        zzh(i2);
        boolean[] zArr = this.zzgf;
        boolean z2 = zArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(zArr, i2 + 1, zArr, i2, (r2 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Boolean.valueOf(z2);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        zzaj();
        int i2 = this.size;
        boolean[] zArr = this.zzgf;
        if (i2 == zArr.length) {
            boolean[] zArr2 = new boolean[((i2 * 3) / 2) + 1];
            System.arraycopy(zArr, 0, zArr2, 0, i2);
            this.zzgf = zArr2;
        }
        boolean[] zArr3 = this.zzgf;
        int i3 = this.size;
        this.size = i3 + 1;
        zArr3[i3] = zBooleanValue;
        return true;
    }
}
