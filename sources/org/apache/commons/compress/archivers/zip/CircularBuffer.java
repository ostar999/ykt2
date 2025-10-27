package org.apache.commons.compress.archivers.zip;

/* loaded from: classes9.dex */
class CircularBuffer {
    private final byte[] buffer;
    private int readIndex;
    private final int size;
    private int writeIndex;

    public CircularBuffer(int i2) {
        this.size = i2;
        this.buffer = new byte[i2];
    }

    public boolean available() {
        return this.readIndex != this.writeIndex;
    }

    public void copy(int i2, int i3) {
        int i4 = this.writeIndex - i2;
        int i5 = i3 + i4;
        while (i4 < i5) {
            byte[] bArr = this.buffer;
            int i6 = this.writeIndex;
            int i7 = this.size;
            bArr[i6] = bArr[(i4 + i7) % i7];
            this.writeIndex = (i6 + 1) % i7;
            i4++;
        }
    }

    public int get() {
        if (!available()) {
            return -1;
        }
        byte[] bArr = this.buffer;
        int i2 = this.readIndex;
        byte b3 = bArr[i2];
        this.readIndex = (i2 + 1) % this.size;
        return b3 & 255;
    }

    public void put(int i2) {
        byte[] bArr = this.buffer;
        int i3 = this.writeIndex;
        bArr[i3] = (byte) i2;
        this.writeIndex = (i3 + 1) % this.size;
    }
}
