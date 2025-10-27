package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
class IntArray {
    private int[] m_ints;

    public IntArray(int i2) {
        this.m_ints = new int[i2];
    }

    public IntArray(BigInteger bigInteger) {
        this(bigInteger, 0);
    }

    public IntArray(BigInteger bigInteger, int i2) {
        int i3;
        if (bigInteger.signum() == -1) {
            throw new IllegalArgumentException("Only positive Integers allowed");
        }
        if (bigInteger.equals(ECConstants.ZERO)) {
            this.m_ints = new int[]{0};
            return;
        }
        byte[] byteArray = bigInteger.toByteArray();
        int length = byteArray.length;
        if (byteArray[0] == 0) {
            length--;
            i3 = 1;
        } else {
            i3 = 0;
        }
        int i4 = (length + 3) / 4;
        if (i4 < i2) {
            this.m_ints = new int[i2];
        } else {
            this.m_ints = new int[i4];
        }
        int i5 = i4 - 1;
        int i6 = (length % 4) + i3;
        if (i3 < i6) {
            int i7 = 0;
            while (i3 < i6) {
                int i8 = i7 << 8;
                int i9 = byteArray[i3];
                if (i9 < 0) {
                    i9 += 256;
                }
                i7 = i8 | i9;
                i3++;
            }
            this.m_ints[i5] = i7;
            i5--;
        }
        while (i5 >= 0) {
            int i10 = 0;
            int i11 = 0;
            while (i10 < 4) {
                int i12 = i11 << 8;
                int i13 = i3 + 1;
                int i14 = byteArray[i3];
                if (i14 < 0) {
                    i14 += 256;
                }
                i11 = i12 | i14;
                i10++;
                i3 = i13;
            }
            this.m_ints[i5] = i11;
            i5--;
        }
    }

    public IntArray(int[] iArr) {
        this.m_ints = iArr;
    }

    private int[] resizedInts(int i2) {
        int[] iArr = new int[i2];
        int[] iArr2 = this.m_ints;
        int length = iArr2.length;
        if (length < i2) {
            i2 = length;
        }
        System.arraycopy(iArr2, 0, iArr, 0, i2);
        return iArr;
    }

    public void addShifted(IntArray intArray, int i2) {
        int usedLength = intArray.getUsedLength();
        int i3 = usedLength + i2;
        if (i3 > this.m_ints.length) {
            this.m_ints = resizedInts(i3);
        }
        for (int i4 = 0; i4 < usedLength; i4++) {
            int[] iArr = this.m_ints;
            int i5 = i4 + i2;
            iArr[i5] = iArr[i5] ^ intArray.m_ints[i4];
        }
    }

    public int bitLength() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return 0;
        }
        int i2 = usedLength - 1;
        int i3 = this.m_ints[i2];
        int i4 = (i2 << 5) + 1;
        if (((-65536) & i3) != 0) {
            if (((-16777216) & i3) != 0) {
                i4 += 24;
                i3 >>>= 24;
            } else {
                i4 += 16;
                i3 >>>= 16;
            }
        } else if (i3 > 255) {
            i4 += 8;
            i3 >>>= 8;
        }
        while (i3 != 1) {
            i4++;
            i3 >>>= 1;
        }
        return i4;
    }

    public Object clone() {
        return new IntArray(Arrays.clone(this.m_ints));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntArray)) {
            return false;
        }
        IntArray intArray = (IntArray) obj;
        int usedLength = getUsedLength();
        if (intArray.getUsedLength() != usedLength) {
            return false;
        }
        for (int i2 = 0; i2 < usedLength; i2++) {
            if (this.m_ints[i2] != intArray.m_ints[i2]) {
                return false;
            }
        }
        return true;
    }

    public void flipBit(int i2) {
        int i3 = i2 >> 5;
        int[] iArr = this.m_ints;
        iArr[i3] = (1 << (i2 & 31)) ^ iArr[i3];
    }

    public int getLength() {
        return this.m_ints.length;
    }

    public int getUsedLength() {
        int[] iArr = this.m_ints;
        int length = iArr.length;
        if (length < 1) {
            return 0;
        }
        if (iArr[0] != 0) {
            do {
                length--;
            } while (this.m_ints[length] == 0);
            return length + 1;
        }
        do {
            length--;
            if (this.m_ints[length] != 0) {
                return length + 1;
            }
        } while (length > 0);
        return 0;
    }

    public int hashCode() {
        int usedLength = getUsedLength();
        int i2 = 1;
        for (int i3 = 0; i3 < usedLength; i3++) {
            i2 = (i2 * 31) + this.m_ints[i3];
        }
        return i2;
    }

    public boolean isZero() {
        int[] iArr = this.m_ints;
        return iArr.length == 0 || (iArr[0] == 0 && getUsedLength() == 0);
    }

    public IntArray multiply(IntArray intArray, int i2) {
        int i3 = (i2 + 31) >> 5;
        if (this.m_ints.length < i3) {
            this.m_ints = resizedInts(i3);
        }
        int i4 = 1;
        IntArray intArray2 = new IntArray(intArray.resizedInts(intArray.getLength() + 1));
        IntArray intArray3 = new IntArray(((i2 + i2) + 31) >> 5);
        for (int i5 = 0; i5 < 32; i5++) {
            for (int i6 = 0; i6 < i3; i6++) {
                if ((this.m_ints[i6] & i4) != 0) {
                    intArray3.addShifted(intArray2, i6);
                }
            }
            i4 <<= 1;
            intArray2.shiftLeft();
        }
        return intArray3;
    }

    public void reduce(int i2, int[] iArr) {
        for (int i3 = (i2 + i2) - 2; i3 >= i2; i3--) {
            if (testBit(i3)) {
                int i4 = i3 - i2;
                flipBit(i4);
                flipBit(i3);
                int length = iArr.length;
                while (true) {
                    length--;
                    if (length >= 0) {
                        flipBit(iArr[length] + i4);
                    }
                }
            }
        }
        this.m_ints = resizedInts((i2 + 31) >> 5);
    }

    public void setBit(int i2) {
        int i3 = i2 >> 5;
        int[] iArr = this.m_ints;
        iArr[i3] = (1 << (i2 & 31)) | iArr[i3];
    }

    public IntArray shiftLeft(int i2) {
        int usedLength = getUsedLength();
        if (usedLength == 0 || i2 == 0) {
            return this;
        }
        if (i2 > 31) {
            throw new IllegalArgumentException("shiftLeft() for max 31 bits , " + i2 + "bit shift is not possible");
        }
        int[] iArr = new int[usedLength + 1];
        int i3 = 32 - i2;
        iArr[0] = this.m_ints[0] << i2;
        for (int i4 = 1; i4 < usedLength; i4++) {
            int[] iArr2 = this.m_ints;
            iArr[i4] = (iArr2[i4 - 1] >>> i3) | (iArr2[i4] << i2);
        }
        iArr[usedLength] = this.m_ints[usedLength - 1] >>> i3;
        return new IntArray(iArr);
    }

    public void shiftLeft() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return;
        }
        int[] iArr = this.m_ints;
        if (iArr[usedLength - 1] < 0 && (usedLength = usedLength + 1) > iArr.length) {
            this.m_ints = resizedInts(iArr.length + 1);
        }
        int i2 = 0;
        boolean z2 = false;
        while (i2 < usedLength) {
            int[] iArr2 = this.m_ints;
            int i3 = iArr2[i2];
            boolean z3 = i3 < 0;
            int i4 = i3 << 1;
            iArr2[i2] = i4;
            if (z2) {
                iArr2[i2] = i4 | 1;
            }
            i2++;
            z2 = z3;
        }
    }

    public IntArray square(int i2) {
        int[] iArr = {0, 1, 4, 5, 16, 17, 20, 21, 64, 65, 68, 69, 80, 81, 84, 85};
        int i3 = (i2 + 31) >> 5;
        if (this.m_ints.length < i3) {
            this.m_ints = resizedInts(i3);
        }
        IntArray intArray = new IntArray(i3 + i3);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < 4; i6++) {
                i5 = (i5 >>> 8) | (iArr[(this.m_ints[i4] >>> (i6 * 4)) & 15] << 24);
            }
            int i7 = i4 + i4;
            intArray.m_ints[i7] = i5;
            int i8 = this.m_ints[i4] >>> 16;
            int i9 = 0;
            for (int i10 = 0; i10 < 4; i10++) {
                i9 = (i9 >>> 8) | (iArr[(i8 >>> (i10 * 4)) & 15] << 24);
            }
            intArray.m_ints[i7 + 1] = i9;
        }
        return intArray;
    }

    public boolean testBit(int i2) {
        return ((1 << (i2 & 31)) & this.m_ints[i2 >> 5]) != 0;
    }

    public BigInteger toBigInteger() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return ECConstants.ZERO;
        }
        int i2 = usedLength - 1;
        int i3 = this.m_ints[i2];
        byte[] bArr = new byte[4];
        int i4 = 0;
        boolean z2 = false;
        for (int i5 = 3; i5 >= 0; i5--) {
            byte b3 = (byte) (i3 >>> (i5 * 8));
            if (z2 || b3 != 0) {
                bArr[i4] = b3;
                i4++;
                z2 = true;
            }
        }
        byte[] bArr2 = new byte[(i2 * 4) + i4];
        for (int i6 = 0; i6 < i4; i6++) {
            bArr2[i6] = bArr[i6];
        }
        for (int i7 = usedLength - 2; i7 >= 0; i7--) {
            int i8 = 3;
            while (i8 >= 0) {
                bArr2[i4] = (byte) (this.m_ints[i7] >>> (i8 * 8));
                i8--;
                i4++;
            }
        }
        return new BigInteger(1, bArr2);
    }

    public String toString() {
        int usedLength = getUsedLength();
        if (usedLength == 0) {
            return "0";
        }
        StringBuffer stringBuffer = new StringBuffer(Integer.toBinaryString(this.m_ints[usedLength - 1]));
        for (int i2 = usedLength - 2; i2 >= 0; i2--) {
            String binaryString = Integer.toBinaryString(this.m_ints[i2]);
            for (int length = binaryString.length(); length < 8; length++) {
                binaryString = "0" + binaryString;
            }
            stringBuffer.append(binaryString);
        }
        return stringBuffer.toString();
    }
}
