package com.beizi.ad.internal.a;

import android.app.Activity;
import android.content.MutableContextWrapper;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.beizi.ad.AdActivity;
import com.beizi.ad.R;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.ad.internal.video.AdVideoView;
import com.beizi.ad.internal.view.AdWebView;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;
import com.beizi.ad.internal.view.e;

/* loaded from: classes2.dex */
public class b implements AdActivity.a {

    /* renamed from: a, reason: collision with root package name */
    private Activity f3997a;

    /* renamed from: b, reason: collision with root package name */
    private AdWebView f3998b;

    /* renamed from: c, reason: collision with root package name */
    private com.beizi.ad.internal.view.c f3999c;

    /* renamed from: d, reason: collision with root package name */
    private FrameLayout f4000d;

    /* renamed from: e, reason: collision with root package name */
    private long f4001e;

    /* renamed from: f, reason: collision with root package name */
    private InterstitialAdViewImpl f4002f;

    public b(Activity activity) {
        this.f3997a = activity;
    }

    private void h() {
        if (this.f3997a != null) {
            InterstitialAdViewImpl interstitialAdViewImpl = this.f4002f;
            if (interstitialAdViewImpl != null && interstitialAdViewImpl.getAdDispatcher() != null) {
                this.f4002f.getAdDispatcher().b();
            }
            this.f3997a.finish();
        }
    }

    @Override // com.beizi.ad.AdActivity.a
    public void a() {
        InterstitialAdViewImpl interstitialAdViewImpl = InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE;
        if (interstitialAdViewImpl == null || interstitialAdViewImpl.getAdQueue().peek() == null || !(InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.getAdQueue().peek().d() instanceof AdWebView)) {
            return;
        }
        this.f3997a.setTheme(R.style.BeiZiDialogStyle);
        AdWebView adWebView = (AdWebView) InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE.getAdQueue().peek().d();
        adWebView.getSettings().setLoadsImagesAutomatically(true);
        if (adWebView.isVideoFullScreen()) {
            this.f3997a.setTheme(R.style.BeiZiTheme_Transparent);
            this.f3997a.requestWindowFeature(1);
            this.f3997a.getWindow().setFlags(1024, 1024);
        }
        this.f4000d = new FrameLayout(this.f3997a);
        this.f4000d.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        this.f3997a.setContentView(this.f4000d);
        this.f4001e = this.f3997a.getIntent().getLongExtra(InterstitialAdViewImpl.INTENT_KEY_TIME, System.currentTimeMillis());
        a(InterstitialAdViewImpl.INTERSTITIALADVIEW_TO_USE);
    }

    @Override // com.beizi.ad.AdActivity.a
    public void b() {
        InterstitialAdViewImpl interstitialAdViewImpl = this.f4002f;
        if (interstitialAdViewImpl == null || interstitialAdViewImpl.getAdDispatcher() == null || this.f4002f.isRewardedVideo() || this.f3997a == null) {
            return;
        }
        this.f4002f.getAdDispatcher().b();
        this.f3997a.finish();
    }

    @Override // com.beizi.ad.AdActivity.a
    public void c() throws IllegalStateException {
        AdWebView adWebView = this.f3998b;
        if (adWebView != null) {
            ViewUtil.removeChildFromParent(adWebView);
            this.f3998b.destroy();
            AdVideoView adVideoView = this.f3998b.mAdVideoView;
            if (adVideoView != null) {
                adVideoView.destroy();
            }
        }
        InterstitialAdViewImpl interstitialAdViewImpl = this.f4002f;
        if (interstitialAdViewImpl != null) {
            interstitialAdViewImpl.setAdImplementation(null);
        }
        this.f3997a.finish();
    }

    @Override // com.beizi.ad.AdActivity.a
    public void d() {
    }

    @Override // com.beizi.ad.AdActivity.a
    public void e() {
        InterstitialAdViewImpl interstitialAdViewImpl = this.f4002f;
        if (interstitialAdViewImpl == null || !interstitialAdViewImpl.shouldDismissOnClick()) {
            return;
        }
        h();
    }

    @Override // com.beizi.ad.AdActivity.a
    public WebView f() {
        return this.f3998b;
    }

    public void g() {
        com.beizi.ad.internal.view.c realDisplayable;
        com.beizi.ad.internal.view.c cVar;
        AdWebView adWebView = this.f3998b;
        if (adWebView == null || (realDisplayable = adWebView.getRealDisplayable()) == (cVar = this.f3999c)) {
            return;
        }
        this.f4000d.removeView(cVar.getView());
        if (realDisplayable instanceof AdVideoView) {
            this.f4000d.addView(realDisplayable.getView(), new ViewGroup.LayoutParams(-1, -1));
        } else {
            this.f4000d.addView(realDisplayable.getView(), new ViewGroup.LayoutParams(-1, -1));
        }
        this.f3999c = realDisplayable;
        realDisplayable.visible();
    }

    private void a(InterstitialAdViewImpl interstitialAdViewImpl) {
        int i2;
        this.f4002f = interstitialAdViewImpl;
        if (interstitialAdViewImpl == null) {
            return;
        }
        interstitialAdViewImpl.setAdImplementation(this);
        this.f4000d.setBackgroundColor(this.f4002f.getBackgroundColor());
        this.f4000d.removeAllViews();
        if (this.f4002f.getParent() != null) {
            ((ViewGroup) this.f4002f.getParent()).removeAllViews();
        }
        e eVarPoll = this.f4002f.getAdQueue().poll();
        while (eVarPoll != null && (this.f4001e - eVarPoll.a() > InterstitialAdViewImpl.MAX_AGE || this.f4001e - eVarPoll.a() < 0)) {
            HaoboLog.w(HaoboLog.baseLogTag, HaoboLog.getString(R.string.too_old));
            eVarPoll = this.f4002f.getAdQueue().poll();
        }
        if (eVarPoll == null || !(eVarPoll.d() instanceof AdWebView)) {
            return;
        }
        AdWebView adWebView = (AdWebView) eVarPoll.d();
        this.f3998b = adWebView;
        if (adWebView.getContext() instanceof MutableContextWrapper) {
            ((MutableContextWrapper) this.f3998b.getContext()).setBaseContext(this.f3997a);
            AdVideoView adVideoView = this.f3998b.mAdVideoView;
            if (adVideoView != null) {
                ((MutableContextWrapper) adVideoView.getContext()).setBaseContext(this.f3997a);
            }
        }
        if ((this.f3998b.getCreativeWidth() != 1 || this.f3998b.getCreativeHeight() != 1) && this.f3997a.getResources().getConfiguration().orientation != 2) {
            try {
                i2 = this.f4002f.getAdParameters().b().getApplicationInfo().targetSdkVersion;
            } catch (Exception e2) {
                e2.printStackTrace();
                i2 = 0;
            }
            if (i2 > 26 && Build.VERSION.SDK_INT == 26) {
                Log.d("lance", "Only fullscreen activities can request orientation");
            } else {
                AdActivity.a(this.f3997a, this.f3998b.getOrientation());
            }
        }
        this.f3999c = this.f3998b.getRealDisplayable();
        AdWebView adWebView2 = this.f3998b;
        if (adWebView2.mAdVideoView != null) {
            this.f4000d.addView(adWebView2.getRealDisplayable().getView(), new FrameLayout.LayoutParams(-2, -2, 17));
        } else if (this.f3997a.getRequestedOrientation() == 0) {
            this.f4000d.addView(this.f3998b.getRealDisplayable().getView(), new FrameLayout.LayoutParams((int) (this.f3998b.getCreativeHeight() * g.a().f4185j), (int) (this.f3998b.getCreativeWidth() * g.a().f4186k), 17));
        } else if (this.f3997a.getRequestedOrientation() == 1) {
            this.f4000d.addView(this.f3998b.getRealDisplayable().getView(), new FrameLayout.LayoutParams((int) (this.f3998b.getCreativeWidth() * g.a().f4185j), (int) (this.f3998b.getCreativeHeight() * g.a().f4186k), 17));
        } else {
            this.f4000d.addView(this.f3998b.getRealDisplayable().getView(), new FrameLayout.LayoutParams((int) (this.f3998b.getCreativeWidth() * g.a().f4185j), (int) (this.f3998b.getCreativeHeight() * g.a().f4186k), 17));
        }
        this.f3999c.visible();
    }
}
