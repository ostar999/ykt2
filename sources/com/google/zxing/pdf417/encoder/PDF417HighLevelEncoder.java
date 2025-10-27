package com.google.zxing.pdf417.encoder;

import com.google.common.primitives.SignedBytes;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes4.dex */
final class PDF417HighLevelEncoder {
    private static final int BYTE_COMPACTION = 1;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED;
    private static final int NUMERIC_COMPACTION = 2;
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 38, 13, 9, 44, HttpTokens.COLON, 35, 45, 46, 36, 47, 43, 37, 42, Base64.padSymbol, 94, 0, 32, 0, 0, 0};
    private static final byte[] TEXT_PUNCTUATION_RAW = {HttpTokens.SEMI_COLON, 60, 62, SignedBytes.MAX_POWER_OF_TWO, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, HttpTokens.COLON, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, Utf8.REPLACEMENT_BYTE, 123, 125, 39, 0};
    private static final byte[] PUNCTUATION = new byte[128];
    private static final List<String> DEFAULT_ENCODING_NAMES = Arrays.asList("Cp437", "IBM437");

    static {
        byte[] bArr = new byte[128];
        MIXED = bArr;
        Arrays.fill(bArr, (byte) -1);
        byte b3 = 0;
        byte b4 = 0;
        while (true) {
            byte[] bArr2 = TEXT_MIXED_RAW;
            if (b4 >= bArr2.length) {
                break;
            }
            byte b5 = bArr2[b4];
            if (b5 > 0) {
                MIXED[b5] = b4;
            }
            b4 = (byte) (b4 + 1);
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (true) {
            byte[] bArr3 = TEXT_PUNCTUATION_RAW;
            if (b3 >= bArr3.length) {
                return;
            }
            byte b6 = bArr3[b3];
            if (b6 > 0) {
                PUNCTUATION[b6] = b3;
            }
            b3 = (byte) (b3 + 1);
        }
    }

    private PDF417HighLevelEncoder() {
    }

    private static int determineConsecutiveBinaryCount(CharSequence charSequence, byte[] bArr, int i2) throws WriterException {
        int i3;
        int i4;
        int length = charSequence.length();
        int i5 = i2;
        while (i5 < length) {
            char cCharAt = charSequence.charAt(i5);
            int i6 = 0;
            int i7 = 0;
            while (i7 < 13 && isDigit(cCharAt) && (i4 = i5 + (i7 = i7 + 1)) < length) {
                cCharAt = charSequence.charAt(i4);
            }
            if (i7 >= 13) {
                return i5 - i2;
            }
            while (i6 < 5 && isText(cCharAt) && (i3 = i5 + (i6 = i6 + 1)) < length) {
                cCharAt = charSequence.charAt(i3);
            }
            if (i6 >= 5) {
                return i5 - i2;
            }
            char cCharAt2 = charSequence.charAt(i5);
            if (bArr[i5] == 63 && cCharAt2 != '?') {
                throw new WriterException("Non-encodable character detected: " + cCharAt2 + " (Unicode: " + ((int) cCharAt2) + ')');
            }
            i5++;
        }
        return i5 - i2;
    }

    private static int determineConsecutiveDigitCount(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = 0;
        if (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            while (isDigit(cCharAt) && i2 < length) {
                i3++;
                i2++;
                if (i2 < length) {
                    cCharAt = charSequence.charAt(i2);
                }
            }
        }
        return i3;
    }

    private static int determineConsecutiveTextCount(CharSequence charSequence, int i2) {
        int length = charSequence.length();
        int i3 = i2;
        while (i3 < length) {
            char cCharAt = charSequence.charAt(i3);
            int i4 = 0;
            while (i4 < 13 && isDigit(cCharAt) && i3 < length) {
                i4++;
                i3++;
                if (i3 < length) {
                    cCharAt = charSequence.charAt(i3);
                }
            }
            if (i4 >= 13) {
                return (i3 - i2) - i4;
            }
            if (i4 <= 0) {
                if (!isText(charSequence.charAt(i3))) {
                    break;
                }
                i3++;
            }
        }
        return i3 - i2;
    }

    private static void encodeBinary(byte[] bArr, int i2, int i3, int i4, StringBuilder sb) {
        int i5;
        if (i3 == 1 && i4 == 0) {
            sb.append((char) 913);
        } else if (i3 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i3 >= 6) {
            char[] cArr = new char[5];
            i5 = i2;
            while ((i2 + i3) - i5 >= 6) {
                long j2 = 0;
                for (int i6 = 0; i6 < 6; i6++) {
                    j2 = (j2 << 8) + (bArr[i5 + i6] & 255);
                }
                for (int i7 = 0; i7 < 5; i7++) {
                    cArr[i7] = (char) (j2 % 900);
                    j2 /= 900;
                }
                for (int i8 = 4; i8 >= 0; i8--) {
                    sb.append(cArr[i8]);
                }
                i5 += 6;
            }
        } else {
            i5 = i2;
        }
        while (i5 < i2 + i3) {
            sb.append((char) (bArr[i5] & 255));
            i5++;
        }
    }

    public static String encodeHighLevel(String str, Compaction compaction, Charset charset) throws WriterException {
        CharacterSetECI characterSetECIByName;
        StringBuilder sb = new StringBuilder(str.length());
        if ((charset != null || !DEFAULT_ENCODING_NAMES.contains(charset.name())) && (characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name())) != null) {
            encodingECI(characterSetECIByName.getValue(), sb);
        }
        int length = str.length();
        if (compaction == Compaction.TEXT) {
            encodeText(str, 0, length, sb, 0);
        } else if (compaction == Compaction.BYTE) {
            byte[] bytes = toBytes(str, charset);
            encodeBinary(bytes, 0, bytes.length, 1, sb);
        } else if (compaction == Compaction.NUMERIC) {
            sb.append((char) 902);
            encodeNumeric(str, 0, length, sb);
        } else {
            byte[] bytes2 = null;
            int i2 = 0;
            int iEncodeText = 0;
            int i3 = 0;
            while (i2 < length) {
                int iDetermineConsecutiveDigitCount = determineConsecutiveDigitCount(str, i2);
                if (iDetermineConsecutiveDigitCount >= 13) {
                    sb.append((char) 902);
                    encodeNumeric(str, i2, iDetermineConsecutiveDigitCount, sb);
                    i2 += iDetermineConsecutiveDigitCount;
                    i3 = 2;
                    iEncodeText = 0;
                } else {
                    int iDetermineConsecutiveTextCount = determineConsecutiveTextCount(str, i2);
                    if (iDetermineConsecutiveTextCount >= 5 || iDetermineConsecutiveDigitCount == length) {
                        if (i3 != 0) {
                            sb.append((char) 900);
                            iEncodeText = 0;
                            i3 = 0;
                        }
                        iEncodeText = encodeText(str, i2, iDetermineConsecutiveTextCount, sb, iEncodeText);
                        i2 += iDetermineConsecutiveTextCount;
                    } else {
                        if (bytes2 == null) {
                            bytes2 = toBytes(str, charset);
                        }
                        int iDetermineConsecutiveBinaryCount = determineConsecutiveBinaryCount(str, bytes2, i2);
                        if (iDetermineConsecutiveBinaryCount == 0) {
                            iDetermineConsecutiveBinaryCount = 1;
                        }
                        if (iDetermineConsecutiveBinaryCount == 1 && i3 == 0) {
                            encodeBinary(bytes2, i2, 1, 0, sb);
                        } else {
                            encodeBinary(bytes2, i2, iDetermineConsecutiveBinaryCount, i3, sb);
                            iEncodeText = 0;
                            i3 = 1;
                        }
                        i2 += iDetermineConsecutiveBinaryCount;
                    }
                }
            }
        }
        return sb.toString();
    }

    private static void encodeNumeric(String str, int i2, int i3, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i3 / 3) + 1);
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(0L);
        int i4 = 0;
        while (i4 < i3 - 1) {
            sb2.setLength(0);
            int iMin = Math.min(44, i3 - i4);
            StringBuilder sb3 = new StringBuilder();
            sb3.append('1');
            int i5 = i2 + i4;
            sb3.append(str.substring(i5, i5 + iMin));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(bigIntegerValueOf).intValue());
                bigInteger = bigInteger.divide(bigIntegerValueOf);
            } while (!bigInteger.equals(bigIntegerValueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i4 += iMin;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x00f6 A[EDGE_INSN: B:76:0x00f6->B:55:0x00f6 BREAK  A[LOOP:0: B:3:0x000f->B:93:0x000f], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x000f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int encodeText(java.lang.CharSequence r16, int r17, int r18, java.lang.StringBuilder r19, int r20) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    private static void encodingECI(int i2, StringBuilder sb) throws WriterException {
        if (i2 >= 0 && i2 < 900) {
            sb.append((char) 927);
            sb.append((char) i2);
            return;
        }
        if (i2 < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i2 / 900) - 1));
            sb.append((char) (i2 % 900));
        } else if (i2 < 811800) {
            sb.append((char) 925);
            sb.append((char) (810900 - i2));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was " + i2);
        }
    }

    private static boolean isAlphaLower(char c3) {
        return c3 == ' ' || (c3 >= 'a' && c3 <= 'z');
    }

    private static boolean isAlphaUpper(char c3) {
        return c3 == ' ' || (c3 >= 'A' && c3 <= 'Z');
    }

    private static boolean isDigit(char c3) {
        return c3 >= '0' && c3 <= '9';
    }

    private static boolean isMixed(char c3) {
        return MIXED[c3] != -1;
    }

    private static boolean isPunctuation(char c3) {
        return PUNCTUATION[c3] != -1;
    }

    private static boolean isText(char c3) {
        return c3 == '\t' || c3 == '\n' || c3 == '\r' || (c3 >= ' ' && c3 <= '~');
    }

    private static byte[] toBytes(String str, Charset charset) throws WriterException {
        if (charset == null) {
            Iterator<String> it = DEFAULT_ENCODING_NAMES.iterator();
            while (it.hasNext()) {
                try {
                    charset = Charset.forName(it.next());
                } catch (UnsupportedCharsetException unused) {
                }
            }
            if (charset == null) {
                throw new WriterException("No support for any encoding: " + DEFAULT_ENCODING_NAMES);
            }
        }
        return str.getBytes(charset);
    }
}
