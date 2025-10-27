package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class DtsUtil {
    public static final int DTS_HD_MAX_RATE_BYTES_PER_SECOND = 2250000;
    public static final int DTS_MAX_RATE_BYTES_PER_SECOND = 192000;
    private static final byte FIRST_BYTE_14B_BE = 31;
    private static final byte FIRST_BYTE_14B_LE = -1;
    private static final byte FIRST_BYTE_BE = 127;
    private static final byte FIRST_BYTE_LE = -2;
    private static final int SYNC_VALUE_14B_BE = 536864768;
    private static final int SYNC_VALUE_14B_LE = -14745368;
    private static final int SYNC_VALUE_BE = 2147385345;
    private static final int SYNC_VALUE_LE = -25230976;
    private static final int[] CHANNELS_BY_AMODE = {1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 6, 7, 8, 8};
    private static final int[] SAMPLE_RATE_BY_SFREQ = {-1, 8000, 16000, 32000, -1, -1, R2.drawable.jpush_ic_richpush_actionbar_divider, R2.layout.item_problem, 44100, -1, -1, R2.drawable.qrcodeimg, R2.string.load_cost, 48000, -1, -1};
    private static final int[] TWICE_BITRATE_KBPS_BY_RATE = {64, 112, 128, 192, 224, 256, R2.attr.arrow_right, R2.attr.banner_orientation, 512, 640, R2.attr.bl_unSelected_gradient_useLevel, R2.attr.carousel_previousState, 1024, R2.attr.contrast, 1280, 1536, R2.attr.iconTint, 2048, R2.attr.lineSpacing, R2.attr.mvGravity, R2.attr.nts_weight, R2.attr.plvRedPointColor, R2.attr.plvTopRightRadius, R2.attr.question_detail_select_tag, R2.attr.search, R2.attr.triangleHeight, 4096, R2.dimen.alivc_common_height_icon_68, R2.dimen.dp_m_8};

    private DtsUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getDtsFrameSize(byte[] r7) {
        /*
            r0 = 0
            r1 = r7[r0]
            r2 = -2
            r3 = 6
            r4 = 7
            r5 = 1
            r6 = 4
            if (r1 == r2) goto L4a
            r2 = -1
            if (r1 == r2) goto L32
            r2 = 31
            if (r1 == r2) goto L21
            r1 = 5
            r1 = r7[r1]
            r1 = r1 & 3
            int r1 = r1 << 12
            r2 = r7[r3]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r6
            r1 = r1 | r2
            r7 = r7[r4]
            goto L58
        L21:
            r0 = r7[r3]
            r0 = r0 & 3
            int r0 = r0 << 12
            r1 = r7[r4]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << r6
            r0 = r0 | r1
            r1 = 8
            r7 = r7[r1]
            goto L42
        L32:
            r0 = r7[r4]
            r0 = r0 & 3
            int r0 = r0 << 12
            r1 = r7[r3]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << r6
            r0 = r0 | r1
            r1 = 9
            r7 = r7[r1]
        L42:
            r7 = r7 & 60
            int r7 = r7 >> 2
            r7 = r7 | r0
            int r7 = r7 + r5
            r0 = r5
            goto L5d
        L4a:
            r1 = r7[r6]
            r1 = r1 & 3
            int r1 = r1 << 12
            r2 = r7[r4]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r6
            r1 = r1 | r2
            r7 = r7[r3]
        L58:
            r7 = r7 & 240(0xf0, float:3.36E-43)
            int r7 = r7 >> r6
            r7 = r7 | r1
            int r7 = r7 + r5
        L5d:
            if (r0 == 0) goto L63
            int r7 = r7 * 16
            int r7 = r7 / 14
        L63:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DtsUtil.getDtsFrameSize(byte[]):int");
    }

    private static ParsableBitArray getNormalizedFrameHeader(byte[] bArr) {
        if (bArr[0] == 127) {
            return new ParsableBitArray(bArr);
        }
        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
        if (isLittleEndianFrameHeader(bArrCopyOf)) {
            for (int i2 = 0; i2 < bArrCopyOf.length - 1; i2 += 2) {
                byte b3 = bArrCopyOf[i2];
                int i3 = i2 + 1;
                bArrCopyOf[i2] = bArrCopyOf[i3];
                bArrCopyOf[i3] = b3;
            }
        }
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArrCopyOf);
        if (bArrCopyOf[0] == 31) {
            ParsableBitArray parsableBitArray2 = new ParsableBitArray(bArrCopyOf);
            while (parsableBitArray2.bitsLeft() >= 16) {
                parsableBitArray2.skipBits(2);
                parsableBitArray.putInt(parsableBitArray2.readBits(14), 14);
            }
        }
        parsableBitArray.reset(bArrCopyOf);
        return parsableBitArray;
    }

    private static boolean isLittleEndianFrameHeader(byte[] bArr) {
        byte b3 = bArr[0];
        return b3 == -2 || b3 == -1;
    }

    public static boolean isSyncWord(int i2) {
        return i2 == SYNC_VALUE_BE || i2 == SYNC_VALUE_LE || i2 == SYNC_VALUE_14B_BE || i2 == SYNC_VALUE_14B_LE;
    }

    public static int parseDtsAudioSampleCount(byte[] bArr) {
        int i2;
        byte b3;
        int i3;
        byte b4;
        byte b5 = bArr[0];
        if (b5 != -2) {
            if (b5 == -1) {
                i2 = (bArr[4] & 7) << 4;
                b4 = bArr[7];
            } else if (b5 != 31) {
                i2 = (bArr[4] & 1) << 6;
                b3 = bArr[5];
            } else {
                i2 = (bArr[5] & 7) << 4;
                b4 = bArr[6];
            }
            i3 = b4 & 60;
            return (((i3 >> 2) | i2) + 1) * 32;
        }
        i2 = (bArr[5] & 1) << 6;
        b3 = bArr[4];
        i3 = b3 & 252;
        return (((i3 >> 2) | i2) + 1) * 32;
    }

    public static Format parseDtsFormat(byte[] bArr, @Nullable String str, @Nullable String str2, @Nullable DrmInitData drmInitData) {
        ParsableBitArray normalizedFrameHeader = getNormalizedFrameHeader(bArr);
        normalizedFrameHeader.skipBits(60);
        int i2 = CHANNELS_BY_AMODE[normalizedFrameHeader.readBits(6)];
        int i3 = SAMPLE_RATE_BY_SFREQ[normalizedFrameHeader.readBits(4)];
        int bits = normalizedFrameHeader.readBits(5);
        int[] iArr = TWICE_BITRATE_KBPS_BY_RATE;
        int i4 = bits >= iArr.length ? -1 : (iArr[bits] * 1000) / 2;
        normalizedFrameHeader.skipBits(10);
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.AUDIO_DTS).setAverageBitrate(i4).setChannelCount(i2 + (normalizedFrameHeader.readBits(2) > 0 ? 1 : 0)).setSampleRate(i3).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseDtsAudioSampleCount(ByteBuffer byteBuffer) {
        int i2;
        byte b3;
        int i3;
        byte b4;
        int iPosition = byteBuffer.position();
        byte b5 = byteBuffer.get(iPosition);
        if (b5 != -2) {
            if (b5 == -1) {
                i2 = (byteBuffer.get(iPosition + 4) & 7) << 4;
                b4 = byteBuffer.get(iPosition + 7);
            } else if (b5 != 31) {
                i2 = (byteBuffer.get(iPosition + 4) & 1) << 6;
                b3 = byteBuffer.get(iPosition + 5);
            } else {
                i2 = (byteBuffer.get(iPosition + 5) & 7) << 4;
                b4 = byteBuffer.get(iPosition + 6);
            }
            i3 = b4 & 60;
            return (((i3 >> 2) | i2) + 1) * 32;
        }
        i2 = (byteBuffer.get(iPosition + 5) & 1) << 6;
        b3 = byteBuffer.get(iPosition + 4);
        i3 = b3 & 252;
        return (((i3 >> 2) | i2) + 1) * 32;
    }
}
