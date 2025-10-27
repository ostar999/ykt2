package com.beizi.ad.internal.b;

import android.app.Activity;
import com.beizi.ad.R;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HaoboLog;

/* loaded from: classes2.dex */
public class c extends b {
    private c(Activity activity, com.beizi.ad.internal.e eVar, a aVar, com.beizi.ad.internal.b bVar, ServerResponse serverResponse) {
        int i2;
        super(eVar, aVar, bVar, l.BANNER, serverResponse);
        if (a(d.class)) {
            HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request));
            f();
            h();
            int i3 = 0;
            if (activity != null) {
                try {
                    if (!e()) {
                        this.f4019e.a(((d) this.f4016b).a(this, activity, this.f4017c.b(), this.f4017c.e(), this.f4017c.c(), this.f4017c.d(), a()));
                        i2 = -1;
                    }
                    HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_null_activity));
                } catch (Error e2) {
                    HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_error), e2);
                } catch (Exception e3) {
                    HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_exception), e3);
                }
                i2 = 0;
            } else {
                HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_request_null_activity));
                i2 = 0;
            }
            if (i2 == -1 && this.f4019e.getView() == null) {
                HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_view_null));
            } else {
                i3 = i2;
            }
            if (i3 != -1) {
                a(i3);
            }
        }
    }

    public static c a(Activity activity, com.beizi.ad.internal.e eVar, a aVar, com.beizi.ad.internal.b bVar, ServerResponse serverResponse) {
        c cVar = new c(activity, eVar, aVar, bVar, serverResponse);
        if (cVar.f4021g) {
            return null;
        }
        return cVar;
    }

    @Override // com.beizi.ad.internal.b.b
    public boolean c() {
        return true;
    }

    @Override // com.beizi.ad.internal.b.b
    public void d() {
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
