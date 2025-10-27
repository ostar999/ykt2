package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Decoder {
    private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    private void correctErrors(byte[] bArr, int i2) throws ChecksumException {
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = bArr[i3] & 255;
        }
        try {
            this.rsDecoder.decode(iArr, bArr.length - i2);
            for (int i4 = 0; i4 < i2; i4++) {
                bArr[i4] = (byte) iArr[i4];
            }
        } catch (ReedSolomonException unused) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    public DecoderResult decode(boolean[][] zArr) throws ChecksumException, FormatException {
        return decode(zArr, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(boolean[][] zArr, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        int length = zArr.length;
        BitMatrix bitMatrix = new BitMatrix(length);
        for (int i2 = 0; i2 < length; i2++) {
            for (int i3 = 0; i3 < length; i3++) {
                if (zArr[i2][i3]) {
                    bitMatrix.set(i3, i2);
                }
            }
        }
        return decode(bitMatrix, map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(BitMatrix bitMatrix, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        ChecksumException e2;
        BitMatrixParser bitMatrixParser = new BitMatrixParser(bitMatrix);
        FormatException formatException = null;
        try {
            return decode(bitMatrixParser, map);
        } catch (ChecksumException e3) {
            e2 = e3;
            try {
                bitMatrixParser.remask();
                bitMatrixParser.setMirror(true);
                bitMatrixParser.readVersion();
                bitMatrixParser.readFormatInformation();
                bitMatrixParser.mirror();
                DecoderResult decoderResultDecode = decode(bitMatrixParser, map);
                decoderResultDecode.setOther(new QRCodeDecoderMetaData(true));
                return decoderResultDecode;
            } catch (ChecksumException | FormatException e4) {
                if (formatException != null) {
                    throw formatException;
                }
                if (e2 != null) {
                    throw e2;
                }
                throw e4;
            }
        } catch (FormatException e5) {
            e2 = null;
            formatException = e5;
            bitMatrixParser.remask();
            bitMatrixParser.setMirror(true);
            bitMatrixParser.readVersion();
            bitMatrixParser.readFormatInformation();
            bitMatrixParser.mirror();
            DecoderResult decoderResultDecode2 = decode(bitMatrixParser, map);
            decoderResultDecode2.setOther(new QRCodeDecoderMetaData(true));
            return decoderResultDecode2;
        }
    }

    private DecoderResult decode(BitMatrixParser bitMatrixParser, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        Version version = bitMatrixParser.readVersion();
        ErrorCorrectionLevel errorCorrectionLevel = bitMatrixParser.readFormatInformation().getErrorCorrectionLevel();
        DataBlock[] dataBlocks = DataBlock.getDataBlocks(bitMatrixParser.readCodewords(), version, errorCorrectionLevel);
        int numDataCodewords = 0;
        for (DataBlock dataBlock : dataBlocks) {
            numDataCodewords += dataBlock.getNumDataCodewords();
        }
        byte[] bArr = new byte[numDataCodewords];
        int i2 = 0;
        for (DataBlock dataBlock2 : dataBlocks) {
            byte[] codewords = dataBlock2.getCodewords();
            int numDataCodewords2 = dataBlock2.getNumDataCodewords();
            correctErrors(codewords, numDataCodewords2);
            int i3 = 0;
            while (i3 < numDataCodewords2) {
                bArr[i2] = codewords[i3];
                i3++;
                i2++;
            }
        }
        return DecodedBitStreamParser.decode(bArr, version, errorCorrectionLevel, map);
    }
}
