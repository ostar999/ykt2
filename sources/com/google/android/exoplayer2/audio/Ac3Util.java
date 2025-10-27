package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.PLVSoftView;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes3.dex */
public final class Ac3Util {
    public static final int AC3_MAX_RATE_BYTES_PER_SECOND = 80000;
    private static final int AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT = 1536;
    private static final int AUDIO_SAMPLES_PER_AUDIO_BLOCK = 256;
    public static final int E_AC3_MAX_RATE_BYTES_PER_SECOND = 768000;
    public static final int TRUEHD_MAX_RATE_BYTES_PER_SECOND = 3062500;
    public static final int TRUEHD_RECHUNK_SAMPLE_COUNT = 16;
    public static final int TRUEHD_SYNCFRAME_PREFIX_LENGTH = 10;
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = {1, 2, 3, 6};
    private static final int[] SAMPLE_RATE_BY_FSCOD = {48000, 44100, 32000};
    private static final int[] SAMPLE_RATE_BY_FSCOD2 = {R2.string.load_cost, R2.layout.item_problem, 16000};
    private static final int[] CHANNEL_COUNT_BY_ACMOD = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD = {32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, R2.attr.arrow_right, R2.attr.banner_orientation, 512, R2.attr.bl_enabled_gradient_centerColor, 640};
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = {69, 87, 104, 121, 139, R2.anim.window_ios_in, 208, 243, R2.attr.ad_marker_width, R2.attr.arcEnabledSingle, 417, R2.attr.bg_circle_black, R2.attr.bl_corners_topRightRadius, 696, R2.attr.btn_disable_color, R2.attr.circle_toupai, 1114, R2.attr.dailyTaskCalendarTaskFailBg, R2.attr.ease_chat_item_receiver_background};

    public static final class SyncFrameInfo {
        public static final int STREAM_TYPE_TYPE0 = 0;
        public static final int STREAM_TYPE_TYPE1 = 1;
        public static final int STREAM_TYPE_TYPE2 = 2;
        public static final int STREAM_TYPE_UNDEFINED = -1;
        public final int channelCount;
        public final int frameSize;

        @Nullable
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;
        public final int streamType;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface StreamType {
        }

        private SyncFrameInfo(@Nullable String str, int i2, int i3, int i4, int i5, int i6) {
            this.mimeType = str;
            this.streamType = i2;
            this.channelCount = i3;
            this.sampleRate = i4;
            this.frameSize = i5;
            this.sampleCount = i6;
        }
    }

    private Ac3Util() {
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit() - 10;
        for (int i2 = iPosition; i2 <= iLimit; i2++) {
            if ((Util.getBigEndianInt(byteBuffer, i2 + 4) & (-2)) == -126718022) {
                return i2 - iPosition;
            }
        }
        return -1;
    }

    private static int getAc3SyncframeSize(int i2, int i3) {
        int i4 = i3 / 2;
        if (i2 < 0) {
            return -1;
        }
        int[] iArr = SAMPLE_RATE_BY_FSCOD;
        if (i2 >= iArr.length || i3 < 0) {
            return -1;
        }
        int[] iArr2 = SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1;
        if (i4 >= iArr2.length) {
            return -1;
        }
        int i5 = iArr[i2];
        if (i5 == 44100) {
            return (iArr2[i4] + (i3 % 2)) * 2;
        }
        int i6 = BITRATE_BY_HALF_FRMSIZECOD[i4];
        return i5 == 32000 ? i6 * 6 : i6 * 4;
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        int i2 = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i3 = CHANNEL_COUNT_BY_ACMOD[(unsignedByte & 56) >> 3];
        if ((unsignedByte & 4) != 0) {
            i3++;
        }
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.AUDIO_AC3).setChannelCount(i3).setSampleRate(i2).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseAc3SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        if (((byteBuffer.get(byteBuffer.position() + 5) & 248) >> 3) > 10) {
            return BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[((byteBuffer.get(byteBuffer.position() + 4) & 192) >> 6) != 3 ? (byteBuffer.get(byteBuffer.position() + 4) & TarConstants.LF_NORMAL) >> 4 : 3] * 256;
        }
        return 1536;
    }

    public static SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray parsableBitArray) {
        int ac3SyncframeSize;
        int i2;
        int i3;
        int i4;
        int i5;
        String str;
        int bits;
        int i6;
        int i7;
        int i8;
        int i9;
        int position = parsableBitArray.getPosition();
        parsableBitArray.skipBits(40);
        boolean z2 = parsableBitArray.readBits(5) > 10;
        parsableBitArray.setPosition(position);
        int i10 = -1;
        if (z2) {
            parsableBitArray.skipBits(16);
            int bits2 = parsableBitArray.readBits(2);
            if (bits2 == 0) {
                i10 = 0;
            } else if (bits2 == 1) {
                i10 = 1;
            } else if (bits2 == 2) {
                i10 = 2;
            }
            parsableBitArray.skipBits(3);
            ac3SyncframeSize = (parsableBitArray.readBits(11) + 1) * 2;
            int bits3 = parsableBitArray.readBits(2);
            if (bits3 == 3) {
                i2 = SAMPLE_RATE_BY_FSCOD2[parsableBitArray.readBits(2)];
                i6 = 6;
                bits = 3;
            } else {
                bits = parsableBitArray.readBits(2);
                i6 = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[bits];
                i2 = SAMPLE_RATE_BY_FSCOD[bits3];
            }
            i4 = i6 * 256;
            int bits4 = parsableBitArray.readBits(3);
            boolean bit = parsableBitArray.readBit();
            i3 = CHANNEL_COUNT_BY_ACMOD[bits4] + (bit ? 1 : 0);
            parsableBitArray.skipBits(10);
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(8);
            }
            if (bits4 == 0) {
                parsableBitArray.skipBits(5);
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
            }
            if (i10 == 1 && parsableBitArray.readBit()) {
                parsableBitArray.skipBits(16);
            }
            if (parsableBitArray.readBit()) {
                if (bits4 > 2) {
                    parsableBitArray.skipBits(2);
                }
                if ((bits4 & 1) == 0 || bits4 <= 2) {
                    i8 = 6;
                } else {
                    i8 = 6;
                    parsableBitArray.skipBits(6);
                }
                if ((bits4 & 4) != 0) {
                    parsableBitArray.skipBits(i8);
                }
                if (bit && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(5);
                }
                if (i10 == 0) {
                    if (parsableBitArray.readBit()) {
                        i9 = 6;
                        parsableBitArray.skipBits(6);
                    } else {
                        i9 = 6;
                    }
                    if (bits4 == 0 && parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(i9);
                    }
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(i9);
                    }
                    int bits5 = parsableBitArray.readBits(2);
                    if (bits5 == 1) {
                        parsableBitArray.skipBits(5);
                    } else if (bits5 == 2) {
                        parsableBitArray.skipBits(12);
                    } else if (bits5 == 3) {
                        int bits6 = parsableBitArray.readBits(5);
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (parsableBitArray.readBit()) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                            }
                        }
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (parsableBitArray.readBit()) {
                                parsableBitArray.skipBits(7);
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(8);
                                }
                            }
                        }
                        parsableBitArray.skipBits((bits6 + 2) * 8);
                        parsableBitArray.byteAlign();
                    }
                    if (bits4 < 2) {
                        if (parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                        if (bits4 == 0 && parsableBitArray.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                    }
                    if (parsableBitArray.readBit()) {
                        if (bits == 0) {
                            parsableBitArray.skipBits(5);
                        } else {
                            for (int i11 = 0; i11 < i6; i11++) {
                                if (parsableBitArray.readBit()) {
                                    parsableBitArray.skipBits(5);
                                }
                            }
                        }
                    }
                }
            }
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(5);
                if (bits4 == 2) {
                    parsableBitArray.skipBits(4);
                }
                if (bits4 >= 6) {
                    parsableBitArray.skipBits(2);
                }
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                if (bits4 == 0 && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(8);
                }
                if (bits3 < 3) {
                    parsableBitArray.skipBit();
                }
            }
            if (i10 == 0 && bits != 3) {
                parsableBitArray.skipBit();
            }
            if (i10 == 2 && (bits == 3 || parsableBitArray.readBit())) {
                i7 = 6;
                parsableBitArray.skipBits(6);
            } else {
                i7 = 6;
            }
            str = (parsableBitArray.readBit() && parsableBitArray.readBits(i7) == 1 && parsableBitArray.readBits(8) == 1) ? MimeTypes.AUDIO_E_AC3_JOC : MimeTypes.AUDIO_E_AC3;
            i5 = i10;
        } else {
            parsableBitArray.skipBits(32);
            int bits7 = parsableBitArray.readBits(2);
            String str2 = bits7 == 3 ? null : MimeTypes.AUDIO_AC3;
            ac3SyncframeSize = getAc3SyncframeSize(bits7, parsableBitArray.readBits(6));
            parsableBitArray.skipBits(8);
            int bits8 = parsableBitArray.readBits(3);
            if ((bits8 & 1) != 0 && bits8 != 1) {
                parsableBitArray.skipBits(2);
            }
            if ((bits8 & 4) != 0) {
                parsableBitArray.skipBits(2);
            }
            if (bits8 == 2) {
                parsableBitArray.skipBits(2);
            }
            int[] iArr = SAMPLE_RATE_BY_FSCOD;
            i2 = bits7 < iArr.length ? iArr[bits7] : -1;
            i3 = CHANNEL_COUNT_BY_ACMOD[bits8] + (parsableBitArray.readBit() ? 1 : 0);
            i4 = 1536;
            i5 = -1;
            str = str2;
        }
        return new SyncFrameInfo(str, i5, i3, i2, ac3SyncframeSize, i4);
    }

    public static int parseAc3SyncframeSize(byte[] bArr) {
        if (bArr.length < 6) {
            return -1;
        }
        if (((bArr[5] & 248) >> 3) > 10) {
            return (((bArr[3] & 255) | ((bArr[2] & 7) << 8)) + 1) * 2;
        }
        byte b3 = bArr[4];
        return getAc3SyncframeSize((b3 & 192) >> 6, b3 & Utf8.REPLACEMENT_BYTE);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        parsableByteArray.skipBytes(2);
        int i2 = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i3 = CHANNEL_COUNT_BY_ACMOD[(unsignedByte & 14) >> 1];
        if ((unsignedByte & 1) != 0) {
            i3++;
        }
        if (((parsableByteArray.readUnsignedByte() & 30) >> 1) > 0 && (2 & parsableByteArray.readUnsignedByte()) != 0) {
            i3 += 2;
        }
        return new Format.Builder().setId(str).setSampleMimeType((parsableByteArray.bytesLeft() <= 0 || (parsableByteArray.readUnsignedByte() & 1) == 0) ? MimeTypes.AUDIO_E_AC3 : MimeTypes.AUDIO_E_AC3_JOC).setChannelCount(i3).setSampleRate(i2).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] bArr) {
        if (bArr[4] == -8 && bArr[5] == 114 && bArr[6] == 111) {
            byte b3 = bArr[7];
            if ((b3 & PLVSoftView.KEYBOARD_STATE_HIDE) == 186) {
                return 40 << ((bArr[(b3 & 255) == 187 ? '\t' : '\b'] >> 4) & 7);
            }
        }
        return 0;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer byteBuffer, int i2) {
        return 40 << ((byteBuffer.get((byteBuffer.position() + i2) + ((byteBuffer.get((byteBuffer.position() + i2) + 7) & 255) == 187 ? 9 : 8)) >> 4) & 7);
    }
}
