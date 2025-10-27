package com.google.android.gms.common.api;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

@KeepForSdk
/* loaded from: classes3.dex */
public class DataBufferResponse<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
    @KeepForSdk
    public DataBufferResponse() {
    }

    @Override // com.google.android.gms.common.data.DataBuffer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        ((AbstractDataBuffer) getResult()).close();
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public T get(int i2) {
        return (T) ((AbstractDataBuffer) getResult()).get(i2);
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public int getCount() {
        return ((AbstractDataBuffer) getResult()).getCount();
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public Bundle getMetadata() {
        return ((AbstractDataBuffer) getResult()).getMetadata();
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public boolean isClosed() {
        return ((AbstractDataBuffer) getResult()).isClosed();
    }

    @Override // com.google.android.gms.common.data.DataBuffer, java.lang.Iterable
    public Iterator<T> iterator() {
        return ((AbstractDataBuffer) getResult()).iterator();
    }

    @Override // com.google.android.gms.common.data.DataBuffer, com.google.android.gms.common.api.Releasable
    public void release() {
        ((AbstractDataBuffer) getResult()).release();
    }

    @Override // com.google.android.gms.common.data.DataBuffer
    public Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer) getResult()).singleRefIterator();
    }

    /* JADX WARN: Incorrect types in method signature: (TR;)V */
    /* JADX WARN: Multi-variable type inference failed */
    @KeepForSdk
    public DataBufferResponse(@NonNull AbstractDataBuffer abstractDataBuffer) {
        super(abstractDataBuffer);
    }
}
