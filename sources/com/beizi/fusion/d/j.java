package com.beizi.fusion.d;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.AppEventId;
import com.tencent.connect.common.Constants;
import java.util.List;

/* loaded from: classes2.dex */
public class j extends e {

    /* renamed from: v, reason: collision with root package name */
    private int f4976v;

    public j(Context context, String str, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
    }

    public void B() throws InterruptedException {
        a((ViewGroup) null);
    }

    public void C() {
        l();
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        AppEventId.getInstance(e.f4925a).setAppFullScreenVideoRequest(this.f4939n);
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d(Constants.VIA_SHARE_TYPE_INFO);
        }
    }

    public boolean b() {
        return this.f4938m;
    }

    public void d(int i2) {
        this.f4976v = i2;
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        long sleepTime;
        sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        switch (str) {
            case "CSJ":
                return new com.beizi.fusion.work.c.b(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            case "GDT":
                return new com.beizi.fusion.work.c.c(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            case "BAIDU":
                return new com.beizi.fusion.work.c.a(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            case "KUAISHOU":
                return new com.beizi.fusion.work.c.d(e.f4925a, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
            default:
                return aVar;
        }
    }

    public void a(Activity activity) {
        com.beizi.fusion.work.a aVar;
        if (activity == null || (aVar = this.f4934i) == null) {
            return;
        }
        aVar.a(activity);
    }
}
