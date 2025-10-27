package com.google.zxing.datamatrix.encoder;

import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public final class ErrorCorrection {
    private static final int MODULO_VALUE = 301;
    private static final int[] FACTOR_SETS = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    private static final int[][] FACTORS = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, 144, 134, 240, 92, 254}, new int[]{28, 24, 185, 166, 223, R2.attr.actionModeFindDrawable, 116, 255, 110, 61}, new int[]{R2.anim.window_ios_out, 138, 205, 12, R2.array.ease_numbers_file_suffix, R2.anim.voice_from_icon, 39, R2.attr.actionModeCloseDrawable, 60, 97, 120}, new int[]{41, 153, 158, 91, 61, 42, 142, 213, 97, 178, 100, 242}, new int[]{156, 97, 192, R2.attr.actionModeShareDrawable, 95, 9, 157, 119, 138, 45, 18, 186, 83, 185}, new int[]{83, R2.array.ease_other_file_suffix, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, 254, 225, 48, 90, 188}, new int[]{15, R2.array.ease_other_file_suffix, 244, 9, 233, 71, R2.anim.voice_from_icon, 2, 188, 160, 153, 145, R2.attr.actionModeSplitBackground, 79, 108, 82, 27, R2.anim.window_ios_in, 186, 172}, new int[]{52, R2.array.ease_excel_file_suffix, 88, 205, 109, 39, 176, 21, 155, R2.array.ease_pdf_file_suffix, R2.attr.actionModeSelectAllDrawable, 223, 155, 21, 5, 172, 254, 124, 12, 181, 184, 96, 50, 193}, new int[]{211, 231, 43, 97, 71, 96, 103, R2.anim.window_ios_in, 37, 151, R2.anim.welcome_loading, 53, 75, 34, R2.attr.actionModePasteDrawable, 121, 17, 138, 110, 213, 141, 136, 120, 151, 233, R2.anim.voice_from_icon, 93, 255}, new int[]{R2.attr.actionModeCloseDrawable, 127, 242, 218, 130, 250, 162, 181, 102, 120, 84, 179, 220, R2.attr.actionModeSelectAllDrawable, 80, 182, 229, 18, 2, 4, 68, 33, 101, 137, 95, 119, 115, 44, R2.anim.window_ios_out, 184, 59, 25, 225, 98, 81, 112}, new int[]{77, 193, 137, 31, 19, 38, 22, 153, R2.attr.actionModeCutDrawable, 105, 122, 2, R2.attr.actionModeCloseDrawable, 133, 242, 8, R2.anim.window_ios_out, 95, 100, 9, 167, 105, 214, 111, 57, 121, 21, 1, R2.attr.actionModeSplitBackground, 57, 54, 101, R2.attr.actionModeFindDrawable, 202, 69, 50, 150, 177, 226, 5, 9, 5}, new int[]{R2.attr.actionModeCloseDrawable, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, R2.array.ease_file_file_suffix, 106, 16, 147, 118, 23, 37, 90, R2.anim.welcome_loading, 205, 131, 88, 120, 100, 66, 138, 186, 240, 82, 44, 176, 87, 187, 147, 160, R2.anim.window_ios_out, 69, 213, 92, R2.attr.actionModeSplitBackground, 225, 19}, new int[]{R2.anim.window_ios_out, 9, 223, 238, 12, 17, 220, 208, 100, 29, R2.anim.window_ios_out, R2.anim.welcome_loading, 230, 192, 215, 235, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, 137, R2.attr.actionModeCloseDrawable, 127, 67, R2.attr.actionModeCutDrawable, 28, 155, 43, 203, 107, 233, 53, 143, 46}, new int[]{242, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, 189, 143, 108, R2.array.ease_pages_file_suffix, 37, 185, 112, 134, 230, R2.attr.actionModeCloseDrawable, 63, R2.array.ease_pdf_file_suffix, R2.array.ease_excel_file_suffix, 250, 106, 185, 221, R2.anim.window_ios_out, 64, 114, 71, 161, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, 163, 31, 176, R2.anim.welcome_loading, 4, 107, 232, 7, 94, 166, 224, 124, 86, 47, 11, 204}, new int[]{220, 228, R2.anim.window_bottom_out, 89, R2.attr.actionModeSelectAllDrawable, 149, 159, 56, 89, 33, 147, 244, 154, 36, 73, 127, 213, 136, R2.attr.actionModeFindDrawable, 180, 234, R2.array.ease_pdf_file_suffix, 158, 177, 68, 122, 93, 213, 15, 160, 227, 236, 66, 139, 153, 185, 202, 167, 179, 25, 220, 232, 96, 210, 231, 136, 223, 239, 181, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186}};
    private static final int[] LOG = new int[256];
    private static final int[] ALOG = new int[255];

    static {
        int i2 = 1;
        for (int i3 = 0; i3 < 255; i3++) {
            ALOG[i3] = i2;
            LOG[i2] = i3;
            i2 <<= 1;
            if (i2 >= 256) {
                i2 ^= 301;
            }
        }
    }

    private ErrorCorrection() {
    }

    private static String createECCBlock(CharSequence charSequence, int i2) {
        return createECCBlock(charSequence, 0, charSequence.length(), i2);
    }

    public static String encodeECC200(String str, SymbolInfo symbolInfo) {
        if (str.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        sb.append(str);
        int interleavedBlockCount = symbolInfo.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb.append(createECCBlock(str, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] iArr = new int[interleavedBlockCount];
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int i2 = 0;
            while (i2 < interleavedBlockCount) {
                int i3 = i2 + 1;
                iArr[i2] = symbolInfo.getDataLengthForInterleavedBlock(i3);
                iArr2[i2] = symbolInfo.getErrorLengthForInterleavedBlock(i3);
                iArr3[i2] = 0;
                if (i2 > 0) {
                    iArr3[i2] = iArr3[i2 - 1] + iArr[i2];
                }
                i2 = i3;
            }
            for (int i4 = 0; i4 < interleavedBlockCount; i4++) {
                StringBuilder sb2 = new StringBuilder(iArr[i4]);
                for (int i5 = i4; i5 < symbolInfo.getDataCapacity(); i5 += interleavedBlockCount) {
                    sb2.append(str.charAt(i5));
                }
                String strCreateECCBlock = createECCBlock(sb2.toString(), iArr2[i4]);
                int i6 = i4;
                int i7 = 0;
                while (i6 < iArr2[i4] * interleavedBlockCount) {
                    sb.setCharAt(symbolInfo.getDataCapacity() + i6, strCreateECCBlock.charAt(i7));
                    i6 += interleavedBlockCount;
                    i7++;
                }
            }
        }
        return sb.toString();
    }

    private static String createECCBlock(CharSequence charSequence, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = 0;
        while (true) {
            int[] iArr = FACTOR_SETS;
            if (i7 >= iArr.length) {
                i7 = -1;
                break;
            }
            if (iArr[i7] == i4) {
                break;
            }
            i7++;
        }
        if (i7 < 0) {
            throw new IllegalArgumentException("Illegal number of error correction codewords specified: " + i4);
        }
        int[] iArr2 = FACTORS[i7];
        char[] cArr = new char[i4];
        for (int i8 = 0; i8 < i4; i8++) {
            cArr[i8] = 0;
        }
        for (int i9 = i2; i9 < i2 + i3; i9++) {
            int i10 = i4 - 1;
            int iCharAt = cArr[i10] ^ charSequence.charAt(i9);
            while (i10 > 0) {
                if (iCharAt == 0 || (i6 = iArr2[i10]) == 0) {
                    cArr[i10] = cArr[i10 - 1];
                } else {
                    char c3 = cArr[i10 - 1];
                    int[] iArr3 = ALOG;
                    int[] iArr4 = LOG;
                    cArr[i10] = (char) (iArr3[(iArr4[iCharAt] + iArr4[i6]) % 255] ^ c3);
                }
                i10--;
            }
            if (iCharAt == 0 || (i5 = iArr2[0]) == 0) {
                cArr[0] = 0;
            } else {
                int[] iArr5 = ALOG;
                int[] iArr6 = LOG;
                cArr[0] = (char) iArr5[(iArr6[iCharAt] + iArr6[i5]) % 255];
            }
        }
        char[] cArr2 = new char[i4];
        for (int i11 = 0; i11 < i4; i11++) {
            cArr2[i11] = cArr[(i4 - i11) - 1];
        }
        return String.valueOf(cArr2);
    }
}
