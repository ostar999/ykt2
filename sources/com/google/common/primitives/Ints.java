package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public final class Ints {
    public static final int BYTES = 4;
    public static final int MAX_POWER_OF_TWO = 1073741824;

    @GwtCompatible
    public static class IntArrayAsList extends AbstractList<Integer> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        final int[] array;
        final int end;
        final int start;

        public IntArrayAsList(int[] iArr) {
            this(iArr, 0, iArr.length);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return (obj instanceof Integer) && Ints.indexOf(this.array, ((Integer) obj).intValue(), this.start, this.end) != -1;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public boolean equals(@NullableDecl Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IntArrayAsList)) {
                return super.equals(obj);
            }
            IntArrayAsList intArrayAsList = (IntArrayAsList) obj;
            int size = size();
            if (intArrayAsList.size() != size) {
                return false;
            }
            for (int i2 = 0; i2 < size; i2++) {
                if (this.array[this.start + i2] != intArrayAsList.array[intArrayAsList.start + i2]) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.AbstractList, java.util.Collection, java.util.List
        public int hashCode() {
            int iHashCode = 1;
            for (int i2 = this.start; i2 < this.end; i2++) {
                iHashCode = (iHashCode * 31) + Ints.hashCode(this.array[i2]);
            }
            return iHashCode;
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            int iIndexOf;
            if (!(obj instanceof Integer) || (iIndexOf = Ints.indexOf(this.array, ((Integer) obj).intValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            int iLastIndexOf;
            if (!(obj instanceof Integer) || (iLastIndexOf = Ints.lastIndexOf(this.array, ((Integer) obj).intValue(), this.start, this.end)) < 0) {
                return -1;
            }
            return iLastIndexOf - this.start;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.end - this.start;
        }

        @Override // java.util.AbstractList, java.util.List
        public List<Integer> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, size());
            if (i2 == i3) {
                return Collections.emptyList();
            }
            int[] iArr = this.array;
            int i4 = this.start;
            return new IntArrayAsList(iArr, i2 + i4, i4 + i3);
        }

        public int[] toIntArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 5);
            sb.append('[');
            sb.append(this.array[this.start]);
            int i2 = this.start;
            while (true) {
                i2++;
                if (i2 >= this.end) {
                    sb.append(']');
                    return sb.toString();
                }
                sb.append(", ");
                sb.append(this.array[i2]);
            }
        }

        public IntArrayAsList(int[] iArr, int i2, int i3) {
            this.array = iArr;
            this.start = i2;
            this.end = i3;
        }

        @Override // java.util.AbstractList, java.util.List
        public Integer get(int i2) {
            Preconditions.checkElementIndex(i2, size());
            return Integer.valueOf(this.array[this.start + i2]);
        }

        @Override // java.util.AbstractList, java.util.List
        public Integer set(int i2, Integer num) {
            Preconditions.checkElementIndex(i2, size());
            int[] iArr = this.array;
            int i3 = this.start;
            int i4 = iArr[i3 + i2];
            iArr[i3 + i2] = ((Integer) Preconditions.checkNotNull(num)).intValue();
            return Integer.valueOf(i4);
        }
    }

    public static final class IntConverter extends Converter<String, Integer> implements Serializable {
        static final IntConverter INSTANCE = new IntConverter();
        private static final long serialVersionUID = 1;

        private IntConverter() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "Ints.stringConverter()";
        }

        @Override // com.google.common.base.Converter
        public String doBackward(Integer num) {
            return num.toString();
        }

        @Override // com.google.common.base.Converter
        public Integer doForward(String str) {
            return Integer.decode(str);
        }
    }

    public enum LexicographicalComparator implements Comparator<int[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "Ints.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(int[] iArr, int[] iArr2) {
            int iMin = Math.min(iArr.length, iArr2.length);
            for (int i2 = 0; i2 < iMin; i2++) {
                int iCompare = Ints.compare(iArr[i2], iArr2[i2]);
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            return iArr.length - iArr2.length;
        }
    }

    private Ints() {
    }

    public static List<Integer> asList(int... iArr) {
        return iArr.length == 0 ? Collections.emptyList() : new IntArrayAsList(iArr);
    }

    public static int checkedCast(long j2) {
        int i2 = (int) j2;
        Preconditions.checkArgument(((long) i2) == j2, "Out of range: %s", j2);
        return i2;
    }

    public static int compare(int i2, int i3) {
        if (i2 < i3) {
            return -1;
        }
        return i2 > i3 ? 1 : 0;
    }

    public static int[] concat(int[]... iArr) {
        int length = 0;
        for (int[] iArr2 : iArr) {
            length += iArr2.length;
        }
        int[] iArr3 = new int[length];
        int length2 = 0;
        for (int[] iArr4 : iArr) {
            System.arraycopy(iArr4, 0, iArr3, length2, iArr4.length);
            length2 += iArr4.length;
        }
        return iArr3;
    }

    @Beta
    public static int constrainToRange(int i2, int i3, int i4) {
        Preconditions.checkArgument(i3 <= i4, "min (%s) must be less than or equal to max (%s)", i3, i4);
        return Math.min(Math.max(i2, i3), i4);
    }

    public static boolean contains(int[] iArr, int i2) {
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static int[] ensureCapacity(int[] iArr, int i2, int i3) {
        Preconditions.checkArgument(i2 >= 0, "Invalid minLength: %s", i2);
        Preconditions.checkArgument(i3 >= 0, "Invalid padding: %s", i3);
        return iArr.length < i2 ? Arrays.copyOf(iArr, i2 + i3) : iArr;
    }

    public static int fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 4, "array too small: %s < %s", bArr.length, 4);
        return fromBytes(bArr[0], bArr[1], bArr[2], bArr[3]);
    }

    public static int fromBytes(byte b3, byte b4, byte b5, byte b6) {
        return (b3 << Ascii.CAN) | ((b4 & 255) << 16) | ((b5 & 255) << 8) | (b6 & 255);
    }

    public static int hashCode(int i2) {
        return i2;
    }

    public static int indexOf(int[] iArr, int i2) {
        return indexOf(iArr, i2, 0, iArr.length);
    }

    public static String join(String str, int... iArr) {
        Preconditions.checkNotNull(str);
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iArr.length * 5);
        sb.append(iArr[0]);
        for (int i2 = 1; i2 < iArr.length; i2++) {
            sb.append(str);
            sb.append(iArr[i2]);
        }
        return sb.toString();
    }

    public static int lastIndexOf(int[] iArr, int i2) {
        return lastIndexOf(iArr, i2, 0, iArr.length);
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int max(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i2 = iArr[0];
        for (int i3 = 1; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i4 > i2) {
                i2 = i4;
            }
        }
        return i2;
    }

    public static int min(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int i2 = iArr[0];
        for (int i3 = 1; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i4 < i2) {
                i2 = i4;
            }
        }
        return i2;
    }

    public static void reverse(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        reverse(iArr, 0, iArr.length);
    }

    public static int saturatedCast(long j2) {
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (j2 < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) j2;
    }

    public static void sortDescending(int[] iArr) {
        Preconditions.checkNotNull(iArr);
        sortDescending(iArr, 0, iArr.length);
    }

    @Beta
    public static Converter<String, Integer> stringConverter() {
        return IntConverter.INSTANCE;
    }

    public static int[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof IntArrayAsList) {
            return ((IntArrayAsList) collection).toIntArray();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = ((Number) Preconditions.checkNotNull(array[i2])).intValue();
        }
        return iArr;
    }

    public static byte[] toByteArray(int i2) {
        return new byte[]{(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
    }

    @Beta
    @NullableDecl
    public static Integer tryParse(String str) {
        return tryParse(str, 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexOf(int[] iArr, int i2, int i3, int i4) {
        while (i3 < i4) {
            if (iArr[i3] == i2) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int lastIndexOf(int[] iArr, int i2, int i3, int i4) {
        for (int i5 = i4 - 1; i5 >= i3; i5--) {
            if (iArr[i5] == i2) {
                return i5;
            }
        }
        return -1;
    }

    @Beta
    @NullableDecl
    public static Integer tryParse(String str, int i2) {
        Long lTryParse = Longs.tryParse(str, i2);
        if (lTryParse == null || lTryParse.longValue() != lTryParse.intValue()) {
            return null;
        }
        return Integer.valueOf(lTryParse.intValue());
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0023, code lost:
    
        r0 = r0 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int indexOf(int[] r5, int[] r6) {
        /*
            java.lang.String r0 = "array"
            com.google.common.base.Preconditions.checkNotNull(r5, r0)
            java.lang.String r0 = "target"
            com.google.common.base.Preconditions.checkNotNull(r6, r0)
            int r0 = r6.length
            r1 = 0
            if (r0 != 0) goto Lf
            return r1
        Lf:
            r0 = r1
        L10:
            int r2 = r5.length
            int r3 = r6.length
            int r2 = r2 - r3
            int r2 = r2 + 1
            if (r0 >= r2) goto L2a
            r2 = r1
        L18:
            int r3 = r6.length
            if (r2 >= r3) goto L29
            int r3 = r0 + r2
            r3 = r5[r3]
            r4 = r6[r2]
            if (r3 == r4) goto L26
            int r0 = r0 + 1
            goto L10
        L26:
            int r2 = r2 + 1
            goto L18
        L29:
            return r0
        L2a:
            r5 = -1
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.primitives.Ints.indexOf(int[], int[]):int");
    }

    public static void reverse(int[] iArr, int i2, int i3) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i2, i3, iArr.length);
        for (int i4 = i3 - 1; i2 < i4; i4--) {
            int i5 = iArr[i2];
            iArr[i2] = iArr[i4];
            iArr[i4] = i5;
            i2++;
        }
    }

    public static void sortDescending(int[] iArr, int i2, int i3) {
        Preconditions.checkNotNull(iArr);
        Preconditions.checkPositionIndexes(i2, i3, iArr.length);
        Arrays.sort(iArr, i2, i3);
        reverse(iArr, i2, i3);
    }
}
