package com.beizi.fusion.work.splash;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.b;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.mbridge.msdk.mbbid.out.BidListennning;
import com.mbridge.msdk.mbbid.out.BidLossCode;
import com.mbridge.msdk.mbbid.out.BidManager;
import com.mbridge.msdk.mbbid.out.BidResponsed;
import com.mbridge.msdk.mbbid.out.SplashBidRequestParams;
import com.mbridge.msdk.out.MBSplashHandler;
import com.mbridge.msdk.out.MBSplashLoadListener;
import com.mbridge.msdk.out.MBSplashShowListener;
import com.mbridge.msdk.out.MBridgeIds;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class l extends com.beizi.fusion.work.a {
    private String A;
    private boolean B = false;
    private boolean C = false;
    private boolean D = false;
    private boolean E = false;

    /* renamed from: n, reason: collision with root package name */
    private BidResponsed f6266n;

    /* renamed from: o, reason: collision with root package name */
    private long f6267o;

    /* renamed from: p, reason: collision with root package name */
    private Context f6268p;

    /* renamed from: q, reason: collision with root package name */
    private String f6269q;

    /* renamed from: r, reason: collision with root package name */
    private long f6270r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f6271s;

    /* renamed from: t, reason: collision with root package name */
    private long f6272t;

    /* renamed from: u, reason: collision with root package name */
    private MBSplashHandler f6273u;

    /* renamed from: v, reason: collision with root package name */
    private BidManager f6274v;

    /* renamed from: w, reason: collision with root package name */
    private String f6275w;

    /* renamed from: x, reason: collision with root package name */
    private String f6276x;

    /* renamed from: y, reason: collision with root package name */
    private String f6277y;

    /* renamed from: z, reason: collision with root package name */
    private String f6278z;

    public l(Context context, String str, long j2, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, com.beizi.fusion.d.e eVar) {
        this.f6268p = context;
        this.f6269q = str;
        this.f6270r = j2;
        this.f6271s = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    private void aM() {
        if (this.D) {
            this.f5549m.sendEmptyMessageDelayed(1, this.f6272t);
            return;
        }
        A();
        com.beizi.fusion.b.a().a(this.f6268p, this.A, this.f6278z, false, null, new b.c() { // from class: com.beizi.fusion.work.splash.l.2
            @Override // com.beizi.fusion.b.c
            public void a(String str, String str2) {
                ac.b("BeiZis", "MTG onInitSuccess");
                l.this.B();
                if (l.this.f6268p instanceof Activity) {
                    l lVar = l.this;
                    lVar.b((Activity) lVar.f6268p);
                    if (l.this.at()) {
                        l lVar2 = l.this;
                        lVar2.a(lVar2.f6276x, l.this.f6277y);
                    }
                }
                if (l.this.f6272t > 0) {
                    ((com.beizi.fusion.work.a) l.this).f5549m.sendEmptyMessageDelayed(1, l.this.f6272t);
                } else {
                    if (((com.beizi.fusion.work.a) l.this).f5540d == null || ((com.beizi.fusion.work.a) l.this).f5540d.r() >= 1 || ((com.beizi.fusion.work.a) l.this).f5540d.q() == 2) {
                        return;
                    }
                    l.this.p();
                }
            }

            @Override // com.beizi.fusion.b.c
            public void a(String str) {
                ac.b("BeiZis", "MTG onInitFail");
                if (l.this.ae()) {
                    l.this.c(3);
                    l.this.Q();
                } else {
                    l.this.aE();
                }
            }
        });
        this.f5538b.z("MAL_16.2.57");
        aB();
        this.D = true;
    }

    private void aN() {
        BidManager bidManager = this.f6274v;
        if (bidManager != null) {
            bidManager.setBidListener(new BidListennning() { // from class: com.beizi.fusion.work.splash.l.5
                public void onFailed(String str) {
                    Log.d("BeiZis", "bid onFailed showMtgSplash onError:" + str);
                    l.this.c(3);
                    l.this.Q();
                }

                public void onSuccessed(BidResponsed bidResponsed) {
                    l.this.f6266n = bidResponsed;
                    l.this.f6275w = bidResponsed.getBidToken();
                    StringBuilder sb = new StringBuilder();
                    sb.append("onSuccessed: token ");
                    sb.append(l.this.f6275w);
                    sb.append(",mbSplashHandler != null ? ");
                    sb.append(l.this.f6273u != null);
                    ac.b("BeiZis", sb.toString());
                    l.this.aR();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aO() {
        com.beizi.fusion.d.e eVar = this.f5540d;
        if (eVar == null) {
            return;
        }
        Log.d("BeiZis", g() + " splashWorkers:" + eVar.p().toString());
        ad();
        com.beizi.fusion.d.h hVar = this.f5543g;
        if (hVar == com.beizi.fusion.d.h.SUCCESS) {
            ai();
            return;
        }
        if (hVar == com.beizi.fusion.d.h.FAIL) {
            Log.d("BeiZis", "other worker shown," + g() + " remove");
        }
    }

    private void aP() {
        ac.b("BeiZis", "enter finalShowAd");
        if (this.f6273u != null) {
            ac.b("BeiZis", "finalShowAd isAdReady = " + this.f6273u.isReady(this.f6275w));
        }
        if (this.f6273u == null || !aQ()) {
            aD();
            return;
        }
        ViewGroup viewGroup = this.f6271s;
        if (viewGroup == null) {
            aD();
            return;
        }
        viewGroup.removeAllViews();
        if (at()) {
            this.f6273u.show(this.f6271s, this.f6275w);
        } else {
            this.f6273u.show(this.f6271s);
        }
    }

    private boolean aQ() {
        String str;
        if (this.f6273u == null) {
            return false;
        }
        return (!at() || (str = this.f6275w) == null) ? this.f6273u.isReady() : this.f6273u.isReady(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aR() {
        if (!F() || this.f6266n == null) {
            return;
        }
        c(2);
        if (this.f6266n.getPrice() != null) {
            try {
                if ("0".compareTo(this.f6266n.getPrice()) < 0) {
                    double d3 = ("USD".equalsIgnoreCase(this.f6266n.getCur()) ? Double.parseDouble(this.f6266n.getPrice()) * 6.400000095367432d : Double.parseDouble(this.f6266n.getPrice())) * 100.0d;
                    ac.a("BeiZisBid", "mtg splash price = " + d3 + ",currency = " + this.f6266n.getCur());
                    a(d3);
                }
                aI();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
        P();
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        if (this.C) {
            return;
        }
        ac.b("BeiZis", "enter sendWinNotice");
        BidResponsed bidResponsed = this.f6266n;
        if (bidResponsed != null) {
            bidResponsed.sendWinNotice(this.f6268p);
            this.C = true;
        }
        super.aG();
    }

    public void aL() {
        MBSplashHandler mBSplashHandler = this.f6273u;
        if (mBSplashHandler == null || this.E) {
            return;
        }
        mBSplashHandler.setSplashLoadListener(new MBSplashLoadListener() { // from class: com.beizi.fusion.work.splash.l.3
            public void isSupportZoomOut(MBridgeIds mBridgeIds, boolean z2) {
                ac.b("BeiZis", "isSupportZoomOut: " + z2 + " ids" + mBridgeIds.toString());
            }

            public void onLoadFailed(MBridgeIds mBridgeIds, String str, int i2) {
                Log.d("BeiZis", "onLoadFailed showMtgSplash onError:" + str);
                l.this.b(str, R2.drawable.ic_combineq_question_bg_empty_day);
            }

            public void onLoadSuccessed(MBridgeIds mBridgeIds, int i2) {
                Log.d("BeiZis", "showMtgSplash onSplashAdLoad()");
                ac.a("BeiZis", "showMtgSplash req to load time = " + (System.currentTimeMillis() - l.this.f6267o));
                ((com.beizi.fusion.work.a) l.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                l.this.aq();
                l.this.E();
                if (l.this.ac()) {
                    l.this.aO();
                } else {
                    l.this.S();
                }
            }
        });
        this.f6273u.setSplashShowListener(new MBSplashShowListener() { // from class: com.beizi.fusion.work.splash.l.4
            public void onAdClicked(MBridgeIds mBridgeIds) {
                Log.d("BeiZis", "showMtgSplash onAdClick()");
                l.this.K();
                if (((com.beizi.fusion.work.a) l.this).f5540d != null) {
                    if (((com.beizi.fusion.work.a) l.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) l.this).f5540d.d(l.this.g());
                    }
                    l.this.an();
                }
            }

            public void onAdTick(MBridgeIds mBridgeIds, long j2) {
                ac.b("BeiZis", "onAdTick: " + j2 + " " + mBridgeIds.toString());
            }

            public void onDismiss(MBridgeIds mBridgeIds, int i2) {
                Log.d("BeiZis", "showMtgSplash onAdTimeOver()");
                if (((com.beizi.fusion.work.a) l.this).f5540d != null && ((com.beizi.fusion.work.a) l.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) l.this).f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.l.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            l.this.ah();
                        }
                    }, 200L);
                }
                l.this.M();
            }

            public void onShowFailed(MBridgeIds mBridgeIds, String str) {
                Log.d("BeiZis", "onShowFailed showMtgSplash onError:" + str);
                l.this.b(str, R2.drawable.ic_combineq_question_bg_empty_day);
            }

            public void onShowSuccessed(MBridgeIds mBridgeIds) {
                Log.d("BeiZis", "showMtgSplash onAdShow()");
                ((com.beizi.fusion.work.a) l.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                l.this.ag();
                l.this.I();
                l.this.J();
                l.this.am();
            }

            public void onZoomOutPlayFinish(MBridgeIds mBridgeIds) {
                ac.b("BeiZis", "onZoomOutPlayFinish: " + mBridgeIds.toString());
            }

            public void onZoomOutPlayStart(MBridgeIds mBridgeIds) {
                ac.b("BeiZis", "onZoomOutPlayStart: " + mBridgeIds.toString());
            }
        });
        this.E = true;
    }

    @Override // com.beizi.fusion.work.a
    public boolean ae() {
        return true;
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "MTG";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        this.f6267o = System.currentTimeMillis();
        try {
            this.f6276x = this.f5545i.split(StrPool.UNDERLINE)[0];
            this.f6277y = this.f5545i.split(StrPool.UNDERLINE)[1];
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.f6278z = this.f5544h.split(StrPool.UNDERLINE)[0];
            this.A = this.f5544h.split(StrPool.UNDERLINE)[1];
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        ac.b("BeiZis", "mtg placementId = " + this.f6276x + ",adUnitId = " + this.f6277y + ",mtgAppId = " + this.f6278z + ",mtgAppKey = " + this.A);
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.mbridge.msdk.MBridgeSDK")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.l.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!l.this.ae()) {
                                l.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                            } else {
                                l.this.c(3);
                                l.this.Q();
                            }
                        }
                    }, 10L);
                    Log.e("BeiZis", "MTG sdk not import , will do nothing");
                    return;
                }
                aM();
            }
        }
        this.f6272t = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            this.f6272t = Math.max(this.f6272t, this.f5542f.getHotRequestDelay());
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aP();
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        BidResponsed bidResponsed = this.f6266n;
        if (bidResponsed == null || "0".compareTo(bidResponsed.getPrice()) >= 0) {
            return null;
        }
        return (("USD".equalsIgnoreCase(this.f6266n.getCur()) ? Double.parseDouble(this.f6266n.getPrice()) * 6.400000095367432d : Double.parseDouble(this.f6266n.getPrice())) * 100.0d) + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        if (!at()) {
            C();
            al();
            aL();
            this.f6273u.preLoad();
            return;
        }
        if (this.f6266n == null) {
            ac.b("BeiZis", "mtg bid first step");
            aN();
            b();
            return;
        }
        aL();
        ac.b("BeiZis", "mtg bid second step mbSplashHandler = " + this.f6273u + ",token = " + this.f6275w);
        aG();
        if (this.f6273u != null) {
            C();
            al();
            this.f6273u.preLoadByToken(this.f6275w);
        }
    }

    public void b(Activity activity) {
        try {
            MBSplashHandler mBSplashHandler = new MBSplashHandler(activity, this.f6276x, this.f6277y);
            this.f6273u = mBSplashHandler;
            mBSplashHandler.setLoadTimeOut(this.f6270r);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        BidResponsed bidResponsed;
        if (this.B) {
            return;
        }
        ac.b("BeiZis", "enter sendLoseNotice state = " + i2);
        if (i2 == 1) {
            BidResponsed bidResponsed2 = this.f6266n;
            if (bidResponsed2 != null) {
                bidResponsed2.sendLossNotice(this.f6268p, BidLossCode.bidPriceNotHighest());
                this.B = true;
            }
        } else if (i2 == 2) {
            BidResponsed bidResponsed3 = this.f6266n;
            if (bidResponsed3 != null) {
                bidResponsed3.sendLossNotice(this.f6268p, BidLossCode.bidTimeOut());
                this.B = true;
            }
        } else if (i2 == 3 && (bidResponsed = this.f6266n) != null) {
            bidResponsed.sendLossNotice(this.f6268p, BidLossCode.bidWinButNotShow());
            this.B = true;
        }
        super.f(i2);
    }

    public void a(String str, String str2) {
        this.f6274v = new BidManager(new SplashBidRequestParams(str, str2, true, 2, 30, 30));
    }

    public void b() {
        BidManager bidManager = this.f6274v;
        if (bidManager != null) {
            bidManager.bid();
        }
    }
}
