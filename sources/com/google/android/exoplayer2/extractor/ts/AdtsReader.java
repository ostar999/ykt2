package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class AdtsReader implements ElementaryStreamReader {
    private static final int CRC_SIZE = 2;
    private static final int HEADER_SIZE = 5;
    private static final int ID3_HEADER_SIZE = 10;
    private static final byte[] ID3_IDENTIFIER = {73, 68, TarConstants.LF_CHR};
    private static final int ID3_SIZE_OFFSET = 6;
    private static final int MATCH_STATE_FF = 512;
    private static final int MATCH_STATE_I = 768;
    private static final int MATCH_STATE_ID = 1024;
    private static final int MATCH_STATE_START = 256;
    private static final int MATCH_STATE_VALUE_SHIFT = 8;
    private static final int STATE_CHECKING_ADTS_HEADER = 1;
    private static final int STATE_FINDING_SAMPLE = 0;
    private static final int STATE_READING_ADTS_HEADER = 3;
    private static final int STATE_READING_ID3_HEADER = 2;
    private static final int STATE_READING_SAMPLE = 4;
    private static final String TAG = "AdtsReader";
    private static final int VERSION_UNSET = -1;
    private final ParsableBitArray adtsScratch;
    private int bytesRead;
    private int currentFrameVersion;
    private TrackOutput currentOutput;
    private long currentSampleDuration;
    private final boolean exposeId3;
    private int firstFrameSampleRateIndex;
    private int firstFrameVersion;
    private String formatId;
    private boolean foundFirstFrame;
    private boolean hasCrc;
    private boolean hasOutputFormat;
    private final ParsableByteArray id3HeaderBuffer;
    private TrackOutput id3Output;

    @Nullable
    private final String language;
    private int matchState;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleSize;
    private int state;
    private long timeUs;

    public AdtsReader(boolean z2) {
        this(z2, null);
    }

    @EnsuresNonNull({"output", "currentOutput", "id3Output"})
    private void assertTracksCreated() {
        Assertions.checkNotNull(this.output);
        Util.castNonNull(this.currentOutput);
        Util.castNonNull(this.id3Output);
    }

    private void checkAdtsHeader(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() == 0) {
            return;
        }
        this.adtsScratch.data[0] = parsableByteArray.getData()[parsableByteArray.getPosition()];
        this.adtsScratch.setPosition(2);
        int bits = this.adtsScratch.readBits(4);
        int i2 = this.firstFrameSampleRateIndex;
        if (i2 != -1 && bits != i2) {
            resetSync();
            return;
        }
        if (!this.foundFirstFrame) {
            this.foundFirstFrame = true;
            this.firstFrameVersion = this.currentFrameVersion;
            this.firstFrameSampleRateIndex = bits;
        }
        setReadingAdtsHeaderState();
    }

    private boolean checkSyncPositionValid(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.setPosition(i2 + 1);
        if (!tryRead(parsableByteArray, this.adtsScratch.data, 1)) {
            return false;
        }
        this.adtsScratch.setPosition(4);
        int bits = this.adtsScratch.readBits(1);
        int i3 = this.firstFrameVersion;
        if (i3 != -1 && bits != i3) {
            return false;
        }
        if (this.firstFrameSampleRateIndex != -1) {
            if (!tryRead(parsableByteArray, this.adtsScratch.data, 1)) {
                return true;
            }
            this.adtsScratch.setPosition(2);
            if (this.adtsScratch.readBits(4) != this.firstFrameSampleRateIndex) {
                return false;
            }
            parsableByteArray.setPosition(i2 + 2);
        }
        if (!tryRead(parsableByteArray, this.adtsScratch.data, 4)) {
            return true;
        }
        this.adtsScratch.setPosition(14);
        int bits2 = this.adtsScratch.readBits(13);
        if (bits2 < 7) {
            return false;
        }
        byte[] data = parsableByteArray.getData();
        int iLimit = parsableByteArray.limit();
        int i4 = i2 + bits2;
        if (i4 >= iLimit) {
            return true;
        }
        byte b3 = data[i4];
        if (b3 == -1) {
            int i5 = i4 + 1;
            if (i5 == iLimit) {
                return true;
            }
            return isAdtsSyncBytes((byte) -1, data[i5]) && ((data[i5] & 8) >> 3) == bits;
        }
        if (b3 != 73) {
            return false;
        }
        int i6 = i4 + 1;
        if (i6 == iLimit) {
            return true;
        }
        if (data[i6] != 68) {
            return false;
        }
        int i7 = i4 + 2;
        return i7 == iLimit || data[i7] == 51;
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i2) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), i2 - this.bytesRead);
        parsableByteArray.readBytes(bArr, this.bytesRead, iMin);
        int i3 = this.bytesRead + iMin;
        this.bytesRead = i3;
        return i3 == i2;
    }

    private void findNextSample(ParsableByteArray parsableByteArray) {
        byte[] data = parsableByteArray.getData();
        int position = parsableByteArray.getPosition();
        int iLimit = parsableByteArray.limit();
        while (position < iLimit) {
            int i2 = position + 1;
            int i3 = data[position] & 255;
            if (this.matchState == 512 && isAdtsSyncBytes((byte) -1, (byte) i3) && (this.foundFirstFrame || checkSyncPositionValid(parsableByteArray, i2 - 2))) {
                this.currentFrameVersion = (i3 & 8) >> 3;
                this.hasCrc = (i3 & 1) == 0;
                if (this.foundFirstFrame) {
                    setReadingAdtsHeaderState();
                } else {
                    setCheckingAdtsHeaderState();
                }
                parsableByteArray.setPosition(i2);
                return;
            }
            int i4 = this.matchState;
            int i5 = i3 | i4;
            if (i5 == 329) {
                this.matchState = 768;
            } else if (i5 == 511) {
                this.matchState = 512;
            } else if (i5 == 836) {
                this.matchState = 1024;
            } else if (i5 == 1075) {
                setReadingId3HeaderState();
                parsableByteArray.setPosition(i2);
                return;
            } else if (i4 != 256) {
                this.matchState = 256;
                i2--;
            }
            position = i2;
        }
        parsableByteArray.setPosition(position);
    }

    private boolean isAdtsSyncBytes(byte b3, byte b4) {
        return isAdtsSyncWord(((b3 & 255) << 8) | (b4 & 255));
    }

    public static boolean isAdtsSyncWord(int i2) {
        return (i2 & 65526) == 65520;
    }

    @RequiresNonNull({"output"})
    private void parseAdtsHeader() throws ParserException {
        this.adtsScratch.setPosition(0);
        if (this.hasOutputFormat) {
            this.adtsScratch.skipBits(10);
        } else {
            int bits = this.adtsScratch.readBits(2) + 1;
            if (bits != 2) {
                StringBuilder sb = new StringBuilder(61);
                sb.append("Detected audio object type: ");
                sb.append(bits);
                sb.append(", but assuming AAC LC.");
                Log.w(TAG, sb.toString());
                bits = 2;
            }
            this.adtsScratch.skipBits(5);
            byte[] bArrBuildAudioSpecificConfig = AacUtil.buildAudioSpecificConfig(bits, this.firstFrameSampleRateIndex, this.adtsScratch.readBits(3));
            AacUtil.Config audioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArrBuildAudioSpecificConfig);
            Format formatBuild = new Format.Builder().setId(this.formatId).setSampleMimeType(MimeTypes.AUDIO_AAC).setCodecs(audioSpecificConfig.codecs).setChannelCount(audioSpecificConfig.channelCount).setSampleRate(audioSpecificConfig.sampleRateHz).setInitializationData(Collections.singletonList(bArrBuildAudioSpecificConfig)).setLanguage(this.language).build();
            this.sampleDurationUs = 1024000000 / formatBuild.sampleRate;
            this.output.format(formatBuild);
            this.hasOutputFormat = true;
        }
        this.adtsScratch.skipBits(4);
        int bits2 = (this.adtsScratch.readBits(13) - 2) - 5;
        if (this.hasCrc) {
            bits2 -= 2;
        }
        setReadingSampleState(this.output, this.sampleDurationUs, 0, bits2);
    }

    @RequiresNonNull({"id3Output"})
    private void parseId3Header() {
        this.id3Output.sampleData(this.id3HeaderBuffer, 10);
        this.id3HeaderBuffer.setPosition(6);
        setReadingSampleState(this.id3Output, 0L, 10, this.id3HeaderBuffer.readSynchSafeInt() + 10);
    }

    @RequiresNonNull({"currentOutput"})
    private void readSample(ParsableByteArray parsableByteArray) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), this.sampleSize - this.bytesRead);
        this.currentOutput.sampleData(parsableByteArray, iMin);
        int i2 = this.bytesRead + iMin;
        this.bytesRead = i2;
        int i3 = this.sampleSize;
        if (i2 == i3) {
            long j2 = this.timeUs;
            if (j2 != C.TIME_UNSET) {
                this.currentOutput.sampleMetadata(j2, 1, i3, 0, null);
                this.timeUs += this.currentSampleDuration;
            }
            setFindingSampleState();
        }
    }

    private void resetSync() {
        this.foundFirstFrame = false;
        setFindingSampleState();
    }

    private void setCheckingAdtsHeaderState() {
        this.state = 1;
        this.bytesRead = 0;
    }

    private void setFindingSampleState() {
        this.state = 0;
        this.bytesRead = 0;
        this.matchState = 256;
    }

    private void setReadingAdtsHeaderState() {
        this.state = 3;
        this.bytesRead = 0;
    }

    private void setReadingId3HeaderState() {
        this.state = 2;
        this.bytesRead = ID3_IDENTIFIER.length;
        this.sampleSize = 0;
        this.id3HeaderBuffer.setPosition(0);
    }

    private void setReadingSampleState(TrackOutput trackOutput, long j2, int i2, int i3) {
        this.state = 4;
        this.bytesRead = i2;
        this.currentOutput = trackOutput;
        this.currentSampleDuration = j2;
        this.sampleSize = i3;
    }

    private boolean tryRead(ParsableByteArray parsableByteArray, byte[] bArr, int i2) {
        if (parsableByteArray.bytesLeft() < i2) {
            return false;
        }
        parsableByteArray.readBytes(bArr, 0, i2);
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) throws ParserException {
        assertTracksCreated();
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.state;
            if (i2 == 0) {
                findNextSample(parsableByteArray);
            } else if (i2 == 1) {
                checkAdtsHeader(parsableByteArray);
            } else if (i2 != 2) {
                if (i2 == 3) {
                    if (continueRead(parsableByteArray, this.adtsScratch.data, this.hasCrc ? 7 : 5)) {
                        parseAdtsHeader();
                    }
                } else {
                    if (i2 != 4) {
                        throw new IllegalStateException();
                    }
                    readSample(parsableByteArray);
                }
            } else if (continueRead(parsableByteArray, this.id3HeaderBuffer.getData(), 10)) {
                parseId3Header();
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.formatId = trackIdGenerator.getFormatId();
        TrackOutput trackOutputTrack = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
        this.output = trackOutputTrack;
        this.currentOutput = trackOutputTrack;
        if (!this.exposeId3) {
            this.id3Output = new DummyTrackOutput();
            return;
        }
        trackIdGenerator.generateNewId();
        TrackOutput trackOutputTrack2 = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
        this.id3Output = trackOutputTrack2;
        trackOutputTrack2.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
    }

    public long getSampleDurationUs() {
        return this.sampleDurationUs;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetFinished() {
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void packetStarted(long j2, int i2) {
        if (j2 != C.TIME_UNSET) {
            this.timeUs = j2;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void seek() {
        this.timeUs = C.TIME_UNSET;
        resetSync();
    }

    public AdtsReader(boolean z2, @Nullable String str) {
        this.adtsScratch = new ParsableBitArray(new byte[7]);
        this.id3HeaderBuffer = new ParsableByteArray(Arrays.copyOf(ID3_IDENTIFIER, 10));
        setFindingSampleState();
        this.firstFrameVersion = -1;
        this.firstFrameSampleRateIndex = -1;
        this.sampleDurationUs = C.TIME_UNSET;
        this.timeUs = C.TIME_UNSET;
        this.exposeId3 = z2;
        this.language = str;
    }
}
