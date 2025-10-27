package cn.hutool.core.img;

/* loaded from: classes.dex */
public enum ScaleType {
    DEFAULT(1),
    FAST(2),
    SMOOTH(4),
    REPLICATE(8),
    AREA_AVERAGING(16);

    private final int value;

    ScaleType(int i2) {
        this.value = i2;
    }

    public int getValue() {
        return this.value;
    }
}
