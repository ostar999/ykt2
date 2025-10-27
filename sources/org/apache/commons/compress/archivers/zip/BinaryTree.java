package org.apache.commons.compress.archivers.zip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes9.dex */
class BinaryTree {
    private static final int NODE = -2;
    private static final int UNDEFINED = -1;
    private final int[] tree;

    public BinaryTree(int i2) {
        int[] iArr = new int[(1 << (i2 + 1)) - 1];
        this.tree = iArr;
        Arrays.fill(iArr, -1);
    }

    public static BinaryTree decode(InputStream inputStream, int i2) throws IOException {
        int i3 = inputStream.read() + 1;
        if (i3 == 0) {
            throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
        }
        byte[] bArr = new byte[i3];
        new DataInputStream(inputStream).readFully(bArr);
        int[] iArr = new int[i2];
        int iMax = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            byte b3 = bArr[i5];
            int i6 = ((b3 & 240) >> 4) + 1;
            int i7 = (b3 & 15) + 1;
            int i8 = 0;
            while (i8 < i6) {
                iArr[i4] = i7;
                i8++;
                i4++;
            }
            iMax = Math.max(iMax, i7);
        }
        int[] iArr2 = new int[i2];
        for (int i9 = 0; i9 < i2; i9++) {
            iArr2[i9] = i9;
        }
        int[] iArr3 = new int[i2];
        int i10 = 0;
        for (int i11 = 0; i11 < i2; i11++) {
            for (int i12 = 0; i12 < i2; i12++) {
                if (iArr[i12] == i11) {
                    iArr3[i10] = i11;
                    iArr2[i10] = i12;
                    i10++;
                }
            }
        }
        int[] iArr4 = new int[i2];
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        for (int i16 = i2 - 1; i16 >= 0; i16--) {
            i13 += i14;
            int i17 = iArr3[i16];
            if (i17 != i15) {
                i14 = 1 << (16 - i17);
                i15 = i17;
            }
            iArr4[iArr2[i16]] = i13;
        }
        BinaryTree binaryTree = new BinaryTree(iMax);
        for (int i18 = 0; i18 < i2; i18++) {
            int i19 = iArr[i18];
            if (i19 > 0) {
                binaryTree.addLeaf(0, Integer.reverse(iArr4[i18] << 16), i19, i18);
            }
        }
        return binaryTree;
    }

    public void addLeaf(int i2, int i3, int i4, int i5) {
        if (i4 != 0) {
            this.tree[i2] = -2;
            addLeaf((i2 * 2) + 1 + (i3 & 1), i3 >>> 1, i4 - 1, i5);
            return;
        }
        int[] iArr = this.tree;
        if (iArr[i2] == -1) {
            iArr[i2] = i5;
            return;
        }
        throw new IllegalArgumentException("Tree value at index " + i2 + " has already been assigned (" + this.tree[i2] + ")");
    }

    public int read(BitStream bitStream) throws IOException {
        int i2 = 0;
        while (true) {
            int iNextBit = bitStream.nextBit();
            if (iNextBit == -1) {
                return -1;
            }
            int i3 = (i2 * 2) + 1 + iNextBit;
            int i4 = this.tree[i3];
            if (i4 != -2) {
                if (i4 != -1) {
                    return i4;
                }
                throw new IOException("The child " + iNextBit + " of node at index " + i2 + " is not defined");
            }
            i2 = i3;
        }
    }
}
