package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
final class X12Encoder extends C40Encoder {
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            encodeChar(currentChar, sb);
            if (sb.length() % 3 == 0) {
                C40Encoder.writeNextTriplet(encoderContext, sb);
                int iLookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode());
                if (iLookAheadTest != getEncodingMode()) {
                    encoderContext.signalEncoderChange(iLookAheadTest);
                    break;
                }
            }
        }
        handleEOD(encoderContext, sb);
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public int encodeChar(char c3, StringBuilder sb) {
        if (c3 == '\r') {
            sb.append((char) 0);
        } else if (c3 == '*') {
            sb.append((char) 1);
        } else if (c3 == '>') {
            sb.append((char) 2);
        } else if (c3 == ' ') {
            sb.append((char) 3);
        } else if (c3 >= '0' && c3 <= '9') {
            sb.append((char) ((c3 - '0') + 4));
        } else if (c3 < 'A' || c3 > 'Z') {
            HighLevelEncoder.illegalCharacter(c3);
        } else {
            sb.append((char) ((c3 - 'A') + 14));
        }
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 3;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
        int length = sb.length();
        if (length == 2) {
            encoderContext.writeCodeword((char) 254);
            encoderContext.pos -= 2;
            encoderContext.signalEncoderChange(0);
        } else if (length == 1) {
            encoderContext.pos--;
            if (dataCapacity > 1) {
                encoderContext.writeCodeword((char) 254);
            }
            encoderContext.signalEncoderChange(0);
        }
    }
}
