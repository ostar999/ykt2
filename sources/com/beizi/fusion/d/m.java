package com.beizi.fusion.d;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.List;

/* loaded from: classes2.dex */
public class m extends e {

    /* renamed from: v, reason: collision with root package name */
    private int f4980v;

    /* renamed from: w, reason: collision with root package name */
    private int f4981w;

    public m(Context context, String str, com.beizi.fusion.a aVar, long j2, int i2) {
        super(context, str, aVar, j2);
        this.f4981w = i2;
    }

    public void B() throws InterruptedException {
        a((ViewGroup) null);
    }

    public void C() {
        l();
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("3");
        }
    }

    public boolean b() {
        return this.f4938m;
    }

    public void d(int i2) {
        this.f4980v = i2;
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        String adType = buyerBean.getAdType();
        long sleepTime = forwardBean.getSleepTime();
        if ("NATIVE".equalsIgnoreCase(adType)) {
            str.hashCode();
            if (str.equals("GDT")) {
                return new com.beizi.fusion.work.interstitial.c(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            }
            return !str.equals("KUAISHOU") ? aVar : new com.beizi.fusion.work.interstitial.d(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
        }
        str.hashCode();
        if (str.equals("CSJ")) {
            return new com.beizi.fusion.work.interstitial.a(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
        }
        return !str.equals("GDT") ? aVar : new com.beizi.fusion.work.interstitial.b(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
    }

    public void a(Activity activity) {
        com.beizi.fusion.work.a aVar;
        if (activity == null || (aVar = this.f4934i) == null) {
            return;
        }
        aVar.a(activity);
    }
}
