package org.apache.http.util;

/* loaded from: classes9.dex */
public final class LangUtils {
    public static final int HASH_OFFSET = 37;
    public static final int HASH_SEED = 17;

    private LangUtils() {
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == null ? obj2 == null : obj.equals(obj2);
    }

    public static int hashCode(int i2, int i3) {
        return (i2 * 37) + i3;
    }

    public static int hashCode(int i2, boolean z2) {
        return hashCode(i2, z2 ? 1 : 0);
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        if (objArr == null) {
            return objArr2 == null;
        }
        if (objArr2 == null || objArr.length != objArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (!equals(objArr[i2], objArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(int i2, Object obj) {
        return hashCode(i2, obj != null ? obj.hashCode() : 0);
    }
}
