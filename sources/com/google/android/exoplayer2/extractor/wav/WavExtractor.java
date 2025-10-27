package com.google.android.exoplayer2.extractor.wav;

import android.net.Uri;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.c;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class WavExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.wav.a
        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public final Extractor[] createExtractors() {
            return WavExtractor.lambda$static$0();
        }

        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
        public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
            return c.a(this, uri, map);
        }
    };
    private static final int STATE_READING_FILE_TYPE = 0;
    private static final int STATE_READING_FORMAT = 2;
    private static final int STATE_READING_RF64_SAMPLE_DATA_SIZE = 1;
    private static final int STATE_READING_SAMPLE_DATA = 4;
    private static final int STATE_SKIPPING_TO_SAMPLE_DATA = 3;
    private static final String TAG = "WavExtractor";
    private static final int TARGET_SAMPLES_PER_SECOND = 10;
    private ExtractorOutput extractorOutput;
    private OutputWriter outputWriter;
    private TrackOutput trackOutput;
    private int state = 0;
    private long rf64SampleDataSize = -1;
    private int dataStartPosition = -1;
    private long dataEndPosition = -1;

    public static final class ImaAdPcmOutputWriter implements OutputWriter {
        private static final int[] INDEX_TABLE = {-1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8};
        private static final int[] STEP_TABLE = {7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, 143, 157, R2.anim.window_bottom_out, R2.array.ease_excel_file_suffix, 209, 230, R2.attr.actionModeSplitBackground, R2.attr.ad_width, 307, R2.attr.app_update_top_img, R2.attr.arcText, 408, R2.attr.banner_radius, R2.attr.bg_invite_friend_bg, R2.attr.bl_checked_gradient_type, R2.attr.bl_focused_gradient_useLevel, R2.attr.bl_pressed_stroke_color, R2.attr.bl_unEnabled_gradient_startColor, R2.attr.borderRoundPercent, R2.attr.bvp_scroll_duration, R2.attr.circle_admin_paizhao, R2.attr.colorScheme, R2.attr.cornerSizeTopLeft, R2.attr.defaultIntentAction, R2.attr.ease_con_item_date_text_color, R2.attr.first_backgroup_color_opaque_33, R2.attr.horizontalPadding, R2.attr.ic_red_packet_right_bg, R2.attr.inputTagHint, R2.attr.layout_srlSpinnerStyle, R2.attr.mhScrollableWhenRefreshing, R2.attr.panelMenuListTheme, 3024, R2.attr.srlScrollableWhenRefreshing, R2.attr.textInputOutlinedExposedDropdownMenuStyle, R2.attr.zx_color_calendar_bg_disable, R2.color.c_ff6662, R2.color.forth_txt_color_night, R2.color.material_blue_grey_900, R2.color.umeng_socialize_text_share_content, R2.dimen.dp_105, R2.dimen.dp_522, R2.dimen.m3_fab_border_width, R2.drawable.bg_book_hot_rank, R2.drawable.ee_6, R2.drawable.ic_score_up_night, R2.drawable.picture_ic_flash_on, R2.drawable.shape_toast_bg, R2.id.btn_pic_rechoose, R2.id.img_picture, R2.id.ly_type, R2.id.sl_school_flag_join, R2.id.twoCvGrop, R2.layout.layout_major_by_department, R2.string.product_share_get, R2.styleable.CollapsingToolbarLayout_expandedTitleGravity, R2.styleable.SearchView_searchIcon, 32767};
        private final ParsableByteArray decodedData;
        private final ExtractorOutput extractorOutput;
        private final Format format;
        private final int framesPerBlock;
        private final byte[] inputData;
        private long outputFrameCount;
        private int pendingInputBytes;
        private int pendingOutputBytes;
        private long startTimeUs;
        private final int targetSampleSizeFrames;
        private final TrackOutput trackOutput;
        private final WavFormat wavFormat;

        public ImaAdPcmOutputWriter(ExtractorOutput extractorOutput, TrackOutput trackOutput, WavFormat wavFormat) throws ParserException {
            this.extractorOutput = extractorOutput;
            this.trackOutput = trackOutput;
            this.wavFormat = wavFormat;
            int iMax = Math.max(1, wavFormat.frameRateHz / 10);
            this.targetSampleSizeFrames = iMax;
            ParsableByteArray parsableByteArray = new ParsableByteArray(wavFormat.extraData);
            parsableByteArray.readLittleEndianUnsignedShort();
            int littleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
            this.framesPerBlock = littleEndianUnsignedShort;
            int i2 = wavFormat.numChannels;
            int i3 = (((wavFormat.blockSize - (i2 * 4)) * 8) / (wavFormat.bitsPerSample * i2)) + 1;
            if (littleEndianUnsignedShort == i3) {
                int iCeilDivide = Util.ceilDivide(iMax, littleEndianUnsignedShort);
                this.inputData = new byte[wavFormat.blockSize * iCeilDivide];
                this.decodedData = new ParsableByteArray(iCeilDivide * numOutputFramesToBytes(littleEndianUnsignedShort, i2));
                int i4 = ((wavFormat.frameRateHz * wavFormat.blockSize) * 8) / littleEndianUnsignedShort;
                this.format = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setAverageBitrate(i4).setPeakBitrate(i4).setMaxInputSize(numOutputFramesToBytes(iMax, i2)).setChannelCount(wavFormat.numChannels).setSampleRate(wavFormat.frameRateHz).setPcmEncoding(2).build();
                return;
            }
            StringBuilder sb = new StringBuilder(56);
            sb.append("Expected frames per block: ");
            sb.append(i3);
            sb.append("; got: ");
            sb.append(littleEndianUnsignedShort);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }

        private void decode(byte[] bArr, int i2, ParsableByteArray parsableByteArray) {
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < this.wavFormat.numChannels; i4++) {
                    decodeBlockForChannel(bArr, i3, i4, parsableByteArray.getData());
                }
            }
            int iNumOutputFramesToBytes = numOutputFramesToBytes(this.framesPerBlock * i2);
            parsableByteArray.setPosition(0);
            parsableByteArray.setLimit(iNumOutputFramesToBytes);
        }

        private void decodeBlockForChannel(byte[] bArr, int i2, int i3, byte[] bArr2) {
            WavFormat wavFormat = this.wavFormat;
            int i4 = wavFormat.blockSize;
            int i5 = wavFormat.numChannels;
            int i6 = (i2 * i4) + (i3 * 4);
            int i7 = (i5 * 4) + i6;
            int i8 = (i4 / i5) - 4;
            int iConstrainValue = (short) (((bArr[i6 + 1] & 255) << 8) | (bArr[i6] & 255));
            int iMin = Math.min(bArr[i6 + 2] & 255, 88);
            int i9 = STEP_TABLE[iMin];
            int i10 = ((i2 * this.framesPerBlock * i5) + i3) * 2;
            bArr2[i10] = (byte) (iConstrainValue & 255);
            bArr2[i10 + 1] = (byte) (iConstrainValue >> 8);
            for (int i11 = 0; i11 < i8 * 2; i11++) {
                int i12 = bArr[((i11 / 8) * i5 * 4) + i7 + ((i11 / 2) % 4)] & 255;
                int i13 = i11 % 2 == 0 ? i12 & 15 : i12 >> 4;
                int i14 = ((((i13 & 7) * 2) + 1) * i9) >> 3;
                if ((i13 & 8) != 0) {
                    i14 = -i14;
                }
                iConstrainValue = Util.constrainValue(iConstrainValue + i14, -32768, 32767);
                i10 += i5 * 2;
                bArr2[i10] = (byte) (iConstrainValue & 255);
                bArr2[i10 + 1] = (byte) (iConstrainValue >> 8);
                int i15 = iMin + INDEX_TABLE[i13];
                int[] iArr = STEP_TABLE;
                iMin = Util.constrainValue(i15, 0, iArr.length - 1);
                i9 = iArr[iMin];
            }
        }

        private int numOutputBytesToFrames(int i2) {
            return i2 / (this.wavFormat.numChannels * 2);
        }

        private int numOutputFramesToBytes(int i2) {
            return numOutputFramesToBytes(i2, this.wavFormat.numChannels);
        }

        private static int numOutputFramesToBytes(int i2, int i3) {
            return i2 * 2 * i3;
        }

        private void writeSampleMetadata(int i2) {
            long jScaleLargeTimestamp = this.startTimeUs + Util.scaleLargeTimestamp(this.outputFrameCount, 1000000L, this.wavFormat.frameRateHz);
            int iNumOutputFramesToBytes = numOutputFramesToBytes(i2);
            this.trackOutput.sampleMetadata(jScaleLargeTimestamp, 1, iNumOutputFramesToBytes, this.pendingOutputBytes - iNumOutputFramesToBytes, null);
            this.outputFrameCount += i2;
            this.pendingOutputBytes -= iNumOutputFramesToBytes;
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        public void init(int i2, long j2) {
            this.extractorOutput.seekMap(new WavSeekMap(this.wavFormat, this.framesPerBlock, i2, j2));
            this.trackOutput.format(this.format);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        public void reset(long j2) {
            this.pendingInputBytes = 0;
            this.startTimeUs = j2;
            this.pendingOutputBytes = 0;
            this.outputFrameCount = 0L;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0020  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:10:0x0035 -> B:4:0x001b). Please report as a decompilation issue!!! */
        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean sampleData(com.google.android.exoplayer2.extractor.ExtractorInput r7, long r8) throws java.io.IOException {
            /*
                r6 = this;
                int r0 = r6.targetSampleSizeFrames
                int r1 = r6.pendingOutputBytes
                int r1 = r6.numOutputBytesToFrames(r1)
                int r0 = r0 - r1
                int r1 = r6.framesPerBlock
                int r0 = com.google.android.exoplayer2.util.Util.ceilDivide(r0, r1)
                com.google.android.exoplayer2.extractor.wav.WavFormat r1 = r6.wavFormat
                int r1 = r1.blockSize
                int r0 = r0 * r1
                r1 = 0
                int r1 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
                r2 = 1
                if (r1 != 0) goto L1d
            L1b:
                r1 = r2
                goto L1e
            L1d:
                r1 = 0
            L1e:
                if (r1 != 0) goto L3e
                int r3 = r6.pendingInputBytes
                if (r3 >= r0) goto L3e
                int r3 = r0 - r3
                long r3 = (long) r3
                long r3 = java.lang.Math.min(r3, r8)
                int r3 = (int) r3
                byte[] r4 = r6.inputData
                int r5 = r6.pendingInputBytes
                int r3 = r7.read(r4, r5, r3)
                r4 = -1
                if (r3 != r4) goto L38
                goto L1b
            L38:
                int r4 = r6.pendingInputBytes
                int r4 = r4 + r3
                r6.pendingInputBytes = r4
                goto L1e
            L3e:
                int r7 = r6.pendingInputBytes
                com.google.android.exoplayer2.extractor.wav.WavFormat r8 = r6.wavFormat
                int r8 = r8.blockSize
                int r7 = r7 / r8
                if (r7 <= 0) goto L75
                byte[] r8 = r6.inputData
                com.google.android.exoplayer2.util.ParsableByteArray r9 = r6.decodedData
                r6.decode(r8, r7, r9)
                int r8 = r6.pendingInputBytes
                com.google.android.exoplayer2.extractor.wav.WavFormat r9 = r6.wavFormat
                int r9 = r9.blockSize
                int r7 = r7 * r9
                int r8 = r8 - r7
                r6.pendingInputBytes = r8
                com.google.android.exoplayer2.util.ParsableByteArray r7 = r6.decodedData
                int r7 = r7.limit()
                com.google.android.exoplayer2.extractor.TrackOutput r8 = r6.trackOutput
                com.google.android.exoplayer2.util.ParsableByteArray r9 = r6.decodedData
                r8.sampleData(r9, r7)
                int r8 = r6.pendingOutputBytes
                int r8 = r8 + r7
                r6.pendingOutputBytes = r8
                int r7 = r6.numOutputBytesToFrames(r8)
                int r8 = r6.targetSampleSizeFrames
                if (r7 < r8) goto L75
                r6.writeSampleMetadata(r8)
            L75:
                if (r1 == 0) goto L82
                int r7 = r6.pendingOutputBytes
                int r7 = r6.numOutputBytesToFrames(r7)
                if (r7 <= 0) goto L82
                r6.writeSampleMetadata(r7)
            L82:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.wav.WavExtractor.ImaAdPcmOutputWriter.sampleData(com.google.android.exoplayer2.extractor.ExtractorInput, long):boolean");
        }
    }

    public interface OutputWriter {
        void init(int i2, long j2) throws ParserException;

        void reset(long j2);

        boolean sampleData(ExtractorInput extractorInput, long j2) throws IOException;
    }

    public static final class PassthroughOutputWriter implements OutputWriter {
        private final ExtractorOutput extractorOutput;
        private final Format format;
        private long outputFrameCount;
        private int pendingOutputBytes;
        private long startTimeUs;
        private final int targetSampleSizeBytes;
        private final TrackOutput trackOutput;
        private final WavFormat wavFormat;

        public PassthroughOutputWriter(ExtractorOutput extractorOutput, TrackOutput trackOutput, WavFormat wavFormat, String str, int i2) throws ParserException {
            this.extractorOutput = extractorOutput;
            this.trackOutput = trackOutput;
            this.wavFormat = wavFormat;
            int i3 = (wavFormat.numChannels * wavFormat.bitsPerSample) / 8;
            int i4 = wavFormat.blockSize;
            if (i4 == i3) {
                int i5 = wavFormat.frameRateHz;
                int i6 = i5 * i3 * 8;
                int iMax = Math.max(i3, (i5 * i3) / 10);
                this.targetSampleSizeBytes = iMax;
                this.format = new Format.Builder().setSampleMimeType(str).setAverageBitrate(i6).setPeakBitrate(i6).setMaxInputSize(iMax).setChannelCount(wavFormat.numChannels).setSampleRate(wavFormat.frameRateHz).setPcmEncoding(i2).build();
                return;
            }
            StringBuilder sb = new StringBuilder(50);
            sb.append("Expected block size: ");
            sb.append(i3);
            sb.append("; got: ");
            sb.append(i4);
            throw ParserException.createForMalformedContainer(sb.toString(), null);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        public void init(int i2, long j2) {
            this.extractorOutput.seekMap(new WavSeekMap(this.wavFormat, 1, i2, j2));
            this.trackOutput.format(this.format);
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        public void reset(long j2) {
            this.startTimeUs = j2;
            this.pendingOutputBytes = 0;
            this.outputFrameCount = 0L;
        }

        @Override // com.google.android.exoplayer2.extractor.wav.WavExtractor.OutputWriter
        public boolean sampleData(ExtractorInput extractorInput, long j2) throws IOException {
            int i2;
            int i3;
            long j3 = j2;
            while (j3 > 0 && (i2 = this.pendingOutputBytes) < (i3 = this.targetSampleSizeBytes)) {
                int iSampleData = this.trackOutput.sampleData((DataReader) extractorInput, (int) Math.min(i3 - i2, j3), true);
                if (iSampleData == -1) {
                    j3 = 0;
                } else {
                    this.pendingOutputBytes += iSampleData;
                    j3 -= iSampleData;
                }
            }
            int i4 = this.wavFormat.blockSize;
            int i5 = this.pendingOutputBytes / i4;
            if (i5 > 0) {
                long jScaleLargeTimestamp = this.startTimeUs + Util.scaleLargeTimestamp(this.outputFrameCount, 1000000L, r1.frameRateHz);
                int i6 = i5 * i4;
                int i7 = this.pendingOutputBytes - i6;
                this.trackOutput.sampleMetadata(jScaleLargeTimestamp, 1, i6, i7, null);
                this.outputFrameCount += i5;
                this.pendingOutputBytes = i7;
            }
            return j3 <= 0;
        }
    }

    @EnsuresNonNull({"extractorOutput", "trackOutput"})
    private void assertInitialized() {
        Assertions.checkStateNotNull(this.trackOutput);
        Util.castNonNull(this.extractorOutput);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$static$0() {
        return new Extractor[]{new WavExtractor()};
    }

    private void readFileType(ExtractorInput extractorInput) throws IOException {
        Assertions.checkState(extractorInput.getPosition() == 0);
        int i2 = this.dataStartPosition;
        if (i2 != -1) {
            extractorInput.skipFully(i2);
            this.state = 4;
        } else {
            if (!WavHeaderReader.checkFileType(extractorInput)) {
                throw ParserException.createForMalformedContainer("Unsupported or unrecognized wav file type.", null);
            }
            extractorInput.skipFully((int) (extractorInput.getPeekPosition() - extractorInput.getPosition()));
            this.state = 1;
        }
    }

    @RequiresNonNull({"extractorOutput", "trackOutput"})
    private void readFormat(ExtractorInput extractorInput) throws IOException {
        WavFormat format = WavHeaderReader.readFormat(extractorInput);
        int i2 = format.formatType;
        if (i2 == 17) {
            this.outputWriter = new ImaAdPcmOutputWriter(this.extractorOutput, this.trackOutput, format);
        } else if (i2 == 6) {
            this.outputWriter = new PassthroughOutputWriter(this.extractorOutput, this.trackOutput, format, MimeTypes.AUDIO_ALAW, -1);
        } else if (i2 == 7) {
            this.outputWriter = new PassthroughOutputWriter(this.extractorOutput, this.trackOutput, format, MimeTypes.AUDIO_MLAW, -1);
        } else {
            int pcmEncodingForType = WavUtil.getPcmEncodingForType(i2, format.bitsPerSample);
            if (pcmEncodingForType == 0) {
                int i3 = format.formatType;
                StringBuilder sb = new StringBuilder(40);
                sb.append("Unsupported WAV format type: ");
                sb.append(i3);
                throw ParserException.createForUnsupportedContainerFeature(sb.toString());
            }
            this.outputWriter = new PassthroughOutputWriter(this.extractorOutput, this.trackOutput, format, MimeTypes.AUDIO_RAW, pcmEncodingForType);
        }
        this.state = 3;
    }

    private void readRf64SampleDataSize(ExtractorInput extractorInput) throws IOException {
        this.rf64SampleDataSize = WavHeaderReader.readRf64SampleDataSize(extractorInput);
        this.state = 2;
    }

    private int readSampleData(ExtractorInput extractorInput) throws IOException {
        Assertions.checkState(this.dataEndPosition != -1);
        return ((OutputWriter) Assertions.checkNotNull(this.outputWriter)).sampleData(extractorInput, this.dataEndPosition - extractorInput.getPosition()) ? -1 : 0;
    }

    private void skipToSampleData(ExtractorInput extractorInput) throws IOException {
        Pair<Long, Long> pairSkipToSampleData = WavHeaderReader.skipToSampleData(extractorInput);
        this.dataStartPosition = ((Long) pairSkipToSampleData.first).intValue();
        long jLongValue = ((Long) pairSkipToSampleData.second).longValue();
        long j2 = this.rf64SampleDataSize;
        if (j2 != -1 && jLongValue == InternalZipConstants.ZIP_64_LIMIT) {
            jLongValue = j2;
        }
        this.dataEndPosition = this.dataStartPosition + jLongValue;
        long length = extractorInput.getLength();
        if (length != -1) {
            long j3 = this.dataEndPosition;
            if (j3 > length) {
                StringBuilder sb = new StringBuilder(69);
                sb.append("Data exceeds input length: ");
                sb.append(j3);
                sb.append(", ");
                sb.append(length);
                Log.w(TAG, sb.toString());
                this.dataEndPosition = length;
            }
        }
        ((OutputWriter) Assertions.checkNotNull(this.outputWriter)).init(this.dataStartPosition, this.dataEndPosition);
        this.state = 4;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        assertInitialized();
        int i2 = this.state;
        if (i2 == 0) {
            readFileType(extractorInput);
            return 0;
        }
        if (i2 == 1) {
            readRf64SampleDataSize(extractorInput);
            return 0;
        }
        if (i2 == 2) {
            readFormat(extractorInput);
            return 0;
        }
        if (i2 == 3) {
            skipToSampleData(extractorInput);
            return 0;
        }
        if (i2 == 4) {
            return readSampleData(extractorInput);
        }
        throw new IllegalStateException();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j2, long j3) {
        this.state = j2 == 0 ? 0 : 4;
        OutputWriter outputWriter = this.outputWriter;
        if (outputWriter != null) {
            outputWriter.reset(j3);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return WavHeaderReader.checkFileType(extractorInput);
    }
}
