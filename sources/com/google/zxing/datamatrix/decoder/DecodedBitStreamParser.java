package com.google.zxing.datamatrix.decoder;

import cn.hutool.core.text.CharPool;
import com.google.common.base.Ascii;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.DecoderResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = {'!', '\"', '#', Typography.dollar, '%', '&', CharPool.SINGLE_QUOTE, '(', ')', '*', '+', ',', CharPool.DASHED, '.', '/', ':', ';', Typography.less, '=', Typography.greater, '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT3_SET_CHARS = {'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', Ascii.MAX};

    /* renamed from: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = iArr;
            try {
                iArr[Mode.C40_ENCODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.TEXT_ENCODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.ANSIX12_ENCODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.EDIFACT_ENCODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.BASE256_ENCODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE
    }

    private DecodedBitStreamParser() {
    }

    public static DecoderResult decode(byte[] bArr) throws FormatException {
        BitSource bitSource = new BitSource(bArr);
        StringBuilder sb = new StringBuilder(100);
        StringBuilder sb2 = new StringBuilder(0);
        ArrayList arrayList = new ArrayList(1);
        Mode modeDecodeAsciiSegment = Mode.ASCII_ENCODE;
        do {
            Mode mode = Mode.ASCII_ENCODE;
            if (modeDecodeAsciiSegment == mode) {
                modeDecodeAsciiSegment = decodeAsciiSegment(bitSource, sb, sb2);
            } else {
                int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[modeDecodeAsciiSegment.ordinal()];
                if (i2 == 1) {
                    decodeC40Segment(bitSource, sb);
                } else if (i2 == 2) {
                    decodeTextSegment(bitSource, sb);
                } else if (i2 == 3) {
                    decodeAnsiX12Segment(bitSource, sb);
                } else if (i2 == 4) {
                    decodeEdifactSegment(bitSource, sb);
                } else {
                    if (i2 != 5) {
                        throw FormatException.getFormatInstance();
                    }
                    decodeBase256Segment(bitSource, sb, arrayList);
                }
                modeDecodeAsciiSegment = mode;
            }
            if (modeDecodeAsciiSegment == Mode.PAD_ENCODE) {
                break;
            }
        } while (bitSource.available() > 0);
        if (sb2.length() > 0) {
            sb.append((CharSequence) sb2);
        }
        String string = sb.toString();
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        return new DecoderResult(bArr, string, arrayList, null);
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        int[] iArr = new int[3];
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                if (i3 == 0) {
                    sb.append('\r');
                } else if (i3 == 1) {
                    sb.append('*');
                } else if (i3 == 2) {
                    sb.append(Typography.greater);
                } else if (i3 == 3) {
                    sb.append(' ');
                } else if (i3 < 14) {
                    sb.append((char) (i3 + 44));
                } else {
                    if (i3 >= 40) {
                        throw FormatException.getFormatInstance();
                    }
                    sb.append((char) (i3 + 51));
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder sb, StringBuilder sb2) throws FormatException {
        boolean z2 = false;
        do {
            int bits = bitSource.readBits(8);
            if (bits == 0) {
                throw FormatException.getFormatInstance();
            }
            if (bits <= 128) {
                if (z2) {
                    bits += 128;
                }
                sb.append((char) (bits - 1));
                return Mode.ASCII_ENCODE;
            }
            if (bits == 129) {
                return Mode.PAD_ENCODE;
            }
            if (bits <= 229) {
                int i2 = bits - 130;
                if (i2 < 10) {
                    sb.append('0');
                }
                sb.append(i2);
            } else {
                if (bits == 230) {
                    return Mode.C40_ENCODE;
                }
                if (bits == 231) {
                    return Mode.BASE256_ENCODE;
                }
                if (bits == 232) {
                    sb.append((char) 29);
                } else if (bits != 233 && bits != 234) {
                    if (bits == 235) {
                        z2 = true;
                    } else if (bits == 236) {
                        sb.append("[)>\u001e05\u001d");
                        sb2.insert(0, "\u001e\u0004");
                    } else if (bits == 237) {
                        sb.append("[)>\u001e06\u001d");
                        sb2.insert(0, "\u001e\u0004");
                    } else {
                        if (bits == 238) {
                            return Mode.ANSIX12_ENCODE;
                        }
                        if (bits == 239) {
                            return Mode.TEXT_ENCODE;
                        }
                        if (bits == 240) {
                            return Mode.EDIFACT_ENCODE;
                        }
                        if (bits != 241 && bits >= 242 && (bits != 254 || bitSource.available() != 0)) {
                            throw FormatException.getFormatInstance();
                        }
                    }
                }
            }
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeBase256Segment(BitSource bitSource, StringBuilder sb, Collection<byte[]> collection) throws FormatException {
        int byteOffset = bitSource.getByteOffset() + 1;
        int i2 = byteOffset + 1;
        int iUnrandomize255State = unrandomize255State(bitSource.readBits(8), byteOffset);
        if (iUnrandomize255State == 0) {
            iUnrandomize255State = bitSource.available() / 8;
        } else if (iUnrandomize255State >= 250) {
            iUnrandomize255State = ((iUnrandomize255State - 249) * 250) + unrandomize255State(bitSource.readBits(8), i2);
            i2++;
        }
        if (iUnrandomize255State < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[iUnrandomize255State];
        int i3 = 0;
        while (i3 < iUnrandomize255State) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            bArr[i3] = (byte) unrandomize255State(bitSource.readBits(8), i2);
            i3++;
            i2++;
        }
        collection.add(bArr);
        try {
            sb.append(new String(bArr, "ISO8859_1"));
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalStateException("Platform does not support required encoding: " + e2);
        }
    }

    private static void decodeC40Segment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i2 = 0;
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            for (int i3 = 0; i3 < 3; i3++) {
                int i4 = iArr[i3];
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 == 2) {
                            char[] cArr = C40_SHIFT2_SET_CHARS;
                            if (i4 < cArr.length) {
                                char c3 = cArr[i4];
                                if (z2) {
                                    sb.append((char) (c3 + 128));
                                    z2 = false;
                                } else {
                                    sb.append(c3);
                                }
                            } else if (i4 == 27) {
                                sb.append((char) 29);
                            } else {
                                if (i4 != 30) {
                                    throw FormatException.getFormatInstance();
                                }
                                z2 = true;
                            }
                            i2 = 0;
                        } else {
                            if (i2 != 3) {
                                throw FormatException.getFormatInstance();
                            }
                            if (z2) {
                                sb.append((char) (i4 + 224));
                                z2 = false;
                                i2 = 0;
                            } else {
                                sb.append((char) (i4 + 96));
                                i2 = 0;
                            }
                        }
                    } else if (z2) {
                        sb.append((char) (i4 + 128));
                        z2 = false;
                        i2 = 0;
                    } else {
                        sb.append((char) i4);
                        i2 = 0;
                    }
                } else if (i4 < 3) {
                    i2 = i4 + 1;
                } else {
                    char[] cArr2 = C40_BASIC_SET_CHARS;
                    if (i4 >= cArr2.length) {
                        throw FormatException.getFormatInstance();
                    }
                    char c4 = cArr2[i4];
                    if (z2) {
                        sb.append((char) (c4 + 128));
                        z2 = false;
                    } else {
                        sb.append(c4);
                    }
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder sb) {
        while (bitSource.available() > 16) {
            for (int i2 = 0; i2 < 4; i2++) {
                int bits = bitSource.readBits(6);
                if (bits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                        return;
                    }
                    return;
                }
                if ((bits & 32) == 0) {
                    bits |= 64;
                }
                sb.append((char) bits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeTextSegment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i2 = 0;
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            for (int i3 = 0; i3 < 3; i3++) {
                int i4 = iArr[i3];
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 == 2) {
                            char[] cArr = C40_SHIFT2_SET_CHARS;
                            if (i4 < cArr.length) {
                                char c3 = cArr[i4];
                                if (z2) {
                                    sb.append((char) (c3 + 128));
                                    z2 = false;
                                } else {
                                    sb.append(c3);
                                }
                            } else if (i4 == 27) {
                                sb.append((char) 29);
                            } else {
                                if (i4 != 30) {
                                    throw FormatException.getFormatInstance();
                                }
                                z2 = true;
                            }
                            i2 = 0;
                        } else {
                            if (i2 != 3) {
                                throw FormatException.getFormatInstance();
                            }
                            char[] cArr2 = TEXT_SHIFT3_SET_CHARS;
                            if (i4 >= cArr2.length) {
                                throw FormatException.getFormatInstance();
                            }
                            char c4 = cArr2[i4];
                            if (z2) {
                                sb.append((char) (c4 + 128));
                                z2 = false;
                                i2 = 0;
                            } else {
                                sb.append(c4);
                                i2 = 0;
                            }
                        }
                    } else if (z2) {
                        sb.append((char) (i4 + 128));
                        z2 = false;
                        i2 = 0;
                    } else {
                        sb.append((char) i4);
                        i2 = 0;
                    }
                } else if (i4 < 3) {
                    i2 = i4 + 1;
                } else {
                    char[] cArr3 = TEXT_BASIC_SET_CHARS;
                    if (i4 >= cArr3.length) {
                        throw FormatException.getFormatInstance();
                    }
                    char c5 = cArr3[i4];
                    if (z2) {
                        sb.append((char) (c5 + 128));
                        z2 = false;
                    } else {
                        sb.append(c5);
                    }
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void parseTwoBytes(int i2, int i3, int[] iArr) {
        int i4 = ((i2 << 8) + i3) - 1;
        int i5 = i4 / 1600;
        iArr[0] = i5;
        int i6 = i4 - (i5 * 1600);
        int i7 = i6 / 40;
        iArr[1] = i7;
        iArr[2] = i6 - (i7 * 40);
    }

    private static int unrandomize255State(int i2, int i3) {
        int i4 = i2 - (((i3 * 149) % 255) + 1);
        return i4 >= 0 ? i4 : i4 + 256;
    }
}
