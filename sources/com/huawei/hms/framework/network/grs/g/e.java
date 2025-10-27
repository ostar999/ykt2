package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.framework.common.hianalytics.HianalyticsHelper;
import com.huawei.hms.framework.common.hianalytics.LinkedHashMapPack;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ long f7665a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ArrayList f7666b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ JSONArray f7667c;

        public a(long j2, ArrayList arrayList, JSONArray jSONArray) {
            this.f7665a = j2;
            this.f7666b = arrayList;
            this.f7667c = jSONArray;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z2;
            com.huawei.hms.framework.network.grs.g.k.a aVar = new com.huawei.hms.framework.network.grs.g.k.a();
            aVar.put("total_time", this.f7665a);
            Iterator it = this.f7666b.iterator();
            while (it.hasNext()) {
                d dVar = (d) it.next();
                if (dVar.o() || dVar.m()) {
                    aVar.put(e.b(dVar));
                    it.remove();
                    z2 = true;
                    break;
                }
            }
            z2 = false;
            if (!z2 && this.f7666b.size() > 0) {
                ArrayList arrayList = this.f7666b;
                d dVar2 = (d) arrayList.get(arrayList.size() - 1);
                aVar.put(e.b(dVar2));
                this.f7666b.remove(dVar2);
            }
            if (this.f7666b.size() > 0) {
                Iterator it2 = this.f7666b.iterator();
                while (it2.hasNext()) {
                    this.f7667c.put(new JSONObject(e.b((d) it2.next())));
                }
            }
            if (this.f7667c.length() > 0) {
                aVar.put("failed_info", this.f7667c.toString());
            }
            Logger.d("HaReportHelper", "grssdk report data to aiops is: %s", new JSONObject(aVar.get()));
            HianalyticsHelper.getInstance().onEvent(aVar.get(), "grs_request");
        }
    }

    public static void a(ArrayList<d> arrayList, long j2, JSONArray jSONArray, Context context) {
        if (context == null || arrayList == null || arrayList.size() <= 0 || !HianalyticsHelper.getInstance().isEnableReportNoSeed(context)) {
            return;
        }
        HianalyticsHelper.getInstance().getReportExecutor().submit(new a(j2, arrayList, jSONArray));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedHashMap<String, String> b(d dVar) {
        LinkedHashMapPack linkedHashMapPack = new LinkedHashMapPack();
        Exception excD = dVar.d();
        if (excD != null) {
            linkedHashMapPack.put("error_code", ExceptionCode.getErrorCodeFromException(excD));
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, excD.getClass().getSimpleName());
            linkedHashMapPack.put("message", StringUtils.anonymizeMessage(excD.getMessage()));
        } else {
            linkedHashMapPack.put("error_code", dVar.b());
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, dVar.c());
        }
        try {
            linkedHashMapPack.put(ClientCookie.DOMAIN_ATTR, new URL(dVar.l()).getHost());
        } catch (MalformedURLException e2) {
            Logger.w("HaReportHelper", "report host MalformedURLException", e2);
        }
        linkedHashMapPack.put("req_start_time", dVar.h());
        linkedHashMapPack.put("req_end_time", dVar.g());
        linkedHashMapPack.put("req_total_time", dVar.i());
        return linkedHashMapPack.getAll();
    }
}
