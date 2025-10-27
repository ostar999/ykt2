package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class ArrayUtils {
    public static final int INDEX_NOT_FOUND = -1;

    public interface Closure<E> {
        void execute(int i2, E e2);
    }

    private ArrayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @NonNull
    public static <T> T[] add(@Nullable T[] tArr, @Nullable T t2) {
        return (T[]) ((Object[]) realAddOne(tArr, t2, tArr != null ? tArr.getClass() : t2 != null ? t2.getClass() : Object.class));
    }

    @NonNull
    public static <T> List<T> asArrayList(@Nullable T... tArr) {
        ArrayList arrayList = new ArrayList();
        if (tArr != null && tArr.length != 0) {
            arrayList.addAll(Arrays.asList(tArr));
        }
        return arrayList;
    }

    @NonNull
    public static <T> List<T> asLinkedList(@Nullable T... tArr) {
        LinkedList linkedList = new LinkedList();
        if (tArr != null && tArr.length != 0) {
            linkedList.addAll(Arrays.asList(tArr));
        }
        return linkedList;
    }

    @NonNull
    public static <T> List<T> asList(@Nullable T... tArr) {
        return (tArr == null || tArr.length == 0) ? Collections.emptyList() : Arrays.asList(tArr);
    }

    @NonNull
    public static <T> List<T> asUnmodifiableList(@Nullable T... tArr) {
        return Collections.unmodifiableList(asList(tArr));
    }

    public static boolean contains(@Nullable Object[] objArr, @Nullable Object obj) {
        return indexOf(objArr, obj) != -1;
    }

    @Nullable
    public static <T> T[] copy(@Nullable T[] tArr) {
        if (tArr == null) {
            return null;
        }
        return (T[]) subArray(tArr, 0, tArr.length);
    }

    public static boolean equals(@Nullable Object[] objArr, @Nullable Object[] objArr2) {
        return Arrays.deepEquals(objArr, objArr2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void forAllDo(@Nullable Object obj, @Nullable Closure<E> closure) {
        if (obj == null || closure == 0) {
            return;
        }
        int i2 = 0;
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            int length = objArr.length;
            while (i2 < length) {
                closure.execute(i2, objArr[i2]);
                i2++;
            }
            return;
        }
        if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            int length2 = zArr.length;
            while (i2 < length2) {
                closure.execute(i2, zArr[i2] ? Boolean.TRUE : Boolean.FALSE);
                i2++;
            }
            return;
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            int length3 = bArr.length;
            while (i2 < length3) {
                closure.execute(i2, Byte.valueOf(bArr[i2]));
                i2++;
            }
            return;
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            int length4 = cArr.length;
            while (i2 < length4) {
                closure.execute(i2, Character.valueOf(cArr[i2]));
                i2++;
            }
            return;
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            int length5 = sArr.length;
            while (i2 < length5) {
                closure.execute(i2, Short.valueOf(sArr[i2]));
                i2++;
            }
            return;
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            int length6 = iArr.length;
            while (i2 < length6) {
                closure.execute(i2, Integer.valueOf(iArr[i2]));
                i2++;
            }
            return;
        }
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            int length7 = jArr.length;
            while (i2 < length7) {
                closure.execute(i2, Long.valueOf(jArr[i2]));
                i2++;
            }
            return;
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            int length8 = fArr.length;
            while (i2 < length8) {
                closure.execute(i2, Float.valueOf(fArr[i2]));
                i2++;
            }
            return;
        }
        if (!(obj instanceof double[])) {
            throw new IllegalArgumentException("Not an array: " + obj.getClass());
        }
        double[] dArr = (double[]) obj;
        int length9 = dArr.length;
        while (i2 < length9) {
            closure.execute(i2, Double.valueOf(dArr[i2]));
            i2++;
        }
    }

    @Nullable
    public static Object get(@Nullable Object obj, int i2) {
        return get(obj, i2, null);
    }

    public static int getLength(@Nullable Object obj) {
        if (obj == null) {
            return 0;
        }
        return Array.getLength(obj);
    }

    public static int indexOf(@Nullable Object[] objArr, @Nullable Object obj) {
        return indexOf(objArr, obj, 0);
    }

    public static boolean isEmpty(@Nullable Object obj) {
        return getLength(obj) == 0;
    }

    public static boolean isSameLength(@Nullable Object obj, @Nullable Object obj2) {
        return getLength(obj) == getLength(obj2);
    }

    public static int lastIndexOf(@Nullable Object[] objArr, @Nullable Object obj) {
        return lastIndexOf(objArr, obj, Integer.MAX_VALUE);
    }

    @NonNull
    public static <T> T[] newArray(T... tArr) {
        return tArr;
    }

    @NonNull
    public static boolean[] newBooleanArray(boolean... zArr) {
        return zArr;
    }

    @NonNull
    public static byte[] newByteArray(byte... bArr) {
        return bArr;
    }

    @NonNull
    public static char[] newCharArray(char... cArr) {
        return cArr;
    }

    @NonNull
    public static double[] newDoubleArray(double... dArr) {
        return dArr;
    }

    @NonNull
    public static float[] newFloatArray(float... fArr) {
        return fArr;
    }

    @NonNull
    public static int[] newIntArray(int... iArr) {
        return iArr;
    }

    @NonNull
    public static long[] newLongArray(long... jArr) {
        return jArr;
    }

    @NonNull
    public static short[] newShortArray(short... sArr) {
        return sArr;
    }

    @NonNull
    private static Object realAdd(@Nullable Object obj, int i2, @Nullable Object obj2, Class cls) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (obj == null) {
            if (i2 == 0) {
                Object objNewInstance = Array.newInstance((Class<?>) cls, 1);
                Array.set(objNewInstance, 0, obj2);
                return objNewInstance;
            }
            throw new IndexOutOfBoundsException("Index: " + i2 + ", Length: 0");
        }
        int length = Array.getLength(obj);
        if (i2 > length || i2 < 0) {
            throw new IndexOutOfBoundsException("Index: " + i2 + ", Length: " + length);
        }
        Object objNewInstance2 = Array.newInstance((Class<?>) cls, length + 1);
        System.arraycopy(obj, 0, objNewInstance2, 0, i2);
        Array.set(objNewInstance2, i2, obj2);
        if (i2 < length) {
            System.arraycopy(obj, i2, objNewInstance2, i2 + 1, length - i2);
        }
        return objNewInstance2;
    }

    private static Object realAddArr(@Nullable Object obj, @Nullable Object obj2) throws NegativeArraySizeException {
        if (obj == null && obj2 == null) {
            return null;
        }
        if (obj == null) {
            return realCopy(obj2);
        }
        if (obj2 == null) {
            return realCopy(obj);
        }
        int length = getLength(obj);
        int length2 = getLength(obj2);
        Object objNewInstance = Array.newInstance(obj.getClass().getComponentType(), length + length2);
        System.arraycopy(obj, 0, objNewInstance, 0, length);
        System.arraycopy(obj2, 0, objNewInstance, length, length2);
        return objNewInstance;
    }

    @NonNull
    private static Object realAddOne(@Nullable Object obj, @Nullable Object obj2, Class cls) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object objNewInstance;
        int i2 = 0;
        if (obj != null) {
            int length = getLength(obj);
            objNewInstance = Array.newInstance(obj.getClass().getComponentType(), length + 1);
            System.arraycopy(obj, 0, objNewInstance, 0, length);
            i2 = length;
        } else {
            objNewInstance = Array.newInstance((Class<?>) cls, 1);
        }
        Array.set(objNewInstance, i2, obj2);
        return objNewInstance;
    }

    @Nullable
    private static Object realCopy(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        return realSubArray(obj, 0, getLength(obj));
    }

    @Nullable
    private static Object realSubArray(@Nullable Object obj, int i2, int i3) throws NegativeArraySizeException {
        if (obj == null) {
            return null;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        int length = getLength(obj);
        if (i3 > length) {
            i3 = length;
        }
        int i4 = i3 - i2;
        Class<?> componentType = obj.getClass().getComponentType();
        if (i4 <= 0) {
            return Array.newInstance(componentType, 0);
        }
        Object objNewInstance = Array.newInstance(componentType, i4);
        System.arraycopy(obj, i2, objNewInstance, 0, i4);
        return objNewInstance;
    }

    @Nullable
    public static Object[] remove(@Nullable Object[] objArr, int i2) {
        if (objArr == null) {
            return null;
        }
        return (Object[]) remove((Object) objArr, i2);
    }

    @Nullable
    public static Object[] removeElement(@Nullable Object[] objArr, @Nullable Object obj) {
        int iIndexOf = indexOf(objArr, obj);
        return iIndexOf == -1 ? copy(objArr) : remove(objArr, iIndexOf);
    }

    public static <T> void reverse(T[] tArr) {
        if (tArr == null) {
            return;
        }
        int length = tArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            T t2 = tArr[length];
            tArr[length] = tArr[i2];
            tArr[i2] = t2;
            length--;
        }
    }

    public static void set(@Nullable Object obj, int i2, @Nullable Object obj2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (obj == null) {
            return;
        }
        Array.set(obj, i2, obj2);
    }

    public static <T> void sort(@Nullable T[] tArr, Comparator<? super T> comparator) {
        if (tArr == null || tArr.length < 2) {
            return;
        }
        Arrays.sort(tArr, comparator);
    }

    @Nullable
    public static <T> T[] subArray(@Nullable T[] tArr, int i2, int i3) {
        return (T[]) ((Object[]) realSubArray(tArr, i2, i3));
    }

    @Nullable
    public static Character[] toObject(@Nullable char[] cArr) {
        if (cArr == null) {
            return null;
        }
        if (cArr.length == 0) {
            return new Character[0];
        }
        Character[] chArr = new Character[cArr.length];
        for (int i2 = 0; i2 < cArr.length; i2++) {
            chArr[i2] = new Character(cArr[i2]);
        }
        return chArr;
    }

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] chArr) {
        if (chArr == null) {
            return null;
        }
        if (chArr.length == 0) {
            return new char[0];
        }
        char[] cArr = new char[chArr.length];
        for (int i2 = 0; i2 < chArr.length; i2++) {
            cArr[i2] = chArr[i2].charValue();
        }
        return cArr;
    }

    @NonNull
    public static String toString(@Nullable Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Object[]) {
            return Arrays.deepToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return Arrays.toString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return Arrays.toString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return Arrays.toString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return Arrays.toString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return Arrays.toString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return Arrays.toString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return Arrays.toString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return Arrays.toString((short[]) obj);
        }
        throw new IllegalArgumentException("Array has incompatible type: " + obj.getClass());
    }

    public static boolean contains(@Nullable long[] jArr, long j2) {
        return indexOf(jArr, j2) != -1;
    }

    @Nullable
    public static long[] copy(@Nullable long[] jArr) {
        if (jArr == null) {
            return null;
        }
        return subArray(jArr, 0, jArr.length);
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        return Arrays.equals(zArr, zArr2);
    }

    @Nullable
    public static Object get(@Nullable Object obj, int i2, @Nullable Object obj2) {
        if (obj == null) {
            return obj2;
        }
        try {
            return Array.get(obj, i2);
        } catch (Exception unused) {
            return obj2;
        }
    }

    public static int indexOf(@Nullable Object[] objArr, @Nullable Object obj, int i2) {
        if (objArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (obj == null) {
            while (i2 < objArr.length) {
                if (objArr[i2] == null) {
                    return i2;
                }
                i2++;
            }
        } else {
            while (i2 < objArr.length) {
                if (obj.equals(objArr[i2])) {
                    return i2;
                }
                i2++;
            }
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable Object[] objArr, @Nullable Object obj, int i2) {
        if (objArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= objArr.length) {
            i2 = objArr.length - 1;
        }
        if (obj == null) {
            while (i2 >= 0) {
                if (objArr[i2] == null) {
                    return i2;
                }
                i2--;
            }
        } else {
            while (i2 >= 0) {
                if (obj.equals(objArr[i2])) {
                    return i2;
                }
                i2--;
            }
        }
        return -1;
    }

    @Nullable
    public static boolean[] remove(@Nullable boolean[] zArr, int i2) {
        if (zArr == null) {
            return null;
        }
        return (boolean[]) remove((Object) zArr, i2);
    }

    @Nullable
    public static long[] subArray(@Nullable long[] jArr, int i2, int i3) {
        return (long[]) realSubArray(jArr, i2, i3);
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] zArr, boolean z2) {
        return (boolean[]) realAddOne(zArr, Boolean.valueOf(z2), Boolean.TYPE);
    }

    public static boolean contains(@Nullable int[] iArr, int i2) {
        return indexOf(iArr, i2) != -1;
    }

    @Nullable
    public static int[] copy(@Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        return subArray(iArr, 0, iArr.length);
    }

    public static boolean equals(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }

    @Nullable
    public static byte[] remove(@Nullable byte[] bArr, int i2) {
        if (bArr == null) {
            return null;
        }
        return (byte[]) remove((Object) bArr, i2);
    }

    public static void sort(@Nullable byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return;
        }
        Arrays.sort(bArr);
    }

    @Nullable
    public static int[] subArray(@Nullable int[] iArr, int i2, int i3) {
        return (int[]) realSubArray(iArr, i2, i3);
    }

    @NonNull
    public static byte[] add(@Nullable byte[] bArr, byte b3) {
        return (byte[]) realAddOne(bArr, Byte.valueOf(b3), Byte.TYPE);
    }

    public static boolean contains(@Nullable short[] sArr, short s2) {
        return indexOf(sArr, s2) != -1;
    }

    @Nullable
    public static short[] copy(@Nullable short[] sArr) {
        if (sArr == null) {
            return null;
        }
        return subArray(sArr, 0, sArr.length);
    }

    public static boolean equals(char[] cArr, char[] cArr2) {
        return Arrays.equals(cArr, cArr2);
    }

    @Nullable
    public static char[] remove(@Nullable char[] cArr, int i2) {
        if (cArr == null) {
            return null;
        }
        return (char[]) remove((Object) cArr, i2);
    }

    @Nullable
    public static boolean[] removeElement(@Nullable boolean[] zArr, boolean z2) {
        int iIndexOf = indexOf(zArr, z2);
        if (iIndexOf == -1) {
            return copy(zArr);
        }
        return remove(zArr, iIndexOf);
    }

    @Nullable
    public static short[] subArray(@Nullable short[] sArr, int i2, int i3) {
        return (short[]) realSubArray(sArr, i2, i3);
    }

    @NonNull
    public static char[] add(@Nullable char[] cArr, char c3) {
        return (char[]) realAddOne(cArr, Character.valueOf(c3), Character.TYPE);
    }

    public static boolean contains(@Nullable char[] cArr, char c3) {
        return indexOf(cArr, c3) != -1;
    }

    @Nullable
    public static char[] copy(@Nullable char[] cArr) {
        if (cArr == null) {
            return null;
        }
        return subArray(cArr, 0, cArr.length);
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        return Arrays.equals(dArr, dArr2);
    }

    @Nullable
    public static double[] remove(@Nullable double[] dArr, int i2) {
        if (dArr == null) {
            return null;
        }
        return (double[]) remove((Object) dArr, i2);
    }

    public static void reverse(long[] jArr) {
        if (jArr == null) {
            return;
        }
        int length = jArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            long j2 = jArr[length];
            jArr[length] = jArr[i2];
            jArr[i2] = j2;
            length--;
        }
    }

    public static void sort(@Nullable char[] cArr) {
        if (cArr == null || cArr.length < 2) {
            return;
        }
        Arrays.sort(cArr);
    }

    @Nullable
    public static char[] subArray(@Nullable char[] cArr, int i2, int i3) {
        return (char[]) realSubArray(cArr, i2, i3);
    }

    @Nullable
    public static Long[] toObject(@Nullable long[] jArr) {
        if (jArr == null) {
            return null;
        }
        if (jArr.length == 0) {
            return new Long[0];
        }
        Long[] lArr = new Long[jArr.length];
        for (int i2 = 0; i2 < jArr.length; i2++) {
            lArr[i2] = new Long(jArr[i2]);
        }
        return lArr;
    }

    @Nullable
    public static char[] toPrimitive(@Nullable Character[] chArr, char c3) {
        if (chArr == null) {
            return null;
        }
        if (chArr.length == 0) {
            return new char[0];
        }
        char[] cArr = new char[chArr.length];
        for (int i2 = 0; i2 < chArr.length; i2++) {
            Character ch = chArr[i2];
            cArr[i2] = ch == null ? c3 : ch.charValue();
        }
        return cArr;
    }

    @NonNull
    public static double[] add(@Nullable double[] dArr, double d3) {
        return (double[]) realAddOne(dArr, Double.valueOf(d3), Double.TYPE);
    }

    public static boolean contains(@Nullable byte[] bArr, byte b3) {
        return indexOf(bArr, b3) != -1;
    }

    @Nullable
    public static byte[] copy(@Nullable byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return subArray(bArr, 0, bArr.length);
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        return Arrays.equals(fArr, fArr2);
    }

    public static int indexOf(@Nullable long[] jArr, long j2) {
        return indexOf(jArr, j2, 0);
    }

    public static int lastIndexOf(@Nullable long[] jArr, long j2) {
        return lastIndexOf(jArr, j2, Integer.MAX_VALUE);
    }

    @Nullable
    public static float[] remove(@Nullable float[] fArr, int i2) {
        if (fArr == null) {
            return null;
        }
        return (float[]) remove((Object) fArr, i2);
    }

    @Nullable
    public static byte[] subArray(@Nullable byte[] bArr, int i2, int i3) {
        return (byte[]) realSubArray(bArr, i2, i3);
    }

    @NonNull
    public static float[] add(@Nullable float[] fArr, float f2) {
        return (float[]) realAddOne(fArr, Float.valueOf(f2), Float.TYPE);
    }

    public static boolean contains(@Nullable double[] dArr, double d3) {
        return indexOf(dArr, d3) != -1;
    }

    @Nullable
    public static double[] copy(@Nullable double[] dArr) {
        if (dArr == null) {
            return null;
        }
        return subArray(dArr, 0, dArr.length);
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        return Arrays.equals(iArr, iArr2);
    }

    public static int indexOf(@Nullable long[] jArr, long j2, int i2) {
        if (jArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < jArr.length) {
            if (j2 == jArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable long[] jArr, long j2, int i2) {
        if (jArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= jArr.length) {
            i2 = jArr.length - 1;
        }
        while (i2 >= 0) {
            if (j2 == jArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static int[] remove(@Nullable int[] iArr, int i2) {
        if (iArr == null) {
            return null;
        }
        return (int[]) remove((Object) iArr, i2);
    }

    @Nullable
    public static byte[] removeElement(@Nullable byte[] bArr, byte b3) {
        int iIndexOf = indexOf(bArr, b3);
        if (iIndexOf == -1) {
            return copy(bArr);
        }
        return remove(bArr, iIndexOf);
    }

    public static void sort(@Nullable double[] dArr) {
        if (dArr == null || dArr.length < 2) {
            return;
        }
        Arrays.sort(dArr);
    }

    @Nullable
    public static double[] subArray(@Nullable double[] dArr, int i2, int i3) {
        return (double[]) realSubArray(dArr, i2, i3);
    }

    @NonNull
    public static int[] add(@Nullable int[] iArr, int i2) {
        return (int[]) realAddOne(iArr, Integer.valueOf(i2), Integer.TYPE);
    }

    public static boolean contains(@Nullable double[] dArr, double d3, double d4) {
        return indexOf(dArr, d3, 0, d4) != -1;
    }

    @Nullable
    public static float[] copy(@Nullable float[] fArr) {
        if (fArr == null) {
            return null;
        }
        return subArray(fArr, 0, fArr.length);
    }

    public static boolean equals(short[] sArr, short[] sArr2) {
        return Arrays.equals(sArr, sArr2);
    }

    @Nullable
    private static Object realAddArr(@Nullable Object obj, int i2, @Nullable Object obj2, Class cls) throws NegativeArraySizeException {
        if (obj == null && obj2 == null) {
            return null;
        }
        int length = getLength(obj);
        int length2 = getLength(obj2);
        if (length == 0) {
            if (i2 == 0) {
                return realCopy(obj2);
            }
            throw new IndexOutOfBoundsException("Index: " + i2 + ", array1 Length: 0");
        }
        if (length2 == 0) {
            return realCopy(obj);
        }
        if (i2 <= length && i2 >= 0) {
            Object objNewInstance = Array.newInstance(obj.getClass().getComponentType(), length + length2);
            if (i2 == length) {
                System.arraycopy(obj, 0, objNewInstance, 0, length);
                System.arraycopy(obj2, 0, objNewInstance, length, length2);
            } else if (i2 == 0) {
                System.arraycopy(obj2, 0, objNewInstance, 0, length2);
                System.arraycopy(obj, 0, objNewInstance, length2, length);
            } else {
                System.arraycopy(obj, 0, objNewInstance, 0, i2);
                System.arraycopy(obj2, 0, objNewInstance, i2, length2);
                System.arraycopy(obj, i2, objNewInstance, length2 + i2, length - i2);
            }
            return objNewInstance;
        }
        throw new IndexOutOfBoundsException("Index: " + i2 + ", array1 Length: " + length);
    }

    @Nullable
    public static long[] remove(@Nullable long[] jArr, int i2) {
        if (jArr == null) {
            return null;
        }
        return (long[]) remove((Object) jArr, i2);
    }

    @Nullable
    public static float[] subArray(@Nullable float[] fArr, int i2, int i3) {
        return (float[]) realSubArray(fArr, i2, i3);
    }

    @NonNull
    public static long[] add(@Nullable long[] jArr, long j2) {
        return (long[]) realAddOne(jArr, Long.valueOf(j2), Long.TYPE);
    }

    public static boolean contains(@Nullable float[] fArr, float f2) {
        return indexOf(fArr, f2) != -1;
    }

    @Nullable
    public static boolean[] copy(@Nullable boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        return subArray(zArr, 0, zArr.length);
    }

    public static int indexOf(@Nullable int[] iArr, int i2) {
        return indexOf(iArr, i2, 0);
    }

    @Nullable
    public static short[] remove(@Nullable short[] sArr, int i2) {
        if (sArr == null) {
            return null;
        }
        return (short[]) remove((Object) sArr, i2);
    }

    public static void reverse(int[] iArr) {
        if (iArr == null) {
            return;
        }
        int length = iArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            int i3 = iArr[length];
            iArr[length] = iArr[i2];
            iArr[i2] = i3;
            length--;
        }
    }

    public static void sort(@Nullable float[] fArr) {
        if (fArr == null || fArr.length < 2) {
            return;
        }
        Arrays.sort(fArr);
    }

    @Nullable
    public static boolean[] subArray(@Nullable boolean[] zArr, int i2, int i3) {
        return (boolean[]) realSubArray(zArr, i2, i3);
    }

    @Nullable
    public static Integer[] toObject(@Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        if (iArr.length == 0) {
            return new Integer[0];
        }
        Integer[] numArr = new Integer[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i2] = new Integer(iArr[i2]);
        }
        return numArr;
    }

    @NonNull
    public static short[] add(@Nullable short[] sArr, short s2) {
        return (short[]) realAddOne(sArr, Short.valueOf(s2), Short.TYPE);
    }

    public static boolean contains(@Nullable boolean[] zArr, boolean z2) {
        return indexOf(zArr, z2) != -1;
    }

    public static int indexOf(@Nullable int[] iArr, int i2, int i3) {
        if (iArr == null) {
            return -1;
        }
        if (i3 < 0) {
            i3 = 0;
        }
        while (i3 < iArr.length) {
            if (i2 == iArr[i3]) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable int[] iArr, int i2) {
        return lastIndexOf(iArr, i2, Integer.MAX_VALUE);
    }

    @NonNull
    private static Object remove(@NonNull Object obj, int i2) throws NegativeArraySizeException {
        int length = getLength(obj);
        if (i2 >= 0 && i2 < length) {
            int i3 = length - 1;
            Object objNewInstance = Array.newInstance(obj.getClass().getComponentType(), i3);
            System.arraycopy(obj, 0, objNewInstance, 0, i2);
            if (i2 < i3) {
                System.arraycopy(obj, i2 + 1, objNewInstance, i2, (length - i2) - 1);
            }
            return objNewInstance;
        }
        throw new IndexOutOfBoundsException("Index: " + i2 + ", Length: " + length);
    }

    @Nullable
    public static char[] removeElement(@Nullable char[] cArr, char c3) {
        int iIndexOf = indexOf(cArr, c3);
        if (iIndexOf == -1) {
            return copy(cArr);
        }
        return remove(cArr, iIndexOf);
    }

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] lArr) {
        if (lArr == null) {
            return null;
        }
        if (lArr.length == 0) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        for (int i2 = 0; i2 < lArr.length; i2++) {
            jArr[i2] = lArr[i2].longValue();
        }
        return jArr;
    }

    @Nullable
    public static <T> T[] add(@Nullable T[] tArr, @Nullable T[] tArr2) {
        return (T[]) ((Object[]) realAddArr(tArr, tArr2));
    }

    public static int lastIndexOf(@Nullable int[] iArr, int i2, int i3) {
        if (iArr == null || i3 < 0) {
            return -1;
        }
        if (i3 >= iArr.length) {
            i3 = iArr.length - 1;
        }
        while (i3 >= 0) {
            if (i2 == iArr[i3]) {
                return i3;
            }
            i3--;
        }
        return -1;
    }

    public static void sort(@Nullable int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            return;
        }
        Arrays.sort(iArr);
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] zArr, @Nullable boolean[] zArr2) {
        return (boolean[]) realAddArr(zArr, zArr2);
    }

    public static int indexOf(@Nullable short[] sArr, short s2) {
        return indexOf(sArr, s2, 0);
    }

    @Nullable
    public static char[] add(@Nullable char[] cArr, @Nullable char[] cArr2) {
        return (char[]) realAddArr(cArr, cArr2);
    }

    public static int indexOf(@Nullable short[] sArr, short s2, int i2) {
        if (sArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < sArr.length) {
            if (s2 == sArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Nullable
    public static double[] removeElement(@Nullable double[] dArr, double d3) {
        int iIndexOf = indexOf(dArr, d3);
        if (iIndexOf == -1) {
            return copy(dArr);
        }
        return remove(dArr, iIndexOf);
    }

    public static void reverse(short[] sArr) {
        if (sArr == null) {
            return;
        }
        int length = sArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            short s2 = sArr[length];
            sArr[length] = sArr[i2];
            sArr[i2] = s2;
            length--;
        }
    }

    public static void sort(@Nullable long[] jArr) {
        if (jArr == null || jArr.length < 2) {
            return;
        }
        Arrays.sort(jArr);
    }

    @Nullable
    public static Short[] toObject(@Nullable short[] sArr) {
        if (sArr == null) {
            return null;
        }
        if (sArr.length == 0) {
            return new Short[0];
        }
        Short[] shArr = new Short[sArr.length];
        for (int i2 = 0; i2 < sArr.length; i2++) {
            shArr[i2] = new Short(sArr[i2]);
        }
        return shArr;
    }

    @Nullable
    public static byte[] add(@Nullable byte[] bArr, @Nullable byte[] bArr2) {
        return (byte[]) realAddArr(bArr, bArr2);
    }

    public static int lastIndexOf(@Nullable short[] sArr, short s2) {
        return lastIndexOf(sArr, s2, Integer.MAX_VALUE);
    }

    @Nullable
    public static long[] toPrimitive(@Nullable Long[] lArr, long j2) {
        if (lArr == null) {
            return null;
        }
        if (lArr.length == 0) {
            return new long[0];
        }
        long[] jArr = new long[lArr.length];
        for (int i2 = 0; i2 < lArr.length; i2++) {
            Long l2 = lArr[i2];
            jArr[i2] = l2 == null ? j2 : l2.longValue();
        }
        return jArr;
    }

    @Nullable
    public static short[] add(@Nullable short[] sArr, @Nullable short[] sArr2) {
        return (short[]) realAddArr(sArr, sArr2);
    }

    public static int indexOf(@Nullable char[] cArr, char c3) {
        return indexOf(cArr, c3, 0);
    }

    public static int lastIndexOf(@Nullable short[] sArr, short s2, int i2) {
        if (sArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= sArr.length) {
            i2 = sArr.length - 1;
        }
        while (i2 >= 0) {
            if (s2 == sArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    public static void sort(@Nullable short[] sArr) {
        if (sArr == null || sArr.length < 2) {
            return;
        }
        Arrays.sort(sArr);
    }

    @Nullable
    public static int[] add(@Nullable int[] iArr, @Nullable int[] iArr2) {
        return (int[]) realAddArr(iArr, iArr2);
    }

    public static int indexOf(@Nullable char[] cArr, char c3, int i2) {
        if (cArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < cArr.length) {
            if (c3 == cArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Nullable
    public static float[] removeElement(@Nullable float[] fArr, float f2) {
        int iIndexOf = indexOf(fArr, f2);
        if (iIndexOf == -1) {
            return copy(fArr);
        }
        return remove(fArr, iIndexOf);
    }

    @Nullable
    public static long[] add(@Nullable long[] jArr, @Nullable long[] jArr2) {
        return (long[]) realAddArr(jArr, jArr2);
    }

    public static void reverse(char[] cArr) {
        if (cArr == null) {
            return;
        }
        int length = cArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            char c3 = cArr[length];
            cArr[length] = cArr[i2];
            cArr[i2] = c3;
            length--;
        }
    }

    @Nullable
    public static Byte[] toObject(@Nullable byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new Byte[0];
        }
        Byte[] bArr2 = new Byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = new Byte(bArr[i2]);
        }
        return bArr2;
    }

    @Nullable
    public static float[] add(@Nullable float[] fArr, @Nullable float[] fArr2) {
        return (float[]) realAddArr(fArr, fArr2);
    }

    public static int indexOf(@Nullable byte[] bArr, byte b3) {
        return indexOf(bArr, b3, 0);
    }

    public static int lastIndexOf(@Nullable char[] cArr, char c3) {
        return lastIndexOf(cArr, c3, Integer.MAX_VALUE);
    }

    @Nullable
    public static double[] add(@Nullable double[] dArr, @Nullable double[] dArr2) {
        return (double[]) realAddArr(dArr, dArr2);
    }

    public static int indexOf(@Nullable byte[] bArr, byte b3, int i2) {
        if (bArr == null) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < bArr.length) {
            if (b3 == bArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable char[] cArr, char c3, int i2) {
        if (cArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= cArr.length) {
            i2 = cArr.length - 1;
        }
        while (i2 >= 0) {
            if (c3 == cArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static int[] removeElement(@Nullable int[] iArr, int i2) {
        int iIndexOf = indexOf(iArr, i2);
        if (iIndexOf == -1) {
            return copy(iArr);
        }
        return remove(iArr, iIndexOf);
    }

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] numArr) {
        if (numArr == null) {
            return null;
        }
        if (numArr.length == 0) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            iArr[i2] = numArr[i2].intValue();
        }
        return iArr;
    }

    @Nullable
    public static <T> T[] add(@Nullable T[] tArr, int i2, @Nullable T[] tArr2) {
        Class<?> componentType;
        if (tArr != null) {
            componentType = tArr.getClass().getComponentType();
        } else {
            if (tArr2 == null) {
                return null;
            }
            componentType = tArr2.getClass().getComponentType();
        }
        return (T[]) ((Object[]) realAddArr(tArr, i2, tArr2, componentType));
    }

    public static int indexOf(@Nullable double[] dArr, double d3) {
        return indexOf(dArr, d3, 0);
    }

    public static void reverse(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        int length = bArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            byte b3 = bArr[length];
            bArr[length] = bArr[i2];
            bArr[i2] = b3;
            length--;
        }
    }

    @Nullable
    public static Double[] toObject(@Nullable double[] dArr) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new Double[0];
        }
        Double[] dArr2 = new Double[dArr.length];
        for (int i2 = 0; i2 < dArr.length; i2++) {
            dArr2[i2] = new Double(dArr[i2]);
        }
        return dArr2;
    }

    public static int indexOf(@Nullable double[] dArr, double d3, double d4) {
        return indexOf(dArr, d3, 0, d4);
    }

    public static int lastIndexOf(@Nullable byte[] bArr, byte b3) {
        return lastIndexOf(bArr, b3, Integer.MAX_VALUE);
    }

    @Nullable
    public static long[] removeElement(@Nullable long[] jArr, long j2) {
        int iIndexOf = indexOf(jArr, j2);
        if (iIndexOf == -1) {
            return copy(jArr);
        }
        return remove(jArr, iIndexOf);
    }

    @Nullable
    public static boolean[] add(@Nullable boolean[] zArr, int i2, @Nullable boolean[] zArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(zArr, i2, zArr2, Boolean.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (boolean[]) objRealAddArr;
    }

    public static int indexOf(@Nullable double[] dArr, double d3, int i2) {
        if (isEmpty(dArr)) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < dArr.length) {
            if (d3 == dArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable byte[] bArr, byte b3, int i2) {
        if (bArr == null || i2 < 0) {
            return -1;
        }
        if (i2 >= bArr.length) {
            i2 = bArr.length - 1;
        }
        while (i2 >= 0) {
            if (b3 == bArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static int[] toPrimitive(@Nullable Integer[] numArr, int i2) {
        if (numArr == null) {
            return null;
        }
        if (numArr.length == 0) {
            return new int[0];
        }
        int[] iArr = new int[numArr.length];
        for (int i3 = 0; i3 < numArr.length; i3++) {
            Integer num = numArr[i3];
            iArr[i3] = num == null ? i2 : num.intValue();
        }
        return iArr;
    }

    public static char[] add(@Nullable char[] cArr, int i2, @Nullable char[] cArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(cArr, i2, cArr2, Character.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (char[]) objRealAddArr;
    }

    @Nullable
    public static short[] removeElement(@Nullable short[] sArr, short s2) {
        int iIndexOf = indexOf(sArr, s2);
        if (iIndexOf == -1) {
            return copy(sArr);
        }
        return remove(sArr, iIndexOf);
    }

    public static void reverse(double[] dArr) {
        if (dArr == null) {
            return;
        }
        int length = dArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            double d3 = dArr[length];
            dArr[length] = dArr[i2];
            dArr[i2] = d3;
            length--;
        }
    }

    @Nullable
    public static Float[] toObject(@Nullable float[] fArr) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new Float[0];
        }
        Float[] fArr2 = new Float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr2[i2] = new Float(fArr[i2]);
        }
        return fArr2;
    }

    public static int indexOf(@Nullable double[] dArr, double d3, int i2, double d4) {
        if (isEmpty(dArr)) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        double d5 = d3 - d4;
        double d6 = d3 + d4;
        while (i2 < dArr.length) {
            double d7 = dArr[i2];
            if (d7 >= d5 && d7 <= d6) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d3) {
        return lastIndexOf(dArr, d3, Integer.MAX_VALUE);
    }

    @Nullable
    public static byte[] add(@Nullable byte[] bArr, int i2, @Nullable byte[] bArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(bArr, i2, bArr2, Byte.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (byte[]) objRealAddArr;
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d3, double d4) {
        return lastIndexOf(dArr, d3, Integer.MAX_VALUE, d4);
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d3, int i2) {
        if (isEmpty(dArr) || i2 < 0) {
            return -1;
        }
        if (i2 >= dArr.length) {
            i2 = dArr.length - 1;
        }
        while (i2 >= 0) {
            if (d3 == dArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] shArr) {
        if (shArr == null) {
            return null;
        }
        if (shArr.length == 0) {
            return new short[0];
        }
        short[] sArr = new short[shArr.length];
        for (int i2 = 0; i2 < shArr.length; i2++) {
            sArr[i2] = shArr[i2].shortValue();
        }
        return sArr;
    }

    @Nullable
    public static short[] add(@Nullable short[] sArr, int i2, @Nullable short[] sArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(sArr, i2, sArr2, Short.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (short[]) objRealAddArr;
    }

    public static int indexOf(@Nullable float[] fArr, float f2) {
        return indexOf(fArr, f2, 0);
    }

    public static void reverse(float[] fArr) {
        if (fArr == null) {
            return;
        }
        int length = fArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            float f2 = fArr[length];
            fArr[length] = fArr[i2];
            fArr[i2] = f2;
            length--;
        }
    }

    @Nullable
    public static Boolean[] toObject(@Nullable boolean[] zArr) {
        if (zArr == null) {
            return null;
        }
        if (zArr.length == 0) {
            return new Boolean[0];
        }
        Boolean[] boolArr = new Boolean[zArr.length];
        for (int i2 = 0; i2 < zArr.length; i2++) {
            boolArr[i2] = zArr[i2] ? Boolean.TRUE : Boolean.FALSE;
        }
        return boolArr;
    }

    public static int indexOf(@Nullable float[] fArr, float f2, int i2) {
        if (isEmpty(fArr)) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < fArr.length) {
            if (f2 == fArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Nullable
    public static int[] add(@Nullable int[] iArr, int i2, @Nullable int[] iArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(iArr, i2, iArr2, Integer.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (int[]) objRealAddArr;
    }

    public static int lastIndexOf(@Nullable double[] dArr, double d3, int i2, double d4) {
        if (isEmpty(dArr) || i2 < 0) {
            return -1;
        }
        if (i2 >= dArr.length) {
            i2 = dArr.length - 1;
        }
        double d5 = d3 - d4;
        double d6 = d3 + d4;
        while (i2 >= 0) {
            double d7 = dArr[i2];
            if (d7 >= d5 && d7 <= d6) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static short[] toPrimitive(@Nullable Short[] shArr, short s2) {
        if (shArr == null) {
            return null;
        }
        if (shArr.length == 0) {
            return new short[0];
        }
        short[] sArr = new short[shArr.length];
        for (int i2 = 0; i2 < shArr.length; i2++) {
            Short sh = shArr[i2];
            sArr[i2] = sh == null ? s2 : sh.shortValue();
        }
        return sArr;
    }

    @Nullable
    public static long[] add(@Nullable long[] jArr, int i2, @Nullable long[] jArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(jArr, i2, jArr2, Long.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (long[]) objRealAddArr;
    }

    public static int indexOf(@Nullable boolean[] zArr, boolean z2) {
        return indexOf(zArr, z2, 0);
    }

    public static void reverse(boolean[] zArr) {
        if (zArr == null) {
            return;
        }
        int length = zArr.length - 1;
        for (int i2 = 0; length > i2; i2++) {
            boolean z2 = zArr[length];
            zArr[length] = zArr[i2];
            zArr[i2] = z2;
            length--;
        }
    }

    public static int indexOf(@Nullable boolean[] zArr, boolean z2, int i2) {
        if (isEmpty(zArr)) {
            return -1;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < zArr.length) {
            if (z2 == zArr[i2]) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Nullable
    public static float[] add(@Nullable float[] fArr, int i2, @Nullable float[] fArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(fArr, i2, fArr2, Float.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (float[]) objRealAddArr;
    }

    public static int lastIndexOf(@Nullable float[] fArr, float f2) {
        return lastIndexOf(fArr, f2, Integer.MAX_VALUE);
    }

    @Nullable
    public static double[] add(@Nullable double[] dArr, int i2, @Nullable double[] dArr2) throws NegativeArraySizeException {
        Object objRealAddArr = realAddArr(dArr, i2, dArr2, Double.TYPE);
        if (objRealAddArr == null) {
            return null;
        }
        return (double[]) objRealAddArr;
    }

    public static int lastIndexOf(@Nullable float[] fArr, float f2, int i2) {
        if (isEmpty(fArr) || i2 < 0) {
            return -1;
        }
        if (i2 >= fArr.length) {
            i2 = fArr.length - 1;
        }
        while (i2 >= 0) {
            if (f2 == fArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = bArr[i2].byteValue();
        }
        return bArr2;
    }

    @NonNull
    public static <T> T[] add(@Nullable T[] tArr, int i2, @Nullable T t2) {
        Class<?> componentType;
        if (tArr != null) {
            componentType = tArr.getClass().getComponentType();
        } else {
            if (t2 == null) {
                T[] tArr2 = (T[]) new Object[1];
                tArr2[0] = null;
                return tArr2;
            }
            componentType = t2.getClass();
        }
        return (T[]) ((Object[]) realAdd(tArr, i2, t2, componentType));
    }

    public static int lastIndexOf(@Nullable boolean[] zArr, boolean z2) {
        return lastIndexOf(zArr, z2, Integer.MAX_VALUE);
    }

    @Nullable
    public static byte[] toPrimitive(@Nullable Byte[] bArr, byte b3) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            Byte b4 = bArr[i2];
            bArr2[i2] = b4 == null ? b3 : b4.byteValue();
        }
        return bArr2;
    }

    @NonNull
    public static boolean[] add(@Nullable boolean[] zArr, int i2, boolean z2) {
        return (boolean[]) realAdd(zArr, i2, Boolean.valueOf(z2), Boolean.TYPE);
    }

    public static int lastIndexOf(@Nullable boolean[] zArr, boolean z2, int i2) {
        if (isEmpty(zArr) || i2 < 0) {
            return -1;
        }
        if (i2 >= zArr.length) {
            i2 = zArr.length - 1;
        }
        while (i2 >= 0) {
            if (z2 == zArr[i2]) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    @NonNull
    public static char[] add(@Nullable char[] cArr, int i2, char c3) {
        return (char[]) realAdd(cArr, i2, Character.valueOf(c3), Character.TYPE);
    }

    @NonNull
    public static byte[] add(@Nullable byte[] bArr, int i2, byte b3) {
        return (byte[]) realAdd(bArr, i2, Byte.valueOf(b3), Byte.TYPE);
    }

    @NonNull
    public static short[] add(@Nullable short[] sArr, int i2, short s2) {
        return (short[]) realAdd(sArr, i2, Short.valueOf(s2), Short.TYPE);
    }

    @NonNull
    public static int[] add(@Nullable int[] iArr, int i2, int i3) {
        return (int[]) realAdd(iArr, i2, Integer.valueOf(i3), Integer.TYPE);
    }

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] dArr) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new double[0];
        }
        double[] dArr2 = new double[dArr.length];
        for (int i2 = 0; i2 < dArr.length; i2++) {
            dArr2[i2] = dArr[i2].doubleValue();
        }
        return dArr2;
    }

    @NonNull
    public static long[] add(@Nullable long[] jArr, int i2, long j2) {
        return (long[]) realAdd(jArr, i2, Long.valueOf(j2), Long.TYPE);
    }

    @NonNull
    public static float[] add(@Nullable float[] fArr, int i2, float f2) {
        return (float[]) realAdd(fArr, i2, Float.valueOf(f2), Float.TYPE);
    }

    @NonNull
    public static double[] add(@Nullable double[] dArr, int i2, double d3) {
        return (double[]) realAdd(dArr, i2, Double.valueOf(d3), Double.TYPE);
    }

    @Nullable
    public static double[] toPrimitive(@Nullable Double[] dArr, double d3) {
        if (dArr == null) {
            return null;
        }
        if (dArr.length == 0) {
            return new double[0];
        }
        double[] dArr2 = new double[dArr.length];
        for (int i2 = 0; i2 < dArr.length; i2++) {
            Double d4 = dArr[i2];
            dArr2[i2] = d4 == null ? d3 : d4.doubleValue();
        }
        return dArr2;
    }

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] fArr) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new float[0];
        }
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr2[i2] = fArr[i2].floatValue();
        }
        return fArr2;
    }

    @Nullable
    public static float[] toPrimitive(@Nullable Float[] fArr, float f2) {
        if (fArr == null) {
            return null;
        }
        if (fArr.length == 0) {
            return new float[0];
        }
        float[] fArr2 = new float[fArr.length];
        for (int i2 = 0; i2 < fArr.length; i2++) {
            Float f3 = fArr[i2];
            fArr2[i2] = f3 == null ? f2 : f3.floatValue();
        }
        return fArr2;
    }

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] boolArr) {
        if (boolArr == null) {
            return null;
        }
        if (boolArr.length == 0) {
            return new boolean[0];
        }
        boolean[] zArr = new boolean[boolArr.length];
        for (int i2 = 0; i2 < boolArr.length; i2++) {
            zArr[i2] = boolArr[i2].booleanValue();
        }
        return zArr;
    }

    @Nullable
    public static boolean[] toPrimitive(@Nullable Boolean[] boolArr, boolean z2) {
        if (boolArr == null) {
            return null;
        }
        if (boolArr.length == 0) {
            return new boolean[0];
        }
        boolean[] zArr = new boolean[boolArr.length];
        for (int i2 = 0; i2 < boolArr.length; i2++) {
            Boolean bool = boolArr[i2];
            zArr[i2] = bool == null ? z2 : bool.booleanValue();
        }
        return zArr;
    }
}
