package cn.hutool.core.math;

/* loaded from: classes.dex */
public class BitStatusUtil {
    public static int add(int i2, int i3) {
        check(i2, i3);
        return i2 | i3;
    }

    private static void check(int... iArr) {
        for (int i2 : iArr) {
            if (i2 < 0) {
                throw new IllegalArgumentException(i2 + " 必须大于等于0");
            }
            if ((i2 & 1) == 1) {
                throw new IllegalArgumentException(i2 + " 不是偶数");
            }
        }
    }

    public static int clear() {
        return 0;
    }

    public static boolean has(int i2, int i3) {
        check(i2, i3);
        return (i2 & i3) == i3;
    }

    public static int remove(int i2, int i3) {
        check(i2, i3);
        return has(i2, i3) ? i2 ^ i3 : i2;
    }
}
