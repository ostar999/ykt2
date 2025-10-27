package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    /* renamed from: com.google.zxing.qrcode.encoder.Encoder$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$qrcode$decoder$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$qrcode$decoder$Mode = iArr;
            try {
                iArr[Mode.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.ALPHANUMERIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$qrcode$decoder$Mode[Mode.KANJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private Encoder() {
    }

    public static void append8BitBytes(String str, BitArray bitArray, String str2) throws WriterException, UnsupportedEncodingException {
        try {
            for (byte b3 : str.getBytes(str2)) {
                bitArray.appendBits(b3, 8);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new WriterException(e2);
        }
    }

    public static void appendAlphanumericBytes(CharSequence charSequence, BitArray bitArray) throws WriterException {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            int alphanumericCode = getAlphanumericCode(charSequence.charAt(i2));
            if (alphanumericCode == -1) {
                throw new WriterException();
            }
            int i3 = i2 + 1;
            if (i3 < length) {
                int alphanumericCode2 = getAlphanumericCode(charSequence.charAt(i3));
                if (alphanumericCode2 == -1) {
                    throw new WriterException();
                }
                bitArray.appendBits((alphanumericCode * 45) + alphanumericCode2, 11);
                i2 += 2;
            } else {
                bitArray.appendBits(alphanumericCode, 6);
                i2 = i3;
            }
        }
    }

    public static void appendBytes(String str, Mode mode, BitArray bitArray, String str2) throws WriterException, UnsupportedEncodingException {
        int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()];
        if (i2 == 1) {
            appendNumericBytes(str, bitArray);
            return;
        }
        if (i2 == 2) {
            appendAlphanumericBytes(str, bitArray);
            return;
        }
        if (i2 == 3) {
            append8BitBytes(str, bitArray, str2);
        } else {
            if (i2 == 4) {
                appendKanjiBytes(str, bitArray);
                return;
            }
            throw new WriterException("Invalid mode: " + mode);
        }
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitArray bitArray) {
        bitArray.appendBits(Mode.ECI.getBits(), 4);
        bitArray.appendBits(characterSetECI.getValue(), 8);
    }

    public static void appendKanjiBytes(String str, BitArray bitArray) throws WriterException, UnsupportedEncodingException {
        int i2;
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            for (int i3 = 0; i3 < length; i3 += 2) {
                int i4 = ((bytes[i3] & 255) << 8) | (bytes[i3 + 1] & 255);
                int i5 = 33088;
                if (i4 >= 33088 && i4 <= 40956) {
                    i2 = i4 - i5;
                } else if (i4 < 57408 || i4 > 60351) {
                    i2 = -1;
                } else {
                    i5 = 49472;
                    i2 = i4 - i5;
                }
                if (i2 == -1) {
                    throw new WriterException("Invalid byte sequence");
                }
                bitArray.appendBits(((i2 >> 8) * 192) + (i2 & 255), 13);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new WriterException(e2);
        }
    }

    public static void appendLengthInfo(int i2, Version version, Mode mode, BitArray bitArray) throws WriterException {
        int characterCountBits = mode.getCharacterCountBits(version);
        int i3 = 1 << characterCountBits;
        if (i2 < i3) {
            bitArray.appendBits(i2, characterCountBits);
            return;
        }
        throw new WriterException(i2 + " is bigger than " + (i3 - 1));
    }

    public static void appendModeInfo(Mode mode, BitArray bitArray) {
        bitArray.appendBits(mode.getBits(), 4);
    }

    public static void appendNumericBytes(CharSequence charSequence, BitArray bitArray) {
        int length = charSequence.length();
        int i2 = 0;
        while (i2 < length) {
            int iCharAt = charSequence.charAt(i2) - '0';
            int i3 = i2 + 2;
            if (i3 < length) {
                bitArray.appendBits((iCharAt * 100) + ((charSequence.charAt(i2 + 1) - '0') * 10) + (charSequence.charAt(i3) - '0'), 10);
                i2 += 3;
            } else {
                i2++;
                if (i2 < length) {
                    bitArray.appendBits((iCharAt * 10) + (charSequence.charAt(i2) - '0'), 7);
                    i2 = i3;
                } else {
                    bitArray.appendBits(iCharAt, 4);
                }
            }
        }
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1(byteMatrix) + MaskUtil.applyMaskPenaltyRule2(byteMatrix) + MaskUtil.applyMaskPenaltyRule3(byteMatrix) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    private static int chooseMaskPattern(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, ByteMatrix byteMatrix) throws WriterException {
        int i2 = Integer.MAX_VALUE;
        int i3 = -1;
        for (int i4 = 0; i4 < 8; i4++) {
            MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, version, i4, byteMatrix);
            int iCalculateMaskPenalty = calculateMaskPenalty(byteMatrix);
            if (iCalculateMaskPenalty < i2) {
                i3 = i4;
                i2 = iCalculateMaskPenalty;
            }
        }
        return i3;
    }

    public static Mode chooseMode(String str) {
        return chooseMode(str, null);
    }

    private static Version chooseVersion(int i2, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i3 = 1; i3 <= 40; i3++) {
            Version versionForNumber = Version.getVersionForNumber(i3);
            if (versionForNumber.getTotalCodewords() - versionForNumber.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() >= (i2 + 7) / 8) {
                return versionForNumber;
            }
        }
        throw new WriterException("Data too big");
    }

    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return encode(str, errorCorrectionLevel, null);
    }

    public static byte[] generateECBytes(byte[] bArr, int i2) {
        int length = bArr.length;
        int[] iArr = new int[length + i2];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(iArr, i2);
        byte[] bArr2 = new byte[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            bArr2[i4] = (byte) iArr[length + i4];
        }
        return bArr2;
    }

    public static int getAlphanumericCode(int i2) {
        int[] iArr = ALPHANUMERIC_TABLE;
        if (i2 < iArr.length) {
            return iArr[i2];
        }
        return -1;
    }

    public static void getNumDataBytesAndNumECBytesForBlockID(int i2, int i3, int i4, int i5, int[] iArr, int[] iArr2) throws WriterException {
        if (i5 >= i4) {
            throw new WriterException("Block ID too large");
        }
        int i6 = i2 % i4;
        int i7 = i4 - i6;
        int i8 = i2 / i4;
        int i9 = i8 + 1;
        int i10 = i3 / i4;
        int i11 = i10 + 1;
        int i12 = i8 - i10;
        int i13 = i9 - i11;
        if (i12 != i13) {
            throw new WriterException("EC bytes mismatch");
        }
        if (i4 != i7 + i6) {
            throw new WriterException("RS blocks mismatch");
        }
        if (i2 != ((i10 + i12) * i7) + ((i11 + i13) * i6)) {
            throw new WriterException("Total bytes mismatch");
        }
        if (i5 < i7) {
            iArr[0] = i10;
            iArr2[0] = i12;
        } else {
            iArr[0] = i11;
            iArr2[0] = i13;
        }
    }

    public static BitArray interleaveWithECBytes(BitArray bitArray, int i2, int i3, int i4) throws WriterException {
        if (bitArray.getSizeInBytes() != i3) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        ArrayList arrayList = new ArrayList(i4);
        int i5 = 0;
        int iMax = 0;
        int iMax2 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int[] iArr = new int[1];
            int[] iArr2 = new int[1];
            getNumDataBytesAndNumECBytesForBlockID(i2, i3, i4, i6, iArr, iArr2);
            int i7 = iArr[0];
            byte[] bArr = new byte[i7];
            bitArray.toBytes(i5 * 8, bArr, 0, i7);
            byte[] bArrGenerateECBytes = generateECBytes(bArr, iArr2[0]);
            arrayList.add(new BlockPair(bArr, bArrGenerateECBytes));
            iMax = Math.max(iMax, i7);
            iMax2 = Math.max(iMax2, bArrGenerateECBytes.length);
            i5 += iArr[0];
        }
        if (i3 != i5) {
            throw new WriterException("Data bytes does not match offset");
        }
        BitArray bitArray2 = new BitArray();
        for (int i8 = 0; i8 < iMax; i8++) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                byte[] dataBytes = ((BlockPair) it.next()).getDataBytes();
                if (i8 < dataBytes.length) {
                    bitArray2.appendBits(dataBytes[i8], 8);
                }
            }
        }
        for (int i9 = 0; i9 < iMax2; i9++) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                byte[] errorCorrectionBytes = ((BlockPair) it2.next()).getErrorCorrectionBytes();
                if (i9 < errorCorrectionBytes.length) {
                    bitArray2.appendBits(errorCorrectionBytes[i9], 8);
                }
            }
        }
        if (i2 == bitArray2.getSizeInBytes()) {
            return bitArray2;
        }
        throw new WriterException("Interleaving error: " + i2 + " and " + bitArray2.getSizeInBytes() + " differ.");
    }

    private static boolean isOnlyDoubleByteKanji(String str) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i2 = 0; i2 < length; i2 += 2) {
                int i3 = bytes[i2] & 255;
                if ((i3 < 129 || i3 > 159) && (i3 < 224 || i3 > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    public static void terminateBits(int i2, BitArray bitArray) throws WriterException {
        int i3 = i2 << 3;
        if (bitArray.getSize() > i3) {
            throw new WriterException("data bits cannot fit in the QR Code" + bitArray.getSize() + " > " + i3);
        }
        for (int i4 = 0; i4 < 4 && bitArray.getSize() < i3; i4++) {
            bitArray.appendBit(false);
        }
        int size = bitArray.getSize() & 7;
        if (size > 0) {
            while (size < 8) {
                bitArray.appendBit(false);
                size++;
            }
        }
        int sizeInBytes = i2 - bitArray.getSizeInBytes();
        for (int i5 = 0; i5 < sizeInBytes; i5++) {
            bitArray.appendBits((i5 & 1) == 0 ? 236 : 17, 8);
        }
        if (bitArray.getSize() != i3) {
            throw new WriterException("Bits size does not equal capacity");
        }
    }

    private static Mode chooseMode(String str, String str2) {
        if ("Shift_JIS".equals(str2)) {
            return isOnlyDoubleByteKanji(str) ? Mode.KANJI : Mode.BYTE;
        }
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt >= '0' && cCharAt <= '9') {
                z3 = true;
            } else {
                if (getAlphanumericCode(cCharAt) == -1) {
                    return Mode.BYTE;
                }
                z2 = true;
            }
        }
        return z2 ? Mode.ALPHANUMERIC : z3 ? Mode.NUMERIC : Mode.BYTE;
    }

    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> map) throws WriterException, UnsupportedEncodingException {
        CharacterSetECI characterSetECIByName;
        String str2 = map == null ? null : (String) map.get(EncodeHintType.CHARACTER_SET);
        if (str2 == null) {
            str2 = "ISO-8859-1";
        }
        Mode modeChooseMode = chooseMode(str, str2);
        BitArray bitArray = new BitArray();
        Mode mode = Mode.BYTE;
        if (modeChooseMode == mode && !"ISO-8859-1".equals(str2) && (characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(str2)) != null) {
            appendECI(characterSetECIByName, bitArray);
        }
        appendModeInfo(modeChooseMode, bitArray);
        BitArray bitArray2 = new BitArray();
        appendBytes(str, modeChooseMode, bitArray2, str2);
        Version versionChooseVersion = chooseVersion(bitArray.getSize() + modeChooseMode.getCharacterCountBits(chooseVersion(bitArray.getSize() + modeChooseMode.getCharacterCountBits(Version.getVersionForNumber(1)) + bitArray2.getSize(), errorCorrectionLevel)) + bitArray2.getSize(), errorCorrectionLevel);
        BitArray bitArray3 = new BitArray();
        bitArray3.appendBitArray(bitArray);
        appendLengthInfo(modeChooseMode == mode ? bitArray2.getSizeInBytes() : str.length(), versionChooseVersion, modeChooseMode, bitArray3);
        bitArray3.appendBitArray(bitArray2);
        Version.ECBlocks eCBlocksForLevel = versionChooseVersion.getECBlocksForLevel(errorCorrectionLevel);
        int totalCodewords = versionChooseVersion.getTotalCodewords() - eCBlocksForLevel.getTotalECCodewords();
        terminateBits(totalCodewords, bitArray3);
        BitArray bitArrayInterleaveWithECBytes = interleaveWithECBytes(bitArray3, versionChooseVersion.getTotalCodewords(), totalCodewords, eCBlocksForLevel.getNumBlocks());
        QRCode qRCode = new QRCode();
        qRCode.setECLevel(errorCorrectionLevel);
        qRCode.setMode(modeChooseMode);
        qRCode.setVersion(versionChooseVersion);
        int dimensionForVersion = versionChooseVersion.getDimensionForVersion();
        ByteMatrix byteMatrix = new ByteMatrix(dimensionForVersion, dimensionForVersion);
        int iChooseMaskPattern = chooseMaskPattern(bitArrayInterleaveWithECBytes, errorCorrectionLevel, versionChooseVersion, byteMatrix);
        qRCode.setMaskPattern(iChooseMaskPattern);
        MatrixUtil.buildMatrix(bitArrayInterleaveWithECBytes, errorCorrectionLevel, versionChooseVersion, iChooseMaskPattern, byteMatrix);
        qRCode.setMatrix(byteMatrix);
        return qRCode;
    }
}
