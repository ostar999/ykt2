package com.google.android.exoplayer2.source.rtsp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class RtpPacket {
    public static final int CSRC_SIZE = 4;
    private static final byte[] EMPTY = new byte[0];
    public static final int MAX_SEQUENCE_NUMBER = 65535;
    public static final int MAX_SIZE = 65507;
    public static final int MIN_HEADER_SIZE = 12;
    public static final int MIN_SEQUENCE_NUMBER = 0;
    public static final int RTP_VERSION = 2;
    public final byte[] csrc;
    public final byte csrcCount;
    public final boolean extension;
    public final boolean marker;
    public final boolean padding;
    public final byte[] payloadData;
    public final byte payloadType;
    public final int sequenceNumber;
    public final int ssrc;
    public final long timestamp;
    public final byte version;

    public static final class Builder {
        private boolean marker;
        private boolean padding;
        private byte payloadType;
        private int sequenceNumber;
        private int ssrc;
        private long timestamp;
        private byte[] csrc = RtpPacket.EMPTY;
        private byte[] payloadData = RtpPacket.EMPTY;

        public RtpPacket build() {
            return new RtpPacket(this);
        }

        public Builder setCsrc(byte[] bArr) {
            Assertions.checkNotNull(bArr);
            this.csrc = bArr;
            return this;
        }

        public Builder setMarker(boolean z2) {
            this.marker = z2;
            return this;
        }

        public Builder setPadding(boolean z2) {
            this.padding = z2;
            return this;
        }

        public Builder setPayloadData(byte[] bArr) {
            Assertions.checkNotNull(bArr);
            this.payloadData = bArr;
            return this;
        }

        public Builder setPayloadType(byte b3) {
            this.payloadType = b3;
            return this;
        }

        public Builder setSequenceNumber(int i2) {
            Assertions.checkArgument(i2 >= 0 && i2 <= 65535);
            this.sequenceNumber = i2 & 65535;
            return this;
        }

        public Builder setSsrc(int i2) {
            this.ssrc = i2;
            return this;
        }

        public Builder setTimestamp(long j2) {
            this.timestamp = j2;
            return this;
        }
    }

    @Nullable
    public static RtpPacket parse(ParsableByteArray parsableByteArray) {
        byte[] bArr;
        if (parsableByteArray.bytesLeft() < 12) {
            return null;
        }
        int unsignedByte = parsableByteArray.readUnsignedByte();
        byte b3 = (byte) (unsignedByte >> 6);
        boolean z2 = ((unsignedByte >> 5) & 1) == 1;
        byte b4 = (byte) (unsignedByte & 15);
        if (b3 != 2) {
            return null;
        }
        int unsignedByte2 = parsableByteArray.readUnsignedByte();
        boolean z3 = ((unsignedByte2 >> 7) & 1) == 1;
        byte b5 = (byte) (unsignedByte2 & 127);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        long unsignedInt = parsableByteArray.readUnsignedInt();
        int i2 = parsableByteArray.readInt();
        if (b4 > 0) {
            bArr = new byte[b4 * 4];
            for (int i3 = 0; i3 < b4; i3++) {
                parsableByteArray.readBytes(bArr, i3 * 4, 4);
            }
        } else {
            bArr = EMPTY;
        }
        byte[] bArr2 = new byte[parsableByteArray.bytesLeft()];
        parsableByteArray.readBytes(bArr2, 0, parsableByteArray.bytesLeft());
        return new Builder().setPadding(z2).setMarker(z3).setPayloadType(b5).setSequenceNumber(unsignedShort).setTimestamp(unsignedInt).setSsrc(i2).setCsrc(bArr).setPayloadData(bArr2).build();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RtpPacket.class != obj.getClass()) {
            return false;
        }
        RtpPacket rtpPacket = (RtpPacket) obj;
        return this.payloadType == rtpPacket.payloadType && this.sequenceNumber == rtpPacket.sequenceNumber && this.marker == rtpPacket.marker && this.timestamp == rtpPacket.timestamp && this.ssrc == rtpPacket.ssrc;
    }

    public int hashCode() {
        int i2 = (((((R2.attr.bl_checkable_gradient_endColor + this.payloadType) * 31) + this.sequenceNumber) * 31) + (this.marker ? 1 : 0)) * 31;
        long j2 = this.timestamp;
        return ((i2 + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.ssrc;
    }

    public String toString() {
        return Util.formatInvariant("RtpPacket(payloadType=%d, seq=%d, timestamp=%d, ssrc=%x, marker=%b)", Byte.valueOf(this.payloadType), Integer.valueOf(this.sequenceNumber), Long.valueOf(this.timestamp), Integer.valueOf(this.ssrc), Boolean.valueOf(this.marker));
    }

    public int writeToBuffer(byte[] bArr, int i2, int i3) {
        int length = (this.csrcCount * 4) + 12 + this.payloadData.length;
        if (i3 < length || bArr.length - i2 < length) {
            return -1;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr, i2, i3);
        byte b3 = (byte) (((this.padding ? 1 : 0) << 5) | 128 | ((this.extension ? 1 : 0) << 4) | (this.csrcCount & 15));
        byteBufferWrap.put(b3).put((byte) (((this.marker ? 1 : 0) << 7) | (this.payloadType & 127))).putShort((short) this.sequenceNumber).putInt((int) this.timestamp).putInt(this.ssrc).put(this.csrc).put(this.payloadData);
        return length;
    }

    private RtpPacket(Builder builder) {
        this.version = (byte) 2;
        this.padding = builder.padding;
        this.extension = false;
        this.marker = builder.marker;
        this.payloadType = builder.payloadType;
        this.sequenceNumber = builder.sequenceNumber;
        this.timestamp = builder.timestamp;
        this.ssrc = builder.ssrc;
        byte[] bArr = builder.csrc;
        this.csrc = bArr;
        this.csrcCount = (byte) (bArr.length / 4);
        this.payloadData = builder.payloadData;
    }

    @Nullable
    public static RtpPacket parse(byte[] bArr, int i2) {
        return parse(new ParsableByteArray(bArr, i2));
    }
}
