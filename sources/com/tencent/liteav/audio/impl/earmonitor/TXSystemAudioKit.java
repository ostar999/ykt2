package com.tencent.liteav.audio.impl.earmonitor;

import android.content.Context;

/* loaded from: classes6.dex */
public interface TXSystemAudioKit {
    void initialize(Context context, a aVar);

    void setSystemEarMonitoringVolume(int i2);

    void startSystemEarMonitoring();

    void stopSystemEarMonitoring();

    void uninitialize();
}
