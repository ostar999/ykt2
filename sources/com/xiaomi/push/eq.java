package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.yikaobang.yixue.R2;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class eq extends ei {

    /* renamed from: a, reason: collision with root package name */
    private SharedPreferences f24769a;

    /* renamed from: a, reason: collision with other field name */
    private ca f342a;

    /* renamed from: a, reason: collision with other field name */
    private final Object f343a;

    /* renamed from: a, reason: collision with other field name */
    private String f344a;

    public eq(Context context, int i2) {
        super(context, i2);
        this.f343a = new Object();
        this.f342a = new er(this);
        a(context);
    }

    private void a(Context context) {
        bk.a(context).b();
        bk.a().a(this.f342a, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str) {
        try {
            String string = new JSONObject(str).getJSONArray("devices").toString();
            String strSubstring = string.substring(1, string.length() - 1);
            return !TextUtils.isEmpty(strSubstring) ? ay.c(strSubstring) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 14;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.WifiDevicesMac;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        if (as.d(((ei) this).f339a)) {
            bk.a().m237a();
            synchronized (this.f343a) {
                try {
                    this.f343a.wait(com.heytap.mcssdk.constant.a.f7153q);
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
            SharedPreferences sharedPreferences = ((ei) this).f339a.getSharedPreferences("mipush_extra", 4);
            this.f24769a = sharedPreferences;
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putLong("last_mac_upload_timestamp", System.currentTimeMillis());
            editorEdit.commit();
        }
        String str = this.f344a;
        this.f344a = "";
        return str;
    }

    @Override // com.xiaomi.push.ei
    public boolean b() {
        if (c()) {
            return ag.a(((ei) this).f339a, String.valueOf(mo185a()), ((ei) this).f24755a);
        }
        int iMax = Math.max(3600, com.xiaomi.push.service.ao.a(((ei) this).f339a).a(ic.WifiDevicesMacWifiUnchangedCollectionFrequency.a(), R2.dimen.dp_584));
        long jCurrentTimeMillis = System.currentTimeMillis();
        SharedPreferences sharedPreferences = ((ei) this).f339a.getSharedPreferences("mipush_extra", 4);
        this.f24769a = sharedPreferences;
        return ((((float) Math.abs(jCurrentTimeMillis - sharedPreferences.getLong("last_mac_upload_timestamp", 0L))) > (((float) (iMax * 1000)) * 0.9f) ? 1 : (((float) Math.abs(jCurrentTimeMillis - sharedPreferences.getLong("last_mac_upload_timestamp", 0L))) == (((float) (iMax * 1000)) * 0.9f) ? 0 : -1)) >= 0) && ag.a(((ei) this).f339a, String.valueOf(mo185a()), (long) iMax);
    }

    public boolean c() {
        WifiInfo connectionInfo;
        try {
            SharedPreferences sharedPreferences = ((ei) this).f339a.getSharedPreferences("mipush_extra", 4);
            this.f24769a = sharedPreferences;
            String string = sharedPreferences.getString("last_wifi_ssid", "");
            WifiManager wifiManager = (WifiManager) ((ei) this).f339a.getSystemService("wifi");
            if (wifiManager.isWifiEnabled() && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                SharedPreferences.Editor editorEdit = this.f24769a.edit();
                editorEdit.putString("last_wifi_ssid", connectionInfo.getSSID());
                editorEdit.commit();
                if (TextUtils.isEmpty(string)) {
                    return false;
                }
                return !TextUtils.equals(connectionInfo.getSSID(), string);
            }
        } catch (Throwable unused) {
        }
        return true;
    }
}
