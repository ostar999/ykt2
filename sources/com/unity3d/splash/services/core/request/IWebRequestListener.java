package com.unity3d.splash.services.core.request;

import java.util.Map;

/* loaded from: classes6.dex */
public interface IWebRequestListener {
    void onComplete(String str, String str2, int i2, Map map);

    void onFailed(String str, String str2);
}
