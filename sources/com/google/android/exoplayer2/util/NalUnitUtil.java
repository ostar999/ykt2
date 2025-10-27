package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.PLVSoftView;
import com.google.common.base.Ascii;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class NalUnitUtil {
    public static final int EXTENDED_SAR = 255;
    private static final int H264_NAL_UNIT_TYPE_SEI = 6;
    private static final int H264_NAL_UNIT_TYPE_SPS = 7;
    private static final int H265_NAL_UNIT_TYPE_PREFIX_SEI = 39;
    private static final String TAG = "NalUnitUtil";
    public static final byte[] NAL_START_CODE = {0, 0, 0, 1};
    public static final float[] ASPECT_RATIO_IDC_VALUES = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, 2.0f};
    private static final Object scratchEscapePositionsLock = new Object();
    private static int[] scratchEscapePositions = new int[10];

    public static final class H265SpsData {
        public final int[] constraintBytes;
        public final int generalLevelIdc;
        public final int generalProfileCompatibilityFlags;
        public final int generalProfileIdc;
        public final int generalProfileSpace;
        public final boolean generalTierFlag;
        public final int height;
        public final float pixelWidthHeightRatio;
        public final int seqParameterSetId;
        public final int width;

        public H265SpsData(int i2, boolean z2, int i3, int i4, int[] iArr, int i5, int i6, int i7, int i8, float f2) {
            this.generalProfileSpace = i2;
            this.generalTierFlag = z2;
            this.generalProfileIdc = i3;
            this.generalProfileCompatibilityFlags = i4;
            this.constraintBytes = iArr;
            this.generalLevelIdc = i5;
            this.seqParameterSetId = i6;
            this.width = i7;
            this.height = i8;
            this.pixelWidthHeightRatio = f2;
        }
    }

    public static final class PpsData {
        public final boolean bottomFieldPicOrderInFramePresentFlag;
        public final int picParameterSetId;
        public final int seqParameterSetId;

        public PpsData(int i2, int i3, boolean z2) {
            this.picParameterSetId = i2;
            this.seqParameterSetId = i3;
            this.bottomFieldPicOrderInFramePresentFlag = z2;
        }
    }

    public static final class SpsData {
        public final int constraintsFlagsAndReservedZero2Bits;
        public final boolean deltaPicOrderAlwaysZeroFlag;
        public final boolean frameMbsOnlyFlag;
        public final int frameNumLength;
        public final int height;
        public final int levelIdc;
        public final int picOrderCntLsbLength;
        public final int picOrderCountType;
        public final float pixelWidthHeightRatio;
        public final int profileIdc;
        public final boolean separateColorPlaneFlag;
        public final int seqParameterSetId;
        public final int width;

        public SpsData(int i2, int i3, int i4, int i5, int i6, int i7, float f2, boolean z2, boolean z3, int i8, int i9, int i10, boolean z4) {
            this.profileIdc = i2;
            this.constraintsFlagsAndReservedZero2Bits = i3;
            this.levelIdc = i4;
            this.seqParameterSetId = i5;
            this.width = i6;
            this.height = i7;
            this.pixelWidthHeightRatio = f2;
            this.separateColorPlaneFlag = z2;
            this.frameMbsOnlyFlag = z3;
            this.frameNumLength = i8;
            this.picOrderCountType = i9;
            this.picOrderCntLsbLength = i10;
            this.deltaPicOrderAlwaysZeroFlag = z4;
        }
    }

    private NalUnitUtil() {
    }

    public static void clearPrefixFlags(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    public static void discardToSps(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i2 + 1;
            if (i4 >= iPosition) {
                byteBuffer.clear();
                return;
            }
            int i5 = byteBuffer.get(i2) & 255;
            if (i3 == 3) {
                if (i5 == 1 && (byteBuffer.get(i4) & Ascii.US) == 7) {
                    ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
                    byteBufferDuplicate.position(i2 - 3);
                    byteBufferDuplicate.limit(iPosition);
                    byteBuffer.position(0);
                    byteBuffer.put(byteBufferDuplicate);
                    return;
                }
            } else if (i5 == 0) {
                i3++;
            }
            if (i5 != 0) {
                i3 = 0;
            }
            i2 = i4;
        }
    }

    public static int findNalUnit(byte[] bArr, int i2, int i3, boolean[] zArr) {
        int i4 = i3 - i2;
        Assertions.checkState(i4 >= 0);
        if (i4 == 0) {
            return i3;
        }
        if (zArr[0]) {
            clearPrefixFlags(zArr);
            return i2 - 3;
        }
        if (i4 > 1 && zArr[1] && bArr[i2] == 1) {
            clearPrefixFlags(zArr);
            return i2 - 2;
        }
        if (i4 > 2 && zArr[2] && bArr[i2] == 0 && bArr[i2 + 1] == 1) {
            clearPrefixFlags(zArr);
            return i2 - 1;
        }
        int i5 = i3 - 1;
        int i6 = i2 + 2;
        while (i6 < i5) {
            byte b3 = bArr[i6];
            if ((b3 & PLVSoftView.KEYBOARD_STATE_HIDE) == 0) {
                int i7 = i6 - 2;
                if (bArr[i7] == 0 && bArr[i6 - 1] == 0 && b3 == 1) {
                    clearPrefixFlags(zArr);
                    return i7;
                }
                i6 -= 2;
            }
            i6 += 3;
        }
        zArr[0] = i4 <= 2 ? !(i4 != 2 ? !(zArr[1] && bArr[i5] == 1) : !(zArr[2] && bArr[i3 + (-2)] == 0 && bArr[i5] == 1)) : bArr[i3 + (-3)] == 0 && bArr[i3 + (-2)] == 0 && bArr[i5] == 1;
        zArr[1] = i4 <= 1 ? zArr[2] && bArr[i5] == 0 : bArr[i3 + (-2)] == 0 && bArr[i5] == 0;
        zArr[2] = bArr[i5] == 0;
        return i3;
    }

    private static int findNextUnescapeIndex(byte[] bArr, int i2, int i3) {
        while (i2 < i3 - 2) {
            if (bArr[i2] == 0 && bArr[i2 + 1] == 0 && bArr[i2 + 2] == 3) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    public static int getH265NalUnitType(byte[] bArr, int i2) {
        return (bArr[i2 + 3] & 126) >> 1;
    }

    public static int getNalUnitType(byte[] bArr, int i2) {
        return bArr[i2 + 3] & Ascii.US;
    }

    public static boolean isNalUnitSei(@Nullable String str, byte b3) {
        if (MimeTypes.VIDEO_H264.equals(str) && (b3 & Ascii.US) == 6) {
            return true;
        }
        return MimeTypes.VIDEO_H265.equals(str) && ((b3 & 126) >> 1) == 39;
    }

    public static H265SpsData parseH265SpsNalUnit(byte[] bArr, int i2, int i3) {
        return parseH265SpsNalUnitPayload(bArr, i2 + 2, i3);
    }

    public static H265SpsData parseH265SpsNalUnitPayload(byte[] bArr, int i2, int i3) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i2, i3);
        parsableNalUnitBitArray.skipBits(4);
        int bits = parsableNalUnitBitArray.readBits(3);
        parsableNalUnitBitArray.skipBit();
        int bits2 = parsableNalUnitBitArray.readBits(2);
        boolean bit = parsableNalUnitBitArray.readBit();
        int bits3 = parsableNalUnitBitArray.readBits(5);
        int i4 = 0;
        for (int i5 = 0; i5 < 32; i5++) {
            if (parsableNalUnitBitArray.readBit()) {
                i4 |= 1 << i5;
            }
        }
        int[] iArr = new int[6];
        for (int i6 = 0; i6 < 6; i6++) {
            iArr[i6] = parsableNalUnitBitArray.readBits(8);
        }
        int bits4 = parsableNalUnitBitArray.readBits(8);
        int i7 = 0;
        for (int i8 = 0; i8 < bits; i8++) {
            if (parsableNalUnitBitArray.readBit()) {
                i7 += 89;
            }
            if (parsableNalUnitBitArray.readBit()) {
                i7 += 8;
            }
        }
        parsableNalUnitBitArray.skipBits(i7);
        if (bits > 0) {
            parsableNalUnitBitArray.skipBits((8 - bits) * 2);
        }
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (unsignedExpGolombCodedInt2 == 3) {
            parsableNalUnitBitArray.skipBit();
        }
        int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit()) {
            int unsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            unsignedExpGolombCodedInt3 -= ((unsignedExpGolombCodedInt2 == 1 || unsignedExpGolombCodedInt2 == 2) ? 2 : 1) * (unsignedExpGolombCodedInt5 + unsignedExpGolombCodedInt6);
            unsignedExpGolombCodedInt4 -= (unsignedExpGolombCodedInt2 == 1 ? 2 : 1) * (unsignedExpGolombCodedInt7 + unsignedExpGolombCodedInt8);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        for (int i9 = parsableNalUnitBitArray.readBit() ? 0 : bits; i9 <= bits; i9++) {
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            skipH265ScalingList(parsableNalUnitBitArray);
        }
        parsableNalUnitBitArray.skipBits(2);
        if (parsableNalUnitBitArray.readBit()) {
            parsableNalUnitBitArray.skipBits(8);
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
        }
        skipShortTermReferencePictureSets(parsableNalUnitBitArray);
        if (parsableNalUnitBitArray.readBit()) {
            for (int i10 = 0; i10 < parsableNalUnitBitArray.readUnsignedExpGolombCodedInt(); i10++) {
                parsableNalUnitBitArray.skipBits(unsignedExpGolombCodedInt9 + 4 + 1);
            }
        }
        parsableNalUnitBitArray.skipBits(2);
        float f2 = 1.0f;
        if (parsableNalUnitBitArray.readBit()) {
            if (parsableNalUnitBitArray.readBit()) {
                int bits5 = parsableNalUnitBitArray.readBits(8);
                if (bits5 == 255) {
                    int bits6 = parsableNalUnitBitArray.readBits(16);
                    int bits7 = parsableNalUnitBitArray.readBits(16);
                    if (bits6 != 0 && bits7 != 0) {
                        f2 = bits6 / bits7;
                    }
                } else {
                    float[] fArr = ASPECT_RATIO_IDC_VALUES;
                    if (bits5 < fArr.length) {
                        f2 = fArr[bits5];
                    } else {
                        StringBuilder sb = new StringBuilder(46);
                        sb.append("Unexpected aspect_ratio_idc value: ");
                        sb.append(bits5);
                        Log.w(TAG, sb.toString());
                    }
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBit();
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBits(4);
                if (parsableNalUnitBitArray.readBit()) {
                    parsableNalUnitBitArray.skipBits(24);
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                unsignedExpGolombCodedInt4 *= 2;
            }
        }
        return new H265SpsData(bits2, bit, bits3, i4, iArr, bits4, unsignedExpGolombCodedInt, unsignedExpGolombCodedInt3, unsignedExpGolombCodedInt4, f2);
    }

    public static PpsData parsePpsNalUnit(byte[] bArr, int i2, int i3) {
        return parsePpsNalUnitPayload(bArr, i2 + 1, i3);
    }

    public static PpsData parsePpsNalUnitPayload(byte[] bArr, int i2, int i3) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i2, i3);
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        return new PpsData(unsignedExpGolombCodedInt, unsignedExpGolombCodedInt2, parsableNalUnitBitArray.readBit());
    }

    public static SpsData parseSpsNalUnit(byte[] bArr, int i2, int i3) {
        return parseSpsNalUnitPayload(bArr, i2 + 1, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x016c A[PHI: r2
      0x016c: PHI (r2v8 float) = (r2v7 float), (r2v7 float), (r2v7 float), (r2v7 float), (r2v7 float), (r2v10 float) binds: [B:71:0x0126, B:73:0x012c, B:83:0x0154, B:77:0x0142, B:78:0x0144, B:79:0x0146] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.exoplayer2.util.NalUnitUtil.SpsData parseSpsNalUnitPayload(byte[] r21, int r22, int r23) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.NalUnitUtil.parseSpsNalUnitPayload(byte[], int, int):com.google.android.exoplayer2.util.NalUnitUtil$SpsData");
    }

    private static void skipH265ScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = 0;
            while (i3 < 6) {
                int i4 = 1;
                if (parsableNalUnitBitArray.readBit()) {
                    int iMin = Math.min(64, 1 << ((i2 << 1) + 4));
                    if (i2 > 1) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i5 = 0; i5 < iMin; i5++) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                } else {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                }
                if (i2 == 3) {
                    i4 = 3;
                }
                i3 += i4;
            }
        }
    }

    private static void skipScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray, int i2) {
        int signedExpGolombCodedInt = 8;
        int i3 = 8;
        for (int i4 = 0; i4 < i2; i4++) {
            if (signedExpGolombCodedInt != 0) {
                signedExpGolombCodedInt = ((parsableNalUnitBitArray.readSignedExpGolombCodedInt() + i3) + 256) % 256;
            }
            if (signedExpGolombCodedInt != 0) {
                i3 = signedExpGolombCodedInt;
            }
        }
    }

    private static void skipShortTermReferencePictureSets(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        boolean bit = false;
        int i2 = 0;
        for (int i3 = 0; i3 < unsignedExpGolombCodedInt; i3++) {
            if (i3 != 0) {
                bit = parsableNalUnitBitArray.readBit();
            }
            if (bit) {
                parsableNalUnitBitArray.skipBit();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                for (int i4 = 0; i4 <= i2; i4++) {
                    if (parsableNalUnitBitArray.readBit()) {
                        parsableNalUnitBitArray.skipBit();
                    }
                }
            } else {
                int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int i5 = unsignedExpGolombCodedInt2 + unsignedExpGolombCodedInt3;
                for (int i6 = 0; i6 < unsignedExpGolombCodedInt2; i6++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
                for (int i7 = 0; i7 < unsignedExpGolombCodedInt3; i7++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
                i2 = i5;
            }
        }
    }

    public static int unescapeStream(byte[] bArr, int i2) {
        int i3;
        synchronized (scratchEscapePositionsLock) {
            int iFindNextUnescapeIndex = 0;
            int i4 = 0;
            while (iFindNextUnescapeIndex < i2) {
                try {
                    iFindNextUnescapeIndex = findNextUnescapeIndex(bArr, iFindNextUnescapeIndex, i2);
                    if (iFindNextUnescapeIndex < i2) {
                        int[] iArr = scratchEscapePositions;
                        if (iArr.length <= i4) {
                            scratchEscapePositions = Arrays.copyOf(iArr, iArr.length * 2);
                        }
                        scratchEscapePositions[i4] = iFindNextUnescapeIndex;
                        iFindNextUnescapeIndex += 3;
                        i4++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            i3 = i2 - i4;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < i4; i7++) {
                int i8 = scratchEscapePositions[i7] - i6;
                System.arraycopy(bArr, i6, bArr, i5, i8);
                int i9 = i5 + i8;
                int i10 = i9 + 1;
                bArr[i9] = 0;
                i5 = i10 + 1;
                bArr[i10] = 0;
                i6 += i8 + 3;
            }
            System.arraycopy(bArr, i6, bArr, i5, i3 - i5);
        }
        return i3;
    }
}
