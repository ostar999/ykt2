package com.beizi.fusion.work.e;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.e;
import com.beizi.fusion.f.b;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.xyz.newad.hudong.ADInit;
import com.xyz.newad.hudong.widgets.FakeListener;
import com.xyz.newad.hudong.widgets.faking.FakeAD;
import com.yikaobang.yixue.R2;

/* loaded from: classes2.dex */
public class a extends com.beizi.fusion.work.a {

    /* renamed from: n, reason: collision with root package name */
    private Context f5679n;

    /* renamed from: o, reason: collision with root package name */
    private String f5680o;

    /* renamed from: p, reason: collision with root package name */
    private long f5681p;

    public a(Context context, String str, long j2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean, e eVar) {
        this.f5679n = context;
        this.f5680o = str;
        this.f5681p = j2;
        this.f5541e = buyerBean;
        this.f5540d = eVar;
        this.f5542f = forwardBean;
        x();
    }

    @Override // com.beizi.fusion.work.a
    public void f() {
    }

    @Override // com.beizi.fusion.work.a
    public String g() {
        return "FinalLink";
    }

    @Override // com.beizi.fusion.work.a
    public void d() {
        if (this.f5540d == null) {
            return;
        }
        this.f5544h = this.f5541e.getAppId();
        this.f5545i = this.f5541e.getSpaceId();
        this.f5539c = b.a(this.f5541e.getId());
        String str = (String) aq.b(this.f5679n, "__OAID__", "");
        ac.b("BeiZis", "AdWorker chanel = " + this.f5539c);
        d dVar = this.f5537a;
        if (dVar != null) {
            com.beizi.fusion.b.b bVarA = dVar.a().a(this.f5539c);
            this.f5538b = bVarA;
            if (bVarA != null) {
                y();
                if (!as.a("com.xyz.newad.hudong.ADInit")) {
                    z();
                    this.f5549m.postDelayed(new Runnable() { // from class: com.beizi.fusion.work.e.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.e(R2.drawable.ic_cut_question_success_anim_day_3);
                        }
                    }, 10L);
                    Log.d("BeiZis", "FinalLink sdk not import , will do nothing");
                    return;
                } else {
                    A();
                    ADInit.getInstance().init(this.f5679n, this.f5544h);
                    ADInit.getInstance().setOaid(str);
                    B();
                }
            }
        }
        long sleepTime = this.f5542f.getSleepTime();
        Log.d("BeiZis", g() + ":requestAd:" + this.f5544h + "====" + this.f5545i + "===" + sleepTime);
        if (sleepTime > 0) {
            this.f5549m.sendEmptyMessageDelayed(1, sleepTime);
            return;
        }
        e eVar = this.f5540d;
        if (eVar == null || eVar.r() >= 1 || this.f5540d.q() == 2) {
            return;
        }
        p();
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
        if (!(this.f5679n instanceof Activity)) {
            Log.e("BeiZis", "NativeNotification Ad needs an Activity Context to show!");
            return;
        }
        FakeAD.get().finish((Activity) this.f5679n);
        ad();
        FakeAD.get().show((Activity) this.f5679n, this.f5545i, new FakeListener() { // from class: com.beizi.fusion.work.e.a.2
            public void onClick() {
                Log.d("BeiZis", "NativeNotification Ad onClick");
                a.this.K();
                if (((com.beizi.fusion.work.a) a.this).f5540d != null) {
                    if (((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                        ((com.beizi.fusion.work.a) a.this).f5540d.d(a.this.g());
                    }
                    a.this.an();
                }
            }

            public void onClose() {
                Log.d("BeiZis", "NativeNotification Ad onClose");
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    a.this.ah();
                }
                a.this.M();
            }

            public void onDismiss() {
                Log.d("BeiZis", "NativeNotification Ad onDismiss");
            }

            public void onFail() {
                Log.d("BeiZis", "NativeNotification Ad onFail");
                a.this.b("获取广告失败", R2.drawable.ic_coupon_middle);
            }

            public void onShow() {
                Log.d("BeiZis", "NativeNotification Ad onShow");
                a.this.ai();
                a.this.E();
                a.this.R();
                ((com.beizi.fusion.work.a) a.this).f5546j = com.beizi.fusion.f.a.ADSHOW;
                if (((com.beizi.fusion.work.a) a.this).f5540d != null && ((com.beizi.fusion.work.a) a.this).f5540d.q() != 2) {
                    ((com.beizi.fusion.work.a) a.this).f5540d.b(a.this.g());
                }
                a.this.I();
                a.this.J();
                a.this.am();
            }
        });
    }

    @Override // com.beizi.fusion.work.a
    public void q() {
        if (this.f5679n instanceof Activity) {
            FakeAD.get().finish((Activity) this.f5679n);
        }
    }
}
