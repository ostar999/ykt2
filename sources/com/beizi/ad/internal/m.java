package com.beizi.ad.internal;

import android.util.Log;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.network.a;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public class m extends n {

    /* renamed from: a, reason: collision with root package name */
    private com.beizi.ad.internal.network.a f4229a;

    /* renamed from: b, reason: collision with root package name */
    private d f4230b = new d(g.a().e(), StringUtil.createRequestId());

    @Override // com.beizi.ad.internal.e
    public void a() {
        this.f4229a = new com.beizi.ad.internal.network.a(new a.C0055a());
        g();
        try {
            this.f4229a.a(this);
            this.f4229a.executeOnExecutor(com.beizi.ad.a.a.c.b().c(), new Void[0]);
        } catch (IllegalStateException e2) {
            Log.d("lance", "ignored:" + e2.getMessage());
        } catch (RejectedExecutionException e3) {
            HaoboLog.e(HaoboLog.baseLogTag, "Concurrent Thread Exception while firing new ad request: " + e3.getMessage());
        }
    }

    @Override // com.beizi.ad.internal.e
    public d c() {
        return this.f4230b;
    }

    @Override // com.beizi.ad.internal.e
    public com.beizi.ad.b.a d() {
        return null;
    }

    @Override // com.beizi.ad.internal.n
    public void e() {
        com.beizi.ad.internal.network.a aVar = this.f4229a;
        if (aVar != null) {
            aVar.cancel(true);
            this.f4229a = null;
        }
        a((LinkedList<com.beizi.ad.internal.b.a>) null);
    }

    @Override // com.beizi.ad.internal.e
    public void a(int i2) {
        HaoboLog.d(HaoboLog.pbLogTag, "Failed to load prefetch request: " + i2);
    }

    @Override // com.beizi.ad.internal.e
    public void a(ServerResponse serverResponse) {
        Iterator<String> it = serverResponse.getPrefetchResources().iterator();
        while (it.hasNext()) {
            String next = it.next();
            HaoboLog.d(HaoboLog.baseLogTag, "Prefetch resource: " + next);
        }
    }
}
