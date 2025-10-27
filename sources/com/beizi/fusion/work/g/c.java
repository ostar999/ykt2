package com.beizi.fusion.work.g;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.k;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ah;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.n;
import com.beizi.fusion.g.u;
import com.beizi.fusion.model.AdSpacesBean;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.comm.managers.status.SDKStatus;
import com.qq.e.comm.pi.IBidding;
import com.qq.e.comm.util.AdError;
import com.yikaobang.yixue.R2;
import java.util.Map;

/* loaded from: classes2.dex */
public class c extends com.beizi.fusion.work.a implements com.beizi.fusion.d.c {

    /* renamed from: n, reason: collision with root package name */
    private Context f5743n;

    /* renamed from: o, reason: collision with root package name */
    private String f5744o;

    /* renamed from: p, reason: collision with root package name */
    private long f5745p;

    /* renamed from: q, reason: collision with root package name */
    private long f5746q;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5747r;

    /* renamed from: s, reason: collision with root package name */
    private RewardVideoAD f5748s;

    /* renamed from: t, reason: collision with root package name */
    private String f5749t;

    /* renamed from: u, reason: collision with root package name */
    private String f5750u;

    public class a implements RewardVideoADListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f5752a;

        /* renamed from: b, reason: collision with root package name */
        boolean f5753b;

        private a() {
            this.f5752a = false;
            this.f5753b = false;
        }

        public void onADClick() {
            Log.d("BeiZis", "showGdtRewardVideo onADClick()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.d(c.this.g());
            }
            if (this.f5753b) {
                return;
            }
            this.f5753b = true;
            c.this.K();
            c.this.an();
        }

        public void onADClose() {
            Log.d("BeiZis", "showGdtRewardVideo onADClose()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.c(c.this.g());
            }
            c.this.M();
        }

        public void onADExpose() {
            Log.d("BeiZis", "showGdtRewardVideo onADExposure()");
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
            if (((com.beizi.fusion.work.a) c.this).f5540d != null && ((com.beizi.fusion.work.a) c.this).f5540d.q() != 2) {
                ((com.beizi.fusion.work.a) c.this).f5540d.b(c.this.g());
            }
            if (this.f5752a) {
                return;
            }
            this.f5752a = true;
            c.this.aG();
            c.this.J();
            c.this.am();
        }

        public void onADLoad() {
            Log.d("BeiZis", "showGdtRewardVideo onADLoad()");
            if (c.this.f5748s.getECPM() > 0) {
                c.this.a(r0.f5748s.getECPM());
            }
            if (u.f5253a) {
                c.this.f5748s.setDownloadConfirmListener(u.f5254b);
            }
            ((com.beizi.fusion.work.a) c.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
            c.this.E();
            if (c.this.ac()) {
                c.this.b();
            } else {
                c.this.S();
            }
        }

        public void onADShow() {
            Log.d("BeiZis", "showGdtRewardVideo onADShow()");
            c.this.I();
        }

        public void onError(AdError adError) {
            Log.d("BeiZis", "showGdtRewardVideo onError:" + adError.getErrorMsg());
            c.this.b(adError.getErrorMsg(), adError.getErrorCode());
        }

        public void onReward(Map<String, Object> map) {
            Log.d("BeiZis", "showGdtRewardVideo onReward()");
            if (map != null) {
                Log.i("BeiZis", "onReward transID = " + map.get(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID));
            }
            c.this.O();
            if (((com.beizi.fusion.work.a) c.this).f5540d != null) {
                ((com.beizi.fusion.work.a) c.this).f5540d.j();
            }
        }

        public void onVideoCached() {
            Log.d("BeiZis", "showGdtRewardVideo onVideoCached()");
        }

        public void onVideoComplete() {
            Log.d("BeiZis", "showGdtRewardVideo onVideoComplete()");
            if (((com.beizi.fusion.work.a) c.this).f5540d != null) {
                ((com.beizi.fusion.work.a) c.this).f5540d.k();
            }
        }
    }

    public c(Context context, String str, String str2, String str3, long j2, long j3, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f5743n = context;
        this.f5744o = str;
        this.f5745p = j2;
        this.f5746q = j3;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f5749t = str2;
        this.f5750u = str3;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f5748s == null) {
            return;
        }
        aq();
        int iA = ah.a(this.f5541e.getPriceDict(), this.f5748s.getECPMLevel());
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
        RewardVideoAD rewardVideoAD = this.f5748s;
        if (rewardVideoAD == null || rewardVideoAD.getECPM() <= 0 || this.f5747r) {
            return;
        }
        this.f5747r = true;
        ac.a("BeiZis", "channel == GDT竞价成功");
        ac.a("BeiZis", "channel == sendWinNoticeECPM" + this.f5748s.getECPM());
        RewardVideoAD rewardVideoAD2 = this.f5748s;
        k.a((IBidding) rewardVideoAD2, rewardVideoAD2.getECPM());
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "GDT";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.qq.e.comm.managers.GDTAdSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.g.c.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "GDT sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    k.a(this.f5743n, this.f5544h);
                    this.f5538b.s(SDKStatus.getIntegrationSDKVersion());
                    aB();
                    B();
                }
            }
        }
        u.f5253a = !n.a(this.f5541e.getDirectDownload());
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + this.f5746q);
        long j2 = this.f5746q;
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

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        RewardVideoAD rewardVideoAD = this.f5748s;
        if (rewardVideoAD == null || rewardVideoAD.getECPM() <= 0 || this.f5747r) {
            return;
        }
        this.f5747r = true;
        ac.a("BeiZis", "channel == GDT竞价失败:" + i2);
        k.b((IBidding) this.f5748s, i2 != 1 ? 10001 : 1);
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if ("S2S".equalsIgnoreCase(this.f5541e.getBidType())) {
            this.f5748s = new RewardVideoAD(this.f5743n, this.f5545i, new a(), false, aJ());
        } else {
            this.f5748s = new RewardVideoAD(this.f5743n, this.f5545i, new a(), false);
        }
        this.f5748s.setServerSideVerificationOptions(new ServerSideVerificationOptions.Builder().setCustomData(this.f5750u).setUserId(this.f5749t).build());
        this.f5748s.loadAD();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " RewardVideoWorkers:" + eVar.p().toString());
        ad();
        h hVar = this.f5543g;
        if (hVar == h.SUCCESS) {
            com.beizi.fusion.d.e eVar2 = this.f5540d;
            if (eVar2 != null) {
                eVar2.a(g(), (View) null);
                return;
            }
            return;
        }
        if (hVar == h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    @Override // com.beizi.fusion.work.a
    public void a(Activity activity) {
        RewardVideoAD rewardVideoAD = this.f5748s;
        if (rewardVideoAD != null) {
            boolean z2 = !rewardVideoAD.hasShown();
            boolean zIsValid = this.f5748s.isValid();
            if (z2 && zIsValid) {
                this.f5748s.showAD();
                return;
            }
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar == null || zIsValid) {
                return;
            }
            eVar.b(1011);
            return;
        }
        com.beizi.fusion.d.e eVar2 = this.f5540d;
        if (eVar2 != null) {
            eVar2.b(R2.drawable.ic_coupon_middle);
        }
    }
}
