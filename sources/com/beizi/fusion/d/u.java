package com.beizi.fusion.d;

import android.content.Context;
import android.view.View;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import java.util.List;

/* loaded from: classes2.dex */
public class u extends e {

    /* renamed from: v, reason: collision with root package name */
    private int f4992v;

    /* renamed from: w, reason: collision with root package name */
    private int f4993w;

    public u(Context context, String str, View view, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
        this.f4932g = view;
    }

    public int B() {
        return this.f4993w;
    }

    public void C() {
        com.beizi.fusion.work.a aVar;
        if (this.f4945t || (aVar = this.f4934i) == null || this.f4944s) {
            return;
        }
        aVar.f();
        this.f4944s = true;
    }

    public int D() {
        String strL;
        com.beizi.fusion.work.a aVar = this.f4934i;
        if (aVar == null || (strL = aVar.l()) == null) {
            return -1;
        }
        try {
            return Integer.parseInt(strL);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public void E() {
        com.beizi.fusion.g.h.b().c().execute(new Runnable() { // from class: com.beizi.fusion.d.u.1
            @Override // java.lang.Runnable
            public void run() {
                com.beizi.fusion.b.c.a(e.f4925a).b(new com.beizi.fusion.b.b(b.f4908b, "", "200.500", "", b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            }
        });
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        AppEventId.getInstance(e.f4925a).setAppSplashRequest(this.f4939n);
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("2");
        }
    }

    public int b() {
        return this.f4992v;
    }

    public void d(int i2) {
        this.f4992v = i2;
    }

    public void e(int i2) {
        this.f4993w = i2;
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        com.beizi.fusion.work.a bVar;
        com.beizi.fusion.work.a jVar;
        bVar = (ar.b().equalsIgnoreCase(str) || "BEIZI".equalsIgnoreCase(str)) ? new com.beizi.fusion.work.splash.b(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this) : aVar;
        str.hashCode();
        switch (str) {
            case "INMOBI":
                return new com.beizi.fusion.work.splash.i(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this);
            case "JADYUN":
                jVar = new com.beizi.fusion.work.splash.j(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, b(), B(), this);
                break;
            case "GM":
                return new com.beizi.fusion.work.splash.g(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this);
            case "CSJ":
                jVar = new com.beizi.fusion.work.splash.e(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, b(), B(), this);
                break;
            case "GDT":
                return new com.beizi.fusion.work.splash.f(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this);
            case "MTG":
                return new com.beizi.fusion.work.splash.l(this.f4927b, this.f4930e, this.f4931f, this.f4929d, buyerBean, forwardBean, this);
            case "BAIDU":
                return new com.beizi.fusion.work.splash.a(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this);
            case "CSJ_NST":
                return new com.beizi.fusion.work.splash.d(this.f4927b, this.f4930e, this.f4931f, this.f4932g, this.f4929d, buyerBean, forwardBean, list, this);
            case "KUAISHOU":
                return new com.beizi.fusion.work.splash.k(this.f4927b, this.f4930e, this.f4931f, this.f4929d, buyerBean, forwardBean, list, this);
            case "HUAWEI":
                return new com.beizi.fusion.work.splash.h(this.f4927b, this.f4930e, this.f4931f, this.f4929d, buyerBean, forwardBean, this);
            default:
                return bVar;
        }
        return jVar;
    }
}
