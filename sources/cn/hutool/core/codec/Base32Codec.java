package cn.hutool.core.codec;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Base32Codec implements Encoder<byte[], String>, Decoder<CharSequence, byte[]> {
    public static Base32Codec INSTANCE = new Base32Codec();

    public static class Base32Decoder implements Decoder<CharSequence, byte[]> {
        private static final char BASE_CHAR = '0';
        public static final Base32Decoder DECODER = new Base32Decoder("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567");
        public static final Base32Decoder HEX_DECODER = new Base32Decoder("0123456789ABCDEFGHIJKLMNOPQRSTUV");
        private final byte[] lookupTable;

        public Base32Decoder(String str) {
            byte[] bArr = new byte[128];
            this.lookupTable = bArr;
            Arrays.fill(bArr, (byte) -1);
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                byte[] bArr2 = this.lookupTable;
                byte b3 = (byte) i2;
                bArr2[cCharAt - '0'] = b3;
                if (cCharAt >= 'A' && cCharAt <= 'Z') {
                    bArr2[Character.toLowerCase(cCharAt) - '0'] = b3;
                }
            }
        }

        @Override // cn.hutool.core.codec.Decoder
        public byte[] decode(CharSequence charSequence) {
            byte b3;
            String string = charSequence.toString();
            int iIndexOf = ((string.endsWith("=") ? string.indexOf("=") : string.length()) * 5) / 8;
            byte[] bArr = new byte[iIndexOf];
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < string.length(); i4++) {
                int iCharAt = string.charAt(i4) - '0';
                if (iCharAt >= 0) {
                    byte[] bArr2 = this.lookupTable;
                    if (iCharAt < bArr2.length && (b3 = bArr2[iCharAt]) >= 0) {
                        if (i2 <= 3) {
                            i2 = (i2 + 5) % 8;
                            if (i2 == 0) {
                                bArr[i3] = (byte) (b3 | bArr[i3]);
                                i3++;
                                if (i3 >= iIndexOf) {
                                    break;
                                }
                            } else {
                                bArr[i3] = (byte) ((b3 << (8 - i2)) | bArr[i3]);
                            }
                        } else {
                            i2 = (i2 + 5) % 8;
                            bArr[i3] = (byte) (bArr[i3] | (b3 >>> i2));
                            i3++;
                            if (i3 >= iIndexOf) {
                                break;
                            }
                            bArr[i3] = (byte) ((b3 << (8 - i2)) | bArr[i3]);
                        }
                    }
                }
            }
            return bArr;
        }
    }

    public static class Base32Encoder implements Encoder<byte[], String> {
        private final char[] alphabet;
        private final Character pad;
        private static final Character DEFAULT_PAD = '=';
        private static final int[] BASE32_FILL = {-1, 4, 1, 6, 3};
        private static final String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
        public static final Base32Encoder ENCODER = new Base32Encoder(DEFAULT_ALPHABET, '=');
        private static final String HEX_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUV";
        public static final Base32Encoder HEX_ENCODER = new Base32Encoder(HEX_ALPHABET, '=');

        public Base32Encoder(String str, Character ch) {
            this.alphabet = str.toCharArray();
            this.pad = ch;
        }

        @Override // cn.hutool.core.codec.Encoder
        public String encode(byte[] bArr) {
            int i2;
            int i3;
            int length = (bArr.length * 8) / 5;
            if (length != 0) {
                length = length + 1 + BASE32_FILL[(bArr.length * 8) % 5];
            }
            StringBuilder sb = new StringBuilder(length);
            int i4 = 0;
            int i5 = 0;
            while (i4 < bArr.length) {
                int i6 = bArr[i4];
                if (i6 < 0) {
                    i6 += 256;
                }
                if (i5 > 3) {
                    i4++;
                    if (i4 < bArr.length) {
                        i3 = bArr[i4];
                        if (i3 < 0) {
                            i3 += 256;
                        }
                    } else {
                        i3 = 0;
                    }
                    int i7 = i6 & (255 >> i5);
                    i5 = (i5 + 5) % 8;
                    i2 = (i7 << i5) | (i3 >> (8 - i5));
                } else {
                    int i8 = i5 + 5;
                    i2 = (i6 >> (8 - i8)) & 31;
                    i5 = i8 % 8;
                    if (i5 == 0) {
                        i4++;
                    }
                }
                sb.append(this.alphabet[i2]);
            }
            if (this.pad != null) {
                while (sb.length() < length) {
                    sb.append(this.pad.charValue());
                }
            }
            return sb.toString();
        }
    }

    @Override // cn.hutool.core.codec.Decoder
    public byte[] decode(CharSequence charSequence) {
        return decode(charSequence, false);
    }

    @Override // cn.hutool.core.codec.Encoder
    public String encode(byte[] bArr) {
        return encode(bArr, false);
    }

    public byte[] decode(CharSequence charSequence, boolean z2) {
        return (z2 ? Base32Decoder.HEX_DECODER : Base32Decoder.DECODER).decode(charSequence);
    }

    public String encode(byte[] bArr, boolean z2) {
        return (z2 ? Base32Encoder.HEX_ENCODER : Base32Encoder.ENCODER).encode(bArr);
    }
}
