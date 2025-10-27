package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

@KeepForSdk
/* loaded from: classes3.dex */
public class DataBufferIterator<T> implements Iterator<T> {
    protected final DataBuffer<T> zaln;
    protected int zalo = -1;

    public DataBufferIterator(DataBuffer<T> dataBuffer) {
        this.zaln = (DataBuffer) Preconditions.checkNotNull(dataBuffer);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.zalo < this.zaln.getCount() - 1;
    }

    @Override // java.util.Iterator
    public T next() {
        if (hasNext()) {
            DataBuffer<T> dataBuffer = this.zaln;
            int i2 = this.zalo + 1;
            this.zalo = i2;
            return dataBuffer.get(i2);
        }
        int i3 = this.zalo;
        StringBuilder sb = new StringBuilder(46);
        sb.append("Cannot advance the iterator beyond ");
        sb.append(i3);
        throw new NoSuchElementException(sb.toString());
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
