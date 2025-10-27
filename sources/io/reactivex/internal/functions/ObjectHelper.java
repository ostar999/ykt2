package io.reactivex.internal.functions;

import io.reactivex.functions.BiPredicate;

/* loaded from: classes8.dex */
public final class ObjectHelper {
    static final BiPredicate<Object, Object> EQUALS = new BiObjectPredicate();

    public static final class BiObjectPredicate implements BiPredicate<Object, Object> {
        @Override // io.reactivex.functions.BiPredicate
        public boolean test(Object obj, Object obj2) {
            return ObjectHelper.equals(obj, obj2);
        }
    }

    private ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static int compare(int i2, int i3) {
        if (i2 < i3) {
            return -1;
        }
        return i2 > i3 ? 1 : 0;
    }

    public static int compare(long j2, long j3) {
        if (j2 < j3) {
            return -1;
        }
        return j2 > j3 ? 1 : 0;
    }

    public static boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static <T> BiPredicate<T, T> equalsPredicate() {
        return (BiPredicate<T, T>) EQUALS;
    }

    public static int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public static <T> T requireNonNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static int verifyPositive(int i2, String str) {
        if (i2 > 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " > 0 required but it was " + i2);
    }

    @Deprecated
    public static long requireNonNull(long j2, String str) {
        throw new InternalError("Null check on a primitive: " + str);
    }

    public static long verifyPositive(long j2, String str) {
        if (j2 > 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " > 0 required but it was " + j2);
    }
}
