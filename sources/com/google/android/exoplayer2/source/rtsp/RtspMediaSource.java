package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.ForwardingTimeline;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod;
import com.google.android.exoplayer2.source.z;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public final class RtspMediaSource extends BaseMediaSource {
    public static final long DEFAULT_TIMEOUT_MS = 8000;
    private final boolean debugLoggingEnabled;
    private final MediaItem mediaItem;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    private boolean timelineIsLive;
    private boolean timelineIsSeekable;
    private final Uri uri;
    private final String userAgent;
    private long timelineDurationUs = C.TIME_UNSET;
    private boolean timelineIsPlaceholder = true;

    public static final class Factory implements MediaSourceFactory {
        private boolean debugLoggingEnabled;
        private boolean forceUseRtpTcp;
        private long timeoutMs = RtspMediaSource.DEFAULT_TIMEOUT_MS;
        private String userAgent = ExoPlayerLibraryInfo.VERSION_SLASHY;

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public /* synthetic */ MediaSource createMediaSource(Uri uri) {
            return z.a(this, uri);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            return new int[]{3};
        }

        public Factory setDebugLoggingEnabled(boolean z2) {
            this.debugLoggingEnabled = z2;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public Factory setDrmUserAgent(@Nullable String str) {
            return this;
        }

        public Factory setForceUseRtpTcp(boolean z2) {
            this.forceUseRtpTcp = z2;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public Factory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public /* synthetic */ MediaSourceFactory setStreamKeys(List list) {
            return z.b(this, list);
        }

        public Factory setTimeoutMs(@IntRange(from = 1) long j2) {
            Assertions.checkArgument(j2 > 0);
            this.timeoutMs = j2;
            return this;
        }

        public Factory setUserAgent(String str) {
            this.userAgent = str;
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public RtspMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.localConfiguration);
            return new RtspMediaSource(mediaItem, this.forceUseRtpTcp ? new TransferRtpDataChannelFactory(this.timeoutMs) : new UdpDataSourceRtpDataChannelFactory(this.timeoutMs), this.userAgent, this.debugLoggingEnabled);
        }
    }

    public static final class RtspPlaybackException extends IOException {
        public RtspPlaybackException(String str) {
            super(str);
        }

        public RtspPlaybackException(Throwable th) {
            super(th);
        }

        public RtspPlaybackException(String str, Throwable th) {
            super(str, th);
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.rtsp");
    }

    @VisibleForTesting
    public RtspMediaSource(MediaItem mediaItem, RtpDataChannel.Factory factory, String str, boolean z2) {
        this.mediaItem = mediaItem;
        this.rtpDataChannelFactory = factory;
        this.userAgent = str;
        this.uri = ((MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration)).uri;
        this.debugLoggingEnabled = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPeriod$0(RtspSessionTiming rtspSessionTiming) {
        this.timelineDurationUs = Util.msToUs(rtspSessionTiming.getDurationMs());
        this.timelineIsSeekable = !rtspSessionTiming.isLive();
        this.timelineIsLive = rtspSessionTiming.isLive();
        this.timelineIsPlaceholder = false;
        notifySourceInfoRefreshed();
    }

    private void notifySourceInfoRefreshed() {
        Timeline singlePeriodTimeline = new SinglePeriodTimeline(this.timelineDurationUs, this.timelineIsSeekable, false, this.timelineIsLive, (Object) null, this.mediaItem);
        if (this.timelineIsPlaceholder) {
            singlePeriodTimeline = new ForwardingTimeline(this, singlePeriodTimeline) { // from class: com.google.android.exoplayer2.source.rtsp.RtspMediaSource.1
                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Period getPeriod(int i2, Timeline.Period period, boolean z2) {
                    super.getPeriod(i2, period, z2);
                    period.isPlaceholder = true;
                    return period;
                }

                @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
                public Timeline.Window getWindow(int i2, Timeline.Window window, long j2) {
                    super.getWindow(i2, window, j2);
                    window.isPlaceholder = true;
                    return window;
                }
            };
        }
        refreshSourceInfo(singlePeriodTimeline);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        return new RtspMediaPeriod(allocator, this.rtpDataChannelFactory, this.uri, new RtspMediaPeriod.Listener() { // from class: com.google.android.exoplayer2.source.rtsp.h
            @Override // com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod.Listener
            public final void onSourceInfoRefreshed(RtspSessionTiming rtspSessionTiming) {
                this.f6891a.lambda$createPeriod$0(rtspSessionTiming);
            }
        }, this.userAgent, this.debugLoggingEnabled);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() {
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        notifySourceInfoRefreshed();
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((RtspMediaPeriod) mediaPeriod).release();
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    public void releaseSourceInternal() {
    }
}
