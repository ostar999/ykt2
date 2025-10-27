package cn.hutool.core.codec;

import cn.hutool.core.text.CharSequenceUtil;
import java.util.Arrays;

/* loaded from: classes.dex */
public class Base58Codec implements Encoder<byte[], String>, Decoder<CharSequence, byte[]> {
    public static Base58Codec INSTANCE = new Base58Codec();

    public static class Base58Decoder implements Decoder<CharSequence, byte[]> {
        public static Base58Decoder DECODER = new Base58Decoder("123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz");
        private final byte[] lookupTable;

        public Base58Decoder(String str) {
            byte[] bArr = new byte[123];
            Arrays.fill(bArr, (byte) -1);
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                bArr[str.charAt(i2)] = (byte) i2;
            }
            this.lookupTable = bArr;
        }

        @Override // cn.hutool.core.codec.Decoder
        public byte[] decode(CharSequence charSequence) {
            int i2 = 0;
            if (charSequence.length() == 0) {
                return new byte[0];
            }
            int length = charSequence.length();
            byte[] bArr = new byte[length];
            for (int i3 = 0; i3 < charSequence.length(); i3++) {
                char cCharAt = charSequence.charAt(i3);
                byte b3 = cCharAt < 128 ? this.lookupTable[cCharAt] : (byte) -1;
                if (b3 < 0) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("Invalid char '{}' at [{}]", Character.valueOf(cCharAt), Integer.valueOf(i3)));
                }
                bArr[i3] = b3;
            }
            while (i2 < length && bArr[i2] == 0) {
                i2++;
            }
            int length2 = charSequence.length();
            byte[] bArr2 = new byte[length2];
            int i4 = length2;
            int i5 = i2;
            while (i5 < length) {
                i4--;
                bArr2[i4] = Base58Codec.divmod(bArr, i5, 58, 256);
                if (bArr[i5] == 0) {
                    i5++;
                }
            }
            while (i4 < length2 && bArr2[i4] == 0) {
                i4++;
            }
            return Arrays.copyOfRange(bArr2, i4 - i2, length2);
        }
    }

    public static class Base58Encoder implements Encoder<byte[], String> {
        private static final String DEFAULT_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
        public static final Base58Encoder ENCODER = new Base58Encoder(DEFAULT_ALPHABET.toCharArray());
        private final char[] alphabet;
        private final char alphabetZero;

        public Base58Encoder(char[] cArr) {
            this.alphabet = cArr;
            this.alphabetZero = cArr[0];
        }

        @Override // cn.hutool.core.codec.Encoder
        public String encode(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            if (bArr.length == 0) {
                return "";
            }
            int i2 = 0;
            while (i2 < bArr.length && bArr[i2] == 0) {
                i2++;
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            int length = bArrCopyOf.length * 2;
            char[] cArr = new char[length];
            int i3 = i2;
            int i4 = length;
            while (i3 < bArrCopyOf.length) {
                i4--;
                cArr[i4] = this.alphabet[Base58Codec.divmod(bArrCopyOf, i3, 256, 58)];
                if (bArrCopyOf[i3] == 0) {
                    i3++;
                }
            }
            while (i4 < length && cArr[i4] == this.alphabetZero) {
                i4++;
            }
            while (true) {
                i2--;
                if (i2 < 0) {
                    return new String(cArr, i4, length - i4);
                }
                i4--;
                cArr[i4] = this.alphabetZero;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte divmod(byte[] bArr, int i2, int i3, int i4) {
        int i5 = 0;
        while (i2 < bArr.length) {
            int i6 = (i5 * i3) + (bArr[i2] & 255);
            bArr[i2] = (byte) (i6 / i4);
            i5 = i6 % i4;
            i2++;
        }
        return (byte) i5;
    }

    @Override // cn.hutool.core.codec.Decoder
    public byte[] decode(CharSequence charSequence) throws IllegalArgumentException {
        return Base58Decoder.DECODER.decode(charSequence);
    }

    @Override // cn.hutool.core.codec.Encoder
    public String encode(byte[] bArr) {
        return Base58Encoder.ENCODER.encode(bArr);
    }
}
