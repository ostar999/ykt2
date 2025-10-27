package com.google.android.exoplayer2.source.ads;

import android.os.Handler;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.j;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class ServerSideInsertedAdsMediaSource extends BaseMediaSource implements MediaSource.MediaSourceCaller, MediaSourceEventListener, DrmSessionEventListener {

    @Nullable
    private Timeline contentTimeline;

    @Nullable
    private SharedMediaPeriod lastUsedMediaPeriod;
    private final MediaSource mediaSource;

    @Nullable
    @GuardedBy("this")
    private Handler playbackHandler;
    private final ListMultimap<Long, SharedMediaPeriod> mediaPeriods = ArrayListMultimap.create();
    private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcherWithoutId = createEventDispatcher(null);
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcherWithoutId = createDrmEventDispatcher(null);

    public static final class MediaPeriodImpl implements MediaPeriod {
        public MediaPeriod.Callback callback;
        public final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
        public boolean[] hasNotifiedDownstreamFormatChange = new boolean[0];
        public long lastStartPositionUs;
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
        public final SharedMediaPeriod sharedPeriod;

        public MediaPeriodImpl(SharedMediaPeriod sharedMediaPeriod, MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener.EventDispatcher eventDispatcher2) {
            this.sharedPeriod = sharedMediaPeriod;
            this.mediaPeriodId = mediaPeriodId;
            this.mediaSourceEventDispatcher = eventDispatcher;
            this.drmEventDispatcher = eventDispatcher2;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long j2) {
            return this.sharedPeriod.continueLoading(this, j2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long j2, boolean z2) {
            this.sharedPeriod.discardBuffer(this, j2, z2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
            return this.sharedPeriod.getAdjustedSeekPositionUs(this, j2, seekParameters);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            return this.sharedPeriod.getBufferedPositionUs(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            return this.sharedPeriod.getNextLoadPositionUs(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.sharedPeriod.getStreamKeys(list);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.sharedPeriod.getTrackGroups();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.sharedPeriod.isLoading(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.sharedPeriod.maybeThrowPrepareError();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long j2) {
            this.callback = callback;
            this.sharedPeriod.prepare(this, j2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            return this.sharedPeriod.readDiscontinuity(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long j2) {
            this.sharedPeriod.reevaluateBuffer(this, j2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long j2) {
            return this.sharedPeriod.seekToUs(this, j2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
            if (this.hasNotifiedDownstreamFormatChange.length == 0) {
                this.hasNotifiedDownstreamFormatChange = new boolean[sampleStreamArr.length];
            }
            return this.sharedPeriod.selectTracks(this, exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j2);
        }
    }

    public static final class SampleStreamImpl implements SampleStream {
        private final MediaPeriodImpl mediaPeriod;
        private final int streamIndex;

        public SampleStreamImpl(MediaPeriodImpl mediaPeriodImpl, int i2) {
            this.mediaPeriod = mediaPeriodImpl;
            this.streamIndex = i2;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.mediaPeriod.sharedPeriod.isReady(this.streamIndex);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.mediaPeriod.sharedPeriod.maybeThrowError(this.streamIndex);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            MediaPeriodImpl mediaPeriodImpl = this.mediaPeriod;
            return mediaPeriodImpl.sharedPeriod.readData(mediaPeriodImpl, this.streamIndex, formatHolder, decoderInputBuffer, i2);
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j2) {
            MediaPeriodImpl mediaPeriodImpl = this.mediaPeriod;
            return mediaPeriodImpl.sharedPeriod.skipData(mediaPeriodImpl, this.streamIndex, j2);
        }
    }

    public static final class ServerSideInsertedAdsTimeline extends ForwardingTimeline {
        private final AdPlaybackState adPlaybackState;

        public ServerSideInsertedAdsTimeline(Timeline timeline, AdPlaybackState adPlaybackState) {
            super(timeline);
            Assertions.checkState(timeline.getPeriodCount() == 1);
            Assertions.checkState(timeline.getWindowCount() == 1);
            this.adPlaybackState = adPlaybackState;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
            super.getPeriod(i2, period, z2);
            long j2 = period.durationUs;
            period.set(period.id, period.uid, period.windowIndex, j2 == C.TIME_UNSET ? this.adPlaybackState.contentDurationUs : ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(j2, -1, this.adPlaybackState), -ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(-period.getPositionInWindowUs(), -1, this.adPlaybackState), this.adPlaybackState, period.isPlaceholder);
            return period;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
            super.getWindow(i2, window, j2);
            long mediaPeriodPositionUsForContent = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs, -1, this.adPlaybackState);
            long j3 = window.durationUs;
            if (j3 == C.TIME_UNSET) {
                long j4 = this.adPlaybackState.contentDurationUs;
                if (j4 != C.TIME_UNSET) {
                    window.durationUs = j4 - mediaPeriodPositionUsForContent;
                }
            } else {
                window.durationUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(window.positionInFirstPeriodUs + j3, -1, this.adPlaybackState) - mediaPeriodPositionUsForContent;
            }
            window.positionInFirstPeriodUs = mediaPeriodPositionUsForContent;
            return window;
        }
    }

    public static final class SharedMediaPeriod implements MediaPeriod.Callback {
        private final MediaPeriod actualMediaPeriod;
        private AdPlaybackState adPlaybackState;
        private boolean hasStartedPreparing;
        private boolean isPrepared;

        @Nullable
        private MediaPeriodImpl loadingPeriod;
        private final List<MediaPeriodImpl> mediaPeriods = new ArrayList();
        private final Map<Long, Pair<LoadEventInfo, MediaLoadData>> activeLoads = new HashMap();
        public ExoTrackSelection[] trackSelections = new ExoTrackSelection[0];
        public SampleStream[] sampleStreams = new SampleStream[0];
        public MediaLoadData[] lastDownstreamFormatChangeData = new MediaLoadData[0];

        public SharedMediaPeriod(MediaPeriod mediaPeriod, AdPlaybackState adPlaybackState) {
            this.actualMediaPeriod = mediaPeriod;
            this.adPlaybackState = adPlaybackState;
        }

        private int findMatchingStreamIndex(MediaLoadData mediaLoadData) {
            String str;
            if (mediaLoadData.trackFormat == null) {
                return -1;
            }
            int i2 = 0;
            loop0: while (true) {
                ExoTrackSelection[] exoTrackSelectionArr = this.trackSelections;
                if (i2 >= exoTrackSelectionArr.length) {
                    return -1;
                }
                ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
                if (exoTrackSelection != null) {
                    TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                    boolean z2 = mediaLoadData.trackType == 0 && trackGroup.equals(getTrackGroups().get(0));
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        Format format = trackGroup.getFormat(i3);
                        if (format.equals(mediaLoadData.trackFormat) || (z2 && (str = format.id) != null && str.equals(mediaLoadData.trackFormat.id))) {
                            break loop0;
                        }
                    }
                }
                i2++;
            }
            return i2;
        }

        private long getMediaPeriodPositionUsWithEndOfSourceHandling(MediaPeriodImpl mediaPeriodImpl, long j2) {
            if (j2 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
            if (mediaPeriodPositionUs >= ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriodImpl, this.adPlaybackState)) {
                return Long.MIN_VALUE;
            }
            return mediaPeriodPositionUs;
        }

        private long getStreamPositionUsWithNotYetStartedHandling(MediaPeriodImpl mediaPeriodImpl, long j2) {
            long j3 = mediaPeriodImpl.lastStartPositionUs;
            return j2 < j3 ? ServerSideInsertedAdsUtil.getStreamPositionUs(j3, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState) - (mediaPeriodImpl.lastStartPositionUs - j2) : ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        private void maybeNotifyDownstreamFormatChanged(MediaPeriodImpl mediaPeriodImpl, int i2) {
            MediaLoadData mediaLoadData;
            boolean[] zArr = mediaPeriodImpl.hasNotifiedDownstreamFormatChange;
            if (zArr[i2] || (mediaLoadData = this.lastDownstreamFormatChangeData[i2]) == null) {
                return;
            }
            zArr[i2] = true;
            mediaPeriodImpl.mediaSourceEventDispatcher.downstreamFormatChanged(ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriodImpl, mediaLoadData, this.adPlaybackState));
        }

        public void add(MediaPeriodImpl mediaPeriodImpl) {
            this.mediaPeriods.add(mediaPeriodImpl);
        }

        public boolean canReuseMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, long j2) {
            MediaPeriodImpl mediaPeriodImpl = (MediaPeriodImpl) Iterables.getLast(this.mediaPeriods);
            return ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodId, this.adPlaybackState) == ServerSideInsertedAdsUtil.getStreamPositionUs(ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriodImpl, this.adPlaybackState), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public boolean continueLoading(MediaPeriodImpl mediaPeriodImpl, long j2) {
            MediaPeriodImpl mediaPeriodImpl2 = this.loadingPeriod;
            if (mediaPeriodImpl2 != null && !mediaPeriodImpl.equals(mediaPeriodImpl2)) {
                for (Pair<LoadEventInfo, MediaLoadData> pair : this.activeLoads.values()) {
                    mediaPeriodImpl2.mediaSourceEventDispatcher.loadCompleted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriodImpl2, (MediaLoadData) pair.second, this.adPlaybackState));
                    mediaPeriodImpl.mediaSourceEventDispatcher.loadStarted((LoadEventInfo) pair.first, ServerSideInsertedAdsMediaSource.correctMediaLoadData(mediaPeriodImpl, (MediaLoadData) pair.second, this.adPlaybackState));
                }
            }
            this.loadingPeriod = mediaPeriodImpl;
            return this.actualMediaPeriod.continueLoading(getStreamPositionUsWithNotYetStartedHandling(mediaPeriodImpl, j2));
        }

        public void discardBuffer(MediaPeriodImpl mediaPeriodImpl, long j2, boolean z2) {
            this.actualMediaPeriod.discardBuffer(ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState), z2);
        }

        public long getAdjustedSeekPositionUs(MediaPeriodImpl mediaPeriodImpl, long j2, SeekParameters seekParameters) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.actualMediaPeriod.getAdjustedSeekPositionUs(ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState), seekParameters), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public long getBufferedPositionUs(MediaPeriodImpl mediaPeriodImpl) {
            return getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriodImpl, this.actualMediaPeriod.getBufferedPositionUs());
        }

        @Nullable
        public MediaPeriodImpl getMediaPeriodForEvent(@Nullable MediaLoadData mediaLoadData) {
            if (mediaLoadData == null || mediaLoadData.mediaStartTimeMs == C.TIME_UNSET) {
                return null;
            }
            for (int i2 = 0; i2 < this.mediaPeriods.size(); i2++) {
                MediaPeriodImpl mediaPeriodImpl = this.mediaPeriods.get(i2);
                long mediaPeriodPositionUs = ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(Util.msToUs(mediaLoadData.mediaStartTimeMs), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
                long mediaPeriodEndPositionUs = ServerSideInsertedAdsMediaSource.getMediaPeriodEndPositionUs(mediaPeriodImpl, this.adPlaybackState);
                if (mediaPeriodPositionUs >= 0 && mediaPeriodPositionUs < mediaPeriodEndPositionUs) {
                    return mediaPeriodImpl;
                }
            }
            return null;
        }

        public long getNextLoadPositionUs(MediaPeriodImpl mediaPeriodImpl) {
            return getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriodImpl, this.actualMediaPeriod.getNextLoadPositionUs());
        }

        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.actualMediaPeriod.getStreamKeys(list);
        }

        public TrackGroupArray getTrackGroups() {
            return this.actualMediaPeriod.getTrackGroups();
        }

        public boolean isLoading(MediaPeriodImpl mediaPeriodImpl) {
            return mediaPeriodImpl.equals(this.loadingPeriod) && this.actualMediaPeriod.isLoading();
        }

        public boolean isReady(int i2) {
            return ((SampleStream) Util.castNonNull(this.sampleStreams[i2])).isReady();
        }

        public boolean isUnused() {
            return this.mediaPeriods.isEmpty();
        }

        public void maybeThrowError(int i2) throws IOException {
            ((SampleStream) Util.castNonNull(this.sampleStreams[i2])).maybeThrowError();
        }

        public void maybeThrowPrepareError() throws IOException {
            this.actualMediaPeriod.maybeThrowPrepareError();
        }

        public void onDownstreamFormatChanged(MediaPeriodImpl mediaPeriodImpl, MediaLoadData mediaLoadData) {
            int iFindMatchingStreamIndex = findMatchingStreamIndex(mediaLoadData);
            if (iFindMatchingStreamIndex != -1) {
                this.lastDownstreamFormatChangeData[iFindMatchingStreamIndex] = mediaLoadData;
                mediaPeriodImpl.hasNotifiedDownstreamFormatChange[iFindMatchingStreamIndex] = true;
            }
        }

        public void onLoadFinished(LoadEventInfo loadEventInfo) {
            this.activeLoads.remove(Long.valueOf(loadEventInfo.loadTaskId));
        }

        public void onLoadStarted(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            this.activeLoads.put(Long.valueOf(loadEventInfo.loadTaskId), Pair.create(loadEventInfo, mediaLoadData));
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            this.isPrepared = true;
            for (int i2 = 0; i2 < this.mediaPeriods.size(); i2++) {
                MediaPeriodImpl mediaPeriodImpl = this.mediaPeriods.get(i2);
                MediaPeriod.Callback callback = mediaPeriodImpl.callback;
                if (callback != null) {
                    callback.onPrepared(mediaPeriodImpl);
                }
            }
        }

        public void prepare(MediaPeriodImpl mediaPeriodImpl, long j2) {
            mediaPeriodImpl.lastStartPositionUs = j2;
            if (this.hasStartedPreparing) {
                if (this.isPrepared) {
                    ((MediaPeriod.Callback) Assertions.checkNotNull(mediaPeriodImpl.callback)).onPrepared(mediaPeriodImpl);
                }
            } else {
                this.hasStartedPreparing = true;
                this.actualMediaPeriod.prepare(this, ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState));
            }
        }

        public int readData(MediaPeriodImpl mediaPeriodImpl, int i2, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i3) {
            int data = ((SampleStream) Util.castNonNull(this.sampleStreams[i2])).readData(formatHolder, decoderInputBuffer, i3 | 1 | 4);
            long mediaPeriodPositionUsWithEndOfSourceHandling = getMediaPeriodPositionUsWithEndOfSourceHandling(mediaPeriodImpl, decoderInputBuffer.timeUs);
            if ((data == -4 && mediaPeriodPositionUsWithEndOfSourceHandling == Long.MIN_VALUE) || (data == -3 && getBufferedPositionUs(mediaPeriodImpl) == Long.MIN_VALUE && !decoderInputBuffer.waitingForKeys)) {
                maybeNotifyDownstreamFormatChanged(mediaPeriodImpl, i2);
                decoderInputBuffer.clear();
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            if (data == -4) {
                maybeNotifyDownstreamFormatChanged(mediaPeriodImpl, i2);
                ((SampleStream) Util.castNonNull(this.sampleStreams[i2])).readData(formatHolder, decoderInputBuffer, i3);
                decoderInputBuffer.timeUs = mediaPeriodPositionUsWithEndOfSourceHandling;
            }
            return data;
        }

        public long readDiscontinuity(MediaPeriodImpl mediaPeriodImpl) {
            if (!mediaPeriodImpl.equals(this.mediaPeriods.get(0))) {
                return C.TIME_UNSET;
            }
            long discontinuity = this.actualMediaPeriod.readDiscontinuity();
            return discontinuity == C.TIME_UNSET ? C.TIME_UNSET : ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(discontinuity, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public void reevaluateBuffer(MediaPeriodImpl mediaPeriodImpl, long j2) {
            this.actualMediaPeriod.reevaluateBuffer(getStreamPositionUsWithNotYetStartedHandling(mediaPeriodImpl, j2));
        }

        public void release(MediaSource mediaSource) {
            mediaSource.releasePeriod(this.actualMediaPeriod);
        }

        public void remove(MediaPeriodImpl mediaPeriodImpl) {
            if (mediaPeriodImpl.equals(this.loadingPeriod)) {
                this.loadingPeriod = null;
                this.activeLoads.clear();
            }
            this.mediaPeriods.remove(mediaPeriodImpl);
        }

        public long seekToUs(MediaPeriodImpl mediaPeriodImpl, long j2) {
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(this.actualMediaPeriod.seekToUs(ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState)), mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public long selectTracks(MediaPeriodImpl mediaPeriodImpl, ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
            mediaPeriodImpl.lastStartPositionUs = j2;
            if (!mediaPeriodImpl.equals(this.mediaPeriods.get(0))) {
                for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
                    ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
                    boolean z2 = true;
                    if (exoTrackSelection != null) {
                        if (zArr[i2] && sampleStreamArr[i2] != null) {
                            z2 = false;
                        }
                        zArr2[i2] = z2;
                        if (z2) {
                            sampleStreamArr[i2] = Util.areEqual(this.trackSelections[i2], exoTrackSelection) ? new SampleStreamImpl(mediaPeriodImpl, i2) : new EmptySampleStream();
                        }
                    } else {
                        sampleStreamArr[i2] = null;
                        zArr2[i2] = true;
                    }
                }
                return j2;
            }
            this.trackSelections = (ExoTrackSelection[]) Arrays.copyOf(exoTrackSelectionArr, exoTrackSelectionArr.length);
            long streamPositionUs = ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
            SampleStream[] sampleStreamArr2 = this.sampleStreams;
            SampleStream[] sampleStreamArr3 = sampleStreamArr2.length == 0 ? new SampleStream[exoTrackSelectionArr.length] : (SampleStream[]) Arrays.copyOf(sampleStreamArr2, sampleStreamArr2.length);
            long jSelectTracks = this.actualMediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr3, zArr2, streamPositionUs);
            this.sampleStreams = (SampleStream[]) Arrays.copyOf(sampleStreamArr3, sampleStreamArr3.length);
            this.lastDownstreamFormatChangeData = (MediaLoadData[]) Arrays.copyOf(this.lastDownstreamFormatChangeData, sampleStreamArr3.length);
            for (int i3 = 0; i3 < sampleStreamArr3.length; i3++) {
                if (sampleStreamArr3[i3] == null) {
                    sampleStreamArr[i3] = null;
                    this.lastDownstreamFormatChangeData[i3] = null;
                } else if (sampleStreamArr[i3] == null || zArr2[i3]) {
                    sampleStreamArr[i3] = new SampleStreamImpl(mediaPeriodImpl, i3);
                    this.lastDownstreamFormatChangeData[i3] = null;
                }
            }
            return ServerSideInsertedAdsUtil.getMediaPeriodPositionUs(jSelectTracks, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState);
        }

        public int skipData(MediaPeriodImpl mediaPeriodImpl, int i2, long j2) {
            return ((SampleStream) Util.castNonNull(this.sampleStreams[i2])).skipData(ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodImpl.mediaPeriodId, this.adPlaybackState));
        }

        public void updateAdPlaybackState(AdPlaybackState adPlaybackState) {
            this.adPlaybackState = adPlaybackState;
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            MediaPeriodImpl mediaPeriodImpl = this.loadingPeriod;
            if (mediaPeriodImpl == null) {
                return;
            }
            ((MediaPeriod.Callback) Assertions.checkNotNull(mediaPeriodImpl.callback)).onContinueLoadingRequested(this.loadingPeriod);
        }
    }

    public ServerSideInsertedAdsMediaSource(MediaSource mediaSource) {
        this.mediaSource = mediaSource;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaLoadData correctMediaLoadData(MediaPeriodImpl mediaPeriodImpl, MediaLoadData mediaLoadData, AdPlaybackState adPlaybackState) {
        return new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, correctMediaLoadDataPositionMs(mediaLoadData.mediaStartTimeMs, mediaPeriodImpl, adPlaybackState), correctMediaLoadDataPositionMs(mediaLoadData.mediaEndTimeMs, mediaPeriodImpl, adPlaybackState));
    }

    private static long correctMediaLoadDataPositionMs(long j2, MediaPeriodImpl mediaPeriodImpl, AdPlaybackState adPlaybackState) {
        if (j2 == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long jMsToUs = Util.msToUs(j2);
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodImpl.mediaPeriodId;
        return Util.usToMs(mediaPeriodId.isAd() ? ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForAd(jMsToUs, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, adPlaybackState) : ServerSideInsertedAdsUtil.getMediaPeriodPositionUsForContent(jMsToUs, -1, adPlaybackState));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getMediaPeriodEndPositionUs(MediaPeriodImpl mediaPeriodImpl, AdPlaybackState adPlaybackState) {
        MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodImpl.mediaPeriodId;
        if (mediaPeriodId.isAd()) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(mediaPeriodId.adGroupIndex);
            if (adGroup.count == -1) {
                return 0L;
            }
            return adGroup.durationsUs[mediaPeriodId.adIndexInAdGroup];
        }
        int i2 = mediaPeriodId.nextAdGroupIndex;
        if (i2 == -1) {
            return Long.MAX_VALUE;
        }
        long j2 = adPlaybackState.getAdGroup(i2).timeUs;
        if (j2 == Long.MIN_VALUE) {
            return Long.MAX_VALUE;
        }
        return j2;
    }

    @Nullable
    private MediaPeriodImpl getMediaPeriodForEvent(@Nullable MediaSource.MediaPeriodId mediaPeriodId, @Nullable MediaLoadData mediaLoadData, boolean z2) {
        if (mediaPeriodId == null) {
            return null;
        }
        List<SharedMediaPeriod> list = this.mediaPeriods.get((ListMultimap<Long, SharedMediaPeriod>) Long.valueOf(mediaPeriodId.windowSequenceNumber));
        if (list.isEmpty()) {
            return null;
        }
        if (z2) {
            SharedMediaPeriod sharedMediaPeriod = (SharedMediaPeriod) Iterables.getLast(list);
            return sharedMediaPeriod.loadingPeriod != null ? sharedMediaPeriod.loadingPeriod : (MediaPeriodImpl) Iterables.getLast(sharedMediaPeriod.mediaPeriods);
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            MediaPeriodImpl mediaPeriodForEvent = list.get(i2).getMediaPeriodForEvent(mediaLoadData);
            if (mediaPeriodForEvent != null) {
                return mediaPeriodForEvent;
            }
        }
        return (MediaPeriodImpl) list.get(0).mediaPeriods.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAdPlaybackState$0(AdPlaybackState adPlaybackState) {
        Iterator<SharedMediaPeriod> it = this.mediaPeriods.values().iterator();
        while (it.hasNext()) {
            it.next().updateAdPlaybackState(adPlaybackState);
        }
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            sharedMediaPeriod.updateAdPlaybackState(adPlaybackState);
        }
        this.adPlaybackState = adPlaybackState;
        if (this.contentTimeline != null) {
            refreshSourceInfo(new ServerSideInsertedAdsTimeline(this.contentTimeline, adPlaybackState));
        }
    }

    private void releaseLastUsedMediaPeriod() {
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            sharedMediaPeriod.release(this.mediaSource);
            this.lastUsedMediaPeriod = null;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        SharedMediaPeriod sharedMediaPeriod = this.lastUsedMediaPeriod;
        if (sharedMediaPeriod != null) {
            this.lastUsedMediaPeriod = null;
            this.mediaPeriods.put(Long.valueOf(mediaPeriodId.windowSequenceNumber), sharedMediaPeriod);
        } else {
            sharedMediaPeriod = (SharedMediaPeriod) Iterables.getLast(this.mediaPeriods.get((ListMultimap<Long, SharedMediaPeriod>) Long.valueOf(mediaPeriodId.windowSequenceNumber)), null);
            if (sharedMediaPeriod == null || !sharedMediaPeriod.canReuseMediaPeriod(mediaPeriodId, j2)) {
                sharedMediaPeriod = new SharedMediaPeriod(this.mediaSource.createPeriod(new MediaSource.MediaPeriodId(mediaPeriodId.periodUid, mediaPeriodId.windowSequenceNumber), allocator, ServerSideInsertedAdsUtil.getStreamPositionUs(j2, mediaPeriodId, this.adPlaybackState)), this.adPlaybackState);
                this.mediaPeriods.put(Long.valueOf(mediaPeriodId.windowSequenceNumber), sharedMediaPeriod);
            }
        }
        MediaPeriodImpl mediaPeriodImpl = new MediaPeriodImpl(sharedMediaPeriod, mediaPeriodId, createEventDispatcher(mediaPeriodId), createDrmEventDispatcher(mediaPeriodId));
        sharedMediaPeriod.add(mediaPeriodImpl);
        return mediaPeriodImpl;
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void disableInternal() {
        releaseLastUsedMediaPeriod();
        this.mediaSource.disable(this);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void enableInternal() {
        this.mediaSource.enable(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.mediaSource.maybeThrowSourceInfoRefreshError();
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onDownstreamFormatChanged(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, false);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.downstreamFormatChanged(mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onDownstreamFormatChanged(mediaPeriodForEvent, mediaLoadData);
            mediaPeriodForEvent.mediaSourceEventDispatcher.downstreamFormatChanged(correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysLoaded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysLoaded();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysLoaded();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRemoved(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysRemoved();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysRemoved();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmKeysRestored(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmKeysRestored();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmKeysRestored();
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public /* synthetic */ void onDrmSessionAcquired(int i2, MediaSource.MediaPeriodId mediaPeriodId) {
        j.d(this, i2, mediaPeriodId);
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionAcquired(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i3) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, true);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionAcquired(i3);
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionAcquired(i3);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionManagerError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionManagerError(exc);
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionManagerError(exc);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
    public void onDrmSessionReleased(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, null, false);
        if (mediaPeriodForEvent == null) {
            this.drmEventDispatcherWithoutId.drmSessionReleased();
        } else {
            mediaPeriodForEvent.drmEventDispatcher.drmSessionReleased();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCanceled(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadCanceled(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadCanceled(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadCompleted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadCompleted(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadCompleted(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadError(loadEventInfo, mediaLoadData, iOException, z2);
            return;
        }
        if (z2) {
            mediaPeriodForEvent.sharedPeriod.onLoadFinished(loadEventInfo);
        }
        mediaPeriodForEvent.mediaSourceEventDispatcher.loadError(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState), iOException, z2);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onLoadStarted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, true);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.loadStarted(loadEventInfo, mediaLoadData);
        } else {
            mediaPeriodForEvent.sharedPeriod.onLoadStarted(loadEventInfo, mediaLoadData);
            mediaPeriodForEvent.mediaSourceEventDispatcher.loadStarted(loadEventInfo, correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
    public void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline) {
        this.contentTimeline = timeline;
        if (AdPlaybackState.NONE.equals(this.adPlaybackState)) {
            return;
        }
        refreshSourceInfo(new ServerSideInsertedAdsTimeline(timeline, this.adPlaybackState));
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public void onUpstreamDiscarded(int i2, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        MediaPeriodImpl mediaPeriodForEvent = getMediaPeriodForEvent(mediaPeriodId, mediaLoadData, false);
        if (mediaPeriodForEvent == null) {
            this.mediaSourceEventDispatcherWithoutId.upstreamDiscarded(mediaLoadData);
        } else {
            mediaPeriodForEvent.mediaSourceEventDispatcher.upstreamDiscarded(correctMediaLoadData(mediaPeriodForEvent, mediaLoadData, this.adPlaybackState));
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper();
        synchronized (this) {
            this.playbackHandler = handlerCreateHandlerForCurrentLooper;
        }
        this.mediaSource.addEventListener(handlerCreateHandlerForCurrentLooper, this);
        this.mediaSource.addDrmEventListener(handlerCreateHandlerForCurrentLooper, this);
        this.mediaSource.prepareSource(this, transferListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaPeriodImpl mediaPeriodImpl = (MediaPeriodImpl) mediaPeriod;
        mediaPeriodImpl.sharedPeriod.remove(mediaPeriodImpl);
        if (mediaPeriodImpl.sharedPeriod.isUnused()) {
            this.mediaPeriods.remove(Long.valueOf(mediaPeriodImpl.mediaPeriodId.windowSequenceNumber), mediaPeriodImpl.sharedPeriod);
            if (this.mediaPeriods.isEmpty()) {
                this.lastUsedMediaPeriod = mediaPeriodImpl.sharedPeriod;
            } else {
                mediaPeriodImpl.sharedPeriod.release(this.mediaSource);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        releaseLastUsedMediaPeriod();
        this.contentTimeline = null;
        synchronized (this) {
            this.playbackHandler = null;
        }
        this.mediaSource.releaseSource(this);
        this.mediaSource.removeEventListener(this);
        this.mediaSource.removeDrmEventListener(this);
    }

    public void setAdPlaybackState(final AdPlaybackState adPlaybackState) {
        Assertions.checkArgument(adPlaybackState.adGroupCount >= this.adPlaybackState.adGroupCount);
        for (int i2 = adPlaybackState.removedAdGroupCount; i2 < adPlaybackState.adGroupCount; i2++) {
            AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
            Assertions.checkArgument(adGroup.isServerSideInserted);
            if (i2 < this.adPlaybackState.adGroupCount) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i2) >= ServerSideInsertedAdsUtil.getAdCountInGroup(this.adPlaybackState, i2));
            }
            if (adGroup.timeUs == Long.MIN_VALUE) {
                Assertions.checkArgument(ServerSideInsertedAdsUtil.getAdCountInGroup(adPlaybackState, i2) == 0);
            }
        }
        synchronized (this) {
            Handler handler = this.playbackHandler;
            if (handler == null) {
                this.adPlaybackState = adPlaybackState;
            } else {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.i
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6856c.lambda$setAdPlaybackState$0(adPlaybackState);
                    }
                });
            }
        }
    }
}
