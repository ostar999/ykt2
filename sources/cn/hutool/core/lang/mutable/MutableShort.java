package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableShort extends Number implements Comparable<MutableShort>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private short value;

    public MutableShort() {
    }

    public MutableShort add(short s2) {
        this.value = (short) (this.value + s2);
        return this;
    }

    public MutableShort decrement() {
        this.value = (short) (this.value - 1);
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableShort) && this.value == ((MutableShort) obj).shortValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public MutableShort increment() {
        this.value = (short) (this.value + 1);
        return this;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public short shortValue() {
        return this.value;
    }

    public MutableShort subtract(short s2) {
        this.value = (short) (this.value - s2);
        return this;
    }

    public String toString() {
        return String.valueOf((int) this.value);
    }

    public MutableShort(short s2) {
        this.value = s2;
    }

    public MutableShort add(Number number) {
        this.value = (short) (this.value + number.shortValue());
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableShort mutableShort) {
        return NumberUtil.compare(this.value, mutableShort.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get, reason: avoid collision after fix types in other method */
    public Number get2() {
        return Short.valueOf(this.value);
    }

    public void set(short s2) {
        this.value = s2;
    }

    public MutableShort subtract(Number number) {
        this.value = (short) (this.value - number.shortValue());
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.shortValue();
    }

    public MutableShort(Number number) {
        this(number.shortValue());
    }

    public MutableShort(String str) throws NumberFormatException {
        this.value = Short.parseShort(str);
    }
}
