package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.SignedBytes;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class DefaultTsPayloadReaderFactory implements TsPayloadReader.Factory {
    private static final int DESCRIPTOR_TAG_CAPTION_SERVICE = 134;
    public static final int FLAG_ALLOW_NON_IDR_KEYFRAMES = 1;
    public static final int FLAG_DETECT_ACCESS_UNITS = 8;
    public static final int FLAG_ENABLE_HDMV_DTS_AUDIO_STREAMS = 64;
    public static final int FLAG_IGNORE_AAC_STREAM = 2;
    public static final int FLAG_IGNORE_H264_STREAM = 4;
    public static final int FLAG_IGNORE_SPLICE_INFO_STREAM = 16;
    public static final int FLAG_OVERRIDE_CAPTION_DESCRIPTORS = 32;
    private final List<Format> closedCaptionFormats;
    private final int flags;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public DefaultTsPayloadReaderFactory() {
        this(0);
    }

    private SeiReader buildSeiReader(TsPayloadReader.EsInfo esInfo) {
        return new SeiReader(getClosedCaptionFormats(esInfo));
    }

    private UserDataReader buildUserDataReader(TsPayloadReader.EsInfo esInfo) {
        return new UserDataReader(getClosedCaptionFormats(esInfo));
    }

    private List<Format> getClosedCaptionFormats(TsPayloadReader.EsInfo esInfo) {
        String str;
        int i2;
        if (isSet(32)) {
            return this.closedCaptionFormats;
        }
        ParsableByteArray parsableByteArray = new ParsableByteArray(esInfo.descriptorBytes);
        List<Format> arrayList = this.closedCaptionFormats;
        while (parsableByteArray.bytesLeft() > 0) {
            int unsignedByte = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition() + parsableByteArray.readUnsignedByte();
            if (unsignedByte == 134) {
                arrayList = new ArrayList<>();
                int unsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
                for (int i3 = 0; i3 < unsignedByte2; i3++) {
                    String string = parsableByteArray.readString(3);
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    boolean z2 = (unsignedByte3 & 128) != 0;
                    if (z2) {
                        i2 = unsignedByte3 & 63;
                        str = MimeTypes.APPLICATION_CEA708;
                    } else {
                        str = MimeTypes.APPLICATION_CEA608;
                        i2 = 1;
                    }
                    byte unsignedByte4 = (byte) parsableByteArray.readUnsignedByte();
                    parsableByteArray.skipBytes(1);
                    arrayList.add(new Format.Builder().setSampleMimeType(str).setLanguage(string).setAccessibilityChannel(i2).setInitializationData(z2 ? CodecSpecificDataUtil.buildCea708InitializationData((unsignedByte4 & SignedBytes.MAX_POWER_OF_TWO) != 0) : null).build());
                }
            }
            parsableByteArray.setPosition(position);
        }
        return arrayList;
    }

    private boolean isSet(int i2) {
        return (i2 & this.flags) != 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    public SparseArray<TsPayloadReader> createInitialPayloadReaders() {
        return new SparseArray<>();
    }

    @Override // com.google.android.exoplayer2.extractor.ts.TsPayloadReader.Factory
    @Nullable
    public TsPayloadReader createPayloadReader(int i2, TsPayloadReader.EsInfo esInfo) {
        if (i2 != 2) {
            if (i2 == 3 || i2 == 4) {
                return new PesReader(new MpegAudioReader(esInfo.language));
            }
            if (i2 == 21) {
                return new PesReader(new Id3Reader());
            }
            if (i2 == 27) {
                if (isSet(4)) {
                    return null;
                }
                return new PesReader(new H264Reader(buildSeiReader(esInfo), isSet(1), isSet(8)));
            }
            if (i2 == 36) {
                return new PesReader(new H265Reader(buildSeiReader(esInfo)));
            }
            if (i2 == 89) {
                return new PesReader(new DvbSubtitleReader(esInfo.dvbSubtitleInfos));
            }
            if (i2 != 138) {
                if (i2 == 172) {
                    return new PesReader(new Ac4Reader(esInfo.language));
                }
                if (i2 == 257) {
                    return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_AIT));
                }
                if (i2 == 134) {
                    if (isSet(16)) {
                        return null;
                    }
                    return new SectionReader(new PassthroughSectionPayloadReader(MimeTypes.APPLICATION_SCTE35));
                }
                if (i2 != 135) {
                    switch (i2) {
                        case 15:
                            if (!isSet(2)) {
                                break;
                            }
                            break;
                        case 16:
                            break;
                        case 17:
                            if (!isSet(2)) {
                                break;
                            }
                            break;
                        default:
                            switch (i2) {
                                case 130:
                                    if (!isSet(64)) {
                                    }
                                    break;
                            }
                    }
                    return null;
                }
                return new PesReader(new Ac3Reader(esInfo.language));
            }
            return new PesReader(new DtsReader(esInfo.language));
        }
        return new PesReader(new H262Reader(buildUserDataReader(esInfo)));
    }

    public DefaultTsPayloadReaderFactory(int i2) {
        this(i2, ImmutableList.of());
    }

    public DefaultTsPayloadReaderFactory(int i2, List<Format> list) {
        this.flags = i2;
        this.closedCaptionFormats = list;
    }
}
