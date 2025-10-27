package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.manager.ClientReportClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class fk {

    /* renamed from: a, reason: collision with root package name */
    private static a f24851a;

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, in> f420a;

    public interface a {
        void a(Context context, ib ibVar);
    }

    public static int a(int i2) {
        if (i2 > 0) {
            return i2 + 1000;
        }
        return -1;
    }

    public static int a(Enum r12) {
        if (r12 != null) {
            if (r12 instanceof hw) {
                return r12.ordinal() + 1001;
            }
            if (r12 instanceof in) {
                return r12.ordinal() + 2001;
            }
            if (r12 instanceof fq) {
                return r12.ordinal() + 3001;
            }
        }
        return -1;
    }

    public static Config a(Context context) {
        boolean zA = com.xiaomi.push.service.ao.a(context).a(ic.PerfUploadSwitch.a(), false);
        boolean zA2 = com.xiaomi.push.service.ao.a(context).a(ic.EventUploadSwitch.a(), false);
        return Config.getBuilder().setEventUploadSwitchOpen(zA2).setEventUploadFrequency(com.xiaomi.push.service.ao.a(context).a(ic.EventUploadFrequency.a(), 86400)).setPerfUploadSwitchOpen(zA).setPerfUploadFrequency(com.xiaomi.push.service.ao.a(context).a(ic.PerfUploadFrequency.a(), 86400)).build(context);
    }

    public static EventClientReport a(Context context, String str, String str2, int i2, long j2, String str3) {
        EventClientReport eventClientReportA = a(str);
        eventClientReportA.eventId = str2;
        eventClientReportA.eventType = i2;
        eventClientReportA.eventTime = j2;
        eventClientReportA.eventContent = str3;
        return eventClientReportA;
    }

    public static EventClientReport a(String str) {
        EventClientReport eventClientReport = new EventClientReport();
        eventClientReport.production = 1000;
        eventClientReport.reportType = 1001;
        eventClientReport.clientInterfaceId = str;
        return eventClientReport;
    }

    public static PerfClientReport a() {
        PerfClientReport perfClientReport = new PerfClientReport();
        perfClientReport.production = 1000;
        perfClientReport.reportType = 1000;
        perfClientReport.clientInterfaceId = "P100000";
        return perfClientReport;
    }

    public static PerfClientReport a(Context context, int i2, long j2, long j3) {
        PerfClientReport perfClientReportA = a();
        perfClientReportA.code = i2;
        perfClientReportA.perfCounts = j2;
        perfClientReportA.perfLatencies = j3;
        return perfClientReportA;
    }

    public static ib a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ib ibVar = new ib();
        ibVar.d("category_client_report_data");
        ibVar.a("push_sdk_channel");
        ibVar.a(1L);
        ibVar.b(str);
        ibVar.a(true);
        ibVar.b(System.currentTimeMillis());
        ibVar.g(context.getPackageName());
        ibVar.e("com.xiaomi.xmsf");
        ibVar.f(com.xiaomi.push.service.bl.a());
        ibVar.c("quality_support");
        return ibVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static in m417a(String str) {
        if (f420a == null) {
            synchronized (in.class) {
                if (f420a == null) {
                    f420a = new HashMap();
                    for (in inVar : in.values()) {
                        f420a.put(inVar.f622a.toLowerCase(), inVar);
                    }
                }
            }
        }
        in inVar2 = f420a.get(str.toLowerCase());
        return inVar2 != null ? inVar2 : in.Invalid;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m418a(int i2) {
        return i2 == 1000 ? "E100000" : i2 == 3000 ? "E100002" : i2 == 2000 ? "E100001" : "";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m419a(Context context) {
        ClientReportClient.updateConfig(context, a(context));
    }

    public static void a(Context context, Config config) {
        ClientReportClient.init(context, config, new fi(context), new fj(context));
    }

    private static void a(Context context, ib ibVar) {
        if (m420a(context.getApplicationContext())) {
            com.xiaomi.push.service.bm.a(context.getApplicationContext(), ibVar);
            return;
        }
        a aVar = f24851a;
        if (aVar != null) {
            aVar.a(context, ibVar);
        }
    }

    public static void a(Context context, List<String> list) {
        if (list == null) {
            return;
        }
        try {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                ib ibVarA = a(context, it.next());
                if (com.xiaomi.push.service.bl.a(ibVarA, false)) {
                    com.xiaomi.channel.commonutils.logger.b.c(ibVarA.d() + "is not valid...");
                } else {
                    com.xiaomi.channel.commonutils.logger.b.c("send event/perf data item id:" + ibVarA.d());
                    a(context, ibVarA);
                }
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.d(th.getMessage());
        }
    }

    public static void a(a aVar) {
        f24851a = aVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m420a(Context context) {
        return (context == null || TextUtils.isEmpty(context.getPackageName()) || !"com.xiaomi.xmsf".equals(context.getPackageName())) ? false : true;
    }
}
