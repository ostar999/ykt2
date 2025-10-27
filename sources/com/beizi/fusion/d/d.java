package com.beizi.fusion.d;

import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import java.util.List;

/* loaded from: classes2.dex */
public class d extends e {

    /* renamed from: v, reason: collision with root package name */
    private float f4922v;

    /* renamed from: w, reason: collision with root package name */
    private float f4923w;

    /* renamed from: x, reason: collision with root package name */
    private ViewGroup f4924x;

    public d(Context context, String str, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        AppEventId.getInstance(e.f4925a).setAppBannerRequest(this.f4939n);
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("4");
        }
    }

    public void b() {
        l();
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        com.beizi.fusion.work.a bVar;
        long sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        if (!str.equals("CSJ")) {
            bVar = !str.equals("GDT") ? aVar : new com.beizi.fusion.work.a.c(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4923w, this.f4922v, this.f4924x);
        } else {
            bVar = new com.beizi.fusion.work.a.b(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4923w, this.f4922v, this.f4924x);
        }
        return (ar.b().equalsIgnoreCase(str) || "BEIZI".equalsIgnoreCase(str)) ? new com.beizi.fusion.work.a.a(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4923w, this.f4922v, this.f4924x) : bVar;
    }

    public void a(float f2, float f3, ViewGroup viewGroup) throws InterruptedException {
        this.f4923w = f2;
        this.f4922v = f3;
        this.f4924x = viewGroup;
        a((ViewGroup) null);
    }
}
