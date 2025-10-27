package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class BundledExtractorsAdapter implements ProgressiveMediaExtractor {

    @Nullable
    private Extractor extractor;

    @Nullable
    private ExtractorInput extractorInput;
    private final ExtractorsFactory extractorsFactory;

    public BundledExtractorsAdapter(ExtractorsFactory extractorsFactory) {
        this.extractorsFactory = extractorsFactory;
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void disableSeekingOnMp3Streams() {
        Extractor extractor = this.extractor;
        if (extractor instanceof Mp3Extractor) {
            ((Mp3Extractor) extractor).disableSeeking();
        }
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public long getCurrentInputPosition() {
        ExtractorInput extractorInput = this.extractorInput;
        if (extractorInput != null) {
            return extractorInput.getPosition();
        }
        return -1L;
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void init(DataReader dataReader, Uri uri, Map<String, List<String>> map, long j2, long j3, ExtractorOutput extractorOutput) throws IOException {
        DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(dataReader, j2, j3);
        this.extractorInput = defaultExtractorInput;
        if (this.extractor != null) {
            return;
        }
        Extractor[] extractorArrCreateExtractors = this.extractorsFactory.createExtractors(uri, map);
        if (extractorArrCreateExtractors.length == 1) {
            this.extractor = extractorArrCreateExtractors[0];
        } else {
            int length = extractorArrCreateExtractors.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                Extractor extractor = extractorArrCreateExtractors[i2];
                try {
                } catch (EOFException unused) {
                    if (this.extractor != null || defaultExtractorInput.getPosition() == j2) {
                    }
                } catch (Throwable th) {
                    Assertions.checkState(this.extractor != null || defaultExtractorInput.getPosition() == j2);
                    defaultExtractorInput.resetPeekPosition();
                    throw th;
                }
                if (extractor.sniff(defaultExtractorInput)) {
                    this.extractor = extractor;
                    Assertions.checkState(true);
                    defaultExtractorInput.resetPeekPosition();
                    break;
                } else {
                    boolean z2 = this.extractor != null || defaultExtractorInput.getPosition() == j2;
                    Assertions.checkState(z2);
                    defaultExtractorInput.resetPeekPosition();
                    i2++;
                }
            }
            if (this.extractor == null) {
                String commaDelimitedSimpleClassNames = Util.getCommaDelimitedSimpleClassNames(extractorArrCreateExtractors);
                StringBuilder sb = new StringBuilder(String.valueOf(commaDelimitedSimpleClassNames).length() + 58);
                sb.append("None of the available extractors (");
                sb.append(commaDelimitedSimpleClassNames);
                sb.append(") could read the stream.");
                throw new UnrecognizedInputFormatException(sb.toString(), (Uri) Assertions.checkNotNull(uri));
            }
        }
        this.extractor.init(extractorOutput);
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public int read(PositionHolder positionHolder) throws IOException {
        return ((Extractor) Assertions.checkNotNull(this.extractor)).read((ExtractorInput) Assertions.checkNotNull(this.extractorInput), positionHolder);
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void release() {
        Extractor extractor = this.extractor;
        if (extractor != null) {
            extractor.release();
            this.extractor = null;
        }
        this.extractorInput = null;
    }

    @Override // com.google.android.exoplayer2.source.ProgressiveMediaExtractor
    public void seek(long j2, long j3) {
        ((Extractor) Assertions.checkNotNull(this.extractor)).seek(j2, j3);
    }
}
