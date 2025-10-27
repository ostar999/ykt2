package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.h.d;
import com.just.agentweb.DefaultWebClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: n, reason: collision with root package name */
    private static final String f7632n = "c";

    /* renamed from: a, reason: collision with root package name */
    private final GrsBaseInfo f7633a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f7634b;

    /* renamed from: c, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.a f7635c;

    /* renamed from: d, reason: collision with root package name */
    private d f7636d;

    /* renamed from: j, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.g.k.c f7642j;

    /* renamed from: k, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.g.k.d f7643k;

    /* renamed from: e, reason: collision with root package name */
    private final Map<String, Future<d>> f7637e = new ConcurrentHashMap(16);

    /* renamed from: f, reason: collision with root package name */
    private final List<d> f7638f = new CopyOnWriteArrayList();

    /* renamed from: g, reason: collision with root package name */
    private final JSONArray f7639g = new JSONArray();

    /* renamed from: h, reason: collision with root package name */
    private final List<String> f7640h = new CopyOnWriteArrayList();

    /* renamed from: i, reason: collision with root package name */
    private final List<String> f7641i = new CopyOnWriteArrayList();

    /* renamed from: l, reason: collision with root package name */
    private String f7644l = "";

    /* renamed from: m, reason: collision with root package name */
    private long f7645m = 1;

    public class a implements Callable<d> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ExecutorService f7646a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ String f7647b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ com.huawei.hms.framework.network.grs.e.c f7648c;

        public a(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) {
            this.f7646a = executorService;
            this.f7647b = str;
            this.f7648c = cVar;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public d call() {
            return c.this.b(this.f7646a, this.f7647b, this.f7648c);
        }
    }

    public c(com.huawei.hms.framework.network.grs.g.k.c cVar, com.huawei.hms.framework.network.grs.e.a aVar) {
        this.f7642j = cVar;
        this.f7633a = cVar.b();
        this.f7634b = cVar.a();
        this.f7635c = aVar;
        c();
        d();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0097 A[LOOP:0: B:3:0x0005->B:33:0x0097, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x008f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.framework.network.grs.g.d a(java.util.concurrent.ExecutorService r16, java.util.List<java.lang.String> r17, java.lang.String r18, com.huawei.hms.framework.network.grs.e.c r19) throws java.util.concurrent.ExecutionException, java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        /*
            r15 = this;
            r9 = r15
            r0 = 0
            r10 = 0
            r11 = r0
            r12 = r10
        L5:
            int r0 = r17.size()
            if (r12 >= r0) goto L9b
            r13 = r17
            java.lang.Object r0 = r13.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L8a
            com.huawei.hms.framework.network.grs.g.a r14 = new com.huawei.hms.framework.network.grs.g.a
            android.content.Context r5 = r9.f7634b
            com.huawei.hms.framework.network.grs.GrsBaseInfo r7 = r9.f7633a
            r1 = r14
            r2 = r0
            r3 = r12
            r4 = r15
            r6 = r18
            r8 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            java.util.concurrent.Callable r1 = r14.g()
            r2 = r16
            java.util.concurrent.Future r1 = r2.submit(r1)
            java.util.Map<java.lang.String, java.util.concurrent.Future<com.huawei.hms.framework.network.grs.g.d>> r3 = r9.f7637e
            r3.put(r0, r1)
            r3 = 1
            long r4 = r9.f7645m     // Catch: java.util.concurrent.TimeoutException -> L68 java.lang.InterruptedException -> L70 java.util.concurrent.ExecutionException -> L79 java.util.concurrent.CancellationException -> L82
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.util.concurrent.TimeoutException -> L68 java.lang.InterruptedException -> L70 java.util.concurrent.ExecutionException -> L79 java.util.concurrent.CancellationException -> L82
            java.lang.Object r0 = r1.get(r4, r0)     // Catch: java.util.concurrent.TimeoutException -> L68 java.lang.InterruptedException -> L70 java.util.concurrent.ExecutionException -> L79 java.util.concurrent.CancellationException -> L82
            r1 = r0
            com.huawei.hms.framework.network.grs.g.d r1 = (com.huawei.hms.framework.network.grs.g.d) r1     // Catch: java.util.concurrent.TimeoutException -> L68 java.lang.InterruptedException -> L70 java.util.concurrent.ExecutionException -> L79 java.util.concurrent.CancellationException -> L82
            if (r1 == 0) goto L66
            boolean r0 = r1.o()     // Catch: java.util.concurrent.TimeoutException -> L5c java.lang.InterruptedException -> L5e java.util.concurrent.ExecutionException -> L61 java.util.concurrent.CancellationException -> L64
            if (r0 != 0) goto L53
            boolean r0 = r1.m()     // Catch: java.util.concurrent.TimeoutException -> L5c java.lang.InterruptedException -> L5e java.util.concurrent.ExecutionException -> L61 java.util.concurrent.CancellationException -> L64
            if (r0 == 0) goto L66
        L53:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f7632n     // Catch: java.util.concurrent.TimeoutException -> L5c java.lang.InterruptedException -> L5e java.util.concurrent.ExecutionException -> L61 java.util.concurrent.CancellationException -> L64
            java.lang.String r4 = "grs request return body is not null and is OK."
            com.huawei.hms.framework.common.Logger.i(r0, r4)     // Catch: java.util.concurrent.TimeoutException -> L5c java.lang.InterruptedException -> L5e java.util.concurrent.ExecutionException -> L61 java.util.concurrent.CancellationException -> L64
            r11 = r1
            goto L8d
        L5c:
            r11 = r1
            goto L68
        L5e:
            r0 = move-exception
            r11 = r1
            goto L71
        L61:
            r0 = move-exception
            r11 = r1
            goto L7a
        L64:
            r11 = r1
            goto L82
        L66:
            r11 = r1
            goto L8c
        L68:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f7632n
            java.lang.String r1 = "the wait timed out"
            com.huawei.hms.framework.common.Logger.w(r0, r1)
            goto L8c
        L70:
            r0 = move-exception
        L71:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.f7632n
            java.lang.String r4 = "the current thread was interrupted while waiting"
            com.huawei.hms.framework.common.Logger.w(r1, r4, r0)
            goto L8d
        L79:
            r0 = move-exception
        L7a:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.f7632n
            java.lang.String r3 = "the computation threw an ExecutionException"
            com.huawei.hms.framework.common.Logger.w(r1, r3, r0)
            goto L8c
        L82:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f7632n
            java.lang.String r1 = "{requestServer} the computation was cancelled"
            com.huawei.hms.framework.common.Logger.i(r0, r1)
            goto L8d
        L8a:
            r2 = r16
        L8c:
            r3 = r10
        L8d:
            if (r3 == 0) goto L97
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f7632n
            java.lang.String r1 = "needBreak is true so need break current circulation"
            com.huawei.hms.framework.common.Logger.v(r0, r1)
            goto L9b
        L97:
            int r12 = r12 + 1
            goto L5
        L9b:
            com.huawei.hms.framework.network.grs.g.d r0 = r15.b(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.c.a(java.util.concurrent.ExecutorService, java.util.List, java.lang.String, com.huawei.hms.framework.network.grs.e.c):com.huawei.hms.framework.network.grs.g.d");
    }

    private void a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        String grsReqParamJoint = this.f7633a.getGrsReqParamJoint(false, false, e(), this.f7634b);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            sb.append("?");
            sb.append(grsReqParamJoint);
        }
        this.f7641i.add(sb.toString());
    }

    private d b(d dVar) throws ExecutionException, InterruptedException, TimeoutException {
        String str;
        String str2;
        for (Map.Entry<String, Future<d>> entry : this.f7637e.entrySet()) {
            if (dVar != null && (dVar.o() || dVar.m())) {
                break;
            }
            try {
                dVar = entry.getValue().get(40000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e = e2;
                str = f7632n;
                str2 = "{checkResponse} when check result, find InterruptedException, check others";
                Logger.w(str, str2, e);
            } catch (CancellationException unused) {
                Logger.i(f7632n, "{checkResponse} when check result, find CancellationException, check others");
            } catch (ExecutionException e3) {
                e = e3;
                str = f7632n;
                str2 = "{checkResponse} when check result, find ExecutionException, check others";
                Logger.w(str, str2, e);
            } catch (TimeoutException unused2) {
                Logger.w(f7632n, "{checkResponse} when check result, find TimeoutException, cancel current request task");
                if (!entry.getValue().isCancelled()) {
                    entry.getValue().cancel(true);
                }
            }
        }
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public d b(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) throws ExecutionException, InterruptedException, TimeoutException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        d dVarA = a(executorService, this.f7641i, str, cVar);
        int iB = dVarA == null ? 0 : dVarA.b();
        String str2 = f7632n;
        Logger.v(str2, "use 2.0 interface return http's code is：{%s}", Integer.valueOf(iB));
        if (iB == 404 || iB == 401) {
            if (TextUtils.isEmpty(e()) && TextUtils.isEmpty(this.f7633a.getAppName())) {
                Logger.i(str2, "request grs server use 1.0 API must set appName,please check.");
                return null;
            }
            this.f7637e.clear();
            Logger.i(str2, "this env has not deploy new interface,so use old interface.");
            dVarA = a(executorService, this.f7640h, str, cVar);
        }
        e.a(new ArrayList(this.f7638f), SystemClock.elapsedRealtime() - jElapsedRealtime, this.f7639g, this.f7634b);
        this.f7638f.clear();
        return dVarA;
    }

    private void b(String str, String str2) {
        if (TextUtils.isEmpty(this.f7633a.getAppName()) && TextUtils.isEmpty(e())) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[1];
        objArr[0] = TextUtils.isEmpty(e()) ? this.f7633a.getAppName() : e();
        sb.append(String.format(locale, str, objArr));
        String grsReqParamJoint = this.f7633a.getGrsReqParamJoint(false, false, "1.0", this.f7634b);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            sb.append("?");
            sb.append(grsReqParamJoint);
        }
        this.f7640h.add(sb.toString());
    }

    private void c() {
        com.huawei.hms.framework.network.grs.g.k.d dVarA = com.huawei.hms.framework.network.grs.g.j.a.a(this.f7634b);
        if (dVarA == null) {
            Logger.w(f7632n, "g*s***_se****er_conf*** maybe has a big error");
            return;
        }
        a(dVarA);
        List<String> listA = dVarA.a();
        if (listA == null || listA.size() <= 0) {
            Logger.v(f7632n, "maybe grs_base_url config with [],please check.");
            return;
        }
        if (listA.size() > 10) {
            throw new IllegalArgumentException("grs_base_url's count is larger than MAX value 10");
        }
        String strC = dVarA.c();
        String strB = dVarA.b();
        if (listA.size() > 0) {
            for (String str : listA) {
                if (str.startsWith(DefaultWebClient.HTTPS_SCHEME)) {
                    b(strC, str);
                    a(strB, str);
                } else {
                    Logger.w(f7632n, "grs server just support https scheme url,please check.");
                }
            }
        }
        Logger.v(f7632n, "request to GRS server url is{%s} and {%s}", this.f7640h, this.f7641i);
    }

    private void d() {
        String grsParasKey = this.f7633a.getGrsParasKey(true, true, this.f7634b);
        this.f7644l = this.f7635c.a().a(grsParasKey + "ETag", "");
    }

    private String e() {
        com.huawei.hms.framework.network.grs.f.b bVarA = com.huawei.hms.framework.network.grs.f.b.a(this.f7634b.getPackageName(), this.f7633a);
        com.huawei.hms.framework.network.grs.local.model.a aVarA = bVarA != null ? bVarA.a() : null;
        if (aVarA == null) {
            return "";
        }
        String strB = aVarA.b();
        Logger.v(f7632n, "get appName from local assets is{%s}", strB);
        return strB;
    }

    public d a(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) {
        String str2;
        String str3;
        if (this.f7640h.isEmpty() && this.f7641i.isEmpty()) {
            return null;
        }
        try {
            return (d) executorService.submit(new a(executorService, str, cVar)).get(b() != null ? r0.d() : 10, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
            e = e2;
            str2 = f7632n;
            str3 = "{submitExcutorTaskWithTimeout} the current thread was interrupted while waiting";
            Logger.w(str2, str3, e);
            return null;
        } catch (CancellationException unused) {
            Logger.i(f7632n, "{submitExcutorTaskWithTimeout} the computation was cancelled");
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            str2 = f7632n;
            str3 = "{submitExcutorTaskWithTimeout} the computation threw an ExecutionException";
            Logger.w(str2, str3, e);
            return null;
        } catch (TimeoutException unused2) {
            Logger.w(f7632n, "{submitExcutorTaskWithTimeout} the wait timed out");
            return null;
        } catch (Exception e4) {
            e = e4;
            str2 = f7632n;
            str3 = "{submitExcutorTaskWithTimeout} catch Exception";
            Logger.w(str2, str3, e);
            return null;
        }
    }

    public String a() {
        return this.f7644l;
    }

    public synchronized void a(d dVar) {
        this.f7638f.add(dVar);
        d dVar2 = this.f7636d;
        if (dVar2 != null && (dVar2.o() || this.f7636d.m())) {
            Logger.v(f7632n, "grsResponseResult is ok");
            return;
        }
        if (dVar.n()) {
            Logger.i(f7632n, "GRS server open 503 limiting strategy.");
            com.huawei.hms.framework.network.grs.h.d.a(this.f7633a.getGrsParasKey(true, true, this.f7634b), new d.a(dVar.k(), SystemClock.elapsedRealtime()));
            return;
        }
        if (dVar.m()) {
            Logger.i(f7632n, "GRS server open 304 Not Modified.");
        }
        if (!dVar.o() && !dVar.m()) {
            Logger.v(f7632n, "grsResponseResult has exception so need return");
            return;
        }
        this.f7636d = dVar;
        this.f7635c.a(this.f7633a, dVar, this.f7634b, this.f7642j);
        for (Map.Entry<String, Future<d>> entry : this.f7637e.entrySet()) {
            if (!entry.getKey().equals(dVar.l()) && !entry.getValue().isCancelled()) {
                Logger.i(f7632n, "future cancel");
                entry.getValue().cancel(true);
            }
        }
    }

    public void a(com.huawei.hms.framework.network.grs.g.k.d dVar) {
        this.f7643k = dVar;
    }

    public com.huawei.hms.framework.network.grs.g.k.d b() {
        return this.f7643k;
    }
}
