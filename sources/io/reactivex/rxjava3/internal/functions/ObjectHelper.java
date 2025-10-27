package io.reactivex.rxjava3.internal.functions;

import io.reactivex.rxjava3.functions.BiPredicate;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class ObjectHelper {
    static final BiPredicate<Object, Object> EQUALS = new BiObjectPredicate();

    public static final class BiObjectPredicate implements BiPredicate<Object, Object> {
        @Override // io.reactivex.rxjava3.functions.BiPredicate
        public boolean test(Object o12, Object o2) {
            return Objects.equals(o12, o2);
        }
    }

    private ObjectHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> BiPredicate<T, T> equalsPredicate() {
        return (BiPredicate<T, T>) EQUALS;
    }

    public static int verifyPositive(int value, String paramName) {
        if (value > 0) {
            return value;
        }
        throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
    }

    public static long verifyPositive(long value, String paramName) {
        if (value > 0) {
            return value;
        }
        throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
    }
}
