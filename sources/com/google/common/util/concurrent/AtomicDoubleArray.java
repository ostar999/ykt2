package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.primitives.ImmutableLongArray;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;
import okhttp3.HttpUrl;

@GwtIncompatible
/* loaded from: classes4.dex */
public class AtomicDoubleArray implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLongArray longs;

    public AtomicDoubleArray(int i2) {
        this.longs = new AtomicLongArray(i2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = objectInputStream.readInt();
        ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
        for (int i3 = 0; i3 < i2; i3++) {
            builder.add(Double.doubleToRawLongBits(objectInputStream.readDouble()));
        }
        this.longs = new AtomicLongArray(builder.build().toArray());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int length = length();
        objectOutputStream.writeInt(length);
        for (int i2 = 0; i2 < length; i2++) {
            objectOutputStream.writeDouble(get(i2));
        }
    }

    @CanIgnoreReturnValue
    public double addAndGet(int i2, double d3) {
        long j2;
        double dLongBitsToDouble;
        do {
            j2 = this.longs.get(i2);
            dLongBitsToDouble = Double.longBitsToDouble(j2) + d3;
        } while (!this.longs.compareAndSet(i2, j2, Double.doubleToRawLongBits(dLongBitsToDouble)));
        return dLongBitsToDouble;
    }

    public final boolean compareAndSet(int i2, double d3, double d4) {
        return this.longs.compareAndSet(i2, Double.doubleToRawLongBits(d3), Double.doubleToRawLongBits(d4));
    }

    public final double get(int i2) {
        return Double.longBitsToDouble(this.longs.get(i2));
    }

    @CanIgnoreReturnValue
    public final double getAndAdd(int i2, double d3) {
        long j2;
        double dLongBitsToDouble;
        do {
            j2 = this.longs.get(i2);
            dLongBitsToDouble = Double.longBitsToDouble(j2);
        } while (!this.longs.compareAndSet(i2, j2, Double.doubleToRawLongBits(dLongBitsToDouble + d3)));
        return dLongBitsToDouble;
    }

    public final double getAndSet(int i2, double d3) {
        return Double.longBitsToDouble(this.longs.getAndSet(i2, Double.doubleToRawLongBits(d3)));
    }

    public final void lazySet(int i2, double d3) {
        this.longs.lazySet(i2, Double.doubleToRawLongBits(d3));
    }

    public final int length() {
        return this.longs.length();
    }

    public final void set(int i2, double d3) {
        this.longs.set(i2, Double.doubleToRawLongBits(d3));
    }

    public String toString() {
        int length = length() - 1;
        if (length == -1) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuilder sb = new StringBuilder((length + 1) * 19);
        sb.append('[');
        int i2 = 0;
        while (true) {
            sb.append(Double.longBitsToDouble(this.longs.get(i2)));
            if (i2 == length) {
                sb.append(']');
                return sb.toString();
            }
            sb.append(',');
            sb.append(' ');
            i2++;
        }
    }

    public final boolean weakCompareAndSet(int i2, double d3, double d4) {
        return this.longs.weakCompareAndSet(i2, Double.doubleToRawLongBits(d3), Double.doubleToRawLongBits(d4));
    }

    public AtomicDoubleArray(double[] dArr) {
        int length = dArr.length;
        long[] jArr = new long[length];
        for (int i2 = 0; i2 < length; i2++) {
            jArr[i2] = Double.doubleToRawLongBits(dArr[i2]);
        }
        this.longs = new AtomicLongArray(jArr);
    }
}
