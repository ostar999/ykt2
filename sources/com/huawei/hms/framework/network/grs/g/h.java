package com.huawei.hms.framework.network.grs.g;

import com.huawei.hms.framework.common.ExecutorsUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.NetworkUtil;
import com.huawei.hms.framework.network.grs.h.d;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private final ExecutorService f7670a = ExecutorsUtils.newCachedThreadPool("GRS_RequestController-Task");

    /* renamed from: b, reason: collision with root package name */
    private final Map<String, com.huawei.hms.framework.network.grs.g.k.b> f7671b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private final Object f7672c = new Object();

    /* renamed from: d, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f7673d;

    public class a implements Callable<d> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.g.k.c f7674a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f7675b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c f7676c;

        public a(com.huawei.hms.framework.network.grs.g.k.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2) {
            this.f7674a = cVar;
            this.f7675b = str;
            this.f7676c = cVar2;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public d call() {
            return new c(this.f7674a, h.this.f7673d).a(h.this.f7670a, this.f7675b, this.f7676c);
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.g.k.c f7678a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f7679b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c f7680c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.b f7681d;

        public b(com.huawei.hms.framework.network.grs.g.k.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2, com.huawei.hms.framework.network.grs.b bVar) {
            this.f7678a = cVar;
            this.f7679b = str;
            this.f7680c = cVar2;
            this.f7681d = bVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            h hVar = h.this;
            hVar.a(hVar.a(this.f7678a, this.f7679b, this.f7680c), this.f7681d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(d dVar, com.huawei.hms.framework.network.grs.b bVar) {
        if (bVar != null) {
            if (dVar == null) {
                Logger.v("RequestController", "GrsResponse is null");
                bVar.a();
            } else {
                Logger.v("RequestController", "GrsResponse is not null");
                bVar.a(dVar);
            }
        }
    }

    public d a(com.huawei.hms.framework.network.grs.g.k.c cVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2) {
        Future<d> futureSubmit;
        String str2;
        String str3;
        Logger.d("RequestController", "request to server with service name is: " + str);
        String grsParasKey = cVar.b().getGrsParasKey(true, true, cVar.a());
        Logger.v("RequestController", "request spUrlKey: " + grsParasKey);
        synchronized (this.f7672c) {
            if (!NetworkUtil.isNetworkAvailable(cVar.a())) {
                return null;
            }
            d.a aVarA = com.huawei.hms.framework.network.grs.h.d.a(grsParasKey);
            com.huawei.hms.framework.network.grs.g.k.b bVar = this.f7671b.get(grsParasKey);
            if (bVar == null || !bVar.b()) {
                if (aVarA != null && aVarA.a()) {
                    return null;
                }
                Logger.d("RequestController", "hitGrsRequestBean == null or request block is released.");
                futureSubmit = this.f7670a.submit(new a(cVar, str, cVar2));
                this.f7671b.put(grsParasKey, new com.huawei.hms.framework.network.grs.g.k.b(futureSubmit));
            } else {
                futureSubmit = bVar.a();
            }
            try {
                return futureSubmit.get();
            } catch (InterruptedException e2) {
                e = e2;
                str2 = "RequestController";
                str3 = "when check result, find InterruptedException, check others";
                Logger.w(str2, str3, e);
                return null;
            } catch (CancellationException e3) {
                e = e3;
                str2 = "RequestController";
                str3 = "when check result, find CancellationException, check others";
                Logger.w(str2, str3, e);
                return null;
            } catch (ExecutionException e4) {
                e = e4;
                str2 = "RequestController";
                str3 = "when check result, find ExecutionException, check others";
                Logger.w(str2, str3, e);
                return null;
            }
        }
    }

    public void a(com.huawei.hms.framework.network.grs.e.a aVar) {
        this.f7673d = aVar;
    }

    public void a(com.huawei.hms.framework.network.grs.g.k.c cVar, com.huawei.hms.framework.network.grs.b bVar, String str, com.huawei.hms.framework.network.grs.e.c cVar2) {
        this.f7670a.execute(new b(cVar, str, cVar2, bVar));
    }

    public void a(String str) {
        synchronized (this.f7672c) {
            this.f7671b.remove(str);
        }
    }
}
