package cn.hutool.core.lang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class Assert {
    private static final String TEMPLATE_VALUE_MUST_BE_BETWEEN_AND = "The value must be between {} and {}.";

    private static String badIndexMsg(int i2, int i3, String str, Object... objArr) {
        if (i2 < 0) {
            return CharSequenceUtil.format("{} ({}) must not be negative", CharSequenceUtil.format(str, objArr), Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return CharSequenceUtil.format("{} ({}) must be less than size ({})", CharSequenceUtil.format(str, objArr), Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException("negative size: " + i3);
    }

    public static <X extends Throwable> int checkBetween(int i2, int i3, int i4, Supplier<? extends X> supplier) throws Throwable {
        if (i2 < i3 || i2 > i4) {
            throw ((Throwable) supplier.get());
        }
        return i2;
    }

    public static int checkIndex(int i2, int i3) throws IndexOutOfBoundsException, IllegalArgumentException {
        return checkIndex(i2, i3, "[Assertion failed]", new Object[0]);
    }

    public static void equals(Object obj, Object obj2) throws Throwable {
        equals(obj, obj2, "({}) must be equals ({})", obj, obj2);
    }

    public static void isAssignable(Class<?> cls, Class<?> cls2) throws IllegalArgumentException {
        isAssignable(cls, cls2, "{} is not assignable to {})", cls2, cls);
    }

    public static <X extends Throwable> void isFalse(boolean z2, Supplier<X> supplier) throws Throwable {
        if (z2) {
            throw ((Throwable) supplier.get());
        }
    }

    public static <T> T isInstanceOf(Class<?> cls, T t2) {
        return (T) isInstanceOf(cls, t2, "Object [{}] is not instanceof [{}]", t2, cls);
    }

    public static <X extends Throwable> void isNull(Object obj, Supplier<X> supplier) throws Throwable {
        if (obj != null) {
            throw ((Throwable) supplier.get());
        }
    }

    public static <X extends Throwable> void isTrue(boolean z2, Supplier<? extends X> supplier) throws Throwable {
        if (!z2) {
            throw ((Throwable) supplier.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$checkBetween$11(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$checkBetween$12(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$checkBetween$13(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$equals$15(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$isFalse$1(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$isNull$2(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$isTrue$0(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$noNullElements$8(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notBlank$5(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notContain$6(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notEmpty$10(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notEmpty$4(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notEmpty$7(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notEmpty$9(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notEquals$14(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IllegalArgumentException lambda$notNull$3(String str, Object[] objArr) {
        return new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    public static <T, X extends Throwable> T[] noNullElements(T[] tArr, Supplier<X> supplier) throws Throwable {
        if (ArrayUtil.hasNull(tArr)) {
            throw ((Throwable) supplier.get());
        }
        return tArr;
    }

    public static <T extends CharSequence, X extends Throwable> T notBlank(T t2, Supplier<X> supplier) throws Throwable {
        if (CharSequenceUtil.isBlank(t2)) {
            throw ((Throwable) supplier.get());
        }
        return t2;
    }

    public static <T extends CharSequence, X extends Throwable> T notContain(CharSequence charSequence, T t2, Supplier<X> supplier) throws Throwable {
        if (CharSequenceUtil.contains(charSequence, t2)) {
            throw ((Throwable) supplier.get());
        }
        return t2;
    }

    public static <T extends CharSequence, X extends Throwable> T notEmpty(T t2, Supplier<X> supplier) throws Throwable {
        if (CharSequenceUtil.isEmpty(t2)) {
            throw ((Throwable) supplier.get());
        }
        return t2;
    }

    public static void notEquals(Object obj, Object obj2) throws Throwable {
        notEquals(obj, obj2, "({}) must be not equals ({})", obj, obj2);
    }

    public static <T, X extends Throwable> T notNull(T t2, Supplier<X> supplier) throws Throwable {
        if (t2 != null) {
            return t2;
        }
        throw ((Throwable) supplier.get());
    }

    public static void state(boolean z2, Supplier<String> supplier) throws IllegalStateException {
        if (!z2) {
            throw new IllegalStateException((String) supplier.get());
        }
    }

    public static int checkBetween(int i2, int i3, int i4, final String str, final Object... objArr) {
        return checkBetween(i2, i3, i4, new Supplier() { // from class: cn.hutool.core.lang.a
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$checkBetween$11(str, objArr);
            }
        });
    }

    public static int checkIndex(int i2, int i3, String str, Object... objArr) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (i2 < 0 || i2 >= i3) {
            throw new IndexOutOfBoundsException(badIndexMsg(i2, i3, str, objArr));
        }
        return i2;
    }

    public static void equals(Object obj, Object obj2, final String str, final Object... objArr) throws Throwable {
        equals(obj, obj2, new Supplier() { // from class: cn.hutool.core.lang.o
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$equals$15(str, objArr);
            }
        });
    }

    public static void isAssignable(Class<?> cls, Class<?> cls2, String str, Object... objArr) throws IllegalArgumentException {
        notNull(cls, "Type to check against must not be null", new Object[0]);
        if (cls2 == null || !cls.isAssignableFrom(cls2)) {
            throw new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
        }
    }

    public static void isFalse(boolean z2, final String str, final Object... objArr) throws Throwable {
        isFalse(z2, new Supplier() { // from class: cn.hutool.core.lang.g
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$isFalse$1(str, objArr);
            }
        });
    }

    public static <T> T isInstanceOf(Class<?> cls, T t2, String str, Object... objArr) throws IllegalArgumentException {
        notNull(cls, "Type to check against must not be null", new Object[0]);
        if (cls.isInstance(t2)) {
            return t2;
        }
        throw new IllegalArgumentException(CharSequenceUtil.format(str, objArr));
    }

    public static void isNull(Object obj, final String str, final Object... objArr) throws Throwable {
        isNull(obj, new Supplier() { // from class: cn.hutool.core.lang.c
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$isNull$2(str, objArr);
            }
        });
    }

    public static void isTrue(boolean z2, final String str, final Object... objArr) throws Throwable {
        isTrue(z2, new Supplier() { // from class: cn.hutool.core.lang.j
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$isTrue$0(str, objArr);
            }
        });
    }

    public static void notEquals(Object obj, Object obj2, final String str, final Object... objArr) throws Throwable {
        notEquals(obj, obj2, new Supplier() { // from class: cn.hutool.core.lang.b
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notEquals$14(str, objArr);
            }
        });
    }

    public static <T> T notNull(T t2, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T) notNull(t2, new Supplier() { // from class: cn.hutool.core.lang.m
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notNull$3(str, objArr);
            }
        });
    }

    public static void state(boolean z2, String str, Object... objArr) throws IllegalStateException {
        if (!z2) {
            throw new IllegalStateException(CharSequenceUtil.format(str, objArr));
        }
    }

    public static int checkBetween(int i2, int i3, int i4) {
        return checkBetween(i2, i3, i4, TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, Integer.valueOf(i3), Integer.valueOf(i4));
    }

    public static <X extends Throwable> void equals(Object obj, Object obj2, Supplier<X> supplier) throws Throwable {
        if (ObjectUtil.notEqual(obj, obj2)) {
            throw ((Throwable) supplier.get());
        }
    }

    public static void isFalse(boolean z2) throws Throwable {
        isFalse(z2, "[Assertion failed] - this expression must be false", new Object[0]);
    }

    public static void isNull(Object obj) throws Throwable {
        isNull(obj, "[Assertion failed] - the object argument must be null", new Object[0]);
    }

    public static void isTrue(boolean z2) throws Throwable {
        isTrue(z2, "[Assertion failed] - this expression must be true", new Object[0]);
    }

    public static <T> T[] noNullElements(T[] tArr, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T[]) noNullElements(tArr, new Supplier() { // from class: cn.hutool.core.lang.f
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$noNullElements$8(str, objArr);
            }
        });
    }

    public static <T extends CharSequence> T notBlank(T t2, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T) notBlank(t2, new Supplier() { // from class: cn.hutool.core.lang.k
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notBlank$5(str, objArr);
            }
        });
    }

    public static String notContain(String str, String str2, final String str3, final Object... objArr) throws IllegalArgumentException {
        return (String) notContain(str, str2, new Supplier() { // from class: cn.hutool.core.lang.h
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notContain$6(str3, objArr);
            }
        });
    }

    public static <T extends CharSequence> T notEmpty(T t2, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T) notEmpty(t2, new Supplier() { // from class: cn.hutool.core.lang.d
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notEmpty$4(str, objArr);
            }
        });
    }

    public static <X extends Throwable> void notEquals(Object obj, Object obj2, Supplier<X> supplier) throws Throwable {
        if (ObjectUtil.equals(obj, obj2)) {
            throw ((Throwable) supplier.get());
        }
    }

    public static <T> T notNull(T t2) throws IllegalArgumentException {
        return (T) notNull(t2, "[Assertion failed] - this argument is required; it must not be null", new Object[0]);
    }

    public static void state(boolean z2) throws IllegalStateException {
        state(z2, "[Assertion failed] - this state invariant must be true", new Object[0]);
    }

    public static <X extends Throwable> long checkBetween(long j2, long j3, long j4, Supplier<? extends X> supplier) throws Throwable {
        if (j2 < j3 || j2 > j4) {
            throw ((Throwable) supplier.get());
        }
        return j2;
    }

    public static <T> T[] noNullElements(T[] tArr) throws IllegalArgumentException {
        return (T[]) noNullElements(tArr, "[Assertion failed] - this array must not contain any null elements", new Object[0]);
    }

    public static <T extends CharSequence> T notBlank(T t2) throws IllegalArgumentException {
        return (T) notBlank(t2, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank", new Object[0]);
    }

    public static String notContain(String str, String str2) throws IllegalArgumentException {
        return notContain(str, str2, "[Assertion failed] - this String argument must not contain the substring [{}]", str2);
    }

    public static <T extends CharSequence> T notEmpty(T t2) throws IllegalArgumentException {
        return (T) notEmpty(t2, "[Assertion failed] - this String argument must have length; it must not be null or empty", new Object[0]);
    }

    public static long checkBetween(long j2, long j3, long j4, final String str, final Object... objArr) {
        return checkBetween(j2, j3, j4, new Supplier() { // from class: cn.hutool.core.lang.l
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$checkBetween$12(str, objArr);
            }
        });
    }

    public static <T, X extends Throwable> T[] notEmpty(T[] tArr, Supplier<X> supplier) throws Throwable {
        if (ArrayUtil.isEmpty((Object[]) tArr)) {
            throw ((Throwable) supplier.get());
        }
        return tArr;
    }

    public static long checkBetween(long j2, long j3, long j4) {
        return checkBetween(j2, j3, j4, TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, Long.valueOf(j3), Long.valueOf(j4));
    }

    public static <X extends Throwable> double checkBetween(double d3, double d4, double d5, Supplier<? extends X> supplier) throws Throwable {
        if (d3 < d4 || d3 > d5) {
            throw ((Throwable) supplier.get());
        }
        return d3;
    }

    public static <T> T[] notEmpty(T[] tArr, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T[]) notEmpty(tArr, new Supplier() { // from class: cn.hutool.core.lang.e
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notEmpty$7(str, objArr);
            }
        });
    }

    public static double checkBetween(double d3, double d4, double d5, final String str, final Object... objArr) {
        return checkBetween(d3, d4, d5, new Supplier() { // from class: cn.hutool.core.lang.n
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$checkBetween$13(str, objArr);
            }
        });
    }

    public static <T> T[] notEmpty(T[] tArr) throws IllegalArgumentException {
        return (T[]) notEmpty(tArr, "[Assertion failed] - this array must not be empty: it must contain at least 1 element", new Object[0]);
    }

    public static double checkBetween(double d3, double d4, double d5) {
        return checkBetween(d3, d4, d5, TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, Double.valueOf(d4), Double.valueOf(d5));
    }

    public static <E, T extends Iterable<E>, X extends Throwable> T notEmpty(T t2, Supplier<X> supplier) throws Throwable {
        if (CollUtil.isEmpty((Iterable<?>) t2)) {
            throw ((Throwable) supplier.get());
        }
        return t2;
    }

    public static Number checkBetween(Number number, Number number2, Number number3) throws IllegalArgumentException {
        notNull(number);
        notNull(number2);
        notNull(number3);
        double dDoubleValue = number.doubleValue();
        double dDoubleValue2 = number2.doubleValue();
        double dDoubleValue3 = number3.doubleValue();
        if (dDoubleValue < dDoubleValue2 || dDoubleValue > dDoubleValue3) {
            throw new IllegalArgumentException(CharSequenceUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, number2, number3));
        }
        return number;
    }

    public static <E, T extends Iterable<E>> T notEmpty(T t2, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T) notEmpty(t2, new Supplier() { // from class: cn.hutool.core.lang.p
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notEmpty$9(str, objArr);
            }
        });
    }

    public static <E, T extends Iterable<E>> T notEmpty(T t2) throws IllegalArgumentException {
        return (T) notEmpty(t2, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element", new Object[0]);
    }

    public static <K, V, T extends Map<K, V>, X extends Throwable> T notEmpty(T t2, Supplier<X> supplier) throws Throwable {
        if (MapUtil.isEmpty(t2)) {
            throw ((Throwable) supplier.get());
        }
        return t2;
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T t2, final String str, final Object... objArr) throws IllegalArgumentException {
        return (T) notEmpty(t2, new Supplier() { // from class: cn.hutool.core.lang.i
            @Override // java.util.function.Supplier
            public final Object get() {
                return Assert.lambda$notEmpty$10(str, objArr);
            }
        });
    }

    public static <K, V, T extends Map<K, V>> T notEmpty(T t2) throws IllegalArgumentException {
        return (T) notEmpty(t2, "[Assertion failed] - this map must not be empty; it must contain at least one entry", new Object[0]);
    }
}
