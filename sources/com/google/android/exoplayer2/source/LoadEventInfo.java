package com.google.android.exoplayer2.source;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public final class LoadEventInfo {
    private static final AtomicLong idSource = new AtomicLong();
    public final long bytesLoaded;
    public final DataSpec dataSpec;
    public final long elapsedRealtimeMs;
    public final long loadDurationMs;
    public final long loadTaskId;
    public final Map<String, List<String>> responseHeaders;
    public final Uri uri;

    public LoadEventInfo(long j2, DataSpec dataSpec, long j3) {
        this(j2, dataSpec, dataSpec.uri, Collections.emptyMap(), j3, 0L, 0L);
    }

    public static long getNewId() {
        return idSource.getAndIncrement();
    }

    public LoadEventInfo(long j2, DataSpec dataSpec, Uri uri, Map<String, List<String>> map, long j3, long j4, long j5) {
        this.loadTaskId = j2;
        this.dataSpec = dataSpec;
        this.uri = uri;
        this.responseHeaders = map;
        this.elapsedRealtimeMs = j3;
        this.loadDurationMs = j4;
        this.bytesLoaded = j5;
    }
}
