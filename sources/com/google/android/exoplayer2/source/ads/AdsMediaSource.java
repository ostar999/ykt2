package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.CompositeMediaSource;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MaskingMediaPeriod;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public final class AdsMediaSource extends CompositeMediaSource<MediaSource.MediaPeriodId> {
    private static final MediaSource.MediaPeriodId CHILD_SOURCE_MEDIA_PERIOD_ID = new MediaSource.MediaPeriodId(new Object());
    private final MediaSourceFactory adMediaSourceFactory;

    @Nullable
    private AdPlaybackState adPlaybackState;
    private final DataSpec adTagDataSpec;
    private final AdViewProvider adViewProvider;
    private final Object adsId;
    private final AdsLoader adsLoader;

    @Nullable
    private ComponentListener componentListener;
    private final MediaSource contentMediaSource;

    @Nullable
    private Timeline contentTimeline;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Timeline.Period period = new Timeline.Period();
    private AdMediaSourceHolder[][] adMediaSourceHolders = new AdMediaSourceHolder[0][];

    public static final class AdLoadException extends IOException {
        public static final int TYPE_AD = 0;
        public static final int TYPE_AD_GROUP = 1;
        public static final int TYPE_ALL_ADS = 2;
        public static final int TYPE_UNEXPECTED = 3;
        public final int type;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        private AdLoadException(int i2, Exception exc) {
            super(exc);
            this.type = i2;
        }

        public static AdLoadException createForAd(Exception exc) {
            return new AdLoadException(0, exc);
        }

        public static AdLoadException createForAdGroup(Exception exc, int i2) {
            StringBuilder sb = new StringBuilder(35);
            sb.append("Failed to load ad group ");
            sb.append(i2);
            return new AdLoadException(1, new IOException(sb.toString(), exc));
        }

        public static AdLoadException createForAllAds(Exception exc) {
            return new AdLoadException(2, exc);
        }

        public static AdLoadException createForUnexpected(RuntimeException runtimeException) {
            return new AdLoadException(3, runtimeException);
        }

        public RuntimeException getRuntimeExceptionForUnexpected() {
            Assertions.checkState(this.type == 3);
            return (RuntimeException) Assertions.checkNotNull(getCause());
        }
    }

    public final class AdMediaSourceHolder {
        private final List<MaskingMediaPeriod> activeMediaPeriods = new ArrayList();
        private MediaSource adMediaSource;
        private Uri adUri;
        private final MediaSource.MediaPeriodId id;
        private Timeline timeline;

        public AdMediaSourceHolder(MediaSource.MediaPeriodId mediaPeriodId) {
            this.id = mediaPeriodId;
        }

        public MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j2);
            this.activeMediaPeriods.add(maskingMediaPeriod);
            MediaSource mediaSource = this.adMediaSource;
            if (mediaSource != null) {
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(AdsMediaSource.this.new AdPrepareListener((Uri) Assertions.checkNotNull(this.adUri)));
            }
            Timeline timeline = this.timeline;
            if (timeline != null) {
                maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(timeline.getUidOfPeriod(0), mediaPeriodId.windowSequenceNumber));
            }
            return maskingMediaPeriod;
        }

        public long getDurationUs() {
            Timeline timeline = this.timeline;
            return timeline == null ? C.TIME_UNSET : timeline.getPeriod(0, AdsMediaSource.this.period).getDurationUs();
        }

        public void handleSourceInfoRefresh(Timeline timeline) {
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            if (this.timeline == null) {
                Object uidOfPeriod = timeline.getUidOfPeriod(0);
                for (int i2 = 0; i2 < this.activeMediaPeriods.size(); i2++) {
                    MaskingMediaPeriod maskingMediaPeriod = this.activeMediaPeriods.get(i2);
                    maskingMediaPeriod.createPeriod(new MediaSource.MediaPeriodId(uidOfPeriod, maskingMediaPeriod.id.windowSequenceNumber));
                }
            }
            this.timeline = timeline;
        }

        public boolean hasMediaSource() {
            return this.adMediaSource != null;
        }

        public void initializeWithMediaSource(MediaSource mediaSource, Uri uri) {
            this.adMediaSource = mediaSource;
            this.adUri = uri;
            for (int i2 = 0; i2 < this.activeMediaPeriods.size(); i2++) {
                MaskingMediaPeriod maskingMediaPeriod = this.activeMediaPeriods.get(i2);
                maskingMediaPeriod.setMediaSource(mediaSource);
                maskingMediaPeriod.setPrepareListener(AdsMediaSource.this.new AdPrepareListener(uri));
            }
            AdsMediaSource.this.prepareChildSource(this.id, mediaSource);
        }

        public boolean isInactive() {
            return this.activeMediaPeriods.isEmpty();
        }

        public void release() {
            if (hasMediaSource()) {
                AdsMediaSource.this.releaseChildSource(this.id);
            }
        }

        public void releaseMediaPeriod(MaskingMediaPeriod maskingMediaPeriod) {
            this.activeMediaPeriods.remove(maskingMediaPeriod);
            maskingMediaPeriod.releasePeriod();
        }
    }

    public final class AdPrepareListener implements MaskingMediaPeriod.PrepareListener {
        private final Uri adUri;

        public AdPrepareListener(Uri uri) {
            this.adUri = uri;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPrepareComplete$0(MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.adsLoader.handlePrepareComplete(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPrepareError$1(MediaSource.MediaPeriodId mediaPeriodId, IOException iOException) {
            AdsMediaSource.this.adsLoader.handlePrepareError(AdsMediaSource.this, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, iOException);
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareComplete(final MediaSource.MediaPeriodId mediaPeriodId) {
            AdsMediaSource.this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6849c.lambda$onPrepareComplete$0(mediaPeriodId);
                }
            });
        }

        @Override // com.google.android.exoplayer2.source.MaskingMediaPeriod.PrepareListener
        public void onPrepareError(final MediaSource.MediaPeriodId mediaPeriodId, final IOException iOException) {
            AdsMediaSource.this.createEventDispatcher(mediaPeriodId).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), new DataSpec(this.adUri), SystemClock.elapsedRealtime()), 6, (IOException) AdLoadException.createForAd(iOException), true);
            AdsMediaSource.this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6851c.lambda$onPrepareError$1(mediaPeriodId, iOException);
                }
            });
        }
    }

    public final class ComponentListener implements AdsLoader.EventListener {
        private final Handler playerHandler = Util.createHandlerForCurrentLooper();
        private volatile boolean stopped;

        public ComponentListener() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAdPlaybackState$0(AdPlaybackState adPlaybackState) {
            if (this.stopped) {
                return;
            }
            AdsMediaSource.this.onAdPlaybackState(adPlaybackState);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public /* synthetic */ void onAdClicked() {
            c.a(this);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdLoadError(AdLoadException adLoadException, DataSpec dataSpec) {
            if (this.stopped) {
                return;
            }
            AdsMediaSource.this.createEventDispatcher(null).loadError(new LoadEventInfo(LoadEventInfo.getNewId(), dataSpec, SystemClock.elapsedRealtime()), 6, (IOException) adLoadException, true);
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public void onAdPlaybackState(final AdPlaybackState adPlaybackState) {
            if (this.stopped) {
                return;
            }
            this.playerHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6854c.lambda$onAdPlaybackState$0(adPlaybackState);
                }
            });
        }

        @Override // com.google.android.exoplayer2.source.ads.AdsLoader.EventListener
        public /* synthetic */ void onAdTapped() {
            c.d(this);
        }

        public void stop() {
            this.stopped = true;
            this.playerHandler.removeCallbacksAndMessages(null);
        }
    }

    public AdsMediaSource(MediaSource mediaSource, DataSpec dataSpec, Object obj, MediaSourceFactory mediaSourceFactory, AdsLoader adsLoader, AdViewProvider adViewProvider) {
        this.contentMediaSource = mediaSource;
        this.adMediaSourceFactory = mediaSourceFactory;
        this.adsLoader = adsLoader;
        this.adViewProvider = adViewProvider;
        this.adTagDataSpec = dataSpec;
        this.adsId = obj;
        adsLoader.setSupportedContentTypes(mediaSourceFactory.getSupportedTypes());
    }

    private long[][] getAdDurationsUs() {
        long[][] jArr = new long[this.adMediaSourceHolders.length][];
        int i2 = 0;
        while (true) {
            AdMediaSourceHolder[][] adMediaSourceHolderArr = this.adMediaSourceHolders;
            if (i2 >= adMediaSourceHolderArr.length) {
                return jArr;
            }
            jArr[i2] = new long[adMediaSourceHolderArr[i2].length];
            int i3 = 0;
            while (true) {
                AdMediaSourceHolder[] adMediaSourceHolderArr2 = this.adMediaSourceHolders[i2];
                if (i3 < adMediaSourceHolderArr2.length) {
                    AdMediaSourceHolder adMediaSourceHolder = adMediaSourceHolderArr2[i3];
                    jArr[i2][i3] = adMediaSourceHolder == null ? C.TIME_UNSET : adMediaSourceHolder.getDurationUs();
                    i3++;
                }
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareSourceInternal$0(ComponentListener componentListener) {
        this.adsLoader.start(this, this.adTagDataSpec, this.adsId, this.adViewProvider, componentListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseSourceInternal$1(ComponentListener componentListener) {
        this.adsLoader.stop(this, componentListener);
    }

    private void maybeUpdateAdMediaSources() {
        Uri uri;
        AdPlaybackState adPlaybackState = this.adPlaybackState;
        if (adPlaybackState == null) {
            return;
        }
        for (int i2 = 0; i2 < this.adMediaSourceHolders.length; i2++) {
            int i3 = 0;
            while (true) {
                AdMediaSourceHolder[] adMediaSourceHolderArr = this.adMediaSourceHolders[i2];
                if (i3 < adMediaSourceHolderArr.length) {
                    AdMediaSourceHolder adMediaSourceHolder = adMediaSourceHolderArr[i3];
                    AdPlaybackState.AdGroup adGroup = adPlaybackState.getAdGroup(i2);
                    if (adMediaSourceHolder != null && !adMediaSourceHolder.hasMediaSource()) {
                        Uri[] uriArr = adGroup.uris;
                        if (i3 < uriArr.length && (uri = uriArr[i3]) != null) {
                            MediaItem.Builder uri2 = new MediaItem.Builder().setUri(uri);
                            MediaItem.LocalConfiguration localConfiguration = this.contentMediaSource.getMediaItem().localConfiguration;
                            if (localConfiguration != null) {
                                uri2.setDrmConfiguration(localConfiguration.drmConfiguration);
                            }
                            adMediaSourceHolder.initializeWithMediaSource(this.adMediaSourceFactory.createMediaSource(uri2.build()), uri);
                        }
                    }
                    i3++;
                }
            }
        }
    }

    private void maybeUpdateSourceInfo() {
        Timeline timeline = this.contentTimeline;
        AdPlaybackState adPlaybackState = this.adPlaybackState;
        if (adPlaybackState == null || timeline == null) {
            return;
        }
        if (adPlaybackState.adGroupCount == 0) {
            refreshSourceInfo(timeline);
        } else {
            this.adPlaybackState = adPlaybackState.withAdDurationsUs(getAdDurationsUs());
            refreshSourceInfo(new SinglePeriodAdTimeline(timeline, this.adPlaybackState));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAdPlaybackState(AdPlaybackState adPlaybackState) {
        AdPlaybackState adPlaybackState2 = this.adPlaybackState;
        if (adPlaybackState2 == null) {
            AdMediaSourceHolder[][] adMediaSourceHolderArr = new AdMediaSourceHolder[adPlaybackState.adGroupCount][];
            this.adMediaSourceHolders = adMediaSourceHolderArr;
            Arrays.fill(adMediaSourceHolderArr, new AdMediaSourceHolder[0]);
        } else {
            Assertions.checkState(adPlaybackState.adGroupCount == adPlaybackState2.adGroupCount);
        }
        this.adPlaybackState = adPlaybackState;
        maybeUpdateAdMediaSources();
        maybeUpdateSourceInfo();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        if (((AdPlaybackState) Assertions.checkNotNull(this.adPlaybackState)).adGroupCount <= 0 || !mediaPeriodId.isAd()) {
            MaskingMediaPeriod maskingMediaPeriod = new MaskingMediaPeriod(mediaPeriodId, allocator, j2);
            maskingMediaPeriod.setMediaSource(this.contentMediaSource);
            maskingMediaPeriod.createPeriod(mediaPeriodId);
            return maskingMediaPeriod;
        }
        int i2 = mediaPeriodId.adGroupIndex;
        int i3 = mediaPeriodId.adIndexInAdGroup;
        AdMediaSourceHolder[][] adMediaSourceHolderArr = this.adMediaSourceHolders;
        AdMediaSourceHolder[] adMediaSourceHolderArr2 = adMediaSourceHolderArr[i2];
        if (adMediaSourceHolderArr2.length <= i3) {
            adMediaSourceHolderArr[i2] = (AdMediaSourceHolder[]) Arrays.copyOf(adMediaSourceHolderArr2, i3 + 1);
        }
        AdMediaSourceHolder adMediaSourceHolder = this.adMediaSourceHolders[i2][i3];
        if (adMediaSourceHolder == null) {
            adMediaSourceHolder = new AdMediaSourceHolder(mediaPeriodId);
            this.adMediaSourceHolders[i2][i3] = adMediaSourceHolder;
            maybeUpdateAdMediaSources();
        }
        return adMediaSourceHolder.createMediaPeriod(mediaPeriodId, allocator, j2);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.contentMediaSource.getMediaItem();
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        super.prepareSourceInternal(transferListener);
        final ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        prepareChildSource(CHILD_SOURCE_MEDIA_PERIOD_ID, this.contentMediaSource);
        this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f6847c.lambda$prepareSourceInternal$0(componentListener);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MaskingMediaPeriod maskingMediaPeriod = (MaskingMediaPeriod) mediaPeriod;
        MediaSource.MediaPeriodId mediaPeriodId = maskingMediaPeriod.id;
        if (!mediaPeriodId.isAd()) {
            maskingMediaPeriod.releasePeriod();
            return;
        }
        AdMediaSourceHolder adMediaSourceHolder = (AdMediaSourceHolder) Assertions.checkNotNull(this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup]);
        adMediaSourceHolder.releaseMediaPeriod(maskingMediaPeriod);
        if (adMediaSourceHolder.isInactive()) {
            adMediaSourceHolder.release();
            this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup] = null;
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        final ComponentListener componentListener = (ComponentListener) Assertions.checkNotNull(this.componentListener);
        this.componentListener = null;
        componentListener.stop();
        this.contentTimeline = null;
        this.adPlaybackState = null;
        this.adMediaSourceHolders = new AdMediaSourceHolder[0][];
        this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.ads.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f6845c.lambda$releaseSourceInternal$1(componentListener);
            }
        });
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSource.MediaPeriodId mediaPeriodId, MediaSource.MediaPeriodId mediaPeriodId2) {
        return mediaPeriodId.isAd() ? mediaPeriodId : mediaPeriodId2;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public void lambda$prepareChildSource$0(MediaSource.MediaPeriodId mediaPeriodId, MediaSource mediaSource, Timeline timeline) {
        if (mediaPeriodId.isAd()) {
            ((AdMediaSourceHolder) Assertions.checkNotNull(this.adMediaSourceHolders[mediaPeriodId.adGroupIndex][mediaPeriodId.adIndexInAdGroup])).handleSourceInfoRefresh(timeline);
        } else {
            Assertions.checkArgument(timeline.getPeriodCount() == 1);
            this.contentTimeline = timeline;
        }
        maybeUpdateSourceInfo();
    }
}
