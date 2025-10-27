package io.reactivex.internal.util;

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

    public static int mix(int i2) {
        int i3 = i2 * INT_PHI;
        return i3 ^ (i3 >>> 16);
    }

    public boolean add(T t2) {
        T t3;
        T[] tArr = this.keys;
        int i2 = this.mask;
        int iMix = mix(t2.hashCode()) & i2;
        T t4 = tArr[iMix];
        if (t4 != null) {
            if (t4.equals(t2)) {
                return false;
            }
            do {
                iMix = (iMix + 1) & i2;
                t3 = tArr[iMix];
                if (t3 == null) {
                }
            } while (!t3.equals(t2));
            return false;
        }
        tArr[iMix] = t2;
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

    public boolean remove(T t2) {
        T t3;
        T[] tArr = this.keys;
        int i2 = this.mask;
        int iMix = mix(t2.hashCode()) & i2;
        T t4 = tArr[iMix];
        if (t4 == null) {
            return false;
        }
        if (t4.equals(t2)) {
            return removeEntry(iMix, tArr, i2);
        }
        do {
            iMix = (iMix + 1) & i2;
            t3 = tArr[iMix];
            if (t3 == null) {
                return false;
            }
        } while (!t3.equals(t2));
        return removeEntry(iMix, tArr, i2);
    }

    public boolean removeEntry(int i2, T[] tArr, int i3) {
        int i4;
        T t2;
        this.size--;
        while (true) {
            int i5 = i2 + 1;
            while (true) {
                i4 = i5 & i3;
                t2 = tArr[i4];
                if (t2 == null) {
                    tArr[i2] = null;
                    return true;
                }
                int iMix = mix(t2.hashCode()) & i3;
                if (i2 <= i4) {
                    if (i2 >= iMix || iMix > i4) {
                        break;
                    }
                    i5 = i4 + 1;
                } else if (i2 < iMix || iMix <= i4) {
                    i5 = i4 + 1;
                }
            }
            tArr[i2] = t2;
            i2 = i4;
        }
    }

    public int size() {
        return this.size;
    }

    public OpenHashSet(int i2) {
        this(i2, 0.75f);
    }

    public OpenHashSet(int i2, float f2) {
        this.loadFactor = f2;
        int iRoundToPowerOfTwo = Pow2.roundToPowerOfTwo(i2);
        this.mask = iRoundToPowerOfTwo - 1;
        this.maxSize = (int) (f2 * iRoundToPowerOfTwo);
        this.keys = (T[]) new Object[iRoundToPowerOfTwo];
    }
}
