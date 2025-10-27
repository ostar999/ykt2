package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

/* loaded from: classes4.dex */
public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    private static int[] bitsToWords(BitArray bitArray, int i2, int i3) {
        int[] iArr = new int[i3];
        int size = bitArray.getSize() / i2;
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                i5 |= bitArray.get((i4 * i2) + i6) ? 1 << ((i2 - i6) - 1) : 0;
            }
            iArr[i4] = i5;
        }
        return iArr;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4 += 2) {
            int i5 = i2 - i4;
            int i6 = i5;
            while (true) {
                int i7 = i2 + i4;
                if (i6 <= i7) {
                    bitMatrix.set(i6, i5);
                    bitMatrix.set(i6, i7);
                    bitMatrix.set(i5, i6);
                    bitMatrix.set(i7, i6);
                    i6++;
                }
            }
        }
        int i8 = i2 - i3;
        bitMatrix.set(i8, i8);
        int i9 = i8 + 1;
        bitMatrix.set(i9, i8);
        bitMatrix.set(i8, i9);
        int i10 = i2 + i3;
        bitMatrix.set(i10, i8);
        bitMatrix.set(i10, i9);
        bitMatrix.set(i10, i10 - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean z2, int i2, BitArray bitArray) {
        int i3 = i2 / 2;
        int i4 = 0;
        if (z2) {
            while (i4 < 7) {
                int i5 = (i3 - 3) + i4;
                if (bitArray.get(i4)) {
                    bitMatrix.set(i5, i3 - 5);
                }
                if (bitArray.get(i4 + 7)) {
                    bitMatrix.set(i3 + 5, i5);
                }
                if (bitArray.get(20 - i4)) {
                    bitMatrix.set(i5, i3 + 5);
                }
                if (bitArray.get(27 - i4)) {
                    bitMatrix.set(i3 - 5, i5);
                }
                i4++;
            }
            return;
        }
        while (i4 < 10) {
            int i6 = (i3 - 5) + i4 + (i4 / 5);
            if (bitArray.get(i4)) {
                bitMatrix.set(i6, i3 - 7);
            }
            if (bitArray.get(i4 + 10)) {
                bitMatrix.set(i3 + 7, i6);
            }
            if (bitArray.get(29 - i4)) {
                bitMatrix.set(i6, i3 + 7);
            }
            if (bitArray.get(39 - i4)) {
                bitMatrix.set(i3 - 7, i6);
            }
            i4++;
        }
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    private static BitArray generateCheckWords(BitArray bitArray, int i2, int i3) {
        int size = bitArray.getSize() / i3;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i3));
        int i4 = i2 / i3;
        int[] iArrBitsToWords = bitsToWords(bitArray, i3, i4);
        reedSolomonEncoder.encode(iArrBitsToWords, i4 - size);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, i2 % i3);
        for (int i5 : iArrBitsToWords) {
            bitArray2.appendBits(i5, i3);
        }
        return bitArray2;
    }

    public static BitArray generateModeMessage(boolean z2, int i2, int i3) {
        BitArray bitArray = new BitArray();
        if (z2) {
            bitArray.appendBits(i2 - 1, 2);
            bitArray.appendBits(i3 - 1, 6);
            return generateCheckWords(bitArray, 28, 4);
        }
        bitArray.appendBits(i2 - 1, 5);
        bitArray.appendBits(i3 - 1, 11);
        return generateCheckWords(bitArray, 40, 4);
    }

    private static GenericGF getGF(int i2) {
        if (i2 == 4) {
            return GenericGF.AZTEC_PARAM;
        }
        if (i2 == 6) {
            return GenericGF.AZTEC_DATA_6;
        }
        if (i2 == 8) {
            return GenericGF.AZTEC_DATA_8;
        }
        if (i2 == 10) {
            return GenericGF.AZTEC_DATA_10;
        }
        if (i2 != 12) {
            return null;
        }
        return GenericGF.AZTEC_DATA_12;
    }

    public static BitArray stuffBits(BitArray bitArray, int i2) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i3 = (1 << i2) - 2;
        int i4 = 0;
        while (i4 < size) {
            int i5 = 0;
            for (int i6 = 0; i6 < i2; i6++) {
                int i7 = i4 + i6;
                if (i7 >= size || bitArray.get(i7)) {
                    i5 |= 1 << ((i2 - 1) - i6);
                }
            }
            int i8 = i5 & i3;
            if (i8 == i3) {
                bitArray2.appendBits(i8, i2);
            } else if (i8 == 0) {
                bitArray2.appendBits(i5 | 1, i2);
            } else {
                bitArray2.appendBits(i5, i2);
                i4 += i2;
            }
            i4--;
            i4 += i2;
        }
        return bitArray2;
    }

    private static int totalBitsInLayer(int i2, boolean z2) {
        return ((z2 ? 88 : 112) + (i2 * 16)) * i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static AztecCode encode(byte[] bArr, int i2, int i3) {
        int i4;
        BitArray bitArrayStuffBits;
        boolean z2;
        int iAbs;
        int i5;
        int i6;
        int i7;
        BitArray bitArrayEncode = new HighLevelEncoder(bArr).encode();
        int size = ((bitArrayEncode.getSize() * i2) / 100) + 11;
        int size2 = bitArrayEncode.getSize() + size;
        int i8 = 4;
        int i9 = 0;
        int i10 = 1;
        if (i3 == 0) {
            BitArray bitArrayStuffBits2 = null;
            int i11 = 0;
            int i12 = 0;
            while (i11 <= 32) {
                boolean z3 = i11 <= 3 ? i10 : i9;
                int i13 = z3 != 0 ? i11 + 1 : i11;
                int i14 = totalBitsInLayer(i13, z3);
                if (size2 > i14) {
                    i4 = i12;
                } else {
                    i4 = WORD_SIZE[i13];
                    if (i12 != i4) {
                        bitArrayStuffBits2 = stuffBits(bitArrayEncode, i4);
                    } else {
                        i4 = i12;
                    }
                    int i15 = i14 - (i14 % i4);
                    if ((z3 == 0 || bitArrayStuffBits2.getSize() <= i4 * 64) && bitArrayStuffBits2.getSize() + size <= i15) {
                        bitArrayStuffBits = bitArrayStuffBits2;
                        z2 = z3;
                        iAbs = i13;
                        i5 = i14;
                        i6 = i4;
                    }
                }
                i11++;
                i10 = i10;
                i12 = i4;
                i8 = 4;
                i9 = 0;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z2 = i3 < 0;
        iAbs = Math.abs(i3);
        if (iAbs > (z2 ? 4 : 32)) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(i3)));
        }
        i5 = totalBitsInLayer(iAbs, z2);
        i6 = WORD_SIZE[iAbs];
        int i16 = i5 - (i5 % i6);
        bitArrayStuffBits = stuffBits(bitArrayEncode, i6);
        if (bitArrayStuffBits.getSize() + size > i16) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z2 && bitArrayStuffBits.getSize() > i6 * 64) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        BitArray bitArrayGenerateCheckWords = generateCheckWords(bitArrayStuffBits, i5, i6);
        int size3 = bitArrayStuffBits.getSize() / i6;
        BitArray bitArrayGenerateModeMessage = generateModeMessage(z2, iAbs, size3);
        int i17 = iAbs * 4;
        int i18 = z2 ? i17 + 11 : i17 + 14;
        int[] iArr = new int[i18];
        int i19 = 2;
        if (z2) {
            for (int i20 = i9; i20 < i18; i20++) {
                iArr[i20] = i20;
            }
            i7 = i18;
        } else {
            int i21 = i18 / 2;
            i7 = i18 + 1 + (((i21 - 1) / 15) * 2);
            int i22 = i7 / 2;
            for (int i23 = i9; i23 < i21; i23++) {
                iArr[(i21 - i23) - 1] = (i22 - r15) - 1;
                iArr[i21 + i23] = (i23 / 15) + i23 + i22 + i10;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i7);
        int i24 = i9;
        int i25 = i24;
        while (i24 < iAbs) {
            int i26 = (iAbs - i24) * i8;
            int i27 = z2 ? i26 + 9 : i26 + 12;
            int i28 = i9;
            while (i28 < i27) {
                int i29 = i28 * 2;
                while (i9 < i19) {
                    if (bitArrayGenerateCheckWords.get(i25 + i29 + i9)) {
                        int i30 = i24 * 2;
                        bitMatrix.set(iArr[i30 + i9], iArr[i30 + i28]);
                    }
                    if (bitArrayGenerateCheckWords.get((i27 * 2) + i25 + i29 + i9)) {
                        int i31 = i24 * 2;
                        bitMatrix.set(iArr[i31 + i28], iArr[((i18 - 1) - i31) - i9]);
                    }
                    if (bitArrayGenerateCheckWords.get((i27 * 4) + i25 + i29 + i9)) {
                        int i32 = (i18 - 1) - (i24 * 2);
                        bitMatrix.set(iArr[i32 - i9], iArr[i32 - i28]);
                    }
                    if (bitArrayGenerateCheckWords.get((i27 * 6) + i25 + i29 + i9)) {
                        int i33 = i24 * 2;
                        bitMatrix.set(iArr[((i18 - 1) - i33) - i28], iArr[i33 + i9]);
                    }
                    i9++;
                    i19 = 2;
                }
                i28++;
                i9 = 0;
                i19 = 2;
            }
            i25 += i27 * 8;
            i24++;
            i8 = 4;
            i9 = 0;
            i19 = 2;
        }
        drawModeMessage(bitMatrix, z2, i7, bitArrayGenerateModeMessage);
        if (z2) {
            drawBullsEye(bitMatrix, i7 / 2, 5);
        } else {
            int i34 = i7 / 2;
            drawBullsEye(bitMatrix, i34, 7);
            int i35 = 0;
            int i36 = 0;
            while (i36 < (i18 / 2) - 1) {
                for (int i37 = i34 & 1; i37 < i7; i37 += 2) {
                    int i38 = i34 - i35;
                    bitMatrix.set(i38, i37);
                    int i39 = i34 + i35;
                    bitMatrix.set(i39, i37);
                    bitMatrix.set(i37, i38);
                    bitMatrix.set(i37, i39);
                }
                i36 += 15;
                i35 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z2);
        aztecCode.setSize(i7);
        aztecCode.setLayers(iAbs);
        aztecCode.setCodeWords(size3);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }
}
