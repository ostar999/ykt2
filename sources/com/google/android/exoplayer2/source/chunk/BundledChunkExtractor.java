package com.google.android.exoplayer2.source.chunk;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public final class BundledChunkExtractor implements ExtractorOutput, ChunkExtractor {
    public static final ChunkExtractor.Factory FACTORY = new ChunkExtractor.Factory() { // from class: com.google.android.exoplayer2.source.chunk.a
        @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
        public final ChunkExtractor createProgressiveMediaExtractor(int i2, Format format, boolean z2, List list, TrackOutput trackOutput) {
            return BundledChunkExtractor.lambda$static$0(i2, format, z2, list, trackOutput);
        }
    };
    private static final PositionHolder POSITION_HOLDER = new PositionHolder();
    private final SparseArray<BindingTrackOutput> bindingTrackOutputs = new SparseArray<>();
    private long endTimeUs;
    private final Extractor extractor;
    private boolean extractorInitialized;
    private final Format primaryTrackManifestFormat;
    private final int primaryTrackType;
    private Format[] sampleFormats;
    private SeekMap seekMap;

    @Nullable
    private ChunkExtractor.TrackOutputProvider trackOutputProvider;

    public static final class BindingTrackOutput implements TrackOutput {
        private long endTimeUs;
        private final DummyTrackOutput fakeTrackOutput = new DummyTrackOutput();
        private final int id;

        @Nullable
        private final Format manifestFormat;
        public Format sampleFormat;
        private TrackOutput trackOutput;
        private final int type;

        public BindingTrackOutput(int i2, int i3, @Nullable Format format) {
            this.id = i2;
            this.type = i3;
            this.manifestFormat = format;
        }

        public void bind(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j2) {
            if (trackOutputProvider == null) {
                this.trackOutput = this.fakeTrackOutput;
                return;
            }
            this.endTimeUs = j2;
            TrackOutput trackOutputTrack = trackOutputProvider.track(this.id, this.type);
            this.trackOutput = trackOutputTrack;
            Format format = this.sampleFormat;
            if (format != null) {
                trackOutputTrack.format(format);
            }
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            Format format2 = this.manifestFormat;
            if (format2 != null) {
                format = format.withManifestFormatInfo(format2);
            }
            this.sampleFormat = format;
            ((TrackOutput) Util.castNonNull(this.trackOutput)).format(this.sampleFormat);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
            return com.google.android.exoplayer2.extractor.d.a(this, dataReader, i2, z2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
            return ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleData(dataReader, i2, z2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
            com.google.android.exoplayer2.extractor.d.b(this, parsableByteArray, i2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
            long j3 = this.endTimeUs;
            if (j3 != C.TIME_UNSET && j2 >= j3) {
                this.trackOutput = this.fakeTrackOutput;
            }
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleMetadata(j2, i2, i3, i4, cryptoData);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
            ((TrackOutput) Util.castNonNull(this.trackOutput)).sampleData(parsableByteArray, i2);
        }
    }

    public BundledChunkExtractor(Extractor extractor, int i2, Format format) {
        this.extractor = extractor;
        this.primaryTrackType = i2;
        this.primaryTrackManifestFormat = format;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ChunkExtractor lambda$static$0(int i2, Format format, boolean z2, List list, TrackOutput trackOutput) {
        Extractor fragmentedMp4Extractor;
        String str = format.containerMimeType;
        if (MimeTypes.isText(str)) {
            if (!MimeTypes.APPLICATION_RAWCC.equals(str)) {
                return null;
            }
            fragmentedMp4Extractor = new RawCcExtractor(format);
        } else if (MimeTypes.isMatroska(str)) {
            fragmentedMp4Extractor = new MatroskaExtractor(1);
        } else {
            fragmentedMp4Extractor = new FragmentedMp4Extractor(z2 ? 4 : 0, null, null, list, trackOutput);
        }
        return new BundledChunkExtractor(fragmentedMp4Extractor, i2, format);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        Format[] formatArr = new Format[this.bindingTrackOutputs.size()];
        for (int i2 = 0; i2 < this.bindingTrackOutputs.size(); i2++) {
            formatArr[i2] = (Format) Assertions.checkStateNotNull(this.bindingTrackOutputs.valueAt(i2).sampleFormat);
        }
        this.sampleFormats = formatArr;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public ChunkIndex getChunkIndex() {
        SeekMap seekMap = this.seekMap;
        if (seekMap instanceof ChunkIndex) {
            return (ChunkIndex) seekMap;
        }
        return null;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    @Nullable
    public Format[] getSampleFormats() {
        return this.sampleFormats;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void init(@Nullable ChunkExtractor.TrackOutputProvider trackOutputProvider, long j2, long j3) {
        this.trackOutputProvider = trackOutputProvider;
        this.endTimeUs = j3;
        if (!this.extractorInitialized) {
            this.extractor.init(this);
            if (j2 != C.TIME_UNSET) {
                this.extractor.seek(0L, j2);
            }
            this.extractorInitialized = true;
            return;
        }
        Extractor extractor = this.extractor;
        if (j2 == C.TIME_UNSET) {
            j2 = 0;
        }
        extractor.seek(0L, j2);
        for (int i2 = 0; i2 < this.bindingTrackOutputs.size(); i2++) {
            this.bindingTrackOutputs.valueAt(i2).bind(trackOutputProvider, j3);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        int i2 = this.extractor.read(extractorInput, POSITION_HOLDER);
        Assertions.checkState(i2 != 1);
        return i2 == 0;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor
    public void release() {
        this.extractor.release();
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
        this.seekMap = seekMap;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i2, int i3) {
        BindingTrackOutput bindingTrackOutput = this.bindingTrackOutputs.get(i2);
        if (bindingTrackOutput == null) {
            Assertions.checkState(this.sampleFormats == null);
            bindingTrackOutput = new BindingTrackOutput(i2, i3, i3 == this.primaryTrackType ? this.primaryTrackManifestFormat : null);
            bindingTrackOutput.bind(this.trackOutputProvider, this.endTimeUs);
            this.bindingTrackOutputs.put(i2, bindingTrackOutput);
        }
        return bindingTrackOutput;
    }
}
