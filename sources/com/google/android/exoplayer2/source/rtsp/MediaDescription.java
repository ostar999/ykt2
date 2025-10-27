package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
final class MediaDescription {
    public static final String MEDIA_TYPE_AUDIO = "audio";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String RTP_AVP_PROFILE = "RTP/AVP";
    public final ImmutableMap<String, String> attributes;
    public final int bitrate;

    @Nullable
    public final String connection;

    @Nullable
    public final String key;

    @Nullable
    public final String mediaTitle;
    public final String mediaType;
    public final int payloadType;
    public final int port;
    public final RtpMapAttribute rtpMapAttribute;
    public final String transportProtocol;

    public static final class Builder {
        private final HashMap<String, String> attributes = new HashMap<>();
        private int bitrate = -1;

        @Nullable
        private String connection;

        @Nullable
        private String key;

        @Nullable
        private String mediaTitle;
        private final String mediaType;
        private final int payloadType;
        private final int port;
        private final String transportProtocol;

        public Builder(String str, int i2, String str2, int i3) {
            this.mediaType = str;
            this.port = i2;
            this.transportProtocol = str2;
            this.payloadType = i3;
        }

        public Builder addAttribute(String str, String str2) {
            this.attributes.put(str, str2);
            return this;
        }

        public MediaDescription build() {
            try {
                Assertions.checkState(this.attributes.containsKey(SessionDescription.ATTR_RTPMAP));
                return new MediaDescription(this, ImmutableMap.copyOf((Map) this.attributes), RtpMapAttribute.parse((String) Util.castNonNull(this.attributes.get(SessionDescription.ATTR_RTPMAP))));
            } catch (ParserException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public Builder setBitrate(int i2) {
            this.bitrate = i2;
            return this;
        }

        public Builder setConnection(String str) {
            this.connection = str;
            return this;
        }

        public Builder setKey(String str) {
            this.key = str;
            return this;
        }

        public Builder setMediaTitle(String str) {
            this.mediaTitle = str;
            return this;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaType {
    }

    public static final class RtpMapAttribute {
        public final int clockRate;
        public final int encodingParameters;
        public final String mediaEncoding;
        public final int payloadType;

        private RtpMapAttribute(int i2, String str, int i3, int i4) {
            this.payloadType = i2;
            this.mediaEncoding = str;
            this.clockRate = i3;
            this.encodingParameters = i4;
        }

        public static RtpMapAttribute parse(String str) throws ParserException {
            String[] strArrSplitAtFirst = Util.splitAtFirst(str, " ");
            Assertions.checkArgument(strArrSplitAtFirst.length == 2);
            int i2 = RtspMessageUtil.parseInt(strArrSplitAtFirst[0]);
            String[] strArrSplit = Util.split(strArrSplitAtFirst[1].trim(), "/");
            Assertions.checkArgument(strArrSplit.length >= 2);
            return new RtpMapAttribute(i2, strArrSplit[0], RtspMessageUtil.parseInt(strArrSplit[1]), strArrSplit.length == 3 ? RtspMessageUtil.parseInt(strArrSplit[2]) : -1);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || RtpMapAttribute.class != obj.getClass()) {
                return false;
            }
            RtpMapAttribute rtpMapAttribute = (RtpMapAttribute) obj;
            return this.payloadType == rtpMapAttribute.payloadType && this.mediaEncoding.equals(rtpMapAttribute.mediaEncoding) && this.clockRate == rtpMapAttribute.clockRate && this.encodingParameters == rtpMapAttribute.encodingParameters;
        }

        public int hashCode() {
            return ((((((217 + this.payloadType) * 31) + this.mediaEncoding.hashCode()) * 31) + this.clockRate) * 31) + this.encodingParameters;
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MediaDescription.class != obj.getClass()) {
            return false;
        }
        MediaDescription mediaDescription = (MediaDescription) obj;
        return this.mediaType.equals(mediaDescription.mediaType) && this.port == mediaDescription.port && this.transportProtocol.equals(mediaDescription.transportProtocol) && this.payloadType == mediaDescription.payloadType && this.bitrate == mediaDescription.bitrate && this.attributes.equals(mediaDescription.attributes) && this.rtpMapAttribute.equals(mediaDescription.rtpMapAttribute) && Util.areEqual(this.mediaTitle, mediaDescription.mediaTitle) && Util.areEqual(this.connection, mediaDescription.connection) && Util.areEqual(this.key, mediaDescription.key);
    }

    public ImmutableMap<String, String> getFmtpParametersAsMap() {
        String str = this.attributes.get(SessionDescription.ATTR_FMTP);
        if (str == null) {
            return ImmutableMap.of();
        }
        String[] strArrSplitAtFirst = Util.splitAtFirst(str, " ");
        Assertions.checkArgument(strArrSplitAtFirst.length == 2, str);
        String[] strArrSplit = strArrSplitAtFirst[1].split(";\\s?", 0);
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (String str2 : strArrSplit) {
            String[] strArrSplitAtFirst2 = Util.splitAtFirst(str2, "=");
            builder.put(strArrSplitAtFirst2[0], strArrSplitAtFirst2[1]);
        }
        return builder.build();
    }

    public int hashCode() {
        int iHashCode = (((((((((((((217 + this.mediaType.hashCode()) * 31) + this.port) * 31) + this.transportProtocol.hashCode()) * 31) + this.payloadType) * 31) + this.bitrate) * 31) + this.attributes.hashCode()) * 31) + this.rtpMapAttribute.hashCode()) * 31;
        String str = this.mediaTitle;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.connection;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.key;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    private MediaDescription(Builder builder, ImmutableMap<String, String> immutableMap, RtpMapAttribute rtpMapAttribute) {
        this.mediaType = builder.mediaType;
        this.port = builder.port;
        this.transportProtocol = builder.transportProtocol;
        this.payloadType = builder.payloadType;
        this.mediaTitle = builder.mediaTitle;
        this.connection = builder.connection;
        this.bitrate = builder.bitrate;
        this.key = builder.key;
        this.attributes = immutableMap;
        this.rtpMapAttribute = rtpMapAttribute;
    }
}
