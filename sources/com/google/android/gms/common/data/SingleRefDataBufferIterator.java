package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.NoSuchElementException;

@KeepForSdk
/* loaded from: classes3.dex */
public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
    private T zams;

    public SingleRefDataBufferIterator(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    @Override // com.google.android.gms.common.data.DataBufferIterator, java.util.Iterator
    public T next() {
        if (!hasNext()) {
            int i2 = this.zalo;
            StringBuilder sb = new StringBuilder(46);
            sb.append("Cannot advance the iterator beyond ");
            sb.append(i2);
            throw new NoSuchElementException(sb.toString());
        }
        int i3 = this.zalo + 1;
        this.zalo = i3;
        if (i3 == 0) {
            T t2 = this.zaln.get(0);
            this.zams = t2;
            if (!(t2 instanceof DataBufferRef)) {
                String strValueOf = String.valueOf(this.zams.getClass());
                StringBuilder sb2 = new StringBuilder(strValueOf.length() + 44);
                sb2.append("DataBuffer reference of type ");
                sb2.append(strValueOf);
                sb2.append(" is not movable");
                throw new IllegalStateException(sb2.toString());
            }
        } else {
            ((DataBufferRef) this.zams).zag(i3);
        }
        return this.zams;
    }
}
