package com.beizi.fusion.work.f;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.beizi.ad.NativeAdListener;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.UnifiedCustomAd;
import com.beizi.ad.internal.nativead.NativeAdEventListener;
import com.beizi.ad.internal.nativead.NativeAdShownListener;
import com.beizi.ad.internal.nativead.NativeAdUtil;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.ViewUtil;
import com.beizi.fusion.d.x;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class b extends a {

    /* renamed from: w, reason: collision with root package name */
    private UnifiedCustomAd f5694w;

    /* renamed from: x, reason: collision with root package name */
    private NativeAdResponse f5695x;

    public b(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5694w == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.f.a
    public void aL() {
        if (!as.a("com.beizi.ad.BeiZi")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.f.b.1
                @Override // java.lang.Runnable
                public void run() {
                    b.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "BeiZi sdk not import , will do nothing");
            return;
        }
        A();
        x.a(((a) this).f5684n, this.f5544h);
        B();
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((a) this).f5686p);
        long j2 = ((a) this).f5686p;
        if (j2 > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, j2);
            return;
        }
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
    }

    @Override // com.beizi.fusion.work.f.a
    public void aM() {
        ((a) this).f5690t = new FrameLayout(((a) this).f5684n);
        UnifiedCustomAd unifiedCustomAd = new UnifiedCustomAd(((a) this).f5684n, this.f5545i, new NativeAdListener() { // from class: com.beizi.fusion.work.f.b.2
            @Override // com.beizi.ad.NativeAdListener
            public void onAdClick() {
                Log.d("BeiZis", "showBeiZiNativeUnifiedAd onAdClick()");
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdFailed(int i2) {
                Log.d("BeiZis", "showBeiZiNativeUnifiedAd onAdFailed: " + i2);
                b.this.b(String.valueOf(i2), i2);
            }

            @Override // com.beizi.ad.NativeAdListener
            public void onAdLoaded(NativeAdResponse nativeAdResponse) {
                Log.d("BeiZis", "showBeiZiNativeUnifiedAd onAdLoaded()");
                b bVar = b.this;
                ((a) bVar).f5685o = com.beizi.fusion.f.a.ADLOAD;
                if (bVar.f5694w.getPrice() != null) {
                    try {
                        b bVar2 = b.this;
                        bVar2.a(Double.parseDouble(bVar2.f5694w.getPrice()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                b.this.E();
                if (nativeAdResponse == null) {
                    b.this.e(-991);
                } else {
                    b.this.f5695x = nativeAdResponse;
                    b.this.aY();
                }
            }
        });
        this.f5694w = unifiedCustomAd;
        unifiedCustomAd.openAdInNativeBrowser(true);
        this.f5694w.loadAd();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aN() {
        ArrayList<String> texts;
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse == null) {
            return null;
        }
        String headline = nativeAdResponse.getHeadline();
        return (!TextUtils.isEmpty(headline) || (texts = this.f5695x.getTexts()) == null || texts.size() < 1) ? headline : texts.get(0);
    }

    @Override // com.beizi.fusion.work.f.a
    public String aO() {
        ArrayList<String> texts;
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse == null || TextUtils.isEmpty(nativeAdResponse.getBody())) {
            return null;
        }
        String body = this.f5695x.getBody();
        return (!TextUtils.isEmpty(body) || (texts = this.f5695x.getTexts()) == null || texts.size() < 2) ? body : texts.get(1);
    }

    @Override // com.beizi.fusion.work.f.a
    public String aP() {
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse == null || TextUtils.isEmpty(nativeAdResponse.getIconUrl())) {
            return null;
        }
        return this.f5695x.getIconUrl();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aQ() {
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse == null || TextUtils.isEmpty(nativeAdResponse.getImageUrl())) {
            return null;
        }
        return this.f5695x.getImageUrl();
    }

    @Override // com.beizi.fusion.work.f.a
    public List<String> aR() {
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse == null) {
            return null;
        }
        if ((nativeAdResponse.getImageUrls() != null) && (this.f5695x.getImageUrls().size() > 0)) {
            return this.f5695x.getImageUrls();
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public int aS() {
        return 1;
    }

    @Override // com.beizi.fusion.work.f.a
    public String aT() {
        String callToAction;
        ArrayList<String> texts;
        NativeAdResponse nativeAdResponse = this.f5695x;
        if (nativeAdResponse != null) {
            callToAction = nativeAdResponse.getCallToAction();
            if (TextUtils.isEmpty(callToAction) && (texts = this.f5695x.getTexts()) != null && texts.size() >= 3) {
                callToAction = texts.get(2);
            }
        } else {
            callToAction = null;
        }
        return TextUtils.isEmpty(callToAction) ? "查看详情" : callToAction;
    }

    @Override // com.beizi.fusion.work.f.a
    public boolean aU() {
        return false;
    }

    @Override // com.beizi.fusion.work.f.a
    public ViewGroup aV() {
        return ((a) this).f5690t;
    }

    @Override // com.beizi.fusion.work.f.a
    public void aX() {
        ServerResponse.AdLogoInfo adUrl = this.f5695x.getAdUrl();
        ServerResponse.AdLogoInfo adLogoInfo = this.f5695x.getlogoUrl();
        FrameLayout frameLayoutCreateAdImageView = ViewUtil.createAdImageView(((a) this).f5684n, adUrl);
        FrameLayout frameLayoutCreateLogoImageView = ViewUtil.createLogoImageView(((a) this).f5684n, adLogoInfo);
        frameLayoutCreateAdImageView.setVisibility(0);
        frameLayoutCreateLogoImageView.setVisibility(0);
        FrameLayout frameLayout = new FrameLayout(((a) this).f5684n);
        frameLayout.addView(frameLayoutCreateAdImageView, new FrameLayout.LayoutParams(85, 42, 83));
        frameLayout.addView(frameLayoutCreateLogoImageView, new FrameLayout.LayoutParams(42, 42, 85));
        ((a) this).f5690t.addView(frameLayout, new FrameLayout.LayoutParams(-1, 42, 80));
    }

    @Override // com.beizi.fusion.work.f.a, com.beizi.fusion.work.a
    public String g() {
        return "BEIZI";
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        UnifiedCustomAd unifiedCustomAd = this.f5694w;
        if (unifiedCustomAd == null) {
            return null;
        }
        return unifiedCustomAd.getPrice();
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        UnifiedCustomAd unifiedCustomAd = this.f5694w;
        if (unifiedCustomAd != null) {
            unifiedCustomAd.cancel();
        }
    }

    @Override // com.beizi.fusion.work.f.a
    public void a(List<View> list) {
        if (((a) this).f5690t != null) {
            aX();
            if (list != null && list.size() > 0) {
                NativeAdUtil.registerTracking(this.f5695x, ((a) this).f5690t, list, new NativeAdEventListener() { // from class: com.beizi.fusion.work.f.b.3
                    @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                    public void onAdWasClicked() {
                        b.this.b();
                    }

                    @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                    public void onAdWillLeaveApplication() {
                        Log.d("BeiZis", "showBeiZiNativeUnifiedAd onAdWillLeaveApplication");
                    }
                });
            } else {
                NativeAdUtil.registerTracking(this.f5695x, ((a) this).f5690t, new NativeAdEventListener() { // from class: com.beizi.fusion.work.f.b.4
                    @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                    public void onAdWasClicked() {
                        b.this.b();
                    }

                    @Override // com.beizi.ad.internal.nativead.NativeAdEventListener
                    public void onAdWillLeaveApplication() {
                        Log.d("BeiZis", "showBeiZiNativeUnifiedAd onAdWillLeaveApplication");
                    }
                });
            }
            NativeAdUtil.registerShow(this.f5695x, ((a) this).f5690t, new NativeAdShownListener() { // from class: com.beizi.fusion.work.f.b.5
                @Override // com.beizi.ad.internal.nativead.NativeAdShownListener
                public void onAdShown() {
                    b.this.ag();
                }
            });
        }
    }
}
