package com.beizi.fusion.d;

import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import java.util.List;

/* loaded from: classes2.dex */
public class p extends e {

    /* renamed from: v, reason: collision with root package name */
    private float f4985v;

    /* renamed from: w, reason: collision with root package name */
    private float f4986w;

    /* renamed from: x, reason: collision with root package name */
    private int f4987x;

    public p(Context context, String str, com.beizi.fusion.a aVar, long j2, int i2) {
        super(context, str, aVar, j2);
        this.f4987x = i2;
    }

    public void B() {
        l();
    }

    public void C() {
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

    public int b() {
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

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        long sleepTime;
        com.beizi.fusion.work.a cVar;
        sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        switch (str) {
            case "CSJ":
                cVar = new com.beizi.fusion.work.nativead.c(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w);
                break;
            case "GDT":
                if (this.f4987x == 3) {
                    cVar = new com.beizi.fusion.work.nativead.e(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w);
                    break;
                } else {
                    cVar = new com.beizi.fusion.work.nativead.d(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w);
                    break;
                }
            case "BAIDU":
                cVar = new com.beizi.fusion.work.nativead.a(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w);
                break;
            case "KUAISHOU":
                cVar = new com.beizi.fusion.work.nativead.f(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w);
                break;
            default:
                cVar = aVar;
                break;
        }
        return (ar.b().equalsIgnoreCase(str) || "BEIZI".equalsIgnoreCase(str)) ? new com.beizi.fusion.work.nativead.b(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this, this.f4985v, this.f4986w) : cVar;
    }

    public void a(float f2, float f3) throws InterruptedException {
        this.f4985v = f2;
        this.f4986w = f3;
        a((ViewGroup) null);
    }
}
