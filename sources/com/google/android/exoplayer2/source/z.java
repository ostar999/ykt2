package com.google.android.exoplayer2.source;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import java.util.List;

/* loaded from: classes3.dex */
public final /* synthetic */ class z {
    @Deprecated
    public static MediaSource a(MediaSourceFactory mediaSourceFactory, Uri uri) {
        return mediaSourceFactory.createMediaSource(MediaItem.fromUri(uri));
    }

    @Deprecated
    public static MediaSourceFactory b(MediaSourceFactory mediaSourceFactory, @Nullable List list) {
        return mediaSourceFactory;
    }
}
