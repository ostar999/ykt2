package net.tsz.afinal.bitmap.core;

import java.util.ArrayList;

/* loaded from: classes9.dex */
public class BytesBufferPool {
    private final int mBufferSize;
    private final ArrayList<BytesBuffer> mList;
    private final int mPoolSize;

    public static class BytesBuffer {
        public byte[] data;
        public int length;
        public int offset;

        private BytesBuffer(int i2) {
            this.data = new byte[i2];
        }
    }

    public BytesBufferPool(int i2, int i3) {
        this.mList = new ArrayList<>(i2);
        this.mPoolSize = i2;
        this.mBufferSize = i3;
    }

    public synchronized void clear() {
        this.mList.clear();
    }

    public synchronized BytesBuffer get() {
        int size;
        size = this.mList.size();
        return size > 0 ? this.mList.remove(size - 1) : new BytesBuffer(this.mBufferSize);
    }

    public synchronized void recycle(BytesBuffer bytesBuffer) {
        if (bytesBuffer.data.length != this.mBufferSize) {
            return;
        }
        if (this.mList.size() < this.mPoolSize) {
            bytesBuffer.offset = 0;
            bytesBuffer.length = 0;
            this.mList.add(bytesBuffer);
        }
    }
}
