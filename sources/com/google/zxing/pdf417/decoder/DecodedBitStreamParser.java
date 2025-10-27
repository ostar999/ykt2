package com.google.zxing.pdf417.decoder;

import cn.hutool.core.text.CharPool;
import com.google.zxing.FormatException;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.math.BigInteger;
import java.util.Arrays;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;
    private static final char[] PUNCT_CHARS = {';', Typography.less, Typography.greater, '@', '[', '\\', '}', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', CharPool.DASHED, '.', Typography.dollar, '/', '\"', '|', '*', '(', ')', '?', '{', '}', CharPool.SINGLE_QUOTE};
    private static final char[] MIXED_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', CharPool.DASHED, '.', Typography.dollar, '/', '+', '%', '*', '=', '^'};

    /* renamed from: com.google.zxing.pdf417.decoder.DecodedBitStreamParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = iArr;
            try {
                iArr[Mode.ALPHA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.ALPHA_SHIFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT_SHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        bigIntegerArr[1] = bigIntegerValueOf;
        int i2 = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = EXP900;
            if (i2 >= bigIntegerArr2.length) {
                return;
            }
            bigIntegerArr2[i2] = bigIntegerArr2[i2 - 1].multiply(bigIntegerValueOf);
            i2++;
        }
    }

    private DecodedBitStreamParser() {
    }

    private static int byteCompaction(int i2, int[] iArr, int i3, StringBuilder sb) {
        int i4;
        int i5;
        int i6 = 922;
        int i7 = 923;
        int i8 = 928;
        long j2 = 900;
        if (i2 != 901) {
            if (i2 != 924) {
                return i3;
            }
            int i9 = i3;
            boolean z2 = false;
            int i10 = 0;
            long j3 = 0;
            while (i9 < iArr[0] && !z2) {
                int i11 = i9 + 1;
                int i12 = iArr[i9];
                if (i12 < 900) {
                    i10++;
                    j3 = (j3 * 900) + i12;
                    i9 = i11;
                } else {
                    if (i12 != 900 && i12 != 901 && i12 != 902 && i12 != 924 && i12 != i8) {
                        if (i12 != 923 && i12 != 922) {
                            i9 = i11;
                        }
                    }
                    i9 = i11 - 1;
                    z2 = true;
                }
                if (i10 % 5 == 0 && i10 > 0) {
                    char[] cArr = new char[6];
                    int i13 = 0;
                    for (int i14 = 6; i13 < i14; i14 = 6) {
                        cArr[5 - i13] = (char) (j3 & 255);
                        j3 >>= 8;
                        i13++;
                    }
                    sb.append(cArr);
                    i10 = 0;
                }
                i8 = 928;
            }
            return i9;
        }
        char[] cArr2 = new char[6];
        int[] iArr2 = new int[6];
        int i15 = 0;
        int i16 = i3 + 1;
        int i17 = iArr[i3];
        long j4 = 0;
        boolean z3 = false;
        while (true) {
            i4 = iArr[0];
            if (i16 >= i4 || z3) {
                break;
            }
            int i18 = i15 + 1;
            iArr2[i15] = i17;
            j4 = (j4 * j2) + i17;
            int i19 = i16 + 1;
            int i20 = iArr[i16];
            if (i20 == 900 || i20 == 901 || i20 == 902 || i20 == 924 || i20 == 928 || i20 == i7 || i20 == i6) {
                i16 = i19 - 1;
                i17 = i20;
                i15 = i18;
                i6 = 922;
                i7 = 923;
                j2 = 900;
                z3 = true;
            } else {
                if (i18 % 5 != 0 || i18 <= 0) {
                    i16 = i19;
                    i17 = i20;
                    i15 = i18;
                } else {
                    for (int i21 = 0; i21 < 6; i21++) {
                        cArr2[5 - i21] = (char) (j4 % 256);
                        j4 >>= 8;
                    }
                    sb.append(cArr2);
                    i16 = i19;
                    i17 = i20;
                    i15 = 0;
                }
                i6 = 922;
                i7 = 923;
                j2 = 900;
            }
        }
        if (i16 != i4 || i17 >= 900) {
            i5 = i15;
        } else {
            i5 = i15 + 1;
            iArr2[i15] = i17;
        }
        for (int i22 = 0; i22 < i5; i22++) {
            sb.append((char) iArr2[i22]);
        }
        return i16;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult decode(int[] r5, java.lang.String r6) throws com.google.zxing.FormatException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            int r1 = r5.length
            r2 = 2
            int r1 = r1 * r2
            r0.<init>(r1)
            r1 = 1
            r1 = r5[r1]
            com.google.zxing.pdf417.PDF417ResultMetadata r3 = new com.google.zxing.pdf417.PDF417ResultMetadata
            r3.<init>()
        L10:
            r4 = 0
            r4 = r5[r4]
            if (r2 >= r4) goto L4f
            r4 = 913(0x391, float:1.28E-42)
            if (r1 == r4) goto L3e
            r4 = 928(0x3a0, float:1.3E-42)
            if (r1 == r4) goto L39
            switch(r1) {
                case 900: goto L34;
                case 901: goto L3e;
                case 902: goto L2f;
                default: goto L20;
            }
        L20:
            switch(r1) {
                case 922: goto L2a;
                case 923: goto L2a;
                case 924: goto L3e;
                default: goto L23;
            }
        L23:
            int r2 = r2 + (-1)
            int r1 = textCompaction(r5, r2, r0)
            goto L42
        L2a:
            com.google.zxing.FormatException r5 = com.google.zxing.FormatException.getFormatInstance()
            throw r5
        L2f:
            int r1 = numericCompaction(r5, r2, r0)
            goto L42
        L34:
            int r1 = textCompaction(r5, r2, r0)
            goto L42
        L39:
            int r1 = decodeMacroBlock(r5, r2, r3)
            goto L42
        L3e:
            int r1 = byteCompaction(r1, r5, r2, r0)
        L42:
            int r2 = r5.length
            if (r1 >= r2) goto L4a
            int r2 = r1 + 1
            r1 = r5[r1]
            goto L10
        L4a:
            com.google.zxing.FormatException r5 = com.google.zxing.FormatException.getFormatInstance()
            throw r5
        L4f:
            int r5 = r0.length()
            if (r5 == 0) goto L63
            com.google.zxing.common.DecoderResult r5 = new com.google.zxing.common.DecoderResult
            java.lang.String r0 = r0.toString()
            r1 = 0
            r5.<init>(r1, r0, r1, r6)
            r5.setOther(r3)
            return r5
        L63:
            com.google.zxing.FormatException r5 = com.google.zxing.FormatException.getFormatInstance()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decode(int[], java.lang.String):com.google.zxing.common.DecoderResult");
    }

    private static String decodeBase900toBase10(int[] iArr, int i2) throws FormatException {
        BigInteger bigIntegerAdd = BigInteger.ZERO;
        for (int i3 = 0; i3 < i2; i3++) {
            bigIntegerAdd = bigIntegerAdd.add(EXP900[(i2 - i3) - 1].multiply(BigInteger.valueOf(iArr[i3])));
        }
        String string = bigIntegerAdd.toString();
        if (string.charAt(0) == '1') {
            return string.substring(1);
        }
        throw FormatException.getFormatInstance();
    }

    private static int decodeMacroBlock(int[] iArr, int i2, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        if (i2 + 2 > iArr[0]) {
            throw FormatException.getFormatInstance();
        }
        int[] iArr2 = new int[2];
        int i3 = 0;
        while (i3 < 2) {
            iArr2[i3] = iArr[i2];
            i3++;
            i2++;
        }
        pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(iArr2, 2)));
        StringBuilder sb = new StringBuilder();
        int iTextCompaction = textCompaction(iArr, i2, sb);
        pDF417ResultMetadata.setFileId(sb.toString());
        int i4 = iArr[iTextCompaction];
        if (i4 != 923) {
            if (i4 != 922) {
                return iTextCompaction;
            }
            pDF417ResultMetadata.setLastSegment(true);
            return iTextCompaction + 1;
        }
        int i5 = iTextCompaction + 1;
        int[] iArr3 = new int[iArr[0] - i5];
        boolean z2 = false;
        int i6 = 0;
        while (i5 < iArr[0] && !z2) {
            int i7 = i5 + 1;
            int i8 = iArr[i5];
            if (i8 < 900) {
                iArr3[i6] = i8;
                i5 = i7;
                i6++;
            } else {
                if (i8 != 922) {
                    throw FormatException.getFormatInstance();
                }
                pDF417ResultMetadata.setLastSegment(true);
                z2 = true;
                i5 = i7 + 1;
            }
        }
        pDF417ResultMetadata.setOptionalData(Arrays.copyOf(iArr3, i6));
        return i5;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void decodeTextCompaction(int[] iArr, int[] iArr2, int i2, StringBuilder sb) {
        Mode mode;
        int i3;
        Mode mode2 = Mode.ALPHA;
        Mode mode3 = mode2;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = iArr[i4];
            char c3 = ' ';
            switch (AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[mode2.ordinal()]) {
                case 1:
                    if (i5 < 26) {
                        i3 = i5 + 65;
                        c3 = (char) i3;
                        break;
                    } else if (i5 != 26) {
                        if (i5 == 27) {
                            mode2 = Mode.LOWER;
                        } else if (i5 == 28) {
                            mode2 = Mode.MIXED;
                        } else if (i5 == 29) {
                            mode = Mode.PUNCT_SHIFT;
                            c3 = 0;
                            Mode mode4 = mode;
                            mode3 = mode2;
                            mode2 = mode4;
                            break;
                        } else if (i5 == 913) {
                            sb.append((char) iArr2[i4]);
                        } else if (i5 == 900) {
                            mode2 = Mode.ALPHA;
                        }
                        c3 = 0;
                        break;
                    }
                    break;
                case 2:
                    if (i5 < 26) {
                        i3 = i5 + 97;
                        c3 = (char) i3;
                        break;
                    } else if (i5 != 26) {
                        if (i5 != 27) {
                            if (i5 == 28) {
                                mode2 = Mode.MIXED;
                            } else if (i5 == 29) {
                                mode = Mode.PUNCT_SHIFT;
                            } else if (i5 == 913) {
                                sb.append((char) iArr2[i4]);
                            } else if (i5 == 900) {
                                mode2 = Mode.ALPHA;
                            }
                            c3 = 0;
                            break;
                        } else {
                            mode = Mode.ALPHA_SHIFT;
                        }
                        c3 = 0;
                        Mode mode42 = mode;
                        mode3 = mode2;
                        mode2 = mode42;
                        break;
                    }
                    break;
                case 3:
                    if (i5 < 25) {
                        c3 = MIXED_CHARS[i5];
                        break;
                    } else {
                        if (i5 == 25) {
                            mode2 = Mode.PUNCT;
                        } else if (i5 != 26) {
                            if (i5 == 27) {
                                mode2 = Mode.LOWER;
                            } else if (i5 == 28) {
                                mode2 = Mode.ALPHA;
                            } else if (i5 == 29) {
                                mode = Mode.PUNCT_SHIFT;
                                c3 = 0;
                                Mode mode422 = mode;
                                mode3 = mode2;
                                mode2 = mode422;
                                break;
                            } else if (i5 == 913) {
                                sb.append((char) iArr2[i4]);
                            } else if (i5 == 900) {
                                mode2 = Mode.ALPHA;
                            }
                        }
                        c3 = 0;
                        break;
                    }
                    break;
                case 4:
                    if (i5 < 29) {
                        c3 = PUNCT_CHARS[i5];
                        break;
                    } else {
                        if (i5 == 29) {
                            mode2 = Mode.ALPHA;
                        } else if (i5 == 913) {
                            sb.append((char) iArr2[i4]);
                        } else if (i5 == 900) {
                            mode2 = Mode.ALPHA;
                        }
                        c3 = 0;
                        break;
                    }
                case 5:
                    if (i5 < 26) {
                        c3 = (char) (i5 + 65);
                    } else if (i5 != 26) {
                        if (i5 == 900) {
                            mode2 = Mode.ALPHA;
                            c3 = 0;
                            break;
                        }
                        c3 = 0;
                    }
                    mode2 = mode3;
                    break;
                case 6:
                    if (i5 < 29) {
                        c3 = PUNCT_CHARS[i5];
                        mode2 = mode3;
                        break;
                    } else {
                        if (i5 == 29) {
                            mode2 = Mode.ALPHA;
                        } else {
                            if (i5 == 913) {
                                sb.append((char) iArr2[i4]);
                            } else if (i5 == 900) {
                                mode2 = Mode.ALPHA;
                            }
                            c3 = 0;
                            mode2 = mode3;
                        }
                        c3 = 0;
                        break;
                    }
                default:
                    c3 = 0;
                    break;
            }
            if (c3 != 0) {
                sb.append(c3);
            }
        }
    }

    private static int numericCompaction(int[] iArr, int i2, StringBuilder sb) throws FormatException {
        int[] iArr2 = new int[15];
        boolean z2 = false;
        int i3 = 0;
        while (true) {
            int i4 = iArr[0];
            if (i2 >= i4 || z2) {
                break;
            }
            int i5 = i2 + 1;
            int i6 = iArr[i2];
            if (i5 == i4) {
                z2 = true;
            }
            if (i6 < 900) {
                iArr2[i3] = i6;
                i3++;
            } else if (i6 == 900 || i6 == 901 || i6 == 924 || i6 == 928 || i6 == 923 || i6 == 922) {
                i5--;
                z2 = true;
            }
            if (i3 % 15 == 0 || i6 == 902 || z2) {
                sb.append(decodeBase900toBase10(iArr2, i3));
                i3 = 0;
            }
            i2 = i5;
        }
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0040 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int textCompaction(int[] r9, int r10, java.lang.StringBuilder r11) {
        /*
            r0 = 0
            r1 = r9[r0]
            int r2 = r1 - r10
            r3 = 1
            int r2 = r2 << r3
            int[] r2 = new int[r2]
            int r1 = r1 - r10
            int r1 = r1 << r3
            int[] r1 = new int[r1]
            r4 = r0
            r5 = r4
        Lf:
            r6 = r9[r0]
            if (r10 >= r6) goto L4f
            if (r4 != 0) goto L4f
            int r6 = r10 + 1
            r10 = r9[r10]
            r7 = 900(0x384, float:1.261E-42)
            if (r10 >= r7) goto L2a
            int r7 = r10 / 30
            r2[r5] = r7
            int r7 = r5 + 1
            int r10 = r10 % 30
            r2[r7] = r10
            int r5 = r5 + 2
            goto L38
        L2a:
            r8 = 913(0x391, float:1.28E-42)
            if (r10 == r8) goto L44
            r8 = 928(0x3a0, float:1.3E-42)
            if (r10 == r8) goto L40
            switch(r10) {
                case 900: goto L3a;
                case 901: goto L40;
                case 902: goto L40;
                default: goto L35;
            }
        L35:
            switch(r10) {
                case 922: goto L40;
                case 923: goto L40;
                case 924: goto L40;
                default: goto L38;
            }
        L38:
            r10 = r6
            goto Lf
        L3a:
            int r10 = r5 + 1
            r2[r5] = r7
            r5 = r10
            goto L38
        L40:
            int r6 = r6 + (-1)
            r4 = r3
            goto L38
        L44:
            r2[r5] = r8
            int r10 = r6 + 1
            r6 = r9[r6]
            r1[r5] = r6
            int r5 = r5 + 1
            goto Lf
        L4f:
            decodeTextCompaction(r2, r1, r5, r11)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.textCompaction(int[], int, java.lang.StringBuilder):int");
    }
}
