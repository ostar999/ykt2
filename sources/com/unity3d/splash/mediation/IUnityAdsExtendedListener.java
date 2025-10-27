package com.unity3d.splash.mediation;

import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds;

/* loaded from: classes6.dex */
public interface IUnityAdsExtendedListener extends IUnityAdsListener {
    void onUnityAdsClick(String str);

    void onUnityAdsPlacementStateChanged(String str, UnityAds.PlacementState placementState, UnityAds.PlacementState placementState2);
}
