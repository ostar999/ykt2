package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class DefaultSsChunkSource implements SsChunkSource {
    private final ChunkExtractor[] chunkExtractors;
    private int currentManifestChunkOffset;
    private final DataSource dataSource;

    @Nullable
    private IOException fatalError;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int streamElementIndex;
    private ExoTrackSelection trackSelection;

    public static final class Factory implements SsChunkSource.Factory {
        private final DataSource.Factory dataSourceFactory;

        public Factory(DataSource.Factory factory) {
            this.dataSourceFactory = factory;
        }

        @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource.Factory
        public SsChunkSource createChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i2, ExoTrackSelection exoTrackSelection, @Nullable TransferListener transferListener) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(loaderErrorThrower, ssManifest, i2, exoTrackSelection, dataSourceCreateDataSource);
        }
    }

    public static final class StreamElementIterator extends BaseMediaChunkIterator {
        private final SsManifest.StreamElement streamElement;
        private final int trackIndex;

        public StreamElementIterator(SsManifest.StreamElement streamElement, int i2, int i3) {
            super(i3, streamElement.chunkCount - 1);
            this.streamElement = streamElement;
            this.trackIndex = i2;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            return getChunkStartTimeUs() + this.streamElement.getChunkDurationUs((int) getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.streamElement.getStartTimeUs((int) getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            return new DataSpec(this.streamElement.buildRequestUri(this.trackIndex, (int) getCurrentIndex()));
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i2, ExoTrackSelection exoTrackSelection, DataSource dataSource) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = ssManifest;
        this.streamElementIndex = i2;
        this.trackSelection = exoTrackSelection;
        this.dataSource = dataSource;
        SsManifest.StreamElement streamElement = ssManifest.streamElements[i2];
        this.chunkExtractors = new ChunkExtractor[exoTrackSelection.length()];
        int i3 = 0;
        while (i3 < this.chunkExtractors.length) {
            int indexInTrackGroup = exoTrackSelection.getIndexInTrackGroup(i3);
            Format format = streamElement.formats[indexInTrackGroup];
            TrackEncryptionBox[] trackEncryptionBoxArr = format.drmInitData != null ? ((SsManifest.ProtectionElement) Assertions.checkNotNull(ssManifest.protectionElement)).trackEncryptionBoxes : null;
            int i4 = streamElement.type;
            int i5 = i3;
            this.chunkExtractors[i5] = new BundledChunkExtractor(new FragmentedMp4Extractor(3, null, new Track(indexInTrackGroup, i4, streamElement.timescale, C.TIME_UNSET, ssManifest.durationUs, format, 0, trackEncryptionBoxArr, i4 == 2 ? 4 : 0, null, null)), streamElement.type, format);
            i3 = i5 + 1;
        }
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource, Uri uri, int i2, long j2, long j3, long j4, int i3, @Nullable Object obj, ChunkExtractor chunkExtractor) {
        return new ContainerMediaChunk(dataSource, new DataSpec(uri), format, i3, obj, j2, j3, j4, C.TIME_UNSET, i2, 1, j2, chunkExtractor);
    }

    private long resolveTimeToLiveEdgeUs(long j2) {
        SsManifest ssManifest = this.manifest;
        if (!ssManifest.isLive) {
            return C.TIME_UNSET;
        }
        SsManifest.StreamElement streamElement = ssManifest.streamElements[this.streamElementIndex];
        int i2 = streamElement.chunkCount - 1;
        return (streamElement.getStartTimeUs(i2) + streamElement.getChunkDurationUs(i2)) - j2;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int chunkIndex = streamElement.getChunkIndex(j2);
        long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
        return seekParameters.resolveSeekPositionUs(j2, startTimeUs, (startTimeUs >= j2 || chunkIndex >= streamElement.chunkCount + (-1)) ? startTimeUs : streamElement.getStartTimeUs(chunkIndex + 1));
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public final void getNextChunk(long j2, long j3, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        int nextChunkIndex;
        long j4 = j3;
        if (this.fatalError != null) {
            return;
        }
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        if (streamElement.chunkCount == 0) {
            chunkHolder.endOfStream = !r4.isLive;
            return;
        }
        if (list.isEmpty()) {
            nextChunkIndex = streamElement.getChunkIndex(j4);
        } else {
            nextChunkIndex = (int) (list.get(list.size() - 1).getNextChunkIndex() - this.currentManifestChunkOffset);
            if (nextChunkIndex < 0) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
        }
        if (nextChunkIndex >= streamElement.chunkCount) {
            chunkHolder.endOfStream = !this.manifest.isLive;
            return;
        }
        long j5 = j4 - j2;
        long jResolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j2);
        int length = this.trackSelection.length();
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[length];
        for (int i2 = 0; i2 < length; i2++) {
            mediaChunkIteratorArr[i2] = new StreamElementIterator(streamElement, this.trackSelection.getIndexInTrackGroup(i2), nextChunkIndex);
        }
        this.trackSelection.updateSelectedTrack(j2, j5, jResolveTimeToLiveEdgeUs, list, mediaChunkIteratorArr);
        long startTimeUs = streamElement.getStartTimeUs(nextChunkIndex);
        long chunkDurationUs = startTimeUs + streamElement.getChunkDurationUs(nextChunkIndex);
        if (!list.isEmpty()) {
            j4 = C.TIME_UNSET;
        }
        long j6 = j4;
        int i3 = nextChunkIndex + this.currentManifestChunkOffset;
        int selectedIndex = this.trackSelection.getSelectedIndex();
        chunkHolder.chunk = newMediaChunk(this.trackSelection.getSelectedFormat(), this.dataSource, streamElement.buildRequestUri(this.trackSelection.getIndexInTrackGroup(selectedIndex), nextChunkIndex), i3, startTimeUs, chunkDurationUs, j6, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.chunkExtractors[selectedIndex]);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public int getPreferredQueueSize(long j2, List<? extends MediaChunk> list) {
        return (this.fatalError != null || this.trackSelection.length() < 2) ? list.size() : this.trackSelection.evaluateQueueSize(j2, list);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z2, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.trackSelection), loadErrorInfo);
        if (z2 && fallbackSelectionFor != null && fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            if (exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (ChunkExtractor chunkExtractor : this.chunkExtractors) {
            chunkExtractor.release();
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j2, chunk, list);
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
    public void updateManifest(SsManifest ssManifest) {
        SsManifest.StreamElement[] streamElementArr = this.manifest.streamElements;
        int i2 = this.streamElementIndex;
        SsManifest.StreamElement streamElement = streamElementArr[i2];
        int i3 = streamElement.chunkCount;
        SsManifest.StreamElement streamElement2 = ssManifest.streamElements[i2];
        if (i3 == 0 || streamElement2.chunkCount == 0) {
            this.currentManifestChunkOffset += i3;
        } else {
            int i4 = i3 - 1;
            long startTimeUs = streamElement.getStartTimeUs(i4) + streamElement.getChunkDurationUs(i4);
            long startTimeUs2 = streamElement2.getStartTimeUs(0);
            if (startTimeUs <= startTimeUs2) {
                this.currentManifestChunkOffset += i3;
            } else {
                this.currentManifestChunkOffset += streamElement.getChunkIndex(startTimeUs2);
            }
        }
        this.manifest = ssManifest;
    }

    @Override // com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.trackSelection = exoTrackSelection;
    }
}
