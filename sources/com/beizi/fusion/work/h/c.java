package com.beizi.fusion.work.h;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.beizi.fusion.R;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.i;
import com.beizi.fusion.model.AdSpacesBean;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes2.dex */
public class c extends a {
    private ViewGroup X;
    private ImageView Y;
    private TextView Z;
    private boolean aa;
    private TTAdNative ab;
    private TTFeedAd ac;

    public c(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    @Override // com.beizi.fusion.work.a
    public void aF() {
        B();
        e();
    }

    @Override // com.beizi.fusion.work.h.a
    public int aL() {
        return R.layout.csj_layout_unified_view;
    }

    @Override // com.beizi.fusion.work.h.a
    public void aM() {
        super.aM();
        this.X = (ViewGroup) ((a) this).f5777o.findViewById(R.id.ll_ad_source_container);
        this.Y = (ImageView) ((a) this).f5777o.findViewById(R.id.ad_source_logo_iv);
        this.Z = (TextView) ((a) this).f5777o.findViewById(R.id.ad_source_logo_tv);
    }

    @Override // com.beizi.fusion.work.h.a
    public void aN() {
        if (!as.a("com.bytedance.sdk.openadsdk.TTAdNative")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.h.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "CSJ sdk not import , will do nothing");
        } else {
            A();
            v.a(this, this.N, this.f5544h, this.f5541e.getDirectDownload());
            this.f5538b.t(TTAdSdk.getAdManager().getSDKVersion());
            aB();
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void aO() {
        if (aC()) {
            return;
        }
        this.ab = v.a().createAdNative(this.N);
        this.ab.loadFeedAd(new AdSlot.Builder().setCodeId(this.f5545i).setSupportDeepLink(true).setAdCount(1).setImageAcceptedSize(640, 320).build(), new TTAdNative.FeedAdListener() { // from class: com.beizi.fusion.work.h.c.2
            public void onError(int i2, String str) {
                Log.d("BeiZis", "showCsjUnifiedAd Callback --> onError:" + str);
                c.this.b(str, i2);
            }

            public void onFeedAdLoad(List<TTFeedAd> list) {
                Log.d("BeiZis", "showCsjUnifiedAd Callback --> onFeedAdLoad()");
                c cVar = c.this;
                cVar.P = com.beizi.fusion.f.a.ADLOAD;
                cVar.E();
                if (list == null || list.size() == 0) {
                    c.this.e(-991);
                    return;
                }
                c.this.ac = list.get(0);
                if (c.this.ac == null) {
                    c.this.e(-991);
                    return;
                }
                c cVar2 = c.this;
                cVar2.aa = cVar2.ac.getImageMode() == 5 || c.this.ac.getImageMode() == 15 || c.this.ac.getImageMode() == 166;
                c.this.aR();
            }
        });
    }

    @Override // com.beizi.fusion.work.h.a
    public void aT() {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void aV() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12, -1);
        layoutParams.addRule(11, -1);
        layoutParams.rightMargin = (((a) this).f5781s.getLayoutParams().width - ((a) this).f5782t.getLayoutParams().width) / 2;
        layoutParams.bottomMargin = ((a) this).f5781s.getLayoutParams().height - ((a) this).f5782t.getLayoutParams().height;
        this.X.setLayoutParams(layoutParams);
        if (this.ac.getAdLogo() != null) {
            this.Y.setImageBitmap(this.ac.getAdLogo());
        }
        if (TextUtils.isEmpty(this.ac.getSource())) {
            return;
        }
        this.Z.setText(this.ac.getSource());
    }

    @Override // com.beizi.fusion.work.h.a
    public void aW() {
        View adView;
        if (!this.aa) {
            TTImage videoCoverImage = this.ac.getVideoCoverImage() != null ? this.ac.getVideoCoverImage() : (this.ac.getImageList() == null || this.ac.getImageList().isEmpty()) ? null : (TTImage) this.ac.getImageList().get(0);
            if (videoCoverImage == null || !videoCoverImage.isValid()) {
                return;
            }
            i.a(this.N).a(videoCoverImage.getImageUrl(), new i.a() { // from class: com.beizi.fusion.work.h.c.4
                @Override // com.beizi.fusion.g.i.a
                public void a() {
                }

                @Override // com.beizi.fusion.g.i.a
                public void a(Bitmap bitmap) {
                    ((a) c.this).f5787y.setImageBitmap(bitmap);
                }
            });
            return;
        }
        this.ac.setVideoAdListener(new TTFeedAd.VideoAdListener() { // from class: com.beizi.fusion.work.h.c.3
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
                Log.d("BeiZis", "showCsjUnifiedVideo Callback --> onVideoError()");
                c.this.b("sdk custom error ".concat("onVideoError"), 99991);
            }

            public void onVideoLoad(TTFeedAd tTFeedAd) {
            }
        });
        if (((a) this).f5782t == null || (adView = this.ac.getAdView()) == null || adView.getParent() != null) {
            return;
        }
        ((a) this).f5782t.removeAllViews();
        ((a) this).f5782t.addView(adView);
    }

    @Override // com.beizi.fusion.work.h.a
    public String aX() {
        return this.ac.getTitle();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aY() {
        return this.ac.getDescription();
    }

    @Override // com.beizi.fusion.work.h.a
    public String aZ() {
        return this.ac.getIcon().getImageUrl();
    }

    @Override // com.beizi.fusion.work.h.a
    public String ba() {
        return this.ac.getButtonText();
    }

    @Override // com.beizi.fusion.work.h.a
    public void bc() {
        if (this.ab == null) {
            e(-991);
        } else {
            aS();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void e() {
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((a) this).H);
        long j2 = ((a) this).H;
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

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public String g() {
        return "CSJ";
    }

    @Override // com.beizi.fusion.work.h.a, com.beizi.fusion.work.a
    public void q() {
        TTFeedAd tTFeedAd = this.ac;
        if (tTFeedAd != null) {
            tTFeedAd.destroy();
        }
    }

    @Override // com.beizi.fusion.work.h.a
    public void c(boolean z2) {
        bd();
    }

    @Override // com.beizi.fusion.work.h.a
    public void a(List<View> list) {
        this.ac.registerViewForInteraction(((a) this).f5781s, (List) null, list, (List) null, (View) null, new TTNativeAd.AdInteractionListener() { // from class: com.beizi.fusion.work.h.c.5
            public void onAdClicked(View view, TTNativeAd tTNativeAd) {
                c.this.aP();
            }

            public void onAdCreativeClick(View view, TTNativeAd tTNativeAd) {
                c.this.aP();
            }

            public void onAdShow(TTNativeAd tTNativeAd) {
                c.this.aQ();
            }
        });
    }
}
