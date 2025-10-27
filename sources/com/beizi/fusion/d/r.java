package com.beizi.fusion.d;

import android.content.Context;
import com.beizi.fusion.model.AdSpacesBean;
import com.tencent.connect.common.Constants;
import java.util.List;

/* loaded from: classes2.dex */
public class r extends e {
    public r(Context context, String str, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.d(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        }
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        str.hashCode();
        return !str.equals("FinalLink") ? aVar : new com.beizi.fusion.work.e.a(this.f4927b, this.f4930e, this.f4931f, buyerBean, forwardBean, this);
    }
}
