package com.beizi.ad;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresPermission;
import com.beizi.ad.AdRequest;
import com.beizi.ad.internal.view.BannerAdViewImpl;

/* loaded from: classes2.dex */
public final class SplashAd implements AdLifeControl {

    /* renamed from: a, reason: collision with root package name */
    private final BannerAdViewImpl f3657a;

    @RequiresPermission("android.permission.INTERNET")
    public SplashAd(Context context, ViewGroup viewGroup, View view, AdListener adListener, String str) {
        viewGroup.setPadding(0, 0, 0, 0);
        BannerAdViewImpl bannerAdViewImpl = new BannerAdViewImpl(context, viewGroup, view);
        this.f3657a = bannerAdViewImpl;
        bannerAdViewImpl.setAdListener(adListener);
        bannerAdViewImpl.setAdUnitId(str);
        bannerAdViewImpl.loadAd(new AdRequest.Builder().build().impl());
    }

    public void adClick() {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.clickArea();
        }
    }

    @Override // com.beizi.ad.AdLifeControl
    public void cancel() {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.cancel();
        }
    }

    public void disableFullClick(View.OnTouchListener onTouchListener) {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl == null || onTouchListener == null) {
            return;
        }
        bannerAdViewImpl.disableFullClick(onTouchListener);
    }

    public String getAdId() {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl == null) {
            return null;
        }
        return bannerAdViewImpl.getAdId();
    }

    public AdListener getAdListener() {
        return this.f3657a.getAdListener();
    }

    public String getAdUnitId() {
        return this.f3657a.getAdUnitId();
    }

    public String getPrice() {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl == null) {
            return null;
        }
        return bannerAdViewImpl.getPrice();
    }

    public boolean isLoaded() {
        return this.f3657a.isLoaded();
    }

    public boolean isLoading() {
        return this.f3657a.isLoading();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onCreateLifeCycle() {
        this.f3657a.onCreateLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onDestoryLifeCycle() {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl != null) {
            bannerAdViewImpl.onDestoryLifeCycle();
        }
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onPauseLifeCycle() {
        this.f3657a.onPauseLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onRestartLifeCycle() {
        this.f3657a.onRestartLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onResumeLifeCycle() {
        this.f3657a.onResumeLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStartLifeCycle() {
        this.f3657a.onStartLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void onStopLifeCycle() {
        this.f3657a.onStopLifeCycle();
    }

    @Override // com.beizi.ad.AdLifeControl
    public void openAdInNativeBrowser(boolean z2) {
        this.f3657a.openAdInNativeBrowser(z2);
    }

    public void setAdUnitId(String str) {
        this.f3657a.setAdUnitId(str);
    }

    public void setCloseButtonPadding(int i2, int i3, int i4, int i5) {
        this.f3657a.setCloseButtonPadding(i2, i3, i4, i5);
    }

    public void setScrollClick(View.OnTouchListener onTouchListener) {
        BannerAdViewImpl bannerAdViewImpl = this.f3657a;
        if (bannerAdViewImpl == null || onTouchListener == null) {
            return;
        }
        bannerAdViewImpl.setScrollClick(onTouchListener);
    }

    public void showAd() {
        this.f3657a.showAd();
    }

    public void adClick(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i2) {
        if (this.f3657a != null) {
            com.beizi.ad.c.c cVar = new com.beizi.ad.c.c();
            if (!TextUtils.isEmpty(str)) {
                cVar.a(str);
            }
            if (!TextUtils.isEmpty(str2)) {
                cVar.b(str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                cVar.c(str3);
            }
            if (!TextUtils.isEmpty(str4)) {
                cVar.d(str4);
            }
            if (!TextUtils.isEmpty(str5)) {
                cVar.e(str5);
            }
            if (!TextUtils.isEmpty(str6)) {
                cVar.f(str6);
            }
            if (!TextUtils.isEmpty(str7)) {
                cVar.g(str7);
            }
            if (!TextUtils.isEmpty(str8)) {
                cVar.h(str8);
            }
            this.f3657a.clickArea(cVar, i2);
        }
    }
}
