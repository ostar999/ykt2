package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.framework.common.ExecutorsUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.framework.network.grs.g.h;
import com.huawei.hms.framework.network.grs.g.i;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: i, reason: collision with root package name */
    private static final String f7579i = "c";

    /* renamed from: j, reason: collision with root package name */
    private static final ExecutorService f7580j = ExecutorsUtils.newSingleThreadExecutor("GRS_GrsClient-Init");

    /* renamed from: k, reason: collision with root package name */
    private static AtomicInteger f7581k = new AtomicInteger(0);

    /* renamed from: a, reason: collision with root package name */
    private GrsBaseInfo f7582a;

    /* renamed from: b, reason: collision with root package name */
    private Context f7583b;

    /* renamed from: c, reason: collision with root package name */
    private h f7584c;

    /* renamed from: d, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f7585d;

    /* renamed from: e, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f7586e;

    /* renamed from: f, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f7587f;

    /* renamed from: g, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.a f7588g;

    /* renamed from: h, reason: collision with root package name */
    private FutureTask<Boolean> f7589h;

    public class a implements Callable<Boolean> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f7590a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ GrsBaseInfo f7591b;

        public a(Context context, GrsBaseInfo grsBaseInfo) {
            this.f7590a = context;
            this.f7591b = grsBaseInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Boolean call() throws NumberFormatException {
            c.this.f7584c = new h();
            c.this.f7586e = new com.huawei.hms.framework.network.grs.e.c(this.f7590a, GrsApp.getInstance().getBrand(StrPool.UNDERLINE) + "share_pre_grs_conf_");
            c.this.f7587f = new com.huawei.hms.framework.network.grs.e.c(this.f7590a, GrsApp.getInstance().getBrand(StrPool.UNDERLINE) + "share_pre_grs_services_");
            c cVar = c.this;
            cVar.f7585d = new com.huawei.hms.framework.network.grs.e.a(cVar.f7586e, c.this.f7587f, c.this.f7584c);
            c cVar2 = c.this;
            cVar2.f7588g = new com.huawei.hms.framework.network.grs.a(cVar2.f7582a, c.this.f7585d, c.this.f7584c, c.this.f7587f);
            if (c.f7581k.incrementAndGet() <= 2 || com.huawei.hms.framework.network.grs.f.b.a(this.f7590a.getPackageName(), c.this.f7582a) == null) {
                new com.huawei.hms.framework.network.grs.f.b(this.f7590a, this.f7591b, true).a(this.f7591b);
            }
            String strC = new com.huawei.hms.framework.network.grs.g.k.c(this.f7591b, this.f7590a).c();
            Logger.v(c.f7579i, "scan serviceSet is:" + strC);
            String strA = c.this.f7587f.a("services", "");
            String strA2 = i.a(strA, strC);
            if (!TextUtils.isEmpty(strA2)) {
                c.this.f7587f.b("services", strA2);
                Logger.i(c.f7579i, "postList is:" + StringUtils.anonymizeMessage(strA2));
                Logger.i(c.f7579i, "currentServices:" + StringUtils.anonymizeMessage(strA));
                if (!strA2.equals(strA)) {
                    c.this.f7584c.a(c.this.f7582a.getGrsParasKey(true, true, this.f7590a));
                    c.this.f7584c.a(new com.huawei.hms.framework.network.grs.g.k.c(this.f7591b, this.f7590a), (String) null, c.this.f7587f);
                }
            }
            c cVar3 = c.this;
            cVar3.a(cVar3.f7586e.a());
            c.this.f7585d.b(this.f7591b, this.f7590a);
            return Boolean.TRUE;
        }
    }

    public c(Context context, GrsBaseInfo grsBaseInfo) {
        this.f7589h = null;
        this.f7583b = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        a(grsBaseInfo);
        GrsBaseInfo grsBaseInfo2 = this.f7582a;
        FutureTask<Boolean> futureTask = new FutureTask<>(new a(this.f7583b, grsBaseInfo2));
        this.f7589h = futureTask;
        f7580j.execute(futureTask);
        Logger.i(f7579i, "GrsClient Instance is init, GRS SDK version: %s, GrsBaseInfoParam: app_name=%s, reg_country=%s, ser_country=%s, issue_country=%s", com.huawei.hms.framework.network.grs.h.a.a(), grsBaseInfo2.getAppName(), grsBaseInfo.getRegCountry(), grsBaseInfo.getSerCountry(), grsBaseInfo.getIssueCountry());
    }

    public c(GrsBaseInfo grsBaseInfo) {
        this.f7589h = null;
        a(grsBaseInfo);
    }

    private void a(GrsBaseInfo grsBaseInfo) {
        try {
            this.f7582a = grsBaseInfo.m96clone();
        } catch (CloneNotSupportedException e2) {
            Logger.w(f7579i, "GrsClient catch CloneNotSupportedException", e2);
            this.f7582a = grsBaseInfo.copy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map<String, ?> map) throws NumberFormatException {
        if (map == null || map.isEmpty()) {
            Logger.v(f7579i, "sp's content is empty.");
            return;
        }
        for (String str : map.keySet()) {
            if (str.endsWith(CrashHianalyticsData.TIME)) {
                String strA = this.f7586e.a(str, "");
                long j2 = 0;
                if (!TextUtils.isEmpty(strA) && strA.matches(RegexPool.NUMBERS)) {
                    try {
                        j2 = Long.parseLong(strA);
                    } catch (NumberFormatException e2) {
                        Logger.w(f7579i, "convert expire time from String to Long catch NumberFormatException.", e2);
                    }
                }
                if (!a(j2)) {
                    Logger.i(f7579i, "init interface auto clear some invalid sp's data.");
                    String strSubstring = str.substring(0, str.length() - 4);
                    this.f7586e.a(strSubstring);
                    this.f7586e.a(str);
                    this.f7586e.a(strSubstring + "ETag");
                }
            }
        }
    }

    private boolean a(long j2) {
        return System.currentTimeMillis() - j2 <= 604800000;
    }

    private boolean e() {
        String str;
        String str2;
        FutureTask<Boolean> futureTask = this.f7589h;
        if (futureTask == null) {
            return false;
        }
        try {
            return futureTask.get(8L, TimeUnit.SECONDS).booleanValue();
        } catch (InterruptedException e2) {
            e = e2;
            str = f7579i;
            str2 = "init compute task interrupted.";
            Logger.w(str, str2, e);
            return false;
        } catch (CancellationException unused) {
            Logger.i(f7579i, "init compute task canceled.");
            return false;
        } catch (ExecutionException e3) {
            e = e3;
            str = f7579i;
            str2 = "init compute task failed.";
            Logger.w(str, str2, e);
            return false;
        } catch (TimeoutException unused2) {
            Logger.w(f7579i, "init compute task timed out");
            return false;
        } catch (Exception e4) {
            e = e4;
            str = f7579i;
            str2 = "init compute task occur unknown Exception";
            Logger.w(str, str2, e);
            return false;
        }
    }

    public String a(String str, String str2) {
        if (this.f7582a == null || str == null || str2 == null) {
            Logger.w(f7579i, "invalid para!");
            return null;
        }
        if (e()) {
            return this.f7588g.a(str, str2, this.f7583b);
        }
        return null;
    }

    public Map<String, String> a(String str) {
        if (this.f7582a != null && str != null) {
            return e() ? this.f7588g.a(str, this.f7583b) : new HashMap();
        }
        Logger.w(f7579i, "invalid para!");
        return new HashMap();
    }

    public void a() {
        if (e()) {
            String grsParasKey = this.f7582a.getGrsParasKey(true, true, this.f7583b);
            this.f7586e.a(grsParasKey);
            this.f7586e.a(grsParasKey + CrashHianalyticsData.TIME);
            this.f7586e.a(grsParasKey + "ETag");
            this.f7584c.a(grsParasKey);
        }
    }

    public void a(String str, IQueryUrlsCallBack iQueryUrlsCallBack) {
        if (iQueryUrlsCallBack == null) {
            Logger.w(f7579i, "IQueryUrlsCallBack is must not null for process continue.");
            return;
        }
        if (this.f7582a == null || str == null) {
            iQueryUrlsCallBack.onCallBackFail(-6);
        } else if (e()) {
            this.f7588g.a(str, iQueryUrlsCallBack, this.f7583b);
        } else {
            Logger.i(f7579i, "grs init task has not completed.");
            iQueryUrlsCallBack.onCallBackFail(-7);
        }
    }

    public void a(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack) {
        if (iQueryUrlCallBack == null) {
            Logger.w(f7579i, "IQueryUrlCallBack is must not null for process continue.");
            return;
        }
        if (this.f7582a == null || str == null || str2 == null) {
            iQueryUrlCallBack.onCallBackFail(-6);
        } else if (e()) {
            this.f7588g.a(str, str2, iQueryUrlCallBack, this.f7583b);
        } else {
            Logger.i(f7579i, "grs init task has not completed.");
            iQueryUrlCallBack.onCallBackFail(-7);
        }
    }

    public boolean a(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && c.class == obj.getClass() && (obj instanceof c)) {
            return this.f7582a.compare(((c) obj).f7582a);
        }
        return false;
    }

    public boolean b() {
        GrsBaseInfo grsBaseInfo;
        Context context;
        if (!e() || (grsBaseInfo = this.f7582a) == null || (context = this.f7583b) == null) {
            return false;
        }
        this.f7585d.a(grsBaseInfo, context);
        return true;
    }
}
