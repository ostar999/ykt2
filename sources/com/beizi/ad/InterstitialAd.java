package com.beizi.ad;

import android.content.Context;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;

/* loaded from: classes2.dex */
public final class InterstitialAd implements AdLifeControl {

    /* renamed from: a, reason: collision with root package name */
    private final InterstitialAdViewImpl f3640a;

    public InterstitialAd(Context context) {
        this.f3640a = new InterstitialAdViewImpl(context, false, false);
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        this.f3640a.cancel();
    }

    public AdListener getAdListener() {
        return this.f3640a.getAdListener();
    }

    public String getAdUnitId() {
        return this.f3640a.getAdUnitId();
    }

    public String getMediationAdapterClassName() {
        return this.f3640a.getMediationAdapterClassName();
    }

    public String getPrice() {
        return this.f3640a.getPrice();
    }

    public boolean isLoaded() {
        return this.f3640a.isLoaded();
    }

    public boolean isLoading() {
        return this.f3640a.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public void loadAd(AdRequest adRequest) {
        this.f3640a.loadAd(adRequest.impl());
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onCreateLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onDestoryLifeCycle() {
        this.f3640a.onDestoryLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onPauseLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onRestartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onResumeLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStartLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStopLifeCycle() {
    }

    @Override // com.beizi.ad.AdLifeControl
    public void openAdInNativeBrowser(boolean z2) {
        this.f3640a.setOpensNativeBrowser(z2);
    }

    public void setAdListener(AdListener adListener) {
        this.f3640a.setAdListener(adListener);
    }

    public void setAdUnitId(String str) {
        this.f3640a.setAdUnitId(str);
    }

    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        this.f3640a.setRewardedVideoAdListener(rewardedVideoAdListener);
    }

    public void show() {
        this.f3640a.show();
    }

    public InterstitialAd(Context context, boolean z2) {
        this.f3640a = new InterstitialAdViewImpl(context, false, z2);
    }
}
