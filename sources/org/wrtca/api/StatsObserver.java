package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface StatsObserver {
    @CalledByNative
    void onComplete(StatsReport[] statsReportArr);
}
