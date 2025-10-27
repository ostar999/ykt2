package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManagerProvider;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.FilteringHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class HlsMediaSource extends BaseMediaSource implements HlsPlaylistTracker.PrimaryPlaylistListener {
    public static final int METADATA_TYPE_EMSG = 3;
    public static final int METADATA_TYPE_ID3 = 1;
    private final boolean allowChunklessPreparation;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionManager drmSessionManager;
    private final long elapsedRealTimeOffsetMs;
    private final HlsExtractorFactory extractorFactory;
    private MediaItem.LiveConfiguration liveConfiguration;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final MediaItem.LocalConfiguration localConfiguration;
    private final MediaItem mediaItem;

    @Nullable
    private TransferListener mediaTransferListener;
    private final int metadataType;
    private final HlsPlaylistTracker playlistTracker;
    private final boolean useSessionKeys;

    public static final class Factory implements MediaSourceFactory {
        private boolean allowChunklessPreparation;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long elapsedRealTimeOffsetMs;
        private HlsExtractorFactory extractorFactory;
        private final HlsDataSourceFactory hlsDataSourceFactory;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private int metadataType;
        private HlsPlaylistParserFactory playlistParserFactory;
        private HlsPlaylistTracker.Factory playlistTrackerFactory;
        private List<StreamKey> streamKeys;

        @Nullable
        private Object tag;
        private boolean useSessionKeys;
        private boolean usingCustomDrmSessionManagerProvider;

        public Factory(DataSource.Factory factory) {
            this(new DefaultHlsDataSourceFactory(factory));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ DrmSessionManager lambda$setDrmSessionManager$0(DrmSessionManager drmSessionManager, MediaItem mediaItem) {
            return drmSessionManager;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{2};
        }

        public Factory setAllowChunklessPreparation(boolean z2) {
            this.allowChunklessPreparation = z2;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(@Nullable CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            if (compositeSequenceableLoaderFactory == null) {
                compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            }
            this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
            return this;
        }

        @VisibleForTesting
        public Factory setElapsedRealTimeOffsetMs(long j2) {
            this.elapsedRealTimeOffsetMs = j2;
            return this;
        }

        public Factory setExtractorFactory(@Nullable HlsExtractorFactory hlsExtractorFactory) {
            if (hlsExtractorFactory == null) {
                hlsExtractorFactory = HlsExtractorFactory.DEFAULT;
            }
            this.extractorFactory = hlsExtractorFactory;
            return this;
        }

        public Factory setMetadataType(int i2) {
            this.metadataType = i2;
            return this;
        }

        public Factory setPlaylistParserFactory(@Nullable HlsPlaylistParserFactory hlsPlaylistParserFactory) {
            if (hlsPlaylistParserFactory == null) {
                hlsPlaylistParserFactory = new DefaultHlsPlaylistParserFactory();
            }
            this.playlistParserFactory = hlsPlaylistParserFactory;
            return this;
        }

        public Factory setPlaylistTrackerFactory(@Nullable HlsPlaylistTracker.Factory factory) {
            if (factory == null) {
                factory = DefaultHlsPlaylistTracker.FACTORY;
            }
            this.playlistTrackerFactory = factory;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(@Nullable List list) {
            return setStreamKeys((List<StreamKey>) list);
        }

        @Deprecated
        public Factory setTag(@Nullable Object obj) {
            this.tag = obj;
            return this;
        }

        public Factory setUseSessionKeys(boolean z2) {
            this.useSessionKeys = z2;
            return this;
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory) {
            this.hlsDataSourceFactory = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory);
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            this.playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            this.extractorFactory = HlsExtractorFactory.DEFAULT;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
            this.metadataType = 1;
            this.streamKeys = Collections.emptyList();
            this.elapsedRealTimeOffsetMs = C.TIME_UNSET;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            if (!this.usingCustomDrmSessionManagerProvider) {
                ((DefaultDrmSessionManagerProvider) this.drmSessionManagerProvider).setDrmHttpDataSourceFactory(factory);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmSessionManager(@Nullable final DrmSessionManager drmSessionManager) {
            if (drmSessionManager == null) {
                setDrmSessionManagerProvider((DrmSessionManagerProvider) null);
            } else {
                setDrmSessionManagerProvider(new DrmSessionManagerProvider() { // from class: com.google.android.exoplayer2.source.hls.a
                    @Override // com.google.android.exoplayer2.drm.DrmSessionManagerProvider
                    public final DrmSessionManager get(MediaItem mediaItem) {
                        return HlsMediaSource.Factory.lambda$setDrmSessionManager$0(drmSessionManager, mediaItem);
                    }
                });
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            if (drmSessionManagerProvider != null) {
                this.drmSessionManagerProvider = drmSessionManagerProvider;
                this.usingCustomDrmSessionManagerProvider = true;
            } else {
                this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
                this.usingCustomDrmSessionManagerProvider = false;
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmUserAgent(@Nullable String str) {
            if (!this.usingCustomDrmSessionManagerProvider) {
                ((DefaultDrmSessionManagerProvider) this.drmSessionManagerProvider).setDrmUserAgent(str);
            }
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            if (loadErrorHandlingPolicy == null) {
                loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            }
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setStreamKeys(@Nullable List<StreamKey> list) {
            if (list == null) {
                list = Collections.emptyList();
            }
            this.streamKeys = list;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public HlsMediaSource createMediaSource(Uri uri) {
            return createMediaSource(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build());
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public HlsMediaSource createMediaSource(MediaItem mediaItem) {
            List<StreamKey> list;
            MediaItem mediaItemBuild = mediaItem;
            Assertions.checkNotNull(mediaItemBuild.localConfiguration);
            HlsPlaylistParserFactory filteringHlsPlaylistParserFactory = this.playlistParserFactory;
            if (mediaItemBuild.localConfiguration.streamKeys.isEmpty()) {
                list = this.streamKeys;
            } else {
                list = mediaItemBuild.localConfiguration.streamKeys;
            }
            if (!list.isEmpty()) {
                filteringHlsPlaylistParserFactory = new FilteringHlsPlaylistParserFactory(filteringHlsPlaylistParserFactory, list);
            }
            MediaItem.LocalConfiguration localConfiguration = mediaItemBuild.localConfiguration;
            boolean z2 = localConfiguration.tag == null && this.tag != null;
            boolean z3 = localConfiguration.streamKeys.isEmpty() && !list.isEmpty();
            if (z2 && z3) {
                mediaItemBuild = mediaItem.buildUpon().setTag(this.tag).setStreamKeys(list).build();
            } else if (z2) {
                mediaItemBuild = mediaItem.buildUpon().setTag(this.tag).build();
            } else if (z3) {
                mediaItemBuild = mediaItem.buildUpon().setStreamKeys(list).build();
            }
            MediaItem mediaItem2 = mediaItemBuild;
            HlsDataSourceFactory hlsDataSourceFactory = this.hlsDataSourceFactory;
            HlsExtractorFactory hlsExtractorFactory = this.extractorFactory;
            CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory = this.compositeSequenceableLoaderFactory;
            DrmSessionManager drmSessionManager = this.drmSessionManagerProvider.get(mediaItem2);
            LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.loadErrorHandlingPolicy;
            return new HlsMediaSource(mediaItem2, hlsDataSourceFactory, hlsExtractorFactory, compositeSequenceableLoaderFactory, drmSessionManager, loadErrorHandlingPolicy, this.playlistTrackerFactory.createTracker(this.hlsDataSourceFactory, loadErrorHandlingPolicy, filteringHlsPlaylistParserFactory), this.elapsedRealTimeOffsetMs, this.allowChunklessPreparation, this.metadataType, this.useSessionKeys);
        }
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MetadataType {
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    private SinglePeriodTimeline createTimelineForLive(HlsMediaPlaylist hlsMediaPlaylist, long j2, long j3, HlsManifest hlsManifest) {
        long initialStartTimeUs = hlsMediaPlaylist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        long j4 = hlsMediaPlaylist.hasEndTag ? initialStartTimeUs + hlsMediaPlaylist.durationUs : -9223372036854775807L;
        long liveEdgeOffsetUs = getLiveEdgeOffsetUs(hlsMediaPlaylist);
        long j5 = this.liveConfiguration.targetOffsetMs;
        maybeUpdateLiveConfiguration(Util.constrainValue(j5 != C.TIME_UNSET ? Util.msToUs(j5) : getTargetLiveOffsetUs(hlsMediaPlaylist, liveEdgeOffsetUs), liveEdgeOffsetUs, hlsMediaPlaylist.durationUs + liveEdgeOffsetUs));
        return new SinglePeriodTimeline(j2, j3, C.TIME_UNSET, j4, hlsMediaPlaylist.durationUs, initialStartTimeUs, getLiveWindowDefaultStartPositionUs(hlsMediaPlaylist, liveEdgeOffsetUs), true, !hlsMediaPlaylist.hasEndTag, hlsMediaPlaylist.playlistType == 2 && hlsMediaPlaylist.hasPositiveStartOffset, hlsManifest, this.mediaItem, this.liveConfiguration);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.android.exoplayer2.source.SinglePeriodTimeline createTimelineForOnDemand(com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist r25, long r26, long r28, com.google.android.exoplayer2.source.hls.HlsManifest r30) {
        /*
            r24 = this;
            r0 = r25
            long r1 = r0.startOffsetUs
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L2f
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist$Segment> r1 = r0.segments
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L16
            goto L2f
        L16:
            boolean r1 = r0.preciseStart
            if (r1 != 0) goto L2c
            long r1 = r0.startOffsetUs
            long r3 = r0.durationUs
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 != 0) goto L23
            goto L2c
        L23:
            java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist$Segment> r3 = r0.segments
            com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist$Segment r1 = findClosestPrecedingSegment(r3, r1)
            long r1 = r1.relativeStartTimeUs
            goto L31
        L2c:
            long r1 = r0.startOffsetUs
            goto L31
        L2f:
            r1 = 0
        L31:
            r16 = r1
            com.google.android.exoplayer2.source.SinglePeriodTimeline r1 = new com.google.android.exoplayer2.source.SinglePeriodTimeline
            r3 = r1
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r10 = r0.durationUs
            r12 = r10
            r14 = 0
            r18 = 1
            r19 = 0
            r20 = 1
            r0 = r24
            com.google.android.exoplayer2.MediaItem r2 = r0.mediaItem
            r22 = r2
            r23 = 0
            r4 = r26
            r6 = r28
            r21 = r30
            r3.<init>(r4, r6, r8, r10, r12, r14, r16, r18, r19, r20, r21, r22, r23)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaSource.createTimelineForOnDemand(com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist, long, long, com.google.android.exoplayer2.source.hls.HlsManifest):com.google.android.exoplayer2.source.SinglePeriodTimeline");
    }

    @Nullable
    private static HlsMediaPlaylist.Part findClosestPrecedingIndependentPart(List<HlsMediaPlaylist.Part> list, long j2) {
        HlsMediaPlaylist.Part part = null;
        for (int i2 = 0; i2 < list.size(); i2++) {
            HlsMediaPlaylist.Part part2 = list.get(i2);
            long j3 = part2.relativeStartTimeUs;
            if (j3 > j2 || !part2.isIndependent) {
                if (j3 > j2) {
                    break;
                }
            } else {
                part = part2;
            }
        }
        return part;
    }

    private static HlsMediaPlaylist.Segment findClosestPrecedingSegment(List<HlsMediaPlaylist.Segment> list, long j2) {
        return list.get(Util.binarySearchFloor((List<? extends Comparable<? super Long>>) list, Long.valueOf(j2), true, true));
    }

    private long getLiveEdgeOffsetUs(HlsMediaPlaylist hlsMediaPlaylist) {
        if (hlsMediaPlaylist.hasProgramDateTime) {
            return Util.msToUs(Util.getNowUnixTimeMs(this.elapsedRealTimeOffsetMs)) - hlsMediaPlaylist.getEndTimeUs();
        }
        return 0L;
    }

    private long getLiveWindowDefaultStartPositionUs(HlsMediaPlaylist hlsMediaPlaylist, long j2) {
        long jMsToUs = hlsMediaPlaylist.startOffsetUs;
        if (jMsToUs == C.TIME_UNSET) {
            jMsToUs = (hlsMediaPlaylist.durationUs + j2) - Util.msToUs(this.liveConfiguration.targetOffsetMs);
        }
        if (hlsMediaPlaylist.preciseStart) {
            return jMsToUs;
        }
        HlsMediaPlaylist.Part partFindClosestPrecedingIndependentPart = findClosestPrecedingIndependentPart(hlsMediaPlaylist.trailingParts, jMsToUs);
        if (partFindClosestPrecedingIndependentPart != null) {
            return partFindClosestPrecedingIndependentPart.relativeStartTimeUs;
        }
        if (hlsMediaPlaylist.segments.isEmpty()) {
            return 0L;
        }
        HlsMediaPlaylist.Segment segmentFindClosestPrecedingSegment = findClosestPrecedingSegment(hlsMediaPlaylist.segments, jMsToUs);
        HlsMediaPlaylist.Part partFindClosestPrecedingIndependentPart2 = findClosestPrecedingIndependentPart(segmentFindClosestPrecedingSegment.parts, jMsToUs);
        return partFindClosestPrecedingIndependentPart2 != null ? partFindClosestPrecedingIndependentPart2.relativeStartTimeUs : segmentFindClosestPrecedingSegment.relativeStartTimeUs;
    }

    private static long getTargetLiveOffsetUs(HlsMediaPlaylist hlsMediaPlaylist, long j2) {
        long j3;
        HlsMediaPlaylist.ServerControl serverControl = hlsMediaPlaylist.serverControl;
        long j4 = hlsMediaPlaylist.startOffsetUs;
        if (j4 != C.TIME_UNSET) {
            j3 = hlsMediaPlaylist.durationUs - j4;
        } else {
            long j5 = serverControl.partHoldBackUs;
            if (j5 == C.TIME_UNSET || hlsMediaPlaylist.partTargetDurationUs == C.TIME_UNSET) {
                long j6 = serverControl.holdBackUs;
                j3 = j6 != C.TIME_UNSET ? j6 : hlsMediaPlaylist.targetDurationUs * 3;
            } else {
                j3 = j5;
            }
        }
        return j3 + j2;
    }

    private void maybeUpdateLiveConfiguration(long j2) {
        long jUsToMs = Util.usToMs(j2);
        MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
        if (jUsToMs != liveConfiguration.targetOffsetMs) {
            this.liveConfiguration = liveConfiguration.buildUpon().setTargetOffsetMs(jUsToMs).build();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        MediaSourceEventListener.EventDispatcher eventDispatcherCreateEventDispatcher = createEventDispatcher(mediaPeriodId);
        return new HlsMediaPeriod(this.extractorFactory, this.playlistTracker, this.dataSourceFactory, this.mediaTransferListener, this.drmSessionManager, createDrmEventDispatcher(mediaPeriodId), this.loadErrorHandlingPolicy, eventDispatcherCreateEventDispatcher, allocator, this.compositeSequenceableLoaderFactory, this.allowChunklessPreparation, this.metadataType, this.useSessionKeys);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener
    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist hlsMediaPlaylist) {
        long jUsToMs = hlsMediaPlaylist.hasProgramDateTime ? Util.usToMs(hlsMediaPlaylist.startTimeUs) : -9223372036854775807L;
        int i2 = hlsMediaPlaylist.playlistType;
        long j2 = (i2 == 2 || i2 == 1) ? jUsToMs : -9223372036854775807L;
        HlsManifest hlsManifest = new HlsManifest((HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist()), hlsMediaPlaylist);
        refreshSourceInfo(this.playlistTracker.isLive() ? createTimelineForLive(hlsMediaPlaylist, j2, jUsToMs, hlsManifest) : createTimelineForOnDemand(hlsMediaPlaylist, j2, jUsToMs, hlsManifest));
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.prepare();
        this.playlistTracker.start(this.localConfiguration.uri, createEventDispatcher(null), this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        this.playlistTracker.stop();
        this.drmSessionManager.release();
    }

    private HlsMediaSource(MediaItem mediaItem, HlsDataSourceFactory hlsDataSourceFactory, HlsExtractorFactory hlsExtractorFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistTracker hlsPlaylistTracker, long j2, boolean z2, int i2, boolean z3) {
        this.localConfiguration = (MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration);
        this.mediaItem = mediaItem;
        this.liveConfiguration = mediaItem.liveConfiguration;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.extractorFactory = hlsExtractorFactory;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.playlistTracker = hlsPlaylistTracker;
        this.elapsedRealTimeOffsetMs = j2;
        this.allowChunklessPreparation = z2;
        this.metadataType = i2;
        this.useSessionKeys = z3;
    }
}
