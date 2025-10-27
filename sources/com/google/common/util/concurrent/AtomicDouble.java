package com.google.common.util.concurrent;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class AtomicDouble extends Number implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLong value;

    public AtomicDouble(double d3) {
        this.value = new AtomicLong(Double.doubleToRawLongBits(d3));
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.value = new AtomicLong();
        set(objectInputStream.readDouble());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeDouble(get());
    }

    @CanIgnoreReturnValue
    public final double addAndGet(double d3) {
        long j2;
        double dLongBitsToDouble;
        do {
            j2 = this.value.get();
            dLongBitsToDouble = Double.longBitsToDouble(j2) + d3;
        } while (!this.value.compareAndSet(j2, Double.doubleToRawLongBits(dLongBitsToDouble)));
        return dLongBitsToDouble;
    }

    public final boolean compareAndSet(double d3, double d4) {
        return this.value.compareAndSet(Double.doubleToRawLongBits(d3), Double.doubleToRawLongBits(d4));
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return get();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) get();
    }

    public final double get() {
        return Double.longBitsToDouble(this.value.get());
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(double d3) {
        long j2;
        double dLongBitsToDouble;
        do {
            j2 = this.value.get();
            dLongBitsToDouble = Double.longBitsToDouble(j2);
        } while (!this.value.compareAndSet(j2, Double.doubleToRawLongBits(dLongBitsToDouble + d3)));
        return dLongBitsToDouble;
    }

    public final double getAndSet(double d3) {
        return Double.longBitsToDouble(this.value.getAndSet(Double.doubleToRawLongBits(d3)));
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) get();
    }

    public final void lazySet(double d3) {
        this.value.lazySet(Double.doubleToRawLongBits(d3));
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) get();
    }

    public final void set(double d3) {
        this.value.set(Double.doubleToRawLongBits(d3));
    }

    public String toString() {
        return Double.toString(get());
    }

    public final boolean weakCompareAndSet(double d3, double d4) {
        return this.value.weakCompareAndSet(Double.doubleToRawLongBits(d3), Double.doubleToRawLongBits(d4));
    }

    public AtomicDouble() {
        this(0.0d);
    }
}
