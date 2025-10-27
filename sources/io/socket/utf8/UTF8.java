package io.socket.utf8;

import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;
import okio.Utf8;

/* loaded from: classes8.dex */
public final class UTF8 {
    private static final String INVALID_CONTINUATION_BYTE = "Invalid continuation byte";
    private static int[] byteArray;
    private static int byteCount;
    private static int byteIndex;

    public static class Options {
        public boolean strict = true;
    }

    private UTF8() {
    }

    private static boolean checkScalarValue(int i2, boolean z2) throws UTF8Exception {
        if (i2 < 55296 || i2 > 57343) {
            return true;
        }
        if (!z2) {
            return false;
        }
        throw new UTF8Exception("Lone surrogate U+" + Integer.toHexString(i2).toUpperCase() + " is not a scalar value");
    }

    private static char[] createByte(int i2, int i3) {
        return Character.toChars(((i2 >> i3) & 63) | 128);
    }

    public static String decode(String str) throws UTF8Exception {
        return decode(str, new Options());
    }

    private static int decodeSymbol(boolean z2) throws UTF8Exception {
        int i2 = byteIndex;
        int i3 = byteCount;
        if (i2 > i3) {
            throw new UTF8Exception("Invalid byte index");
        }
        if (i2 == i3) {
            return -1;
        }
        int i4 = byteArray[i2] & 255;
        byteIndex = i2 + 1;
        if ((i4 & 128) == 0) {
            return i4;
        }
        if ((i4 & 224) == 192) {
            int continuationByte = readContinuationByte() | ((i4 & 31) << 6);
            if (continuationByte >= 128) {
                return continuationByte;
            }
            throw new UTF8Exception(INVALID_CONTINUATION_BYTE);
        }
        if ((i4 & 240) == 224) {
            int continuationByte2 = (readContinuationByte() << 6) | ((i4 & 15) << 12) | readContinuationByte();
            if (continuationByte2 >= 2048) {
                return checkScalarValue(continuationByte2, z2) ? continuationByte2 : Utf8.REPLACEMENT_CODE_POINT;
            }
            throw new UTF8Exception(INVALID_CONTINUATION_BYTE);
        }
        if ((i4 & R2.attr.actionModeFindDrawable) == 240) {
            int continuationByte3 = (readContinuationByte() << 12) | ((i4 & 15) << 18) | (readContinuationByte() << 6) | readContinuationByte();
            if (continuationByte3 >= 65536 && continuationByte3 <= 1114111) {
                return continuationByte3;
            }
        }
        throw new UTF8Exception(INVALID_CONTINUATION_BYTE);
    }

    public static String encode(String str) throws UTF8Exception {
        return encode(str, new Options());
    }

    private static String encodeCodePoint(int i2, boolean z2) throws UTF8Exception {
        StringBuilder sb = new StringBuilder();
        if ((i2 & (-128)) == 0) {
            sb.append(Character.toChars(i2));
            return sb.toString();
        }
        if ((i2 & (-2048)) == 0) {
            sb.append(Character.toChars(((i2 >> 6) & 31) | 192));
        } else if (((-65536) & i2) == 0) {
            if (!checkScalarValue(i2, z2)) {
                i2 = Utf8.REPLACEMENT_CODE_POINT;
            }
            sb.append(Character.toChars(((i2 >> 12) & 15) | 224));
            sb.append(createByte(i2, 6));
        } else if (((-2097152) & i2) == 0) {
            sb.append(Character.toChars(((i2 >> 18) & 7) | 240));
            sb.append(createByte(i2, 12));
            sb.append(createByte(i2, 6));
        }
        sb.append(Character.toChars((i2 & 63) | 128));
        return sb.toString();
    }

    private static int[] listToArray(List<Integer> list) {
        int size = list.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = list.get(i2).intValue();
        }
        return iArr;
    }

    private static int readContinuationByte() throws UTF8Exception {
        int i2 = byteIndex;
        if (i2 >= byteCount) {
            throw new UTF8Exception("Invalid byte index");
        }
        int i3 = byteArray[i2] & 255;
        byteIndex = i2 + 1;
        if ((i3 & 192) == 128) {
            return i3 & 63;
        }
        throw new UTF8Exception(INVALID_CONTINUATION_BYTE);
    }

    private static int[] ucs2decode(String str) {
        int length = str.length();
        int iCharCount = 0;
        int[] iArr = new int[str.codePointCount(0, length)];
        int i2 = 0;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            iArr[i2] = iCodePointAt;
            iCharCount += Character.charCount(iCodePointAt);
            i2++;
        }
        return iArr;
    }

    private static String ucs2encode(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 : iArr) {
            sb.appendCodePoint(i2);
        }
        return sb.toString();
    }

    public static String decode(String str, Options options) throws UTF8Exception {
        boolean z2 = options.strict;
        int[] iArrUcs2decode = ucs2decode(str);
        byteArray = iArrUcs2decode;
        byteCount = iArrUcs2decode.length;
        byteIndex = 0;
        ArrayList arrayList = new ArrayList();
        while (true) {
            int iDecodeSymbol = decodeSymbol(z2);
            if (iDecodeSymbol == -1) {
                return ucs2encode(listToArray(arrayList));
            }
            arrayList.add(Integer.valueOf(iDecodeSymbol));
        }
    }

    public static String encode(String str, Options options) throws UTF8Exception {
        boolean z2 = options.strict;
        int[] iArrUcs2decode = ucs2decode(str);
        int length = iArrUcs2decode.length;
        StringBuilder sb = new StringBuilder();
        int i2 = -1;
        while (true) {
            i2++;
            if (i2 >= length) {
                return sb.toString();
            }
            sb.append(encodeCodePoint(iArrUcs2decode[i2], z2));
        }
    }
}
