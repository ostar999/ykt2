package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
class C40Encoder implements Encoder {
    private int backtrackOneCharacter(EncoderContext encoderContext, StringBuilder sb, StringBuilder sb2, int i2) {
        int length = sb.length();
        sb.delete(length - i2, length);
        encoderContext.pos--;
        int iEncodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
        encoderContext.resetSymbolInfo();
        return iEncodeChar;
    }

    private static String encodeToCodewords(CharSequence charSequence, int i2) {
        int iCharAt = (charSequence.charAt(i2) * 1600) + (charSequence.charAt(i2 + 1) * '(') + charSequence.charAt(i2 + 2) + 1;
        return new String(new char[]{(char) (iCharAt / 256), (char) (iCharAt % 256)});
    }

    public static void writeNextTriplet(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.writeCodewords(encodeToCodewords(sb, 0));
        sb.delete(0, 3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        int iLookAheadTest;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            int iEncodeChar = encodeChar(currentChar, sb);
            int codewordCount = encoderContext.getCodewordCount() + ((sb.length() / 3) * 2);
            encoderContext.updateSymbolInfo(codewordCount);
            int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount;
            if (!encoderContext.hasMoreCharacters()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (dataCapacity < 2 || dataCapacity > 2)) {
                    iEncodeChar = backtrackOneCharacter(encoderContext, sb, sb2, iEncodeChar);
                }
                while (sb.length() % 3 == 1 && ((iEncodeChar <= 3 && dataCapacity != 1) || iEncodeChar > 3)) {
                    iEncodeChar = backtrackOneCharacter(encoderContext, sb, sb2, iEncodeChar);
                }
            } else if (sb.length() % 3 == 0 && (iLookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode())) != getEncodingMode()) {
                encoderContext.signalEncoderChange(iLookAheadTest);
                break;
            }
        }
        handleEOD(encoderContext, sb);
    }

    public int encodeChar(char c3, StringBuilder sb) {
        if (c3 == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c3 >= '0' && c3 <= '9') {
            sb.append((char) ((c3 - '0') + 4));
            return 1;
        }
        if (c3 >= 'A' && c3 <= 'Z') {
            sb.append((char) ((c3 - 'A') + 14));
            return 1;
        }
        if (c3 >= 0 && c3 <= 31) {
            sb.append((char) 0);
            sb.append(c3);
            return 2;
        }
        if (c3 >= '!' && c3 <= '/') {
            sb.append((char) 1);
            sb.append((char) (c3 - '!'));
            return 2;
        }
        if (c3 >= ':' && c3 <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c3 - ':') + 15));
            return 2;
        }
        if (c3 >= '[' && c3 <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c3 - '[') + 22));
            return 2;
        }
        if (c3 >= '`' && c3 <= 127) {
            sb.append((char) 2);
            sb.append((char) (c3 - '`'));
            return 2;
        }
        if (c3 >= 128) {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c3 - 128), sb) + 2;
        }
        throw new IllegalArgumentException("Illegal character: " + c3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 1;
    }

    public void handleEOD(EncoderContext encoderContext, StringBuilder sb) {
        int length = (sb.length() / 3) * 2;
        int length2 = sb.length() % 3;
        int codewordCount = encoderContext.getCodewordCount() + length;
        encoderContext.updateSymbolInfo(codewordCount);
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount;
        if (length2 == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
        } else if (dataCapacity == 1 && length2 == 1) {
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
            encoderContext.pos--;
        } else {
            if (length2 != 0) {
                throw new IllegalStateException("Unexpected case. Please report!");
            }
            while (sb.length() >= 3) {
                writeNextTriplet(encoderContext, sb);
            }
            if (dataCapacity > 0 || encoderContext.hasMoreCharacters()) {
                encoderContext.writeCodeword((char) 254);
            }
        }
        encoderContext.signalEncoderChange(0);
    }
}
