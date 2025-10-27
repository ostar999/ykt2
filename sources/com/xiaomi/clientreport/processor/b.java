package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.push.be;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class b implements IPerfProcessor {

    /* renamed from: a, reason: collision with root package name */
    protected Context f24498a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> f101a;

    public b(Context context) {
        this.f24498a = context;
    }

    public static String a(com.xiaomi.clientreport.data.a aVar) {
        return String.valueOf(aVar.production) + DictionaryFactory.SHARP + aVar.clientInterfaceId;
    }

    private String b(com.xiaomi.clientreport.data.a aVar) {
        String str;
        int i2 = aVar.production;
        String str2 = aVar.clientInterfaceId;
        if (i2 <= 0 || TextUtils.isEmpty(str2)) {
            str = "";
        } else {
            str = String.valueOf(i2) + DictionaryFactory.SHARP + str2;
        }
        File externalFilesDir = this.f24498a.getExternalFilesDir("perf");
        if (externalFilesDir == null) {
            com.xiaomi.channel.commonutils.logger.b.d("cannot get folder when to write perf");
            return null;
        }
        if (!externalFilesDir.exists()) {
            externalFilesDir.mkdirs();
        }
        return new File(externalFilesDir, str).getAbsolutePath();
    }

    private String c(com.xiaomi.clientreport.data.a aVar) {
        String strB = b(aVar);
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        for (int i2 = 0; i2 < 20; i2++) {
            String str = strB + i2;
            if (be.m229a(this.f24498a, str)) {
                return str;
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.c
    public void a() throws Throwable {
        be.a(this.f24498a, "perf", "perfUploading");
        File[] fileArrM230a = be.m230a(this.f24498a, "perfUploading");
        if (fileArrM230a == null || fileArrM230a.length <= 0) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.c(this.f24498a.getPackageName() + "  perfread  paths " + fileArrM230a.length);
        for (File file : fileArrM230a) {
            if (file != null) {
                List<String> listA = e.a(this.f24498a, file.getAbsolutePath());
                file.delete();
                a(listA);
            }
        }
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a */
    public void mo122a(com.xiaomi.clientreport.data.a aVar) {
        if ((aVar instanceof PerfClientReport) && this.f101a != null) {
            PerfClientReport perfClientReport = (PerfClientReport) aVar;
            String strA = a((com.xiaomi.clientreport.data.a) perfClientReport);
            String strA2 = e.a(perfClientReport);
            HashMap<String, com.xiaomi.clientreport.data.a> map = this.f101a.get(strA);
            if (map == null) {
                map = new HashMap<>();
            }
            PerfClientReport perfClientReport2 = (PerfClientReport) map.get(strA2);
            if (perfClientReport2 != null) {
                perfClientReport.perfCounts += perfClientReport2.perfCounts;
                perfClientReport.perfLatencies += perfClientReport2.perfLatencies;
            }
            map.put(strA2, perfClientReport);
            this.f101a.put(strA, map);
            com.xiaomi.channel.commonutils.logger.b.c("pre perf inner " + map.size() + " outer " + this.f101a.size());
        }
    }

    public void a(List<String> list) {
        be.a(this.f24498a, list);
    }

    public void a(com.xiaomi.clientreport.data.a[] aVarArr) throws IOException {
        String strC = c(aVarArr[0]);
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        e.a(strC, aVarArr);
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() throws IOException {
        HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> map = this.f101a;
        if (map == null) {
            return;
        }
        if (map.size() > 0) {
            Iterator<String> it = this.f101a.keySet().iterator();
            while (it.hasNext()) {
                HashMap<String, com.xiaomi.clientreport.data.a> map2 = this.f101a.get(it.next());
                if (map2 != null && map2.size() > 0) {
                    com.xiaomi.channel.commonutils.logger.b.c("begin write perfJob " + map2.size());
                    com.xiaomi.clientreport.data.a[] aVarArr = new com.xiaomi.clientreport.data.a[map2.size()];
                    map2.values().toArray(aVarArr);
                    a(aVarArr);
                }
            }
        }
        this.f101a.clear();
    }

    @Override // com.xiaomi.clientreport.processor.IPerfProcessor
    public void setPerfMap(HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> map) {
        this.f101a = map;
    }
}
