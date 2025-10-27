package com.google.android.exoplayer2.source.rtsp;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
final class RtspPlayResponse {
    public final RtspSessionTiming sessionTiming;
    public final int status;
    public final ImmutableList<RtspTrackTiming> trackTimingList;

    public RtspPlayResponse(int i2, RtspSessionTiming rtspSessionTiming, List<RtspTrackTiming> list) {
        this.status = i2;
        this.sessionTiming = rtspSessionTiming;
        this.trackTimingList = ImmutableList.copyOf((Collection) list);
    }
}
