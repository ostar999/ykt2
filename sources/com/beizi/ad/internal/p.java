package com.beizi.ad.internal;

import android.content.Context;
import com.beizi.ad.AdRequest;
import com.beizi.ad.RewardedVideoAd;
import com.beizi.ad.RewardedVideoAdListener;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;

/* loaded from: classes2.dex */
final class p implements RewardedVideoAd {

    /* renamed from: a, reason: collision with root package name */
    private final InterstitialAdViewImpl f4398a;

    public p(Context context) {
        this.f4398a = new InterstitialAdViewImpl(context, true, false);
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void destroy(Context context) {
        this.f4398a.destroy();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public String getPrice() {
        return this.f4398a.getPrice();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public RewardedVideoAdListener getRewardedVideoAdListener() {
        return this.f4398a.getRewaredVideoAdListener();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public boolean isLoaded() {
        return this.f4398a.isLoaded();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void loadAd(String str, AdRequest adRequest) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        this.f4398a.setAdUnitId(str);
        this.f4398a.loadAd(adRequest.impl());
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void pause(Context context) {
        this.f4398a.activityOnPause();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void resume(Context context) {
        this.f4398a.activityOnResume();
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        this.f4398a.setRewardedVideoAdListener(rewardedVideoAdListener);
    }

    @Override // com.beizi.ad.RewardedVideoAd
    public void show() {
        this.f4398a.show();
    }
}
