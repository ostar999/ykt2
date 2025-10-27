package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UnknownNull;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes3.dex */
public abstract class CompositeMediaSource<T> extends BaseMediaSource {
    private final HashMap<T, MediaSourceAndListener<T>> childSources = new HashMap<>();

    @Nullable
    private Handler eventHandler;

    @Nullable
    private TransferListener mediaTransferListener;

    public final class ForwardingEventListener implements MediaSourceEventListener, DrmSessionEventListener {
        private DrmSessionEventListener.EventDispatcher drmEventDispatcher;

        @UnknownNull
        private final T id;
        private MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;

        public ForwardingEventListener(@UnknownNull T t2) {
            this.mediaSourceEventDispatcher = CompositeMediaSource.this.createEventDispatcher(null);
            this.drmEventDispatcher = CompositeMediaSource.this.createDrmEventDispatcher(null);
            this.id = t2;
        }

        private boolean maybeUpdateEventDispatcher(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            MediaSource.MediaPeriodId mediaPeriodIdForChildMediaPeriodId;
            if (mediaPeriodId != null) {
                mediaPeriodIdForChildMediaPeriodId = CompositeMediaSource.this.getMediaPeriodIdForChildMediaPeriodId(this.id, mediaPeriodId);
                if (mediaPeriodIdForChildMediaPeriodId == null) {
                    return false;
                }
            } else {
                mediaPeriodIdForChildMediaPeriodId = null;
            }
            int windowIndexForChildWindowIndex = CompositeMediaSource.this.getWindowIndexForChildWindowIndex(this.id, i2);
            MediaSourceEventListener.EventDispatcher eventDispatcher = this.mediaSourceEventDispatcher;
            if (eventDispatcher.windowIndex != windowIndexForChildWindowIndex || !Util.areEqual(eventDispatcher.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                this.mediaSourceEventDispatcher = CompositeMediaSource.this.createEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId, 0L);
            }
            DrmSessionEventListener.EventDispatcher eventDispatcher2 = this.drmEventDispatcher;
            if (eventDispatcher2.windowIndex == windowIndexForChildWindowIndex && Util.areEqual(eventDispatcher2.mediaPeriodId, mediaPeriodIdForChildMediaPeriodId)) {
                return true;
            }
            this.drmEventDispatcher = CompositeMediaSource.this.createDrmEventDispatcher(windowIndexForChildWindowIndex, mediaPeriodIdForChildMediaPeriodId);
            return true;
        }

        private MediaLoadData maybeUpdateMediaLoadData(MediaLoadData mediaLoadData) {
            long mediaTimeForChildMediaTime = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.id, mediaLoadData.mediaStartTimeMs);
            long mediaTimeForChildMediaTime2 = CompositeMediaSource.this.getMediaTimeForChildMediaTime(this.id, mediaLoadData.mediaEndTimeMs);
            return (mediaTimeForChildMediaTime == mediaLoadData.mediaStartTimeMs && mediaTimeForChildMediaTime2 == mediaLoadData.mediaEndTimeMs) ? mediaLoadData : new MediaLoadData(mediaLoadData.dataType, mediaLoadData.trackType, mediaLoadData.trackFormat, mediaLoadData.trackSelectionReason, mediaLoadData.trackSelectionData, mediaTimeForChildMediaTime, mediaTimeForChildMediaTime2);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onDownstreamFormatChanged(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.downstreamFormatChanged(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysLoaded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysLoaded();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRemoved(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmKeysRestored(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmKeysRestored();
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public /* synthetic */ void onDrmSessionAcquired(int i2, MediaSource.MediaPeriodId mediaPeriodId) {
            com.google.android.exoplayer2.drm.j.d(this, i2, mediaPeriodId);
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionAcquired(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, int i3) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionAcquired(i3);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionManagerError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception exc) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionManagerError(exc);
            }
        }

        @Override // com.google.android.exoplayer2.drm.DrmSessionEventListener
        public void onDrmSessionReleased(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.drmEventDispatcher.drmSessionReleased();
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCanceled(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCanceled(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadCompleted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadCompleted(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadError(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData), iOException, z2);
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onLoadStarted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.loadStarted(loadEventInfo, maybeUpdateMediaLoadData(mediaLoadData));
            }
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
        public void onUpstreamDiscarded(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            if (maybeUpdateEventDispatcher(i2, mediaPeriodId)) {
                this.mediaSourceEventDispatcher.upstreamDiscarded(maybeUpdateMediaLoadData(mediaLoadData));
            }
        }
    }

    public static final class MediaSourceAndListener<T> {
        public final MediaSource.MediaSourceCaller caller;
        public final CompositeMediaSource<T>.ForwardingEventListener eventListener;
        public final MediaSource mediaSource;

        public MediaSourceAndListener(MediaSource mediaSource, MediaSource.MediaSourceCaller mediaSourceCaller, CompositeMediaSource<T>.ForwardingEventListener forwardingEventListener) {
            this.mediaSource = mediaSource;
            this.caller = mediaSourceCaller;
            this.eventListener = forwardingEventListener;
        }
    }

    public final void disableChildSource(@UnknownNull T t2) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(t2));
        mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void disableInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.disable(mediaSourceAndListener.caller);
        }
    }

    public final void enableChildSource(@UnknownNull T t2) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.get(t2));
        mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void enableInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.enable(mediaSourceAndListener.caller);
        }
    }

    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(@UnknownNull T t2, MediaSource.MediaPeriodId mediaPeriodId) {
        return mediaPeriodId;
    }

    public long getMediaTimeForChildMediaTime(@UnknownNull T t2, long j2) {
        return j2;
    }

    public int getWindowIndexForChildWindowIndex(@UnknownNull T t2, int i2) {
        return i2;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    @CallSuper
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        Iterator<MediaSourceAndListener<T>> it = this.childSources.values().iterator();
        while (it.hasNext()) {
            it.next().mediaSource.maybeThrowSourceInfoRefreshError();
        }
    }

    /* renamed from: onChildSourceInfoRefreshed, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$prepareChildSource$0(@UnknownNull T t2, MediaSource mediaSource, Timeline timeline);

    public final void prepareChildSource(@UnknownNull final T t2, MediaSource mediaSource) {
        Assertions.checkArgument(!this.childSources.containsKey(t2));
        MediaSource.MediaSourceCaller mediaSourceCaller = new MediaSource.MediaSourceCaller() { // from class: com.google.android.exoplayer2.source.a
            @Override // com.google.android.exoplayer2.source.MediaSource.MediaSourceCaller
            public final void onSourceInfoRefreshed(MediaSource mediaSource2, Timeline timeline) {
                this.f6842c.lambda$prepareChildSource$0(t2, mediaSource2, timeline);
            }
        };
        ForwardingEventListener forwardingEventListener = new ForwardingEventListener(t2);
        this.childSources.put(t2, new MediaSourceAndListener<>(mediaSource, mediaSourceCaller, forwardingEventListener));
        mediaSource.addEventListener((Handler) Assertions.checkNotNull(this.eventHandler), forwardingEventListener);
        mediaSource.addDrmEventListener((Handler) Assertions.checkNotNull(this.eventHandler), forwardingEventListener);
        mediaSource.prepareSource(mediaSourceCaller, this.mediaTransferListener);
        if (isEnabled()) {
            return;
        }
        mediaSource.disable(mediaSourceCaller);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void prepareSourceInternal(@Nullable TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.eventHandler = Util.createHandlerForCurrentLooper();
    }

    public final void releaseChildSource(@UnknownNull T t2) {
        MediaSourceAndListener mediaSourceAndListener = (MediaSourceAndListener) Assertions.checkNotNull(this.childSources.remove(t2));
        mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
        mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
        mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
    }

    @Override // com.google.android.exoplayer2.source.BaseMediaSource
    @CallSuper
    public void releaseSourceInternal() {
        for (MediaSourceAndListener<T> mediaSourceAndListener : this.childSources.values()) {
            mediaSourceAndListener.mediaSource.releaseSource(mediaSourceAndListener.caller);
            mediaSourceAndListener.mediaSource.removeEventListener(mediaSourceAndListener.eventListener);
            mediaSourceAndListener.mediaSource.removeDrmEventListener(mediaSourceAndListener.eventListener);
        }
        this.childSources.clear();
    }
}
