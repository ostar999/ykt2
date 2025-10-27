package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzdl extends zzcp<Double> implements zzee<Double>, zzfq, RandomAccess {
    private static final zzdl zzgz;
    private int size;
    private double[] zzha;

    static {
        zzdl zzdlVar = new zzdl(new double[0], 0);
        zzgz = zzdlVar;
        zzdlVar.zzai();
    }

    public zzdl() {
        this(new double[10], 0);
    }

    public static zzdl zzax() {
        return zzgz;
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
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        if (i2 < 0 || i2 > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i2));
        }
        double[] dArr = this.zzha;
        if (i3 < dArr.length) {
            System.arraycopy(dArr, i2, dArr, i2 + 1, i3 - i2);
        } else {
            double[] dArr2 = new double[((i3 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i2);
            System.arraycopy(this.zzha, i2, dArr2, i2 + 1, this.size - i2);
            this.zzha = dArr2;
        }
        this.zzha[i2] = dDoubleValue;
        this.size++;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Double> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzdl)) {
            return super.addAll(collection);
        }
        zzdl zzdlVar = (zzdl) collection;
        int i2 = zzdlVar.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size;
        if (Integer.MAX_VALUE - i3 < i2) {
            throw new OutOfMemoryError();
        }
        int i4 = i3 + i2;
        double[] dArr = this.zzha;
        if (i4 > dArr.length) {
            this.zzha = Arrays.copyOf(dArr, i4);
        }
        System.arraycopy(zzdlVar.zzha, 0, this.zzha, this.size, zzdlVar.size);
        this.size = i4;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzdl)) {
            return super.equals(obj);
        }
        zzdl zzdlVar = (zzdl) obj;
        if (this.size != zzdlVar.size) {
            return false;
        }
        double[] dArr = zzdlVar.zzha;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (Double.doubleToLongBits(this.zzha[i2]) != Double.doubleToLongBits(dArr[i2])) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        zzh(i2);
        return Double.valueOf(this.zzha[i2]);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzk = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            iZzk = (iZzk * 31) + zzeb.zzk(Double.doubleToLongBits(this.zzha[i2]));
        }
        return iZzk;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i2 = 0; i2 < this.size; i2++) {
            if (obj.equals(Double.valueOf(this.zzha[i2]))) {
                double[] dArr = this.zzha;
                System.arraycopy(dArr, i2 + 1, dArr, i2, (this.size - i2) - 1);
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
        double[] dArr = this.zzha;
        System.arraycopy(dArr, i3, dArr, i2, this.size - i3);
        this.size -= i3 - i2;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        zzh(i2);
        double[] dArr = this.zzha;
        double d3 = dArr[i2];
        dArr[i2] = dDoubleValue;
        return Double.valueOf(d3);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Double> zzj(int i2) {
        if (i2 >= this.size) {
            return new zzdl(Arrays.copyOf(this.zzha, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    private zzdl(double[] dArr, int i2) {
        this.zzha = dArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        zzaj();
        zzh(i2);
        double[] dArr = this.zzha;
        double d3 = dArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(dArr, i2 + 1, dArr, i2, (r3 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Double.valueOf(d3);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        double dDoubleValue = ((Double) obj).doubleValue();
        zzaj();
        int i2 = this.size;
        double[] dArr = this.zzha;
        if (i2 == dArr.length) {
            double[] dArr2 = new double[((i2 * 3) / 2) + 1];
            System.arraycopy(dArr, 0, dArr2, 0, i2);
            this.zzha = dArr2;
        }
        double[] dArr3 = this.zzha;
        int i3 = this.size;
        this.size = i3 + 1;
        dArr3[i3] = dDoubleValue;
        return true;
    }
}
