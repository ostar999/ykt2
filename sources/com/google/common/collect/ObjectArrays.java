package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
public final class ObjectArrays {
    private ObjectArrays() {
    }

    @CanIgnoreReturnValue
    public static Object checkElementNotNull(Object obj, int i2) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("at index " + i2);
    }

    @CanIgnoreReturnValue
    public static Object[] checkElementsNotNull(Object... objArr) {
        return checkElementsNotNull(objArr, objArr.length);
    }

    @GwtIncompatible
    public static <T> T[] concat(T[] tArr, T[] tArr2, Class<T> cls) {
        T[] tArr3 = (T[]) newArray(cls, tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, tArr3, 0, tArr.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    public static Object[] copyAsObjectArray(Object[] objArr, int i2, int i3) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, objArr.length);
        if (i3 == 0) {
            return new Object[0];
        }
        Object[] objArr2 = new Object[i3];
        System.arraycopy(objArr, i2, objArr2, 0, i3);
        return objArr2;
    }

    @CanIgnoreReturnValue
    private static Object[] fillArray(Iterable<?> iterable, Object[] objArr) {
        Iterator<?> it = iterable.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            objArr[i2] = it.next();
            i2++;
        }
        return objArr;
    }

    @GwtIncompatible
    public static <T> T[] newArray(Class<T> cls, int i2) {
        return (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i2));
    }

    public static void swap(Object[] objArr, int i2, int i3) {
        Object obj = objArr[i2];
        objArr[i2] = objArr[i3];
        objArr[i3] = obj;
    }

    public static <T> T[] toArrayImpl(Collection<?> collection, T[] tArr) {
        int size = collection.size();
        if (tArr.length < size) {
            tArr = (T[]) newArray(tArr, size);
        }
        fillArray(collection, tArr);
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }

    @CanIgnoreReturnValue
    public static Object[] checkElementsNotNull(Object[] objArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            checkElementNotNull(objArr[i3], i3);
        }
        return objArr;
    }

    public static <T> T[] newArray(T[] tArr, int i2) {
        return (T[]) Platform.newArray(tArr, i2);
    }

    public static <T> T[] concat(@NullableDecl T t2, T[] tArr) {
        T[] tArr2 = (T[]) newArray(tArr, tArr.length + 1);
        tArr2[0] = t2;
        System.arraycopy(tArr, 0, tArr2, 1, tArr.length);
        return tArr2;
    }

    public static <T> T[] concat(T[] tArr, @NullableDecl T t2) {
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, tArr.length + 1);
        tArr2[tArr.length] = t2;
        return tArr2;
    }

    public static <T> T[] toArrayImpl(Object[] objArr, int i2, int i3, T[] tArr) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, objArr.length);
        if (tArr.length < i3) {
            tArr = (T[]) newArray(tArr, i3);
        } else if (tArr.length > i3) {
            tArr[i3] = null;
        }
        System.arraycopy(objArr, i2, tArr, 0, i3);
        return tArr;
    }

    public static Object[] toArrayImpl(Collection<?> collection) {
        return fillArray(collection, new Object[collection.size()]);
    }
}
