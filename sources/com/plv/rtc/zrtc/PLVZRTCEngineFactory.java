package com.plv.rtc.zrtc;

import android.app.Application;
import com.plv.rtc.zrtc.a.a;
import com.plv.rtc.zrtc.enummeration.ZRTCScenario;
import com.plv.rtc.zrtc.listener.IPLVZRTCEventHandler;

/* loaded from: classes5.dex */
public class PLVZRTCEngineFactory {
    public static PLVZRTCEngine createEngine(long j2, String str, boolean z2, ZRTCScenario zRTCScenario, Application application, IPLVZRTCEventHandler iPLVZRTCEventHandler) {
        return new a();
    }

    public static void destroyEngine() {
    }
}
