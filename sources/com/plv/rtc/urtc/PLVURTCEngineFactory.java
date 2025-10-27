package com.plv.rtc.urtc;

import com.plv.rtc.urtc.a.a;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;

/* loaded from: classes5.dex */
public class PLVURTCEngineFactory {
    public static PLVURTCEngine createEngine(URtcSdkEventListener uRtcSdkEventListener) {
        return new a(uRtcSdkEventListener);
    }
}
