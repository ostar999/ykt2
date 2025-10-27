package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
final class ASCIIEncoder implements Encoder {
    private static char encodeASCIIDigits(char c3, char c4) {
        if (HighLevelEncoder.isDigit(c3) && HighLevelEncoder.isDigit(c4)) {
            return (char) (((c3 - '0') * 10) + (c4 - '0') + 130);
        }
        throw new IllegalArgumentException("not digits: " + c3 + c4);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(encoderContext.getMessage(), encoderContext.pos) >= 2) {
            encoderContext.writeCodeword(encodeASCIIDigits(encoderContext.getMessage().charAt(encoderContext.pos), encoderContext.getMessage().charAt(encoderContext.pos + 1)));
            encoderContext.pos += 2;
            return;
        }
        char currentChar = encoderContext.getCurrentChar();
        int iLookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode());
        if (iLookAheadTest == getEncodingMode()) {
            if (!HighLevelEncoder.isExtendedASCII(currentChar)) {
                encoderContext.writeCodeword((char) (currentChar + 1));
                encoderContext.pos++;
                return;
            } else {
                encoderContext.writeCodeword((char) 235);
                encoderContext.writeCodeword((char) ((currentChar - 128) + 1));
                encoderContext.pos++;
                return;
            }
        }
        if (iLookAheadTest == 1) {
            encoderContext.writeCodeword((char) 230);
            encoderContext.signalEncoderChange(1);
            return;
        }
        if (iLookAheadTest == 2) {
            encoderContext.writeCodeword((char) 239);
            encoderContext.signalEncoderChange(2);
            return;
        }
        if (iLookAheadTest == 3) {
            encoderContext.writeCodeword((char) 238);
            encoderContext.signalEncoderChange(3);
            return;
        }
        if (iLookAheadTest == 4) {
            encoderContext.writeCodeword((char) 240);
            encoderContext.signalEncoderChange(4);
        } else if (iLookAheadTest == 5) {
            encoderContext.writeCodeword((char) 231);
            encoderContext.signalEncoderChange(5);
        } else {
            throw new IllegalStateException("Illegal mode: " + iLookAheadTest);
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 0;
    }
}
