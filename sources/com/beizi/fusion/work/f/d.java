package com.beizi.fusion.work.f;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.internal.view.SupportMenu;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeADEventListener;
import com.qq.e.ads.nativ.NativeADMediaListener;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends com.beizi.fusion.work.f.a {
    private boolean A;

    /* renamed from: w, reason: collision with root package name */
    private NativeUnifiedAD f5707w;

    /* renamed from: x, reason: collision with root package name */
    private NativeUnifiedADData f5708x;

    /* renamed from: y, reason: collision with root package name */
    private NativeADMediaListener f5709y;

    /* renamed from: z, reason: collision with root package name */
    private MediaView f5710z;

    public class a implements NativeADUnifiedListener {
        private a() {
        }

        public void onADLoaded(List<NativeUnifiedADData> list) {
            Log.d("BeiZis", "ShowGdtNativeUnifiedAd onADLoaded()");
            d dVar = d.this;
            ((com.beizi.fusion.work.f.a) dVar).f5685o = com.beizi.fusion.f.a.ADLOAD;
            dVar.E();
            if (list == null || list.size() == 0) {
                d.this.e(-991);
                return;
            }
            d.this.f5708x = list.get(0);
            if (d.this.f5708x == null) {
                d.this.e(-991);
                return;
            }
            if (d.this.f5708x.getECPM() > 0) {
                d.this.a(r4.f5708x.getECPM());
            }
            if (u.f5253a) {
                d.this.f5708x.setDownloadConfirmListener(u.f5254b);
            }
            d.this.aZ();
            d.this.aY();
        }

        public void onNoAD(AdError adError) {
            Log.d("BeiZis", "ShowGdtNativeUnifiedAd onNoAD: " + adError.getErrorMsg());
            d.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }
    }

    public d(Context context, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar, int i2) {
        super(context, j2, buyerBean, forwardBean, eVar, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aZ() {
        this.f5709y = new NativeADMediaListener() { // from class: com.beizi.fusion.work.f.d.2
            public void onVideoClicked() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoClicked()");
            }

            public void onVideoCompleted() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoCompleted()");
            }

            public void onVideoError(AdError adError) {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoError: " + adError.getErrorMsg());
                d.this.b(adError.getErrorMsg(), adError.getErrorCode());
            }

            public void onVideoInit() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoInit()");
            }

            public void onVideoLoaded(int i2) {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoLoaded()");
            }

            public void onVideoLoading() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoLoading()");
            }

            public void onVideoPause() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoPause()");
            }

            public void onVideoReady() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoReady()");
            }

            public void onVideoResume() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoResume()");
            }

            public void onVideoStart() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoStart()");
            }

            public void onVideoStop() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd MediaView onVideoStop()");
            }
        };
        this.f5708x.setNativeAdEventListener(new NativeADEventListener() { // from class: com.beizi.fusion.work.f.d.3
            public void onADClicked() {
                d.this.b();
            }

            public void onADError(AdError adError) {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd onADError: " + adError.getErrorMsg());
                d.this.b(adError.getErrorMsg(), adError.getErrorCode());
            }

            public void onADExposed() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd onADExposed()");
                d.this.ag();
                VideoOption.Builder builder = new VideoOption.Builder();
                builder.setAutoPlayPolicy(1);
                VideoOption videoOptionBuild = builder.build();
                if (d.this.aU()) {
                    d.this.f5708x.bindMediaView(d.this.f5710z, videoOptionBuild, d.this.f5709y);
                }
            }

            public void onADStatusChanged() {
                Log.d("BeiZis", "ShowGdtNativeUnifiedAd onADStatusChanged()");
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5708x == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5708x.getECPMLevel());
        if (iA == -1 || iA == -2) {
            if (iA == -2) {
                Q();
            }
        } else {
            Log.d("BeiZisBid", "gdt realPrice = " + iA);
            a((double) iA);
        }
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.A) {
            return;
        }
        this.A = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5708x.getECPM());
        NativeUnifiedADData nativeUnifiedADData2 = this.f5708x;
        k.a((IBidding) nativeUnifiedADData2, nativeUnifiedADData2.getECPM());
    }

    @Override // com.beizi.fusion.work.f.a
    public void aL() {
        if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
            z();
            this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.f.d.1
                @Override // java.lang.Runnable
                public void run() {
                    d.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                }
            }, 10L);
            Log.e("BeiZis", "GDT sdk not import , will do nothing");
            return;
        }
        A();
        k.a(((com.beizi.fusion.work.f.a) this).f5684n, this.f5544h);
        this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
        aB();
        B();
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + ((com.beizi.fusion.work.f.a) this).f5686p);
        long j2 = ((com.beizi.fusion.work.f.a) this).f5686p;
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
        ((com.beizi.fusion.work.f.a) this).f5690t = new NativeAdContainer(((com.beizi.fusion.work.f.a) this).f5684n);
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5707w = new NativeUnifiedAD(((com.beizi.fusion.work.f.a) this).f5684n, this.f5545i, new a(), aJ());
        } else {
            this.f5707w = new NativeUnifiedAD(((com.beizi.fusion.work.f.a) this).f5684n, this.f5545i, new a());
        }
        this.f5707w.loadData(1);
    }

    @Override // com.beizi.fusion.work.f.a
    public String aN() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || TextUtils.isEmpty(nativeUnifiedADData.getTitle())) {
            return null;
        }
        return this.f5708x.getTitle();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aO() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || TextUtils.isEmpty(nativeUnifiedADData.getDesc())) {
            return null;
        }
        return this.f5708x.getDesc();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aP() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || TextUtils.isEmpty(nativeUnifiedADData.getIconUrl())) {
            return null;
        }
        return this.f5708x.getIconUrl();
    }

    @Override // com.beizi.fusion.work.f.a
    public String aQ() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null) {
            return null;
        }
        String imgUrl = nativeUnifiedADData.getImgUrl();
        if (!TextUtils.isEmpty(imgUrl)) {
            return imgUrl;
        }
        List imgList = this.f5708x.getImgList();
        return !TextUtils.isEmpty((CharSequence) imgList.get(0)) ? (String) imgList.get(0) : imgUrl;
    }

    @Override // com.beizi.fusion.work.f.a
    public List<String> aR() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData != null) {
            if ((nativeUnifiedADData.getImgList() != null) & (this.f5708x.getImgList().size() > 0)) {
                ArrayList arrayList = new ArrayList();
                List imgList = this.f5708x.getImgList();
                for (int i2 = 0; i2 < imgList.size(); i2++) {
                    String str = (String) imgList.get(i2);
                    if (!TextUtils.isEmpty(str)) {
                        arrayList.add(str);
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
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null) {
            return 0;
        }
        int adPatternType = nativeUnifiedADData.getAdPatternType();
        if (adPatternType != 1) {
            if (adPatternType == 2) {
                return 2;
            }
            if (adPatternType != 3 && adPatternType != 4) {
                return 0;
            }
        }
        return 1;
    }

    @Override // com.beizi.fusion.work.f.a
    public String aT() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || TextUtils.isEmpty(nativeUnifiedADData.getButtonText())) {
            return null;
        }
        return this.f5708x.getButtonText();
    }

    @Override // com.beizi.fusion.work.f.a
    public boolean aU() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        return nativeUnifiedADData != null && nativeUnifiedADData.getAdPatternType() == 2;
    }

    @Override // com.beizi.fusion.work.f.a
    public ViewGroup aV() {
        return ((com.beizi.fusion.work.f.a) this).f5690t;
    }

    @Override // com.beizi.fusion.work.f.a
    public View aW() {
        if (!aU()) {
            return null;
        }
        MediaView mediaView = new MediaView(((com.beizi.fusion.work.f.a) this).f5684n);
        this.f5710z = mediaView;
        mediaView.setBackgroundColor(SupportMenu.CATEGORY_MASK);
        return this.f5710z;
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null || nativeUnifiedADData.getECPM() <= 0 || this.A) {
            return;
        }
        this.A = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5708x, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.f.a, com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData == null) {
            return null;
        }
        int iA = ah.a(this.f5541e.getPriceDict(), nativeUnifiedADData.getECPMLevel());
        if (iA == -1 || iA == -2) {
            return null;
        }
        return iA + "";
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        NativeUnifiedADData nativeUnifiedADData = this.f5708x;
        if (nativeUnifiedADData != null) {
            nativeUnifiedADData.destroy();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void r() {
        if (this.f5708x == null || !aU()) {
            return;
        }
        this.f5708x.resumeVideo();
    }

    @Override // com.beizi.fusion.work.f.a
    public void a(List<View> list) {
        this.f5708x.bindAdToView(((com.beizi.fusion.work.f.a) this).f5684n, ((com.beizi.fusion.work.f.a) this).f5690t, (FrameLayout.LayoutParams) null, list);
    }
}
