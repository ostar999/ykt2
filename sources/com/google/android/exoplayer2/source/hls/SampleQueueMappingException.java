package com.google.android.exoplayer2.source.hls;

import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class SampleQueueMappingException extends IOException {
    public SampleQueueMappingException(@Nullable String str) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 60);
        sb.append("Unable to bind a sample queue to TrackGroup with mime type ");
        sb.append(str);
        sb.append(StrPool.DOT);
        super(sb.toString());
    }
}
