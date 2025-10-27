package cn.hutool.core.lang.mutable;

import cn.hutool.core.util.NumberUtil;

/* loaded from: classes.dex */
public class MutableByte extends Number implements Comparable<MutableByte>, Mutable<Number> {
    private static final long serialVersionUID = 1;
    private byte value;

    public MutableByte() {
    }

    public MutableByte add(byte b3) {
        this.value = (byte) (this.value + b3);
        return this;
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return this.value;
    }

    public MutableByte decrement() {
        this.value = (byte) (this.value - 1);
        return this;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableByte) && this.value == ((MutableByte) obj).byteValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public MutableByte increment() {
        this.value = (byte) (this.value + 1);
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

    public MutableByte subtract(byte b3) {
        this.value = (byte) (this.value - b3);
        return this;
    }

    public String toString() {
        return String.valueOf((int) this.value);
    }

    public MutableByte(byte b3) {
        this.value = b3;
    }

    public MutableByte add(Number number) {
        this.value = (byte) (this.value + number.byteValue());
        return this;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableByte mutableByte) {
        return NumberUtil.compare(this.value, mutableByte.value);
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    /* renamed from: get */
    public Number get2() {
        return Byte.valueOf(this.value);
    }

    public void set(byte b3) {
        this.value = b3;
    }

    public MutableByte subtract(Number number) {
        this.value = (byte) (this.value - number.byteValue());
        return this;
    }

    @Override // cn.hutool.core.lang.mutable.Mutable
    public void set(Number number) {
        this.value = number.byteValue();
    }

    public MutableByte(Number number) {
        this(number.byteValue());
    }

    public MutableByte(String str) throws NumberFormatException {
        this.value = Byte.parseByte(str);
    }
}
