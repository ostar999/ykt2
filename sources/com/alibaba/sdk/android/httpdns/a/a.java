package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import com.alibaba.sdk.android.beacon.Beacon;
import com.alibaba.sdk.android.httpdns.d.d;
import com.alibaba.sdk.android.httpdns.f.b;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import com.plv.livescenes.upload.PLVDocumentUploadConstant;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {
    public static void a(Context context, final String str, final d dVar) {
        if (context == null || str == null || str.isEmpty() || dVar == null) {
            HttpDnsLog.w("params is empty");
            return;
        }
        HashMap map = new HashMap();
        map.put("sdkId", "httpdns");
        map.put("sdkVer", "2.0.4");
        map.put("accountId", str);
        Beacon beaconBuild = new Beacon.Builder().defaultConfig().startPoll(false).extras(map).build();
        beaconBuild.addUpdateListener(new Beacon.OnUpdateListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.1
            @Override // com.alibaba.sdk.android.beacon.Beacon.OnUpdateListener
            public void onUpdate(List<Beacon.Config> list) {
                if (list == null || list.size() <= 0) {
                    return;
                }
                for (Beacon.Config config : list) {
                    if (config.key.equals("___httpdns_service___")) {
                        try {
                            JSONObject jSONObject = new JSONObject(config.value);
                            if (jSONObject.optString("status", PLVDocumentUploadConstant.ConvertStatus.NORMAL).equals("disabled")) {
                                dVar.c(true);
                                HttpDnsLog.w("beacon disabled");
                            } else {
                                dVar.c(false);
                                HttpDnsLog.d("beacon normal");
                            }
                            if (jSONObject.optString("ut", PLVDocumentUploadConstant.ConvertStatus.NORMAL).equals("disabled")) {
                                b.a(str, false);
                            } else {
                                b.a(str, true);
                            }
                            if (jSONObject.optString("ip-ranking", PLVDocumentUploadConstant.ConvertStatus.NORMAL).equals("disabled")) {
                                dVar.d(true);
                                HttpDnsLog.w("beacon probe disabled");
                            } else {
                                dVar.d(false);
                                HttpDnsLog.d("beacon probe normal");
                            }
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        });
        beaconBuild.addServiceErrListener(new Beacon.OnServiceErrListener() { // from class: com.alibaba.sdk.android.httpdns.a.a.2
            @Override // com.alibaba.sdk.android.beacon.Beacon.OnServiceErrListener
            public void onErr(Beacon.Error error) {
                HttpDnsLog.w("beacon err " + error.errCode + " " + error.errMsg);
            }
        });
        beaconBuild.start(context);
    }
}
