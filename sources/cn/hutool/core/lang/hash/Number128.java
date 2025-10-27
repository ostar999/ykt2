package cn.hutool.core.lang.hash;

import java.util.Objects;

/* loaded from: classes.dex */
public class Number128 extends Number {
    private static final long serialVersionUID = 1;
    private long highValue;
    private long lowValue;

    public Number128(long j2, long j3) {
        this.lowValue = j2;
        this.highValue = j3;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return longValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Number128 number128 = (Number128) obj;
        return this.lowValue == number128.lowValue && this.highValue == number128.highValue;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return longValue();
    }

    public long getHighValue() {
        return this.highValue;
    }

    public long[] getLongArray() {
        return new long[]{this.lowValue, this.highValue};
    }

    public long getLowValue() {
        return this.lowValue;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.lowValue), Long.valueOf(this.highValue));
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) longValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.lowValue;
    }

    public void setHighValue(long j2) {
        this.highValue = j2;
    }

    public void setLowValue(long j2) {
        this.lowValue = j2;
    }
}
