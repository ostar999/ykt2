package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import java.util.List;

/* loaded from: classes3.dex */
public interface MediaSourceFactory {
    public static final MediaSourceFactory UNSUPPORTED = new MediaSourceFactory() { // from class: com.google.android.exoplayer2.source.MediaSourceFactory.1
        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public /* synthetic */ MediaSource createMediaSource(Uri uri) {
            return z.a(this, uri);
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public MediaSource createMediaSource(MediaItem mediaItem) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public int[] getSupportedTypes() {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public MediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public MediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public MediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        @Deprecated
        public MediaSourceFactory setDrmUserAgent(@Nullable String str) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public MediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            return this;
        }

        @Override // com.google.android.exoplayer2.source.MediaSourceFactory
        public /* synthetic */ MediaSourceFactory setStreamKeys(List list) {
            return z.b(this, list);
        }
    };

    @Deprecated
    MediaSource createMediaSource(Uri uri);

    MediaSource createMediaSource(MediaItem mediaItem);

    int[] getSupportedTypes();

    @Deprecated
    MediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory);

    @Deprecated
    MediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager);

    MediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider);

    @Deprecated
    MediaSourceFactory setDrmUserAgent(@Nullable String str);

    MediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy);

    @Deprecated
    MediaSourceFactory setStreamKeys(@Nullable List<StreamKey> list);
}
