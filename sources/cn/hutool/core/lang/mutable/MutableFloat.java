package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableFloat extends Number implements Comparable<MutableFloat>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private float value;

    public MutableFloat() {
    }

    public MutableFloat add(float f2) {
        this.value += f2;
        return this;
    }

    public MutableFloat decrement() {
        this.value -= 1.0f;
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableFloat) && Float.floatToIntBits(((MutableFloat) obj).value) == Float.floatToIntBits(this.value);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }

    public MutableFloat increment() {
        this.value += 1.0f;
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

    public MutableFloat subtract(float f2) {
        this.value -= f2;
        return this;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableFloat(float f2) {
        this.value = f2;
    }

    public MutableFloat add(Number number) {
        this.value += number.floatValue();
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableFloat mutableFloat) {
        return NumberUtil.compare(this.value, mutableFloat.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get */
    public Number get2() {
        return Float.valueOf(this.value);
    }

    public void set(float f2) {
        this.value = f2;
    }

    public MutableFloat subtract(Number number) {
        this.value -= number.floatValue();
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.floatValue();
    }

    public MutableFloat(Number number) {
        this(number.floatValue());
    }

    public MutableFloat(String str) throws NumberFormatException {
        this.value = Float.parseFloat(str);
    }
}
