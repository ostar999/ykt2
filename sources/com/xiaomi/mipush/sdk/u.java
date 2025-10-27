package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;
import com.xiaomi.push.ai;
import com.xiaomi.push.hw;
import com.xiaomi.push.hy;
import com.xiaomi.push.ic;
import com.xiaomi.push.ih;
import com.xiaomi.push.ik;
import com.xiaomi.push.il;
import com.xiaomi.push.in;
import com.xiaomi.push.iq;
import com.xiaomi.push.iu;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public class u extends ai.a {

    /* renamed from: a, reason: collision with other field name */
    private Context f165a;

    /* renamed from: c, reason: collision with root package name */
    private int f24581c;

    /* renamed from: a, reason: collision with root package name */
    private final int f24579a = -1;

    /* renamed from: b, reason: collision with root package name */
    private final int f24580b = 3600;

    public u(Context context, int i2) {
        this.f165a = context;
        this.f24581c = i2;
    }

    private static Location a(Context context) {
        Location lastKnownLocation;
        Location lastKnownLocation2;
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        Location lastKnownLocation3 = null;
        try {
            lastKnownLocation = locationManager.getLastKnownLocation("network");
        } catch (Exception unused) {
            lastKnownLocation = null;
        }
        try {
            lastKnownLocation2 = locationManager.getLastKnownLocation("gps");
        } catch (Exception unused2) {
            lastKnownLocation2 = null;
        }
        try {
            lastKnownLocation3 = locationManager.getLastKnownLocation("passive");
        } catch (Exception unused3) {
        }
        return a(lastKnownLocation3, a(lastKnownLocation, lastKnownLocation2));
    }

    private static Location a(Location location, Location location2) {
        return location == null ? location2 : (location2 != null && location.getTime() <= location2.getTime()) ? location2 : location;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static ih m180a(Context context) {
        Location locationA;
        if (!m184a(context) || (locationA = a(context)) == null) {
            return null;
        }
        ik ikVar = new ik();
        ikVar.b(locationA.getLatitude());
        ikVar.a(locationA.getLongitude());
        ih ihVar = new ih();
        ihVar.a(locationA.getAccuracy());
        ihVar.a(ikVar);
        ihVar.a(locationA.getProvider());
        ihVar.a(new Date().getTime() - locationA.getTime());
        return ihVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static il m181a(Context context) {
        il ilVar = new il();
        if (com.xiaomi.push.n.d()) {
            return ilVar;
        }
        ilVar.a(m182a(context));
        ilVar.b(b(context));
        ilVar.a(m180a(context));
        return ilVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static List<iu> m182a(Context context) {
        v vVar = new v();
        try {
            List<ScanResult> scanResults = ((WifiManager) context.getSystemService("wifi")).getScanResults();
            if (com.xiaomi.push.ad.a(scanResults)) {
                return null;
            }
            Collections.sort(scanResults, vVar);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < Math.min(30, scanResults.size()); i2++) {
                ScanResult scanResult = scanResults.get(i2);
                if (scanResult != null) {
                    iu iuVar = new iu();
                    iuVar.a(TextUtils.isEmpty(scanResult.BSSID) ? "" : scanResult.BSSID);
                    iuVar.a(scanResult.level);
                    iuVar.b(scanResult.SSID);
                    arrayList.add(iuVar);
                }
            }
            return arrayList;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m183a(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 4).edit();
        editorEdit.putLong("last_upload_lbs_data_timestamp", System.currentTimeMillis() / 1000);
        editorEdit.commit();
    }

    public static void a(Context context, boolean z2) {
        il ilVarM181a = m181a(context);
        byte[] bArrA = jp.a(ilVarM181a);
        je jeVar = new je("-1", false);
        jeVar.c(in.GeoUpdateLoc.f622a);
        jeVar.a(bArrA);
        jeVar.a(new HashMap());
        jeVar.m610a().put(Constants.EXTRA_KEY_INITIAL_WIFI_UPLOAD, String.valueOf(z2));
        boolean zB = com.xiaomi.push.service.j.b(context);
        if (zB) {
            jeVar.m610a().put(Constants.EXTRA_KEY_XMSF_GEO_IS_WORK, String.valueOf(zB));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("reportLocInfo locInfo timestamp:");
        sb.append(System.currentTimeMillis());
        sb.append(",");
        sb.append(String.valueOf(ilVarM181a.a() != null ? ilVarM181a.a() : "null"));
        sb.append(",");
        List<hy> list = ilVarM181a.f615b;
        sb.append(String.valueOf(list != null ? list.toString() : null));
        sb.append(",");
        List<iu> list2 = ilVarM181a.f614a;
        sb.append(String.valueOf(list2 != null ? list2.toString() : null));
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        az.a(context).a((az) jeVar, hw.Notification, true, (iq) null);
        m183a(context);
    }

    private boolean a() {
        if (com.xiaomi.push.as.d(this.f165a)) {
            return true;
        }
        return com.xiaomi.push.as.e(this.f165a) && a((long) Math.max(60, com.xiaomi.push.service.ao.a(this.f165a).a(ic.UploadNOWIFIGeoLocFrequency.a(), 3600)));
    }

    private boolean a(long j2) {
        return ((float) Math.abs((System.currentTimeMillis() / 1000) - this.f165a.getSharedPreferences("mipush_extra", 4).getLong("last_upload_lbs_data_timestamp", -1L))) > ((float) j2) * 0.9f;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m184a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        return (packageManager.checkPermission(Permission.ACCESS_COARSE_LOCATION, packageName) == 0) || (packageManager.checkPermission(Permission.ACCESS_FINE_LOCATION, packageName) == 0);
    }

    private static List<hy> b(Context context) {
        try {
            List neighboringCellInfo = ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getNeighboringCellInfo();
            int i2 = 0;
            ArrayList arrayList = null;
            while (i2 < neighboringCellInfo.size()) {
                NeighboringCellInfo neighboringCellInfo2 = (NeighboringCellInfo) neighboringCellInfo.get(i2);
                ArrayList arrayList2 = new ArrayList();
                if (neighboringCellInfo2.getLac() > 0 || neighboringCellInfo2.getCid() > 0) {
                    hy hyVar = new hy();
                    hyVar.a(neighboringCellInfo2.getCid());
                    hyVar.b((neighboringCellInfo2.getRssi() * 2) - 113);
                    arrayList2.add(hyVar);
                }
                i2++;
                arrayList = arrayList2;
            }
            return arrayList;
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a, reason: collision with other method in class */
    public int mo185a() {
        return 11;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (com.xiaomi.push.service.j.e(this.f165a) && com.xiaomi.push.service.ao.a(this.f165a).a(ic.UploadGeoAppLocSwitch.a(), true) && com.xiaomi.push.as.c(this.f165a) && a() && com.xiaomi.push.ag.a(this.f165a, String.valueOf(11), this.f24581c)) {
            a(this.f165a, false);
        }
    }
}
