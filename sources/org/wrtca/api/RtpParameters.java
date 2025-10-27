package org.wrtca.api;

import java.util.ArrayList;
import java.util.List;
import org.wrtca.api.MediaStreamTrack;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class RtpParameters {
    public final List<Codec> codecs;
    public final List<Encoding> encodings;

    public static class Codec {
        public Integer clockRate;
        public MediaStreamTrack.MediaType kind;
        public String name;
        public Integer numChannels;
        public int payloadType;

        @CalledByNative("Codec")
        public Codec(int i2, String str, MediaStreamTrack.MediaType mediaType, Integer num, Integer num2) {
            this.payloadType = i2;
            this.name = str;
            this.kind = mediaType;
            this.clockRate = num;
            this.numChannels = num2;
        }

        @CalledByNative("Codec")
        public Integer getClockRate() {
            return this.clockRate;
        }

        @CalledByNative("Codec")
        public MediaStreamTrack.MediaType getKind() {
            return this.kind;
        }

        @CalledByNative("Codec")
        public String getName() {
            return this.name;
        }

        @CalledByNative("Codec")
        public Integer getNumChannels() {
            return this.numChannels;
        }

        @CalledByNative("Codec")
        public int getPayloadType() {
            return this.payloadType;
        }
    }

    public static class Encoding {
        public boolean active;
        public Integer maxBitrateBps;
        public Long ssrc;

        @CalledByNative("Encoding")
        public Encoding(boolean z2, Integer num, Long l2) {
            this.active = z2;
            this.maxBitrateBps = num;
            this.ssrc = l2;
        }

        @CalledByNative("Encoding")
        public boolean getActive() {
            return this.active;
        }

        @CalledByNative("Encoding")
        public Integer getMaxBitrateBps() {
            return this.maxBitrateBps;
        }

        @CalledByNative("Encoding")
        public Long getSsrc() {
            return this.ssrc;
        }
    }

    public RtpParameters() {
        this.encodings = new ArrayList();
        this.codecs = new ArrayList();
    }

    @CalledByNative
    public List<Codec> getCodecs() {
        return this.codecs;
    }

    @CalledByNative
    public List<Encoding> getEncodings() {
        return this.encodings;
    }

    @CalledByNative
    public RtpParameters(List<Encoding> list, List<Codec> list2) {
        this.encodings = list;
        this.codecs = list2;
    }
}
