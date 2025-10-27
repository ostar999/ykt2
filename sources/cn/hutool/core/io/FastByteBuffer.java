package cn.hutool.core.io;

/* loaded from: classes.dex */
public class FastByteBuffer {
    private byte[][] buffers;
    private int buffersCount;
    private byte[] currentBuffer;
    private int currentBufferIndex;
    private final int minChunkLen;
    private int offset;
    private int size;

    public FastByteBuffer() {
        this(1024);
    }

    private void needNewBuffer(int i2) {
        int iMax = Math.max(this.minChunkLen, i2 - this.size);
        int i3 = this.currentBufferIndex + 1;
        this.currentBufferIndex = i3;
        this.currentBuffer = new byte[iMax];
        this.offset = 0;
        byte[][] bArr = this.buffers;
        if (i3 >= bArr.length) {
            byte[][] bArr2 = new byte[bArr.length << 1][];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.buffers = bArr2;
        }
        this.buffers[this.currentBufferIndex] = this.currentBuffer;
        this.buffersCount++;
    }

    public FastByteBuffer append(byte[] bArr, int i2, int i3) {
        int i4 = i2 + i3;
        if (i2 < 0 || i3 < 0 || i4 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return this;
        }
        int i5 = this.size + i3;
        byte[] bArr2 = this.currentBuffer;
        if (bArr2 != null) {
            int iMin = Math.min(i3, bArr2.length - this.offset);
            System.arraycopy(bArr, i4 - i3, this.currentBuffer, this.offset, iMin);
            i3 -= iMin;
            this.offset += iMin;
            this.size += iMin;
        }
        if (i3 > 0) {
            needNewBuffer(i5);
            int iMin2 = Math.min(i3, this.currentBuffer.length - this.offset);
            System.arraycopy(bArr, i4 - i3, this.currentBuffer, this.offset, iMin2);
            this.offset += iMin2;
            this.size += iMin2;
        }
        return this;
    }

    public byte[] array(int i2) {
        return this.buffers[i2];
    }

    public byte get(int i2) {
        if (i2 >= this.size || i2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = 0;
        while (true) {
            byte[] bArr = this.buffers[i3];
            if (i2 < bArr.length) {
                return bArr[i2];
            }
            i3++;
            i2 -= bArr.length;
        }
    }

    public int index() {
        return this.currentBufferIndex;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int offset() {
        return this.offset;
    }

    public void reset() {
        this.size = 0;
        this.offset = 0;
        this.currentBufferIndex = -1;
        this.currentBuffer = null;
        this.buffersCount = 0;
    }

    public int size() {
        return this.size;
    }

    public byte[] toArray() {
        byte[] bArr = new byte[this.size];
        if (this.currentBufferIndex == -1) {
            return bArr;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = this.currentBufferIndex;
            if (i2 >= i4) {
                System.arraycopy(this.buffers[i4], 0, bArr, i3, this.offset);
                return bArr;
            }
            byte[] bArr2 = this.buffers[i2];
            int length = bArr2.length;
            System.arraycopy(bArr2, 0, bArr, i3, length);
            i3 += length;
            i2++;
        }
    }

    public FastByteBuffer(int i2) {
        this.buffers = new byte[16][];
        this.currentBufferIndex = -1;
        this.minChunkLen = Math.abs(i2 <= 0 ? 1024 : i2);
    }

    public byte[] toArray(int i2, int i3) {
        byte[] bArr = new byte[i3];
        if (i3 == 0) {
            return bArr;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr2 = this.buffers[i4];
            if (i2 < bArr2.length) {
                break;
            }
            i2 -= bArr2.length;
            i4++;
        }
        int i5 = 0;
        while (i4 < this.buffersCount) {
            byte[] bArr3 = this.buffers[i4];
            int iMin = Math.min(bArr3.length - i2, i3);
            System.arraycopy(bArr3, i2, bArr, i5, iMin);
            i5 += iMin;
            i3 -= iMin;
            if (i3 == 0) {
                break;
            }
            i4++;
            i2 = 0;
        }
        return bArr;
    }

    public FastByteBuffer append(byte[] bArr) {
        return append(bArr, 0, bArr.length);
    }

    public FastByteBuffer append(byte b3) {
        byte[] bArr = this.currentBuffer;
        if (bArr == null || this.offset == bArr.length) {
            needNewBuffer(this.size + 1);
        }
        byte[] bArr2 = this.currentBuffer;
        int i2 = this.offset;
        bArr2[i2] = b3;
        this.offset = i2 + 1;
        this.size++;
        return this;
    }

    public FastByteBuffer append(FastByteBuffer fastByteBuffer) {
        if (fastByteBuffer.size == 0) {
            return this;
        }
        for (int i2 = 0; i2 < fastByteBuffer.currentBufferIndex; i2++) {
            append(fastByteBuffer.buffers[i2]);
        }
        append(fastByteBuffer.currentBuffer, 0, fastByteBuffer.offset);
        return this;
    }
}
