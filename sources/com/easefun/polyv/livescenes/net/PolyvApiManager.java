package com.easefun.polyv.livescenes.net;

import com.easefun.polyv.livescenes.net.api.PolyvApichatApi;
import com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvApiManager {
    public static PolyvApichatApi getPolyvApichatApi() {
        return PolyvApichatApi.INSTANCE;
    }

    public static PolyvLiveStatusApi getPolyvLiveStatusApi() {
        return PolyvLiveStatusApi.INSTANCE;
    }
}
