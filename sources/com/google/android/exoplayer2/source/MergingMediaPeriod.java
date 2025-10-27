package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

/* loaded from: classes3.dex */
final class MergingMediaPeriod implements MediaPeriod, MediaPeriod.Callback {

    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final MediaPeriod[] periods;

    @Nullable
    private TrackGroupArray trackGroups;
    private final ArrayList<MediaPeriod> childrenPendingPreparation = new ArrayList<>();
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices = new IdentityHashMap<>();
    private MediaPeriod[] enabledPeriods = new MediaPeriod[0];

    public static final class TimeOffsetMediaPeriod implements MediaPeriod, MediaPeriod.Callback {
        private MediaPeriod.Callback callback;
        private final MediaPeriod mediaPeriod;
        private final long timeOffsetUs;

        public TimeOffsetMediaPeriod(MediaPeriod mediaPeriod, long j2) {
            this.mediaPeriod = mediaPeriod;
            this.timeOffsetUs = j2;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean continueLoading(long j2) {
            return this.mediaPeriod.continueLoading(j2 - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void discardBuffer(long j2, boolean z2) {
            this.mediaPeriod.discardBuffer(j2 - this.timeOffsetUs, z2);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
            return this.mediaPeriod.getAdjustedSeekPositionUs(j2 - this.timeOffsetUs, seekParameters) + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getBufferedPositionUs() {
            long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
            if (bufferedPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.timeOffsetUs + bufferedPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public long getNextLoadPositionUs() {
            long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
            if (nextLoadPositionUs == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            return this.timeOffsetUs + nextLoadPositionUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
            return this.mediaPeriod.getStreamKeys(list);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public TrackGroupArray getTrackGroups() {
            return this.mediaPeriod.getTrackGroups();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public boolean isLoading() {
            return this.mediaPeriod.isLoading();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void maybeThrowPrepareError() throws IOException {
            this.mediaPeriod.maybeThrowPrepareError();
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
        public void onPrepared(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public void prepare(MediaPeriod.Callback callback, long j2) {
            this.callback = callback;
            this.mediaPeriod.prepare(this, j2 - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long readDiscontinuity() {
            long discontinuity = this.mediaPeriod.readDiscontinuity();
            return discontinuity == C.TIME_UNSET ? C.TIME_UNSET : this.timeOffsetUs + discontinuity;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
        public void reevaluateBuffer(long j2) {
            this.mediaPeriod.reevaluateBuffer(j2 - this.timeOffsetUs);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long seekToUs(long j2) {
            return this.mediaPeriod.seekToUs(j2 - this.timeOffsetUs) + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriod
        public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
            SampleStream[] sampleStreamArr2 = new SampleStream[sampleStreamArr.length];
            int i2 = 0;
            while (true) {
                SampleStream childStream = null;
                if (i2 >= sampleStreamArr.length) {
                    break;
                }
                TimeOffsetSampleStream timeOffsetSampleStream = (TimeOffsetSampleStream) sampleStreamArr[i2];
                if (timeOffsetSampleStream != null) {
                    childStream = timeOffsetSampleStream.getChildStream();
                }
                sampleStreamArr2[i2] = childStream;
                i2++;
            }
            long jSelectTracks = this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr2, zArr2, j2 - this.timeOffsetUs);
            for (int i3 = 0; i3 < sampleStreamArr.length; i3++) {
                SampleStream sampleStream = sampleStreamArr2[i3];
                if (sampleStream == null) {
                    sampleStreamArr[i3] = null;
                } else {
                    SampleStream sampleStream2 = sampleStreamArr[i3];
                    if (sampleStream2 == null || ((TimeOffsetSampleStream) sampleStream2).getChildStream() != sampleStream) {
                        sampleStreamArr[i3] = new TimeOffsetSampleStream(sampleStream, this.timeOffsetUs);
                    }
                }
            }
            return jSelectTracks + this.timeOffsetUs;
        }

        @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
        public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
        }
    }

    public static final class TimeOffsetSampleStream implements SampleStream {
        private final SampleStream sampleStream;
        private final long timeOffsetUs;

        public TimeOffsetSampleStream(SampleStream sampleStream, long j2) {
            this.sampleStream = sampleStream;
            this.timeOffsetUs = j2;
        }

        public SampleStream getChildStream() {
            return this.sampleStream;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public boolean isReady() {
            return this.sampleStream.isReady();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public void maybeThrowError() throws IOException {
            this.sampleStream.maybeThrowError();
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            int data = this.sampleStream.readData(formatHolder, decoderInputBuffer, i2);
            if (data == -4) {
                decoderInputBuffer.timeUs = Math.max(0L, decoderInputBuffer.timeUs + this.timeOffsetUs);
            }
            return data;
        }

        @Override // com.google.android.exoplayer2.source.SampleStream
        public int skipData(long j2) {
            return this.sampleStream.skipData(j2 - this.timeOffsetUs);
        }
    }

    public MergingMediaPeriod(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, long[] jArr, MediaPeriod... mediaPeriodArr) {
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.periods = mediaPeriodArr;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        for (int i2 = 0; i2 < mediaPeriodArr.length; i2++) {
            long j2 = jArr[i2];
            if (j2 != 0) {
                this.periods[i2] = new TimeOffsetMediaPeriod(mediaPeriodArr[i2], j2);
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j2) {
        if (this.childrenPendingPreparation.isEmpty()) {
            return this.compositeSequenceableLoader.continueLoading(j2);
        }
        int size = this.childrenPendingPreparation.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.childrenPendingPreparation.get(i2).continueLoading(j2);
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        for (MediaPeriod mediaPeriod : this.enabledPeriods) {
            mediaPeriod.discardBuffer(j2, z2);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
        return (mediaPeriodArr.length > 0 ? mediaPeriodArr[0] : this.periods[0]).getAdjustedSeekPositionUs(j2, seekParameters);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public MediaPeriod getChildPeriod(int i2) {
        MediaPeriod mediaPeriod = this.periods[i2];
        return mediaPeriod instanceof TimeOffsetMediaPeriod ? ((TimeOffsetMediaPeriod) mediaPeriod).mediaPeriod : mediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        return q.a(this, list);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.compositeSequenceableLoader.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod mediaPeriod : this.periods) {
            mediaPeriod.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod mediaPeriod) {
        this.childrenPendingPreparation.remove(mediaPeriod);
        if (this.childrenPendingPreparation.isEmpty()) {
            int i2 = 0;
            for (MediaPeriod mediaPeriod2 : this.periods) {
                i2 += mediaPeriod2.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            int i3 = 0;
            for (MediaPeriod mediaPeriod3 : this.periods) {
                TrackGroupArray trackGroups = mediaPeriod3.getTrackGroups();
                int i4 = trackGroups.length;
                int i5 = 0;
                while (i5 < i4) {
                    trackGroupArr[i3] = trackGroups.get(i5);
                    i5++;
                    i3++;
                }
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        Collections.addAll(this.childrenPendingPreparation, this.periods);
        for (MediaPeriod mediaPeriod : this.periods) {
            mediaPeriod.prepare(this, j2);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        long j2 = -9223372036854775807L;
        for (MediaPeriod mediaPeriod : this.enabledPeriods) {
            long discontinuity = mediaPeriod.readDiscontinuity();
            if (discontinuity == C.TIME_UNSET) {
                if (j2 != C.TIME_UNSET && mediaPeriod.seekToUs(j2) != j2) {
                    throw new IllegalStateException("Unexpected child seekToUs result.");
                }
            } else if (j2 == C.TIME_UNSET) {
                for (MediaPeriod mediaPeriod2 : this.enabledPeriods) {
                    if (mediaPeriod2 == mediaPeriod) {
                        break;
                    }
                    if (mediaPeriod2.seekToUs(discontinuity) != discontinuity) {
                        throw new IllegalStateException("Unexpected child seekToUs result.");
                    }
                }
                j2 = discontinuity;
            } else if (discontinuity != j2) {
                throw new IllegalStateException("Conflicting discontinuities.");
            }
        }
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
        this.compositeSequenceableLoader.reevaluateBuffer(j2);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j2) {
        long jSeekToUs = this.enabledPeriods[0].seekToUs(j2);
        int i2 = 1;
        while (true) {
            MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
            if (i2 >= mediaPeriodArr.length) {
                return jSeekToUs;
            }
            if (mediaPeriodArr[i2].seekToUs(jSeekToUs) != jSeekToUs) {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
            i2++;
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        int[] iArr = new int[exoTrackSelectionArr.length];
        int[] iArr2 = new int[exoTrackSelectionArr.length];
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            SampleStream sampleStream = sampleStreamArr[i2];
            Integer num = sampleStream == null ? null : this.streamPeriodIndices.get(sampleStream);
            iArr[i2] = num == null ? -1 : num.intValue();
            iArr2[i2] = -1;
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
            if (exoTrackSelection != null) {
                TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                int i3 = 0;
                while (true) {
                    MediaPeriod[] mediaPeriodArr = this.periods;
                    if (i3 >= mediaPeriodArr.length) {
                        break;
                    }
                    if (mediaPeriodArr[i3].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i2] = i3;
                        break;
                    }
                    i3++;
                }
            }
        }
        this.streamPeriodIndices.clear();
        int length = exoTrackSelectionArr.length;
        SampleStream[] sampleStreamArr2 = new SampleStream[length];
        SampleStream[] sampleStreamArr3 = new SampleStream[exoTrackSelectionArr.length];
        ExoTrackSelection[] exoTrackSelectionArr2 = new ExoTrackSelection[exoTrackSelectionArr.length];
        ArrayList arrayList = new ArrayList(this.periods.length);
        long j3 = j2;
        int i4 = 0;
        while (i4 < this.periods.length) {
            for (int i5 = 0; i5 < exoTrackSelectionArr.length; i5++) {
                sampleStreamArr3[i5] = iArr[i5] == i4 ? sampleStreamArr[i5] : null;
                exoTrackSelectionArr2[i5] = iArr2[i5] == i4 ? exoTrackSelectionArr[i5] : null;
            }
            int i6 = i4;
            ArrayList arrayList2 = arrayList;
            ExoTrackSelection[] exoTrackSelectionArr3 = exoTrackSelectionArr2;
            long jSelectTracks = this.periods[i4].selectTracks(exoTrackSelectionArr2, zArr, sampleStreamArr3, zArr2, j3);
            if (i6 == 0) {
                j3 = jSelectTracks;
            } else if (jSelectTracks != j3) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean z2 = false;
            for (int i7 = 0; i7 < exoTrackSelectionArr.length; i7++) {
                if (iArr2[i7] == i6) {
                    SampleStream sampleStream2 = (SampleStream) Assertions.checkNotNull(sampleStreamArr3[i7]);
                    sampleStreamArr2[i7] = sampleStreamArr3[i7];
                    this.streamPeriodIndices.put(sampleStream2, Integer.valueOf(i6));
                    z2 = true;
                } else if (iArr[i7] == i6) {
                    Assertions.checkState(sampleStreamArr3[i7] == null);
                }
            }
            if (z2) {
                arrayList2.add(this.periods[i6]);
            }
            i4 = i6 + 1;
            arrayList = arrayList2;
            exoTrackSelectionArr2 = exoTrackSelectionArr3;
        }
        System.arraycopy(sampleStreamArr2, 0, sampleStreamArr, 0, length);
        MediaPeriod[] mediaPeriodArr2 = (MediaPeriod[]) arrayList.toArray(new MediaPeriod[0]);
        this.enabledPeriods = mediaPeriodArr2;
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(mediaPeriodArr2);
        return j3;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }
}
