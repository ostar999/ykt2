package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
class ExplodingInputStream extends InputStream {
    private BitStream bits;
    private final CircularBuffer buffer = new CircularBuffer(32768);
    private final int dictionarySize;
    private BinaryTree distanceTree;
    private final InputStream in;
    private BinaryTree lengthTree;
    private BinaryTree literalTree;
    private final int minimumMatchLength;
    private final int numberOfTrees;

    public ExplodingInputStream(int i2, int i3, InputStream inputStream) {
        if (i2 != 4096 && i2 != 8192) {
            throw new IllegalArgumentException("The dictionary size must be 4096 or 8192");
        }
        if (i3 != 2 && i3 != 3) {
            throw new IllegalArgumentException("The number of trees must be 2 or 3");
        }
        this.dictionarySize = i2;
        this.numberOfTrees = i3;
        this.minimumMatchLength = i3;
        this.in = inputStream;
    }

    private void fillBuffer() throws IOException {
        init();
        int iNextBit = this.bits.nextBit();
        if (iNextBit == 1) {
            BinaryTree binaryTree = this.literalTree;
            int iNextByte = binaryTree != null ? binaryTree.read(this.bits) : this.bits.nextByte();
            if (iNextByte == -1) {
                return;
            }
            this.buffer.put(iNextByte);
            return;
        }
        if (iNextBit == 0) {
            int i2 = this.dictionarySize == 4096 ? 6 : 7;
            int iNextBits = (int) this.bits.nextBits(i2);
            int i3 = this.distanceTree.read(this.bits);
            if (i3 != -1 || iNextBits > 0) {
                int i4 = (i3 << i2) | iNextBits;
                int iNextBits2 = this.lengthTree.read(this.bits);
                if (iNextBits2 == 63) {
                    iNextBits2 = (int) (iNextBits2 + this.bits.nextBits(8));
                }
                this.buffer.copy(i4 + 1, iNextBits2 + this.minimumMatchLength);
            }
        }
    }

    private void init() throws IOException {
        if (this.bits == null) {
            if (this.numberOfTrees == 3) {
                this.literalTree = BinaryTree.decode(this.in, 256);
            }
            this.lengthTree = BinaryTree.decode(this.in, 64);
            this.distanceTree = BinaryTree.decode(this.in, 64);
            this.bits = new BitStream(this.in);
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.buffer.available()) {
            fillBuffer();
        }
        return this.buffer.get();
    }
}
