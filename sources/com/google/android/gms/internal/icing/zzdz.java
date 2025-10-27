package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzdz extends zzcp<Integer> implements zzee<Integer>, zzfq, RandomAccess {
    private static final zzdz zzkk;
    private int size;
    private int[] zzkl;

    static {
        zzdz zzdzVar = new zzdz(new int[0], 0);
        zzkk = zzdzVar;
        zzdzVar.zzai();
    }

    public zzdz() {
        this(new int[10], 0);
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
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        if (i2 < 0 || i2 > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i2));
        }
        int[] iArr = this.zzkl;
        if (i3 < iArr.length) {
            System.arraycopy(iArr, i2, iArr, i2 + 1, i3 - i2);
        } else {
            int[] iArr2 = new int[((i3 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            System.arraycopy(this.zzkl, i2, iArr2, i2 + 1, this.size - i2);
            this.zzkl = iArr2;
        }
        this.zzkl[i2] = iIntValue;
        this.size++;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzdz)) {
            return super.addAll(collection);
        }
        zzdz zzdzVar = (zzdz) collection;
        int i2 = zzdzVar.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size;
        if (Integer.MAX_VALUE - i3 < i2) {
            throw new OutOfMemoryError();
        }
        int i4 = i3 + i2;
        int[] iArr = this.zzkl;
        if (i4 > iArr.length) {
            this.zzkl = Arrays.copyOf(iArr, i4);
        }
        System.arraycopy(zzdzVar.zzkl, 0, this.zzkl, this.size, zzdzVar.size);
        this.size = i4;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdz)) {
            return super.equals(obj);
        }
        zzdz zzdzVar = (zzdz) obj;
        if (this.size != zzdzVar.size) {
            return false;
        }
        int[] iArr = zzdzVar.zzkl;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (this.zzkl[i2] != iArr[i2]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        return Integer.valueOf(getInt(i2));
    }

    public final int getInt(int i2) {
        zzh(i2);
        return this.zzkl[i2];
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i2 = 1;
        for (int i3 = 0; i3 < this.size; i3++) {
            i2 = (i2 * 31) + this.zzkl[i3];
        }
        return i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i2 = 0; i2 < this.size; i2++) {
            if (obj.equals(Integer.valueOf(this.zzkl[i2]))) {
                int[] iArr = this.zzkl;
                System.arraycopy(iArr, i2 + 1, iArr, i2, (this.size - i2) - 1);
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
        int[] iArr = this.zzkl;
        System.arraycopy(iArr, i3, iArr, i2, this.size - i3);
        this.size -= i3 - i2;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        zzh(i2);
        int[] iArr = this.zzkl;
        int i3 = iArr[i2];
        iArr[i2] = iIntValue;
        return Integer.valueOf(i3);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Integer> zzj(int i2) {
        if (i2 >= this.size) {
            return new zzdz(Arrays.copyOf(this.zzkl, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    private zzdz(int[] iArr, int i2) {
        this.zzkl = iArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        zzaj();
        zzh(i2);
        int[] iArr = this.zzkl;
        int i3 = iArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(iArr, i2 + 1, iArr, i2, (r2 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Integer.valueOf(i3);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        int iIntValue = ((Integer) obj).intValue();
        zzaj();
        int i2 = this.size;
        int[] iArr = this.zzkl;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[((i2 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.zzkl = iArr2;
        }
        int[] iArr3 = this.zzkl;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr3[i3] = iIntValue;
        return true;
    }
}
