package com.beizi.ad;

import android.content.Context;

/* loaded from: classes2.dex */
public interface RewardedVideoAd {
    void destroy(Context context);

    String getPrice();

    RewardedVideoAdListener getRewardedVideoAdListener();

    boolean isLoaded();

    void loadAd(String str, AdRequest adRequest);

    void pause(Context context);

    void resume(Context context);

    void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener);

    void show();
}
