package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.IcyDataSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSourceUtil;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes3.dex */
final class ProgressiveMediaPeriod implements MediaPeriod, ExtractorOutput, Loader.Callback<ExtractingLoadable>, Loader.ReleaseCallback, SampleQueue.UpstreamFormatChangedListener {
    private static final long DEFAULT_LAST_SAMPLE_DURATION_US = 10000;
    private final Allocator allocator;

    @Nullable
    private MediaPeriod.Callback callback;
    private final long continueLoadingCheckIntervalBytes;

    @Nullable
    private final String customCacheKey;
    private final DataSource dataSource;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private int enabledTrackCount;
    private int extractedSamplesCountAtStartOfLoad;
    private boolean haveAudioVideoTracks;

    @Nullable
    private IcyHeaders icyHeaders;
    private boolean isLive;
    private long lastSeekPositionUs;
    private final Listener listener;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private boolean loadingFinished;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private boolean notifyDiscontinuity;
    private boolean pendingDeferredRetry;
    private boolean prepared;
    private final ProgressiveMediaExtractor progressiveMediaExtractor;
    private boolean released;
    private boolean sampleQueuesBuilt;
    private SeekMap seekMap;
    private boolean seenFirstTrackSelection;
    private TrackState trackState;
    private final Uri uri;
    private static final Map<String, String> ICY_METADATA_HEADERS = createIcyMetadataHeaders();
    private static final Format ICY_FORMAT = new Format.Builder().setId("icy").setSampleMimeType(MimeTypes.APPLICATION_ICY).build();
    private final Loader loader = new Loader("ProgressiveMediaPeriod");
    private final ConditionVariable loadCondition = new ConditionVariable();
    private final Runnable maybeFinishPrepareRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.a0
        @Override // java.lang.Runnable
        public final void run() {
            this.f6844c.maybeFinishPrepare();
        }
    };
    private final Runnable onContinueLoadingRequestedRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.b0
        @Override // java.lang.Runnable
        public final void run() {
            this.f6859c.lambda$new$0();
        }
    };
    private final Handler handler = Util.createHandlerForCurrentLooper();
    private TrackId[] sampleQueueTrackIds = new TrackId[0];
    private SampleQueue[] sampleQueues = new SampleQueue[0];
    private long pendingResetPositionUs = C.TIME_UNSET;
    private long length = -1;
    private long durationUs = C.TIME_UNSET;
    private int dataType = 1;

    public final class ExtractingLoadable implements Loader.Loadable, IcyDataSource.Listener {
        private final StatsDataSource dataSource;
        private final ExtractorOutput extractorOutput;

        @Nullable
        private TrackOutput icyTrackOutput;
        private volatile boolean loadCanceled;
        private final ConditionVariable loadCondition;
        private final ProgressiveMediaExtractor progressiveMediaExtractor;
        private long seekTimeUs;
        private boolean seenIcyMetadata;
        private final Uri uri;
        private final PositionHolder positionHolder = new PositionHolder();
        private boolean pendingExtractorSeek = true;
        private long length = -1;
        private final long loadTaskId = LoadEventInfo.getNewId();
        private DataSpec dataSpec = buildDataSpec(0);

        public ExtractingLoadable(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, ExtractorOutput extractorOutput, ConditionVariable conditionVariable) {
            this.uri = uri;
            this.dataSource = new StatsDataSource(dataSource);
            this.progressiveMediaExtractor = progressiveMediaExtractor;
            this.extractorOutput = extractorOutput;
            this.loadCondition = conditionVariable;
        }

        private DataSpec buildDataSpec(long j2) {
            return new DataSpec.Builder().setUri(this.uri).setPosition(j2).setKey(ProgressiveMediaPeriod.this.customCacheKey).setFlags(6).setHttpRequestHeaders(ProgressiveMediaPeriod.ICY_METADATA_HEADERS).build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLoadPosition(long j2, long j3) {
            this.positionHolder.position = j2;
            this.seekTimeUs = j3;
            this.pendingExtractorSeek = true;
            this.seenIcyMetadata = false;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
            this.loadCanceled = true;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            int i2 = 0;
            while (i2 == 0 && !this.loadCanceled) {
                try {
                    long j2 = this.positionHolder.position;
                    DataSpec dataSpecBuildDataSpec = buildDataSpec(j2);
                    this.dataSpec = dataSpecBuildDataSpec;
                    long jOpen = this.dataSource.open(dataSpecBuildDataSpec);
                    this.length = jOpen;
                    if (jOpen != -1) {
                        this.length = jOpen + j2;
                    }
                    ProgressiveMediaPeriod.this.icyHeaders = IcyHeaders.parse(this.dataSource.getResponseHeaders());
                    DataReader icyDataSource = this.dataSource;
                    if (ProgressiveMediaPeriod.this.icyHeaders != null && ProgressiveMediaPeriod.this.icyHeaders.metadataInterval != -1) {
                        icyDataSource = new IcyDataSource(this.dataSource, ProgressiveMediaPeriod.this.icyHeaders.metadataInterval, this);
                        TrackOutput trackOutputIcyTrack = ProgressiveMediaPeriod.this.icyTrack();
                        this.icyTrackOutput = trackOutputIcyTrack;
                        trackOutputIcyTrack.format(ProgressiveMediaPeriod.ICY_FORMAT);
                    }
                    long currentInputPosition = j2;
                    this.progressiveMediaExtractor.init(icyDataSource, this.uri, this.dataSource.getResponseHeaders(), j2, this.length, this.extractorOutput);
                    if (ProgressiveMediaPeriod.this.icyHeaders != null) {
                        this.progressiveMediaExtractor.disableSeekingOnMp3Streams();
                    }
                    if (this.pendingExtractorSeek) {
                        this.progressiveMediaExtractor.seek(currentInputPosition, this.seekTimeUs);
                        this.pendingExtractorSeek = false;
                    }
                    while (true) {
                        long j3 = currentInputPosition;
                        while (i2 == 0 && !this.loadCanceled) {
                            try {
                                this.loadCondition.block();
                                i2 = this.progressiveMediaExtractor.read(this.positionHolder);
                                currentInputPosition = this.progressiveMediaExtractor.getCurrentInputPosition();
                                if (currentInputPosition > ProgressiveMediaPeriod.this.continueLoadingCheckIntervalBytes + j3) {
                                    break;
                                }
                            } catch (InterruptedException unused) {
                                throw new InterruptedIOException();
                            }
                        }
                        this.loadCondition.close();
                        ProgressiveMediaPeriod.this.handler.post(ProgressiveMediaPeriod.this.onContinueLoadingRequestedRunnable);
                    }
                    if (i2 == 1) {
                        i2 = 0;
                    } else if (this.progressiveMediaExtractor.getCurrentInputPosition() != -1) {
                        this.positionHolder.position = this.progressiveMediaExtractor.getCurrentInputPosition();
                    }
                    DataSourceUtil.closeQuietly(this.dataSource);
                } catch (Throwable th) {
                    if (i2 != 1 && this.progressiveMediaExtractor.getCurrentInputPosition() != -1) {
                        this.positionHolder.position = this.progressiveMediaExtractor.getCurrentInputPosition();
                    }
                    DataSourceUtil.closeQuietly(this.dataSource);
                    throw th;
                }
            }
        }

        @Override // com.google.android.exoplayer2.source.IcyDataSource.Listener
        public void onIcyMetadata(ParsableByteArray parsableByteArray) {
            long jMax = !this.seenIcyMetadata ? this.seekTimeUs : Math.max(ProgressiveMediaPeriod.this.getLargestQueuedTimestampUs(), this.seekTimeUs);
            int iBytesLeft = parsableByteArray.bytesLeft();
            TrackOutput trackOutput = (TrackOutput) Assertions.checkNotNull(this.icyTrackOutput);
            trackOutput.sampleData(parsableByteArray, iBytesLeft);
            trackOutput.sampleMetadata(jMax, 1, iBytesLeft, 0, null);
            this.seenIcyMetadata = true;
        }
    }

    public interface Listener {
        void onSourceInfoRefreshed(long j2, boolean z2, boolean z3);
    }

    public final class SampleStreamImpl implements SampleStream {
        private final int track;

        public SampleStreamImpl(int i2) {
            this.track = i2;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return ProgressiveMediaPeriod.this.isReady(this.track);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            ProgressiveMediaPeriod.this.maybeThrowError(this.track);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            return ProgressiveMediaPeriod.this.readData(this.track, formatHolder, decoderInputBuffer, i2);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j2) {
            return ProgressiveMediaPeriod.this.skipData(this.track, j2);
        }
    }

    public static final class TrackId {
        public final int id;
        public final boolean isIcyTrack;

        public TrackId(int i2, boolean z2) {
            this.id = i2;
            this.isIcyTrack = z2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || TrackId.class != obj.getClass()) {
                return false;
            }
            TrackId trackId = (TrackId) obj;
            return this.id == trackId.id && this.isIcyTrack == trackId.isIcyTrack;
        }

        public int hashCode() {
            return (this.id * 31) + (this.isIcyTrack ? 1 : 0);
        }
    }

    public static final class TrackState {
        public final boolean[] trackEnabledStates;
        public final boolean[] trackIsAudioVideoFlags;
        public final boolean[] trackNotifiedDownstreamFormats;
        public final TrackGroupArray tracks;

        public TrackState(TrackGroupArray trackGroupArray, boolean[] zArr) {
            this.tracks = trackGroupArray;
            this.trackIsAudioVideoFlags = zArr;
            int i2 = trackGroupArray.length;
            this.trackEnabledStates = new boolean[i2];
            this.trackNotifiedDownstreamFormats = new boolean[i2];
        }
    }

    public ProgressiveMediaPeriod(Uri uri, DataSource dataSource, ProgressiveMediaExtractor progressiveMediaExtractor, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, Listener listener, Allocator allocator, @Nullable String str, int i2) {
        this.uri = uri;
        this.dataSource = dataSource;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = eventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.mediaSourceEventDispatcher = eventDispatcher2;
        this.listener = listener;
        this.allocator = allocator;
        this.customCacheKey = str;
        this.continueLoadingCheckIntervalBytes = i2;
        this.progressiveMediaExtractor = progressiveMediaExtractor;
    }

    @EnsuresNonNull({"trackState", "seekMap"})
    private void assertPrepared() {
        Assertions.checkState(this.prepared);
        Assertions.checkNotNull(this.trackState);
        Assertions.checkNotNull(this.seekMap);
    }

    private boolean configureRetry(ExtractingLoadable extractingLoadable, int i2) {
        SeekMap seekMap;
        if (this.length != -1 || ((seekMap = this.seekMap) != null && seekMap.getDurationUs() != C.TIME_UNSET)) {
            this.extractedSamplesCountAtStartOfLoad = i2;
            return true;
        }
        if (this.prepared && !suppressRead()) {
            this.pendingDeferredRetry = true;
            return false;
        }
        this.notifyDiscontinuity = this.prepared;
        this.lastSeekPositionUs = 0L;
        this.extractedSamplesCountAtStartOfLoad = 0;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.reset();
        }
        extractingLoadable.setLoadPosition(0L, 0L);
        return true;
    }

    private void copyLengthFromLoader(ExtractingLoadable extractingLoadable) {
        if (this.length == -1) {
            this.length = extractingLoadable.length;
        }
    }

    private static Map<String, String> createIcyMetadataHeaders() {
        HashMap map = new HashMap();
        map.put(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_NAME, "1");
        return Collections.unmodifiableMap(map);
    }

    private int getExtractedSamplesCount() {
        int writeIndex = 0;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            writeIndex += sampleQueue.getWriteIndex();
        }
        return writeIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getLargestQueuedTimestampUs() {
        long jMax = Long.MIN_VALUE;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            jMax = Math.max(jMax, sampleQueue.getLargestQueuedTimestampUs());
        }
        return jMax;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.released) {
            return;
        }
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinishPrepare() {
        if (this.released || this.prepared || !this.sampleQueuesBuilt || this.seekMap == null) {
            return;
        }
        for (SampleQueue sampleQueue : this.sampleQueues) {
            if (sampleQueue.getUpstreamFormat() == null) {
                return;
            }
        }
        this.loadCondition.close();
        int length = this.sampleQueues.length;
        TrackGroup[] trackGroupArr = new TrackGroup[length];
        boolean[] zArr = new boolean[length];
        for (int i2 = 0; i2 < length; i2++) {
            Format formatBuild = (Format) Assertions.checkNotNull(this.sampleQueues[i2].getUpstreamFormat());
            String str = formatBuild.sampleMimeType;
            boolean zIsAudio = MimeTypes.isAudio(str);
            boolean z2 = zIsAudio || MimeTypes.isVideo(str);
            zArr[i2] = z2;
            this.haveAudioVideoTracks = z2 | this.haveAudioVideoTracks;
            IcyHeaders icyHeaders = this.icyHeaders;
            if (icyHeaders != null) {
                if (zIsAudio || this.sampleQueueTrackIds[i2].isIcyTrack) {
                    Metadata metadata = formatBuild.metadata;
                    formatBuild = formatBuild.buildUpon().setMetadata(metadata == null ? new Metadata(icyHeaders) : metadata.copyWithAppendedEntries(icyHeaders)).build();
                }
                if (zIsAudio && formatBuild.averageBitrate == -1 && formatBuild.peakBitrate == -1 && icyHeaders.bitrate != -1) {
                    formatBuild = formatBuild.buildUpon().setAverageBitrate(icyHeaders.bitrate).build();
                }
            }
            trackGroupArr[i2] = new TrackGroup(formatBuild.copyWithCryptoType(this.drmSessionManager.getCryptoType(formatBuild)));
        }
        this.trackState = new TrackState(new TrackGroupArray(trackGroupArr), zArr);
        this.prepared = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    private void maybeNotifyDownstreamFormat(int i2) {
        assertPrepared();
        TrackState trackState = this.trackState;
        boolean[] zArr = trackState.trackNotifiedDownstreamFormats;
        if (zArr[i2]) {
            return;
        }
        Format format = trackState.tracks.get(i2).getFormat(0);
        this.mediaSourceEventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(format.sampleMimeType), format, 0, null, this.lastSeekPositionUs);
        zArr[i2] = true;
    }

    private void maybeStartDeferredRetry(int i2) {
        assertPrepared();
        boolean[] zArr = this.trackState.trackIsAudioVideoFlags;
        if (this.pendingDeferredRetry && zArr[i2]) {
            if (this.sampleQueues[i2].isReady(false)) {
                return;
            }
            this.pendingResetPositionUs = 0L;
            this.pendingDeferredRetry = false;
            this.notifyDiscontinuity = true;
            this.lastSeekPositionUs = 0L;
            this.extractedSamplesCountAtStartOfLoad = 0;
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.reset();
            }
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
        }
    }

    private TrackOutput prepareTrackOutput(TrackId trackId) {
        int length = this.sampleQueues.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (trackId.equals(this.sampleQueueTrackIds[i2])) {
                return this.sampleQueues[i2];
            }
        }
        SampleQueue sampleQueueCreateWithDrm = SampleQueue.createWithDrm(this.allocator, this.handler.getLooper(), this.drmSessionManager, this.drmEventDispatcher);
        sampleQueueCreateWithDrm.setUpstreamFormatChangeListener(this);
        int i3 = length + 1;
        TrackId[] trackIdArr = (TrackId[]) Arrays.copyOf(this.sampleQueueTrackIds, i3);
        trackIdArr[length] = trackId;
        this.sampleQueueTrackIds = (TrackId[]) Util.castNonNullTypeArray(trackIdArr);
        SampleQueue[] sampleQueueArr = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, i3);
        sampleQueueArr[length] = sampleQueueCreateWithDrm;
        this.sampleQueues = (SampleQueue[]) Util.castNonNullTypeArray(sampleQueueArr);
        return sampleQueueCreateWithDrm;
    }

    private boolean seekInsideBufferUs(boolean[] zArr, long j2) {
        int length = this.sampleQueues.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (!this.sampleQueues[i2].seekTo(j2, false) && (zArr[i2] || !this.haveAudioVideoTracks)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setSeekMap, reason: merged with bridge method [inline-methods] */
    public void lambda$seekMap$1(SeekMap seekMap) {
        this.seekMap = this.icyHeaders == null ? seekMap : new SeekMap.Unseekable(C.TIME_UNSET);
        this.durationUs = seekMap.getDurationUs();
        boolean z2 = this.length == -1 && seekMap.getDurationUs() == C.TIME_UNSET;
        this.isLive = z2;
        this.dataType = z2 ? 7 : 1;
        this.listener.onSourceInfoRefreshed(this.durationUs, seekMap.isSeekable(), this.isLive);
        if (this.prepared) {
            return;
        }
        maybeFinishPrepare();
    }

    private void startLoading() {
        ExtractingLoadable extractingLoadable = new ExtractingLoadable(this.uri, this.dataSource, this.progressiveMediaExtractor, this, this.loadCondition);
        if (this.prepared) {
            Assertions.checkState(isPendingReset());
            long j2 = this.durationUs;
            if (j2 != C.TIME_UNSET && this.pendingResetPositionUs > j2) {
                this.loadingFinished = true;
                this.pendingResetPositionUs = C.TIME_UNSET;
                return;
            }
            extractingLoadable.setLoadPosition(((SeekMap) Assertions.checkNotNull(this.seekMap)).getSeekPoints(this.pendingResetPositionUs).first.position, this.pendingResetPositionUs);
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.setStartTimeUs(this.pendingResetPositionUs);
            }
            this.pendingResetPositionUs = C.TIME_UNSET;
        }
        this.extractedSamplesCountAtStartOfLoad = getExtractedSamplesCount();
        this.mediaSourceEventDispatcher.loadStarted(new LoadEventInfo(extractingLoadable.loadTaskId, extractingLoadable.dataSpec, this.loader.startLoading(extractingLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.dataType))), 1, -1, null, 0, null, extractingLoadable.seekTimeUs, this.durationUs);
    }

    private boolean suppressRead() {
        return this.notifyDiscontinuity || isPendingReset();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j2) {
        if (this.loadingFinished || this.loader.hasFatalError() || this.pendingDeferredRetry) {
            return false;
        }
        if (this.prepared && this.enabledTrackCount == 0) {
            return false;
        }
        boolean zOpen = this.loadCondition.open();
        if (this.loader.isLoading()) {
            return zOpen;
        }
        startLoading();
        return true;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        assertPrepared();
        if (isPendingReset()) {
            return;
        }
        boolean[] zArr = this.trackState.trackEnabledStates;
        int length = this.sampleQueues.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.sampleQueues[i2].discardTo(j2, z2, zArr[i2]);
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        this.sampleQueuesBuilt = true;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        assertPrepared();
        if (!this.seekMap.isSeekable()) {
            return 0L;
        }
        SeekMap.SeekPoints seekPoints = this.seekMap.getSeekPoints(j2);
        return seekParameters.resolveSeekPositionUs(j2, seekPoints.first.timeUs, seekPoints.second.timeUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        long largestQueuedTimestampUs;
        assertPrepared();
        boolean[] zArr = this.trackState.trackIsAudioVideoFlags;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        if (this.haveAudioVideoTracks) {
            int length = this.sampleQueues.length;
            largestQueuedTimestampUs = Long.MAX_VALUE;
            for (int i2 = 0; i2 < length; i2++) {
                if (zArr[i2] && !this.sampleQueues[i2].isLastSampleQueued()) {
                    largestQueuedTimestampUs = Math.min(largestQueuedTimestampUs, this.sampleQueues[i2].getLargestQueuedTimestampUs());
                }
            }
        } else {
            largestQueuedTimestampUs = Long.MAX_VALUE;
        }
        if (largestQueuedTimestampUs == Long.MAX_VALUE) {
            largestQueuedTimestampUs = getLargestQueuedTimestampUs();
        }
        return largestQueuedTimestampUs == Long.MIN_VALUE ? this.lastSeekPositionUs : largestQueuedTimestampUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (this.enabledTrackCount == 0) {
            return Long.MIN_VALUE;
        }
        return getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        return q.a(this, list);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        assertPrepared();
        return this.trackState.tracks;
    }

    public TrackOutput icyTrack() {
        return prepareTrackOutput(new TrackId(0, true));
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.loader.isLoading() && this.loadCondition.isOpen();
    }

    public boolean isReady(int i2) {
        return !suppressRead() && this.sampleQueues[i2].isReady(this.loadingFinished);
    }

    public void maybeThrowError(int i2) throws IOException {
        this.sampleQueues[i2].maybeThrowError();
        maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
        if (this.loadingFinished && !this.prepared) {
            throw ParserException.createForMalformedContainer("Loading finished before preparation is complete.", null);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.release();
        }
        this.progressiveMediaExtractor.release();
    }

    @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        this.loadCondition.open();
        startLoading();
    }

    public int readData(int i2, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i3) {
        if (suppressRead()) {
            return -3;
        }
        maybeNotifyDownstreamFormat(i2);
        int i4 = this.sampleQueues[i2].read(formatHolder, decoderInputBuffer, i3, this.loadingFinished);
        if (i4 == -3) {
            maybeStartDeferredRetry(i2);
        }
        return i4;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        if (!this.notifyDiscontinuity) {
            return C.TIME_UNSET;
        }
        if (!this.loadingFinished && getExtractedSamplesCount() <= this.extractedSamplesCountAtStartOfLoad) {
            return C.TIME_UNSET;
        }
        this.notifyDiscontinuity = false;
        return this.lastSeekPositionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
    }

    public void release() {
        if (this.prepared) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.preRelease();
            }
        }
        this.loader.release(this);
        this.handler.removeCallbacksAndMessages(null);
        this.callback = null;
        this.released = true;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(final SeekMap seekMap) {
        this.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.c0
            @Override // java.lang.Runnable
            public final void run() {
                this.f6861c.lambda$seekMap$1(seekMap);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j2) {
        assertPrepared();
        boolean[] zArr = this.trackState.trackIsAudioVideoFlags;
        if (!this.seekMap.isSeekable()) {
            j2 = 0;
        }
        int i2 = 0;
        this.notifyDiscontinuity = false;
        this.lastSeekPositionUs = j2;
        if (isPendingReset()) {
            this.pendingResetPositionUs = j2;
            return j2;
        }
        if (this.dataType != 7 && seekInsideBufferUs(zArr, j2)) {
            return j2;
        }
        this.pendingDeferredRetry = false;
        this.pendingResetPositionUs = j2;
        this.loadingFinished = false;
        if (this.loader.isLoading()) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            int length = sampleQueueArr.length;
            while (i2 < length) {
                sampleQueueArr[i2].discardToEnd();
                i2++;
            }
            this.loader.cancelLoading();
        } else {
            this.loader.clearFatalError();
            SampleQueue[] sampleQueueArr2 = this.sampleQueues;
            int length2 = sampleQueueArr2.length;
            while (i2 < length2) {
                sampleQueueArr2[i2].reset();
                i2++;
            }
        }
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        ExoTrackSelection exoTrackSelection;
        assertPrepared();
        TrackState trackState = this.trackState;
        TrackGroupArray trackGroupArray = trackState.tracks;
        boolean[] zArr3 = trackState.trackEnabledStates;
        int i2 = this.enabledTrackCount;
        int i3 = 0;
        for (int i4 = 0; i4 < exoTrackSelectionArr.length; i4++) {
            SampleStream sampleStream = sampleStreamArr[i4];
            if (sampleStream != null && (exoTrackSelectionArr[i4] == null || !zArr[i4])) {
                int i5 = ((SampleStreamImpl) sampleStream).track;
                Assertions.checkState(zArr3[i5]);
                this.enabledTrackCount--;
                zArr3[i5] = false;
                sampleStreamArr[i4] = null;
            }
        }
        boolean z2 = !this.seenFirstTrackSelection ? j2 == 0 : i2 != 0;
        for (int i6 = 0; i6 < exoTrackSelectionArr.length; i6++) {
            if (sampleStreamArr[i6] == null && (exoTrackSelection = exoTrackSelectionArr[i6]) != null) {
                Assertions.checkState(exoTrackSelection.length() == 1);
                Assertions.checkState(exoTrackSelection.getIndexInTrackGroup(0) == 0);
                int iIndexOf = trackGroupArray.indexOf(exoTrackSelection.getTrackGroup());
                Assertions.checkState(!zArr3[iIndexOf]);
                this.enabledTrackCount++;
                zArr3[iIndexOf] = true;
                sampleStreamArr[i6] = new SampleStreamImpl(iIndexOf);
                zArr2[i6] = true;
                if (!z2) {
                    SampleQueue sampleQueue = this.sampleQueues[iIndexOf];
                    z2 = (sampleQueue.seekTo(j2, true) || sampleQueue.getReadIndex() == 0) ? false : true;
                }
            }
        }
        if (this.enabledTrackCount == 0) {
            this.pendingDeferredRetry = false;
            this.notifyDiscontinuity = false;
            if (this.loader.isLoading()) {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length = sampleQueueArr.length;
                while (i3 < length) {
                    sampleQueueArr[i3].discardToEnd();
                    i3++;
                }
                this.loader.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr2 = this.sampleQueues;
                int length2 = sampleQueueArr2.length;
                while (i3 < length2) {
                    sampleQueueArr2[i3].reset();
                    i3++;
                }
            }
        } else if (z2) {
            j2 = seekToUs(j2);
            while (i3 < sampleStreamArr.length) {
                if (sampleStreamArr[i3] != null) {
                    zArr2[i3] = true;
                }
                i3++;
            }
        }
        this.seenFirstTrackSelection = true;
        return j2;
    }

    public int skipData(int i2, long j2) {
        if (suppressRead()) {
            return 0;
        }
        maybeNotifyDownstreamFormat(i2);
        SampleQueue sampleQueue = this.sampleQueues[i2];
        int skipCount = sampleQueue.getSkipCount(j2, this.loadingFinished);
        sampleQueue.skip(skipCount);
        if (skipCount == 0) {
            maybeStartDeferredRetry(i2);
        }
        return skipCount;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int i2, int i3) {
        return prepareTrackOutput(new TrackId(i2, false));
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCanceled(ExtractingLoadable extractingLoadable, long j2, long j3, boolean z2) {
        StatsDataSource statsDataSource = extractingLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(extractingLoadable.loadTaskId, extractingLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(extractingLoadable.loadTaskId);
        this.mediaSourceEventDispatcher.loadCanceled(loadEventInfo, 1, -1, null, 0, null, extractingLoadable.seekTimeUs, this.durationUs);
        if (z2) {
            return;
        }
        copyLengthFromLoader(extractingLoadable);
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.reset();
        }
        if (this.enabledTrackCount > 0) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCompleted(ExtractingLoadable extractingLoadable, long j2, long j3) {
        SeekMap seekMap;
        if (this.durationUs == C.TIME_UNSET && (seekMap = this.seekMap) != null) {
            boolean zIsSeekable = seekMap.isSeekable();
            long largestQueuedTimestampUs = getLargestQueuedTimestampUs();
            long j4 = largestQueuedTimestampUs == Long.MIN_VALUE ? 0L : largestQueuedTimestampUs + 10000;
            this.durationUs = j4;
            this.listener.onSourceInfoRefreshed(j4, zIsSeekable, this.isLive);
        }
        StatsDataSource statsDataSource = extractingLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(extractingLoadable.loadTaskId, extractingLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(extractingLoadable.loadTaskId);
        this.mediaSourceEventDispatcher.loadCompleted(loadEventInfo, 1, -1, null, 0, null, extractingLoadable.seekTimeUs, this.durationUs);
        copyLengthFromLoader(extractingLoadable);
        this.loadingFinished = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(ExtractingLoadable extractingLoadable, long j2, long j3, IOException iOException, int i2) {
        boolean z2;
        ExtractingLoadable extractingLoadable2;
        Loader.LoadErrorAction loadErrorActionCreateRetryAction;
        copyLengthFromLoader(extractingLoadable);
        StatsDataSource statsDataSource = extractingLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(extractingLoadable.loadTaskId, extractingLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, null, 0, null, Util.usToMs(extractingLoadable.seekTimeUs), Util.usToMs(this.durationUs)), iOException, i2));
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY_FATAL;
        } else {
            int extractedSamplesCount = getExtractedSamplesCount();
            if (extractedSamplesCount > this.extractedSamplesCountAtStartOfLoad) {
                extractingLoadable2 = extractingLoadable;
                z2 = true;
            } else {
                z2 = false;
                extractingLoadable2 = extractingLoadable;
            }
            loadErrorActionCreateRetryAction = configureRetry(extractingLoadable2, extractedSamplesCount) ? Loader.createRetryAction(z2, retryDelayMsFor) : Loader.DONT_RETRY;
        }
        boolean z3 = !loadErrorActionCreateRetryAction.isRetry();
        this.mediaSourceEventDispatcher.loadError(loadEventInfo, 1, -1, null, 0, null, extractingLoadable.seekTimeUs, this.durationUs, iOException, z3);
        if (z3) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(extractingLoadable.loadTaskId);
        }
        return loadErrorActionCreateRetryAction;
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError(this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.dataType));
    }
}
