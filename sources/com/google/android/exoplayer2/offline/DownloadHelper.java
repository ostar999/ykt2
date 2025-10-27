package com.google.android.exoplayer2.offline;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.VideoSize;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public final class DownloadHelper {

    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS;
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT;

    @Deprecated
    public static final DefaultTrackSelector.Parameters DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT;
    private Callback callback;
    private final Handler callbackHandler;
    private List<ExoTrackSelection>[][] immutableTrackSelectionsByPeriodAndRenderer;
    private boolean isPreparedWithMedia;
    private final MediaItem.LocalConfiguration localConfiguration;
    private MappingTrackSelector.MappedTrackInfo[] mappedTrackInfos;
    private MediaPreparer mediaPreparer;

    @Nullable
    private final MediaSource mediaSource;
    private final RendererCapabilities[] rendererCapabilities;
    private final SparseIntArray scratchSet;
    private TrackGroupArray[] trackGroupArrays;
    private List<ExoTrackSelection>[][] trackSelectionsByPeriodAndRenderer;
    private final DefaultTrackSelector trackSelector;
    private final Timeline.Window window;

    public interface Callback {
        void onPrepareError(DownloadHelper downloadHelper, IOException iOException);

        void onPrepared(DownloadHelper downloadHelper);
    }

    public static final class DownloadTrackSelection extends BaseTrackSelection {

        public static final class Factory implements ExoTrackSelection.Factory {
            private Factory() {
            }

            @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection.Factory
            public ExoTrackSelection[] createTrackSelections(ExoTrackSelection.Definition[] definitionArr, BandwidthMeter bandwidthMeter, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) {
                ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
                for (int i2 = 0; i2 < definitionArr.length; i2++) {
                    ExoTrackSelection.Definition definition = definitionArr[i2];
                    exoTrackSelectionArr[i2] = definition == null ? null : new DownloadTrackSelection(definition.group, definition.tracks);
                }
                return exoTrackSelectionArr;
            }
        }

        public DownloadTrackSelection(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        @Nullable
        public Object getSelectionData() {
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
        }
    }

    public static final class FakeBandwidthMeter implements BandwidthMeter {
        private FakeBandwidthMeter() {
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public long getBitrateEstimate() {
            return 0L;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public /* synthetic */ long getTimeToFirstByteEstimateUs() {
            return com.google.android.exoplayer2.upstream.a.a(this);
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        @Nullable
        public TransferListener getTransferListener() {
            return null;
        }

        @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
        public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        }
    }

    public static class LiveContentUnsupportedException extends IOException {
    }

    public static final class MediaPreparer implements MediaSource.MediaSourceCaller, MediaPeriod.Callback, Handler.Callback {
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_FAILED = 1;
        private static final int DOWNLOAD_HELPER_CALLBACK_MESSAGE_PREPARED = 0;
        private static final int MESSAGE_CHECK_FOR_FAILURE = 1;
        private static final int MESSAGE_CONTINUE_LOADING = 2;
        private static final int MESSAGE_PREPARE_SOURCE = 0;
        private static final int MESSAGE_RELEASE = 3;
        private final DownloadHelper downloadHelper;
        public MediaPeriod[] mediaPeriods;
        private final MediaSource mediaSource;
        private final Handler mediaSourceHandler;
        private final HandlerThread mediaSourceThread;
        private boolean released;
        public Timeline timeline;
        private final Allocator allocator = new DefaultAllocator(true, 65536);
        private final ArrayList<MediaPeriod> pendingMediaPeriods = new ArrayList<>();
        private final Handler downloadHelperHandler = Util.createHandlerForCurrentOrMainLooper(new Handler.Callback() { // from class: com.google.android.exoplayer2.offline.i
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f6823c.handleDownloadHelperCallbackMessage(message);
            }
        });

        public MediaPreparer(MediaSource mediaSource, DownloadHelper downloadHelper) {
            this.mediaSource = mediaSource;
            this.downloadHelper = downloadHelper;
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:DownloadHelper");
            this.mediaSourceThread = handlerThread;
            handlerThread.start();
            Handler handlerCreateHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.mediaSourceHandler = handlerCreateHandler;
            handlerCreateHandler.sendEmptyMessage(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean handleDownloadHelperCallbackMessage(Message message) {
            if (this.released) {
                return false;
            }
            int i2 = message.what;
            if (i2 == 0) {
                this.downloadHelper.onMediaPrepared();
                return true;
            }
            if (i2 != 1) {
                return false;
            }
            release();
            this.downloadHelper.onMediaPreparationFailed((IOException) Util.castNonNull(message.obj));
            return true;
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == 0) {
                this.mediaSource.prepareSource(this, null);
                this.mediaSourceHandler.sendEmptyMessage(1);
                return true;
            }
            int i3 = 0;
            if (i2 == 1) {
                try {
                    if (this.mediaPeriods == null) {
                        this.mediaSource.maybeThrowSourceInfoRefreshError();
                    } else {
                        while (i3 < this.pendingMediaPeriods.size()) {
                            this.pendingMediaPeriods.get(i3).maybeThrowPrepareError();
                            i3++;
                        }
                    }
                    this.mediaSourceHandler.sendEmptyMessageDelayed(1, 100L);
                } catch (IOException e2) {
                    this.downloadHelperHandler.obtainMessage(1, e2).sendToTarget();
                }
                return true;
            }
            if (i2 == 2) {
                MediaPeriod mediaPeriod = (MediaPeriod) message.obj;
                if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                    mediaPeriod.continueLoading(0L);
                }
                return true;
            }
            if (i2 != 3) {
                return false;
            }
            MediaPeriod[] mediaPeriodArr = this.mediaPeriods;
            if (mediaPeriodArr != null) {
                int length = mediaPeriodArr.length;
                while (i3 < length) {
                    this.mediaSource.releasePeriod(mediaPeriodArr[i3]);
                    i3++;
                }
            }
            this.mediaSource.releaseSource(this);
            this.mediaSourceHandler.removeCallbacksAndMessages(null);
            this.mediaSourceThread.quit();
            return true;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            this.pendingMediaPeriods.remove(mediaPeriod);
            if (this.pendingMediaPeriods.isEmpty()) {
                this.mediaSourceHandler.removeMessages(1);
                this.downloadHelperHandler.sendEmptyMessage(0);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
        public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
            MediaPeriod[] mediaPeriodArr;
            if (this.timeline != null) {
                return;
            }
            if (timeline.getWindow(0, new Timeline.Window()).isLive()) {
                this.downloadHelperHandler.obtainMessage(1, new LiveContentUnsupportedException()).sendToTarget();
                return;
            }
            this.timeline = timeline;
            this.mediaPeriods = new MediaPeriod[timeline.getPeriodCount()];
            int i2 = 0;
            while (true) {
                mediaPeriodArr = this.mediaPeriods;
                if (i2 >= mediaPeriodArr.length) {
                    break;
                }
                MediaPeriod mediaPeriodCreatePeriod = this.mediaSource.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(i2)), this.allocator, 0L);
                this.mediaPeriods[i2] = mediaPeriodCreatePeriod;
                this.pendingMediaPeriods.add(mediaPeriodCreatePeriod);
                i2++;
            }
            for (MediaPeriod mediaPeriod : mediaPeriodArr) {
                mediaPeriod.prepare(this, 0L);
            }
        }

        public void release() {
            if (this.released) {
                return;
            }
            this.released = true;
            this.mediaSourceHandler.sendEmptyMessage(3);
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            if (this.pendingMediaPeriods.contains(mediaPeriod)) {
                this.mediaSourceHandler.obtainMessage(2, mediaPeriod).sendToTarget();
            }
        }
    }

    static {
        DefaultTrackSelector.Parameters parametersBuild = DefaultTrackSelector.Parameters.DEFAULT_WITHOUT_CONTEXT.buildUpon().setForceHighestSupportedBitrate(true).build();
        DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT = parametersBuild;
        DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_VIEWPORT = parametersBuild;
        DEFAULT_TRACK_SELECTOR_PARAMETERS = parametersBuild;
    }

    public DownloadHelper(MediaItem mediaItem, @Nullable MediaSource mediaSource, DefaultTrackSelector.Parameters parameters, RendererCapabilities[] rendererCapabilitiesArr) {
        this.localConfiguration = (MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration);
        this.mediaSource = mediaSource;
        DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(parameters, new DownloadTrackSelection.Factory());
        this.trackSelector = defaultTrackSelector;
        this.rendererCapabilities = rendererCapabilitiesArr;
        this.scratchSet = new SparseIntArray();
        defaultTrackSelector.init(new TrackSelector.InvalidationListener() { // from class: com.google.android.exoplayer2.offline.c
            @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
            public final void onTrackSelectionsInvalidated() {
                DownloadHelper.lambda$new$2();
            }
        }, new FakeBandwidthMeter());
        this.callbackHandler = Util.createHandlerForCurrentOrMainLooper();
        this.window = new Timeline.Window();
    }

    @EnsuresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void assertPreparedWithMedia() {
        Assertions.checkState(this.isPreparedWithMedia);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory) {
        return createMediaSource(downloadRequest, factory, null);
    }

    private static MediaSource createMediaSourceInternal(MediaItem mediaItem, DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        return new DefaultMediaSourceFactory(factory, ExtractorsFactory.EMPTY).setDrmSessionManager(drmSessionManager).createMediaSource(mediaItem);
    }

    @Deprecated
    public static DownloadHelper forDash(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forDash(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    @Deprecated
    public static DownloadHelper forHls(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forHls(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem) {
        Assertions.checkArgument(isProgressive((MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration)));
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), null, null, null);
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).build());
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, null, DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT);
    }

    public static DefaultTrackSelector.Parameters getDefaultTrackSelectorParameters(Context context) {
        return DefaultTrackSelector.Parameters.getDefaults(context).buildUpon().setForceHighestSupportedBitrate(true).build();
    }

    public static RendererCapabilities[] getRendererCapabilities(RenderersFactory renderersFactory) {
        Renderer[] rendererArrCreateRenderers = renderersFactory.createRenderers(Util.createHandlerForCurrentOrMainLooper(), new VideoRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.1
            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onDroppedFrames(int i2, long j2) {
                com.google.android.exoplayer2.video.d.a(this, i2, j2);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onRenderedFirstFrame(Object obj, long j2) {
                com.google.android.exoplayer2.video.d.b(this, obj, j2);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoCodecError(Exception exc) {
                com.google.android.exoplayer2.video.d.c(this, exc);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoDecoderInitialized(String str, long j2, long j3) {
                com.google.android.exoplayer2.video.d.d(this, str, j2, j3);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoDecoderReleased(String str) {
                com.google.android.exoplayer2.video.d.e(this, str);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoDisabled(DecoderCounters decoderCounters) {
                com.google.android.exoplayer2.video.d.f(this, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoEnabled(DecoderCounters decoderCounters) {
                com.google.android.exoplayer2.video.d.g(this, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoFrameProcessingOffset(long j2, int i2) {
                com.google.android.exoplayer2.video.d.h(this, j2, i2);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoInputFormatChanged(Format format) {
                com.google.android.exoplayer2.video.d.i(this, format);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                com.google.android.exoplayer2.video.d.j(this, format, decoderReuseEvaluation);
            }

            @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
            public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
                com.google.android.exoplayer2.video.d.k(this, videoSize);
            }
        }, new AudioRendererEventListener() { // from class: com.google.android.exoplayer2.offline.DownloadHelper.2
            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioCodecError(Exception exc) {
                com.google.android.exoplayer2.audio.d.a(this, exc);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioDecoderInitialized(String str, long j2, long j3) {
                com.google.android.exoplayer2.audio.d.b(this, str, j2, j3);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioDecoderReleased(String str) {
                com.google.android.exoplayer2.audio.d.c(this, str);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioDisabled(DecoderCounters decoderCounters) {
                com.google.android.exoplayer2.audio.d.d(this, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioEnabled(DecoderCounters decoderCounters) {
                com.google.android.exoplayer2.audio.d.e(this, decoderCounters);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioInputFormatChanged(Format format) {
                com.google.android.exoplayer2.audio.d.f(this, format);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                com.google.android.exoplayer2.audio.d.g(this, format, decoderReuseEvaluation);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioPositionAdvancing(long j2) {
                com.google.android.exoplayer2.audio.d.h(this, j2);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioSinkError(Exception exc) {
                com.google.android.exoplayer2.audio.d.i(this, exc);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onAudioUnderrun(int i2, long j2, long j3) {
                com.google.android.exoplayer2.audio.d.j(this, i2, j2, j3);
            }

            @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
            public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z2) {
                com.google.android.exoplayer2.audio.d.k(this, z2);
            }
        }, new TextOutput() { // from class: com.google.android.exoplayer2.offline.e
            @Override // com.google.android.exoplayer2.text.TextOutput
            public final void onCues(List list) {
                DownloadHelper.lambda$getRendererCapabilities$0(list);
            }
        }, new MetadataOutput() { // from class: com.google.android.exoplayer2.offline.f
            @Override // com.google.android.exoplayer2.metadata.MetadataOutput
            public final void onMetadata(Metadata metadata) {
                DownloadHelper.lambda$getRendererCapabilities$1(metadata);
            }
        });
        RendererCapabilities[] rendererCapabilitiesArr = new RendererCapabilities[rendererArrCreateRenderers.length];
        for (int i2 = 0; i2 < rendererArrCreateRenderers.length; i2++) {
            rendererCapabilitiesArr[i2] = rendererArrCreateRenderers[i2].getCapabilities();
        }
        return rendererCapabilitiesArr;
    }

    private static boolean isProgressive(MediaItem.LocalConfiguration localConfiguration) {
        return Util.inferContentTypeForUriAndMimeType(localConfiguration.uri, localConfiguration.mimeType) == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRendererCapabilities$0(List list) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRendererCapabilities$1(Metadata metadata) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMediaPreparationFailed$5(IOException iOException) {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepareError(this, iOException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMediaPrepared$4() {
        ((Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepare$3(Callback callback) {
        callback.onPrepared(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMediaPreparationFailed(final IOException iOException) {
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f6818c.lambda$onMediaPreparationFailed$5(iOException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMediaPrepared() {
        Assertions.checkNotNull(this.mediaPreparer);
        Assertions.checkNotNull(this.mediaPreparer.mediaPeriods);
        Assertions.checkNotNull(this.mediaPreparer.timeline);
        int length = this.mediaPreparer.mediaPeriods.length;
        int length2 = this.rendererCapabilities.length;
        this.trackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance((Class<?>) List.class, length, length2);
        this.immutableTrackSelectionsByPeriodAndRenderer = (List[][]) Array.newInstance((Class<?>) List.class, length, length2);
        for (int i2 = 0; i2 < length; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                this.trackSelectionsByPeriodAndRenderer[i2][i3] = new ArrayList();
                this.immutableTrackSelectionsByPeriodAndRenderer[i2][i3] = Collections.unmodifiableList(this.trackSelectionsByPeriodAndRenderer[i2][i3]);
            }
        }
        this.trackGroupArrays = new TrackGroupArray[length];
        this.mappedTrackInfos = new MappingTrackSelector.MappedTrackInfo[length];
        for (int i4 = 0; i4 < length; i4++) {
            this.trackGroupArrays[i4] = this.mediaPreparer.mediaPeriods[i4].getTrackGroups();
            this.trackSelector.onSelectionActivated(runTrackSelection(i4).info);
            this.mappedTrackInfos[i4] = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(this.trackSelector.getCurrentMappedTrackInfo());
        }
        setPreparedWithMedia();
        ((Handler) Assertions.checkNotNull(this.callbackHandler)).post(new Runnable() { // from class: com.google.android.exoplayer2.offline.g
            @Override // java.lang.Runnable
            public final void run() {
                this.f6820c.lambda$onMediaPrepared$4();
            }
        });
    }

    @RequiresNonNull({"trackGroupArrays", "trackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline"})
    private TrackSelectorResult runTrackSelection(int i2) {
        boolean z2;
        try {
            TrackSelectorResult trackSelectorResultSelectTracks = this.trackSelector.selectTracks(this.rendererCapabilities, this.trackGroupArrays[i2], new MediaSource.MediaPeriodId(this.mediaPreparer.timeline.getUidOfPeriod(i2)), this.mediaPreparer.timeline);
            for (int i3 = 0; i3 < trackSelectorResultSelectTracks.length; i3++) {
                ExoTrackSelection exoTrackSelection = trackSelectorResultSelectTracks.selections[i3];
                if (exoTrackSelection != null) {
                    List<ExoTrackSelection> list = this.trackSelectionsByPeriodAndRenderer[i2][i3];
                    int i4 = 0;
                    while (true) {
                        if (i4 >= list.size()) {
                            z2 = false;
                            break;
                        }
                        ExoTrackSelection exoTrackSelection2 = list.get(i4);
                        if (exoTrackSelection2.getTrackGroup() == exoTrackSelection.getTrackGroup()) {
                            this.scratchSet.clear();
                            for (int i5 = 0; i5 < exoTrackSelection2.length(); i5++) {
                                this.scratchSet.put(exoTrackSelection2.getIndexInTrackGroup(i5), 0);
                            }
                            for (int i6 = 0; i6 < exoTrackSelection.length(); i6++) {
                                this.scratchSet.put(exoTrackSelection.getIndexInTrackGroup(i6), 0);
                            }
                            int[] iArr = new int[this.scratchSet.size()];
                            for (int i7 = 0; i7 < this.scratchSet.size(); i7++) {
                                iArr[i7] = this.scratchSet.keyAt(i7);
                            }
                            list.set(i4, new DownloadTrackSelection(exoTrackSelection2.getTrackGroup(), iArr));
                            z2 = true;
                        } else {
                            i4++;
                        }
                    }
                    if (!z2) {
                        list.add(exoTrackSelection);
                    }
                }
            }
            return trackSelectorResultSelectTracks;
        } catch (ExoPlaybackException e2) {
            throw new UnsupportedOperationException(e2);
        }
    }

    @RequiresNonNull({"trackGroupArrays", "mappedTrackInfos", "trackSelectionsByPeriodAndRenderer", "immutableTrackSelectionsByPeriodAndRenderer", "mediaPreparer", "mediaPreparer.timeline", "mediaPreparer.mediaPeriods"})
    private void setPreparedWithMedia() {
        this.isPreparedWithMedia = true;
    }

    public void addAudioLanguagesToSelection(String... strArr) {
        assertPreparedWithMedia();
        for (int i2 = 0; i2 < this.mappedTrackInfos.length; i2++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[i2];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i3 = 0; i3 < rendererCount; i3++) {
                if (mappedTrackInfo.getRendererType(i3) != 1) {
                    parametersBuilderBuildUpon.setRendererDisabled(i3, true);
                }
            }
            for (String str : strArr) {
                parametersBuilderBuildUpon.setPreferredAudioLanguage(str);
                addTrackSelection(i2, parametersBuilderBuildUpon.build());
            }
        }
    }

    public void addTextLanguagesToSelection(boolean z2, String... strArr) {
        assertPreparedWithMedia();
        for (int i2 = 0; i2 < this.mappedTrackInfos.length; i2++) {
            DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = DEFAULT_TRACK_SELECTOR_PARAMETERS_WITHOUT_CONTEXT.buildUpon();
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = this.mappedTrackInfos[i2];
            int rendererCount = mappedTrackInfo.getRendererCount();
            for (int i3 = 0; i3 < rendererCount; i3++) {
                if (mappedTrackInfo.getRendererType(i3) != 3) {
                    parametersBuilderBuildUpon.setRendererDisabled(i3, true);
                }
            }
            parametersBuilderBuildUpon.setSelectUndeterminedTextLanguage(z2);
            for (String str : strArr) {
                parametersBuilderBuildUpon.setPreferredTextLanguage(str);
                addTrackSelection(i2, parametersBuilderBuildUpon.build());
            }
        }
    }

    public void addTrackSelection(int i2, DefaultTrackSelector.Parameters parameters) {
        assertPreparedWithMedia();
        this.trackSelector.setParameters(parameters);
        runTrackSelection(i2);
    }

    public void addTrackSelectionForSingleRenderer(int i2, int i3, DefaultTrackSelector.Parameters parameters, List<DefaultTrackSelector.SelectionOverride> list) {
        assertPreparedWithMedia();
        DefaultTrackSelector.ParametersBuilder parametersBuilderBuildUpon = parameters.buildUpon();
        int i4 = 0;
        while (i4 < this.mappedTrackInfos[i2].getRendererCount()) {
            parametersBuilderBuildUpon.setRendererDisabled(i4, i4 != i3);
            i4++;
        }
        if (list.isEmpty()) {
            addTrackSelection(i2, parametersBuilderBuildUpon.build());
            return;
        }
        TrackGroupArray trackGroups = this.mappedTrackInfos[i2].getTrackGroups(i3);
        for (int i5 = 0; i5 < list.size(); i5++) {
            parametersBuilderBuildUpon.setSelectionOverride(i3, trackGroups, list.get(i5));
            addTrackSelection(i2, parametersBuilderBuildUpon.build());
        }
    }

    public void clearTrackSelections(int i2) {
        assertPreparedWithMedia();
        for (int i3 = 0; i3 < this.rendererCapabilities.length; i3++) {
            this.trackSelectionsByPeriodAndRenderer[i2][i3].clear();
        }
    }

    public DownloadRequest getDownloadRequest(@Nullable byte[] bArr) {
        return getDownloadRequest(this.localConfiguration.uri.toString(), bArr);
    }

    @Nullable
    public Object getManifest() {
        if (this.mediaSource == null) {
            return null;
        }
        assertPreparedWithMedia();
        if (this.mediaPreparer.timeline.getWindowCount() > 0) {
            return this.mediaPreparer.timeline.getWindow(0, this.window).manifest;
        }
        return null;
    }

    public MappingTrackSelector.MappedTrackInfo getMappedTrackInfo(int i2) {
        assertPreparedWithMedia();
        return this.mappedTrackInfos[i2];
    }

    public int getPeriodCount() {
        if (this.mediaSource == null) {
            return 0;
        }
        assertPreparedWithMedia();
        return this.trackGroupArrays.length;
    }

    public TrackGroupArray getTrackGroups(int i2) {
        assertPreparedWithMedia();
        return this.trackGroupArrays[i2];
    }

    public List<ExoTrackSelection> getTrackSelections(int i2, int i3) {
        assertPreparedWithMedia();
        return this.immutableTrackSelectionsByPeriodAndRenderer[i2][i3];
    }

    public void prepare(final Callback callback) {
        Assertions.checkState(this.callback == null);
        this.callback = callback;
        MediaSource mediaSource = this.mediaSource;
        if (mediaSource != null) {
            this.mediaPreparer = new MediaPreparer(mediaSource, this);
        } else {
            this.callbackHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6821c.lambda$prepare$3(callback);
                }
            });
        }
    }

    public void release() {
        MediaPreparer mediaPreparer = this.mediaPreparer;
        if (mediaPreparer != null) {
            mediaPreparer.release();
        }
    }

    public void replaceTrackSelections(int i2, DefaultTrackSelector.Parameters parameters) {
        clearTrackSelections(i2);
        addTrackSelection(i2, parameters);
    }

    public static MediaSource createMediaSource(DownloadRequest downloadRequest, DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        return createMediaSourceInternal(downloadRequest.toMediaItem(), factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forProgressive(Context context, Uri uri, @Nullable String str) {
        return forMediaItem(context, new MediaItem.Builder().setUri(uri).setCustomCacheKey(str).build());
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Context context, Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory) {
        return forSmoothStreaming(uri, factory, renderersFactory, null, getDefaultTrackSelectorParameters(context));
    }

    public DownloadRequest getDownloadRequest(String str, @Nullable byte[] bArr) {
        DownloadRequest.Builder mimeType = new DownloadRequest.Builder(str, this.localConfiguration.uri).setMimeType(this.localConfiguration.mimeType);
        MediaItem.DrmConfiguration drmConfiguration = this.localConfiguration.drmConfiguration;
        DownloadRequest.Builder data = mimeType.setKeySetId(drmConfiguration != null ? drmConfiguration.getKeySetId() : null).setCustomCacheKey(this.localConfiguration.customCacheKey).setData(bArr);
        if (this.mediaSource == null) {
            return data.build();
        }
        assertPreparedWithMedia();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int length = this.trackSelectionsByPeriodAndRenderer.length;
        for (int i2 = 0; i2 < length; i2++) {
            arrayList2.clear();
            int length2 = this.trackSelectionsByPeriodAndRenderer[i2].length;
            for (int i3 = 0; i3 < length2; i3++) {
                arrayList2.addAll(this.trackSelectionsByPeriodAndRenderer[i2][i3]);
            }
            arrayList.addAll(this.mediaPreparer.mediaPeriods[i2].getStreamKeys(arrayList2));
        }
        return data.setStreamKeys(arrayList).build();
    }

    @Deprecated
    public static DownloadHelper forDash(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_MPD).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    @Deprecated
    public static DownloadHelper forHls(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_M3U8).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    public static DownloadHelper forMediaItem(Context context, MediaItem mediaItem, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, getDefaultTrackSelectorParameters(context), renderersFactory, factory, null);
    }

    @Deprecated
    public static DownloadHelper forSmoothStreaming(Uri uri, DataSource.Factory factory, RenderersFactory renderersFactory, @Nullable DrmSessionManager drmSessionManager, DefaultTrackSelector.Parameters parameters) {
        return forMediaItem(new MediaItem.Builder().setUri(uri).setMimeType(MimeTypes.APPLICATION_SS).build(), parameters, renderersFactory, factory, drmSessionManager);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory) {
        return forMediaItem(mediaItem, parameters, renderersFactory, factory, null);
    }

    public static DownloadHelper forMediaItem(MediaItem mediaItem, DefaultTrackSelector.Parameters parameters, @Nullable RenderersFactory renderersFactory, @Nullable DataSource.Factory factory, @Nullable DrmSessionManager drmSessionManager) {
        boolean zIsProgressive = isProgressive((MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration));
        Assertions.checkArgument(zIsProgressive || factory != null);
        return new DownloadHelper(mediaItem, zIsProgressive ? null : createMediaSourceInternal(mediaItem, (DataSource.Factory) Util.castNonNull(factory), drmSessionManager), parameters, renderersFactory != null ? getRendererCapabilities(renderersFactory) : new RendererCapabilities[0]);
    }
}
