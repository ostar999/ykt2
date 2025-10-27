package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
final class RtpH264Reader implements RtpPayloadReader {
    private static final int FU_PAYLOAD_OFFSET = 2;
    private static final long MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int NAL_UNIT_TYPE_IDR = 5;
    private static final int RTP_PACKET_TYPE_FU_A = 28;
    private static final int RTP_PACKET_TYPE_STAP_A = 24;
    private static final String TAG = "RtpH264Reader";
    private int bufferFlags;
    private int fragmentedSampleSizeBytes;
    private final RtpPayloadFormat payloadFormat;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private final ParsableByteArray nalStartCodeArray = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private final ParsableByteArray fuScratchBuffer = new ParsableByteArray();
    private long firstReceivedTimestamp = C.TIME_UNSET;
    private int previousSequenceNumber = -1;

    public RtpH264Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private static int getBufferFlagsFromNalType(int i2) {
        return i2 == 5 ? 1 : 0;
    }

    @RequiresNonNull({"trackOutput"})
    private void processFragmentationUnitPacket(ParsableByteArray parsableByteArray, int i2) {
        byte b3 = parsableByteArray.getData()[0];
        byte b4 = parsableByteArray.getData()[1];
        int i3 = (b3 & 224) | (b4 & Ascii.US);
        boolean z2 = (b4 & 128) > 0;
        boolean z3 = (b4 & SignedBytes.MAX_POWER_OF_TWO) > 0;
        if (z2) {
            this.fragmentedSampleSizeBytes += writeStartCode();
            parsableByteArray.getData()[1] = (byte) i3;
            this.fuScratchBuffer.reset(parsableByteArray.getData());
            this.fuScratchBuffer.setPosition(1);
        } else {
            int i4 = (this.previousSequenceNumber + 1) % 65535;
            if (i2 != i4) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(i4), Integer.valueOf(i2)));
                return;
            } else {
                this.fuScratchBuffer.reset(parsableByteArray.getData());
                this.fuScratchBuffer.setPosition(2);
            }
        }
        int iBytesLeft = this.fuScratchBuffer.bytesLeft();
        this.trackOutput.sampleData(this.fuScratchBuffer, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        if (z3) {
            this.bufferFlags = getBufferFlagsFromNalType(i3 & 31);
        }
    }

    @RequiresNonNull({"trackOutput"})
    private void processSingleNalUnitPacket(ParsableByteArray parsableByteArray) {
        int iBytesLeft = parsableByteArray.bytesLeft();
        this.fragmentedSampleSizeBytes += writeStartCode();
        this.trackOutput.sampleData(parsableByteArray, iBytesLeft);
        this.fragmentedSampleSizeBytes += iBytesLeft;
        this.bufferFlags = getBufferFlagsFromNalType(parsableByteArray.getData()[0] & Ascii.US);
    }

    @RequiresNonNull({"trackOutput"})
    private void processSingleTimeAggregationPacket(ParsableByteArray parsableByteArray) {
        parsableByteArray.readUnsignedByte();
        while (parsableByteArray.bytesLeft() > 4) {
            int unsignedShort = parsableByteArray.readUnsignedShort();
            this.fragmentedSampleSizeBytes += writeStartCode();
            this.trackOutput.sampleData(parsableByteArray, unsignedShort);
            this.fragmentedSampleSizeBytes += unsignedShort;
        }
        this.bufferFlags = 0;
    }

    private static long toSampleUs(long j2, long j3, long j4) {
        return j2 + Util.scaleLargeTimestamp(j3 - j4, 1000000L, MEDIA_CLOCK_FREQUENCY);
    }

    private int writeStartCode() {
        this.nalStartCodeArray.setPosition(0);
        int iBytesLeft = this.nalStartCodeArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(this.nalStartCodeArray, iBytesLeft);
        return iBytesLeft;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) throws ParserException {
        try {
            int i3 = parsableByteArray.getData()[0] & Ascii.US;
            Assertions.checkStateNotNull(this.trackOutput);
            if (i3 > 0 && i3 < 24) {
                processSingleNalUnitPacket(parsableByteArray);
            } else if (i3 == 24) {
                processSingleTimeAggregationPacket(parsableByteArray);
            } else {
                if (i3 != 28) {
                    throw ParserException.createForMalformedManifest(String.format("RTP H264 packetization mode [%d] not supported.", Integer.valueOf(i3)), null);
                }
                processFragmentationUnitPacket(parsableByteArray, i2);
            }
            if (z2) {
                if (this.firstReceivedTimestamp == C.TIME_UNSET) {
                    this.firstReceivedTimestamp = j2;
                }
                this.trackOutput.sampleMetadata(toSampleUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp), this.bufferFlags, this.fragmentedSampleSizeBytes, 0, null);
                this.fragmentedSampleSizeBytes = 0;
            }
            this.previousSequenceNumber = i2;
        } catch (IndexOutOfBoundsException e2) {
            throw ParserException.createForMalformedManifest(null, e2);
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 2);
        this.trackOutput = trackOutputTrack;
        ((TrackOutput) Util.castNonNull(trackOutputTrack)).format(this.payloadFormat.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.fragmentedSampleSizeBytes = 0;
        this.startTimeOffsetUs = j3;
    }
}
