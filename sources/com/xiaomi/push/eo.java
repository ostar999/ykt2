package com.xiaomi.push;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes6.dex */
public class eo extends ei {

    /* renamed from: a, reason: collision with root package name */
    private Comparator<ScanResult> f24767a;

    public eo(Context context, int i2) {
        super(context, i2);
        this.f24767a = new ep(this);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 8;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.WIFI;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        WifiInfo connectionInfo;
        StringBuilder sb = new StringBuilder();
        try {
            WifiManager wifiManager = (WifiManager) ((ei) this).f339a.getSystemService("wifi");
            if (wifiManager.isWifiEnabled() && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                sb.append(connectionInfo.getSSID());
                sb.append(",");
                sb.append(ay.c(connectionInfo.getBSSID()));
                sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
            }
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (!ad.a(scanResults)) {
                Collections.sort(scanResults, this.f24767a);
                for (int i2 = 0; i2 < Math.min(10, scanResults.size()); i2++) {
                    ScanResult scanResult = scanResults.get(i2);
                    if (i2 > 0) {
                        sb.append(com.alipay.sdk.util.h.f3376b);
                    }
                    if (scanResult != null) {
                        sb.append(scanResult.SSID);
                        sb.append(",");
                        sb.append(ay.c(scanResult.BSSID));
                        sb.append(",");
                        sb.append(scanResult.level);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }
}
