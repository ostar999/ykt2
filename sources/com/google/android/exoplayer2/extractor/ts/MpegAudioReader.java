package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.MpegAudioUtil;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class MpegAudioReader implements ElementaryStreamReader {
    private static final int HEADER_SIZE = 4;
    private static final int STATE_FINDING_HEADER = 0;
    private static final int STATE_READING_FRAME = 2;
    private static final int STATE_READING_HEADER = 1;
    private String formatId;
    private int frameBytesRead;
    private long frameDurationUs;
    private int frameSize;
    private boolean hasOutputFormat;
    private final MpegAudioUtil.Header header;
    private final ParsableByteArray headerScratch;

    @Nullable
    private final String language;
    private boolean lastByteWasFF;
    private TrackOutput output;
    private int state;
    private long timeUs;

    public MpegAudioReader() {
        this(null);
    }

    private void findHeader(ParsableByteArray parsableByteArray) {
        byte[] data = parsableByteArray.getData();
        int iLimit = parsableByteArray.limit();
        for (int position = parsableByteArray.getPosition(); position < iLimit; position++) {
            byte b3 = data[position];
            boolean z2 = (b3 & 255) == 255;
            boolean z3 = this.lastByteWasFF && (b3 & 224) == 224;
            this.lastByteWasFF = z2;
            if (z3) {
                parsableByteArray.setPosition(position + 1);
                this.lastByteWasFF = false;
                this.headerScratch.getData()[1] = data[position];
                this.frameBytesRead = 2;
                this.state = 1;
                return;
            }
        }
        parsableByteArray.setPosition(iLimit);
    }

    @RequiresNonNull({"output"})
    private void readFrameRemainder(ParsableByteArray parsableByteArray) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), this.frameSize - this.frameBytesRead);
        this.output.sampleData(parsableByteArray, iMin);
        int i2 = this.frameBytesRead + iMin;
        this.frameBytesRead = i2;
        int i3 = this.frameSize;
        if (i2 < i3) {
            return;
        }
        long j2 = this.timeUs;
        if (j2 != C.TIME_UNSET) {
            this.output.sampleMetadata(j2, 1, i3, 0, null);
            this.timeUs += this.frameDurationUs;
        }
        this.frameBytesRead = 0;
        this.state = 0;
    }

    @RequiresNonNull({"output"})
    private void readHeaderRemainder(ParsableByteArray parsableByteArray) {
        int iMin = Math.min(parsableByteArray.bytesLeft(), 4 - this.frameBytesRead);
        parsableByteArray.readBytes(this.headerScratch.getData(), this.frameBytesRead, iMin);
        int i2 = this.frameBytesRead + iMin;
        this.frameBytesRead = i2;
        if (i2 < 4) {
            return;
        }
        this.headerScratch.setPosition(0);
        if (!this.header.setForHeaderData(this.headerScratch.readInt())) {
            this.frameBytesRead = 0;
            this.state = 1;
            return;
        }
        this.frameSize = this.header.frameSize;
        if (!this.hasOutputFormat) {
            this.frameDurationUs = (r8.samplesPerFrame * 1000000) / r8.sampleRate;
            this.output.format(new Format.Builder().setId(this.formatId).setSampleMimeType(this.header.mimeType).setMaxInputSize(4096).setChannelCount(this.header.channels).setSampleRate(this.header.sampleRate).setLanguage(this.language).build());
            this.hasOutputFormat = true;
        }
        this.headerScratch.setPosition(0);
        this.output.sampleData(this.headerScratch, 4);
        this.state = 2;
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.output);
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.state;
            if (i2 == 0) {
                findHeader(parsableByteArray);
            } else if (i2 == 1) {
                readHeaderRemainder(parsableByteArray);
            } else {
                if (i2 != 2) {
                    throw new IllegalStateException();
                }
                readFrameRemainder(parsableByteArray);
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.formatId = trackIdGenerator.getFormatId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
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
        this.state = 0;
        this.frameBytesRead = 0;
        this.lastByteWasFF = false;
        this.timeUs = C.TIME_UNSET;
    }

    public MpegAudioReader(@Nullable String str) {
        this.state = 0;
        ParsableByteArray parsableByteArray = new ParsableByteArray(4);
        this.headerScratch = parsableByteArray;
        parsableByteArray.getData()[0] = -1;
        this.header = new MpegAudioUtil.Header();
        this.timeUs = C.TIME_UNSET;
        this.language = str;
    }
}
