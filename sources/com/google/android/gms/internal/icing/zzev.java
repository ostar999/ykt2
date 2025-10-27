package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzev extends zzcp<Long> implements zzee<Long>, zzfq, RandomAccess {
    private static final zzev zzmf;
    private int size;
    private long[] zzmg;

    static {
        zzev zzevVar = new zzev(new long[0], 0);
        zzmf = zzevVar;
        zzevVar.zzai();
    }

    public zzev() {
        this(new long[10], 0);
    }

    public static zzev zzci() {
        return zzmf;
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
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        if (i2 < 0 || i2 > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzi(i2));
        }
        long[] jArr = this.zzmg;
        if (i3 < jArr.length) {
            System.arraycopy(jArr, i2, jArr, i2 + 1, i3 - i2);
        } else {
            long[] jArr2 = new long[((i3 * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i2);
            System.arraycopy(this.zzmg, i2, jArr2, i2 + 1, this.size - i2);
            this.zzmg = jArr2;
        }
        this.zzmg[i2] = jLongValue;
        this.size++;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Long> collection) {
        zzaj();
        zzeb.checkNotNull(collection);
        if (!(collection instanceof zzev)) {
            return super.addAll(collection);
        }
        zzev zzevVar = (zzev) collection;
        int i2 = zzevVar.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size;
        if (Integer.MAX_VALUE - i3 < i2) {
            throw new OutOfMemoryError();
        }
        int i4 = i3 + i2;
        long[] jArr = this.zzmg;
        if (i4 > jArr.length) {
            this.zzmg = Arrays.copyOf(jArr, i4);
        }
        System.arraycopy(zzevVar.zzmg, 0, this.zzmg, this.size, zzevVar.size);
        this.size = i4;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzev)) {
            return super.equals(obj);
        }
        zzev zzevVar = (zzev) obj;
        if (this.size != zzevVar.size) {
            return false;
        }
        long[] jArr = zzevVar.zzmg;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (this.zzmg[i2] != jArr[i2]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i2) {
        return Long.valueOf(getLong(i2));
    }

    public final long getLong(int i2) {
        zzh(i2);
        return this.zzmg[i2];
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int iZzk = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            iZzk = (iZzk * 31) + zzeb.zzk(this.zzmg[i2]);
        }
        return iZzk;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzaj();
        for (int i2 = 0; i2 < this.size; i2++) {
            if (obj.equals(Long.valueOf(this.zzmg[i2]))) {
                long[] jArr = this.zzmg;
                System.arraycopy(jArr, i2 + 1, jArr, i2, (this.size - i2) - 1);
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
        long[] jArr = this.zzmg;
        System.arraycopy(jArr, i3, jArr, i2, this.size - i3);
        this.size -= i3 - i2;
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i2, Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        zzh(i2);
        long[] jArr = this.zzmg;
        long j2 = jArr[i2];
        jArr[i2] = jLongValue;
        return Long.valueOf(j2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee<Long> zzj(int i2) {
        if (i2 >= this.size) {
            return new zzev(Arrays.copyOf(this.zzmg, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    private zzev(long[] jArr, int i2) {
        this.zzmg = jArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i2) {
        zzaj();
        zzh(i2);
        long[] jArr = this.zzmg;
        long j2 = jArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(jArr, i2 + 1, jArr, i2, (r3 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Long.valueOf(j2);
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* synthetic */ boolean add(Object obj) {
        long jLongValue = ((Long) obj).longValue();
        zzaj();
        int i2 = this.size;
        long[] jArr = this.zzmg;
        if (i2 == jArr.length) {
            long[] jArr2 = new long[((i2 * 3) / 2) + 1];
            System.arraycopy(jArr, 0, jArr2, 0, i2);
            this.zzmg = jArr2;
        }
        long[] jArr3 = this.zzmg;
        int i3 = this.size;
        this.size = i3 + 1;
        jArr3[i3] = jLongValue;
        return true;
    }
}
