package com.google.zxing.datamatrix.encoder;

/* loaded from: classes4.dex */
final class TextEncoder extends C40Encoder {
    @Override // com.google.zxing.datamatrix.encoder.C40Encoder
    public int encodeChar(char c3, StringBuilder sb) {
        if (c3 == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c3 >= '0' && c3 <= '9') {
            sb.append((char) ((c3 - '0') + 4));
            return 1;
        }
        if (c3 >= 'a' && c3 <= 'z') {
            sb.append((char) ((c3 - 'a') + 14));
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
        if (c3 == '`') {
            sb.append((char) 2);
            sb.append((char) (c3 - '`'));
            return 2;
        }
        if (c3 >= 'A' && c3 <= 'Z') {
            sb.append((char) 2);
            sb.append((char) ((c3 - 'A') + 1));
            return 2;
        }
        if (c3 >= '{' && c3 <= 127) {
            sb.append((char) 2);
            sb.append((char) ((c3 - '{') + 27));
            return 2;
        }
        if (c3 >= 128) {
            sb.append("\u0001\u001e");
            return encodeChar((char) (c3 - 128), sb) + 2;
        }
        HighLevelEncoder.illegalCharacter(c3);
        return -1;
    }

    @Override // com.google.zxing.datamatrix.encoder.C40Encoder, com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 2;
    }
}
