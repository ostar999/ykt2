package com.google.android.gms.internal.icing;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzfs<E> extends zzcp<E> implements RandomAccess {
    private static final zzfs<Object> zzni;
    private int size;
    private E[] zznj;

    static {
        zzfs<Object> zzfsVar = new zzfs<>(new Object[0], 0);
        zzni = zzfsVar;
        zzfsVar.zzai();
    }

    public zzfs() {
        this(new Object[10], 0);
    }

    public static <E> zzfs<E> zzcu() {
        return (zzfs<E>) zzni;
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

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(E e2) {
        zzaj();
        int i2 = this.size;
        E[] eArr = this.zznj;
        if (i2 == eArr.length) {
            this.zznj = (E[]) Arrays.copyOf(eArr, ((i2 * 3) / 2) + 1);
        }
        E[] eArr2 = this.zznj;
        int i3 = this.size;
        this.size = i3 + 1;
        eArr2[i3] = e2;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public final E get(int i2) {
        zzh(i2);
        return this.zznj[i2];
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final E remove(int i2) {
        zzaj();
        zzh(i2);
        E[] eArr = this.zznj;
        E e2 = eArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(eArr, i2 + 1, eArr, i2, (r2 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return e2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final E set(int i2, E e2) {
        zzaj();
        zzh(i2);
        E[] eArr = this.zznj;
        E e3 = eArr[i2];
        eArr[i2] = e2;
        ((AbstractList) this).modCount++;
        return e3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.android.gms.internal.icing.zzee
    public final /* synthetic */ zzee zzj(int i2) {
        if (i2 >= this.size) {
            return new zzfs(Arrays.copyOf(this.zznj, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    private zzfs(E[] eArr, int i2) {
        this.zznj = eArr;
        this.size = i2;
    }

    @Override // com.google.android.gms.internal.icing.zzcp, java.util.AbstractList, java.util.List
    public final void add(int i2, E e2) {
        int i3;
        zzaj();
        if (i2 >= 0 && i2 <= (i3 = this.size)) {
            E[] eArr = this.zznj;
            if (i3 < eArr.length) {
                System.arraycopy(eArr, i2, eArr, i2 + 1, i3 - i2);
            } else {
                E[] eArr2 = (E[]) new Object[((i3 * 3) / 2) + 1];
                System.arraycopy(eArr, 0, eArr2, 0, i2);
                System.arraycopy(this.zznj, i2, eArr2, i2 + 1, this.size - i2);
                this.zznj = eArr2;
            }
            this.zznj[i2] = e2;
            this.size++;
            ((AbstractList) this).modCount++;
            return;
        }
        throw new IndexOutOfBoundsException(zzi(i2));
    }
}
