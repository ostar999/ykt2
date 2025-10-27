package com.plv.rtc;

import com.plv.rtc.b.b;
import com.plv.rtc.transcode.IPLVARTCTranscoding;

/* loaded from: classes5.dex */
public class PLVARTCFactory {
    public static IPLVARTCTranscoding newLiveTranscoding() {
        return new b();
    }
}
