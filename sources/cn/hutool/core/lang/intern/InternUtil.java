package cn.hutool.core.lang.intern;

/* loaded from: classes.dex */
public class InternUtil {
    public static Interner<String> createJdkInterner() {
        return new JdkStringInterner();
    }

    public static Interner<String> createStringInterner(boolean z2) {
        return z2 ? createWeakInterner() : createJdkInterner();
    }

    public static <T> Interner<T> createWeakInterner() {
        return new WeakInterner();
    }
}
