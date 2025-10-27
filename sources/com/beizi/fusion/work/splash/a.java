package com.beizi.fusion.work.splash;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.baidu.mobads.sdk.api.AdSettings;
import com.baidu.mobads.sdk.api.RequestParameters;
import com.baidu.mobads.sdk.api.SplashAd;
import com.baidu.mobads.sdk.api.SplashInteractionListener;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.m;
import com.beizi.fusion.model.AdSpacesBean;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a {
    private float A;

    /* renamed from: n, reason: collision with root package name */
    private Context f6020n;

    /* renamed from: o, reason: collision with root package name */
    private String f6021o;

    /* renamed from: p, reason: collision with root package name */
    private long f6022p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f6023q;

    /* renamed from: r, reason: collision with root package name */
    private View f6024r;

    /* renamed from: s, reason: collision with root package name */
    private ViewGroup f6025s;

    /* renamed from: t, reason: collision with root package name */
    private ViewGroup f6026t;

    /* renamed from: u, reason: collision with root package name */
    private SplashAd f6027u;

    /* renamed from: v, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6028v;

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6029w = new ArrayList();

    /* renamed from: x, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6030x = new ArrayList();

    /* renamed from: y, reason: collision with root package name */
    private boolean f6031y;

    /* renamed from: z, reason: collision with root package name */
    private float f6032z;

    public a(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.d.e eVar) {
        this.f6020n = context;
        this.f6021o = str;
        this.f6022p = j2;
        this.f6024r = view;
        this.f6025s = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6026t = new SplashContainer(context);
        this.f6028v = list;
        x();
    }

    private void aL() {
        ViewGroup viewGroup;
        SplashAd splashAd = this.f6027u;
        if (splashAd == null || (viewGroup = this.f6025s) == null) {
            aD();
            return;
        }
        splashAd.show(viewGroup);
        if (this.f6031y) {
            aN();
        }
    }

    private void aM() {
        for (int i2 = 0; i2 < this.f6028v.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6028v.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6030x.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6029w.add(renderViewBean);
            }
        }
        if (this.f6029w.size() > 0) {
            Collections.sort(this.f6029w, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.a.3
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean2, AdSpacesBean.RenderViewBean renderViewBean3) {
                    return renderViewBean3.getLevel() - renderViewBean2.getLevel();
                }
            });
        }
    }

    private void aN() {
        if (this.f6029w.size() > 0) {
            aO();
        }
    }

    private void aO() {
        for (AdSpacesBean.RenderViewBean renderViewBean : this.f6029w) {
            AdSpacesBean.PositionBean layerPosition = renderViewBean.getLayerPosition();
            ImageView imageView = new ImageView(this.f6020n);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setVisibility(0);
            String imageUrl = renderViewBean.getImageUrl();
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("http")) {
                com.beizi.fusion.g.i.a(this.f6020n).a(imageUrl).a(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.work.splash.a.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (a.this.f6025s != null) {
                        a.this.aP();
                    }
                }
            });
            float width = this.f6025s.getWidth();
            float height = this.f6025s.getHeight();
            if (width == 0.0f) {
                width = this.f6032z;
            }
            if (height == 0.0f) {
                height = this.A - as.a(this.f6020n, 100.0f);
            }
            this.f6025s.addView(imageView, new FrameLayout.LayoutParams((int) (width * layerPosition.getWidth() * 0.01d), (int) (height * layerPosition.getHeight() * 0.01d)));
            float centerX = (float) (layerPosition.getCenterX() * 0.01d);
            float centerY = (height * ((float) (layerPosition.getCenterY() * 0.01d))) - (r6 / 2);
            imageView.setX((width * centerX) - (r5 / 2));
            imageView.setY(centerY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void aP() {
        float fRandom = (int) ((Math.random() * 10.0d) + 1.0d);
        m.a(this.f6025s, this.f6025s.getPivotX() + fRandom, this.f6025s.getPivotY() - fRandom);
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f6027u == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public void aG() {
        SplashAd splashAd = this.f6027u;
        if (splashAd == null || TextUtils.isEmpty(splashAd.getECPMLevel()) || this.f6023q) {
            return;
        }
        this.f6023q = true;
        ac.a("BeiZis", "showBdSplash channel == Baidu竞价成功");
        ac.a("BeiZis", "showBdSplash channel == sendWinNoticeECPM:" + this.f6027u.getECPMLevel());
        SplashAd splashAd2 = this.f6027u;
        splashAd2.biddingSuccess(splashAd2.getECPMLevel());
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "BAIDU";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = com.beizi.fusion.f.b.a(this.f5541e.getId());
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        com.beizi.fusion.b.d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.baidu.mobads.sdk.api.BDAdConfig")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "BAIDU sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    this.f5538b.v(String.valueOf(AdSettings.getSDKVersion()));
                    aB();
                    com.beizi.fusion.d.f.a(this.f6020n, this.f5544h);
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6028v;
        boolean z2 = list != null && list.size() > 0;
        this.f6031y = z2;
        if (z2) {
            aM();
        }
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
        } else {
            com.beizi.fusion.d.e eVar = this.f5540d;
            if (eVar != null && eVar.r() < 1 && this.f5540d.q() != 2) {
                p();
            }
        }
        this.f6032z = as.m(this.f6020n);
        this.A = as.n(this.f6020n);
    }

    @Override // com.beizi.fusion.work.a
    public void f(int i2) {
        SplashAd splashAd = this.f6027u;
        if (splashAd == null || TextUtils.isEmpty(splashAd.getECPMLevel()) || this.f6023q) {
            return;
        }
        this.f6023q = true;
        ac.a("BeiZis", "showBdSplash channel == Baidu竞价失败:" + i2);
        this.f6027u.biddingFail(i2 != 1 ? i2 != 2 ? "900" : "100" : "203");
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        SplashAd splashAd = this.f6027u;
        if (splashAd == null) {
            return null;
        }
        return splashAd.getECPMLevel();
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        SplashAd splashAd = new SplashAd((Activity) this.f6020n, this.f5545i, new RequestParameters.Builder().addExtra("fetchAd", k.a.f27524v).addExtra("shake_logo_size", "80").addExtra("displayDownloadInfo", k.a.f27523u).addExtra("use_dialog_frame", k.a.f27524v).addExtra("timeout", String.valueOf(this.f6022p)).build(), new SplashInteractionListener() { // from class: com.beizi.fusion.work.splash.a.2

            /* renamed from: a, reason: collision with root package name */
            boolean f6034a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f6035b = false;

            public void onADLoaded() {
                Log.d("BeiZis", "showBdSplash onADLoaded()");
                try {
                    if (a.this.f6027u != null && !TextUtils.isEmpty(a.this.f6027u.getECPMLevel())) {
                        Log.d("BeiZis", "showBdSplash getECPMLevel:" + a.this.f6027u.getECPMLevel());
                        a aVar = a.this;
                        aVar.a(Double.parseDouble(aVar.f6027u.getECPMLevel()));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                a.this.E();
                if (a.this.ac()) {
                    a.this.b();
                } else {
                    a.this.S();
                }
            }

            public void onAdCacheFailed() {
                Log.d("BeiZis", "showBdSplash onAdCacheFailed()");
            }

            public void onAdCacheSuccess() {
                Log.d("BeiZis", "showBdSplash onAdCacheSuccess()");
            }

            public void onAdClick() {
                Log.d("BeiZis", "showBdSplash onAdClick()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                    ((com.beizi.fusion.work.a) a.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) a.this).f5548l + 5000) - System.currentTimeMillis());
                }
                if (this.f6035b) {
                    return;
                }
                this.f6035b = true;
                a.this.K();
                a.this.an();
            }

            public void onAdDismissed() {
                Log.d("BeiZis", "showBdSplash onAdDismissed()");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    a.this.ah();
                }
                a.this.M();
            }

            public void onAdFailed(String str) {
                Log.d("BeiZis", "showBdSplash onAdFailed:" + str);
                a.this.b(str, R2.attr.toolbar_color_new);
            }

            public void onAdPresent() {
                Log.d("BeiZis", "showBdSplash onAdPresent()");
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (this.f6034a) {
                    return;
                }
                this.f6034a = true;
                a.this.aG();
                a.this.ag();
                a.this.I();
                a.this.J();
                a.this.am();
            }

            public void onLpClosed() {
                Log.d("BeiZis", "showBdSplash onLpClosed()");
            }
        });
        this.f6027u = splashAd;
        splashAd.load();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
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

    @Override // com.beizi.fusion.work.a
    public void a(Message message) {
        com.beizi.fusion.b.b bVar = this.f5538b;
        if (bVar != null) {
            bVar.i(String.valueOf(message.obj));
            aB();
            H();
        }
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aL();
    }
}
