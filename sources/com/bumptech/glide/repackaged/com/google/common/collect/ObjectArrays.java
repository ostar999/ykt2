package com.bumptech.glide.repackaged.com.google.common.collect;

/* loaded from: classes2.dex */
public final class ObjectArrays {
    static final Object[] EMPTY_ARRAY = new Object[0];

    public static <T> T[] arraysCopyOf(T[] tArr, int i2) {
        T[] tArr2 = (T[]) newArray(tArr, i2);
        System.arraycopy(tArr, 0, tArr2, 0, Math.min(tArr.length, i2));
        return tArr2;
    }

    public static Object checkElementNotNull(Object obj, int i2) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("at index " + i2);
    }

    public static Object[] checkElementsNotNull(Object... objArr) {
        return checkElementsNotNull(objArr, objArr.length);
    }

    public static <T> T[] newArray(T[] tArr, int i2) {
        return (T[]) Platform.newArray(tArr, i2);
    }

    public static Object[] checkElementsNotNull(Object[] objArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            checkElementNotNull(objArr[i3], i3);
        }
        return objArr;
    }
}
