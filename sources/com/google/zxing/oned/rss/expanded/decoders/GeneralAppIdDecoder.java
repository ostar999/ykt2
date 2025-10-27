package com.google.zxing.oned.rss.expanded.decoders;

import cn.hutool.core.text.CharPool;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.yikaobang.yixue.R2;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
final class GeneralAppIdDecoder {
    private final BitArray information;
    private final CurrentParsingState current = new CurrentParsingState();
    private final StringBuilder buffer = new StringBuilder();

    public GeneralAppIdDecoder(BitArray bitArray) {
        this.information = bitArray;
    }

    private DecodedChar decodeAlphanumeric(int i2) {
        char c3;
        int iExtractNumericValueFromBitArray = extractNumericValueFromBitArray(i2, 5);
        if (iExtractNumericValueFromBitArray == 15) {
            return new DecodedChar(i2 + 5, Typography.dollar);
        }
        if (iExtractNumericValueFromBitArray >= 5 && iExtractNumericValueFromBitArray < 15) {
            return new DecodedChar(i2 + 5, (char) ((iExtractNumericValueFromBitArray + 48) - 5));
        }
        int iExtractNumericValueFromBitArray2 = extractNumericValueFromBitArray(i2, 6);
        if (iExtractNumericValueFromBitArray2 >= 32 && iExtractNumericValueFromBitArray2 < 58) {
            return new DecodedChar(i2 + 6, (char) (iExtractNumericValueFromBitArray2 + 33));
        }
        switch (iExtractNumericValueFromBitArray2) {
            case 58:
                c3 = '*';
                break;
            case 59:
                c3 = ',';
                break;
            case 60:
                c3 = CharPool.DASHED;
                break;
            case 61:
                c3 = '.';
                break;
            case 62:
                c3 = '/';
                break;
            default:
                throw new IllegalStateException("Decoding invalid alphanumeric value: " + iExtractNumericValueFromBitArray2);
        }
        return new DecodedChar(i2 + 6, c3);
    }

    private DecodedChar decodeIsoIec646(int i2) throws FormatException {
        char c3;
        int iExtractNumericValueFromBitArray = extractNumericValueFromBitArray(i2, 5);
        if (iExtractNumericValueFromBitArray == 15) {
            return new DecodedChar(i2 + 5, Typography.dollar);
        }
        if (iExtractNumericValueFromBitArray >= 5 && iExtractNumericValueFromBitArray < 15) {
            return new DecodedChar(i2 + 5, (char) ((iExtractNumericValueFromBitArray + 48) - 5));
        }
        int iExtractNumericValueFromBitArray2 = extractNumericValueFromBitArray(i2, 7);
        if (iExtractNumericValueFromBitArray2 >= 64 && iExtractNumericValueFromBitArray2 < 90) {
            return new DecodedChar(i2 + 7, (char) (iExtractNumericValueFromBitArray2 + 1));
        }
        if (iExtractNumericValueFromBitArray2 >= 90 && iExtractNumericValueFromBitArray2 < 116) {
            return new DecodedChar(i2 + 7, (char) (iExtractNumericValueFromBitArray2 + 7));
        }
        switch (extractNumericValueFromBitArray(i2, 8)) {
            case 232:
                c3 = '!';
                break;
            case 233:
                c3 = '\"';
                break;
            case 234:
                c3 = '%';
                break;
            case 235:
                c3 = '&';
                break;
            case 236:
                c3 = CharPool.SINGLE_QUOTE;
                break;
            case 237:
                c3 = '(';
                break;
            case 238:
                c3 = ')';
                break;
            case 239:
                c3 = '*';
                break;
            case 240:
                c3 = '+';
                break;
            case 241:
                c3 = ',';
                break;
            case 242:
                c3 = CharPool.DASHED;
                break;
            case 243:
                c3 = '.';
                break;
            case 244:
                c3 = '/';
                break;
            case R2.attr.actionModeCloseDrawable /* 245 */:
                c3 = ':';
                break;
            case R2.attr.actionModeCopyDrawable /* 246 */:
                c3 = ';';
                break;
            case R2.attr.actionModeCutDrawable /* 247 */:
                c3 = Typography.less;
                break;
            case R2.attr.actionModeFindDrawable /* 248 */:
                c3 = '=';
                break;
            case R2.attr.actionModePasteDrawable /* 249 */:
                c3 = Typography.greater;
                break;
            case 250:
                c3 = '?';
                break;
            case R2.attr.actionModeSelectAllDrawable /* 251 */:
                c3 = '_';
                break;
            case R2.attr.actionModeShareDrawable /* 252 */:
                c3 = ' ';
                break;
            default:
                throw FormatException.getFormatInstance();
        }
        return new DecodedChar(i2 + 8, c3);
    }

    private DecodedNumeric decodeNumeric(int i2) throws FormatException {
        int i3 = i2 + 7;
        if (i3 > this.information.getSize()) {
            int iExtractNumericValueFromBitArray = extractNumericValueFromBitArray(i2, 4);
            return iExtractNumericValueFromBitArray == 0 ? new DecodedNumeric(this.information.getSize(), 10, 10) : new DecodedNumeric(this.information.getSize(), iExtractNumericValueFromBitArray - 1, 10);
        }
        int iExtractNumericValueFromBitArray2 = extractNumericValueFromBitArray(i2, 7) - 8;
        return new DecodedNumeric(i3, iExtractNumericValueFromBitArray2 / 11, iExtractNumericValueFromBitArray2 % 11);
    }

    private boolean isAlphaOr646ToNumericLatch(int i2) {
        int i3 = i2 + 3;
        if (i3 > this.information.getSize()) {
            return false;
        }
        while (i2 < i3) {
            if (this.information.get(i2)) {
                return false;
            }
            i2++;
        }
        return true;
    }

    private boolean isAlphaTo646ToAlphaLatch(int i2) {
        int i3;
        if (i2 + 1 > this.information.getSize()) {
            return false;
        }
        for (int i4 = 0; i4 < 5 && (i3 = i4 + i2) < this.information.getSize(); i4++) {
            if (i4 == 2) {
                if (!this.information.get(i2 + 2)) {
                    return false;
                }
            } else if (this.information.get(i3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumericToAlphaNumericLatch(int i2) {
        int i3;
        if (i2 + 1 > this.information.getSize()) {
            return false;
        }
        for (int i4 = 0; i4 < 4 && (i3 = i4 + i2) < this.information.getSize(); i4++) {
            if (this.information.get(i3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isStillAlpha(int i2) {
        int iExtractNumericValueFromBitArray;
        if (i2 + 5 > this.information.getSize()) {
            return false;
        }
        int iExtractNumericValueFromBitArray2 = extractNumericValueFromBitArray(i2, 5);
        if (iExtractNumericValueFromBitArray2 < 5 || iExtractNumericValueFromBitArray2 >= 16) {
            return i2 + 6 <= this.information.getSize() && (iExtractNumericValueFromBitArray = extractNumericValueFromBitArray(i2, 6)) >= 16 && iExtractNumericValueFromBitArray < 63;
        }
        return true;
    }

    private boolean isStillIsoIec646(int i2) {
        int iExtractNumericValueFromBitArray;
        if (i2 + 5 > this.information.getSize()) {
            return false;
        }
        int iExtractNumericValueFromBitArray2 = extractNumericValueFromBitArray(i2, 5);
        if (iExtractNumericValueFromBitArray2 >= 5 && iExtractNumericValueFromBitArray2 < 16) {
            return true;
        }
        if (i2 + 7 > this.information.getSize()) {
            return false;
        }
        int iExtractNumericValueFromBitArray3 = extractNumericValueFromBitArray(i2, 7);
        if (iExtractNumericValueFromBitArray3 < 64 || iExtractNumericValueFromBitArray3 >= 116) {
            return i2 + 8 <= this.information.getSize() && (iExtractNumericValueFromBitArray = extractNumericValueFromBitArray(i2, 8)) >= 232 && iExtractNumericValueFromBitArray < 253;
        }
        return true;
    }

    private boolean isStillNumeric(int i2) {
        if (i2 + 7 > this.information.getSize()) {
            return i2 + 4 <= this.information.getSize();
        }
        int i3 = i2;
        while (true) {
            int i4 = i2 + 3;
            if (i3 >= i4) {
                return this.information.get(i4);
            }
            if (this.information.get(i3)) {
                return true;
            }
            i3++;
        }
    }

    private BlockParsedResult parseAlphaBlock() {
        while (isStillAlpha(this.current.getPosition())) {
            DecodedChar decodedCharDecodeAlphanumeric = decodeAlphanumeric(this.current.getPosition());
            this.current.setPosition(decodedCharDecodeAlphanumeric.getNewPosition());
            if (decodedCharDecodeAlphanumeric.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedCharDecodeAlphanumeric.getValue());
        }
        if (isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
        } else if (isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
            if (this.current.getPosition() + 5 < this.information.getSize()) {
                this.current.incrementPosition(5);
            } else {
                this.current.setPosition(this.information.getSize());
            }
            this.current.setIsoIec646();
        }
        return new BlockParsedResult(false);
    }

    private DecodedInformation parseBlocks() throws FormatException {
        BlockParsedResult numericBlock;
        boolean zIsFinished;
        do {
            int position = this.current.getPosition();
            if (this.current.isAlpha()) {
                numericBlock = parseAlphaBlock();
                zIsFinished = numericBlock.isFinished();
            } else if (this.current.isIsoIec646()) {
                numericBlock = parseIsoIec646Block();
                zIsFinished = numericBlock.isFinished();
            } else {
                numericBlock = parseNumericBlock();
                zIsFinished = numericBlock.isFinished();
            }
            if (!(position != this.current.getPosition()) && !zIsFinished) {
                break;
            }
        } while (!zIsFinished);
        return numericBlock.getDecodedInformation();
    }

    private BlockParsedResult parseIsoIec646Block() throws FormatException {
        while (isStillIsoIec646(this.current.getPosition())) {
            DecodedChar decodedCharDecodeIsoIec646 = decodeIsoIec646(this.current.getPosition());
            this.current.setPosition(decodedCharDecodeIsoIec646.getNewPosition());
            if (decodedCharDecodeIsoIec646.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedCharDecodeIsoIec646.getValue());
        }
        if (isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
        } else if (isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
            if (this.current.getPosition() + 5 < this.information.getSize()) {
                this.current.incrementPosition(5);
            } else {
                this.current.setPosition(this.information.getSize());
            }
            this.current.setAlpha();
        }
        return new BlockParsedResult(false);
    }

    private BlockParsedResult parseNumericBlock() throws FormatException {
        while (isStillNumeric(this.current.getPosition())) {
            DecodedNumeric decodedNumericDecodeNumeric = decodeNumeric(this.current.getPosition());
            this.current.setPosition(decodedNumericDecodeNumeric.getNewPosition());
            if (decodedNumericDecodeNumeric.isFirstDigitFNC1()) {
                return new BlockParsedResult(decodedNumericDecodeNumeric.isSecondDigitFNC1() ? new DecodedInformation(this.current.getPosition(), this.buffer.toString()) : new DecodedInformation(this.current.getPosition(), this.buffer.toString(), decodedNumericDecodeNumeric.getSecondDigit()), true);
            }
            this.buffer.append(decodedNumericDecodeNumeric.getFirstDigit());
            if (decodedNumericDecodeNumeric.isSecondDigitFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedNumericDecodeNumeric.getSecondDigit());
        }
        if (isNumericToAlphaNumericLatch(this.current.getPosition())) {
            this.current.setAlpha();
            this.current.incrementPosition(4);
        }
        return new BlockParsedResult(false);
    }

    public String decodeAllCodes(StringBuilder sb, int i2) throws NotFoundException, FormatException {
        String str = null;
        while (true) {
            DecodedInformation decodedInformationDecodeGeneralPurposeField = decodeGeneralPurposeField(i2, str);
            String fieldsInGeneralPurpose = FieldParser.parseFieldsInGeneralPurpose(decodedInformationDecodeGeneralPurposeField.getNewString());
            if (fieldsInGeneralPurpose != null) {
                sb.append(fieldsInGeneralPurpose);
            }
            String strValueOf = decodedInformationDecodeGeneralPurposeField.isRemaining() ? String.valueOf(decodedInformationDecodeGeneralPurposeField.getRemainingValue()) : null;
            if (i2 == decodedInformationDecodeGeneralPurposeField.getNewPosition()) {
                return sb.toString();
            }
            i2 = decodedInformationDecodeGeneralPurposeField.getNewPosition();
            str = strValueOf;
        }
    }

    public DecodedInformation decodeGeneralPurposeField(int i2, String str) throws FormatException {
        this.buffer.setLength(0);
        if (str != null) {
            this.buffer.append(str);
        }
        this.current.setPosition(i2);
        DecodedInformation blocks = parseBlocks();
        return (blocks == null || !blocks.isRemaining()) ? new DecodedInformation(this.current.getPosition(), this.buffer.toString()) : new DecodedInformation(this.current.getPosition(), this.buffer.toString(), blocks.getRemainingValue());
    }

    public int extractNumericValueFromBitArray(int i2, int i3) {
        return extractNumericValueFromBitArray(this.information, i2, i3);
    }

    public static int extractNumericValueFromBitArray(BitArray bitArray, int i2, int i3) {
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            if (bitArray.get(i2 + i5)) {
                i4 |= 1 << ((i3 - i5) - 1);
            }
        }
        return i4;
    }
}
