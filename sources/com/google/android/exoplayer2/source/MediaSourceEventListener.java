package com.google.android.exoplayer2.source;

import android.os.Handler;
import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public interface MediaSourceEventListener {

    public static class EventDispatcher {
        private final CopyOnWriteArrayList<ListenerAndHandler> listenerAndHandlers;

        @Nullable
        public final MediaSource.MediaPeriodId mediaPeriodId;
        private final long mediaTimeOffsetMs;
        public final int windowIndex;

        public static final class ListenerAndHandler {
            public Handler handler;
            public MediaSourceEventListener listener;

            public ListenerAndHandler(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
                this.handler = handler;
                this.listener = mediaSourceEventListener;
            }
        }

        public EventDispatcher() {
            this(new CopyOnWriteArrayList(), 0, null, 0L);
        }

        private long adjustMediaTime(long j2) {
            long jUsToMs = Util.usToMs(j2);
            return jUsToMs == C.TIME_UNSET ? C.TIME_UNSET : this.mediaTimeOffsetMs + jUsToMs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$downstreamFormatChanged$5(MediaSourceEventListener mediaSourceEventListener, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onDownstreamFormatChanged(this.windowIndex, this.mediaPeriodId, mediaLoadData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadCanceled$2(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCanceled(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadCompleted$1(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadCompleted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadError$3(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2) {
            mediaSourceEventListener.onLoadError(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData, iOException, z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$loadStarted$0(MediaSourceEventListener mediaSourceEventListener, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onLoadStarted(this.windowIndex, this.mediaPeriodId, loadEventInfo, mediaLoadData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$upstreamDiscarded$4(MediaSourceEventListener mediaSourceEventListener, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            mediaSourceEventListener.onUpstreamDiscarded(this.windowIndex, mediaPeriodId, mediaLoadData);
        }

        public void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            Assertions.checkNotNull(handler);
            Assertions.checkNotNull(mediaSourceEventListener);
            this.listenerAndHandlers.add(new ListenerAndHandler(handler, mediaSourceEventListener));
        }

        public void downstreamFormatChanged(int i2, @Nullable Format format, int i3, @Nullable Object obj, long j2) {
            downstreamFormatChanged(new MediaLoadData(1, i2, format, i3, obj, adjustMediaTime(j2), C.TIME_UNSET));
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int i2) {
            loadCanceled(loadEventInfo, i2, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int i2) {
            loadCompleted(loadEventInfo, i2, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void loadError(LoadEventInfo loadEventInfo, int i2, IOException iOException, boolean z2) {
            loadError(loadEventInfo, i2, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET, iOException, z2);
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int i2) {
            loadStarted(loadEventInfo, i2, -1, null, 0, null, C.TIME_UNSET, C.TIME_UNSET);
        }

        public void removeEventListener(MediaSourceEventListener mediaSourceEventListener) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                if (next.listener == mediaSourceEventListener) {
                    this.listenerAndHandlers.remove(next);
                }
            }
        }

        public void upstreamDiscarded(int i2, long j2, long j3) {
            upstreamDiscarded(new MediaLoadData(1, i2, null, 3, null, adjustMediaTime(j2), adjustMediaTime(j3)));
        }

        @CheckResult
        public EventDispatcher withParameters(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j2) {
            return new EventDispatcher(this.listenerAndHandlers, i2, mediaPeriodId, j2);
        }

        private EventDispatcher(CopyOnWriteArrayList<ListenerAndHandler> copyOnWriteArrayList, int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long j2) {
            this.listenerAndHandlers = copyOnWriteArrayList;
            this.windowIndex = i2;
            this.mediaPeriodId = mediaPeriodId;
            this.mediaTimeOffsetMs = j2;
        }

        public void loadCanceled(LoadEventInfo loadEventInfo, int i2, int i3, @Nullable Format format, int i4, @Nullable Object obj, long j2, long j3) {
            loadCanceled(loadEventInfo, new MediaLoadData(i2, i3, format, i4, obj, adjustMediaTime(j2), adjustMediaTime(j3)));
        }

        public void loadCompleted(LoadEventInfo loadEventInfo, int i2, int i3, @Nullable Format format, int i4, @Nullable Object obj, long j2, long j3) {
            loadCompleted(loadEventInfo, new MediaLoadData(i2, i3, format, i4, obj, adjustMediaTime(j2), adjustMediaTime(j3)));
        }

        public void loadError(LoadEventInfo loadEventInfo, int i2, int i3, @Nullable Format format, int i4, @Nullable Object obj, long j2, long j3, IOException iOException, boolean z2) {
            loadError(loadEventInfo, new MediaLoadData(i2, i3, format, i4, obj, adjustMediaTime(j2), adjustMediaTime(j3)), iOException, z2);
        }

        public void loadStarted(LoadEventInfo loadEventInfo, int i2, int i3, @Nullable Format format, int i4, @Nullable Object obj, long j2, long j3) {
            loadStarted(loadEventInfo, new MediaLoadData(i2, i3, format, i4, obj, adjustMediaTime(j2), adjustMediaTime(j3)));
        }

        public void downstreamFormatChanged(final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.u
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6902c.lambda$downstreamFormatChanged$5(mediaSourceEventListener, mediaLoadData);
                    }
                });
            }
        }

        public void upstreamDiscarded(final MediaLoadData mediaLoadData) {
            final MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) Assertions.checkNotNull(this.mediaPeriodId);
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.y
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6919c.lambda$upstreamDiscarded$4(mediaSourceEventListener, mediaPeriodId, mediaLoadData);
                    }
                });
            }
        }

        public void loadCanceled(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.x
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6915c.lambda$loadCanceled$2(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        public void loadCompleted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.w
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6911c.lambda$loadCompleted$1(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }

        public void loadError(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData, final IOException iOException, final boolean z2) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.v
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6905c.lambda$loadError$3(mediaSourceEventListener, loadEventInfo, mediaLoadData, iOException, z2);
                    }
                });
            }
        }

        public void loadStarted(final LoadEventInfo loadEventInfo, final MediaLoadData mediaLoadData) {
            Iterator<ListenerAndHandler> it = this.listenerAndHandlers.iterator();
            while (it.hasNext()) {
                ListenerAndHandler next = it.next();
                final MediaSourceEventListener mediaSourceEventListener = next.listener;
                Util.postOrRun(next.handler, new Runnable() { // from class: com.google.android.exoplayer2.source.t
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6898c.lambda$loadStarted$0(mediaSourceEventListener, loadEventInfo, mediaLoadData);
                    }
                });
            }
        }
    }

    void onDownstreamFormatChanged(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData);

    void onLoadCanceled(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadCompleted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadError(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2);

    void onLoadStarted(int i2, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onUpstreamDiscarded(int i2, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData);
}
