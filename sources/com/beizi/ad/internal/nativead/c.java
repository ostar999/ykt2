package com.beizi.ad.internal.nativead;

import android.util.Log;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.d;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.n;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public class c extends n {

    /* renamed from: a, reason: collision with root package name */
    private final SoftReference<b> f4331a;

    /* renamed from: b, reason: collision with root package name */
    private com.beizi.ad.internal.nativead.a.a f4332b;

    public c(b bVar) {
        this.f4331a = new SoftReference<>(bVar);
    }

    @Override // com.beizi.ad.internal.e
    public void a() {
        b bVar = this.f4331a.get();
        if (bVar == null) {
            HaoboLog.e(HaoboLog.baseLogTag, "Before execute request manager, you should set ad request!");
            return;
        }
        g();
        try {
            bVar.a(this);
            bVar.executeOnExecutor(com.beizi.ad.a.a.c.b().c(), new Void[0]);
        } catch (IllegalStateException e2) {
            Log.d("lance", "ignored:" + e2.getMessage());
        } catch (RejectedExecutionException e3) {
            HaoboLog.e(HaoboLog.baseLogTag, "Concurrent Thread Exception while firing new ad request: " + e3.getMessage());
        }
    }

    @Override // com.beizi.ad.internal.e
    public d c() {
        b bVar = this.f4331a.get();
        if (bVar != null) {
            return bVar.f();
        }
        return null;
    }

    @Override // com.beizi.ad.internal.e
    public com.beizi.ad.b.a d() {
        return null;
    }

    @Override // com.beizi.ad.internal.n
    public void e() {
        b bVar = this.f4331a.get();
        if (bVar != null) {
            bVar.cancel(true);
        }
        a((LinkedList<com.beizi.ad.internal.b.a>) null);
        com.beizi.ad.internal.nativead.a.a aVar = this.f4332b;
        if (aVar != null) {
            aVar.a(true);
            this.f4332b = null;
        }
    }

    @Override // com.beizi.ad.internal.e
    public void a(int i2) {
        h();
        b bVar = this.f4331a.get();
        if (bVar != null) {
            bVar.g().a(i2);
        }
    }

    @Override // com.beizi.ad.internal.e
    public void a(final ServerResponse serverResponse) {
        b bVar = this.f4331a.get();
        if (bVar != null) {
            boolean zContainsAds = serverResponse.containsAds();
            boolean z2 = (b() == null || b().isEmpty()) ? false : true;
            Log.d("lance", zContainsAds + "=====" + z2);
            if (!zContainsAds && !z2) {
                HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_no_ads));
                bVar.g().a(3);
                return;
            }
            if (zContainsAds) {
                a(serverResponse.getMediationAds());
            }
            if (b() != null && !b().isEmpty()) {
                com.beizi.ad.internal.b.a aVarI = i();
                if (aVarI != null) {
                    aVarI.a(serverResponse.getExtras());
                }
                this.f4332b = com.beizi.ad.internal.nativead.a.a.a(aVarI, this, serverResponse);
                return;
            }
            final a aVar = (a) serverResponse.getNativeAdResponse();
            aVar.a(bVar.f().a());
            aVar.a(bVar.a());
            a(new com.beizi.ad.internal.network.b() { // from class: com.beizi.ad.internal.nativead.c.1
                @Override // com.beizi.ad.internal.network.b
                public l a() {
                    return l.NATIVE;
                }

                @Override // com.beizi.ad.internal.network.b
                public boolean b() {
                    return false;
                }

                @Override // com.beizi.ad.internal.network.b
                public com.beizi.ad.internal.view.c c() {
                    return null;
                }

                @Override // com.beizi.ad.internal.network.b
                public NativeAdResponse d() {
                    return aVar;
                }

                @Override // com.beizi.ad.internal.network.b
                public String e() {
                    return "";
                }

                @Override // com.beizi.ad.internal.network.b
                public String f() {
                    return serverResponse.getPrice();
                }

                @Override // com.beizi.ad.internal.network.b
                public String g() {
                    return serverResponse.getAdId();
                }

                @Override // com.beizi.ad.internal.network.b
                public void h() {
                    aVar.destroy();
                }
            });
        }
    }

    public void a(com.beizi.ad.internal.network.b bVar) {
        h();
        if (this.f4332b != null) {
            this.f4332b = null;
        }
        b bVar2 = this.f4331a.get();
        if (bVar2 != null) {
            bVar2.g().a(bVar);
        } else {
            bVar.h();
        }
    }
}
