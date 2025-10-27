package com.google.firebase.appindexing.builders;

/* loaded from: classes4.dex */
public class StopwatchLapBuilder extends IndexableBuilder<StopwatchLapBuilder> {
    public StopwatchLapBuilder() {
        super("StopwatchLap");
    }

    public StopwatchLapBuilder setAccumulatedTime(long j2) {
        return put("accumulatedTime", j2);
    }

    public StopwatchLapBuilder setElapsedTime(long j2) {
        return put("elapsedTime", j2);
    }
}
