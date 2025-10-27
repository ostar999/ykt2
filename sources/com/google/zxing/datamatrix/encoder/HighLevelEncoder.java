package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.yikaobang.yixue.R2;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = 254;
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = 238;
    static final char LATCH_TO_BASE256 = 231;
    static final char LATCH_TO_C40 = 230;
    static final char LATCH_TO_EDIFACT = 240;
    static final char LATCH_TO_TEXT = 239;
    private static final char MACRO_05 = 236;
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = 237;
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = 129;
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = 235;
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = 254;

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int i2) {
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

    public static String encodeHighLevel(String str) {
        return encodeHighLevel(str, SymbolShapeHint.FORCE_NONE, null, null);
    }

    private static int findMinimums(float[] fArr, int[] iArr, int i2, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        for (int i3 = 0; i3 < 6; i3++) {
            int iCeil = (int) Math.ceil(fArr[i3]);
            iArr[i3] = iCeil;
            if (i2 > iCeil) {
                Arrays.fill(bArr, (byte) 0);
                i2 = iCeil;
            }
            if (i2 == iCeil) {
                bArr[i3] = (byte) (bArr[i3] + 1);
            }
        }
        return i2;
    }

    private static int getMinimumCount(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            i2 += bArr[i3];
        }
        return i2;
    }

    public static void illegalCharacter(char c3) {
        String hexString = Integer.toHexString(c3);
        throw new IllegalArgumentException("Illegal character: " + c3 + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }

    public static boolean isDigit(char c3) {
        return c3 >= '0' && c3 <= '9';
    }

    public static boolean isExtendedASCII(char c3) {
        return c3 >= 128 && c3 <= 255;
    }

    private static boolean isNativeC40(char c3) {
        return c3 == ' ' || (c3 >= '0' && c3 <= '9') || (c3 >= 'A' && c3 <= 'Z');
    }

    private static boolean isNativeEDIFACT(char c3) {
        return c3 >= ' ' && c3 <= '^';
    }

    private static boolean isNativeText(char c3) {
        return c3 == ' ' || (c3 >= '0' && c3 <= '9') || (c3 >= 'a' && c3 <= 'z');
    }

    private static boolean isNativeX12(char c3) {
        return isX12TermSep(c3) || c3 == ' ' || (c3 >= '0' && c3 <= '9') || (c3 >= 'A' && c3 <= 'Z');
    }

    private static boolean isSpecialB256(char c3) {
        return false;
    }

    private static boolean isX12TermSep(char c3) {
        return c3 == '\r' || c3 == '*' || c3 == '>';
    }

    public static int lookAheadTest(CharSequence charSequence, int i2, int i3) {
        float[] fArr;
        char c3;
        if (i2 >= charSequence.length()) {
            return i3;
        }
        int i4 = 6;
        if (i3 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            fArr = new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr[i3] = 0.0f;
        }
        int i5 = 0;
        while (true) {
            int i6 = i2 + i5;
            if (i6 == charSequence.length()) {
                byte[] bArr = new byte[i4];
                int[] iArr = new int[i4];
                int iFindMinimums = findMinimums(fArr, iArr, Integer.MAX_VALUE, bArr);
                int minimumCount = getMinimumCount(bArr);
                if (iArr[0] == iFindMinimums) {
                    return 0;
                }
                if (minimumCount == 1 && bArr[5] > 0) {
                    return 5;
                }
                if (minimumCount == 1 && bArr[4] > 0) {
                    return 4;
                }
                if (minimumCount != 1 || bArr[2] <= 0) {
                    return (minimumCount != 1 || bArr[3] <= 0) ? 1 : 3;
                }
                return 2;
            }
            char cCharAt = charSequence.charAt(i6);
            i5++;
            if (isDigit(cCharAt)) {
                fArr[0] = (float) (fArr[0] + 0.5d);
            } else if (isExtendedASCII(cCharAt)) {
                float fCeil = (int) Math.ceil(fArr[0]);
                fArr[0] = fCeil;
                fArr[0] = fCeil + 2.0f;
            } else {
                float fCeil2 = (int) Math.ceil(fArr[0]);
                fArr[0] = fCeil2;
                fArr[0] = fCeil2 + 1.0f;
            }
            if (isNativeC40(cCharAt)) {
                fArr[1] = fArr[1] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[1] = fArr[1] + 2.6666667f;
            } else {
                fArr[1] = fArr[1] + 1.3333334f;
            }
            if (isNativeText(cCharAt)) {
                fArr[2] = fArr[2] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[2] = fArr[2] + 2.6666667f;
            } else {
                fArr[2] = fArr[2] + 1.3333334f;
            }
            if (isNativeX12(cCharAt)) {
                fArr[3] = fArr[3] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[3] = fArr[3] + 4.3333335f;
            } else {
                fArr[3] = fArr[3] + 3.3333333f;
            }
            if (isNativeEDIFACT(cCharAt)) {
                fArr[4] = fArr[4] + 0.75f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[4] = fArr[4] + 4.25f;
            } else {
                fArr[4] = fArr[4] + 3.25f;
            }
            if (isSpecialB256(cCharAt)) {
                c3 = 5;
                fArr[5] = fArr[5] + 4.0f;
            } else {
                c3 = 5;
                fArr[5] = fArr[5] + 1.0f;
            }
            if (i5 >= 4) {
                int[] iArr2 = new int[i4];
                byte[] bArr2 = new byte[i4];
                findMinimums(fArr, iArr2, Integer.MAX_VALUE, bArr2);
                int minimumCount2 = getMinimumCount(bArr2);
                int i7 = iArr2[0];
                int i8 = iArr2[c3];
                if (i7 < i8 && i7 < iArr2[1] && i7 < iArr2[2] && i7 < iArr2[3] && i7 < iArr2[4]) {
                    return 0;
                }
                if (i8 < i7) {
                    return 5;
                }
                byte b3 = bArr2[1];
                byte b4 = bArr2[2];
                byte b5 = bArr2[3];
                byte b6 = bArr2[4];
                if (b3 + b4 + b5 + b6 == 0) {
                    return 5;
                }
                if (minimumCount2 == 1 && b6 > 0) {
                    return 4;
                }
                if (minimumCount2 == 1 && b4 > 0) {
                    return 2;
                }
                if (minimumCount2 == 1 && b5 > 0) {
                    return 3;
                }
                int i9 = iArr2[1];
                if (i9 + 1 < i7 && i9 + 1 < i8 && i9 + 1 < iArr2[4] && i9 + 1 < iArr2[2]) {
                    int i10 = iArr2[3];
                    if (i9 < i10) {
                        return 1;
                    }
                    if (i9 == i10) {
                        for (int i11 = i2 + i5 + 1; i11 < charSequence.length(); i11++) {
                            char cCharAt2 = charSequence.charAt(i11);
                            if (isX12TermSep(cCharAt2)) {
                                return 3;
                            }
                            if (!isNativeX12(cCharAt2)) {
                                break;
                            }
                        }
                        return 1;
                    }
                }
            }
            i4 = 6;
        }
    }

    private static char randomize253State(char c3, int i2) {
        int i3 = c3 + ((i2 * 149) % R2.attr.actionModeSplitBackground) + 1;
        if (i3 > 254) {
            i3 -= 254;
        }
        return (char) i3;
    }

    public static String encodeHighLevel(String str, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        int newEncoding = 0;
        Encoder[] encoderArr = {new ASCIIEncoder(), new C40Encoder(), new TextEncoder(), new X12Encoder(), new EdifactEncoder(), new Base256Encoder()};
        EncoderContext encoderContext = new EncoderContext(str);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (str.startsWith(MACRO_05_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_05);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
        } else if (str.startsWith(MACRO_06_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_06);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
        }
        while (encoderContext.hasMoreCharacters()) {
            encoderArr[newEncoding].encode(encoderContext);
            if (encoderContext.getNewEncoding() >= 0) {
                newEncoding = encoderContext.getNewEncoding();
                encoderContext.resetEncoderSignal();
            }
        }
        int codewordCount = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity();
        if (codewordCount < dataCapacity && newEncoding != 0 && newEncoding != 5) {
            encoderContext.writeCodeword((char) 254);
        }
        StringBuilder codewords = encoderContext.getCodewords();
        if (codewords.length() < dataCapacity) {
            codewords.append(PAD);
        }
        while (codewords.length() < dataCapacity) {
            codewords.append(randomize253State(PAD, codewords.length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }
}
