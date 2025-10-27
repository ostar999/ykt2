package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

/* loaded from: classes3.dex */
public interface CacheKeyFactory {
    public static final CacheKeyFactory DEFAULT = new CacheKeyFactory() { // from class: com.google.android.exoplayer2.upstream.cache.a
        @Override // com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
        public final String buildCacheKey(DataSpec dataSpec) {
            return b.a(dataSpec);
        }
    };

    String buildCacheKey(DataSpec dataSpec);
}
