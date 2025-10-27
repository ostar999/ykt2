package cn.hutool.core.codec;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.text.CharSequenceUtil;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;

/* loaded from: classes.dex */
public class Base16Codec implements Encoder<byte[], char[]>, Decoder<CharSequence, byte[]> {
    public static final Base16Codec CODEC_LOWER = new Base16Codec(true);
    public static final Base16Codec CODEC_UPPER = new Base16Codec(false);
    private final char[] alphabets;

    public Base16Codec(boolean z2) {
        this.alphabets = (z2 ? "0123456789abcdef" : BinTools.hex).toCharArray();
    }

    private static int toDigit(char c3, int i2) {
        int iDigit = Character.digit(c3, 16);
        if (iDigit >= 0) {
            return iDigit;
        }
        throw new UtilException("Illegal hexadecimal character {} at index {}", Character.valueOf(c3), Integer.valueOf(i2));
    }

    public void appendHex(StringBuilder sb, byte b3) {
        sb.append(this.alphabets[(b3 & 240) >>> 4]);
        sb.append(this.alphabets[b3 & 15]);
    }

    public String toUnicodeHex(char c3) {
        return "\\u" + this.alphabets[(c3 >> '\f') & 15] + this.alphabets[(c3 >> '\b') & 15] + this.alphabets[(c3 >> 4) & 15] + this.alphabets[c3 & 15];
    }

    @Override // cn.hutool.core.codec.Decoder
    public byte[] decode(CharSequence charSequence) {
        if (CharSequenceUtil.isEmpty(charSequence)) {
            return null;
        }
        String strCleanBlank = CharSequenceUtil.cleanBlank(charSequence);
        int length = strCleanBlank.length();
        if ((length & 1) != 0) {
            strCleanBlank = "0" + ((Object) strCleanBlank);
            length = strCleanBlank.length();
        }
        byte[] bArr = new byte[length >> 1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int digit = toDigit(strCleanBlank.charAt(i2), i2) << 4;
            int i4 = i2 + 1;
            int digit2 = digit | toDigit(strCleanBlank.charAt(i4), i4);
            i2 = i4 + 1;
            bArr[i3] = (byte) (digit2 & 255);
            i3++;
        }
        return bArr;
    }

    @Override // cn.hutool.core.codec.Encoder
    public char[] encode(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = this.alphabets;
            cArr[i2] = cArr2[(b3 & 240) >>> 4];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b3 & 15];
        }
        return cArr;
    }
}
