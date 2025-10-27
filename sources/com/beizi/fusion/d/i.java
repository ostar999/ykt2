package com.beizi.fusion.d;

import android.content.Context;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.List;

/* loaded from: classes2.dex */
public class i extends e {
    public i(Context context, String str, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
    }

    public void B() {
        l();
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d("7");
        }
    }

    public void b() throws InterruptedException {
        a((ViewGroup) null);
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        long sleepTime = forwardBean.getSleepTime();
        str.hashCode();
        if (str.equals("CSJ")) {
            return new com.beizi.fusion.work.b.a(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
        }
        return !str.equals("KUAISHOU") ? aVar : new com.beizi.fusion.work.b.b(this.f4927b, this.f4930e, this.f4931f, sleepTime, buyerBean, forwardBean, this);
    }
}
