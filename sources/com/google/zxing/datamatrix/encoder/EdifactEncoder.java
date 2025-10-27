package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
final class EdifactEncoder implements Encoder {
    private static void encodeChar(char c3, StringBuilder sb) {
        if (c3 >= ' ' && c3 <= '?') {
            sb.append(c3);
        } else if (c3 < '@' || c3 > '^') {
            HighLevelEncoder.illegalCharacter(c3);
        } else {
            sb.append((char) (c3 - '@'));
        }
    }

    private static String encodeToCodewords(CharSequence charSequence, int i2) {
        int length = charSequence.length() - i2;
        if (length == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        int iCharAt = (charSequence.charAt(i2) << 18) + ((length >= 2 ? charSequence.charAt(i2 + 1) : (char) 0) << '\f') + ((length >= 3 ? charSequence.charAt(i2 + 2) : (char) 0) << 6) + (length >= 4 ? charSequence.charAt(i2 + 3) : (char) 0);
        char c3 = (char) ((iCharAt >> 16) & 255);
        char c4 = (char) ((iCharAt >> 8) & 255);
        char c5 = (char) (iCharAt & 255);
        StringBuilder sb = new StringBuilder(3);
        sb.append(c3);
        if (length >= 2) {
            sb.append(c4);
        }
        if (length >= 3) {
            sb.append(c5);
        }
        return sb.toString();
    }

    private static void handleEOD(EncoderContext encoderContext, CharSequence charSequence) {
        try {
            int length = charSequence.length();
            if (length == 0) {
                return;
            }
            boolean z2 = true;
            if (length == 1) {
                encoderContext.updateSymbolInfo();
                int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
                if (encoderContext.getRemainingCharacters() == 0 && dataCapacity <= 2) {
                    return;
                }
            }
            if (length > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            int i2 = length - 1;
            String strEncodeToCodewords = encodeToCodewords(charSequence, 0);
            if (!(!encoderContext.hasMoreCharacters()) || i2 > 2) {
                z2 = false;
            }
            if (i2 <= 2) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + i2);
                if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + strEncodeToCodewords.length());
                    z2 = false;
                }
            }
            if (z2) {
                encoderContext.resetSymbolInfo();
                encoderContext.pos -= i2;
            } else {
                encoderContext.writeCodewords(strEncodeToCodewords);
            }
        } finally {
            encoderContext.signalEncoderChange(0);
        }
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            encodeChar(encoderContext.getCurrentChar(), sb);
            encoderContext.pos++;
            if (sb.length() >= 4) {
                encoderContext.writeCodewords(encodeToCodewords(sb, 0));
                sb.delete(0, 4);
                if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            }
        }
        sb.append((char) 31);
        handleEOD(encoderContext, sb);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 4;
    }
}
