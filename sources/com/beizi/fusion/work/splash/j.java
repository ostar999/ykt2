package com.beizi.fusion.work.splash;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.d.n;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.jd.ad.sdk.bl.initsdk.JADYunSdk;
import com.jd.ad.sdk.dl.model.JADSlot;
import com.jd.ad.sdk.splash.JADSplash;
import com.jd.ad.sdk.splash.JADSplashListener;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public class j extends com.beizi.fusion.work.a {
    private int A;
    private int B;

    /* renamed from: n, reason: collision with root package name */
    private Context f6223n;

    /* renamed from: o, reason: collision with root package name */
    private String f6224o;

    /* renamed from: p, reason: collision with root package name */
    private long f6225p;

    /* renamed from: q, reason: collision with root package name */
    private View f6226q;

    /* renamed from: r, reason: collision with root package name */
    private ViewGroup f6227r;

    /* renamed from: s, reason: collision with root package name */
    private View f6228s;

    /* renamed from: t, reason: collision with root package name */
    private JADSplash f6229t;

    /* renamed from: u, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6230u;

    /* renamed from: v, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6231v = new ArrayList();

    /* renamed from: w, reason: collision with root package name */
    private List<AdSpacesBean.RenderViewBean> f6232w = new ArrayList();

    /* renamed from: x, reason: collision with root package name */
    private boolean f6233x;

    /* renamed from: y, reason: collision with root package name */
    private float f6234y;

    /* renamed from: z, reason: collision with root package name */
    private float f6235z;

    public j(Context context, String str, long j2, View view, ViewGroup viewGroup, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, List<AdSpacesBean.RenderViewBean> list, int i2, int i3, com.beizi.fusion.d.e eVar) {
        this.f6223n = context;
        this.f6224o = str;
        this.f6225p = j2;
        this.f6226q = view;
        this.f6227r = viewGroup;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        this.f6230u = list;
        this.A = i2;
        this.B = i3;
        x();
    }

    private void aL() {
        ViewGroup viewGroup;
        if (this.f6229t == null || (viewGroup = this.f6227r) == null || this.f6228s == null) {
            aD();
        } else {
            viewGroup.removeAllViews();
            this.f6227r.addView(this.f6228s);
        }
    }

    private void aM() {
        for (int i2 = 0; i2 < this.f6230u.size(); i2++) {
            AdSpacesBean.RenderViewBean renderViewBean = this.f6230u.get(i2);
            String type = renderViewBean.getType();
            if ("SKIPVIEW".equals(type)) {
                this.f6232w.add(renderViewBean);
            } else if ("MATERIALVIEW".equals(type)) {
                this.f6231v.add(renderViewBean);
            }
        }
        if (this.f6231v.size() > 0) {
            Collections.sort(this.f6231v, new Comparator<AdSpacesBean.RenderViewBean>() { // from class: com.beizi.fusion.work.splash.j.3
                @Override // java.util.Comparator
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public int compare(AdSpacesBean.RenderViewBean renderViewBean2, AdSpacesBean.RenderViewBean renderViewBean3) {
                    return renderViewBean3.getLevel() - renderViewBean2.getLevel();
                }
            });
        }
    }

    @Override // com.beizi.fusion.work.a
    public void G() {
        if (!F() || this.f6229t == null) {
            return;
        }
        aq();
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "JADYUN";
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
                if (!as.a("com.jd.ad.sdk.bl.initsdk.JADYunSdk")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.splash.j.1
                        @Override // java.lang.Runnable
                        public void run() {
                            j.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.e("BeiZis", "JD sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    n.a(this.f6223n, this.f5544h);
                    this.f5538b.y(JADYunSdk.getSDKVersion());
                    aB();
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        if (this.f5540d.t()) {
            sleepTime = Math.max(sleepTime, this.f5542f.getHotRequestDelay());
        }
        List<AdSpacesBean.RenderViewBean> list = this.f6230u;
        boolean z2 = list != null && list.size() > 0;
        this.f6233x = z2;
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
        this.f6234y = as.m(this.f6223n);
        this.f6235z = as.n(this.f6223n);
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
        Log.d("BeiZis", g() + " out make show ad");
        aL();
    }

    @Override // com.beizi.fusion.work.a
    public com.beizi.fusion.f.a k() {
        return this.f5546j;
    }

    @Override // com.beizi.fusion.work.a
    public String l() {
        JADSplash jADSplash = this.f6229t;
        if (jADSplash == null || jADSplash.getJADExtra() == null || this.f6229t.getJADExtra().getPrice() <= 0) {
            return null;
        }
        return this.f6229t.getJADExtra().getPrice() + "";
    }

    @Override // com.beizi.fusion.work.a
    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    @Override // com.beizi.fusion.work.a
    public void p() {
        C();
        al();
        if (this.A == 0) {
            this.A = (int) as.k(this.f6223n);
        }
        if (this.B == 0) {
            this.B = (int) as.l(this.f6223n);
        }
        JADSplash jADSplash = new JADSplash(this.f6223n, new JADSlot.Builder().setSlotID(this.f5545i).setSize(this.A, this.B).setTolerateTime(Math.round(this.f6225p / 1000.0f)).setSkipTime(5).setSplashClickAreaType(0).build());
        this.f6229t = jADSplash;
        jADSplash.loadAd(new JADSplashListener() { // from class: com.beizi.fusion.work.splash.j.2

            /* renamed from: a, reason: collision with root package name */
            boolean f6237a = false;

            /* renamed from: b, reason: collision with root package name */
            boolean f6238b = false;

            public void onClick() {
                Log.d("BeiZis", "showJadYunSplash onClick()");
                if (((com.beizi.fusion.work.a) j.this).f5540d != null && ((com.beizi.fusion.work.a) j.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) j.this).f5540d.d(j.this.g());
                    ((com.beizi.fusion.work.a) j.this).f5549m.sendEmptyMessageDelayed(2, (((com.beizi.fusion.work.a) j.this).f5548l + 5000) - System.currentTimeMillis());
                }
                if (this.f6238b) {
                    return;
                }
                this.f6238b = true;
                j.this.K();
                j.this.an();
            }

            public void onClose() {
                Log.d("BeiZis", "showJadYunSplash onClose()");
                if (((com.beizi.fusion.work.a) j.this).f5540d != null && ((com.beizi.fusion.work.a) j.this).f5540d.q() != 2) {
                    j.this.ah();
                }
                j.this.M();
            }

            public void onExposure() {
                Log.d("BeiZis", "showJadYunSplash onExposure()");
                ((com.beizi.fusion.work.a) j.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (this.f6237a) {
                    return;
                }
                this.f6237a = true;
                j.this.aG();
                j.this.ag();
                j.this.I();
                j.this.J();
                j.this.am();
            }

            public void onLoadFailure(int i2, String str) {
                Log.d("BeiZis", "showJadYunSplash onLoadFailure code:" + i2 + ";message:" + str);
                j.this.b(str, i2);
                j.this.q();
            }

            public void onLoadSuccess() {
                Log.d("BeiZis", "showJadYunSplash onLoadSuccess()");
                if (j.this.f6229t != null && j.this.f6229t.getJADExtra() != null) {
                    Log.d("BeiZis", "showJadYunSplash getECPMLevel:" + j.this.f6229t.getJADExtra().getPrice());
                    j jVar = j.this;
                    jVar.a((double) jVar.f6229t.getJADExtra().getPrice());
                }
                ((com.beizi.fusion.work.a) j.this).f5546j = com.beizi.fusion.f.a.ADLOAD;
                j.this.E();
            }

            public void onRenderFailure(int i2, String str) {
                Log.d("BeiZis", "showJadYunSplash onRenderFailure code:" + i2 + ";message:" + str);
                j.this.b(str, i2);
                j.this.q();
            }

            public void onRenderSuccess(View view) {
                Log.d("BeiZis", "showJadYunSplash onRenderSuccess()");
                j.this.f6228s = view;
                if (j.this.ac()) {
                    j.this.b();
                } else {
                    j.this.S();
                }
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        JADSplash jADSplash = this.f6229t;
        if (jADSplash != null) {
            jADSplash.destroy();
        }
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
}
