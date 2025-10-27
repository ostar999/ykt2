package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableLong extends Number implements Comparable<MutableLong>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private long value;

    public MutableLong() {
    }

    public MutableLong add(long j2) {
        this.value += j2;
        return this;
    }

    public MutableLong decrement() {
        this.value--;
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableLong) && this.value == ((MutableLong) obj).longValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        long j2 = this.value;
        return (int) (j2 ^ (j2 >>> 32));
    }

    public MutableLong increment() {
        this.value++;
        return this;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.value;
    }

    public MutableLong subtract(long j2) {
        this.value -= j2;
        return this;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public MutableLong(long j2) {
        this.value = j2;
    }

    public MutableLong add(Number number) {
        this.value += number.longValue();
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableLong mutableLong) {
        return NumberUtil.compare(this.value, mutableLong.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get */
    public Number get2() {
        return Long.valueOf(this.value);
    }

    public void set(long j2) {
        this.value = j2;
    }

    public MutableLong subtract(Number number) {
        this.value -= number.longValue();
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.longValue();
    }

    public MutableLong(Number number) {
        this(number.longValue());
    }

    public MutableLong(String str) throws NumberFormatException {
        this.value = Long.parseLong(str);
    }
}
