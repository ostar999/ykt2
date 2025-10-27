package com.alipay.security.mobile.module.http.model;

import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class b {
    private static DataReportRequest a(d dVar) {
        DataReportRequest dataReportRequest = new DataReportRequest();
        if (dVar == null) {
            return null;
        }
        dataReportRequest.os = com.alipay.security.mobile.module.a.a.d(dVar.f3459a);
        dataReportRequest.rpcVersion = dVar.f3468j;
        dataReportRequest.bizType = "1";
        HashMap map = new HashMap();
        dataReportRequest.bizData = map;
        map.put("apdid", com.alipay.security.mobile.module.a.a.d(dVar.f3460b));
        dataReportRequest.bizData.put("apdidToken", com.alipay.security.mobile.module.a.a.d(dVar.f3461c));
        dataReportRequest.bizData.put("umidToken", com.alipay.security.mobile.module.a.a.d(dVar.f3462d));
        dataReportRequest.bizData.put("dynamicKey", dVar.f3463e);
        Map<String, String> map2 = dVar.f3464f;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        dataReportRequest.deviceData = map2;
        return dataReportRequest;
    }

    public static c a(DataReportResult dataReportResult) {
        c cVar = new c();
        if (dataReportResult == null) {
            return null;
        }
        cVar.f3443a = dataReportResult.success;
        cVar.f3444b = dataReportResult.resultCode;
        Map<String, String> map = dataReportResult.resultData;
        if (map != null) {
            cVar.f3450h = map.get("apdid");
            cVar.f3451i = map.get("apdidToken");
            cVar.f3454l = map.get("dynamicKey");
            cVar.f3455m = map.get("timeInterval");
            cVar.f3456n = map.get("webrtcUrl");
            cVar.f3457o = "";
            String str = map.get("drmSwitch");
            if (com.alipay.security.mobile.module.a.a.b(str)) {
                if (str.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str.charAt(0));
                    cVar.f3452j = sb.toString();
                }
                if (str.length() >= 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str.charAt(2));
                    cVar.f3453k = sb2.toString();
                }
            }
            if (map.containsKey("apse_degrade")) {
                cVar.f3458p = map.get("apse_degrade");
            }
        }
        return cVar;
    }
}
