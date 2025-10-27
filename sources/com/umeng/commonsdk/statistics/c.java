package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.bn;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMFrUtils;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.internal.d;
import com.umeng.commonsdk.statistics.noise.ABTest;
import com.umeng.commonsdk.statistics.noise.Defcon;
import com.umeng.commonsdk.statistics.proto.Response;
import java.io.File;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    private static final int f23299b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static final int f23300c = 2;

    /* renamed from: d, reason: collision with root package name */
    private static final int f23301d = 3;

    /* renamed from: o, reason: collision with root package name */
    private static final String f23302o = "thtstart";

    /* renamed from: p, reason: collision with root package name */
    private static final String f23303p = "gkvc";

    /* renamed from: q, reason: collision with root package name */
    private static final String f23304q = "ekvc";

    /* renamed from: a, reason: collision with root package name */
    String f23305a;

    /* renamed from: f, reason: collision with root package name */
    private com.umeng.commonsdk.statistics.internal.c f23307f;

    /* renamed from: g, reason: collision with root package name */
    private ImprintHandler f23308g;

    /* renamed from: h, reason: collision with root package name */
    private e f23309h;

    /* renamed from: i, reason: collision with root package name */
    private ImprintHandler.a f23310i;

    /* renamed from: k, reason: collision with root package name */
    private Defcon f23312k;

    /* renamed from: l, reason: collision with root package name */
    private long f23313l;

    /* renamed from: m, reason: collision with root package name */
    private int f23314m;

    /* renamed from: n, reason: collision with root package name */
    private int f23315n;

    /* renamed from: r, reason: collision with root package name */
    private Context f23316r;

    /* renamed from: e, reason: collision with root package name */
    private final int f23306e = 1;

    /* renamed from: j, reason: collision with root package name */
    private ABTest f23311j = null;

    public c(Context context) {
        this.f23309h = null;
        this.f23310i = null;
        this.f23312k = null;
        this.f23313l = 0L;
        this.f23314m = 0;
        this.f23315n = 0;
        this.f23305a = null;
        this.f23316r = context;
        this.f23310i = ImprintHandler.getImprintService(context).c();
        this.f23312k = Defcon.getService(this.f23316r);
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(this.f23316r);
        this.f23313l = sharedPreferences.getLong(f23302o, 0L);
        this.f23314m = sharedPreferences.getInt(f23303p, 0);
        this.f23315n = sharedPreferences.getInt(f23304q, 0);
        this.f23305a = UMEnvelopeBuild.imprintProperty(this.f23316r, "track_list", null);
        ImprintHandler imprintService = ImprintHandler.getImprintService(this.f23316r);
        this.f23308g = imprintService;
        imprintService.a(new d() { // from class: com.umeng.commonsdk.statistics.c.1
            @Override // com.umeng.commonsdk.statistics.internal.d
            public void onImprintChanged(ImprintHandler.a aVar) {
                c.this.f23312k.onImprintChanged(aVar);
                c cVar = c.this;
                cVar.f23305a = UMEnvelopeBuild.imprintProperty(cVar.f23316r, "track_list", null);
            }
        });
        if (!UMConfigure.needSendZcfgEnv(this.f23316r)) {
            this.f23309h = e.a(this.f23316r);
        }
        com.umeng.commonsdk.statistics.internal.c cVar = new com.umeng.commonsdk.statistics.internal.c(this.f23316r);
        this.f23307f = cVar;
        cVar.a(StatTracer.getInstance(this.f23316r));
    }

    public boolean a(File file) {
        if (file == null) {
            return false;
        }
        try {
            byte[] byteArray = UMFrUtils.toByteArray(file.getPath());
            if (byteArray == null) {
                return false;
            }
            String name = file.getName();
            if (TextUtils.isEmpty(name)) {
                return false;
            }
            com.umeng.commonsdk.statistics.internal.a aVarA = com.umeng.commonsdk.statistics.internal.a.a(this.f23316r);
            aVarA.d(name);
            boolean zA = aVarA.a(name);
            boolean zB = aVarA.b(name);
            boolean zC = aVarA.c(name);
            String strD = com.umeng.commonsdk.stateless.d.d(name);
            byte[] bArrA = this.f23307f.a(byteArray, zA, zC, !TextUtils.isEmpty(strD) ? com.umeng.commonsdk.stateless.d.c(strD) : zC ? UMServerURL.ZCFG_PATH : UMServerURL.PATH_ANALYTICS);
            int iA = bArrA == null ? 1 : a(bArrA);
            if (UMConfigure.isDebugLog()) {
                if (zC && iA == 2) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Zero req: succeed.");
                } else if (zB && iA == 2) {
                    MLog.d("本次启动数据: 发送成功!");
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Send instant data: succeed.");
                } else if (zA && iA == 2) {
                    MLog.d("普通统计数据: 发送成功!");
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Send analytics data: succeed.");
                } else {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "Inner req: succeed.");
                }
            }
            if (iA == 2) {
                e eVar = this.f23309h;
                if (eVar != null) {
                    eVar.e();
                }
                StatTracer.getInstance(this.f23316r).saveSate();
            } else if (iA == 3) {
                StatTracer.getInstance(this.f23316r).saveSate();
                if (zC) {
                    FieldManager.a().a(this.f23316r);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 零号报文应答内容报错!!! ，特殊处理!，继续正常流程。");
                    Context context = this.f23316r;
                    UMWorkDispatch.sendEvent(context, 32784, com.umeng.commonsdk.internal.b.a(context).a(), null);
                    return true;
                }
            }
            return iA == 2;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(this.f23316r, th);
            return false;
        }
    }

    private int a(byte[] bArr) {
        Response response = new Response();
        try {
            new ay(new bn.a()).a(response, bArr);
            if (response.resp_code == 1) {
                this.f23308g.b(response.getImprint());
                this.f23308g.d();
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(this.f23316r, th);
        }
        return response.resp_code == 1 ? 2 : 3;
    }
}
