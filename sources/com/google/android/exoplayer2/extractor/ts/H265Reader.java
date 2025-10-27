package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class H265Reader implements ElementaryStreamReader {
    private static final int AUD_NUT = 35;
    private static final int BLA_W_LP = 16;
    private static final int CRA_NUT = 21;
    private static final int PPS_NUT = 34;
    private static final int PREFIX_SEI_NUT = 39;
    private static final int RASL_R = 9;
    private static final int SPS_NUT = 33;
    private static final int SUFFIX_SEI_NUT = 40;
    private static final String TAG = "H265Reader";
    private static final int VPS_NUT = 32;
    private String formatId;
    private boolean hasOutputFormat;
    private TrackOutput output;
    private SampleReader sampleReader;
    private final SeiReader seiReader;
    private long totalBytesWritten;
    private final boolean[] prefixFlags = new boolean[3];
    private final NalUnitTargetBuffer vps = new NalUnitTargetBuffer(32, 128);
    private final NalUnitTargetBuffer sps = new NalUnitTargetBuffer(33, 128);
    private final NalUnitTargetBuffer pps = new NalUnitTargetBuffer(34, 128);
    private final NalUnitTargetBuffer prefixSei = new NalUnitTargetBuffer(39, 128);
    private final NalUnitTargetBuffer suffixSei = new NalUnitTargetBuffer(40, 128);
    private long pesTimeUs = C.TIME_UNSET;
    private final ParsableByteArray seiWrapper = new ParsableByteArray();

    public static final class SampleReader {
        private static final int FIRST_SLICE_FLAG_OFFSET = 2;
        private boolean isFirstPrefixNalUnit;
        private boolean isFirstSlice;
        private boolean lookingForFirstSliceFlag;
        private int nalUnitBytesRead;
        private boolean nalUnitHasKeyframeData;
        private long nalUnitPosition;
        private long nalUnitTimeUs;
        private final TrackOutput output;
        private boolean readingPrefix;
        private boolean readingSample;
        private boolean sampleIsKeyframe;
        private long samplePosition;
        private long sampleTimeUs;

        public SampleReader(TrackOutput trackOutput) {
            this.output = trackOutput;
        }

        private static boolean isPrefixNalUnit(int i2) {
            return (32 <= i2 && i2 <= 35) || i2 == 39;
        }

        private static boolean isVclBodyNalUnit(int i2) {
            return i2 < 32 || i2 == 40;
        }

        private void outputSample(int i2) {
            long j2 = this.sampleTimeUs;
            if (j2 == C.TIME_UNSET) {
                return;
            }
            boolean z2 = this.sampleIsKeyframe;
            this.output.sampleMetadata(j2, z2 ? 1 : 0, (int) (this.nalUnitPosition - this.samplePosition), i2, null);
        }

        public void endNalUnit(long j2, int i2, boolean z2) {
            if (this.readingPrefix && this.isFirstSlice) {
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
                this.readingPrefix = false;
            } else if (this.isFirstPrefixNalUnit || this.isFirstSlice) {
                if (z2 && this.readingSample) {
                    outputSample(i2 + ((int) (j2 - this.nalUnitPosition)));
                }
                this.samplePosition = this.nalUnitPosition;
                this.sampleTimeUs = this.nalUnitTimeUs;
                this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
                this.readingSample = true;
            }
        }

        public void readNalUnitData(byte[] bArr, int i2, int i3) {
            if (this.lookingForFirstSliceFlag) {
                int i4 = this.nalUnitBytesRead;
                int i5 = (i2 + 2) - i4;
                if (i5 >= i3) {
                    this.nalUnitBytesRead = i4 + (i3 - i2);
                } else {
                    this.isFirstSlice = (bArr[i5] & 128) != 0;
                    this.lookingForFirstSliceFlag = false;
                }
            }
        }

        public void reset() {
            this.lookingForFirstSliceFlag = false;
            this.isFirstSlice = false;
            this.isFirstPrefixNalUnit = false;
            this.readingSample = false;
            this.readingPrefix = false;
        }

        public void startNalUnit(long j2, int i2, int i3, long j3, boolean z2) {
            this.isFirstSlice = false;
            this.isFirstPrefixNalUnit = false;
            this.nalUnitTimeUs = j3;
            this.nalUnitBytesRead = 0;
            this.nalUnitPosition = j2;
            if (!isVclBodyNalUnit(i3)) {
                if (this.readingSample && !this.readingPrefix) {
                    if (z2) {
                        outputSample(i2);
                    }
                    this.readingSample = false;
                }
                if (isPrefixNalUnit(i3)) {
                    this.isFirstPrefixNalUnit = !this.readingPrefix;
                    this.readingPrefix = true;
                }
            }
            boolean z3 = i3 >= 16 && i3 <= 21;
            this.nalUnitHasKeyframeData = z3;
            this.lookingForFirstSliceFlag = z3 || i3 <= 9;
        }
    }

    public H265Reader(SeiReader seiReader) {
        this.seiReader = seiReader;
    }

    @EnsuresNonNull({"output", "sampleReader"})
    private void assertTracksCreated() {
        Assertions.checkStateNotNull(this.output);
        Util.castNonNull(this.sampleReader);
    }

    @RequiresNonNull({"output", "sampleReader"})
    private void endNalUnit(long j2, int i2, int i3, long j3) {
        this.sampleReader.endNalUnit(j2, i2, this.hasOutputFormat);
        if (!this.hasOutputFormat) {
            this.vps.endNalUnit(i3);
            this.sps.endNalUnit(i3);
            this.pps.endNalUnit(i3);
            if (this.vps.isCompleted() && this.sps.isCompleted() && this.pps.isCompleted()) {
                this.output.format(parseMediaFormat(this.formatId, this.vps, this.sps, this.pps));
                this.hasOutputFormat = true;
            }
        }
        if (this.prefixSei.endNalUnit(i3)) {
            NalUnitTargetBuffer nalUnitTargetBuffer = this.prefixSei;
            this.seiWrapper.reset(this.prefixSei.nalData, NalUnitUtil.unescapeStream(nalUnitTargetBuffer.nalData, nalUnitTargetBuffer.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(j3, this.seiWrapper);
        }
        if (this.suffixSei.endNalUnit(i3)) {
            NalUnitTargetBuffer nalUnitTargetBuffer2 = this.suffixSei;
            this.seiWrapper.reset(this.suffixSei.nalData, NalUnitUtil.unescapeStream(nalUnitTargetBuffer2.nalData, nalUnitTargetBuffer2.nalLength));
            this.seiWrapper.skipBytes(5);
            this.seiReader.consume(j3, this.seiWrapper);
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void nalUnitData(byte[] bArr, int i2, int i3) {
        this.sampleReader.readNalUnitData(bArr, i2, i3);
        if (!this.hasOutputFormat) {
            this.vps.appendToNalUnit(bArr, i2, i3);
            this.sps.appendToNalUnit(bArr, i2, i3);
            this.pps.appendToNalUnit(bArr, i2, i3);
        }
        this.prefixSei.appendToNalUnit(bArr, i2, i3);
        this.suffixSei.appendToNalUnit(bArr, i2, i3);
    }

    private static Format parseMediaFormat(@Nullable String str, NalUnitTargetBuffer nalUnitTargetBuffer, NalUnitTargetBuffer nalUnitTargetBuffer2, NalUnitTargetBuffer nalUnitTargetBuffer3) {
        int i2 = nalUnitTargetBuffer.nalLength;
        byte[] bArr = new byte[nalUnitTargetBuffer2.nalLength + i2 + nalUnitTargetBuffer3.nalLength];
        System.arraycopy(nalUnitTargetBuffer.nalData, 0, bArr, 0, i2);
        System.arraycopy(nalUnitTargetBuffer2.nalData, 0, bArr, nalUnitTargetBuffer.nalLength, nalUnitTargetBuffer2.nalLength);
        System.arraycopy(nalUnitTargetBuffer3.nalData, 0, bArr, nalUnitTargetBuffer.nalLength + nalUnitTargetBuffer2.nalLength, nalUnitTargetBuffer3.nalLength);
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(nalUnitTargetBuffer2.nalData, 0, nalUnitTargetBuffer2.nalLength);
        parsableNalUnitBitArray.skipBits(44);
        int bits = parsableNalUnitBitArray.readBits(3);
        parsableNalUnitBitArray.skipBit();
        int bits2 = parsableNalUnitBitArray.readBits(2);
        boolean bit = parsableNalUnitBitArray.readBit();
        int bits3 = parsableNalUnitBitArray.readBits(5);
        int i3 = 0;
        for (int i4 = 0; i4 < 32; i4++) {
            if (parsableNalUnitBitArray.readBit()) {
                i3 |= 1 << i4;
            }
        }
        int[] iArr = new int[6];
        for (int i5 = 0; i5 < 6; i5++) {
            iArr[i5] = parsableNalUnitBitArray.readBits(8);
        }
        int bits4 = parsableNalUnitBitArray.readBits(8);
        int i6 = 0;
        for (int i7 = 0; i7 < bits; i7++) {
            if (parsableNalUnitBitArray.readBit()) {
                i6 += 89;
            }
            if (parsableNalUnitBitArray.readBit()) {
                i6 += 8;
            }
        }
        parsableNalUnitBitArray.skipBits(i6);
        if (bits > 0) {
            parsableNalUnitBitArray.skipBits((8 - bits) * 2);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (unsignedExpGolombCodedInt == 3) {
            parsableNalUnitBitArray.skipBit();
        }
        int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit()) {
            int unsignedExpGolombCodedInt4 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            int unsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            unsignedExpGolombCodedInt2 -= ((unsignedExpGolombCodedInt == 1 || unsignedExpGolombCodedInt == 2) ? 2 : 1) * (unsignedExpGolombCodedInt4 + unsignedExpGolombCodedInt5);
            unsignedExpGolombCodedInt3 -= (unsignedExpGolombCodedInt == 1 ? 2 : 1) * (unsignedExpGolombCodedInt6 + unsignedExpGolombCodedInt7);
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int unsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        for (int i8 = parsableNalUnitBitArray.readBit() ? 0 : bits; i8 <= bits; i8++) {
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        }
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
            skipScalingList(parsableNalUnitBitArray);
        }
        parsableNalUnitBitArray.skipBits(2);
        if (parsableNalUnitBitArray.readBit()) {
            parsableNalUnitBitArray.skipBits(8);
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
        }
        skipShortTermRefPicSets(parsableNalUnitBitArray);
        if (parsableNalUnitBitArray.readBit()) {
            for (int i9 = 0; i9 < parsableNalUnitBitArray.readUnsignedExpGolombCodedInt(); i9++) {
                parsableNalUnitBitArray.skipBits(unsignedExpGolombCodedInt8 + 4 + 1);
            }
        }
        parsableNalUnitBitArray.skipBits(2);
        float f2 = 1.0f;
        if (parsableNalUnitBitArray.readBit()) {
            if (parsableNalUnitBitArray.readBit()) {
                int bits5 = parsableNalUnitBitArray.readBits(8);
                if (bits5 == 255) {
                    int bits6 = parsableNalUnitBitArray.readBits(16);
                    int bits7 = parsableNalUnitBitArray.readBits(16);
                    if (bits6 != 0 && bits7 != 0) {
                        f2 = bits6 / bits7;
                    }
                } else {
                    float[] fArr = NalUnitUtil.ASPECT_RATIO_IDC_VALUES;
                    if (bits5 < fArr.length) {
                        f2 = fArr[bits5];
                    } else {
                        StringBuilder sb = new StringBuilder(46);
                        sb.append("Unexpected aspect_ratio_idc value: ");
                        sb.append(bits5);
                        Log.w(TAG, sb.toString());
                    }
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBit();
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.skipBits(4);
                if (parsableNalUnitBitArray.readBit()) {
                    parsableNalUnitBitArray.skipBits(24);
                }
            }
            if (parsableNalUnitBitArray.readBit()) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            parsableNalUnitBitArray.skipBit();
            if (parsableNalUnitBitArray.readBit()) {
                unsignedExpGolombCodedInt3 *= 2;
            }
        }
        return new Format.Builder().setId(str).setSampleMimeType(MimeTypes.VIDEO_H265).setCodecs(CodecSpecificDataUtil.buildHevcCodecString(bits2, bit, bits3, i3, iArr, bits4)).setWidth(unsignedExpGolombCodedInt2).setHeight(unsignedExpGolombCodedInt3).setPixelWidthHeightRatio(f2).setInitializationData(Collections.singletonList(bArr)).build();
    }

    private static void skipScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = 0;
            while (i3 < 6) {
                int i4 = 1;
                if (parsableNalUnitBitArray.readBit()) {
                    int iMin = Math.min(64, 1 << ((i2 << 1) + 4));
                    if (i2 > 1) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                    for (int i5 = 0; i5 < iMin; i5++) {
                        parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    }
                } else {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                }
                if (i2 == 3) {
                    i4 = 3;
                }
                i3 += i4;
            }
        }
    }

    private static void skipShortTermRefPicSets(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        int unsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        boolean bit = false;
        int i2 = 0;
        for (int i3 = 0; i3 < unsignedExpGolombCodedInt; i3++) {
            if (i3 != 0) {
                bit = parsableNalUnitBitArray.readBit();
            }
            if (bit) {
                parsableNalUnitBitArray.skipBit();
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                for (int i4 = 0; i4 <= i2; i4++) {
                    if (parsableNalUnitBitArray.readBit()) {
                        parsableNalUnitBitArray.skipBit();
                    }
                }
            } else {
                int unsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int unsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                int i5 = unsignedExpGolombCodedInt2 + unsignedExpGolombCodedInt3;
                for (int i6 = 0; i6 < unsignedExpGolombCodedInt2; i6++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
                for (int i7 = 0; i7 < unsignedExpGolombCodedInt3; i7++) {
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                }
                i2 = i5;
            }
        }
    }

    @RequiresNonNull({"sampleReader"})
    private void startNalUnit(long j2, int i2, int i3, long j3) {
        this.sampleReader.startNalUnit(j2, i2, i3, j3, this.hasOutputFormat);
        if (!this.hasOutputFormat) {
            this.vps.startNalUnit(i3);
            this.sps.startNalUnit(i3);
            this.pps.startNalUnit(i3);
        }
        this.prefixSei.startNalUnit(i3);
        this.suffixSei.startNalUnit(i3);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        assertTracksCreated();
        while (parsableByteArray.bytesLeft() > 0) {
            int position = parsableByteArray.getPosition();
            int iLimit = parsableByteArray.limit();
            byte[] data = parsableByteArray.getData();
            this.totalBytesWritten += parsableByteArray.bytesLeft();
            this.output.sampleData(parsableByteArray, parsableByteArray.bytesLeft());
            while (position < iLimit) {
                int iFindNalUnit = NalUnitUtil.findNalUnit(data, position, iLimit, this.prefixFlags);
                if (iFindNalUnit == iLimit) {
                    nalUnitData(data, position, iLimit);
                    return;
                }
                int h265NalUnitType = NalUnitUtil.getH265NalUnitType(data, iFindNalUnit);
                int i2 = iFindNalUnit - position;
                if (i2 > 0) {
                    nalUnitData(data, position, iFindNalUnit);
                }
                int i3 = iLimit - iFindNalUnit;
                long j2 = this.totalBytesWritten - i3;
                endNalUnit(j2, i3, i2 < 0 ? -i2 : 0, this.pesTimeUs);
                startNalUnit(j2, i3, h265NalUnitType, this.pesTimeUs);
                position = iFindNalUnit + 3;
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.formatId = trackIdGenerator.getFormatId();
        TrackOutput trackOutputTrack = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.output = trackOutputTrack;
        this.sampleReader = new SampleReader(trackOutputTrack);
        this.seiReader.createTracks(extractorOutput, trackIdGenerator);
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j2, int i2) {
        if (j2 != C.TIME_UNSET) {
            this.pesTimeUs = j2;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.totalBytesWritten = 0L;
        this.pesTimeUs = C.TIME_UNSET;
        NalUnitUtil.clearPrefixFlags(this.prefixFlags);
        this.vps.reset();
        this.sps.reset();
        this.pps.reset();
        this.prefixSei.reset();
        this.suffixSei.reset();
        SampleReader sampleReader = this.sampleReader;
        if (sampleReader != null) {
            sampleReader.reset();
        }
    }
}
