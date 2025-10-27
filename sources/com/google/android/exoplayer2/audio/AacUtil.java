package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.yikaobang.yixue.R2;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class AacUtil {
    public static final int AAC_ELD_MAX_RATE_BYTES_PER_SECOND = 8000;
    public static final int AAC_HE_AUDIO_SAMPLE_COUNT = 2048;
    public static final int AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND = 16000;
    public static final int AAC_HE_V2_MAX_RATE_BYTES_PER_SECOND = 7000;
    public static final int AAC_LC_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_LC_MAX_RATE_BYTES_PER_SECOND = 100000;
    public static final int AAC_LD_AUDIO_SAMPLE_COUNT = 512;
    public static final int AAC_XHE_AUDIO_SAMPLE_COUNT = 1024;
    public static final int AAC_XHE_MAX_RATE_BYTES_PER_SECOND = 256000;
    public static final int AUDIO_OBJECT_TYPE_AAC_ELD = 23;
    public static final int AUDIO_OBJECT_TYPE_AAC_ER_BSAC = 22;
    public static final int AUDIO_OBJECT_TYPE_AAC_LC = 2;
    public static final int AUDIO_OBJECT_TYPE_AAC_PS = 29;
    public static final int AUDIO_OBJECT_TYPE_AAC_SBR = 5;
    public static final int AUDIO_OBJECT_TYPE_AAC_XHE = 42;
    private static final int AUDIO_OBJECT_TYPE_ESCAPE = 31;
    private static final int AUDIO_SPECIFIC_CONFIG_CHANNEL_CONFIGURATION_INVALID = -1;
    private static final int AUDIO_SPECIFIC_CONFIG_FREQUENCY_INDEX_ARBITRARY = 15;
    private static final String CODECS_STRING_PREFIX = "mp4a.40.";
    private static final String TAG = "AacUtil";
    private static final int[] AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE = {96000, 88200, 64000, 48000, 44100, 32000, R2.string.load_cost, R2.layout.item_problem, 16000, R2.drawable.qrcodeimg, R2.drawable.jpush_ic_richpush_actionbar_divider, 8000, R2.dimen.dp_717};
    private static final int[] AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE = {0, 1, 2, 3, 4, 5, 6, 8, -1, -1, -1, 7, 8, -1, 8, -1};

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AacAudioObjectType {
    }

    public static final class Config {
        public final int channelCount;
        public final String codecs;
        public final int sampleRateHz;

        private Config(int i2, int i3, String str) {
            this.sampleRateHz = i2;
            this.channelCount = i3;
            this.codecs = str;
        }
    }

    private AacUtil() {
    }

    public static byte[] buildAacLcAudioSpecificConfig(int i2, int i3) {
        int i4 = 0;
        int i5 = -1;
        int i6 = 0;
        while (true) {
            int[] iArr = AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE;
            if (i6 >= iArr.length) {
                break;
            }
            if (i2 == iArr[i6]) {
                i5 = i6;
            }
            i6++;
        }
        int i7 = -1;
        while (true) {
            int[] iArr2 = AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE;
            if (i4 >= iArr2.length) {
                break;
            }
            if (i3 == iArr2[i4]) {
                i7 = i4;
            }
            i4++;
        }
        if (i2 != -1 && i7 != -1) {
            return buildAudioSpecificConfig(2, i5, i7);
        }
        StringBuilder sb = new StringBuilder(67);
        sb.append("Invalid sample rate or number of channels: ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        throw new IllegalArgumentException(sb.toString());
    }

    public static byte[] buildAudioSpecificConfig(int i2, int i3, int i4) {
        return new byte[]{(byte) (((i2 << 3) & R2.attr.actionModeFindDrawable) | ((i3 >> 1) & 7)), (byte) (((i3 << 7) & 128) | ((i4 << 3) & 120))};
    }

    private static int getAudioObjectType(ParsableBitArray parsableBitArray) {
        int bits = parsableBitArray.readBits(5);
        return bits == 31 ? parsableBitArray.readBits(6) + 32 : bits;
    }

    private static int getSamplingFrequency(ParsableBitArray parsableBitArray) throws ParserException {
        int bits = parsableBitArray.readBits(4);
        if (bits == 15) {
            return parsableBitArray.readBits(24);
        }
        if (bits < 13) {
            return AUDIO_SPECIFIC_CONFIG_SAMPLING_RATE_TABLE[bits];
        }
        throw ParserException.createForMalformedContainer(null, null);
    }

    public static Config parseAudioSpecificConfig(byte[] bArr) throws ParserException {
        return parseAudioSpecificConfig(new ParsableBitArray(bArr), false);
    }

    private static void parseGaSpecificConfig(ParsableBitArray parsableBitArray, int i2, int i3) {
        if (parsableBitArray.readBit()) {
            Log.w(TAG, "Unexpected frameLengthFlag = 1");
        }
        if (parsableBitArray.readBit()) {
            parsableBitArray.skipBits(14);
        }
        boolean bit = parsableBitArray.readBit();
        if (i3 == 0) {
            throw new UnsupportedOperationException();
        }
        if (i2 == 6 || i2 == 20) {
            parsableBitArray.skipBits(3);
        }
        if (bit) {
            if (i2 == 22) {
                parsableBitArray.skipBits(16);
            }
            if (i2 == 17 || i2 == 19 || i2 == 20 || i2 == 23) {
                parsableBitArray.skipBits(3);
            }
            parsableBitArray.skipBits(1);
        }
    }

    public static Config parseAudioSpecificConfig(ParsableBitArray parsableBitArray, boolean z2) throws ParserException {
        int audioObjectType = getAudioObjectType(parsableBitArray);
        int samplingFrequency = getSamplingFrequency(parsableBitArray);
        int bits = parsableBitArray.readBits(4);
        StringBuilder sb = new StringBuilder(19);
        sb.append(CODECS_STRING_PREFIX);
        sb.append(audioObjectType);
        String string = sb.toString();
        if (audioObjectType == 5 || audioObjectType == 29) {
            samplingFrequency = getSamplingFrequency(parsableBitArray);
            audioObjectType = getAudioObjectType(parsableBitArray);
            if (audioObjectType == 22) {
                bits = parsableBitArray.readBits(4);
            }
        }
        if (z2) {
            if (audioObjectType != 1 && audioObjectType != 2 && audioObjectType != 3 && audioObjectType != 4 && audioObjectType != 6 && audioObjectType != 7 && audioObjectType != 17) {
                switch (audioObjectType) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                        break;
                    default:
                        StringBuilder sb2 = new StringBuilder(42);
                        sb2.append("Unsupported audio object type: ");
                        sb2.append(audioObjectType);
                        throw ParserException.createForUnsupportedContainerFeature(sb2.toString());
                }
            }
            parseGaSpecificConfig(parsableBitArray, audioObjectType, bits);
            switch (audioObjectType) {
                case 17:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                    int bits2 = parsableBitArray.readBits(2);
                    if (bits2 == 2 || bits2 == 3) {
                        StringBuilder sb3 = new StringBuilder(33);
                        sb3.append("Unsupported epConfig: ");
                        sb3.append(bits2);
                        throw ParserException.createForUnsupportedContainerFeature(sb3.toString());
                    }
            }
        }
        int i2 = AUDIO_SPECIFIC_CONFIG_CHANNEL_COUNT_TABLE[bits];
        if (i2 != -1) {
            return new Config(samplingFrequency, i2, string);
        }
        throw ParserException.createForMalformedContainer(null, null);
    }
}
