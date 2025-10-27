package com.beizi.fusion.d;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import java.util.List;

/* loaded from: classes2.dex */
public class t extends e {

    /* renamed from: v, reason: collision with root package name */
    private int f4989v;

    /* renamed from: w, reason: collision with root package name */
    private String f4990w;

    /* renamed from: x, reason: collision with root package name */
    private String f4991x;

    public t(Context context, String str, com.beizi.fusion.a aVar, long j2, int i2) {
        super(context, str, aVar, j2);
        this.f4989v = i2;
    }

    public String B() {
        return this.f4991x;
    }

    public boolean C() {
        return this.f4938m;
    }

    public void D() throws InterruptedException {
        a((ViewGroup) null);
    }

    public void E() {
        l();
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        AppEventId.getInstance(e.f4925a).setAppRewardedVideoRequest(this.f4939n);
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("1");
        }
    }

    public String b() {
        return this.f4990w;
    }

    public void e(String str) {
        this.f4990w = str;
    }

    public void f(String str) {
        this.f4991x = str;
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        long sleepTime;
        com.beizi.fusion.work.a bVar;
        sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        switch (str) {
            case "CSJ":
                bVar = new com.beizi.fusion.work.g.b(e.f4925a, this.f4930e, b(), B(), this.f4931f, sleepTime, buyerBean, forwardBean, this);
                break;
            case "GDT":
                bVar = new com.beizi.fusion.work.g.c(e.f4925a, this.f4930e, b(), B(), this.f4931f, sleepTime, buyerBean, forwardBean, this);
                break;
            case "BAIDU":
                return new com.beizi.fusion.work.g.a(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            case "KUAISHOU":
                return new com.beizi.fusion.work.g.e(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            case "HUAWEI":
                return new com.beizi.fusion.work.g.d(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            default:
                return aVar;
        }
        return bVar;
    }

    public void a(Activity activity) {
        com.beizi.fusion.work.a aVar;
        if (activity == null || (aVar = this.f4934i) == null) {
            return;
        }
        aVar.a(activity);
    }
}
