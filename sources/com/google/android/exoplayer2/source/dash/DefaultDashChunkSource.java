package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;

    @Nullable
    private IOException fatalError;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;

    @Nullable
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
    protected final RepresentationHolder[] representationHolders;
    private ExoTrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements DashChunkSource.Factory {
        private final ChunkExtractor.Factory chunkExtractorFactory;
        private final DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(DataSource.Factory factory) {
            this(factory, 1);
        }

        @Override // com.google.android.exoplayer2.source.dash.DashChunkSource.Factory
        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i2, int[] iArr, ExoTrackSelection exoTrackSelection, int i3, long j2, boolean z2, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, @Nullable TransferListener transferListener) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultDashChunkSource(this.chunkExtractorFactory, loaderErrorThrower, dashManifest, baseUrlExclusionList, i2, iArr, exoTrackSelection, i3, dataSourceCreateDataSource, j2, this.maxSegmentsPerLoad, z2, list, playerTrackEmsgHandler);
        }

        public Factory(DataSource.Factory factory, int i2) {
            this(BundledChunkExtractor.FACTORY, factory, i2);
        }

        public Factory(ChunkExtractor.Factory factory, DataSource.Factory factory2, int i2) {
            this.chunkExtractorFactory = factory;
            this.dataSourceFactory = factory2;
            this.maxSegmentsPerLoad = i2;
        }
    }

    public static final class RepresentationHolder {

        @Nullable
        final ChunkExtractor chunkExtractor;
        private final long periodDurationUs;
        public final Representation representation;

        @Nullable
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;
        public final BaseUrl selectedBaseUrl;

        public RepresentationHolder(long j2, Representation representation, BaseUrl baseUrl, @Nullable ChunkExtractor chunkExtractor, long j3, @Nullable DashSegmentIndex dashSegmentIndex) {
            this.periodDurationUs = j2;
            this.representation = representation;
            this.selectedBaseUrl = baseUrl;
            this.segmentNumShift = j3;
            this.chunkExtractor = chunkExtractor;
            this.segmentIndex = dashSegmentIndex;
        }

        @CheckResult
        public RepresentationHolder copyWithNewRepresentation(long j2, Representation representation) throws BehindLiveWindowException {
            long segmentNum;
            long segmentNum2;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            if (index == null) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index);
            }
            if (!index.isExplicit()) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long segmentCount = index.getSegmentCount(j2);
            if (segmentCount == 0) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, index2);
            }
            long firstSegmentNum = index.getFirstSegmentNum();
            long timeUs = index.getTimeUs(firstSegmentNum);
            long j3 = (segmentCount + firstSegmentNum) - 1;
            long timeUs2 = index.getTimeUs(j3) + index.getDurationUs(j3, j2);
            long firstSegmentNum2 = index2.getFirstSegmentNum();
            long timeUs3 = index2.getTimeUs(firstSegmentNum2);
            long j4 = this.segmentNumShift;
            if (timeUs2 == timeUs3) {
                segmentNum = j3 + 1;
            } else {
                if (timeUs2 < timeUs3) {
                    throw new BehindLiveWindowException();
                }
                if (timeUs3 < timeUs) {
                    segmentNum2 = j4 - (index2.getSegmentNum(timeUs, j2) - firstSegmentNum);
                    return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
                }
                segmentNum = index.getSegmentNum(timeUs3, j2);
            }
            segmentNum2 = j4 + (segmentNum - firstSegmentNum2);
            return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.chunkExtractor, segmentNum2, index2);
        }

        @CheckResult
        public RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex dashSegmentIndex) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.selectedBaseUrl, this.chunkExtractor, this.segmentNumShift, dashSegmentIndex);
        }

        @CheckResult
        public RepresentationHolder copyWithNewSelectedBaseUrl(BaseUrl baseUrl) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, baseUrl, this.chunkExtractor, this.segmentNumShift, this.segmentIndex);
        }

        public long getFirstAvailableSegmentNum(long j2) {
            return this.segmentIndex.getFirstAvailableSegmentNum(this.periodDurationUs, j2) + this.segmentNumShift;
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public long getLastAvailableSegmentNum(long j2) {
            return (getFirstAvailableSegmentNum(j2) + this.segmentIndex.getAvailableSegmentCount(this.periodDurationUs, j2)) - 1;
        }

        public long getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentEndTimeUs(long j2) {
            return getSegmentStartTimeUs(j2) + this.segmentIndex.getDurationUs(j2 - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long j2) {
            return this.segmentIndex.getSegmentNum(j2, this.periodDurationUs) + this.segmentNumShift;
        }

        public long getSegmentStartTimeUs(long j2) {
            return this.segmentIndex.getTimeUs(j2 - this.segmentNumShift);
        }

        public RangedUri getSegmentUrl(long j2) {
            return this.segmentIndex.getSegmentUrl(j2 - this.segmentNumShift);
        }

        public boolean isSegmentAvailableAtFullNetworkSpeed(long j2, long j3) {
            return this.segmentIndex.isExplicit() || j3 == C.TIME_UNSET || getSegmentEndTimeUs(j2) <= j3;
        }
    }

    public static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final long nowPeriodTimeUs;
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder, long j2, long j3, long j4) {
            super(j2, j3);
            this.representationHolder = representationHolder;
            this.nowPeriodTimeUs = j4;
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        @Override // com.google.android.exoplayer2.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            checkInBounds();
            long currentIndex = getCurrentIndex();
            RangedUri segmentUrl = this.representationHolder.getSegmentUrl(currentIndex);
            int i2 = this.representationHolder.isSegmentAvailableAtFullNetworkSpeed(currentIndex, this.nowPeriodTimeUs) ? 0 : 8;
            RepresentationHolder representationHolder = this.representationHolder;
            return DashUtil.buildDataSpec(representationHolder.representation, representationHolder.selectedBaseUrl.url, segmentUrl, i2);
        }
    }

    public DefaultDashChunkSource(ChunkExtractor.Factory factory, LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i2, int[] iArr, ExoTrackSelection exoTrackSelection, int i3, DataSource dataSource, long j2, int i4, boolean z2, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.adaptationSetIndices = iArr;
        this.trackSelection = exoTrackSelection;
        this.trackType = i3;
        this.dataSource = dataSource;
        this.periodIndex = i2;
        this.elapsedRealtimeOffsetMs = j2;
        this.maxSegmentsPerLoad = i4;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i2);
        ArrayList<Representation> representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[exoTrackSelection.length()];
        int i5 = 0;
        while (i5 < this.representationHolders.length) {
            Representation representation = representations.get(exoTrackSelection.getIndexInTrackGroup(i5));
            BaseUrl baseUrlSelectBaseUrl = baseUrlExclusionList.selectBaseUrl(representation.baseUrls);
            RepresentationHolder[] representationHolderArr = this.representationHolders;
            if (baseUrlSelectBaseUrl == null) {
                baseUrlSelectBaseUrl = representation.baseUrls.get(0);
            }
            int i6 = i5;
            representationHolderArr[i6] = new RepresentationHolder(periodDurationUs, representation, baseUrlSelectBaseUrl, BundledChunkExtractor.FACTORY.createProgressiveMediaExtractor(i3, representation.format, z2, list, playerTrackEmsgHandler), 0L, representation.getIndex());
            i5 = i6 + 1;
        }
    }

    private LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection, List<BaseUrl> list) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (exoTrackSelection.isBlacklisted(i3, jElapsedRealtime)) {
                i2++;
            }
        }
        int priorityCount = BaseUrlExclusionList.getPriorityCount(list);
        return new LoadErrorHandlingPolicy.FallbackOptions(priorityCount, priorityCount - this.baseUrlExclusionList.getPriorityCountAfterExclusion(list), length, i2);
    }

    private long getAvailableLiveDurationUs(long j2, long j3) {
        if (!this.manifest.dynamic) {
            return C.TIME_UNSET;
        }
        return Math.max(0L, Math.min(getNowPeriodTimeUs(j2), this.representationHolders[0].getSegmentEndTimeUs(this.representationHolders[0].getLastAvailableSegmentNum(j2))) - j3);
    }

    private long getNowPeriodTimeUs(long j2) {
        DashManifest dashManifest = this.manifest;
        long j3 = dashManifest.availabilityStartTimeMs;
        return j3 == C.TIME_UNSET ? C.TIME_UNSET : j2 - Util.msToUs(j3 + dashManifest.getPeriod(this.periodIndex).startMs);
    }

    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i2 : this.adaptationSetIndices) {
            arrayList.addAll(list.get(i2).representations);
        }
        return arrayList;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, @Nullable MediaChunk mediaChunk, long j2, long j3, long j4) {
        return mediaChunk != null ? mediaChunk.getNextChunkIndex() : Util.constrainValue(representationHolder.getSegmentNum(j2), j3, j4);
    }

    private RepresentationHolder updateSelectedBaseUrl(int i2) {
        RepresentationHolder representationHolder = this.representationHolders[i2];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder.representation.baseUrls);
        if (baseUrlSelectBaseUrl == null || baseUrlSelectBaseUrl.equals(representationHolder.selectedBaseUrl)) {
            return representationHolder;
        }
        RepresentationHolder representationHolderCopyWithNewSelectedBaseUrl = representationHolder.copyWithNewSelectedBaseUrl(baseUrlSelectBaseUrl);
        this.representationHolders[i2] = representationHolderCopyWithNewSelectedBaseUrl;
        return representationHolderCopyWithNewSelectedBaseUrl;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(j2);
                long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                long segmentCount = representationHolder.getSegmentCount();
                return seekParameters.resolveSeekPositionUs(j2, segmentStartTimeUs, (segmentStartTimeUs >= j2 || (segmentCount != -1 && segmentNum >= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
            }
        }
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void getNextChunk(long j2, long j3, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        int i2;
        int i3;
        MediaChunkIterator[] mediaChunkIteratorArr;
        long j4;
        long j5;
        if (this.fatalError != null) {
            return;
        }
        long j6 = j3 - j2;
        long jMsToUs = Util.msToUs(this.manifest.availabilityStartTimeMs) + Util.msToUs(this.manifest.getPeriod(this.periodIndex).startMs) + j3;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler == null || !playerTrackEmsgHandler.maybeRefreshManifestBeforeLoadingNextChunk(jMsToUs)) {
            long jMsToUs2 = Util.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
            long nowPeriodTimeUs = getNowPeriodTimeUs(jMsToUs2);
            MediaChunk mediaChunk = list.isEmpty() ? null : list.get(list.size() - 1);
            int length = this.trackSelection.length();
            MediaChunkIterator[] mediaChunkIteratorArr2 = new MediaChunkIterator[length];
            int i4 = 0;
            while (i4 < length) {
                RepresentationHolder representationHolder = this.representationHolders[i4];
                if (representationHolder.segmentIndex == null) {
                    mediaChunkIteratorArr2[i4] = MediaChunkIterator.EMPTY;
                    i2 = i4;
                    i3 = length;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    j4 = j6;
                    j5 = jMsToUs2;
                } else {
                    long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(jMsToUs2);
                    long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(jMsToUs2);
                    i2 = i4;
                    i3 = length;
                    mediaChunkIteratorArr = mediaChunkIteratorArr2;
                    j4 = j6;
                    j5 = jMsToUs2;
                    long segmentNum = getSegmentNum(representationHolder, mediaChunk, j3, firstAvailableSegmentNum, lastAvailableSegmentNum);
                    if (segmentNum < firstAvailableSegmentNum) {
                        mediaChunkIteratorArr[i2] = MediaChunkIterator.EMPTY;
                    } else {
                        mediaChunkIteratorArr[i2] = new RepresentationSegmentIterator(updateSelectedBaseUrl(i2), segmentNum, lastAvailableSegmentNum, nowPeriodTimeUs);
                    }
                }
                i4 = i2 + 1;
                jMsToUs2 = j5;
                mediaChunkIteratorArr2 = mediaChunkIteratorArr;
                length = i3;
                j6 = j4;
            }
            long j7 = j6;
            long j8 = jMsToUs2;
            this.trackSelection.updateSelectedTrack(j2, j7, getAvailableLiveDurationUs(j8, j2), list, mediaChunkIteratorArr2);
            RepresentationHolder representationHolderUpdateSelectedBaseUrl = updateSelectedBaseUrl(this.trackSelection.getSelectedIndex());
            ChunkExtractor chunkExtractor = representationHolderUpdateSelectedBaseUrl.chunkExtractor;
            if (chunkExtractor != null) {
                Representation representation = representationHolderUpdateSelectedBaseUrl.representation;
                RangedUri initializationUri = chunkExtractor.getSampleFormats() == null ? representation.getInitializationUri() : null;
                RangedUri indexUri = representationHolderUpdateSelectedBaseUrl.segmentIndex == null ? representation.getIndexUri() : null;
                if (initializationUri != null || indexUri != null) {
                    chunkHolder.chunk = newInitializationChunk(representationHolderUpdateSelectedBaseUrl, this.dataSource, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), initializationUri, indexUri);
                    return;
                }
            }
            long j9 = representationHolderUpdateSelectedBaseUrl.periodDurationUs;
            long j10 = C.TIME_UNSET;
            boolean z2 = j9 != C.TIME_UNSET;
            if (representationHolderUpdateSelectedBaseUrl.getSegmentCount() == 0) {
                chunkHolder.endOfStream = z2;
                return;
            }
            long firstAvailableSegmentNum2 = representationHolderUpdateSelectedBaseUrl.getFirstAvailableSegmentNum(j8);
            long lastAvailableSegmentNum2 = representationHolderUpdateSelectedBaseUrl.getLastAvailableSegmentNum(j8);
            long segmentNum2 = getSegmentNum(representationHolderUpdateSelectedBaseUrl, mediaChunk, j3, firstAvailableSegmentNum2, lastAvailableSegmentNum2);
            if (segmentNum2 < firstAvailableSegmentNum2) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
            if (segmentNum2 > lastAvailableSegmentNum2 || (this.missingLastSegment && segmentNum2 >= lastAvailableSegmentNum2)) {
                chunkHolder.endOfStream = z2;
                return;
            }
            if (z2 && representationHolderUpdateSelectedBaseUrl.getSegmentStartTimeUs(segmentNum2) >= j9) {
                chunkHolder.endOfStream = true;
                return;
            }
            int iMin = (int) Math.min(this.maxSegmentsPerLoad, (lastAvailableSegmentNum2 - segmentNum2) + 1);
            if (j9 != C.TIME_UNSET) {
                while (iMin > 1 && representationHolderUpdateSelectedBaseUrl.getSegmentStartTimeUs((iMin + segmentNum2) - 1) >= j9) {
                    iMin--;
                }
            }
            int i5 = iMin;
            if (list.isEmpty()) {
                j10 = j3;
            }
            chunkHolder.chunk = newMediaChunk(representationHolderUpdateSelectedBaseUrl, this.dataSource, this.trackType, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), segmentNum2, i5, j10, nowPeriodTimeUs);
        }
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

    public Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource, Format format, int i2, @Nullable Object obj, @Nullable RangedUri rangedUri, @Nullable RangedUri rangedUri2) {
        RangedUri rangedUri3 = rangedUri;
        Representation representation = representationHolder.representation;
        if (rangedUri3 != null) {
            RangedUri rangedUriAttemptMerge = rangedUri3.attemptMerge(rangedUri2, representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge != null) {
                rangedUri3 = rangedUriAttemptMerge;
            }
        } else {
            rangedUri3 = rangedUri2;
        }
        return new InitializationChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, rangedUri3, 0), format, i2, obj, representationHolder.chunkExtractor);
    }

    public Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource, int i2, Format format, int i3, Object obj, long j2, int i4, long j3, long j4) {
        Representation representation = representationHolder.representation;
        long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(j2);
        RangedUri segmentUrl = representationHolder.getSegmentUrl(j2);
        if (representationHolder.chunkExtractor == null) {
            return new SingleSampleMediaChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, segmentUrl, representationHolder.isSegmentAvailableAtFullNetworkSpeed(j2, j4) ? 0 : 8), format, i3, obj, segmentStartTimeUs, representationHolder.getSegmentEndTimeUs(j2), j2, i2, format);
        }
        int i5 = 1;
        int i6 = 1;
        while (i5 < i4) {
            RangedUri rangedUriAttemptMerge = segmentUrl.attemptMerge(representationHolder.getSegmentUrl(i5 + j2), representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge == null) {
                break;
            }
            i6++;
            i5++;
            segmentUrl = rangedUriAttemptMerge;
        }
        long j5 = (i6 + j2) - 1;
        long segmentEndTimeUs = representationHolder.getSegmentEndTimeUs(j5);
        long j6 = representationHolder.periodDurationUs;
        return new ContainerMediaChunk(dataSource, DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, segmentUrl, representationHolder.isSegmentAvailableAtFullNetworkSpeed(j5, j4) ? 0 : 8), format, i3, obj, segmentStartTimeUs, segmentEndTimeUs, j3, (j6 == C.TIME_UNSET || j6 > segmentEndTimeUs) ? -9223372036854775807L : j6, j2, i6, -representation.presentationTimeOffsetUs, representationHolder.chunkExtractor);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
        ChunkIndex chunkIndex;
        if (chunk instanceof InitializationChunk) {
            int iIndexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[iIndexOf];
            if (representationHolder.segmentIndex == null && (chunkIndex = representationHolder.chunkExtractor.getChunkIndex()) != null) {
                this.representationHolders[iIndexOf] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex(chunkIndex, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z2, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!z2) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null && playerTrackEmsgHandler.onChunkLoadError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk)) {
            IOException iOException = loadErrorInfo.exception;
            if ((iOException instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode == 404) {
                RepresentationHolder representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
                long segmentCount = representationHolder.getSegmentCount();
                if (segmentCount != -1 && segmentCount != 0) {
                    if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + segmentCount) - 1) {
                        this.missingLastSegment = true;
                        return true;
                    }
                }
            }
        }
        RepresentationHolder representationHolder2 = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder2.representation.baseUrls);
        if (baseUrlSelectBaseUrl != null && !representationHolder2.selectedBaseUrl.equals(baseUrlSelectBaseUrl)) {
            return true;
        }
        LoadErrorHandlingPolicy.FallbackOptions fallbackOptionsCreateFallbackOptions = createFallbackOptions(this.trackSelection, representationHolder2.representation.baseUrls);
        if ((!fallbackOptionsCreateFallbackOptions.isFallbackAvailable(2) && !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(1)) || (fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(fallbackOptionsCreateFallbackOptions, loadErrorInfo)) == null || !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(fallbackSelectionFor.type)) {
            return false;
        }
        int i2 = fallbackSelectionFor.type;
        if (i2 == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            return exoTrackSelection.blacklist(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs);
        }
        if (i2 != 1) {
            return false;
        }
        this.baseUrlExclusionList.exclude(representationHolder2.selectedBaseUrl, fallbackSelectionFor.exclusionDurationMs);
        return true;
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public void release() {
        for (RepresentationHolder representationHolder : this.representationHolders) {
            ChunkExtractor chunkExtractor = representationHolder.chunkExtractor;
            if (chunkExtractor != null) {
                chunkExtractor.release();
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j2, chunk, list);
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateManifest(DashManifest dashManifest, int i2) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i2;
            long periodDurationUs = dashManifest.getPeriodDurationUs(i2);
            ArrayList<Representation> representations = getRepresentations();
            for (int i3 = 0; i3 < this.representationHolders.length; i3++) {
                Representation representation = representations.get(this.trackSelection.getIndexInTrackGroup(i3));
                RepresentationHolder[] representationHolderArr = this.representationHolders;
                representationHolderArr[i3] = representationHolderArr[i3].copyWithNewRepresentation(periodDurationUs, representation);
            }
        } catch (BehindLiveWindowException e2) {
            this.fatalError = e2;
        }
    }

    @Override // com.google.android.exoplayer2.source.dash.DashChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.trackSelection = exoTrackSelection;
    }
}
