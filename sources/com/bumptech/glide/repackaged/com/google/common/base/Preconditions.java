package com.bumptech.glide.repackaged.com.google.common.base;

/* loaded from: classes2.dex */
public final class Preconditions {
    private static String badElementIndex(int i2, int i3, String str) {
        if (i2 < 0) {
            return format("%s (%s) must not be negative", str, Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return format("%s (%s) must be less than size (%s)", str, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException("negative size: " + i3);
    }

    private static String badPositionIndex(int i2, int i3, String str) {
        if (i2 < 0) {
            return format("%s (%s) must not be negative", str, Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return format("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException("negative size: " + i3);
    }

    private static String badPositionIndexes(int i2, int i3, int i4) {
        return (i2 < 0 || i2 > i4) ? badPositionIndex(i2, i4, "start index") : (i3 < 0 || i3 > i4) ? badPositionIndex(i3, i4, "end index") : format("end index (%s) must not be less than start index (%s)", Integer.valueOf(i3), Integer.valueOf(i2));
    }

    public static void checkArgument(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    public static int checkElementIndex(int i2, int i3) {
        return checkElementIndex(i2, i3, "index");
    }

    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }

    public static int checkPositionIndex(int i2, int i3) {
        return checkPositionIndex(i2, i3, "index");
    }

    public static void checkPositionIndexes(int i2, int i3, int i4) {
        if (i2 < 0 || i3 < i2 || i3 > i4) {
            throw new IndexOutOfBoundsException(badPositionIndexes(i2, i3, i4));
        }
    }

    public static void checkState(boolean z2) {
        if (!z2) {
            throw new IllegalStateException();
        }
    }

    public static String format(String str, Object... objArr) {
        int iIndexOf;
        String strValueOf = String.valueOf(str);
        StringBuilder sb = new StringBuilder(strValueOf.length() + (objArr.length * 16));
        int i2 = 0;
        int i3 = 0;
        while (i2 < objArr.length && (iIndexOf = strValueOf.indexOf("%s", i3)) != -1) {
            sb.append(strValueOf.substring(i3, iIndexOf));
            sb.append(objArr[i2]);
            i3 = iIndexOf + 2;
            i2++;
        }
        sb.append(strValueOf.substring(i3));
        if (i2 < objArr.length) {
            sb.append(" [");
            sb.append(objArr[i2]);
            for (int i4 = i2 + 1; i4 < objArr.length; i4++) {
                sb.append(", ");
                sb.append(objArr[i4]);
            }
            sb.append(']');
        }
        return sb.toString();
    }

    public static void checkArgument(boolean z2, Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static int checkElementIndex(int i2, int i3, String str) {
        if (i2 < 0 || i2 >= i3) {
            throw new IndexOutOfBoundsException(badElementIndex(i2, i3, str));
        }
        return i2;
    }

    public static <T> T checkNotNull(T t2, Object obj) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static int checkPositionIndex(int i2, int i3, String str) {
        if (i2 < 0 || i2 > i3) {
            throw new IndexOutOfBoundsException(badPositionIndex(i2, i3, str));
        }
        return i2;
    }

    public static void checkState(boolean z2, Object obj) {
        if (!z2) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkArgument(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalArgumentException(format(str, objArr));
        }
    }
}
