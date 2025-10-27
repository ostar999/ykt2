package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableDouble extends Number implements Comparable<MutableDouble>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private double value;

    public MutableDouble() {
    }

    public MutableDouble add(double d3) {
        this.value += d3;
        return this;
    }

    public MutableDouble decrement() {
        this.value -= 1.0d;
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableDouble) && Double.doubleToLongBits(((MutableDouble) obj).value) == Double.doubleToLongBits(this.value);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) this.value;
    }

    public int hashCode() {
        long jDoubleToLongBits = Double.doubleToLongBits(this.value);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public MutableDouble increment() {
        this.value += 1.0d;
        return this;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) this.value;
    }

    public MutableDouble subtract(double d3) {
        this.value -= d3;
        return this;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableDouble(double d3) {
        this.value = d3;
    }

    public MutableDouble add(Number number) {
        this.value += number.doubleValue();
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableDouble mutableDouble) {
        return NumberUtil.compare(this.value, mutableDouble.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get, reason: merged with bridge method [inline-methods] */
    public Number get2() {
        return Double.valueOf(this.value);
    }

    public void set(double d3) {
        this.value = d3;
    }

    public MutableDouble subtract(Number number) {
        this.value -= number.doubleValue();
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.doubleValue();
    }

    public MutableDouble(Number number) {
        this(number.doubleValue());
    }

    public MutableDouble(String str) throws NumberFormatException {
        this.value = Double.parseDouble(str);
    }
}
