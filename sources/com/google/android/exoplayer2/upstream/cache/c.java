package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public final /* synthetic */ class c {
    public static long a(ContentMetadata contentMetadata) {
        return contentMetadata.get(ContentMetadata.KEY_CONTENT_LENGTH, -1L);
    }

    @Nullable
    public static Uri b(ContentMetadata contentMetadata) {
        String str = contentMetadata.get(ContentMetadata.KEY_REDIRECTED_URI, (String) null);
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }
}
