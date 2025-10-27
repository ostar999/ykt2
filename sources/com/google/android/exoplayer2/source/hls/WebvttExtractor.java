package com.google.android.exoplayer2.source.hls;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.webvtt.WebvttParserUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class WebvttExtractor implements Extractor {
    private static final int HEADER_MAX_LENGTH = 9;
    private static final int HEADER_MIN_LENGTH = 6;
    private static final Pattern LOCAL_TIMESTAMP = Pattern.compile("LOCAL:([^,]+)");
    private static final Pattern MEDIA_TIMESTAMP = Pattern.compile("MPEGTS:(-?\\d+)");

    @Nullable
    private final String language;
    private ExtractorOutput output;
    private int sampleSize;
    private final TimestampAdjuster timestampAdjuster;
    private final ParsableByteArray sampleDataWrapper = new ParsableByteArray();
    private byte[] sampleData = new byte[1024];

    public WebvttExtractor(@Nullable String str, TimestampAdjuster timestampAdjuster) {
        this.language = str;
        this.timestampAdjuster = timestampAdjuster;
    }

    @RequiresNonNull({"output"})
    private TrackOutput buildTrackOutput(long j2) {
        TrackOutput trackOutputTrack = this.output.track(0, 3);
        trackOutputTrack.format(new Format.Builder().setSampleMimeType(MimeTypes.TEXT_VTT).setLanguage(this.language).setSubsampleOffsetUs(j2).build());
        this.output.endTracks();
        return trackOutputTrack;
    }

    @RequiresNonNull({"output"})
    private void processSample() throws ParserException, NumberFormatException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(this.sampleData);
        WebvttParserUtil.validateWebvttHeaderLine(parsableByteArray);
        long jPtsToUs = 0;
        long timestampUs = 0;
        for (String line = parsableByteArray.readLine(); !TextUtils.isEmpty(line); line = parsableByteArray.readLine()) {
            if (line.startsWith("X-TIMESTAMP-MAP")) {
                Matcher matcher = LOCAL_TIMESTAMP.matcher(line);
                if (!matcher.find()) {
                    throw ParserException.createForMalformedContainer(line.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain local timestamp: ".concat(line) : new String("X-TIMESTAMP-MAP doesn't contain local timestamp: "), null);
                }
                Matcher matcher2 = MEDIA_TIMESTAMP.matcher(line);
                if (!matcher2.find()) {
                    throw ParserException.createForMalformedContainer(line.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain media timestamp: ".concat(line) : new String("X-TIMESTAMP-MAP doesn't contain media timestamp: "), null);
                }
                timestampUs = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcher.group(1)));
                jPtsToUs = TimestampAdjuster.ptsToUs(Long.parseLong((String) Assertions.checkNotNull(matcher2.group(1))));
            }
        }
        Matcher matcherFindNextCueHeader = WebvttParserUtil.findNextCueHeader(parsableByteArray);
        if (matcherFindNextCueHeader == null) {
            buildTrackOutput(0L);
            return;
        }
        long timestampUs2 = WebvttParserUtil.parseTimestampUs((String) Assertions.checkNotNull(matcherFindNextCueHeader.group(1)));
        long jAdjustTsTimestamp = this.timestampAdjuster.adjustTsTimestamp(TimestampAdjuster.usToWrappedPts((jPtsToUs + timestampUs2) - timestampUs));
        TrackOutput trackOutputBuildTrackOutput = buildTrackOutput(jAdjustTsTimestamp - timestampUs2);
        this.sampleDataWrapper.reset(this.sampleData, this.sampleSize);
        trackOutputBuildTrackOutput.sampleData(this.sampleDataWrapper, this.sampleSize);
        trackOutputBuildTrackOutput.sampleMetadata(jAdjustTsTimestamp, 1, this.sampleSize, 0, null);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
        extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, NumberFormatException {
        Assertions.checkNotNull(this.output);
        int length = (int) extractorInput.getLength();
        int i2 = this.sampleSize;
        byte[] bArr = this.sampleData;
        if (i2 == bArr.length) {
            this.sampleData = Arrays.copyOf(bArr, ((length != -1 ? length : bArr.length) * 3) / 2);
        }
        byte[] bArr2 = this.sampleData;
        int i3 = this.sampleSize;
        int i4 = extractorInput.read(bArr2, i3, bArr2.length - i3);
        if (i4 != -1) {
            int i5 = this.sampleSize + i4;
            this.sampleSize = i5;
            if (length == -1 || i5 != length) {
                return 0;
            }
        }
        processSample();
        return -1;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j2, long j3) {
        throw new IllegalStateException();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        extractorInput.peekFully(this.sampleData, 0, 6, false);
        this.sampleDataWrapper.reset(this.sampleData, 6);
        if (WebvttParserUtil.isWebvttHeaderLine(this.sampleDataWrapper)) {
            return true;
        }
        extractorInput.peekFully(this.sampleData, 6, 3, false);
        this.sampleDataWrapper.reset(this.sampleData, 9);
        return WebvttParserUtil.isWebvttHeaderLine(this.sampleDataWrapper);
    }
}
