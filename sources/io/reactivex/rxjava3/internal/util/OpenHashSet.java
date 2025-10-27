package io.reactivex.rxjava3.internal.util;

/* loaded from: classes8.dex */
public final class OpenHashSet<T> {
    private static final int INT_PHI = -1640531527;
    T[] keys;
    final float loadFactor;
    int mask;
    int maxSize;
    int size;

    public OpenHashSet() {
        this(16, 0.75f);
    }

    public static int mix(int x2) {
        int i2 = x2 * INT_PHI;
        return i2 ^ (i2 >>> 16);
    }

    public boolean add(T value) {
        T t2;
        T[] tArr = this.keys;
        int i2 = this.mask;
        int iMix = mix(value.hashCode()) & i2;
        T t3 = tArr[iMix];
        if (t3 != null) {
            if (t3.equals(value)) {
                return false;
            }
            do {
                iMix = (iMix + 1) & i2;
                t2 = tArr[iMix];
                if (t2 == null) {
                }
            } while (!t2.equals(value));
            return false;
        }
        tArr[iMix] = value;
        int i3 = this.size + 1;
        this.size = i3;
        if (i3 >= this.maxSize) {
            rehash();
        }
        return true;
    }

    public Object[] keys() {
        return this.keys;
    }

    public void rehash() {
        T t2;
        T[] tArr = this.keys;
        int length = tArr.length;
        int i2 = length << 1;
        int i3 = i2 - 1;
        T[] tArr2 = (T[]) new Object[i2];
        int i4 = this.size;
        while (true) {
            int i5 = i4 - 1;
            if (i4 == 0) {
                this.mask = i3;
                this.maxSize = (int) (i2 * this.loadFactor);
                this.keys = tArr2;
                return;
            }
            do {
                length--;
                t2 = tArr[length];
            } while (t2 == null);
            int iMix = mix(t2.hashCode()) & i3;
            if (tArr2[iMix] != null) {
                do {
                    iMix = (iMix + 1) & i3;
                } while (tArr2[iMix] != null);
            }
            tArr2[iMix] = tArr[length];
            i4 = i5;
        }
    }

    public boolean remove(T value) {
        T t2;
        T[] tArr = this.keys;
        int i2 = this.mask;
        int iMix = mix(value.hashCode()) & i2;
        T t3 = tArr[iMix];
        if (t3 == null) {
            return false;
        }
        if (t3.equals(value)) {
            return removeEntry(iMix, tArr, i2);
        }
        do {
            iMix = (iMix + 1) & i2;
            t2 = tArr[iMix];
            if (t2 == null) {
                return false;
            }
        } while (!t2.equals(value));
        return removeEntry(iMix, tArr, i2);
    }

    public boolean removeEntry(int pos, T[] a3, int m2) {
        int i2;
        T t2;
        this.size--;
        while (true) {
            int i3 = pos + 1;
            while (true) {
                i2 = i3 & m2;
                t2 = a3[i2];
                if (t2 == null) {
                    a3[pos] = null;
                    return true;
                }
                int iMix = mix(t2.hashCode()) & m2;
                if (pos <= i2) {
                    if (pos >= iMix || iMix > i2) {
                        break;
                    }
                    i3 = i2 + 1;
                } else if (pos < iMix || iMix <= i2) {
                    i3 = i2 + 1;
                }
            }
            a3[pos] = t2;
            pos = i2;
        }
    }

    public int size() {
        return this.size;
    }

    public OpenHashSet(int capacity) {
        this(capacity, 0.75f);
    }

    public OpenHashSet(int i2, float f2) {
        this.loadFactor = f2;
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(i2);
        this.mask = iRoundToPowerOfTwo - 1;
        this.maxSize = (int) (f2 * iRoundToPowerOfTwo);
        this.keys = (T[]) new Object[iRoundToPowerOfTwo];
    }
}
