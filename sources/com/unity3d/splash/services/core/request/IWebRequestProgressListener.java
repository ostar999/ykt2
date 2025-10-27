package com.unity3d.splash.services.core.request;

import java.util.Map;

/* loaded from: classes6.dex */
public interface IWebRequestProgressListener {
    void onRequestProgress(String str, long j2, long j3);

    void onRequestStart(String str, long j2, int i2, Map map);
}
