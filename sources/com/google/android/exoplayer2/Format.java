package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Joiner;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class Format implements Bundleable {
    private static final int FIELD_ACCESSIBILITY_CHANNEL = 28;
    private static final int FIELD_AVERAGE_BITRATE = 5;
    private static final int FIELD_CHANNEL_COUNT = 23;
    private static final int FIELD_CODECS = 7;
    private static final int FIELD_COLOR_INFO = 22;
    private static final int FIELD_CONTAINER_MIME_TYPE = 9;
    private static final int FIELD_CRYPTO_TYPE = 29;
    private static final int FIELD_DRM_INIT_DATA = 13;
    private static final int FIELD_ENCODER_DELAY = 26;
    private static final int FIELD_ENCODER_PADDING = 27;
    private static final int FIELD_FRAME_RATE = 17;
    private static final int FIELD_HEIGHT = 16;
    private static final int FIELD_ID = 0;
    private static final int FIELD_INITIALIZATION_DATA = 12;
    private static final int FIELD_LABEL = 1;
    private static final int FIELD_LANGUAGE = 2;
    private static final int FIELD_MAX_INPUT_SIZE = 11;
    private static final int FIELD_METADATA = 8;
    private static final int FIELD_PCM_ENCODING = 25;
    private static final int FIELD_PEAK_BITRATE = 6;
    private static final int FIELD_PIXEL_WIDTH_HEIGHT_RATIO = 19;
    private static final int FIELD_PROJECTION_DATA = 20;
    private static final int FIELD_ROLE_FLAGS = 4;
    private static final int FIELD_ROTATION_DEGREES = 18;
    private static final int FIELD_SAMPLE_MIME_TYPE = 10;
    private static final int FIELD_SAMPLE_RATE = 24;
    private static final int FIELD_SELECTION_FLAGS = 3;
    private static final int FIELD_STEREO_MODE = 21;
    private static final int FIELD_SUBSAMPLE_OFFSET_US = 14;
    private static final int FIELD_WIDTH = 15;
    public static final int NO_VALUE = -1;
    public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;
    public final int accessibilityChannel;
    public final int averageBitrate;
    public final int bitrate;
    public final int channelCount;

    @Nullable
    public final String codecs;

    @Nullable
    public final ColorInfo colorInfo;

    @Nullable
    public final String containerMimeType;
    public final int cryptoType;

    @Nullable
    public final DrmInitData drmInitData;
    public final int encoderDelay;
    public final int encoderPadding;
    public final float frameRate;
    private int hashCode;
    public final int height;

    @Nullable
    public final String id;
    public final List<byte[]> initializationData;

    @Nullable
    public final String label;

    @Nullable
    public final String language;
    public final int maxInputSize;

    @Nullable
    public final Metadata metadata;
    public final int pcmEncoding;
    public final int peakBitrate;
    public final float pixelWidthHeightRatio;

    @Nullable
    public final byte[] projectionData;
    public final int roleFlags;
    public final int rotationDegrees;

    @Nullable
    public final String sampleMimeType;
    public final int sampleRate;
    public final int selectionFlags;
    public final int stereoMode;
    public final long subsampleOffsetUs;
    public final int width;
    private static final Format DEFAULT = new Builder().build();
    public static final Bundleable.Creator<Format> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.k1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return Format.fromBundle(bundle);
        }
    };

    public static final class Builder {
        private int accessibilityChannel;
        private int averageBitrate;
        private int channelCount;

        @Nullable
        private String codecs;

        @Nullable
        private ColorInfo colorInfo;

        @Nullable
        private String containerMimeType;
        private int cryptoType;

        @Nullable
        private DrmInitData drmInitData;
        private int encoderDelay;
        private int encoderPadding;
        private float frameRate;
        private int height;

        @Nullable
        private String id;

        @Nullable
        private List<byte[]> initializationData;

        @Nullable
        private String label;

        @Nullable
        private String language;
        private int maxInputSize;

        @Nullable
        private Metadata metadata;
        private int pcmEncoding;
        private int peakBitrate;
        private float pixelWidthHeightRatio;

        @Nullable
        private byte[] projectionData;
        private int roleFlags;
        private int rotationDegrees;

        @Nullable
        private String sampleMimeType;
        private int sampleRate;
        private int selectionFlags;
        private int stereoMode;
        private long subsampleOffsetUs;
        private int width;

        public Format build() {
            return new Format(this);
        }

        public Builder setAccessibilityChannel(int i2) {
            this.accessibilityChannel = i2;
            return this;
        }

        public Builder setAverageBitrate(int i2) {
            this.averageBitrate = i2;
            return this;
        }

        public Builder setChannelCount(int i2) {
            this.channelCount = i2;
            return this;
        }

        public Builder setCodecs(@Nullable String str) {
            this.codecs = str;
            return this;
        }

        public Builder setColorInfo(@Nullable ColorInfo colorInfo) {
            this.colorInfo = colorInfo;
            return this;
        }

        public Builder setContainerMimeType(@Nullable String str) {
            this.containerMimeType = str;
            return this;
        }

        public Builder setCryptoType(int i2) {
            this.cryptoType = i2;
            return this;
        }

        public Builder setDrmInitData(@Nullable DrmInitData drmInitData) {
            this.drmInitData = drmInitData;
            return this;
        }

        public Builder setEncoderDelay(int i2) {
            this.encoderDelay = i2;
            return this;
        }

        public Builder setEncoderPadding(int i2) {
            this.encoderPadding = i2;
            return this;
        }

        public Builder setFrameRate(float f2) {
            this.frameRate = f2;
            return this;
        }

        public Builder setHeight(int i2) {
            this.height = i2;
            return this;
        }

        public Builder setId(@Nullable String str) {
            this.id = str;
            return this;
        }

        public Builder setInitializationData(@Nullable List<byte[]> list) {
            this.initializationData = list;
            return this;
        }

        public Builder setLabel(@Nullable String str) {
            this.label = str;
            return this;
        }

        public Builder setLanguage(@Nullable String str) {
            this.language = str;
            return this;
        }

        public Builder setMaxInputSize(int i2) {
            this.maxInputSize = i2;
            return this;
        }

        public Builder setMetadata(@Nullable Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public Builder setPcmEncoding(int i2) {
            this.pcmEncoding = i2;
            return this;
        }

        public Builder setPeakBitrate(int i2) {
            this.peakBitrate = i2;
            return this;
        }

        public Builder setPixelWidthHeightRatio(float f2) {
            this.pixelWidthHeightRatio = f2;
            return this;
        }

        public Builder setProjectionData(@Nullable byte[] bArr) {
            this.projectionData = bArr;
            return this;
        }

        public Builder setRoleFlags(int i2) {
            this.roleFlags = i2;
            return this;
        }

        public Builder setRotationDegrees(int i2) {
            this.rotationDegrees = i2;
            return this;
        }

        public Builder setSampleMimeType(@Nullable String str) {
            this.sampleMimeType = str;
            return this;
        }

        public Builder setSampleRate(int i2) {
            this.sampleRate = i2;
            return this;
        }

        public Builder setSelectionFlags(int i2) {
            this.selectionFlags = i2;
            return this;
        }

        public Builder setStereoMode(int i2) {
            this.stereoMode = i2;
            return this;
        }

        public Builder setSubsampleOffsetUs(long j2) {
            this.subsampleOffsetUs = j2;
            return this;
        }

        public Builder setWidth(int i2) {
            this.width = i2;
            return this;
        }

        public Builder() {
            this.averageBitrate = -1;
            this.peakBitrate = -1;
            this.maxInputSize = -1;
            this.subsampleOffsetUs = Long.MAX_VALUE;
            this.width = -1;
            this.height = -1;
            this.frameRate = -1.0f;
            this.pixelWidthHeightRatio = 1.0f;
            this.stereoMode = -1;
            this.channelCount = -1;
            this.sampleRate = -1;
            this.pcmEncoding = -1;
            this.accessibilityChannel = -1;
            this.cryptoType = 0;
        }

        public Builder setId(int i2) {
            this.id = Integer.toString(i2);
            return this;
        }

        private Builder(Format format) {
            this.id = format.id;
            this.label = format.label;
            this.language = format.language;
            this.selectionFlags = format.selectionFlags;
            this.roleFlags = format.roleFlags;
            this.averageBitrate = format.averageBitrate;
            this.peakBitrate = format.peakBitrate;
            this.codecs = format.codecs;
            this.metadata = format.metadata;
            this.containerMimeType = format.containerMimeType;
            this.sampleMimeType = format.sampleMimeType;
            this.maxInputSize = format.maxInputSize;
            this.initializationData = format.initializationData;
            this.drmInitData = format.drmInitData;
            this.subsampleOffsetUs = format.subsampleOffsetUs;
            this.width = format.width;
            this.height = format.height;
            this.frameRate = format.frameRate;
            this.rotationDegrees = format.rotationDegrees;
            this.pixelWidthHeightRatio = format.pixelWidthHeightRatio;
            this.projectionData = format.projectionData;
            this.stereoMode = format.stereoMode;
            this.colorInfo = format.colorInfo;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.pcmEncoding = format.pcmEncoding;
            this.encoderDelay = format.encoderDelay;
            this.encoderPadding = format.encoderPadding;
            this.accessibilityChannel = format.accessibilityChannel;
            this.cryptoType = format.cryptoType;
        }
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i6, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i6).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i4).setSampleRate(i5).build();
    }

    @Deprecated
    public static Format createContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, int i3, int i4, @Nullable String str6) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i3).setRoleFlags(i4).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setContainerMimeType(str3).setSampleMimeType(str4).build();
    }

    @Deprecated
    public static Format createSampleFormat(@Nullable String str, @Nullable String str2) {
        return new Builder().setId(str).setSampleMimeType(str2).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, float f2, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i4).setHeight(i5).setFrameRate(f2).build();
    }

    @Nullable
    private static <T> T defaultIfNull(@Nullable T t2, @Nullable T t3) {
        return t2 != null ? t2 : t3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Format fromBundle(Bundle bundle) {
        Builder builder = new Builder();
        BundleableUtil.ensureClassLoader(bundle);
        int i2 = 0;
        String string = bundle.getString(keyForField(0));
        Format format = DEFAULT;
        builder.setId((String) defaultIfNull(string, format.id)).setLabel((String) defaultIfNull(bundle.getString(keyForField(1)), format.label)).setLanguage((String) defaultIfNull(bundle.getString(keyForField(2)), format.language)).setSelectionFlags(bundle.getInt(keyForField(3), format.selectionFlags)).setRoleFlags(bundle.getInt(keyForField(4), format.roleFlags)).setAverageBitrate(bundle.getInt(keyForField(5), format.averageBitrate)).setPeakBitrate(bundle.getInt(keyForField(6), format.peakBitrate)).setCodecs((String) defaultIfNull(bundle.getString(keyForField(7)), format.codecs)).setMetadata((Metadata) defaultIfNull((Metadata) bundle.getParcelable(keyForField(8)), format.metadata)).setContainerMimeType((String) defaultIfNull(bundle.getString(keyForField(9)), format.containerMimeType)).setSampleMimeType((String) defaultIfNull(bundle.getString(keyForField(10)), format.sampleMimeType)).setMaxInputSize(bundle.getInt(keyForField(11), format.maxInputSize));
        ArrayList arrayList = new ArrayList();
        while (true) {
            byte[] byteArray = bundle.getByteArray(keyForInitializationData(i2));
            if (byteArray == null) {
                Builder drmInitData = builder.setInitializationData(arrayList).setDrmInitData((DrmInitData) bundle.getParcelable(keyForField(13)));
                String strKeyForField = keyForField(14);
                Format format2 = DEFAULT;
                drmInitData.setSubsampleOffsetUs(bundle.getLong(strKeyForField, format2.subsampleOffsetUs)).setWidth(bundle.getInt(keyForField(15), format2.width)).setHeight(bundle.getInt(keyForField(16), format2.height)).setFrameRate(bundle.getFloat(keyForField(17), format2.frameRate)).setRotationDegrees(bundle.getInt(keyForField(18), format2.rotationDegrees)).setPixelWidthHeightRatio(bundle.getFloat(keyForField(19), format2.pixelWidthHeightRatio)).setProjectionData(bundle.getByteArray(keyForField(20))).setStereoMode(bundle.getInt(keyForField(21), format2.stereoMode)).setColorInfo((ColorInfo) BundleableUtil.fromNullableBundle(ColorInfo.CREATOR, bundle.getBundle(keyForField(22)))).setChannelCount(bundle.getInt(keyForField(23), format2.channelCount)).setSampleRate(bundle.getInt(keyForField(24), format2.sampleRate)).setPcmEncoding(bundle.getInt(keyForField(25), format2.pcmEncoding)).setEncoderDelay(bundle.getInt(keyForField(26), format2.encoderDelay)).setEncoderPadding(bundle.getInt(keyForField(27), format2.encoderPadding)).setAccessibilityChannel(bundle.getInt(keyForField(28), format2.accessibilityChannel)).setCryptoType(bundle.getInt(keyForField(29), format2.cryptoType));
                return builder.build();
            }
            arrayList.add(byteArray);
            i2++;
        }
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    private static String keyForInitializationData(int i2) {
        String strKeyForField = keyForField(12);
        String string = Integer.toString(i2, 36);
        StringBuilder sb = new StringBuilder(String.valueOf(strKeyForField).length() + 1 + String.valueOf(string).length());
        sb.append(strKeyForField);
        sb.append(StrPool.UNDERLINE);
        sb.append(string);
        return sb.toString();
    }

    public static String toLogString(@Nullable Format format) {
        if (format == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("id=");
        sb.append(format.id);
        sb.append(", mimeType=");
        sb.append(format.sampleMimeType);
        if (format.bitrate != -1) {
            sb.append(", bitrate=");
            sb.append(format.bitrate);
        }
        if (format.codecs != null) {
            sb.append(", codecs=");
            sb.append(format.codecs);
        }
        if (format.drmInitData != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int i2 = 0;
            while (true) {
                DrmInitData drmInitData = format.drmInitData;
                if (i2 >= drmInitData.schemeDataCount) {
                    break;
                }
                UUID uuid = drmInitData.get(i2).uuid;
                if (uuid.equals(C.COMMON_PSSH_UUID)) {
                    linkedHashSet.add(C.CENC_TYPE_cenc);
                } else if (uuid.equals(C.CLEARKEY_UUID)) {
                    linkedHashSet.add("clearkey");
                } else if (uuid.equals(C.PLAYREADY_UUID)) {
                    linkedHashSet.add("playready");
                } else if (uuid.equals(C.WIDEVINE_UUID)) {
                    linkedHashSet.add("widevine");
                } else if (uuid.equals(C.UUID_NIL)) {
                    linkedHashSet.add("universal");
                } else {
                    String strValueOf = String.valueOf(uuid);
                    StringBuilder sb2 = new StringBuilder(strValueOf.length() + 10);
                    sb2.append("unknown (");
                    sb2.append(strValueOf);
                    sb2.append(")");
                    linkedHashSet.add(sb2.toString());
                }
                i2++;
            }
            sb.append(", drm=[");
            sb.append(Joiner.on(',').join(linkedHashSet));
            sb.append(']');
        }
        if (format.width != -1 && format.height != -1) {
            sb.append(", res=");
            sb.append(format.width);
            sb.append("x");
            sb.append(format.height);
        }
        if (format.frameRate != -1.0f) {
            sb.append(", fps=");
            sb.append(format.frameRate);
        }
        if (format.channelCount != -1) {
            sb.append(", channels=");
            sb.append(format.channelCount);
        }
        if (format.sampleRate != -1) {
            sb.append(", sample_rate=");
            sb.append(format.sampleRate);
        }
        if (format.language != null) {
            sb.append(", language=");
            sb.append(format.language);
        }
        if (format.label != null) {
            sb.append(", label=");
            sb.append(format.label);
        }
        if ((format.roleFlags & 16384) != 0) {
            sb.append(", trick-play-track");
        }
        return sb.toString();
    }

    public Builder buildUpon() {
        return new Builder();
    }

    @Deprecated
    public Format copyWithBitrate(int i2) {
        return buildUpon().setAverageBitrate(i2).setPeakBitrate(i2).build();
    }

    public Format copyWithCryptoType(int i2) {
        return buildUpon().setCryptoType(i2).build();
    }

    @Deprecated
    public Format copyWithDrmInitData(@Nullable DrmInitData drmInitData) {
        return buildUpon().setDrmInitData(drmInitData).build();
    }

    @Deprecated
    public Format copyWithFrameRate(float f2) {
        return buildUpon().setFrameRate(f2).build();
    }

    @Deprecated
    public Format copyWithGaplessInfo(int i2, int i3) {
        return buildUpon().setEncoderDelay(i2).setEncoderPadding(i3).build();
    }

    @Deprecated
    public Format copyWithLabel(@Nullable String str) {
        return buildUpon().setLabel(str).build();
    }

    @Deprecated
    public Format copyWithManifestFormatInfo(Format format) {
        return withManifestFormatInfo(format);
    }

    @Deprecated
    public Format copyWithMaxInputSize(int i2) {
        return buildUpon().setMaxInputSize(i2).build();
    }

    @Deprecated
    public Format copyWithMetadata(@Nullable Metadata metadata) {
        return buildUpon().setMetadata(metadata).build();
    }

    @Deprecated
    public Format copyWithSubsampleOffsetUs(long j2) {
        return buildUpon().setSubsampleOffsetUs(j2).build();
    }

    @Deprecated
    public Format copyWithVideoSize(int i2, int i3) {
        return buildUpon().setWidth(i2).setHeight(i3).build();
    }

    public boolean equals(@Nullable Object obj) {
        int i2;
        if (this == obj) {
            return true;
        }
        if (obj == null || Format.class != obj.getClass()) {
            return false;
        }
        Format format = (Format) obj;
        int i3 = this.hashCode;
        if (i3 == 0 || (i2 = format.hashCode) == 0 || i3 == i2) {
            return this.selectionFlags == format.selectionFlags && this.roleFlags == format.roleFlags && this.averageBitrate == format.averageBitrate && this.peakBitrate == format.peakBitrate && this.maxInputSize == format.maxInputSize && this.subsampleOffsetUs == format.subsampleOffsetUs && this.width == format.width && this.height == format.height && this.rotationDegrees == format.rotationDegrees && this.stereoMode == format.stereoMode && this.channelCount == format.channelCount && this.sampleRate == format.sampleRate && this.pcmEncoding == format.pcmEncoding && this.encoderDelay == format.encoderDelay && this.encoderPadding == format.encoderPadding && this.accessibilityChannel == format.accessibilityChannel && this.cryptoType == format.cryptoType && Float.compare(this.frameRate, format.frameRate) == 0 && Float.compare(this.pixelWidthHeightRatio, format.pixelWidthHeightRatio) == 0 && Util.areEqual(this.id, format.id) && Util.areEqual(this.label, format.label) && Util.areEqual(this.codecs, format.codecs) && Util.areEqual(this.containerMimeType, format.containerMimeType) && Util.areEqual(this.sampleMimeType, format.sampleMimeType) && Util.areEqual(this.language, format.language) && Arrays.equals(this.projectionData, format.projectionData) && Util.areEqual(this.metadata, format.metadata) && Util.areEqual(this.colorInfo, format.colorInfo) && Util.areEqual(this.drmInitData, format.drmInitData) && initializationDataEquals(format);
        }
        return false;
    }

    public int getPixelCount() {
        int i2;
        int i3 = this.width;
        if (i3 == -1 || (i2 = this.height) == -1) {
            return -1;
        }
        return i3 * i2;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            String str = this.id;
            int iHashCode = (R2.attr.bl_checkable_gradient_endColor + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.label;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.language;
            int iHashCode3 = (((((((((iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31) + this.averageBitrate) * 31) + this.peakBitrate) * 31;
            String str4 = this.codecs;
            int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            Metadata metadata = this.metadata;
            int iHashCode5 = (iHashCode4 + (metadata == null ? 0 : metadata.hashCode())) * 31;
            String str5 = this.containerMimeType;
            int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
            String str6 = this.sampleMimeType;
            this.hashCode = ((((((((((((((((((((((((((((((iHashCode6 + (str6 != null ? str6.hashCode() : 0)) * 31) + this.maxInputSize) * 31) + ((int) this.subsampleOffsetUs)) * 31) + this.width) * 31) + this.height) * 31) + Float.floatToIntBits(this.frameRate)) * 31) + this.rotationDegrees) * 31) + Float.floatToIntBits(this.pixelWidthHeightRatio)) * 31) + this.stereoMode) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + this.pcmEncoding) * 31) + this.encoderDelay) * 31) + this.encoderPadding) * 31) + this.accessibilityChannel) * 31) + this.cryptoType;
        }
        return this.hashCode;
    }

    public boolean initializationDataEquals(Format format) {
        if (this.initializationData.size() != format.initializationData.size()) {
            return false;
        }
        for (int i2 = 0; i2 < this.initializationData.size(); i2++) {
            if (!Arrays.equals(this.initializationData.get(i2), format.initializationData.get(i2))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(keyForField(0), this.id);
        bundle.putString(keyForField(1), this.label);
        bundle.putString(keyForField(2), this.language);
        bundle.putInt(keyForField(3), this.selectionFlags);
        bundle.putInt(keyForField(4), this.roleFlags);
        bundle.putInt(keyForField(5), this.averageBitrate);
        bundle.putInt(keyForField(6), this.peakBitrate);
        bundle.putString(keyForField(7), this.codecs);
        bundle.putParcelable(keyForField(8), this.metadata);
        bundle.putString(keyForField(9), this.containerMimeType);
        bundle.putString(keyForField(10), this.sampleMimeType);
        bundle.putInt(keyForField(11), this.maxInputSize);
        for (int i2 = 0; i2 < this.initializationData.size(); i2++) {
            bundle.putByteArray(keyForInitializationData(i2), this.initializationData.get(i2));
        }
        bundle.putParcelable(keyForField(13), this.drmInitData);
        bundle.putLong(keyForField(14), this.subsampleOffsetUs);
        bundle.putInt(keyForField(15), this.width);
        bundle.putInt(keyForField(16), this.height);
        bundle.putFloat(keyForField(17), this.frameRate);
        bundle.putInt(keyForField(18), this.rotationDegrees);
        bundle.putFloat(keyForField(19), this.pixelWidthHeightRatio);
        bundle.putByteArray(keyForField(20), this.projectionData);
        bundle.putInt(keyForField(21), this.stereoMode);
        bundle.putBundle(keyForField(22), BundleableUtil.toNullableBundle(this.colorInfo));
        bundle.putInt(keyForField(23), this.channelCount);
        bundle.putInt(keyForField(24), this.sampleRate);
        bundle.putInt(keyForField(25), this.pcmEncoding);
        bundle.putInt(keyForField(26), this.encoderDelay);
        bundle.putInt(keyForField(27), this.encoderPadding);
        bundle.putInt(keyForField(28), this.accessibilityChannel);
        bundle.putInt(keyForField(29), this.cryptoType);
        return bundle;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.label;
        String str3 = this.containerMimeType;
        String str4 = this.sampleMimeType;
        String str5 = this.codecs;
        int i2 = this.bitrate;
        String str6 = this.language;
        int i3 = this.width;
        int i4 = this.height;
        float f2 = this.frameRate;
        int i5 = this.channelCount;
        int i6 = this.sampleRate;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 104 + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str6).length());
        sb.append("Format(");
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        sb.append(", ");
        sb.append(str3);
        sb.append(", ");
        sb.append(str4);
        sb.append(", ");
        sb.append(str5);
        sb.append(", ");
        sb.append(i2);
        sb.append(", ");
        sb.append(str6);
        sb.append(", [");
        sb.append(i3);
        sb.append(", ");
        sb.append(i4);
        sb.append(", ");
        sb.append(f2);
        sb.append("], [");
        sb.append(i5);
        sb.append(", ");
        sb.append(i6);
        sb.append("])");
        return sb.toString();
    }

    public Format withManifestFormatInfo(Format format) {
        String str;
        if (this == format) {
            return this;
        }
        int trackType = MimeTypes.getTrackType(this.sampleMimeType);
        String str2 = format.id;
        String str3 = format.label;
        if (str3 == null) {
            str3 = this.label;
        }
        String str4 = this.language;
        if ((trackType == 3 || trackType == 1) && (str = format.language) != null) {
            str4 = str;
        }
        int i2 = this.averageBitrate;
        if (i2 == -1) {
            i2 = format.averageBitrate;
        }
        int i3 = this.peakBitrate;
        if (i3 == -1) {
            i3 = format.peakBitrate;
        }
        String str5 = this.codecs;
        if (str5 == null) {
            String codecsOfType = Util.getCodecsOfType(format.codecs, trackType);
            if (Util.splitCodecs(codecsOfType).length == 1) {
                str5 = codecsOfType;
            }
        }
        Metadata metadata = this.metadata;
        Metadata metadataCopyWithAppendedEntriesFrom = metadata == null ? format.metadata : metadata.copyWithAppendedEntriesFrom(format.metadata);
        float f2 = this.frameRate;
        if (f2 == -1.0f && trackType == 2) {
            f2 = format.frameRate;
        }
        return buildUpon().setId(str2).setLabel(str3).setLanguage(str4).setSelectionFlags(this.selectionFlags | format.selectionFlags).setRoleFlags(this.roleFlags | format.roleFlags).setAverageBitrate(i2).setPeakBitrate(i3).setCodecs(str5).setMetadata(metadataCopyWithAppendedEntriesFrom).setDrmInitData(DrmInitData.createSessionCreationData(format.drmInitData, this.drmInitData)).setFrameRate(f2).build();
    }

    private Format(Builder builder) {
        this.id = builder.id;
        this.label = builder.label;
        this.language = Util.normalizeLanguageCode(builder.language);
        this.selectionFlags = builder.selectionFlags;
        this.roleFlags = builder.roleFlags;
        int i2 = builder.averageBitrate;
        this.averageBitrate = i2;
        int i3 = builder.peakBitrate;
        this.peakBitrate = i3;
        this.bitrate = i3 != -1 ? i3 : i2;
        this.codecs = builder.codecs;
        this.metadata = builder.metadata;
        this.containerMimeType = builder.containerMimeType;
        this.sampleMimeType = builder.sampleMimeType;
        this.maxInputSize = builder.maxInputSize;
        this.initializationData = builder.initializationData == null ? Collections.emptyList() : builder.initializationData;
        DrmInitData drmInitData = builder.drmInitData;
        this.drmInitData = drmInitData;
        this.subsampleOffsetUs = builder.subsampleOffsetUs;
        this.width = builder.width;
        this.height = builder.height;
        this.frameRate = builder.frameRate;
        this.rotationDegrees = builder.rotationDegrees == -1 ? 0 : builder.rotationDegrees;
        this.pixelWidthHeightRatio = builder.pixelWidthHeightRatio == -1.0f ? 1.0f : builder.pixelWidthHeightRatio;
        this.projectionData = builder.projectionData;
        this.stereoMode = builder.stereoMode;
        this.colorInfo = builder.colorInfo;
        this.channelCount = builder.channelCount;
        this.sampleRate = builder.sampleRate;
        this.pcmEncoding = builder.pcmEncoding;
        this.encoderDelay = builder.encoderDelay == -1 ? 0 : builder.encoderDelay;
        this.encoderPadding = builder.encoderPadding != -1 ? builder.encoderPadding : 0;
        this.accessibilityChannel = builder.accessibilityChannel;
        if (builder.cryptoType != 0 || drmInitData == null) {
            this.cryptoType = builder.cryptoType;
        } else {
            this.cryptoType = 1;
        }
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, float f2, @Nullable List<byte[]> list, int i6, float f3, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i4).setHeight(i5).setFrameRate(f2).setRotationDegrees(i6).setPixelWidthHeightRatio(f3).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, int i6, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i7, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i7).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i4).setSampleRate(i5).setPcmEncoding(i6).build();
    }
}
