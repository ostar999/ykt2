package com.beizi.fusion.d;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import java.util.List;

/* loaded from: classes2.dex */
public class w extends e {

    /* renamed from: v, reason: collision with root package name */
    private int f4997v;

    public w(Context context, String str, com.beizi.fusion.a aVar, long j2, int i2) {
        super(context, str, aVar, j2);
        this.f4997v = i2;
    }

    public boolean B() {
        return this.f4938m;
    }

    public void C() {
        l();
    }

    public void D() {
        m();
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        AppEventId.getInstance(e.f4925a).setAppNativeRequest(this.f4939n);
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("5");
        }
    }

    public void b() throws InterruptedException {
        a((ViewGroup) null);
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        long sleepTime;
        sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        switch (str) {
            case "CSJ":
                aVar = new com.beizi.fusion.work.h.c(this.f4927b, sleepTime, buyerBean, forwardBean, this, this.f4997v);
                break;
            case "GDT":
                aVar = new com.beizi.fusion.work.h.d(this.f4927b, sleepTime, buyerBean, forwardBean, this, this.f4997v);
                break;
            case "KUAISHOU":
                aVar = new com.beizi.fusion.work.h.e(this.f4927b, sleepTime, buyerBean, forwardBean, this, this.f4997v);
                break;
        }
        return (ar.b().equalsIgnoreCase(str) || "BEIZI".equalsIgnoreCase(str)) ? new com.beizi.fusion.work.h.b(this.f4927b, sleepTime, buyerBean, forwardBean, this, this.f4997v) : aVar;
    }

    public void a(Activity activity) {
        com.beizi.fusion.work.a aVar;
        if (activity == null || (aVar = this.f4934i) == null) {
            return;
        }
        aVar.a(activity);
    }
}
