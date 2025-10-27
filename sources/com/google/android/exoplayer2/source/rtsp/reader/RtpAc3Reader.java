package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public final class RtpAc3Reader implements RtpPayloadReader {
    private static final int AC3_FRAME_TYPE_COMPLETE_FRAME = 0;
    private static final int AC3_FRAME_TYPE_INITIAL_FRAGMENT_A = 1;
    private static final int AC3_FRAME_TYPE_INITIAL_FRAGMENT_B = 2;
    private static final int AC3_FRAME_TYPE_NON_INITIAL_FRAGMENT = 3;
    private static final int AC3_PAYLOAD_HEADER_SIZE = 2;
    private int numBytesPendingMetadataOutput;
    private final RtpPayloadFormat payloadFormat;
    private long sampleTimeUsOfFramePendingMetadataOutput;
    private long startTimeOffsetUs;
    private TrackOutput trackOutput;
    private final ParsableBitArray scratchBitBuffer = new ParsableBitArray();
    private long firstReceivedTimestamp = C.TIME_UNSET;

    public RtpAc3Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    private void maybeOutputSampleMetadata() {
        if (this.numBytesPendingMetadataOutput > 0) {
            outputSampleMetadataForFragmentedPackets();
        }
    }

    private void outputSampleMetadataForFragmentedPackets() {
        ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(this.sampleTimeUsOfFramePendingMetadataOutput, 1, this.numBytesPendingMetadataOutput, 0, null);
        this.numBytesPendingMetadataOutput = 0;
    }

    private void processFragmentedPacket(ParsableByteArray parsableByteArray, boolean z2, int i2, long j2) {
        int iBytesLeft = parsableByteArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(parsableByteArray, iBytesLeft);
        this.numBytesPendingMetadataOutput += iBytesLeft;
        this.sampleTimeUsOfFramePendingMetadataOutput = j2;
        if (z2 && i2 == 3) {
            outputSampleMetadataForFragmentedPackets();
        }
    }

    private void processMultiFramePacket(ParsableByteArray parsableByteArray, int i2, long j2) {
        this.scratchBitBuffer.reset(parsableByteArray.getData());
        this.scratchBitBuffer.skipBytes(2);
        for (int i3 = 0; i3 < i2; i3++) {
            Ac3Util.SyncFrameInfo ac3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(this.scratchBitBuffer);
            ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(parsableByteArray, ac3SyncframeInfo.frameSize);
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(j2, 1, ac3SyncframeInfo.frameSize, 0, null);
            j2 += (ac3SyncframeInfo.sampleCount / ac3SyncframeInfo.sampleRate) * 1000000;
            this.scratchBitBuffer.skipBytes(ac3SyncframeInfo.frameSize);
        }
    }

    private void processSingleFramePacket(ParsableByteArray parsableByteArray, long j2) {
        int iBytesLeft = parsableByteArray.bytesLeft();
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleData(parsableByteArray, iBytesLeft);
        ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(j2, 1, iBytesLeft, 0, null);
    }

    private static long toSampleTimeUs(long j2, long j3, long j4, int i2) {
        return j2 + Util.scaleLargeTimestamp(j3 - j4, 1000000L, i2);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j2, int i2, boolean z2) {
        int unsignedByte = parsableByteArray.readUnsignedByte() & 3;
        int unsignedByte2 = parsableByteArray.readUnsignedByte() & 255;
        long sampleTimeUs = toSampleTimeUs(this.startTimeOffsetUs, j2, this.firstReceivedTimestamp, this.payloadFormat.clockRate);
        if (unsignedByte == 0) {
            maybeOutputSampleMetadata();
            if (unsignedByte2 == 1) {
                processSingleFramePacket(parsableByteArray, sampleTimeUs);
                return;
            } else {
                processMultiFramePacket(parsableByteArray, unsignedByte2, sampleTimeUs);
                return;
            }
        }
        if (unsignedByte == 1 || unsignedByte == 2) {
            maybeOutputSampleMetadata();
        } else if (unsignedByte != 3) {
            throw new IllegalArgumentException(String.valueOf(unsignedByte));
        }
        processFragmentedPacket(parsableByteArray, z2, unsignedByte, sampleTimeUs);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i2) {
        TrackOutput trackOutputTrack = extractorOutput.track(i2, 1);
        this.trackOutput = trackOutputTrack;
        trackOutputTrack.format(this.payloadFormat.format);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j2, int i2) {
        Assertions.checkState(this.firstReceivedTimestamp == C.TIME_UNSET);
        this.firstReceivedTimestamp = j2;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.reader.RtpPayloadReader
    public void seek(long j2, long j3) {
        this.firstReceivedTimestamp = j2;
        this.startTimeOffsetUs = j3;
    }
}
