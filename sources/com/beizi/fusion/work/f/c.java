package com.beizi.fusion.work.f;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class c extends a {

    /* renamed from: w, reason: collision with root package name */
    private TTAdNative f5701w;

    /* renamed from: x, reason: collision with root package name */
    private TTFeedAd f5702x;

    public c(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aZ() {
        if (aU()) {
            this.f5702x.setVideoAdListener(new TTFeedAd.VideoAdListener() { // from class: com.beizi.fusion.work.f.c.3
                public void onProgressUpdate(long j2, long j3) {
                }

                public void onVideoAdComplete(TTFeedAd tTFeedAd) {
                }

                public void onVideoAdContinuePlay(TTFeedAd tTFeedAd) {
                }

                public void onVideoAdPaused(TTFeedAd tTFeedAd) {
                }

                public void onVideoAdStartPlay(TTFeedAd tTFeedAd) {
                }

                public void onVideoError(int i2, int i3) {
                    Log.d("BeiZis", "showCsjNativeUnifiedAd Callback --> onVideoError()");
                    c.this.b("sdk custom error ".concat("onVideoError"), 99991);
                }

                public void onVideoLoad(TTFeedAd tTFeedAd) {
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    @Override // com.beizi.fusion.work.f.a
    public void aL() {
        if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.f.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "CSJ sdk not import , will do nothing");
        } else {
            A();
            v.a(this, ((a) this).f5684n, this.f5544h, this.f5541e.getDirectDownload());
            this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
            aB();
        }
    }

    @Override // com.beizi.fusion.work.f.a
    public void aM() {
        if (aC()) {
            return;
        }
        ((a) this).f5690t = new FrameLayout(((a) this).f5684n);
        this.f5701w = v.a().createAdNative(((a) this).f5684n);
        this.f5701w.loadFeedAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setImageAcceptedSize(640, 320).build(), new TTAdNative.FeedAdListener() { // from class: com.beizi.fusion.work.f.c.2
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjNativeUnifiedAd Callback --> onError:" + str);
                c.this.b(str, i2);
            }

            public void onFeedAdLoad(List<TTFeedAd> list) {
                Log.d("BeiZis", "showCsjNativeUnifiedAd Callback --> onFeedAdLoad()");
                c cVar = c.this;
                ((a) cVar).f5685o = com.beizi.fusion.f.a.ADLOAD;
                cVar.E();
                if (list == null || list.size() == 0) {
                    c.this.e(-991);
                    return;
                }
                c.this.f5702x = list.get(0);
                if (c.this.f5702x == null) {
                    c.this.e(-991);
                } else {
                    c.this.aZ();
                    c.this.aY();
                }
            }
        });
    }

    @Override // com.beizi.fusion.work.f.a
    public String aN() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd == null || TextUtils.isEmpty(tTFeedAd.getTitle())) {
            return null;
        }
        return this.f5702x.getTitle();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aO() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd == null || TextUtils.isEmpty(tTFeedAd.getDescription())) {
            return null;
        }
        return this.f5702x.getDescription();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aP() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd == null || tTFeedAd.getIcon() == null || TextUtils.isEmpty(this.f5702x.getIcon().getImageUrl())) {
            return null;
        }
        return this.f5702x.getIcon().getImageUrl();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aQ() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd != null) {
            if ((tTFeedAd.getImageList() != null) & (this.f5702x.getImageList().size() > 0)) {
                List imageList = this.f5702x.getImageList();
                String imageUrl = ((TTImage) imageList.get(0)).getImageUrl();
                if (!TextUtils.isEmpty(imageUrl) && ((TTImage) imageList.get(0)).isValid()) {
                    return imageUrl;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public List<String> aR() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd != null) {
            if ((tTFeedAd.getImageList() != null) & (this.f5702x.getImageList().size() > 0)) {
                ArrayList arrayList = new ArrayList();
                List imageList = this.f5702x.getImageList();
                for (int i2 = 0; i2 < imageList.size(); i2++) {
                    String imageUrl = ((TTImage) imageList.get(i2)).getImageUrl();
                    if (!TextUtils.isEmpty(imageUrl) && ((TTImage) imageList.get(i2)).isValid()) {
                        arrayList.add(imageUrl);
                    }
                }
                if (arrayList.size() > 0) {
                    return arrayList;
                }
            }
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public int aS() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd == null) {
            return 0;
        }
        int imageMode = tTFeedAd.getImageMode();
        if (imageMode == 2 || imageMode == 3 || imageMode == 4) {
            return 1;
        }
        return (imageMode == 5 || imageMode == 15 || imageMode == 166) ? 2 : 0;
    }

    @Override // com.beizi.fusion.work.f.a
    public String aT() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd == null || TextUtils.isEmpty(tTFeedAd.getButtonText())) {
            return null;
        }
        return this.f5702x.getButtonText();
    }

    @Override // com.beizi.fusion.work.f.a
    public boolean aU() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd != null) {
            return tTFeedAd.getImageMode() == 5 || this.f5702x.getImageMode() == 15 || this.f5702x.getImageMode() == 166;
        }
        return false;
    }

    @Override // com.beizi.fusion.work.f.a
    public ViewGroup aV() {
        return ((a) this).f5690t;
    }

    @Override // com.beizi.fusion.work.f.a
    public View aW() {
        if (aU()) {
            return this.f5702x.getAdView();
        }
        return null;
    }

    @Override // com.beizi.fusion.work.f.a
    public void aX() {
        FrameLayout frameLayout = new FrameLayout(((a) this).f5684n);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        if (this.f5702x.getAdLogo() != null) {
            ImageView imageView = new ImageView(((a) this).f5684n);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageBitmap(this.f5702x.getAdLogo());
            frameLayout.addView(imageView);
        }
        ((a) this).f5690t.addView(frameLayout, new FrameLayout.LayoutParams(-2, -2, 85));
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
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

    @Override // com.beizi.fusion.work.f.a, com.beizi.fusion.work.a
    public String g() {
        return "CSJ";
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        TTFeedAd tTFeedAd = this.f5702x;
        if (tTFeedAd != null) {
            tTFeedAd.destroy();
        }
    }

    @Override // com.beizi.fusion.work.f.a
    public void a(List<View> list) {
        aX();
        this.f5702x.registerViewForInteraction(((a) this).f5690t, (List) null, list, (List) null, (View) null, new TTNativeAd.AdInteractionListener() { // from class: com.beizi.fusion.work.f.c.4
            public void onAdClicked(View view, TTNativeAd tTNativeAd) {
                c.this.b();
            }

            public void onAdCreativeClick(View view, TTNativeAd tTNativeAd) {
                c.this.b();
            }

            public void onAdShow(TTNativeAd tTNativeAd) {
                c.this.ag();
            }
        });
    }
}
