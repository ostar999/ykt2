package cn.hutool.core.date;

/* loaded from: classes.dex */
public enum Quarter {
    Q1(1),
    Q2(2),
    Q3(3),
    Q4(4);

    private final int value;

    Quarter(int i2) {
        this.value = i2;
    }

    public static Quarter of(int i2) {
        if (i2 == 1) {
            return Q1;
        }
        if (i2 == 2) {
            return Q2;
        }
        if (i2 == 3) {
            return Q3;
        }
        if (i2 != 4) {
            return null;
        }
        return Q4;
    }

    public int getValue() {
        return this.value;
    }
}
