package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

/* loaded from: classes3.dex */
public final /* synthetic */ class b {
    static {
        CacheKeyFactory cacheKeyFactory = CacheKeyFactory.DEFAULT;
    }

    public static /* synthetic */ String a(DataSpec dataSpec) {
        String str = dataSpec.key;
        return str != null ? str : dataSpec.uri.toString();
    }
}
