package com.beizi.ad.internal.b;

import android.app.Activity;
import com.beizi.ad.R;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HaoboLog;

/* loaded from: classes2.dex */
public class f extends b {
    private f(Activity activity, com.beizi.ad.internal.e eVar, a aVar, com.beizi.ad.internal.b bVar, ServerResponse serverResponse) {
        super(eVar, aVar, bVar, l.INTERSTITIAL, serverResponse);
        if (a(g.class)) {
            HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request));
            f();
            h();
            int i2 = 0;
            try {
                if (activity != null) {
                    ((g) this.f4016b).a(this, activity, this.f4017c.b(), this.f4017c.e(), a());
                    i2 = -1;
                } else {
                    HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_null_activity));
                }
            } catch (Error e2) {
                HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_error), e2);
            } catch (Exception e3) {
                HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_exception), e3);
            }
            if (i2 != -1) {
                a(i2);
            }
        }
    }

    public static f a(Activity activity, com.beizi.ad.internal.e eVar, a aVar, com.beizi.ad.internal.b bVar, ServerResponse serverResponse) {
        f fVar = new f(activity, eVar, aVar, bVar, serverResponse);
        if (fVar.f4021g) {
            return null;
        }
        return fVar;
    }

    @Override // com.beizi.ad.internal.b.b
    public boolean c() {
        return ((g) this.f4016b).b();
    }

    @Override // com.beizi.ad.internal.b.b
    public void d() {
        if (this.f4016b == null || e()) {
            return;
        }
        ((g) this.f4016b).a();
    }

    @Override // com.beizi.ad.internal.b.b
    public void j() {
        this.f4023i = true;
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.f();
        }
    }

    @Override // com.beizi.ad.internal.b.b
    public void k() {
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.d();
        }
    }

    @Override // com.beizi.ad.internal.b.b
    public void l() {
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.e();
        }
    }
}
