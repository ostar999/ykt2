package com.unity3d.splash;

import com.unity3d.splash.UnityAds;

/* loaded from: classes6.dex */
public interface IUnityAdsListener {
    void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String str);

    void onUnityAdsFinish(String str, UnityAds.FinishState finishState);

    void onUnityAdsReady(String str);

    void onUnityAdsStart(String str);
}
