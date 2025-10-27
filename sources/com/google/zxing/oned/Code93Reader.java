package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.yikaobang.yixue.R2;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Code93Reader extends OneDReader {
    private static final int ASTERISK_ENCODING;
    private static final int[] CHARACTER_ENCODINGS;
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    private final StringBuilder decodeRowResult = new StringBuilder(20);
    private final int[] counters = new int[6];

    static {
        int[] iArr = {R2.attr.ad_height, R2.attr.antiAlias, 324, 322, R2.attr.all_course_search_icon, R2.attr.all_comment_text_color, R2.attr.alignmentMode, R2.attr.app_title_color, R2.attr.adSizes, R2.attr.activityChooserViewStyle, 424, 420, 418, 404, 402, R2.attr.autoSizeStepGranularity, 360, R2.attr.arcLabelTextSize, R2.attr.arcLabelText, 308, R2.attr.adjustable, R2.attr.arcAllowableOffsets, R2.attr.app_setting_color, 326, 300, R2.attr.ad_marker_width, R2.attr.banner_indicator_marginBottom, R2.attr.banner_indicator_height, R2.attr.badgeStyle, 422, 406, 410, R2.attr.arcShowLabel, R2.attr.arcMode, 310, 314, 302, R2.attr.bead_margin, R2.attr.barrierDirection, R2.attr.barLength, R2.attr.arcShowTick, R2.attr.arcThumbRadius, R2.attr.badgeWidePadding, R2.attr.all_course_live_banner_start_bg_color, R2.attr.behavior_fitToContents, R2.attr.behavior_autoHide, 306, R2.attr.arcLabelPaddingBottom};
        CHARACTER_ENCODINGS = iArr;
        ASTERISK_ENCODING = iArr[47];
    }

    private static void checkChecksums(CharSequence charSequence) throws ChecksumException {
        int length = charSequence.length();
        checkOneChecksum(charSequence, length - 2, 20);
        checkOneChecksum(charSequence, length - 1, 15);
    }

    private static void checkOneChecksum(CharSequence charSequence, int i2, int i3) throws ChecksumException {
        int iIndexOf = 0;
        int i4 = 1;
        for (int i5 = i2 - 1; i5 >= 0; i5--) {
            iIndexOf += ALPHABET_STRING.indexOf(charSequence.charAt(i5)) * i4;
            i4++;
            if (i4 > i3) {
                i4 = 1;
            }
        }
        if (charSequence.charAt(i2) != ALPHABET[iIndexOf % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }

    private static String decodeExtended(CharSequence charSequence) throws FormatException {
        int i2;
        char c3;
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        int i3 = 0;
        while (i3 < length) {
            char cCharAt = charSequence.charAt(i3);
            if (cCharAt < 'a' || cCharAt > 'd') {
                sb.append(cCharAt);
            } else {
                if (i3 >= length - 1) {
                    throw FormatException.getFormatInstance();
                }
                i3++;
                char cCharAt2 = charSequence.charAt(i3);
                switch (cCharAt) {
                    case 'a':
                        if (cCharAt2 >= 'A' && cCharAt2 <= 'Z') {
                            i2 = cCharAt2 - '@';
                            c3 = (char) i2;
                            sb.append(c3);
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case 'b':
                        if (cCharAt2 >= 'A' && cCharAt2 <= 'E') {
                            i2 = cCharAt2 - '&';
                        } else {
                            if (cCharAt2 < 'F' || cCharAt2 > 'W') {
                                throw FormatException.getFormatInstance();
                            }
                            i2 = cCharAt2 - 11;
                        }
                        c3 = (char) i2;
                        sb.append(c3);
                        break;
                    case 'c':
                        if (cCharAt2 >= 'A' && cCharAt2 <= 'O') {
                            i2 = cCharAt2 - ' ';
                            c3 = (char) i2;
                            sb.append(c3);
                        } else {
                            if (cCharAt2 != 'Z') {
                                throw FormatException.getFormatInstance();
                            }
                            c3 = ':';
                            sb.append(c3);
                            break;
                        }
                        break;
                    case 'd':
                        if (cCharAt2 >= 'A' && cCharAt2 <= 'Z') {
                            i2 = cCharAt2 + ' ';
                            c3 = (char) i2;
                            sb.append(c3);
                            break;
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    default:
                        c3 = 0;
                        sb.append(c3);
                        break;
                }
            }
            i3++;
        }
        return sb.toString();
    }

    private int[] findAsteriskPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        Arrays.fill(this.counters, 0);
        int[] iArr = this.counters;
        int length = iArr.length;
        boolean z2 = false;
        int i2 = 0;
        int i3 = nextSet;
        while (nextSet < size) {
            if (bitArray.get(nextSet) ^ z2) {
                iArr[i2] = iArr[i2] + 1;
            } else {
                int i4 = length - 1;
                if (i2 != i4) {
                    i2++;
                } else {
                    if (toPattern(iArr) == ASTERISK_ENCODING) {
                        return new int[]{i3, nextSet};
                    }
                    i3 += iArr[0] + iArr[1];
                    int i5 = length - 2;
                    System.arraycopy(iArr, 2, iArr, 0, i5);
                    iArr[i5] = 0;
                    iArr[i4] = 0;
                    i2--;
                }
                iArr[i2] = 1;
                z2 = !z2;
            }
            nextSet++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static char patternToChar(int i2) throws NotFoundException {
        int i3 = 0;
        while (true) {
            int[] iArr = CHARACTER_ENCODINGS;
            if (i3 >= iArr.length) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (iArr[i3] == i2) {
                return ALPHABET[i3];
            }
            i3++;
        }
    }

    private static int toPattern(int[] iArr) {
        int length = iArr.length;
        int i2 = 0;
        for (int i3 : iArr) {
            i2 += i3;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = ((iArr[i5] << 8) * 9) / i2;
            int i7 = i6 >> 8;
            if ((i6 & 255) > 127) {
                i7++;
            }
            if (i7 < 1 || i7 > 4) {
                return -1;
            }
            if ((i5 & 1) == 0) {
                for (int i8 = 0; i8 < i7; i8++) {
                    i4 = (i4 << 1) | 1;
                }
            } else {
                i4 <<= i7;
            }
        }
        return i4;
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        int nextSet = bitArray.getNextSet(findAsteriskPattern(bitArray)[1]);
        int size = bitArray.getSize();
        int[] iArr = this.counters;
        Arrays.fill(iArr, 0);
        StringBuilder sb = this.decodeRowResult;
        sb.setLength(0);
        while (true) {
            OneDReader.recordPattern(bitArray, nextSet, iArr);
            int pattern = toPattern(iArr);
            if (pattern < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            char cPatternToChar = patternToChar(pattern);
            sb.append(cPatternToChar);
            int i3 = nextSet;
            for (int i4 : iArr) {
                i3 += i4;
            }
            int nextSet2 = bitArray.getNextSet(i3);
            if (cPatternToChar == '*') {
                sb.deleteCharAt(sb.length() - 1);
                int i5 = 0;
                for (int i6 : iArr) {
                    i5 += i6;
                }
                if (nextSet2 == size || !bitArray.get(nextSet2)) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (sb.length() < 2) {
                    throw NotFoundException.getNotFoundInstance();
                }
                checkChecksums(sb);
                sb.setLength(sb.length() - 2);
                float f2 = i2;
                return new Result(decodeExtended(sb), null, new ResultPoint[]{new ResultPoint((r14[1] + r14[0]) / 2.0f, f2), new ResultPoint(nextSet + (i5 / 2.0f), f2)}, BarcodeFormat.CODE_93);
            }
            nextSet = nextSet2;
        }
    }
}
