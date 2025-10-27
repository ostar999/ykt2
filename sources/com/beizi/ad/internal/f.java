package com.beizi.ad.internal;

import android.app.Activity;
import android.util.Log;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.view.AdViewImpl;
import com.beizi.ad.internal.view.AdWebView;
import com.beizi.ad.internal.view.BannerAdViewImpl;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
class f extends n {

    /* renamed from: a, reason: collision with root package name */
    private final SoftReference<AdViewImpl> f4162a;

    /* renamed from: b, reason: collision with root package name */
    private com.beizi.ad.internal.b.b f4163b;

    /* renamed from: c, reason: collision with root package name */
    private com.beizi.ad.internal.network.a f4164c;

    public f(AdViewImpl adViewImpl) {
        this.f4162a = new SoftReference<>(adViewImpl);
    }

    @Override // com.beizi.ad.internal.e
    public d c() {
        AdViewImpl adViewImpl = this.f4162a.get();
        if (adViewImpl != null) {
            return adViewImpl.getAdParameters();
        }
        return null;
    }

    @Override // com.beizi.ad.internal.e
    public com.beizi.ad.b.a d() {
        a.C0055a c0055aF = f();
        if (c0055aF != null) {
            return c0055aF.e();
        }
        return null;
    }

    @Override // com.beizi.ad.internal.n
    public void e() {
        com.beizi.ad.internal.network.a aVar = this.f4164c;
        if (aVar != null) {
            aVar.cancel(true);
            this.f4164c = null;
        }
        a((LinkedList<com.beizi.ad.internal.b.a>) null);
        com.beizi.ad.internal.b.b bVar = this.f4163b;
        if (bVar != null) {
            bVar.a(true);
            this.f4163b = null;
        }
    }

    public a.C0055a f() {
        if (this.f4162a.get() != null) {
            return this.f4162a.get().getAdRequest();
        }
        return null;
    }

    @Override // com.beizi.ad.internal.e
    public void a() {
        if (f() == null) {
            HaoboLog.e(HaoboLog.baseLogTag, "Before execute request manager, you should set ad request!");
            return;
        }
        this.f4164c = new com.beizi.ad.internal.network.a(f());
        g();
        try {
            this.f4164c.a(this);
            this.f4164c.executeOnExecutor(com.beizi.ad.a.a.c.b().c(), new Void[0]);
            AdViewImpl adViewImpl = this.f4162a.get();
            if (adViewImpl != null) {
                adViewImpl.getAdDispatcher().e();
            }
        } catch (IllegalStateException e2) {
            Log.d("lance", "ignored:" + e2.getMessage());
        } catch (RejectedExecutionException e3) {
            HaoboLog.e(HaoboLog.baseLogTag, "Concurrent Thread Exception while firing new ad request: " + e3.getMessage());
        }
    }

    @Override // com.beizi.ad.internal.e
    public void a(int i2) {
        h();
        AdViewImpl adViewImpl = this.f4162a.get();
        if (adViewImpl != null) {
            adViewImpl.getAdDispatcher().a(i2);
        }
    }

    @Override // com.beizi.ad.internal.e
    public void a(final ServerResponse serverResponse) {
        final AdViewImpl adViewImpl = this.f4162a.get();
        if (adViewImpl != null) {
            adViewImpl.getMyHandler().post(new Runnable() { // from class: com.beizi.ad.internal.f.1
                @Override // java.lang.Runnable
                public void run() {
                    ServerResponse serverResponse2;
                    ServerResponse serverResponse3 = serverResponse;
                    boolean z2 = false;
                    boolean z3 = serverResponse3 != null && serverResponse3.containsAds();
                    if (f.this.b() != null && !f.this.b().isEmpty()) {
                        z2 = true;
                    }
                    Log.d("lance", z3 + "=====" + z2);
                    if (!z3 && !z2) {
                        HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_no_ads));
                        adViewImpl.getAdDispatcher().a(3);
                        return;
                    }
                    Log.d("lance", "getMediaType:" + adViewImpl.getMediaType());
                    l mediaType = adViewImpl.getMediaType();
                    l lVar = l.BANNER;
                    if (mediaType.equals(lVar)) {
                        ((BannerAdViewImpl) adViewImpl).resetContainerIfNeeded();
                    }
                    if (z3) {
                        f.this.a(serverResponse.getMediationAds());
                    }
                    if (f.this.b() == null || f.this.b().isEmpty()) {
                        if (serverResponse != null) {
                            Log.d("lance", "handleStandardAds");
                            f.this.a(adViewImpl, serverResponse);
                            return;
                        }
                        return;
                    }
                    com.beizi.ad.internal.b.a aVarI = f.this.i();
                    if (aVarI != null && (serverResponse2 = serverResponse) != null) {
                        aVarI.a(serverResponse2.getExtras());
                    }
                    if (adViewImpl.getMediaType().equals(l.SPLASH)) {
                        f.this.f4163b = com.beizi.ad.internal.b.h.a((Activity) adViewImpl.getContext(), f.this, aVarI, adViewImpl.getAdDispatcher(), adViewImpl.getSplashParent(), serverResponse);
                        return;
                    }
                    if (adViewImpl.getMediaType().equals(lVar)) {
                        f.this.f4163b = com.beizi.ad.internal.b.c.a((Activity) adViewImpl.getContext(), f.this, aVarI, adViewImpl.getAdDispatcher(), serverResponse);
                    } else if (adViewImpl.getMediaType().equals(l.INTERSTITIAL)) {
                        f.this.f4163b = com.beizi.ad.internal.b.f.a((Activity) adViewImpl.getContext(), f.this, aVarI, adViewImpl.getAdDispatcher(), serverResponse);
                    } else {
                        HaoboLog.e(HaoboLog.baseLogTag, "Request type can not be identified.");
                        adViewImpl.getAdDispatcher().a(1);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final AdViewImpl adViewImpl, final ServerResponse serverResponse) {
        try {
            adViewImpl.getAdParameters().a(false);
            final AdWebView adWebView = new AdWebView(adViewImpl);
            adWebView.loadAd(serverResponse);
            adViewImpl.createAdLogo(serverResponse.getAdUrl(), serverResponse.getLogoUrl());
            if (adViewImpl.getMediaType().equals(l.BANNER)) {
                adViewImpl.addBannerCloseBtn();
                BannerAdViewImpl bannerAdViewImpl = (BannerAdViewImpl) adViewImpl;
                if (bannerAdViewImpl.getExpandsToFitScreenWidth()) {
                    bannerAdViewImpl.expandToFitScreenWidth(serverResponse.getWidth(), serverResponse.getHeight(), adWebView);
                }
            }
            adViewImpl.serverResponse = serverResponse;
            a(new com.beizi.ad.internal.network.b() { // from class: com.beizi.ad.internal.f.2
                @Override // com.beizi.ad.internal.network.b
                public l a() {
                    return adViewImpl.getMediaType();
                }

                @Override // com.beizi.ad.internal.network.b
                public boolean b() {
                    return false;
                }

                @Override // com.beizi.ad.internal.network.b
                public com.beizi.ad.internal.view.c c() {
                    return (adViewImpl.getMediaType() == l.INTERSTITIAL || adViewImpl.getMediaType() == l.REWARDEDVIDEO) ? adWebView : adWebView.getRealDisplayable();
                }

                @Override // com.beizi.ad.internal.network.b
                public NativeAdResponse d() {
                    return null;
                }

                @Override // com.beizi.ad.internal.network.b
                public String e() {
                    return serverResponse.getAdExtInfo();
                }

                @Override // com.beizi.ad.internal.network.b
                public String f() {
                    return serverResponse.getPrice();
                }

                @Override // com.beizi.ad.internal.network.b
                public String g() {
                    return serverResponse.getAdId();
                }

                @Override // com.beizi.ad.internal.network.b
                public void h() {
                    adWebView.destroy();
                }
            });
        } catch (Exception e2) {
            Log.d("lance", "========Exception=========:" + e2);
            HaoboLog.e(HaoboLog.baseLogTag, "Exception initializing the view: " + e2.getMessage());
            a(0);
        }
    }

    public void a(com.beizi.ad.internal.network.b bVar) {
        h();
        if (this.f4163b != null) {
            this.f4163b = null;
        }
        AdViewImpl adViewImpl = this.f4162a.get();
        if (adViewImpl != null) {
            adViewImpl.getAdDispatcher().a(bVar);
        } else {
            bVar.h();
        }
    }
}
