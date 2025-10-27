package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface RtcStatsCollectorCallback {
    @CalledByNative
    void onStatsDelivered(RtcStatsReport rtcStatsReport);
}
