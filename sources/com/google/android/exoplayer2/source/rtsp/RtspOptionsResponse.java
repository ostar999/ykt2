package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
final class RtspOptionsResponse {
    public final int status;
    public final ImmutableList<Integer> supportedMethods;

    public RtspOptionsResponse(int i2, List<Integer> list) {
        this.status = i2;
        this.supportedMethods = ImmutableList.copyOf((Collection) list);
    }
}
