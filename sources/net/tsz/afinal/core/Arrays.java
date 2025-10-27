package net.tsz.afinal.core;

import com.yikaobang.yixue.R2;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import okhttp3.HttpUrl;

/* loaded from: classes9.dex */
public class Arrays {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static class ArrayList<E> extends AbstractList<E> implements List<E>, Serializable, RandomAccess {
        private static final long serialVersionUID = -2764017481108945198L;

        /* renamed from: a, reason: collision with root package name */
        private final E[] f27748a;

        public ArrayList(E[] eArr) {
            eArr.getClass();
            this.f27748a = eArr;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            if (obj != null) {
                for (E e2 : this.f27748a) {
                    if (obj.equals(e2)) {
                        return true;
                    }
                }
            } else {
                for (E e3 : this.f27748a) {
                    if (e3 == null) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // java.util.AbstractList, java.util.List
        public E get(int i2) {
            try {
                return this.f27748a[i2];
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw e2;
            }
        }

        @Override // java.util.AbstractList, java.util.List
        public int indexOf(Object obj) {
            int i2 = 0;
            if (obj != null) {
                while (true) {
                    E[] eArr = this.f27748a;
                    if (i2 >= eArr.length) {
                        return -1;
                    }
                    if (obj.equals(eArr[i2])) {
                        return i2;
                    }
                    i2++;
                }
            } else {
                while (true) {
                    E[] eArr2 = this.f27748a;
                    if (i2 >= eArr2.length) {
                        return -1;
                    }
                    if (eArr2[i2] == null) {
                        return i2;
                    }
                    i2++;
                }
            }
        }

        @Override // java.util.AbstractList, java.util.List
        public int lastIndexOf(Object obj) {
            if (obj != null) {
                for (int length = this.f27748a.length - 1; length >= 0; length--) {
                    if (obj.equals(this.f27748a[length])) {
                        return length;
                    }
                }
                return -1;
            }
            for (int length2 = this.f27748a.length - 1; length2 >= 0; length2--) {
                if (this.f27748a[length2] == null) {
                    return length2;
                }
            }
            return -1;
        }

        @Override // java.util.AbstractList, java.util.List
        public E set(int i2, E e2) {
            E[] eArr = this.f27748a;
            E e3 = eArr[i2];
            eArr[i2] = e2;
            return e3;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.f27748a.length;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public Object[] toArray() {
            return (Object[]) this.f27748a.clone();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (size > tArr.length) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
            }
            System.arraycopy(this.f27748a, 0, tArr, 0, size);
            if (size < tArr.length) {
                tArr[size] = null;
            }
            return tArr;
        }
    }

    private Arrays() {
    }

    public static <T> List<T> asList(T... tArr) {
        return new ArrayList(tArr);
    }

    public static int binarySearch(byte[] bArr, byte b3) {
        return binarySearch(bArr, 0, bArr.length, b3);
    }

    private static void checkBinarySearchBounds(int i2, int i3, int i4) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        if (i2 < 0 || i3 > i4) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static boolean[] copyOf(boolean[] zArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(zArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static boolean[] copyOfRange(boolean[] zArr, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        int length = zArr.length;
        if (i2 < 0 || i2 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i4 = i3 - i2;
        int iMin = Math.min(i4, length - i2);
        boolean[] zArr2 = new boolean[i4];
        System.arraycopy(zArr, i2, zArr2, 0, iMin);
        return zArr2;
    }

    public static boolean deepEquals(Object[] objArr, Object[] objArr2) {
        if (objArr == objArr2) {
            return true;
        }
        if (objArr == null || objArr2 == null || objArr.length != objArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (!deepEqualsElements(objArr[i2], objArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private static boolean deepEqualsElements(Object obj, Object obj2) {
        Class<?> componentType;
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null || (componentType = obj.getClass().getComponentType()) != obj2.getClass().getComponentType()) {
            return false;
        }
        return componentType == null ? obj.equals(obj2) : !componentType.isPrimitive() ? deepEquals((Object[]) obj, (Object[]) obj2) : componentType.equals(Integer.TYPE) ? equals((int[]) obj, (int[]) obj2) : componentType.equals(Character.TYPE) ? equals((char[]) obj, (char[]) obj2) : componentType.equals(Boolean.TYPE) ? equals((boolean[]) obj, (boolean[]) obj2) : componentType.equals(Byte.TYPE) ? equals((byte[]) obj, (byte[]) obj2) : componentType.equals(Long.TYPE) ? equals((long[]) obj, (long[]) obj2) : componentType.equals(Float.TYPE) ? equals((float[]) obj, (float[]) obj2) : componentType.equals(Double.TYPE) ? equals((double[]) obj, (double[]) obj2) : equals((short[]) obj, (short[]) obj2);
    }

    public static int deepHashCode(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        int iDeepHashCodeElement = 1;
        for (Object obj : objArr) {
            iDeepHashCodeElement = (iDeepHashCodeElement * 31) + deepHashCodeElement(obj);
        }
        return iDeepHashCodeElement;
    }

    private static int deepHashCodeElement(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> componentType = obj.getClass().getComponentType();
        return componentType == null ? obj.hashCode() : !componentType.isPrimitive() ? deepHashCode((Object[]) obj) : componentType.equals(Integer.TYPE) ? hashCode((int[]) obj) : componentType.equals(Character.TYPE) ? hashCode((char[]) obj) : componentType.equals(Boolean.TYPE) ? hashCode((boolean[]) obj) : componentType.equals(Byte.TYPE) ? hashCode((byte[]) obj) : componentType.equals(Long.TYPE) ? hashCode((long[]) obj) : componentType.equals(Float.TYPE) ? hashCode((float[]) obj) : componentType.equals(Double.TYPE) ? hashCode((double[]) obj) : hashCode((short[]) obj);
    }

    public static String deepToString(Object[] objArr) {
        if (objArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(objArr.length * 9);
        deepToStringImpl(objArr, new Object[]{objArr}, sb);
        return sb.toString();
    }

    private static void deepToStringImpl(Object[] objArr, Object[] objArr2, StringBuilder sb) {
        if (objArr == null) {
            sb.append("null");
            return;
        }
        sb.append('[');
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (i2 != 0) {
                sb.append(", ");
            }
            Object obj = objArr[i2];
            if (obj == null) {
                sb.append("null");
            } else {
                Class<?> cls = obj.getClass();
                if (cls.isArray()) {
                    Class<?> componentType = cls.getComponentType();
                    if (componentType.isPrimitive()) {
                        if (Boolean.TYPE.equals(componentType)) {
                            sb.append(toString((boolean[]) obj));
                        } else if (Byte.TYPE.equals(componentType)) {
                            sb.append(toString((byte[]) obj));
                        } else if (Character.TYPE.equals(componentType)) {
                            sb.append(toString((char[]) obj));
                        } else if (Double.TYPE.equals(componentType)) {
                            sb.append(toString((double[]) obj));
                        } else if (Float.TYPE.equals(componentType)) {
                            sb.append(toString((float[]) obj));
                        } else if (Integer.TYPE.equals(componentType)) {
                            sb.append(toString((int[]) obj));
                        } else if (Long.TYPE.equals(componentType)) {
                            sb.append(toString((long[]) obj));
                        } else {
                            if (!Short.TYPE.equals(componentType)) {
                                throw new AssertionError();
                            }
                            sb.append(toString((short[]) obj));
                        }
                    } else if (deepToStringImplContains(objArr2, obj)) {
                        sb.append("[...]");
                    } else {
                        Object[] objArr3 = (Object[]) obj;
                        Object[] objArr4 = new Object[objArr2.length + 1];
                        System.arraycopy(objArr2, 0, objArr4, 0, objArr2.length);
                        objArr4[objArr2.length] = objArr3;
                        deepToStringImpl(objArr3, objArr4, sb);
                    }
                } else {
                    sb.append(objArr[i2]);
                }
            }
        }
        sb.append(']');
    }

    private static boolean deepToStringImplContains(Object[] objArr, Object obj) {
        if (objArr != null && objArr.length != 0) {
            for (Object obj2 : objArr) {
                if (obj2 == obj) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equals(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr.length != bArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static void fill(byte[] bArr, byte b3) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = b3;
        }
    }

    public static int hashCode(boolean[] zArr) {
        if (zArr == null) {
            return 0;
        }
        int i2 = 1;
        for (boolean z2 : zArr) {
            i2 = (i2 * 31) + (z2 ? R2.attr.customColorDrawableValue : R2.attr.customIsDrag);
        }
        return i2;
    }

    public static String toString(boolean[] zArr) {
        if (zArr == null) {
            return "null";
        }
        if (zArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(zArr.length * 7);
        sb.append('[');
        sb.append(zArr[0]);
        for (int i2 = 1; i2 < zArr.length; i2++) {
            sb.append(", ");
            sb.append(zArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static int binarySearch(byte[] bArr, int i2, int i3, byte b3) {
        checkBinarySearchBounds(i2, i3, bArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            byte b4 = bArr[i5];
            if (b4 < b3) {
                i2 = i5 + 1;
            } else {
                if (b4 <= b3) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static int hashCode(int[] iArr) {
        if (iArr == null) {
            return 0;
        }
        int i2 = 1;
        for (int i3 : iArr) {
            i2 = (i2 * 31) + i3;
        }
        return i2;
    }

    public static byte[] copyOf(byte[] bArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(bArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static void fill(int[] iArr, int i2) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = i2;
        }
    }

    public static int hashCode(short[] sArr) {
        if (sArr == null) {
            return 0;
        }
        int i2 = 1;
        for (short s2 : sArr) {
            i2 = (i2 * 31) + s2;
        }
        return i2;
    }

    public static int binarySearch(char[] cArr, char c3) {
        return binarySearch(cArr, 0, cArr.length, c3);
    }

    public static boolean equals(short[] sArr, short[] sArr2) {
        if (sArr == sArr2) {
            return true;
        }
        if (sArr == null || sArr2 == null || sArr.length != sArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < sArr.length; i2++) {
            if (sArr[i2] != sArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int i2 = 1;
        for (char c3 : cArr) {
            i2 = (i2 * 31) + c3;
        }
        return i2;
    }

    public static int binarySearch(char[] cArr, int i2, int i3, char c3) {
        checkBinarySearchBounds(i2, i3, cArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            char c4 = cArr[i5];
            if (c4 < c3) {
                i2 = i5 + 1;
            } else {
                if (c4 <= c3) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static char[] copyOf(char[] cArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(cArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static void fill(boolean[] zArr, boolean z2) {
        for (int i2 = 0; i2 < zArr.length; i2++) {
            zArr[i2] = z2;
        }
    }

    public static int hashCode(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int i2 = 1;
        for (byte b3 : bArr) {
            i2 = (i2 * 31) + b3;
        }
        return i2;
    }

    public static int hashCode(long[] jArr) {
        if (jArr == null) {
            return 0;
        }
        int i2 = 1;
        for (long j2 : jArr) {
            i2 = (i2 * 31) + ((int) (j2 ^ (j2 >>> 32)));
        }
        return i2;
    }

    public static int binarySearch(double[] dArr, double d3) {
        return binarySearch(dArr, 0, dArr.length, d3);
    }

    public static double[] copyOf(double[] dArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(dArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static byte[] copyOfRange(byte[] bArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = bArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, i2, bArr2, 0, iMin);
                return bArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static boolean equals(char[] cArr, char[] cArr2) {
        if (cArr == cArr2) {
            return true;
        }
        if (cArr == null || cArr2 == null || cArr.length != cArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < cArr.length; i2++) {
            if (cArr[i2] != cArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static void fill(Object[] objArr, Object obj) {
        for (int i2 = 0; i2 < objArr.length; i2++) {
            objArr[i2] = obj;
        }
    }

    public static int hashCode(float[] fArr) {
        if (fArr == null) {
            return 0;
        }
        int iFloatToIntBits = 1;
        for (float f2 : fArr) {
            iFloatToIntBits = (iFloatToIntBits * 31) + Float.floatToIntBits(f2);
        }
        return iFloatToIntBits;
    }

    public static int binarySearch(double[] dArr, int i2, int i3, double d3) {
        checkBinarySearchBounds(i2, i3, dArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            double d4 = dArr[i5];
            if (d4 >= d3) {
                if (d4 <= d3) {
                    if (d4 != 0.0d && d4 == d3) {
                        return i5;
                    }
                    long jDoubleToLongBits = Double.doubleToLongBits(d4);
                    long jDoubleToLongBits2 = Double.doubleToLongBits(d3);
                    if (jDoubleToLongBits >= jDoubleToLongBits2) {
                        if (jDoubleToLongBits <= jDoubleToLongBits2) {
                            return i5;
                        }
                    }
                }
                i4 = i5 - 1;
            }
            i2 = i5 + 1;
        }
        return ~i2;
    }

    public static float[] copyOf(float[] fArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(fArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static int hashCode(double[] dArr) {
        if (dArr == null) {
            return 0;
        }
        int i2 = 1;
        for (double d3 : dArr) {
            long jDoubleToLongBits = Double.doubleToLongBits(d3);
            i2 = (i2 * 31) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
        }
        return i2;
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        if (iArr == iArr2) {
            return true;
        }
        if (iArr == null || iArr2 == null || iArr.length != iArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static String toString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        if (bArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 6);
        sb.append('[');
        sb.append((int) bArr[0]);
        for (int i2 = 1; i2 < bArr.length; i2++) {
            sb.append(", ");
            sb.append((int) bArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static int[] copyOf(int[] iArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(iArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static int hashCode(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        int length = objArr.length;
        int iHashCode = 1;
        for (int i2 = 0; i2 < length; i2++) {
            Object obj = objArr[i2];
            iHashCode = (iHashCode * 31) + (obj == null ? 0 : obj.hashCode());
        }
        return iHashCode;
    }

    public static int binarySearch(float[] fArr, float f2) {
        return binarySearch(fArr, 0, fArr.length, f2);
    }

    public static int binarySearch(float[] fArr, int i2, int i3, float f2) {
        checkBinarySearchBounds(i2, i3, fArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            float f3 = fArr[i5];
            if (f3 >= f2) {
                if (f3 <= f2) {
                    if (f3 != 0.0f && f3 == f2) {
                        return i5;
                    }
                    int iFloatToIntBits = Float.floatToIntBits(f3);
                    int iFloatToIntBits2 = Float.floatToIntBits(f2);
                    if (iFloatToIntBits >= iFloatToIntBits2) {
                        if (iFloatToIntBits <= iFloatToIntBits2) {
                            return i5;
                        }
                    }
                }
                i4 = i5 - 1;
            }
            i2 = i5 + 1;
        }
        return ~i2;
    }

    public static long[] copyOf(long[] jArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(jArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static char[] copyOfRange(char[] cArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = cArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                char[] cArr2 = new char[i4];
                System.arraycopy(cArr, i2, cArr2, 0, iMin);
                return cArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static boolean equals(long[] jArr, long[] jArr2) {
        if (jArr == jArr2) {
            return true;
        }
        if (jArr == null || jArr2 == null || jArr.length != jArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (jArr[i2] != jArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static short[] copyOf(short[] sArr, int i2) {
        if (i2 >= 0) {
            return copyOfRange(sArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        if (fArr == fArr2) {
            return true;
        }
        if (fArr == null || fArr2 == null || fArr.length != fArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (Float.floatToIntBits(fArr[i2]) != Float.floatToIntBits(fArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public static int binarySearch(int[] iArr, int i2) {
        return binarySearch(iArr, 0, iArr.length, i2);
    }

    public static <T> T[] copyOf(T[] tArr, int i2) {
        tArr.getClass();
        if (i2 >= 0) {
            return (T[]) copyOfRange(tArr, 0, i2);
        }
        throw new NegativeArraySizeException();
    }

    public static int binarySearch(int[] iArr, int i2, int i3, int i4) {
        checkBinarySearchBounds(i2, i3, iArr.length);
        int i5 = i3 - 1;
        while (i2 <= i5) {
            int i6 = (i2 + i5) >>> 1;
            int i7 = iArr[i6];
            if (i7 < i4) {
                i2 = i6 + 1;
            } else {
                if (i7 <= i4) {
                    return i6;
                }
                i5 = i6 - 1;
            }
        }
        return ~i2;
    }

    public static double[] copyOfRange(double[] dArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = dArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                double[] dArr2 = new double[i4];
                System.arraycopy(dArr, i2, dArr2, 0, iMin);
                return dArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static String toString(char[] cArr) {
        if (cArr == null) {
            return "null";
        }
        if (cArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(cArr.length * 3);
        sb.append('[');
        sb.append(cArr[0]);
        for (int i2 = 1; i2 < cArr.length; i2++) {
            sb.append(", ");
            sb.append(cArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static int binarySearch(long[] jArr, long j2) {
        return binarySearch(jArr, 0, jArr.length, j2);
    }

    public static <T, U> T[] copyOf(U[] uArr, int i2, Class<? extends T[]> cls) {
        if (i2 >= 0) {
            return (T[]) copyOfRange(uArr, 0, i2, cls);
        }
        throw new NegativeArraySizeException();
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        if (dArr == dArr2) {
            return true;
        }
        if (dArr == null || dArr2 == null || dArr.length != dArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < dArr.length; i2++) {
            if (Double.doubleToLongBits(dArr[i2]) != Double.doubleToLongBits(dArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public static int binarySearch(long[] jArr, int i2, int i3, long j2) {
        checkBinarySearchBounds(i2, i3, jArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            long j3 = jArr[i5];
            if (j3 < j2) {
                i2 = i5 + 1;
            } else {
                if (j3 <= j2) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static int binarySearch(Object[] objArr, Object obj) {
        return binarySearch(objArr, 0, objArr.length, obj);
    }

    public static int binarySearch(Object[] objArr, int i2, int i3, Object obj) {
        checkBinarySearchBounds(i2, i3, objArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int iCompareTo = ((Comparable) objArr[i5]).compareTo(obj);
            if (iCompareTo < 0) {
                i2 = i5 + 1;
            } else {
                if (iCompareTo <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        if (zArr == zArr2) {
            return true;
        }
        if (zArr == null || zArr2 == null || zArr.length != zArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < zArr.length; i2++) {
            if (zArr[i2] != zArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static float[] copyOfRange(float[] fArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = fArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                float[] fArr2 = new float[i4];
                System.arraycopy(fArr, i2, fArr2, 0, iMin);
                return fArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static <T> int binarySearch(T[] tArr, T t2, Comparator<? super T> comparator) {
        return binarySearch(tArr, 0, tArr.length, t2, comparator);
    }

    public static <T> int binarySearch(T[] tArr, int i2, int i3, T t2, Comparator<? super T> comparator) {
        if (comparator == null) {
            return binarySearch(tArr, i2, i3, t2);
        }
        checkBinarySearchBounds(i2, i3, tArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            int iCompare = comparator.compare(tArr[i5], t2);
            if (iCompare < 0) {
                i2 = i5 + 1;
            } else {
                if (iCompare <= 0) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        if (objArr == objArr2) {
            return true;
        }
        if (objArr == null || objArr2 == null || objArr.length != objArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            Object obj2 = objArr2[i2];
            if (obj == null) {
                if (obj2 != null) {
                    return false;
                }
            } else {
                if (!obj.equals(obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String toString(double[] dArr) {
        if (dArr == null) {
            return "null";
        }
        if (dArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(dArr.length * 7);
        sb.append('[');
        sb.append(dArr[0]);
        for (int i2 = 1; i2 < dArr.length; i2++) {
            sb.append(", ");
            sb.append(dArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static int binarySearch(short[] sArr, short s2) {
        return binarySearch(sArr, 0, sArr.length, s2);
    }

    public static int binarySearch(short[] sArr, int i2, int i3, short s2) {
        checkBinarySearchBounds(i2, i3, sArr.length);
        int i4 = i3 - 1;
        while (i2 <= i4) {
            int i5 = (i2 + i4) >>> 1;
            short s3 = sArr[i5];
            if (s3 < s2) {
                i2 = i5 + 1;
            } else {
                if (s3 <= s2) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i2;
    }

    public static int[] copyOfRange(int[] iArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = iArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                int[] iArr2 = new int[i4];
                System.arraycopy(iArr, i2, iArr2, 0, iMin);
                return iArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static long[] copyOfRange(long[] jArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = jArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                long[] jArr2 = new long[i4];
                System.arraycopy(jArr, i2, jArr2, 0, iMin);
                return jArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static String toString(float[] fArr) {
        if (fArr == null) {
            return "null";
        }
        if (fArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(fArr.length * 7);
        sb.append('[');
        sb.append(fArr[0]);
        for (int i2 = 1; i2 < fArr.length; i2++) {
            sb.append(", ");
            sb.append(fArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static short[] copyOfRange(short[] sArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = sArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                short[] sArr2 = new short[i4];
                System.arraycopy(sArr, i2, sArr2, 0, iMin);
                return sArr2;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static String toString(int[] iArr) {
        if (iArr == null) {
            return "null";
        }
        if (iArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(iArr.length * 6);
        sb.append('[');
        sb.append(iArr[0]);
        for (int i2 = 1; i2 < iArr.length; i2++) {
            sb.append(", ");
            sb.append(iArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static <T> T[] copyOfRange(T[] tArr, int i2, int i3) {
        int length = tArr.length;
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        if (i2 >= 0 && i2 <= length) {
            int i4 = i3 - i2;
            int iMin = Math.min(i4, length - i2);
            T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i4));
            System.arraycopy(tArr, i2, tArr2, 0, iMin);
            return tArr2;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public static <T, U> T[] copyOfRange(U[] uArr, int i2, int i3, Class<? extends T[]> cls) {
        if (i2 <= i3) {
            int length = uArr.length;
            if (i2 >= 0 && i2 <= length) {
                int i4 = i3 - i2;
                int iMin = Math.min(i4, length - i2);
                T[] tArr = (T[]) ((Object[]) Array.newInstance(cls.getComponentType(), i4));
                System.arraycopy(uArr, i2, tArr, 0, iMin);
                return tArr;
            }
            throw new ArrayIndexOutOfBoundsException();
        }
        throw new IllegalArgumentException();
    }

    public static String toString(long[] jArr) {
        if (jArr == null) {
            return "null";
        }
        if (jArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(jArr.length * 6);
        sb.append('[');
        sb.append(jArr[0]);
        for (int i2 = 1; i2 < jArr.length; i2++) {
            sb.append(", ");
            sb.append(jArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static String toString(short[] sArr) {
        if (sArr == null) {
            return "null";
        }
        if (sArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(sArr.length * 6);
        sb.append('[');
        sb.append((int) sArr[0]);
        for (int i2 = 1; i2 < sArr.length; i2++) {
            sb.append(", ");
            sb.append((int) sArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static String toString(Object[] objArr) {
        if (objArr == null) {
            return "null";
        }
        if (objArr.length == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder(objArr.length * 7);
        sb.append('[');
        sb.append(objArr[0]);
        for (int i2 = 1; i2 < objArr.length; i2++) {
            sb.append(", ");
            sb.append(objArr[i2]);
        }
        sb.append(']');
        return sb.toString();
    }
}
