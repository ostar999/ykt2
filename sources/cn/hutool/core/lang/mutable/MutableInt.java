package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private int value;

    public MutableInt() {
    }

    public MutableInt add(int i2) {
        this.value += i2;
        return this;
    }

    public MutableInt decrement() {
        this.value--;
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableInt) && this.value == ((MutableInt) obj).intValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public MutableInt increment() {
        this.value++;
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

    public MutableInt subtract(int i2) {
        this.value -= i2;
        return this;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableInt(int i2) {
        this.value = i2;
    }

    public MutableInt add(Number number) {
        this.value += number.intValue();
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableInt mutableInt) {
        return NumberUtil.compare(this.value, mutableInt.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get */
    public Number get2() {
        return Integer.valueOf(this.value);
    }

    public void set(int i2) {
        this.value = i2;
    }

    public MutableInt subtract(Number number) {
        this.value -= number.intValue();
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.intValue();
    }

    public MutableInt(Number number) {
        this(number.intValue());
    }

    public MutableInt(String str) throws NumberFormatException {
        this.value = Integer.parseInt(str);
    }
}
