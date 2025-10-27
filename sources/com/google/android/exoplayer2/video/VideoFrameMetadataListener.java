package com.google.android.exoplayer2.video;

import android.media.MediaFormat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;

/* loaded from: classes3.dex */
public interface VideoFrameMetadataListener {
    void onVideoFrameAboutToBeRendered(long j2, long j3, Format format, @Nullable MediaFormat mediaFormat);
}
